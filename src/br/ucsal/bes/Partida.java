package br.ucsal.bes;

import java.util.Scanner;
public class Partida {
	private static String goOn;
    private static boolean verify, stopGame=false;
    private static final Scanner sc = new Scanner(System.in);
	private static Relogio r1, r2;
	private static Thread thP1, thP2;
    private static Integer numeroJogadasP1=0,numeroJogadasP2=0,numeroDePartidas=0;
	public static void main(String[] args) {
		novaPartida();
		iniciarPartida();
	}

	public static void novaPartida() {
		r1 = new Relogio();
		r2 = new Relogio();
		r1.setName("PLAYER 1");
		r2.setName("PLAYER 2");
		thP1 = new Thread(r1);
		thP2 = new Thread(r2);
		thP1.setDaemon(true);
		thP2.setDaemon(true);
		thP1.start();
		thP2.start();
		numeroJogadasP1=0;
		numeroJogadasP2=0;
	}

	public static void iniciarPartida() {
		numeroDePartidas++;
		print("Clique ENTER para Comecar");
		print("Caso queira PARAR a Partida, clique 'e'");
		goOn = sc.nextLine();
		print("ROUND DO " + r1.getName());
		r1.isStopped.set(false);
		numeroJogadasP1++;
		realizarPrimeiraJogada();
	}

	public static void realizarPrimeiraJogada() {
		goOn = sc.nextLine();
		goOn+=" ";
		verify = r1.isDone();
		stopGame = (goOn.charAt(0) == 'e' || verify);
	    if (!stopGame) {
		    r1.isStopped.set(true);
		    numeroJogadasP2++;
		    print("ROUND DO "+ r2.getName() + " - " + numeroJogadasP2 + " Jogadas");
		    r2.isStopped.set(false);
		    verifyInput(r2, numeroJogadasP2, r1, numeroJogadasP1);
			return;
	    }
		r1.isStopped.set(true);
		reiniciarPartida();
	}

	public static void verifyInput(Relogio nextPlayer, Integer numJogadasNext, Relogio previousPlayer, Integer numJogadasPrev) {
		goOn = sc.nextLine();
		goOn+=" ";
		verify = (nextPlayer.isDone() || previousPlayer.isDone());
		stopGame = (goOn.charAt(0) == 'e' || verify);
		if (!stopGame) {
			realizarJogadas(nextPlayer, numJogadasNext, previousPlayer, numJogadasPrev);
			return;
		}
		nextPlayer.isStopped.set(true); previousPlayer.isStopped.set(true);
		reiniciarPartida();
	}

	private static void realizarJogadas(Relogio previousPlayer, Integer numJogadasPrev, Relogio nextPlayer, Integer numJogadasNext) {
		previousPlayer.isStopped.set(true);
		print("ROUND DO "+nextPlayer.getName()+" - "+(++numJogadasNext)+" Jogadas");
		nextPlayer.isStopped.set(false);
		if(nextPlayer==r1){numeroJogadasP1++;} if(nextPlayer==r2){numeroJogadasP2++;}
		verifyInput(nextPlayer, numJogadasNext, previousPlayer, numJogadasPrev);
	}

	public static void reiniciarPartida() {
		print(	"O jogo acabou!\n");

		print(	"- Dados da partida:\n" +
				"-\tNumero de partidas jogadas : "+numeroDePartidas+"\n" +
				"-\tNumero de jogadas nessa partida:\n" +
				"-\t\t"+r1.getName()+ " : " + numeroJogadasP1 + " jogadas\n" +
				"-\t\t"+r2.getName()+ " : "+ numeroJogadasP2+ " jogadas\n" +
				"-\tTempo gasto de cada jogador:\n"+
				"-\t\t"+r1.getName()+ " : " + r1.getTempo() + " segundos\n" +
				"-\t\t"+r2.getName()+ " : "+ r2.getTempo() + " segundos\n");

		print(	"Deseja jogar outra Partida?\n" +
				"Para sim digite 's' e pressione ENTER\n" +
				"se nao, digite qualquer outra tecla e pressione ENTER.");
		try {
			char caracterReset = sc.next().charAt(0);
			sc.nextLine();
			if(Character.toLowerCase(caracterReset) == 's') {
				novaPartida();
				iniciarPartida();
				return;
			}
			System.out.println("Adeus!");
			System.exit(0);
		} catch (NullPointerException ignored) {}
	}

	public static void print(String string) {
		System.out.println(string);
	}
}
