package model.dispositivo;

public class Lampada extends Dispositivo 
{
    /* consumo por segundo em Modo.ON */
    private double cpSOn;  
    /* consumo por segundo em Modo.OFF */
    private double cpSEco;
    private double consumoTotal;
    private double consumoPeriodo;
    private int intensidadeLuz;
    private String corLampada;
    private long stamp; // para calcular consumo em Wh (cpSOn, cpSEco)
    private long stampTempo;    // para calcular tempo de uso
    private double tempoUso;


    public Lampada() {
        super();
        this.cpSOn = 2;
        this.cpSEco = 1;
        this.consumoTotal = 0;
        this.consumoPeriodo = 0;
        this.intensidadeLuz = 0;
        this.corLampada = "n/a";
        this.stamp = System.currentTimeMillis(); // instante de tempo em que ocorreu a última actualização do estado da lâmpada 
        
        this.stampTempo = System.currentTimeMillis();
        this.tempoUso = 0;
    
    }

    public Lampada(
        String id, String marca, String modelo, EstadoDispositivo estado, double consumo,    
        double cpsE, double cpsO, double cT, double pC, int valor, String cor)
    {
        super(id,marca,modelo,estado,consumo);
        this.cpSEco = cpsE;
        this.cpSOn = cpsO;
        this.consumoTotal = cT;
        this.consumoPeriodo = pC;
        this.intensidadeLuz = valor;
        this.corLampada = cor;
        this.stamp = System.currentTimeMillis();

        this.stampTempo = System.currentTimeMillis();
        this.tempoUso = 0;
    }

    public Lampada(Lampada lampada){
        super(lampada);
        this.cpSEco = lampada.getCpSEco();
        this.cpSOn = lampada.getCpSOn();
        this.consumoTotal = lampada.totalConsumo();
        this.consumoPeriodo = lampada.periodoConsumo();
        this.intensidadeLuz = lampada.getIntensidade();
        this.corLampada = lampada.getCor();
        this.stamp = lampada.getStamp();

        
        this.stampTempo = lampada.getStamp(); 
        this.tempoUso = lampada.getTempoUso();
    }

    public long getStamp() {
        return stamp;
    }

    public double getCpSEco(){
        return this.cpSEco;
    }

    public void setCpSEco(double x){
        this.cpSEco = x;
    }

    public double getCpSOn(){
        return this.cpSOn;
    }

    public void setCpSOn(double x){
        this.cpSOn = x;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public void setConsumoTotal(double cT){
        this.consumoTotal = cT;
    }

    public double getConsumoPeriodo() {
        return consumoPeriodo;
    }

    public int getIntensidade(){
        return this.intensidadeLuz;
    }

    void setIntensidade(int valor){
        this.intensidadeLuz = valor;
    }

    public String getCor(){
        return this.corLampada;
    }

    void setCor (String cor){
        this.corLampada = cor;
    }

    public void setConsumoPeriodo(double pC){
        this.consumoPeriodo = pC;
    }

    public void ligarLampada() {
        this.atualizaConsumo();
        this.ligarOuDesligar(true);
    }

    public void desligarLampada() {
        this.atualizaConsumo();
        this.ligarOuDesligar(false);
    }

    public void lampadaEco() {
        this.atualizaConsumo();
        this.modoEco();
    }

    public void resetPeriodo(){
        atualizaConsumo();
        this.consumoPeriodo = 0;
    }

    public double periodoConsumo(){
        atualizaConsumo();
        return consumoPeriodo;
    }
    public double totalConsumo() {
        atualizaConsumo();
        return consumoTotal;
    }

    private void atualizaConsumo(){
        long periodo = System.currentTimeMillis() - stamp;

        if(this.getEstado() == EstadoDispositivo.ON) {
            this.consumoTotal += cpSOn * periodo;
            this.consumoPeriodo += cpSOn * periodo;
        } else if(this.getEstado() == EstadoDispositivo.ECO) {
            this.consumoTotal += cpSEco * periodo;
            this.consumoPeriodo += cpSEco * periodo;
        }

        this.stamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(super.toString());
        sb.append("Lampada: ").append(this.getEstado())
        .append("\nConsumo por segundo Eco: ").append(this.cpSEco)
        .append("\nConsumo por segundo On: ").append(this.cpSOn)
        .append("\nConsumoTotal: ").append(this.consumoTotal)
        .append("\nInicio de ultimo consumo: ").append(this.consumoPeriodo);
        
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;

        Lampada l = (Lampada) o;
        return super.equals(l) && 
                l.getEstado() == this.getEstado() &&
                l.getCpSOn() == this.cpSOn &&
                l.getCpSEco() == this.cpSEco &&
                l.getConsumoTotal() == this.consumoTotal &&
                l.getConsumoPeriodo() == this.consumoPeriodo &&
                l.getStamp() == this.stamp &&
                l.getStampTempo() == this.stampTempo &&
                l.getTempoUso() == this.tempoUso;
    }
        
    @Override
    public Lampada clone() {
        return new Lampada(this);
    }



    private void atualizaTempo() {
        long periodo = System.currentTimeMillis() - stampTempo;
        if (this.getEstado() == EstadoDispositivo.ON)
            this.tempoUso += periodo;
        this.stampTempo = System.currentTimeMillis();
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

    public long getStampTempo() { return this.stampTempo; }

























}
