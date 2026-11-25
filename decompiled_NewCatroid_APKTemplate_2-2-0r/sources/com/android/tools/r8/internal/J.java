package com.android.tools.r8.internal;

import java.util.AbstractCollection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J extends AbstractCollection implements InterfaceC3088tr {
    protected J() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public boolean a(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return f(((Integer) obj).intValue());
    }

    public boolean j(int i) {
        InterfaceC0841Ar it = iterator();
        while (it.hasNext()) {
            if (i == it.t()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return j(((Integer) obj).intValue());
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC0841Ar it = iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                sb.append("}");
                return sb.toString();
            }
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(String.valueOf(it.t()));
            size = i;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public boolean add(Integer num) {
        return a(num.intValue());
    }

    public int[] a(int[] iArr) {
        if (iArr == null || iArr.length < size()) {
            iArr = new int[size()];
        }
        AbstractC0935Dr.a(iterator(), iArr);
        return iArr;
    }

    public boolean a(InterfaceC3088tr interfaceC3088tr) {
        InterfaceC0841Ar it = interfaceC3088tr.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (a(it.t())) {
                z = true;
            }
        }
        return z;
    }
}
