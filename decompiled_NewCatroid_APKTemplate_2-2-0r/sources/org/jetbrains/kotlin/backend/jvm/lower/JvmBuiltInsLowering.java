package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: JvmBuiltInsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u0010\u0014\u001a\u00020\u0011*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000bH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R&\u0010\u0007\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\t\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmBuiltInsLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "jvm8builtInReplacements", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "coerceIfPossible", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "toType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "replaceWithCallTo", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "replacement", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBuiltInsLowering implements FileLoweringPass {
    private final JvmBackendContext context;
    private final Map<Pair<String, String>, IrSimpleFunctionSymbol> jvm8builtInReplacements;

    public JvmBuiltInsLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.jvm8builtInReplacements = MapsKt.mapOf(TuplesKt.to(TuplesKt.to("kotlin.UInt", "compareTo"), context.getIr().getSymbols().getCompareUnsignedInt()), TuplesKt.to(TuplesKt.to("kotlin.UInt", "div"), context.getIr().getSymbols().getDivideUnsignedInt()), TuplesKt.to(TuplesKt.to("kotlin.UInt", "rem"), context.getIr().getSymbols().getRemainderUnsignedInt()), TuplesKt.to(TuplesKt.to("kotlin.UInt", "toString"), context.getIr().getSymbols().getToUnsignedStringInt()), TuplesKt.to(TuplesKt.to("kotlin.ULong", "compareTo"), context.getIr().getSymbols().getCompareUnsignedLong()), TuplesKt.to(TuplesKt.to("kotlin.ULong", "div"), context.getIr().getSymbols().getDivideUnsignedLong()), TuplesKt.to(TuplesKt.to("kotlin.ULong", "rem"), context.getIr().getSymbols().getRemainderUnsignedLong()), TuplesKt.to(TuplesKt.to("kotlin.ULong", "toString"), context.getIr().getSymbols().getToUnsignedStringLong()));
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.transformChildren(new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmBuiltInsLowering$lower$transformer$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                expression.transformChildren(this, null);
                IrSimpleFunction owner = expression.getSymbol().getOwner();
                String strAsString = AdditionalIrUtilsKt.getFqNameForIrSerialization(owner.getParent()).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                String strAsString2 = owner.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                if (Intrinsics.areEqual(strAsString, "kotlin.CompareToKt") && Intrinsics.areEqual(strAsString2, "compareTo")) {
                    IrExpression valueArgument = expression.getValueArgument(0);
                    Intrinsics.checkNotNull(valueArgument);
                    IrType type = valueArgument.getType();
                    if (IrTypePredicatesKt.isUInt(type)) {
                        JvmBuiltInsLowering jvmBuiltInsLowering = this.this$0;
                        return jvmBuiltInsLowering.replaceWithCallTo(expression, jvmBuiltInsLowering.getContext().getIr().getSymbols().getCompareUnsignedInt());
                    }
                    if (IrTypePredicatesKt.isULong(type)) {
                        JvmBuiltInsLowering jvmBuiltInsLowering2 = this.this$0;
                        return jvmBuiltInsLowering2.replaceWithCallTo(expression, jvmBuiltInsLowering2.getContext().getIr().getSymbols().getCompareUnsignedLong());
                    }
                }
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) this.this$0.jvm8builtInReplacements.get(TuplesKt.to(strAsString, strAsString2));
                if (irSimpleFunctionSymbol != null) {
                    return this.this$0.replaceWithCallTo(expression, irSimpleFunctionSymbol);
                }
                IrSimpleFunction irSimpleFunction = owner;
                if (VarargLoweringKt.isArrayOf(irSimpleFunction)) {
                    IrExpression valueArgument2 = expression.getValueArgument(0);
                    if (valueArgument2 != null) {
                        return valueArgument2;
                    }
                    throw new AssertionError("Argument #0 expected: " + DumpIrTreeKt.dump$default(expression, null, 1, null));
                }
                if (VarargLoweringKt.isEmptyArray(irSimpleFunction)) {
                    JvmBackendContext context = this.this$0.getContext();
                    ScopeWithIr currentScope = getCurrentScope();
                    Intrinsics.checkNotNull(currentScope);
                    return IrArrayBuilderKt.irArrayOf$default(JvmIrBuilderKt.createJvmIrBuilder(context, currentScope, expression), expression.getType(), null, 2, null);
                }
                return expression;
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression replaceWithCallTo(IrCall irCall, IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        IrType type = irCall.getType();
        IrType returnType = irSimpleFunctionSymbol.getOwner().getReturnType();
        IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(irCall.getStartOffset(), irCall.getEndOffset(), returnType, irSimpleFunctionSymbol, (240 & 16) != 0 ? irSimpleFunctionSymbol.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? irSimpleFunctionSymbol.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        Ref.IntRef intRef = new Ref.IntRef();
        IrExpression extensionReceiver = irCall.getExtensionReceiver();
        if (extensionReceiver != null && !replaceWithCallTo$lambda$2$tryToAddCoercedArgument(this, irSimpleFunctionSymbol, intRef, irCallImplFromSymbolOwner, extensionReceiver)) {
            return irCall;
        }
        IrExpression dispatchReceiver = irCall.getDispatchReceiver();
        if (dispatchReceiver != null && !replaceWithCallTo$lambda$2$tryToAddCoercedArgument(this, irSimpleFunctionSymbol, intRef, irCallImplFromSymbolOwner, dispatchReceiver)) {
            return irCall;
        }
        int valueArgumentsCount = irCall.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = irCall.getValueArgument(i);
            Intrinsics.checkNotNull(valueArgument);
            if (!replaceWithCallTo$lambda$2$tryToAddCoercedArgument(this, irSimpleFunctionSymbol, intRef, irCallImplFromSymbolOwner, valueArgument)) {
                return irCall;
            }
        }
        if (IrTypePredicatesKt.isInt(returnType) || IrTypePredicatesKt.isLong(returnType)) {
            IrExpression irExpressionCoerceIfPossible = coerceIfPossible(irCallImplFromSymbolOwner, type);
            if (irExpressionCoerceIfPossible != null) {
                return irExpressionCoerceIfPossible;
            }
            throw new AssertionError("Can't coerce '" + RenderIrElementKt.render$default(returnType, (DumpIrTreeOptions) null, 1, (Object) null) + "' to '" + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null) + '\'');
        }
        return irCallImplFromSymbolOwner;
    }

    private static final boolean replaceWithCallTo$lambda$2$tryToAddCoercedArgument(JvmBuiltInsLowering jvmBuiltInsLowering, IrSimpleFunctionSymbol irSimpleFunctionSymbol, Ref.IntRef intRef, IrCallImpl irCallImpl, IrExpression irExpression) {
        IrExpression irExpressionCoerceIfPossible = jvmBuiltInsLowering.coerceIfPossible(irExpression, irSimpleFunctionSymbol.getOwner().getValueParameters().get(intRef.element).getType());
        if (irExpressionCoerceIfPossible == null) {
            return false;
        }
        int i = intRef.element;
        intRef.element = i + 1;
        irCallImpl.putValueArgument(i, irExpressionCoerceIfPossible);
        return true;
    }

    private final IrExpression coerceIfPossible(IrExpression irExpression, IrType irType) {
        if (!Intrinsics.areEqual(IrTypeMapper.mapType$default(this.context.getDefaultTypeMapper(), irExpression.getType(), null, null, false, 14, null), IrTypeMapper.mapType$default(this.context.getDefaultTypeMapper(), irType, null, null, false, 14, null))) {
            return null;
        }
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        int startOffset = irExpression.getStartOffset();
        int endOffset = irExpression.getEndOffset();
        IrSimpleFunctionSymbol unsafeCoerceIntrinsic = this.context.getIr().getSymbols().getUnsafeCoerceIntrinsic();
        IrCallImpl irCallImplFromSymbolOwner = companion.fromSymbolOwner(startOffset, endOffset, irType, unsafeCoerceIntrinsic, (240 & 16) != 0 ? unsafeCoerceIntrinsic.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? unsafeCoerceIntrinsic.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : null, (240 & 128) != 0 ? null : null);
        irCallImplFromSymbolOwner.putTypeArgument(0, irExpression.getType());
        irCallImplFromSymbolOwner.putTypeArgument(1, irType);
        irCallImplFromSymbolOwner.putValueArgument(0, irExpression);
        return irCallImplFromSymbolOwner;
    }
}
