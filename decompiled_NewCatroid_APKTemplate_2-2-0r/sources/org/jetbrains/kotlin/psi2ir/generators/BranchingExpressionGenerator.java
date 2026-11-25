package org.jetbrains.kotlin.psi2ir.generators;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrSingleStatementBuilder;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtOperationReferenceExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionIsPattern;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.IrUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: BranchingExpressionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J0\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0012H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u0018\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u000e\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\rJ\"\u0010\"\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0002J\u0018\u0010&\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020'H\u0002J\"\u0010(\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010)\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020*H\u0002J\"\u0010+\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020*2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010$\u001a\u0004\u0018\u00010%H\u0002J\u000e\u0010,\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\nJ\u0012\u0010-\u001a\u0004\u0018\u00010\u001d2\u0006\u0010!\u001a\u00020\nH\u0002¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/BranchingExpressionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "addElseBranchForExhaustiveWhenIfNeeded", "", "irWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "whenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "createIrWhen", "ktIf", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "irBranches", "", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "irElseResult", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "resultType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "elseBranch", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrElseBranchImpl;", "result", "generateEmptyBlockForMissingBranch", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrBlockImpl;", "ktLastIf", "generateEqualsCondition", "irSubject", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ktCondition", "Lorg/jetbrains/kotlin/psi/KtWhenConditionWithExpression;", "generateIfExpression", "expression", "generateInCondition", "Lorg/jetbrains/kotlin/psi/KtWhenConditionInRange;", "ktSubject", "Lorg/jetbrains/kotlin/psi/KtExpression;", "generateIsPatternCondition", "Lorg/jetbrains/kotlin/psi/KtWhenConditionIsPattern;", "generateWhenBody", "generateWhenConditionNoSubject", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "generateWhenConditionWithSubject", "generateWhenExpression", "generateWhenSubject", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BranchingExpressionGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BranchingExpressionGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrExpression generateIfExpression(KtIfExpression expression) {
        IrBlockImpl irBlockImplGenerateEmptyBlockForMissingBranch;
        IrExpression irExpressionGenExpr;
        Intrinsics.checkNotNullParameter(expression, "expression");
        SmartList smartList = new SmartList();
        KtIfExpression ktIfExpression = expression;
        while (true) {
            KtExpression condition = ktIfExpression.getCondition();
            Intrinsics.checkNotNull(condition);
            IrExpression irExpressionGenExpr2 = genExpr(condition);
            KtExpression then = ktIfExpression.getThen();
            if (then == null || (irBlockImplGenerateEmptyBlockForMissingBranch = genExpr(then)) == null) {
                irBlockImplGenerateEmptyBlockForMissingBranch = generateEmptyBlockForMissingBranch(ktIfExpression);
            }
            smartList.add(new IrBranchImpl(irExpressionGenExpr2, irBlockImplGenerateEmptyBlockForMissingBranch));
            KtExpression ktExpression = ktIfExpression.getElse();
            irExpressionGenExpr = null;
            KtElement ktElementDeparenthesize = ktExpression != null ? KotlinUtilsKt.deparenthesize(ktExpression) : null;
            if (ktElementDeparenthesize == null) {
                break;
            }
            if (!(ktElementDeparenthesize instanceof KtIfExpression)) {
                if (ktElementDeparenthesize instanceof KtExpression) {
                    irExpressionGenExpr = genExpr((KtExpression) ktElementDeparenthesize);
                    break;
                }
                throw new AssertionError("Unexpected else expression: " + ktElementDeparenthesize.getText());
            }
            ktIfExpression = (KtIfExpression) ktElementDeparenthesize;
        }
        return createIrWhen(expression, smartList, irExpressionGenExpr, toIrType(GeneratorKt.getExpressionTypeWithCoercionToUnitOrFail(this, expression)));
    }

    private final IrBlockImpl generateEmptyBlockForMissingBranch(KtIfExpression ktLastIf) {
        KtIfExpression ktIfExpression = ktLastIf;
        return new IrBlockImpl(PsiUtilsKt.getStartOffset(ktIfExpression), PsiUtilsKt.getEndOffset(ktIfExpression), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.IF.INSTANCE, CollectionsKt.emptyList());
    }

    private final IrWhen createIrWhen(KtIfExpression ktIf, List<? extends IrBranch> irBranches, IrExpression irElseResult, IrType resultType) {
        if (irBranches.size() == 1) {
            IrBranch irBranch = irBranches.get(0);
            BranchingExpressionGenerator branchingExpressionGenerator = this;
            KtIfExpression ktIfExpression = ktIf;
            return ExpressionHelpersKt.irIfThenMaybeElse(new IrSingleStatementBuilder(branchingExpressionGenerator.getContext(), branchingExpressionGenerator.getScope(), PsiUtilsKt.getStartOffsetSkippingComments(ktIfExpression), PsiUtilsKt.getEndOffset(ktIfExpression), null, 16, null), resultType, irBranch.getCondition(), irBranch.getResult(), irElseResult, IrStatementOrigin.IF.INSTANCE);
        }
        KtIfExpression ktIfExpression2 = ktIf;
        IrWhenImpl irWhenImpl = new IrWhenImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktIfExpression2), PsiUtilsKt.getEndOffset(ktIfExpression2), resultType, IrStatementOrigin.IF.INSTANCE);
        irWhenImpl.getBranches().addAll(irBranches);
        if (irElseResult != null) {
            irWhenImpl.getBranches().add(elseBranch(irElseResult));
        }
        return irWhenImpl;
    }

    private final IrElseBranchImpl elseBranch(IrExpression result) {
        return new IrElseBranchImpl(IrConstImpl.INSTANCE.m7206boolean(result.getStartOffset(), result.getEndOffset(), getContext().getIrBuiltIns().getBooleanType(), true), result);
    }

    public final IrExpression generateWhenExpression(KtWhenExpression expression) {
        IrExpression irExpressionGenerateWhenConditionNoSubject;
        IrWhen irWhenWhenComma;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrVariable irVariableGenerateWhenSubject = generateWhenSubject(expression);
        KtWhenExpression ktWhenExpression = expression;
        IrWhenImpl irWhenImpl = new IrWhenImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktWhenExpression), PsiUtilsKt.getEndOffset(ktWhenExpression), toIrType(GeneratorKt.getExpressionTypeWithCoercionToUnitOrFail(this, expression)), IrStatementOrigin.WHEN.INSTANCE);
        for (KtWhenEntry ktWhenEntry : expression.getEntries()) {
            if (ktWhenEntry.isElse()) {
                KtExpression expression2 = ktWhenEntry.getExpression();
                Intrinsics.checkNotNull(expression2);
                irWhenImpl.getBranches().add(elseBranch(genExpr(expression2)));
                return generateWhenBody(expression, irVariableGenerateWhenSubject, irWhenImpl);
            }
            KtWhenCondition[] conditions = ktWhenEntry.getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            Iterator it = CollectionsKt.chunked(ArraysKt.toList(conditions), 64).iterator();
            while (it.hasNext()) {
                IrWhen irWhen = null;
                for (KtWhenCondition ktWhenCondition : (List) it.next()) {
                    if (irVariableGenerateWhenSubject != null) {
                        Intrinsics.checkNotNull(ktWhenCondition);
                        irExpressionGenerateWhenConditionNoSubject = generateWhenConditionWithSubject(ktWhenCondition, irVariableGenerateWhenSubject, expression.getSubjectExpression());
                    } else {
                        Intrinsics.checkNotNull(ktWhenCondition);
                        irExpressionGenerateWhenConditionNoSubject = generateWhenConditionNoSubject(ktWhenCondition);
                    }
                    irWhen = (irWhen == null || (irWhenWhenComma = PrimitivesKt.whenComma(getContext(), irWhen, irExpressionGenerateWhenConditionNoSubject)) == null) ? irExpressionGenerateWhenConditionNoSubject : irWhenWhenComma;
                }
                KtExpression expression3 = ktWhenEntry.getExpression();
                Intrinsics.checkNotNull(expression3);
                IrExpression irExpressionGenExpr = genExpr(expression3);
                List<IrBranch> branches = irWhenImpl.getBranches();
                Intrinsics.checkNotNull(irWhen);
                branches.add(new IrBranchImpl(irWhen, irExpressionGenExpr));
            }
        }
        addElseBranchForExhaustiveWhenIfNeeded(irWhenImpl, expression);
        return generateWhenBody(expression, irVariableGenerateWhenSubject, irWhenImpl);
    }

    private final IrVariable generateWhenSubject(KtWhenExpression expression) {
        KtProperty subjectVariable = expression.getSubjectVariable();
        KtExpression subjectExpression = expression.getSubjectExpression();
        if (subjectVariable != null) {
            IrStatement irStatementVisitProperty = getStatementGenerator().visitProperty(subjectVariable, (Void) null);
            Intrinsics.checkNotNull(irStatementVisitProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
            return (IrVariable) irStatementVisitProperty;
        }
        if (subjectExpression == null) {
            return null;
        }
        Scope scope = getScope();
        IrExpression irExpressionGenExpr = genExpr(subjectExpression);
        return scope.createTemporaryVariable(irExpressionGenExpr, (108 & 2) != 0 ? null : "subject", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionGenExpr.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionGenExpr.getEndOffset() : 0);
    }

    private final void addElseBranchForExhaustiveWhenIfNeeded(IrWhen irWhen, KtWhenExpression whenExpression) {
        boolean zAreEqual;
        if (BindingContextUtilsKt.isUsedAsExpression(whenExpression, getContext().getBindingContext())) {
            WritableSlice<KtWhenExpression, Boolean> EXHAUSTIVE_WHEN = BindingContext.EXHAUSTIVE_WHEN;
            Intrinsics.checkNotNullExpressionValue(EXHAUSTIVE_WHEN, "EXHAUSTIVE_WHEN");
            zAreEqual = Intrinsics.areEqual((Object) true, GeneratorKt.get(this, EXHAUSTIVE_WHEN, whenExpression));
        } else {
            WritableSlice<KtWhenExpression, Boolean> IMPLICIT_EXHAUSTIVE_WHEN = BindingContext.IMPLICIT_EXHAUSTIVE_WHEN;
            Intrinsics.checkNotNullExpressionValue(IMPLICIT_EXHAUSTIVE_WHEN, "IMPLICIT_EXHAUSTIVE_WHEN");
            zAreEqual = Intrinsics.areEqual((Object) true, GeneratorKt.get(this, IMPLICIT_EXHAUSTIVE_WHEN, whenExpression));
        }
        if (zAreEqual) {
            IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
            IrType nothingType = getContext().getIrBuiltIns().getNothingType();
            IrSimpleFunctionSymbol noWhenBranchMatchedExceptionSymbol = getContext().getIrBuiltIns().getNoWhenBranchMatchedExceptionSymbol();
            irWhen.getBranches().add(elseBranch(companion.fromSymbolDescriptor(-1, -1, nothingType, noWhenBranchMatchedExceptionSymbol, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(noWhenBranchMatchedExceptionSymbol.getDescriptor()) : 0, (176 & 32) != 0 ? noWhenBranchMatchedExceptionSymbol.getDescriptor().getValueParameters().size() + noWhenBranchMatchedExceptionSymbol.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : null, (176 & 128) != 0 ? null : null)));
        }
    }

    private final IrExpression generateWhenBody(KtWhenExpression expression, IrVariable irSubject, IrWhen irWhen) {
        IrBlockImpl irBlockImpl;
        if (irSubject == null) {
            if (irWhen.getBranches().isEmpty()) {
                KtWhenExpression ktWhenExpression = expression;
                return new IrBlockImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktWhenExpression), PsiUtilsKt.getEndOffset(ktWhenExpression), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.WHEN.INSTANCE);
            }
            return irWhen;
        }
        if (irWhen.getBranches().isEmpty()) {
            KtWhenExpression ktWhenExpression2 = expression;
            irBlockImpl = new IrBlockImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktWhenExpression2), PsiUtilsKt.getEndOffset(ktWhenExpression2), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.WHEN.INSTANCE);
            irBlockImpl.getStatements().add(irSubject);
        } else {
            KtWhenExpression ktWhenExpression3 = expression;
            IrBlockImpl irBlockImpl2 = new IrBlockImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktWhenExpression3), PsiUtilsKt.getEndOffset(ktWhenExpression3), irWhen.getType(), IrStatementOrigin.WHEN.INSTANCE);
            irBlockImpl2.getStatements().add(irSubject);
            irBlockImpl2.getStatements().add(irWhen);
            irBlockImpl = irBlockImpl2;
        }
        return irBlockImpl;
    }

    private final IrExpression generateWhenConditionNoSubject(KtWhenCondition ktCondition) {
        Intrinsics.checkNotNull(ktCondition, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtWhenConditionWithExpression");
        KtExpression expression = ((KtWhenConditionWithExpression) ktCondition).getExpression();
        Intrinsics.checkNotNull(expression);
        return genExpr(expression);
    }

    private final IrExpression generateWhenConditionWithSubject(KtWhenCondition ktCondition, IrVariable irSubject, KtExpression ktSubject) {
        if (ktCondition instanceof KtWhenConditionWithExpression) {
            return generateEqualsCondition(irSubject, (KtWhenConditionWithExpression) ktCondition);
        }
        if (ktCondition instanceof KtWhenConditionInRange) {
            return generateInCondition(irSubject, (KtWhenConditionInRange) ktCondition, ktSubject);
        }
        if (ktCondition instanceof KtWhenConditionIsPattern) {
            return generateIsPatternCondition(irSubject, (KtWhenConditionIsPattern) ktCondition);
        }
        throw new AssertionError("Unexpected 'when' condition: " + ktCondition.getText());
    }

    private final IrExpression generateIsPatternCondition(IrVariable irSubject, KtWhenConditionIsPattern ktCondition) {
        WritableSlice<KtTypeReference, KotlinType> TYPE = BindingContext.TYPE;
        Intrinsics.checkNotNullExpressionValue(TYPE, "TYPE");
        KotlinType kotlinType = (KotlinType) GeneratorKt.getOrFail(this, TYPE, ktCondition.getTypeReference());
        Intrinsics.checkNotNull(kotlinType);
        IrType irType = toIrType(kotlinType);
        KtWhenConditionIsPattern ktWhenConditionIsPattern = ktCondition;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktWhenConditionIsPattern);
        IrTypeOperatorCallImpl irTypeOperatorCallImpl = new IrTypeOperatorCallImpl(startOffsetSkippingComments, PsiUtilsKt.getEndOffset(ktWhenConditionIsPattern), getContext().getIrBuiltIns().getBooleanType(), IrTypeOperator.INSTANCEOF, irType, IrUtilsKt.loadAt(irSubject, startOffsetSkippingComments, startOffsetSkippingComments));
        if (ktCondition.isNegated()) {
            return PrimitivesKt.primitiveOp1(PsiUtilsKt.getStartOffsetSkippingComments(ktWhenConditionIsPattern), PsiUtilsKt.getEndOffset(ktWhenConditionIsPattern), getContext().getIrBuiltIns().getBooleanNotSymbol(), getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.EXCL.INSTANCE, irTypeOperatorCallImpl);
        }
        return irTypeOperatorCallImpl;
    }

    private final IrExpression generateInCondition(final IrVariable irSubject, KtWhenConditionInRange ktCondition, final KtExpression ktSubject) {
        KtWhenConditionInRange ktWhenConditionInRange = ktCondition;
        final int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktWhenConditionInRange);
        int endOffset = PsiUtilsKt.getEndOffset(ktWhenConditionInRange);
        StatementGenerator statementGenerator = getStatementGenerator();
        KtOperationReferenceExpression operationReference = ktCondition.getOperationReference();
        Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, operationReference);
        Intrinsics.checkNotNull(resolvedCall);
        CallBuilder callBuilderPregenerateCallUsing = ArgumentsGenerationUtilsKt.pregenerateCallUsing(statementGenerator, resolvedCall, new Function1<KtExpression, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.BranchingExpressionGenerator$generateInCondition$inCall$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(KtExpression it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it != ktSubject) {
                    return this.getStatementGenerator().generateExpression(it);
                }
                IrVariable irVariable = irSubject;
                int i = startOffsetSkippingComments;
                return IrUtilsKt.loadAt(irVariable, i, i);
            }
        });
        IrStatementOrigin infixOperator = OperatorConventionsKt.getInfixOperator(ktCondition.getOperationReference().getReferencedNameElementType());
        IrExpression irExpressionGenerateCall$default = CallGeneratorKt.generateCall$default(new CallGenerator(getStatementGenerator()), ktCondition, callBuilderPregenerateCallUsing, infixOperator, 0, 0, 24, null);
        if (Intrinsics.areEqual(infixOperator, IrStatementOrigin.IN.INSTANCE)) {
            return irExpressionGenerateCall$default;
        }
        if (Intrinsics.areEqual(infixOperator, IrStatementOrigin.NOT_IN.INSTANCE)) {
            return PrimitivesKt.primitiveOp1(startOffsetSkippingComments, endOffset, getContext().getIrBuiltIns().getBooleanNotSymbol(), getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.EXCL.INSTANCE, irExpressionGenerateCall$default);
        }
        throw new AssertionError("Expected 'in' or '!in', got " + infixOperator);
    }

    private final IrExpression generateEqualsCondition(IrVariable irSubject, KtWhenConditionWithExpression ktCondition) {
        KtExpression expression = ktCondition.getExpression();
        Intrinsics.checkNotNull(expression);
        IrExpression irExpressionGenExpr = genExpr(expression);
        KtWhenConditionWithExpression ktWhenConditionWithExpression = ktCondition;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktWhenConditionWithExpression);
        return new OperatorExpressionGenerator(getStatementGenerator()).generateEquality(startOffsetSkippingComments, PsiUtilsKt.getEndOffset(ktWhenConditionWithExpression), IrStatementOrigin.EQEQ.INSTANCE, IrUtilsKt.loadAt(irSubject, startOffsetSkippingComments, startOffsetSkippingComments), irExpressionGenExpr, (PrimitiveNumericComparisonInfo) getContext().getBindingContext().get(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, expression));
    }
}
