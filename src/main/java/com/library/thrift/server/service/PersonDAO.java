package com.library.thrift.server.service;


import com.library.thrift.model.Person;

import java.util.List;

public interface PersonDAO {
    List<Person> getPersonList();
}
