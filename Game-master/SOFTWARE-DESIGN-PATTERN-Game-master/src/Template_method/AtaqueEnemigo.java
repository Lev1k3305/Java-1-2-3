package Template_method;

public abstract class AtaqueEnemigo {
	protected int danio;
	public AtaqueEnemigo(int danio) {
		// TODO Auto-generated constructor stub
		this.danio=danio;
	}
	public abstract int danioCausado();

}
