package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.IndexedGetHeaderInfo;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IndexedGetIterationHandlers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ$\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00022\b\u0010\u0015\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u00020\f*\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u00020\f*\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndexedGetIterationHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "canCacheLast", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Z)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getGetFunction", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "sizePropertyGetter", "getSizePropertyGetter", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IndexedGetIterationHandler implements HeaderInfoHandler {
    private final boolean canCacheLast;
    private final CommonBackendContext context;

    public abstract IrSimpleFunction getGetFunction(IrType irType);

    public abstract IrSimpleFunction getSizePropertyGetter(IrType irType);

    public IndexedGetIterationHandler(CommonBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.canCacheLast = z;
    }

    protected final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public HeaderInfo build(IrExpression expression, Void data, IrSymbol scopeOwner) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilderCreateIrBuilder.getScope(), expression, "indexedObject", false, (IrDeclarationOrigin) null, (IrType) null, 28, (Object) null);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder, getSizePropertyGetter(expression.getType()));
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(declarationIrBuilder, irVariableCreateTmpVariable$default));
        return new IndexedGetHeaderInfo(this.context.getIr().getSymbols(), ExpressionHelpersKt.irInt$default(declarationIrBuilder, 0, null, 2, null), irFunctionAccessExpressionIrCall, ExpressionHelpersKt.irInt$default(declarationIrBuilder, 1, null, 2, null), this.canCacheLast, irVariableCreateTmpVariable$default, this);
    }
}
