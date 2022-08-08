package me.pluto.nilinproject.services;

import me.pluto.nilinproject.model.Course;
import me.pluto.nilinproject.model.Teacher;
import me.pluto.nilinproject.repository.CourseRepository;
import me.pluto.nilinproject.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, StudentService studentService) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    public void addNewTeacher(Teacher teacher){
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByNameEquals(teacher.getName());
        if (teacherOptional.isEmpty()){
            teacherRepository.insert(teacher);
        }else {
            throw new IllegalStateException("Teacher with name " + teacher.getName() + " already exists");
        }
    }

    public List<Course> getTeacherCourses(String name){
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByNameEquals(name);

        if (teacherOptional.isPresent()){
            List<Course> teacherCourses = new ArrayList<>();

            for (String s : teacherOptional.get().getCoursesId()){
                Optional<Course> courseOptional = courseRepository.findById(s);
                courseOptional.ifPresent(teacherCourses::add);
            }
            return teacherCourses;
        }else {
            return null;
        }
    }

    public void removeStudentFromCourse(String name, String id, Long code){
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByNameEquals(name);
        if (teacherOptional.isPresent()){
            Teacher teacher = teacherOptional.get();
            if (teacher.getCoursesId().contains(id)){
                studentService.removeStudentCourse(code, id);
            }else {
                throw new IllegalStateException("Course " + id + " was not found for teacher " + name);
            }
        }else {
            throw new IllegalStateException("Teacher " + name + " not found!");
        }
    }


    public void addCourse(String teacherName, String courseId){
        Optional<Teacher> teacherOptional = teacherRepository.findTeacherByNameEquals(teacherName);
        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            teacher.getCoursesId().add(courseId);
            teacherRepository.save(teacher);
        }else {
            throw new IllegalStateException("Teacher with name " + teacherName + " was not found!");
        }
    }

    public Optional<Teacher> getTeacher(String name){
        return teacherRepository.findTeacherByNameEquals(name);
    }
}
