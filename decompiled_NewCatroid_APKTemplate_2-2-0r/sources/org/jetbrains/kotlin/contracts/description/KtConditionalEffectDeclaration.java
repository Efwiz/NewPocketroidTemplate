package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtEffects.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B-\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007JA\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0002\u0010\u0011\"\u0004\b\u0003\u0010\u00122\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/KtConditionalEffectDeclaration;", "Type", "Diagnostic", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "effect", "condition", "Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "(Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;)V", "getCondition", "()Lorg/jetbrains/kotlin/contracts/description/KtBooleanExpression;", "getEffect", "()Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "erroneous", "", "getErroneous", "()Z", "accept", "R", "D", "contractDescriptionVisitor", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;", "data", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtConditionalEffectDeclaration<Type, Diagnostic> extends KtEffectDeclaration<Type, Diagnostic> {
    private final KtBooleanExpression<Type, Diagnostic> condition;
    private final KtEffectDeclaration<Type, Diagnostic> effect;

    public final KtEffectDeclaration<Type, Diagnostic> getEffect() {
        return this.effect;
    }

    public final KtBooleanExpression<Type, Diagnostic> getCondition() {
        return this.condition;
    }

    public KtConditionalEffectDeclaration(KtEffectDeclaration<Type, Diagnostic> effect, KtBooleanExpression<Type, Diagnostic> condition) {
        Intrinsics.checkNotNullParameter(effect, "effect");
        Intrinsics.checkNotNullParameter(condition, "condition");
        this.effect = effect;
        this.condition = condition;
    }

    @Override // org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public boolean getErroneous() {
        return this.effect.getErroneous() || this.condition.getErroneous();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.contracts.description.KtEffectDeclaration, org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement
    public <R, D> R accept(KtContractDescriptionVisitor<? extends R, ? super D, Type, Diagnostic> contractDescriptionVisitor, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionVisitor, "contractDescriptionVisitor");
        return contractDescriptionVisitor.visitConditionalEffectDeclaration(this, data);
    }
}
