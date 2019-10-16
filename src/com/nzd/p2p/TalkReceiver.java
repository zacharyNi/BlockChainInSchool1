package com.nzd.p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TalkReceiver implements Runnable{
    private DatagramSocket ds;
    private int port;
    private String info;
    private byte[] container;
    public TalkReceiver(int port) {
        try {
            ds = new DatagramSocket(port);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            container = new byte[1024 * 60];
            DatagramPacket dp = new DatagramPacket(container, 0, container.length);
            //阻塞等待
            try {
                ds.receive(dp);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            byte[] data = dp.getData();
            info = new String(data, 0, data.length);
            System.out.println(info);
            if (info.equals("quit")) {
                break;
            }
        }
        ds.close();
    }


}

