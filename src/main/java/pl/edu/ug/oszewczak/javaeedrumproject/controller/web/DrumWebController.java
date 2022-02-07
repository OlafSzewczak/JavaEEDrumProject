package pl.edu.ug.oszewczak.javaeedrumproject.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.ug.oszewczak.javaeedrumproject.domain.Drum;
import pl.edu.ug.oszewczak.javaeedrumproject.service.DrumService;

import javax.validation.Valid;

@Controller
public class DrumWebController {

    private final DrumService drumService;

    @Autowired
    public DrumWebController(DrumService drumService) {
        this.drumService = drumService;
    }


    @GetMapping("/editDrum")
    public String editDrum(@RequestParam(required = false, defaultValue = "-1") Long id, Model model) {

        Drum drum;
        if (id == -1) {
            drum = new Drum();
        } else {
            drum = drumService.getOneDrum(id);
        }

        model.addAttribute("drum", drum);
        model.addAttribute("shells", drumService.getShellsWithoutOwner());
        model.addAttribute("manufacturers", drumService.getAllManufacturers());
        return "drum-add";
    }

    @PostMapping("/addDrum")
    public String addDrum(@Valid @ModelAttribute("drum") Drum drum, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("shells", drumService.getShellsWithoutOwner());
            model.addAttribute("manufacturers", drumService.getAllManufacturers());
            return "drum-add";
        } else {
            drumService.addOrUpdateDrum(drum);
            return "redirect:/";
        }
    }

    @GetMapping("/")
    public String drums(Model model) {
        model.addAttribute("drums", drumService.getAllDrums());
        return "drums-all";
    }

    @GetMapping("/deleteDrum")
    public String deleteDrum(@RequestParam Long id) {
        drumService.deleteDrum(id);
        return "redirect:/";
    }


}
