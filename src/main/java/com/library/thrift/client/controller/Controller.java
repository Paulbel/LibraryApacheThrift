package com.library.thrift.client.controller;

import com.library.thrift.LibraryService;
import com.library.thrift.model.Book;
import com.library.thrift.model.Organisation;
import com.library.thrift.model.Person;
import org.apache.thrift.TException;

import java.util.List;

public class Controller {
    private final LibraryService.Client client;

    public Controller(LibraryService.Client client) {
        this.client = client;
    }

    public List<Book> getBookList() {
        try {
            client.getOutputProtocol().getTransport().open();
            List<Book> books = client.getBookList();
            client.getOutputProtocol().getTransport().close();
            return books;
        } catch (TException e) {
            throw new RuntimeException(e);
        }

    }

    public void changeBookInfo(Book book) {
        try {
            client.getOutputProtocol().getTransport().open();
            client.changeBookInfo(book);
            client.getOutputProtocol().getTransport().close();
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getPersonList() {
        try {
            client.getOutputProtocol().getTransport().open();
            List<Person> personList = client.getPersonList();
            client.getOutputProtocol().getTransport().close();
            return personList;
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Organisation> getOrganisationList() {
        try {
            client.getOutputProtocol().getTransport().open();
            List<Organisation> organisationList = client.getOrganisationList();
            client.getOutputProtocol().getTransport().close();
            return organisationList;
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book) {
        try {
            client.getOutputProtocol().getTransport().open();
            client.addBook(book);
            client.getOutputProtocol().getTransport().close();
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findBook(String name) {
        try {
            client.getOutputProtocol().getTransport().open();
            List<Book> bookList = client.findBook(name);
            client.getOutputProtocol().getTransport().close();
            return bookList;
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

}
