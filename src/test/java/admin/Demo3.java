package admin;


/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/4/25 15:32
 */
public class Demo3 {

    public static void main(String[] args) {
/*        String areaName="河北秦皇岛市";

        String areaName2="秦皇岛";
        String areaName3="唐山";
        String areaName4="承德";
        String areaName5="张家口";
        String areaName6="保定";

        boolean contains = areaName.contains(areaName);
        System.out.println(contains);*/
        long numTotal=100;
        long two=2;
        long three=3;

        long fun=1;

        long funThree=1;

        long numTwo=0;

        long numThree=0;

        //循环查找2的倍数,numTwo为2的倍数,初始值为0
        while (true){
            two=2*fun;
            if (two>numTotal){
                break;
            }
            fun++;
            numTwo++;
        }
        System.out.println(numTwo);

        //循环查找3的倍数,numThree为3的倍数,初始值为0
        while (true){
            three=3*funThree;
            if (three>numTotal){
                break;
            }
            funThree++;
            numThree++;
        }

        System.out.println(numThree);

        System.out.println("=========");

        System.out.println(numTwo+numThree);

    }


}
