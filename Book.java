import java.util.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;  // By default, the book is available
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void displayBookDetails() {
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }
}

class Library {
    private List<Book> books;
    private Map<String, Book> bookByIsbn;

    public Library() {
        books = new ArrayList<>();
        bookByIsbn = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        bookByIsbn.put(book.getIsbn(), book);
    }

    public Book findBookByIsbn(String isbn) {
        return bookByIsbn.get(isbn);
    }

    public void displayAvailableBooks() {
        boolean found = false;
        for (Book book : books) {
            if (book.isAvailable()) {
                book.displayBookDetails();
                System.out.println("--------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available books in the library.");
        }
    }

    public void displayAllBooks() {
        for (Book book : books) {
            book.displayBookDetails();
            System.out.println("--------------------");
        }
    }
}

class Member {
    private String name;
    private String id;
    private List<Book> borrowedBooks;

    public Member(String name, String id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            borrowedBooks.add(book);
            System.out.println(name + " successfully borrowed: " + book.getTitle());
        } else {
            System.out.println("Sorry, " + book.getTitle() + " is currently not available.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.setAvailable(true);
            borrowedBooks.remove(book);
            System.out.println(name + " successfully returned: " + book.getTitle());
        } else {
            System.out.println(name + " did not borrow this book.");
        }
    }

    public void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println(name + " has no borrowed books.");
        } else {
            System.out.println(name + "'s borrowed books:");
            for (Book book : borrowedBooks) {
                System.out.println(book.getTitle());
            }
        }
    }
}

public class LibraryManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();
    private static Map<String, Member> members = new HashMap<>();
    private static Map<String, Member> loggedInMembers = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            switch (choice) {
                case 1:
                    registerMember();
                    break;
                case 2:
                    loginMember();
                    break;
                case 3:
                    addBook();
                    break;
                case 4:
                    displayAvailableBooks();
                    break;
                case 5:
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    viewBorrowedBooks();
                    break;
                case 8:
                    logoutMember();
                    break;
                case 9:
                    exit();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void displayMainMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Register Member");
        System.out.println("2. Login");
        System.out.println("3. Add Book");
        System.out.println("4. Display Available Books");
        System.out.println("5. Borrow Book");
        System.out.println("6. Return Book");
        System.out.println("7. View Borrowed Books");
        System.out.println("8. Logout");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    public static void registerMember() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member ID: ");
        String id = scanner.nextLine();

        if (members.containsKey(id)) {
            System.out.println("A member with this ID already exists.");
        } else {
            Member member = new Member(name, id);
            members.put(id, member);
            System.out.println("Member registered successfully.");
        }
    }

    public static void loginMember() {
        System.out.print("Enter member ID: ");
        String id = scanner.nextLine();

        if (members.containsKey(id)) {
            loggedInMembers.put(id, members.get(id));
            System.out.println("Logged in successfully as " + members.get(id).getName());
        } else {
            System.out.println("Invalid member ID.");
        }
    }

    public static void addBook() {
        if (loggedInMembers.isEmpty()) {
            System.out.println("Please login first to add a book.");
            return;
        }

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        Book book = new Book(title, author, isbn);
        library.addBook(book);
        System.out.println("Book added successfully to the library.");
    }

    public static void displayAvailableBooks() {
        if (loggedInMembers.isEmpty()) {
            System.out.println("Please login first to view available books.");
            return;
        }

        library.displayAvailableBooks();
    }

    public static void borrowBook() {
        if (loggedInMembers.isEmpty()) {
            System.out.println("Please login first to borrow a book.");
            return;
        }

        System.out.print("Enter ISBN of the book to borrow: ");
        String isbn = scanner.nextLine();
        Book book = library.findBookByIsbn(isbn);

        if (book != null) {
            loggedInMembers.values().iterator().next().borrowBook(book);
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    public static void returnBook() {
        if (loggedInMembers.isEmpty()) {
            System.out.println("Please login first to return a book.");
            return;
        }

        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();
        Book book = library.findBookByIsbn(isbn);

        if (book != null) {
            loggedInMembers.values().iterator().next().returnBook(book);
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    public static void viewBorrowedBooks() {
        if (loggedInMembers.isEmpty()) {
            System.out.println("Please login first to view borrowed books.");
            return;
        }

        loggedInMembers.values().iterator().next().viewBorrowedBooks();
    }

    public static void logoutMember() {
        if (loggedInMembers.isEmpty()) {
            System.out.println("No one is logged in.");
            return;
        }

        loggedInMembers.clear();
        System.out.println("Logged out successfully.");
    }

    public static void exit() {
        System.out.println("Exiting Library Management System...");
        scanner.close();
    }
}