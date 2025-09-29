import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Inventory {
    private final ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        System.out.println("Предмет добавлен!");
    }

    public void showItems() {
        if (items.isEmpty()) {
            System.out.println("Инвентарь пуст!");
            return;
        }
        System.out.println("Ваш инвентарь:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println("Общая ценность инвентаря: " + calculateTotalValue());
    }

    public void removeItem(int index) {
        if (index < 1 || index > items.size()) {
            System.out.println("Неверный номер предмета!");
            return;
        }
        items.remove(index - 1);
        System.out.println("Предмет удалён!");
    }

    public Item findItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void sortByValue() {
        Collections.sort(items, Comparator.comparingInt(Item::getValue));
        System.out.println("Инвентарь отсортирован по ценности!");
    }

    public void sortByName() {
        Collections.sort(items, Comparator.comparing(Item::getName));
        System.out.println("Инвентарь отсортирован по алфавиту!");
    }

    private int calculateTotalValue() {
        int total = 0;
        for (Item item : items) {
            total += item.getValue();
        }
        return total;
    }

    // Геттер для доступа к списку (для проверки в Main)
    public ArrayList<Item> getItems() {
        return items;
    }
}