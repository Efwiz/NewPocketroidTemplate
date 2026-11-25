package com.android.tools.r8.diagnostic;

import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface MissingDefinitionInfo {
    default MissingClassInfo asMissingClass() {
        return null;
    }

    default MissingFieldInfo asMissingField() {
        return null;
    }

    default MissingMethodInfo asMissingMethod() {
        return null;
    }

    String getDiagnosticMessage();

    Collection<DefinitionContext> getReferencedFromContexts();

    default boolean isMissingClass() {
        return false;
    }

    default boolean isMissingField() {
        return false;
    }

    default boolean isMissingMethod() {
        return false;
    }
}
