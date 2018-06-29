package com.msmata.task.service;


import java.util.List;

import com.msmata.task.model.Task;

public interface TaskService {
	
	Task findByNombre(String nombre);
	
	Task findById(Integer id);
	
	void saveTask(Task task);
	
	void deleteTaskById(Integer id);

	List<Task> findAllTasks();
	
	void deleteAllTasks();
	
	boolean isTaskExist(Task task);
	
}
