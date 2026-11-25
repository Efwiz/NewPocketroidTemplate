package com.android.tools.r8.ir.regalloc;

import com.android.tools.r8.internal.AbstractC3472z00;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class m {
    static final /* synthetic */ boolean d = true;
    final AbstractC3472z00 a;
    c b;
    final c c;

    public m(AbstractC3472z00 abstractC3472z00, c cVar, c cVar2) {
        this.a = abstractC3472z00;
        this.c = cVar;
        this.b = cVar2;
        boolean z = d;
        if (!z && cVar.i() == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
        if (!z && cVar2.i() == Integer.MIN_VALUE) {
            throw new AssertionError();
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof m)) {
            return false;
        }
        m mVar = (m) obj;
        return this.a == mVar.a && this.b.i() == mVar.b.i() && this.c.i() == mVar.c.i() && this.b.l() == mVar.b.l() && this.c.l() == mVar.c.l();
    }

    public final int hashCode() {
        return (this.c.i() * 5) + (this.b.i() * 3) + this.a.hashCode();
    }

    public final String toString() {
        return this.c.i() + " <- " + this.b.i() + " (" + this.a + Constant.AFTER_QUTO;
    }
}
