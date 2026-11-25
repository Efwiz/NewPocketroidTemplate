package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtEffects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006JA\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0002\u0010\u000e\"\u0004\b\u0003\u0010\u000f2\u001e\u0010\u0010\u001a\u001a\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtReturnsEffectDeclaration;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "value", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "(Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;)V", "erroneous", "", "getErroneous", "()Z", "getValue", "()Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtReturnsEffectDeclaration<Type, Diagnostic> extends KtEffectDeclaration<Type, Diagnostic> {
    private final KtConstantReference<Type, Diagnostic> value;

    public final KtConstantReference<Type, Diagnostic> getValue() {
        return this.value;
    }

    public KtReturnsEffectDeclaration(KtConstantReference<Type, Diagnostic> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return this.value.getErroneous();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtEffectDeclaration, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitReturnsEffectDeclaration(this, data);
    }
}
