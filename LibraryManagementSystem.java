import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String author;
    boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }
}

class Member {
    static int memberCount = 0;
    int id;
    String name;

    public Member(String name) {
        this.id = ++memberCount;
        this.name = name;
    }
}

public class LibraryManagementSystem {
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    Queue<Integer> bookQueue = new LinkedList<>();

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Library Management System Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("6. View Members");
            System.out.println("7. Display Borrowed Books");
            System.out.println("8. Display Borrowed Books Queue");
            System.out.println("9. View Book Details");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    addMember(scanner);
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    displayBooks();
                    break;
                case 6:
                    displayMembers();
                    break;
                case 7:
                    displayBorrowedBooks();
                    break;
                case 8:
                    displayBorrowedBooksQueue();
                    break;
                case 9:
                    viewBookDetails(scanner);
                    break;
                case 10:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private int bookIdCounter = 0;

    private void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        int bookId = ++bookIdCounter;
        Book book = new Book(bookId, title, author);
        books.add(book);

        System.out.println("\nBook added successfully with ID: B" + String.format("%04d", bookId));
    }

    private void addMember(Scanner scanner) {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        Member member = new Member(name);
        members.add(member);

        System.out.println("\nMember added successfully with ID: " + member.id);
    }

    private void displayMembers() {
        System.out.println("\n=== List of Members ===");
        System.out.printf("%-5s | %-20s%n", "ID", "Name");
        System.out.println("------------------------");
        for (Member member : members) {
            System.out.printf("%-5d | %-20s%n", member.id, member.name);
        }
    }

    private void displayBooks() {
        System.out.println("\n=== List of Books ===");
        System.out.printf("%-5s | %-25s | %-20s | %-10s%n", "ID", "Title", "Author", "Available");
        System.out.println("------------------------------------------------------------");
        for (Book book : books) {
            System.out.printf("%-5d | %-25s | %-20s | %-10s%n",
                    book.id, book.title, book.author, (book.available ? "Yes" : "No"));
        }
    }

    private void borrowBook(Scanner scanner) {
        if (members.isEmpty() || books.isEmpty()) {
            System.out.println("\nNo members or books available.");
            return;
        }

        System.out.print("Enter member ID: ");
        int memberId = scanner.nextInt();

        if (memberId < 1 || memberId > members.size()) {
            System.out.println("\nInvalid member ID.");
            return;
        }

        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();

        if (bookId < 1 || bookId > books.size()) {
            System.out.println("\nInvalid book ID.");
            return;
        }

        if (!books.get(bookId - 1).available) {
            System.out.println("\nThis book is not available.");
            return;
        }
        books.get(bookId - 1).available = false;
        bookQueue.add(bookId);

        System.out.println("\nBook borrowed successfully.");
    }

    private void returnBook(Scanner scanner) {
        System.out.print("Enter the ID of the book you want to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        boolean bookFound = false;

        for (Book book : books) {
            if (book.id == bookId && !book.available) {
                book.available = true;
                bookQueue.remove(book.id);
                bookFound = true;
                System.out.println("\nBook with ID " + bookId + " has been successfully returned.");
                break;
            }
        }

        if (!bookFound) {
            System.out.println("\nBook with ID " + bookId + " not found or already returned.");
        }
    }

    private void displayBorrowedBooks() {
        System.out.println("\n=== List of Borrowed Books ===");
        System.out.printf("%-5s | %-25s | %-20s%n", "ID", "Title", "Author");
        System.out.println("------------------------------------------------------------");
        for (int bookId : bookQueue) {
            Book book = books.get(bookId - 1);
            System.out.printf("%-5d | %-25s | %-20s%n", book.id, book.title, book.author);
        }
    }

    private void displayBorrowedBooksQueue() {
        System.out.println("\n=== Borrowed Books Queue ===");
        System.out.println("Book ID");
        System.out.println("--------");
        for (int bookId : bookQueue) {
            System.out.println(String.format("%-8d", bookId));
        }
    }

    private void viewBookDetails(Scanner scanner) {
        System.out.print("Enter the ID of the book you want to view details for: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        if (bookId >= 1 && bookId <= books.size()) {
            Book book = books.get(bookId - 1);
            System.out.println("\n=== Book Details ===");
            System.out.println("ID: " + book.id);
            System.out.println("Title: " + book.title);
            System.out.println("Author: " + book.author);
            System.out.println("Available: " + (book.available ? "Yes" : "No"));
        } else {
            System.out.println("\nInvalid book ID.");
        }
    }
}
