package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IndexedGetIterationHandlers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\b*\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/CharSequenceIterationHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndexedGetIterationHandler;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "canCacheLast", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Z)V", "getFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getGetFunction", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "sizePropertyGetter", "getSizePropertyGetter", "matchIterable", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "matchIteratorCall", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CharSequenceIterationHandler extends IndexedGetIterationHandler {
    public /* synthetic */ CharSequenceIterationHandler(CommonBackendContext commonBackendContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CharSequenceIterationHandler(CommonBackendContext context, boolean z) {
        super(context, z);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return IrTypeUtilsKt.isSubtypeOfClass(expression.getType(), getContext().getIr().getSymbols().getCharSequence());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIteratorCall(IrCall call) {
        IrValueParameter extensionReceiverParameter;
        IrType type;
        Intrinsics.checkNotNullParameter(call, "call");
        IrSimpleFunction owner = call.getSymbol().getOwner();
        return owner.getValueParameters().isEmpty() && (extensionReceiverParameter = owner.getExtensionReceiverParameter()) != null && (type = extensionReceiverParameter.getType()) != null && IrTypePredicatesKt.isCharSequence(type) && Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName(new StringBuilder("kotlin.text.").append(OperatorNameConventions.ITERATOR).toString()));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler
    public IrSimpleFunction getSizePropertyGetter(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(getContext().getIr().getSymbols().getCharSequence(), "length");
        Intrinsics.checkNotNull(propertyGetter);
        return propertyGetter.getOwner();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler
    public IrSimpleFunction getGetFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol charSequence = getContext().getIr().getSymbols().getCharSequence();
        String strAsString = OperatorNameConventions.GET.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrSimpleFunctionSymbol simpleFunction = AdditionalIrUtilsKt.getSimpleFunction(charSequence, strAsString);
        Intrinsics.checkNotNull(simpleFunction);
        return simpleFunction.getOwner();
    }
}
