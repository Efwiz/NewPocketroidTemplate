package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IterableLoopHeader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J<\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\n2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\u001a2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/IterableLoopHeader;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "headerInfo", "Lorg/jetbrains/kotlin/backend/common/lower/loops/IterableHeaderInfo;", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/IterableHeaderInfo;)V", "consumesLoopVariableComponents", "", "getConsumesLoopVariableComponents", "()Z", "loopInitStatements", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getLoopInitStatements", "()Ljava/util/List;", "buildLoop", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "oldLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "newBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializeIteration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "loopVariable", "loopVariableComponents", "", "", "backendContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IterableLoopHeader implements ForLoopHeader {
    private final boolean consumesLoopVariableComponents;
    private final IterableHeaderInfo headerInfo;
    private final List<IrVariable> loopInitStatements;

    public IterableLoopHeader(IterableHeaderInfo headerInfo) {
        Intrinsics.checkNotNullParameter(headerInfo, "headerInfo");
        this.headerInfo = headerInfo;
        this.loopInitStatements = CollectionsKt.listOf(headerInfo.getIteratorVariable());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrVariable> getLoopInitStatements() {
        return this.loopInitStatements;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public boolean getConsumesLoopVariableComponents() {
        return this.consumesLoopVariableComponents;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [org.jetbrains.kotlin.ir.declarations.IrVariable] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v7, types: [org.jetbrains.kotlin.ir.expressions.IrExpression] */
    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrStatement> initializeIteration(IrVariable loopVariable, Map<Integer, ? extends IrVariable> loopVariableComponents, DeclarationIrBuilder builder, CommonBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(loopVariableComponents, "loopVariableComponents");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        IrClass irClass = IrTypesKt.getClass(this.headerInfo.getIteratorVariable().getType());
        Intrinsics.checkNotNull(irClass);
        DeclarationIrBuilder declarationIrBuilder = builder;
        for (IrSimpleFunction irSimpleFunction : IrUtilsKt.getFunctions(irClass)) {
            if (Intrinsics.areEqual(irSimpleFunction.getName(), OperatorNameConventions.NEXT) && irSimpleFunction.getValueParameters().isEmpty()) {
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunction.getSymbol());
                irCall.setDispatchReceiver(ExpressionHelpersKt.irGet(declarationIrBuilder, this.headerInfo.getIteratorVariable()));
                if (loopVariable != 0) {
                    IrExpression initializer = loopVariable.getInitializer();
                    loopVariable.setInitializer(initializer != null ? initializer.transform((IrElementTransformer<? super Object>) new InitializerCallReplacer(irCall), (Object) null) : null);
                }
                if (loopVariable == 0) {
                    loopVariable = IrUtilsKt.coerceToUnitIfNeeded(irCall, irCall.getType(), builder.getContext().getIrBuiltIns(), backendContext.getTypeSystem());
                }
                return CollectionsKt.listOf((IrElement) loopVariable);
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public LoopReplacement buildLoop(DeclarationIrBuilder builder, IrLoop oldLoop, IrExpression newBody) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(oldLoop, "oldLoop");
        IrClass irClass = IrTypesKt.getClass(this.headerInfo.getIteratorVariable().getType());
        Intrinsics.checkNotNull(irClass);
        DeclarationIrBuilder declarationIrBuilder = builder;
        for (IrSimpleFunction irSimpleFunction : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
            if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.HAS_NEXT) && irSimpleFunction2.getValueParameters().isEmpty()) {
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, irSimpleFunction);
                irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(declarationIrBuilder, this.headerInfo.getIteratorVariable()));
                IrWhileLoopImpl irWhileLoopImpl = new IrWhileLoopImpl(oldLoop.getStartOffset(), oldLoop.getEndOffset(), oldLoop.getType(), oldLoop.getOrigin());
                irWhileLoopImpl.setLabel(oldLoop.getLabel());
                irWhileLoopImpl.setCondition(irFunctionAccessExpressionIrCall);
                irWhileLoopImpl.setBody(newBody);
                return new LoopReplacement(irWhileLoopImpl, irWhileLoopImpl);
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }
}
