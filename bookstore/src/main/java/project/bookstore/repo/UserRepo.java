package project.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.bookstore.entity.User;

import javax.transaction.Transactional;

@Repository
public  interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteUserById(Long id);
    boolean existsByUsername(String username);

    boolean existsUserById(Long id);
}
