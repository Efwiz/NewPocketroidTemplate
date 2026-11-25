package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2557mh extends C1207Nf {
    static final /* synthetic */ boolean h = true;

    C2557mh(C1229Oa c1229Oa) {
        super(c1229Oa);
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final AbstractC1154Lf a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, Set set) {
        AbstractC3472z00 abstractC3472z00A = super.o().b().a(c0702m, abstractC1476Wj, (AbstractC1476Wj) null, set);
        if (h || abstractC3472z00A.w() || abstractC3472z00A.H()) {
            return abstractC3472z00A.w() ? new C2557mh(abstractC3472z00A.b()) : C1207Nf.f;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final C1229Oa d() {
        return super.o().b();
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final C1229Oa e() {
        return super.o().b();
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean equals(Object obj) {
        if (obj == null || C2557mh.class != obj.getClass()) {
            return false;
        }
        return super.o().b().equals(super.o().b());
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final int hashCode() {
        return super.o().b().hashCode();
    }

    @Override // com.android.tools.r8.internal.C1207Nf, com.android.tools.r8.internal.AbstractC1154Lf
    public final boolean j() {
        return true;
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final AbstractC3472z00 o() {
        return super.o().b();
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final String toString() {
        return "ExactDynamicType(type=" + super.o().b() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.C1207Nf
    public final C1207Nf a(WH wh) {
        return f() == wh ? this : new C2557mh(super.o().b().a(wh));
    }
}
