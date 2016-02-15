/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import com.hf.hfw.api.v1.DateService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.json.JsonObject;
import javax.ws.rs.Path;

/**
 *
 * @author pldorrell
 */
@Path("/date")
public class DateServiceImpl implements DateService {
    private final static SimpleDateFormat YEAR_ONLY = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat MONTH_ONLY = new SimpleDateFormat("MM");
    private final static SimpleDateFormat DAY_ONLY = new SimpleDateFormat("dd");
    
    @Override
    public String getCurrentYear() {
        return YEAR_ONLY.format(new Date());

    }

    @Override
    public String getCurrentMonth() {
        return MONTH_ONLY.format(new Date());
    }

    @Override
    public String getCurrentDay() {
        return DAY_ONLY.format(new Date());
    }

    @Override
    public Object getCurrent() {
        HashMap<String,String> dateMap = new HashMap<>();
        dateMap.put("date", this.getCurrentYear()+"-"+this.getCurrentMonth()+"-"+this.getCurrentDay());
        return dateMap;
    }
    
}
