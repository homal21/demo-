package project.bookstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bookstore.entity.Book;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Long> {
    List<Book> findAll();
}
