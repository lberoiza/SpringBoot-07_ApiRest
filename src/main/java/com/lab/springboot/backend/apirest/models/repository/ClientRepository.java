package com.lab.springboot.backend.apirest.models.repository;


import com.lab.springboot.backend.apirest.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
  boolean existsByEmail(String Email);
}
