using System;
using System.Collections.Generic;

// Абстрактный класс пользователя
public abstract class User
{
    public int Id { get; set; }
    public string Name { get; set; }
    public string Email { get; set; }

    protected User(int id, string name, string email)
    {
        Id = id;
        Name = name;
        Email = email;
    }

    public abstract void DisplayInfo();
}

// Читатель (Reader)
public class Reader : User
{
    public List<Loan> BorrowedBooks { get; private set; } = new List<Loan>();

    public Reader(int id, string name, string email) : base(id, name, email) { }

    public void BorrowBook(Book book, Librarian librarian)
    {
        if (book.AvailabilityStatus)
        {
            librarian.IssueLoan(this, book);
            Console.WriteLine($"{Name} кітапты алды: '{book.Title}'.");
        }
        else
        {
            Console.WriteLine($"Кітап қол жетімді емес: '{book.Title}'.");
        }
    }

    public override void DisplayInfo()
    {
        Console.WriteLine($"Оқырман: {Name}, Email: {Email}");
    }
}

// Библиотекарь (Librarian)
public class Librarian : User
{
    public Librarian(int id, string name, string email) : base(id, name, email) { }

    public void IssueLoan(Reader reader, Book book)
    {
        var loan = new Loan(book, reader, DateTime.Now);
        reader.BorrowedBooks.Add(loan);
        book.ChangeAvailabilityStatus(false);
    }

    public void ReturnBook(Reader reader, Book book)
    {
        var loan = reader.BorrowedBooks.Find(l => l.Book == book);
        if (loan != null)
        {
            reader.BorrowedBooks.Remove(loan);
            book.ChangeAvailabilityStatus(true);
            Console.WriteLine($"{reader.Name} кітапты қайтарды: '{book.Title}'.");
        }
    }

    public override void DisplayInfo()
    {
        Console.WriteLine($"Кітапханашы: {Name}, Email: {Email}");
    }
}

// Кітап (Book)
public class Book
{
    public string Title { get; set; }
    public string ISBN { get; set; }
    public string Author { get; set; }
    public int PublicationYear { get; set; }
    public bool AvailabilityStatus { get; private set; }

    public Book(string title, string isbn, string author, int year)
    {
        Title = title;
        ISBN = isbn;
        Author = author;
        PublicationYear = year;
        AvailabilityStatus = true;
    }

    public void ChangeAvailabilityStatus(bool status)
    {
        AvailabilityStatus = status;
    }

    public void GetBookInfo()
    {
        Console.WriteLine($"Кітап: {Title}, Автор: {Author}, Қолжетімді: {(AvailabilityStatus ? "Иә" : "Жоқ")}");
    }
}

// Операция выдачи книги (Loan)
public class Loan
{
    public Book Book { get; private set; }
    public Reader Reader { get; private set; }
    public DateTime LoanDate { get; private set; }
    public DateTime? ReturnDate { get; private set; }

    public Loan(Book book, Reader reader, DateTime loanDate)
    {
        Book = book;
        Reader = reader;
        LoanDate = loanDate;
    }

    public void ReturnBook()
    {
        ReturnDate = DateTime.Now;
    }
}

// Главный класс библиотеки
public class Library
{
    public List<Book> Books { get; private set; } = new List<Book>();
    public List<User> Users { get; private set; } = new List<User>();

    public void AddBook(Book book)
    {
        Books.Add(book);
    }

    public void AddUser(User user)
    {
        Users.Add(user);
    }

    public void DisplayBooks()
    {
        foreach (var book in Books)
        {
            book.GetBookInfo();
        }
    }
}

// Точка входа
public class Program
{
    public static void Main()
    {
        // Кітапхана, қолданушылар және кітаптарды жасау
        var library = new Library();
        var librarian = new Librarian(1, "Аяулым", "ayau@library.com");
        var reader = new Reader(2, "Ержан", "erzhan@library.com");

        library.AddUser(librarian);
        library.AddUser(reader);

        var book1 = new Book("Соғыс және бейбітшілік", "12345", "Лев Толстой", 1869);
        var book2 = new Book("Қылмыс пен жаза", "67890", "Федор Достоевский", 1866);

        library.AddBook(book1);
        library.AddBook(book2);

        // Кітаптар туралы ақпарат шығару
        Console.WriteLine("Кітапханадағы кітаптар:");
        library.DisplayBooks();

        // Оқырман кітап алады
        reader.BorrowBook(book1, librarian);

        // Жалға алғаннан кейінгі шығару
        Console.WriteLine("\nКітаптар тізімі (жалға алғаннан кейін):");
        library.DisplayBooks();

        // Оқырман кітапты қайтарып береді
        librarian.ReturnBook(reader, book1);

        // Қайтарғаннан кейінгі шығару
        Console.WriteLine("\nКітаптар тізімі (қайтарғаннан кейін):");
        library.DisplayBooks();
    }
}
