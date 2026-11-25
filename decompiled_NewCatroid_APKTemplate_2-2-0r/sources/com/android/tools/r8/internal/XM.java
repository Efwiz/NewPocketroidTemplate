package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class XM extends AbstractC3472z00 {
    static final /* synthetic */ boolean c = true;

    static XM a(C0767v2 c0767v2, boolean z) {
        if (c || c0767v2.W0()) {
            return a((char) c0767v2.e.e[0], z);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final boolean H() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final WH N() {
        return WH.b();
    }

    public abstract String P();

    public abstract String Q();

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final XM c() {
        return this;
    }

    public final C0767v2 a(C0722p1 c0722p1) {
        if (s()) {
            return c0722p1.v1;
        }
        if (u()) {
            return c0722p1.w1;
        }
        if (J()) {
            return c0722p1.C1;
        }
        if (v()) {
            return c0722p1.x1;
        }
        if (C()) {
            return c0722p1.A1;
        }
        if (B()) {
            return c0722p1.z1;
        }
        if (D()) {
            return c0722p1.B1;
        }
        if (z()) {
            return c0722p1.y1;
        }
        throw new C3405y10("Imprecise primitive type '" + toString() + FormatHelper.QUOTE);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0058  */
    /* JADX WARN: Code duplicated, block: B:33:0x005f  */
    private static XM a(char c2, boolean z) {
        if (c2 == 'F') {
            return AbstractC3472z00.j();
        }
        if (c2 == 'S') {
            if (z) {
                return AbstractC3472z00.n();
            }
            if (z) {
                return AbstractC3472z00.h();
            }
        } else {
            if (c2 == 'V') {
                throw new C3089ts("No value type for void type.");
            }
            if (c2 != 'Z') {
                if (c2 != 'I') {
                    if (c2 == 'J') {
                        return AbstractC3472z00.l();
                    }
                    switch (c2) {
                        case 'B':
                            break;
                        case 'C':
                            if (z) {
                                return AbstractC3472z00.h();
                            }
                            break;
                        case 'D':
                            return AbstractC3472z00.i();
                        default:
                            throw new C3405y10("Invalid descriptor char '" + c2 + FormatHelper.QUOTE);
                    }
                }
            } else if (z) {
                return AbstractC3472z00.e();
            }
            if (z) {
                return AbstractC3472z00.g();
            }
            if (z) {
                return AbstractC3472z00.n();
            }
            if (z) {
                return AbstractC3472z00.h();
            }
        }
        return AbstractC3472z00.k();
    }

    public static XM a(EnumC2527mI enumC2527mI) {
        switch (WM.a[enumC2527mI.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return AbstractC3472z00.k();
            case 5:
                return AbstractC3472z00.j();
            case 6:
                return AbstractC3472z00.l();
            case 7:
                return AbstractC3472z00.i();
            default:
                throw new C3405y10("Invalid numeric type '" + enumC2527mI + FormatHelper.QUOTE);
        }
    }
}
