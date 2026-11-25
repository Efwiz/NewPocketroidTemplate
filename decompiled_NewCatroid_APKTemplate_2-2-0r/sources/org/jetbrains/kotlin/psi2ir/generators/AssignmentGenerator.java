package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.AccessorPropertyLValue;
import org.jetbrains.kotlin.psi2ir.intermediate.ArrayAccessAssignmentReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.AssignmentReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.BackingFieldLValue;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.DelegatedLocalPropertyLValue;
import org.jetbrains.kotlin.psi2ir.intermediate.DynamicMemberLValue;
import org.jetbrains.kotlin.psi2ir.intermediate.FieldPropertyLValue;
import org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue;
import org.jetbrains.kotlin.psi2ir.intermediate.IrUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.LValue;
import org.jetbrains.kotlin.psi2ir.intermediate.OnceExpressionValue;
import org.jetbrains.kotlin.psi2ir.intermediate.PropertyLValueBase;
import org.jetbrains.kotlin.psi2ir.intermediate.SpecialExpressionAssignmentReceiver;
import org.jetbrains.kotlin.psi2ir.intermediate.VariableLValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.PropertyImportedFromObject;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: AssignmentGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J,\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002JR\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u00162\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J \u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0016\u0010\"\u001a\u00020#2\u0006\u0010\u0007\u001a\u00020$2\u0006\u0010\r\u001a\u00020\u000eJ,\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010(\u001a\u00020)2\b\b\u0002\u0010*\u001a\u00020)H\u0002J<\u0010+\u001a\u00020&2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020\b2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030-2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)H\u0002J\u0016\u0010.\u001a\u00020#2\u0006\u0010\u0007\u001a\u00020$2\u0006\u0010\r\u001a\u00020\u000eJ \u0010/\u001a\u00020&2\u0006\u0010'\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0002J\u0016\u00100\u001a\u00020#2\u0006\u0010\u0007\u001a\u0002012\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u00102\u001a\u00020#2\u0006\u0010\u0007\u001a\u0002032\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u00104\u001a\u0002052\u0006\u00106\u001a\u000207H\u0002J\f\u00108\u001a\u00020\n*\u00020\nH\u0002¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/AssignmentGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "createBackingFieldLValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/BackingFieldLValue;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "receiverValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "createPropertyLValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/PropertyLValueBase;", "resultingDescriptor", "candidateDescriptor", "propertyReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/CallReceiver;", "typeArgumentsMap", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/types/KotlinType;", "superQualifier", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "createVariableValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/VariableLValue;", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "generateArrayAccessAssignmentReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/ArrayAccessAssignmentReceiver;", "ktArrayExpr", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "generateAssignment", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "generateAssignmentReceiver", "Lorg/jetbrains/kotlin/psi2ir/intermediate/AssignmentReceiver;", "ktLeft", "isAssignmentStatement", "", "isAugmentedAssignment", "generateAssignmentReceiverForProperty", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "generateAugmentedAssignment", "generateExpressionAssignmentReceiver", "generatePostfixIncrementDecrement", "Lorg/jetbrains/kotlin/psi/KtPostfixExpression;", "generatePrefixIncrementDecrement", "Lorg/jetbrains/kotlin/psi/KtPrefixExpression;", "getDynamicAugmentedAssignmentOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "operatorToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "unwrapPropertyDescriptor", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AssignmentGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AssignmentGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrExpression generateAssignment(KtBinaryExpression ktExpression, IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        if (GeneratorKt.getResolvedCall(this, ktExpression) != null) {
            return generateAugmentedAssignment(ktExpression, origin);
        }
        KtExpression left = ktExpression.getLeft();
        Intrinsics.checkNotNull(left);
        KtExpression right = ktExpression.getRight();
        Intrinsics.checkNotNull(right);
        return generateAssignmentReceiver$default(this, left, IrStatementOrigin.EQ.INSTANCE, false, false, 12, null).assign(genExpr(right));
    }

    public final IrExpression generateAugmentedAssignment(final KtBinaryExpression ktExpression, final IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        AssignmentGenerator assignmentGenerator = this;
        final ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(assignmentGenerator, ktExpression);
        Intrinsics.checkNotNull(resolvedCall);
        WritableSlice<KtExpression, Boolean> VARIABLE_REASSIGNMENT = BindingContext.VARIABLE_REASSIGNMENT;
        Intrinsics.checkNotNullExpressionValue(VARIABLE_REASSIGNMENT, "VARIABLE_REASSIGNMENT");
        Boolean bool = (Boolean) GeneratorKt.get(assignmentGenerator, VARIABLE_REASSIGNMENT, ktExpression);
        final boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
        KtExpression left = ktExpression.getLeft();
        Intrinsics.checkNotNull(left);
        final KtExpression right = ktExpression.getRight();
        Intrinsics.checkNotNull(right);
        AssignmentReceiver assignmentReceiverGenerateAssignmentReceiver$default = generateAssignmentReceiver$default(this, left, origin, false, true, 4, null);
        final boolean zIsDynamic = org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(resolvedCall.getResultingDescriptor());
        return assignmentReceiverGenerateAssignmentReceiver$default.assign(new Function1<LValue, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.AssignmentGenerator.generateAugmentedAssignment.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(LValue irLValue) {
                Intrinsics.checkNotNullParameter(irLValue, "irLValue");
                if (zIsDynamic) {
                    int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
                    int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
                    IrType unitType = this.getContext().getIrBuiltIns().getUnitType();
                    AssignmentGenerator assignmentGenerator2 = this;
                    IElementType operationToken = ktExpression.getOperationToken();
                    Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
                    IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(startOffsetSkippingComments, endOffset, unitType, assignmentGenerator2.getDynamicAugmentedAssignmentOperator(operationToken));
                    AssignmentGenerator assignmentGenerator3 = this;
                    KtExpression ktExpression2 = right;
                    IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl2 = irDynamicOperatorExpressionImpl;
                    IrExpressionsKt.setLeft(irDynamicOperatorExpressionImpl2, irLValue.load());
                    IrExpressionsKt.setRight(irDynamicOperatorExpressionImpl2, assignmentGenerator3.genExpr(ktExpression2));
                    return irDynamicOperatorExpressionImpl;
                }
                CallBuilder callBuilderPregenerateCallReceivers = ArgumentsGenerationUtilsKt.pregenerateCallReceivers(this.getStatementGenerator(), resolvedCall);
                CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCallReceivers, irLValue);
                callBuilderPregenerateCallReceivers.getIrValueArgumentsByIndex()[0] = this.genExpr(right);
                ArgumentsGenerationUtilsKt.generateSamConversionForValueArgumentsIfRequired(this.getStatementGenerator(), callBuilderPregenerateCallReceivers, resolvedCall);
                IrExpression irExpressionGenerateCall$default = CallGeneratorKt.generateCall$default(new CallGenerator(this.getStatementGenerator()), ktExpression, callBuilderPregenerateCallReceivers, origin, 0, 0, 24, null);
                return zBooleanValue ? irLValue.store(irExpressionGenerateCall$default) : irExpressionGenerateCall$default;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrDynamicOperator getDynamicAugmentedAssignmentOperator(IElementType operatorToken) {
        if (Intrinsics.areEqual(operatorToken, KtTokens.PLUSEQ)) {
            return IrDynamicOperator.PLUSEQ;
        }
        if (Intrinsics.areEqual(operatorToken, KtTokens.MINUSEQ)) {
            return IrDynamicOperator.MINUSEQ;
        }
        if (Intrinsics.areEqual(operatorToken, KtTokens.MULTEQ)) {
            return IrDynamicOperator.MULEQ;
        }
        if (Intrinsics.areEqual(operatorToken, KtTokens.DIVEQ)) {
            return IrDynamicOperator.DIVEQ;
        }
        if (Intrinsics.areEqual(operatorToken, KtTokens.PERCEQ)) {
            return IrDynamicOperator.MODEQ;
        }
        throw new AssertionError("Unexpected operator token: " + operatorToken);
    }

    public final IrExpression generatePrefixIncrementDecrement(final KtPrefixExpression ktExpression, final IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        final ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktExpression);
        Intrinsics.checkNotNull(resolvedCall);
        KtExpression baseExpression = ktExpression.getBaseExpression();
        Intrinsics.checkNotNull(baseExpression);
        AssignmentReceiver assignmentReceiverGenerateAssignmentReceiver$default = generateAssignmentReceiver$default(this, baseExpression, origin, false, false, 8, null);
        final boolean zIsDynamic = org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(resolvedCall.getResultingDescriptor());
        return assignmentReceiverGenerateAssignmentReceiver$default.assign(new Function1<LValue, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.AssignmentGenerator.generatePrefixIncrementDecrement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(LValue irLValue) {
                IrDynamicOperator irDynamicOperator;
                Intrinsics.checkNotNullParameter(irLValue, "irLValue");
                int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
                int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
                if (zIsDynamic) {
                    IrType type = irLValue.getType();
                    if (Intrinsics.areEqual(ktExpression.getOperationToken(), KtTokens.PLUSPLUS)) {
                        irDynamicOperator = IrDynamicOperator.PREFIX_INCREMENT;
                    } else {
                        irDynamicOperator = IrDynamicOperator.PREFIX_DECREMENT;
                    }
                    IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(startOffsetSkippingComments, endOffset, type, irDynamicOperator);
                    irDynamicOperatorExpressionImpl.setReceiver(irLValue.load());
                    return irDynamicOperatorExpressionImpl;
                }
                AssignmentGenerator assignmentGenerator = this;
                IrStatementOrigin irStatementOrigin = origin;
                IrType type2 = irLValue.getType();
                AssignmentGenerator assignmentGenerator2 = this;
                ResolvedCall<? extends CallableDescriptor> resolvedCall2 = resolvedCall;
                KtPrefixExpression ktPrefixExpression = ktExpression;
                IrStatementOrigin irStatementOrigin2 = origin;
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(assignmentGenerator.getContext(), assignmentGenerator.getScope(), startOffsetSkippingComments, endOffset, irStatementOrigin, type2, false, 64, null);
                CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(assignmentGenerator2.getStatementGenerator(), resolvedCall2);
                CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, irLValue);
                irBlockBuilder.unaryPlus(irLValue.store(CallGeneratorKt.generateCall$default(new CallGenerator(assignmentGenerator2.getStatementGenerator()), ktPrefixExpression, callBuilderPregenerateCall, irStatementOrigin2, 0, 0, 24, null)));
                irBlockBuilder.unaryPlus(irLValue.load());
                return irBlockBuilder.getIrBlockBody();
            }
        });
    }

    public final IrExpression generatePostfixIncrementDecrement(final KtPostfixExpression ktExpression, final IrStatementOrigin origin) {
        Intrinsics.checkNotNullParameter(ktExpression, "ktExpression");
        Intrinsics.checkNotNullParameter(origin, "origin");
        final ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktExpression);
        Intrinsics.checkNotNull(resolvedCall);
        KtExpression baseExpression = ktExpression.getBaseExpression();
        Intrinsics.checkNotNull(baseExpression);
        AssignmentReceiver assignmentReceiverGenerateAssignmentReceiver$default = generateAssignmentReceiver$default(this, baseExpression, origin, false, false, 8, null);
        final boolean zIsDynamic = org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(resolvedCall.getResultingDescriptor());
        KtPostfixExpression ktPostfixExpression = ktExpression;
        final int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktPostfixExpression);
        final int endOffset = PsiUtilsKt.getEndOffset(ktPostfixExpression);
        return assignmentReceiverGenerateAssignmentReceiver$default.assign(new Function1<LValue, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.AssignmentGenerator.generatePostfixIncrementDecrement.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(LValue irLValue) {
                IrDynamicOperator irDynamicOperator;
                Intrinsics.checkNotNullParameter(irLValue, "irLValue");
                if (zIsDynamic) {
                    int i = startOffsetSkippingComments;
                    int i2 = endOffset;
                    IrType type = irLValue.getType();
                    if (Intrinsics.areEqual(ktExpression.getOperationToken(), KtTokens.PLUSPLUS)) {
                        irDynamicOperator = IrDynamicOperator.POSTFIX_INCREMENT;
                    } else {
                        irDynamicOperator = IrDynamicOperator.POSTFIX_DECREMENT;
                    }
                    IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(i, i2, type, irDynamicOperator);
                    irDynamicOperatorExpressionImpl.setReceiver(irLValue.load());
                    return irDynamicOperatorExpressionImpl;
                }
                AssignmentGenerator assignmentGenerator = this;
                int i3 = startOffsetSkippingComments;
                int i4 = endOffset;
                IrStatementOrigin irStatementOrigin = origin;
                IrType type2 = irLValue.getType();
                AssignmentGenerator assignmentGenerator2 = this;
                ResolvedCall<? extends CallableDescriptor> resolvedCall2 = resolvedCall;
                int i5 = startOffsetSkippingComments;
                int i6 = endOffset;
                KtPostfixExpression ktPostfixExpression2 = ktExpression;
                IrStatementOrigin irStatementOrigin2 = origin;
                IrBlockBuilder irBlockBuilder = new IrBlockBuilder(assignmentGenerator.getContext(), assignmentGenerator.getScope(), i3, i4, irStatementOrigin, type2, false, 64, null);
                IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irLValue.load(), null, null, false, null, 30, null);
                CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(assignmentGenerator2.getStatementGenerator(), resolvedCall2);
                CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, new VariableLValue(irBlockBuilder.getContext(), i5, i6, irVariableIrTemporary$default.getSymbol(), irVariableIrTemporary$default.getType(), null, 32, null));
                irBlockBuilder.unaryPlus(irLValue.store(CallGeneratorKt.generateCall$default(new CallGenerator(assignmentGenerator2.getStatementGenerator()), ktPostfixExpression2, callBuilderPregenerateCall, irStatementOrigin2, 0, 0, 24, null)));
                irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default.getType(), irVariableIrTemporary$default.getSymbol()));
                return irBlockBuilder.getIrBlockBody();
            }
        });
    }

    static /* synthetic */ AssignmentReceiver generateAssignmentReceiver$default(AssignmentGenerator assignmentGenerator, KtExpression ktExpression, IrStatementOrigin irStatementOrigin, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return assignmentGenerator.generateAssignmentReceiver(ktExpression, irStatementOrigin, z, z2);
    }

    private final AssignmentReceiver generateAssignmentReceiver(KtExpression ktLeft, IrStatementOrigin origin, boolean isAssignmentStatement, boolean isAugmentedAssignment) {
        AssignmentReceiver assignmentReceiverCreateVariableValue;
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(ktLeft);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        if (ktExpressionSafeDeparenthesize instanceof KtArrayAccessExpression) {
            return generateArrayAccessAssignmentReceiver((KtArrayAccessExpression) ktExpressionSafeDeparenthesize, origin);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktExpressionSafeDeparenthesize);
        if (resolvedCall == null) {
            return generateExpressionAssignmentReceiver(ktExpressionSafeDeparenthesize, origin, isAssignmentStatement);
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        KtExpression ktExpression = ktExpressionSafeDeparenthesize;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
        if (resultingDescriptor instanceof SyntheticFieldDescriptor) {
            SyntheticFieldDescriptor syntheticFieldDescriptor = (SyntheticFieldDescriptor) resultingDescriptor;
            return createBackingFieldLValue(ktExpressionSafeDeparenthesize, syntheticFieldDescriptor.getPropertyDescriptor(), ArgumentsGenerationUtilsKt.generateBackingFieldReceiver(getStatementGenerator(), startOffsetSkippingComments, endOffset, resolvedCall, syntheticFieldDescriptor), origin);
        }
        if (resultingDescriptor instanceof LocalVariableDescriptor) {
            LocalVariableDescriptor localVariableDescriptor = (LocalVariableDescriptor) resultingDescriptor;
            if (localVariableDescriptor.getIsDelegated()) {
                GeneratorContext context = getContext();
                KotlinType type = localVariableDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                IrType irType = toIrType(type);
                LocalVariableAccessorDescriptor.Getter getter = localVariableDescriptor.getGetter();
                IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceDeclaredFunction = getter != null ? getContext().getSymbolTable().getDescriptorExtension().referenceDeclaredFunction(getter) : null;
                LocalVariableAccessorDescriptor.Setter setter = localVariableDescriptor.getSetter();
                assignmentReceiverCreateVariableValue = new DelegatedLocalPropertyLValue(context, startOffsetSkippingComments, endOffset, irType, irSimpleFunctionSymbolReferenceDeclaredFunction, setter != null ? getContext().getSymbolTable().getDescriptorExtension().referenceDeclaredFunction(setter) : null, origin);
            } else {
                assignmentReceiverCreateVariableValue = createVariableValue(ktExpressionSafeDeparenthesize, (ValueDescriptor) resultingDescriptor, origin);
            }
            return assignmentReceiverCreateVariableValue;
        }
        if (resultingDescriptor instanceof PropertyDescriptor) {
            return generateAssignmentReceiverForProperty((PropertyDescriptor) resultingDescriptor, origin, ktExpressionSafeDeparenthesize, resolvedCall, isAssignmentStatement, isAugmentedAssignment);
        }
        if (resultingDescriptor instanceof FakeCallableDescriptorForObject) {
            return new OnceExpressionValue(genExpr(ktExpressionSafeDeparenthesize));
        }
        if (resultingDescriptor instanceof ValueDescriptor) {
            return createVariableValue(ktExpressionSafeDeparenthesize, (ValueDescriptor) resultingDescriptor, origin);
        }
        return new OnceExpressionValue(genExpr(ktExpressionSafeDeparenthesize));
    }

    private final AssignmentReceiver generateExpressionAssignmentReceiver(KtExpression ktLeft, IrStatementOrigin origin, boolean isAssignmentStatement) {
        IrType irType;
        if (!isAssignmentStatement) {
            throw new AssertionError("Arbitrary assignment receiver found in assignment-like expression: " + ktLeft.getLightParent().getText());
        }
        StatementGenerator statementGenerator = getStatementGenerator();
        KotlinType type = getContext().getBindingContext().getType(ktLeft);
        if (type != null && (irType = toIrType(type)) != null) {
            return new SpecialExpressionAssignmentReceiver(statementGenerator, ktLeft, origin, irType);
        }
        throw new AssertionError("No type for expression " + ktLeft.getText());
    }

    private final VariableLValue createVariableValue(KtExpression ktExpression, ValueDescriptor descriptor, IrStatementOrigin origin) {
        GeneratorContext context = getContext();
        KtExpression ktExpression2 = ktExpression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression2);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression2);
        IrValueSymbol irValueSymbolReferenceValue = getContext().getSymbolTable().getDescriptorExtension().referenceValue(descriptor);
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new VariableLValue(context, startOffsetSkippingComments, endOffset, irValueSymbolReferenceValue, toIrType(type), origin);
    }

    private final BackingFieldLValue createBackingFieldLValue(KtExpression ktExpression, PropertyDescriptor descriptor, IntermediateValue receiverValue, IrStatementOrigin origin) {
        GeneratorContext context = getContext();
        KtExpression ktExpression2 = ktExpression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression2);
        int endOffset = PsiUtilsKt.getEndOffset(ktExpression2);
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new BackingFieldLValue(context, startOffsetSkippingComments, endOffset, toIrType(type), getContext().getSymbolTable().getDescriptorExtension().referenceField(descriptor), receiverValue, origin);
    }

    private final AssignmentReceiver generateAssignmentReceiverForProperty(PropertyDescriptor descriptor, IrStatementOrigin origin, KtExpression ktLeft, ResolvedCall<?> resolvedCall, boolean isAssignmentStatement, boolean isAugmentedAssignment) {
        if (org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt.isDynamic(descriptor)) {
            GeneratorContext context = getContext();
            KtExpression ktExpression = ktLeft;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression);
            int endOffset = PsiUtilsKt.getEndOffset(ktExpression);
            KotlinType type = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            IrType irType = toIrType(type);
            String strAsString = descriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
            ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
            List<ReceiverValue> contextReceivers = resolvedCall.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
            Call call = resolvedCall.get$fakeCall();
            Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
            return new DynamicMemberLValue(context, startOffsetSkippingComments, endOffset, irType, strAsString, ArgumentsGenerationUtilsKt.generateCallReceiver(getStatementGenerator(), ktLeft, descriptor, dispatchReceiver, extensionReceiver, contextReceivers, CallUtilKt.isSafeCall(call), isAssignmentStatement));
        }
        if (Intrinsics.areEqual(origin, IrStatementOrigin.EQ.INSTANCE) && !descriptor.isVar() && !isAugmentedAssignment) {
            ReceiverValue dispatchReceiver2 = resolvedCall.getDispatchReceiver();
            if (dispatchReceiver2 == null) {
                ReceiverParameterDescriptor dispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
                dispatchReceiver2 = dispatchReceiverParameter != null ? dispatchReceiverParameter.getValue() : null;
            }
            return createBackingFieldLValue(ktLeft, descriptor, ArgumentsGenerationUtilsKt.generateReceiverOrNull(getStatementGenerator(), ktLeft, dispatchReceiver2), null);
        }
        ReceiverValue dispatchReceiver3 = resolvedCall.getDispatchReceiver();
        ReceiverValue extensionReceiver2 = resolvedCall.getExtensionReceiver();
        List<ReceiverValue> contextReceivers2 = resolvedCall.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers2, "getContextReceivers(...)");
        Call call2 = resolvedCall.get$fakeCall();
        Intrinsics.checkNotNullExpressionValue(call2, "getCall(...)");
        CallReceiver callReceiverGenerateCallReceiver = ArgumentsGenerationUtilsKt.generateCallReceiver(getStatementGenerator(), ktLeft, descriptor, dispatchReceiver3, extensionReceiver2, contextReceivers2, CallUtilKt.isSafeCall(call2), isAssignmentStatement);
        ClassDescriptor superQualifier = ArgumentsGenerationUtilsKt.getSuperQualifier(this, resolvedCall);
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNull(callableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        return createPropertyLValue(ktLeft, descriptor, (PropertyDescriptor) callableDescriptor, callReceiverGenerateCallReceiver, ArgumentsGenerationUtilsKt.getTypeArguments(resolvedCall), origin, superQualifier);
    }

    private final PropertyDescriptor unwrapPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor instanceof PropertyImportedFromObject ? ((PropertyImportedFromObject) propertyDescriptor).getCallableFromObject() : propertyDescriptor;
    }

    private final PropertyLValueBase createPropertyLValue(KtExpression ktExpression, PropertyDescriptor resultingDescriptor, PropertyDescriptor candidateDescriptor, CallReceiver propertyReceiver, Map<TypeParameterDescriptor, ? extends KotlinType> typeArgumentsMap, IrStatementOrigin origin, ClassDescriptor superQualifier) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction;
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction2;
        ArrayList arrayList;
        FunctionDescriptor functionDescriptorComputeSubstitutedSyntheticAccessor;
        ClassDescriptor classDescriptor;
        PropertyDescriptor propertyDescriptorUnwrapPropertyDescriptor = unwrapPropertyDescriptor(resultingDescriptor);
        FunctionDescriptor unwrappedGetMethod = KotlinUtilsKt.getUnwrappedGetMethod(propertyDescriptorUnwrapPropertyDescriptor);
        FunctionDescriptor unwrappedSetMethod = KotlinUtilsKt.getUnwrappedSetMethod(propertyDescriptorUnwrapPropertyDescriptor);
        FunctionDescriptor functionDescriptorComputeSubstitutedSyntheticAccessor2 = null;
        if (unwrappedSetMethod == null || Intrinsics.areEqual(unwrappedSetMethod.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            unwrappedSetMethod = null;
        }
        if (unwrappedGetMethod != null) {
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            FunctionDescriptor original = unwrappedGetMethod.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            irSimpleFunctionSymbolReferenceSimpleFunction = descriptorExtension.referenceSimpleFunction(original);
        } else {
            irSimpleFunctionSymbolReferenceSimpleFunction = null;
        }
        if (unwrappedSetMethod != null) {
            DescriptorSymbolTableExtension descriptorExtension2 = getContext().getSymbolTable().getDescriptorExtension();
            FunctionDescriptor original2 = unwrappedSetMethod.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original2, "getOriginal(...)");
            irSimpleFunctionSymbolReferenceSimpleFunction2 = descriptorExtension2.referenceSimpleFunction(original2);
        } else {
            irSimpleFunctionSymbolReferenceSimpleFunction2 = null;
        }
        KotlinType type = resultingDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = toIrType(type);
        if (irSimpleFunctionSymbolReferenceSimpleFunction != null || irSimpleFunctionSymbolReferenceSimpleFunction2 != null) {
            IrClassSymbol irClassSymbolReferenceClass = superQualifier != null ? getContext().getSymbolTable().getDescriptorExtension().referenceClass(superQualifier) : null;
            if (typeArgumentsMap != null) {
                List<TypeParameterDescriptor> typeParameters = candidateDescriptor.getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                List<TypeParameterDescriptor> list = typeParameters;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    KotlinType kotlinType = typeArgumentsMap.get((TypeParameterDescriptor) it.next());
                    Intrinsics.checkNotNull(kotlinType);
                    arrayList2.add(toIrType(kotlinType));
                }
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
            GeneratorContext context = getContext();
            Scope scope = getScope();
            KtExpression ktExpression2 = ktExpression;
            int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression2);
            int endOffset = PsiUtilsKt.getEndOffset(ktExpression2);
            if (unwrappedGetMethod != null) {
                PropertyGetterDescriptor getter = propertyDescriptorUnwrapPropertyDescriptor.getGetter();
                Intrinsics.checkNotNull(getter);
                functionDescriptorComputeSubstitutedSyntheticAccessor = IrUtilsKt.computeSubstitutedSyntheticAccessor(propertyDescriptorUnwrapPropertyDescriptor, unwrappedGetMethod, getter);
            } else {
                functionDescriptorComputeSubstitutedSyntheticAccessor = null;
            }
            if (unwrappedSetMethod != null) {
                PropertySetterDescriptor setter = propertyDescriptorUnwrapPropertyDescriptor.getSetter();
                Intrinsics.checkNotNull(setter);
                functionDescriptorComputeSubstitutedSyntheticAccessor2 = IrUtilsKt.computeSubstitutedSyntheticAccessor(propertyDescriptorUnwrapPropertyDescriptor, unwrappedSetMethod, setter);
            }
            return new AccessorPropertyLValue(context, scope, startOffsetSkippingComments, endOffset, origin, irType, irSimpleFunctionSymbolReferenceSimpleFunction, functionDescriptorComputeSubstitutedSyntheticAccessor, irSimpleFunctionSymbolReferenceSimpleFunction2, functionDescriptorComputeSubstitutedSyntheticAccessor2, arrayList, propertyReceiver, irClassSymbolReferenceClass);
        }
        if (superQualifier == null) {
            DeclarationDescriptor containingDeclaration = propertyDescriptorUnwrapPropertyDescriptor.getContainingModule();
            classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        } else {
            classDescriptor = superQualifier;
        }
        IrClassSymbol irClassSymbolReferenceClass2 = classDescriptor != null ? getContext().getSymbolTable().getDescriptorExtension().referenceClass(classDescriptor) : null;
        GeneratorContext context2 = getContext();
        Scope scope2 = getScope();
        KtExpression ktExpression3 = ktExpression;
        int startOffsetSkippingComments2 = PsiUtilsKt.getStartOffsetSkippingComments(ktExpression3);
        int endOffset2 = PsiUtilsKt.getEndOffset(ktExpression3);
        DescriptorSymbolTableExtension descriptorExtension3 = getContext().getSymbolTable().getDescriptorExtension();
        PropertyDescriptor original3 = KotlinUtilsKt.resolveFakeOverride(propertyDescriptorUnwrapPropertyDescriptor).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original3, "getOriginal(...)");
        return new FieldPropertyLValue(context2, scope2, startOffsetSkippingComments2, endOffset2, origin, descriptorExtension3.referenceField(original3), propertyDescriptorUnwrapPropertyDescriptor, irType, propertyReceiver, irClassSymbolReferenceClass2);
    }

    private final ArrayAccessAssignmentReceiver generateArrayAccessAssignmentReceiver(KtArrayAccessExpression ktArrayExpr, IrStatementOrigin origin) {
        AssignmentGenerator assignmentGenerator = this;
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> INDEXED_LVALUE_GET = BindingContext.INDEXED_LVALUE_GET;
        Intrinsics.checkNotNullExpressionValue(INDEXED_LVALUE_GET, "INDEXED_LVALUE_GET");
        final ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.get(assignmentGenerator, INDEXED_LVALUE_GET, ktArrayExpr);
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> INDEXED_LVALUE_SET = BindingContext.INDEXED_LVALUE_SET;
        Intrinsics.checkNotNullExpressionValue(INDEXED_LVALUE_SET, "INDEXED_LVALUE_SET");
        final ResolvedCall resolvedCall2 = (ResolvedCall) GeneratorKt.get(assignmentGenerator, INDEXED_LVALUE_SET, ktArrayExpr);
        KtExpression arrayExpression = ktArrayExpr.getArrayExpression();
        Intrinsics.checkNotNull(arrayExpression);
        IrExpression irExpressionGenExpr = genExpr(arrayExpression);
        List<KtExpression> indexExpressions = ktArrayExpr.getIndexExpressions();
        Intrinsics.checkNotNullExpressionValue(indexExpressions, "getIndexExpressions(...)");
        List<KtExpression> indexExpressions2 = ktArrayExpr.getIndexExpressions();
        Intrinsics.checkNotNullExpressionValue(indexExpressions2, "getIndexExpressions(...)");
        List<KtExpression> list = indexExpressions2;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtExpression ktExpression : list) {
            Intrinsics.checkNotNull(ktExpression);
            arrayList.add(genExpr(ktExpression));
        }
        KtArrayAccessExpression ktArrayAccessExpression = ktArrayExpr;
        return new ArrayAccessAssignmentReceiver(irExpressionGenExpr, indexExpressions, arrayList, resolvedCall, resolvedCall2, new Function0<CallBuilder>() { // from class: org.jetbrains.kotlin.psi2ir.generators.AssignmentGenerator.generateArrayAccessAssignmentReceiver.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CallBuilder invoke() {
                ResolvedCall<FunctionDescriptor> resolvedCall3 = resolvedCall;
                if (resolvedCall3 != null) {
                    return ArgumentsGenerationUtilsKt.pregenerateCallReceivers(this.getStatementGenerator(), resolvedCall3);
                }
                return null;
            }
        }, new Function0<CallBuilder>() { // from class: org.jetbrains.kotlin.psi2ir.generators.AssignmentGenerator.generateArrayAccessAssignmentReceiver.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CallBuilder invoke() {
                ResolvedCall<FunctionDescriptor> resolvedCall3 = resolvedCall2;
                if (resolvedCall3 != null) {
                    return ArgumentsGenerationUtilsKt.pregenerateCallReceivers(this.getStatementGenerator(), resolvedCall3);
                }
                return null;
            }
        }, new CallGenerator(getStatementGenerator()), PsiUtilsKt.getStartOffsetSkippingComments(ktArrayAccessExpression), PsiUtilsKt.getEndOffset(ktArrayAccessExpression), origin);
    }
}
