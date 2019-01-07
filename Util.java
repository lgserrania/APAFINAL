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
public class Util {
    
    public static Cliente base;
    public static int capacidade;
    
    public static double distancia(Cliente c1, Cliente c2){
        double result = Math.sqrt(Math.pow((c2.x - c1.x), 2) + Math.pow((c2.y - c1.y), 2));
        return result;
    }
    
}
