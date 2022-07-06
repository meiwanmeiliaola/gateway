package com.msm.admin.utils;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/5 15:00
 */
public class BeanHump {


    //转变的依赖字符

    public static final char UNDERLINE='_';


    /**

     * 将驼峰转换成"_"(userId:user_id)

     * @param param

     * @return

     */

    public static String camelToUnderline(String param){

        if (param==null||"".equals(param.trim())){

            return "";

        }
        int len=param.length();

        StringBuilder sb=new StringBuilder(len);

        for (int i = 0; i < len; i++) {

            char c=param.charAt(i);

            if (Character.isUpperCase(c)){

                sb.append(UNDERLINE);

                sb.append(Character.toLowerCase(c));

            }else{

                sb.append(c);

            }

        }
        return sb.toString();
    }


}

