package jogoexplosao;

/**
 *
 * @author lucastavares
 */
class Jogador {
    private final String nome;
    public Item passivo;
    public Item instantaneo;
    private final int posicaoInicial;
    private int posicao;
    public int posicaoAnterior;
    public int posicaoReal;
    public int checkpoint;
    
    public Jogador(String nome, int posicaoInicial) {
        this.nome = nome;
        posicaoReal = 0;
        posicao = posicaoInicial;
        this.posicaoInicial = posicaoInicial;
        posicaoAnterior = posicaoInicial;
//        passivo.nome = "";
//        instantaneo.nome = "";
    }
    
    public void setPassivo(Item i) {
        this.passivo = i;
        i.toString();
    }
//    
//    public void setInstantaneo() {
//        
//    }
//    
//    public Item getPassivo() {
//        return passivo;
//    }
//    
//    public Item getInstantaneo() {
//        return instantaneo;
//    }
    
    public String getNome() {
        return nome;
    }
    
    public void mover(int valorDado) {
        posicaoAnterior = posicao;
        posicao += valorDado;
        if(posicao >= 20) {
            int extra = posicao - 20;
            posicao = extra;
        }
        guardaCheckpoint();
        posicaoReal += valorDado;
        validacaoParaVencer(posicaoReal);
    }
    
    public void recalculaPosicaoRealCheckpoint() {
        int recalculaPosicaoReal;
        recalculaPosicaoReal = posicao - checkpoint;
        posicaoReal -= recalculaPosicaoReal;
    }
    
    public void setPosicao(int pCheckpoint) {
        this.posicao = pCheckpoint;
    }
    
    public int getPosicao() {
        return posicao;
    }
    
    public void guardaCheckpoint() {
        if(posicao >= 15) {
            checkpoint = 15;
        } else if(posicao >= 10) {
            checkpoint = 10;
        } else if(posicao >= 5) {
            checkpoint = 5;
        } else {
            checkpoint = 0;
        }
    }
    
//    public void descobreItem(int numeroDoItem) {
//
//    }
    
    public boolean validacaoParaVencer(int pReal){
        if(pReal >= 20) {
            posicao = posicaoInicial;
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return nome + " [Checkpoint: " + checkpoint + " | Posicao anterior: " + posicaoAnterior + " | Posicao atual: " + posicao + "]";
    }
}