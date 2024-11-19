package com.example.lab6.Controller;

import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity getEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body("Employee added");

    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable String id, @RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        for (Employee e : employees) {
            if (e.getID().equals(id)) {
                employees.set(employees.indexOf(e), employee);
                break;
            }
        }
            return ResponseEntity.status(200).body("Employee updated");

        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity deleteEmployee(@PathVariable String id){
        for (Employee e :employees){
            if(e.getID().equals(id)){
                employees.remove(employees.indexOf(e));
                return ResponseEntity.status(200).body("Employee deleted");
                }
        }
        return ResponseEntity.status(400).body("Employee Not found");
        }

        @GetMapping("/searchByPosition/{position}")
        public ResponseEntity searchByPosition(@PathVariable @Valid String position) {
        if(!position.equals("Coordinator")&&!position.equals("Supervisor")){
            return ResponseEntity.status(400).body("Position must be Supervisor or Coordinator");
        }
        ArrayList<Employee> newArray=new ArrayList<>();
        for (Employee employee:employees){
            if(employee.getPosition().equals(position))
                newArray.add(employee);
        }
        return ResponseEntity.status(200).body(newArray);


        }

        @GetMapping("/ageRange/{min}/{max}")
        public ResponseEntity ageRange(@PathVariable int min ,@PathVariable int max){
        if(min>25 && max<99) {
            ArrayList<Employee> newArray = new ArrayList<>();
            for (Employee employee : employees) {
                if (employee.getAge() >= min && employee.getAge() <= max) {
                    newArray.add(employee);
                }
            }return ResponseEntity.status(200).body(newArray);

        }
        return ResponseEntity.status(400).body("Age must be more than 25 and less than 99");
        }


        @PutMapping("/applyAnnual/{id}")
       public ResponseEntity applyAnnual_Leave(@PathVariable String id ){
        String message="Employee Not Found";
        for(Employee employee:employees){
            if(employee.getID().equals(id)){
                if(!employee.isOnLeave()){
                    if(employee.getAnnualLeave()>0){
                        employee.setOnLeave(true);
                    employee.setAnnualLeave(employee.getAnnualLeave()-1);
                    return ResponseEntity.status(200).body("Annual leave Applying successfully");
                    }else message="Annual Leave must be at least 1 day";
                }else message="Employee must not be on leave";
            }
        }
        return ResponseEntity.status(400).body(message);
       }


       @GetMapping("/noAnnual")
       public ResponseEntity NoAnnualEmployee(){
        ArrayList<Employee> noAnnualArray=new ArrayList<>();

        for(Employee employee:employees){
            if(employee.getAnnualLeave()==0)
                noAnnualArray.add(employee);
        }
        return ResponseEntity.status(200).body(noAnnualArray);
       }

@PutMapping("/promot/{id_Super}/{id_Employ}")
       public ResponseEntity PromotEmployee(@PathVariable String id_Super , @PathVariable String id_Employ) {
    Employee simpleEmploye = null;
String message ="Employee not found";
    ;
    for (Employee employee : employees) {
        if (employee.getID().equals(id_Employ)) {
            simpleEmploye = employee;
        }
    }
    for (Employee employee : employees) {
        if (employee.getID().equals(id_Super)) {
            if (employee.getPosition().equals("Supervisor")) {
                if (simpleEmploye.getAge() >= 30 && !simpleEmploye.isOnLeave()) {
                    simpleEmploye.setPosition("Supervisor");
                    return ResponseEntity.status(200).body("Employee Promoted successfully");
                } else message = "Employee doesn't meet the criteria";
            }else message = "requester is Not a supervisor.";
        }
    }
    return ResponseEntity.status(400).body(message);


}





    }

