package com.android.tools.r8.diagnostic;

import com.android.tools.r8.references.ClassReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface DefinitionClassContext extends DefinitionContext {
    @Override // com.android.tools.r8.diagnostic.DefinitionContext
    default DefinitionClassContext asClassContext() {
        return this;
    }

    ClassReference getClassReference();

    @Override // com.android.tools.r8.diagnostic.DefinitionContext
    default boolean isClassContext() {
        return true;
    }
}
