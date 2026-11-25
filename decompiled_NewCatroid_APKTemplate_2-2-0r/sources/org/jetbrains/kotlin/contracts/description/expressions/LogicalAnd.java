package org.jetbrains.kotlin.contracts.description.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;

/* JADX INFO: compiled from: LogicalCombinators.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J5\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH\u0016¢\u0006\u0002\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "left", "right", "(Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;)V", "getLeft", "()Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "getRight", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LogicalAnd implements BooleanExpression {
    private final BooleanExpression left;
    private final BooleanExpression right;

    public LogicalAnd(BooleanExpression left, BooleanExpression right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        this.left = left;
        this.right = right;
    }

    public final BooleanExpression getLeft() {
        return this.left;
    }

    public final BooleanExpression getRight() {
        return this.right;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.BooleanExpression, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitLogicalAnd(this, data);
    }
}
