package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtContractEffect;
import org.jetbrains.kotlin.psi.stubs.KotlinContractEffectStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtContractEffectElementType;

/* JADX INFO: compiled from: KotlinContractEffectStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001f\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinContractEffectStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinPlaceHolderStubImpl;", "Lorg/jetbrains/kotlin/psi/KtContractEffect;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinContractEffectStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtContractEffectElementType;", "(Lcom/intellij/psi/stubs/StubElement;Lorg/jetbrains/kotlin/psi/stubs/elements/KtContractEffectElementType;)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinContractEffectStubImpl extends KotlinPlaceHolderStubImpl<KtContractEffect> implements KotlinContractEffectStub {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinContractEffectStubImpl(StubElement<? extends PsiElement> stubElement, KtContractEffectElementType elementType) {
        super(stubElement, elementType);
        Intrinsics.checkNotNullParameter(elementType, "elementType");
    }
}
