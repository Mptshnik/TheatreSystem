package com.system.theatre.controller;

import com.system.theatre.model.SceneRole;
import com.system.theatre.repo.EmployeeRepository;
import com.system.theatre.repo.PerformanceRepository;
import com.system.theatre.repo.SceneRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/scenerole")
public class SceneRoleController extends BaseController
{
    @Autowired
    private SceneRoleRepository sceneRoleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    @GetMapping("/all")
    public String allSceneRoles(@ModelAttribute("sceneRole") SceneRole sceneRole, Model model)
    {
        insertHeader(model);

        model.addAttribute("performances", performanceRepository.findAll());
        model.addAttribute("actors", employeeRepository.findByPostNameAndOntour("Актер", false));

        return "/scenerole/all";
    }

    @GetMapping("/add")
    public String addSceneRole(@ModelAttribute("sceneRole") SceneRole sceneRole, Model model)
    {
        insertHeader(model);
        model.addAttribute("sceneRoleExists", false);
        model.addAttribute("performances", performanceRepository.findAll());
        model.addAttribute("actors", employeeRepository.findByPostNameAndOntour("Актер", false));

        return "/scenerole/add";
    }

    @PostMapping("/create")
    public String createSceneRole(@ModelAttribute("sceneRole") @Valid SceneRole sceneRole,
                                  BindingResult bindingResult,
                                  Model model)
    {
        insertHeader(model);
        model.addAttribute("performances", performanceRepository.findAll());
        model.addAttribute("actors", employeeRepository.findByPostNameAndOntour("Актер", false));

        if(bindingResult.hasErrors())
        {
            return "/scenerole/add";
        }

        SceneRole dbSceneRole = sceneRoleRepository.findByName(sceneRole.getName());
        if(dbSceneRole != null)
        {
            model.addAttribute("sceneRoleExists", true);
            return "/scenerole/add";
        }

        model.addAttribute("success", true);
        model.addAttribute("successMessage", "Актер назначен на спектакль "
                + sceneRole.getPerformance().getName());

        sceneRoleRepository.save(sceneRole);

        return "/scenerole/add";
    }
}
