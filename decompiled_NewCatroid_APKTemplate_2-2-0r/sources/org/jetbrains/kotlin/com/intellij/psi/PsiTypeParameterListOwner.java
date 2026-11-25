package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmTypeParametersOwner;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTypeParameterListOwner extends JvmTypeParametersOwner, PsiJvmMember {
    /* JADX INFO: renamed from: getTypeParameterList */
    PsiTypeParameterList mo6093getTypeParameterList();

    @Override // 
    PsiTypeParameter[] getTypeParameters();

    boolean hasTypeParameters();
}
