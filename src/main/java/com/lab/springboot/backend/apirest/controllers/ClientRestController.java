package com.lab.springboot.backend.apirest.controllers;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public List<Client> getClients(){
    return clientService.findAll();
  }


}
