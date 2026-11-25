package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiVariableEx extends PsiVariable {
    Object computeConstantValue(Set<PsiVariable> set);
}
