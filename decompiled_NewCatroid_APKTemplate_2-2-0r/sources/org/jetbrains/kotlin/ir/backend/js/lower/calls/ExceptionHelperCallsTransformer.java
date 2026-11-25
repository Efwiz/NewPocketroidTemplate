package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ExceptionHelperCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/ExceptionHelperCallsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "helperMapping", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "referenceFunction", "fqn", "Lorg/jetbrains/kotlin/name/FqName;", "transformFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "call", "doNotIntrinsify", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExceptionHelperCallsTransformer implements CallsTransformer {
    private final JsIrBackendContext context;
    private final Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> helperMapping;

    public ExceptionHelperCallsTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        IrSimpleFunctionSymbol checkNotNullSymbol = context.getIrBuiltIns().getCheckNotNullSymbol();
        FqName fqNameChild = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(Namer.NULL_CHECK_INTRINSIC_NAME));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        IrSimpleFunctionSymbol throwCceSymbol = context.getIrBuiltIns().getThrowCceSymbol();
        FqName fqNameChild2 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(BuiltInOperatorNames.THROW_CCE));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        IrSimpleFunctionSymbol throwIseSymbol = context.getIrBuiltIns().getThrowIseSymbol();
        FqName fqNameChild3 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(BuiltInOperatorNames.THROW_ISE));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        IrSimpleFunctionSymbol illegalArgumentExceptionSymbol = context.getIrBuiltIns().getIllegalArgumentExceptionSymbol();
        FqName fqNameChild4 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier("THROW_IAE"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
        IrSimpleFunctionSymbol noWhenBranchMatchedExceptionSymbol = context.getIrBuiltIns().getNoWhenBranchMatchedExceptionSymbol();
        FqName fqNameChild5 = IrTypeUtilsKt.getKotlinPackageFqn().child(Name.identifier(BuiltInOperatorNames.NO_WHEN_BRANCH_MATCHED_EXCEPTION));
        Intrinsics.checkNotNullExpressionValue(fqNameChild5, "child(...)");
        this.helperMapping = MapsKt.mapOf(TuplesKt.to(checkNotNullSymbol, referenceFunction(fqNameChild)), TuplesKt.to(throwCceSymbol, referenceFunction(fqNameChild2)), TuplesKt.to(throwIseSymbol, referenceFunction(fqNameChild3)), TuplesKt.to(illegalArgumentExceptionSymbol, referenceFunction(fqNameChild4)), TuplesKt.to(noWhenBranchMatchedExceptionSymbol, referenceFunction(fqNameChild5)), TuplesKt.to(context.getIrBuiltIns().getLinkageErrorSymbol(), context.getIntrinsics().getLinkageErrorSymbol()));
    }

    private final IrSimpleFunctionSymbol referenceFunction(FqName fqn) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction;
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.singleOrNull((List) this.context.getFunctions(fqn));
        if (simpleFunctionDescriptor == null || (irSimpleFunctionSymbolReferenceSimpleFunction = this.context.getSymbolTable().getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor)) == null) {
            throw new AssertionError("Function not found: " + fqn);
        }
        return irSimpleFunctionSymbolReferenceSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrFunctionAccessExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        IrCall irCall;
        Intrinsics.checkNotNullParameter(call, "call");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.helperMapping.get(call.getSymbol());
        return (irSimpleFunctionSymbol == null || (irCall = IrUtilsKt.irCall(call, irSimpleFunctionSymbol, (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : null, (12 & 32) != 0 ? null : null)) == null) ? call : irCall;
    }
}
