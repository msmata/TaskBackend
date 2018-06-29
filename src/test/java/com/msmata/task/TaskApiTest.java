package com.msmata.task;

import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.msmata.task.model.Task;

public class TaskApiTest {

	public static final String REST_SERVICE_URI = "http://localhost:8080/TaskApi/api";

	private static String generateRandomString(int length) {
		Random random = new Random();
		return random.ints(48, 122).filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97)).mapToObj(i -> (char) i)
				.limit(length).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

	@Test
	public void testACreateTask() {
		RestTemplate restTemplate = new RestTemplate();
		Task task = new Task(0, generateRandomString(25));
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/task/", task, Task.class);
		assertTrue(uri != null);
		System.out.println("Location : " + uri.toASCIIString());
	}

	@Test
	public void testCListAllTasks() {

		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> tasksMap = restTemplate.getForObject(REST_SERVICE_URI + "/task/", List.class);
		assertTrue(tasksMap != null);
		if (tasksMap != null) {
			for (LinkedHashMap<String, Object> map : tasksMap) {
				System.out.println("task : id=" + map.get("id") + ", taskname=" + map.get("taskname"));
			}
		}
	}

	@Test
	public void testBGetTask() {
		RestTemplate restTemplate = new RestTemplate();
		Task task = restTemplate.getForObject(REST_SERVICE_URI + "/task/1", Task.class);
		System.out.println(task);
		assertTrue(task != null);
	}

	@Test
	public void testDUpdatetask() {
		RestTemplate restTemplate = new RestTemplate();
		Task task = new Task(1, "Tomy");
		restTemplate.put(REST_SERVICE_URI + "/task/1", task);
		assertTrue(true);
	}

	@Test(expected = HttpClientErrorException.class)
	public void testEDeletetask() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/task/1");
		restTemplate.getForObject(REST_SERVICE_URI + "/task/1", Task.class);
	}

	@Test
	public void testFDeleteAlltasks() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/task/");
	}

}
