package org.jetbrains.kotlin.contracts.model.functors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.contracts.model.AbstractFunctor;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.ESTypeSubstitution;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESOr;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.ValuesKt;
import org.jetbrains.kotlin.contracts.model.visitors.Reducer;

/* JADX INFO: compiled from: AbstractBinaryFunctor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0018\u0010\f\u001a\u0004\u0018\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004H\u0004J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0007J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016H$J*\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004H$¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/functors/AbstractBinaryFunctor;", "Lorg/jetbrains/kotlin/contracts/model/AbstractFunctor;", "()V", "doInvocation", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "arguments", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "typeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "foldConditionsWithOr", "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", SchemaSymbols.ATTVAL_LIST, "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", "invokeWithArguments", "left", "right", "invokeWithConstant", "computation", "constant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "invokeWithReturningEffects", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractBinaryFunctor extends AbstractFunctor {
    protected abstract List<ESEffect> invokeWithConstant(Computation computation, ESConstant constant);

    protected abstract List<ConditionalEffect> invokeWithReturningEffects(List<ConditionalEffect> left, List<ConditionalEffect> right);

    @Override // org.jetbrains.kotlin.contracts.model.AbstractFunctor
    protected List<ESEffect> doInvocation(List<? extends Computation> arguments, ESTypeSubstitution typeSubstitution, Reducer reducer) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(reducer, "reducer");
        arguments.size();
        return invokeWithArguments(arguments.get(0), arguments.get(1));
    }

    public final List<ESEffect> invokeWithArguments(Computation left, Computation right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        if (left instanceof ESConstant) {
            return invokeWithConstant(right, (ESConstant) left);
        }
        if (right instanceof ESConstant) {
            return invokeWithConstant(left, (ESConstant) right);
        }
        List<ESEffect> effects = left.getEffects();
        ArrayList arrayList = new ArrayList();
        for (Object obj : effects) {
            if (obj instanceof ConditionalEffect) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            SimpleEffect simpleEffect = ((ConditionalEffect) obj2).getSimpleEffect();
            if ((simpleEffect instanceof ESReturns) && !ValuesKt.isWildcard(((ESReturns) simpleEffect).getValue())) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        List<ESEffect> effects2 = right.getEffects();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : effects2) {
            if (obj3 instanceof ConditionalEffect) {
                arrayList4.add(obj3);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj4 : arrayList4) {
            SimpleEffect simpleEffect2 = ((ConditionalEffect) obj4).getSimpleEffect();
            if ((simpleEffect2 instanceof ESReturns) && !ValuesKt.isWildcard(((ESReturns) simpleEffect2).getValue())) {
                arrayList5.add(obj4);
            }
        }
        ArrayList arrayList6 = arrayList5;
        return CollectionsKt.plus((Collection) CollectionsKt.minus((Iterable) CollectionsKt.plus((Collection) CollectionsKt.minus((Iterable) left.getEffects(), (Iterable) arrayList3), (Iterable) right.getEffects()), (Iterable) arrayList6), (Iterable) invokeWithReturningEffects(arrayList3, arrayList6));
    }

    protected final ESExpression foldConditionsWithOr(List<ConditionalEffect> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        if (list.isEmpty()) {
            return null;
        }
        List<ConditionalEffect> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((ConditionalEffect) it.next()).getCondition());
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = (ESExpression) new ESOr((ESExpression) next, (ESExpression) it2.next());
        }
        return (ESExpression) next;
    }
}
