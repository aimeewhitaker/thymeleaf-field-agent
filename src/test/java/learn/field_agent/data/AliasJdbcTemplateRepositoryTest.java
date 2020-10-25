package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasJdbcTemplateRepositoryTest {

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

/*    @Test
    void shouldFindAgentAliasesById() {
        List<Alias> aliases = repository.findAliasesByAgentId(2);
        assertTrue(aliases.size() == 1);
    }*/

    @Test
    void shouldAddAlias() {
        Alias alias = new Alias();
        alias.setName("Juicy Lucy");
        alias.setPersona("burger");
        alias.setAgentId(1);

        Alias actual = repository.add(alias);

        assertNotNull(actual);
    }

/*    @Test
    void shouldDelete() {
        boolean actual = repository.deleteById(1);
        assertTrue(actual);
        actual = repository.deleteById(1);
        assertFalse(actual);
    }*/
}
