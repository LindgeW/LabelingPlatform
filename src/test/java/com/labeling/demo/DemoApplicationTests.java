package com.labeling.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}

class StuVO extends Student{
    private Integer age;

    public StuVO(Integer id, String name, Integer age) {
        super(id, name);
        this.age = age;
    }

    public StuVO(Student stu, Integer age) {
        super(stu.getId(), stu.getName());
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
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
        StuVO sv = new StuVO(s, 10);
        System.out.println(sv.getAge());
        System.out.println(sv.getName());
        System.out.println(sv.getId());
    }
}
