package org.jetbrains.kotlin.contracts.model.functors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.ValuesKt;

/* JADX INFO: compiled from: NotFunctor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/functors/NotFunctor;", "Lorg/jetbrains/kotlin/contracts/model/functors/AbstractUnaryFunctor;", "()V", "invokeWithReturningEffects", "", "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", SchemaSymbols.ATTVAL_LIST, "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotFunctor extends AbstractUnaryFunctor {
    @Override // org.jetbrains.kotlin.contracts.model.functors.AbstractUnaryFunctor
    protected List<ConditionalEffect> invokeWithReturningEffects(List<ConditionalEffect> list) {
        ConditionalEffect conditionalEffect;
        Intrinsics.checkNotNullParameter(list, "list");
        ArrayList arrayList = new ArrayList();
        for (ConditionalEffect conditionalEffect2 : list) {
            SimpleEffect simpleEffect = conditionalEffect2.getSimpleEffect();
            Intrinsics.checkNotNull(simpleEffect, "null cannot be cast to non-null type org.jetbrains.kotlin.contracts.model.structure.ESReturns");
            ESValue value = ((ESReturns) simpleEffect).getValue();
            if (ValuesKt.isTrue(value)) {
                conditionalEffect = new ConditionalEffect(conditionalEffect2.getCondition(), new ESReturns(ESConstants.INSTANCE.getFalseValue()));
            } else {
                conditionalEffect = ValuesKt.isFalse(value) ? new ConditionalEffect(conditionalEffect2.getCondition(), new ESReturns(ESConstants.INSTANCE.getTrueValue())) : null;
            }
            if (conditionalEffect != null) {
                arrayList.add(conditionalEffect);
            }
        }
        return arrayList;
    }
}
