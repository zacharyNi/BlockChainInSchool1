package com.nzd.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TalkSender implements  Runnable{
    private DatagramSocket ds;
    private int port;
    private int toport;
    private String toIP;
    private BufferedReader br;
    private String info;
    public TalkSender(int port,String toIP,int toport) {
        this.port = port;
        this.toport = toport;
        this.toIP = toIP;
        try {
            ds = new DatagramSocket(port);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        br = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        byte[] data;
        while(true) {
            try {
                info = br.readLine();
                data = info.getBytes();
                DatagramPacket sender = new DatagramPacket(data,0,data.length,new InetSocketAddress(toIP,toport));
                ds.send(sender);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(info.equals("quit")) {
                break;
            }
        }
        ds.close();
    }
}
