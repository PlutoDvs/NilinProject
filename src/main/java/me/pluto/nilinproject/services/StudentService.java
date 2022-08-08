package me.pluto.nilinproject.services;

import me.pluto.nilinproject.model.Course;
import me.pluto.nilinproject.model.Student;
import me.pluto.nilinproject.repository.CourseRepository;
import me.pluto.nilinproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Course> getStudentCourses(Long code){
        Optional<Student> student = studentRepository.findStudentByCode(code);
        if (student.isPresent()){
            Student s = student.get();
            List<Course> studentCourses = new ArrayList<>();
            for (String id : s.getSelectedCoursesId()){
                if(courseRepository.findById(id).isPresent()){
                     studentCourses.add(courseRepository.findById(id).get());
                }
            }
            return studentCourses;
        }else {
            return null;
        }
    }

    public void addNewStudent(Student student){
        Optional<Student> studentOptional = studentRepository.findStudentByCode(student.getCode());
        if (studentOptional.isEmpty()){
            studentRepository.insert(student);
        }else{
            throw new IllegalStateException("Student with the given code already exists!");
        }
    }

    public void addStudentCourse(Long code, String id){
        Optional<Student> studentOptional = studentRepository.findStudentByCode(code);
        if (studentOptional.isPresent()){
            Optional<Course> courseOptional = courseRepository.findById(id);
            if (courseOptional.isPresent()){

                Student student = studentOptional.get();
                Course course = courseOptional.get();

                if (student.getSelectedCoursesId().contains(id) || course.getAttendees().contains(code)){
                    throw new IllegalStateException("Student " + code + " has already attended for course " + id);
                }

                student.getSelectedCoursesId().add(id);
                course.getAttendees().add(code);

                studentRepository.save(student);
                courseRepository.save(course);

            }else {
                throw new IllegalStateException("Course with id " + id + " not found!");
            }
        }else {
            throw new IllegalStateException("Student with code " + code + " not found!");
        }
    }

    public void removeStudentCourse(Long code, String id){
        Optional<Student> studentOptional = studentRepository.findStudentByCode(code);
        if (studentOptional.isPresent()){
            Optional<Course> courseOptional = courseRepository.findById(id);
            if (courseOptional.isPresent()){

                Student student = studentOptional.get();
                Course course = courseOptional.get();

                if (!student.getSelectedCoursesId().contains(id) || !course.getAttendees().contains(code)){
                    throw new IllegalStateException("Student " + code + " has not attended for course " + id);
                }

                student.getSelectedCoursesId().remove(id);
                course.getAttendees().remove(code);

                studentRepository.save(student);
                courseRepository.save(course);

            }else {
                throw new IllegalStateException("Course with id " + id + " not found!");
            }
        }else {
            throw new IllegalStateException("Student with code " + code + " not found!");
        }
    }

    public Optional<Student> getStudent(Long code){
        return studentRepository.findStudentByCode(code);
    }

}
