package com.sxkj.blcok.chain;

import lombok.Data;

/**
 * @author zwd
 * 封装区块对象
 */
@Data
public class Block {
    /** 当前块的数字签名 */
    private String hash;
    /** 前一个块的数字签名 */
    private String preHash;
    /** 区块存放的信息 */
    private String data;
    /** 时间戳 */
    private long timeStamp;
    /** 工作证明 */
    private int nonce;

    public Block(String data, String preHash) {
        this.data = data;
        this.preHash = preHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    /**
     * 基于上一个块的数字签名
     * @return
     */
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                preHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        data );
        return calculatedhash;
    }

    /**
     * 挖矿
     * @param difficulty
     */
    public void mineBlock(int difficulty) {
        // 目标值，difficulty越大，计算量越大
        String target = new String(new char[difficulty]).replace('\0','0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined 成功啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦 !!! "+ hash);
    }
}
