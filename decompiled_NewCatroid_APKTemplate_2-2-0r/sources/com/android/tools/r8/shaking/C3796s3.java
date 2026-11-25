package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC1639am;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.s3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3796s3 extends AbstractC3815v3 {
    private final O3 c;

    private C3796s3(O3 o3) {
        super();
        this.c = o3;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    public final boolean a(String str) {
        this.c.a(str);
        return true;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    protected final AbstractC3815v3 b() {
        return new C3796s3(this.c.f());
    }

    public final String toString() {
        return "*";
    }

    @Override // com.android.tools.r8.shaking.AbstractC3815v3
    protected final Iterable a() {
        return AbstractC1639am.c(this.c);
    }
}
