package Decorator;

import FactoryMethod.Enemigo;

public class EspadasCaos extends Arma {
	
	
	public EspadasCaos() {
		super();
		this.nombre = "Espadas";
		
	}
	public int calcularDanio(Arma arma, Enemigo enemigo) {
		return 0;
	}
	
	

}
