package jogoexplosao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author lucastavares
 */

public final class JogoExplosao {
    private final List<Jogador> jogadores;
    private int jogadorDaRodada;
//    private final Tabuleiro tabuleiro;
    private final Dado dado;
    private int nJogadores;
    
    public JogoExplosao() {
        jogadorDaRodada = 0;
        nJogadores = 0;
        jogadores = new ArrayList<>();
//        tabuleiro = new Tabuleiro();
        dado = new Dado();
    }
    
    private Jogador proximoJogador() {
        jogadorDaRodada++;
        if(jogadorDaRodada >= jogadores.size()) {
            jogadorDaRodada = 0;
        }
        return jogadores.get(jogadorDaRodada);
    }
    
    public void rodada() {
        Jogador j = jogadores.get(jogadorDaRodada);
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\n\nRodada do jogador " + j.getNome() + ".");
        if(j.podeJogar()) {
            System.out.print("\nPara girar o dado digite [rolarDado]: ");
            String acao = sc.nextLine();
            if(acao.equals("rolar")) {

                int valorDado = dado.rolar();
                if (valorDado < 4) {
                    j.mover(valorDado);
                    if(j.getInstantaneo() != null) {
                        if(j.getInstantaneo().getNome().equals("Velocidade")) {
                            System.out.print("\nO jogador " + j.getNome() + " usou o item [Velocidade] e andou o dobro de casas.\n");
                        }
                    }
                    System.out.print(j + " tirou " + valorDado + " no dado.");
                    System.out.print("\n\t[Item Passivo: " + (j.getPassivo()==null ? " " : j.getPassivo()) + " | Item Instantaneo: " + (j.getInstantaneo()==null ? " " : j.getInstantaneo()) + "]");            
                } else if (valorDado < 6) {
                    System.out.print("Jogador [" + j.getNome() + "] tirou bomba no dado.");
                    bomba();
                } else {
                    int numeroPowerupSorteado = dado.rolar();
                    switch(numeroPowerupSorteado) {
                        case 1: // PRONTO
                            AsasDeIcaro pu1 = new AsasDeIcaro();
                            j.setPassivo(pu1);
                            System.out.print("Parabens jogador " + j.getNome() + " voce ganhou um item [" + pu1.toString() + "] do tipo passivo.");
                            break;
                        case 2:
                            Escudo pu2 = new Escudo(); // Evita ser derrubado pelo empurrar && evita ser imobilizado.
                            j.setPassivo(pu2);
                            System.out.print("Parabens jogador " + j.getNome() + " voce ganhou um item [" + pu2.toString() + "] do tipo passivo.");
                            break;
                        case 3:
                            Empurrar pu3 = new Empurrar();
                            j.setPassivo(pu3);
                            System.out.print("Parabens jogador " + j.getNome() + " voce ganhou um item [" + pu3.toString() + "] do tipo passivo.");
                            break;
                        case 4: // PRONTO
                            Imobilizar pu4 = new Imobilizar();
                            j.setInstantaneo(pu4);
                            System.out.print("O jogador " + j.getNome() + " ganhou o item [" + pu4.toString() + "] e deixou ");
                            for(Jogador jfor : jogadores) {
                                if(jfor.getNome() != j.getNome()) {
                                    jfor.bloquear();
                                    System.out.print("o jogador " + jfor.getNome() + " uma rodada sem jogar.");
                                    break;
                                }
                            }
                            break;
                        case 5: // PRONTO
                            Velocidade pu5 = new Velocidade();
                            j.setInstantaneo(pu5);
                            System.out.print("Parabens jogador " + j.getNome() + " voce ganhou um item [" + pu5.toString() + "] do tipo instantaneo.");
                            break;
                        case 6:
                            Sabotar pu6 = new Sabotar();
                            j.setInstantaneo(pu6);
                            System.out.print("Parabens jogador " + j.getNome() + " voce ganhou um item [" + pu6.toString() + "] do tipo instantaneo.");
                            break;
                    }
                }
            }

            boolean validaVitoria = j.validacaoParaVencer(j.posicaoReal);
            if (validaVitoria == true) {
                vencedor(j.getNome());
            } else {
                proximoJogador();
                rodada();
            }
        } else {
            System.out.print("O jogador " + j.getNome() + "ficara sem rodar nessa rodada.");
            j.desbloquear();
            proximoJogador();
            rodada();
        }
    }
    
    public void vencedor(String vencedor) {
        System.out.print("\n\nJogador " + vencedor + " ganhou o jogo!\n");
    }
    
    public void bomba() {
        Jogador j = jogadores.get(jogadorDaRodada);
        int ladoBomba = dado.rolarBomba();
        switch (ladoBomba) {
            case 1:
                System.out.print("\n\nA BOMBA EXPLODIU!!!\nO LADO DESTRUIDO FOI O DE CIMA\n");
                for(int i = 0; i < nJogadores; i++) {
                    Jogador j1 = jogadores.get(jogadorDaRodada);
                    if(j1.getPosicao() > 0 && j1.getPosicao() < 5) {
                        int pAnterior = j1.getPosicao();
                        j1.recalculaPosicaoRealCheckpoint();
                        j1.setPosicao(j1.checkpoint);
                        if(j1.getPassivo() != null) {
                            if(j1.getPassivo().getNome().equals("AsasDeIcaro")) {
                                j1.setPosicao(j1.checkpoint + 5);
                                j1.setPassivo(null);
                                j1.pularParaProximoCheckpoint();
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " a ponte caiu,\nmas o seu item [AsasDeIcaro] o levou para a proxima torre.");
                            } else {
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                            }
                        } else {
                            System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                        }
                    }
                    proximoJogador();
                }
                break;
            case 2:
                System.out.print("\n\nA BOMBA EXPLODIU!!!\nO LADO DESTRUIDO FOI O DA DIREITA\n");
                for(int i = 0; i < nJogadores; i++) {
                    Jogador j1 = jogadores.get(jogadorDaRodada);
                    if(j1.getPosicao() > 5 && j1.getPosicao() < 10) {
                        int pAnterior = j1.getPosicao();
                        j1.recalculaPosicaoRealCheckpoint();
                        j1.setPosicao(j1.checkpoint);
                        if(j1.getPassivo() != null) {
                            if(j1.getPassivo().getNome().equals("AsasDeIcaro")) {
                                j1.setPosicao(j1.checkpoint + 5);
                                j1.setPassivo(null);
                                j1.pularParaProximoCheckpoint();
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " a ponte caiu,\nmas o seu item [AsasDeIcaro] o levou para a proxima torre.");
                            } else {
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                            }
                        } else {
                            System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                        }
                    }
                    proximoJogador();
                }
                break;
            case 3:
                System.out.print("\n\nA BOMBA EXPLODIU!!!\nO LADO DESTRUIDO FOI O DE BAIXO\n");
                for(int i = 0; i < nJogadores; i++) {
                    Jogador j1 = jogadores.get(jogadorDaRodada);
                    if(j1.getPosicao() > 10 && j1.getPosicao() < 15) {
                        int pAnterior = j1.getPosicao();
                        j1.recalculaPosicaoRealCheckpoint();
                        j1.setPosicao(j1.checkpoint);
                        if(j1.getPassivo() != null) {
                            if(j1.getPassivo().getNome().equals("AsasDeIcaro")) {
                                j1.setPosicao(j1.checkpoint + 5);
                                j1.setPassivo(null);
                                j1.pularParaProximoCheckpoint();
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " a ponte caiu,\nmas o seu item [AsasDeIcaro] o levou para a proxima torre.");
                            } else {
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                            }
                        } else {
                            System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                        }
                    }
                    proximoJogador();
                }
                break;
            case 4:
                System.out.print("\n\nA BOMBA EXPLODIU!!!\nO LADO DESTRUIDO FOI O DA ESQUERDA\n");
                for(int i = 0; i < nJogadores; i++) {
                    Jogador j1 = jogadores.get(jogadorDaRodada);
                    if(j1.getPosicao() > 15 && j1.getPosicao() < 20) {
                        int pAnterior = j1.getPosicao();
                        j1.recalculaPosicaoRealCheckpoint();
                        j1.setPosicao(j1.checkpoint);
                        if(j1.getPassivo() != null) {
                            if(j1.getPassivo().getNome().equals("AsasDeIcaro")) {
                                j1.setPosicao(j1.checkpoint + 5);
                                j1.setPassivo(null);
                                j1.pularParaProximoCheckpoint();
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " a ponte caiu,\nmas o seu item [AsasDeIcaro] o levou para a proxima torre.");
                            } else {
                                System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                            }
                        } else {
                            System.out.print("\nO jogador " + j1.getNome() + " estava na posicao " + pAnterior + " e caiu da ponte!\n" + "Voltando para o checkpoint " + j1.checkpoint);
                        }
                    }
                    proximoJogador();
                }
                break;
        }
    }

    public void configuracaoInicial() {
        Scanner sc = new Scanner(System.in);
        
        // Descobrir a quantidade de Jogadores
        System.out.print("Digite o numero de jogadores [2-4]: ");
        nJogadores = sc.nextInt();
        while(nJogadores < 2 || nJogadores > 4) {
            System.out.print("Valor inválido.\n");
            System.out.print("Digite o numero de jogadores [2-4]: ");
            nJogadores = sc.nextInt();
        }
        
        sc.nextLine();
        
        // Colocar nome nos jogadores
        int posicaoInicial = 0;
        for(int i = 0; i < nJogadores; i++) {
            System.out.print("Digite o nome do jogador " + (i+1) + ": ");
            String nomeDoJogador = sc.nextLine();
            Jogador jogador = new Jogador(nomeDoJogador, posicaoInicial);
            jogadores.add(jogador);
            posicaoInicial += 5;
        }
        rodada();
    }

    public static void main(String[] args) {
        JogoExplosao jogo = new JogoExplosao();
        jogo.configuracaoInicial();
    }
}