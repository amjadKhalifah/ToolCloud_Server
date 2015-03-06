package tum.toolcloud.server.model;

import java.util.ArrayList;
import java.util.List;

public class Intake {

	
	private String intakeId;
	private String name;
	private String length;
	private String height;
	private String machineId;
	private Tool tool;
	public Intake() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Intake(String id, String name, String length, String height,
			String machineId) {
		super();
		this.intakeId = id;
		this.name = name;
		this.length = length;
		this.height = height;
		this.machineId = machineId;
	}
	public String getIntakeId() {
		return intakeId;
	}
	public void setIntakeId(String id) {
		this.intakeId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	
	
	public Tool getTool() {
		return tool;
	}
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	
	
	@Override
	public String toString() {
		
		return	"<Intake> "
				+ "<Intake_intakeId>" + intakeId + "</Intake_intakeId>"
				+ "<Intake_name>" + name + "</Intake_name>"
				+ "<Intake_length>" + length + "</Intake_length>"
				+ "<Intake_height>" + height + "</Intake_height>"
				+ "<Intake_machineId>" + machineId + "</Intake_machineId>"
		+ "</Intake>" ;
	

	}
	public String toStringWithTool() {
		if (tool == null )
			return toString();
		
		return	"<Intake> "
					+ "<Intake_intakeId>" + intakeId + "</Intake_intakeId>"
					+ "<Intake_name>" + name + "</Intake_name>"
					+ "<Intake_length>" + length + "</Intake_length>"
					+ "<Intake_height>" + height + "</Intake_height>"
					+ "<Intake_machineId>" + machineId + "</Intake_machineId>"
					+tool.toString2()
				+ "</Intake>" ;
		
	}
	
}
