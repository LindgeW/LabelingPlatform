package com.labeling.demo.controller;

import com.labeling.demo.entity.DataType;
import com.labeling.demo.service.DataTypeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DataTypeController {
    private static final String root = "bg/";
    private DataTypeService dataTypeService;

    @Autowired
    public DataTypeController(DataTypeService dataTypeService) {
        this.dataTypeService = dataTypeService;
    }

    @RequestMapping("/datatype")
    @RequiresRoles("admin")
    public String toDataTypePage(Model model){
        List<DataType> dataTypes = dataTypeService.findAll();
        model.addAttribute("dataTypes", dataTypes);
        return root+"datatype";
    }

    @PostMapping("/addType")
    @RequiresRoles("admin")
    public String addDataType(DataType dataType, Model model){
        System.out.println(dataType);

        if(null == dataTypeService.findByName(dataType.getTypeName())) {
            dataTypeService.save(dataType);
        } else {
            model.addAttribute("msg", "该数据类型已存在！");
        }

        return "forward:/datatype";
    }

    @PostMapping("/updateType")
    @RequiresRoles("admin")
    public String alterDataType(DataType dataType, Model model){
        System.out.println(dataType);

        dataTypeService.updateDataType(dataType);

        return "forward:/datatype";
    }
}
