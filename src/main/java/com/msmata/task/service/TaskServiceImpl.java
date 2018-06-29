package com.msmata.task.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msmata.task.dao.TaskRepository;
import com.msmata.task.model.Task;

@Service("taskService")
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository taskRepository;
	
	public List<Task> findAllTasks() {
		return taskRepository.findAll();
	}
	
	public Task findById(Integer id) {
		return taskRepository.findOne(id);
	}
	
	public Task findByNombre(String nombre) {
		return taskRepository.findByNombre(nombre);
	}
	
	public void saveTask(Task task) {
		taskRepository.save(task);
	}

	public void deleteTaskById(Integer id) {
		taskRepository.delete(id);
	}

	public boolean isTaskExist(Task task) {
		return findByNombre(task.getNombre())!=null;
	}
	
	public void deleteAllTasks(){
		taskRepository.deleteAll();
	}

}
