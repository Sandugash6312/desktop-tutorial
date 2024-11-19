// Подсистема бронирования номеров
class RoomBookingSystem {
    public void bookRoom(String guestName) {
        System.out.println("Номер забронирован для " + guestName);
    }

    public void cancelBooking(String guestName) {
        System.out.println("Бронирование номера для " + guestName + " отменено");
    }

    public boolean isRoomAvailable() {
        System.out.println("Проверка доступности номера...");
        return true; // Для упрощения всегда доступен
    }
}

// Подсистема ресторана
class RestaurantSystem {
    public void reserveTable(String guestName) {
        System.out.println("Столик забронирован для " + guestName);
    }

    public void orderFood(String guestName, String food) {
        System.out.println("Еда '" + food + "' заказана для " + guestName);
    }
}

// Подсистема мероприятий
class EventManagementSystem {
    public void bookEventHall(String eventName) {
        System.out.println("Зал забронирован для мероприятия: " + eventName);
    }

    public void orderEquipment(String equipment) {
        System.out.println("Оборудование '" + equipment + "' заказано для мероприятия");
    }
}

// Подсистема службы уборки
class CleaningService {
    public void scheduleCleaning(String room) {
        System.out.println("Уборка запланирована для комнаты: " + room);
    }

    public void requestCleaning(String room) {
        System.out.println("Запрос на уборку комнаты: " + room);
    }
}

// Фасад для управления гостиницей
class HotelFacade {
    private RoomBookingSystem roomBooking;
    private RestaurantSystem restaurant;
    private EventManagementSystem eventManagement;
    private CleaningService cleaningService;

    public HotelFacade() {
        this.roomBooking = new RoomBookingSystem();
        this.restaurant = new RestaurantSystem();
        this.eventManagement = new EventManagementSystem();
        this.cleaningService = new CleaningService();
    }

    // Бронирование номера с заказом еды и уборкой
    public void bookRoomWithServices(String guestName, String food) {
        if (roomBooking.isRoomAvailable()) {
            roomBooking.bookRoom(guestName);
            restaurant.orderFood(guestName, food);
            cleaningService.scheduleCleaning("номер " + guestName);
        } else {
            System.out.println("Извините, номер недоступен для бронирования");
        }
    }

    // Организация мероприятия с бронированием номеров и оборудования
    public void organizeEvent(String eventName, String equipment) {
        eventManagement.bookEventHall(eventName);
        eventManagement.orderEquipment(equipment);
        roomBooking.bookRoom("Гости мероприятия " + eventName);
    }

    // Бронирование стола в ресторане с вызовом такси
    public void reserveRestaurantWithTaxi(String guestName) {
        restaurant.reserveTable(guestName);
        System.out.println("Такси вызвано для гостя: " + guestName);
    }

    // Дополнительные методы фасада
    public void cancelRoomBooking(String guestName) {
        roomBooking.cancelBooking(guestName);
    }

    public void requestRoomCleaning(String room) {
        cleaningService.requestCleaning(room);
    }
}

// Основной класс для демонстрации работы
public class Main {
    public static void main(String[] args) {
        HotelFacade hotelFacade = new HotelFacade();

        // Сценарий 1: Бронирование номера с услугами ресторана и уборки
        System.out.println("Сценарий 1: Бронирование номера с услугами ресторана и уборки");
        hotelFacade.bookRoomWithServices("Иван Иванов", "Паста");

        // Сценарий 2: Организация мероприятия с бронированием оборудования и номеров
        System.out.println("\nСценарий 2: Организация мероприятия с бронированием оборудования и номеров");
        hotelFacade.organizeEvent("Конференция IT", "Проектор");

        // Сценарий 3: Бронирование стола в ресторане с вызовом такси
        System.out.println("\nСценарий 3: Бронирование стола в ресторане с вызовом такси");
        hotelFacade.reserveRestaurantWithTaxi("Андрей Петров");

        // Сценарий 4: Отмена бронирования и запрос на уборку
        System.out.println("\nСценарий 4: Отмена бронирования и запрос на уборку");
        hotelFacade.cancelRoomBooking("Иван Иванов");
        hotelFacade.requestRoomCleaning("номер 101");
    }
}
