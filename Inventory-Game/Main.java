import java.util.Scanner;
// Импорт класса Item
// import Item; // Удалено, используйте правильный путь или убедитесь, что Item находится в том же пакете

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Inventory inventory = new Inventory();
            boolean running = true;
            
            while (running) {
                System.out.println("Меню:");
                System.out.println("1 - Добавить предмет");
                System.out.println("2 - Показать инвентарь");
                System.out.println("3 - Удалить предмет");
                System.out.println("4 - Поиск предмета по названию");
                System.out.println("5 - Сортировать инвентарь по ценности");
                System.out.println("6 - Сортировать инвентарь по алфавиту");
                System.out.println("0 - Выйти");
                System.out.print("Ваш выбор: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1 -> {
                        System.out.print("Введите название предмета: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите тип предмета (оружие/зелье/броня/что-нибудь еще): ");
                        String type = scanner.nextLine();
                        System.out.print("Введите ценность предмета: ");
                        int value = scanner.nextInt();
                        scanner.nextLine();
                        Item item = new Item(name, type, value);
                        inventory.addItem(item);
                    }
                    case 2 -> inventory.showItems();
                    case 3 -> {
                        inventory.showItems();
                        if (!inventory.getItems().isEmpty()) {
                            System.out.print("Введите номер предмета для удаления: ");
                            int index = scanner.nextInt();
                            scanner.nextLine();
                            inventory.removeItem(index);
                        }
                    }
                    case 4 -> {
                        System.out.print("Введите название предмета для поиска: ");
                        String searchName = scanner.nextLine();
                        Item foundItem = inventory.findItemByName(searchName);
                        if (foundItem != null) {
                            System.out.println("Найден предмет: " + foundItem);
                        } else {
                            System.out.println("Предмет не найден!");
                        }
                    }
                        
                    case 5 -> {
                        inventory.sortByValue();
                        inventory.showItems();
                    }
                    case 6 -> {
                        inventory.sortByName();
                        inventory.showItems();
                    }
                    case 0 -> running = false;
                    default -> System.out.println("Неверный выбор!");
                }
            }
        }
    }
}
