package me.pluto.nilinproject.repository;

import me.pluto.nilinproject.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {

    Optional<Teacher> findTeacherByNameEquals(String name);

}
