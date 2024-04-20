package com.example.StudySphere.controller;

import com.example.StudySphere.entity.*;
import com.example.StudySphere.service.*;
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
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    MaterialService materialService;


    FindAuth findAuth=new FindAuth();


//    teacher
    @GetMapping("/manageTeacherMaterial")
    public String manageTeacherMaterial(@RequestParam("uid") String uid, Model model){
        Teacher teacher  = teacherService.findById(uid);
        model.addAttribute("teacher",teacher);
        List<Subject> anchorSubject = subjectService.anchorSubjects(uid);
        List<Subject> allSubject = subjectService.findAll();
        model.addAttribute("anchorSubjects",anchorSubject);
        model.addAttribute("allSubjects",allSubject);
        return "teacher/manageMaterial";
    }


    //teacher
    @GetMapping("/anchorMaterial")
    public String anchorMaterial(@RequestParam("subjectId") String id, Model model) {
        String username = findAuth.findUser();
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
//    teacher
    @PostMapping("/addMaterial")
    public String upload(@ModelAttribute Material material, @RequestParam("subject_id") String subject_id, @RequestParam("pdfFile") MultipartFile file, RedirectAttributes redirectAttributes) {
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

        return "redirect:/material/anchorMaterial?subjectId="+subject_id;
    }

    @GetMapping("/manageStudentMaterial")
    public String manageStudentMaterial(@RequestParam("uid") String uid, Model model){
        Student student  = studentService.findById(uid);
        model.addAttribute("student",student);
        List<Subject> allSubject = subjectService.findAll();
        model.addAttribute("allSubjects",allSubject);
        return "student/manageMaterial";
    }

    @GetMapping("/viewMaterial")
    public String viewMaterial(@RequestParam("subjectId") String id, Model model) {
        String username = findAuth.findUser();
        User user = userService.findById(username);
        model.addAttribute("user", user);
        List<Material> materials = subjectService.findAllMaterialsBySubjectId(id);
        model.addAttribute("materials", materials);
        System.out.println(materials);
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);

        return "material/viewMaterial";
    }

    @GetMapping("/viewMaterial/displayChapter")
    public String viewChapter(@RequestParam("material_id") int id , @RequestParam("subject_id") String subjectId, Model model) {
        String username = findAuth.findUser();
        User user = userService.findById(username);
        model.addAttribute("user", user);
        Material material = materialService.findMaterialById(id);
        String path= "https://drive.google.com/file/d/"+material.getFileId()+"/preview";
        List<Material> materials = subjectService.findAllMaterialsBySubjectId(subjectId);
        model.addAttribute("materials", materials);
        model.addAttribute("path",path);
        model.addAttribute("chapter",material.getChapter());
        System.out.println(material.toString());
        Subject subject = subjectService.findById(subjectId);
        model.addAttribute("subject", subject);

        return "material/viewChapter";
    }




}
