package com.system.theatre.controller;

import com.system.theatre.model.Employee;
import com.system.theatre.model.Membership;
import com.system.theatre.repo.AuthorRepository;
import com.system.theatre.repo.GenreRepository;
import com.system.theatre.repo.MembershipRepository;
import com.system.theatre.repo.PerformanceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/membership")
public class MembershipController extends BaseController
{
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private PerformanceTypeRepository performanceTypeRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    private void loadData(Model model)
    {
        model.addAttribute("performanceTypes", performanceTypeRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
    }

    @GetMapping("/all")
    public String allMemberships(Model model)
    {
        insertHeader(model);

        model.addAttribute("memberships", membershipRepository.findAll());

        return "/membership/all";
    }

    @GetMapping("/add")
    public String addMembership(@ModelAttribute("membership")Membership membership, Model model)
    {
        insertHeader(model);

        loadData(model);

        return "/membership/add";
    }

    @PostMapping("/create")
    public String createMembership(@ModelAttribute("membership") @Valid Membership membership,
                                   BindingResult bindingResult,
                                   Model model)
    {
        insertHeader(model);


        if(bindingResult.hasErrors())
        {
            loadData(model);

            return "/membership/add";
        }

        membershipRepository.save(membership);

        return "redirect:/membership/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteMembership(@PathVariable("id") long id)
    {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid membership Id:" + id));

        membershipRepository.delete(membership);

        return "redirect:/membership/all";
    }

    @GetMapping("/edit/{id}")
    public String editMembership(@PathVariable("id") long id, Model model)
    {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid membership Id:" + id));

        insertHeader(model);

        loadData(model);

        model.addAttribute("membership", membership);

        return "/membership/edit";
    }

    @PostMapping("/update/{id}")
    public String updateMembership(@ModelAttribute("membership") @Valid Membership membership,
                                   BindingResult bindingResult,
                                   @PathVariable("id") long id, Model model)
    {
        insertHeader(model);


        if(bindingResult.hasErrors())
        {
            loadData(model);
            return "/membership/edit";
        }

        membership.setId(id);
        membershipRepository.save(membership);

        return "redirect:/membership/all";
    }
}
