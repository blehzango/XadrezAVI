package br.ucsal.bes;

import java.util.concurrent.atomic.AtomicBoolean;
public class Relogio implements Runnable{
    private Integer tempo = 500;
    private String name="";
    private static final Integer tempoTotal = 500;
    private AtomicBoolean done = new AtomicBoolean(false);
    public AtomicBoolean isStopped=new AtomicBoolean(true);

    @Override
    public void run() {
        while (!done.get()) {
            while (!isStopped.get()) {
                contar();
            }
        }
    }
    
    public void contar() {
         try {
             if (tempo>0) {
                 Partida.print( tempo + " segundos restantes");
                 Thread.sleep(1000);
                 tempo--;
             } else if (tempo==0) {
                 tempo--;
                 Partida.print( name + " | acabou o tempo!");
             } else {
                 done.set(true);
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    }

    public int getTempo(){
        if(tempo<=0) {
            return tempoTotal;
        }
        return tempoTotal-tempo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done.get();
    }
}
