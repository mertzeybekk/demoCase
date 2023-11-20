package com.mert.zeybek.service.Service;

import com.mert.zeybek.dto.Request.BookRequest;
import com.mert.zeybek.dto.Response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    public BookResponse bookAdd(BookRequest bookRequest);
    Page<BookResponse> bookList(Pageable pageable);
    public BookResponse getBookByISBN(String Isbn);
    public BookResponse bookUpdate(BookRequest bookRequest,String bookIsbn);
    public BookResponse bookDelete(String bookIsbn);
}
