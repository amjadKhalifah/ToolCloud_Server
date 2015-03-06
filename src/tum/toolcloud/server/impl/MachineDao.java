package tum.toolcloud.server.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import tum.toolcloud.server.idao.IMachineDAO;
import tum.toolcloud.server.model.Company;
import tum.toolcloud.server.model.Machine;

@Component
public class MachineDao implements IMachineDAO {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int insertCompany(Company company) {
		String inserQuery = "insert into COMPANY (COMPANY_ID, NAME) values (?, ?) ";
		Object[] params = new Object[] { company.getCompanyId(),
				company.getName() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR };
		return jdbcTemplate.update(inserQuery, params, types);

	}

	@Override
	public Machine find(String mid) {
		String sql = "SELECT * FROM MACHINE WHERE MACHINE_ID = ?";
		try {
			Machine machine = (Machine) jdbcTemplate.queryForObject(sql,
					new Object[] { mid }, new BeanPropertyRowMapper(
							Machine.class));
			return machine;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Machine> findAll() {
		String sql = "SELECT * FROM MACHINE";

		List<Machine> machines = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(Machine.class));
		return machines;
	}

	@Override
	public List<String> findIds() {
		String sql = "SELECT MACHINE_ID FROM MACHINE";

		List<String> ids = (List<String>) jdbcTemplate.queryForList(sql,
				String.class);
		return ids;
	}

}
