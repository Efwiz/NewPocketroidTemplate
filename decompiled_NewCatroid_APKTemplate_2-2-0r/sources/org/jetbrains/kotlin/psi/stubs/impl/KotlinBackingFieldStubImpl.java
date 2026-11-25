package org.jetbrains.kotlin.psi.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtBackingField;
import org.jetbrains.kotlin.psi.stubs.KotlinBackingFieldStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinBackingFieldStubImpl extends KotlinStubBaseImpl<KtBackingField> implements KotlinBackingFieldStub {
    private final boolean hasInitializer;

    public KotlinBackingFieldStubImpl(StubElement<?> stubElement, boolean z) {
        super(stubElement, KtStubElementTypes.BACKING_FIELD);
        this.hasInitializer = z;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinBackingFieldStub
    public boolean hasInitializer() {
        return this.hasInitializer;
    }
}
