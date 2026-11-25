package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: IndexedGetIterationHandlers.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\f\u0010\u0015\u001a\u00020\u0006*\u00020\tH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\r*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\b*\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/ArrayIterationHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/IndexedGetIterationHandler;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "supportsUnsignedArrays", "", "getFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getGetFunction", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctionName", "Lorg/jetbrains/kotlin/name/Name;", "getGetFunctionName", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/name/Name;", "sizePropertyGetter", "getSizePropertyGetter", "matchIterable", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isArrayType", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayIterationHandler extends IndexedGetIterationHandler {
    private final boolean supportsUnsignedArrays;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArrayIterationHandler(CommonBackendContext context) {
        super(context, true);
        Intrinsics.checkNotNullParameter(context, "context");
        this.supportsUnsignedArrays = context.getOptimizeLoopsOverUnsignedArrays();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrExpression expression) {
        IrSimpleFunctionSymbol symbol;
        IrSimpleFunction owner;
        IrValueParameter extensionReceiverParameter;
        IrType type;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (isArrayType(expression.getType())) {
            return true;
        }
        IrCall irCall = expression instanceof IrCall ? (IrCall) expression : null;
        if (irCall == null || (symbol = irCall.getSymbol()) == null || (owner = symbol.getOwner()) == null) {
            return false;
        }
        return owner.getValueParameters().isEmpty() && (extensionReceiverParameter = owner.getExtensionReceiverParameter()) != null && (type = extensionReceiverParameter.getType()) != null && (IrTypePredicatesKt.isArray(type) || IrTypeUtilsKt.isPrimitiveArray(type)) && Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName("kotlin.collections.reversed"));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler
    public IrSimpleFunction getSizePropertyGetter(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClass irClass = IrTypesKt.getClass(irType);
        Intrinsics.checkNotNull(irClass);
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(irClass, "size");
        Intrinsics.checkNotNull(propertyGetter);
        return propertyGetter.getOwner();
    }

    private final boolean isArrayType(IrType irType) {
        return IrTypePredicatesKt.isArray(irType) || IrTypeUtilsKt.isPrimitiveArray(irType) || (this.supportsUnsignedArrays && IrTypeUtilsKt.isUnsignedArray(irType));
    }

    private final Name getGetFunctionName(IrType irType) {
        Name getWithoutBoundCheckName = getContext().getIr().getSymbols().getGetWithoutBoundCheckName();
        return (!isArrayType(irType) || getWithoutBoundCheckName == null) ? OperatorNameConventions.GET : getWithoutBoundCheckName;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.handlers.IndexedGetIterationHandler
    public IrSimpleFunction getGetFunction(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClass irClass = IrTypesKt.getClass(irType);
        Intrinsics.checkNotNull(irClass);
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
            if (Intrinsics.areEqual(irSimpleFunction3.getName(), getGetFunctionName(irType)) && irSimpleFunction3.getValueParameters().size() == 1 && IrTypePredicatesKt.isInt(irSimpleFunction3.getValueParameters().get(0).getType())) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunction = irSimpleFunction2;
                z = true;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irSimpleFunction;
    }
}
