package me.pluto.nilinproject.controllers;

import me.pluto.nilinproject.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/getCourses/{name}")
    public ResponseEntity<?> getTeacherCourses(@PathVariable String name){
        return ResponseEntity.ok(teacherService.getTeacherCourses(name));
    }

    @PutMapping("/removeStudent/{name}/{id}/{code}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable String name,
                                                          @PathVariable String id,
                                                          @PathVariable Long code){
        teacherService.removeStudentFromCourse(name, id, code);
        return ResponseEntity.ok(teacherService.getTeacherCourses(name));
    }
}
