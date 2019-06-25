package edu.ufp.inf.sd.rmi.calculator.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI {


    public CalculatorImpl() throws RemoteException {
        super();
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
        return (a * b);
    }

    
}