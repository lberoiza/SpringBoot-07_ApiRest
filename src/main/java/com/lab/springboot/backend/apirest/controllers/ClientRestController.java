package com.lab.springboot.backend.apirest.controllers;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.service.ClientService;
import com.lab.springboot.backend.apirest.utils.ApiResponse;
import com.lab.springboot.backend.apirest.utils.ApiServiceException;
import com.lab.springboot.backend.apirest.utils.ResponseEntityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
  public ResponseEntity<ApiResponse<Client>> save(@RequestBody Client client) {
    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<Client> response = new ApiResponse<>();
      response.addResponseData(this.clientService.saveOrUpdate(client));
      return response;
    });
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Client>> update(@RequestBody Client client, @PathVariable Long id) {

    return ResponseEntityHandler.handleApiResponse(() -> {
      ApiResponse<Client> response = new ApiResponse<>();

      Client currentClient = this.clientService.findByIdOrThrowsException(id);

      if(this.clientService.checkIfEmailExistByAnotherUser(currentClient, client.getEmail())) {
        String error = String.format("The Email '%s' already exists by another user", client.getEmail());
        throw new ApiServiceException(error, HttpStatus.ALREADY_REPORTED);
      }
      currentClient.setName(client.getName());
      currentClient.setSurname(client.getSurname());
      currentClient.setImage(client.getImage());
      currentClient.setEmail(client.getEmail());

      response.addResponseData(this.clientService.saveOrUpdate(currentClient));
      return response;
    });
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<ApiResponse<Client>> deleteClient(@PathVariable Long id) {
    return ResponseEntityHandler.handleApiResponse(() -> {
      this.clientService.deleteById(id);
      return null;
    });
  }

}
