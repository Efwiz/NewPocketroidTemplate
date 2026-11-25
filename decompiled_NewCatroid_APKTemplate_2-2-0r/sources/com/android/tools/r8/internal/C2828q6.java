package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.HashMap;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2828q6 {
    static final /* synthetic */ boolean f = true;
    private final AbstractC1915eM a;
    private final HashMap b;
    private final AbstractC1915eM c;
    private final boolean d;
    private AbstractC1915eM e;

    public C2828q6(AbstractC1915eM abstractC1915eM, int i, C0684j2 c0684j2, boolean z, AbstractC1915eM abstractC1915eM2) {
        this.b = new HashMap((abstractC1915eM == null ? 0 : 1) + 1 + i);
        abstractC1915eM2 = abstractC1915eM2 == null ? AbstractC1915eM.c.x().a(0).a(c0684j2).a() : abstractC1915eM2;
        if (abstractC1915eM != null) {
            this.a = b(abstractC1915eM);
            this.d = z;
            this.c = b(com.android.tools.r8.graph.W.a(abstractC1915eM, abstractC1915eM2, z));
        } else {
            this.a = null;
            this.d = false;
            this.c = b(abstractC1915eM2);
        }
    }

    public final AbstractC1915eM a() {
        return this.c;
    }

    public final AbstractC1915eM b(AbstractC1915eM abstractC1915eM) {
        AbstractC1915eM abstractC1915eM2 = (AbstractC1915eM) this.b.putIfAbsent(abstractC1915eM, abstractC1915eM);
        return abstractC1915eM2 != null ? abstractC1915eM2 : abstractC1915eM;
    }

    public final AbstractC1915eM a(AbstractC1915eM abstractC1915eM) {
        AbstractC1915eM abstractC1915eMA;
        if (abstractC1915eM == null) {
            return this.a;
        }
        AbstractC1915eM abstractC1915eM2 = abstractC1915eM.c;
        if (abstractC1915eM2 == null && this.a == null) {
            return b(abstractC1915eM);
        }
        if (abstractC1915eM2 == null && this.d) {
            return this.a;
        }
        AbstractC1915eM abstractC1915eMA2 = a(abstractC1915eM2);
        if (abstractC1915eM.o()) {
            AbstractC1915eM.b.a aVarA = AbstractC1915eM.b.x().a(abstractC1915eM.b);
            aVarA.c = abstractC1915eMA2;
            AbstractC1915eM.b.a aVar = (AbstractC1915eM.b.a) aVarA.c();
            aVar.f = true;
            abstractC1915eMA = ((AbstractC1915eM.b.a) aVar.c()).a();
        } else {
            AbstractC1915eM.a aVarA2 = abstractC1915eM.a();
            aVarA2.c = abstractC1915eMA2;
            abstractC1915eMA = aVarA2.c().a();
        }
        return b(abstractC1915eMA);
    }

    public final AbstractC1915eM a(boolean z, Supplier supplier, C0684j2 c0684j2) {
        AbstractC1915eM abstractC1915eMA;
        if (this.e == null) {
            if (z) {
                int iMax = Integer.MIN_VALUE;
                int iMin = Integer.MAX_VALUE;
                for (AbstractC1915eM abstractC1915eM : (Iterable) supplier.get()) {
                    if (!f && abstractC1915eM != abstractC1915eM.h()) {
                        throw new AssertionError();
                    }
                    int i = abstractC1915eM.a;
                    iMin = Math.min(iMin, i);
                    iMax = Math.max(iMax, i);
                }
                if (iMin == Integer.MAX_VALUE) {
                    abstractC1915eMA = this.c;
                } else {
                    AbstractC1915eM.c.a aVarX = AbstractC1915eM.c.x();
                    if (iMin < iMax) {
                        iMin--;
                    }
                    AbstractC1915eM.c.a aVarA = aVarX.a(iMin).a(c0684j2);
                    aVarA.c = this.a;
                    abstractC1915eMA = aVarA.a();
                }
                this.e = abstractC1915eMA;
            } else {
                this.e = AbstractC1915eM.v();
            }
        }
        return this.e;
    }
}
