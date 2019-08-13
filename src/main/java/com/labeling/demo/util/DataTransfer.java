package com.labeling.demo.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class DataTransfer {
    public String dataTrans(Integer correctNum,Integer tagNum)
    {
        Double correctRate = new BigDecimal((float) correctNum / tagNum).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        NumberFormat nFromat = NumberFormat.getPercentInstance();
         return nFromat.format(correctRate);
    }
}
