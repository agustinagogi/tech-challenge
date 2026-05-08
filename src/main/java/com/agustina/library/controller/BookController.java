package com.agustina.library.controller;

import com.agustina.library.dto.CreateBookRequest;
import com.agustina.library.model.Book;
import com.agustina.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody CreateBookRequest request){
        return bookService.createBook(request);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title){
        return bookService.searchBooksByTitle(title);
    }
}
