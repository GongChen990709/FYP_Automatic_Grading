import FYP19.Entities.Assignment;
import FYP19.Entities.Module;
import FYP19.Entities.Students;
import FYP19.Service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.jvm.hotspot.tools.jcore.ByteCodeRewriter;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentServiceTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    StudentsService studentsServiceIImp = (StudentsService) context.getBean("StudentsServiceImpl");
    TeacherService teacherServiceIImp = (TeacherService) context.getBean("TeacherServiceImpl");

    MailService mailService = (MailService) context.getBean("MailService");
    EncryptionService encryptionService = (EncryptionService) context.getBean("EncryptionService");

     AssignmentService assignmentService = (AssignmentService) context.getBean("AssignmentService");


    @Test
    public void test(){
        System.out.println("aaa".equals(null));
    }

    @Test
    public void queryAllStudents(){
       Students stu = studentsServiceIImp.queryStudentById(172059141);
        System.out.println(stu);
    }

    @Test
    public void testtttt(){
        Map<String, List<Object>> map = new HashMap<>();
        Object o = new int[]{1,2,3,4};
        Object o2 = "gc is clever";
        List<Object> a = new LinkedList<>();
        a.add(o);
        a.add(o2);
        Map<String,String> m =new HashMap<>();
        m.put("data","gc is very clever");
        a.add(m);
        map.put("method1", a);

        List<Object> d = new LinkedList<>();
        List<Integer> value = new LinkedList<>();
        value.add(99);
        value.add(98);
        value.add(97);
        d.add(value);

        Map<String, Float> map2 = new HashMap<>();
        map2.put("gc", (float) 2.5);
        map2.put("fulan", (float)2.51);
        d.add(map2);

        map.put("method2",d);


        JSONObject object = JSONObject.fromObject(map);
        System.out.println(object);


//        Iterator iter = object.entrySet().iterator();

//        while(iter.hasNext()){
//            Map.Entry entry = (Map.Entry) iter.next();
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//            JSONArray array = (JSONArray) entry.getValue();
//            for(int i=0;i<array.size();i++){
//                Object jsonObject = array.get(i);
//                System.out.println(jsonObject.toString());
//            }
//        }

        //System.out.println(object);
    }

    @Test
    public void abcde(){
        JSONObject object = new JSONObject();

        JSONArray array = new JSONArray();
        array.add(1);
        array.add(2);
        array.add(3);
        String a = array.toString();

        object.put("test",":"+a);
        System.out.println(object);
    }




    @Test
    public void aa(){
        teacherServiceIImp.registerModule(new Module("COMP2003J","Object-Oriented Programming",2));
        teacherServiceIImp.registerModule(new Module("COMP2004J","Database and Info System",2));
        teacherServiceIImp.registerModule(new Module("COMP2005J","Object-Oriented Design",2));
        teacherServiceIImp.registerModule(new Module("COMP2006J","Cloud Computing",2));
        teacherServiceIImp.registerModule(new Module("COMP2007J","Python",2));
    }

    @Test
    public void test11(){
        System.out.println(studentsServiceIImp.queryActivationCodeById(3123));
    }


    @Test
    public void test1112(){
        System.out.println(studentsServiceIImp.queryAllModules(17));
    }

//    @Test
//    public void textEmail(){
//        //studentsServiceIImp.registerStudent(new Students(11111,"helloWorld",null,"chen.gong@ucdconnect.ie","EEEN"));
//        mailService.registerStudent(new Students(11111,"helloWorld",null,"chen.gong@ucdconnect.ie","EEEN"));
//    }

    @Test
    public void aaa(){
//        String salt = BCrypt.gensalt();
//        System.out.println(salt);
//        String pwd = BCrypt.hashpw("gc",salt);
//        System.out.println(pwd);
        float a =(float) 3.112;
        System.out.println(a/2);
    }

    @Test
    public void path() throws ParseException {
        //String uuid = UUID.randomUUID().toString();
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date datetime = formatter.parse("1969-01-01 02:00:00");
        //System.out.println(datetime.toString());
        //assignmentService.insertAssignment(new Assignment(uuid,datetime,"assignment1","suanfa"));
        Assignment ass = assignmentService.queryAssignment("assignment1");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(ass.getDue_date());
        System.out.println(dateString);
    }

    @Test
    public void tes111(){
        for(int i=0;i<4;i++){
            String salt = BCrypt.gensalt();
            System.out.println(salt);
            String cipherPwd = BCrypt.hashpw("fyp123",salt);
            System.out.println(cipherPwd);
        }

    }

    @Test
    public void tesaas(){
        System.out.println(studentsServiceIImp.countStudentNumByCode("COMP"));
    }







}
