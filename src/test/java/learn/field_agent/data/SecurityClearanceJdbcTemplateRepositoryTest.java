package learn.field_agent.data;

import learn.field_agent.models.Location;
import learn.field_agent.models.SecurityClearance;
import org.apache.catalina.security.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<SecurityClearance> securityClearanceList = repository.findAll();

        assertTrue(securityClearanceList.size() >= 1);

    }

    @Test
    void shouldFindById() {
        SecurityClearance actual = repository.findById(1);
        assertNotNull(actual);
    }

    @Test
    void shouldNotFindById() {
        SecurityClearance actual = repository.findById(repository.findAll().size() + 100);
        assertEquals(null, actual);
    }

    @Test
    void shouldAdd() {
        SecurityClearance expected = new SecurityClearance();
        expected.setName("presidential");

        SecurityClearance actual = repository.add(expected);

        assertEquals("presidential",actual.getName());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        assertTrue(repository.update(securityClearance));
        securityClearance.setSecurityClearanceId(repository.findAll().size() + 100);
        assertFalse(repository.update(securityClearance));
    }

    @Test
    void shouldDelete() {
        SecurityClearance securityClearance = makeSecurityClearance();
        SecurityClearance securityClearance1 = repository.add(securityClearance);
        assertTrue(repository.deleteById(securityClearance1.getSecurityClearanceId()));
        assertFalse(repository.deleteById(securityClearance1.getSecurityClearanceId()));
    }

    SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("super-top-secret");
        securityClearance.setSecurityClearanceId(1);
        return  securityClearance;
    }


}