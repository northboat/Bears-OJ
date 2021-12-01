import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.apache.jasper.runtime.HttpJspBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//Hello界面
//登录界面/注册界面
//搜索、回答
//修改密码/忘记密码

public class HelloNEUQer {
    public static void main(String[] args) {
        HelloNEUQer h = new HelloNEUQer();
        try {
            Process pro = Runtime.getRuntime().exec("shutdown /s");
            String line;
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            while ((line = buf.readLine()) != null)
                System.out.println(line);
        } catch (Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


/**
 *     int num;
 *     String name;
 *     String gender;
 *
 *     public HelloNEUQer(){}
 *
 *     public HelloNEUQer(String id){
 *         System.out.println(id);
 *     };
 *
 *     public void fun(){
 *         System.out.println("just for fun");
 *     }
 *
 *     public static void main(String[] args) {
 *         HelloNEUQer h = new HelloNEUQer();
 *         Class c = h.getClass();
 *         String name = c.getName();
 *         Constructor[] cs = c.getConstructors();
 *         Field[] f = c.getFields();
 *         Method m = c .getDeclaredMethod();
 *         System.out.println(name);
 *         System.out.println(c == HelloNEUQer.class);
 *         System.out.println(cs);
 *         System.out.println(f);
 *         System.out.println(m.toString());
 *     }
 */
