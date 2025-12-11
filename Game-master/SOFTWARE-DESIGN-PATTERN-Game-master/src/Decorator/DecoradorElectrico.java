package Decorator;

import FactoryMethod.Enemigo;

public class DecoradorElectrico extends Decorador {
	
	public DecoradorElectrico(DecoradorAtaque armadec) {
		super(armadec);
	}
	
	public int calcularDanio(Arma arma, Enemigo enemigo) {
		int danio;
		if(arma.nombre.equals("Punios") && enemigo.getTipo().equals("Fuego")) {
			danio = 15;
		}else danio = 10;
		if(arma.nombre.equals("Hacha") && enemigo.getTipo().equals("Fuego")) {
			danio = 20;
		}else danio = 15;
		if(arma.nombre.equals("Espadas") && enemigo.getTipo().equals("Fuego")) {
			danio = 25;
		}else danio = 20;
		
		return danio;
		
	}
	

}
