package com.nzd.p2p;

import com.nzd.vote.AbstractVote;
import com.nzd.vote.VoteManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class VoteInfoReceiver {
    private DatagramSocket ds;
    private int port;
    private String info;
    private byte[] container;
    public  VoteInfoReceiver(int port) {
        try {
            ds = new DatagramSocket(port);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public AbstractVote ReceiveMsg(){
        container = new byte[1024 * 60];
        AbstractVote av = null;
        DatagramPacket dp = new DatagramPacket(container, 0, container.length);
        //阻塞等待
        try {
            ds.receive(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = dp.getData();
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));

            try {
                av = (AbstractVote) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ds.close();
        return av;
    }
}
