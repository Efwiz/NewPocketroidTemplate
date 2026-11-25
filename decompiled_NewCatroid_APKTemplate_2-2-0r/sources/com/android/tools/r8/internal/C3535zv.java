package com.android.tools.r8.internal;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3535zv extends AbstractC0939Dv {
    private DeclaredType a;

    private C3535zv() {
        super();
        this.a = null;
    }

    public final Object visitType(TypeMirror typeMirror, Object obj) {
        C0971Ev c0971Ev = new C0971Ev();
        typeMirror.accept(c0971Ev, (Object) null);
        this.a = c0971Ev.a;
        return this;
    }
}
