package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;
    private final AgentRepository agentRepository;


    public AliasService(AliasRepository repository, AgentRepository agentRepository) {
        this.repository = repository;
        this.agentRepository = agentRepository;
    }

    public List<Object> findAliasesByAgentId(int agentId) {
        List<Object> comboList = new ArrayList();
        comboList.add(agentRepository.findById(agentId));
        comboList.add(repository.findAliasesByAgentId(agentId));
        return comboList;
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);

        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("aliasId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        Alias a = repository.add(alias);
        result.setPayload(a);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);

        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("aliasId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("aliasId: %s, not found", alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Alias> deleteById(int aliasId) {
        Result<Alias> result = new Result<>();

        if (!repository.deleteById(aliasId)) {
            result.addMessage("could not delete", ResultType.NOT_FOUND);
            return result;
        }
        result.addMessage("", ResultType.SUCCESS);
        return result;
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("alias cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (!agentRepository.findAll().stream().anyMatch(agent -> agent.getAgentId() == alias.getAgentId())) {
            result.addMessage("agent does not exist", ResultType.INVALID);
        }

        if (repository.findAll().stream()
                .anyMatch(sc -> sc.getName().equals(alias.getName()))) {
            if (Validations.isNullOrBlank(alias.getPersona())) {
                result.addMessage("persona cannot be blank if null is duplicate", ResultType.INVALID);
            }
        }
        return result;
    }
}
