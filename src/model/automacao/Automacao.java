package model.automacao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Automacao implements Serializable {
    private String idAutomacao;
    private String tipoCondicao; // e.g. "ConsumoTotalCasa", "NumeroDispositivosLigados"
    private double valorCondicao; // If greater than this, trigger
    private List<Acao> acoes;

    public Automacao(String idAutomacao, String tipoCondicao, double valorCondicao, List<Acao> acoes) {
        this.idAutomacao = idAutomacao;
        this.tipoCondicao = tipoCondicao;
        this.valorCondicao = valorCondicao;
        this.acoes = new ArrayList<>();
        for (Acao a : acoes) { this.acoes.add(a.clone()); }
    }
    
    public Automacao(Automacao a) {
        this.idAutomacao = a.getIdAutomacao();
        this.tipoCondicao = a.getTipoCondicao();
        this.valorCondicao = a.getValorCondicao();
        this.acoes = a.getAcoes();
    }
    
    public String getIdAutomacao() { return idAutomacao; }
    public String getTipoCondicao() { return tipoCondicao; }
    public double getValorCondicao() { return valorCondicao; }
    
    public List<Acao> getAcoes() {
        List<Acao> res = new ArrayList<>();
        for (Acao a : this.acoes) res.add(a.clone());
        return res;
    }
    
    public Automacao clone() { return new Automacao(this); }
}
