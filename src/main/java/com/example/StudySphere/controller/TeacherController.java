package com.example.StudySphere.controller;

import com.example.StudySphere.entity.Material;
import com.example.StudySphere.entity.Subject;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.entity.User;
import com.example.StudySphere.service.MaterialService;
import com.example.StudySphere.service.SubjectService;
import com.example.StudySphere.service.TeacherService;
import com.example.StudySphere.service.UserService;
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
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MaterialService materialService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/")
    public String dashboard(@RequestParam("uid") String uid, Model model){
        Teacher teacher  = teacherService.findById(uid);
        model.addAttribute("teacher",teacher);
        return "teacher/dashboard";
    }

    @GetMapping("/manageMaterial")
    public String manageMaterial(@RequestParam("uid") String uid, Model model){
        Teacher teacher  = teacherService.findById(uid);
        model.addAttribute("teacher",teacher);
        List<Subject> anchorSubject = subjectService.anchorSubjects(uid);
        List<Subject> allSubject = subjectService.findAll();
        model.addAttribute("anchorSubjects",anchorSubject);
        model.addAttribute("allSubjects",allSubject);
        return "teacher/manageMaterial";
    }

    @GetMapping("/anchorMaterial")
    public String anchorMaterial(@RequestParam("subjectId") String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Teacher teacher = teacherService.findById(username);
        model.addAttribute("teacher", teacher);
        List<Material> materials = subjectService.findAllMaterialsBySubjectId(id);
        model.addAttribute("materials", materials);
        System.out.println(materials);
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);
        model.addAttribute("material", new Material());

        return "teacher/anchorMaterial";
    }

    @PostMapping("/addMaterial")
    public String upload(@ModelAttribute Material material,@RequestParam("subject_id") String subject_id, @RequestParam("pdfFile") MultipartFile file, RedirectAttributes redirectAttributes) {
        System.out.println("reached add");
        if (file.isEmpty()) {
            System.out.println("empyty");
            redirectAttributes.addFlashAttribute("message", "Please select a PDF file to upload.");
        }
        try {
            File tempFile = File.createTempFile("temp", ".pdf");

            // Transfer the multipart file content to the temporary file
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }
            subjectService.addMaterial(subject_id,material.getChapter(),tempFile);
//            System.out.println(res.getUrl());


        } catch (Exception e) {
            System.out.println(e.toString());
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
        }

        return "redirect:/teacher/anchorMaterial?subjectId="+subject_id;
    }

    @GetMapping("/viewMaterial")
    public String viewMaterial(@RequestParam("subjectId") String id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Teacher teacher = teacherService.findById(username);
        model.addAttribute("teacher", teacher);
        List<Material> materials = subjectService.findAllMaterialsBySubjectId(id);
        model.addAttribute("materials", materials);
        System.out.println(materials);
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);

        return "teacher/viewMaterial";
    }
    @GetMapping("/viewMaterial/displayChapter")
    public String viewChapter(@RequestParam("material_id") int id, @RequestParam("subject_id") String subjectId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Teacher teacher = teacherService.findById(username);
        model.addAttribute("teacher", teacher);
        Material material = materialService.findMaterialById(id);
        String path= "https://drive.google.com/file/d/"+material.getFileId()+"/preview";
        List<Material> materials = subjectService.findAllMaterialsBySubjectId(subjectId);
        model.addAttribute("materials", materials);
        model.addAttribute("path",path);
        model.addAttribute("chapter",material.getChapter());
        System.out.println(material.toString());
        Subject subject = subjectService.findById(subjectId);
        model.addAttribute("subject", subject);

        return "teacher/viewChapter";
    }

}
