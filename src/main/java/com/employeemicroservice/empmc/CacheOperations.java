package com.employeemicroservice.empmc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class CacheOperations {
    @Autowired
    EmployeeRepositary employeeRepositary;

   public HashMap<Integer, Employee> cache = new HashMap<>();
   //We can make it static and call using class name then no need Autowiring of CacheOperation it will load on class time
   public List<Employee> employeeList ;

   @PostConstruct
   @Scheduled(cron = "${cronExpression}")
   public void loadCache(){
       System.out.println("Cache loading Started !!!!!");
      employeeList =employeeRepositary.findAll();
      if(!employeeList.isEmpty()){
          employeeList.forEach(employee -> cache.put(employee.getEmployeeId(), employee));

      }
       System.out.println("Cache loading ended !!!");
   }
}
