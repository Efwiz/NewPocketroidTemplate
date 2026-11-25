package org.jetbrains.kotlin.contracts.interpretation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.ConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;

/* JADX INFO: compiled from: EffectsInterpreters.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/contracts/interpretation/ConditionalEffectInterpreter;", "", "dispatcher", "Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;", "(Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;)V", "interpret", "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", "conditionalEffectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/ConditionalEffectDeclaration;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionalEffectInterpreter {
    private final ContractInterpretationDispatcher dispatcher;

    public ConditionalEffectInterpreter(ContractInterpretationDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.dispatcher = dispatcher;
    }

    public final ConditionalEffect interpret(ConditionalEffectDeclaration conditionalEffectDeclaration) {
        ESExpression eSExpressionInterpretCondition$resolution;
        Intrinsics.checkNotNullParameter(conditionalEffectDeclaration, "conditionalEffectDeclaration");
        ESEffect eSEffectInterpretEffect$resolution = this.dispatcher.interpretEffect$resolution(conditionalEffectDeclaration.getEffect());
        SimpleEffect simpleEffect = eSEffectInterpretEffect$resolution instanceof SimpleEffect ? (SimpleEffect) eSEffectInterpretEffect$resolution : null;
        if (simpleEffect == null || (eSExpressionInterpretCondition$resolution = this.dispatcher.interpretCondition$resolution(conditionalEffectDeclaration.getCondition())) == null) {
            return null;
        }
        return new ConditionalEffect(eSExpressionInterpretCondition$resolution, simpleEffect);
    }
}
