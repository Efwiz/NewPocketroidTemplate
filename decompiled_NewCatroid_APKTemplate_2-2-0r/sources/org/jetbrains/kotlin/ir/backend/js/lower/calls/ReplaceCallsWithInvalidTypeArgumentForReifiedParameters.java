package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: ReplaceCallsWithInvalidTypeArgumentForReifiedParameters.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/ReplaceCallsWithInvalidTypeArgumentForReifiedParameters;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "transformFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "doNotIntrinsify", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplaceCallsWithInvalidTypeArgumentForReifiedParameters implements CallsTransformer {
    private final JsIrBackendContext context;

    public ReplaceCallsWithInvalidTypeArgumentForReifiedParameters(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        if (!this.context.getErrorPolicy().getAllowErrors()) {
            return call;
        }
        for (IrTypeParameter irTypeParameter : call.getSymbol().getOwner().getTypeParameters()) {
            if (irTypeParameter.getIsReified()) {
                IrType typeArgument = call.getTypeArgument(irTypeParameter.getIndex());
                if ((typeArgument != null ? IrTypesKt.getClassOrNull(typeArgument) : null) == null) {
                    List<Pair<IrValueParameter, IrExpression>> argumentsWithIr = IrUtilsKt.getArgumentsWithIr(call);
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(argumentsWithIr, 10));
                    Iterator<T> it = argumentsWithIr.iterator();
                    while (it.hasNext()) {
                        arrayList.add((IrExpression) ((Pair) it.next()).getSecond());
                    }
                    ArrayList arrayList2 = arrayList;
                    JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                    IrSimpleFunctionSymbol errorCodeSymbol = this.context.getErrorCodeSymbol();
                    Intrinsics.checkNotNull(errorCodeSymbol);
                    IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(jsIrBuilder, errorCodeSymbol, null, null, null, null, 30, null);
                    irCallBuildCall$default.putValueArgument(0, IrConstImpl.INSTANCE.string(-1, -1, this.context.getIrBuiltIns().getStringType(), "Invalid type argument (" + (typeArgument != null ? RenderIrElementKt.render$default(typeArgument, (DumpIrTreeOptions) null, 1, (Object) null) : null) + ") for reified type parameter (" + RenderIrElementKt.render$default(irTypeParameter, (DumpIrTreeOptions) null, 1, (Object) null) + ')'));
                    return arrayList2.isEmpty() ? irCallBuildCall$default : new IrCompositeImpl(-1, -1, call.getType(), call.getOrigin(), CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOf(irCallBuildCall$default)));
                }
            }
        }
        return call;
    }
}
