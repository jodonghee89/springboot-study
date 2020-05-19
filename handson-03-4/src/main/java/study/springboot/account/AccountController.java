package study.springboot.account;

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
@RequestMapping("/account")
class AccountController {
    private final AccountService service;
    private final ObjectMapper mapper;

    AccountController(AccountService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping({"", "/list"})
    String pageGet(@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable, Model m) throws JsonProcessingException {
        Page<AccountDto> page = service.page(pageable);
        if (page.getTotalElements() > 0) {
            m.addAttribute("list", page.getContent());
            m.addAttribute("page", mapper.writeValueAsString(new PagingDto(page)));
        }
        return "account/list";
    }

    @GetMapping("/view")
    String viewGet(@RequestParam("id") long id, Model m) {
        m.addAttribute("account", service.find(id));
        return "account/view";
    }

    @GetMapping("/create")
    String createGet(Model m) {
        m.addAttribute("account", new AccountDto());
        return "account/save";
    }


    @PostMapping("/save")
    String createPost(@Valid AccountDto accountDto, BindingResult bindingResult, Model m) throws JsonProcessingException {
        if(bindingResult.hasErrors()) {
            m.addAttribute("account", new AccountDto());
            m.addAttribute("json", mapper.writeValueAsString(accountDto));
            m.addAttribute("hasError", true);
            m.addAttribute("errors", bindingResult.getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(toList()));
            return "account/save";
        }

        if(accountDto.id == 0)
            service.create(accountDto);
        else
            service.update(accountDto);

        return "redirect:/account/list";
    }

    @GetMapping("/update")
    String updateGet(@RequestParam("id") long id, Model m) throws JsonProcessingException {
        AccountDto accountDto = service.find(id);
        m.addAttribute("account", accountDto);
        m.addAttribute("json", mapper.writeValueAsString(accountDto));
        List checkbox_roles = accountDto.roles.stream().map(x -> "role_" + x.toLowerCase()).collect(toList());
        m.addAttribute("checkbox_roles", checkbox_roles);
        return "account/save";
    }

    @PostMapping("/delete")
    String deletePost(@RequestParam("id") long id) {
        service.delete(id);
        return "redirect:/account/list";
    }
}
