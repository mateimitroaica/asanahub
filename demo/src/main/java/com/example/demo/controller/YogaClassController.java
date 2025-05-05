package com.example.demo.controller;

import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaClassType;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.mappers.YogaClassMapper;
import com.example.demo.service.YogaClassService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/yoga-classes")
public class YogaClassController {
    private final YogaClassService yogaClassService;

    public YogaClassController(YogaClassService yogaClassService) {
        this.yogaClassService = yogaClassService;
    }

    @GetMapping
    public String showAllClasses(Model model) {
        List<YogaClass> classes = yogaClassService.findAllClasses();
        model.addAttribute("classes", classes);
        return "classes-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("yogaClassDTO", new YogaClassDTO());
        return "create-class";
    }

    @GetMapping("/filter")
    public String filterClasses(@RequestParam(required = false) YogaClassType style,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                Model model) {

        List<YogaClass> filtered = yogaClassService.filterClasses(style, date);
        model.addAttribute("classes", filtered);
        model.addAttribute("searchPerformed", true);
        return "classes-list";
    }


    @GetMapping("/search")
    public String searchClasses(@RequestParam("query") String query, Model model) {
        List<YogaClass> filteredClasses = yogaClassService.findByName(query);
        model.addAttribute("classes", filteredClasses);
        model.addAttribute("searchPerformed", true);
        return "classes-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        YogaClass yogaClass = yogaClassService.findYogaClassById(id);
        YogaClassDTO yogaClassDTO = YogaClassMapper.toDTO(yogaClass);
        model.addAttribute("yogaClassDTO", yogaClassDTO);
        model.addAttribute("clazz", yogaClass);
        return "edit-class";
    }

    @PostMapping
    public String createClass(@ModelAttribute YogaClassDTO yogaClassDTO, Model model) {
        try {
            yogaClassService.saveYogaClass(yogaClassDTO);
            return "redirect:/yoga-classes";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload studio image: " + e.getMessage());
            model.addAttribute("yogaClassDTO", yogaClassDTO);
            return "create-class";
        }

    }

    @PutMapping("/edit/{id}")
    public String updateYogaClass(@PathVariable Long id,
                                  @ModelAttribute YogaClassDTO yogaClassDTO) {
        yogaClassService.updateYogaClass(id, yogaClassDTO);
        return "redirect:/yoga-classes";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteYogaClass(@PathVariable Long id) {
        yogaClassService.deleteYogaClass(id);
        return ResponseEntity.noContent().build();
    }
}
