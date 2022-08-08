package me.pluto.nilinproject.controllers;

import me.pluto.nilinproject.model.Course;
import me.pluto.nilinproject.model.Student;
import me.pluto.nilinproject.model.Teacher;
import me.pluto.nilinproject.services.AdminService;
import me.pluto.nilinproject.services.CourseService;
import me.pluto.nilinproject.services.StudentService;
import me.pluto.nilinproject.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StudentService studentService;
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Autowired
    public AdminController(StudentService studentService, AdminService adminService,
                           TeacherService teacherService, CourseService courseService) {
        this.studentService = studentService;
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    @PostMapping("/newStudent")
    public ResponseEntity<?> addNewStudent(@RequestBody Student student,
                                           @RequestParam(required = true) String user,
                                           @RequestParam(required = true) String password){
        if (adminService.authenticate(user, password)){
            studentService.addNewStudent(student);
            return ResponseEntity.ok(student);
        }else {
            return ResponseEntity.badRequest().body("Failed to authenticate admin");
        }
    }

    @PostMapping("/newTeacher")
    public ResponseEntity<?> addNewTeacher(@RequestBody Teacher teacher,
                                           @RequestParam(required = true) String user,
                                           @RequestParam(required = true) String password){
        if (adminService.authenticate(user, password)){
            teacherService.addNewTeacher(teacher);
            return ResponseEntity.ok(teacher);
        }else {
            return ResponseEntity.badRequest().body("Failed to authenticate admin");
        }
    }

    @PostMapping("/newCourse")
    public ResponseEntity<?> addNewCourse(@RequestBody Course course,
                                          @RequestParam(required = true) String user,
                                          @RequestParam(required = true) String password){
        if (adminService.authenticate(user, password)){
            courseService.addCourse(course);
            return ResponseEntity.ok(course);
        }else {
            return ResponseEntity.badRequest().body("Failed to authenticate admin");
        }
    }

    @GetMapping("/getStudent/{code}")
    public ResponseEntity<?> getStudent(@PathVariable Long code,
                                        @RequestParam(required = true) String user,
                                        @RequestParam(required = true) String password){
        if (adminService.authenticate(user, password)){
            Optional<Student> studentOptional = studentService.getStudent(code);
            if (studentOptional.isPresent()){
                return ResponseEntity.ok(studentOptional.get());
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().body("Failed to authenticate admin");
        }
    }

    @GetMapping("/getTeacher/{name}")
    public ResponseEntity<?> getTeacher(@PathVariable String name,
                                        @RequestParam(required = true) String user,
                                        @RequestParam(required = true) String password){
        if (adminService.authenticate(user, password)){
            Optional<Teacher> teacherOptional = teacherService.getTeacher(name);
            if (teacherOptional.isPresent()){
                return ResponseEntity.ok(teacherOptional.get());
            }else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().body("Failed to authenticate admin");
        }
    }

    @PutMapping("/setTeacher/{id}/{name}")
    public ResponseEntity<?> setTeacher(@PathVariable String id, @PathVariable String name,
                                        @RequestParam(required = true) String user,
                                        @RequestParam(required = true) String password){
        Optional<Teacher> teacherOptional = teacherService.getTeacher(name);
        if (teacherOptional.isPresent()){
            courseService.setTeacher(id, name);
            return ResponseEntity.ok("Set teacher : " + name + " for course " + id);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
