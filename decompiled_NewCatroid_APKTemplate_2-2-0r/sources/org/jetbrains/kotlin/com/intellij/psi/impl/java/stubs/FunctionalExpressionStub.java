package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class FunctionalExpressionStub<T extends PsiFunctionalExpression> extends StubBase<T> {
    private final String myPresentableText;

    protected FunctionalExpressionStub(StubElement stubElement, IStubElementType iStubElementType, String str) {
        super(stubElement, iStubElementType);
        this.myPresentableText = str;
    }

    public String getPresentableText() {
        return this.myPresentableText;
    }
}
