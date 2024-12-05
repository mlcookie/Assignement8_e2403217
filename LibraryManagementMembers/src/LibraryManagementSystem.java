import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Abstract class LibraryItem
abstract class LibraryItem {
    private String id;
    protected boolean availability;
    protected LocalDate dueToDate;

    public LibraryItem(String id) {
        this.id = id;
        this.availability = true;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return availability;
    }

    public abstract void borrowItem();

    public void returnItem() {
        this.availability = true;
        this.dueToDate = null;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Available: " + availability +
                (dueToDate != null ? ", Due Date: " + dueToDate : "");
    }
}

// Class Book
class Book extends LibraryItem {
    private String title;

    public Book(String id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void borrowItem() {
        if (isAvailable()) {
            availability = false;
            dueToDate = LocalDate.now().plusWeeks(4); // Due in 4 weeks
        }
    }

    @Override
    public String toString() {
        return "Book - " + title + ", " + super.toString();
    }
}

// Class Magazine
class Magazine extends LibraryItem {
    private String issue;

    public Magazine(String id, String issue) {
        super(id);
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    @Override
    public void borrowItem() {
        if (isAvailable()) {
            availability = false;
            dueToDate = LocalDate.now().plusWeeks(2); // Due in 2 weeks
        }
    }

    @Override
    public String toString() {
        return "Magazine - Issue: " + issue + ", " + super.toString();
    }
}

// Interface User
interface User {
    String getName();
    int getBorrowingLimit();
    int getBorrowedItemCount();
    void borrowItem(LibraryItem item);
    void returnItem(LibraryItem item);
}

// FacultyMember class
class FacultyMember implements User {
    private String name;
    private List<LibraryItem> borrowedItems;

    public FacultyMember(String name) {
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBorrowingLimit() {
        return 5;
    }

    @Override
    public int getBorrowedItemCount() {
        return borrowedItems.size();
    }

    @Override
    public void borrowItem(LibraryItem item) {
        if (borrowedItems.size() < getBorrowingLimit() && item.isAvailable()) {
            borrowedItems.add(item);
            item.borrowItem();
        } else {
            JOptionPane.showMessageDialog(null, "Borrowing limit reached or item unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}

// StudentMember class
class StudentMember implements User {
    private String name;
    private List<LibraryItem> borrowedItems;

    public StudentMember(String name) {
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBorrowingLimit() {
        return 3;
    }

    @Override
    public int getBorrowedItemCount() {
        return borrowedItems.size();
    }

    @Override
    public void borrowItem(LibraryItem item) {
        if (borrowedItems.size() < getBorrowingLimit() && item.isAvailable()) {
            borrowedItems.add(item);
            item.borrowItem();
        } else {
            JOptionPane.showMessageDialog(null, "Borrowing limit reached or item unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}

// GuestMember class
class GuestMember implements User {
    private String name;
    private List<LibraryItem> borrowedItems;

    public GuestMember(String name) {
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBorrowingLimit() {
        return 1;
    }

    @Override
    public int getBorrowedItemCount() {
        return borrowedItems.size();
    }

    @Override
    public void borrowItem(LibraryItem item) {
        if (borrowedItems.size() < getBorrowingLimit() && item.isAvailable()) {
            borrowedItems.add(item);
            item.borrowItem();
        } else {
            JOptionPane.showMessageDialog(null, "Borrowing limit reached or item unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
        item.returnItem();
    }
}

// Class LibraryManager
class LibraryManager {
    private List<LibraryItem> libraryItems = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public void addLibraryItem(LibraryItem item) {
        libraryItems.add(item);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public LibraryItem findItemById(String id) {
        for (LibraryItem item : libraryItems) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }
}

// Main Class with Swing GUI
public class LibraryManagementSystem {
    private static LibraryManager libraryManager = new LibraryManager();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createMainMenu());
    }

    private static void createMainMenu() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton viewItemsButton = new JButton("View Library Items");
        JButton borrowItemButton = new JButton("Borrow Item");
        JButton returnItemButton = new JButton("Return Item");
        JButton addBookButton = new JButton("Add Book");
        JButton addMagazineButton = new JButton("Add Magazine");
        JButton addUserButton = new JButton("Add User");
        JButton exitButton = new JButton("Exit");

        // Action Listeners
        viewItemsButton.addActionListener(e -> viewLibraryItems());
        borrowItemButton.addActionListener(e -> borrowLibraryItem());
        returnItemButton.addActionListener(e -> returnLibraryItem());
        addBookButton.addActionListener(e -> addBook());
        addMagazineButton.addActionListener(e -> addMagazine());
        addUserButton.addActionListener(e -> addUser());
        exitButton.addActionListener(e -> System.exit(0));

        // Layout
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.add(viewItemsButton);
        panel.add(borrowItemButton);
        panel.add(returnItemButton);
        panel.add(addBookButton);
        panel.add(addMagazineButton);
        panel.add(addUserButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void viewLibraryItems() {
        List<LibraryItem> items = libraryManager.getLibraryItems();
        StringBuilder sb = new StringBuilder();
        for (LibraryItem item : items) {
            sb.append(item).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No items in the library.", "Library Items", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void borrowLibraryItem() {
        String id = JOptionPane.showInputDialog("Enter the ID of the item to borrow:");
        LibraryItem item = libraryManager.findItemById(id);

        if (item != null) {
            if (item.isAvailable()) {
                item.borrowItem();
                JOptionPane.showMessageDialog(null, "Item borrowed successfully. Due Date: " + item.dueToDate, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Item is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void returnLibraryItem() {
        String id = JOptionPane.showInputDialog("Enter the ID of the item to return:");
        LibraryItem item = libraryManager.findItemById(id);

        if (item != null) {
            if (!item.isAvailable()) {
                item.returnItem();
                JOptionPane.showMessageDialog(null, "Item returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Item is already available.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void addBook() {
        String id = JOptionPane.showInputDialog("Enter Book ID:");
        String title = JOptionPane.showInputDialog("Enter Book Title:");
        libraryManager.addLibraryItem(new Book(id, title));
        JOptionPane.showMessageDialog(null, "Book added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void addMagazine() {
        String id = JOptionPane.showInputDialog("Enter Magazine ID:");
        String issue = JOptionPane.showInputDialog("Enter Magazine Issue:");
        libraryManager.addLibraryItem(new Magazine(id, issue));
        JOptionPane.showMessageDialog(null, "Magazine added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void addUser() {
        String name = JOptionPane.showInputDialog("Enter User Name:");
        String userType = JOptionPane.showInputDialog("Enter User Type (Faculty/Student/Guest):");

        User user;
        if ("Faculty".equalsIgnoreCase(userType)) {
            user = new FacultyMember(name);
        } else if ("Student".equalsIgnoreCase(userType)) {
            user = new StudentMember(name);
        } else if ("Guest".equalsIgnoreCase(userType)) {
            user = new GuestMember(name);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid user type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        libraryManager.addUser(user);
        JOptionPane.showMessageDialog(null, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
