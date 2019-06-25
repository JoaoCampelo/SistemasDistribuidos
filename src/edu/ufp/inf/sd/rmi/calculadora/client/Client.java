package edu.ufp.inf.sd.rmi.calculadora.client;

import edu.ufp.inf.sd.rmi.calculadora.server.Calcular;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client {

    private Client() {
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String host = (args.length < 1) ? null : args[0];
        int a = 0, b = 0, response=0;
        String operacao = "";
        
        System.out.println("Operacao: ");
        operacao = entrada.nextLine();
        System.out.println("Primeiro Numero: ");
        a = entrada.nextInt();
        System.out.println("Segundo Numero: ");
        b = entrada.nextInt();
        
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Calcular stub = (Calcular) registry.lookup("Calcular");
            
            if(null != operacao)switch (operacao) {
                case "+":
                    response = stub.soma(a, b);
                    break;
                case "-":
                    response = stub.subtracao(a, b);
                    break;
                case "*":
                    response = stub.multiplicacao(a, b);
                    break;
                case "/":
                    if(b != 0){
                        response = stub.divisao(a, b);
                    }
                    else{
                        System.out.println("ERRO!! Não é possive dividir por 0");
                    }   break;
                default:
                    break;
            }
            System.out.println("Resultado: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}