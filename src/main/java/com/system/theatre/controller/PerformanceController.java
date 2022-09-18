package com.system.theatre.controller;

import com.system.theatre.model.Employee;
import com.system.theatre.model.Performance;
import com.system.theatre.repo.AuthorRepository;
import com.system.theatre.repo.GenreRepository;
import com.system.theatre.repo.PerformanceRepository;
import com.system.theatre.repo.PerformanceTypeRepository;
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

    @GetMapping("/all")
    public String allPerformances(Model model)
    {
        insertHeader(model);

        model.addAttribute("performances", performanceRepository.findAll());
        model.addAttribute("performancesEmpty", performanceRepository.count() == 0);

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
