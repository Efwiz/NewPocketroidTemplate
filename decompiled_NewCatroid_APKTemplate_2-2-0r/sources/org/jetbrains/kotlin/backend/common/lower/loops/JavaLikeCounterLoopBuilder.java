package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBreakImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: JavaLikeCounterLoopBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/JavaLikeCounterLoopBuilder;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "booleanNot", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "buildJavaLikeDoWhileCounterLoop", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "oldLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "newLoopCondition", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "newBody", "loopOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "createNegatedConditionCheck", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrWhenImpl;", "doWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "moveInductionVariableUpdateToLoopCondition", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaLikeCounterLoopBuilder {
    private final IrSimpleFunction booleanNot;
    private final CommonBackendContext context;

    public JavaLikeCounterLoopBuilder(CommonBackendContext context) {
        Object next;
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Iterator<T> it = context.getIrBuiltIns().getBooleanClass().getOwner().getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if ((irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getName(), OperatorNameConventions.NOT)) {
                break;
            }
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) (next instanceof IrSimpleFunction ? next : null);
        if (irSimpleFunction == null) {
            throw new IllegalStateException(("No '" + OperatorNameConventions.NOT + "' in " + RenderIrElementKt.render$default(this.context.getIrBuiltIns().getBooleanClass().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        this.booleanNot = irSimpleFunction;
    }

    public final LoopReplacement buildJavaLikeDoWhileCounterLoop(IrLoop oldLoop, IrExpression newLoopCondition, IrExpression newBody, IrStatementOrigin loopOrigin) {
        Intrinsics.checkNotNullParameter(oldLoop, "oldLoop");
        Intrinsics.checkNotNullParameter(newLoopCondition, "newLoopCondition");
        IrContainerExpression irContainerExpression = newBody instanceof IrContainerExpression ? (IrContainerExpression) newBody : null;
        if (irContainerExpression == null) {
            throw new AssertionError("newBody: " + (newBody != null ? DumpIrTreeKt.dump$default(newBody, null, 1, null) : null));
        }
        Iterator<IrStatement> it = irContainerExpression.getStatements().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            IrStatement next = it.next();
            if ((next instanceof IrContainerExpression) && Intrinsics.areEqual(((IrContainerExpression) next).getOrigin(), IrStatementOrigin.FOR_LOOP_NEXT.INSTANCE)) {
                break;
            }
            i++;
        }
        if (i < 0) {
            throw new AssertionError("No FOR_LOOP_NEXT block in bodyBlock: " + DumpIrTreeKt.dump$default(irContainerExpression, null, 1, null));
        }
        IrStatement irStatement = irContainerExpression.getStatements().get(i);
        Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrContainerExpression");
        IrContainerExpression irContainerExpression2 = (IrContainerExpression) irStatement;
        Object objLast = CollectionsKt.last((List<? extends Object>) irContainerExpression2.getStatements());
        IrSetValue irSetValue = objLast instanceof IrSetValue ? (IrSetValue) objLast : null;
        if (irSetValue == null) {
            throw new AssertionError("forLoopNextBlock.last: " + DumpIrTreeKt.dump$default((IrElement) CollectionsKt.last((List) irContainerExpression2.getStatements()), null, 1, null));
        }
        IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(oldLoop.getStartOffset(), oldLoop.getEndOffset(), oldLoop.getType(), loopOrigin);
        irDoWhileLoopImpl.setLabel(oldLoop.getLabel());
        List<IrStatement> statements = irContainerExpression.getStatements();
        IrCompositeImpl irCompositeImpl = new IrCompositeImpl(irContainerExpression2.getStartOffset(), irContainerExpression2.getEndOffset(), irContainerExpression2.getType(), irContainerExpression2.getOrigin());
        irCompositeImpl.getStatements().add(createNegatedConditionCheck(newLoopCondition, irDoWhileLoopImpl));
        if (irContainerExpression2.getStatements().size() >= 2) {
            irCompositeImpl.getStatements().addAll(irContainerExpression2.getStatements().subList(0, CollectionsKt.getLastIndex(irContainerExpression2.getStatements())));
        }
        Unit unit = Unit.INSTANCE;
        statements.set(i, irCompositeImpl);
        irDoWhileLoopImpl.setBody(irContainerExpression);
        int startOffset = irSetValue.getStartOffset();
        int endOffset = irSetValue.getEndOffset();
        irDoWhileLoopImpl.setCondition(new IrCompositeImpl(startOffset, endOffset, this.context.getIrBuiltIns().getBooleanType(), null, CollectionsKt.listOf((Object[]) new IrExpression[]{irSetValue, IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, this.context.getIrBuiltIns().getBooleanType(), true)})));
        return new LoopReplacement(irDoWhileLoopImpl, irDoWhileLoopImpl);
    }

    private final IrWhenImpl createNegatedConditionCheck(IrExpression newLoopCondition, IrDoWhileLoop doWhileLoop) {
        int startOffset = newLoopCondition.getStartOffset();
        int endOffset = newLoopCondition.getEndOffset();
        IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(startOffset, endOffset, this.booleanNot.getSymbol());
        irCallImplFromSymbolOwner.setDispatchReceiver(newLoopCondition);
        return new IrWhenImpl(startOffset, endOffset, this.context.getIrBuiltIns().getUnitType(), null, CollectionsKt.listOf(new IrBranchImpl(irCallImplFromSymbolOwner, new IrBreakImpl(startOffset, endOffset, this.context.getIrBuiltIns().getNothingType(), doWhileLoop))));
    }

    public final void moveInductionVariableUpdateToLoopCondition(IrDoWhileLoop doWhileLoop) {
        Intrinsics.checkNotNullParameter(doWhileLoop, "doWhileLoop");
        IrExpression body = doWhileLoop.getBody();
        IrContainerExpression irContainerExpression = body instanceof IrContainerExpression ? (IrContainerExpression) body : null;
        if (irContainerExpression != null && Intrinsics.areEqual(irContainerExpression.getOrigin(), IrStatementOrigin.FOR_LOOP_INNER_WHILE.INSTANCE)) {
            IrStatement irStatement = irContainerExpression.getStatements().get(0);
            IrContainerExpression irContainerExpression2 = irStatement instanceof IrContainerExpression ? (IrContainerExpression) irStatement : null;
            if (irContainerExpression2 != null && Intrinsics.areEqual(irContainerExpression2.getOrigin(), IrStatementOrigin.FOR_LOOP_NEXT.INSTANCE)) {
                Iterator<IrStatement> it = irContainerExpression2.getStatements().iterator();
                int i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    }
                    IrStatement next = it.next();
                    if ((next instanceof IrSetValue) && HeaderProcessorKt.isInductionVariable(((IrSetValue) next).getSymbol().getOwner(), this.context)) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                IrStatement irStatement2 = irContainerExpression2.getStatements().get(i);
                irContainerExpression2.getStatements().remove(i);
                IrExpression condition = doWhileLoop.getCondition();
                int startOffset = condition.getStartOffset();
                int endOffset = condition.getEndOffset();
                doWhileLoop.setCondition(new IrCompositeImpl(startOffset, endOffset, condition.getType(), null, CollectionsKt.listOf((Object[]) new IrStatement[]{createNegatedConditionCheck(doWhileLoop.getCondition(), doWhileLoop), irStatement2, IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, this.context.getIrBuiltIns().getBooleanType(), true)})));
            }
        }
    }
}
