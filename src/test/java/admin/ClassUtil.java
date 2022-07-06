package admin;

import java.lang.reflect.Method;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/3/30 11:39
 */
public class ClassUtil {

    public static void printClassMessage(Object obj){
        //要获取类的信息，首先要获取类的类类型
        Class c = obj.getClass();//传递的是哪个子类的对象，c就是该子类的类类型
        //获取类的名称
        System.out.println("类的名称为:"+c.getName());
        /*
         * Method类，方法对象
         * 一个成员方法就是一个Method对象
         * getMethods()方法获取的是所有的public的函数，包括父类继承而来的
         * getDeclaredMethods()获取的是所有该类自己声明的方法，部位访问权限
         */
        Method[] methods = c.getMethods();
        //
        for(int i=0;i<methods.length;i++){
            //得到方法的返回值类型的类类型
            Class returnType = methods[i].getReturnType();

            System.out.print(returnType.getName()+" ");
            //得到方法的名称
            System.out.print(methods[i].getName()+"(");
            //获取参数类型--》得到的参数列表的类型de类类型
            Class[] paramType = methods[i].getParameterTypes();
            for(Class class1:paramType){
                System.out.print(class1.getName()+",");
            }
            System.out.println(")");
        }
    }

}
