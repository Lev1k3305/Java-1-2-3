package State;

public class EstadoDebil extends Estado {

	
	public EstadoDebil(int estadoActual) {
		super(estadoActual);
		
	}

	public void getEstado() {
		System.out.println("El jugador esta debil");
	}

	
	public Estado cambiarEstado(int vida) {
		if(vida <= 30)
			return new EstadoDebil(1);
		else if(vida <= 60)
			return new EstadoSano(2);
		else if(vida >=60)
			return new EstadoFuerte(3);
		return new EstadoDebil(2);
	}

}
