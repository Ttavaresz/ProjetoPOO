/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogoexplosao;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author lucastavares
 */
class Celula {
    private final Set<Jogador> jogadores;
    private final char tipo;
    
    public Celula(char t) {
        jogadores = new HashSet<>();
        tipo = t;
    }
    
    public void adicionaJogadorCelula(Jogador j) {
        jogadores.add(j);
    }
    
    public char tipo() {
        return tipo;
    }
}
