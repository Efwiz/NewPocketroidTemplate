package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IndexedGetIterationHandlers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00020\u0006*\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/StringIterationHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/CharSequenceIterationHandler;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getGetFunction", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "sizePropertyGetter", "getSizePropertyGetter", "matchIterable", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringIterationHandler extends CharSequenceIterationHandler {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringIterationHandler(CommonBackendContext context) {
        super(context, true);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.CharSequenceIterationHandler, org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return IrTypePredicatesKt.isString(expression.getType());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.CharSequenceIterationHandler, org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler
    public IrSimpleFunction getSizePropertyGetter(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(getContext().getIr().getSymbols().getString(), "length");
        Intrinsics.checkNotNull(propertyGetter);
        return propertyGetter.getOwner();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.CharSequenceIterationHandler, org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler
    public IrSimpleFunction getGetFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol string = getContext().getIr().getSymbols().getString();
        String strAsString = OperatorNameConventions.GET.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(string, strAsString);
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction.getOwner();
    }
}
