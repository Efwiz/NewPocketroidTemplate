package com.huawei.hms.mlsdk.internal.client.rest;

import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: RestClientManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class e {
    private static e a;
    private static Map<String, c> b = new HashMap();

    private e() {
    }

    public static e a() {
        e eVar;
        synchronized (e.class) {
            if (a == null) {
                a = new e();
            }
            eVar = a;
        }
        return eVar;
    }

    public c a(String str) {
        g gVar = new g(MLApplication.getInstance(), null);
        String str2 = g.class.getName() + "." + str;
        if (b.containsKey(str2)) {
            return b.get(str2);
        }
        try {
            c cVarA = gVar.a(str);
            a(str2, cVarA);
            return cVarA;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception unused) {
            return null;
        }
    }

    private void a(String str, c cVar) {
        synchronized (b) {
            b.remove(str);
            b.put(str, cVar);
        }
    }
}
