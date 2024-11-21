public class Main {
    // Базовый интерфейс для всех напитков
    public interface Beverage {
        String getDescription(); // Описание напитка
        double cost();           // Стоимость напитка
    }

    // Реализация напитка: Эспрессо
    public static class Espresso implements Beverage {
        @Override
        public String getDescription() {
            return "Эспрессо";
        }

        @Override
        public double cost() {
            return 2.0; // Базовая стоимость
        }
    }

    // Реализация напитка: Чай
    public static class Tea implements Beverage {
        @Override
        public String getDescription() {
            return "Чай";
        }

        @Override
        public double cost() {
            return 1.5;
        }
    }

    // Реализация напитка: Латте
    public static class Latte implements Beverage {
        @Override
        public String getDescription() {
            return "Латте";
        }

        @Override
        public double cost() {
            return 3.0;
        }
    }

    // Абстрактный класс для декораторов
    public static abstract class BeverageDecorator implements Beverage {
        protected Beverage baseBeverage;

        // Конструктор принимает базовый напиток
        public BeverageDecorator(Beverage baseBeverage) {
            this.baseBeverage = baseBeverage;
        }

        @Override
        public String getDescription() {
            return baseBeverage.getDescription(); // Делегируем описание базовому напитку
        }

        @Override
        public double cost() {
            return baseBeverage.cost(); // Базовая стоимость
        }
    }

    // Декоратор: Молоко
    public static class Milk extends BeverageDecorator {
        public Milk(Beverage baseBeverage) {
            super(baseBeverage);
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " + молоко";
        }

        @Override
        public double cost() {
            return super.cost() + 0.5; // Добавляем стоимость молока
        }
    }

    // Декоратор: Сахар
    public static class Sugar extends BeverageDecorator {
        public Sugar(Beverage baseBeverage) {
            super(baseBeverage);
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " + сахар";
        }

        @Override
        public double cost() {
            return super.cost() + 0.2;
        }
    }

    // Декоратор: Взбитые сливки
    public static class WhippedCream extends BeverageDecorator {
        public WhippedCream(Beverage baseBeverage) {
            super(baseBeverage);
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " + взбитые сливки";
        }

        @Override
        public double cost() {
            return super.cost() + 0.7;
        }
    }

    // Основной метод для тестирования
    public static void main(String[] args) {
        // Напиток 1: Эспрессо с добавками
        Beverage order1 = new Espresso();
        order1 = new Milk(order1);         // Добавляем молоко
        order1 = new Sugar(order1);        // Добавляем сахар
        order1 = new WhippedCream(order1); // Добавляем сливки
        System.out.println("Заказ: " + order1.getDescription());
        System.out.println("Итоговая стоимость: $" + order1.cost());

        // Напиток 2: Чай с одной добавкой
        Beverage order2 = new Tea();
        order2 = new Sugar(order2); // Добавляем сахар
        System.out.println("Заказ: " + order2.getDescription());
        System.out.println("Итоговая стоимость: $" + order2.cost());

        // Напиток 3: Латте без добавок
        Beverage order3 = new Latte();
        System.out.println("Заказ: " + order3.getDescription());
        System.out.println("Итоговая стоимость: $" + order3.cost());
    }
}
