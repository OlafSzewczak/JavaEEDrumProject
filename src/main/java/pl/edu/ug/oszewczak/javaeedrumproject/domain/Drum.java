package pl.edu.ug.oszewczak.javaeedrumproject.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "drums")
public class Drum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required!")
    private String name;

    private DrumType type;

    @NotBlank(message = "Color is required!")
    @Size(min = 3, max = 20, message = "Color must be between 3 and 20 characters!")
    private String colour;

    @Valid
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Shell shell;

    @Valid
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;


    public Drum(String name, DrumType type, String colour) {
        this.name = name;
        this.type = type;
        this.colour = colour;
    }

    public void setManufacturer(Manufacturer manufacturer) {

        if (sameAsFormer(manufacturer)) {
            return;
        }

        Manufacturer oldManufacturer = this.manufacturer;
        this.manufacturer = manufacturer;

        if (oldManufacturer != null) {
            oldManufacturer.removeDrum(this);
        }

        if (manufacturer != null) {
            manufacturer.addDrum(this);
        }
    }

    public boolean sameAsFormer(Manufacturer newManufacturer) {
        return Objects.equals(manufacturer, newManufacturer);
    }
}
