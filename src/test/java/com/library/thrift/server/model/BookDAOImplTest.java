package com.library.thrift.server.model;

import com.library.thrift.server.service.BookDAOImpl;
import org.junit.Test;

public class BookDAOImplTest {
    @Test
    public void getBookList() throws Exception {
        System.out.println(new BookDAOImpl().getBookList());
    }

}