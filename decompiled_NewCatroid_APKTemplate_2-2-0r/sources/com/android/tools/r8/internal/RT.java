package com.android.tools.r8.internal;

import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.retrace.RetracedFieldReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class RT implements RetracedFieldReference {
    private RT() {
    }

    static PT a(FieldReference fieldReference) {
        return new PT(fieldReference);
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference
    public final boolean isKnown() {
        return !isUnknown();
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference
    public boolean isUnknown() {
        return !(this instanceof PT);
    }

    static QT a(AbstractC1070Ih abstractC1070Ih) {
        return new QT(abstractC1070Ih);
    }
}
