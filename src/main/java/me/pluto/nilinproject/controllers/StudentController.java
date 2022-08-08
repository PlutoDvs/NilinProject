package me.pluto.nilinproject.controllers;

import me.pluto.nilinproject.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getCourses/{code}")
    public ResponseEntity<?> getStudentCourses(@PathVariable long code){
        if (studentService.getStudentCourses(code) != null){
            return ResponseEntity.ok(studentService.getStudentCourses(code));
        }else {
            return new ResponseEntity<>("Student with code " + code + " was not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/addCourse/{code}/{id}")
    private ResponseEntity<?> addStudentCourse(@PathVariable Long code, @PathVariable String id){
        studentService.addStudentCourse(code, id);
        return ResponseEntity.ok(studentService.getStudent(code));
    }

    @PutMapping("/removeCourse/{code}/{id}")
    private ResponseEntity<?> removeStudentCourse(@PathVariable Long code, @PathVariable String id){
        studentService.removeStudentCourse(code, id);
        return ResponseEntity.ok(studentService.getStudent(code));
    }

}
