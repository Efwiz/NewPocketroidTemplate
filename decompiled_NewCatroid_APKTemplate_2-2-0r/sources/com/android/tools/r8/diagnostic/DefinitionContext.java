package com.android.tools.r8.diagnostic;

import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface DefinitionContext {
    default DefinitionClassContext asClassContext() {
        return null;
    }

    default DefinitionFieldContext asFieldContext() {
        return null;
    }

    default DefinitionMethodContext asMethodContext() {
        return null;
    }

    Origin getOrigin();

    default boolean isClassContext() {
        return false;
    }

    default boolean isFieldContext() {
        return false;
    }

    default boolean isMethodContext() {
        return false;
    }
}
