package com.alfarosoft.peoplelist.controller;

import com.alfarosoft.peoplelist.exception.PeopleListException;
import com.alfarosoft.peoplelist.model.Store;
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
import com.alfarosoft.peoplelist.service.StoreService;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

@RestController
@RequestMapping(value = "/services/stores")
public class StoreController {
    private final StoreService storeService;
    private static final Logger LOG = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Operation(summary = "Adds a Store to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Store successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Store.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid body applied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> addStore(@RequestBody Store store){
        LOG.info("Incoming add request from StoreController", keyValue("requestBody", store));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(storeService.addStore(store));
    }

    @Operation(summary = "Searches a Store by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Store.class)) }),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Store> lookupStore (@PathVariable String id){
        LOG.info("Incoming lookup request from StoreController", keyValue("requestId", id));
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getStore(id));
    }

    @Operation(summary = "Searches for all the Stores on the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stores found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Store.class)) }),
            @ApiResponse(responseCode = "404", description = "Stores not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Store>> searchStores(){
        LOG.info("Incoming search request from StoreController");
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getStores());
    }

    @Operation(summary = "Updates Store data after finding it by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Store.class)) }),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PutMapping(value = "/update/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> updateStore (@PathVariable String id, @RequestBody Store store){
        LOG.info("Incoming update request from StoreController", keyValue("requestBody", store));
        return ResponseEntity.status(HttpStatus.OK).body(storeService.updateStore(id, store));
    }

    @Operation(summary = "Deletes a Store after finding it by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Store deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Store.class)) }),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @DeleteMapping(value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStore(@PathVariable String id){
        LOG.info("Incoming delete request from StoreController", keyValue("requestId", id));
        storeService.removeStore(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Store successfully removed");
    }

    @ExceptionHandler(PeopleListException.class)
    public ResponseEntity<String> handleException(final PeopleListException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
