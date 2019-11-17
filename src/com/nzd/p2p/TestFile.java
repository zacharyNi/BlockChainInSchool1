package com.nzd.p2p;

import com.nzd.Blocks.Block;
import com.nzd.Blocks.BlockChain;
import com.nzd.Utils.IOUtils;
import com.nzd.vote.VoteItem;
import com.nzd.vote.VoteManager;

public class TestFile {
    public static void main(String[] args) {
        BlockChain chains = IOUtils.ReadVoteInfo();

        System.out.println(chains.isChainValid());
    }
}

