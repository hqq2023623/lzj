package zj.mongo;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 报无法将java.util.Date转换成java.sql.Timestamp。
 * 原因是我们实体类中的时间是Timestamp类型的，而Spring Data默认的时间类型是Date
 *
 * @author Lzj Created on 2015/12/18.
 */
public class TimestampConverter implements Converter<Date, Timestamp> {

    public Timestamp convert(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

}
