package study.springboot.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.springboot.common.dto.PagingDto;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/user")
class UserController {
    private final UserService service;
    private final ObjectMapper mapper;

    UserController(UserService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping({"", "/list"})
    String pageGet(@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
                   UserSearchDto searchDto,
                   Model m) throws JsonProcessingException {
        Page<UserDto> page = service.page(pageable, searchDto);
        if(page.getTotalElements() > 0 ) {
            m.addAttribute("list", page.getContent());
            m.addAttribute("page",  mapper.writeValueAsString(new PagingDto(page)));
            m.addAttribute("search", mapper.writeValueAsString(searchDto));
        }

        return "user/list";
    }

    @GetMapping("/view")
    String viewGet(@RequestParam("id") long id, Model m) {
        m.addAttribute("user", service.find(id));
        return "user/view";
    }

    @GetMapping("/create")
    String createGet(Model m) {
        m.addAttribute("user", new UserDto());
        return "user/save";
    }


    @PostMapping("/save")
    String createPost(@Valid UserDto accountDto, BindingResult bindingResult, Model m) throws JsonProcessingException {
        if(bindingResult.hasErrors()) {
            m.addAttribute("user", new UserDto());
            m.addAttribute("json", mapper.writeValueAsString(accountDto));
            m.addAttribute("hasError", true);
            m.addAttribute("errors", bindingResult.getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(toList()));
            return "user/save";
        }

        if(accountDto.id == 0)
            service.create(accountDto);
        else
            service.update(accountDto);

        return "redirect:/user/list";
    }

    @GetMapping("/update")
    String updateGet(@RequestParam("id") long id, Model m) throws JsonProcessingException {
        UserDto userDto = service.find(id);
        m.addAttribute("user", userDto);
        m.addAttribute("json", mapper.writeValueAsString(userDto));
        List checkbox_roles = userDto.roles.stream().map(x -> "role_" + x.toLowerCase()).collect(toList());
        m.addAttribute("checkbox_roles", checkbox_roles);
        return "user/save";
    }

    @PostMapping("/delete")
    String deletePost(@RequestParam("id") long id) {
        service.delete(id);
        return "redirect:/user/list";
    }
}
