package com.android.tools.r8.internal;

import java.util.AbstractCollection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1576a0 extends AbstractCollection implements UC {
    protected AbstractC1576a0() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public abstract XC iterator();

    public boolean a(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        return a(((Long) obj).longValue());
    }

    public abstract boolean b(long j);

    public boolean c(long j) {
        XC it = iterator();
        while (it.hasNext()) {
            if (j == it.c()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return b(((Long) obj).longValue());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return c(((Long) obj).longValue());
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        XC it = iterator();
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
            sb.append(String.valueOf(it.c()));
            size = i;
        }
    }
}
