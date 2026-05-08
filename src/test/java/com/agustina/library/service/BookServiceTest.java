package com.agustina.library.service;

import com.agustina.library.dto.CreateBookRequest;
import com.agustina.library.model.Book;
import com.agustina.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceTest {

    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private final BookService bookService = new BookService(bookRepository);

    @Test
    void shouldCreateBook() {

        CreateBookRequest request = new CreateBookRequest("Prueba", "9780132350884", 3);

        Book savedBook = new Book(1L, "Prueba", "9780132350884", 3);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.createBook(request);

        /* System.out.println(result);
        System.out.println(result.getTitle());
        System.out.println(result.getIsbn());
        System.out.println(result.getAvailableCopies()); */

        assertEquals(1L, result.getId());
        assertEquals("Prueba", result.getTitle());
        assertEquals("9780132350884", result.getIsbn());
        assertEquals(3, result.getAvailableCopies());

        verify(bookRepository).save(any(Book.class));
    }
}
