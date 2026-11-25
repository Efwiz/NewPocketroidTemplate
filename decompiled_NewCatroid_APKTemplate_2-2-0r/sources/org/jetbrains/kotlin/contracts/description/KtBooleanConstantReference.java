package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtValues.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007JA\u0010\b\u001a\u0002H\t\"\u0004\b\u0002\u0010\t\"\u0004\b\u0003\u0010\n2\u001e\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\f2\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtBooleanConstantReference;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtConstantReference;", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "name", "", "(Ljava/lang/String;)V", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtBooleanConstantReference<Type, Diagnostic> extends KtConstantReference<Type, Diagnostic> implements KtBooleanExpression<Type, Diagnostic> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtBooleanConstantReference(String name2) {
        super(name2);
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtConstantReference, org.jetbrains.kotlin.contracts.description.KtContractDescriptionValue, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitBooleanConstantDescriptor(this, data);
    }
}
