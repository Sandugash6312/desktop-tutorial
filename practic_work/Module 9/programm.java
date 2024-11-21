import java.util.Scanner;

public class Main {

    // Интерфейс для отчетов
    interface IReport {
        String generate();
    }

    // Отчет по продажам
    static class SalesReport implements IReport {
        @Override
        public String generate() {
            return "Отчет по продажам: Товар1 - $100, Товар2 - $200";
        }
    }

    // Отчет по пользователям
    static class UserReport implements IReport {
        @Override
        public String generate() {
            return "Отчет по пользователям: Sandugash, Dos, Zhibek";
        }
    }

    // Базовый декоратор
    static class ReportDecorator implements IReport {
        protected IReport report;

        public ReportDecorator(IReport report) {
            this.report = report;
        }

        @Override
        public String generate() {
            return report.generate();
        }
    }

    // Декоратор для фильтрации по датам
    static class DateFilterDecorator extends ReportDecorator {
        private final String startDate;
        private final String endDate;

        public DateFilterDecorator(IReport report, String startDate, String endDate) {
            super(report);
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String generate() {
            return super.generate() + " | Фильтр по датам: с " + startDate + " по " + endDate;
        }
    }

    // Декоратор для сортировки данных
    static class SortingDecorator extends ReportDecorator {
        private final String criterion;

        public SortingDecorator(IReport report, String criterion) {
            super(report);
            this.criterion = criterion;
        }

        @Override
        public String generate() {
            return super.generate() + " | Сортировка по: " + criterion;
        }
    }

    // Декоратор для экспорта в CSV
    static class CsvExportDecorator extends ReportDecorator {
        public CsvExportDecorator(IReport report) {
            super(report);
        }

        @Override
        public String generate() {
            return super.generate() + " | Экспорт: CSV";
        }
    }

    // Декоратор для экспорта в PDF
    static class PdfExportDecorator extends ReportDecorator {
        public PdfExportDecorator(IReport report) {
            super(report);
        }

        @Override
        public String generate() {
            return super.generate() + " | Экспорт: PDF";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Выбор типа отчета
        System.out.println("Выберите отчет:");
        System.out.println("1 - Отчет по продажам");
        System.out.println("2 - Отчет по пользователям");
        int reportChoice = scanner.nextInt();
        IReport report = reportChoice == 1 ? new SalesReport() : new UserReport();

        // Добавление декораторов
        System.out.println("Добавить фильтр по датам? (1 - Да, 0 - Нет)");
        if (scanner.nextInt() == 1) {
            System.out.print("Введите начальную дату (YYYY-MM-DD): ");
            String startDate = scanner.next();
            System.out.print("Введите конечную дату (YYYY-MM-DD): ");
            String endDate = scanner.next();
            report = new DateFilterDecorator(report, startDate, endDate);
        }

        System.out.println("Добавить сортировку? (1 - Да, 0 - Нет)");
        if (scanner.nextInt() == 1) {
            System.out.print("Введите критерий сортировки (например, дата, сумма): ");
            String criterion = scanner.next();
            report = new SortingDecorator(report, criterion);
        }

        System.out.println("Добавить экспорт?");
        System.out.println("1 - CSV");
        System.out.println("2 - PDF");
        System.out.println("0 - Пропустить");
        int exportChoice = scanner.nextInt();
        if (exportChoice == 1) {
            report = new CsvExportDecorator(report);
        } else if (exportChoice == 2) {
            report = new PdfExportDecorator(report);
        }

        // Вывод результата
        System.out.println("\nСгенерированный отчет:");
        System.out.println(report.generate());
    }
}
