package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public interface ElementClassHint {
    public static final Key<ElementClassHint> KEY = Key.create("ElementClassHint");

    public enum DeclarationKind {
        CLASS,
        PACKAGE,
        METHOD,
        VARIABLE,
        FIELD,
        ENUM_CONST
    }

    boolean shouldProcess(DeclarationKind declarationKind);
}
