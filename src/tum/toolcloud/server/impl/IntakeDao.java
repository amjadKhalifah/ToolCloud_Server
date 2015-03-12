package tum.toolcloud.server.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import tum.toolcloud.server.idao.IIntakeDAO;
import tum.toolcloud.server.model.Intake;

@Component
public class IntakeDao implements IIntakeDAO {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	@Override
	public Intake find(String inid) {
		String sql = "SELECT * FROM INTAKE WHERE INTAKE_ID = ?";
		try {
			Intake intake = (Intake) jdbcTemplate.queryForObject(sql,
					new Object[] { inid }, new BeanPropertyRowMapper(
							Intake.class));

			return intake;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Intake> findAll() {
		String sql = "SELECT * FROM INTAKE";

		List<Intake> intakes = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(Intake.class));
		return intakes;
	}

	@Override
	public void update(Intake intake) {
		String sql = "UPDATE INTAKE SET MACHINE_ID=?  WHERE INTAKE_ID = ?";

		jdbcTemplate.update(sql,
				new Object[] { intake.getMachineId(), intake.getIntakeId() });

	}

	@Override
	public List<String> findIds() {
		String sql = "SELECT INTAKE_ID FROM INTAKE";

		List<String> ids = (List<String>) jdbcTemplate.queryForList(sql,
				String.class);
		return ids;
	}



	@Override
	public List<Intake> findByMachineId(String machineId) {
		String sql = "SELECT * FROM INTAKE   WHERE MACHINE_ID=? ";

		List<Intake> intakes = jdbcTemplate.query(sql,new Object[] { machineId },
				new BeanPropertyRowMapper(Intake.class));
		return intakes;
	}

}
