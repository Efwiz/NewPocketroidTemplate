package com.android.tools.r8.diagnostic;

import com.android.tools.r8.references.FieldReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface MissingFieldInfo extends MissingDefinitionInfo {
    @Override // com.android.tools.r8.diagnostic.MissingDefinitionInfo
    default MissingFieldInfo asMissingField() {
        return this;
    }

    FieldReference getFieldReference();

    @Override // com.android.tools.r8.diagnostic.MissingDefinitionInfo
    default boolean isMissingField() {
        return true;
    }
}
