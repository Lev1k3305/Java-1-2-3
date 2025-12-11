package Decorator;

import FactoryMethod.Enemigo;

public class DecoradorFuego extends Decorador{

	public DecoradorFuego(DecoradorAtaque armadec) {
		super(armadec);
	}
		
	public int calcularDanio(Arma arma, Enemigo enemigo) {
		int danio;
		if(arma.nombre.equals("Punios") && enemigo.getTipo().equals("Hielo")) {
			danio = 15;
		}else danio = 10;
		if(arma.nombre.equals("Hacha") && enemigo.getTipo().equals("Hielo")) {
			danio = 20;
		}else danio = 15;
		if(arma.nombre.equals("Espadas") && enemigo.getTipo().equals("Hielo")) {
			danio = 25;
		}else danio = 20;
		
		return danio;
	}

}
