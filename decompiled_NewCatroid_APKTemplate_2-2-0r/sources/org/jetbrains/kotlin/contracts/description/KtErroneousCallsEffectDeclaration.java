package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: KtEffects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0001¢\u0006\u0002\u0010\u0007JA\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0002\u0010\u0010\"\u0004\b\u0003\u0010\u00112\u001e\u0010\u0012\u001a\u001a\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015R\u0013\u0010\u0006\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtErroneousCallsEffectDeclaration;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtCallsEffectDeclaration;", "valueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "diagnostic", "(Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;Ljava/lang/Object;)V", "getDiagnostic", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "erroneous", "", "getErroneous", "()Z", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtErroneousCallsEffectDeclaration<Type, Diagnostic> extends KtCallsEffectDeclaration<Type, Diagnostic> {
    private final Diagnostic diagnostic;

    @Override // org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return true;
    }

    public final Diagnostic getDiagnostic() {
        return this.diagnostic;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtErroneousCallsEffectDeclaration(KtValueParameterReference<Type, Diagnostic> valueParameterReference, Diagnostic diagnostic) {
        super(valueParameterReference, EventOccurrencesRange.UNKNOWN);
        Intrinsics.checkNotNullParameter(valueParameterReference, "valueParameterReference");
        this.diagnostic = diagnostic;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration, org.jetbrains.kotlin.contracts.description.KtEffectDeclaration, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitErroneousCallsEffectDeclaration(this, data);
    }
}
