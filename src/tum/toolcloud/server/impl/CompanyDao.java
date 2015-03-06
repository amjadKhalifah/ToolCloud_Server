package tum.toolcloud.server.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import tum.toolcloud.server.idao.ICompanyDAO;
import tum.toolcloud.server.model.Company;
@Component
public class CompanyDao implements ICompanyDAO {
	
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	public int insertCompany(Company company) {
		String inserQuery = "insert into COMPANY (COMPANY_ID, NAME) values (?, ?) ";
		Object[] params = new Object[] { company.getCompanyId(),
				company.getName() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR};
		return jdbcTemplate.update(inserQuery, params, types);

	}


	@Override
	public List<Company> findAll() {
		String sql = "SELECT * FROM COMPANY";
		 
		List<Company> companies  = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(Company.class));
		return companies;
	}


	@Override
	public Company find(String cid) {
		String sql = "SELECT * FROM COMPANY WHERE COMPANY_ID = ?";
		 
		Company company = (Company)jdbcTemplate.queryForObject(
				sql, new Object[] { cid }, 
				new BeanPropertyRowMapper(Company.class));
	 
		return company;
	}

}
