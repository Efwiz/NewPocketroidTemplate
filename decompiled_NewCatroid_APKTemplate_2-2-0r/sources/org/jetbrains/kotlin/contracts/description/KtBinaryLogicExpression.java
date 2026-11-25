package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtLogicalCombinators.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B5\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJA\u0010\u0012\u001a\u0002H\u0013\"\u0004\b\u0002\u0010\u0013\"\u0004\b\u0003\u0010\u00142\u001e\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00162\u0006\u0010\u0017\u001a\u0002H\u0014H\u0016¢\u0006\u0002\u0010\u0018R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtBinaryLogicExpression;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "left", "right", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;", "(Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;)V", "erroneous", "", "getErroneous", "()Z", "getKind", "()Lorg/jetbrains/kotlin/contracts/description/LogicOperationKind;", "getLeft", "()Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "getRight", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtBinaryLogicExpression<Type, Diagnostic> implements KtBooleanExpression<Type, Diagnostic> {
    private final LogicOperationKind kind;
    private final KtBooleanExpression<Type, Diagnostic> left;
    private final KtBooleanExpression<Type, Diagnostic> right;

    public KtBinaryLogicExpression(KtBooleanExpression<Type, Diagnostic> left, KtBooleanExpression<Type, Diagnostic> right, LogicOperationKind kind) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.left = left;
        this.right = right;
        this.kind = kind;
    }

    public final KtBooleanExpression<Type, Diagnostic> getLeft() {
        return this.left;
    }

    public final KtBooleanExpression<Type, Diagnostic> getRight() {
        return this.right;
    }

    public final LogicOperationKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return this.left.getErroneous() || this.right.getErroneous();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtBooleanExpression, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitLogicalBinaryOperationContractExpression(this, data);
    }
}
