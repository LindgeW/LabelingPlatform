package com.labeling.demo.controller;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.entity.RespEntity;
import com.labeling.demo.entity.RespStatus;
import com.labeling.demo.service.InstanceService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AnnotateController {
    private InstanceService instanceService;
    private List<Instance> instanceList = null;
    private Integer counter = 0;

    @Autowired
    public AnnotateController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @GetMapping("/annotate")
    public String toAnnotate(Model model){
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        instanceList = instanceService.findAll();

        model.addAttribute("username", username);
        counter = 0;
        model.addAttribute("instance", instanceList.get(counter));
        return "annotate";
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(@RequestParam("tag") String tag,
                               @RequestParam("cmd") String cmd){
        System.out.println(tag);
        if(cmd.equalsIgnoreCase("prev")){
            counter --;
        }else if (cmd.equalsIgnoreCase("next")){
            counter ++;
        }

        if (counter <= 0){
            counter = 0;
        }

        if (counter >= instanceList.size()){
            return new RespEntity<>(RespStatus.Over);
        }
        return new RespEntity<>(RespStatus.SUCCESS, instanceList.get(counter));
    }
}
