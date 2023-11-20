package com.mert;

import com.mert.zeybek.dto.Request.BookRequest;
import com.mert.zeybek.dto.Response.BookResponse;
import com.mert.zeybek.model.Book;
import com.mert.zeybek.repository.BookRepository;
import com.mert.zeybek.service.ServiceImpl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testBookAdd() {
        // Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn("54543435443434323232543");
        bookRequest.setPrice(58.45);
        bookRequest.setAuthor("mert");
        bookRequest.setTitle("deneme");
        bookRequest.setStockQuantity(250);

        // Mock the behavior of the bookRepository
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setId(1L); // Set an example ID for testing
            return savedBook;
        });

        // Act
        BookResponse result = bookService.bookAdd(bookRequest);

        // Assert
        assertNotNull(result);
        assertEquals("54543435443434323232543", result.getIsbn());
        assertEquals(58.45, result.getPrice(), 0.001);
        assertEquals("mert", result.getAuthor());
        assertEquals("deneme", result.getTitle());
        assertEquals(250, result.getStockQuantity());

    }

    @Test
    public void testBookList() {
        // Mocking the bookRepository to return a Page of books
        when(bookRepository.findAllByOrderByCreatedAtDesc(any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(createTestBook())));

        Page<BookResponse> bookResponses = bookService.bookList(PageRequest.of(0, 10));

        assertNotNull(bookResponses);
        assertEquals(1, bookResponses.getTotalElements());
        assertEquals("123456789", bookResponses.getContent().get(0).getIsbn());
    }

    @Test
    public void testGetBookByISBN() {
        when(bookRepository.findByIsbn("123456789")).thenReturn(Optional.of(createTestBook()));

        BookResponse bookResponse = bookService.getBookByISBN("123456789");

        assertNotNull(bookResponse);
        assertEquals("123456789", bookResponse.getIsbn());
    }

    @Test
    public void testBookUpdate() {
        when(bookRepository.findByIsbn("567556")).thenReturn(Optional.of(createTestBook()));

        // Mocking the bookRepository save method
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book savedBook = invocation.getArgument(0);
            savedBook.setUpdatedAt(new Date());
            return savedBook;
        });

        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Updated");
        bookRequest.setAuthor("Updated Author");
        bookRequest.setPrice(29.99);
        bookRequest.setStockQuantity(150);

        BookResponse updatedBook = bookService.bookUpdate(bookRequest, "567556");

        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
        assertEquals(29.99, updatedBook.getPrice());
        assertEquals(150, updatedBook.getStockQuantity());
        assertNotNull(updatedBook.getUpdatedDate());
    }

    @Test
   public void testBookDelete() {
        // Mocking the bookRepository to return an Optional of a book
        when(bookRepository.findByIsbn("567556")).thenReturn(Optional.of(createTestBook()));

        // Mocking the bookRepository deleteById method
        doNothing().when(bookRepository).deleteById(ArgumentMatchers.anyLong());

        BookResponse deletedBook = bookService.bookDelete("123456789");

        assertNotNull(deletedBook);
        assertEquals("123456789", deletedBook.getIsbn());
    }

    private Book createTestBook() {
        Book book = new Book();
        book.setIsbn("123456789");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(19.99);
        book.setStockQuantity(100);
        book.setCreatedAt(new Date());
        return book;
    }
}
