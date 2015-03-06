package tum.toolcloud.server.idao;

import java.util.List;

import tum.toolcloud.server.model.Intake;


public interface IIntakeDAO {

	Intake find(String inid);
	
	List<Intake> findAll();
	
	List<String> findIds();
	
	void update(Intake intake);
	List<Intake> findByMachineId(String machineId);
	
}