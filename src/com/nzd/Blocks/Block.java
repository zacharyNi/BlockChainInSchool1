package com.nzd.Blocks;

import com.nzd.Utils.MyUtils;
import com.nzd.vote.VoteManager;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {

    private static final long serialVersionUID = 1360083184095236410L;
    private String hash;
    private String previous_hash;
    private VoteManager voteInfo; //记录投票纪录
    private long timeStamp; //as number of milliseconds since 1/1/1970.

    //Block Constructor.
    public Block(VoteManager v,String previousHash ) {
        this.voteInfo = v;
        this.previous_hash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = MyUtils.applySha256(
                previous_hash +
                        Long.toString(timeStamp) +
                        voteInfo.toString()
        );
        return calculatedhash;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevious_hash() {
        return previous_hash;
    }

    public VoteManager getVoteInfo() {
        return voteInfo;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void showBlock(){
        System.out.println("-----------------------------");
        System.out.println("previous hash: "+this.previous_hash);
        System.out.println("hash: "+this.hash);
        System.out.println("timeStamp: "+this.timeStamp);
        System.out.println("VoteM"+voteInfo.toString());
        System.out.println("-----------------------------");
        //this.voteInfo.showResult();
    }

    @Override
    public String toString() {
        return "Block[hash="+hash+",previous_hash="+previous_hash+",voteInfo="+voteInfo+",timeStamp="+timeStamp+"]";
    }
}