package com.library.thrift.client.controller;

import com.library.thrift.BookService;
import com.library.thrift.client.view.MainFrame;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class JavaClient {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please enter 'simple' or 'secure'");
            System.exit(0);
        }
        try {
            TTransport transport;
            transport = new TSocket("localhost", 9090);

            TProtocol protocol = new TBinaryProtocol(transport);
            BookService.Client client = new BookService.Client(protocol);

            Controller controller = new Controller(client);

            MainFrame mainFrame = new MainFrame(controller);
        } catch (TException e) {
            e.printStackTrace();
        }

    }


}