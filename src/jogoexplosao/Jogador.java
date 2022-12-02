package jogoexplosao;

/**
 *
 * @author lucastavares
 */
class Jogador {
    private final String nome;
    private Item passivo;
    private Item instantaneo;
    private boolean podeMover;
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
        this.podeMover = true;
    }
    
    public void setPassivo(Item i) {
        this.passivo = i;
    }
    
    public void setInstantaneo(Item i) {
        this.instantaneo = i;
    }
    
    public Item getPassivo() {
        return passivo;
    }
    
    public Item getInstantaneo() {
        return instantaneo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void mover(int valorDado) {
        if(instantaneo != null) {
            if(instantaneo.nome.equals("Velocidade")) {
                System.out.print("\nEntrou no if da velocidade(APAGAR)\n");
                valorDado *= 2;
            }
        }
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
    
    public void bloquear() {
        podeMover = false;
    }
    
    public void desbloquear() {
        podeMover = true;
    }
    
    public boolean podeJogar() {
        return podeMover;
    }
    
    public void recalculaPosicaoRealCheckpoint() {
        int recalculaPosicaoReal;
        recalculaPosicaoReal = posicao - checkpoint;
        posicaoReal -= recalculaPosicaoReal;
    }
    
    public void pularParaProximoCheckpoint() {
        posicaoReal += 5;
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