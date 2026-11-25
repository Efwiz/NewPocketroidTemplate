package com.hcaptcha.sdk.journeylitics;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
final class MetaMapHelper {
    private MetaMapHelper() {
    }

    @SafeVarargs
    public static Map<String, Object> createFieldMap(Map.Entry<String, Object>... entryArr) {
        HashMap map = new HashMap();
        for (Map.Entry<String, Object> entry : entryArr) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    @SafeVarargs
    public static Map<String, Object> createMetaMap(Map.Entry<FieldKey, Object>... entryArr) {
        HashMap map = new HashMap();
        for (Map.Entry<FieldKey, Object> entry : entryArr) {
            map.put(entry.getKey().getJsonKey(), entry.getValue());
        }
        return map;
    }
}
