package com.library.thrift.server.service;

import com.library.thrift.model.Organisation;

import java.util.List;

public interface OrganisationDAO {
    List<Organisation> getOrganisationList();
}
