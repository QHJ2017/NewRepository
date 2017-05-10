import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/*
��Ա�̣�������
 */
public class BowlingGame {

    /*
    ������֪ArrayList�������÷֡�
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
            if (char_temp[0] == 'X') {  // ���˸���strike��������2�������
//                System.out.println(10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 2));
                score = score + 10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 2);
            } else if (char_temp[1] != '/') {  // �޶���ӷ����
//                System.out.println(getSingleCharScore(char_temp[0]) + getSingleCharScore(char_temp[1]));
                score = score + getSingleCharScore(char_temp[0]) + getSingleCharScore(char_temp[1]);
            } else {  // ���˸���spare��������1�����
//                System.out.println(10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 1));
                score = score + 10 + getExtraScore(arrayList.get(i + 1) + "", arrayList.get(i + 2) + "", 1);
            }
//            System.out.println(score);
        }
        return score;
    }

    /*
    �˺������ڼ������1���򡢻����2����ĵ÷֡�
     */
    private int getExtraScore(String str_1, String str_2, int i) {
        // str_1��str_2�ֱ��ǵ�ǰ���ڸ�֮�������i��ʾȡ֮���1����ķ�������2����ķ�����
//        System.out.println(str_1 + " // " + str_2 + " i= " + i);
        char[] char_temp_1 = str_1.toCharArray();
        char[] char_temp_2 = str_2.toCharArray();
        int score = 0;
        if (i == 1) {
            return getSingleCharScore(char_temp_1[0]);
        } else {  // i == 2�����������ĵ÷֡�
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
    ���㵥���ַ���Ӧ������
     */
    private int getSingleCharScore(char c) {
        if (c == 'X') {
            return 10;
        } else if (c == '-') {
            return 0;
        } else if (c == '/') {
            System.out.println("����");
            return 0;
        } else {
            return Integer.parseInt(c + "");
        }
    }

    /*
    ��String s ת���ɷ��������ArrayList��
    ��ArrayList����ʮ������λ��ǰʮ����λ��Ӧ�Ǹ����񡱣����������λ��Ӧ���ζ������
    ���ü���Ƿ�Ϸ�����������ר�ż���Ƿ�Ϸ��ĺ�������
     */
    private ArrayList toArrayList(String s) {
        ArrayList arrayList = new ArrayList();
        char[] chrCharArray = s.toCharArray();
        String s_temp = "";
        int i = 0;
        boolean isI = false;
        for (i = 0; i < chrCharArray.length; i++) {
            if (chrCharArray[i] == '|' && isI) {
                // ��ǰ�������ַ�����'|'ʱ���Ѿ���¼ǰ10�񣬴�ʱ��¼iֵ���˳�forѭ����
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
            //��¼�����������÷������
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
    �˺��������ж��ַ��������Ƿ�Ϸ���
    */
    private boolean IsLegal(String s) {
        // ÿһ�����ֻ�ܻ���2�Ρ�
        char[] s_char = s.toCharArray();
        ArrayList arrayList = toArrayList(s);
        int count_I = 0;
        for (int i = 0; i < s_char.length; i++) {
            if (s_char[i] == '|') {
                count_I++;
            }
        }
        if (count_I != 11) {
            System.out.println("|�ĸ������ԡ�");
            return false;
        }
        for (int i = 0; i < s_char.length; i++) {
            // �ַ�������1~9֮�䣬����ΪX��/��-��|
            if (s_char[i] != '1' && s_char[i] != '2' && s_char[i] != '3' && s_char[i] != '4' && s_char[i] != '5'
                    && s_char[i] != '6' && s_char[i] != '7' && s_char[i] != '8' && s_char[i] != '9' && s_char[i] != 'X'
                    && s_char[i] != '/' && s_char[i] != '-' && s_char[i] != '|') {
                System.out.println("�����ַ����ڡ�");
                return false;
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if ((arrayList.get(i) + "").length() >= 3) {
                System.out.println("���̲��ԡ�");
                return false;
            }

            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[0] == 'X') {
                // ��һλX���ڶ�λ���������Ƿ���
                System.out.println("X��������");
                return false;
            }
            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[1] == 'X') {
                // �ڶ�λX���Ƿ�
                System.out.println("X�ڵڶ�λ��");
                return false;
            }
            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[0] == '/') {
                // ��һλ/���ڶ�λ���������Ƿ���
                System.out.println("/��������");
                return false;
            }
            if ((arrayList.get(i) + "").length() == 2 && (arrayList.get(i) + "").toCharArray()[1] == '/'
                    && ((arrayList.get(i) + "").toCharArray()[0] == '/' || (arrayList.get(i) + "").toCharArray()[0] == 'X')) {
                // �ڶ�λ/����һλ��������Ҳ����-���Ƿ���
                System.out.println("�ڶ�λ/����һλ��������Ҳ����-��");
                return false;
            }
        }
        return true;
    }


    @Test  // ���ڲ����ַ����Ƿ�Ϸ�
    public void is_legal() {
        // Given
        String str_1 = "X|7/|9-|X|-8|8/|-6|X|X|X||81";   // ��ȷ
        String str_2 = "X1|7/|9-|X|-8|8/|-6|X|X|X||81";  // X �����1����
        String str_3 = "X|/1|9-|X|-8|8/|-6|X|X|X||81";  // / �����һ����
        String str_4 = "M|7/|X/|X|-8|8/|-6|X|X|X||81";  // M �Ƿ���
        String str_5 = "X/|7/|9-|X|89|8/|-6|X|X|X||81";  // X/ �Ƿ�.
        String str_6 = "222|7/|9-|X|-8|8/|-6|X|X|X||81";  // 3λ��
        String str_7 = "22|7/|9-|X|-8|8/|-6|X|X|X|81";  // ����11��|
        String str_8 = "22|7/|9-|X|-8|8/||-6|X|X|X|81";  // �м��������||��δ��⡣
        String str_9 = "22|7/|9-|X|-8|8/|-6|X|X|X|8|1";  // ����������||
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

    @Test  // ���ڲ����ַ����Ƿ�ת��Ϊ�Ϸ���ArrayList��
    public void is_right_ArrayList() {  // 10+10+10=30
        //Given
        String str = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";
        ArrayList arrayList = toArrayList(str);  // �õ�str��ArrayList���顣
        //Then
        System.out.println(arrayList);
    }

    @Test  // ��ⵥ���ַ��Ƿ���ת��Ϊ��Ӧ����
    public void is_right_getSingleCharScore() {
        char c_1 = 'X';
        char c_2 = '2';
        char c_3 = '9';
        System.out.println(getSingleCharScore(c_1));
        System.out.println(getSingleCharScore(c_2));
        System.out.println(getSingleCharScore(c_3));
    }


    @Test  // ���ղ���
    public void is_right_getBowlingScore() {
        //Given
        String str_1 = "X|X|X|X|X|X|X|X|X|X||XX";
        String str_2 = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||";
        String str_3 = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";
        String str_4 = "X|7/|9-|X|-8|8/|-6|X|X|X||81";
        String str_5 = "X/|7/|9-|X|89|8/|-6|X|X|X||81";  // X/ �Ƿ�.
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