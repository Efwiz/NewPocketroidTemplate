package org.jetbrains.kotlin.contracts.description.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;

/* JADX INFO: compiled from: Values.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J5\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\b\"\u0004\b\u0001\u0010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\u000b2\u0006\u0010\f\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "Lorg/jetbrains/kotlin/contracts/description/expressions/ContractDescriptionValue;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ConstantReference implements ContractDescriptionValue {
    private final String name;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConstantReference NULL = new ConstantReference("NULL");
    private static final ConstantReference WILDCARD = new ConstantReference("WILDCARD");
    private static final ConstantReference NOT_NULL = new ConstantReference("NOT_NULL");

    public ConstantReference(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
    }

    public final String getName() {
        return this.name;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitConstantDescriptor(this, data);
    }

    /* JADX INFO: compiled from: Values.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference$Companion;", "", "()V", "NOT_NULL", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "getNOT_NULL", "()Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "NULL", "getNULL", "WILDCARD", "getWILDCARD", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConstantReference getNULL() {
            return ConstantReference.NULL;
        }

        public final ConstantReference getWILDCARD() {
            return ConstantReference.WILDCARD;
        }

        public final ConstantReference getNOT_NULL() {
            return ConstantReference.NOT_NULL;
        }
    }
}
