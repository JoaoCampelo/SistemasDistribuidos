package edu.ufp.inf.sd.rmi.pingpong.client;

import java.rmi.RemoteException;

public class PongClient {

    public static void main(String[] args) throws RemoteException {

        if (args.length == 3) {
            String rmiRegisteryIp = args[0];
            int rmiRegisteryPort = Integer.parseInt(args[1]);
            int playerId = Integer.parseInt(args[2]);
            PongImpl a = new PongImpl(playerId, rmiRegisteryPort, rmiRegisteryIp);
        }
    }
}
