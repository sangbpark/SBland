package com.sbland.common.keys.provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UidProvider {

    public String createUid (String type) {
        String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String uniqueId = UUID.randomUUID().toString().split("-")[0];
        return type + "_" + datePrefix + "_" + uniqueId;
    }

}
