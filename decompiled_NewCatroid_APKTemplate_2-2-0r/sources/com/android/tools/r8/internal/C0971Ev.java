package com.android.tools.r8.internal;

import javax.lang.model.type.DeclaredType;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ev, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0971Ev extends AbstractC0997Fv {
    private DeclaredType a;

    private C0971Ev() {
        super();
        this.a = null;
    }

    public final Object visitDeclared(DeclaredType declaredType, Object obj) {
        this.a = declaredType;
        return this;
    }
}
