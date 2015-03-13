package tum.toolcloud.server.model;

public class Tool {
	
	
	private String toolId;
	private String name;
	private String length;
	private String height;
	private String machineId;
	private String intakeId;
	private String cad;
	public Tool() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tool(String id, String name, String length, String height,
			String machineId, String intakeId) {
		super();
		this.toolId = id;
		this.name = name;
		this.length = length;
		this.height = height;
		this.machineId = machineId;
		this.intakeId = intakeId;
	}
	public String getToolId() {
		return toolId;
	}
	public void setToolId(String id) {
		this.toolId = id;
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
	public String getIntakeId() {
		return intakeId;
	}
	public void setIntakeId(String intakeId) {
		this.intakeId = intakeId;
	}
	
	
	public String getCad() {
		return cad;
	}
	public void setCad(String cad) {
		this.cad = cad;
	}
	@Override
	public String toString() {
		
		return "<Tool> "
				+ "<Tool_toolId>" + toolId + "</Tool_toolId>"
				+ "<Tool_name>" + name + "</Tool_name>"
				+ "<Tool_length>" + length + "</Tool_length>"
				+ "<Tool_height>" + height + "</Tool_height>"
				+ "<Tool_machineId>" + machineId + "</Tool_machineId>"
				+ "<Tool_intakeId>" + intakeId + "</Tool_intakeId>"
				+ "<Tool_cad>" + cad + "</Tool_cad>"
		+ "</Tool>" ;
		
	}
	
	public String toString2() {
		
		return "<Intake_Tool> "
				+ "<Intake_Tool_toolId>" + toolId + "</Intake_Tool_toolId>"
				+ "<Intake_Tool_name>" + name + "</Intake_Tool_name>"
				+ "<Intake_Tool_length>" + length + "</Intake_Tool_length>"
				+ "<Intake_Tool_height>" + height + "</Intake_Tool_height>"
				+ "<Intake_Tool_machineId>" + machineId + "</Intake_Tool_machineId>"
				+ "<Intake_Tool_intakeId>" + intakeId + "</Intake_Tool_intakeId>"
			    + "<Intake_Tool_cad>" + cad + "</Intake_Tool_cad>"
		+ "</Intake_Tool>" ;
		
	}
	
}
