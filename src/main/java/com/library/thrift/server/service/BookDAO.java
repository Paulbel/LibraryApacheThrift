package com.library.thrift.server.service;


import com.library.thrift.model.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getBookList();

    void changeBookInfo(Book book);

    void addBook(Book book);
}


