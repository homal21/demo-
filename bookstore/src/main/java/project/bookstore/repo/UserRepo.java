package project.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bookstore.entity.User;

@Repository
public  interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
