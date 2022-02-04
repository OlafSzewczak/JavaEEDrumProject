package pl.edu.ug.oszewczak.javaeedrumproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "salesmen")
public class Salesman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Drum> drums;

    public Salesman(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
