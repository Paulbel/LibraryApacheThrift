package com.library.thrift.server.service;

import com.library.thrift.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {
    private final static String GET_PERSON_LIST = "SELECT * FROM author";

    @Override
    public List<Person> getPersonList() {
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            List<Person> personList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(GET_PERSON_LIST);
            while (resultSet.next()) {
                personList.add(createPersonFromResultSet(resultSet));
            }
            return personList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Person createPersonFromResultSet(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setName(resultSet.getString("author.name"));
        person.setSurname(resultSet.getString("author.surname"));
        person.setId(resultSet.getInt("author.id"));
        return person;
    }
}
