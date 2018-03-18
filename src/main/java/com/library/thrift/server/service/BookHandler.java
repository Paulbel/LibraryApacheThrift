package com.library.thrift.server.service;

import com.library.thrift.BookService;
import com.library.thrift.model.Book;
import com.library.thrift.model.Person;
import org.apache.thrift.TException;

import java.util.List;

public class BookHandler implements BookService.Iface {
    private final static BookDAO bookDAO = new BookDAOImpl();
    private final static PersonDAO personDAO = new PersonDAOImpl();

    @Override
    public List<Book> getBookList() throws TException {
        return bookDAO.getBookList();
    }

    @Override
    public void changeBookInfo(Book book) throws TException {
        bookDAO.changeBookInfo(book);
    }

    @Override
    public List<Person> getPersonList() throws TException {
        return personDAO.getPersonList();
    }
}
