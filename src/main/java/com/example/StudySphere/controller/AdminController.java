package com.example.StudySphere.controller;

import com.example.StudySphere.entity.User;
import com.example.StudySphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String dashboard(@RequestParam("uid") String uid, Model model){
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "admin/dashboard";
    }

    @GetMapping("/manageAccounts")
    public String manageAccounts(@RequestParam("uid") String uid, Model model){
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "admin/manageAccounts";
    }

    @GetMapping("/editAccount")
    public String editAccount(@RequestParam("uid") String uid, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        User admin  = userService.findById(username);
        model.addAttribute("admin",admin);
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "admin/editAccount";
    }

    @GetMapping("/createAccounts")
    public String createAccounts( Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User admin  = userService.findById(username);
        model.addAttribute("admin",admin);

        User newuser = new User();
        model.addAttribute("user",newuser);
        return "admin/createAccount";
    }

    @PostMapping("/createProfile")
    public String create(@ModelAttribute User user) {
        System.out.println("SAVING"+ user.getUsername());

        userService.save(user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/profile?uid="+logged.getUid();

    }

}
