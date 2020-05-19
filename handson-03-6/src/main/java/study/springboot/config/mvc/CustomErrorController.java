package study.springboot.config.mvc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model m) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            m.addAttribute("statusCode", statusCode);

            boolean hasImage = statusCode == 403 || statusCode == 404 || statusCode == 500;
            m.addAttribute("hasImage", hasImage);
        }
        return "error/page";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}