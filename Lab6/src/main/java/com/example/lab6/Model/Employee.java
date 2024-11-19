package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "Empty ID")
    @Size(min = 3,message = "ID length must be mre than 2")
    private String ID ;
    @NotEmpty(message = "Empty name")
    @Size(min = 5 ,message = "Name length must be more than 4 ")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only alphabetic characters and spaces")
    private String name ;
    @Email(message = "Enter valid email")
    private String email ;
    @Pattern(regexp = "^05\\d{8}$" ,message = "Phone number must start with '05' and be 10 digits long")
    private String phoneNumber ;
    @NotNull(message = "Empty age")
    @Positive(message = "Number")
    @Min(value = 26,message = "Age must be more than 25")
    @Max(value = 98,message = "Age must be less than 99")
    private int age ;
    @NotEmpty(message = "Empty position")
    @Pattern(regexp = "Supervisor|Coordinator",message = "Position must be Supervisor or Coordinator ")
    private String position;
    private boolean onLeave=false;
    @NotNull(message = "Empty Hire date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Hire Date should be in the past or present")
    private LocalDate hireDate ;
    @NotNull(message = "Empty Annual Leave")
    @PositiveOrZero(message = "Annual leave must be positive")
    private int annualLeave ;
}
