package com.msmata.task.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.msmata.task.model.Task;
import com.msmata.task.service.TaskService;
import com.msmata.task.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class TaskApiController {

	public static final Logger logger = LoggerFactory.getLogger(TaskApiController.class);

	@Autowired
	TaskService taskService; 

	@RequestMapping(value = "/task/", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> listAllTasks() {
		List<Task> tasks = taskService.findAllTasks();
		if (tasks.isEmpty()) {
			return new ResponseEntity<List<Task>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}

	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTask(@PathVariable("id") Integer id) {
		logger.info("Buscando tarea con id ", id);
		Task task = taskService.findById(id);
		if (task == null) {
			logger.error("Tarea con id {} no encontrada.", id);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Tarea con id " + id 	+ " no encontrada."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}

	@RequestMapping(value = "/task/", method = RequestMethod.POST)
	public ResponseEntity<?> createTask(@RequestBody Task task, UriComponentsBuilder ucBuilder) {
		logger.info("Creando tarea : {}", task);

		if (taskService.isTaskExist(task)) {
			logger.error("Ya existe tarea con nombre {}", task.getNombre());
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Ya existe tarea con nombre " + task.getNombre()),HttpStatus.CONFLICT);
		}
		taskService.saveTask(task);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/task/{id}").buildAndExpand(task.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/task/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTask(@PathVariable("id") Integer id, @RequestBody Task task) {
		logger.info("Actualizando Tarea con id {}", id);

		Task tarea = taskService.findById(id);

		if (tarea == null) {
			logger.error("Tarea con id {} no encontrada.", id);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Tarea con " + id + " no encontrada."), HttpStatus.NOT_FOUND);
		}

		tarea.setNombre(task.getNombre());

		taskService.saveTask(tarea);
		return new ResponseEntity<Task>(tarea, HttpStatus.OK);
	}

	@RequestMapping(value = "/tarea/{id}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id) {
		logger.info("Borrando tarea con id {}", id);

		Task task = taskService.findById(id);
		if (task == null) {
			logger.error("Tarea con id {} no encontrada.", id);
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Tarea con " + id + " no encontrada."), HttpStatus.NOT_FOUND);
		}
		taskService.deleteTaskById(id);
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/task/", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> deleteAllTasks() {
		logger.info("Borrando todos las tareas");

		taskService.deleteAllTasks();
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}

}