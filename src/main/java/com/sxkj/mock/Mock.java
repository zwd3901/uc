package com.sxkj.mock;

import java.util.Random;

public class Mock {
    private static final String[] en_first = {"James", "John", "Robert", "Michael", "William",
            "David", "Richard", "Charles", "Joseph", "Thomas",
            "Christopher", "Daniel", "Paul", "Mark", "Donald",
            "George", "Kenneth", "Steven", "Edward", "Brian",
            "Ronald", "Anthony", "Kevin", "Jason", "Matthew",
            "Gary", "Timothy", "Jose", "Larry", "Jeffrey",
            "Frank", "Scott", "Eric","Mary", "Patricia", "Linda", "Barbara", "Elizabeth",
            "Jennifer", "Maria", "Susan", "Margaret", "Dorothy",
            "Lisa", "Nancy", "Karen", "Betty", "Helen",
            "Sandra", "Donna", "Carol", "Ruth", "Sharon",
            "Michelle", "Laura", "Sarah", "Kimberly", "Deborah",
            "Jessica", "Shirley", "Cynthia", "Angela", "Melissa",
            "Brenda", "Amy", "Anna"};

    private static final String[] en_last = {"Smith", "Johnson", "Williams", "Brown", "Jones",
            "Miller", "Davis", "Garcia", "Rodriguez", "Wilson",
            "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez",
            "Moore", "Martin", "Jackson", "Thompson", "White",
            "Lopez", "Lee", "Gonzalez", "Harris", "Clark",
            "Lewis", "Robinson", "Walker", "Perez", "Hall",
            "Young", "Allen"};

    private static final String[] cn_first = {"王","李","张","刘","陈","杨","赵","黄","周","吴","徐","孙","胡","朱","高","林","何","郭","马","罗","梁","宋","郑","谢","韩","唐","冯","于","董","萧","程","曹","袁","邓","许","傅","沈","曾","彭","吕","苏","卢","蒋","蔡","贾","丁","魏","薛","叶","阎","余","潘","杜","戴","夏","锺","汪","田","任","姜","范","方","石","姚","谭","廖","邹","熊","金","陆","郝","孔","白","崔","康","毛","邱","秦","江","史","顾","侯","邵","孟","龙","万","段","雷","钱","汤","尹","黎","易","常","武","乔","贺","赖","龚","文"};

    private static final String[] cn_last = {"蔼","仁","容德","轩","贤","良","伦","正","清","义","诚","直","道","颖","灵","睿","锐哲","慧","敦","迪","明","晓","显","悉","晰","维学","思","悟","析文","书","勤","俊威","英","健","壮","焕","挺","秀","伟","武雄","巍","松","柏","山","石","婵娟","姣","妯","婷","姿","媚","婉","妩","倩兰","安","静","顺","通","坦","泰","然","宁","定","和","康"};

    private static final String s = "的一是在不了有和人这中大为上个国我以要他时来用们生到作地于出就分对成会可主发年动同工也能下过子说产种面而方后多定行学法所民得经十三之进着等部度家电力里如水化高自二理起小物现实加量都两体制机当使点从业本去把性好应开它合还因由其些然前外天政四日那社义事平形相全表间样与关各重新线内数正心反你明看原又么利比或但质气第向道命此变条只没结解问意建月公无系军很情者最立代想已通并提直题党程展五果料象员革位入常文总次品式活设及管特件长求老头基资边流路级少图山统接知较将组见计别她手角期根论运农指几九区强放决西被干做必战先回则任取据处队南给色光门即保治北造百规热领七海口东导器压志世金增争济阶油思术极交受联什认六共权收证改清己美再采转更单风切打白教速花带安场身车例真务具万每目至达走积示议声报斗完类八离华名确才科张信马节话米整空元况今集温传土许步群广石记需段研界拉林律叫且究观越织装影算低持音众书布复容儿须际商非验连断深难近矿千周委素技备半办青省列习响约支般史感劳便团往酸历市克何除消构府称太准精值号率族维划选标写存候毛亲快效斯院查江型眼王按格养易置派层片始却专状育厂京识适属圆包火住调满县局照参红细引听该铁价严龙飞";

    /**
     * 获取英文的first name
     * @return
     */
    public static String getEnFirst(){
        Random random = new Random();
        return en_first[random.nextInt(en_first.length)];
    }

    /**
     * 获取英文的last name
     * @return
     */
    public static String getEnLast() {
        Random random = new Random();
        return en_last[random.nextInt(en_last.length)];
    }

    /**
     * 获取英文的完整name
     * @return
     */
    public static String getEnName(){
        return getEnFirst()+" "+getEnLast();
    }

    /**
     * 获取中文姓名
     * @return
     */
    public static String getCnName(){
        Random random = new Random();
        String xing = cn_first[random.nextInt(cn_first.length)];
        String ming = cn_last[random.nextInt(cn_last.length)];
        return xing+ming;
    }

    /**
     * 获取中文长文本
     * @param min 最小字数
     * @param max 最大字数
     * @return
     */
    public static String getCnText(int min,int max) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<random.nextInt(max-min+1)+min;i++){

            int r = random.nextInt(s.length());
            if(r==0){
                continue;
            }
            sb.append(s.substring(r-1,r));
        }
        return sb.toString();
    }
    private static String getChar(){
        int start = 97;
        int end = 122;
        Random random = new Random();
        int r = random.nextInt(end - start + 1) + start;
        return ((char)r)+"";
    }

    /**
     * 获取英文单词
     * @param min   最小字数
     * @param max   最大字数
     * @return
     */
    public static String getEnWord(int min, int max) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(max - min + 1) + min; i++) {
            if (i == 0) {
                sb.append(getChar().toUpperCase());
            }
            sb.append(getChar());
        }
        return sb.toString();
    }

    /**
     * 获取英文长文本
     * @param min   单词的最小字数
     * @param max   单词的最大字数
     * @param len   文本单词数
     * @return
     */
    public static String getEnText(int min, int max, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if(sb.length()>0){
                sb.append(" ");
            }
            sb.append(getEnWord(min,max));
        }
        return sb.toString();
    }

    /**
     * 获取随机数值
     * @param max   最大的数值
     * @return
     */
    public static int getNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * 获取随机年龄，最大值100
     * @return
     */

    public static int getAge() {
        return getNumber(100);
    }
    public static void main(String[] args) {
        System.err.println(Mock.getEnFirst());
        System.err.println(Mock.getCnName());
        System.err.println(Mock.getCnText(4,8));
        System.err.println(Mock.getEnWord(4,8));
        System.err.println(Mock.getEnText(4,80,5));
        System.err.println(Mock.getEnName());
        System.err.println(Mock.getAge());
    }


}
