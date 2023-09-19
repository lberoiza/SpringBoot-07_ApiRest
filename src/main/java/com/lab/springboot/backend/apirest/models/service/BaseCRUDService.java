package com.lab.springboot.backend.apirest.models.service;

import com.lab.springboot.backend.apirest.models.entity.EntityTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseCRUDService<E extends EntityTable, R extends JpaRepository<E, Long>> {

  protected R repository;

  @Autowired
  private void setRepository(R repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public Optional<E> findById(Long entityId) {
    return repository.findById(entityId);
  }

  @Transactional(readOnly = true)
  public List<E> findAll() {
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public Page<E> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional
  public E saveOrUpdate(E entity) {
    return repository.save(entity);
  }

  @Transactional
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  @Transactional
  public void delete(E entity) {
    repository.delete(entity);
  }

  @Transactional
  public void deleteAll() {
    repository.deleteAll();
  }

  public Long count() {
    return repository.count();
  }
}
