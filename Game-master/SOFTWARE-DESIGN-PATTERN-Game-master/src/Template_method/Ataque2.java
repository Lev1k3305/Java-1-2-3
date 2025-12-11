package Template_method;

public class Ataque2 extends AtaqueEnemigo {

	public Ataque2(int danio) {
		// TODO Auto-generated constructor stub
		super(danio);
	}

	@Override
	public int danioCausado() {
		System.out.println("El enemigo ha dado un golpe basico");
		return this.danio*2;
	}

}
