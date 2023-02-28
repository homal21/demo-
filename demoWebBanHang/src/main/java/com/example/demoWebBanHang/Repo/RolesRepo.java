package com.example.demoWebBanHang.Repo;

import com.example.demoWebBanHang.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
