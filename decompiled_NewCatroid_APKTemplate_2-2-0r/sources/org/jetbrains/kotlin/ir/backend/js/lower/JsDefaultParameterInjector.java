package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JsDefaultParameterInjector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0014J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J*\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\f\u0010\u0016\u001a\u00020\r*\u00020\u000bH\u0002J\f\u0010\u0017\u001a\u00020\r*\u00020\u0015H\u0002J\f\u0010\u0018\u001a\u00020\u0006*\u00020\u0019H\u0002¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsDefaultParameterInjector;", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultParameterInjector;", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "context", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "nullConst", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "irParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "shouldReplaceWithSyntheticFunction", "", "functionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "argumentsForCall", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "expression", "stubFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "hasDefaultValue", "hasSuperContextParameter", "prototypeOf", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDefaultParameterInjector extends DefaultParameterInjector<JsIrBackendContext> {
    /* JADX WARN: Illegal instructions before constructor call */
    public JsDefaultParameterInjector(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JsIrBackendContext jsIrBackendContext = context;
        super(jsIrBackendContext, new JsDefaultArgumentFunctionFactory(jsIrBackendContext), false, true, false, 4, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected IrExpression nullConst(int startOffset, int endOffset, IrValueParameter irParameter) {
        Intrinsics.checkNotNullParameter(irParameter, "irParameter");
        if (!AdditionalIrUtilsKt.isVararg(irParameter) || hasDefaultValue(irParameter)) {
            return IrJsUtilsKt.getVoid(getContext());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected boolean shouldReplaceWithSyntheticFunction(IrFunctionAccessExpression functionAccess) {
        Intrinsics.checkNotNullParameter(functionAccess, "functionAccess");
        if (!super.shouldReplaceWithSyntheticFunction(functionAccess)) {
            IrFunction owner = functionAccess.getSymbol().getOwner();
            if (Intrinsics.areEqual(owner.getOrigin(), JsLoweredDeclarationOrigin.JS_SHADOWED_EXPORT.INSTANCE)) {
                IrFunction irFunction = owner;
                if (IrUtilsKt.isTopLevel(irFunction) || Intrinsics.areEqual(functionAccess.getOrigin(), JsStatementOrigins.IMPLEMENTATION_DELEGATION_CALL.INSTANCE) || !ExportModelGeneratorKt.isExported(irFunction, getContext())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector
    protected Map<IrValueParameter, IrExpression> argumentsForCall(IrBlockBuilder irBlockBuilder, IrFunctionAccessExpression expression, IrFunction stubFunction) {
        IrExpression irExpressionPrototypeOf;
        Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(stubFunction, "stubFunction");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        IrValueParameter dispatchReceiverParameter = stubFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
        }
        IrValueParameter extensionReceiverParameter = stubFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
        }
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrValueParameter irValueParameter = stubFunction.getValueParameters().get(i);
            IrExpression valueArgument = expression.getValueArgument(i);
            if (valueArgument == null) {
                valueArgument = nullConst(startOffset, endOffset, irValueParameter);
            }
            mapCreateMapBuilder.put(irValueParameter, valueArgument);
        }
        if ((expression instanceof IrCall) && hasSuperContextParameter(stubFunction)) {
            IrValueParameter irValueParameter2 = stubFunction.getValueParameters().get(expression.getValueArgumentsCount());
            IrClassSymbol superQualifierSymbol = ((IrCall) expression).getSuperQualifierSymbol();
            if (superQualifierSymbol == null || (irExpressionPrototypeOf = prototypeOf(superQualifierSymbol)) == null) {
                irExpressionPrototypeOf = IrJsUtilsKt.getVoid(getContext());
            }
            mapCreateMapBuilder.put(irValueParameter2, irExpressionPrototypeOf);
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    private final boolean hasSuperContextParameter(IrFunction irFunction) {
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irFunction.getValueParameters());
        return Intrinsics.areEqual(irValueParameter != null ? irValueParameter.getOrigin() : null, JsLoweredDeclarationOrigin.JS_SUPER_CONTEXT_PARAMETER.INSTANCE);
    }

    private final IrExpression prototypeOf(IrClassSymbol irClassSymbol) {
        IrCallImpl irCallImpl = new IrCallImpl(-1, -1, getContext().getDynamicType(), getContext().getIntrinsics().getJsPrototypeOfSymbol(), 0, 1, null, null, 192, null);
        irCallImpl.putValueArgument(0, IrJsUtilsKt.jsConstructorReference(irClassSymbol.getOwner(), getContext()));
        return irCallImpl;
    }

    private final boolean hasDefaultValue(IrValueParameter irValueParameter) {
        return Intrinsics.areEqual(irValueParameter.getOrigin(), JsLoweredDeclarationOrigin.JS_SHADOWED_DEFAULT_PARAMETER.INSTANCE);
    }
}
