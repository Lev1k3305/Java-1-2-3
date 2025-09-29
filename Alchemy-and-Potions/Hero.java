// Hero.java
public final class Hero {
    private int hp;
    private int strength;
    private int intelligence;

    public Hero(int hp, int strength, int intelligence) {
        setHp(hp);
        setStrength(strength);
        setIntelligence(intelligence);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = (hp < 0) ? 0 : hp;
        // Нет верхнего предела для hp, но можно добавить, если нужно (например, <= 1000)
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength < 0) {
            this.strength = 0;
        } else if (strength > 100) {
            this.strength = 100;
        } else {
            this.strength = strength;
        }
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        if (intelligence < 0) {
            this.intelligence = 0;
        } else if (intelligence > 100) {
            this.intelligence = 100;
        } else {
            this.intelligence = intelligence;
        }
    }
}