import java.util.ArrayList;
import java.util.List;

// Абстрактный класс OrganizationComponent
abstract class OrganizationComponent {
    protected String name;

    public OrganizationComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double getBudget();
    public abstract int getEmployeeCount();
    public abstract void displayInfo();
}

// Класс Employee (Сотрудник)
class Employee extends OrganizationComponent {
    private String position;
    private double salary;

    public Employee(String name, String position, double salary) {
        super(name);
        this.position = position;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public double getBudget() {
        return salary;
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }

    @Override
    public void displayInfo() {
        System.out.println("Сотрудник: " + name + ", Должность: " + position + ", Зарплата: " + salary);
    }
}

// Класс Contractor (Контрактор) для временных сотрудников
class Contractor extends Employee {
    public Contractor(String name, String position, double fixedRate) {
        super(name, position, fixedRate);
    }

    @Override
    public double getBudget() {
        return 0;  // Контракторы не включаются в бюджет отдела
    }
}

// Класс Department (Отдел)
class Department extends OrganizationComponent {
    private List<OrganizationComponent> components;

    public Department(String name) {
        super(name);
        this.components = new ArrayList<>();
    }

    public void addComponent(OrganizationComponent component) {
        components.add(component);
    }

    public void removeComponent(OrganizationComponent component) {
        components.remove(component);
    }

    @Override
    public double getBudget() {
        double totalBudget = 0;
        for (OrganizationComponent component : components) {
            totalBudget += component.getBudget();
        }
        return totalBudget;
    }

    @Override
    public int getEmployeeCount() {
        int totalEmployees = 0;
        for (OrganizationComponent component : components) {
            totalEmployees += component.getEmployeeCount();
        }
        return totalEmployees;
    }

    public OrganizationComponent findEmployeeByName(String name) {
        for (OrganizationComponent component : components) {
            if (component instanceof Employee && component.getName().equalsIgnoreCase(name)) {
                return component;
            } else if (component instanceof Department) {
                OrganizationComponent found = ((Department) component).findEmployeeByName(name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public void displayAllEmployees() {
        System.out.println("Отдел: " + name);
        for (OrganizationComponent component : components) {
            component.displayInfo();
        }
        for (OrganizationComponent component : components) {
            if (component instanceof Department) {
                ((Department) component).displayAllEmployees();
            }
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Отдел: " + name + ", Бюджет: " + getBudget() + ", Количество сотрудников: " + getEmployeeCount());
    }
}

// Основной класс для демонстрации работы
public class Main {
    public static void main(String[] args) {
        // Создаем отделы
        Department headDepartment = new Department("Головной отдел");
        Department salesDepartment = new Department("Отдел продаж");
        Department techDepartment = new Department("Технический отдел");

        // Добавляем подотделы
        headDepartment.addComponent(salesDepartment);
        headDepartment.addComponent(techDepartment);

        // Создаем сотрудников
        Employee emp1 = new Employee("Анна Смирнова", "Менеджер", 50000);
        Employee emp2 = new Employee("Иван Иванов", "Специалист", 45000);
        Employee emp3 = new Employee("Петр Петров", "Инженер", 60000);
        Contractor contractor = new Contractor("Сергей Сергеев", "Временный работник", 30000);

        // Добавляем сотрудников в отделы
        headDepartment.addComponent(emp1);
        salesDepartment.addComponent(emp2);
        techDepartment.addComponent(emp3);
        techDepartment.addComponent(contractor);

        // Отображаем структуру организации
        System.out.println("Структура организации:");
        headDepartment.displayInfo();

        // Расчет бюджета
        System.out.println("\nОбщий бюджет головного отдела: " + headDepartment.getBudget());

        // Подсчет общего количества сотрудников
        System.out.println("Общее количество сотрудников в головном отделе: " + headDepartment.getEmployeeCount());

        // Изменение зарплаты
        System.out.println("\nИзменение зарплаты сотрудника Иван Иванов:");
        OrganizationComponent employee = headDepartment.findEmployeeByName("Иван Иванов");
        if (employee instanceof Employee) {
            ((Employee) employee).setSalary(47000);
            employee.displayInfo();
        } else {
            System.out.println("Сотрудник не найден");
        }

        // Поиск сотрудника
        System.out.println("\nПоиск сотрудника Петр Петров:");
        OrganizationComponent foundEmployee = headDepartment.findEmployeeByName("Петр Петров");
        if (foundEmployee != null) {
            foundEmployee.displayInfo();
        } else {
            System.out.println("Сотрудник не найден");
        }

        // Отображение всех сотрудников
        System.out.println("\nСписок всех сотрудников:");
        headDepartment.displayAllEmployees();
    }
}
