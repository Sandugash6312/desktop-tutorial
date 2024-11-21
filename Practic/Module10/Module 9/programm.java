import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    // Интерфейс для отчетов
    interface IReport {
        String generate();
    }

    // Класс отчета по продажам
    static class SalesReport implements IReport {
        @Override
        public String generate() {
            return "Отчет по продажам:\n1. Продажа: $100\n2. Продажа: $200\n3. Продажа: $150";
        }
    }

    // Класс отчета по пользователям
    static class UserReport implements IReport {
        @Override
        public String generate() {
            return "Отчет по пользователям:\n1. Sandugash\n2. Dos\n3. Zhibek";
        }
    }

    // Абстрактный декоратор
    static abstract class ReportDecorator implements IReport {
        protected IReport report;

        public ReportDecorator(IReport report) {
            this.report = report;
        }

        @Override
        public String generate() {
            return report.generate();
        }
    }

    // Декоратор фильтрации по датам
    static class DateFilterDecorator extends ReportDecorator {
        private final Date startDate;
        private final Date endDate;

        public DateFilterDecorator(IReport report, Date startDate, Date endDate) {
            super(report);
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String generate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return super.generate() + "\nФильтрация по датам: с " + sdf.format(startDate) + " по " + sdf.format(endDate);
        }
    }

    // Декоратор сортировки данных
    static class SortingDecorator extends ReportDecorator {
        private final String criterion;

        public SortingDecorator(IReport report, String criterion) {
            super(report);
            this.criterion = criterion;
        }

        @Override
        public String generate() {
            return super.generate() + "\nСортировка по: " + criterion;
        }
    }

    // Декоратор экспорта в CSV
    static class CsvExportDecorator extends ReportDecorator {
        public CsvExportDecorator(IReport report) {
            super(report);
        }

        @Override
        public String generate() {
            return super.generate() + "\nЭкспортировано в CSV.";
        }
    }

    // Декоратор экспорта в PDF
    static class PdfExportDecorator extends ReportDecorator {
        public PdfExportDecorator(IReport report) {
            super(report);
        }

        @Override
        public String generate() {
            return super.generate() + "\nЭкспортировано в PDF.";
        }
    }

    public static void main(String[] args) {
        try {
            // Базовый отчет по продажам
            IReport report = new SalesReport();

            // Применяем декораторы
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2024-01-01");
            Date endDate = sdf.parse("2024-12-31");

            report = new DateFilterDecorator(report, startDate, endDate);
            report = new SortingDecorator(report, "сумма продажи");
            report = new CsvExportDecorator(report);

            // Вывод результата
            System.out.println(report.generate());

            System.out.println("\n---\n");

            // Базовый отчет по пользователям
            IReport userReport = new UserReport();
            userReport = new SortingDecorator(userReport, "имя");
            userReport = new PdfExportDecorator(userReport);

            // Вывод результата
            System.out.println(userReport.generate());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
