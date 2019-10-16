package com.nzd.vote;


import java.io.Serializable;

public class VoteItem implements Serializable {
    int index;
    String Vote_Info;
    int num;//票数

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public VoteItem(int index,String info) {
        this.Vote_Info = info;
        this.index = index;
        this.num = 0;
    }

    public String getVote_Info() {
        return Vote_Info;
    }

    public void setVote_Info(String vote_Info) {
        Vote_Info = vote_Info;
    }

    public void show() {
        System.out.println(this.Vote_Info);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void add(){
        num++;
        return;
    }

    @Override
    public String toString() {
        return "VoteItem[index="+index+",Vote_Info="+Vote_Info+",num="+num+"]";
    }
}

