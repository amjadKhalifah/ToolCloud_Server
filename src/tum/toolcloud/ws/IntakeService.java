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

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import tum.toolcloud.server.idao.IIntakeDAO;
import tum.toolcloud.server.idao.IToolDAO;
import tum.toolcloud.server.impl.IntakeDao;
import tum.toolcloud.server.model.Intake;
import tum.toolcloud.server.model.Machine;
import tum.toolcloud.server.model.Tool;

@Component
@Path("/intake")
public class IntakeService {

	@Autowired
	IIntakeDAO intakeDao;
	@Autowired
	IToolDAO toolDao;

	@GET
	@Produces("application/xml")
	public String findAll() {

		List<Intake> intakes = intakeDao.findAll();
		return createXml(intakes);
	}

	@Path("{inid}")
	@GET
	@Produces("application/xml")
	public String find(@PathParam("inid") String inid) {
		Intake intake = intakeDao.find(inid);
		List<Intake> intakes = new ArrayList<>();
		intakes.add(intake);
		return createXml(intakes);

	}

	@Path("/details/{oid}")
	@GET
	@Produces("application/xml")
	public String getDetails(@PathParam("oid") String oid) {
		Intake intake;
		// get the intakes
		intake = intakeDao.find(oid);
		if (intake == null ){
			return "<Results> Unkown object<Results>";
		}
		Tool tool = toolDao.findByIntakeId(intake.getIntakeId());
		intake.setTool(tool);

		// get tools

		return createReultsXml(intake);
	}
	@Path("/details/json/{oid}")
	@GET
	@Produces("application/json")
	public String getJSONDetails(@PathParam("oid") String oid) {
		Intake intake;
		// get the intakes
		intake = intakeDao.find(oid);
		if (intake == null ){
			return "<Results> Unkown object<Results>";
		}
		Tool tool = toolDao.findByIntakeId(intake.getIntakeId());
		intake.setTool(tool);

		// get tools

		return createReultsJSON(intake);
	}

	private String createReultsJSON(Intake intake) {
		JSONObject xmlJSONObj = XML.toJSONObject(createReultsXml(intake));
		String jsonPrettyPrintString = xmlJSONObj.toString();
		return jsonPrettyPrintString;
	}
	private String createReultsXml(Intake intake) {
		String xml = "<Results>";
		if (intake != null) {
			xml += "<Intakes>";
			xml += intake.toStringWithTool();

			xml += "</Intakes>";
		}
		xml += "</Results>";
		return xml;
	}

	public String createXml(List<Intake> intakes) {
		String xml = "<Intakes>";
		for (Intake intake : intakes) {
			xml += intake.toString();
		}
		xml += "</Intakes>";

		return xml;
	}
}