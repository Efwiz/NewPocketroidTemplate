package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IdSignatureValues;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: JsStringConcatenationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0014H\u0016J\f\u0010\u0015\u001a\u00020\u0010*\u00020\u0010H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\nR\u0018\u0010\u000b\u001a\u00020\b*\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsStringConcatenationTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "isStringPlus", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;)Z", "shouldExplicitlyConvertToString", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getShouldExplicitlyConvertToString", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "explicitlyConvertedToString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsStringConcatenationTransformer extends IrElementTransformerVoid {
    private final CommonBackendContext context;

    public JsStringConcatenationTransformer(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getShouldExplicitlyConvertToString(IrType irType) {
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        IdSignature signature = ((IrSimpleType) irType).getClassifier().getSignature();
        if (Intrinsics.areEqual(signature, IdSignatureValues.any) ? true : Intrinsics.areEqual(signature, IdSignatureValues.comparable) ? true : Intrinsics.areEqual(signature, IdSignatureValues.number) ? true : Intrinsics.areEqual(signature, IdSignatureValues._long)) {
            return true;
        }
        return Intrinsics.areEqual(signature, IdSignatureValues._char);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x00a8  */
    public final IrExpression explicitlyConvertedToString(IrExpression irExpression) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        SmartList smartListEmptyList;
        getShouldExplicitlyConvertToString(irExpression.getType());
        if (IrTypeUtilsKt.isNullable(irExpression.getType())) {
            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIr().getSymbols().getExtensionToString(), null, null, null, null, 30, null);
            irCallBuildCall$default.setExtensionReceiver(irExpression);
            return irCallBuildCall$default;
        }
        IrSimpleFunctionSymbol memberToString = this.context.getIr().getSymbols().getMemberToString();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irExpression.getType());
        if (classOrNull != null) {
            List<IrDeclaration> declarations = classOrNull.getOwner().getDeclarations();
            if (declarations.isEmpty()) {
                smartListEmptyList = CollectionsKt.emptyList();
            } else {
                SmartList smartList = new SmartList();
                for (Object obj : declarations) {
                    if ((obj instanceof IrSimpleFunction) && AdditionalIrUtilsKt.overrides((IrSimpleFunction) obj, memberToString.getOwner())) {
                        smartList.add(obj);
                    }
                }
                smartListEmptyList = smartList;
            }
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) CollectionsKt.singleOrNull(smartListEmptyList);
            IrSimpleFunctionSymbol symbol = irSimpleFunction != null ? irSimpleFunction.getSymbol() : null;
            if (symbol == null) {
                irSimpleFunctionSymbol = memberToString;
            } else {
                irSimpleFunctionSymbol = symbol;
            }
        } else {
            irSimpleFunctionSymbol = memberToString;
        }
        IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunctionSymbol, null, null, null, null, 30, null);
        irCallBuildCall$default2.setDispatchReceiver(irExpression);
        return irCallBuildCall$default2;
    }

    private final boolean isStringPlus(IrFunctionSymbol irFunctionSymbol) {
        return this.context.getIr().getSymbols().isStringPlus(irFunctionSymbol);
    }

    private static final IrExpression visitCall$explicitlyConvertToStringIfNeeded(IrCall irCall, JsStringConcatenationTransformer jsStringConcatenationTransformer) {
        int valueArgumentsCount = irCall.getValueArgumentsCount() - 1;
        IrExpression valueArgument = irCall.getValueArgument(valueArgumentsCount);
        if (valueArgument == null) {
            return super.visitCall(irCall);
        }
        if (!jsStringConcatenationTransformer.getShouldExplicitlyConvertToString(valueArgument.getType())) {
            return super.visitCall(irCall);
        }
        irCall.putValueArgument(valueArgumentsCount, jsStringConcatenationTransformer.explicitlyConvertedToString(valueArgument));
        return irCall;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        IrFunctionSymbol symbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression.getValueArgumentsCount() == 0) {
            return super.visitCall(expression);
        }
        if (isStringPlus(expression.getSymbol())) {
            return visitCall$explicitlyConvertToStringIfNeeded(expression, this);
        }
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (!(dispatchReceiver instanceof IrFunctionReference)) {
            dispatchReceiver = null;
        }
        IrFunctionReference irFunctionReference = (IrFunctionReference) dispatchReceiver;
        if (irFunctionReference != null && (symbol = irFunctionReference.getSymbol()) != null && isStringPlus(symbol)) {
            return visitCall$explicitlyConvertToStringIfNeeded(expression, this);
        }
        return super.visitCall(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsStringConcatenationTransformer.visitStringConcatenation.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitExpression(IrExpression expression2) {
                Intrinsics.checkNotNullParameter(expression2, "expression");
                return JsStringConcatenationTransformer.this.getShouldExplicitlyConvertToString(expression2.getType()) ? JsStringConcatenationTransformer.this.explicitlyConvertedToString(expression2) : expression2;
            }
        });
        return super.visitStringConcatenation(expression);
    }
}
