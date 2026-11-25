package com.huawei.hms.ml.language.common.utils;

import android.util.ArrayMap;
import android.util.Log;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class ResultsProcessingUtil {
    static Map<String, Object> map;

    static {
        ArrayMap arrayMap = new ArrayMap();
        map = arrayMap;
        arrayMap.put(ErrorMessage.ERROR_MESSAGE_IS_MAX_LENGTH, ErrorMessage.ERROR_MESSAGE_IS_MAX_LENGTH);
        map.put(ErrorMessage.ERROR_MESSAGE_MAX_LENGTH, ErrorMessage.ERROR_MESSAGE_IS_MAX_LENGTH);
        map.put(ErrorMessage.ERROR_MESSAGE_EMPTY, ErrorMessage.ERROR_MESSAGE_CLOUD_EMPTY);
    }

    public static String getMessage(String str) {
        Log.i("retMsg", str);
        if (str == null) {
            return ErrorMessage.ERROR_MESSAGE_CLOUD_EMPTY;
        }
        String str2 = (String) map.get(str);
        return str2 == null ? ErrorMessage.ERROR_MESSAGE_PARAMETER : str2;
    }
}
