package com.labeling.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
