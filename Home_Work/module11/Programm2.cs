using System;
using System.Collections.Generic;

// Интерфейс для поиска и отображения информации об отелях
public interface IHotelService
{
    List<string> SearchHotels(string location, string roomClass, decimal price);
    void AddHotel(string name, string location, string roomClass, decimal price);
}

// Интерфейс для бронирования отелей
public interface IBookingService
{
    bool BookRoom(string hotelName, string roomClass, DateTime startDate, DateTime endDate, string user);
    bool CheckAvailability(string hotelName, string roomClass, DateTime startDate, DateTime endDate);
}

// Интерфейс для обработки платежей
public interface IPaymentService
{
    bool ProcessPayment(decimal amount, string paymentMethod);
}

// Интерфейс для отправки уведомлений пользователям
public interface INotificationService
{
    void SendConfirmationNotification(string user);
    void SendReminderNotification(string user);
}

// Интерфейс для управления пользователями
public interface IUserManagementService
{
    void RegisterUser(string username, string password);
    bool AuthenticateUser(string username, string password);
}

// Реализация компонента HotelService
public class HotelService : IHotelService
{
    private List<string> hotels = new List<string>();

    public void AddHotel(string name, string location, string roomClass, decimal price)
    {
        hotels.Add($"{name} ({location}, {roomClass}, {price:C})");
    }

    public List<string> SearchHotels(string location, string roomClass, decimal price)
    {
        // Для простоты, будем выводить все отели (фильтрацию можно улучшить)
        return hotels;
    }
}

// Реализация компонента BookingService
public class BookingService : IBookingService
{
    private Dictionary<string, bool> bookings = new Dictionary<string, bool>();

    public bool BookRoom(string hotelName, string roomClass, DateTime startDate, DateTime endDate, string user)
    {
        string bookingKey = $"{hotelName}-{roomClass}-{startDate}-{endDate}";
        if (!bookings.ContainsKey(bookingKey))
        {
            bookings[bookingKey] = true;
            return true;
        }
        return false;
    }

    public bool CheckAvailability(string hotelName, string roomClass, DateTime startDate, DateTime endDate)
    {
        string bookingKey = $"{hotelName}-{roomClass}-{startDate}-{endDate}";
        return !bookings.ContainsKey(bookingKey);
    }
}

// Реализация компонента PaymentService
public class PaymentService : IPaymentService
{
    public bool ProcessPayment(decimal amount, string paymentMethod)
    {
        Console.WriteLine($"Оплата на сумму {amount:C} методом {paymentMethod} выполнена успешно.");
        return true;
    }
}

// Реализация компонента NotificationService
public class NotificationService : INotificationService
{
    public void SendConfirmationNotification(string user)
    {
        Console.WriteLine($"Уведомление для {user}: Бронирование подтверждено.");
    }

    public void SendReminderNotification(string user)
    {
        Console.WriteLine($"Уведомление для {user}: Напоминание о прибытии в отель.");
    }
}

// Реализация компонента UserManagementService
public class UserManagementService : IUserManagementService
{
    private Dictionary<string, string> users = new Dictionary<string, string>();

    public void RegisterUser(string username, string password)
    {
        users[username] = password;
    }

    public bool AuthenticateUser(string username, string password)
    {
        return users.ContainsKey(username) && users[username] == password;
    }
}

// Консольное приложение (UI-компонент)
public class Program
{
    static void Main()
    {
        // Создание сервисов
        IHotelService hotelService = new HotelService();
        IBookingService bookingService = new BookingService();
        IPaymentService paymentService = new PaymentService();
        INotificationService notificationService = new NotificationService();
        IUserManagementService userManagementService = new UserManagementService();

        // Регистрация пользователя
        userManagementService.RegisterUser("JohnDoe", "password123");

        // Авторизация пользователя
        bool isAuthenticated = userManagementService.AuthenticateUser("JohnDoe", "password123");
        if (!isAuthenticated)
        {
            Console.WriteLine("Ошибка авторизации.");
            return;
        }

        // Добавление отелей
        hotelService.AddHotel("Hotel A", "New York", "Deluxe", 200);
        hotelService.AddHotel("Hotel B", "Los Angeles", "Standard", 150);

        // Поиск отелей
        var hotels = hotelService.SearchHotels("New York", "Deluxe", 200);
        Console.WriteLine("Доступные отели:");
        foreach (var hotel in hotels)
        {
            Console.WriteLine(hotel);
        }

        // Проверка доступности и бронирование
        if (bookingService.CheckAvailability("Hotel A", "Deluxe", DateTime.Now, DateTime.Now.AddDays(1)))
        {
            if (bookingService.BookRoom("Hotel A", "Deluxe", DateTime.Now, DateTime.Now.AddDays(1), "JohnDoe"))
            {
                Console.WriteLine("Номер забронирован.");
                paymentService.ProcessPayment(200, "Карта");
                notificationService.SendConfirmationNotification("JohnDoe");
            }
        }
        else
        {
            Console.WriteLine("Номер не доступен.");
        }
    }
}
