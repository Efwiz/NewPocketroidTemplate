package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.SyntheticElement;

/* JADX INFO: loaded from: classes3.dex */
public interface LightRecordMember extends PsiElement, SyntheticElement {
    PsiRecordComponent getRecordComponent();
}
