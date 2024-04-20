package com.example.StudySphere.controller;

import com.example.StudySphere.entity.Material;
import com.example.StudySphere.entity.Subject;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.service.MaterialService;
import com.example.StudySphere.service.SubjectService;
import com.example.StudySphere.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;




    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/")
    public String dashboard(@RequestParam("uid") String uid, Model model){
        Teacher teacher  = teacherService.findById(uid);
        model.addAttribute("teacher",teacher);
        return "teacher/dashboard";
    }



}
