package com.android.tools.r8.internal;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class EB extends AbstractSet {
    final /* synthetic */ JB a;

    EB(JB jb) {
        this.a = jb;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        IB ibA;
        Object obj2;
        Object value;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        JB jb = this.a;
        Map.Entry entry = (Map.Entry) obj;
        jb.getClass();
        Object key = entry.getKey();
        IB ib = null;
        if (key != null) {
            try {
                ibA = jb.a(key, false);
            } catch (ClassCastException unused) {
                ibA = null;
            }
        } else {
            ibA = null;
        }
        if (ibA != null && ((obj2 = ibA.g) == (value = entry.getValue()) || (obj2 != null && obj2.equals(value)))) {
            ib = ibA;
        }
        return ib != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new DB(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        IB ibA;
        Object obj2;
        Object value;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        JB jb = this.a;
        Map.Entry entry = (Map.Entry) obj;
        jb.getClass();
        Object key = entry.getKey();
        IB ib = null;
        if (key != null) {
            try {
                ibA = jb.a(key, false);
            } catch (ClassCastException unused) {
                ibA = null;
            }
        } else {
            ibA = null;
        }
        if (ibA != null && ((obj2 = ibA.g) == (value = entry.getValue()) || (obj2 != null && obj2.equals(value)))) {
            ib = ibA;
        }
        if (ib == null) {
            return false;
        }
        this.a.b(ib, true);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.c;
    }
}
