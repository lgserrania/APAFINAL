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
public class Util {
    
    public static Cliente base;
    public static int capacidade;
    
    public static double distancia(Cliente c1, Cliente c2){
        double result = Math.sqrt(Math.pow((c2.x - c1.x), 2) + Math.pow((c2.y - c1.y), 2));
        return result;
    }
    
    public static boolean validaRota(ArrayList<Cliente> clientes){
        double carga = 0;
        double tempo = Util.distancia(base, clientes.get(0));
        
        if(tempo < clientes.get(0).inicio){
            tempo += clientes.get(0).inicio - tempo;
        }
        tempo += clientes.get(0).atendimento;
        carga += clientes.get(0).demanda;
        
        for(int i = 1; i < clientes.size() - 1; i++){
            tempo += Util.distancia(clientes.get(i - 1), clientes.get(i));
            if(tempo > clientes.get(i).fim) return false;
            if(tempo < clientes.get(i).inicio) tempo += clientes.get(i).inicio - tempo;
            tempo += clientes.get(i).atendimento;
            carga += clientes.get(i).demanda;
            if(carga > capacidade) return false;
        }
        
        tempo += Util.distancia(clientes.get(clientes.size() - 1), base);
        if(tempo > clientes.get(clientes.size() - 1).fim) return false;
        return true;
    }
    
}
