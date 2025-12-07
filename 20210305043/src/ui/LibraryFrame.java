package ui;

import business.LibraryService;
import model.Book;
import model.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Single window: left = Books, right = small Members panel
public class LibraryFrame extends JFrame {

    private final LibraryService service = new LibraryService();

    private final DefaultTableModel bookTableModel;
    private final JTable bookTable;

    private final DefaultTableModel memberTableModel;
    private final JTable memberTable;

    public LibraryFrame() {
        setTitle("Library System");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Top title
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Center: split pane (Books | Members)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.7); // 70% books, 30% members
        add(splitPane, BorderLayout.CENTER);

        // LEFT: Books table
        JPanel booksPanel = new JPanel(new BorderLayout());
        JLabel booksLabel = new JLabel("Books", SwingConstants.LEFT);
        booksLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        booksPanel.add(booksLabel, BorderLayout.NORTH);

        bookTableModel = new DefaultTableModel(
                new Object[]{"ID", "Title", "Author", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        bookTable = new JTable(bookTableModel);
        booksPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        splitPane.setLeftComponent(booksPanel);

        // RIGHT: small Members panel
        JPanel membersPanel = new JPanel(new BorderLayout());
        membersPanel.setPreferredSize(new Dimension(220, 0));

        JLabel membersLabel = new JLabel("Members", SwingConstants.CENTER);
        membersLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        membersPanel.add(membersLabel, BorderLayout.NORTH);

        memberTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        memberTable = new JTable(memberTableModel);
        membersPanel.add(new JScrollPane(memberTable), BorderLayout.CENTER);

        JButton btnAddMember = new JButton("Add Member");
        membersPanel.add(btnAddMember, BorderLayout.SOUTH);

        splitPane.setRightComponent(membersPanel);

        // BOTTOM: book actions
        JPanel bottomPanel = new JPanel();
        JButton btnAddBook = new JButton("Add Book");
        JButton btnBorrow = new JButton("Borrow");
        JButton btnReturn = new JButton("Return");

        bottomPanel.add(btnAddBook);
        bottomPanel.add(btnBorrow);
        bottomPanel.add(btnReturn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        btnAddBook.addActionListener(e -> showAddBookDialog());
        btnAddMember.addActionListener(e -> showAddMemberDialog());
        btnBorrow.addActionListener(e -> showBorrowDialog());
        btnReturn.addActionListener(e -> returnSelectedBook());

        // Initial table data
        refreshBookTable();
        refreshMemberTable();
    }

    // --- Refresh tables ---

    private void refreshBookTable() {
        bookTableModel.setRowCount(0);
        for (Book b : service.getAllBooks()) {
            bookTableModel.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.isBorrowed() ? "Borrowed" : "Available"
            });
        }
    }

    private void refreshMemberTable() {
        memberTableModel.setRowCount(0);
        for (Member m : service.getAllMembers()) {
            memberTableModel.addRow(new Object[]{
                    m.getId(),
                    m.getName()
            });
        }
    }

    // --- Dialogs ---

    private void showAddBookDialog() {
        try {
            String idText = JOptionPane.showInputDialog(this, "Book ID:");
            if (idText == null) return;
            int id = Integer.parseInt(idText);

            String title = JOptionPane.showInputDialog(this, "Title:");
            if (title == null) return;

            String author = JOptionPane.showInputDialog(this, "Author:");
            if (author == null) return;

            service.addBook(id, title, author);
            refreshBookTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Book ID must be a number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddMemberDialog() {
        try {
            String idText = JOptionPane.showInputDialog(this, "Member ID:");
            if (idText == null) return;
            int id = Integer.parseInt(idText);

            String name = JOptionPane.showInputDialog(this, "Name:");
            if (name == null) return;

            service.addMember(id, name);
            refreshMemberTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Member ID must be a number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Integer getSelectedBookId() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book from the table.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (Integer) bookTableModel.getValueAt(row, 0);
    }

    private void showBorrowDialog() {
        Integer bookId = getSelectedBookId();
        if (bookId == null) return;

        String memberIdText = JOptionPane.showInputDialog(this, "Member ID to borrow:");
        if (memberIdText == null) return;

        try {
            int memberId = Integer.parseInt(memberIdText);
            service.borrowBook(bookId, memberId);
            refreshBookTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Member ID must be a number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnSelectedBook() {
        Integer bookId = getSelectedBookId();
        if (bookId == null) return;
        service.returnBook(bookId);
        refreshBookTable();
    }
}
