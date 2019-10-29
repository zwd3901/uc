package com.sxkj.blcok.chain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Main {
    /** 存放所有区块 */
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    /** 计算难度，值越大难度越大 */
    public static int difficulty = 5;

    public static void main(String[] args) {
        /*
        long beginTime1 = System.currentTimeMillis();
        blockchain.add(new Block("Hi i am first block", "0"));
        System.out.println("Trying to mine block 1 ...");
        blockchain.get(0).mineBlock(difficulty);
        long endTime1 = System.currentTimeMillis();
        System.err.println("Mining block 1 cost "+(endTime1-beginTime1));

        long beginTime2 = System.currentTimeMillis();
        blockchain.add(new Block("Hi i am the second block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to mine block 2 ...");
        blockchain.get(1).mineBlock(difficulty);
        long endTime2 = System.currentTimeMillis();
        System.err.println("Mining block 1 cost "+(endTime2-beginTime2));

        long beginTime3 = System.currentTimeMillis();
        blockchain.add(new Block("Hi i am the third block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to mine block 3 ...");
        blockchain.get(2).mineBlock(difficulty);
        long endTime3 = System.currentTimeMillis();
        System.err.println("Mining block 1 cost "+(endTime3-beginTime3));
        */

        addBlock(new Block("firstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirst","0"));
        addBlock(new Block("secondfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirst",blockchain.get(blockchain.size()-1).hash));
        addBlock(new Block("thirdfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirstfirst",blockchain.get(blockchain.size()-1).hash));

        System.out.println("\n Blockchain is Valid : "+isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.err.println(blockchainJson.length());
        /*for(int i=0;i<3;i++){
            System.out.println(blockchain.get(i).getData());
        }*/


    }

    /**
     * 检查区块链的完整性，是否经过篡改
     * @return
     */
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        // 循环区块链
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            // 比较注册hash和计算的hash
            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current Hashes not equal !");
                return false;
            }
            // 比较上一个hash和注册hash的上一个hash
            if(!previousBlock.hash.equals(currentBlock.preHash)){
                System.out.println("Previous Hashes not equal !");
                return false;
            }
            // 检查hash是否被使用
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
}
