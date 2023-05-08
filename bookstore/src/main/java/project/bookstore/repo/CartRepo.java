package project.bookstore.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.bookstore.entity.Cart;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c WHERE c.id IN (SELECT MAX(c2.id) FROM Cart c2 where c2.userId = :userId GROUP BY  c2.bookId)")
    List<Cart> findAllByGroupBookId(@Param("userId")Long userId) ;
    List<Cart> findByBookIdOrderByIdDesc(Long bookId);
    List<Cart> findAllByUserId(Long userId);

    List<Cart> findAllByBookId(Long bookId);

    boolean existsByBookId(Long bookId);

    @Transactional
    void deleteAllByUserId(Long userId);
}