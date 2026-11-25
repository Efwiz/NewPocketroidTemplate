package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiParameterStub extends NamedStub<PsiParameter> {
    PsiModifierListStub getModList();

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    String getName();

    TypeInfo getType();

    boolean isParameterTypeEllipsis();
}
