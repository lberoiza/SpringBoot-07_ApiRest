package com.lab.springboot.backend.apirest.models.repository;


import com.lab.springboot.backend.apirest.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
  boolean existsByEmail(String Email);

  Client findByEmail(String Email);
}
