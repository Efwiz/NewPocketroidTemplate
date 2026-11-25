package org.jetbrains.kotlin.contracts.model.functors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.AbstractFunctor;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESTypeSubstitution;
import org.jetbrains.kotlin.contracts.model.ESValue;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESEqual;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.TypesKt;
import org.jetbrains.kotlin.contracts.model.structure.ValuesKt;
import org.jetbrains.kotlin.contracts.model.visitors.Reducer;

/* JADX INFO: compiled from: EqualsFunctor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J,\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\nJ \u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/functors/EqualsFunctor;", "Lorg/jetbrains/kotlin/contracts/model/AbstractFunctor;", "isNegated", "", "(Z)V", "()Z", "doInvocation", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "arguments", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "typeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "equateCallAndConstant", "call", "constant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "equateValues", "left", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "right", "invokeWithArguments", "isSafeToProduceFalse", "leftCall", "leftConstant", "rightConstant", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EqualsFunctor extends AbstractFunctor {
    private final boolean isNegated;

    public EqualsFunctor(boolean z) {
        this.isNegated = z;
    }

    /* JADX INFO: renamed from: isNegated, reason: from getter */
    public final boolean getIsNegated() {
        return this.isNegated;
    }

    @Override // org.jetbrains.kotlin.contracts.model.AbstractFunctor
    protected List<ESEffect> doInvocation(List<? extends Computation> arguments, ESTypeSubstitution typeSubstitution, Reducer reducer) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(reducer, "reducer");
        arguments.size();
        return arguments.size() != 2 ? CollectionsKt.emptyList() : invokeWithArguments(arguments.get(0), arguments.get(1));
    }

    public final List<ESEffect> invokeWithArguments(Computation left, Computation right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        if ((left instanceof ESValue) && (right instanceof ESValue)) {
            return equateValues((ESValue) left, (ESValue) right);
        }
        if (left instanceof ESConstant) {
            return equateCallAndConstant(right, (ESConstant) left);
        }
        if (right instanceof ESConstant) {
            return equateCallAndConstant(left, (ESConstant) right);
        }
        return CollectionsKt.emptyList();
    }

    private final List<ESEffect> equateCallAndConstant(Computation call, ESConstant constant) {
        ArrayList arrayList = new ArrayList();
        for (ESEffect eSEffect : call.getEffects()) {
            if (eSEffect instanceof ConditionalEffect) {
                ConditionalEffect conditionalEffect = (ConditionalEffect) eSEffect;
                if ((conditionalEffect.getSimpleEffect() instanceof ESReturns) && !ValuesKt.isWildcard(((ESReturns) conditionalEffect.getSimpleEffect()).getValue())) {
                    if (Intrinsics.areEqual(((ESReturns) conditionalEffect.getSimpleEffect()).getValue(), constant)) {
                        arrayList.add(new ConditionalEffect(conditionalEffect.getCondition(), new ESReturns(ESConstants.INSTANCE.booleanValue(!this.isNegated))));
                    }
                    if (!Intrinsics.areEqual(((ESReturns) conditionalEffect.getSimpleEffect()).getValue(), constant) && (((ESReturns) conditionalEffect.getSimpleEffect()).getValue() instanceof ESConstant) && isSafeToProduceFalse(call, (ESConstant) ((ESReturns) conditionalEffect.getSimpleEffect()).getValue(), constant)) {
                        arrayList.add(new ConditionalEffect(conditionalEffect.getCondition(), new ESReturns(ESConstants.INSTANCE.booleanValue(this.isNegated))));
                    }
                }
            }
            arrayList.add(eSEffect);
        }
        return arrayList;
    }

    private final boolean isSafeToProduceFalse(Computation leftCall, ESConstant leftConstant, ESConstant rightConstant) {
        if (TypesKt.isBoolean(rightConstant.getType()) && TypesKt.isBoolean(leftCall.getType())) {
            return true;
        }
        return leftConstant.isNullConstant() && rightConstant.isNullConstant();
    }

    private final List<ESEffect> equateValues(ESValue left, ESValue right) {
        return CollectionsKt.listOf((Object[]) new ConditionalEffect[]{new ConditionalEffect(new ESEqual(left, right, this.isNegated), new ESReturns(ESConstants.INSTANCE.getTrueValue())), new ConditionalEffect(new ESEqual(left, right, !this.isNegated), new ESReturns(ESConstants.INSTANCE.getFalseValue()))});
    }
}
