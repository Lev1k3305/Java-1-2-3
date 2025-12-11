package FactoryMethod;

public class FabricaFuego implements FabricaEnemigos {
		
		private String tipo ="Fuego";
		public FabricaFuego() {
			
		}

		public Zombie crearZombie() {
			
			return new ZombieFuego(7,50,tipo);
		}
		
		public Troll crearTroll() {
			
			return new TrollFuego(12,65,tipo);
		}

		public Boss crearBoss() {
			
			return new BossFuego(17,80,tipo);
		}
}
