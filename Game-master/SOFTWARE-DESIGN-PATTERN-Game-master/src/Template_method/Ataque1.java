package Template_method;

public class Ataque1 extends AtaqueEnemigo{

	public Ataque1(int danio) {
		super(danio);
	}

	@Override
	public int danioCausado() {
		
		System.out.println("El enemigo ha dado un golpe flojo");
		return this.danio;
	}

}
