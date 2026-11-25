package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;

/* JADX INFO: loaded from: classes3.dex */
public interface MethodSignature {
    public static final MethodSignature[] EMPTY_ARRAY = new MethodSignature[0];

    String getName();

    PsiType[] getParameterTypes();

    PsiSubstitutor getSubstitutor();

    PsiTypeParameter[] getTypeParameters();

    boolean isConstructor();

    boolean isRaw();
}
