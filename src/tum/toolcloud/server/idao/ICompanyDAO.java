package tum.toolcloud.server.idao;

import java.util.List;

import tum.toolcloud.server.model.Company;


public interface ICompanyDAO {

	Company find(String cid);
	
	List<Company> findAll();
}