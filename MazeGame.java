import java.util.Random;
import java.util.Scanner;

public class MazeGame {
    // Размеры лабиринта
    private static final int ROWS = 10;
    private static final int COLS = 10;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Random random = new Random();
            
            // Инициализация героя: [HP, XP, LEVEL, ATTACK]
            int[] hero = {100, 0, 1, 20};
            
            // Инициализация лабиринта
            char[][] maze = {
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', 'P', '_', '_', '#', '_', 'M', '_', '_', '#'},
                {'#', '_', '#', '_', '#', '_', '#', '#', '_', '#'},
                {'#', '_', '#', '_', '_', '_', '_', '_', '_', '#'},
                {'#', '_', '#', '#', '#', '#', '_', '#', '_', '#'},
                {'#', '_', '_', '_', '_', '#', '_', '#', '_', '#'},
                {'#', '_', '#', '#', '_', '#', '_', '#', '_', '#'},
                {'#', '_', '#', '_', '_', '_', '_', '_', 'M', '#'},
                {'#', '_', '_', '_', '#', '#', '#', '_', 'E', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
            };
            
            // Позиция игрока
            int playerRow = 1;
            int playerCol = 1;
            
            boolean gameOver = false;
            
            // Игровой цикл
            while (!gameOver) {
                clearConsole();
                printMaze(maze);
                System.out.println("Герой: HP = " + hero[0] + ", XP = " + hero[1] + ", Уровень = " + hero[2]);
                System.out.print("Введите команду (w/a/s/d): ");
                char move = scanner.next().charAt(0);
                
                // Сохраняем старую позицию
                int newRow = playerRow;
                int newCol = playerCol;
                
                // Обработка движения
                switch (move) {
                    case 'w' -> newRow--;
                    case 's' -> newRow++;
                    case 'a' -> newCol--;
                    case 'd' -> newCol++;
                    default -> {
                        System.out.println("Неверная команда! Используйте w/a/s/d.");
                        continue;
                    }
                }
                
                // Проверка границ лабиринта
                if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) {
                    System.out.println("Нельзя выйти за пределы лабиринта!");
                    continue;
                }
                
                // Проверка столкновения со стеной
                if (maze[newRow][newCol] == '#') {
                    hero[0] -= 20;
                    System.out.println("Вы врезались в стену! Потеряно 20 HP.");
                    if (hero[0] <= 0) {
                        clearConsole();
                        printMaze(maze);
                        System.out.println("Герой погиб! Игра окончена.");
                        break;
                    }
                    continue;
                }
                
                // Проверка столкновения с монстром
                if (maze[newRow][newCol] == 'M') {
                    System.out.println("Встречен монстр! Начинается бой!");
                    boolean won = battle(hero, random);
                    if (!won) {
                        clearConsole();
                        printMaze(maze);
                        System.out.println("Герой погиб в бою! Игра окончена.");
                        break;
                    } else {
                        maze[newRow][newCol] = '_'; // Удаляем монстра после победы
                    }
                }
                
                // Проверка достижения выхода
                if (maze[newRow][newCol] == 'E') {
                    clearConsole();
                    printMaze(maze);
                    System.out.println("Поздравляем! Вы нашли выход!");
                    System.out.println("Финальные характеристики героя:");
                    System.out.println("Уровень: " + hero[2]);
                    System.out.println("Опыт: " + hero[1]);
                    System.out.println("Здоровье: " + hero[0]);
                    break;
                }
                
                // Обновление позиции игрока
                maze[playerRow][playerCol] = '_';
                maze[newRow][newCol] = 'P';
                playerRow = newRow;
                playerCol = newCol;
            }
        }
    }

    // Метод для отображения лабиринта
    public static void printMaze(char[][] maze) {
        for (char[] maze1 : maze) {
            for (int j = 0; j < maze1.length; j++) {
                System.out.print(maze1[j] + " ");
            }
            System.out.println();
        }
    }

    // Метод для очистки консоли (имитация)
    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    // Метод боя (адаптирован из практической работы №2)
    public static boolean battle(int[] hero, Random random) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] monster = {30}; // Здоровье монстра (можно настроить)
            int monsterAttack = 10; // Базовый урон монстра (можно настроить)
            boolean isFighting = true;

            while (isFighting && hero[0] > 0 && monster[0] > 0) {
                clearConsole();
                printHeroStats(hero);
                System.out.println("Монстр: HP = " + monster[0]);
                System.out.println("Выберите действие: 1 - Атаковать, 2 - Лечиться, 3 - Убежать");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> {
                        // Атаковать
                        attack(hero, monster, random);
                        if (monster[0] <= 0) {
                            System.out.println("Монстр побеждён!");
                            hero[1] += 20; // Получаем 20 XP за победу
                            levelUp(hero);
                            return true;
                        } else {
                            monsterAttack(hero, monsterAttack, random);
                        }
                    }
                    case 2 -> {
                        // Лечиться
                        heal(hero, random);
                        monsterAttack(hero, monsterAttack, random);
                    }
                    case 3 -> {
                        // Убежать
                        if (random.nextBoolean()) {
                            System.out.println("Вы успешно сбежали!");
                            return true;
                        } else {
                            System.out.println("Побег не удался!");
                            monsterAttack(hero, monsterAttack, random);
                        }
                    }
                    default -> {
                        System.out.println("Неверный выбор! Пропуск хода.");
                        monsterAttack(hero, monsterAttack, random);
                    }
                }

                if (hero[0] <= 0) {
                    return false; // Герой погиб
                }
            }
        }
        return true; // Герой выжил
    }

    // Метод атаки героя
    public static void attack(int[] hero, int[] monster, Random random) {
        int damage = hero[3] + random.nextInt(6); // Базовая атака + [0, 5]
        monster[0] -= damage;
        System.out.println("Герой нанёс " + damage + " урона!");
    }

    // Метод лечения героя
    public static void heal(int[] hero, Random random) {
        int healAmount = 10 + random.nextInt(21); // Случайное лечение [10, 30]
        hero[0] += healAmount;
        if (hero[0] > 100) hero[0] = 100; // Ограничение максимального здоровья
        System.out.println("Герой восстановил " + healAmount + " HP!");
    }

    // Метод атаки монстра
    public static void monsterAttack(int[] hero, int baseMonsterAttack, Random random) {
        int damage = baseMonsterAttack + random.nextInt(4); // Базовый урон + [0, 3]
        hero[0] -= damage;
        System.out.println("Монстр нанёс " + damage + " урона!");
    }

    // Метод повышения уровня
    public static void levelUp(int[] hero) {
        while (hero[1] >= hero[2] * 50) { // Требуется 50 XP * уровень
            hero[2]++;
            hero[3] += 5; // Увеличиваем атаку на 5
            System.out.println("Герой повысил уровень! Новый уровень: " + hero[2] + ", Атака: " + hero[3]);
        }
    }

    // Метод для вывода статов героя
    public static void printHeroStats(int[] hero) {
        System.out.println("Герой: HP = " + hero[0] + ", XP = " + hero[1] + ", Уровень = " + hero[2]);
    }
}