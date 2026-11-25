package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.MissingFieldInfo;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.references.FieldReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class o extends j implements MissingFieldInfo {
    private final FieldReference b;

    private o(FieldReference fieldReference, AbstractC1639am abstractC1639am) {
        super(abstractC1639am);
        this.b = fieldReference;
    }

    public static n a() {
        return new n();
    }

    @Override // com.android.tools.r8.diagnostic.MissingFieldInfo
    public final FieldReference getFieldReference() {
        return this.b;
    }
}
