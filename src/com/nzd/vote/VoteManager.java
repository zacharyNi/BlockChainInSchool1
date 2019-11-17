package com.nzd.vote;

import com.nzd.p2p.VoteInfoSender;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 用于存放投票信息
 */
public class VoteManager extends AbstractVote {
    String VoteSubject;
    List<VoteItem> VoteItems = new ArrayList<VoteItem>();
    int population;//参与投票的人数

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public List<VoteItem> getVoteItems() {
        return VoteItems;
    }

    public void setVoteItems(List<VoteItem> voteItems) {
        VoteItems = voteItems;
    }

    public VoteManager(String subject, int num){
        this.VoteSubject = subject;
        this.population = num;
    }

    public void addVoteItems(VoteItem t){
        VoteItems.add(t);
    }

    public void show(){
        System.out.println(this.VoteSubject);
        int count = 1;
        for(VoteItem i:VoteItems){
            System.out.print(count);
            System.out.print('.');
            i.show();
            count++;
        }
    }

    public VoteResult Voting(){
        this.show();
        Scanner scanner =  new Scanner(System.in);
        System.out.println("输入你要选择：");
        int choice = Integer.parseInt(scanner.nextLine());
        return new VoteResult(VoteItems.get(choice - 1));
    }

    public void showResult(){
        System.out.println(this.VoteSubject);
        for(VoteItem i:VoteItems){
            System.out.print(i.getIndex());
            System.out.print('.');
            System.out.print(i.getVote_Info());
            System.out.print('\t');
            System.out.println(i.getNum());
        }
    }

    @Override
    public String toString() {
        return  "VoteManager[VoteSubject="+VoteSubject+",VoteItems="+VoteItems+",population="+population+"]";
    }
}

