using System;
using System.Collections.Generic;
using System.Linq;

// Интерфейс для учета книг
public interface IAccountingSystem
{
    void RegisterLoan(string ticketNumber, Book book);
    void RegisterReturn(string ticketNumber, Book book);
    void ShowLoans();
}

// Интерфейс для работы с каталогом
public interface ICatalog
{
    void AddBook(Book book);
    List<Book> SearchBooks(string query);
    List<Book> FilterByGenre(string genre);
    List<Book> FilterByAuthor(string author);
}

// Класс "Кітап"
public class Book
{
    public string Title { get; set; }
    public string Author { get; set; }
    public string Genre { get; set; }
    public string ISBN { get; set; }
    public bool IsAvailable { get; set; } = true;

    public Book(string title, string author, string genre, string isbn)
    {
        Title = title;
        Author = author;
        Genre = genre;
        ISBN = isbn;
    }

    public override string ToString()
    {
        return $"Атауы: {Title}, Автор: {Author}, Жанры: {Genre}, ISBN: {ISBN}, Қол жетімді: {(IsAvailable ? "Иә" : "Жоқ")}";
    }
}

// Класс "Оқырман"
public class Reader
{
    public string FirstName { get; set; }
    public string LastName { get; set; }
    public string TicketNumber { get; set; }

    public Reader(string firstName, string lastName, string ticketNumber)
    {
        FirstName = firstName;
        LastName = lastName;
        TicketNumber = ticketNumber;
    }

    public override string ToString()
    {
        return $"Оқырман: {FirstName} {LastName}, Билет: {TicketNumber}";
    }
}

// Класс "Каталог"
public class Catalog : ICatalog
{
    private List<Book> books = new List<Book>();

    public void AddBook(Book book)
    {
        books.Add(book);
    }

    public List<Book> SearchBooks(string query)
    {
        return books.Where(b => b.Title.Contains(query, StringComparison.OrdinalIgnoreCase)).ToList();
    }

    public List<Book> FilterByGenre(string genre)
    {
        return books.Where(b => b.Genre.Equals(genre, StringComparison.OrdinalIgnoreCase)).ToList();
    }

    public List<Book> FilterByAuthor(string author)
    {
        return books.Where(b => b.Author.Equals(author, StringComparison.OrdinalIgnoreCase)).ToList();
    }

    public void ShowCatalog()
    {
        foreach (var book in books)
        {
            Console.WriteLine(book);
        }
    }
}

// Класс "Есеп жүйесі"
public class AccountingSystem : IAccountingSystem
{
    private List<(string TicketNumber, Book Book, DateTime LoanDate)> loans = new List<(string, Book, DateTime)>();

    public void RegisterLoan(string ticketNumber, Book book)
    {
        loans.Add((ticketNumber, book, DateTime.Now));
        Console.WriteLine($"Кітап '{book.Title}' оқырманға билет №{ticketNumber} арқылы берілді.");
    }

    public void RegisterReturn(string ticketNumber, Book book)
    {
        var loan = loans.FirstOrDefault(l => l.TicketNumber == ticketNumber && l.Book == book);
        if (loan.Book != null)
        {
            loans.Remove(loan);
            Console.WriteLine($"Кітап '{book.Title}' оқырманнан билет №{ticketNumber} арқылы қайтарылды.");
        }
        else
        {
            Console.WriteLine($"Қате: '{book.Title}' кітабы қайтарылған кітаптар тізімінен табылмады.");
        }
    }

    public void ShowLoans()
    {
        Console.WriteLine("\nҚазіргі берілген кітаптар:");
        foreach (var loan in loans)
        {
            Console.WriteLine($"Оқырман №{loan.TicketNumber}, Кітап: {loan.Book.Title}, Берілген күні: {loan.LoanDate}");
        }
    }
}

// Класс "Кітапханашы"
public class Librarian
{
    private ICatalog catalog;
    private IAccountingSystem accountingSystem;

    public Librarian(ICatalog catalog, IAccountingSystem accountingSystem)
    {
        this.catalog = catalog;
        this.accountingSystem = accountingSystem;
    }

    public void IssueBook(Reader reader, Book book)
    {
        if (book.IsAvailable)
        {
            book.IsAvailable = false;
            accountingSystem.RegisterLoan(reader.TicketNumber, book);
        }
        else
        {
            Console.WriteLine($"Кітап '{book.Title}' қол жетімді емес.");
        }
    }

    public void ReturnBook(Reader reader, Book book)
    {
        book.IsAvailable = true;
        accountingSystem.RegisterReturn(reader.TicketNumber, book);
    }
}

// Точка входа
public class Program
{
    public static void Main()
    {
        // Компоненттерді жасау
        var catalog = new Catalog();
        var accountingSystem = new AccountingSystem();
        var librarian = new Librarian(catalog, accountingSystem);

        catalog.AddBook(new Book("Абай жолы", "Мұхтар Әуезов", "Роман", "123456789"));
        catalog.AddBook(new Book("Бақытсыз Жамал", "Міржақып Дулатов", "Роман", "987654321"));
        catalog.AddBook(new Book("Қара сөздер", "Абай Құнанбаев", "Философия", "112233445"));

        // Оқырманды жасау
        var reader = new Reader("Әлия", "Серікқызы", "T123");

        // Каталогты көрсету
        Console.WriteLine("Кітапхана каталогы:");
        catalog.ShowCatalog();

        Console.WriteLine("\nКітапты беру:");
        librarian.IssueBook(reader, catalog.SearchBooks("Абай жолы")[0]);

        accountingSystem.ShowLoans();

        Console.WriteLine("\nКітапты қайтару:");
        librarian.ReturnBook(reader, catalog.SearchBooks("Абай жолы")[0]);
      
        Console.WriteLine("\nКітапхана каталогы:");
        catalog.ShowCatalog();
    }
}
