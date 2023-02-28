package com.example.demoWebBanHang.Repo;

import com.example.demoWebBanHang.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {


    List<Cart> findAllByBookId(Long bookId);

    boolean existsByBookId(Long bookId);

    @Transactional
    void deleteAllByUserId(Long userId);
}
