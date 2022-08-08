package me.pluto.nilinproject.repository;

import me.pluto.nilinproject.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    boolean existsAdminByUserEqualsAndPasswordEquals(String user, String password);

}
