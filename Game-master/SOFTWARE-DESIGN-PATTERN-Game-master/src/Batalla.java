import java.util.Scanner;

import Decorator.DecoradorElectrico;
import Decorator.DecoradorFuego;
import Decorator.EspadasCaos;
import Decorator.Hacha;
import Decorator.Punios;
import FactoryMethod.Enemigo;
import State.EstadoFuerte;
import Strategy.EstrategiaAgresiva;
import Strategy.EstrategiaDefensiva;
import Template_method.Ataque1;
import Template_method.Ataque2;
import Template_method.Ataque3;
import Template_method.AtaqueEnemigo;

public class Batalla {
		
	public int batalla(Jugador jugador , Enemigo zombie,Calculador punts) {
		int danioJugador = 0;
		int danioEnemigo = 0;
		int flag = 0;

		Scanner sc = new Scanner(System.in);
		Scanner sd = new Scanner(System.in);
		// первый мир: начало боя
		// создаём фабрику обычных противников
		
		
		
		int empiezaPrimero = (int) (Math.random()*2 + 1);

		
		

		
		int estrategiaJugador = 0;
		int estrategiaEnemigo = 0;
		//bucle primera batalla
		while (jugador.vida >= 0 && zombie.getVida() >= 0) {	

			if(empiezaPrimero==1 || flag != 0) {
				System.out.println("");
				System.out.println("Атака игрока:");
				
				System.out.println("Выберите оружие для атаки \"1\" Кулаки(10 урона), \"2\" Топор(15 урона), \"3\" Мечи(20 урона):");				
				int arma=sc.nextInt();
				System.out.println("Тип атаки: Огонь \"F\" Электричество \"E\":");
				String tipo = sd.nextLine();
				System.out.println("Выберите стратегию \"A\" АГРЕССИВНАЯ (+5 к урону), \"2\" ЗАЩИТНАЯ (+5 к здоровью):");
				String estrat = sd.nextLine();			
				
				if(estrat.equals("A")) {
					EstrategiaAgresiva est=new EstrategiaAgresiva();
					 estrategiaJugador=est.accion();
				}
				else {
					EstrategiaDefensiva est2= new EstrategiaDefensiva();
					estrategiaJugador=est2.accion();
					
				}
				
				if(arma==1) {
					Punios arm=new Punios();
					if(tipo.equals("E")) {
						
						DecoradorElectrico electrico=new DecoradorElectrico(arm);
						
						
						danioJugador = jugador.danio + electrico.calcularDanio(arm, zombie);
						
						
					}else {
						DecoradorFuego fuego=new DecoradorFuego(arm);
						
						danioJugador = jugador.danio + fuego.calcularDanio(arm, zombie);
					}
				}
				if(arma==2) {
					Hacha arm=new Hacha();
					if(tipo.equals("E")) {
						
						DecoradorElectrico electrico=new DecoradorElectrico(arm);
						
						danioJugador = jugador.danio + electrico.calcularDanio(arm, zombie);
					}else {
						DecoradorFuego fuego = new DecoradorFuego(arm);
						
						danioJugador = jugador.danio + fuego.calcularDanio(arm, zombie);
					}
				}
				if(arma==3) {
					EspadasCaos arm=new EspadasCaos();
					if(tipo.equals("E")) {
						
						DecoradorElectrico electrico=new DecoradorElectrico(arm);
						
						danioJugador = jugador.danio + electrico.calcularDanio(arm, zombie);
					}else {
						DecoradorFuego fuego = new DecoradorFuego(arm);
						
						danioJugador = jugador.danio + fuego.calcularDanio(arm, zombie);
					}
									
				}
				flag = 1;
			}
			
			punts.danioJugador=danioJugador;
			punts.danioestratJugador=estrategiaJugador;
			punts.vidaJugador=jugador.getVida();

			if(estrategiaJugador == -5) {
				if((zombie.getVida() - (danioJugador + (-estrategiaJugador)) <= 0)) 
					flag = 0;
			}else if((zombie.getVida() - danioJugador) <= 0)
					flag = 0;
				
			System.out.println("");
			
			if(empiezaPrimero == 2 || flag != 0 ) {
				
				System.out.println("Ataca el enemigo:");
				int ataqueRandom1 = (int) (Math.random()*2 + 1);
				if(ataqueRandom1==1) {
					
					EstrategiaAgresiva est=new EstrategiaAgresiva();
					 estrategiaEnemigo=est.accion();
					 
				}
				else {
					
					EstrategiaDefensiva est2= new EstrategiaDefensiva();
					estrategiaEnemigo=est2.accion();
					
				}
				int ataqueRandom2 = (int) (Math.random()*10 + 1);
					if((ataqueRandom2 >= 1) && (4 >= ataqueRandom2)) {
						// атака 1 имеет более высокую вероятность
					AtaqueEnemigo ataque = new Ataque1(zombie.getdanio());
					danioEnemigo = ataque.danioCausado();
					
				}
					if((ataqueRandom2 >= 5) && (8 >= ataqueRandom2)) {
						// атака 2
					AtaqueEnemigo ataque = new Ataque2(zombie.getdanio());
					danioEnemigo = ataque.danioCausado();
					
				}
					if((ataqueRandom2 >= 9) && ( ataqueRandom2 <= 10)) {
						// атака 3
					AtaqueEnemigo ataque = new Ataque3(zombie.getdanio());
					danioEnemigo = ataque.danioCausado();
					
				}
				
			
				flag = 1;
			}
			punts.danioEnemigo=danioEnemigo;
			punts.vidaEnemigo=zombie.getVida();
			punts.danioestratEnemigo=estrategiaEnemigo;
		
			//calcularresultado daño al jugador
			
			zombie.setVida(punts.calcularresultadodanio2());
			if(zombie.getVida() > 0)
				jugador.vida = punts.calcularresultadodanio1();
			System.out.println("");
			System.out.println("Жизни:");
			System.out.println(jugador.getVida());
			System.out.println(zombie.getVida());
			
			// eliminamos al zombie o al jugador
			if(zombie.getVida() <= 0) {
				System.out.println("Противник погиб");
				return 1;
			}else if(jugador.getVida() <= 0) {
				System.out.println("Игрок погиб");
				return 0;		
			}
				
			jugador.setState(jugador.state.cambiarEstado(jugador.getVida()));
			jugador.state.getEstado();
			empiezaPrimero = 0;
			}
			sc.close();
			sd.close();	
			return 0;
		}
	
	}

	

