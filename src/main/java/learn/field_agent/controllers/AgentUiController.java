package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgentUiController {

    @Autowired
    AgentService service;

    @GetMapping("/")
    public String getHomeView() {

        return "home";
    }

//    @GetMapping("/agents")
//    public String getAgentHome(Model model) {
//        model.addAttribute("agents", service.findAll());
//        return "agents";
//    }

}
