package org.jetbrains.kotlin.contracts.model.functors;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;
import org.jetbrains.kotlin.contracts.model.structure.ESAnd;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESOr;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.ValuesKt;

/* JADX INFO: compiled from: AndFunctor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J*\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004H\u0014¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/functors/AndFunctor;", "Lorg/jetbrains/kotlin/contracts/model/functors/AbstractBinaryFunctor;", "()V", "invokeWithConstant", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "computation", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "constant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "invokeWithReturningEffects", "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", "left", "right", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AndFunctor extends AbstractBinaryFunctor {
    @Override // org.jetbrains.kotlin.contracts.model.functors.AbstractBinaryFunctor
    protected List<ESEffect> invokeWithConstant(Computation computation, ESConstant constant) {
        Intrinsics.checkNotNullParameter(computation, "computation");
        Intrinsics.checkNotNullParameter(constant, "constant");
        ESConstant eSConstant = constant;
        if (ValuesKt.isTrue(eSConstant)) {
            return computation.getEffects();
        }
        return ValuesKt.isFalse(eSConstant) ? CollectionsKt.emptyList() : computation.getEffects();
    }

    @Override // org.jetbrains.kotlin.contracts.model.functors.AbstractBinaryFunctor
    protected List<ConditionalEffect> invokeWithReturningEffects(List<ConditionalEffect> left, List<ConditionalEffect> right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        List<ConditionalEffect> list = left;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            SimpleEffect simpleEffect = ((ConditionalEffect) obj).getSimpleEffect();
            if ((simpleEffect instanceof ESReturns) && ValuesKt.isTrue(((ESReturns) simpleEffect).getValue())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            SimpleEffect simpleEffect2 = ((ConditionalEffect) obj2).getSimpleEffect();
            if ((simpleEffect2 instanceof ESReturns) && ValuesKt.isFalse(((ESReturns) simpleEffect2).getValue())) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = arrayList3;
        List<ConditionalEffect> list2 = right;
        ArrayList arrayList5 = new ArrayList();
        for (Object obj3 : list2) {
            SimpleEffect simpleEffect3 = ((ConditionalEffect) obj3).getSimpleEffect();
            if ((simpleEffect3 instanceof ESReturns) && ValuesKt.isTrue(((ESReturns) simpleEffect3).getValue())) {
                arrayList5.add(obj3);
            }
        }
        ArrayList arrayList6 = arrayList5;
        ArrayList arrayList7 = new ArrayList();
        for (Object obj4 : list2) {
            SimpleEffect simpleEffect4 = ((ConditionalEffect) obj4).getSimpleEffect();
            if ((simpleEffect4 instanceof ESReturns) && ValuesKt.isFalse(((ESReturns) simpleEffect4).getValue())) {
                arrayList7.add(obj4);
            }
        }
        ESExpression eSExpressionFoldConditionsWithOr = foldConditionsWithOr(arrayList2);
        ESExpression eSExpressionFoldConditionsWithOr2 = foldConditionsWithOr(arrayList6);
        ESExpression eSExpressionFoldConditionsWithOr3 = foldConditionsWithOr(arrayList4);
        ESExpression eSExpressionFoldConditionsWithOr4 = foldConditionsWithOr(arrayList7);
        ESExpression eSExpression = (ESExpression) FunctorsUtilsKt.applyWithDefault(eSExpressionFoldConditionsWithOr, eSExpressionFoldConditionsWithOr2, new Function2<ESExpression, ESExpression, ESExpression>() { // from class: org.jetbrains.kotlin.contracts.model.functors.AndFunctor$invokeWithReturningEffects$conditionWhenTrue$1
            @Override // kotlin.jvm.functions.Function2
            public final ESExpression invoke(ESExpression l, ESExpression r) {
                Intrinsics.checkNotNullParameter(l, "l");
                Intrinsics.checkNotNullParameter(r, "r");
                return new ESAnd(l, r);
            }
        });
        ESOr eSOr = (ESOr) FunctorsUtilsKt.applyIfBothNotNull(eSExpressionFoldConditionsWithOr3, eSExpressionFoldConditionsWithOr4, new Function2<ESExpression, ESExpression, ESOr>() { // from class: org.jetbrains.kotlin.contracts.model.functors.AndFunctor$invokeWithReturningEffects$conditionWhenFalse$1
            @Override // kotlin.jvm.functions.Function2
            public final ESOr invoke(ESExpression l, ESExpression r) {
                Intrinsics.checkNotNullParameter(l, "l");
                Intrinsics.checkNotNullParameter(r, "r");
                return new ESOr(l, r);
            }
        });
        ArrayList arrayList8 = new ArrayList();
        if (eSExpression != null) {
            arrayList8.add(new ConditionalEffect(eSExpression, new ESReturns(ESConstants.INSTANCE.getTrueValue())));
        }
        if (eSOr != null) {
            arrayList8.add(new ConditionalEffect(eSOr, new ESReturns(ESConstants.INSTANCE.getFalseValue())));
        }
        return arrayList8;
    }
}
