package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.utils.C3882d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class F3 implements ArtProfileClassRuleBuilder {
    final /* synthetic */ H3 a;

    F3(H3 h3) {
        this.a = h3;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder
    public final ArtProfileClassRuleBuilder setClassReference(ClassReference classReference) {
        I3.a(this.a.a, C3882d0.a(classReference));
        return this;
    }
}
