package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class E implements InterfaceC3087tq, Serializable {
    protected Object a;

    protected E() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
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

    public Object get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (a(iIntValue)) {
            return get(iIntValue);
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3087tq
    public Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Object put(Integer num, Object obj) {
        int iIntValue = num.intValue();
        boolean zA = a(iIntValue);
        Object objA = a(iIntValue, obj);
        if (zA) {
            return objA;
        }
        return null;
    }

    public Object remove(Object obj) {
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
