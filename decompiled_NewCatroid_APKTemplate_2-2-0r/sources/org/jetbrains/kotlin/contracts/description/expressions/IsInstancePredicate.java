package org.jetbrains.kotlin.contracts.description.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Predicates.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u000e\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010H\u0016¢\u0006\u0002\u0010\u0014J\u0006\u0010\u0015\u001a\u00020\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isNegated", "", "(Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;Lorg/jetbrains/kotlin/types/KotlinType;Z)V", "getArg", "()Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "()Z", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "negated", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IsInstancePredicate implements BooleanExpression {
    private final VariableReference arg;
    private final boolean isNegated;
    private final KotlinType type;

    public IsInstancePredicate(VariableReference arg, KotlinType type, boolean z) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        Intrinsics.checkNotNullParameter(type, "type");
        this.arg = arg;
        this.type = type;
        this.isNegated = z;
    }

    public final VariableReference getArg() {
        return this.arg;
    }

    public final KotlinType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isNegated, reason: from getter */
    public final boolean getIsNegated() {
        return this.isNegated;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.BooleanExpression, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitIsInstancePredicate(this, data);
    }

    public final IsInstancePredicate negated() {
        return new IsInstancePredicate(this.arg, this.type, !this.isNegated);
    }
}
