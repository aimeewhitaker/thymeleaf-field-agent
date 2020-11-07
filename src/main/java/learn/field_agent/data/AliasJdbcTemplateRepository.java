package learn.field_agent.data;

import learn.field_agent.data.mappers.AgentMapper;
import learn.field_agent.data.mappers.AliasMapper;
import learn.field_agent.data.mappers.SecurityClearanceMapper;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AliasJdbcTemplateRepository implements AliasRepository {

    private final JdbcTemplate jdbcTemplate;

    private final AgentRepository repository;

    public AliasJdbcTemplateRepository(JdbcTemplate jdbcTemplate, AgentRepository repository) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Alias> findAll() {
        final String sql = "select * "
                + "from alias;";
        return jdbcTemplate.query(sql, new AliasMapper()).stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Object> findAliasesByAgentId(int agentId) {
        final String sql = "select a.alias_id, a.name, a.persona, a.agent_id "
                + "from alias a "
                + "where a.agent_id = ?;";
        return jdbcTemplate.query(sql, new AliasMapper(), agentId).stream()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Alias add(Alias alias) {
        final String sql = "insert into alias (name, persona, agent_id)"
                + "values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, alias.getName());
            ps.setString(2, alias.getPersona());
            ps.setInt(3, alias.getAgentId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        alias.setAliasId(keyHolder.getKey().intValue());

        //add alias to agent
        (repository.findById(alias.getAgentId())).getAliases().add(alias);

        return alias;
    }

    @Override
    @Transactional
    public boolean update(Alias alias) {
        final String sql = "update alias set "
                + "name = ?, "
                + "persona = ?, "
                + "agent_id = ? "
                + "where alias_id = ?;";

        return jdbcTemplate.update(sql,
                alias.getName(),
                alias.getPersona(),
                alias.getAgentId(),
                alias.getAliasId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int aliasId) {
        return jdbcTemplate.update(
                "delete from alias where alias_id = ?", aliasId) > 0;
    }
}
