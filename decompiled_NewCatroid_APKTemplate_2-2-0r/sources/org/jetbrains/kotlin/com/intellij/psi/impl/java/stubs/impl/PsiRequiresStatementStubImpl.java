package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRequiresStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PsiRequiresStatementStubImpl extends StubBase<PsiRequiresStatement> implements PsiRequiresStatementStub {
    private final String myModuleName;

    public PsiRequiresStatementStubImpl(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.REQUIRES_STATEMENT);
        this.myModuleName = (String) ObjectUtils.notNull(str, "");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRequiresStatementStub
    public String getModuleName() {
        return this.myModuleName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiRequiresStatementStub:" + this.myModuleName;
    }
}
