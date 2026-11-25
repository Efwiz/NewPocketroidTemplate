package com.android.tools.r8.internal;

import java.util.AbstractMap;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.At, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0843At extends AbstractMap {
    final C0843At a;
    final BitSet b;
    final HashMap c;
    final C3338xA d;
    final /* synthetic */ C0869Bt e;

    C0843At(C0869Bt c0869Bt, C0843At c0843At, BitSet bitSet) {
        this.e = c0869Bt;
        for (C0843At c0843At2 = c0843At; c0843At2 != null; c0843At2 = c0843At2.a) {
            if (c0843At2.b == bitSet) {
                throw new IllegalArgumentException("Recursive invocation of " + bitSet);
            }
        }
        this.a = c0843At;
        this.b = bitSet;
        this.d = c0843At == null ? null : new C3338xA();
        this.c = new HashMap();
        C3338xA c3338xA = null;
        for (int i = 0; i < c0869Bt.x.size(); i++) {
            AbstractC3097u abstractC3097uJ = c0869Bt.x.j(i);
            if (abstractC3097uJ.c() == 8) {
                C3338xA c3338xA2 = (C3338xA) abstractC3097uJ;
                c3338xA = c3338xA == null ? new C3338xA() : c3338xA;
                this.c.put(c3338xA2, c3338xA);
            } else if (a(i) == this) {
                c3338xA = null;
            }
        }
    }

    final C0843At a(int i) {
        if (!this.b.get(i)) {
            return null;
        }
        if (!this.e.H.get(i)) {
            return this;
        }
        C0843At c0843At = this;
        for (C0843At c0843At2 = this.a; c0843At2 != null; c0843At2 = c0843At2.a) {
            if (c0843At2.b.get(i)) {
                c0843At = c0843At2;
            }
        }
        return c0843At;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        C3338xA c3338xA = (C3338xA) obj;
        return (C3338xA) a(this.e.x.b(c3338xA)).c.get(c3338xA);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }
}
