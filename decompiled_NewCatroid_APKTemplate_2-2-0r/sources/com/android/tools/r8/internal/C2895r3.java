package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder;
import com.android.tools.r8.references.ClassReference;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2895r3 extends J3 implements ArtProfileClassRuleBuilder, InterfaceC1965f1 {
    static final /* synthetic */ boolean c = true;
    private final C0722p1 a;
    private C0767v2 b;

    C2895r3(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    public final C2895r3 a(C0767v2 c0767v2) {
        this.b = c0767v2;
        return this;
    }

    @Override // com.android.tools.r8.internal.J3
    final void b() {
    }

    @Override // com.android.tools.r8.internal.J3
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final C2966s3 d() {
        return new C2966s3(this.b);
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder
    public final ArtProfileClassRuleBuilder setClassReference(ClassReference classReference) {
        if (!c && this.a == null) {
            throw new AssertionError();
        }
        this.b = this.a.e(classReference.getDescriptor());
        return this;
    }
}
