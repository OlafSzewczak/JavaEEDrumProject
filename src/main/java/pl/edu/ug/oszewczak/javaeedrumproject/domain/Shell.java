package pl.edu.ug.oszewczak.javaeedrumproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shells")
public class Shell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Size must be at least 1!")
    private float size;
    @Min(value = 1, message = "Depth must be at least 1!")
    private float depth;
    @NotBlank(message = "Material is required!")
    @Size(min = 2, max = 30)
    private String material;
    @NotBlank(message = "Colour is required!")
    @Size(min = 2, max = 30)
    private String colour;
    @NotBlank(message = "Hardware colour is required!")
    @Size(min = 2, max = 30)
    private String hardwareColour;

    public Shell(float size, float depth, String material, String colour, String hardwareColour) {
        this.size = size;
        this.depth = depth;
        this.material = material;
        this.colour = colour;
        this.hardwareColour = hardwareColour;
    }

    @Override
    public String toString() {
        return size + "''x" + depth + "'' " + colour + " " + material + ", Hardware: " + hardwareColour;
    }
}