// Main.java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Hero hero = new Hero(100, 10, 8);
        System.out.println("Герой создан: сила = " + hero.getStrength() + ", интеллект = " + hero.getIntelligence());

        ArrayList<Potion> potions = new ArrayList<>();
        potions.add(new Potion("Зелье силы", 5, 0));
        potions.add(new Potion("Зелье разума", 0, 5));
        potions.add(new Potion("Эликсир мудрости", 3, 3));
        potions.add(new Potion("Зелье яда", -10, -5));

        for (Potion potion : potions) {
            potion.apply(hero);
        }

        // Дополнительное задание: боевой система
        // Демонстрируем, как зелья помогают в победе над монстром
        // Сначала битва без зелий (герой проиграет)
        System.out.println("\nДополнительное задание: Битва без зелий");
        Hero heroWithout = new Hero(100, 10, 8);
        Monster monsterWithout = new Monster(150, 10);
        battle(heroWithout, monsterWithout);

        // Теперь битва с применением только положительных зелий (герой выиграет)
        System.out.println("\nДополнительное задание: Битва с зельями");
        Hero heroWith = new Hero(100, 10, 8);
        ArrayList<Potion> goodPotions = new ArrayList<>();
        goodPotions.add(new Potion("Зелье силы", 5, 0));
        goodPotions.add(new Potion("Зелье разума", 0, 5));
        goodPotions.add(new Potion("Эликсир мудрости", 3, 3));
        // Не добавляем "Зелье яда", так как оно негативное и не помогает в победе

        for (Potion potion : goodPotions) {
            potion.apply(heroWith);
        }

        Monster monsterWith = new Monster(150, 10);
        battle(heroWith, monsterWith);
    }

    private static void battle(Hero hero, Monster monster) {
        int heroHp = hero.getHp();
        int monsterHp = monster.getHp();
        boolean heroTurn = true; // Герой начинает первым

        while (heroHp > 0 && monsterHp > 0) {
            if (heroTurn) {
                monsterHp -= hero.getStrength();
                monsterHp = Math.max(0, monsterHp);
                System.out.println("Герой атакует монстра. HP монстра: " + monsterHp);
            } else {
                heroHp -= monster.getAttack();
                heroHp = Math.max(0, heroHp);
                System.out.println("Монстр атакует героя. HP героя: " + heroHp);
            }
            heroTurn = !heroTurn;
        }

        if (heroHp > 0) {
            System.out.println("Герой победил!");
        } else {
            System.out.println("Монстр победил!");
        }
    }
}