package com.sxkj.uc.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @ClassName DateConvert
 * @Description: Date和LocalDateTime转换
 * @Author zwd
 * @Date 2019/12/12 0012
 **/
@Slf4j
public class DateConvert {

    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        try {
            return sdf.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e.getCause());
            return date;
        }
    }

    public static LocalDateTime fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
