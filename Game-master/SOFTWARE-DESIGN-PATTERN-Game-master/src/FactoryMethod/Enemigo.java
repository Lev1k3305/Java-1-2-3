package FactoryMethod;



public abstract class Enemigo{
	protected int danio;
	protected int vida;
	protected String tipo;
	
	public Enemigo(int danio, int vida, String tipo) {
		this.danio=danio;
		this.vida =vida;
		this.tipo =tipo;
	}
	public int getVida() {
		return vida;
	}
	public int getdanio() {
		return danio;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
