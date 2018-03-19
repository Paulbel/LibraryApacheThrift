package com.library.thrift.server.service;


import com.library.thrift.model.Book;
import com.library.thrift.model.Organisation;
import com.library.thrift.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    private final static String ADD_BOOK_QUERY = "INSERT INTO book (name, author, publisher, page_number) VALUES (?,?,?,?);";
    private final static String GET_BOOK_LIST =
            "SELECT * FROM author " +
                    "INNER JOIN book ON author.id= book.author INNER JOIN organisation ON book.publisher = organisation.id;";

    private final static String CHANGE_BOOK_INFO = "UPDATE book SET name = ?, page_number =?, publisher = ?, author = ? WHERE id = ?;";

    @Override
    public List<Book> getBookList() {
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            List<Book> bookList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(GET_BOOK_LIST);
            while (resultSet.next()) {
                bookList.add(createBookFromResultSet(resultSet));
            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeBookInfo(Book book) {
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_BOOK_INFO)) {
            statement.setString(1, book.getName());
            statement.setInt(2, book.getPageNumber());
            statement.setInt(3, book.getPublisher().getId());
            statement.setInt(4, book.getAuthor().getId());
            statement.setInt(5, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBook(Book book) {
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOK_QUERY)) {
            statement.setString(1, book.getName());
            statement.setInt(2, book.getPageNumber());
            statement.setInt(3, book.getPublisher().getId());
            statement.setInt(4, book.getAuthor().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("book.id"));
        book.setName(resultSet.getString("book.name"));
        book.setPageNumber(resultSet.getInt("book.page_number"));

        Person author = new Person();
        author.setId(resultSet.getInt("author.id"));
        author.setName(resultSet.getString("author.name"));
        author.setSurname(resultSet.getString("author.surname"));
        book.setAuthor(author);

        Organisation organisation = new Organisation();
        organisation.setId(resultSet.getInt("organisation.id"));
        organisation.setName(resultSet.getString("organisation.name"));
        organisation.setEmail(resultSet.getString("organisation.email"));
        organisation.setPhone(resultSet.getString("organisation.phone"));
        book.setPublisher(organisation);
        return book;
    }

}
