package com.mert.zeybek.repository;

import com.mert.zeybek.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Page<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<Book> findByIsbn(String Isbn);
}
