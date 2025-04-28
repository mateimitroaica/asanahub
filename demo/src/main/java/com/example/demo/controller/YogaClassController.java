package com.example.demo.controller;

import com.example.demo.domain.YogaClass;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.mappers.YogaClassMapper;
import com.example.demo.service.YogaClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        YogaClass yogaClass = yogaClassService.findYogaClassById(id);
        YogaClassDTO yogaClassDTO = YogaClassMapper.toDTO(yogaClass);
        model.addAttribute("yogaClassDTO", yogaClassDTO);
        model.addAttribute("clazz", yogaClass);
        return "edit-class";
    }

    @PostMapping
    public String createClass(@ModelAttribute YogaClassDTO yogaClassDTO) {
        yogaClassService.saveYogaClass(yogaClassDTO);
        return "redirect:/yoga-classes";
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
