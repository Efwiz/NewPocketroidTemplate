package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhileLoopImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: IndexedGetLoopHeader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J<\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\r2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\r0\u001c2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0007H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/IndexedGetLoopHeader;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericForLoopHeader;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/IndexedGetHeaderInfo;", "headerInfo", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/IndexedGetHeaderInfo;Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "javaLikeCounterLoopBuilder", "Lorg/jetbrains/kotlin/backend/common/lower/loops/JavaLikeCounterLoopBuilder;", "loopInitStatements", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getLoopInitStatements", "()Ljava/util/List;", "preferJavaLikeCounterLoop", "", "buildLoop", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "oldLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "newBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializeIteration", "Lorg/jetbrains/kotlin/ir/IrStatement;", "loopVariable", "loopVariableComponents", "", "", "backendContext", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IndexedGetLoopHeader extends NumericForLoopHeader<IndexedGetHeaderInfo> {
    private final JavaLikeCounterLoopBuilder javaLikeCounterLoopBuilder;
    private final List<IrVariable> loopInitStatements;
    private final boolean preferJavaLikeCounterLoop;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IndexedGetLoopHeader(IndexedGetHeaderInfo headerInfo, DeclarationIrBuilder builder, CommonBackendContext context) {
        super(headerInfo, builder, context);
        Intrinsics.checkNotNullParameter(headerInfo, "headerInfo");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(context, "context");
        this.preferJavaLikeCounterLoop = context.getPreferJavaLikeCounterLoop();
        this.javaLikeCounterLoopBuilder = new JavaLikeCounterLoopBuilder(context);
        this.loopInitStatements = CollectionsKt.listOfNotNull((Object[]) new IrVariable[]{headerInfo.getObjectVariable(), getInductionVariable(), getLastVariableIfCanCacheLast(), getStepVariable()});
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrVariable> getLoopInitStatements() {
        return this.loopInitStatements;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrStatement> initializeIteration(IrVariable loopVariable, Map<Integer, ? extends IrVariable> loopVariableComponents, DeclarationIrBuilder builder, CommonBackendContext backendContext) {
        IrType returnType;
        Intrinsics.checkNotNullParameter(loopVariableComponents, "loopVariableComponents");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        IrSimpleFunction getFunction = getHeaderInfo().getExpressionHandler().getGetFunction(getHeaderInfo().getObjectVariable().getType());
        DeclarationIrBuilder declarationIrBuilder = builder;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(declarationIrBuilder, getFunction.getSymbol(), getFunction.getReturnType(), 0, 0, null, 28, null);
        irCallIrCall$default.setDispatchReceiver(ExpressionHelpersKt.irGet(declarationIrBuilder, getHeaderInfo().getObjectVariable()));
        irCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(declarationIrBuilder, getInductionVariable()));
        IrCall irCall = irCallIrCall$default;
        if (loopVariable == null || (returnType = loopVariable.getType()) == null) {
            returnType = getFunction.getReturnType();
        }
        IrExpression irExpressionImplicitCastIfNeededTo = IrUtilsKt.implicitCastIfNeededTo(irCall, returnType);
        if (loopVariable != null) {
            IrExpression initializer = loopVariable.getInitializer();
            loopVariable.setInitializer(initializer != null ? initializer.transform((IrElementTransformer<? super Object>) new InitializerCallReplacer(irExpressionImplicitCastIfNeededTo), (Object) null) : null);
        }
        return CollectionsKt.plus((Collection<? extends IrStatement>) CollectionsKt.listOf(loopVariable != null ? loopVariable : irExpressionImplicitCastIfNeededTo), incrementInductionVariable(builder));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public LoopReplacement buildLoop(DeclarationIrBuilder builder, IrLoop oldLoop, IrExpression newBody) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(oldLoop, "oldLoop");
        IrExpression irExpressionBuildLoopCondition = buildLoopCondition(builder);
        if (this.preferJavaLikeCounterLoop) {
            return this.javaLikeCounterLoopBuilder.buildJavaLikeDoWhileCounterLoop(oldLoop, irExpressionBuildLoopCondition, newBody, null);
        }
        IrWhileLoopImpl irWhileLoopImpl = new IrWhileLoopImpl(oldLoop.getStartOffset(), oldLoop.getEndOffset(), oldLoop.getType(), oldLoop.getOrigin());
        irWhileLoopImpl.setLabel(oldLoop.getLabel());
        irWhileLoopImpl.setCondition(irExpressionBuildLoopCondition);
        irWhileLoopImpl.setBody(newBody);
        return new LoopReplacement(irWhileLoopImpl, irWhileLoopImpl);
    }
}
