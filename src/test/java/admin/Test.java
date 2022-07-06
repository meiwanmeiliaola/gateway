package admin;

import javax.naming.Name;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author quavario@gmail.com
 * @date 2022/2/7 16:21
 */
public class Test<T> {




    public static void main(String[] args) {



        List<Integer> list =new ArrayList<>(1);

        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        int age=0;
        list.forEach(s ->{

        });



   /*
        ILove love;
        love = (a) -> {
            String  b="sa";
            return a+b;
        };
        System.out.println(love.love("我是"));*/
    }


}
interface ILove{
    String love(String name);
}
