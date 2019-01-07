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
public class Cliente {
    
    public int num;
    public int x;
    public int y;
    public int demanda;
    public int inicio;
    public int fim;
    public int atendimento;

    public Cliente(int num, int x, int y, int demanda, int inicio, int fim, int atendimento) {
        this.num = num;
        this.x = x;
        this.y = y;
        this.demanda = demanda;
        this.inicio = inicio;
        this.fim = fim;
        this.atendimento = atendimento;
    }
    
    
}
