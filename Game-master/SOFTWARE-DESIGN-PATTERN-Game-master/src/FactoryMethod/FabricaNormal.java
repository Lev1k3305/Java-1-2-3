package FactoryMethod;

public class FabricaNormal implements FabricaEnemigos {
	private String tipo ="Normal";
	public FabricaNormal() {
		// TODO Auto-generated constructor stub
	}
	public Zombie crearZombie() {
		return new ZombieNormal(5,50,tipo);
	}
	public Troll crearTroll(){
		return new TrollNormal(10,65,tipo);
	}
	public Boss crearBoss() {
		return new BossNormal(15,80,tipo);
	}

}
