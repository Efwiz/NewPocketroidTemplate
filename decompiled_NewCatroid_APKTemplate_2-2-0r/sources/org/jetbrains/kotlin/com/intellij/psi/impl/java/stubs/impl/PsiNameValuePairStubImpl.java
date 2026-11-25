package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiNameValuePairStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiNameValuePairStubImpl extends StubBase<PsiNameValuePair> implements PsiNameValuePairStub {
    private final String myName;
    private final String myValue;

    public PsiNameValuePairStubImpl(StubElement stubElement, String str, String str2) {
        super(stubElement, JavaStubElementTypes.NAME_VALUE_PAIR);
        this.myName = str;
        this.myValue = str2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiNameValuePairStub
    public String getName() {
        return this.myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiNameValuePairStub
    public String getValue() {
        return this.myValue;
    }
}
