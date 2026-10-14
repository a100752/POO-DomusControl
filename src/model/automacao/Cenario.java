package model.automacao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Cenario implements Serializable {
    private String nomeCenario;
    private List<Acao> acoes;

    public Cenario(String nomeCenario, List<Acao> acoes) {
        this.nomeCenario = nomeCenario;
        this.acoes = new ArrayList<>();
        for (Acao a : acoes) { this.acoes.add(a.clone()); } // Composicao
    }
    
    public Cenario(Cenario c) {
        this.nomeCenario = c.getNomeCenario();
        this.acoes = c.getAcoes();
    }
    
    public String getNomeCenario() { return nomeCenario; }
    
    public List<Acao> getAcoes() {
        List<Acao> res = new ArrayList<>();
        for (Acao a : this.acoes) res.add(a.clone()); // Cópia defensiva
        return res;
    }
    
    public Cenario clone() { return new Cenario(this); }
}
