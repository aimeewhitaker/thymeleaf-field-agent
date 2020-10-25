package learn.field_agent.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.field_agent.data.AgencyRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.Agency;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityClearanceControllerTest {

    @MockBean
    SecurityClearanceRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void addShouldReturn400WhenEmpty() throws Exception {

        var request = post("/api/security_clearance")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        SecurityClearance securityClearance = new SecurityClearance();
        String agencyJson = jsonMapper.writeValueAsString(securityClearance);

        var request = post("/api/security_clearance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agencyJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn415WhenMultipart() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        SecurityClearance securityClearance = new SecurityClearance(0, "Test");
        String agencyJson = jsonMapper.writeValueAsString(securityClearance);

        var request = post("/api/security_clearance")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(agencyJson);

        mvc.perform(request)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void addShouldReturn201() throws Exception {

        SecurityClearance securityClearance = new SecurityClearance(0, "Test");
        SecurityClearance expected = new SecurityClearance(1, "Test");

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String securityClearanceJson = jsonMapper.writeValueAsString(securityClearance);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/api/security_clearance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(securityClearanceJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

/*    @Test
    void updateShouldReturn201() throws Exception {
        SecurityClearance updated = new SecurityClearance(1, "Test Cookies");

        when(repository.update(any())).thenReturn(true);
        ObjectMapper jsonMapper = new ObjectMapper();

        String securityClearanceJson = jsonMapper.writeValueAsString(updated);

        var updateRequest = put("/api/security_clearance/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(securityClearanceJson)
                .content(1);

        mvc.perform(updateRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(securityClearanceJson));
    }*/

}
