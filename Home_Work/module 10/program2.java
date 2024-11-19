import java.util.ArrayList;
import java.util.List;

// Интерфейс FileSystemComponent
interface FileSystemComponent {
    void display();   // Вывод информации о компоненте
    int getSize();    // Получение размера компонента
}

// Класс File, представляющий файл
class File implements FileSystemComponent {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("Файл: " + name + " (размер: " + size + " KB)");
    }

    @Override
    public int getSize() {
        return size;
    }
}

// Класс Directory, представляющий папку
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components;

    public Directory(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    // Метод для добавления компонента в папку
    public void addComponent(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        } else {
            System.out.println("Компонент уже существует в папке: " + name);
        }
    }

    // Метод для удаления компонента из папки
    public void removeComponent(FileSystemComponent component) {
        if (components.contains(component)) {
            components.remove(component);
        } else {
            System.out.println("Компонент не найден в папке: " + name);
        }
    }

    @Override
    public void display() {
        System.out.println("Папка: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}

// Основной класс Main для демонстрации работы
public class Main {
    public static void main(String[] args) {
        // Создание файлов
        File file1 = new File("File1.txt", 100);
        File file2 = new File("File2.txt", 200);
        File file3 = new File("File3.jpg", 300);
        File file4 = new File("File4.mp4", 400);

        // Создание папок
        Directory rootDirectory = new Directory("Root");
        Directory documentsDirectory = new Directory("Documents");
        Directory mediaDirectory = new Directory("Media");
        Directory imagesDirectory = new Directory("Images");

        // Построение иерархии
        documentsDirectory.addComponent(file1);
        documentsDirectory.addComponent(file2);
        mediaDirectory.addComponent(file3);
        mediaDirectory.addComponent(file4);
        mediaDirectory.addComponent(imagesDirectory);

        // Вложенная папка
        imagesDirectory.addComponent(file3);

        // Добавление папок в корневую директорию
        rootDirectory.addComponent(documentsDirectory);
        rootDirectory.addComponent(mediaDirectory);

        // Вывод иерархии и размера
        System.out.println("Содержимое корневой папки:");
        rootDirectory.display();
        System.out.println("Общий размер корневой папки: " + rootDirectory.getSize() + " KB");
    }
}
