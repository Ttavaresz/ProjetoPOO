package jogoexplosao;

import java.util.List;

/**
 *
 * @author lucastavares
 */

class Tabuleiro {
    public static final int NUMERO_DE_CASAS = 20;
//    private final char[] mapaDoTabuleiro = {"T", "P", "P", "P", "P",
//                                            "T", "P", "P", "P", "P", 
//                                            "T", "P", "P", "P", "P", 
//                                            "T", "P", "P", "P", "P"};
//                                         {"00", "01", "02", "03", "04", 
//                                          "05", "06", "07", "08", "09",
//                                          "10", "11", "12", "13", "14",
//                                          "15", "16", "17", "18", "19"};
    public Celula[] tabuleiro;
    
    public Tabuleiro() {
        tabuleiro = new Celula[NUMERO_DE_CASAS];
    }
    
    public void adicionarJogadores(List<Jogador> jogadores) {
        for(Jogador j : jogadores) {
            tabuleiro[j.getPosicao()].adicionaJogadorCelula(j);
        }
    }
    
//    public void criarTabuleiro() {
//        
//    }
}