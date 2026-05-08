package com.agustina.library.service;

import com.agustina.library.dto.CreateBookRequest;
import com.agustina.library.model.Book;
import com.agustina.library.repository.BookRepository;
import org.springframework.stereotype.Service;

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
}
