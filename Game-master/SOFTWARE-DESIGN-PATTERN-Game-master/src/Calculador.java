
public class Calculador implements Cloneable {
	
	int vidaJugador;
	int danioJugador;
	int danioestratJugador;
	int vidaEnemigo;
	int danioEnemigo;
	int danioestratEnemigo;
	
	public Calculador() {
		
	}
	
	private static Calculador instancia = null;
	
	public static Calculador instance() {
		if(instancia==null) {
			instancia= new Calculador();
		}
		else {
			System.out.println("Ошибка: попытка создать Calculador повторно");
		
		}
		return instancia;
	}
	public int calcularresultadodanio1( ) {
		int aux = 0;
		if(danioestratEnemigo == -5 && danioestratJugador == 5)
			aux=(vidaJugador+danioestratJugador)-(danioEnemigo+(-danioestratEnemigo));
		else if(danioestratEnemigo == -5) 
			aux=vidaJugador-(danioEnemigo+(-danioestratEnemigo));
		else if(danioestratJugador == 5)
			aux=(vidaJugador+danioestratJugador)-(danioEnemigo);
		else aux = vidaJugador - danioEnemigo;
		return aux;
	}
	public int calcularresultadodanio2() {
		int aux = 0;
		if(danioestratEnemigo == 5 && danioestratJugador == -5)
			aux=(vidaEnemigo+danioestratEnemigo)-(danioJugador+ (-danioestratJugador));
		else if(danioestratJugador == -5)
			aux=vidaEnemigo-(danioJugador+(-danioestratJugador));
		else if(danioestratJugador == 5)
			aux=(vidaEnemigo+danioestratEnemigo)-danioEnemigo;
		else aux = vidaEnemigo - danioJugador;

		
		return aux;
	}
}
