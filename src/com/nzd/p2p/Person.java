package com.nzd.p2p;

import com.nzd.Blocks.Block;
import com.nzd.Blocks.BlockChain;
import com.nzd.Utils.IOUtils;
import com.nzd.vote.AbstractVote;
import com.nzd.vote.VoteItem;
import com.nzd.vote.VoteManager;
import com.nzd.vote.VoteResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
* VERSION 1:
* 1.通信双方只能一收一发，不能边收边发
* */

public class Person {
    Scanner sc;
    List<VoteInfoSender> Senders = new ArrayList<VoteInfoSender>();
    List<VoteResult> results = new ArrayList<>();
    BlockChain previousResult;//登录前读取先前保存的投票记录
    VoteInfoReceiver vir = null;
    VoteManager myVoteManager = null;
    int receiveport;
    int sendPort;
    String toIP;
    int toPort;
    public void MainMenu(){
        System.out.println("欢迎!");
        System.out.println("1.比赛组队");
        System.out.println("2.记账");
        System.out.println("3.投票");
        System.out.println("4.加好友");
        System.out.println("5.quit");
    }

    public void VoteMenu(){
        System.out.println("1.发起投票");
        System.out.println("2.参与投票");
        System.out.println("3.查看历史投票记录");
        System.out.println("4.quit");
    }

    Person(int receiveport,int sendPort,String toIP,int toPort){
        sc = new Scanner(System.in);
        vir = new VoteInfoReceiver(receiveport);
        this.receiveport = receiveport;
        this.sendPort = sendPort;
        this.toIP = toIP;
        this.toPort = toPort;
    }

    public void run(){
        this.MainMenu();
        System.out.println("输入你想使用的功能");
        int choice = 0;
        while(sc.hasNext()){
            choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:break;
                case 2:break;
                case 3:this.vote();break;
                case 4:this.addFriends();break;
                case 5:break;
                default:break;
            }
        }
    }

    public void vote(){
        //VERSION 1:只能选择参与投票或者发起投票
        //读取先前的投票纪录
        this.previousResult = IOUtils.ReadVoteInfo();
        this.VoteMenu();
        System.out.println("输入你想使用的功能");
        sc = new Scanner(System.in);
        int choice = 0;
        while(sc.hasNext()){
            choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:this.setVote();break;
                case 2:this.inVoting();break;
                case 3:this.readVoteResult();break;
                default:break;
            }
        }
    }

    public void setVote(){
        //发起投票
        System.out.println("输入投票的主题");
        String Subject = sc.nextLine();
        System.out.println("输入参与投票的人数：(目前只有两人)");
        int num = Integer.parseInt(sc.nextLine());
        String info;
        myVoteManager = new VoteManager(Subject,num);
        System.out.println("设置投票选项数：");
        int count = Integer.parseInt(sc.nextLine());
        for(int i = 0;i < count;i++){
            System.out.println("设置第"+(i+1)+"个选项内容");
            info = sc.nextLine();
            myVoteManager.addVoteItems(new VoteItem(i+1,info));
        }
        //网络传输 sender
        //目前只能两个人投票。。
        Senders.add(new VoteInfoSender(sendPort,toIP,toPort));
        for(int i = 0;i < Senders.size();i++){
            Senders.get(i).SendMsg(myVoteManager);
        }
        //自己投一票
        VoteResult myVoteItem = myVoteManager.Voting();
        this.results.add(myVoteItem);
        //发送自己的投票结果
        for(int i = 0;i < myVoteManager.getPopulation() - 1;i++){//new changed
            Senders.get(i).SendMsg(myVoteItem);
        }
        //获取投票结果
        while(results.size() !=this.myVoteManager.getPopulation()){
            Voting();
        }
        //处理投票结果
        for(int i = 0;i < results.size();i++){
            myVoteManager.getVoteItems().get(results.get(i).getResult().getIndex()-1).add();//new changed
        }
        myVoteManager.showResult();
        for(int i = 0;i < Senders.size();i++){
            Senders.get(i).close();
        }
        //保存投票结果
        previousResult.addBlock(new Block(myVoteManager,previousResult.getPreviousBlock().getHash()));
        IOUtils.SaveVoteInfo(previousResult);
    }

    public void inVoting(){
        //获得VoteManager
        while(this.myVoteManager == null){
            AbstractVote abstractVote = vir.ReceiveMsg();
            if(abstractVote instanceof VoteManager){
                this.myVoteManager = (VoteManager)abstractVote;
                break;
            }else if(abstractVote instanceof  VoteResult){
                this.results.add((VoteResult)abstractVote);
            }else{
                continue;
            }
        }
        //投票
        VoteResult myResult = myVoteManager.Voting();
        this.results.add(myResult);
        //发送投票结果
        Senders.add(new VoteInfoSender(sendPort,toIP,toPort));
        for(int i = 0;i < Senders.size();i++){
            Senders.get(i).SendMsg(myResult);
        }
        //获取投票结果
        AbstractVote abstractVote;
        while(results.size() !=this.myVoteManager.getPopulation()){
            abstractVote = vir.ReceiveMsg();
            if(abstractVote instanceof  VoteManager){
                continue;
            }else if(abstractVote instanceof  VoteResult){
                results.add((VoteResult)abstractVote);
            }else{
                continue;
            }
        }
        //处理投票结果
        for(int i = 0;i < results.size();i++){
            myVoteManager.getVoteItems().get(results.get(i).getResult().getIndex()-1).add();
        }
        myVoteManager.showResult();
        vir.close();
    }



    public void Voting(){
        AbstractVote abstractVote = vir.ReceiveMsg();
        if(abstractVote instanceof VoteManager){
            if(this.myVoteManager != null){
                return;
            }else{
                VoteManager voteManager = (VoteManager)abstractVote;
                VoteResult voteResult = voteManager.Voting();
                //目前只能两个人之间投票
                VoteInfoSender vis = new VoteInfoSender(sendPort,toIP,toPort);
                vis.SendMsg(voteResult);
                return;
            }
        }else if(abstractVote instanceof  VoteResult){
            results.add((VoteResult)abstractVote);
            return;
        }else{
            return;
        }
    }

    public void readVoteResult(){
        previousResult.showAllInfo();
    }

    public void addFriends(){
        //..未完待续
    }

}