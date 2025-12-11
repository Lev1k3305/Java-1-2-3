package Strategy;

public class EstrategiaAgresiva implements InterfazEstrategia{
	
	public EstrategiaAgresiva() {
		
	}
	public int accion() {
		System.out.println("Eligio una estrategia agresiva.");
		return -5;
	}
}
