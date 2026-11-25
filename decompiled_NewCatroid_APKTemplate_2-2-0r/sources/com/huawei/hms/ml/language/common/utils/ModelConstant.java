package com.huawei.hms.ml.language.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class ModelConstant {
    public static final int DEFAULT_MODEL_LEVEL = 2;
    public static final int EN_ZH_MODEL_LEVEL = 3;
    public static final String EN_ZH_MODEL_NAME = "translate-en_zh";
    public static final Map<String, Integer> MODEL_LEVEL_MAP;

    static {
        HashMap map = new HashMap();
        map.put(EN_ZH_MODEL_NAME, 3);
        MODEL_LEVEL_MAP = Collections.unmodifiableMap(map);
    }
}
