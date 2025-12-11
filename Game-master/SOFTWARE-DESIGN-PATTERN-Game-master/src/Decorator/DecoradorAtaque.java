package Decorator;

import FactoryMethod.Enemigo;

public interface DecoradorAtaque {
	
	Arma armaDecorator=null;
	
	
	 int calcularDanio(Arma arma, Enemigo enemigo);
	
}
