package com.example.StudySphere.controller;
import com.example.StudySphere.entity.User;
import com.example.StudySphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginPage")
    public String loginPage(){

        return "login";
    }
    @GetMapping("/")
    public String dashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        User user  = userService.findById(username);
        model.addAttribute("user",user);
        if(Objects.equals(user.getRole(), "ROLE_ADMIN")){
            return "redirect:/admin/?uid="+user.getUid();
        }
        else if(Objects.equals(user.getRole(), "ROLE_TEACHER")){
            return "redirect:/teacher/?uid="+user.getUid();
        }
        return "dashboard";
    }

    @GetMapping("/profile")
    public String userprofile(Model model, @RequestParam("uid") String uid){
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "profile";


    }
    @GetMapping("/editprofile")
    public String editprofile(Model model, @RequestParam("uid") String uid){
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "editprofile";
    }

    @PostMapping("/updateProfile")
    public String save(@ModelAttribute User user) {
        System.out.println("SAVING"+ user.getUsername());

        userService.updateProfile(user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/profile?uid="+logged.getUid();

    }

}
