// Подсистема: TV
class TV {
    public void on() {
        System.out.println("Телевизор включен.");
    }

    public void off() {
        System.out.println("Телевизор выключен.");
    }

    public void setChannel(int channel) {
        System.out.println("Выбран канал " + channel);
    }
}

// Подсистема: AudioSystem
class AudioSystem {
    public void on() {
        System.out.println("Аудиосистема включена.");
    }

    public void off() {
        System.out.println("Аудиосистема выключена.");
    }

    public void setVolume(int volume) {
        System.out.println("Громкость установлена на уровне " + volume);
    }
}

// Подсистема: DVDPlayer
class DVDPlayer {
    public void play() {
        System.out.println("Воспроизведение DVD начато.");
    }

    public void pause() {
        System.out.println("DVD на паузе.");
    }

    public void stop() {
        System.out.println("Воспроизведение DVD остановлено.");
    }
}

// Подсистема: GameConsole
class GameConsole {
    public void on() {
        System.out.println("Игровая консоль включена.");
    }

    public void startGame(String gameName) {
        System.out.println("Игра '" + gameName + "' запущена.");
    }
}

// Фасад: HomeTheaterFacade
class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audioSystem;
    private DVDPlayer dvdPlayer;
    private GameConsole gameConsole;

    public HomeTheaterFacade(TV tv, AudioSystem audioSystem, DVDPlayer dvdPlayer, GameConsole gameConsole) {
        this.tv = tv;
        this.audioSystem = audioSystem;
        this.dvdPlayer = dvdPlayer;
        this.gameConsole = gameConsole;
    }

    public void watchMovie() {
        System.out.println("Подготовка к просмотру фильма...");
        tv.on();
        audioSystem.on();
        audioSystem.setVolume(5);
        dvdPlayer.play();
    }

    public void endMovie() {
        System.out.println("Завершение просмотра фильма...");
        dvdPlayer.stop();
        tv.off();
        audioSystem.off();
    }

    public void playGame(String gameName) {
        System.out.println("Подготовка к запуску видеоигры...");
        tv.on();
        audioSystem.on();
        audioSystem.setVolume(7);
        gameConsole.on();
        gameConsole.startGame(gameName);
    }

    public void listenToMusic() {
        System.out.println("Подготовка к прослушиванию музыки...");
        tv.on();
        audioSystem.on();
        audioSystem.setVolume(5);
        System.out.println("Настройка аудиовхода на телевизоре для воспроизведения музыки.");
    }

    public void adjustVolume(int volume) {
        audioSystem.setVolume(volume);
    }

    public void shutdown() {
        System.out.println("Выключение всей системы...");
        tv.off();
        audioSystem.off();
        gameConsole.on();
    }
}

// Класс Main для демонстрации работы фасада
public class Main {
    public static void main(String[] args) {
        // Создание экземпляров подсистем
        TV tv = new TV();
        AudioSystem audioSystem = new AudioSystem();
        DVDPlayer dvdPlayer = new DVDPlayer();
        GameConsole gameConsole = new GameConsole();

        // Создание фасада
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audioSystem, dvdPlayer, gameConsole);

        // Сценарий: Просмотр фильма
        homeTheater.watchMovie();
        homeTheater.endMovie();

        // Сценарий: Запуск игры
        homeTheater.playGame("FIFA");

        // Сценарий: Прослушивание музыки
        homeTheater.listenToMusic();

        // Регулировка громкости
        homeTheater.adjustVolume(10);

        // Выключение всей системы
        homeTheater.shutdown();
    }
}
