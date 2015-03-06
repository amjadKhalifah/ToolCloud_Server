package tum.toolcloud.server.idao;

import java.util.List;

import tum.toolcloud.server.model.Machine;


public interface IMachineDAO {

	
	Machine find(String mid);
	
	List<Machine> findAll();
	List<String> findIds();
	
}