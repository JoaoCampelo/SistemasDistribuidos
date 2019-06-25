package edu.ufp.inf.sd.rmi.calculadora.server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Calcular {

    public Server() {
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            Calcular stub = (Calcular) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Calcular", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public int soma(int a, int b) throws RemoteException {
        return (a + b);
    }

    @Override
    public int subtracao(int a, int b) throws RemoteException {
        return (a - b);    
    }

    @Override
    public int multiplicacao(int a, int b) throws RemoteException {
        return (a * b);
    }

    @Override
    public int divisao(int a, int b) throws RemoteException {
        return (a / b);
    }
}