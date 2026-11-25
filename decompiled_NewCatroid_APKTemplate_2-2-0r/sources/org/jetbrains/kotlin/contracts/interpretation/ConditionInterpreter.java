package org.jetbrains.kotlin.contracts.interpretation;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanVariableReference;
import org.jetbrains.kotlin.contracts.description.expressions.IsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.expressions.IsNullPredicate;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalAnd;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalNot;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalOr;
import org.jetbrains.kotlin.contracts.model.ESExpression;
import org.jetbrains.kotlin.contracts.model.functors.IsFunctor;
import org.jetbrains.kotlin.contracts.model.structure.ESAnd;
import org.jetbrains.kotlin.contracts.model.structure.ESConstants;
import org.jetbrains.kotlin.contracts.model.structure.ESEqual;
import org.jetbrains.kotlin.contracts.model.structure.ESIs;
import org.jetbrains.kotlin.contracts.model.structure.ESNot;
import org.jetbrains.kotlin.contracts.model.structure.ESOr;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;
import org.jetbrains.kotlin.contracts.model.structure.TypesKt;

/* JADX INFO: compiled from: ConditionInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u000bJ\u001f\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u000fJ\u001f\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0013J\u001f\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0017J\u001f\u0010\u0018\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001bJ\u001f\u0010\u001c\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u001fJ\u001f\u0010 \u001a\u0004\u0018\u00010\u00022\u0006\u0010!\u001a\u00020\"2\u0006\u0010\n\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010#R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/contracts/interpretation/ConditionInterpreter;", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "", "dispatcher", "Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;", "(Lorg/jetbrains/kotlin/contracts/interpretation/ContractInterpretationDispatcher;)V", "visitBooleanConstantDescriptor", "booleanConstantDescriptor", "Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;", "data", "(Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "visitBooleanVariableReference", "booleanVariableReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanVariableReference;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanVariableReference;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "visitIsInstancePredicate", "isInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "visitIsNullPredicate", "isNullPredicate", "Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "visitLogicalAnd", "logicalAnd", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "visitLogicalNot", "logicalNot", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalNot;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalNot;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "visitLogicalOr", "logicalOr", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalOr;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalOr;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/model/ESExpression;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionInterpreter implements ContractDescriptionVisitor<ESExpression, Unit> {
    private final ContractInterpretationDispatcher dispatcher;

    public ConditionInterpreter(ContractInterpretationDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.dispatcher = dispatcher;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitLogicalOr(LogicalOr logicalOr, Unit data) {
        ESExpression eSExpression;
        Intrinsics.checkNotNullParameter(logicalOr, "logicalOr");
        Intrinsics.checkNotNullParameter(data, "data");
        ConditionInterpreter conditionInterpreter = this;
        ESExpression eSExpression2 = (ESExpression) logicalOr.getLeft().accept(conditionInterpreter, data);
        if (eSExpression2 == null || (eSExpression = (ESExpression) logicalOr.getRight().accept(conditionInterpreter, data)) == null) {
            return null;
        }
        return new ESOr(eSExpression2, eSExpression);
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitLogicalAnd(LogicalAnd logicalAnd, Unit data) {
        ESExpression eSExpression;
        Intrinsics.checkNotNullParameter(logicalAnd, "logicalAnd");
        Intrinsics.checkNotNullParameter(data, "data");
        ConditionInterpreter conditionInterpreter = this;
        ESExpression eSExpression2 = (ESExpression) logicalAnd.getLeft().accept(conditionInterpreter, data);
        if (eSExpression2 == null || (eSExpression = (ESExpression) logicalAnd.getRight().accept(conditionInterpreter, data)) == null) {
            return null;
        }
        return new ESAnd(eSExpression2, eSExpression);
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitLogicalNot(LogicalNot logicalNot, Unit data) {
        Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
        Intrinsics.checkNotNullParameter(data, "data");
        ESExpression eSExpression = (ESExpression) logicalNot.getArg().accept(this, data);
        if (eSExpression == null) {
            return null;
        }
        return new ESNot(eSExpression);
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitIsInstancePredicate(IsInstancePredicate isInstancePredicate, Unit data) {
        Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
        Intrinsics.checkNotNullParameter(data, "data");
        ESVariable eSVariableInterpretVariable$resolution = this.dispatcher.interpretVariable$resolution(isInstancePredicate.getArg());
        if (eSVariableInterpretVariable$resolution == null) {
            return null;
        }
        return new ESIs(eSVariableInterpretVariable$resolution, new IsFunctor(TypesKt.toESType(isInstancePredicate.getType()), isInstancePredicate.getIsNegated()));
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitIsNullPredicate(IsNullPredicate isNullPredicate, Unit data) {
        Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
        Intrinsics.checkNotNullParameter(data, "data");
        ESVariable eSVariableInterpretVariable$resolution = this.dispatcher.interpretVariable$resolution(isNullPredicate.getArg());
        if (eSVariableInterpretVariable$resolution == null) {
            return null;
        }
        return new ESEqual(eSVariableInterpretVariable$resolution, ESConstants.INSTANCE.getNullValue(), isNullPredicate.getIsNegated());
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitBooleanConstantDescriptor(BooleanConstantReference booleanConstantDescriptor, Unit data) {
        Intrinsics.checkNotNullParameter(booleanConstantDescriptor, "booleanConstantDescriptor");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.dispatcher.interpretConstant$resolution(booleanConstantDescriptor);
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public ESExpression visitBooleanVariableReference(BooleanVariableReference booleanVariableReference, Unit data) {
        Intrinsics.checkNotNullParameter(booleanVariableReference, "booleanVariableReference");
        Intrinsics.checkNotNullParameter(data, "data");
        return this.dispatcher.interpretVariable$resolution(booleanVariableReference);
    }
}
