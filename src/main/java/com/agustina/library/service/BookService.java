package com.agustina.library.service;

import com.agustina.library.dto.CreateBookRequest;
import com.agustina.library.model.Book;
import com.agustina.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(CreateBookRequest request){
        Book book = new Book(null, request.title(), request.isbn(), request.availableCopies());

        return bookRepository.save(book);
    }

    @GetMapping("/search")
    public List<Book> searchBooksByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
