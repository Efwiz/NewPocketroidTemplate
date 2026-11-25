package com.beust.jcommander.internal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class Maps {
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> Map<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <T> Map<T, T> newHashMap(T... tArr) {
        Map<T, T> mapNewHashMap = newHashMap();
        for (int i = 0; i < tArr.length; i += 2) {
            mapNewHashMap.put(tArr[i], tArr[i + 1]);
        }
        return mapNewHashMap;
    }
}
