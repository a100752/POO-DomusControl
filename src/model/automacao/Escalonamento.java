package model.automacao;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class Escalonamento implements Serializable {
    private String idEscalonamento;
    private LocalTime horaAgendada;
    private List<Acao> acoes;

    public Escalonamento(String idEscalonamento, LocalTime horaAgendada, List<Acao> acoes) {
        this.idEscalonamento = idEscalonamento;
        this.horaAgendada = horaAgendada; // LocalTime é imutável
        this.acoes = new ArrayList<>();
        for (Acao a : acoes) { this.acoes.add(a.clone()); } // Composição
    }
    
    public Escalonamento(Escalonamento e) {
        this.idEscalonamento = e.getIdEscalonamento();
        this.horaAgendada = e.getHoraAgendada();
        this.acoes = e.getAcoes();
    }
    
    public String getIdEscalonamento() { return idEscalonamento; }
    public LocalTime getHoraAgendada() { return horaAgendada; }
    
    public List<Acao> getAcoes() {
        List<Acao> res = new ArrayList<>();
        for (Acao a : this.acoes) res.add(a.clone());
        return res;
    }
    
    public Escalonamento clone() { return new Escalonamento(this); }
}
