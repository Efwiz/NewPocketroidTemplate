package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3470z implements InterfaceC2730op, Serializable {
    protected Object a;

    protected AbstractC3470z() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2730op
    public Object a(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public abstract void clear();

    @Override // com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return a(((Integer) obj).intValue());
    }

    public final Object get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (a(iIntValue)) {
            return get(iIntValue);
        }
        return null;
    }

    public final Object put(Object obj, Object obj2) {
        int iIntValue = ((Integer) obj).intValue();
        boolean zA = a(iIntValue);
        Object objA = a(iIntValue, obj2);
        if (zA) {
            return objA;
        }
        return null;
    }

    public Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    public final Object remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        boolean zA = a(iIntValue);
        Object objRemove = remove(iIntValue);
        if (zA) {
            return objRemove;
        }
        return null;
    }
}
