package com.flip.service.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Charles on 21/06/2021
 */
@Component
public class RefUtil {

    private static DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static synchronized String generateUniqueRef() {
        String ref = UUID.randomUUID().toString().replace("-", "");
        return ref;
    }

    public static synchronized String generateTransactionRef() {
        return formatter.format(new Date());
    }
}
