package ru.geekbrains.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @JsonIgnore
  @OneToMany(
      mappedBy = "category",
      cascade = CascadeType.ALL)
  private List<Product> products;

  public Category(Integer id, String title, List<Product> products) {
    this.id = id;
    this.title = title;
    this.products = products;
  }
}
