package org.jetbrains.kotlin.contracts.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ESEffect.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0017\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0016¢\u0006\u0002\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "condition", "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "simpleEffect", "Lorg/jetbrains/kotlin/contracts/model/SimpleEffect;", "(Lorg/jetbrains/kotlin/contracts/model/ESExpression;Lorg/jetbrains/kotlin/contracts/model/SimpleEffect;)V", "getCondition", "()Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "getSimpleEffect", "()Lorg/jetbrains/kotlin/contracts/model/SimpleEffect;", "isImplies", "", "other", "(Lorg/jetbrains/kotlin/contracts/model/ESEffect;)Ljava/lang/Boolean;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionalEffect extends ESEffect {
    private final ESExpression condition;
    private final SimpleEffect simpleEffect;

    @Override // org.jetbrains.kotlin.contracts.model.ESEffect
    public Boolean isImplies(ESEffect other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConditionalEffect(ESExpression condition, SimpleEffect simpleEffect) {
        super(null);
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(simpleEffect, "simpleEffect");
        this.condition = condition;
        this.simpleEffect = simpleEffect;
    }

    public final ESExpression getCondition() {
        return this.condition;
    }

    public final SimpleEffect getSimpleEffect() {
        return this.simpleEffect;
    }
}
