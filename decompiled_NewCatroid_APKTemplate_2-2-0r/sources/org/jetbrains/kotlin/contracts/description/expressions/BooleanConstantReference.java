package org.jetbrains.kotlin.contracts.description.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \r2\u00020\u00012\u00020\u0002:\u0001\rB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\n2\u0006\u0010\u000b\u001a\u0002H\bH\u0016¢\u0006\u0002\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "name", "", "(Ljava/lang/String;)V", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BooleanConstantReference extends ConstantReference implements BooleanExpression {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final BooleanConstantReference TRUE = new BooleanConstantReference("TRUE");
    private static final BooleanConstantReference FALSE = new BooleanConstantReference("FALSE");

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BooleanConstantReference(String name2) {
        super(name2);
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.expressions.ConstantReference, org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitBooleanConstantDescriptor(this, data);
    }

    /* JADX INFO: compiled from: Values.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference$Companion;", "", "()V", "FALSE", "Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;", "getFALSE", "()Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;", "TRUE", "getTRUE", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BooleanConstantReference getTRUE() {
            return BooleanConstantReference.TRUE;
        }

        public final BooleanConstantReference getFALSE() {
            return BooleanConstantReference.FALSE;
        }
    }
}
