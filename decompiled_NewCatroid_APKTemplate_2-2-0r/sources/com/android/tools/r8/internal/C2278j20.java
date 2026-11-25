package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2278j20 extends SF {
    static final /* synthetic */ boolean c = true;
    private final SF b;

    public C2278j20(SF sf) {
        this.b = sf;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        boolean z = c;
        if (!z) {
            Collection collectionA = this.b.a(c2688oE);
            if (!z && collectionA.size() != 1) {
                throw new AssertionError();
            }
            C2688oE c2688oE2 = (C2688oE) collectionA.iterator().next();
            if (!z && (c2688oE2.size() != c2688oE.size() || !c2688oE.containsAll(c2688oE2))) {
                throw new AssertionError();
            }
        }
        return Collections.singletonList(c2688oE);
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "VerifyMultiClassPolicyAlwaysSatisfied(" + this.b.e() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !com.android.tools.r8.utils.A1.b() || this.b.j();
    }
}
