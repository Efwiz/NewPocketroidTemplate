package org.jetbrains.kotlin.contracts.description.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanVariableReference;", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;)V", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BooleanVariableReference extends VariableReference implements BooleanExpression {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BooleanVariableReference(ParameterDescriptor descriptor) {
        super(descriptor);
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.expressions.VariableReference, org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitBooleanVariableReference(this, data);
    }
}
