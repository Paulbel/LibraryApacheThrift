package com.library.thrift.client.view;

import com.library.thrift.client.controller.Controller;
import com.library.thrift.model.Book;
import com.library.thrift.model.Person;
import org.apache.thrift.TException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame {
    private final JFrame frame;
    private final Controller controller;
    private List<Book> bookList;
    private JList<Book> bookJList;
    private JPanel infoPanel;
    private JPanel editPanel;
    private int currentBookViewIndex;
    private JTabbedPane tabbedPane;

    public int getCurrentBookViewIndex() {
        return currentBookViewIndex;
    }

    public void setCurrentBookViewIndex(int currentBookViewIndex) {
        this.currentBookViewIndex = currentBookViewIndex;
    }

    public MainFrame(Controller controller) throws TException {
        this.currentBookViewIndex = 0;
        this.tabbedPane = new JTabbedPane();
        this.editPanel = new JPanel();

        //tabbedPane.addChangeListener(new BookTabbedPaneChangeListener(this));

        this.controller = controller;
        this.frame = new JFrame();
        this.bookList = controller.getBookList();
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel listPanel = new JPanel();
        //listPanel.setPreferredSize(new Dimension(200, 400));
        listPanel.setBackground(Color.BLUE);
        JPanel contentPanel = new JPanel();
        infoPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(tabbedPane, BorderLayout.NORTH);
        tabbedPane.addTab("tab1", infoPanel);
        tabbedPane.addTab("tab2", editPanel);
        //contentPanel.add(infoPanel, BorderLayout.CENTER);

        infoPanel.setLayout(new GridLayout(10, 1));
        infoPanel.setPreferredSize(new Dimension(400, 400));
        infoPanel.setBackground(Color.RED);
        bookJList = new JList<>();
        bookJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AbstractListModel abstractListModel = new BookListModel(bookList);

        bookJList.addListSelectionListener(new BookListSelectionListener(this));
        bookJList.setModel(abstractListModel);
        listPanel.add(bookJList);

        mainPanel.add(listPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        //mainPanel.add(tabbedPane, BorderLayout.NORTH);

        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }


    public JFrame getFrame() {
        return frame;
    }

    public Controller getController() {
        return controller;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public JList<Book> getBookJList() {
        return bookJList;
    }

    public void setBookJList(JList<Book> bookJList) {
        this.bookJList = bookJList;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(JPanel infoPanel) {
        this.infoPanel = infoPanel;
    }


    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public void revalidateView() {
        if (currentBookViewIndex != -1) {
            setBookView();
            setBookEdit();
        }
    }


    private void setBookView() {
        infoPanel.removeAll();

        Book currentBook = bookList.get(currentBookViewIndex);
        infoPanel.add(new JLabel("Название: " + currentBook.getName()));
        infoPanel.add(new JLabel("Автор: " + getNameSurname(currentBook.getAuthor())));

        infoPanel.add(new JLabel("Издательство: " + currentBook.getPublisher().getName() + ", тел.: " + currentBook.getPublisher().getPhone() + ", email: " + currentBook.getPublisher().getEmail()));
        infoPanel.add(new JLabel("Количество страниц: " + currentBook.getPageNumber()));
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void setBookEdit() {
        editPanel.removeAll();
        editPanel.setBackground(Color.GREEN);
        Book currentBook = bookList.get(currentBookViewIndex);

        editPanel.add(new JLabel("Название: "));
        JTextField nameField = new JTextField(currentBook.getName());
        editPanel.add(nameField);

        editPanel.add(new JLabel("Количество страниц: "));
        JTextField pageNumberField = new JTextField(String.valueOf(currentBook.getPageNumber()));
        editPanel.add(pageNumberField);

        editPanel.add(new JLabel("Автор: "));
        JButton editNameButton = new JButton("Изменить");
        editNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    currentBook.setName(nameField.getText());
                    currentBook.setPageNumber(Integer.parseInt(pageNumberField.getText()));
                    controller.changeBookInfo(currentBook);

                    setBookList(controller.getBookList());
                    bookJList.setModel(new BookListModel(bookList));
                } catch (TException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editPanel.add(editNameButton);

        try {
            List<Person> personList = controller.getPersonList();
            String[] names = new String[personList.size()];
            for (int index = 0; index < names.length; index++) {
                names[index] = getNameSurname(personList.get(index));
            }
            JComboBox comboBox = new JComboBox(names);


            editPanel.add(comboBox);
            editPanel.revalidate();
            editPanel.repaint();

        } catch (TException e) {
            e.printStackTrace();
        }
    }

    private String getNameSurname(Person person) {
        return person.getSurname() + " " + person.getName().substring(0, 1) + ".";
    }
}
