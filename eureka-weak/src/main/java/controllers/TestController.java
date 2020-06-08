package controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/hello2")
    @ResponseBody
    public String getHello() {
        return "Hello 2. Return if the Jedi";
    }
}
