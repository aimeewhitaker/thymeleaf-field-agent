package learn.field_agent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FieldAgentUIController {


    @GetMapping("/")
    public String getHomeView() {
        return "home"; }
}
