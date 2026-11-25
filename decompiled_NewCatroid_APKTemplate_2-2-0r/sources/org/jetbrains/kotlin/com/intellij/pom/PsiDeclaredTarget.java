package org.jetbrains.kotlin.com.intellij.pom;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiTarget;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDeclaredTarget extends PsiTarget {
    TextRange getNameIdentifierRange();
}
