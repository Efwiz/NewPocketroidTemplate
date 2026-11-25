package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.MissingDefinitionInfo;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class p extends i {
    private MethodReference b;

    private p() {
    }

    @Override // com.android.tools.r8.diagnostic.internal.i
    final i a() {
        return this;
    }

    public final MissingDefinitionInfo b() {
        return new q(this.b, this.a.a());
    }

    public final p a(MethodReference methodReference) {
        this.b = methodReference;
        return this;
    }
}
