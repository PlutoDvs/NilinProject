package me.pluto.nilinproject.services;

import me.pluto.nilinproject.model.Course;
import me.pluto.nilinproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    @Autowired
    public CourseService(CourseRepository courseRepository, TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void addCourse(Course course){
        courseRepository.insert(course);
    }

    @Transactional
    public void setTeacher(String id, String teacherName){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Course by id " + id + " was not found"));
        if (course.getTeacherName() == null){
            course.setTeacherName(teacherName);
            teacherService.addCourse(teacherName, id);
            courseRepository.save(course);
        }else {
            throw new IllegalStateException("Course " + course.getId() + " already has a teacher assigned to it");
        }
    }

}
