package com.nzd.vote;

import com.google.gson.JsonObject;
import netscape.javascript.JSObject;

public class Test {
    public static void main(String[] args){
        VoteManager vm = new VoteManager("班费收多少？",2);
        vm.addVoteItems(new VoteItem(1,"8000"));
        vm.addVoteItems(new VoteItem(2,"9000"));
        vm.addVoteItems(new VoteItem(3,"800"));
        System.out.println(vm.toString());

    }
}
