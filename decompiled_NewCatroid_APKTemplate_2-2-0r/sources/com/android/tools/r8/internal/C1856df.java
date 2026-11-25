package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0436f1;
import com.android.tools.r8.dex.code.C0441g1;
import com.android.tools.r8.dex.code.C0446h1;
import com.android.tools.r8.dex.code.C0451i1;
import com.android.tools.r8.dex.code.C0456j1;
import com.android.tools.r8.dex.code.C0461k1;
import com.android.tools.r8.dex.code.C0466l1;
import com.android.tools.r8.dex.code.C0471m1;
import com.android.tools.r8.dex.code.C0476n1;
import com.android.tools.r8.dex.code.C0481o1;
import com.android.tools.r8.dex.code.C0486p1;
import com.android.tools.r8.dex.code.C0491q1;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.df, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1856df extends AbstractC1939ef {
    static final /* synthetic */ boolean f = true;
    private int e;

    public C1856df(C1015Gl c1015Gl) {
        super(c1015Gl);
        this.e = -1;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0118  */
    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final void a(C2322jf c2322jf, ArrayList arrayList) {
        com.android.tools.r8.dex.code.B1 c0436f1;
        com.android.tools.r8.dex.code.B1 c0441g1;
        C1015Gl c1015Gl = (C1015Gl) ((AbstractC1939ef) this).a;
        int iC = c2322jf.c(c1015Gl.K2().k()).c() - c2322jf.c(c1015Gl).c();
        C1015Gl c1015Gl2 = (C1015Gl) ((AbstractC1939ef) this).a;
        int iA = c2322jf.d.a((S10) c1015Gl2.h1().get(0), c1015Gl2.W0());
        if (iC < 0) {
            c2322jf.p = true;
        }
        if (this.e == 3) {
            if (!f) {
                C1015Gl c1015Gl3 = (C1015Gl) ((AbstractC1939ef) this).a;
                if (c1015Gl3 != c2322jf.a(c1015Gl3.K2()).a()) {
                    throw new AssertionError();
                }
            }
            iC--;
            arrayList.add(new com.android.tools.r8.dex.code.R2());
        }
        if (!f && iC == 0) {
            throw new AssertionError();
        }
        if (!c1015Gl.N2()) {
            C1015Gl c1015Gl4 = (C1015Gl) ((AbstractC1939ef) this).a;
            int iA2 = c2322jf.d.a((S10) c1015Gl4.h1().get(1), c1015Gl4.W0());
            switch (AbstractC1527Ye.a[((C1015Gl) ((AbstractC1939ef) this).a).L2().ordinal()]) {
                case 1:
                    c0436f1 = new C0436f1(iA, iA2, iC);
                    c0441g1 = c0436f1;
                    break;
                case 2:
                    c0436f1 = new C0446h1(iA, iA2, iC);
                    c0441g1 = c0436f1;
                    break;
                case 3:
                    c0436f1 = new C0456j1(iA, iA2, iC);
                    c0441g1 = c0436f1;
                    break;
                case 4:
                    c0436f1 = new C0466l1(iA, iA2, iC);
                    c0441g1 = c0436f1;
                    break;
                case 5:
                    c0436f1 = new C0476n1(iA, iA2, iC);
                    c0441g1 = c0436f1;
                    break;
                case 6:
                    c0436f1 = new C0486p1(iA, iA2, iC);
                    c0441g1 = c0436f1;
                    break;
                default:
                    c0441g1 = null;
                    break;
            }
        } else {
            switch (AbstractC1527Ye.a[((C1015Gl) ((AbstractC1939ef) this).a).L2().ordinal()]) {
                case 1:
                    c0441g1 = new C0441g1(iA, iC);
                    break;
                case 2:
                    c0441g1 = new C0451i1(iA, iC);
                    break;
                case 3:
                    c0441g1 = new C0461k1(iA, iC);
                    break;
                case 4:
                    c0441g1 = new C0471m1(iA, iC);
                    break;
                case 5:
                    c0441g1 = new C0481o1(iA, iC);
                    break;
                case 6:
                    c0441g1 = new C0491q1(iA, iC);
                    break;
                default:
                    c0441g1 = null;
                    break;
            }
        }
        c0441g1.g(super.c());
        arrayList.add(c0441g1);
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int d() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int e() {
        return 3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int f() {
        return 2;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0042  */
    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int a(C2322jf c2322jf) {
        AbstractC1939ef abstractC1939efA = c2322jf.a(((C1015Gl) ((AbstractC1939ef) this).a).K2());
        int iB = super.b();
        int iB2 = abstractC1939efA.b();
        if (iB2 < iB) {
            int iC = abstractC1939efA.c() - super.c();
            if (!f && iC >= 0) {
                throw new AssertionError();
            }
            if (iC < -32768) {
                c2322jf.i.add(((C1015Gl) ((AbstractC1939ef) this).a).h());
            }
        } else if ((iB2 - (iB - super.c())) - super.c() > 32767) {
            c2322jf.i.add(((C1015Gl) ((AbstractC1939ef) this).a).h());
        }
        C1015Gl c1015Gl = (C1015Gl) ((AbstractC1939ef) this).a;
        int i = c1015Gl == c2322jf.a(c1015Gl.K2()).a() ? 3 : 2;
        this.e = i;
        return i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final AbstractC2412ko a() {
        return ((AbstractC1939ef) this).a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final boolean a(AbstractC1939ef abstractC1939ef, C2322jf c2322jf) {
        int[] iArr;
        int[] iArr2;
        if (!(abstractC1939ef instanceof C1856df)) {
            return false;
        }
        C1856df c1856df = (C1856df) abstractC1939ef;
        if (((C1015Gl) ((AbstractC1939ef) this).a).L2() != ((C1015Gl) ((AbstractC1939ef) c1856df).a).L2()) {
            return false;
        }
        if (((C1015Gl) ((AbstractC1939ef) this).a).N2()) {
            C1015Gl c1015Gl = (C1015Gl) ((AbstractC1939ef) this).a;
            iArr = new int[]{c2322jf.d.a((S10) ((ArrayList) c1015Gl.h1()).get(0), c1015Gl.W0())};
        } else {
            C1015Gl c1015Gl2 = (C1015Gl) ((AbstractC1939ef) this).a;
            S10 s10 = (S10) ((ArrayList) c1015Gl2.h1()).get(0);
            int iW0 = c1015Gl2.W0();
            C1015Gl c1015Gl3 = (C1015Gl) ((AbstractC1939ef) this).a;
            iArr = new int[]{c2322jf.d.a(s10, iW0), c2322jf.d.a((S10) ((ArrayList) c1015Gl3.h1()).get(1), c1015Gl3.W0())};
        }
        if (((C1015Gl) ((AbstractC1939ef) c1856df).a).N2()) {
            C1015Gl c1015Gl4 = (C1015Gl) ((AbstractC1939ef) c1856df).a;
            iArr2 = new int[]{c2322jf.d.a((S10) ((ArrayList) c1015Gl4.h1()).get(0), c1015Gl4.W0())};
        } else {
            C1015Gl c1015Gl5 = (C1015Gl) ((AbstractC1939ef) c1856df).a;
            S10 s11 = (S10) ((ArrayList) c1015Gl5.h1()).get(0);
            int iW1 = c1015Gl5.W0();
            C1015Gl c1015Gl6 = (C1015Gl) ((AbstractC1939ef) c1856df).a;
            iArr2 = new int[]{c2322jf.d.a(s11, iW1), c2322jf.d.a((S10) ((ArrayList) c1015Gl6.h1()).get(1), c1015Gl6.W0())};
        }
        return Arrays.equals(iArr, iArr2);
    }
}
