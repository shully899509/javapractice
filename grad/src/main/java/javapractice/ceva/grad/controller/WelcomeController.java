package javapractice.ceva.grad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WelcomeController {

    @GetMapping("welcome")
    public String welcome(Model model){

        model.addAttribute("message", "hell");
        return "welcome";
    }
}
