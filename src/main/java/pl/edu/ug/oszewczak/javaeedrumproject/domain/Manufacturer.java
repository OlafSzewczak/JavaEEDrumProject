package pl.edu.ug.oszewczak.javaeedrumproject.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required!")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters!")
    private String name;

    @NotBlank(message = "Country is required!")
    @Size(min = 2, max = 30, message = "Country must be between 2 and 30 characters!")
    private String country;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manufacturer")
    private List<Drum> drums = new ArrayList<>();

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addDrum(Drum drum) {
        if (drums.contains(drum)) {
            return;
        }

        drums.add(drum);
        drum.setManufacturer(this);
    }

    public void removeDrum(Drum drum) {
        if (!drums.contains(drum)) {
            return;
        }
        drums.remove(drum);
        drum.setManufacturer(null);
    }
}