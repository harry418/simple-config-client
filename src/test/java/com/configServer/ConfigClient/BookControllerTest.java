package com.configServer.ConfigClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.configServer.ConfigClient.controller.BookController;
import com.configServer.ConfigClient.entity.Book;
import com.configServer.ConfigClient.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookController.class)
public class BookControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockitoBean // Mocks the service layer in the Spring context
    private BookService bookService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Book testBook;
    
    @BeforeEach
    void setUp() {
        testBook = Book.builder()
                .bookId(1L)
                .name("Clean Code")
                .summary("A Handbook of Agile Software Craftsmanship")
                .build();
    }
    
    @Test
    @DisplayName("POST /api/books - Success")
    void createBookTest() throws Exception {
        Mockito.when(bookService.saveBook(any(Book.class))).thenReturn(testBook);
        
        mockMvc.perform(post("/api/books")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(testBook)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.name").value("Clean Code"));
     
    }
    
    @Test
    @DisplayName("GET /api/books - Success")
    void getAllBooksTest() throws Exception {
        List<Book> books = Arrays.asList(testBook, new Book(2L, "Java 21", "The future"));
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        
        mockMvc.perform(get("/api/books"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$[0].name").value("Clean Code"));
    }
    
    @Test
    @DisplayName("GET /api/books/{id} - Success")
    void getBookByIdTest() throws Exception {
        Mockito.when(bookService.getBookById(1L)).thenReturn(testBook);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1))
                .andExpect(jsonPath("$.name").value("Clean Code"));
    }
    
    @Test
    @DisplayName("PUT /api/books/{id} - Success")
    void updateBookTest() throws Exception {
        Book updatedDetails = Book.builder().name("Refactored Code").summary("Updated").build();
        Mockito.when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedDetails);

        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Refactored Code"));
    }
    
    @Test
    @DisplayName("DELETE /api/books/{id} - Success")
    void deleteBookTest() throws Exception {
        // No return needed for delete usually, just verify status
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully!"));

        Mockito.verify(bookService, Mockito.times(1)).deleteBook(1L);
    }
    
    
}
