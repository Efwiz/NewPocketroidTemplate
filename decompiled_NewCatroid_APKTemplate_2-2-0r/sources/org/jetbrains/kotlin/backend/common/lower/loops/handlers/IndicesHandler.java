package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IndicesHandlers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\u00020\f*\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndicesHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "preferJavaLikeCounterLoop", "", "sizePropertyGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getSizePropertyGetter", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IndicesHandler implements HeaderInfoHandler<IrCall, ProgressionType> {
    private final CommonBackendContext context;
    private final boolean preferJavaLikeCounterLoop;

    public abstract IrSimpleFunction getSizePropertyGetter(IrType irType);

    public IndicesHandler(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.preferJavaLikeCounterLoop = context.getPreferJavaLikeCounterLoop();
    }

    protected final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public HeaderInfo build(IrCall expression, ProgressionType data, IrSymbol scopeOwner) {
        IrFunctionAccessExpression irFunctionAccessExpressionDecrement;
        boolean z;
        IrExpression irExpressionDecrement;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        if (this.preferJavaLikeCounterLoop) {
            IrValueParameter extensionReceiverParameter = expression.getSymbol().getOwner().getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder, getSizePropertyGetter(extensionReceiverParameter.getType()));
            IrExpression extensionReceiver = expression.getExtensionReceiver();
            Intrinsics.checkNotNull(extensionReceiver);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(extensionReceiver);
            irFunctionAccessExpressionDecrement = irFunctionAccessExpressionIrCall;
            irExpressionDecrement = UtilsKt.decrement(irFunctionAccessExpressionDecrement);
            z = false;
        } else {
            IrValueParameter extensionReceiverParameter2 = expression.getSymbol().getOwner().getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter2);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder, getSizePropertyGetter(extensionReceiverParameter2.getType()));
            IrExpression extensionReceiver2 = expression.getExtensionReceiver();
            Intrinsics.checkNotNull(extensionReceiver2);
            irFunctionAccessExpressionIrCall2.setDispatchReceiver(extensionReceiver2);
            irFunctionAccessExpressionDecrement = UtilsKt.decrement(irFunctionAccessExpressionIrCall2);
            z = true;
            irExpressionDecrement = null;
        }
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        return new ProgressionHeaderInfo(data, ExpressionHelpersKt.irInt$default(declarationIrBuilder, 0, null, 2, null), irFunctionAccessExpressionDecrement, ExpressionHelpersKt.irInt$default(declarationIrBuilder, 1, null, 2, null), z, false, false, ProgressionDirection.INCREASING, null, irExpressionDecrement, 288, null);
    }
}
