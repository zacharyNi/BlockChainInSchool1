package com.nzd.Blocks;

import com.nzd.Utils.MyUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class BlockChain implements Serializable {
    private static final long serialVersionUID = -93019649351364166L;
    //存放所有的区块集合
    public ArrayList<Block> blockchain = new ArrayList<Block>();

    /**
     * 检查区块链的完整性
     * @return
     */
    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        //循环区块链检查散列:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //比较注册散列和计算散列:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //比较以前的散列和注册的先前的散列
            if(!previousBlock.getHash().equals(currentBlock.getPrevious_hash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }

    /**
     * 增加一个新的区块
     * @param newBlock
     */
    public void addBlock(Block newBlock) {
        blockchain.add(newBlock);
    }

    public Block getPreviousBlock(){
        return blockchain.get(blockchain.size() - 1);
    }

    public void showAllInfo() {
        int i = 0;
        for (Block b : blockchain) {
            System.out.println("Block" + i + ":");
            b.getVoteInfo().showResult();
            i++;
        }
    }

    public void showAllBlock(){
        int i = 0;
        for (Block b : blockchain) {
            System.out.println("Block" + i + ":");
            b.showBlock();
            i++;
        }
    }

    @Override
    public String toString() {
        return "BlockChain[blockchain="+blockchain+"]";
    }
}
