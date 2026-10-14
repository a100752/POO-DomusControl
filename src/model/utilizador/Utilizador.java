package model.utilizador;
//import java.util.ArrayList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utilizador  implements Serializable
{
    /** Identificador único do utilizador. */
    private String idUtilizador;
    
    /** Tipo de utilizador. */
    private String papel;

    /** Nome e apelido do utilizador. */
    private String nome;

    /** Email do utilizador. */
    private String email;

    /** NUmero de casas do utilizador. */
   // private int numCasas;
    
    /** Lista dos ids das casas do utilizador. */
    private List<String> idCasas;




    /* CONSTRUTORES */


    /* Construtor por omissao */

    public Utilizador(){
        this.idUtilizador = "n/a"; // Não tenho certeza disto
        this.papel = "n/a";
        this.nome = "n/a";
        this.email = "n/a";
        //this.numCasas = 0;
        this.idCasas = new ArrayList<>();
    }

   

     /* Construtor parametrizado */

    public Utilizador(String idUtilizador, String papel, String nome, String email, int numCasas,List<String> idCasas) {
        this.idUtilizador = idUtilizador;
        this.papel = papel;
        this.nome = nome;
        this.email = email;
       // this.numCasas = numCasas;
        this.idCasas = new ArrayList<>(idCasas);
    }



    /* Construtor por copia */

    public Utilizador(Utilizador u) {
        this.idUtilizador = u.getIdUtilizador();
        this.papel        = u.getPapel();
        this.nome         = u.getNome();
        this.email        = u.getEmail();
       // this.numCasas     = u.getNumCasas();
        this.idCasas = new ArrayList<>(u.getIdCasas());
    }





    /* Getters e Setters */



    public String getIdUtilizador() {
        return this.idUtilizador;
    }

    public void setIdUtilizador(String idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getPapel() {
        return this.papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  /*   public int getNumCasas() {
        return this.numCasas;
    }

*/


  /*
    public void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }

 */



    public List<String> getIdCasas() {
        return new ArrayList<>(this.idCasas);
    }

    public void setIdCasas(List<String> idCasas) {
        this.idCasas = new ArrayList<>(idCasas);
    }


    public void adicionarCasa(String idCasa) {
        if (!this.idCasas.contains(idCasa))
            this.idCasas.add(idCasa);
    }

    public void removerCasa(String idCasa) {
        this.idCasas.remove(idCasa);
    }





    /* clone toString equals */

    public Utilizador clone() {
        return new Utilizador(this);
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(this.idUtilizador).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");
        sb.append("Email: ").append(this.email).append("\n");
        sb.append("Papel: ").append(this.papel).append("\n");
      //  sb.append("Nº Casas: ").append(this.numCasas).append("\n");
        sb.append("Casas: ").append(this.idCasas).append("\n");
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Utilizador u = (Utilizador) o;
        return this.idUtilizador.equals(u.getIdUtilizador())
            && this.nome.equals(u.getNome())
            && this.email.equals(u.getEmail())
            && this.papel.equals(u.getPapel())
            && this.idCasas.equals(u.getIdCasas());
    }






}