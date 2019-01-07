/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apafinal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Gustavo
 */
public class CarregaArquivo {
    
    FileReader arq;
    BufferedReader lerArq;
    Instancia instancia;
    
    public CarregaArquivo(String diretorio) throws FileNotFoundException, IOException{
        arq = new FileReader(diretorio);
        lerArq = new BufferedReader(arq);
        
        String linha = lerArq.readLine(); //A primeira linha é o nome da instância
        String nome = linha;
        
        lerArq.readLine(); //Linha em branco
        lerArq.readLine(); //Linha sem importância
        lerArq.readLine(); //Linha de cabeçalho
        
        linha = lerArq.readLine(); //Linha com número de veículos e capacidade
        String partes[] = linha.split("\\s+");
        
        int numCaminhoes = Integer.parseInt(partes[1]);
        int capacidade = Integer.parseInt(partes[2]);
        
        instancia = new Instancia(nome, numCaminhoes, capacidade);
        
        lerArq.readLine(); //Linha em branco
        lerArq.readLine(); //Linha sem importância
        lerArq.readLine(); //Linha de cabeçalho
        lerArq.readLine(); //Linha em branco 
        
        linha = lerArq.readLine(); //Linha com número de veículos e capacidade
        
        do{
       
            partes = linha.split("\\s+");
            int num = Integer.parseInt(partes[1]);
            int x = Integer.parseInt(partes[2]);
            int y = Integer.parseInt(partes[3]);
            int demanda = Integer.parseInt(partes[4]);
            int inicio = Integer.parseInt(partes[5]);
            int fim = Integer.parseInt(partes[6]);
            int atendimento = Integer.parseInt(partes[7]);
            instancia.adicionarCliente(new Cliente(num,x,y,demanda,inicio,fim,atendimento));
            linha = lerArq.readLine(); //Linha com número de veículos e capacidade

        }while(linha != null);
        
        Util.base = instancia.clientes.get(0);
        instancia.clientes.remove(0);
    }
}
