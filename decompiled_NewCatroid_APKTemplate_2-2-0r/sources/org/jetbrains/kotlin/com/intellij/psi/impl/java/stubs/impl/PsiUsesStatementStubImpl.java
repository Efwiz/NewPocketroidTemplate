package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiUsesStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PsiUsesStatementStubImpl extends StubBase<PsiUsesStatement> implements PsiUsesStatementStub {
    private final String myClassName;

    public PsiUsesStatementStubImpl(StubElement stubElement, String str) {
        super(stubElement, JavaStubElementTypes.USES_STATEMENT);
        this.myClassName = (String) ObjectUtils.notNull(str, "");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiUsesStatementStub
    public String getClassName() {
        return this.myClassName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiUsesStatementStub:" + this.myClassName;
    }
}
