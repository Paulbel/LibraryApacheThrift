package com.library.thrift.client.view;

import com.library.thrift.client.controller.Controller;
import com.library.thrift.model.Book;
import com.library.thrift.model.Organisation;
import com.library.thrift.model.Person;
import org.apache.thrift.TException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame {
    private JFrame frame;
    private Controller controller;
    private List<Book> bookList;
    private List<Person> personList;
    private List<Organisation> organisationList;
    private JList<Book> bookJList;
    private JPanel infoPanel;
    private JPanel editPanel;
    private JPanel addPanel;
    private JPanel listPanel;
    private JPanel searchPanel;
    private JTabbedPane tabbedPane;


    public MainFrame(Controller controller) throws TException {
        this.controller = controller;

        frame = new JFrame();

        tabbedPane = new JTabbedPane();
        searchPanel = new JPanel();
        editPanel = new JPanel();
        infoPanel = new JPanel();
        addPanel = new JPanel();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bookList = controller.getBookList();
        JPanel mainPanel = new JPanel(new BorderLayout());
        listPanel = new JPanel();
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(tabbedPane, BorderLayout.NORTH);

        tabbedPane.addTab("Просмотр", infoPanel);
        tabbedPane.addTab("Редактирование", editPanel);
        tabbedPane.addTab("Добавление", addPanel);
        tabbedPane.addTab("Поиск", searchPanel);

        //contentPanel.add(infoPanel, BorderLayout.CENTER);


        infoPanel.setPreferredSize(new Dimension(400, 400));
        bookJList = new JList<>();
        bookJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AbstractListModel abstractListModel = new BookListModel(bookList);

        bookJList.addListSelectionListener(new BookListSelectionListener(this));
        bookJList.setModel(abstractListModel);
        listPanel.add(bookJList);

        mainPanel.add(listPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

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
        bookList = controller.getBookList();
        organisationList = controller.getOrganisationList();
        personList = controller.getPersonList();
        int currentIndex = bookJList.getSelectedIndex();
        setBookAddPanel();
        setBookSearchPanel();
        if (currentIndex > -1) {
            setBookViewInfoPanel(currentIndex);
            setBookEditPanel(currentIndex);
        }
        frame.revalidate();
        frame.repaint();
    }

    private void setBookAddPanel() {
        addPanel.removeAll();
        addPanel.setLayout(new GridLayout(5,2));
        addPanel.add(new JLabel("Название: "));
        JTextField nameTextField = new JTextField(20);
        addPanel.add(nameTextField);
        addPanel.add(new JLabel("Кол-во страниц: "));

        JTextField pageNumberTextField = new JTextField(20);
        addPanel.add(pageNumberTextField);

        String[] organisationNames = new String[organisationList.size()];
        for (int index = 0; index < organisationList.size(); index++) {
            organisationNames[index] = organisationList.get(index).getName();
        }
        JComboBox organisationComboBox = new JComboBox(organisationNames);
        addPanel.add(new JLabel("Издательство: "));

        addPanel.add(organisationComboBox);

        String[] names = new String[personList.size()];
        for (int index = 0; index < names.length; index++) {
            names[index] = getNameSurname(personList.get(index));
        }
        MainFrame mainFrame = this;
        JComboBox authorComboBox = new JComboBox(names);
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = new Book();
                book.setName(nameTextField.getText());
                book.setPageNumber(Integer.parseInt(pageNumberTextField.getText()));
                book.setAuthor(personList.get(authorComboBox.getSelectedIndex()));
                book.setPublisher(organisationList.get(organisationComboBox.getSelectedIndex()));
                controller.addBook(book);
                mainFrame.revalidateView();
                revalidateList();
            }
        });
        addPanel.add(new JLabel("Автор: "));

        addPanel.add(authorComboBox);
        addPanel.add(addButton);
    }

    private void setBookSearchPanel() {
        searchPanel.removeAll();
        searchPanel.setLayout(new BorderLayout());
        JTextField nameTextField = new JTextField(20);
        JPanel fieldPanel = new JPanel();
        fieldPanel.add(nameTextField);
        JPanel resultPanel = new JPanel();
        JButton findButton = new JButton("Найти");
        fieldPanel.add(findButton);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Book> bookList = controller.findBook(nameTextField.getText());
                JList list = new JList(new BookListModel(bookList));
                resultPanel.removeAll();
                resultPanel.add(list);
                frame.revalidate();
                frame.repaint();
            }
        });
        searchPanel.add(fieldPanel, BorderLayout.NORTH);
        searchPanel.add(resultPanel, BorderLayout.CENTER);

    }

    private void setBookViewInfoPanel(int bookIndex) {
        infoPanel.removeAll();
        infoPanel.setPreferredSize(new Dimension(200,200));
        infoPanel.setLayout(new GridLayout(4, 2));
        Book currentBook = bookList.get(bookIndex);
        infoPanel.add(new JLabel("Название: "));
        infoPanel.add(new JLabel(currentBook.getName()));

        infoPanel.add(new JLabel("Автор: "));
        infoPanel.add(new JLabel(getNameSurname(currentBook.getAuthor())));

        infoPanel.add(new JLabel("Издательство: "));
        infoPanel.add(new JLabel(currentBook.getPublisher().getName() +
                ", тел.: " + currentBook.getPublisher().getPhone() +
                ", email: " + currentBook.getPublisher().getEmail()));
        infoPanel.add(new JLabel("Количество страниц: "));
        infoPanel.add(new JLabel(String.valueOf(currentBook.getPageNumber())));

    }

    private void setBookEditPanel(int bookIndex) {
        editPanel.removeAll();
        editPanel.setLayout(new GridLayout(5,2));
        Book currentBook = bookList.get(bookIndex);

        editPanel.add(new JLabel("Название: "));
        JTextField nameField = new JTextField(currentBook.getName());
        editPanel.add(nameField);

        editPanel.add(new JLabel("Количество страниц: "));
        JTextField pageNumberField = new JTextField(String.valueOf(currentBook.getPageNumber()));
        editPanel.add(pageNumberField);

        editPanel.add(new JLabel("Автор: "));


        String[] organisationNames = new String[organisationList.size()];
        for (int index = 0; index < organisationList.size(); index++) {
            organisationNames[index] = organisationList.get(index).getName();
        }
        JComboBox organisationComboBox = new JComboBox(organisationNames);
        organisationComboBox.setSelectedIndex(organisationList.indexOf(currentBook.getPublisher()));
        String[] names = new String[personList.size()];
        for (int index = 0; index < names.length; index++) {
            names[index] = getNameSurname(personList.get(index));
        }

        JComboBox authorComboBox = new JComboBox(names);
        authorComboBox.setSelectedIndex(personList.indexOf(currentBook.getAuthor()));

        editPanel.add(authorComboBox);
        editPanel.add(new JLabel("Организация: "));

        editPanel.add(organisationComboBox);
        JButton editButton = new JButton("Изменить");

        editPanel.add(editButton);
        editButton.addActionListener(e -> {
            int currentBookIndex = bookJList.getSelectedIndex();
            Book currentBook1 = bookList.get(currentBookIndex);

            currentBook1.setName(nameField.getText());
            currentBook1.setAuthor(personList.get(authorComboBox.getSelectedIndex()));
            currentBook1.setPublisher(organisationList.get(organisationComboBox.getSelectedIndex()));
            currentBook1.setPageNumber(Integer.parseInt(pageNumberField.getText()));
            controller.changeBookInfo(currentBook1);
            revalidateList();
            bookJList.setSelectedIndex(currentBookIndex);
        });
    }

    private void revalidateList() {
        setBookList(controller.getBookList());
        int currentIndex = bookJList.getSelectedIndex();
        listPanel.remove(bookJList);
        bookJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AbstractListModel abstractListModel = new BookListModel(bookList);

        bookJList.addListSelectionListener(new BookListSelectionListener(this));
        bookJList.setModel(abstractListModel);
        listPanel.add(bookJList);
        bookJList.setSelectedIndex(currentIndex);
        frame.revalidate();
        frame.repaint();
    }

    private String getNameSurname(Person person) {
        return person.getSurname() + " " + person.getName().substring(0, 1) + ".";
    }
}
