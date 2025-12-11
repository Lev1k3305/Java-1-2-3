package FactoryMethod;

public class FabricaHielo implements FabricaEnemigos {
	private String tipo ="Hielo";
	public FabricaHielo() {
		
	}
	public Zombie crearZombie() {
		return new ZombieHielo(5,60,tipo);
	}
	public Troll crearTroll(){
		return new TrollHielo(10,75,tipo);
	}
	public Boss crearBoss() {
		return new BossHielo(15,90,tipo);
	}

}
