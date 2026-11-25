package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.MissingClassInfo;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.references.ClassReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class h extends j implements MissingClassInfo {
    private final ClassReference b;

    private h(ClassReference classReference, AbstractC1639am abstractC1639am) {
        super(abstractC1639am);
        this.b = classReference;
    }

    public static g a() {
        return new g();
    }

    @Override // com.android.tools.r8.diagnostic.MissingClassInfo
    public final ClassReference getClassReference() {
        return this.b;
    }
}
