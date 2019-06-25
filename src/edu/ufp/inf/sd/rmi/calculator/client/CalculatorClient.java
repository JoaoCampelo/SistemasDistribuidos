package edu.ufp.inf.sd.rmi.calculator.client;

import  edu.ufp.inf.sd.rmi.calculator.server.CalculatorRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class  CalculatorClient {


    private SetupContextRMI contextRMI;
    private  CalculatorRI myRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.exit(-1);
        } else {
            CalculatorClient clt = new CalculatorClient(args);
            clt.lookupService();
            clt.playService();
        }
    }

    public CalculatorClient(String[] args) {
        try {
            String registryIP   = args[0];
            String registryPort = args[1];
            String serviceName  = args[2];
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(CalculatorClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);                
                myRI = (CalculatorRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return myRI;
    }
    
    private void playService() {
        Scanner entrada = new Scanner(System.in);
        int a = 0, b = 0, response=0;
        String operacao = "";
        
        
        System.out.println("Operacao: ");
        operacao = entrada.nextLine();
        System.out.println("Primeiro Numero: ");
        a = entrada.nextInt();
        System.out.println("Segundo Numero: ");
        b = entrada.nextInt();
        try {
            if(null != operacao)switch (operacao) {
                case "+":
                    response = this.myRI.soma(a, b);
                    break;
                case "-":
                    response = this.myRI.subtracao(a, b);
                    break;
                case "*":
                    response = this.myRI.multiplicacao(a, b);
                    break;
                case "/":
                    if(b != 0){
                        response = this.myRI.divisao(a, b);
                    }
                    else{
                        System.out.println("ERRO!! Não é possive dividir por 0");
                    }   break;
                default:
                    break;
            }
            System.out.println("Resultado: " + response);
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
