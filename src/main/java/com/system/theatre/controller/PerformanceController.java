package com.system.theatre.controller;

import com.google.common.collect.Lists;
import com.system.theatre.model.Employee;
import com.system.theatre.model.Performance;
import com.system.theatre.model.SceneRole;
import com.system.theatre.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/performance")
public class PerformanceController extends BaseController
{
    @Autowired
    private SceneRoleRepository sceneRoleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PerformanceRepository performanceRepository;
    @Autowired
    private PerformanceTypeRepository performanceTypeRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    private void loadData(Model model)
    {
        model.addAttribute("performanceTypes", performanceTypeRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
    }

    @GetMapping("/{id}/all-actors")
    public String allActors(@PathVariable("id") long id, Model model)
    {
        insertHeader(model);

        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid performance Id:" + id));

        model.addAttribute("sceneRoles", performance.getSceneRoles());

        return "/scenerole/all";
    }

    @GetMapping("/delete-actor/{id}")
    public String deleteActor(@PathVariable("id") long id, Model model)
    {
        insertHeader(model);

        SceneRole sceneRole = sceneRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sceneRole Id:" + id));

        sceneRoleRepository.delete(sceneRole);

        return "/scenerole/all";
    }

    @GetMapping("/all")
    public String allPerformances(Model model)
    {
        insertHeader(model);

        model.addAttribute("performances", performanceRepository.findAll());
        model.addAttribute("performancesEmpty", performanceRepository.count() == 0);
        model.addAttribute("sceneRolesEmpty", sceneRoleRepository.count() == 0);

        return "/performance/all";
    }

    @GetMapping("/delete/{id}")
    public String deletePerformance(@PathVariable("id") long id)
    {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid performance Id:" + id));

        performanceRepository.delete(performance);

        return "redirect:/performance/all";
    }

    @GetMapping("/edit/{id}")
    public String editPerformance(@PathVariable("id") long id, Model model)
    {
        insertHeader(model);
        loadData(model);

        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid performance Id:" + id));

        model.addAttribute("performance", performance);

        return "/performance/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePerformance(@PathVariable("id") long id,
                                    @ModelAttribute("performance") @Valid Performance performance,
                                    BindingResult bindingResult,
                                    Model model)
    {
        insertHeader(model);

        if(bindingResult.hasErrors())
        {
            loadData(model);
            return "/performance/edit";
        }

        performance.setId(id);
        performanceRepository.save(performance);

        return "redirect:/performance/all";
    }

    @GetMapping("/add")
    public String addPerformance(@ModelAttribute("performance") Performance performance, Model model)
    {
        insertHeader(model);

        loadData(model);

        return "/performance/add";
    }

    @PostMapping("/create")
    public String createPerformance(@ModelAttribute("performance") @Valid Performance performance,
                                    BindingResult bindingResult,
                                    Model model)
    {
        insertHeader(model);

        if(bindingResult.hasErrors())
        {
            loadData(model);
            return "/performance/add";
        }
        performanceRepository.save(performance);

        return "redirect:/performance/all";
    }

}
