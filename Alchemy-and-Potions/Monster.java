public final class Monster {
    private int hp;
    private int attack;

    public Monster(int hp, int attack) {
        setHp(hp);
        setAttack(attack);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = (hp < 0) ? 0 : hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = (attack < 0) ? 0 : attack;
    }
}
