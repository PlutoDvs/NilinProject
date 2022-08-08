package me.pluto.nilinproject.services;

import me.pluto.nilinproject.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean authenticate(String user, String password){
        return adminRepository.existsAdminByUserEqualsAndPasswordEquals(user, password);
    }

}
