package com.huawei.hms.hatool;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public final class f0 {
    public static f0 b;
    public volatile Map<String, g0> a = new HashMap();

    public static f0 a() {
        if (b == null) {
            b();
        }
        return b;
    }

    public static synchronized void b() {
        if (b == null) {
            b = new f0();
        }
    }

    public final g0 a(String str) {
        if (!this.a.containsKey(str)) {
            this.a.put(str, new g0());
        }
        return this.a.get(str);
    }

    public g0 a(String str, long j) {
        g0 g0VarA = a(str);
        g0VarA.a(j);
        return g0VarA;
    }
}
