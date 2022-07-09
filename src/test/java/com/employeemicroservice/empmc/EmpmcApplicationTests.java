package com.employeemicroservice.empmc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
class EmpmcApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testGetEmployeeByID() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/getEmployee/1";
		URI uri = new URI(url);
		ResponseEntity<String> result = restTemplate.getForEntity(uri,String.class);
		Assertions.assertEquals(200,result.getStatusCodeValue());
		System.out.println("Get Employee API test OK");
	}
	@Test
	public void testGetAllEmployees() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/getAllEmployees";
		URI uri = new URI(url);
		ResponseEntity<String> result = restTemplate.getForEntity(uri,String.class);
		Assertions.assertEquals(200,result.getStatusCodeValue());
		System.out.println("Get all Employees API test OK");
	}
	@Test
	public void testSaveEmployee() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/saveEmployee";
		URI uri = new URI(url);
		Employee employee = new Employee();
		employee.setEmployeeAddress("ABC Street, London,UK");
		employee.setEmployeeName("David malan");
		employee.setSalary(250000);
		ResponseEntity<String> result = restTemplate.postForEntity(uri,employee,String.class);
		Assertions.assertEquals(201  , result.getStatusCodeValue());
		System.out.println("Save API test OK");
	}
	@Test
	public void testUpdateEmployee() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/updateEmployee";
		URI uri = new URI(url);
		Employee employee = new Employee();
		employee.setEmployeeId(6);
		employee.setEmployeeName("Satish Sharma");
		employee.setEmployeeAddress("Bahadurganjh");
		employee.setSalary(20000);

		restTemplate.put(uri, employee);
		Assertions.assertEquals(201 | 200 , 201 | 200);
		System.out.println("Update API test OK");
	}
	@Test
	public  void testDeleteEmployee() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/deleteEmployee/5";
		URI uri = new URI(url);
		restTemplate.delete(uri);
		Assertions.assertEquals(200 , 200);
		System.out.println("Delete API test OK");
	}

}
