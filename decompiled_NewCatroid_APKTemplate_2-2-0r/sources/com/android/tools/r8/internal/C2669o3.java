package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder;
import com.android.tools.r8.references.ClassReference;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2669o3 implements ArtProfileClassRuleBuilder {
    private ClassReference a;

    C2669o3() {
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileClassRuleBuilder
    public final ArtProfileClassRuleBuilder setClassReference(ClassReference classReference) {
        this.a = classReference;
        return this;
    }
}
