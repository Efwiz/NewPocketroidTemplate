package com.android.tools.r8.internal;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2347k extends AbstractC2122h implements Cloneable, Set {
    protected AbstractC2347k() {
    }

    @Override // com.android.tools.r8.internal.AbstractC2122h
    public final boolean c(boolean z) {
        return d(z);
    }

    public abstract boolean d(boolean z);

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
    public final int hashCode() {
        int size = size();
        InterfaceC2282j5 it = iterator();
        int i = 0;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return i;
            }
            i += it.q() ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU;
            size = i2;
        }
    }
}
