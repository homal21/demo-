package project.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bookstore.entity.Roles;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
