import java.util.Scanner;

import FactoryMethod.BossFuego;
import FactoryMethod.BossHielo;
import FactoryMethod.BossNormal;
import FactoryMethod.FabricaFuego;
import FactoryMethod.FabricaHielo;
import FactoryMethod.FabricaNormal;
import FactoryMethod.TrollFuego;
import FactoryMethod.TrollHielo;
import FactoryMethod.TrollNormal;
import FactoryMethod.ZombieFuego;
import FactoryMethod.ZombieHielo;
import FactoryMethod.ZombieNormal;
import State.EstadoFuerte;

public class main {

    public static void main(String[] args) {

        Jugador jugador=new Jugador(100,10);

        FabricaNormal fabricanormal= new FabricaNormal();

        ZombieNormal zombie =(ZombieNormal) fabricanormal.crearZombie();
        TrollNormal troll =(TrollNormal) fabricanormal.crearTroll();
        BossNormal boss=(BossNormal)fabricanormal.crearBoss();

        FabricaHielo fabricahielo=new FabricaHielo();

        ZombieHielo zombieHielo=(ZombieHielo) fabricahielo.crearZombie();
        TrollHielo trollHielo=(TrollHielo) fabricahielo.crearTroll();
        BossHielo bossHielo=(BossHielo) fabricahielo.crearBoss();

        FabricaFuego fabricafuego=new FabricaFuego();

        ZombieFuego zombieFuego=(ZombieFuego) fabricafuego.crearZombie();
        TrollFuego trollFuego=(TrollFuego) fabricafuego.crearTroll();
        BossFuego bossFuego=(BossFuego) fabricafuego.crearBoss();

        Batalla batalla1 = new Batalla();
        Calculador punts=Calculador.instance();
        int flag = 1;
        int vidaTotal = 100;
        Scanner sc = new Scanner(System.in);

        while(flag != 0) {

            System.out.println("Вы вошли в первый мир:");
            System.out.println("");
            System.out.println("Вы сражаетесь с зомби:");
            flag = batalla1.batalla(jugador, zombie,punts);

            if(flag==1){
                System.out.println("Вы сражаетесь с троллем:");
                flag = batalla1.batalla(jugador, troll, punts);

                System.out.println("");
            }
            if(flag == 1) {
                System.out.println("Тролль сбросил камень жизни, который добавил вам 45 единиц здоровья.");
                jugador.setVida(jugador.getVida() + 45);
                System.out.println("");

                System.out.println("Вы сражаетесь с боссом:");
                flag = batalla1.batalla(jugador, boss, punts);
            }
            if(flag == 1) {
                System.out.println("Вы прошли этот мир.");
                System.out.println("");
                jugador.setVida(vidaTotal);
                jugador.setState(new EstadoFuerte(3)); // восстанавливает жизнь и состояние после завершения первого мира
                System.out.println("Поздравляем! Преодолев первый мир, вы открываете одну из двух привилегий: 1) +5 к урону 2) +20 к здоровью:");
                int decision = sc.nextInt();
                if(decision == 1)
                    jugador.setDanio(jugador.getDanio() + 5);
                else {
                    jugador.setVida(jugador.getVida() + 20);
                    vidaTotal += 20;
                }
                System.out.println("");
                System.out.println("Вы вошли во второй мир:");
                System.out.println("Вы сражаетесь с ледяным зомби:");
                flag = batalla1.batalla(jugador, zombieHielo,punts);
            }
            if(flag == 1) {
                System.out.println("Вы сражаетесь с ледяным троллем:");
                flag = batalla1.batalla(jugador, trollHielo, punts);
            }
            if(flag == 1) {
                System.out.println("");
                System.out.println("Тролль сбросил камень жизни, который добавил вам 45 единиц здоровья");
                jugador.setVida(jugador.getVida() + 45);

                System.out.println("Вы сражаетесь с ледяным боссом:");
                flag = batalla1.batalla(jugador, bossHielo, punts);
            }
            if(flag == 1) {
                System.out.println("Вы прошли этот мир");
                jugador.setVida(vidaTotal); // восстанавливает жизнь после завершения мира и состояние
                jugador.setState(new EstadoFuerte(3));
                System.out.println("Поздравляем! Преодолев второй мир, вы открываете одну из двух привилегий: \"1\" +5 к урону \"2\" +20 к здоровью");
                int decision2 = sc.nextInt();
                if(decision2 == 1)
                    jugador.setDanio(jugador.getDanio() + 5);
                else {
                    jugador.setVida(jugador.getVida() + 20);
                    vidaTotal += 20;   
                }

                System.out.println("Вы вошли в третий мир:");
                System.out.println("Вы сражаетесь с огненным зомби:");
                flag = batalla1.batalla(jugador, zombieFuego,punts);
            }
            if(flag == 1) {
                System.out.println("Вы сражаетесь с огненным троллем:");
                flag = batalla1.batalla(jugador, trollFuego, punts);
            }
            if(flag == 1) {
                System.out.println("Тролль сбросил камень жизни, который добавил вам 45 единиц здоровья");
                jugador.setVida(jugador.getVida() + 45);
                System.out.println(jugador.getVida());

                System.out.println("Вы сражаетесь с огненным боссом:");
                flag = batalla1.batalla(jugador, bossFuego, punts);
            }
            if(flag == 1) {
                System.out.println("Вы прошли последний мир");

                System.out.println("Поздравляем, вы прошли игру");
                flag = 0;
            }
        }
        sc.close();
    }
}
