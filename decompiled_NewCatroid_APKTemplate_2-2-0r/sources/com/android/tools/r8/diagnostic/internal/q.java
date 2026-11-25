package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.MissingMethodInfo;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class q extends j implements MissingMethodInfo {
    private final MethodReference b;

    private q(MethodReference methodReference, AbstractC1639am abstractC1639am) {
        super(abstractC1639am);
        this.b = methodReference;
    }

    public static p a() {
        return new p();
    }

    @Override // com.android.tools.r8.diagnostic.MissingMethodInfo
    public final MethodReference getMethodReference() {
        return this.b;
    }
}
