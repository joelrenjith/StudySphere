package com.example.StudySphere.controller;

import com.example.StudySphere.entity.Student;
import com.example.StudySphere.entity.Subject;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.entity.User;
import com.example.StudySphere.factory.UserFactory;
import com.example.StudySphere.factory.UserFactoryManager;
import com.example.StudySphere.service.SubjectService;
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
    @Autowired
    private SubjectService subjectService;


    private FindAuth findAuth=new FindAuth();

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
        String username = findAuth.findUser();
        System.out.println(username);
        User admin  = userService.findById(username);
        model.addAttribute("admin",admin);
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "admin/editAccount";
    }

    @PostMapping("/createAccounts")
    public String createAccounts(@RequestParam("accountType") String type, Model model){
        String username = findAuth.findUser();
        User admin  = userService.findById(username);
        model.addAttribute("admin",admin);

        UserFactoryManager factoryManager = new UserFactoryManager();
        User newUser = factoryManager.createUser(type);
        model.addAttribute("user",newUser);
        return "admin/create"+type+"Account";
    }

    @PostMapping("/createProfile")
    public String create(@ModelAttribute User user) {
        System.out.println("SAVING"+ user.getUsername());
        userService.save(user);
        String username = findAuth.findUser();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/profile?uid="+logged.getUid();

    }
    @PostMapping("/createStudentProfile")
    public String createStudent(@ModelAttribute Student student) {
        System.out.println("SAVING"+ student.getUsername());


        userService.save(student);

        String username = findAuth.findUser();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/profile?uid="+logged.getUid();

    }
    @PostMapping("/createTeacherProfile")
    public String createTeacher(@ModelAttribute Teacher teacher) {
        System.out.println("SAVING"+ teacher.getUsername());


        userService.save(teacher);

        String username = findAuth.findUser();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/profile?uid="+logged.getUid();

    }

    @GetMapping("/manageSubjects")
    public String manageSubjects(@RequestParam("uid") String uid, Model model){
        User user  = userService.findById(uid);
        model.addAttribute("user",user);
        return "admin/manageSubjects";
    }

    @GetMapping("/editSubject")
    public String editSubject(@RequestParam("id") String id, Model model){
        String username = findAuth.findUser();
        System.out.println(username);
        User admin  = userService.findById(username);
        model.addAttribute("admin",admin);
        Subject subject  = subjectService.findById(id);
        model.addAttribute("subject",subject);
        return "admin/editSubject";
    }



    @PostMapping("/updateSubject")
    public String updateSubject(@ModelAttribute Subject subject){
        System.out.println("SAVING"+ subject.getName());


        subjectService.updateSubject(subject);

        String username = findAuth.findUser();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/admin/manageSubjects?uid="+logged.getUid();
    }

    @GetMapping("/createSubject")
    public String createSubject( Model model) {
        String username = findAuth.findUser();
        User admin = userService.findById(username);
        model.addAttribute("admin", admin);

        Subject newsubject = new Subject();
        model.addAttribute("subject", newsubject);
        return "admin/createSubject";

    }

    @PostMapping("/saveSubject")
    public String saveSubject(@ModelAttribute Subject subject) {
        System.out.println("SAVING"+ subject.getName());


        subjectService.save(subject);

        String username = findAuth.findUser();
        System.out.println(username);
        User logged  = userService.findById(username);

        return "redirect:/admin/manageSubjects?uid="+logged.getUid();

    }

}
