package com.system.theatre.controller;

import com.system.theatre.model.Author;
import com.system.theatre.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/author")
public class AuthorController extends BaseController
{
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/all")
    public String allAuthors(Model model)
    {
        insertHeader(model);

        model.addAttribute("authors", authorRepository.findAll());

        return "/author/all";
    }

    @GetMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author, Model model)
    {
        insertHeader(model);

        return "/author/add";
    }

    @GetMapping("/edit/{id}")
    public String editAuthor(@PathVariable("id") long id, Model model)
    {
        insertHeader(model);

        Author author = authorRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));

        model.addAttribute(author);

        return "/author/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id)
    {
        Author author = authorRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));

        authorRepository.delete(author);



        return "redirect:/author/all";
    }

    @PostMapping("/create")
    public String createAuthor(@ModelAttribute("author") @Valid Author author,
                               BindingResult bindingResult,
                               Model model)
    {
        insertHeader(model);

        if(bindingResult.hasErrors())
        {
            return "/author/add";
        }

        authorRepository.save(author);

        return "redirect:/author/all";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable("id") long id,
                               @ModelAttribute("author") @Valid Author author,
                               BindingResult bindingResult,
                               Model model)
    {
        insertHeader(model);

        if(bindingResult.hasErrors())
        {
            return "/author/edit";
        }

        author.setId(id);
        authorRepository.save(author);

        return "redirect:/author/all";
    }
}
