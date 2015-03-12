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

import tum.toolcloud.server.idao.IIntakeDAO;
import tum.toolcloud.server.idao.IMachineDAO;
import tum.toolcloud.server.impl.ToolDao;
import tum.toolcloud.server.model.Intake;
import tum.toolcloud.server.model.Machine;
import tum.toolcloud.server.model.Tool;

@Component
@Path("/machine")
public class MachineService {

	@Autowired  
	IMachineDAO machineDao;
	@Autowired
	IIntakeDAO intakeDao;
	@Autowired
	ToolDao toolDao;


	@GET
	@Produces("application/xml")
	public String findAll() {
		
		List<Machine> machines = machineDao.findAll();	
		return createXml(machines);
	}

	@Path("{mid}")
	@GET
	@Produces("application/xml")
	public String find(@PathParam("mid") String mid) {
		Machine machine = machineDao.find(mid);	
		List<Machine> machines  = new ArrayList<>();
		machines.add(machine);
		return createXml(machines);

	}
	

	@Path("/identify/{oid}")
	@GET
	@Produces("application/xml")
	public String identify(@PathParam("oid") String oid) {
		Machine machine = machineDao.find(oid);	
		if (machine != null)
		{
			return createObjectXml("machine", machine.getName(), machine.getMachineId(),false,false); 
		} 
		
		Intake intake = intakeDao.find(oid);	
		if (intake != null)
		{
			Tool tool = toolDao.findByIntakeId(intake.getIntakeId());
			boolean aggregatedAsParent = (tool==null)? false:true;
			boolean aggregatedAsChild = (intake.getMachineId()==null)? false:true;
			return createObjectXml("intake", intake.getName(), intake.getIntakeId(),aggregatedAsChild ,aggregatedAsParent); 
		}
		
		Tool tool = toolDao.find(oid);	
		if (tool != null)
		{
			boolean aggregatedAsChild=false;
			if (tool.getMachineId()!=null || tool.getIntakeId()!=null){
				aggregatedAsChild = true;
			}
			return createObjectXml("tool", tool.getName(), tool.getToolId(),aggregatedAsChild,false); 
		}
		
		else {
			
			return createObjectXml("undefined","Object not found in database.", "undefined",false,false); 
		}
		

	}
	
	
	
	@Path("/details/{oid}")
	@GET
	@Produces("application/xml")
	public String getDetails(@PathParam("oid") String oid) {
		Machine machine;
		List<Intake>intakes= new ArrayList<>();
		List<Tool> tools = new ArrayList<>();
		// get the machine info
		machine = machineDao.find(oid);	
		// get the intakes
		intakes = intakeDao.findByMachineId(oid);
		// loop intakes and get tools
		for (Intake intake: intakes){
			// could return null;
			Tool tool = toolDao.findByIntakeId(intake.getIntakeId());
			intake.setTool(tool);
		}
		
		// get tools 
		tools = toolDao.findByMachineId(oid);
		
		return createReultsXml(machine,intakes,tools);
	}
	
	

	private String createReultsXml(Machine machine, List<Intake> intakes,
			List<Tool> tools) {
		 String xml = "<Results>";
		 if (machine!=null){
			 xml += machine.toString();
		 }
		 if (intakes!=null && !intakes.isEmpty()){
			 xml+="<Intakes>";
			 for (Intake intake: intakes){
				 xml += intake.toStringWithTool();
				}
				
			 xml+="</Intakes>";
		 }
		 if (tools!=null && !tools.isEmpty()){
			 xml+="<Tools>";
			 for (Tool tool: tools){
				 xml += tool.toString();
				}
				
			 xml+="</Tools>";
		 }
		 xml+="</Results>";
		 return xml;
	}

	public String createXml(List<Machine> machines){
	 String xml = "<Machines>";
	 for (Machine machine: machines){
		 xml += machine.toString();
		 }
	 xml+="</Machines>";

	 return xml;
	}
	
	/** used in identifying objects
	 * @param type
	 * @param name
	 * @param id
	 * @return
	 */
	public String createObjectXml(String type, String name, String id, boolean aggregatedAsChild,boolean aggregatedAsParent){
		 String xml = "<Object>";
		 
		 	xml+="<type>"+type+"</type>";
			xml+="<name>"+name+"</name>";
			xml+="<id>"+id+"</id>";
			xml+="<aggregatedAsChild>"+aggregatedAsChild+"</aggregatedAsChild>";
			xml+="<aggregatedAsParent>"+aggregatedAsParent+"</aggregatedAsParent>";
		 xml+="</Object>";

		 return xml;
		}
	
	
	
	
}