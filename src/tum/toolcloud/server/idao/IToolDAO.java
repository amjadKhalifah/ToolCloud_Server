package tum.toolcloud.server.idao;

import java.util.List;

import tum.toolcloud.server.model.Tool;




public interface IToolDAO {

	Tool find(String tid);
	
	List<Tool> findAll();
	List<String> findIds();
	void update(Tool tool);

	void updateMachine(Tool tool);
	
	List<Tool> findByMachineId(String machineId);
	Tool findByIntakeId(String intakeId);
}