package ui;

import model.Domus;
import model.casa.Casa;
import model.casa.Divisao;
import model.dispositivo.ColunaSom;
import model.dispositivo.Dispositivo;
import model.dispositivo.Lampada;
import model.dispositivo.Termostato;
import model.utilizador.Utilizador;
import exceptions.CasaAlreadyExistsException;
import exceptions.CasaDoesNotExistException;
import exceptions.DispositivoAlreadyExistsException;
import exceptions.DispositivoDoesNotExistException;
import exceptions.DivisaoAlreadyExistsException;
import exceptions.DivisaoDoesNotExistException;
import exceptions.UtilizadorAlreadyExistsException;
import exceptions.UtilizadorDoesNotExistException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DomusApp {

    // o modelo
    private Domus model;

    // scanner
    private Scanner sc;

    /**
     * Construtor que cria os menus e o model
     */
    public DomusApp() {
        this.model = new Domus();
        this.sc = new Scanner(System.in);
    }

    /**
     * Método que executa o menu principal.
     * Coloca a interface em execução.
     */
    public void run() {
        NewMenu menu = new NewMenu(new String[] {
                "Gestão de Casas",
                "Gestão de Utilizadores",
                "Estatísticas",
                "Simulação e Cenários",
                "Guardar estado",
                "Carregar estado"
        });

        menu.setHandler(1, () -> gestaoCasas());
        menu.setHandler(2, () -> gestaoUtilizadores());
        menu.setHandler(3, () -> estatisticas());
        menu.setHandler(4, () -> simulacaoCenarios());
        menu.setHandler(5, () -> guardarEstado());
        menu.setHandler(6, () -> carregarEstado());

        menu.run();
    }

    // =========================================================
    // GESTAO DE CASAS(gestao casas;gestao divisoes;gestao dispositivos)
    // =========================================================

    private void gestaoCasas() {
        NewMenu menuCasas = new NewMenu(new String[] {
                "Adicionar Casa",
                "Listar Casas",
                "Remover Casa",
                "Gerir Divisões de uma Casa"
        });

        menuCasas.setHandler(1, () -> adicionarCasa());
        menuCasas.setHandler(2, () -> listarCasas());
        menuCasas.setHandler(3, () -> removerCasa());
        menuCasas.setHandler(4, () -> gerirDivisoes());

        menuCasas.run();
    }

    private void adicionarCasa() {
        System.out.print("Id da casa: ");
        String id = sc.nextLine();
        System.out.print("Nome da casa: ");
        String nome = sc.nextLine();

        Casa c = new Casa();
        c.setIdCasa(id);
        c.setNomeCasa(nome);

        try {
            this.model.adicionarCasa(c);
            System.out.println("Casa adicionada com sucesso.");
        } catch (CasaAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarCasas() {
        System.out.println(this.model.listarCasas());
    }

    private void removerCasa() {
        System.out.print("Id da casa a remover: ");
        String id = sc.nextLine();

        try {
            this.model.removerCasa(id);
            System.out.println("Casa removida com sucesso.");
        } catch (CasaDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /* GESTAO DIVISOES */

    private void gerirDivisoes() {
        System.out.print("Id da casa: ");
        String idCasa = sc.nextLine();

        NewMenu menuDivisoes = new NewMenu(new String[] {
                "Adicionar Divisão",
                "Listar Divisões",
                "Remover Divisão",
                "Gerir Dispositivos de uma Divisão"
        });

        menuDivisoes.setHandler(1, () -> adicionarDivisao(idCasa));
        menuDivisoes.setHandler(2, () -> listarDivisoes(idCasa));
        menuDivisoes.setHandler(3, () -> removerDivisao(idCasa));
        menuDivisoes.setHandler(4, () -> gerirDispositivos(idCasa));

        menuDivisoes.run();
    }

    private void adicionarDivisao(String idCasa) {
        System.out.print("Id da divisão: ");
        String id = sc.nextLine();
        System.out.print("Nome da divisão: ");
        String nome = sc.nextLine();

        Divisao d = new Divisao();
        d.setIdDivisao(id);
        d.setNomeDivisao(nome);

        try {
            this.model.adicionarDivisao(idCasa, d);
            System.out.println("Divisão adicionada com sucesso.");
        } catch (CasaDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (DivisaoAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

    }

    private void listarDivisoes(String idCasa) {
        try {
            System.out.println(this.model.listarDivisoes(idCasa));
        } catch (CasaDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerDivisao(String idCasa) {
        System.out.print("Id da divisão a remover: ");
        String id = sc.nextLine();

        try {
            this.model.removerDivisao(idCasa, id);
            System.out.println("Divisão removida com sucesso.");
        } catch (CasaDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (DivisaoDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /* GESTAO DISPOSITIVOS */

    private void gerirDispositivos(String idCasa) {
        System.out.print("Id da divisão: ");
        String idDivisao = sc.nextLine();

        NewMenu menuDispositivos = new NewMenu(new String[] {
            "Adicionar Dispositivo",
            "Listar Dispositivos",
            "Remover Dispositivo",
            "Operar Dispositivo"
        });

        menuDispositivos.setHandler(1, () -> adicionarDispositivo(idCasa, idDivisao));
        menuDispositivos.setHandler(2, () -> listarDispositivos(idCasa, idDivisao));
        menuDispositivos.setHandler(3, () -> removerDispositivo(idCasa, idDivisao));
        menuDispositivos.setHandler(4, () -> operarDispositivo(idCasa, idDivisao));

        menuDispositivos.run();
    }


    private void adicionarDispositivo(String idCasa, String idDivisao) {
        NewMenu menuTipo = new NewMenu(new String[] {
            "Lâmpada",
            "Termostato",
            "Coluna de Som"
        });

        menuTipo.setHandler(1, () -> {
            try {
                adicionarLampada(idCasa, idDivisao);
            } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        });

        menuTipo.setHandler(2, () -> {
            try {
                adicionarTermostato(idCasa, idDivisao);
            } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        });

        menuTipo.setHandler(3, () -> {
            try {
                adicionarColunaSom(idCasa, idDivisao);
            } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        });

        menuTipo.run();
    }

    // O padrão é sempre o mesmo — cada classe(no caso DomusApp) verifica se a
    // entidade existe e delega para a classe abaixo(Domus).

    private void adicionarLampada(String idCasa, String idDivisao)
            throws CasaDoesNotExistException, DivisaoDoesNotExistException, DispositivoAlreadyExistsException {
        System.out.print("Id: ");
        String id = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Consumo por hora (Wh): ");
        double consumo = Double.parseDouble(sc.nextLine());

        Lampada l = new Lampada();
        l.setIdDispositivo(id);
        l.setMarca(marca);
        l.setModelo(modelo);
        l.setConsumoPorHora(consumo);

        this.model.adicionarDispositivo(idCasa, idDivisao, l);
        System.out.println("Lâmpada adicionada com sucesso.");
    }

    private void adicionarTermostato(String idCasa, String idDivisao)
            throws CasaDoesNotExistException, DivisaoDoesNotExistException, DispositivoAlreadyExistsException {
        System.out.print("Id: ");
        String id = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Consumo por hora (Wh): ");
        double consumo = Double.parseDouble(sc.nextLine());
        System.out.print("Temperatura atual: ");
        double tempAtual = Double.parseDouble(sc.nextLine());
        System.out.print("Temperatura alvo: ");
        double tempAlvo = Double.parseDouble(sc.nextLine());

        Termostato t = new Termostato();
        t.setIdDispositivo(id);
        t.setMarca(marca);
        t.setModelo(modelo);
        t.setConsumoPorHora(consumo);
        t.setTempAtual(tempAtual);
        t.setTempAlvo(tempAlvo);

        this.model.adicionarDispositivo(idCasa, idDivisao, t);
        System.out.println("Termostato adicionado com sucesso.");
    }


    private void adicionarColunaSom(String idCasa, String idDivisao)
        throws CasaDoesNotExistException, DivisaoDoesNotExistException, DispositivoAlreadyExistsException {
            System.out.print("Id: ");
            String id = sc.nextLine();
            System.out.print("Marca: ");
            String marca = sc.nextLine();
            System.out.print("Modelo: ");
            String modelo = sc.nextLine();
            System.out.print("Consumo por hora (Wh): ");
            double consumo = Double.parseDouble(sc.nextLine());

            ColunaSom c = new ColunaSom();
            c.setIdDispositivo(id);
            c.setMarca(marca);
            c.setModelo(modelo);
            c.setConsumoPorHora(consumo);

            this.model.adicionarDispositivo(idCasa, idDivisao, c);
            System.out.println("Coluna de Som adicionada com sucesso.");
    }


























    private void removerDispositivo(String idCasa, String idDivisao) {
        System.out.print("Id do dispositivo a remover: ");
        String id = sc.nextLine();

        try {
            this.model.removerDispositivo(idCasa, idDivisao, id);
            System.out.println("Dispositivo removido com sucesso.");
        } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarDispositivos(String idCasa, String idDivisao) {
        try {
            System.out.println(this.model.listarDispositivos(idCasa, idDivisao));
        } catch (CasaDoesNotExistException | DivisaoDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    
    private void operarDispositivo(String idCasa, String idDivisao) {
        System.out.print("Id do dispositivo: ");
        String idDispositivo = sc.nextLine();
        System.out.print("Ação (Ligar/Desligar): ");
        String estado = sc.nextLine();
        
        try {
            boolean accao = estado.equals("Ligar");
            this.model.alteraEstadoDispositivo(idCasa, idDivisao, idDispositivo, accao);
            System.out.println("Estado alterado com sucesso.");
        } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    /* PARA FAZERMOS MAIS OPERACOES TINHAMOS ISTO MAS NAO DEU TEMPO PARA TESTAR
    
    private void operarDispositivo(String idCasa, String idDivisao) {
    System.out.print("Id do dispositivo: ");
    String idDispositivo = sc.nextLine();

    NewMenu menuOperar = new NewMenu(new String[] {
        "Ligar",
        "Desligar",
        "Aumentar Volume (Coluna)",
        "Diminuir Volume (Coluna)",
        "Aumentar Temperatura (Termostato)",
        "Diminuir Temperatura (Termostato)"
    });

    menuOperar.setHandler(1, () -> operar(idCasa, idDivisao, idDispositivo, true));
    menuOperar.setHandler(2, () -> operar(idCasa, idDivisao, idDispositivo, false));
    menuOperar.setHandler(3, () -> alterarVolume(idCasa, idDivisao, idDispositivo, true));
    menuOperar.setHandler(4, () -> alterarVolume(idCasa, idDivisao, idDispositivo, false));
    menuOperar.setHandler(5, () -> alterarTemperatura(idCasa, idDivisao, idDispositivo, true));
    menuOperar.setHandler(6, () -> alterarTemperatura(idCasa, idDivisao, idDispositivo, false));

    menuOperar.run();
}

private void operar(String idCasa, String idDivisao, String idDispositivo, boolean accao) {
    try {
        this.model.alteraEstadoDispositivo(idCasa, idDivisao, idDispositivo, accao);
        System.out.println("Estado alterado com sucesso.");
    } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoDoesNotExistException e) {
        System.out.println(e.getMessage());
    }
}

private void alterarVolume(String idCasa, String idDivisao, String idDispositivo, boolean aumentar) {
    try {
        this.model.alterarVolume(idCasa, idDivisao, idDispositivo, aumentar);
        System.out.println("Volume alterado com sucesso.");
    } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoDoesNotExistException e) {
        System.out.println(e.getMessage());
    }
}

private void alterarTemperatura(String idCasa, String idDivisao, String idDispositivo, boolean aumentar) {
    try {
        this.model.alterarTemperatura(idCasa, idDivisao, idDispositivo, aumentar);
        System.out.println("Temperatura alterada com sucesso.");
    } catch (CasaDoesNotExistException | DivisaoDoesNotExistException | DispositivoDoesNotExistException e) {
        System.out.println(e.getMessage());
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    */















    // =========================================================
    // GESTAO DE UTILIZADORES
    // =========================================================

    private void gestaoUtilizadores() {
        NewMenu menuUtilizadores = new NewMenu(new String[] {
                "Adicionar Utilizador",
                "Listar Utilizadores",
                "Remover Utilizador"
        });

        menuUtilizadores.setHandler(1, () -> adicionarUtilizador());
        menuUtilizadores.setHandler(2, () -> listarUtilizadores());
        menuUtilizadores.setHandler(3, () -> removerUtilizador());

        menuUtilizadores.run();
    }

    private void adicionarUtilizador() {
        System.out.print("Id: ");
        String id = sc.nextLine();
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Papel (ADMINISTRADOR/DIARIO): ");
        String papel = sc.nextLine();

        Utilizador u = new Utilizador();
        u.setIdUtilizador(id);
        u.setNome(nome);
        u.setEmail(email);
        u.setPapel(papel);

        // associar casas ao utilizador
        System.out.print("Id da casa a associar (enter para terminar): ");
        String idCasa = sc.nextLine();
        while (!idCasa.isEmpty()) {
            u.adicionarCasa(idCasa);
            System.out.print("Id da casa a associar (enter para terminar): ");
            idCasa = sc.nextLine();
        }

        try {
            this.model.adicionarUtilizador(u);
            System.out.println("Utilizador adicionado com sucesso.");
        } catch (UtilizadorAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarUtilizadores() {
        System.out.println(this.model.listarUtilizadores());
    }

    private void removerUtilizador() {
        System.out.print("Id do utilizador a remover: ");
        String id = sc.nextLine();
        try {
            this.model.removerUtilizador(id);
            System.out.println("Utilizador removido com sucesso.");
        } catch (UtilizadorDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    // =========================================================
    // ESTATISTICAS
    // =========================================================

    private void estatisticas() {
        NewMenu menuEstat = new NewMenu(new String[] {
                "Casa que mais consome",
                "Top 3 dispositivos por ativações",
                "Top 3 dispositivos por tempo",
                "Top 3 divisões com mais dispositivos"
        });

        menuEstat.setHandler(1, () -> casaMaisConsumidora());
        menuEstat.setHandler(2, () -> top3DispositivosPorAtivacoes());
        menuEstat.setHandler(3, () -> top3DispositivosPorTempo());
        menuEstat.setHandler(4, () -> top3Divisoes());


        menuEstat.run();
    }




    private void casaMaisConsumidora() {
        Casa c = this.model.obterCasaMaisConsumidora();
        if (c == null)
            System.out.println("Sem casas no sistema.");
        else
            System.out.println("Casa: " + c.getNomeCasa() + " | Consumo: " + c.getConsumoTotal() + " Wh");
    }

    

    private void top3DispositivosPorAtivacoes() {
        System.out.print("Id da casa: ");
        String idCasa = sc.nextLine();
        List<Dispositivo> list = this.model.top3DispositivosPorActivacoes(idCasa);
        for (Dispositivo d : list) {
            System.out.println("Id: " + d.getIdDispositivo() +
                            " | Marca: " + d.getMarca() +
                            " | Modelo: " + d.getModelo() +
                            " | Ativações: " + d.getNumeroActivacoes());
        }
    }



    private void top3DispositivosPorTempo() {
        System.out.print("Id da casa: ");
        String idCasa = sc.nextLine();
        List<Dispositivo> list = this.model.top3DispositivosPorTempo(idCasa);
        for (Dispositivo d : list) {
            System.out.println("Id: " + d.getIdDispositivo() + 
                            " | Marca: " + d.getMarca() +
                            " | Modelo: " + d.getModelo() + 
                            " | Tempo: " + d.getTempoUso() + " ms");
        }
    }




    private void top3Divisoes() {
        List<Divisao> list = this.model.top3DivisoesComMaisDispositivos();
        for (Divisao d : list) {
            System.out.println("Divisão: " + d.getNomeDivisao() + " | Dispositivos: " + d.getNumeroDispositivos());
        }
    }

    // =========================================================
    // SIMULACAO E CENARIOS (PONTO 8.3)
    // =========================================================

    private void simulacaoCenarios() {
        System.out.println("Hora Atual do Sistema: " + this.model.getRelogio());
        NewMenu menuSim = new NewMenu(new String[] {
                "Avançar no Tempo",
                "Executar Cenário Manual",
                "Carregar Dados de Teste Ponto 8.3 (Cria Cenários e Escalonamentos base)"
        });

        menuSim.setHandler(1, () -> avancarTempo());
        menuSim.setHandler(2, () -> executarCenario());
        menuSim.setHandler(3, () -> carregarDadosTeste83());

        menuSim.run();
    }

    private void avancarTempo() {
        System.out.print("Quantas horas avançar: ");
        int h = Integer.parseInt(sc.nextLine());
        System.out.print("Quantos minutos avançar: ");
        int m = Integer.parseInt(sc.nextLine());
        this.model.avancarTempo(h, m);
        System.out.println("Tempo avançado com sucesso. Nova hora: " + this.model.getRelogio());
    }

    private void executarCenario() {
        System.out.print("Id da Casa: ");
        String idCasa = sc.nextLine();
        System.out.print("Nome do Cenário: ");
        String idCenario = sc.nextLine();
        try {
            this.model.executarCenario(idCasa, idCenario);
            System.out.println("Cenário executado.");
        } catch (CasaDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void carregarDadosTeste83() {
        System.out.print("Id da Casa para injetar testes: ");
        String idCasa = sc.nextLine();
        try {
            // Cria acoes ficticias
            java.util.List<model.automacao.Acao> acoesSair = new java.util.ArrayList<>();
            acoesSair.add(new model.automacao.Acao("sala", "luz1", false));
            model.automacao.Cenario sair = new model.automacao.Cenario("Sair de Casa", acoesSair);
            this.model.adicionarCenario(idCasa, sair);

            java.util.List<model.automacao.Acao> acoesAcordar = new java.util.ArrayList<>();
            acoesAcordar.add(new model.automacao.Acao("quarto", "luz2", true));
            model.automacao.Escalonamento e = new model.automacao.Escalonamento("Acordar", java.time.LocalTime.of(8, 0),
                    acoesAcordar);
            this.model.adicionarEscalonamento(idCasa, e);

            System.out.println("Cenário 'Sair de Casa' e Escalonamento 'Acordar' às 08:00 criados na casa " + idCasa);
        } catch (CasaDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    // =========================================================
    // GUARDAR / CARREGAR
    // =========================================================

    private void guardarEstado() {
        System.out.print("Nome do ficheiro: ");
        String nomeFicheiro = sc.nextLine();
        try {
            this.model.save(nomeFicheiro);
            System.out.println("Estado guardado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar: " + e.getMessage());
        }
    }

    private void carregarEstado() {
        System.out.print("Nome do ficheiro: ");
        String nomeFicheiro = sc.nextLine();
        try {
            this.model = this.model.load(nomeFicheiro);
            System.out.println("Estado carregado com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }

}
