import State.Estado;
import State.EstadoFuerte;


public class Jugador {
	protected int vida;
	protected int danio;
	protected Estado state;
	
	
	public Estado getState() {
		return state;
	}

	public void setState(Estado state) {
		this.state = state;
	}
	public Jugador() {
		
	}
	
	public Jugador(int vida,int fuerza) {
		this.vida=vida;
		this.danio=fuerza;
		this.state = new EstadoFuerte(3);
		
	}
	
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getDanio() {
		return danio;
	}

	public void setDanio(int danio) {
		this.danio = danio;
	}

	
}
