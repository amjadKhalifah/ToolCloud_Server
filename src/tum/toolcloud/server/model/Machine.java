package tum.toolcloud.server.model;

public class Machine {

	private String machineId;
	private String name;
	private String der;//?
	private String companyName;
	
	public Machine() {

	}
	public Machine(String id, String name, String der, String companyName) {
		super();
		this.machineId = id;
		this.name = name;
		this.der = der;
		this.companyName = companyName;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String id) {
		this.machineId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDer() {
		return der;
	}
	public void setDer(String der) {
		this.der = der;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return	"<Machine> "
					+ "<machineId>" + machineId + "</machineId>"
					+ "<name>" + name + "</name>"
					+ "<der>" + der + "</der>"
					+ "<companyId>" + companyName + "</companyId>"
			+ "</Machine>" ;
		
		
		
	}
	
	
}
