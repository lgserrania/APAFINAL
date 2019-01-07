/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apafinal;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Gustavo
 */
public class Instancia {
    
    public String nome;
    public int numCaminhoes;
    public int capacidade;
    public ArrayList<Cliente> clientes;
    public ArrayList<Caminhao> caminhoes;
    Random gen = new Random();

    public Instancia(String nome, int numCaminhoes, int capacidade) {
        this.nome = nome;
        this.numCaminhoes = numCaminhoes;
        this.caminhoes = new ArrayList(this.numCaminhoes);
        Util.capacidade = capacidade;
        clientes = new ArrayList();    
    }
    
    public void adicionarCliente(Cliente cliente){
        this.clientes.add(cliente);
    }
    
    public void inicializaCaminhoes(){
        for(int i = 0; i < this.numCaminhoes; i++){
            this.caminhoes.add(new Caminhao());
        }
    }
    
    public void alocacaoInicial(){
        this.clientes.forEach(c -> {           
            for(int i = 0; i < this.caminhoes.size(); i++){               
                if(this.caminhoes.get(i).adicionaParada(c)) break;               
            }          
        });
    }
    
    public double distanciaTotal(){
        double distanciaTotal = 0;
        
        for(int i = 0; i < this.numCaminhoes; i++){
            distanciaTotal += this.caminhoes.get(i).totalDistancia();
        }
        
        return distanciaTotal;
    }
    
    public void imprime(){
        
        double distanciaTotal = 0;
        
        for(int i = 0; i < this.caminhoes.size(); i++){
            System.out.printf("Caminhão %d: %.2f %n",i, this.caminhoes.get(i).totalDistancia());
            distanciaTotal += this.caminhoes.get(i).totalDistancia();
            for(int j = 0; j < this.caminhoes.get(i).paradas.size(); j++){
                Parada parada = this.caminhoes.get(i).paradas.get(j);
                System.out.print(parada.cliente.num + " -> ");
            }
            System.out.println("#");
        }
        
        System.out.printf("Distância Total: %.2f %n", distanciaTotal);
    }
    
    public void hillclimb(){ 
       
        for(int i = 0; i < 10000000; i++){
            if(gen.nextDouble() <= 0.5){
                this.swap();
            }else{
                this.reinsert();
            }
        }
    }
    
    public void reinsert(){
        
        Caminhao caminhao1;
        Caminhao caminhao2;
        boolean reinsert = false;
        //Sorteia os dois caminhões que sofrerão as trocas
        //Pode ocorrer uma troca dentro de um mesmo caminhão
        do{
            caminhao1 = this.caminhoes.get(gen.nextInt(this.numCaminhoes));
            caminhao2 = this.caminhoes.get(gen.nextInt(this.numCaminhoes));
        }while(caminhao1.paradas.size() <= 0 && caminhao2.paradas.size() <= 0);
        
        //Sorteia uma parada do primeiro caminhão
        int index = gen.nextInt(caminhao1.paradas.size());
        Parada parada = caminhao1.paradas.get(index);
        caminhao1.remove(parada);
        
        double distanciaAtual = this.distanciaTotal();
        
        for(int i = 0; i < caminhao2.paradas.size(); i++){
            if(caminhao2.adicionaParada(i, parada)){
                double distanciaNova = this.distanciaTotal();
                if(distanciaNova < distanciaAtual){
                    System.out.println("Reinseriu");
                    reinsert = true;
                    break;
                }
                else{
                    caminhao2.remove(parada);
                }
            }
        }
        
        if(!reinsert){
           caminhao1.voltaParada(index, parada);
        }
    }
    
    public void swap(){
        Caminhao caminhao1;
        Caminhao caminhao2;

        //Sorteia os dois caminhões que sofrerão as trocas
        //Pode ocorrer uma troca dentro de um mesmo caminhão
        do{
            caminhao1 = this.caminhoes.get(gen.nextInt(this.numCaminhoes));
            caminhao2 = this.caminhoes.get(gen.nextInt(this.numCaminhoes));
        }while(caminhao1.paradas.size() <= 0 && caminhao2.paradas.size() <= 0);

        Parada parada1 = caminhao1.paradas.get(gen.nextInt(caminhao1.paradas.size()));
        Parada parada2 = caminhao2.paradas.get(gen.nextInt(caminhao2.paradas.size()));
        
        double distanciaAtual = this.distanciaTotal();

        //Caso seja possível trocar os dois a troca é realizada
        if(caminhao1.trocaParadas(parada1, parada2) && caminhao2.trocaParadas(parada2, parada1)){
            int index1 = caminhao1.paradas.indexOf(parada1);
            int index2 = caminhao2.paradas.indexOf(parada2);
            caminhao1.setParada(parada2, index1);
            caminhao2.setParada(parada1, index2);
            double distanciaNova = this.distanciaTotal();
            if(distanciaNova > distanciaAtual){
                caminhao1.setParada(parada1, index1);
                caminhao2.setParada(parada2, index2);
            }
        }
    }   
    
}


