package org.jetbrains.kotlin.psi.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeProjectionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KotlinTypeProjectionStubImpl extends KotlinStubBaseImpl<KtTypeProjection> implements KotlinTypeProjectionStub {
    private final int projectionKindOrdinal;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/psi/stubs/impl/KotlinTypeProjectionStubImpl", "getProjectionKind"));
    }

    public KotlinTypeProjectionStubImpl(StubElement stubElement, int i) {
        super(stubElement, KtStubElementTypes.TYPE_PROJECTION);
        this.projectionKindOrdinal = i;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinTypeProjectionStub
    public KtProjectionKind getProjectionKind() {
        KtProjectionKind ktProjectionKind = KtProjectionKind.values()[this.projectionKindOrdinal];
        if (ktProjectionKind == null) {
            $$$reportNull$$$0(0);
        }
        return ktProjectionKind;
    }
}
