package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1777cc extends ZE {
    static final /* synthetic */ boolean a = true;

    @Override // com.android.tools.r8.internal.YE
    public final YE a(C0702m c0702m, AbstractC0717o2 abstractC0717o2, YE ye, FW fw) {
        if (ye.d()) {
            return this;
        }
        if (ye.isUnknown()) {
            return ye;
        }
        AbstractC1777cc abstractC1777ccE = ye.e();
        if (this instanceof C1853dc) {
            abstractC1777ccE.getClass();
            if (abstractC1777ccE instanceof C1853dc) {
                return b().a(c0702m, abstractC0717o2, abstractC1777ccE.b(), fw);
            }
        }
        if (this instanceof C2247ic) {
            abstractC1777ccE.getClass();
            if (abstractC1777ccE instanceof C2247ic) {
                return j().a(c0702m, abstractC0717o2, abstractC1777ccE.j(), fw);
            }
        }
        if (a) {
            return C2508m10.k();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final AbstractC1777cc e() {
        return this;
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final boolean f() {
        return true;
    }

    @Override // com.android.tools.r8.internal.YE
    public final YE a(C0702m c0702m, AbstractC0717o2 abstractC0717o2, FW fw, Function function) {
        return a(c0702m, abstractC0717o2, (YE) function.apply(this), fw);
    }
}
