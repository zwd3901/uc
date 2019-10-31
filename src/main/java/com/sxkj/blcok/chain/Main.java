package com.sxkj.blcok.chain;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sxkj.mock.Mock;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class Main {
    private String rootDir;
    private String selfFile;
    private String otherDir;

    private BlockChain blockChain;

    public Main(BlockChain blockChain,String rootDir,String selfFile,String otherDir) {
        this.rootDir = rootDir;
        this.selfFile = selfFile;
        this.otherDir = otherDir;

        this.blockChain = blockChain;
        //scanChain(other);
        init(this.rootDir,this.selfFile);
        //save(rootDir,selfFile);
        //save(blockChain.getBlockchain());
        createAndSave();
    }

    /**
     * 解析文件内容，获取区块链
     * @param fileName 文件名
     * @return
     */
    public List<Block> parseData(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return null;
            }
            FileInputStream input = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = input.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            return convert(sb.toString());
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
        return null;
    }

    /**
     * 字符串转换为区块链
     * @param blockChainJson 字符串
     * @return
     */
    public List<Block> convert(String blockChainJson) {
        Type type = new TypeToken<List<Block>>(){}.getType();
        return new GsonBuilder().setPrettyPrinting().create().fromJson(blockChainJson, type);
    }

    /**
     * 扫描，获取最长链
     * @return
     */
    public void scanChain(){
        // todo 扫描,获取最长链
        List<Block> selfList = parseData(this.rootDir+File.separator+this.selfFile);
        List<Block> result = selfList==null?new ArrayList<>(16):selfList;

        File other = new File(this.otherDir);
        if (other.isDirectory()) {
            String[] fileArray = other.list();
            for (int i = 0, len = fileArray.length; i < len; i++) {
                List<Block> tmp = parseData(otherDir+File.separator+fileArray[i]);
                if(tmp!=null &&tmp.size()>result.size()){
                    result = tmp;
                }
            }
        }
        if (result == null || result.isEmpty()) {
            blockChain.addFirstBlock("first");
        }else {
            log.info("=====================================================================");
            log.info(new GsonBuilder().setPrettyPrinting().create().toJson(result));
            log.info("=====================================================================");
            blockChain.setBlockchain(result);
        }

    }

    private void init(String rootDir,String selfFile) {
        // todo 在本地保存区块链
        File dir = new File(rootDir);
        File file = new File(rootDir + File.separator + selfFile);
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            } else if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
        /*try(FileWriter writer = new FileWriter(file)) {

            blockChain.addFirstBlock("first");
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(blockChain.getBlockchain()));
            writer.flush();
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }*/
    }

    public void createAndSave() {
        // todo 把新的块添加到链上，并保存到本地
        scanChain();
        File file = new File(this.rootDir + File.separator + this.selfFile);
        blockChain.addBlock(Mock.getCnText(5,40));
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(blockChain.getBlockchain()));
            writer.flush();
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
    }

    public void save(List<Block> list) {
        // todo 把新的块添加到链上，并保存到本地
        File file = new File(this.rootDir + File.separator + this.selfFile);
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(list));
            writer.flush();
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
    }

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run(){
                BlockChain bc = new BlockChain(3);
                Main main = new Main(bc,"F:/block/local","test.txt","F:/block/other");
                for (int i=0;i<2;i++){
                    main.createAndSave();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.err.println("t : "+bc.isChainValid());
            }
        }.start();

        new Thread(){
            @Override
            public void run(){
                BlockChain bc = new BlockChain(3);
                Main main = new Main(bc,"F:/block/other","test.txt","F:/block/local");
                for (int i=0;i<3;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.err.println("t2 : "+bc.isChainValid());
            }
        }.start();

    }

    private void test() {
        BlockChain bc = new BlockChain(3);
        Main main = new Main(bc,"F:/block/local","test.txt","F:/block/other");
        main.createAndSave();
    }
}
/**
 1、用户启用程序
 2、扫描、获取区块链，保存到本地
 3、用户挖矿，生产区块，添加到链上
 4、更改本地区块链，便于其他用户扫描
 5、扫描
 */