package jogoexplosao;

 import java.util.Random;

/**
 *
 * @author lucastavares
 */

class Dado {
    public int valor;
    
    public int rolar() {
        Random random = new Random();
        int valor = random.nextInt(5) + 1;
        return valor;
    }
    
    public int rolarBomba() {
        Random randomBomba = new Random();
        int valor = randomBomba.nextInt(3) + 1;
        return valor;
    }
    
    @Override
    public String toString() {
        return "tirou " + valor + " no dado.";
    } 
}