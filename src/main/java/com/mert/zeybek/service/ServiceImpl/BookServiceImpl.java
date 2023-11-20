package com.mert.zeybek.service.ServiceImpl;

import com.mert.zeybek.dto.Request.BookRequest;
import com.mert.zeybek.dto.Response.BookResponse;
import com.mert.zeybek.exception.BookNotFoundException;
import com.mert.zeybek.model.Book;
import com.mert.zeybek.repository.BookRepository;
import com.mert.zeybek.service.Service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponse bookAdd(BookRequest bookRequest) {
        Book book = mapRequestToBook(bookRequest);
        book.setCreatedAt(new Date());
        Book savedBook = bookRepository.save(book);
        return mapBookToResponse(savedBook);
    }

    @Override
    public Page<BookResponse> bookList(org.springframework.data.domain.Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAllByOrderByCreatedAtDesc(pageable);
        return booksPage.map(this::mapBookToResponse);
    }

    @Override
    public BookResponse getBookByISBN(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
        return mapBookToResponse(book);
    }

    @Override
    public BookResponse bookUpdate(BookRequest bookRequest, String bookIsbn) {
        Book existingBook = bookRepository.findByIsbn(bookIsbn)
                .orElseThrow(() -> new BookNotFoundException("Cannot update book. Book not found with ISBN: " + bookIsbn));
        updateBookFromRequest(existingBook, bookRequest);
        existingBook.setUpdatedAt(new Date());
        Book updatedBook = bookRepository.save(existingBook);
        return mapBookToResponse(updatedBook);
    }

    @Override
    public BookResponse bookDelete(String bookIsbn) {
        Book book = bookRepository.findByIsbn(bookIsbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + bookIsbn));
        bookRepository.deleteById(book.getId());
        return mapBookToResponse(book);
    }

    private Book mapRequestToBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setIsbn(bookRequest.getIsbn());
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        book.setStockQuantity(bookRequest.getStockQuantity());
        return book;
    }

    private void updateBookFromRequest(Book book, BookRequest bookRequest) {
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPrice(bookRequest.getPrice());
        book.setStockQuantity(bookRequest.getStockQuantity());
    }

    private BookResponse mapBookToResponse(Book book) {
        return new BookResponse(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStockQuantity(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
