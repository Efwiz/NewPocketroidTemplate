package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IndicesHandlers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/CollectionIndicesHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndicesHandler;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "sizePropertyGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getSizePropertyGetter", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "matchIterable", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionIndicesHandler extends IndicesHandler {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectionIndicesHandler(CommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        IrValueParameter extensionReceiverParameter;
        IrType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        return owner.getValueParameters().isEmpty() && (extensionReceiverParameter = owner.getExtensionReceiverParameter()) != null && (type = extensionReceiverParameter.getType()) != null && IrTypePredicatesKt.isCollection(type) && Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName("kotlin.collections.<get-indices>"));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndicesHandler
    public IrSimpleFunction getSizePropertyGetter(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(getContext().getIr().getSymbols().getCollection(), "size");
        Intrinsics.checkNotNull(propertyGetter);
        return propertyGetter.getOwner();
    }
}
