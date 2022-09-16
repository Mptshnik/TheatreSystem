package com.system.theatre.controller;

import com.system.theatre.model.Employee;
import com.system.theatre.model.Role;
import com.system.theatre.model.User;
import com.system.theatre.model.VoiceType;
import com.system.theatre.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController
{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VoiceTypeRepository voiceTypeRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/all")
    public String getEmployees(Model model)
    {
        Iterable<Employee> employees = employeeRepository.findAll();

        model.addAttribute("employees", employees);
        model.addAttribute("header", getHtmlHeaderPath());

        return "/employee/all";
    }

    @GetMapping("/add")
    public String addEmployee(@ModelAttribute("employee")Employee employee,
                              @ModelAttribute("user") User user, Model model)
    {
        model.addAttribute("voiceTypes", voiceTypeRepository.findAll());
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("header", getHtmlHeaderPath());

        return "/employee/add";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult employeeResult,
                                 @ModelAttribute("user") @Valid User user, BindingResult userResult,
                                 @RequestParam String role,
                                 @RequestParam(value = "createAccount", required = false) String checkboxValue)
    {
        if(employeeResult.hasErrors())
        {
            return "/employee/add";
        }

        if(checkboxValue != null)
        {
            if(userResult.hasErrors())
            {
                return "/employee/add";
            }
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.valueOf(role)));
            employee.setUser(user);
        }

        employeeRepository.save(employee);

        return "redirect:/employee/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        employeeRepository.delete(employee);

        return "redirect:/employee/all";
    }

    @GetMapping("/details/{id}")
    public String detailsEmployee(@PathVariable("id") long id, Model model)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        model.addAttribute("header", getHtmlHeaderPath());
        model.addAttribute("employee", employee);

        return "/employee/details";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") long id, Model model)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        model.addAttribute("voiceTypes", voiceTypeRepository.findAll());
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("header", getHtmlHeaderPath());
        model.addAttribute("employee", employee);
        if(employee.getUser() == null)
            model.addAttribute("user", new User());
        else
            model.addAttribute("user", employee.getUser());

        return "/employee/edit";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult employeeResult,
                                 @ModelAttribute("user") @Valid User user, BindingResult userResult,
                                 @RequestParam String role,
                                 @RequestParam(value = "createAccount", required = false) String checkboxValue,
                                 @PathVariable("id") long id)
    {
        if(employeeResult.hasErrors())
        {
            return "/employee/update";
        }

        if(checkboxValue != null)
        {
            if(userResult.hasErrors())
            {
                return "/employee/update";
            }
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.valueOf(role)));
            employee.setUser(user);
        }

        employee.setId(id);
        employeeRepository.save(employee);

        return "redirect:/employee/all";
    }

    private String getHtmlHeaderPath()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toString();

        String path = "";

        if(role.contains("ADMIN"))
        {
            path = "/headers/admin-header";
        }
        else if(role.contains("DIRECTOR"))
        {
            path = "/headers/director-header";
        }
        else if(role.contains("CASHIER"))
        {
            path = "/headers/cashier-header";
        }

        return path;
    }
}