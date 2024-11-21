import java.util.ArrayList;
import java.util.List;

// Класс Book (Книга)
class Book {
    String title;
    String author;
    String isbn;
    boolean isRented;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isRented = false;
    }

    public void rentBook() {
        isRented = true;
    }

    public void returnBook() {
        isRented = false;
    }

    public String getInfo() {
        return title + " автор: " + author + " (ISBN: " + isbn + ")";
    }

    public boolean isRented() {
        return isRented;
    }
}

// Класс Reader (Читатель)
class Reader {
    String name;
    List<Book> rentedBooks;

    public Reader(String name) {
        this.name = name;
        this.rentedBooks = new ArrayList<>();
    }

    public boolean rentBook(Book book) {
        if (rentedBooks.size() < 3 && !book.isRented()) {
            rentedBooks.add(book);
            book.rentBook();
            return true;
        }
        return false;
    }

    public void returnBook(Book book) {
        rentedBooks.remove(book);
        book.returnBook();
    }

    public void showRentedBooks() {
        System.out.println(name + " арендовала следующие книги:");
        for (Book book : rentedBooks) {
            System.out.println(book.getInfo());
        }
    }
}

// Класс Librarian (Библиотекарь)
class Librarian {
    String name;

    public Librarian(String name) {
        this.name = name;
    }

    public void manageBook(Library library, Book book) {
        if (book.isRented()) {
            System.out.println("Книга уже арендована: " + book.getInfo());
        } else {
            library.addBook(book);
            System.out.println("Книга добавлена в библиотеку: " + book.getInfo());
        }
    }
}

// Класс Library (Библиотека)
class Library {
    List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void showAvailableBooks() {
        System.out.println("Доступные книги в библиотеке:");
        for (Book book : books) {
            if (!book.isRented()) {
                System.out.println(book.getInfo());
            }
        }
    }

    public Book searchByTitle(String title) {
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public Book searchByAuthor(String author) {
        for (Book book : books) {
            if (book.author.equalsIgnoreCase(author)) {
                return book;
            }
        }
        return null;
    }
}

// Главный класс
public class Main {
    public static void main(String[] args) {
        // Создание библиотеки и книг
        Library library = new Library();
        Book book1 = new Book("Программирование на Java", "Джон Доу", "123456");
        Book book2 = new Book("Изучение Java", "Джейн Смит", "789101");
        library.addBook(book1);
        library.addBook(book2);

        // Создание читателя
        Reader reader = new Reader("Сандугаш");

        // Аренда книги
        library.showAvailableBooks();
        if (reader.rentBook(book1)) {
            System.out.println("Книга арендована: " + book1.getInfo());
        }
        library.showAvailableBooks();
        reader.showRentedBooks();

        // Возврат книги
        reader.returnBook(book1);
        System.out.println("Книга возвращена: " + book1.getInfo());
        library.showAvailableBooks();
        reader.showRentedBooks();

        // Поиск книги по названию
        Book foundBook = library.searchByTitle("Изучение Java");
        if (foundBook != null) {
            System.out.println("Найдена книга по названию: " + foundBook.getInfo());
        } else {
            System.out.println("Книга не найдена по названию.");
        }

        // Поиск книги по автору
        Book foundBookByAuthor = library.searchByAuthor("Джейн Смит");
        if (foundBookByAuthor != null) {
            System.out.println("Найдена книга автора Джейн Смит: " + foundBookByAuthor.getInfo());
        } else {
            System.out.println("Книги этого автора не найдены.");
        }
    }
}

