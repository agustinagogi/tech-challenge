package com.agustina.library.controller;

import com.agustina.library.dto.CreateBookRequest;
import com.agustina.library.model.Book;
import com.agustina.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody CreateBookRequest request){
        return bookService.createBook(request);
    }
}
