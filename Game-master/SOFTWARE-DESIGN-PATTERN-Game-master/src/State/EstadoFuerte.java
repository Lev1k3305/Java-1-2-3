package State;

public class EstadoFuerte extends Estado{

	public EstadoFuerte(int estado) {
		super(estado);
	}
	
	public void getEstado() {
		System.out.println("El jugador esta fuerte");
	}

	
	public Estado cambiarEstado(int vida) {
		
		if(vida <= 30)
			return new EstadoDebil(1);
		else if(vida <= 60)
			return new EstadoSano(2);
		else if(vida >=60)
			return new EstadoFuerte(3);
		return new EstadoFuerte(3);
	}


	
	
}