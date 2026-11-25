package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2350k1 implements XQ, Serializable {
    protected AbstractC2350k1() {
    }

    public boolean a(Object obj, boolean z) {
        throw new UnsupportedOperationException();
    }

    public boolean c(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public final Object get(Object obj) {
        if (containsKey(obj)) {
            return Boolean.valueOf(b(obj));
        }
        return null;
    }

    public final Object put(Object obj, Object obj2) {
        boolean zContainsKey = containsKey(obj);
        boolean zA = a(obj, ((Boolean) obj2).booleanValue());
        if (zContainsKey) {
            return Boolean.valueOf(zA);
        }
        return null;
    }

    public final Object remove(Object obj) {
        boolean zContainsKey = containsKey(obj);
        boolean zC = c(obj);
        if (zContainsKey) {
            return Boolean.valueOf(zC);
        }
        return null;
    }
}
