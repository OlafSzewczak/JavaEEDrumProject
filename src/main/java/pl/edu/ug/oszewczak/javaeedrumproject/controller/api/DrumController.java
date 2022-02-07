package pl.edu.ug.oszewczak.javaeedrumproject.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.edu.ug.oszewczak.javaeedrumproject.domain.Drum;
import pl.edu.ug.oszewczak.javaeedrumproject.domain.Manufacturer;
import pl.edu.ug.oszewczak.javaeedrumproject.service.DrumService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DrumController {

    private final DrumService drumService;

    @Autowired
    public DrumController(DrumService drumService) {
        this.drumService = drumService;
    }

    @PostMapping("/drums")
    public Drum addDrum(@Valid @RequestBody(required = false) Drum drum) {
        return drumService.addDrum(drum);
    }

    @GetMapping("/manufacturers")
    public List<Manufacturer> allManufacturers() {
        return drumService.getAllManufacturers();
    }

    @GetMapping("/drums")
    public List<Drum> allDrums() {
        return drumService.getAllDrums();
    }

    @GetMapping("/drums/{id}")
    public Drum oneDrum(@PathVariable Long id) {
        return drumService.getOneDrum(id);
    }

    @PutMapping("/drums/{id}")
    public Drum updateDrum(@PathVariable Long id, @RequestBody Drum drum) {
        return drumService.updateDrum(id, drum);
    }

    @DeleteMapping("/drums/{id}")
    public ResponseEntity<?> deleteDrum(@PathVariable Long id) {
        drumService.deleteDrum(id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
