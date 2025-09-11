import java.util.Random;
import java.util.Scanner;

public class monsterbattle {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Random random = new Random();
            
            // Инициализация героя: [HP, XP, LVL, Attack]
            int[] hero = {100, 0, 1, 10}; // Начальные характеристики: 100 HP, 0 XP, уровень 1, атака 10
            
            // Основной цикл для трёх боёв
            for (int round = 1; round <= 3; round++) {
                // Инициализация монстра: [HP]
                int[] monster = {15 + round * 10}; // Здоровье монстра: 15 + номер_раунда * 10
                int monsterAttack = 5 + round * 3; // Базовый урон монстра: 5 + номер_раунда * 3
                boolean isFighting = true;
                
                System.out.println("\nРаунд " + round + ": Появился монстр с " + monster[0] + " HP!");
                
                // Цикл боя
                while (isFighting && hero[0] > 0 && monster[0] > 0) {
                    // Вывод состояния героя
                    System.out.println("\nГерой: HP = " + hero[0] + ", XP = " + hero[1] + ", Уровень = " + hero[2]);
                    System.out.println("Монстр: HP = " + monster[0]);
                    System.out.println("Выберите действие: 1 - Атаковать, 2 - Лечиться, 3 - Убежать");
                    int choice = scanner.nextInt();
                    
                    // Обработка выбора игрока
                    switch (choice) {
                        case 1 -> {
                            // Атаковать
                            attack(hero, monster, random);
                            if (monster[0] <= 0) {
                                System.out.println("Монстр побеждён!");
                                hero[1] += 20; // Получаем 20 XP за победу
                                levelUp(hero);
                                isFighting = false;
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
                            if (random.nextBoolean()) { // 50% шанс побега
                                System.out.println("Вы успешно сбежали!");
                                isFighting = false;
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
                    
                    // Проверка смерти героя
                    if (hero[0] <= 0) {
                        System.out.println("Герой погиб! Игра окончена.");
                        scanner.close();
                        return;
                    }
                }
            }
            
            // Финальный результат
            System.out.println("\nИгра завершена!");
            System.out.println("Финальные характеристики героя:");
            System.out.println("Уровень: " + hero[2]);
            System.out.println("Опыт: " + hero[1]);
            System.out.println("Здоровье: " + hero[0]);
        }
    }

    // Метод атаки героя
    public static void attack(int[] hero, int[] monster, Random random) {
        int damage = hero[3] + random.nextInt(6); // Базовая атака + случайное число [0, 5]
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
        int damage = baseMonsterAttack + random.nextInt(4); // Базовый урон + случайное число [0, 3]
        hero[0] -= damage;
        System.out.println("Монстр нанёс " + damage + " урона!");
    }

    // Метод повышения уровня
    public static void levelUp(int[] hero) {
        while (hero[1] >= hero[2] * 50) { // Требуется 50 XP * текущий уровень для повышения
            hero[2]++; // Увеличиваем уровень
            hero[3] += 5; // Увеличиваем атаку на 5
            System.out.println("Герой повысил уровень! Новый уровень: " + hero[2] + ", Атака: " + hero[3]);
        }
    }
}