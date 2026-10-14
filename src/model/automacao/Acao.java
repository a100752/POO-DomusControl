package model.automacao;

import java.io.Serializable;

public class Acao implements Serializable {
    private String idDivisao;
    private String idDispositivo;
    private boolean ligar; // true = ligar, false = desligar
    
    public Acao(String idDivisao, String idDispositivo, boolean ligar) {
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
        this.ligar = ligar;
    }
    
    public Acao(Acao a) {
        this.idDivisao = a.getIdDivisao();
        this.idDispositivo = a.getIdDispositivo();
        this.ligar = a.isLigar();
    }
    
    public String getIdDivisao() { return idDivisao; }
    public String getIdDispositivo() { return idDispositivo; }
    public boolean isLigar() { return ligar; }
    
    public Acao clone() { return new Acao(this); }
    
    public String toString() { return (ligar ? "Ligar" : "Desligar") + " " + idDispositivo + " na divisao " + idDivisao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acao acao = (Acao) o;
        return ligar == acao.ligar &&
               idDivisao.equals(acao.idDivisao) &&
               idDispositivo.equals(acao.idDispositivo);
    }
}
