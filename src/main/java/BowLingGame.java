import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/*
结对编程，保龄球。
 */
public class BowlingGame {

    /*
    根据已知ArrayList计算最后得分。
     */
    public int getBowlingScore(String str) {
        ArrayList arrayList = toArrayList(str);
//        System.out.println(arrayList);
        int score = 0;
        if (!IsLegal(str)) {
            return 0;
        }
        for (int i = 0; i < 10; i++) {
            char[] char_temp = (arrayList.get(i) + "").toCharArray();
//            System.out.println(char_temp);
            if (char_temp[0] == 'X') {  // 若此格打出strike，额外算2球分数。
//                System.out.println(10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 2));
                score = score + 10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 2);
            } else if (char_temp[1] != '/') {  // 无额外加分情况
//                System.out.println(getSingleCharScore(char_temp[0]) + getSingleCharScore(char_temp[1]));
                score = score + getSingleCharScore(char_temp[0]) + getSingleCharScore(char_temp[1]);
            } else {  // 若此格打出spare，额外算1球分数
//                System.out.println(10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 1));
                score = score + 10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 1);
            }
//            System.out.println(score);
        }
        return score;
    }

    /*
    此函数用于计算额外1个球、或额外2个球的得分。
     */
    private int getExtraScore(String str_1, String str_2, int i) {
        // str_1、str_2分别是当前所在格之后的两格，i表示取之后的1个球的分数还是2个球的分数。
//        System.out.println(str_1 + " // " + str_2 + " i= " + i);
        char[] char_temp_1 = str_1.toCharArray();
        char[] char_temp_2 = str_2.toCharArray();
        int score = 0;
        if (i == 1) {
            return getSingleCharScore(char_temp_1[0]);
        } else {  // i == 2，计算后两球的得分。
            if (char_temp_1.length == 1) {
                return getSingleCharScore(char_temp_1[0]) + getSingleCharScore(char_temp_2[0]);
            } else {  // char_temp_1.length == 2
                if (char_temp_1[1] == '/') {
                    return 10;
                } else {
                    return getSingleCharScore(char_temp_1[0]) + getSingleCharScore(char_temp_1[1]);
                }
            }
        }
    }

    /*
    计算单个字符对应分数。
     */
    private int getSingleCharScore(char c) {
        if (c == 'X') {
            return 10;
        } else if (c == '-') {
            return 0;
        } else if (c == '/') {
            System.out.println("错误！");
            return 0;
        } else {
            return Integer.parseInt(c + "");
        }
    }

    /*
    将String s 转换成方便操作的ArrayList。
    此ArrayList包含十二个单位，前十个单位对应是个“格”，最后两个单位对应两次额外击球。
    不用检查是否合法，此问题有专门检查是否合法的函数负责。
     */
    private ArrayList toArrayList(String s) {
        ArrayList arrayList = new ArrayList();
        char[] chrCharArray = s.toCharArray();
        String s_temp = "";
        int i = 0;
        boolean isI = false;
        for (i = 0; i < chrCharArray.length; i++) {
            if (chrCharArray[i] == '|' && isI) {
                // 当前后两个字符都是'|'时，已经记录前10格，此时记录i值并退出for循环。
                break;
            } else if (chrCharArray[i] != '|') {
                isI = false;
                s_temp = s_temp + chrCharArray[i];
            } else {
                arrayList.add(s_temp);
                isI = true;
                s_temp = "";
            }
        }
        for (i = i + 1; i < chrCharArray.length; i++) {
            //记录最后两个额外得分情况。
            arrayList.add(chrCharArray[i] + "");
        }
        if (arrayList.size() == 10) {
            arrayList.add("-");
            arrayList.add("-");
        }
        if (arrayList.size() == 11) {
            arrayList.add("-");
        }
        return arrayList;
    }

    /*
    此函数用于判断字符串输入是否合法。
    */
    private boolean IsLegal(String s) {
        // 每一格最多只能击球2次。
        char[] s_char = s.toCharArray();
        ArrayList arrayList = toArrayList(s);
        int count_I = 0;
        for (int i = 0; i < s_char.length; i++) {
            if (s_char[i] == '|') {
                count_I++;
            }
        }
        if (count_I != 11) {
            System.out.println("|的个数不对。");
            return false;
        }
        for (int i = 0; i < s_char.length; i++) {
            // 字符必须在1~9之间，或者为X、/、-、|
            if (s_char[i] != '1' && s_char[i] != '2' && s_char[i] != '3' && s_char[i] != '4' && s_char[i] != '5'
                    && s_char[i] != '6' && s_char[i] != '7' && s_char[i] != '8' && s_char[i] != '9' && s_char[i] != 'X'
                    && s_char[i] != '/' && s_char[i] != '-' && s_char[i] != '|') {
                System.out.println("其他字符存在。");
                return false;
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if ((arrayList.get(i) + "").length() >= 3) {
                System.out.println("长短不对。");
                return false;
            }

            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[0] == 'X') {
                // 第一位X，第二位还有数，非法。
                System.out.println("X还有数。");
                return false;
            }
            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[1] == 'X') {
                // 第二位X，非法
                System.out.println("X在第二位。");
                return false;
            }
            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[0] == '/') {
                // 第一位/，第二位还有数，非法。
                System.out.println("/后还有数。");
                return false;
            }
            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[1] == '/'
                    && ((arrayList.get(i) + "").toCharArray()[0] == '/' || (arrayList.get(i) + "").toCharArray()[0] == 'X')) {
                // 第二位/，第一位不是数，也不是-，非法。
                System.out.println("第二位/，第一位不是数，也不是-。");
                return false;
            }
        }
        return true;
    }


    @Test  // 用于测试字符串是否合法
    public void is_legal() {
        // Given
        String str_1 = "X|7/|9-|X|-8|8/|-6|X|X|X||81";   // 正确
        String str_2 = "X1|7/|9-|X|-8|8/|-6|X|X|X||81";  // X 后面多1个数
        String str_3 = "X|/1|9-|X|-8|8/|-6|X|X|X||81";  // / 后面多一个数
        String str_4 = "M|7/|X/|X|-8|8/|-6|X|X|X||81";  // M 非法。
        String str_5 = "X/|7/|9-|X|89|8/|-6|X|X|X||81";  // X/ 非法.
        String str_6 = "222|7/|9-|X|-8|8/|-6|X|X|X||81";  // 3位数
        String str_7 = "22|7/|9-|X|-8|8/|-6|X|X|X|81";  // 不是11个|
        String str_8 = "22|7/|9-|X|-8|8/||-6|X|X|X|81";  // 中间出现两个||，未检测。
        String str_9 = "22|7/|9-|X|-8|8/|-6|X|X|X|8|1";  // 无连续两个||
        // Then
        System.out.println("1" + IsLegal(str_1));
        System.out.println("2" + IsLegal(str_2));
        System.out.println("3" + IsLegal(str_3));
        System.out.println("4" + IsLegal(str_4));
        System.out.println("5" + IsLegal(str_5));
        System.out.println("6" + IsLegal(str_6));
        System.out.println("7" + IsLegal(str_7));
        System.out.println("8" + IsLegal(str_8));
        System.out.println("9" + IsLegal(str_9));
    }

    @Test  // 用于测试字符串是否转换为合法的ArrayList。
    public void is_right_ArrayList() {  // 10+10+10=30
        //Given
        String str = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";
        ArrayList arrayList = toArrayList(str);  // 拿到str的ArrayList数组。
        //Then
        System.out.println(arrayList);
    }

    @Test  // 检测单个字符是否能转化为对应分数
    public void is_right_getSingleCharScore() {
        char c_1 = 'X';
        char c_2 = '2';
        char c_3 = '9';
        System.out.println(getSingleCharScore(c_1));
        System.out.println(getSingleCharScore(c_2));
        System.out.println(getSingleCharScore(c_3));
    }


    @Test  // 最终测试
    public void is_right_getBowlingScore() {
        //Given
        String str_1 = "X|X|X|X|X|X|X|X|X|X||XX";
        String str_2 = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||";
        String str_3 = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";
        String str_4 = "X|7/|9-|X|-8|8/|-6|X|X|X||81";
        String str_5 = "X/|7/|9-|X|89|8/|-6|X|X|X||81";  // X/ 非法.
        //When
        int score_1 = getBowlingScore(str_1);
        int score_2 = getBowlingScore(str_2);
        int score_3 = getBowlingScore(str_3);
        int score_4 = getBowlingScore(str_4);
        int score_5 = getBowlingScore(str_5);
        //Then
        assertEquals(300, score_1);
        assertEquals(90, score_2);
        assertEquals(150, score_3);
        assertEquals(167, score_4);
        assertEquals(0, score_5);
    }
}