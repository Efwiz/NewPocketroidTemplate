package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrContinueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtExpressionWithLabel;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLoopExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilderKt;
import org.jetbrains.kotlin.psi2ir.intermediate.VariableLValue;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: LoopExpressionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0012\u0010!\u001a\u0004\u0018\u00010\u00062\u0006\u0010\"\u001a\u00020#H\u0002J\u0012\u0010$\u001a\u0004\u0018\u00010\n2\u0006\u0010\"\u001a\u00020#H\u0002¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/LoopExpressionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "findParentLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "targetLabel", "", "ktWithLabel", "Lorg/jetbrains/kotlin/psi/KtExpressionWithLabel;", "generateBreak", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ktBreak", "Lorg/jetbrains/kotlin/psi/KtBreakExpression;", "generateContinue", "ktContinue", "Lorg/jetbrains/kotlin/psi/KtContinueExpression;", "generateDoWhileLoop", "ktDoWhile", "Lorg/jetbrains/kotlin/psi/KtDoWhileExpression;", "generateDoWhileLoopBody", "ktLoopBody", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "generateForLoop", "ktFor", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "generateWhileLoop", "ktWhile", "Lorg/jetbrains/kotlin/psi/KtWhileExpression;", "generateWhileLoopBody", "getLoop", "ktLoop", "Lorg/jetbrains/kotlin/psi/KtLoopExpression;", "getLoopLabel", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoopExpressionGenerator extends StatementGeneratorExtension {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LoopExpressionGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
    }

    public final IrExpression generateWhileLoop(KtWhileExpression ktWhile) {
        IrExpression irExpressionGenExpr;
        Intrinsics.checkNotNullParameter(ktWhile, "ktWhile");
        KtWhileExpression ktWhileExpression = ktWhile;
        IrWhileLoopImpl irWhileLoopImpl = new IrWhileLoopImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktWhileExpression), PsiUtilsKt.getEndOffset(ktWhileExpression), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.WHILE_LOOP.INSTANCE);
        KtExpression condition = ktWhile.getCondition();
        Intrinsics.checkNotNull(condition);
        irWhileLoopImpl.setCondition(genExpr(condition));
        KtWhileExpression ktWhileExpression2 = ktWhile;
        getStatementGenerator().getBodyGenerator().putLoop(ktWhileExpression2, irWhileLoopImpl);
        irWhileLoopImpl.setLabel(getLoopLabel(ktWhileExpression2));
        KtExpression body = ktWhile.getBody();
        if (body == null) {
            irExpressionGenExpr = null;
        } else if (body instanceof KtBlockExpression) {
            irExpressionGenExpr = generateWhileLoopBody((KtBlockExpression) body);
        } else {
            irExpressionGenExpr = genExpr(body);
        }
        irWhileLoopImpl.setBody(irExpressionGenExpr);
        return irWhileLoopImpl;
    }

    public final IrExpression generateDoWhileLoop(KtDoWhileExpression ktDoWhile) {
        IrExpression irExpressionGenExpr;
        Intrinsics.checkNotNullParameter(ktDoWhile, "ktDoWhile");
        KtDoWhileExpression ktDoWhileExpression = ktDoWhile;
        IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktDoWhileExpression), PsiUtilsKt.getEndOffset(ktDoWhileExpression), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.DO_WHILE_LOOP.INSTANCE);
        KtDoWhileExpression ktDoWhileExpression2 = ktDoWhile;
        getStatementGenerator().getBodyGenerator().putLoop(ktDoWhileExpression2, irDoWhileLoopImpl);
        irDoWhileLoopImpl.setLabel(getLoopLabel(ktDoWhileExpression2));
        KtExpression body = ktDoWhile.getBody();
        if (body == null) {
            irExpressionGenExpr = null;
        } else if (body instanceof KtBlockExpression) {
            irExpressionGenExpr = generateDoWhileLoopBody((KtBlockExpression) body);
        } else {
            irExpressionGenExpr = genExpr(body);
        }
        irDoWhileLoopImpl.setBody(irExpressionGenExpr);
        KtExpression condition = ktDoWhile.getCondition();
        Intrinsics.checkNotNull(condition);
        irDoWhileLoopImpl.setCondition(genExpr(condition));
        IrBlockImpl irBlockImpl = new IrBlockImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktDoWhileExpression), PsiUtilsKt.getEndOffset(ktDoWhileExpression), getContext().getIrBuiltIns().getUnitType(), null, 8, null);
        irBlockImpl.getStatements().add(irDoWhileLoopImpl);
        return irBlockImpl;
    }

    private final IrExpression generateWhileLoopBody(KtBlockExpression ktLoopBody) {
        KtBlockExpression ktBlockExpression = ktLoopBody;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBlockExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktBlockExpression);
        IrType unitType = getContext().getIrBuiltIns().getUnitType();
        List<KtExpression> statements = ktLoopBody.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<KtExpression> list = statements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtExpression ktExpression : list) {
            Intrinsics.checkNotNull(ktExpression);
            arrayList.add(genStmt(ktExpression));
        }
        return new IrBlockImpl(startOffsetSkippingComments, endOffset, unitType, null, arrayList);
    }

    private final IrExpression generateDoWhileLoopBody(KtBlockExpression ktLoopBody) {
        KtBlockExpression ktBlockExpression = ktLoopBody;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBlockExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktBlockExpression);
        IrType unitType = getContext().getIrBuiltIns().getUnitType();
        List<KtExpression> statements = ktLoopBody.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<KtExpression> list = statements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtExpression ktExpression : list) {
            Intrinsics.checkNotNull(ktExpression);
            arrayList.add(genStmt(ktExpression));
        }
        return new IrCompositeImpl(startOffsetSkippingComments, endOffset, unitType, null, arrayList);
    }

    public final IrExpression generateBreak(KtBreakExpression ktBreak) {
        Intrinsics.checkNotNullParameter(ktBreak, "ktBreak");
        IrLoop irLoopFindParentLoop = findParentLoop(ktBreak);
        if (irLoopFindParentLoop == null) {
            return new ErrorExpressionGenerator(getStatementGenerator()).generateErrorExpression(ktBreak, new RuntimeException("Loop not found for break expression: " + ktBreak.getText()));
        }
        KtBreakExpression ktBreakExpression = ktBreak;
        IrBreakImpl irBreakImpl = new IrBreakImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktBreakExpression), PsiUtilsKt.getEndOffset(ktBreakExpression), getContext().getIrBuiltIns().getNothingType(), irLoopFindParentLoop);
        String label = irLoopFindParentLoop.getLabel();
        if (!Boolean.valueOf(ktBreak.getLabelName() != null).booleanValue()) {
            label = null;
        }
        irBreakImpl.setLabel(label);
        return irBreakImpl;
    }

    public final IrExpression generateContinue(KtContinueExpression ktContinue) {
        Intrinsics.checkNotNullParameter(ktContinue, "ktContinue");
        IrLoop irLoopFindParentLoop = findParentLoop(ktContinue);
        if (irLoopFindParentLoop == null) {
            return new ErrorExpressionGenerator(getStatementGenerator()).generateErrorExpression(ktContinue, new RuntimeException("Loop not found for continue expression: " + ktContinue.getText()));
        }
        KtContinueExpression ktContinueExpression = ktContinue;
        IrContinueImpl irContinueImpl = new IrContinueImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktContinueExpression), PsiUtilsKt.getEndOffset(ktContinueExpression), getContext().getIrBuiltIns().getNothingType(), irLoopFindParentLoop);
        String label = irLoopFindParentLoop.getLabel();
        if (!Boolean.valueOf(ktContinue.getLabelName() != null).booleanValue()) {
            label = null;
        }
        irContinueImpl.setLabel(label);
        return irContinueImpl;
    }

    private final String getLoopLabel(KtLoopExpression ktLoop) {
        PsiElement parent = ktLoop.getLightParent();
        KtLabeledExpression ktLabeledExpression = parent instanceof KtLabeledExpression ? (KtLabeledExpression) parent : null;
        if (ktLabeledExpression != null) {
            return ktLabeledExpression.getLabelName();
        }
        return null;
    }

    private final IrLoop findParentLoop(KtExpressionWithLabel ktWithLabel) {
        return findParentLoop(ktWithLabel, ktWithLabel.getLabelName());
    }

    private final IrLoop findParentLoop(KtExpression ktExpression, String targetLabel) {
        while (ktExpression != null) {
            ktExpression = (KtExpression) PsiTreeUtil.getParentOfType(ktExpression, KtLoopExpression.class, true);
            if (ktExpression == null) {
                return null;
            }
            if (targetLabel == null) {
                IrLoop loop = getLoop((KtLoopExpression) ktExpression);
                if (loop != null) {
                    return loop;
                }
            } else {
                KtLoopExpression ktLoopExpression = (KtLoopExpression) ktExpression;
                PsiElement parent = ktLoopExpression.getLightParent();
                while (parent instanceof KtLabeledExpression) {
                    KtLabeledExpression ktLabeledExpression = (KtLabeledExpression) parent;
                    String labelName = ktLabeledExpression.getLabelName();
                    Intrinsics.checkNotNull(labelName);
                    if (Intrinsics.areEqual(targetLabel, labelName)) {
                        IrLoop loop2 = getLoop(ktLoopExpression);
                        if (loop2 == null) {
                            break;
                        }
                        return loop2;
                    }
                    parent = ktLabeledExpression.getLightParent();
                }
            }
        }
        return null;
    }

    private final IrLoop getLoop(KtLoopExpression ktLoop) {
        return getStatementGenerator().getBodyGenerator().getLoop(ktLoop);
    }

    public final IrExpression generateForLoop(KtForExpression ktFor) {
        IrVariable irVariableCreateTemporaryVariable;
        Intrinsics.checkNotNullParameter(ktFor, "ktFor");
        KtParameter loopParameter = ktFor.getLoopParameter();
        KtDestructuringDeclaration destructuringDeclaration = ktFor.getDestructuringDeclaration();
        if (loopParameter == null && destructuringDeclaration == null) {
            throw new AssertionError("Either loopParameter or destructuringParameter should be present:\n" + ktFor.getText());
        }
        KtExpression loopRange = ktFor.getLoopRange();
        Intrinsics.checkNotNull(loopRange);
        KtExpression body = ktFor.getBody();
        LoopExpressionGenerator loopExpressionGenerator = this;
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_ITERATOR_RESOLVED_CALL = BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_ITERATOR_RESOLVED_CALL, "LOOP_RANGE_ITERATOR_RESOLVED_CALL");
        ResolvedCall resolvedCall = (ResolvedCall) GeneratorKt.getOrFail(loopExpressionGenerator, LOOP_RANGE_ITERATOR_RESOLVED_CALL, loopRange);
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_HAS_NEXT_RESOLVED_CALL = BindingContext.LOOP_RANGE_HAS_NEXT_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_HAS_NEXT_RESOLVED_CALL, "LOOP_RANGE_HAS_NEXT_RESOLVED_CALL");
        ResolvedCall resolvedCall2 = (ResolvedCall) GeneratorKt.getOrFail(loopExpressionGenerator, LOOP_RANGE_HAS_NEXT_RESOLVED_CALL, loopRange);
        WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_NEXT_RESOLVED_CALL = BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL;
        Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_NEXT_RESOLVED_CALL, "LOOP_RANGE_NEXT_RESOLVED_CALL");
        ResolvedCall resolvedCall3 = (ResolvedCall) GeneratorKt.getOrFail(loopExpressionGenerator, LOOP_RANGE_NEXT_RESOLVED_CALL, loopRange);
        CallGenerator callGenerator = new CallGenerator(getStatementGenerator());
        KtForExpression ktForExpression = ktFor;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktForExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktForExpression);
        IrBlockImpl irBlockImpl = new IrBlockImpl(startOffsetSkippingComments, endOffset, getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.FOR_LOOP.INSTANCE);
        StatementGenerator statementGenerator = getStatementGenerator();
        Intrinsics.checkNotNull(resolvedCall);
        KtExpression ktExpression = loopRange;
        IrExpression irExpressionGenerateCall$default = CallGeneratorKt.generateCall$default(callGenerator, ktExpression, ArgumentsGenerationUtilsKt.pregenerateCall(statementGenerator, resolvedCall), IrStatementOrigin.FOR_LOOP_ITERATOR.INSTANCE, 0, 0, 24, null);
        IrVariable irVariableCreateTemporaryVariable2 = getScope().createTemporaryVariable(irExpressionGenerateCall$default, (108 & 2) != 0 ? null : "iterator", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : IrDeclarationOrigin.FOR_LOOP_ITERATOR.INSTANCE, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionGenerateCall$default.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionGenerateCall$default.getEndOffset() : 0);
        VariableLValue variableLValue = new VariableLValue(getContext(), irVariableCreateTemporaryVariable2, null, 0, 0, 28, null);
        irBlockImpl.getStatements().add(irVariableCreateTemporaryVariable2);
        IrWhileLoopImpl irWhileLoopImpl = new IrWhileLoopImpl(startOffsetSkippingComments, endOffset, getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE);
        KtForExpression ktForExpression2 = ktFor;
        irWhileLoopImpl.setLabel(getLoopLabel(ktForExpression2));
        getStatementGenerator().getBodyGenerator().putLoop(ktForExpression2, irWhileLoopImpl);
        irBlockImpl.getStatements().add(irWhileLoopImpl);
        StatementGenerator statementGenerator2 = getStatementGenerator();
        Intrinsics.checkNotNull(resolvedCall2);
        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGenerator2, resolvedCall2);
        VariableLValue variableLValue2 = variableLValue;
        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall, variableLValue2);
        irWhileLoopImpl.setCondition(CallGeneratorKt.generateCall$default(callGenerator, ktExpression, callBuilderPregenerateCall, IrStatementOrigin.FOR_LOOP_HAS_NEXT.INSTANCE, 0, 0, 24, null));
        IrBlockImpl irBlockImpl2 = new IrBlockImpl(startOffsetSkippingComments, endOffset, getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE);
        irWhileLoopImpl.setBody(irBlockImpl2);
        StatementGenerator statementGenerator3 = getStatementGenerator();
        Intrinsics.checkNotNull(resolvedCall3);
        CallBuilder callBuilderPregenerateCall2 = ArgumentsGenerationUtilsKt.pregenerateCall(statementGenerator3, resolvedCall3);
        CallBuilderKt.setExplicitReceiverValue(callBuilderPregenerateCall2, variableLValue2);
        IrExpression irExpressionGenerateCall$default2 = CallGeneratorKt.generateCall$default(callGenerator, ktExpression, callBuilderPregenerateCall2, IrStatementOrigin.FOR_LOOP_NEXT.INSTANCE, 0, 0, 24, null);
        if (loopParameter == null || destructuringDeclaration != null) {
            irVariableCreateTemporaryVariable = getScope().createTemporaryVariable(irExpressionGenerateCall$default2, (108 & 2) != 0 ? null : "loop_parameter", (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpressionGenerateCall$default2.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionGenerateCall$default2.getEndOffset() : 0);
        } else {
            WritableSlice<KtParameter, VariableDescriptor> VALUE_PARAMETER = BindingContext.VALUE_PARAMETER;
            Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER, "VALUE_PARAMETER");
            VariableDescriptor variableDescriptor = (VariableDescriptor) GeneratorKt.getOrFail(loopExpressionGenerator, VALUE_PARAMETER, loopParameter);
            DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
            KtParameter ktParameter = loopParameter;
            int startOffsetSkippingComments2 = PsiUtilsKt.getStartOffsetSkippingComments(ktParameter);
            int endOffset2 = PsiUtilsKt.getEndOffset(ktParameter);
            IrDeclarationOrigin.FOR_LOOP_VARIABLE for_loop_variable = IrDeclarationOrigin.FOR_LOOP_VARIABLE.INSTANCE;
            Intrinsics.checkNotNull(variableDescriptor);
            KotlinType type = variableDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            irVariableCreateTemporaryVariable = descriptorExtension.declareVariable(startOffsetSkippingComments2, endOffset2, for_loop_variable, variableDescriptor, toIrType(type), irExpressionGenerateCall$default2);
        }
        irBlockImpl2.getStatements().add(irVariableCreateTemporaryVariable);
        if (destructuringDeclaration != null) {
            VariableLValue variableLValue3 = new VariableLValue(getContext(), irVariableCreateTemporaryVariable, null, 0, 0, 28, null);
            StatementGenerator statementGenerator4 = getStatementGenerator();
            IrBlockImpl irBlockImpl3 = irBlockImpl2;
            VariableLValue variableLValue4 = variableLValue3;
            if (getContext().getExtensions().getDebugInfoOnlyOnVariablesInDestructuringDeclarations()) {
                variableLValue3 = new VariableLValue(getContext(), irVariableCreateTemporaryVariable, null, -2, -2, 4, null);
            }
            statementGenerator4.declareComponentVariablesInBlock(destructuringDeclaration, irBlockImpl3, variableLValue4, variableLValue3);
        }
        if (body != null) {
            irBlockImpl2.getStatements().add(genExpr(body));
        }
        return irBlockImpl;
    }
}
