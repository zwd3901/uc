package com.sxkj.blcok.chain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockChain {
    /** 存放所有区块 */
    private List<Block> blockchain = new ArrayList<>();
    /** 计算难度，值越大难度越大 */
    private int difficulty;

    public BlockChain(int difficulty) {
        this.difficulty = difficulty;
    }
    /**
     * 检查区块链的完整性，是否经过篡改
     * @return
     */
    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[getDifficulty()]).replace('\0', '0');
        // 循环区块链
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            // 比较注册hash和计算的hash
            if(!currentBlock.getHash().equals(currentBlock.calculateHash())){
                System.out.println("Current Hashes not equal !");
                return false;
            }
            // 比较上一个hash和注册hash的上一个hash
            if(!previousBlock.getHash().equals(currentBlock.getPreHash())){
                System.out.println("Previous Hashes not equal !");
                return false;
            }
            // 检查hash是否被使用
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    /**
     * 添加新块
     * @param newBlock
     */
    public void addBlock(Block newBlock) {
        newBlock.mineBlock(getDifficulty());
        blockchain.add(newBlock);
    }

    public void addBlock(String data,String preHash) {
        Block block = new Block(data, preHash);
        block.mineBlock(getDifficulty());
        blockchain.add(block);
    }

    public void addBlock(String data) {
        if (blockchain == null || blockchain.size() == 0) {
            addFirstBlock(data);
        }else{
            Block preBlock = blockchain.get(blockchain.size()-1);
            Block block = new Block(data, preBlock.getHash());
            block.mineBlock(getDifficulty());
            blockchain.add(block);
        }

    }

    public void addFirstBlock(String data) {
        addBlock(data,"0");
    }
}
