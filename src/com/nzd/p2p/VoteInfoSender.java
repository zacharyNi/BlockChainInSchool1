package com.nzd.p2p;
import com.nzd.vote.AbstractVote;
import com.nzd.vote.VoteItem;
import com.nzd.vote.VoteManager;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class VoteInfoSender {

        private DatagramSocket ds;
        private int port;
        private int toport;
        private String toIP;
        private BufferedReader br;
        private String info;
        public VoteInfoSender(int port,String toIP,int toport) {
            this.port = port;
            this.toport = toport;
            this.toIP = toIP;
            try {
                ds = new DatagramSocket(port);
            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void SendMsg(AbstractVote abstractVote){
            //对象流  写  对象输出流
            //内存流（基类流）
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //对象流（包装流 ）因为传输对象
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                //将对象写入对象流
                oos.writeObject(abstractVote);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //将对象转化为字节数组  因为数据包需要字节数组  该方法属于字节流  很重要！
            byte[] buf = baos.toByteArray();

            //将字节数组的数据放入数据包
            DatagramPacket dp = new DatagramPacket(buf, buf.length,new InetSocketAddress(toIP,toport));
            //向客户端传输数据包
            try {
                ds.send(dp );
            } catch (IOException e) {
                e.printStackTrace();
            }
            //关流
            ds.close();}

}

