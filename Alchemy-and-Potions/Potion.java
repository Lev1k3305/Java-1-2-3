// Potion.java
public class Potion {
    private final String name;
    private final int deltaStrength;
    private final int deltaIntelligence;

    public Potion(String name, int deltaStrength, int deltaIntelligence) {
        this.name = name;
        this.deltaStrength = deltaStrength;
        this.deltaIntelligence = deltaIntelligence;
    }

    public void apply(Hero hero) {
        System.out.println("Применяем " + name + "...");
        hero.setStrength(hero.getStrength() + deltaStrength);
        hero.setIntelligence(hero.getIntelligence() + deltaIntelligence);
        System.out.println("Новые характеристики героя: сила = " + hero.getStrength() + ", интеллект = " + hero.getIntelligence());
    }
}