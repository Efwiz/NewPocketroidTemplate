package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J5\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\f0\u000e2\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/ConditionalEffectDeclaration;", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "effect", "condition", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "(Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;)V", "getCondition", "()Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "getEffect", "()Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionalEffectDeclaration implements EffectDeclaration {
    private final BooleanExpression condition;
    private final EffectDeclaration effect;

    public ConditionalEffectDeclaration(EffectDeclaration effect, BooleanExpression condition) {
        Intrinsics.checkNotNullParameter(effect, "effect");
        Intrinsics.checkNotNullParameter(condition, "condition");
        this.effect = effect;
        this.condition = condition;
    }

    public final BooleanExpression getCondition() {
        return this.condition;
    }

    public final EffectDeclaration getEffect() {
        return this.effect;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.EffectDeclaration, org.jetbrains.kotlin.contracts.description.ContractDescriptionElement
    public <R, D> R accept(ContractDescriptionVisitor<? extends R, ? super D> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitConditionalEffectDeclaration(this, data);
    }
}
