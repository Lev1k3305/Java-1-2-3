package Decorator;

import FactoryMethod.Enemigo;

public class Hacha extends Arma{

	public Hacha() {
		super();
		this.nombre = "Hacha";

	}
	public  int calcularDanio(Arma arma, Enemigo enemigo) {
		return 5;
	}

	
}
