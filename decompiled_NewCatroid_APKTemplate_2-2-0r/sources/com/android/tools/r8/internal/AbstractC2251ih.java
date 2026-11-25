package com.android.tools.r8.internal;

import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ih, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2251ih implements BiPredicate {
    protected AbstractC2251ih() {
    }

    protected abstract int a(Object obj);

    protected abstract boolean a(Object obj, Object obj2);

    public final boolean b(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return a(obj, obj2);
    }

    public final C2173hh c(Object obj) {
        return new C2173hh(this, obj, 0);
    }

    @Override // java.util.function.BiPredicate
    public final boolean test(Object obj, Object obj2) {
        return b(obj, obj2);
    }

    public final int b(Object obj) {
        if (obj == null) {
            return 0;
        }
        return a(obj);
    }
}
