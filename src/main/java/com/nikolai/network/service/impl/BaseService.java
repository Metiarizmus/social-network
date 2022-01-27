package com.nikolai.network.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseService {
    public String dateNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String format = formatter.format(new Date());
        return format;
    }


    public String convertDateToFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String format = formatter.format(date);
        return format;
    }
}
