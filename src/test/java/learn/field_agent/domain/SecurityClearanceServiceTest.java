package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Agent;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;

    @MockBean
    AgencyAgentRepository aaRepository;

    @Test
    void shouldNotAddDuplicate() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("Top Secret");

        when(repository.findAll()).thenReturn(new ArrayList<SecurityClearance>(Arrays.asList(new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret"))));

        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotAddWhenInvalid() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setName("   ");
        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

        securityClearance = null;
        actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

        securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(6);
        actual = service.add(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = makeSecurityClearance();
        SecurityClearance mockOut = makeSecurityClearance();
        mockOut.setSecurityClearanceId(1);

        when(repository.add(securityClearance)).thenReturn(mockOut);

        Result<SecurityClearance> actual = service.add(securityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(500);
        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.NOT_FOUND, actual.getType());

        securityClearance = makeSecurityClearance();
        securityClearance.setName("    ");
        actual = service.update(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());

        securityClearance = null;
        actual = service.update(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateDuplicate() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("Top Secret");

        when(repository.findAll()).thenReturn(new ArrayList<SecurityClearance>(Arrays.asList(new SecurityClearance(1, "Secret"),
                new SecurityClearance(2, "Top Secret"))));

        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        securityClearance.setName("Boogey Man eyes only");

        when(repository.update(securityClearance)).thenReturn(true);

        Result<SecurityClearance> actual = service.update(securityClearance);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldDelete() {
        when(aaRepository.findAll()).thenReturn(new ArrayList<AgencyAgent>(Arrays.asList(new AgencyAgent(1, "test",
                LocalDate.of(1954, 12, 16), true, new Agent(),
                new SecurityClearance(2, "Secret")))));

        when(repository.deleteById(1)).thenReturn(true);

        Result<SecurityClearance> actual = service.deleteById(1);
        assertEquals(true, actual.isSuccess());
    }

    @Test
    void shouldNotDeleteIfReferenced() {
        when(aaRepository.findAll()).thenReturn(new ArrayList<AgencyAgent>(Arrays.asList(new AgencyAgent(1, "test",
                LocalDate.of(1954, 12, 16), true, new Agent(),
                new SecurityClearance(2, "Secret")))));

        when(repository.deleteById(2)).thenReturn(true);

        Result<SecurityClearance> actual = service.deleteById(2);
        assertEquals(false, actual.isSuccess());
    }

    @Test
    void shouldNotDeleteFake() {
        when(aaRepository.findAll()).thenReturn(new ArrayList<AgencyAgent>(Arrays.asList(new AgencyAgent(1, "test",
                LocalDate.of(1954, 12, 16), true, new Agent(),
                new SecurityClearance(1, "Secret")))));

        when(repository.deleteById(50)).thenReturn(false);

        Result<SecurityClearance> actual = service.deleteById(50);
        assertEquals(false, actual.isSuccess());
    }

    SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("super-top-secret");
        return securityClearance;
    }


}
