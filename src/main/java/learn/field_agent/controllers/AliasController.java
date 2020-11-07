package learn.field_agent.controllers;

import learn.field_agent.domain.AgentService;
import learn.field_agent.domain.AliasService;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"localhost:3000"})
@RequestMapping("/api/alias")
public class AliasController {

    private final AliasService service;

    private final AgentService agentService;

    public AliasController(AliasService service, AgentService agentService) {
        this.agentService = agentService;
        this.service = service;
    }

    @GetMapping("/{agentId}")
    public List<Object> findAliasesByAgentId(@PathVariable int agentId) {
        return service.findAliasesByAgentId(agentId);
    }


}
