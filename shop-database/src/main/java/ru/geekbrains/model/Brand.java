package ru.geekbrains.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @OneToMany(
      mappedBy = "brand",
      cascade = CascadeType.ALL)
  private List<Product> products;

}
