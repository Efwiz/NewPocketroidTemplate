package org.jetbrains.kotlin.psi.stubs.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.psi.KtContractEffect;
import org.jetbrains.kotlin.psi.stubs.KotlinContractEffectStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinContractEffectStubImpl;

/* JADX INFO: compiled from: KtContractEffectElementType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00032\f\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\fH\u0016J \u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u0014H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/elements/KtContractEffectElementType;", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtStubElementType;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinContractEffectStub;", "Lorg/jetbrains/kotlin/psi/KtContractEffect;", "debugName", "", "psiClass", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)V", "createStub", "psi", "parentStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "deserialize", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "serialize", "", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtContractEffectElementType extends KtStubElementType<KotlinContractEffectStub, KtContractEffect> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinContractEffectStub stub, StubOutputStream dataStream) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public /* bridge */ /* synthetic */ StubElement createStub(PsiElement psiElement, StubElement stubElement) {
        return createStub((KtContractEffect) psiElement, (StubElement<?>) stubElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtContractEffectElementType(String debugName, Class<KtContractEffect> psiClass) {
        super(debugName, psiClass, KotlinContractEffectStub.class);
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(psiClass, "psiClass");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinContractEffectStub deserialize(StubInputStream dataStream, StubElement<PsiElement> parentStub) {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        return new KotlinContractEffectStubImpl(parentStub, this);
    }

    public KotlinContractEffectStub createStub(KtContractEffect psi, StubElement<?> parentStub) {
        Intrinsics.checkNotNullParameter(psi, "psi");
        return new KotlinContractEffectStubImpl(parentStub, this);
    }
}
