package com.nzd.vote;

public class VoteResult extends AbstractVote{
    VoteResult(VoteItem result){
        this.result = result;
    }

    public VoteItem getResult() {
        return result;
    }

    public void setResult(VoteItem result) {
        this.result = result;
    }

    VoteItem result;

}
