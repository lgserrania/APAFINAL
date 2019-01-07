/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apafinal;

import java.io.IOException;

/**
 *
 * @author Gustavo
 */
public class APAFINAL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        CarregaArquivo ca = new CarregaArquivo("R101.txt");
        Instancia instancia = ca.instancia;
        instancia.inicializaCaminhoes();
        instancia.alocacaoInicial();
        instancia.hillclimb();
        instancia.imprime();
    }
    
}
