package Template_method;

public class Ataque3 extends AtaqueEnemigo {

	public Ataque3(int danio) {
		// TODO Auto-generated constructor stub
		super(danio);
	}

	@Override
	public int danioCausado() {
		System.out.println("El enemigo ha dado un golpe fuerte");
		return this.danio * 3;
	}

}
