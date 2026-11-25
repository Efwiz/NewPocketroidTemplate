package org.jetbrains.kotlin.contracts.description;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.IsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.expressions.IsNullPredicate;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalAnd;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalNot;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalOr;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;

/* JADX INFO: compiled from: ContractDescriptionRenderer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0011\u0012\n\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005¢\u0006\u0002\u0010\u0006J&\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\fH\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002J\u001d\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u0013J\u001d\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u001fJ\u001d\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010#J\u001d\u0010$\u001a\u00020\u00022\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010'J\u001d\u0010(\u001a\u00020\u00022\u0006\u0010)\u001a\u00020*2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010+J\u001d\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010/J\u001d\u00100\u001a\u00020\u00022\u0006\u00101\u001a\u0002022\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u00103J\u001d\u00104\u001a\u00020\u00022\u0006\u00105\u001a\u0002062\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¢\u0006\u0002\u00107J\f\u00108\u001a\u00020\u000e*\u00020\tH\u0002R\u0012\u0010\u0003\u001a\u00060\u0004j\u0002`\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionRenderer;", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionVisitor;", "", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "(Ljava/lang/StringBuilder;)V", "inBracketsIfNecessary", "parent", "Lorg/jetbrains/kotlin/contracts/description/ContractDescriptionElement;", "child", "block", "Lkotlin/Function0;", "needsBrackets", "", "visitCallsEffectDeclaration", "callsEffect", "Lorg/jetbrains/kotlin/contracts/description/CallsEffectDeclaration;", "data", "(Lorg/jetbrains/kotlin/contracts/description/CallsEffectDeclaration;Lkotlin/Unit;)V", "visitConditionalEffectDeclaration", "conditionalEffect", "Lorg/jetbrains/kotlin/contracts/description/ConditionalEffectDeclaration;", "(Lorg/jetbrains/kotlin/contracts/description/ConditionalEffectDeclaration;Lkotlin/Unit;)V", "visitConstantDescriptor", "constantReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/ConstantReference;Lkotlin/Unit;)V", "visitIsInstancePredicate", "isInstancePredicate", "Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/IsInstancePredicate;Lkotlin/Unit;)V", "visitIsNullPredicate", "isNullPredicate", "Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/IsNullPredicate;Lkotlin/Unit;)V", "visitLogicalAnd", "logicalAnd", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalAnd;Lkotlin/Unit;)V", "visitLogicalNot", "logicalNot", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalNot;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalNot;Lkotlin/Unit;)V", "visitLogicalOr", "logicalOr", "Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalOr;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/LogicalOr;Lkotlin/Unit;)V", "visitReturnsEffectDeclaration", "returnsEffect", "Lorg/jetbrains/kotlin/contracts/description/ReturnsEffectDeclaration;", "(Lorg/jetbrains/kotlin/contracts/description/ReturnsEffectDeclaration;Lkotlin/Unit;)V", "visitVariableReference", "variableReference", "Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;", "(Lorg/jetbrains/kotlin/contracts/description/expressions/VariableReference;Lkotlin/Unit;)V", "isAtom", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractDescriptionRenderer implements ContractDescriptionVisitor<Unit, Unit> {
    private final StringBuilder builder;

    public ContractDescriptionRenderer(StringBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        this.builder = builder;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitCallsEffectDeclaration(CallsEffectDeclaration callsEffectDeclaration, Unit unit) {
        visitCallsEffectDeclaration2(callsEffectDeclaration, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitConditionalEffectDeclaration(ConditionalEffectDeclaration conditionalEffectDeclaration, Unit unit) {
        visitConditionalEffectDeclaration2(conditionalEffectDeclaration, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitConstantDescriptor(ConstantReference constantReference, Unit unit) {
        visitConstantDescriptor2(constantReference, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitIsInstancePredicate(IsInstancePredicate isInstancePredicate, Unit unit) {
        visitIsInstancePredicate2(isInstancePredicate, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitIsNullPredicate(IsNullPredicate isNullPredicate, Unit unit) {
        visitIsNullPredicate2(isNullPredicate, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitLogicalAnd(LogicalAnd logicalAnd, Unit unit) {
        visitLogicalAnd2(logicalAnd, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitLogicalNot(LogicalNot logicalNot, Unit unit) {
        visitLogicalNot2(logicalNot, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitLogicalOr(LogicalOr logicalOr, Unit unit) {
        visitLogicalOr2(logicalOr, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitReturnsEffectDeclaration(ReturnsEffectDeclaration returnsEffectDeclaration, Unit unit) {
        visitReturnsEffectDeclaration2(returnsEffectDeclaration, unit);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.contracts.description.ContractDescriptionVisitor
    public /* bridge */ /* synthetic */ Unit visitVariableReference(VariableReference variableReference, Unit unit) {
        visitVariableReference2(variableReference, unit);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConditionalEffectDeclaration, reason: avoid collision after fix types in other method */
    public void visitConditionalEffectDeclaration2(ConditionalEffectDeclaration conditionalEffect, Unit data) {
        Intrinsics.checkNotNullParameter(conditionalEffect, "conditionalEffect");
        Intrinsics.checkNotNullParameter(data, "data");
        ContractDescriptionRenderer contractDescriptionRenderer = this;
        conditionalEffect.getEffect().accept(contractDescriptionRenderer, data);
        this.builder.append(" -> ");
        conditionalEffect.getCondition().accept(contractDescriptionRenderer, data);
    }

    /* JADX INFO: renamed from: visitReturnsEffectDeclaration, reason: avoid collision after fix types in other method */
    public void visitReturnsEffectDeclaration2(ReturnsEffectDeclaration returnsEffect, Unit data) {
        Intrinsics.checkNotNullParameter(returnsEffect, "returnsEffect");
        Intrinsics.checkNotNullParameter(data, "data");
        this.builder.append("Returns(");
        returnsEffect.getValue().accept(this, data);
        this.builder.append(Constant.AFTER_QUTO);
    }

    /* JADX INFO: renamed from: visitCallsEffectDeclaration, reason: avoid collision after fix types in other method */
    public void visitCallsEffectDeclaration2(CallsEffectDeclaration callsEffect, Unit data) {
        Intrinsics.checkNotNullParameter(callsEffect, "callsEffect");
        Intrinsics.checkNotNullParameter(data, "data");
        this.builder.append("CallsInPlace(");
        callsEffect.getVariableReference().accept(this, data);
        this.builder.append(", " + callsEffect.getKind() + ')');
    }

    /* JADX INFO: renamed from: visitLogicalOr, reason: avoid collision after fix types in other method */
    public void visitLogicalOr2(final LogicalOr logicalOr, final Unit data) {
        Intrinsics.checkNotNullParameter(logicalOr, "logicalOr");
        Intrinsics.checkNotNullParameter(data, "data");
        LogicalOr logicalOr2 = logicalOr;
        inBracketsIfNecessary(logicalOr2, logicalOr.getLeft(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.contracts.description.ContractDescriptionRenderer.visitLogicalOr.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                logicalOr.getLeft().accept(this, data);
            }
        });
        this.builder.append(" || ");
        inBracketsIfNecessary(logicalOr2, logicalOr.getRight(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.contracts.description.ContractDescriptionRenderer.visitLogicalOr.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                logicalOr.getRight().accept(this, data);
            }
        });
    }

    /* JADX INFO: renamed from: visitLogicalAnd, reason: avoid collision after fix types in other method */
    public void visitLogicalAnd2(final LogicalAnd logicalAnd, final Unit data) {
        Intrinsics.checkNotNullParameter(logicalAnd, "logicalAnd");
        Intrinsics.checkNotNullParameter(data, "data");
        LogicalAnd logicalAnd2 = logicalAnd;
        inBracketsIfNecessary(logicalAnd2, logicalAnd.getLeft(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.contracts.description.ContractDescriptionRenderer.visitLogicalAnd.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                logicalAnd.getLeft().accept(this, data);
            }
        });
        this.builder.append(" && ");
        inBracketsIfNecessary(logicalAnd2, logicalAnd.getRight(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.contracts.description.ContractDescriptionRenderer.visitLogicalAnd.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                logicalAnd.getRight().accept(this, data);
            }
        });
    }

    /* JADX INFO: renamed from: visitLogicalNot, reason: avoid collision after fix types in other method */
    public void visitLogicalNot2(LogicalNot logicalNot, Unit data) {
        Intrinsics.checkNotNullParameter(logicalNot, "logicalNot");
        Intrinsics.checkNotNullParameter(data, "data");
        inBracketsIfNecessary(logicalNot, logicalNot.getArg(), new Function0<Unit>() { // from class: org.jetbrains.kotlin.contracts.description.ContractDescriptionRenderer.visitLogicalNot.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ContractDescriptionRenderer.this.builder.append("!");
            }
        });
        logicalNot.getArg().accept(this, data);
    }

    /* JADX INFO: renamed from: visitIsInstancePredicate, reason: avoid collision after fix types in other method */
    public void visitIsInstancePredicate2(IsInstancePredicate isInstancePredicate, Unit data) {
        Intrinsics.checkNotNullParameter(isInstancePredicate, "isInstancePredicate");
        Intrinsics.checkNotNullParameter(data, "data");
        isInstancePredicate.getArg().accept(this, data);
        this.builder.append(" " + (isInstancePredicate.getIsNegated() ? "!" : "") + "is " + isInstancePredicate.getType());
    }

    /* JADX INFO: renamed from: visitIsNullPredicate, reason: avoid collision after fix types in other method */
    public void visitIsNullPredicate2(IsNullPredicate isNullPredicate, Unit data) {
        Intrinsics.checkNotNullParameter(isNullPredicate, "isNullPredicate");
        Intrinsics.checkNotNullParameter(data, "data");
        isNullPredicate.getArg().accept(this, data);
        this.builder.append(" " + (isNullPredicate.getIsNegated() ? "!=" : "==") + " null");
    }

    /* JADX INFO: renamed from: visitConstantDescriptor, reason: avoid collision after fix types in other method */
    public void visitConstantDescriptor2(ConstantReference constantReference, Unit data) {
        Intrinsics.checkNotNullParameter(constantReference, "constantReference");
        Intrinsics.checkNotNullParameter(data, "data");
        this.builder.append(constantReference.getName());
    }

    /* JADX INFO: renamed from: visitVariableReference, reason: avoid collision after fix types in other method */
    public void visitVariableReference2(VariableReference variableReference, Unit data) {
        Intrinsics.checkNotNullParameter(variableReference, "variableReference");
        Intrinsics.checkNotNullParameter(data, "data");
        this.builder.append(variableReference.getDescriptor().getShortName());
    }

    private final boolean isAtom(ContractDescriptionElement contractDescriptionElement) {
        return (contractDescriptionElement instanceof VariableReference) || (contractDescriptionElement instanceof ConstantReference) || (contractDescriptionElement instanceof IsNullPredicate) || (contractDescriptionElement instanceof IsInstancePredicate);
    }

    private final boolean needsBrackets(ContractDescriptionElement parent, ContractDescriptionElement child) {
        if (isAtom(child)) {
            return false;
        }
        return (parent instanceof LogicalNot) || parent.getClass() != child.getClass();
    }

    private final void inBracketsIfNecessary(ContractDescriptionElement parent, ContractDescriptionElement child, Function0<Unit> block) {
        if (needsBrackets(parent, child)) {
            this.builder.append("(");
            block.invoke();
            this.builder.append(Constant.AFTER_QUTO);
            return;
        }
        block.invoke();
    }
}
