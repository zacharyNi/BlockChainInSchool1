package com.nzd.Utils;

import com.nzd.Blocks.Block;
import com.nzd.Blocks.BlockChain;
import com.nzd.vote.VoteManager;

import java.io.*;

public class IOUtils {
    static String  saveInfoPath = "E:\\Block.blk";
    public static void SaveVoteInfo( BlockChain previousResults){
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(saveInfoPath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(previousResults);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BlockChain ReadVoteInfo(){
        FileInputStream is = null;
        ObjectInputStream ois = null;
        BlockChain previousResults = null;
        try {
            is = new FileInputStream(saveInfoPath);
            ois = new ObjectInputStream(is);
            previousResults = (BlockChain) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            //初次创建文件时
            previousResults = new BlockChain();
            previousResults.addBlock(new Block(new VoteManager("null",0),"0"));//创世块
            SaveVoteInfo(previousResults);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return previousResults;

    }


}
