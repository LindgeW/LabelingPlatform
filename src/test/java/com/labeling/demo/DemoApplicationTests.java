package com.labeling.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.labeling.demo.entity.Instance;
import com.labeling.demo.repository.InstanceMapper;
import com.labeling.demo.service.InstanceService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

class Student {
    private Integer id;
    private String name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "Student{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }

    @Override
    public int hashCode() {
        return id.hashCode() * id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }else if(obj == this){
            return true;
        }else if(obj instanceof Student){
            Student stu = (Student) obj;
            return (id.equals(stu.getId()) && name.equals(stu.getName()));
        }

        return false;
    }
}

class StuVO extends Student{
    private Integer age;
//    private List<String> hobbies;
    private String[] hobbies;

    public StuVO(Integer id, String name, Integer age) {
        super(id, name);
        this.age = age;
    }


    public StuVO(Student stu, Integer age, String[] hobbies) {
        super(stu.getId(), stu.getName());
        this.age = age;
        this.hobbies = hobbies;
    }

//    public StuVO(Student stu, Integer age, List<String> hobbies) {
//        super(stu.getId(), stu.getName());
//        this.age = age;
//        this.hobbies = hobbies;
//    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    //    public List<String> getHobbies() {
//        return hobbies;
//    }
//
//    public void setHobbies(List<String> hobbies) {
//        this.hobbies = hobbies;
//    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        String[] ss = new String[]{"ad", "fwe", "gwrw"};
        List<String> sLst = new ArrayList<>();
        Collections.addAll(sLst, ss);
        System.out.println(sLst);

        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println(rand.nextInt(ss.length));
        }

//        System.out.println(StringUtils.join(ss, ";"));
    }

    @Test
    public void strUtil(){
        String s = "  我 来自上海浦东 \n " + "adfa  ";
        System.out.println(StringUtils.trimToEmpty(s));
        System.out.println(StringUtils.isAllBlank(" a "));
        System.out.println(StringUtils.trimToNull(s));

        String s2 = "asdfas;asdf;qweqwer;qwerwqer;werq";
        String[] tokens = StringUtils.split(s2, ';');
        for (String t: tokens) {
            System.out.println(t);
        }

    }

    @Test
    public void objTest(){
        Student s = new Student(1, "张三");
//        List<String> hs = new ArrayList<>();
//        hs.add("play");
//        hs.add("swim");
//        hs.add("sing");
        String[] hs = new String[]{"play", "swim", "sing"};
        StuVO sv = new StuVO(s, 10, hs);
        String ss = JSON.toJSONString(sv);
        System.out.println(ss);
    }

    @Test
    public void contains(){
//        System.out.println(StringUtils.contains("I love you", "you"));
//        List<String> arrLst = new ArrayList<>();
//        arrLst.add("asda");
//        arrLst.add("fdga");
//        arrLst.add("vads");
//        arrLst.add("griore");
//        System.out.println(arrLst.subList(0, 5));
        System.out.println(String.format("%.2f%%", 100*2.0/3));
        String x = "aaaa";
        System.out.println(String.format("name=%s.json", x));
    }

    @Test
    public void jsonTest(){
        List<Student> stus = new ArrayList<>();
        Student s1 = new Student(1, "张三");
        Student s2 = new Student(2, "李四");
        Student s3 = new Student(3, "王五");
        stus.add(s1);
        stus.add(s2);
        stus.add(s3);
//        StuVO sv = new StuVO(s1, 10);
        String stuStr = JSON.toJSONString(stus);
        System.out.println(stuStr);

//        JSONObject jsonObject = JSON.parseObject(stuStr);
//        System.out.println(jsonObject.get("name"));
//        System.out.println(jsonObject.get("age"));
//        JSONArray objects = JSON.parseArray(stuStr);
//        System.out.println(objects.get(0));

//        IOUtils.toString(new FileInputStream("generator/item.json"));
        try {
            String jsonStr = FileUtils.readFileToString(new File("F:\\github_proj\\LabelingPlatform\\src\\main\\resources\\generator\\item.json"));
//            JSONObject result = JSON.parseObject(jsonStr);
//            System.out.println(result);
//            System.out.println(result.get("expert"));

            JSONArray objects = JSON.parseArray(jsonStr);
            JSONObject raw = objects.getJSONObject(0);
            System.out.println(raw);
            raw.fluentPut("model1", "xxxx");
            System.out.println(raw);
            raw.put("expert", "iiiii");
            System.out.println(raw);

//            for(Object obj : objects){
//                System.out.println(obj);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObj(){
        Student stu1 = new Student(312, "aaa");
        Student stu2 = new Student(312, "aaa");
        System.out.println(stu1.hashCode());
        System.out.println(stu2.hashCode());
        Set<Student> ss = new HashSet<>();
        ss.add(stu1);
        ss.add(stu2);
        System.out.println(ss);
    }

    @Autowired
    InstanceMapper instanceMapper;

    @Test
    public void testTask(){
        //instanceId, taskName, item, tagExpert, tagModel, status, tagNum
        Instance inst1 = new Instance();
        inst1.setInstanceId(2L);
        inst1.setTagNum(1);
        inst1.setTagExpert("gen");
        inst1.setTagModel("gen");

        Instance inst2 = new Instance();
        inst2.setInstanceId(3L);
        inst2.setTagNum(1);
        inst2.setTagExpert("gen");
        inst2.setTagModel("pos");

        List<Instance> instLst = new ArrayList<>();
        instLst.add(inst1);
        instLst.add(inst2);
        instanceMapper.saveAll(instLst);
//        System.out.println(instanceMapper.findPageDataByTaskNameRand("电商评论", PageRequest.of(1, 3)));
    }

    @Test
    public void testUnicode(){
        String us = "1\u3001\u5bf9\u6807\u6ce8\u5458\u51fa\u73b0\u5206\u6b67\u7684\u6807\u6ce8\u7ed3\u679c\u505a\u51b3\u65ad\uff08\u4e13\u5bb6\u4e0d\u80fd\u8bc4\u5b9a\u81ea\u5df1\u6807\u7684\u6570\u636e\uff09";
        System.out.println(us);
    }
}
