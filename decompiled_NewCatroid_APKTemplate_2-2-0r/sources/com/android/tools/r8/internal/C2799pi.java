package com.android.tools.r8.internal;

import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2799pi extends S10 {
    static final /* synthetic */ boolean r = true;
    private final int q;

    public C2799pi(int i, AbstractC3472z00 abstractC3472z00) {
        super(-1, abstractC3472z00, null);
        a(true);
        this.q = i;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean L() {
        return false;
    }

    @Override // com.android.tools.r8.internal.S10
    public final C2799pi b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.S10
    public final boolean b(Predicate predicate) {
        return false;
    }

    @Override // com.android.tools.r8.internal.S10
    public final EnumC1812d20 d0() {
        AbstractC3472z00 abstractC3472z00W = w();
        if (!abstractC3472z00W.H()) {
            if (r || abstractC3472z00W.I()) {
                return EnumC1812d20.OBJECT;
            }
            throw new AssertionError();
        }
        if (abstractC3472z00W.K()) {
            if (abstractC3472z00W.C()) {
                return EnumC1812d20.INT;
            }
            if (abstractC3472z00W.B()) {
                return EnumC1812d20.FLOAT;
            }
        } else {
            if (!r && !abstractC3472z00W.M()) {
                throw new AssertionError();
            }
            if (abstractC3472z00W.z()) {
                return EnumC1812d20.DOUBLE;
            }
            if (abstractC3472z00W.D()) {
                return EnumC1812d20.LONG;
            }
        }
        throw new C3405y10("Unexpected imprecise type: " + abstractC3472z00W);
    }

    public int j0() {
        return this.q;
    }

    @Override // com.android.tools.r8.internal.S10
    public final String toString() {
        return "r" + this.q;
    }
}
