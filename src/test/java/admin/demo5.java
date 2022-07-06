package admin;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/13 17:01
 */
public class demo5 {

    public static void main(String[] args) {


        HashMap<Integer,String> map=new HashMap<>();

        List<Integer> list=new ArrayList<>();
        list.add(4);
        for (int i = 0; i < list.get(0); i++) {
            String value="Test"+i;
            map.put(i+1,value);
        }
        System.out.println(map);

        Set<Integer> set = map.keySet();

        for (Integer integer : set) {
            System.out.println(map.get(integer));
        }




    }

}
