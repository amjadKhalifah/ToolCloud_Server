package tum.toolcloud.server.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import tum.toolcloud.server.idao.IToolDAO;
import tum.toolcloud.server.model.Tool;

@Component
public class ToolDao implements IToolDAO {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public Tool find(String tid) {
		String sql = "SELECT * FROM Tool WHERE Tool_ID = ?";
		try {
			Tool intake = (Tool) jdbcTemplate
					.queryForObject(sql, new Object[] { tid },
							new BeanPropertyRowMapper(Tool.class));

			return intake;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Tool> findAll() {
		String sql = "SELECT * FROM TOOL";

		List<Tool> tools = jdbcTemplate.query(sql, new BeanPropertyRowMapper(
				Tool.class));
		return tools;
	}

	@Override
	public void update(Tool tool) {
		String sql = "UPDATE TOOL SET INTAKE_ID=? WHERE TOOL_ID = ?";

		jdbcTemplate.update(sql,
				new Object[] { tool.getIntakeId(), tool.getToolId() });

	}

	@Override
	public void updateMachine(Tool tool) {
		String sql = "UPDATE TOOL SET  MACHINE_ID=?  WHERE TOOL_ID = ?";

		jdbcTemplate.update(sql,
				new Object[] { tool.getMachineId(), tool.getToolId() });

	}

	@Override
	public List<String> findIds() {
		String sql = "SELECT TOOL_ID FROM TOOL";

		List<String> ids = (List<String>) jdbcTemplate.queryForList(sql,
				String.class);
		return ids;
	}
	
	@Override
	public List<Tool> findByMachineId(String machineId) {
		String sql = "SELECT * FROM TOOL   WHERE MACHINE_ID=? ";

		List<Tool> tools = jdbcTemplate.query(sql,new Object[] { machineId },
				new BeanPropertyRowMapper(Tool.class));
		return tools;
	}

	@Override
	public Tool findByIntakeId(String intakeId) {
		String sql = "SELECT * FROM Tool WHERE INTAKE_ID = ?";
		try {
			Tool intake = (Tool) jdbcTemplate
					.queryForObject(sql, new Object[] { intakeId },
							new BeanPropertyRowMapper(Tool.class));

			return intake;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

}
