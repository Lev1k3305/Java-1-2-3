package State;

public abstract class Estado {
	protected int estadoActual;
	
	 public void setEstadoActual(int estadoActual) {
		this.estadoActual = estadoActual;
	}
	public Estado(int estadoActual) {
		this.estadoActual=estadoActual;
	} 
	public abstract void getEstado();
	public abstract Estado cambiarEstado(int vida);

}
