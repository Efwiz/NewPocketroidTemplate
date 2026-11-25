package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.f0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1963f0 extends AbstractC1576a0 implements Cloneable, InterfaceC1897eD {
    protected AbstractC1963f0() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean c(long j) {
        LC lc = (LC) this;
        QC qc = lc.a;
        int i = qc.g;
        qc.a(j);
        return lc.a.g != i;
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
        if (set.size() != ((LC) this).a.g) {
            return false;
        }
        return containsAll(set);
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int i = ((LC) this).a.g;
        XC it = iterator();
        int iA = 0;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return iA;
            }
            iA += AbstractC3307wk.a(((KC) it).c());
            i = i2;
        }
    }
}
