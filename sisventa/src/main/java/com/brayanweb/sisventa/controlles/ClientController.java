package com.brayanweb.sisventa.controlles;

import com.brayanweb.sisventa.dtos.ClientRequest;
import com.brayanweb.sisventa.dtos.ClientResponse;
import com.brayanweb.sisventa.services.ClientService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getClients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "id") String sortBy) {

        Page<ClientResponse> clientsPage = clientService.getClients(page, size, sortBy);

        Map<String, Object> response = Map.of(
                "status", "success",
                "clients", clientsPage.getContent(),
                "page", page,
                "size", size,
                "totalPages", clientsPage.getTotalPages(),
                "totalElements", clientsPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getClient(@PathVariable("id") Long id) {
        ClientResponse client = clientService.getClient(id);
        return ResponseEntity.ok(createResponse("succes", "client", client));
    }
    
    @GetMapping("/{dniOrRuc}")
    public ResponseEntity<Map<String,Object>> getClientByDniOrRuc(@PathVariable("dniOrRuc") String dniOrRuc){
        ClientResponse client=clientService.getClientByDniOrRuc(dniOrRuc);
        return ResponseEntity.ok(createResponse("success","client",client));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ClientRequest clientRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        ClientResponse createdClient = clientService.create(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponse("success", "client", createdClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody ClientRequest clientRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMap(result));
        }

        ClientResponse updatedClient = clientService.update(id, clientRequest);
        return ResponseEntity.ok(createResponse("success", "client", updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        String messageDelete = clientService.delete(id);
        return ResponseEntity.ok(createResponse("success", "msg", messageDelete));
    }

    private Map<String, Object> createResponse(String status, String key, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put(key, data);
        return response;

    }

    private Map<String, Object> createErrorMap(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}
