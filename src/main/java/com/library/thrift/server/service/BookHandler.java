package com.library.thrift.server.service;

import com.library.thrift.LibraryService;
import com.library.thrift.model.Book;
import com.library.thrift.model.Organisation;
import com.library.thrift.model.Person;
import org.apache.thrift.TException;

import java.util.List;

public class BookHandler implements LibraryService.Iface {
    private final static BookDAO bookDAO = new BookDAOImpl();
    private final static PersonDAO personDAO = new PersonDAOImpl();
    private final static OrganisationDAO organisationDAO = new OrganisationDAOImpl();

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

    @Override
    public List<Organisation> getOrganisationList() throws TException {
        return organisationDAO.getOrganisationList();
    }

    @Override
    public void addBook(Book book) throws TException {
        bookDAO.addBook(book);
    }
}
