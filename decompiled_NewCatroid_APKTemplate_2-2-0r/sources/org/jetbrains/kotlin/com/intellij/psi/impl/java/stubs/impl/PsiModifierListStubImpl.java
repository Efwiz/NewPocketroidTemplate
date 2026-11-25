package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiModifierListStubImpl extends StubBase<PsiModifierList> implements PsiModifierListStub {
    private final int myMask;

    public PsiModifierListStubImpl(StubElement stubElement, int i) {
        super(stubElement, JavaStubElementTypes.MODIFIER_LIST);
        this.myMask = i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub
    public int getModifiersMask() {
        return this.myMask;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiModifierListStub[mask=" + getModifiersMask() + "]";
    }
}
