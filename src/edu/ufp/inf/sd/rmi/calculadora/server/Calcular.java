package edu.ufp.inf.sd.rmi.calculadora.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calcular extends Remote {
    int soma(int a, int b) throws RemoteException;
    int subtracao(int a, int b) throws RemoteException;
    int multiplicacao(int a, int b) throws RemoteException;
    int divisao(int a, int b) throws RemoteException;
}