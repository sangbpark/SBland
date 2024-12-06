package com.sbland.common.uid.provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MerchantUidProvider implements IUidGenerator {

	@Override
    public String createUid(String type) {
        String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String uniqueId = UUID.randomUUID().toString().split("-")[0];
        return type + "_" + datePrefix + "_" + uniqueId;
    }

}
