/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apafinal;

/**
 *
 * @author Gustavo
 */
public class Parada {
    
    public Cliente cliente;
    public double chegada;
    public double saida;
    
    public Parada(Cliente cliente, double chegada){
        this.cliente = cliente;
        this.chegada = chegada;
        this.saida = chegada;
        
        //Se o tempo de chegada for menor que o início do atendimento
        //ele precisará esperar o início
        if(this.chegada < cliente.inicio){
            this.saida += cliente.inicio - this.chegada;
        }
        
        //Soma o resultado com o tempo de atendimento
        this.saida += cliente.atendimento;
    }
    
}
