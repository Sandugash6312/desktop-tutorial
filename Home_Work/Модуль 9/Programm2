// Основной класс
public class Main {

    // Интерфейс для существующего процессора платежей
    public interface IPaymentProcessor {
        void processPayment(double amount);
    }

    // Реализация интерфейса: PayPal
    public static class PayPalPaymentProcessor implements IPaymentProcessor {
        @Override
        public void processPayment(double amount) {
            System.out.println("Оплата через PayPal на сумму: $" + amount);
        }
    }

    // Сторонний платежный сервис: Stripe
    public static class StripePaymentService {
        public void makeTransaction(double totalAmount) {
            System.out.println("Платеж через Stripe выполнен на сумму: $" + totalAmount);
        }
    }

    // Адаптер для StripePaymentService
    public static class StripePaymentAdapter implements IPaymentProcessor {
        private final StripePaymentService stripeService;

        public StripePaymentAdapter(StripePaymentService stripeService) {
            this.stripeService = stripeService;
        }

        @Override
        public void processPayment(double amount) {
            // Адаптация вызова метода
            stripeService.makeTransaction(amount);
        }
    }

    // Сторонний платежный сервис: Qiwi
    public static class QiwiPaymentService {
        public void sendPayment(double money) {
            System.out.println("Платеж через Qiwi на сумму: $" + money);
        }
    }

    // Адаптер для QiwiPaymentService
    public static class QiwiPaymentAdapter implements IPaymentProcessor {
        private final QiwiPaymentService qiwiService;

        public QiwiPaymentAdapter(QiwiPaymentService qiwiService) {
            this.qiwiService = qiwiService;
        }

        @Override
        public void processPayment(double amount) {
            // Адаптация вызова метода
            qiwiService.sendPayment(amount);
        }
    }

    // Основной метод для тестирования
    public static void main(String[] args) {
        // Использование PayPal
        IPaymentProcessor paypalProcessor = new PayPalPaymentProcessor();
        paypalProcessor.processPayment(50.0);

        // Использование Stripe через адаптер
        StripePaymentService stripeService = new StripePaymentService();
        IPaymentProcessor stripeAdapter = new StripePaymentAdapter(stripeService);
        stripeAdapter.processPayment(75.0);

        // Использование Qiwi через адаптер
        QiwiPaymentService qiwiService = new QiwiPaymentService();
        IPaymentProcessor qiwiAdapter = new QiwiPaymentAdapter(qiwiService);
        qiwiAdapter.processPayment(100.0);
    }
}
