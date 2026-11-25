package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiRecordComponentStub extends PsiMemberStub<PsiRecordComponent> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    String getName();

    TypeInfo getType();

    boolean isVararg();
}
