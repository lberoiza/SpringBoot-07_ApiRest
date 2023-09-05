package com.lab.springboot.backend.apirest.controllers;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.service.ClientService;
import com.lab.springboot.backend.apirest.utils.ApiResponse;
import com.lab.springboot.backend.apirest.utils.ApiServiceException;
import com.lab.springboot.backend.apirest.utils.ResponseEntityHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

  private final ClientService clientService;

  @Autowired
  public ClientRestController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("/list")
  public ResponseEntity<ApiResponse<List<Client>>> getClients() {
    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<List<Client>> response = new ApiResponse<>();
      List<Client> clients = this.clientService.findAll();
      response.addResponseData(clients);
      if (clients.isEmpty()) {
        response.addWarning("There are no clients");
        response.setHttpStatus(HttpStatus.NOT_FOUND);
      }
      return response;
    });
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Client>> getClientDetails(@PathVariable Long id) {
    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<Client> response = new ApiResponse<>();
      response.addResponseData(this.clientService.findByIdOrThrowsException(id));
      return response;
    });
  }

  @PostMapping("")
  public ResponseEntity<ApiResponse<Client>> save(@Valid @RequestBody Client client,
                                                  BindingResult result) {
    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<Client> response = this.clientService.validateClient(result);
      if (response.hasErrors()) {
        return response;
      }
      this.clientService.throwApiServiceExceptionIfEmailExistByAnotherUser(client, client.getEmail());
      response.addResponseData(this.clientService.saveOrUpdate(client));
      response.setHttpStatus(HttpStatus.CREATED);
      return response;
    });
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Client>> update(@PathVariable Long id,
                                                    @Valid @RequestBody Client client,
                                                    BindingResult result) {

    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<Client> response = this.clientService.validateClient(result);
      if (response.hasErrors()) {
        return response;
      }

      Client currentClient = this.clientService.findByIdOrThrowsException(id);
      this.clientService.throwApiServiceExceptionIfEmailExistByAnotherUser(currentClient, client.getEmail());
      currentClient.setName(client.getName());
      currentClient.setSurname(client.getSurname());
      currentClient.setImage(client.getImage());
      currentClient.setEmail(client.getEmail());

      response.addResponseData(this.clientService.saveOrUpdate(currentClient));
      response.setHttpStatus(HttpStatus.CREATED);
      return response;
    });
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Client>> deleteClient(@PathVariable Long id) {
    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<Client> response = new ApiResponse<>();
      this.clientService.deleteById(id);
      response.setHttpStatus(HttpStatus.NO_CONTENT);
      return response;
    });
  }

}
