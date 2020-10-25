package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @MockBean
    AgentRepository agentRepository;

    @Test
    void shouldNotAddWhenInvalid() {
        Alias alias = new Alias();
        alias.setName("   ");

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());

        alias = null;
        actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddNonexistentAgent() {
        Alias alias = new Alias();
        alias.setName("aimee");
        alias.setAgentId(3000);

        when(agentRepository.findAll()).thenReturn(new ArrayList<Agent>(Arrays.asList()));

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddWhenDuplicate() {
        Alias alias = new Alias();
        alias.setName("jumbo");
        alias.setAgentId(1);

        when(agentRepository.findAll()).thenReturn(new ArrayList<Agent>(Arrays.asList(new Agent(
                1, "cindy", null, "whitaker", null, 65))));

        when(repository.findAll()).thenReturn(new ArrayList<Alias>(Arrays.asList(
                new Alias(1, "jumbo", null, 1))));

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldAdd() {
        Alias alias = new Alias();
        alias.setName("jumbo");
        alias.setAgentId(1);

        when(agentRepository.findAll()).thenReturn(new ArrayList<Agent>(Arrays.asList(new Agent(
                1, "cindy", null, "whitaker", null, 65))));

        Result<Alias> actual = service.add(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdate() {
        Alias alias = new Alias();
        alias.setAliasId(0);
        alias.setName("jumbo");
        alias.setAgentId(1);

        when(agentRepository.findAll()).thenReturn(new ArrayList<Agent>(Arrays.asList(new Agent(
                1, "cindy", null, "whitaker", null, 65))));

        when(repository.findAll()).thenReturn(new ArrayList<Alias>(Arrays.asList(
                new Alias(1, "jumbo", null, 1))));

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWithDuplicate() {
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("jumbo");
        alias.setAgentId(1);

        when(agentRepository.findAll()).thenReturn(new ArrayList<Agent>(Arrays.asList(new Agent(
                1, "cindy", null, "whitaker", null, 65))));

        when(repository.findAll()).thenReturn(new ArrayList<Alias>(Arrays.asList(
                new Alias(1, "jumbo", null, 1))));

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("candy");
        alias.setAgentId(1);
        alias.setPersona(null);

        when(repository.findAll()).thenReturn(new ArrayList<Alias>(Arrays.asList(
                new Alias(1, "jumbo", null, 1),
                new Alias(2, "booger", "hallmark", 1))));

        when(agentRepository.findAll()).thenReturn(new ArrayList<Agent>(Arrays.asList(new Agent(
                1, "cindy", null, "whitaker", null, 65))));

        when(repository.update(alias)).thenReturn(true);

        Result<Alias> actual = service.update(alias);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);

        Result<Alias> result = service.deleteById(1);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDelete() {
        when(repository.deleteById(1)).thenReturn(false);

        Result<Alias> result = service.deleteById(1);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }
}

