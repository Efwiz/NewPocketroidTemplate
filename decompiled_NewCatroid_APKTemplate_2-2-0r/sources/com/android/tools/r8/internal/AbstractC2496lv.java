package com.android.tools.r8.internal;

import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2496lv {
    public String a() {
        return null;
    }

    public C2424kx b() {
        return null;
    }

    public abstract List c();

    public final boolean d() {
        return a() != null;
    }

    public static AbstractC2496lv a(String str) {
        return new C2339jv(str);
    }

    public static AbstractC2496lv a(C2424kx c2424kx) {
        return new C2420kv(c2424kx);
    }

    public final boolean a(Predicate predicate) {
        if (d()) {
            return predicate.test(a());
        }
        return b().e();
    }
}
