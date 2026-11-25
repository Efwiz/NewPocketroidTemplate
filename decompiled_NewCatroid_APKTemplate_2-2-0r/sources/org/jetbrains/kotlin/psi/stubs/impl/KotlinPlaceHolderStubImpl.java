package org.jetbrains.kotlin.psi.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinPlaceHolderStubImpl<T extends KtElementImplStub<? extends StubElement<?>>> extends KotlinStubBaseImpl<T> implements KotlinPlaceHolderStub<T> {
    public KotlinPlaceHolderStubImpl(StubElement stubElement, IStubElementType iStubElementType) {
        super(stubElement, iStubElementType);
    }
}
