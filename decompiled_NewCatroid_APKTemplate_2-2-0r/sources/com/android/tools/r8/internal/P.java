package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class P extends J implements Cloneable, InterfaceC1277Pr {
    protected P() {
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (set.size() != size()) {
            return false;
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int size = size();
        InterfaceC0841Ar it = iterator();
        int iT = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iT;
            }
            iT += it.t();
            size = i;
        }
    }

    @Override // com.android.tools.r8.internal.J
    public final boolean j(int i) {
        return k(i);
    }

    public boolean k(int i) {
        return super.j(i);
    }
}
