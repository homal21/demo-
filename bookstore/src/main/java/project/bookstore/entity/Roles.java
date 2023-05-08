package project.bookstore.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

}
