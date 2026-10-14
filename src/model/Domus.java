package model;

import model.casa.Casa; 
import model.casa.Divisao;
import model.dispositivo.Dispositivo;
import model.dispositivo.Lampada;
import model.dispositivo.Termostato;
import model.utilizador.Utilizador;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalTime;

//excecoes importantes na gestao de casas
import exceptions.CasaAlreadyExistsException;
import exceptions.CasaDoesNotExistException;
import exceptions.DispositivoAlreadyExistsException;
import exceptions.DispositivoDoesNotExistException;
import exceptions.DivisaoAlreadyExistsException;
import exceptions.DivisaoDoesNotExistException;
import exceptions.UtilizadorAlreadyExistsException;
import exceptions.UtilizadorDoesNotExistException;

import java.io.Serializable;

public class Domus implements Serializable {
    private static final long serialVersionUID = -2795919749811841425L;

    /* VARIAVEIS INSTANCIA */
    private Map<String, Casa> casas; // composicao logo clone
    private Map<String, Utilizador> users; // composicao logo clone
    private LocalTime relogio; // Simulação do Tempo



    /* CONSTRUTORES */


    /* Construtor por omissao */

    public Domus() {
        this.casas = new HashMap<>();
        this.users = new HashMap<>(); 
        this.relogio = LocalTime.of(0, 0); // Começa à meia-noite
    }
    


     /* Construtor parametrizado */

    public Domus(Map<String, Casa> casas, Map<String, Utilizador> users, LocalTime relogio) {
        this.casas = casas.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()
                ));

        this.users = users.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()
                ));
        this.relogio = relogio;
    }

    

    /* Construtor por copia */

    public Domus(Domus domus) {
        this.casas = domus.casas.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()  // composicao logo clone
                ));
        this.users = domus.users.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()  // composicao logo clone
                ));
        this.relogio = domus.getRelogio();
    }




    /* GETTERS e SETTERS */

    public Map<String, Casa> getCasas() {
        return this.casas.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()
                )); // composicao logo clone
    }

    public void setCasas(Map<String, Casa> casas) {
        this.casas = casas.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()
                )); // composicao logo clone
    }

    public Map<String, Utilizador> getUsers() {
        return this.users.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()
                )); // composicao logo clone
    }

    public void setUsers(Map<String, Utilizador> users) {
        this.users = users.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().clone()
                )); // composicao logo clone
    }

    public LocalTime getRelogio() {
        return this.relogio;
    }

    public void setRelogio(LocalTime relogio) {
        this.relogio = relogio;
    }



    /* METODOS toString, equals, clone */

 
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== Casas ===\n");
        this.casas.values().forEach(casa -> sb.append(casa.toString()).append("\n"));
        sb.append("=== Utilizadores ===\n");
        this.users.values().forEach(user -> sb.append(user.toString()).append("\n"));
        return sb.toString();
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Domus domus = (Domus) o;
        return this.casas.equals(domus.getCasas())
            && this.users.equals(domus.getUsers())
            && this.relogio.equals(domus.getRelogio());
    }


    public Domus clone() {
        return new Domus(this);
    }





    /* METODOS DE GESTAO DE CASAS - é a classe Domus que gere as casas*/


   //adicionar casa

   public void adicionarCasa(Casa c) throws CasaAlreadyExistsException {
        if (this.casas.containsKey(c.getIdCasa())) {
            throw new CasaAlreadyExistsException(c.getIdCasa());
        }

        this.casas.put(c.getIdCasa(), c.clone());
    }


    // remover casa

    public void removerCasa(String id) throws CasaDoesNotExistException {
        if (!this.casas.containsKey(id))
            throw new CasaDoesNotExistException(id);
        this.casas.remove(id);
    }




    // listar casas

    public String listarCasas() {
        StringBuffer sb = new StringBuffer();
        for (Casa c : this.casas.values()) {
            sb.append("Id: ").append(c.getIdCasa())
            .append(" | Nome: ").append(c.getNomeCasa()).append("\n");
        }
        return sb.toString();
    }




    /* METODOS DE GESTAO DE DIVISOES , Domus apenas usa Divisao como argumento e deixa para Casa que saber o que fazer com ele */
    //Domus verifica se a entidade existe e delega para a classe abaixo(casa) */
   
    

    public void adicionarDivisao(String idCasa, Divisao d)
            throws CasaDoesNotExistException, DivisaoAlreadyExistsException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaDoesNotExistException(idCasa);
        this.casas.get(idCasa).adicionarDivisao(d);
    }

    public void removerDivisao(String idCasa, String idDivisao)
            throws CasaDoesNotExistException, DivisaoDoesNotExistException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaDoesNotExistException(idCasa);
        this.casas.get(idCasa).removerDivisao(idDivisao);
    }

    public String listarDivisoes(String idCasa) throws CasaDoesNotExistException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaDoesNotExistException(idCasa);
        return this.casas.get(idCasa).listarDivisoes();
    }

    





    /*METODOS DE GESTAO DE DISPOSITIVOS */
    // O padrão é sempre o mesmo — cada classe(no caso Domus) verifica se a entidade existe e delega para a classe abaixo(Casa).

    public void alteraEstadoDispositivo(String idCasa, String idDivisao, String idDispositivo, boolean accao)
        throws CasaDoesNotExistException, DivisaoDoesNotExistException, DispositivoDoesNotExistException 
    {    
        if (!this.casas.containsKey(idCasa))
            throw new CasaDoesNotExistException(idCasa);
        this.casas.get(idCasa).alteraEstadoDispositivo(idDivisao, idDispositivo, accao);
    }

    //nao precisa de haver uma funcao assim para cada dispositivo especifico
    // ha polimorfismo por Lampada e Termostato serem ambos Dispositivo, logo este método aceita qualquer subclasse
    public void adicionarDispositivo(String idCasa, String idDivisao, Dispositivo d)
        throws CasaDoesNotExistException, DivisaoDoesNotExistException, DispositivoAlreadyExistsException {
            if (!this.casas.containsKey(idCasa))
                throw new CasaDoesNotExistException(idCasa);
            this.casas.get(idCasa).adicionarDispositivo(idDivisao, d);
    }




    public void removerDispositivo(String idCasa, String idDivisao, String idDispositivo)
        throws CasaDoesNotExistException, DivisaoDoesNotExistException, DispositivoDoesNotExistException {
            if (!this.casas.containsKey(idCasa))
                throw new CasaDoesNotExistException(idCasa);
            this.casas.get(idCasa).removerDispositivo(idDivisao, idDispositivo);
    }


    public String listarDispositivos(String idCasa, String idDivisao)
        throws CasaDoesNotExistException, DivisaoDoesNotExistException {
            if (!this.casas.containsKey(idCasa))
                throw new CasaDoesNotExistException(idCasa);
            return this.casas.get(idCasa).listarDispositivos(idDivisao);
    }

    


    /* METODOS DE GESTAO DE UTILIZADORES é a classe Domus que gere os utilizadores */

    public void adicionarUtilizador(Utilizador u) throws UtilizadorAlreadyExistsException {
        if (this.users.containsKey(u.getIdUtilizador()))
            throw new UtilizadorAlreadyExistsException(u.getIdUtilizador());
        this.users.put(u.getIdUtilizador(), u.clone());
    }

    public void removerUtilizador(String id) throws UtilizadorDoesNotExistException {
        if (!this.users.containsKey(id))
            throw new UtilizadorDoesNotExistException(id);
        this.users.remove(id);
    }

   
    public String listarUtilizadores() {
        StringBuffer sb = new StringBuffer();
        for (Utilizador u : this.users.values()) {
            sb.append(u.toString()).append("\n");
        }
        return sb.toString();
    }

    /* PONTO 8.3 e TEMPO */

    public void avancarTempo(int horas, int minutos) {
        this.relogio = this.relogio.plusHours(horas).plusMinutes(minutos);
        // Ao avançar o tempo, pedir a todas as casas para verificarem escalonamentos
        for (Casa c : this.casas.values()) {
            c.verificarEscalonamentos(this.relogio);
            // Temos que atualizar as casas no map original (mesmo o value ja sendo a referencia, para garantir coerencia)
            // Mas as casas.values() ja sāo as referencias reais da nossa classe Domus
        }
    }

    public void adicionarCenario(String idCasa, model.automacao.Cenario cenario) throws CasaDoesNotExistException {
        if (!this.casas.containsKey(idCasa)) throw new CasaDoesNotExistException(idCasa);
        this.casas.get(idCasa).adicionarCenario(cenario);
    }

    public void executarCenario(String idCasa, String idCenario) throws CasaDoesNotExistException {
        if (!this.casas.containsKey(idCasa)) throw new CasaDoesNotExistException(idCasa);
        this.casas.get(idCasa).executarCenario(idCenario);
    }

    public void adicionarEscalonamento(String idCasa, model.automacao.Escalonamento e) throws CasaDoesNotExistException {
        if (!this.casas.containsKey(idCasa)) throw new CasaDoesNotExistException(idCasa);
        this.casas.get(idCasa).adicionarEscalonamento(e);
    }    /*  PONTO 8.2 -> QUERIES DE ESTATISTICA  */
   
   
    // 1. Casa que mais consome
    public Casa obterCasaMaisConsumidora() {
        return this.casas.values().stream()
            .max((c1, c2) -> Double.compare(c1.getConsumoTotal(), c2.getConsumoTotal()))
            .map(Casa::clone)
            .orElse(null);
    }


    // 2. Para uma casa determinar os três dispositivos mais utilizados (em número de activações)
    public List<Dispositivo> top3DispositivosPorActivacoes(String idCasa) {
        Casa casa = this.casas.get(idCasa);
        if (casa == null) return new ArrayList<>();
        
        List<Dispositivo> list = new ArrayList<>();
        for (Divisao d : casa.getDivisoes().values()) {
            for (Dispositivo disp : d.getDispositivos().values()) {
                list.add(disp);
            }
        }
        
        list.sort(Divisao.COMPARATORS.get("porActivacoes"));
        return list.subList(0, Math.min(3, list.size()));
    }



    // 2. Para uma casa determinar os três dispositivos mais utilizados (em tempo )
    public List<Dispositivo> top3DispositivosPorTempo(String idCasa) {
        Casa casa = this.casas.get(idCasa);
        if (casa == null) return new ArrayList<>();
        List<Dispositivo> list = new ArrayList<>();
        for (Divisao d : casa.getDivisoes().values()) {
            for (Dispositivo disp : d.getDispositivos().values()) {
                list.add(disp);
            }
        }
        list.sort(Divisao.COMPARATORS.get("porTempo"));
        return list.subList(0, Math.min(3, list.size()));
    }













    //3. quais as três divisões  é que possuem mais dispositivos
    public List<Divisao> top3DivisoesComMaisDispositivos() {
        List<Divisao> list = new ArrayList<>();
        
        for (Casa c : this.casas.values()) {
            for (Divisao d : c.getDivisoes().values()) {
                list.add(d);
            }
        }
        
        list.sort(Casa.COMPARATORS.get("porDispositivos"));
        return list.subList(0, Math.min(3, list.size()));
    }






    // Ponto 8.2 -> gravar e ler a partir de ficheiro (binários

    public void save(String nomeFicheiro) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public Domus load(String nomeFicheiro) throws IOException, ClassNotFoundException { //IO exeption tambem abrange filenotfoundexecption , dai n dar erro na linha de baixo
        FileInputStream fis = new FileInputStream(nomeFicheiro);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Domus d = (Domus) ois.readObject(); // temos de dizer q esse object é o domus e isso resolve-se com um cast
        ois.close();
        return d;
    }




}



