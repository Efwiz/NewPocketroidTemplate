package com.huawei.hms.hatool;

import android.content.Context;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: classes10.dex */
public abstract class m1 {
    public static k1 a;

    public static synchronized k1 a() {
        if (a == null) {
            a = p1.c().b();
        }
        return a;
    }

    public static void a(int i, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a() == null || !x0.b().a()) {
            return;
        }
        if (i == 1 || i == 0) {
            a.a(i, str, linkedHashMap);
        } else {
            z.d("hmsSdk", "Data type no longer collects range.type: " + i);
        }
    }

    @Deprecated
    public static void a(Context context, String str, String str2) {
        if (a() != null) {
            a.a(context, str, str2);
        }
    }

    public static void b(int i, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a() == null || !x0.b().a()) {
            return;
        }
        if (i == 1 || i == 0) {
            a.b(i, str, linkedHashMap);
        } else {
            z.d("hmsSdk", "Data type no longer collects range.type: " + i);
        }
    }

    public static boolean b() {
        return p1.c().a();
    }

    public static void c() {
        if (a() == null || !x0.b().a()) {
            return;
        }
        a.a(-1);
    }
}
