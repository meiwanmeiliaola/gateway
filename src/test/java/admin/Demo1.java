package admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author quavario@gmail.com
 * @date 2022/1/27 15:18
 */
public class Demo1 implements Runnable{

    private static String winner;

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if (Thread.currentThread().getName().equals("兔子") && i%10==0){
                try {
                    System.out.println("1");
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean b = gameOver(i);
            if (b){
                break;
            }
            System.out.println(Thread.currentThread().getName()+"跑了"+i+"步");
        }
    }

    private boolean gameOver(int i){
        if (winner!=null){
            return true;
        }{
            if (i>=100){
                winner=Thread.currentThread().getName();
                System.out.println("winner是"+winner);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Demo1 demo1=new Demo1();
        new Thread(demo1,"兔子").start();
        new Thread(demo1,"乌龟").start();
        String a="49443303000000043B3E545945520000000D000001FFFE3200300032";
        String b="49443303000000043B3E545945520000000D000001FFFE3200300032";



    }


}
