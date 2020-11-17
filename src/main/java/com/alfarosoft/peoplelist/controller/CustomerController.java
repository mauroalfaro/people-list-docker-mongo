package com.alfarosoft.peoplelist.controller;

import com.alfarosoft.peoplelist.exception.PeopleListException;
import com.alfarosoft.peoplelist.model.Customer;
import com.alfarosoft.peoplelist.service.CustomerService;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

@RestController
@RequestMapping(value = "/services/customers")
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Adds a customer to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Customer successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid body applied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping(value = "/add", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> addCustomer (@RequestBody Customer customer){
        LOG.info("Incoming add request from CustomerController", keyValue("requestBody", customer));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.addCustomer(customer));
    }

    @Operation(summary = "Searches a Customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> lookupCustomer (@PathVariable String id){
        LOG.info("Incoming lookup request from CustomerController", keyValue("requestId", id));
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(id));
    }

    @Operation(summary = "Searches for all the Customers on the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "404", description = "Customers not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> searchCustomers(){
        LOG.info("Incoming search request from CustomerController");
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers());
    }

    @Operation(summary = "Updates Customer data after finding it by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PutMapping(value = "/update/{id}" , produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer (@PathVariable String id, @RequestBody Customer customer){
        LOG.info("Incoming update request from CustomerController", keyValue("requestBody", customer));
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, customer));
    }

    @Operation(summary = "Deletes a Customer after finding it by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @DeleteMapping(value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCustomer(@PathVariable String id){
        LOG.info("Incoming delete request from CustomerController", keyValue("requestId", id));
        customerService.removeCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer successfully removed");
    }

    @ExceptionHandler(PeopleListException.class)
    public ResponseEntity<String> handleException(final PeopleListException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
