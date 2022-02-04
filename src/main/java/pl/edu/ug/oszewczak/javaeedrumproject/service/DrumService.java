package pl.edu.ug.oszewczak.javaeedrumproject.service;

import static pl.edu.ug.oszewczak.javaeedrumproject.domain.DrumType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.ug.oszewczak.javaeedrumproject.domain.*;
import pl.edu.ug.oszewczak.javaeedrumproject.exception.DrumNotFoundException;
import pl.edu.ug.oszewczak.javaeedrumproject.repository.DrumRepository;
import pl.edu.ug.oszewczak.javaeedrumproject.repository.ManufacturerRepository;
import pl.edu.ug.oszewczak.javaeedrumproject.repository.SalesmanRepository;
import pl.edu.ug.oszewczak.javaeedrumproject.repository.ShellRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class DrumService {

    private final DrumRepository drumRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final SalesmanRepository salesmanRepository;
    private final ShellRepository shellRepository;

    @Autowired
    public DrumService(DrumRepository drumRepository, ManufacturerRepository manufacturerRepository, SalesmanRepository salesmanRepository, ShellRepository shellRepository) {
        this.drumRepository = drumRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.salesmanRepository = salesmanRepository;
        this.shellRepository = shellRepository;
    }

    // Drum operations
    public Drum addDrum(Drum drum) {
        if (drum.getShell() != null) {
            shellRepository.save(drum.getShell());
        }
        if (!drum.getManufacturer().getDrums().contains(drum)) {
            drum.getManufacturer().addDrum(drum);
        }
        if (drum.getManufacturer() != null) {
            manufacturerRepository.save(drum.getManufacturer());
        }
        return drumRepository.save(drum);
    }

    public List<Drum> getAllDrums() {
        List<Drum> drums = new ArrayList<>();
        drumRepository.findAll().forEach(drums::add);
        return drums;
    }

    public Drum getOneDrum(Long id) {
        return drumRepository.findById(id).orElseThrow(() -> new DrumNotFoundException(id));
    }

    public Drum updateDrum(Long id, Drum drum) {
        System.out.println("Update Drum " + drum);
        Drum drumToUpdate = drumRepository.findById(id).orElseThrow(() -> new DrumNotFoundException(id));
        drumToUpdate.setColour(drum.getColour());
        drumToUpdate.setName(drum.getName());
        drumToUpdate.setType(drum.getType());
        drumToUpdate.setShell(drum.getShell());
        drumToUpdate.setManufacturer(drum.getManufacturer());
        return drumRepository.save(drumToUpdate);
    }

    public void deleteDrum(Long id) {
        Drum drumToDelete = drumRepository.findById(id).orElseThrow(() -> new DrumNotFoundException(id));
        for (Salesman salesman : salesmanRepository.findAll()) {
            salesman.getDrums().remove(drumToDelete);
        }
        drumRepository.deleteById(id);
    }

    // Manufacturer operations
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturerRepository.findAll().forEach(manufacturers::add);
        return manufacturers;
    }

    // Salesman Operations
    public Salesman addSalesman(Salesman salesman) {
        return salesmanRepository.save(salesman);
    }

    // Shell Operations
    public Shell addShell(Shell shell) {
        return shellRepository.save(shell);
    }

    public List<Shell> getShellsWithoutOwner() {
        Iterable<Shell> iterableShells = shellRepository.findAll();
        List<Shell> shellsNoOwner = new ArrayList<>();
        iterableShells.forEach(shellsNoOwner::add);

        List<Drum> allDrums = getAllDrums();
        for (Drum drum : allDrums) {
            shellsNoOwner.remove(drum.getShell());
        }
        return shellsNoOwner;
    }

    // Initialize DB
    public void initializeDatabase() {
        // Initialize shells
        Shell shell1 = new Shell(14f, 5.5f, "Poplar/Mahogany", "Black", "Chrome");
        Shell shell2 = new Shell(18f, 15f, "Birch", "Black", "Chrome");
        Shell shell3 = new Shell(24f, 18f, "Poplar", "Red", "Chrome");
        Shell shell4 = new Shell(18f, 16f, "Birch", "Black", "Chrome");
        Shell shell5 = new Shell(14.5f, 8f, "Mahogany", "Yellow", "Chrome");
        Shell shell6 = new Shell(15f, 10f, "Oak", "Blue", "Aluminum");
        Shell shell7 = new Shell(14f, 10.5f, "Birch", "Blue", "Aluminum");
        Shell shell8 = new Shell(13f, 7f, "Mahogany", "Purple", "Chrome");
        Shell shell9 = new Shell(22f, 17.5f, "Birch", "Gray", "Chrome");


        // Initialize Manufacturers
        Manufacturer yamaha = new Manufacturer("Yamaha", "Japan");
        Manufacturer pearl = new Manufacturer("Pearl Drums", "Japan");
        Manufacturer tama = new Manufacturer("Tama", "Japan");
        Manufacturer ludwig = new Manufacturer("Ludwig-Musser", "USA");


        // Initialize salesman
        Salesman salesman1 = new Salesman("John", "Boughtman");
        Salesman salesman2 = new Salesman("Ginger", "Baker");
        Salesman salesman3 = new Salesman("Olaf", "Szewczak");


        // Initialize drums
        Drum drum1 = new Drum("Export", SNARE, "Jet Black");
        Drum drum2 = new Drum("Stage Custom", BASS, "Raven Black");
        Drum drum3 = new Drum("Behemoth", BASS, "Fire Red");
        Drum drum4 = new Drum("Stage Custom", FLOOR_TOM, "Raven Black");
        Drum drum5 = new Drum("Sunlight", TOM, "Sun Yellow");
        Drum drum6 = new Drum("Starborn", SNARE, "Cosmic Blue");

        // Connect Entities
        drum1.setShell(shell1);
        drum1.setManufacturer(yamaha);

        drum2.setShell(shell2);
        drum2.setManufacturer(pearl);

        drum3.setShell(shell3);
        drum3.setManufacturer(tama);

        drum4.setShell(shell4);
        drum4.setManufacturer(yamaha);

        drum5.setShell(shell5);
        drum5.setManufacturer(ludwig);

        drum6.setShell(shell6);
        drum6.setManufacturer(pearl);

        salesman1.setDrums(Arrays.asList(drum1, drum2, drum5));
        salesman2.setDrums(Arrays.asList(drum2, drum3));
        salesman3.setDrums(Arrays.asList(drum1, drum2, drum5, drum6, drum3));


        // Add entities
        addDrum(drum1);
        addDrum(drum2);
        addDrum(drum3);
        addDrum(drum4);
        addDrum(drum5);
        addDrum(drum6);

        addShell(shell7);
        addShell(shell8);
        addShell(shell9);

        addSalesman(salesman1);
        addSalesman(salesman2);
        addSalesman(salesman3);
    }
}
