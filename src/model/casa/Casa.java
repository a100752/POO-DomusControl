
package model.casa;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import exceptions.DispositivoAlreadyExistsException;
import exceptions.DispositivoDoesNotExistException;
//excecoes importantes na gestao de divisoes
import exceptions.DivisaoAlreadyExistsException;
import exceptions.DivisaoDoesNotExistException;
import model.dispositivo.Dispositivo;
import model.automacao.Acao;
import model.automacao.Automacao;
import model.automacao.Cenario;
import model.automacao.Escalonamento;

import java.io.Serializable;


public class Casa implements Serializable {


    /* VARIAVEIS INSTANCIA */
    private String idCasa;
    private String nomeCasa;
    private Map<String,Divisao> divisoes; 
    private Map<String, Automacao> automacoes;
    private Map<String, Escalonamento> escalonamentos;
    private Map<String, Cenario> cenarios;

    /* Para o ponto 8.2 , queries que envolvam comparator */
    public static final Map<String, Comparator<Divisao>> COMPARATORS;
    static {
        Map<String, Comparator<Divisao>> temp = new HashMap<>();
        temp.put("porDispositivos",
        Comparator.comparingInt(Divisao::getNumeroDispositivos).reversed());
        COMPARATORS = java.util.Collections.unmodifiableMap(temp);
    }



    /* CONSTRUTORES */




    /*  Construtor por omissao */

    public Casa(){
        this.idCasa = "";
        this.nomeCasa = "";
        this.divisoes = new HashMap<>(); 
        this.automacoes = new HashMap<>();
        this.escalonamentos = new HashMap<>();
        this.cenarios = new HashMap<>();
    }


    /* Construtor parametrizado */

    public Casa(String id, String nome,Map<String,Divisao> divisoes){
        this.idCasa = id;
        this.nomeCasa = nome;
        this.divisoes = divisoes.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey(),
                     entry ->  entry.getValue(). clone())); 
        this.automacoes = new HashMap<>();
        this.escalonamentos = new HashMap<>();
        this.cenarios = new HashMap<>();
    }


    /* Construtor por cópia */

    public Casa(Casa casa){
        this.idCasa = casa.getIdCasa();
        this.nomeCasa = casa.getNomeCasa();
        this.divisoes = casa.divisoes.entrySet().stream()
                        .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry ->  entry.getValue(). clone()));
        this.automacoes = casa.getAutomacoes();
        this.escalonamentos = casa.getEscalonamentos();
        this.cenarios = casa.getCenarios();
    }



    


    /* GETTERS e SETTERS */

    public String getIdCasa(){
        return this.idCasa;      
    }


    public void setIdCasa(String id){
        this.idCasa = id;     
    }


    public String getNomeCasa(){
        return this.nomeCasa;        
    }


    public void setNomeCasa(String nome){
        this.nomeCasa = nome;     
    }


    
    public Map<String,Divisao> getDivisoes(){
        return this.divisoes.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey(),
                     entry ->  entry.getValue(). clone())); // COMPOSICAO LOGO CLONE
     }


    public void  setDivisoes(Map<String,Divisao> divisoes){
        this.divisoes = divisoes.entrySet().stream()
                        .collect(Collectors.toMap(entry -> entry.getKey(),
                        entry ->  entry.getValue(). clone()));
    }

    public Map<String, Automacao> getAutomacoes() {
        return this.automacoes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }
    public void setAutomacoes(Map<String, Automacao> automacoes) {
        this.automacoes = automacoes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Map<String, Escalonamento> getEscalonamentos() {
        return this.escalonamentos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }
    public void setEscalonamentos(Map<String, Escalonamento> escalonamentos) {
        this.escalonamentos = escalonamentos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Map<String, Cenario> getCenarios() {
        return this.cenarios.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }
    public void setCenarios(Map<String, Cenario> cenarios) {
        this.cenarios = cenarios.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }



    /* Método toString */


    public String toString(){

        
        StringBuffer sb = new StringBuffer();

        sb.append(this.idCasa).append("\n");
        sb.append(this.nomeCasa).append("\n");
        this.divisoes.values().forEach(divisao-> sb.append(divisao.toString()).append("\n"));
        
        return sb.toString();
    }



    /* Método Equals */




    public boolean equals(Object umaCasa){

        if(this == umaCasa){
            return true;
        }

        if((umaCasa == null) || (this.getClass() != umaCasa.getClass()))
            return false;

        Casa casa = (Casa) umaCasa;
        return(this.idCasa.equals(casa.getIdCasa()))
            && this.nomeCasa.equals(casa.getNomeCasa())
            && this.divisoes.equals(casa.getDivisoes())
            && this.automacoes.equals(casa.getAutomacoes())
            && this.escalonamentos.equals(casa.getEscalonamentos())
            && this.cenarios.equals(casa.getCenarios());
            
    }







    /* CLONE */

    public Casa clone(){
        return new Casa(this);
    }




     /* Métodos Importantes */


    public double getConsumoTotal() {
        return this.divisoes.values().stream()
                .mapToDouble(Divisao::getConsumoTotal)
                .sum();
    }





    /* METODOS DE GESTAO DE DIVISOES - Casa é quem gere divisoes*/


    // adicionar divisao

    public void adicionarDivisao(Divisao d) throws DivisaoAlreadyExistsException {
        if (this.divisoes.containsKey(d.getIdDivisao()))
            throw new DivisaoAlreadyExistsException(d.getIdDivisao());
        this.divisoes.put(d.getIdDivisao(), d.clone());
    }

    // remover divisao

    public void removerDivisao(String idDivisao) throws DivisaoDoesNotExistException {
        if (!this.divisoes.containsKey(idDivisao))
            throw new DivisaoDoesNotExistException(idDivisao);
        this.divisoes.remove(idDivisao);
    }

    // listar divisoes

    public String listarDivisoes() {
        StringBuffer sb = new StringBuffer();
        for (Divisao d : this.divisoes.values()) {
            sb.append("Id: ").append(d.getIdDivisao())
            .append(" | Nome: ").append(d.getNomeDivisao()).append("\n");
        }
        return sb.toString();
    }




    /*METODOS DE GESTAO DE DISPOSITIVOS */
    // O padrão é sempre o mesmo — cada classe(no caso Casa) verifica se a entidade existe e delega para a classe abaixo(Divisao)



    //nao precisa de haver uma funcao assim para cada dispositivo especifico
    // ha polimorfismo por Lampada e Termostato serem ambos Dispositivo, logo este método aceita qualquer subclasse
    public void adicionarDispositivo(String idDivisao, Dispositivo d)
        throws DivisaoDoesNotExistException, DispositivoAlreadyExistsException {
            if (!this.divisoes.containsKey(idDivisao))
                throw new DivisaoDoesNotExistException(idDivisao);
            this.divisoes.get(idDivisao).adicionarDispositivo(d);
    }



    public void removerDispositivo(String idDivisao, String idDispositivo)
        throws DivisaoDoesNotExistException, DispositivoDoesNotExistException {
            if (!this.divisoes.containsKey(idDivisao))
                throw new DivisaoDoesNotExistException(idDivisao);
            this.divisoes.get(idDivisao).removerDispositivo(idDispositivo);
    }

    public void alteraEstadoDispositivo(String idDivisao, String idDispositivo, boolean accao)
        throws DivisaoDoesNotExistException, DispositivoDoesNotExistException {
            if (!this.divisoes.containsKey(idDivisao))
                throw new DivisaoDoesNotExistException(idDivisao);
            this.divisoes.get(idDivisao).alteraEstadoDispositivo(idDispositivo, accao);
    }



    public String listarDispositivos(String idDivisao) throws DivisaoDoesNotExistException {
        if (!this.divisoes.containsKey(idDivisao))
            throw new DivisaoDoesNotExistException(idDivisao);
        return this.divisoes.get(idDivisao).listarDispositivos();
    }

    /* PONTO 8.3: GESTAO DE AUTOMACOES, ESCALONAMENTOS E CENARIOS */

    public void adicionarAutomacao(Automacao a) {
        this.automacoes.put(a.getIdAutomacao(), a.clone());
    }

    public void adicionarEscalonamento(Escalonamento e) {
        this.escalonamentos.put(e.getIdEscalonamento(), e.clone());
    }

    public void adicionarCenario(Cenario c) {
        this.cenarios.put(c.getNomeCenario(), c.clone());
    }

    public void executarAcoes(List<Acao> acoes) {
        for (Acao a : acoes) {
            try {
                this.alteraEstadoDispositivo(a.getIdDivisao(), a.getIdDispositivo(), a.isLigar());
            } catch (Exception e) {
                // Ignore invalid actions to allow others to run
            }
        }
    }

    public void executarCenario(String idCenario) {
        if (this.cenarios.containsKey(idCenario)) {
            executarAcoes(this.cenarios.get(idCenario).getAcoes());
        }
    }

    public void verificarEscalonamentos(java.time.LocalTime horaAtual) {
        for (Escalonamento e : this.escalonamentos.values()) {
            if (e.getHoraAgendada().equals(horaAtual) || e.getHoraAgendada().isBefore(horaAtual)) {
                // Para simplificar, se for igual executa. Idealmente seria um range ou marcaria como executado
                executarAcoes(e.getAcoes());
            }
        }
    }
}
