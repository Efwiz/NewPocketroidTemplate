package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtEffects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJA\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0002\u0010\u0012\"\u0004\b\u0003\u0010\u00132\u001e\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtCallsEffectDeclaration;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "valueParameterReference", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)V", "erroneous", "", "getErroneous", "()Z", "getKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "getValueParameterReference", "()Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtCallsEffectDeclaration<Type, Diagnostic> extends KtEffectDeclaration<Type, Diagnostic> {
    private final EventOccurrencesRange kind;
    private final KtValueParameterReference<Type, Diagnostic> valueParameterReference;

    public final KtValueParameterReference<Type, Diagnostic> getValueParameterReference() {
        return this.valueParameterReference;
    }

    public final EventOccurrencesRange getKind() {
        return this.kind;
    }

    public KtCallsEffectDeclaration(KtValueParameterReference<Type, Diagnostic> valueParameterReference, EventOccurrencesRange kind) {
        Intrinsics.checkNotNullParameter(valueParameterReference, "valueParameterReference");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.valueParameterReference = valueParameterReference;
        this.kind = kind;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return this.valueParameterReference.getErroneous();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtEffectDeclaration, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitCallsEffectDeclaration(this, data);
    }
}
