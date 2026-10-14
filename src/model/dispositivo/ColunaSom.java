package model.dispositivo;

public class ColunaSom extends Dispositivo 
{
    private int volumeAtual;
    private int volumeMax;
    private EstadoDispositivo estadoProducao;
    private String streaming; 
    private int incrementoVolume; // 20 - 25 

    private long stamp;
    private double tempoUso;

    public ColunaSom(){
        super();
        this.volumeAtual = 0;
        this.volumeMax = 0;
        this.estadoProducao = EstadoDispositivo.STOPPED;
        this.streaming = "n/a";
        this.incrementoVolume = 0;

        this.stamp = System.currentTimeMillis();
        this.tempoUso = 0;

    }

    public ColunaSom(
        String id, String marca, String modelo, EstadoDispositivo estado, double consumo,
        int atual, int max, EstadoDispositivo producao, String plataforma, int numero)
    {
        super(id,marca,modelo,estado,consumo);
        this.volumeAtual = atual;
        this.volumeMax = max;
        this.estadoProducao = producao;
        this.streaming = plataforma;
        this.incrementoVolume = numero;

        this.stamp = System.currentTimeMillis();
        this.tempoUso = 0;

    }
    
    public ColunaSom(ColunaSom c) {
        super(c);
        this.volumeAtual = c.getVolumeAtual();
        this.volumeMax = c.getVolumeMax();
        this.estadoProducao = c.getEstadoProducao();
        this.streaming = c.getStreaming();
        this.incrementoVolume = c.getIncrementoVolume();

        this.stamp = c.getStamp(); 
        this.tempoUso = c.getTempoUso();
            

    }

    public int getVolumeAtual() { return volumeAtual; }
    public void setVolumeAtual(int volumeAtual) { this.volumeAtual = volumeAtual; }
    public int getVolumeMax() { return volumeMax; }
    public void setVolumeMax(int volumeMax) { this.volumeMax = volumeMax; }
    public EstadoDispositivo getEstadoProducao() { return estadoProducao; }
    public void setEstadoProducao(EstadoDispositivo estadoProducao) { this.estadoProducao = estadoProducao; }
    public String getStreaming() { return streaming; }
    public void setStreaming(String streaming) { this.streaming = streaming; }
    public int getIncrementoVolume() { return incrementoVolume; }
    public void setIncrementoVolume(int incrementoVolume) { this.incrementoVolume = incrementoVolume; }

    @Override
    public ColunaSom clone() {
        return new ColunaSom(this);
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


    public void aumentarVolume() { this.volumeAtual = Math.min(volumeAtual + incrementoVolume, volumeMax); }
public void diminuirVolume() { this.volumeAtual = Math.max(volumeAtual - incrementoVolume, 0); }

*/








}