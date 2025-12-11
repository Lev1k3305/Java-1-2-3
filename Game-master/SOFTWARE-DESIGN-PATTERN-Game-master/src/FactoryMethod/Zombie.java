package FactoryMethod;

public abstract class Zombie extends Enemigo {
	
	protected int danio;
	protected int vida;
	

	public Zombie(int danio, int vida,String tipo) {
		super(danio,vida,tipo);
	}
	
}
