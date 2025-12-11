package State;

public class EstadoSano extends Estado{
	

	public EstadoSano(int estado) {
		super(estado);
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

	public void getEstado() {
		System.out.println("El jugador sano");
		
	}
	
}

