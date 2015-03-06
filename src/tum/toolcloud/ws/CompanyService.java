package tum.toolcloud.ws;

/**
 * @author Amjad
 */

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import tum.toolcloud.server.idao.ICompanyDAO;
import tum.toolcloud.server.model.Company;

@Component  
@Path("/company")
public class CompanyService {

	@Autowired
	ICompanyDAO companyDao;

	@GET
	@Produces("application/xml")
	public String findAll() {
		
		List<Company> companies = companyDao.findAll();	
		return createXml(companies);
	}

	@Path("{cid}")
	@GET
	@Produces("application/xml")
	public String find(@PathParam("cid") String cid) {
		Company company = companyDao.find(cid);	
		List<Company> companies  = new ArrayList<>();
		companies.add(company);
		return createXml(companies);

	}
	
	public String createXml(List<Company> companies){
	 String xml = "<Companies>";
	 for (Company company: companies){
		 xml += company.toString();
		 }
	 xml+="</Companies>";

	 return xml;
	}
}