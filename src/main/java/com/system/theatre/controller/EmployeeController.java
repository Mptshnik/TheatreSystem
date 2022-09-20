package com.system.theatre.controller;

import com.system.theatre.model.*;
import com.system.theatre.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.google.common.collect.Lists;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController
{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VoiceTypeRepository voiceTypeRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/all")
    public String getEmployees(Model model)
    {
        Iterable<Employee> employees = employeeRepository.findAll();

        model.addAttribute("employees", employees);
        model.addAttribute("header", getHtmlHeaderPath());

        return "/employee/all";
    }

    private void loadData(Model model)
    {
        model.addAttribute("voiceTypes", voiceTypeRepository.findAll());
        model.addAttribute("genders", genderRepository.findAll());
        model.addAttribute("posts", Lists.newArrayList(postRepository.findAll()));
        model.addAttribute("header", getHtmlHeaderPath());
    }

    @GetMapping("/add")
    public String addEmployee(@ModelAttribute("employee")Employee employee,
                              Model model,
                              @ModelAttribute("user") User user)
    {
        loadData(model);

        return "/employee/add";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult employeeResult,
                                 @ModelAttribute("user") @Valid User user, BindingResult userResult,
                                 @RequestParam String role,
                                 @RequestParam(value = "createAccount", required = false) String checkboxValue,
                                 Model model)
    {
        loadData(model);

        if(employeeResult.hasErrors())
        {
            return "/employee/add";
        }

        if(checkboxValue != null)
        {
            User userFromDB = userRepository.findByUsername(user.getUsername());

            if(userResult.hasErrors())
            {
                return "/employee/add";
            }

            if(userFromDB != null)
            {
                model.addAttribute("userExists", true);

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

        loadData(model);

        model.addAttribute("employee", employee);
        if(employee.getUser() != null)
        {
            model.addAttribute("user", employee.getUser());
            if(employee.getUser().getRoles().size() > 0)
                model.addAttribute("user_role", Role.valueOf(employee.getUser().getRoles().toArray()[0].toString()));
            else
                model.addAttribute("user_role", Role.ADMIN);
        }
        else
        {
            model.addAttribute("user", new User());
        }

        return "/employee/edit";
    }



    @PostMapping("/update/{id}")
    public String updateEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult employeeResult,
                                 @ModelAttribute("user") @Valid User user, BindingResult userResult,
                                 @RequestParam String role,
                                 @RequestParam(value = "createAccount", required = false) String checkboxValue,
                                 @PathVariable("id") long id, Model model)
    {
        model.addAttribute("header", getHtmlHeaderPath());
        Employee employeeFromDB = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        if(employeeResult.hasErrors())
        {
            loadData(model);
            return "/employee/edit";
        }

        if(checkboxValue != null)
        {
            if(userResult.hasErrors())
            {
                loadData(model);
                return "/employee/edit";
            }

            user.setActive(true);
            user.setRoles(Collections.singleton(Role.valueOf(role)));

            if(employeeFromDB.getUser() == null)
            {
                employee.setUser(user);
            }
            else
            {
                user.setId(employeeFromDB.getUser().getId());
                employee.setUser(user);
            }
        }
        else
        {
            if(employeeFromDB.getUser() != null)
            {
                user.setId(employeeFromDB.getUser().getId());
                user.setRoles(employeeFromDB.getUser().getRoles());
                employee.setUser(user);
            }
        }

        employee.setId(id);
        employeeRepository.save(employee);

        return "redirect:/employee/all";
    }

    @GetMapping("/search")
    public String getFilter(Model model)
    {
        insertHeader(model);

        return "/employee/search";
    }

    @PostMapping("/search-accurate")
    public String searchAdvanced(@RequestParam String value, Model model)
    {
        insertHeader(model);

        List<Employee> result = employeeRepository.findByLastnameOrFirstnameOrMiddlename(value, value, value);
        if(result == null)
        {
            model.addAttribute("notFound", true);
            return "/employee/search";
        }

        model.addAttribute("result", result);
        model.addAttribute("notFound", result.size() == 0);
        return "/employee/search";
    }

    @PostMapping("/search")
    public String simpleSearch(@RequestParam String value, Model model)
    {
        insertHeader(model);

        List<Employee> result = employeeRepository.findByLastnameOrFirstnameOrMiddlenameContains(value, value, value);
        model.addAttribute("result", result);
        model.addAttribute("notFound", result.size() == 0);
        return "/employee/search";
    }

}
