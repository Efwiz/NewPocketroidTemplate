package org.jetbrains.kotlin.contracts.interpretation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.ConditionalEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.ContractDescription;
import org.jetbrains.kotlin.contracts.description.EffectDeclaration;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;
import org.jetbrains.kotlin.contracts.model.ConditionalEffect;
import org.jetbrains.kotlin.contracts.model.ESEffect;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.Functor;
import org.jetbrains.kotlin.contracts.model.functors.SubstitutingFunctor;
import org.jetbrains.kotlin.contracts.model.structure.ESConstant;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;

/* JADX INFO: compiled from: ContractInterpretationDispatcher.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0000¢\u0006\u0002\b\u0014J\u0017\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0000¢\u0006\u0002\b\u0019J\u0017\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0000¢\u0006\u0002\b\u001eJ\u0017\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"H\u0000¢\u0006\u0002\b#R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;", "", "()V", "conditionInterpreter", "Lorg/jetbrains/kotlin/contracts/interpretation/ConditionInterpreter;", "conditionalEffectInterpreter", "Lorg/jetbrains/kotlin/contracts/interpretation/ConditionalEffectInterpreter;", "constantsInterpreter", "Lorg/jetbrains/kotlin/contracts/interpretation/ConstantValuesInterpreter;", "effectsInterpreters", "", "Lorg/jetbrains/kotlin/contracts/interpretation/EffectDeclarationInterpreter;", "convertContractDescriptorToFunctor", "Lorg/jetbrains/kotlin/contracts/model/Functor;", "contractDescription", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "interpretCondition", "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "booleanExpression", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "interpretCondition$resolution", "interpretConstant", "Lorg/jetbrains/kotlin/contracts/model/structure/ESConstant;", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "interpretConstant$resolution", "interpretEffect", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "effectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "interpretEffect$resolution", "interpretVariable", "Lorg/jetbrains/kotlin/contracts/model/structure/ESVariable;", "variableReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "interpretVariable$resolution", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractInterpretationDispatcher {
    private final ConstantValuesInterpreter constantsInterpreter = new ConstantValuesInterpreter();
    private final ConditionInterpreter conditionInterpreter = new ConditionInterpreter(this);
    private final ConditionalEffectInterpreter conditionalEffectInterpreter = new ConditionalEffectInterpreter(this);
    private final List<EffectDeclarationInterpreter> effectsInterpreters = CollectionsKt.listOf((Object[]) new EffectDeclarationInterpreter[]{new ReturnsEffectInterpreter(this), new CallsEffectInterpreter(this)});

    public final Functor convertContractDescriptorToFunctor(ContractDescription contractDescription) {
        ConditionalEffect conditionalEffect;
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        List<EffectDeclaration> effects = contractDescription.getEffects();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(effects, 10));
        for (EffectDeclaration effectDeclaration : effects) {
            if (effectDeclaration instanceof ConditionalEffectDeclaration) {
                ConditionalEffect conditionalEffectInterpret = this.conditionalEffectInterpreter.interpret((ConditionalEffectDeclaration) effectDeclaration);
                if (conditionalEffectInterpret == null) {
                    return null;
                }
                conditionalEffect = conditionalEffectInterpret;
            } else {
                List<EffectDeclarationInterpreter> list = this.effectsInterpreters;
                ArrayList arrayList2 = new ArrayList();
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    ESEffect eSEffectTryInterpret = ((EffectDeclarationInterpreter) it.next()).tryInterpret(effectDeclaration);
                    if (eSEffectTryInterpret != null) {
                        arrayList2.add(eSEffectTryInterpret);
                    }
                }
                conditionalEffect = (ESEffect) CollectionsKt.singleOrNull((List) arrayList2);
                if (conditionalEffect == null) {
                    return null;
                }
            }
            arrayList.add(conditionalEffect);
        }
        return new SubstitutingFunctor(arrayList, contractDescription.getOwnerFunction());
    }

    public final ESEffect interpretEffect$resolution(EffectDeclaration effectDeclaration) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        List<EffectDeclarationInterpreter> list = this.effectsInterpreters;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            ESEffect eSEffectTryInterpret = ((EffectDeclarationInterpreter) it.next()).tryInterpret(effectDeclaration);
            if (eSEffectTryInterpret != null) {
                arrayList.add(eSEffectTryInterpret);
            }
        }
        return (ESEffect) CollectionsKt.singleOrNull((List) arrayList);
    }

    public final ESConstant interpretConstant$resolution(ConstantReference constantReference) {
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        return this.constantsInterpreter.interpretConstant(constantReference);
    }

    public final ESExpression interpretCondition$resolution(BooleanExpression booleanExpression) {
        Intrinsics.checkNotNullParameter(booleanExpression, "booleanExpression");
        return (ESExpression) booleanExpression.accept(this.conditionInterpreter, Unit.INSTANCE);
    }

    public final ESVariable interpretVariable$resolution(VariableReference variableReference) {
        Intrinsics.checkNotNullParameter(variableReference, "variableReference");
        return new ESVariable(variableReference.getDescriptor());
    }
}
