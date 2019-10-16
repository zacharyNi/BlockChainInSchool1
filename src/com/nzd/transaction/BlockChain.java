package com.nzd.transaction;

import com.nzd.Utils.MyUtils;

import java.util.ArrayList;

public class BlockChain{
    //存放所有的区块集合
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;//挖矿的难度，数字越大越难

    /**
     * 检查区块链的完整性
     * @return
     */
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //循环区块链检查散列:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //比较注册散列和计算散列:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //比较以前的散列和注册的先前的散列
            if(!previousBlock.hash.equals(currentBlock.previous_hash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //检查哈希是否被使用
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("这个区块还没有被开采。。。");
                return false;
            }

        }
        return true;
    }
    /**
     * 增加一个新的区块
     * @param newBlock
     */
    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
}
