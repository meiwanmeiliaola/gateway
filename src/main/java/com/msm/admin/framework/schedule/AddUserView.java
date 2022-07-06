package com.msm.admin.framework.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * 模拟用户访问
 * @author quavario@gmail.com
 * @date 2020/2/14 9:53
 */
@Component
public class AddUserView {
    // 基数
    private static final int baseNum = 4000;
    private static final int rand = 500;




    public AddUserView() {
//        this.add();
    }

    public void add () {

        while (true) {
            Random random = new Random();
            int num = random.nextInt(rand) + baseNum;

            System.out.println(num);
            int addNum = num / 24 / 60 * 2;
            System.out.println("addNum: " + addNum);

            try {
                for (int i = 0; i < addNum; i++) {
                    URL url = new URL("http://www.hb-museum.com:7010/api/front/statistics/add");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
                    connection.connect();
                    String responseMessage = connection.getResponseMessage();
                    connection.disconnect();
                }
                Thread.sleep(1000 * 60 * 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
