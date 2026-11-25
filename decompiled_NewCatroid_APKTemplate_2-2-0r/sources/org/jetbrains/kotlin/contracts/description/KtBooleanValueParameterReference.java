package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJA\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0002\u0010\u000b\"\u0004\b\u0003\u0010\f2\u001e\u0010\r\u001a\u001a\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000e2\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtBooleanValueParameterReference;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtValueParameterReference;", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "parameterIndex", "", "name", "", "(ILjava/lang/String;)V", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtBooleanValueParameterReference<Type, Diagnostic> extends KtValueParameterReference<Type, Diagnostic> implements KtBooleanExpression<Type, Diagnostic> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtBooleanValueParameterReference(int i, String name2) {
        super(i, name2);
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtValueParameterReference, org.jetbrains.kotlin.contracts.description.KtContractDescriptionValue, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitBooleanValueParameterReference(this, data);
    }
}
