package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1180Mf extends C1207Nf {
    static final /* synthetic */ boolean i = true;
    private final C1229Oa h;

    private C1180Mf(C1229Oa c1229Oa, C1229Oa c1229Oa2) {
        super(c1229Oa);
        boolean z = i;
        if (!z && c1229Oa.equals(c1229Oa2)) {
            throw new AssertionError();
        }
        if (!z && c1229Oa.N() != c1229Oa2.N()) {
            throw new AssertionError();
        }
        this.h = c1229Oa2;
    }

    static C1180Mf a(C0702m c0702m, C1229Oa c1229Oa, C1229Oa c1229Oa2) {
        boolean z = i;
        if (!z && c1229Oa == null) {
            throw new AssertionError();
        }
        if (!z && c1229Oa2 == null) {
            throw new AssertionError();
        }
        if (!z && c1229Oa.N() != c1229Oa2.N()) {
            throw new AssertionError();
        }
        if (z || ((C3733i) c0702m.f()).b(c1229Oa2.R(), c1229Oa.R())) {
            return new C1180Mf(c1229Oa, c1229Oa2);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final C1229Oa d() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1180Mf c1180Mf = (C1180Mf) obj;
        return super.o().b().equals(super.o().b()) && this.h.equals(c1180Mf.h);
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final AbstractC3472z00 o() {
        return super.o().b();
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final String toString() {
        return "DynamicTypeWithLowerBound(upperBound=" + super.o().b() + ", lowerBound=" + this.h + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final int hashCode() {
        return Objects.hash(super.o().b(), this.h);
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final C1207Nf a(WH wh) {
        return super.o().b().N() == wh ? this : new C1180Mf(super.o().b().a(wh), this.h.a(wh));
    }
}
