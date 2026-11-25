package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class RD {
    public static HashMap a() {
        return new HashMap();
    }

    public static HashMap b(int i) {
        return new HashMap(a(i));
    }

    public static LinkedHashMap c(int i) {
        return new LinkedHashMap(a(i));
    }

    static int a(int i) {
        if (i < 3) {
            AbstractC2625nb.a(i, "expectedSize");
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) ((i / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static IdentityHashMap b() {
        return new IdentityHashMap();
    }

    public static Map.Entry a(Object obj, Object obj2) {
        return new C1325Rl(obj, obj2);
    }

    static Object a(Map map, Object obj) {
        int i = AbstractC3123uM.a;
        map.getClass();
        try {
            return map.get(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    static Object a(Map.Entry entry) {
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }
}
