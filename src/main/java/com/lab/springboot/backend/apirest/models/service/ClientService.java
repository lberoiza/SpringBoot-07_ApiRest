package com.lab.springboot.backend.apirest.models.service;

import com.lab.springboot.backend.apirest.models.entity.Client;
import com.lab.springboot.backend.apirest.models.repository.ClientRepository;
import com.lab.springboot.backend.apirest.utils.ApiResponse;
import com.lab.springboot.backend.apirest.utils.ApiServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService extends BaseCRUDService<Client, ClientRepository> {

  public boolean existsEmail(String email) {
    return this.repository.existsByEmail(email);
  }

  public Client findByIdOrThrowsException(Long id) throws ApiServiceException {
    Optional<Client> clientOptional = this.repository.findById(id);
    if (clientOptional.isEmpty()) {
      String error = String.format("Client with id (%d) not found", id);
      throw new ApiServiceException(error, HttpStatus.NOT_FOUND);
    }
    return clientOptional.get();
  }

  public boolean checkIfEmailExistByAnotherUser(Client client, String email) {
    Client clientWithEmail = this.repository.findByEmail(email);
    if (clientWithEmail == null) {
      return false;
    }
    return !Objects.equals(client.getId(), clientWithEmail.getId());
  }

  public void throwApiServiceExceptionIfEmailExistByAnotherUser(Client client, String email) {
    if (this.checkIfEmailExistByAnotherUser(client, email)) {
      String error = String.format("The Email '%s' already exists by another user", email);
      throw new ApiServiceException(error);
    }
  }

  public ApiResponse<Client> validateClient(BindingResult result) {
    ApiResponse<Client> response = new ApiResponse<>();

    if (result.hasErrors()) {
      for (FieldError errorObject : result.getFieldErrors()) {
        String error = String
            .format("%s: %s",
                errorObject.getField(),
                errorObject.getDefaultMessage());
        response.addError(error);
      }
      response.setHttpStatus(HttpStatus.PRECONDITION_REQUIRED);
    }
    return response;
  }

}
