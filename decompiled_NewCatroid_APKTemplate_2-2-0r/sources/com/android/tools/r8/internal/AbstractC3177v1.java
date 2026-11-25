package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.v1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3177v1 extends AbstractC2891r1 implements Cloneable, InterfaceC3211vS {
    protected AbstractC3177v1() {
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
        InterfaceC1912eK it = iterator();
        int iIdentityHashCode = 0;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iIdentityHashCode;
            }
            iIdentityHashCode += System.identityHashCode(it.next());
            size = i;
        }
    }
}
