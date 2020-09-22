package com.sihalov.laundry.service;

import com.sihalov.laundry.data.entity.Admin;
import com.sihalov.laundry.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }
}
