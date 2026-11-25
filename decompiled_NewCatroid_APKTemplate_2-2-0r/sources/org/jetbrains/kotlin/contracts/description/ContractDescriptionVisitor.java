package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanVariableReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue;
import org.jetbrains.kotlin.contracts.description.expressions.IsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.expressions.IsNullPredicate;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalAnd;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalNot;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalOr;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;

/* JADX INFO: compiled from: ContractDescriptionVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00002\u00020\u0003J\u001d\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\bJ\u001d\u0010\t\u001a\u00028\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010J\u001d\u0010\u0011\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0014J\u001d\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0018J\u001d\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001cJ\u001d\u0010\u001d\u001a\u00028\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010 J\u001d\u0010!\u001a\u00028\u00002\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010$J\u001d\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010(J\u001d\u0010)\u001a\u00028\u00002\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010,J\u001d\u0010-\u001a\u00028\u00002\u0006\u0010.\u001a\u00020/2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00100J\u001d\u00101\u001a\u00028\u00002\u0006\u00102\u001a\u0002032\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00104J\u001d\u00105\u001a\u00028\u00002\u0006\u00106\u001a\u0002072\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00108J\u001d\u00109\u001a\u00028\u00002\u0006\u0010:\u001a\u00020;2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010<J\u001d\u0010=\u001a\u00028\u00002\u0006\u0010>\u001a\u00020?2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010@J\u001d\u0010A\u001a\u00028\u00002\u0006\u0010B\u001a\u00020C2\u0006\u0010\u0007\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010Dø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006EÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "R", "D", "", "visitBooleanConstantDescriptor", "booleanConstantDescriptor", "Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;", "data", "(Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanConstantReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitBooleanExpression", "booleanExpression", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "(Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;Ljava/lang/Object;)Ljava/lang/Object;", "visitBooleanVariableReference", "booleanVariableReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanVariableReference;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/BooleanVariableReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitCallsEffectDeclaration", "callsEffect", "Lorg/jetbrains/kotlin/contracts/description/CallsEffectDeclaration;", "(Lorg/jetbrains/kotlin/contracts/description/CallsEffectDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitConditionalEffectDeclaration", "conditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/ConditionalEffectDeclaration;", "(Lorg/jetbrains/kotlin/contracts/description/ConditionalEffectDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitConstantDescriptor", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;Ljava/lang/Object;)Ljava/lang/Object;", "visitContractDescriptionElement", "contractDescriptionElement", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionElement;", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionElement;Ljava/lang/Object;)Ljava/lang/Object;", "visitEffectDeclaration", "effectDeclaration", "Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;", "(Lorg/jetbrains/kotlin/contracts/description/EffectDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitIsInstancePredicate", "isInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;Ljava/lang/Object;)Ljava/lang/Object;", "visitIsNullPredicate", "isNullPredicate", "Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;Ljava/lang/Object;)Ljava/lang/Object;", "visitLogicalAnd", "logicalAnd", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;Ljava/lang/Object;)Ljava/lang/Object;", "visitLogicalNot", "logicalNot", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalNot;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalNot;Ljava/lang/Object;)Ljava/lang/Object;", "visitLogicalOr", "logicalOr", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalOr;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalOr;Ljava/lang/Object;)Ljava/lang/Object;", "visitReturnsEffectDeclaration", "returnsEffect", "Lorg/jetbrains/kotlin/contracts/description/ReturnsEffectDeclaration;", "(Lorg/jetbrains/kotlin/contracts/description/ReturnsEffectDeclaration;Ljava/lang/Object;)Ljava/lang/Object;", "visitValue", "value", "Lorg/jetbrains/kotlin/contracts/description/expressions/ContractDescriptionValue;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/ContractDescriptionValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitVariableReference", "variableReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;Ljava/lang/Object;)Ljava/lang/Object;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ContractDescriptionVisitor<R, D> {
    default R visitContractDescriptionElement(ContractDescriptionElement contractDescriptionElement, D data) {
        Intrinsics.checkNotNullParameter(contractDescriptionElement, "contractDescriptionElement");
        throw new IllegalStateException("Top of hierarchy reached, no overloads were found for element: " + contractDescriptionElement);
    }

    default R visitEffectDeclaration(EffectDeclaration effectDeclaration, D data) {
        Intrinsics.checkNotNullParameter(effectDeclaration, "effectDeclaration");
        return visitContractDescriptionElement(effectDeclaration, data);
    }

    default R visitConditionalEffectDeclaration(ConditionalEffectDeclaration conditionalEffect, D data) {
        Intrinsics.checkNotNullParameter(conditionalEffect, "conditionalEffect");
        return visitEffectDeclaration(conditionalEffect, data);
    }

    default R visitReturnsEffectDeclaration(ReturnsEffectDeclaration returnsEffect, D data) {
        Intrinsics.checkNotNullParameter(returnsEffect, "returnsEffect");
        return visitEffectDeclaration(returnsEffect, data);
    }

    default R visitCallsEffectDeclaration(CallsEffectDeclaration callsEffect, D data) {
        Intrinsics.checkNotNullParameter(callsEffect, "callsEffect");
        return visitEffectDeclaration(callsEffect, data);
    }

    default R visitBooleanExpression(BooleanExpression booleanExpression, D data) {
        Intrinsics.checkNotNullParameter(booleanExpression, "booleanExpression");
        return visitContractDescriptionElement(booleanExpression, data);
    }

    default R visitLogicalOr(LogicalOr logicalOr, D data) {
        Intrinsics.checkNotNullParameter(logicalOr, "logicalOr");
        return visitBooleanExpression(logicalOr, data);
    }

    default R visitLogicalAnd(LogicalAnd logicalAnd, D data) {
        Intrinsics.checkNotNullParameter(logicalAnd, "logicalAnd");
        return visitBooleanExpression(logicalAnd, data);
    }

    default R visitLogicalNot(LogicalNot logicalNot, D data) {
        Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
        return visitBooleanExpression(logicalNot, data);
    }

    default R visitIsInstancePredicate(IsInstancePredicate isInstancePredicate, D data) {
        Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
        return visitBooleanExpression(isInstancePredicate, data);
    }

    default R visitIsNullPredicate(IsNullPredicate isNullPredicate, D data) {
        Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
        return visitBooleanExpression(isNullPredicate, data);
    }

    default R visitValue(ContractDescriptionValue value, D data) {
        Intrinsics.checkNotNullParameter(value, "value");
        return visitContractDescriptionElement(value, data);
    }

    default R visitConstantDescriptor(ConstantReference constantReference, D data) {
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        return visitValue(constantReference, data);
    }

    default R visitBooleanConstantDescriptor(BooleanConstantReference booleanConstantDescriptor, D data) {
        Intrinsics.checkNotNullParameter(booleanConstantDescriptor, "booleanConstantDescriptor");
        return visitConstantDescriptor(booleanConstantDescriptor, data);
    }

    default R visitVariableReference(VariableReference variableReference, D data) {
        Intrinsics.checkNotNullParameter(variableReference, "variableReference");
        return visitValue(variableReference, data);
    }

    default R visitBooleanVariableReference(BooleanVariableReference booleanVariableReference, D data) {
        Intrinsics.checkNotNullParameter(booleanVariableReference, "booleanVariableReference");
        return visitVariableReference(booleanVariableReference, data);
    }
}
