package org.jetbrains.kotlin.contracts.description.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;

/* JADX INFO: compiled from: Predicates.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J5\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\f0\u000e2\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0010J\u0006\u0010\u0011\u001a\u00020\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "isNegated", "", "(Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;Z)V", "getArg", "()Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "()Z", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "negated", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IsNullPredicate implements BooleanExpression {
    private final VariableReference arg;
    private final boolean isNegated;

    public IsNullPredicate(VariableReference arg, boolean z) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        this.arg = arg;
        this.isNegated = z;
    }

    public final VariableReference getArg() {
        return this.arg;
    }

    /* JADX INFO: renamed from: isNegated, reason: from getter */
    public final boolean getIsNegated() {
        return this.isNegated;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.BooleanExpression, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitIsNullPredicate(this, data);
    }

    public final IsNullPredicate negated() {
        return new IsNullPredicate(this.arg, !this.isNegated);
    }
}
