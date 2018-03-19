package com.library.thrift.server.service;

import com.library.thrift.model.Organisation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrganisationDAOImpl implements OrganisationDAO {
    private final static String GET_ORGANISATION_LIST = "SELECT * FROM organisation";

    @Override
    public List<Organisation> getOrganisationList() {
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            List<Organisation> organisationList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(GET_ORGANISATION_LIST);
            while (resultSet.next()) {
                organisationList.add(createOrganisationFromResultSet(resultSet));
            }
            return organisationList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Organisation createOrganisationFromResultSet(ResultSet resultSet) throws SQLException {
        Organisation organisation = new Organisation();
        organisation.setId(resultSet.getInt("organisation.id"));
        organisation.setName(resultSet.getString("organisation.name"));
        organisation.setEmail(resultSet.getString("organisation.email"));
        organisation.setPhone(resultSet.getString("organisation.phone"));
        return organisation;
    }
}
