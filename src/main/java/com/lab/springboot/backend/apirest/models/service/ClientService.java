package com.lab.springboot.backend.apirest.models.service;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends BaseCRUDService<Client, ClientRepository>{

}
