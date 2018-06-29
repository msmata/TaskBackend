package com.msmata.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msmata.task.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	Task findByNombre(String nombre);
}
