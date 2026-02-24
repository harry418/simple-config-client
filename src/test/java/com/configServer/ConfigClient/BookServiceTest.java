package com.configServer.ConfigClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.configServer.ConfigClient.entity.Book;
import com.configServer.ConfigClient.repo.BookRepository;
import com.configServer.ConfigClient.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	private Book testBook;

	@BeforeEach
	void setup() {
		testBook = Book.builder().bookId(1L).name("Spring Boot 3 Guide").summary("Comprehensive Testing").build();
	}

	@Test
    @DisplayName("Should return book when found by ID")
    void getBookById_Found() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Spring Boot 3 Guide", result.getName());
    }
	
	@Test
    @DisplayName("Should throw exception when book ID does not exist")
    void getBookById_NotFound() {
        Mockito.when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookById(99L);
        });

        assertTrue(exception.getMessage().contains("not found"));
    }
	
	@Test
    @DisplayName("Should save book successfully")
    void saveBook_Success() {
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        Book savedBook = bookService.saveBook(testBook);

        assertNotNull(savedBook);
    }
	
	
}
