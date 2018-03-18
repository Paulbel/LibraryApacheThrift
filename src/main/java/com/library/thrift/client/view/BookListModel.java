package com.library.thrift.client.view;


import com.library.thrift.model.Book;

import javax.swing.*;
import java.util.List;

public class BookListModel extends AbstractListModel {
    private List<Book> bookList;

    public BookListModel(List<Book> bookList) {
        this.bookList = bookList;
    }


    @Override
    public int getSize() {
        return bookList.size();
    }

    @Override
    public String getElementAt(int index) {
        Book book = bookList.get(index);
        return book.getName();
    }
}
