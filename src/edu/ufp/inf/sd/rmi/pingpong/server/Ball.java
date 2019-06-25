/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rmi.pingpong.server;

import java.io.Serializable;

/**
 *
 * @author joaoc
 */
public class Ball implements Serializable{
    
    public int PlayerId;

    public Ball(int PlayerId) {
        this.PlayerId = PlayerId;
    }

    public int getPlayerId() {
        return PlayerId;
    }
    
}
