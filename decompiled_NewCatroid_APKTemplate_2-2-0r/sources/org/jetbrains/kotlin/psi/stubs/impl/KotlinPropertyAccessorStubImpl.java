package org.jetbrains.kotlin.psi.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.stubs.KotlinPropertyAccessorStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinPropertyAccessorStubImpl extends KotlinStubBaseImpl<KtPropertyAccessor> implements KotlinPropertyAccessorStub {
    private final boolean hasBlockBody;
    private final boolean hasBody;
    private final boolean isGetter;

    public KotlinPropertyAccessorStubImpl(StubElement stubElement, boolean z, boolean z2, boolean z3) {
        super(stubElement, KtStubElementTypes.PROPERTY_ACCESSOR);
        this.isGetter = z;
        this.hasBody = z2;
        this.hasBlockBody = z3;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyAccessorStub
    public boolean isGetter() {
        return this.isGetter;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyAccessorStub
    public boolean hasBody() {
        return this.hasBody;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinPropertyAccessorStub
    public boolean hasBlockBody() {
        return this.hasBlockBody;
    }
}
