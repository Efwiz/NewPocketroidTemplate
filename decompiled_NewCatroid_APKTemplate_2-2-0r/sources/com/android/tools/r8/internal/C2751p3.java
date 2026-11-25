package com.android.tools.r8.internal;

import com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder;
import com.android.tools.r8.references.MethodReference;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2751p3 implements ArtProfileMethodRuleBuilder {
    private MethodReference a;

    C2751p3() {
        C3.b();
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder
    public final ArtProfileMethodRuleBuilder setMethodReference(MethodReference methodReference) {
        this.a = methodReference;
        return this;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileMethodRuleBuilder
    public final ArtProfileMethodRuleBuilder setMethodRuleInfo(Consumer consumer) {
        C3.a aVarA = C3.a();
        consumer.accept(aVarA);
        aVarA.a();
        return this;
    }
}
