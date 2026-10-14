
 package model.dispositivo;

public class Termostato extends Dispositivo 
{
    private double temperaturaAtual;
    private double temperaturaAlvo; // Pode ser para o caso de aquecimento ou refrigeração
    private String unidadeTemperatura;
    private double taxaAquecimento;   // °C por minuto
    private double taxaArrefecimento; // °C por minuto
    private EstadoDispositivo modoFuncionamento;


    private long stamp;
    private double tempoUso;


    public Termostato() {
        super(); 
        this.temperaturaAtual   = 0.00;
        this.temperaturaAlvo    = 0.00;
        this.unidadeTemperatura = "Celsius";
        this.taxaAquecimento    = 0.15;
        this.taxaArrefecimento  = 0.20;
        this.modoFuncionamento  = EstadoDispositivo.OFF;

        this.stamp = System.currentTimeMillis();
        this.tempoUso = 0;

    }

    public Termostato(String idDispositivo, String marca, String modelo, double consumoPorHora,
                    double atual, double alvo, String unidade, EstadoDispositivo funcionamento) {
                        
        super(idDispositivo, marca, modelo, EstadoDispositivo.OFF, consumoPorHora);
        this.temperaturaAtual   = atual;
        this.temperaturaAlvo    = alvo;
        this.unidadeTemperatura = unidade;
        this.modoFuncionamento  = funcionamento;
        this.taxaAquecimento    = 0.15; 
        this.taxaArrefecimento  = 0.20;

        this.stamp = System.currentTimeMillis();
        this.tempoUso = 0;

    }

    public Termostato(Termostato termostato) {
        super(termostato); // copia as variaveis do Dispositivo
        this.temperaturaAtual   = termostato.getTempAtual();
        this.temperaturaAlvo    = termostato.getTempAlvo();
        this.unidadeTemperatura = termostato.getUnidade();
        this.modoFuncionamento  = termostato.getModoFunc();
        this.taxaAquecimento    = termostato.getTaxaAquecimento();
        this.taxaArrefecimento  = termostato.getTaxaArrefecimento();

        this.stamp = termostato.getStamp(); // ou termostato/coluna
        this.tempoUso = termostato.getTempoUso();
    }






    public double getTempAtual(){
        return this.temperaturaAtual;    
    }

    public void setTempAtual(double novoAtual){
        this.temperaturaAtual = novoAtual;
    }

    public double getTempAlvo(){
        return this.temperaturaAlvo;
    }

    public void setTempAlvo(double novoAlvo){
        this.temperaturaAlvo = novoAlvo;
    }

    public String getUnidade(){
        return this.unidadeTemperatura;
    }

    public void setUnidade(String novaUnidade){
        this.unidadeTemperatura = novaUnidade;
    }

    public EstadoDispositivo getModoFunc(){
        return this.modoFuncionamento;
        
    }

    public void setModoFunc(EstadoDispositivo novaFuncao){
        this.modoFuncionamento = novaFuncao;
    }
    
    public double getTaxaAquecimento() {
        return this.taxaAquecimento;
    }

    public double getTaxaArrefecimento() {
        return this.taxaArrefecimento;
    }





    public double estimarTempoAteAlvo()
    {
        double diferenca = Math.abs(temperaturaAlvo - temperaturaAtual);

        double taxa;
        switch (this.getEstado()) {
            case AQUECIMENTO:
                taxa = taxaAquecimento;
                break;
            case ARREFECIMENTO:
                taxa = taxaArrefecimento;
                break;
            default:
                taxa = 0;
                break;
        }

        if (taxa <= 0) return Double.POSITIVE_INFINITY;

        return diferenca / taxa; // minutos
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Termostato: ").append(this.getModoFunc())
        .append("\nTemperatura Atual: ").append(this.getTempAtual())
        .append("\nTemperatura Alvo: ").append(this.getTempAlvo())
        .append("\nUnidade: ").append(this.getUnidade());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Termostato t = (Termostato) o;
        return t.getModoFunc() == this.getModoFunc() &&
            t.getTempAtual() == this.getTempAtual() &&
            t.getTempAlvo() == this.getTempAlvo() &&
            t.getUnidade().equals(this.getUnidade());
    }

    @Override
    public Termostato clone() {
        return new Termostato(this);
    }







    private void atualizaTempo() {
        long periodo = System.currentTimeMillis() - stamp;
        if (this.getEstado() == EstadoDispositivo.ON)
            this.tempoUso += periodo;
        this.stamp = System.currentTimeMillis();
    }

    @Override
    public void ligarOuDesligar(boolean estado) {
        atualizaTempo();
        super.ligarOuDesligar(estado);
    }

    @Override
    public double getTempoUso() {
        atualizaTempo();
        return this.tempoUso;
    }

    public long getStamp() { return this.stamp; }




/* PARA FAZERMOS MAIS OPERACOES TINHAMOS ISTO MAS NAO DEU TEMPO PARA ENTREGAR

    public void aumentarTemperatura() { this.temperaturaAlvo += 1; }
public void diminuirTemperatura() { this.temperaturaAlvo -= 1; }


*/


}