package jogoexplosao;

/**
 *
 * @author lucastavares
 */

public abstract class Item {
    public String nome;
    
    public Item() {
    }
    
    public void getNome(String nome) {
        this.nome = nome;
    }
}
