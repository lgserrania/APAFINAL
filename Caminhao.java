/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apafinal;

import java.util.ArrayList;

/**
 *
 * @author Gustavo
 */
public class Caminhao {
    
    ArrayList<Parada> paradas;
    double chegadaBase;
    double carga = 0;
    
    public Caminhao(){
        this.paradas = new ArrayList();
    }
    
    public boolean adicionaParada(Cliente c1){
        //Se a demanda do cliente somada com a carga atual do caminhão
        //ultrapassar a capacidade máxima, a inserção não é feita
        if((this.carga + c1.demanda) > Util.capacidade) return false;
        Parada parada;
        double distancia;
        double distanciaBase;
        //Caso a lista de paradas esteja vazia, a distancia será calculada a partir da base
        if(this.paradas.isEmpty()){
            distancia = Util.distancia(Util.base, c1);
            parada = new Parada(c1, distancia);
            distanciaBase = distancia;
        }else{ //Caso não esteja vazia, a distância é calculada a partir da última parada
            Cliente c2 = this.last().cliente;
            distancia = Util.distancia(c2, c1);
            //Se o tempo de saída da última parada, somado com a distancia entre ela e a que está 
            //sendo inserida, for maior que o tempo de inicio da nova parada
            //a inserção não pode ser realizada.
            if((this.last().saida + distancia) > c1.inicio) return false; 
            parada = new Parada(c1, distancia);
            distanciaBase = Util.distancia(Util.base, c1);
        }
        
        //Caso com a inserção da nova parada, o tempo de chegada ao retornar a base
        //fique maior do que o estipulado, a inserção não é feita
        if((parada.saida + distanciaBase) > Util.base.fim) return false;
        
        this.chegadaBase = parada.saida + distancia;
        this.paradas.add(parada);
        this.carga += c1.demanda;
        return true;
    }
    
    public Parada last(){
        return this.paradas.get(this.paradas.size() - 1);
    }
    
    public Parada first(){
        return this.paradas.get(0);
    }
    
    public boolean trocaParadas(Parada antiga, Parada nova){
        Cliente cnovo = nova.cliente;
        Cliente cantigo = antiga.cliente;
        
        if(cantigo.demanda < cnovo.demanda){
            if(this.carga + (cnovo.demanda - cantigo.demanda) > Util.capacidade) return false;
        }
        
        int index = this.paradas.indexOf(antiga);
        
        Parada antes;
        
        if(index == 0){
            antes = new Parada(Util.base,0);
        }else{
            antes = this.paradas.get(index - 1);
        }
        nova.chegada = antes.saida + Util.distancia(antes.cliente, nova.cliente);
        nova.saida = nova.chegada + nova.cliente.atendimento;
        
        return testaTempo(nova, index);
    }
    
    private boolean testaTempo(Parada parada, int index){
        
        //Se estiver inserindo no último ponto, ele só verifica se chegará a tempo
        //na base
        if(index + 1 > this.paradas.size()){
            if(parada.saida + Util.distancia(parada.cliente, Util.base) > Util.base.fim){
                return false;
            }else{
                return true;
            }
        }
        
        double tempo = parada.saida;
        
        //Caso esteja inserindo no meio .. ele irá verificar o tempo de cada
        //parada subsequente após a inserção da nova parada
        for(int i = index + 1; i < this.paradas.size(); i++){
            tempo += Util.distancia(parada.cliente, this.paradas.get(i).cliente);
            if(tempo > this.paradas.get(i).cliente.fim) return false;
            tempo += this.paradas.get(i).cliente.atendimento;
        }
        
        tempo += Util.distancia(this.last().cliente, Util.base);
        
        if(tempo > Util.base.fim) return false;
        return true;
        
    }
    
    //Adiciona uma parada em um index pré definido
    public boolean adicionaParada(int index, Parada nova){
        
        this.paradas.add(index, nova);
        this.atualizaCaminhao();
        if(!this.validaParadas()){
            this.remove(nova);
            this.atualizaCaminhao();
            return false;
        }
        return true;
    }
    
    public void atualizaCaminhao(){
        int carga = 0;
        
        if(this.paradas.isEmpty()) return;
        
        this.paradas.get(0).chegada = Util.distancia(Util.base, this.paradas.get(0).cliente);
        this.paradas.get(0).saida = this.paradas.get(0).chegada + this.paradas.get(0).cliente.atendimento;
        carga += this.paradas.get(0).cliente.demanda;
        
        for(int i = 1; i < this.paradas.size() - 1; i++){
            Parada atual = this.paradas.get(i);
            Parada antes = this.paradas.get(i - 1);
            
            atual.chegada = antes.saida + Util.distancia(atual.cliente, antes.cliente);
            atual.saida = atual.chegada + atual.cliente.atendimento;
            carga += atual.cliente.demanda;
        }
        
        this.chegadaBase = this.last().saida + Util.distancia(this.last().cliente, Util.base);
    }
    
    private boolean validaParadas(){
        if(this.chegadaBase > Util.base.fim) return false;
        if(this.carga > Util.capacidade) return false;
        
        for(int i = 0; i < this.paradas.size(); i++){
            Parada atual = this.paradas.get(i);
            if(atual.chegada > atual.cliente.fim) return false;
        }
        
        return true;
    }
    
    public void voltaParada(int index, Parada parada){
        this.paradas.add(index, parada);
        this.carga += parada.cliente.demanda;
    }
    
    public void setParada(Parada novo, int index){
        
        Parada atual;
        Parada antes;
        Parada antigo = this.paradas.get(index);
        
        this.carga += novo.cliente.demanda - antigo.cliente.demanda;
        
        //Caso vá ser colocado na primeira posição
        //o anterior é a base
        if(index == 0){
            antes = new Parada(Util.base,0);
        }else{
            antes = this.paradas.get(index - 1);
        }
        
        this.paradas.set(index, novo);
        novo.chegada = antes.saida + Util.distancia(antes.cliente, novo.cliente);
        novo.saida = novo.chegada + novo.cliente.atendimento;
        
        //Calcula os tempos nas paradas subsequentes
        for(int i = index + 1; i < this.paradas.size(); i++){
            antes = this.paradas.get(i - 1);
            atual = this.paradas.get(i);
            atual.chegada = antes.saida + Util.distancia(antes.cliente, atual.cliente);
            atual.saida = atual.chegada + atual.cliente.atendimento;
        }
        
        atual = this.last();
        this.chegadaBase = atual.saida + Util.distancia(atual.cliente, Util.base);
        
    }
    
    public double totalDistancia(){
        
        if(this.paradas.isEmpty()) return 0;
        
        double distancia = Util.distancia(Util.base, this.paradas.get(0).cliente);
        
        for(int i = 0; i < this.paradas.size() - 1; i++){
            distancia += Util.distancia(this.paradas.get(i).cliente, this.paradas.get(i + 1).cliente);
        }
        
        distancia += Util.distancia(this.last().cliente, Util.base);
        
        return distancia;
        
    }
    
    public void remove(Parada parada){
        this.carga -= parada.cliente.demanda;
        this.paradas.remove(parada);
    }
    
    public void remove(int index){
        Parada parada = this.paradas.get(index);
        this.carga -= parada.cliente.demanda;
        this.paradas.remove(parada);
    }
    
}
