package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: ProgressionLoopHeader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J<\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00110\u001c2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0007H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionLoopHeader;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericForLoopHeader;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionHeaderInfo;", "headerInfo", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionHeaderInfo;Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "javaLikeCounterLoopBuilder", "Lorg/jetbrains/kotlin/backend/common/lower/loops/JavaLikeCounterLoopBuilder;", "loopInitStatements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "getLoopInitStatements", "()Ljava/util/List;", "loopVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "preferJavaLikeCounterLoop", "", "buildLoop", "Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "oldLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "newBody", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "initializeIteration", "loopVariableComponents", "", "", "backendContext", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProgressionLoopHeader extends NumericForLoopHeader<ProgressionHeaderInfo> {
    private final JavaLikeCounterLoopBuilder javaLikeCounterLoopBuilder;
    private final List<IrStatement> loopInitStatements;
    private IrVariable loopVariable;
    private final boolean preferJavaLikeCounterLoop;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProgressionLoopHeader(ProgressionHeaderInfo headerInfo, DeclarationIrBuilder builder, CommonBackendContext context) {
        List listListOfNotNull;
        super(headerInfo, builder, context);
        Intrinsics.checkNotNullParameter(headerInfo, "headerInfo");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(context, "context");
        this.preferJavaLikeCounterLoop = context.getPreferJavaLikeCounterLoop();
        this.javaLikeCounterLoopBuilder = new JavaLikeCounterLoopBuilder(context);
        List<IrStatement> additionalStatements = headerInfo.getAdditionalStatements();
        if (headerInfo.getIsReversed()) {
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new IrVariable[]{getLastVariableIfCanCacheLast(), getInductionVariable()});
        } else {
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new IrVariable[]{getInductionVariable(), getLastVariableIfCanCacheLast()});
        }
        this.loopInitStatements = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) additionalStatements, (Iterable) listListOfNotNull), (Iterable) CollectionsKt.listOfNotNull(getStepVariable()));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrStatement> getLoopInitStatements() {
        return this.loopInitStatements;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public List<IrStatement> initializeIteration(IrVariable loopVariable, Map<Integer, ? extends IrVariable> loopVariableComponents, DeclarationIrBuilder builder, CommonBackendContext backendContext) {
        IrGetValueImpl irGetValueImplAsUnsigned;
        Intrinsics.checkNotNullParameter(loopVariableComponents, "loopVariableComponents");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        if (getHeaderInfo().getCanOverflow() && loopVariable == null) {
            loopVariable = IrBuilderKt.createTmpVariable$default(builder.getScope(), (IrExpression) ExpressionHelpersKt.irGet(builder, getInductionVariable()), "loopVariable", true, (IrDeclarationOrigin) null, (IrType) null, 24, (Object) null);
        } else if (loopVariable != null) {
            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(builder, getInductionVariable());
            ProgressionType progressionType = getHeaderInfo().getProgressionType();
            if (progressionType instanceof UnsignedProgressionType) {
                irGetValueImplAsUnsigned = ((UnsignedProgressionType) progressionType).asUnsigned(irGetValueImplIrGet);
            } else {
                irGetValueImplAsUnsigned = irGetValueImplIrGet;
            }
            loopVariable.setInitializer(irGetValueImplAsUnsigned);
        }
        this.loopVariable = loopVariable;
        return CollectionsKt.listOfNotNull((Object[]) new IrStatement[]{loopVariable, incrementInductionVariable(builder)});
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public LoopReplacement buildLoop(DeclarationIrBuilder builder, IrLoop oldLoop, IrExpression newBody) {
        IrStatementOrigin origin;
        IrGetValueImpl irGetValueImplAsSigned;
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(oldLoop, "oldLoop");
        if (getHeaderInfo().getCanOverflow() || (this.preferJavaLikeCounterLoop && (getHeaderInfo().getProgressionType() instanceof UnsignedProgressionType) && getHeaderInfo().getIsLastInclusive())) {
            if (this.preferJavaLikeCounterLoop) {
                origin = getContext().getDoWhileCounterLoopOrigin();
            } else {
                origin = oldLoop.getOrigin();
            }
            IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(oldLoop.getStartOffset(), oldLoop.getEndOffset(), oldLoop.getType(), origin);
            DeclarationIrBuilder declarationIrBuilder = builder;
            IrVariable irVariable = this.loopVariable;
            Intrinsics.checkNotNull(irVariable);
            IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable);
            ProgressionType progressionType = getHeaderInfo().getProgressionType();
            if (progressionType instanceof UnsignedProgressionType) {
                irGetValueImplAsSigned = ((UnsignedProgressionType) progressionType).asSigned(irGetValueImplIrGet);
            } else {
                irGetValueImplAsSigned = irGetValueImplIrGet;
            }
            irDoWhileLoopImpl.setLabel(oldLoop.getLabel());
            irDoWhileLoopImpl.setCondition(ExpressionHelpersKt.irNotEquals(declarationIrBuilder, irGetValueImplAsSigned, getLastExpression()));
            irDoWhileLoopImpl.setBody(newBody);
            if (this.preferJavaLikeCounterLoop) {
                this.javaLikeCounterLoopBuilder.moveInductionVariableUpdateToLoopCondition(irDoWhileLoopImpl);
            }
            return new LoopReplacement(irDoWhileLoopImpl, LowerUtilsKt.irIfThen(declarationIrBuilder, buildLoopCondition(builder), irDoWhileLoopImpl));
        }
        if (this.preferJavaLikeCounterLoop && !getHeaderInfo().getIsLastInclusive()) {
            return this.javaLikeCounterLoopBuilder.buildJavaLikeDoWhileCounterLoop(oldLoop, buildLoopCondition(builder), newBody, getContext().getDoWhileCounterLoopOrigin());
        }
        IrDoWhileLoopImpl irDoWhileLoopImpl2 = new IrDoWhileLoopImpl(oldLoop.getStartOffset(), oldLoop.getEndOffset(), oldLoop.getType(), oldLoop.getOrigin());
        irDoWhileLoopImpl2.setLabel(oldLoop.getLabel());
        irDoWhileLoopImpl2.setCondition(buildLoopCondition(builder));
        irDoWhileLoopImpl2.setBody(newBody);
        return new LoopReplacement(irDoWhileLoopImpl2, LowerUtilsKt.irIfThen(builder, buildLoopCondition(builder), irDoWhileLoopImpl2));
    }
}
