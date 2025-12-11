package Decorator;

import FactoryMethod.Enemigo;

public abstract class Decorador implements DecoradorAtaque {
	
	protected DecoradorAtaque armadecorada;
	
	public Decorador(DecoradorAtaque armadec) {
		this.armadecorada=armadec;
	}
	public int calcularDanio(Arma arma, Enemigo enemigo) {
		return armadecorada.calcularDanio(arma, enemigo);
	}
}
