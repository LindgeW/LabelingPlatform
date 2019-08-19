package com.labeling.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NERController {

    @RequestMapping("/ner_annotate")
    public String toEntityAnnotate(){
        return "ner_annotate";
    }


}
