package model.casa;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import exceptions.DispositivoAlreadyExistsException;
import exceptions.DispositivoDoesNotExistException;
import model.dispositivo.Dispositivo;

import java.io.Serializable;

public class Divisao implements Serializable {

    /* VARIAVEIS INSTANCIA */
    
    private String idDivisao;
    private String nomeDivisao;
    private Map<String, Dispositivo> dispositivos;



    /* Para o ponto 8.2 , queries que envolvam comparator */

    public static final Map<String, Comparator<Dispositivo>> COMPARATORS;

    static {
        Map<String, Comparator<Dispositivo>> temp = new HashMap<>();
        temp.put("porActivacoes", 
            Comparator.comparingInt(Dispositivo::getNumeroActivacoes).reversed());
        temp.put("porConsumo", 
            Comparator.comparingDouble(Dispositivo::getConsumoPorHora).reversed());
        temp.put("porTempo", 
            Comparator.comparingDouble(Dispositivo::getTempoUso).reversed());
        COMPARATORS = java.util.Collections.unmodifiableMap(temp);
    }






    /* CONSTRUTORES */




    /*  Construtor por omissao */

    public Divisao(){
        this.idDivisao = "";
        this.nomeDivisao = "";
        this.dispositivos = new HashMap<>();
    }


    /* Construtor parametrizado */

    public Divisao(String id, String nome, Map<String, Dispositivo> dispositivos) {
        this.idDivisao = id;
        this.nomeDivisao = nome;
        this.dispositivos = dispositivos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }


    /* Construtor por cópia */

    public Divisao(Divisao divisao){
        this.idDivisao = divisao.getIdDivisao();
        this.nomeDivisao = divisao.getNomeDivisao();
        this.dispositivos = divisao.getDispositivos();
    }



   /* GETTERS e SETTERS */
    public String getIdDivisao(){
        return this.idDivisao;      
    }

    public void setIdDivisao(String id){
        this.idDivisao = id;     
    }

    public String getNomeDivisao(){
        return this.nomeDivisao;        
    }

    public void setNomeDivisao(String nome){
        this.nomeDivisao = nome;     
    }

    public Map<String, Dispositivo> getDispositivos() {
        return this.dispositivos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public void setDispositivos(Map<String, Dispositivo> dispositivos) {
        this.dispositivos = dispositivos.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    /* METODOS AUXILIARES DE DISPOSITIVOS */


    public Dispositivo getDispositivo(String idDispositivo) {
        Dispositivo d = this.dispositivos.get(idDispositivo);
        return d != null ? d.clone() : null;
    }

    public int getNumeroDispositivos() {
        return this.dispositivos.size();
    }

    






     /**
   * Método toString
   * 
   * @return uma string com a informação textual do objecto Divisao
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();

    sb.append("Id: ");
    sb.append(this.idDivisao + " - ");
    sb.append("Nome: ");
    sb.append(this.nomeDivisao).append("\n");
    this.dispositivos.values().forEach(d -> sb.append(d.toString()).append("\n"));
    return sb.toString();
  }



  
  /**
   * Implementação do método de igualdade entre dois Aluno
   * Redefinição do método equals de Object.
   * 
   * @param divisao que é comparado com o receptor
   * @return booleano true ou false
   */
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if ((o == null) || (this.getClass() != o.getClass()))
      return false;

    Divisao divisao = (Divisao) o;
    return (this.idDivisao.equals(divisao.getIdDivisao())
        && this.nomeDivisao.equals(divisao.getNomeDivisao())
        && this.dispositivos.equals(divisao.getDispositivos()));
}
        
  

    
     /* CLONE */

    public Divisao clone(){
        return new Divisao(this);
    }


    /* Métodos Importantes */


    public double getConsumoTotal() {
        return this.dispositivos.values().stream()
                .mapToDouble(Dispositivo::getConsumoPorHora)
                .sum();
    }
    



    /*METODOS DE GESTAO DE DISPOSITIVOS - Divisao é quem gere os dispositivos */

    public void adicionarDispositivo(Dispositivo d) throws DispositivoAlreadyExistsException {
        if (this.dispositivos.containsKey(d.getIdDispositivo()))
            throw new DispositivoAlreadyExistsException(d.getIdDispositivo());
        this.dispositivos.put(d.getIdDispositivo(), d.clone());
    }

    public void alteraEstadoDispositivo(String idDispositivo, boolean accao) throws DispositivoDoesNotExistException {
        if (!this.dispositivos.containsKey(idDispositivo))
            throw new DispositivoDoesNotExistException(idDispositivo);
        this.dispositivos.get(idDispositivo).ligarOuDesligar(accao);
    }



    public void removerDispositivo(String idDispositivo) throws DispositivoDoesNotExistException {
        if (!this.dispositivos.containsKey(idDispositivo))
            throw new DispositivoDoesNotExistException(idDispositivo);
        this.dispositivos.remove(idDispositivo);
    }


    public String listarDispositivos() {
        StringBuffer sb = new StringBuffer();
        for (Dispositivo d : this.dispositivos.values()) {
            sb.append(d.toString()).append("\n");
        }
        return sb.toString();
    }


}