package com.example.demoWebBanHang.Repo;
import com.example.demoWebBanHang.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserRepo extends JpaRepository <User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
