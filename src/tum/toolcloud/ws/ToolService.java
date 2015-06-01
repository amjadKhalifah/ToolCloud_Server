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

import tum.toolcloud.server.idao.IToolDAO;
import tum.toolcloud.server.impl.ToolDao;
import tum.toolcloud.server.model.Intake;
import tum.toolcloud.server.model.Tool;
@Component
@Path("/tool")
public class ToolService {
	
	@Autowired
	IToolDAO toolDao;

	

	@GET
	@Produces("application/xml")
	public String findAll() {
		
		List<Tool> tools = toolDao.findAll();	
		return createXml(tools);
	}

	@Path("{tid}")
	@GET
	@Produces("application/xml")
	public String find(@PathParam("tid") String tid) {
		Tool tool = toolDao.find(tid);	
		List<Tool> tools  = new ArrayList<>();
		tools.add(tool);
		return createXml(tools);

	}
	
	
	@Path("/details/{oid}")
	@GET
	@Produces("application/xml")
	public String getDetails(@PathParam("oid") String oid) {
		Tool tool;
		// get the intakes
		tool = toolDao.find(oid);
		if (tool == null ){
			return "<Results> Unkown object<Results>";
		}
		
		

		return createReultsXml(tool);
	}
	@Path("/details/json/{oid}")
	@GET
	@Produces("application/json")
	public String getJSONDetails(@PathParam("oid") String oid) {
		Tool tool;
		// get the intakes
		tool = toolDao.find(oid);
		if (tool == null ){
			return "<Results> Unkown object<Results>";
		}

		return createReultsJSON(tool);
	}
	private String createReultsJSON(Tool tool) {
		JSONObject xmlJSONObj = XML.toJSONObject(createReultsXml(tool));
		String jsonPrettyPrintString = xmlJSONObj.toString();
		return jsonPrettyPrintString;
	}
	private String createReultsXml(Tool tool) {
		String xml = "<Results>";
		if (tool != null) {
			xml += "<Tools>";
			xml += tool.toString();

			xml += "</Tools>";
		}
		xml += "</Results>";
		return xml;
	}

	
	public String createXml(List<Tool> tools){
	 String toolsXml = "<Tools>";
	 for (Tool tool: tools){
		 toolsXml += tool.toString();
		 }
	 toolsXml+="</Tools>";

	 return toolsXml;
	}
}