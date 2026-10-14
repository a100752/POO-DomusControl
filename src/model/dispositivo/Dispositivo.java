
package model.dispositivo;

import java.io.Serializable;

public abstract class Dispositivo implements Serializable
{
    /* VARIAVEIS INSTANCIA */
    private String idDispositivo;
    private String marca;
    private String modelo;
    private EstadoDispositivo estado;
    private double consumoPorHora; // em Wh 
    private int numeroActivacoes; 

    /* CONSTRUTORES */
    /* Construtor por omissao */
    public Dispositivo() {
        this.idDispositivo  = "";
        this.marca          = "";
        this.modelo         = "";
        this.estado         = EstadoDispositivo.OFF;
        this.consumoPorHora = 0.0;
        this.numeroActivacoes = 0;
    }

    /* Construtor parametrizado */
    public Dispositivo(String idDispositivo, String marca, String modelo, EstadoDispositivo estado, double consumoPorHora) {
        this.idDispositivo  = idDispositivo;
        this.marca          = marca;
        this.modelo         = modelo;
        this.estado         = estado;
        this.consumoPorHora = consumoPorHora;
        this.numeroActivacoes = 0; // não é passado como argumento porque um dispositivo recém criado começa sempre com 0 ativações e elas acumulam-se através do uso, nunca são definidas na criação.
    
    }
 
    /* Construtor por copia */
    public Dispositivo(Dispositivo d) {
        this.idDispositivo  = d.getIdDispositivo();
        this.marca          = d.getMarca();
        this.modelo         = d.getModelo();
        this.estado         = d.getEstado();
        this.consumoPorHora = d.getConsumoPorHora();
        this.numeroActivacoes = d.getNumeroActivacoes();
    }
 
    /* GETTERS e SETTERS */
    public String getIdDispositivo(){
        return this.idDispositivo; 
    }
    
    public void setIdDispositivo(String idDispositivo){ 
        this.idDispositivo = idDispositivo; 
    }
 
    public String getMarca(){
        return this.marca; 
    }
    
    public void setMarca(String marca){
        this.marca = marca;
    }
 
    public String getModelo(){
        return this.modelo; 
    }

    public void setModelo(String modelo){
        this.modelo = modelo; 
    }
 
    public double getConsumoPorHora(){
        return this.consumoPorHora;
    }
    
    public void setConsumoPorHora(double consumoPorHora){
        this.consumoPorHora = consumoPorHora;
    }
 
    public EstadoDispositivo getEstado(){
        return this.estado; 
    }

    public void setEstado(EstadoDispositivo novoEstado){
        this.estado = novoEstado; 
    }


    public int getNumeroActivacoes() {
        return this.numeroActivacoes;
        
    }

    // em baixo preciso para uma casa determinar os três dispositivos mais utilizados EM TEMPO
    // tempo de uso em milissegundos — por defeito 0, subclasses podem fazer override
    public double getTempoUso() {
            return 0;
    }


    
    /* OPERACOES */
    
    // Ligar ou Desligar
    public void ligarOuDesligar(boolean estado) {
        if (estado) {
            this.estado = EstadoDispositivo.ON;
            this.numeroActivacoes++;
        } else {
            this.estado = EstadoDispositivo.OFF;
        }
    }

    public void modoEco(){
        this.estado = EstadoDispositivo.ECO; 
    }
 
    /* toString */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(this.idDispositivo).append("\n");
        sb.append("Marca: ").append(this.marca).append("\n");
        sb.append("Modelo: ").append(this.modelo).append("\n");
        sb.append("Consumo por hora: ").append(this.consumoPorHora).append(" Wh\n");
        sb.append("Estado: ").append(this.estado == EstadoDispositivo.ON ? "Ligado" : "Desligado").append("\n");
        return sb.toString();
    }
    
    /* equals */
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Dispositivo d = (Dispositivo) o;
        return this.idDispositivo.equals(d.getIdDispositivo())
            && this.marca.equals(d.getMarca())
            && this.modelo.equals(d.getModelo())
            && Double.compare(this.consumoPorHora, d.getConsumoPorHora()) == 0
            && this.estado == d.getEstado();
    }
 
    /* clone abstrato — cada subclasse implementa o seu */
    public abstract Dispositivo clone();
}



