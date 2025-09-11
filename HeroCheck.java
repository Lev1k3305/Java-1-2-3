import java.util.Scanner;

public class HeroCheck {
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        // Создаем объект Scanner для ввода данных
        Scanner scanner = new Scanner(System.in);

        // Ввод данных от пользователя
        System.out.print("Введите имя героя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст героя: ");
        int age = scanner.nextInt();

        System.out.print("Есть ли лицензия на меч? (true/false): ");
        boolean hasSwordLicense = scanner.nextBoolean();

        System.out.print("Есть ли броня? (true/false): ");
        boolean hasArmor = scanner.nextBoolean();

        System.out.print("Введите уровень силы героя (1–100): ");
        int strength = scanner.nextInt();

        // Проверка диапазона силы
        if (strength < 1 || strength > 100) {
            System.out.println("Ошибка: уровень силы должен быть в диапазоне 1–100!");
            scanner.close();
            return;
        }

        // Проверка допуска к бою
        boolean isAllowedToFight = (age > 18 && hasSwordLicense) || hasArmor;

        // Вывод результата допуска
        if (isAllowedToFight) {
            System.out.println(name + ", вы допущены к бою с драконом!");

            // Определение исхода боя
            if (strength < 30) {
                System.out.println("Вы проиграли дракону!");
            } else if (strength >= 30 && strength <= 60) {
                System.out.println("Вы сражались достойно, но дракон улетел!");
            } else {
                System.out.println("Поздравляем! Вы победили дракона!");
            }

            // Подсчет очков героя
            int points = (age / 2) + strength + (hasArmor ? 20 : 0) + (hasSwordLicense ? 10 : 0);

            // Присвоение титула
            String title;
            if (points < 50) {
                title = "Новичок";
            } else if (points >= 50 && points <= 100) {
                title = "Рыцарь";
            } else {
                title = "Легендарный герой";
            }

            // Вывод очков и титула
            System.out.println("Очки героя: " + points);
            System.out.println("Ваш титул: " + title);
        } else {
            System.out.println(name + ", вы слишком слабы и не можете участвовать в бою!");
        }

        // Закрываем Scanner
        scanner.close();
    }
}