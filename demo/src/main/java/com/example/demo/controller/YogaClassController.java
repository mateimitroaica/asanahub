package com.example.demo.controller;

import com.example.demo.domain.SubscriptionType;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaClassType;
import com.example.demo.domain.YogaUser;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.mappers.YogaClassMapper;
import com.example.demo.repository.YogaUserRepository;
import com.example.demo.service.YogaClassService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/yoga-classes")
public class YogaClassController {
    private final YogaClassService yogaClassService;
    private final YogaUserRepository yogaUserRepository;

    public YogaClassController(YogaClassService yogaClassService, YogaUserRepository yogaUserRepository) {
        this.yogaClassService = yogaClassService;
        this.yogaUserRepository = yogaUserRepository;
    }
@GetMapping
public String listPaginatedClasses(@RequestParam(defaultValue = "0") int page, Model model) {
    Page<YogaClass> classPage = yogaClassService.getPaginatedClasses(page, 2);
    model.addAttribute("classes", classPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", classPage.getTotalPages());
    model.addAttribute("baseUrl", "/yoga-classes");
    return "classes-list";
}

    @GetMapping("/{id}")
    public String getClass(@PathVariable Long id, Model model, Principal principal) {
        YogaClass yogaClass = yogaClassService.findYogaClassById(id);
        model.addAttribute("yogaClass", yogaClass);

        boolean enrolled = false;
        boolean hasActiveProSub = false;

        if (principal != null) {
            String email = principal.getName();
            Optional<YogaUser> userOpt = yogaUserRepository.findByEmail(email);

            if (userOpt.isPresent()) {
                YogaUser user = userOpt.get();
                enrolled = user.getReservations().contains(yogaClass);

                hasActiveProSub = user.getSubscriptions().stream()
                        .anyMatch(sub ->
                                sub.getStudio().getId().equals(yogaClass.getStudio().getId()) &&
                                        sub.getSubscriptionType() == SubscriptionType.PRO &&
                                        sub.getActive()
                        );
            }
        }

        model.addAttribute("enrolled", enrolled);
        model.addAttribute("hasActiveProSub", hasActiveProSub);
        return "class-detail";
    }

    @GetMapping("/search")
    public String searchClasses(@RequestParam("query") String query,
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {
        Page<YogaClass> classPage = yogaClassService.searchPaginated(query, page, 2);
        model.addAttribute("classes", classPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", classPage.getTotalPages());
        model.addAttribute("baseUrl", "/yoga-classes/search?query=" + query);
        model.addAttribute("searchPerformed", true);
        return "classes-list";
    }

    @GetMapping("/filter")
    public String filterClasses(@RequestParam(required = false) YogaClassType style,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                @RequestParam(defaultValue = "0") int page,
                                Model model) {
        Page<YogaClass> classPage = yogaClassService.filterPaginated(style, date, page, 2);
        model.addAttribute("classes", classPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", classPage.getTotalPages());

        String baseUrl = "/yoga-classes/filter?";
        if (style != null) baseUrl += "style=" + style + "&";
        if (date != null) baseUrl += "date=" + date + "&";
        baseUrl = baseUrl.replaceAll("&$", "");

        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("searchPerformed", true);
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
    public String createClass(@Valid @ModelAttribute YogaClassDTO yogaClassDTO, Model model) {
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
