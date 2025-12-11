package Strategy;

public class EstrategiaDefensiva implements InterfazEstrategia  {

	public EstrategiaDefensiva() {
		
	}
	public int accion() {
		
		System.out.println("Eligio una estrategia defensiva.");
		return 5;
		
	}

}
