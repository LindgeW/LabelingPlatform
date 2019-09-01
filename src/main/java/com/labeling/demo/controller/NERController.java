package com.labeling.demo.controller;

import com.labeling.demo.entity.RespEntity;
import com.labeling.demo.entity.RespStatus;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// deprecated
@Controller
public class NERController {

    @RequestMapping("/ner_annotate")
    public String toEntityAnnotate(Model model){
        String[] tokens = StringUtils.split("我 来自 湖北 武汉 ， 一座 美丽 的 城市 ！ 我 来自 湖北 武汉 ， 一座 美丽 的 城市 ！", " ");
        String[] lbls = new String[]{"Person", "Loc", "Org", "Date", "Number"};

        Map<String, String> lblMap = new HashMap<>();
        int r, g, b;
        double yuv;

        for (String lbl: lbls) {
            do{
                r = RandomUtils.nextInt(5, 200);
                g = RandomUtils.nextInt(5, 200);
                b = RandomUtils.nextInt(5, 200);
                // rgb模式转YUV模式，Y是明亮度
                yuv = 0.299 * r + 0.587 * g + 0.114 * b;
            } while (yuv >= 192);
            String bgColor = String.format("rgb(%d, %d, %d)", r, g, b);
            lblMap.put(lbl, bgColor);
        }

        model.addAttribute("tokens", tokens);
        model.addAttribute("ner_lbls", lblMap);

        return "ner_annotate";
    }


    @PostMapping("/save_ner")
    @ResponseBody
    public RespEntity nerAnnotate(@RequestParam("ner_tag") String tagSeq){
        System.out.println(tagSeq);

//        String[] tagSeq = new String[tokenLen];
//        for (int i=0; i<tokenLen; i++){
//            tagSeq[i] = "O";
//        }
//        if (StringUtils.isNotBlank(nerInfo)){
//            JSONArray jsonArray = JSON.parseArray(nerInfo);
//            for(int i=0 ; i< jsonArray.size() ; i++){
//                JSONObject obj = jsonArray.getJSONObject(i);
//                int l = obj.getInteger("L");
//                int r = obj.getInteger("R");
//                String tag = obj.getString("Tag");
//                if (l == r){
//                    tagSeq[r] = "S-" + tag;
//                } else {
//                    tagSeq[l] = "B-" + tag;
//
//                    for (int j=l+1; j<r; j++){
//                        tagSeq[j] = "I-" + tag;
//                    }
//
//                    tagSeq[r] = "E-" + tag;
//                }
//            }
//        }
//        String tagSeqStr = StringUtils.join(tagSeq, " ");

        return new RespEntity<>(RespStatus.SUCCESS, tagSeq);
    }
}
