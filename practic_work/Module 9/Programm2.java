import java.util.HashMap;
import java.util.Map;

// Главный класс
public class Main {

    public static void main(String[] args) {
        DeliveryServiceFactory factory = new DeliveryServiceFactory();

        // Выбор службы доставки
        IInternalDeliveryService deliveryService = factory.getDeliveryService("ExternalA");
        deliveryService.deliverOrder("ORDER123");
        System.out.println(deliveryService.getDeliveryStatus("ORDER123"));

        // Использование другой службы
        deliveryService = factory.getDeliveryService("ExternalB");
        deliveryService.deliverOrder("ORDER456");
        System.out.println(deliveryService.getDeliveryStatus("ORDER456"));
    }

    // Интерфейс внутренней службы доставки
    interface IInternalDeliveryService {
        void deliverOrder(String orderId);
        String getDeliveryStatus(String orderId);
        double calculateDeliveryCost(String orderId);
    }

    // Реализация внутренней службы доставки
    static class InternalDeliveryService implements IInternalDeliveryService {
        private final Map<String, String> orders = new HashMap<>();

        @Override
        public void deliverOrder(String orderId) {
            orders.put(orderId, "В пути");
            System.out.println("Внутренняя доставка: Заказ " + orderId + " отправлен.");
        }

        @Override
        public String getDeliveryStatus(String orderId) {
            return orders.getOrDefault(orderId, "Неизвестный заказ");
        }

        @Override
        public double calculateDeliveryCost(String orderId) {
            return 50.0; // Фиксированная стоимость для внутренней доставки
        }
    }

    // Сторонняя служба логистики A
    static class ExternalLogisticsServiceA {
        void shipItem(int itemId) {
            System.out.println("ExternalLogisticsServiceA: Отправка товара " + itemId);
        }

        String trackShipment(int shipmentId) {
            return "ExternalLogisticsServiceA: Заказ " + shipmentId + " в пути.";
        }

        double getShippingCost(int itemId) {
            return 100.0; // Пример расчета стоимости
        }
    }

    // Сторонняя служба логистики B
    static class ExternalLogisticsServiceB {
        void sendPackage(String packageInfo) {
            System.out.println("ExternalLogisticsServiceB: Отправка посылки: " + packageInfo);
        }

        String checkPackageStatus(String trackingCode) {
            return "ExternalLogisticsServiceB: Статус посылки " + trackingCode + ": доставляется.";
        }

        double estimateCost(String packageInfo) {
            return 120.0; // Пример расчета стоимости
        }
    }

    // Адаптер для ExternalLogisticsServiceA
    static class LogisticsAdapterA implements IInternalDeliveryService {
        private final ExternalLogisticsServiceA externalService = new ExternalLogisticsServiceA();
        private final Map<String, Integer> orderToItemMap = new HashMap<>();

        @Override
        public void deliverOrder(String orderId) {
            int itemId = orderId.hashCode(); // Пример связи
            orderToItemMap.put(orderId, itemId);
            externalService.shipItem(itemId);
        }

        @Override
        public String getDeliveryStatus(String orderId) {
            Integer itemId = orderToItemMap.get(orderId);
            if (itemId == null) return "Неизвестный заказ";
            return externalService.trackShipment(itemId);
        }

        @Override
        public double calculateDeliveryCost(String orderId) {
            Integer itemId = orderToItemMap.get(orderId);
            return itemId == null ? 0.0 : externalService.getShippingCost(itemId);
        }
    }

    // Адаптер для ExternalLogisticsServiceB
    static class LogisticsAdapterB implements IInternalDeliveryService {
        private final ExternalLogisticsServiceB externalService = new ExternalLogisticsServiceB();
        private final Map<String, String> orderToPackageMap = new HashMap<>();

        @Override
        public void deliverOrder(String orderId) {
            String packageInfo = "Пакет для заказа " + orderId;
            orderToPackageMap.put(orderId, packageInfo);
            externalService.sendPackage(packageInfo);
        }

        @Override
        public String getDeliveryStatus(String orderId) {
            String packageInfo = orderToPackageMap.get(orderId);
            if (packageInfo == null) return "Неизвестный заказ";
            return externalService.checkPackageStatus(packageInfo);
        }

        @Override
        public double calculateDeliveryCost(String orderId) {
            String packageInfo = orderToPackageMap.get(orderId);
            return packageInfo == null ? 0.0 : externalService.estimateCost(packageInfo);
        }
    }

    // Фабрика служб доставки
    static class DeliveryServiceFactory {
        public IInternalDeliveryService getDeliveryService(String type) {
            switch (type) {
                case "Internal":
                    return new InternalDeliveryService();
                case "ExternalA":
                    return new LogisticsAdapterA();
                case "ExternalB":
                    return new LogisticsAdapterB();
                default:
                    throw new IllegalArgumentException("Неизвестный тип службы доставки: " + type);
            }
        }
    }
}
