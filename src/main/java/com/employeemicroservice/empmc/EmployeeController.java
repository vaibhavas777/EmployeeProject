package com.employeemicroservice.empmc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
public class EmployeeController {

// This is comment I have added to to check whether git is working is not 
// Proper comment added
    @Autowired
    EmployeeRepositary employeeRepositary;

    @Autowired
    CacheOperations cacheOperations;

    @GetMapping("/getMsg/{name}")
    public String getMsg(@PathVariable String name){

        return "hi "+name+ " now we are doing project Kick-Off";

    }
    @PostMapping("/saveEmployee")
   public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){

        try{
			cacheOperations.cache.put(employee.getEmployeeId(), employee);
            Employee emp =  employeeRepositary.save(employee);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
   }
    @PutMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){

     Optional<Employee> empld = employeeRepositary.findById(employee.getEmployeeId());
     if(empld.isPresent()){
		 cacheOperations.cache.put(employee.getEmployeeId(), employee);
         return new ResponseEntity<>(employeeRepositary.save(employee), HttpStatus.CREATED);
     }
       return new ResponseEntity<String>("Record corresponding to your id is not found,Check following...\n"+
               "Check your internet connection.\n"+"Check whether the id provided is correct or not.\n"+"Check url is correct or misspelled.", HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/getEmployee/{employeeId}")
    public ResponseEntity<?> retrieveEmployee(@PathVariable int employeeId){

  /* When we use Optional<Employee> as return type :if record with given id is
     present then Optional will return record otherwise null

     return  employeeRepositary.findById(employeeId);*/

        Employee emp =  cacheOperations.cache.get(employeeId);
        if(emp != null){
            return new ResponseEntity<>(emp,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Employee Record with id "+employeeId+" not found", HttpStatus.NOT_FOUND);
        }
    }

   @GetMapping("/getAllEmployees")
   public ResponseEntity<List<Employee>> getAllEmployees(){
   // Getting all employees from cache
        return new ResponseEntity<List<Employee>>(cacheOperations.cache.values().stream().collect(Collectors.toList()), HttpStatus.OK);

   }
   
   @DeleteMapping("/deleteEmployee/{id}")
   public String deleteEmployee(@PathVariable int id){

        if(employeeRepositary.findById(id).isPresent()){
            employeeRepositary.deleteById(id);
            return "Record deleted successfully !!!!";
        }
        else
        {
            return "Employee record not available with this id !!!!!";
        }

   }

}
