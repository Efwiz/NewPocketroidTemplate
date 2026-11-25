package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1617aV {
    static final /* synthetic */ boolean a = true;

    public abstract AbstractC1617aV a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar, C2005fV c2005fV);

    public C1846dV a() {
        return null;
    }

    public abstract boolean a(AbstractC1305Qs abstractC1305Qs);

    public C1928eV b() {
        return null;
    }

    public boolean c() {
        return this instanceof P1;
    }

    public boolean d() {
        return false;
    }

    public boolean e() {
        return this instanceof C1846dV;
    }

    public boolean f() {
        return this instanceof C1928eV;
    }

    public boolean g() {
        return this instanceof C3493zG;
    }

    public final AbstractC1617aV a(Supplier supplier) {
        boolean z = this instanceof C3493zG;
        if (z) {
            return C3493zG.b;
        }
        AbstractC1617aV abstractC1617aV = (AbstractC1617aV) supplier.get();
        if (this instanceof P1) {
            return abstractC1617aV;
        }
        abstractC1617aV.getClass();
        if (abstractC1617aV instanceof P1) {
            return this;
        }
        if (z || (abstractC1617aV instanceof C3493zG)) {
            return C3493zG.b;
        }
        if (this instanceof C1846dV) {
            return a().a(abstractC1617aV);
        }
        if (abstractC1617aV instanceof C1846dV) {
            return abstractC1617aV.a().a(this);
        }
        boolean z2 = a;
        if (!z2 && !d() && !(this instanceof C1928eV)) {
            throw new AssertionError();
        }
        if (z2 || abstractC1617aV.d() || (abstractC1617aV instanceof C1928eV)) {
            return new C1846dV(AbstractC1639am.a(this, abstractC1617aV));
        }
        throw new AssertionError();
    }
}
