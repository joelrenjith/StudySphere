package com.example.StudySphere.controller;


import com.example.StudySphere.entity.Assignment;
import com.example.StudySphere.entity.Material;
import com.example.StudySphere.entity.Subject;
import com.example.StudySphere.entity.Teacher;
import com.example.StudySphere.service.AssignmentService;
import com.example.StudySphere.service.SubjectService;
import com.example.StudySphere.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    TeacherService teacherService;
    @Autowired
    SubjectService subjectService;



    FindAuth findAuth=new FindAuth();

    @GetMapping("/manageAssignment")
    public String manageAssignment(Model model) {
        String uid = findAuth.findUser();
        Teacher teacher = teacherService.findById(uid);
        List<Assignment> assignments = teacher.getAssignments();
        HashSet<Subject> assignment_subjects= new HashSet<Subject>();
        for (Assignment assignment : assignments) {
            assignment_subjects.add(assignment.getSubject());
        }
        model.addAttribute("assignment_subjects", assignment_subjects);
        model.addAttribute("teacher", teacher);
        return "assignment/manageAssignment";
    }

    @GetMapping("/createAssignment")
    public String createAssignment( @RequestParam("subject_id") String subject_id , Model model) {
        String uid = findAuth.findUser();
        Assignment assignment = new Assignment();
        model.addAttribute("subject", subjectService.findById(subject_id));
        model.addAttribute("assignment", assignment);
        model.addAttribute("teacher", teacherService.findById(uid));
        return "assignment/createAssignment";

    }

    @PostMapping("/addAssignment")
    public String upload(@ModelAttribute Assignment assignment, @RequestParam("subject_id") String subject_id,@RequestParam("teacher_id") String teacher_id, @RequestParam("pdfFile") MultipartFile file, RedirectAttributes redirectAttributes) {
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
            Assignment newAssignment = assignmentService.addAssignment(assignment,tempFile);
            subjectService.addAssignmnet(subject_id,newAssignment);
            teacherService.addAssignmnet(teacher_id,newAssignment);


//            System.out.println(res.getUrl());


        } catch (Exception e) {
            System.out.println(e.toString());
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
        }

        return "redirect:/assignment/manageAssignment";
    }

    @GetMapping("/viewAssignments")
    public String viewAssignments(@RequestParam("subject_id") String subject_id, Model model) {
        String uid = findAuth.findUser();

        List<Assignment> assignments1 = subjectService.findById(subject_id).getAssignments();
        List<Assignment> assignments2 = teacherService.findById(uid).getAssignments();
        assignments1.retainAll(assignments2);
        model.addAttribute("subject", subjectService.findById(subject_id));
        model.addAttribute("assignments", assignments1);
        model.addAttribute("teacher", teacherService.findById(uid));
        return "assignment/viewAssignments";
    }

    @GetMapping("/viewAssignment/displayAssignment")
    public String displayAssignment(@RequestParam("assignment_id") int assignment_id, Model model) {
        String uid = findAuth.findUser();
        model.addAttribute("teacher", teacherService.findById(uid));
        Assignment assignment = assignmentService.findById(assignment_id);
        String path= "https://drive.google.com/file/d/"+assignment.getFile_id()+"/preview";
        List<Assignment> assignments1 = assignment.getSubject().getAssignments();
        List<Assignment> assignments2 = teacherService.findById(uid).getAssignments();
        assignments1.retainAll(assignments2);
        model.addAttribute("assignmentList", assignments1);
        model.addAttribute("path",path);
        model.addAttribute("assignment", assignment);
        model.addAttribute("subject", assignment.getSubject());
        return "assignment/displayAssignment";


    }
}
