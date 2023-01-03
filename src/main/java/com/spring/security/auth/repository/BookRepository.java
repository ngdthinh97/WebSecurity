package com.spring.security.auth.repository;

import com.spring.security.auth.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
