package com.lab.springboot.backend.apirest.controllers;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  public List<Client> getClients() {
    return this.clientService.findAll();
  }

  @GetMapping("/{id}")
  public Client getClientDetails(@PathVariable Long id) {
    return this.clientService.findById(id).orElse(null);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Client save(@RequestBody Client client) {
    return this.clientService.saveOrUpdate(client);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Client update(@RequestBody Client client, @PathVariable Long id) {
    Optional<Client> optionalClient = this.clientService.findById(id);
    if (optionalClient.isEmpty()) {
      return null;
    }

    Client currentClient = optionalClient.get();
    currentClient.setName(client.getName());
    currentClient.setSurname(client.getSurname());
    currentClient.setImage(client.getImage());
    currentClient.setEmail(client.getEmail());
    return this.clientService.saveOrUpdate(currentClient);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteClient(@PathVariable Long id) {
    this.clientService.deleteById(id);
  }


}
