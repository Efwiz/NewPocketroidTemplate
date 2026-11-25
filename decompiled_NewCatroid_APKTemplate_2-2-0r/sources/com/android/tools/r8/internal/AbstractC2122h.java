package com.android.tools.r8.internal;

import java.util.AbstractCollection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2122h extends AbstractCollection implements InterfaceC1889e5 {
    protected AbstractC2122h() {
    }

    public boolean a(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        return a(((Boolean) obj).booleanValue());
    }

    public abstract boolean b(boolean z);

    public boolean c(boolean z) {
        InterfaceC2282j5 it = iterator();
        while (it.hasNext()) {
            if (z == it.q()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, com.android.tools.r8.internal.InterfaceC1889e5
    public final boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return b(((Boolean) obj).booleanValue());
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return c(((Boolean) obj).booleanValue());
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        InterfaceC2282j5 it = iterator();
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
            sb.append(String.valueOf(it.q()));
            size = i;
        }
    }
}
