package model.utilizador;

import model.casa.Casa;
import java.io.Serializable;

public class PapelUtilizadorNaCasa implements Serializable
{
    private String idCasa;
    
    private String idUtilizador;

    private PapelUtilizador papel;

    public PapelUtilizadorNaCasa(String idUtilizador, String idCasa, PapelUtilizador papel)
    {
        this.idUtilizador = idUtilizador;
        this.idCasa = idCasa;
        this.papel = papel;
    }

    public String getIdCasa() {
        return idCasa;
    }

    public String getIdUtilizador(){ 
        return idUtilizador;
    }
    
    public PapelUtilizador getPapel() {
        return papel; 
    }
}