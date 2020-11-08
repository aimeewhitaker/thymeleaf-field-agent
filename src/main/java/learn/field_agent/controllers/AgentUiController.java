package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import learn.field_agent.models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AgentUiController {

    @Autowired
    AgentService service;

    @GetMapping("/agents")
    public String getAgentHome(Model model) {
        model.addAttribute("agents", service.findAll());
        return "agents/agents";
    }

    @GetMapping("/agents/add")
    public String getAddView(@ModelAttribute("agent") Agent agent) {
        return "agents/add";
    }

    @PostMapping("/agents/add")
    public String handleAdd(
            @ModelAttribute("agent") @Valid Agent agent,
            BindingResult result) {

        System.out.println(agent);

        if (result.hasErrors()) {
            return "agents/add";
        }

        service.add(agent);

        return "redirect:/agents";
    }

    @GetMapping("/agents/delete/{id}")
    public String getDeleteView(@PathVariable int id, Model model) {
        Agent agent = service.findById(id);
        if (agent == null) {
            return "not-found";
        }
        model.addAttribute("agent", agent);
        return "agents/delete";
    }

    @PostMapping("/agents/delete/{id}")
    public String handleDelete(@PathVariable int id) {
        Agent agent = service.findById(id);
        if (agent == null) {
            return "not-found";
        }
        service.deleteById(id);
        return "redirect:/agents";
    }

    @GetMapping("/agents/update/{id}")
    public String displayEdit(@PathVariable int id, Model model) {
        System.out.println(id);
        Agent agent = service.findById(id);
        if (agent == null) {
            return "not-found";
        }
        model.addAttribute("agent", agent);
        return "agents/update";
    }

    @PostMapping("/agents/update/*")
    public String handleEdit(
            @ModelAttribute("agent") @Valid Agent agent,
            BindingResult result) {
        System.out.println(agent.getAgentId());

        if (result.hasErrors()) {
            return "agents/update";
        }

        service.update(agent);

        System.out.println(agent.getAgentId());

        return "redirect:/agents";
    }

}
