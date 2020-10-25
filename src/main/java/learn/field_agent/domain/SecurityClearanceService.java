package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository repository;

    private final AgencyAgentRepository aaRepository;

    public SecurityClearanceService(SecurityClearanceRepository repository, AgencyAgentRepository aaRepository) {
        this.repository = repository;
        this.aaRepository = aaRepository;
    }

    public List<SecurityClearance> findAll() {
        return repository.findAll();
    }

    public SecurityClearance findById(int securityClearanceId) {
        return repository.findById(securityClearanceId);
    }

    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);

        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("securityClearanceId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        SecurityClearance sc = repository.add(securityClearance);
        result.setPayload(sc);
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);

        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("securityClearanceId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(securityClearance)) {
            String msg = String.format("securityClearanceId: %s, not found", securityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<SecurityClearance> deleteById(int securityClearanceId) {
        Result<SecurityClearance> result = new Result<>();
        if (aaRepository.findAll().stream()
                .anyMatch(agencyAgent -> agencyAgent.
                        getSecurityClearance().getSecurityClearanceId() == securityClearanceId)) {
            result.addMessage("security clearance referenced in agency-agent", ResultType.INVALID);
            return result;
        }
        if (!repository.deleteById(securityClearanceId)) {
            result.addMessage("could not delete", ResultType.NOT_FOUND);
            return result;
        }
        result.addMessage("", ResultType.SUCCESS);
        return result;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();

        if (securityClearance == null) {
            result.addMessage("security clearance cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (repository.findAll().stream()
            .anyMatch(sc -> sc.getName().equals(securityClearance.getName()))) {
            result.addMessage("name is duplicate", ResultType.INVALID);
        }

        return result;
    }
}
