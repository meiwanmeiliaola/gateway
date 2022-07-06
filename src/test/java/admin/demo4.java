package admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/12 16:37
 */
public class demo4 {

    public static void main(String[] args) {
/*
        List<Integer> intList=new ArrayList<>();

        List<Integer> abandonList=new ArrayList<>();

        int num=2;

        boolean flag=false;

        HashMap<Integer,Integer> map=new HashMap<>(16);

        for (int i = 1; i <= 50; i++) {
            intList.add(i);
        }

        for (int i = 0; i < intList.size(); i++) {
            Integer index = intList.get(i);
        }
        System.out.println(map);*/

        int num1=1;
        int c=50;


        while (true){
            int b=c/2-1;
            if (b>0){
                num1++;
            }
            break;
        }

        System.out.println(num1);

    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
