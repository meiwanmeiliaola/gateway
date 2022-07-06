package com.msm.admin.framework.aop;


import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.msm.admin.framework.handler.GlobalExceptionHandler;
import com.msm.admin.jdbc.FileDelProperties;
import com.msm.admin.jdbc.FileType;
import com.msm.admin.jdbc.JDBCUtil;
import com.msm.admin.utils.BeanHump;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/5/5 11:08
 */

@Aspect
@Component
public class FileAspect {


    private static final String ID="id";

    private static final String ZIPCODE="5";

    private static final String SQL="select * from  ";

    private static final String WHERE=" where ";

    public String tableName="";

    public static final String EQ="=";

    public static boolean FLAG=false;

    private static final String HTML="index.html";

    private static final String DATA="/data";

    private static final HashMap<String,Object> OLD_MAP=new HashMap<>(16);

    private static HashMap<String,Object> NEW_MAP;

    private static final List<String> FIELD_NAMES = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Resource
    private FileDelProperties fileDelProperties;



    @Pointcut("@annotation( org.springframework.web.bind.annotation.PutMapping)")
    public void filePointCut() {

    }

    /**
     * 切面 在方法之前执行  前置通知
    */
    @Before("filePointCut()")
    public void fileAndImgUpdateBefore(JoinPoint joinPoint) throws Exception{

        log.info("切面 ......");

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取pojo的class
        Class<?> clazz = method.getReturnType();

        //借鉴mybatis-plus的注解获取pojo对应的信息
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);

        //表名称
        tableName = tableInfo.getTableName();

        log.info("对应的表名:"+tableName);

        List<TableFieldInfo> fieldList = tableInfo.getFieldList();


        for (TableFieldInfo tableFieldInfo : fieldList) {
               String name=tableFieldInfo.getColumn();
               FIELD_NAMES.add(name);
        }

        log.info("字段:"+FIELD_NAMES);

        log.info("pojo根路径:"+clazz.getName());

        //请求的参数
        Object[] args = joinPoint.getArgs();

        List<Object> list = Arrays.asList(args);

        Object o = list.get(0);

        Assert.notNull(o,"所修改的信息不可为空");

        Class<?> aClass = o.getClass();

        Field[] fields = aClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();

            Object value = field.get(o);
            if (value == null){
                continue;
            }
            keyName = BeanHump.camelToUnderline(keyName);
            OLD_MAP.put(keyName,value);

        }


        Object idIsNull = OLD_MAP.get(ID);

        //如果是添加,则直接跳过此过程
        if (ObjectUtils.isEmpty(idIsNull)){
            return;
        }


        String id=String.valueOf(OLD_MAP.get(ID));

        String sql=SQL+tableName+WHERE+ID+EQ+"'"+id+"'";

        NEW_MAP = JDBCUtil.sqlResult(sql,FIELD_NAMES);
    }

    /**
     * 后置通知  切点方法执行后的返回值
     * TODO @After 无论什么情况下都会执行的方法 ,类似finally
    */
    @AfterReturning("filePointCut()")
    public void fileAndImgUpdateAfter(){

        //对比两个map中的参数,NEW_MAP中的参数不同于OLD_MAP中的参数且参数为图片或文本格式,则根据NEW_MAP参数的路径直接删除其文件
        for (String s : FIELD_NAMES) {
            String oldKey=String.valueOf(OLD_MAP.get(s));
            String newKey=String.valueOf(NEW_MAP.get(s));

            //参数是否为文件,如不是,则跳出本次循环执行下次循环
            String code = FileType.screen(oldKey);
            if (code != null){
                FLAG = oldKey.equals(newKey);

                if (!FLAG){
                    newKey=newKey.replace(DATA,"");
                    //如果后缀为html,则去掉.html,根据路径删除文件
                    if (ZIPCODE.equals(code)){
                        newKey=newKey.replace(HTML,"");
                    }
                    newKey =  fileDelProperties.getDefaultDelPath()+newKey;
                    log.info("====最终的路径为=========="+newKey);
                    File file=new File(newKey);
                    boolean exists = file.exists();
                    if (exists){
                        boolean delete = file.delete();
                        if (delete) {
                            log.info("删除文件" + newKey + "成功");
                        } else {
                            log.error("删除文件" + newKey + "失败");
                        }
                    }else {
                        log.info("文件不存在");
                    }

                }
            }
        }
    }

}
