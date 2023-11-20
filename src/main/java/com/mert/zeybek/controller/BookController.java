package com.mert.zeybek.controller;

import com.mert.zeybek.dto.Request.BookRequest;
import com.mert.zeybek.dto.Response.BookResponse;
import com.mert.zeybek.service.Service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BookResponse>> addBook(@RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.bookAdd(bookRequest);
        ApiResponse<BookResponse> response = new ApiResponse<>();
        response.setMessage("Book added successfully");
        response.setData(bookResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BookResponse> bookResponses = bookService.bookList(PageRequest.of(page, size));
        ApiResponse<Page<BookResponse>> response = new ApiResponse<>();
        response.setMessage("Books retrieved successfully");
        response.setData(bookResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details/{Isbn}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookByISBN(@PathVariable String Isbn) {
        BookResponse bookResponse = bookService.getBookByISBN(Isbn);
        ApiResponse<BookResponse> response = new ApiResponse<>();
        response.setMessage("Books retrieved successfully");
        response.setData(bookResponse);
        Link selfLink = linkTo(methodOn(BookController.class).getBookByISBN(Isbn)).withSelfRel().withType("GET");
        bookResponse.add(selfLink);
        response.setLink(selfLink);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{Isbn}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@RequestBody BookRequest bookRequest, @PathVariable String Isbn) {
        BookResponse bookResponse = bookService.bookUpdate(bookRequest, Isbn);
        ApiResponse<BookResponse> response = new ApiResponse<>();
        response.setMessage("Book updated successfully");
        response.setData(bookResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Isbn}")
    public ResponseEntity<ApiResponse<BookResponse>> deleteBook(@PathVariable String Isbn) {
        BookResponse bookResponse = bookService.bookDelete(Isbn);
        ApiResponse<BookResponse> response = new ApiResponse<>();
        response.setMessage("Book deleted successfully");
        response.setData(bookResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
