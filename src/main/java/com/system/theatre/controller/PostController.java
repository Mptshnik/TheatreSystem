package com.system.theatre.controller;

import com.system.theatre.model.Post;
import com.system.theatre.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController
{
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/all")
    public String getAllPosts(Model model)
    {
        insertHeader(model);

        model.addAttribute("posts", postRepository.findAll());

        return "/post/all";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") long id, Model model)
    {
        insertHeader(model);
        Post post = postRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Invalid post Id:" + id));

        model.addAttribute("post", post);

        return "/post/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id,
                             @ModelAttribute("post") @Valid Post post,
                             BindingResult postResult, Model model)
    {
        insertHeader(model);

        if(postResult.hasErrors())
        {
            return "/post/edit";
        }

        post.setId(id);
        postRepository.save(post);

        return "redirect:/post/all";
    }

    @GetMapping("/add")
    public String addPost(@ModelAttribute("post") Post post, Model model)
    {
        insertHeader(model);

        return "/post/add";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("post") @Valid Post post,
                             BindingResult postResult, Model model)
    {
        insertHeader(model);

        if(postResult.hasErrors())
        {
            return "/post/add";
        }

        postRepository.save(post);

        return "redirect:/post/all";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") long id)
    {
        Post post = postRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Invalid post Id:" + id));

        postRepository.delete(post);

        return "redirect:/post/all";
    }
}
