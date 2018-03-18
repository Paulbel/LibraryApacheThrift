package com.library.thrift.client.controller;

import com.library.thrift.BookService;
import com.library.thrift.model.Book;
import com.library.thrift.model.Person;
import org.apache.thrift.TException;

import java.util.List;

public class Controller {
    private final BookService.Client client;

    public Controller(BookService.Client client) {
        this.client = client;
    }

    public List<Book> getBookList() throws TException {
        client.getOutputProtocol().getTransport().open();
        List<Book> books = client.getBookList();
        client.getOutputProtocol().getTransport().close();
        return books;
    }

    public void changeBookInfo(Book book) throws TException {
        client.getOutputProtocol().getTransport().open();
        client.changeBookInfo(book);
        client.getOutputProtocol().getTransport().close();
    }

    public List<Person> getPersonList() throws TException {
        client.getOutputProtocol().getTransport().open();
        List <Person> personList = client.getPersonList();
        client.getOutputProtocol().getTransport().close();
        return personList;
    }

}
