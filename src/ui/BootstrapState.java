package ui;

import model.Domus;
import model.casa.Casa;
import model.casa.Divisao;
import model.dispositivo.Lampada;
import model.dispositivo.Termostato;
import model.dispositivo.ColunaSom;
import model.automacao.Acao;
import model.automacao.Automacao;
import model.automacao.Cenario;
import model.automacao.Escalonamento;
import java.time.LocalTime;
import java.util.Arrays;
import java.io.IOException;

public class BootstrapState {
    public static void main(String[] args) {
        try {
            Domus model = new Domus();

            // Create Casa
            Casa c1 = new Casa();
            c1.setIdCasa("C1");
            c1.setNomeCasa("Casa do Ponto 8.3");

            // Create Divisoes
            Divisao sala = new Divisao(); sala.setIdDivisao("D1"); sala.setNomeDivisao("Sala");
            Divisao quarto = new Divisao(); quarto.setIdDivisao("D2"); quarto.setNomeDivisao("Quarto");
            Divisao cozinha = new Divisao(); cozinha.setIdDivisao("D3"); cozinha.setNomeDivisao("Cozinha");
            
            c1.adicionarDivisao(sala);
            c1.adicionarDivisao(quarto);
            c1.adicionarDivisao(cozinha);

            // Create Dispositivos
            Lampada l1 = new Lampada(); l1.setIdDispositivo("L1"); l1.setMarca("Philips"); l1.setModelo("Hue"); l1.setConsumoPorHora(10);
            Lampada l2 = new Lampada(); l2.setIdDispositivo("L2"); l2.setMarca("Philips"); l2.setModelo("Hue"); l2.setConsumoPorHora(10);
            Termostato t1 = new Termostato(); t1.setIdDispositivo("T1"); t1.setMarca("Nest"); t1.setModelo("Termo"); t1.setConsumoPorHora(50); t1.setTempAtual(20); t1.setTempAlvo(22);
            ColunaSom col1 = new ColunaSom(); col1.setIdDispositivo("S1"); col1.setMarca("Sonos"); col1.setModelo("One"); col1.setConsumoPorHora(20);
            
            c1.adicionarDispositivo("D1", l1); // Lampada Sala
            c1.adicionarDispositivo("D1", t1); // Termo Sala
            c1.adicionarDispositivo("D1", col1); // Coluna Sala
            c1.adicionarDispositivo("D2", l2); // Lampada Quarto

            // Point 8.3: Automações, Escalonamentos e Cenários

            // 2 Automações
            Automacao auto1 = new Automacao("A1", "ConsumoTotalCasa", 100.0, Arrays.asList(
                new Acao("D1", "L1", false),
                new Acao("D2", "L2", false)
            ));
            Automacao auto2 = new Automacao("A2", "TemperaturaAbaixo", 18.0, Arrays.asList(
                new Acao("D1", "T1", true)
            ));
            c1.adicionarAutomacao(auto1);
            c1.adicionarAutomacao(auto2);
            
            // 4 Escalonamentos
            Escalonamento esc1 = new Escalonamento("E1", LocalTime.of(8, 0), Arrays.asList(
                new Acao("D2", "L2", true) // Ligar luz quarto
            ));
            Escalonamento esc2 = new Escalonamento("E2", LocalTime.of(9, 0), Arrays.asList(
                new Acao("D1", "L1", false), // Desligar luz sala
                new Acao("D2", "L2", false)  // Desligar luz quarto
            ));
            Escalonamento esc3 = new Escalonamento("E3", LocalTime.of(19, 0), Arrays.asList(
                new Acao("D1", "L1", true) // Ligar luz sala
            ));
            Escalonamento esc4 = new Escalonamento("E4", LocalTime.of(23, 0), Arrays.asList(
                new Acao("D1", "L1", false),
                new Acao("D1", "T1", false),
                new Acao("D1", "S1", false)
            ));
            c1.adicionarEscalonamento(esc1);
            c1.adicionarEscalonamento(esc2);
            c1.adicionarEscalonamento(esc3);
            c1.adicionarEscalonamento(esc4);

            // 4 Cenários
            Cenario cen1 = new Cenario("sair de casa", Arrays.asList(
                new Acao("D1", "L1", false),
                new Acao("D2", "L2", false),
                new Acao("D1", "S1", false)
            ));
            Cenario cen2 = new Cenario("jantar com amigos", Arrays.asList(
                new Acao("D1", "L1", true),
                new Acao("D1", "S1", true)
            ));
            Cenario cen3 = new Cenario("deitar", Arrays.asList(
                new Acao("D1", "L1", false),
                new Acao("D1", "S1", false),
                new Acao("D2", "L2", false)
            ));
            Cenario cen4 = new Cenario("acordar", Arrays.asList(
                new Acao("D2", "L2", true),
                new Acao("D1", "T1", true)
            ));
            c1.adicionarCenario(cen1);
            c1.adicionarCenario(cen2);
            c1.adicionarCenario(cen3);
            c1.adicionarCenario(cen4);

            // Add Casa to Model
            model.adicionarCasa(c1);

            // Save to file
            model.save("estado83.dat");
            System.out.println("Estado gerado e gravado em estado83.dat");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
