package me.pluto.nilinproject.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "student")
@Data
public class Student{

    @Id
    private String id;

    private String name;
    private long code;
    private String major;
    private List<String> selectedCoursesId;

    public Student(String name, long code, String major) {
        this.name = name;
        this.code = code;
        this.major = major;
        this.selectedCoursesId = new ArrayList<>();
    }

    //Setters and getters are generated by lombok
}
