package com.roshogolla.bookstoreapi.repository;

import com.roshogolla.bookstoreapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByPriceGreaterThan(Float price);

    List<Book> findByPriceLessThan(Float price);
}
