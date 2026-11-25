package org.jetbrains.kotlin.contracts.model.functors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.contracts.model.AbstractFunctor;
import org.jetbrains.kotlin.contracts.model.Computation;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESTypeSubstitution;
import org.jetbrains.kotlin.contracts.model.SimpleEffect;
import org.jetbrains.kotlin.contracts.model.structure.ESReturns;
import org.jetbrains.kotlin.contracts.model.structure.ValuesKt;
import org.jetbrains.kotlin.contracts.model.visitors.Reducer;

/* JADX INFO: compiled from: AbstractUnaryFunctor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\r\u001a\u00020\u0007J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004H$¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/functors/AbstractUnaryFunctor;", "Lorg/jetbrains/kotlin/contracts/model/AbstractFunctor;", "()V", "doInvocation", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "arguments", "Lorg/jetbrains/kotlin/contracts/model/Computation;", "typeSubstitution", "Lorg/jetbrains/kotlin/contracts/model/ESTypeSubstitution;", "reducer", "Lorg/jetbrains/kotlin/contracts/model/visitors/Reducer;", "invokeWithArguments", ParameterDescription.NAME_PREFIX, "invokeWithReturningEffects", "Lorg/jetbrains/kotlin/contracts/model/ConditionalEffect;", SchemaSymbols.ATTVAL_LIST, "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractUnaryFunctor extends AbstractFunctor {
    protected abstract List<ConditionalEffect> invokeWithReturningEffects(List<ConditionalEffect> list);

    @Override // org.jetbrains.kotlin.contracts.model.AbstractFunctor
    protected List<ESEffect> doInvocation(List<? extends Computation> arguments, ESTypeSubstitution typeSubstitution, Reducer reducer) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(reducer, "reducer");
        arguments.size();
        return invokeWithArguments(arguments.get(0));
    }

    public final List<ESEffect> invokeWithArguments(Computation arg) {
        Intrinsics.checkNotNullParameter(arg, "arg");
        List<ESEffect> effects = arg.getEffects();
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
        return CollectionsKt.plus((Collection) CollectionsKt.minus((Iterable) arg.getEffects(), (Iterable) arrayList3), (Iterable) invokeWithReturningEffects(arrayList3));
    }
}
