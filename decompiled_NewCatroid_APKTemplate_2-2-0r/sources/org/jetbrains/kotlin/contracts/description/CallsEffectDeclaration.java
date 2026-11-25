package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J5\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/CallsEffectDeclaration;", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "variableReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;)V", "getKind", "()Lorg/jetbrains/kotlin/contracts/description/EventOccurrencesRange;", "getVariableReference", "()Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallsEffectDeclaration implements EffectDeclaration {
    private final EventOccurrencesRange kind;
    private final VariableReference variableReference;

    public CallsEffectDeclaration(VariableReference variableReference, EventOccurrencesRange kind) {
        Intrinsics.checkNotNullParameter(variableReference, "variableReference");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.variableReference = variableReference;
        this.kind = kind;
    }

    public final EventOccurrencesRange getKind() {
        return this.kind;
    }

    public final VariableReference getVariableReference() {
        return this.variableReference;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.EffectDeclaration, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitCallsEffectDeclaration(this, data);
    }
}
