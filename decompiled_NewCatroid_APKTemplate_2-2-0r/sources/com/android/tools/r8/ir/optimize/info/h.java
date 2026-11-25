package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.internal.AbstractC1008Gg;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1380Tn;
import com.android.tools.r8.internal.AbstractC1617aV;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.InterfaceC2090ga;
import com.android.tools.r8.internal.M5;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.BitSet;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class h implements g {
    static final /* synthetic */ boolean a = true;

    public abstract boolean A();

    public abstract boolean B();

    public abstract boolean C();

    public abstract boolean D();

    public abstract boolean E();

    public abstract AbstractC1380Tn a(C1166Ls c1166Ls);

    public abstract boolean a(AbstractC1305Qs abstractC1305Qs, A1 a1);

    public abstract boolean e();

    public abstract boolean f();

    public abstract boolean g();

    public abstract B1 h();

    public abstract a i();

    public abstract M5 j();

    public abstract InterfaceC2090ga k();

    public abstract AbstractC1380Tn l();

    public abstract AbstractC1154Lf m();

    public abstract AbstractC1008Gg n();

    public abstract Set o();

    public abstract BitSet p();

    public abstract BitSet q();

    public abstract int r();

    public abstract AbstractC1617aV s();

    public abstract BitSet t();

    public abstract boolean u();

    public final boolean v() {
        if (a || t() == null || !t().isEmpty()) {
            return t() != null;
        }
        throw new AssertionError();
    }

    public abstract boolean w();

    public abstract boolean x();

    public abstract boolean y();

    public abstract AbstractC3938n2 z();
}
