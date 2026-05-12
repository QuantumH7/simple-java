import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

// Book Class
class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isIssued;

    private int issueDay;
    private int dueDay;
    private int feePerDay = 10;

    private String issuedTo;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issueBook(String studentName, int currentDay) {
        isIssued = true;
        issuedTo = studentName;
        issueDay = currentDay;
        dueDay = currentDay + 7;
    }

    public int returnBook(int currentDay) {
        isIssued = false;

        int fee = 0;
        if (currentDay > dueDay) {
            int lateDays = currentDay - dueDay;
            fee = lateDays * feePerDay;
        }

        issuedTo = null;
        return fee;
    }

    public void displayBook() {
    String status;

    if (isIssued) {
        status = "Issued (" + issuedTo + ", Issued: " + issueDay + ", Due: " + dueDay + ")";
    } else {
        status = "Available";
        
    }

    System.out.printf("%-6s %-25s %-20s %-25s%n",
            "[" + bookId + "]",
            title,
            author,
            status);
}
}

// Person Class
class Person {
    protected String name;
    protected String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", ID: " + id);
    }
}
// Student Class
class Student extends Person {
    public Student(String name, String id) {
        super(name, id);
    }

    @Override
    public void displayInfo() {
        System.out.println("Student Name: " + name + ", Student ID: " + id);
    }
}

// Library Class
class Library {
    private ArrayList<Book> books = new ArrayList<>();
    public void sortBooksByTitle() {
    Collections.sort(books, new Comparator<Book>() {
        public int compare(Book b1, Book b2) {
            return b1.getTitle().compareToIgnoreCase(b2.getTitle());
        }
    });

    System.out.println("Books sorted by title.");
}

    public void addBook(Book book) {
        for (Book b : books) {
            if (b.getBookId().equals(book.getBookId())) {
                System.out.println("Book ID already exists.");
                return;
            }
        }
        books.add(book);
        System.out.println("Book added successfully.");
    }

    public void removeBook(String bookId) {
        boolean removed = books.removeIf(book -> book.getBookId().equals(bookId));
        if (removed)
            System.out.println("Book removed.");
        else
            System.out.println("Book not found.");
    }

    public void issueBook(String bookId, Student student, int currentDay) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId) && !book.isIssued()) {
                book.issueBook(student.name, currentDay);
                System.out.println("Book issued to " + student.name);
                return;
            }
        }
        System.out.println("Book not available.");
    }

    public void returnBook(String bookId, int currentDay) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId) && book.isIssued()) {
                int fee = book.returnBook(currentDay);

                if (fee > 0)
                    System.out.println("Late fee: " + fee + " birr");
                else
                    System.out.println("Returned on time.");

                return;
            }
        }
        System.out.println("Invalid return.");
    }

    public void displayBooks() {
    if (books.isEmpty()) {
        System.out.println("No books in library.");
        return;
    }

    // Header
    System.out.println("\n--- Book List ---");
    System.out.printf("%-6s %-25s %-20s %-25s%n",
            "ID", "Book Title", "Book Author", "Status");
    System.out.println("--------------------------------------------------------------");

    // Book rows
    for (Book book : books) {
        book.displayBook();

    }
}

    public void searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.displayBook();
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void showStatistics() {
        int issued = 0, available = 0;

        for (Book book : books) {
            if (book.isIssued())
                issued++;
            else
                available++;
        }

        System.out.println("Available Books: " + available);
        System.out.println("Issued Books: " + issued);
    }
}

// Main Class
public class LibrarySystem {

    // Safe integer input
    public static int getValidInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine(); // clear buffer
                return value;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // discard wrong input
            }
        }
    }

    // Safe string input (not empty)
    public static String getValidString(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n======================================");
            System.out.println("      LIBRARY MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Books");
            System.out.println("6. Search Book");
            System.out.println("7. Show Statistics");
            System.out.println("8. Sort Books");
            System.out.println("9. Exit");
            System.out.println("======================================");

            int choice = getValidInt(sc, ">>> Enter your choice:  ");

            switch (choice) {
                case 1:
                    String id = getValidString(sc, "Enter Book ID: ");
                    String title = getValidString(sc, "Enter Title: ");
                    String author = getValidString(sc, "Enter Author: ");
                    library.addBook(new Book(id, title, author));
                    break;

                case 2:
                    library.removeBook(getValidString(sc, "Enter Book ID: "));
                    break;

                case 3:
                    String issueId = getValidString(sc, "Enter Book ID: ");
                    String name = getValidString(sc, "Enter Student Name: ");
                    String studentId = getValidString(sc, "Enter Student ID: ");
                    int issueDay = getValidInt(sc, "Enter current day: ");

                    library.issueBook(issueId, new Student(name, studentId), issueDay);
                    break;

                case 4:
                    String returnId = getValidString(sc, "Enter Book ID: ");
                    int returnDay = getValidInt(sc, "Enter current day: ");

                    library.returnBook(returnId, returnDay);
                    break;

                case 5:
                    library.displayBooks();
                    break;

                case 6:
                    library.searchBook(getValidString(sc, "Enter Book Title: "));
                    break;

                case 7:
                    library.showStatistics();
                    break;

                case 8:
                    library.sortBooksByTitle();
                    break;

                case 9:
                System.out.println("Exiting program...");
                sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}