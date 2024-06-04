package com.example.StudySphere.controller;


import com.example.StudySphere.dao.SubmissionDao;
import com.example.StudySphere.entity.*;
import com.example.StudySphere.service.*;
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

    @Autowired
    StudentService studentService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    ResultService resultService;



    FindAuth findAuth=new FindAuth();
    @Autowired
    private SubmissionDao submissionDao;

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
        model.addAttribute("user", teacherService.findById(uid));
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
        List<Submission> submissions = submissionService.findByAssignment(assignment_id);
        model.addAttribute("assignmentList", assignments1);
        model.addAttribute("path",path);
        model.addAttribute("assignment", assignment);
        model.addAttribute("subject", assignment.getSubject());
        model.addAttribute("submissions", submissions);
        return "assignment/displayAssignment";


    }

    @GetMapping("/manageStudentAssignment")
    public String manageStudentAssignment(Model model) {
        String uid = findAuth.findUser();
        Student student = studentService.findById(uid);
        List<Assignment> assignments = assignmentService.getAllAssignments();
        HashSet<Subject> assignment_subjects= new HashSet<>();
        for (Assignment assignment : assignments) {
            assignment_subjects.add(assignment.getSubject());
        }
        model.addAttribute("assignment_subjects", assignment_subjects);
        model.addAttribute("student", student);
        return "assignment/manageStudentAssignment";
    }

    @GetMapping("/viewStudentAssignments")
    public String viewStudentAssignments(@RequestParam("subject_id") String subject_id, Model model) {
        String uid = findAuth.findUser();
        List<Assignment> assignments1 = subjectService.findById(subject_id).getAssignments();
        model.addAttribute("subject", subjectService.findById(subject_id));
        model.addAttribute("assignments", assignments1);
        model.addAttribute("user", studentService.findById(uid));
        return "assignment/viewAssignments";
    }

    @GetMapping("/viewAssignment/displayStudentAssignment")
    public String displayStudentAssignment(@RequestParam("assignment_id") int assignment_id, Model model) {
        String uid = findAuth.findUser();
        model.addAttribute("student", studentService.findById(uid));
        Assignment assignment = assignmentService.findById(assignment_id);
        String path= "https://drive.google.com/file/d/"+assignment.getFile_id()+"/preview";
        Submission submission = submissionDao.findByStudentUidAndAssignmentId(uid,assignment_id);

        List<Assignment> assignments1 = assignment.getSubject().getAssignments();
        model.addAttribute("assignmentList", assignments1);
        model.addAttribute("oldsubmission", submission);
        model.addAttribute("path",path);

        model.addAttribute("assignment", assignment);
        model.addAttribute("subject", assignment.getSubject());
        model.addAttribute("submission",new Submission());
        return "assignment/displayStudentAssignment";


    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute Submission submission,@RequestParam("oldSubmission_id") int oldSubmission_id, @RequestParam("assignment_id") int assignment_id,@RequestParam("student_id") String student_id, @RequestParam("pdfFile") MultipartFile file, RedirectAttributes redirectAttributes) {
        if(oldSubmission_id!=-255){
            if(submissionService.findById(oldSubmission_id).getResult()!=null){
            resultService.delete(submissionService.findById(oldSubmission_id).getResult());}
            submissionService.delete(submissionService.findById(oldSubmission_id));
        }
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
            Submission newSubmission = new Submission();
            studentService.addSubmission(student_id,newSubmission);
            assignmentService.addSubmission(assignment_id,newSubmission);
            submissionService.addSubmission(newSubmission,tempFile);


//            System.out.println(res.getUrl());


        } catch (Exception e) {
            System.out.println(e.toString());
            redirectAttributes.addFlashAttribute("error", "Failed to upload file: " + e.getMessage());
        }

        return "redirect:/assignment/viewAssignment/displayStudentAssignment?assignment_id="+assignment_id;
    }

    @GetMapping("/gradeAssignment")
    public String gradeAssignment(@RequestParam("submission_id") int submission_id, Model model) {
        String uid = findAuth.findUser();
        Submission submission = submissionService.findById(submission_id);
        String path= "https://drive.google.com/file/d/"+submission.getFile_id()+"/preview";
        model.addAttribute("submission", submission);
        model.addAttribute("teacher", teacherService.findById(uid));
//        model.addAttribute("result",new Result());
        model.addAttribute("path",path);
        return "assignment/gradeAssignment";

    }

    @PostMapping("/submitGrade")
    public String submitGrade(@RequestParam("grade")char grade,@RequestParam("submission_id")int submission_id,Model model){
        Submission submission = submissionService.findById(submission_id);
        Result existingResult = submission.getResult();
        if (existingResult != null) {
                existingResult.setGrade(grade);
                resultService.save(existingResult);

        }
        else{

        Result result = new Result();
        result.setGrade(grade);

        result.setSubmission(submission);
        submission.setResult(result);
        resultService.save(result);
        submissionService.save(submission);}
        return "redirect:/assignment/viewAssignment/displayAssignment?assignment_id="+submission.getAssignment().getId();

    }

}
