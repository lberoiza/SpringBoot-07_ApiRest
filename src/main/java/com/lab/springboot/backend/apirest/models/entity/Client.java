package com.lab.springboot.backend.apirest.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client implements EntityTable {

  @Serial
  private static final long serialVersionUID = -7205843364359501687L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_client_id")
  @SequenceGenerator(name = "seq_client_id", initialValue = 50, allocationSize = 1)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Past
  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  private String image;

//  @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//  @JsonManagedReference
//  private List<Invoice> invoices;


  public Client() {
//    this.invoices = new ArrayList<>();
  }

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public boolean hasValidId() {
    return getId() != null && getId() > 0;
  }

  public boolean hasImage() {
    return this.image != null && !this.image.isEmpty();
  }


//  public void addInvoice(Invoice invoice) {
//    this.invoices.add(invoice);
//  }


  @Override
  public String toString() {
    return "Client{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", email='" + email + '\'' +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", image='" + image + '\'' +
//        ", invoices=" + invoices +
        '}';
  }


}
