package com.example.StudySphere.controller;


import com.example.StudySphere.entity.Student;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.service.StudentService;
import com.example.StudySphere.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;





    @GetMapping("/")
    public String dashboard(@RequestParam("uid") String uid, Model model){
        Student student  = studentService.findById(uid);
        model.addAttribute("student",student);
        return "student/dashboard";
    }

}
