package com.lab.springboot.backend.apirest.models.service;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.repository.ClientRepository;
import com.lab.springboot.backend.apirest.utils.ApiServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService extends BaseCRUDService<Client, ClientRepository>{

  public boolean existsEmail(String email){
    return this.repository.existsByEmail(email);
  }

  public Client findByIdOrThrowsException(Long id) throws ApiServiceException{
    Optional<Client> clientOptional = this.repository.findById(id);
    if (clientOptional.isEmpty()) {
      String error = String.format("Client with id (%d) not found", id);
      throw new ApiServiceException(error, HttpStatus.NOT_FOUND);
    }
    return clientOptional.get();
  }

  public boolean checkIfEmailExistByAnotherUser(Client client, String email){
    Client clientWithEmail = this.repository.findByEmail(email);
    if(clientWithEmail == null ) {
      return false;
    }
    return !Objects.equals(client.getId(), clientWithEmail.getId());
  }

  public void throwApiServiceExceptionIfEmailExistByAnotherUser(Client client, String email){
    if(this.checkIfEmailExistByAnotherUser(client, email)){
      String error = String.format("The Email '%s' already exists by another user", email);
      throw new ApiServiceException(error);
    }
  }


}
