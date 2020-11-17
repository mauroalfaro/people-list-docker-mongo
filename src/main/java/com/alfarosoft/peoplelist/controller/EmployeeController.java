package com.alfarosoft.peoplelist.controller;

import com.alfarosoft.peoplelist.exception.PeopleListException;
import com.alfarosoft.peoplelist.model.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alfarosoft.peoplelist.service.EmployeeService;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

@RestController
@RequestMapping(value = "/services/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Adds a employee to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Employee successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid body applied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployee (@RequestBody Employee employee){
        LOG.info("Incoming add request from EmployeeController", keyValue("requestBody", employee));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.addEmployee(employee));
    }

    @Operation(summary = "Searches a Employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Employee> lookupEmployee (@PathVariable String id){
        LOG.info("Incoming lookup request from EmployeeController", keyValue("requestId", id));
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployee(id));
    }

    @Operation(summary = "Searches for all the Employees on the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "404", description = "Employees not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> searchEmployees(){
        LOG.info("Incoming search request from EmployeeController");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployees());
    }

    @Operation(summary = "Updates Employee data after finding it by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PutMapping(value = "/update/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployee (@PathVariable String id, @RequestBody Employee employee){
        LOG.info("Incoming update request from EmployeeController", keyValue("requestBody", employee));
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(id, employee));
    }

    @Operation(summary = "Deletes a Employee after finding it by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class)) }),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @DeleteMapping(value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEmployee(@PathVariable String id){
        LOG.info("Incoming delete request from CustomerController", keyValue("requestId", id));
        employeeService.removeEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employee successfully removed");
    }

    @ExceptionHandler(PeopleListException.class)
    public ResponseEntity<String> handleException(final PeopleListException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
