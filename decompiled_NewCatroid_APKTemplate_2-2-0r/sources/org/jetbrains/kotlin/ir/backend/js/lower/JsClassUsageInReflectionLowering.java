package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicMemberExpressionImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: JsClassUsageInReflectionLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\f\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0002J\u000e\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u0012H\u0002J\f\u0010\u0013\u001a\u00020\u0014*\u00020\u0015H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsClassUsageInReflectionLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "generateDirectConstructorUsage", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "generateDirectValueUsage", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "isGetJsCall", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsClassUsageInReflectionLowering implements BodyLoweringPass {
    private final JsIrBackendContext backendContext;

    public JsClassUsageInReflectionLowering(JsIrBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    public final JsIrBackendContext getBackendContext() {
        return this.backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsClassUsageInReflectionLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (!Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.GET_PROPERTY.INSTANCE) || !JsClassUsageInReflectionLowering.this.isGetJsCall(expression)) {
                    return super.visitCall(expression);
                }
                IrExpression extensionReceiver = expression.getExtensionReceiver();
                if (extensionReceiver instanceof IrClassReference) {
                    IrExpression irExpressionGenerateDirectValueUsage = JsClassUsageInReflectionLowering.this.generateDirectValueUsage((IrClassReference) extensionReceiver);
                    return irExpressionGenerateDirectValueUsage == null ? super.visitCall(expression) : irExpressionGenerateDirectValueUsage;
                }
                if (extensionReceiver instanceof IrGetClass) {
                    return JsClassUsageInReflectionLowering.this.generateDirectConstructorUsage((IrGetClass) extensionReceiver);
                }
                return super.visitCall(expression);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression generateDirectValueUsage(IrClassReference irClassReference) {
        JsIrBackendContext jsIrBackendContext = this.backendContext;
        IrClassifierSymbol symbol = irClassReference.getSymbol();
        IrClassSymbol irClassSymbol = symbol instanceof IrClassSymbol ? (IrClassSymbol) symbol : null;
        if (irClassSymbol == null || Intrinsics.areEqual(irClassSymbol, jsIrBackendContext.getIrBuiltIns().getNothingClass())) {
            return null;
        }
        if (Intrinsics.areEqual(irClassSymbol, jsIrBackendContext.getIrBuiltIns().getAnyClass())) {
            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, jsIrBackendContext.getIntrinsics().getJsCode(), null, null, null, null, 30, null);
            irCallBuildCall$default.putValueArgument(0, IrConstImpl.INSTANCE.string(-1, -1, jsIrBackendContext.getIrBuiltIns().getStringType(), CommonClassNames.JAVA_LANG_OBJECT_SHORT));
            return irCallBuildCall$default;
        }
        return IrJsUtilsKt.jsConstructorReference(irClassSymbol.getOwner(), this.backendContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrDynamicMemberExpression generateDirectConstructorUsage(IrGetClass irGetClass) {
        return new IrDynamicMemberExpressionImpl(irGetClass.getStartOffset(), irGetClass.getEndOffset(), this.backendContext.getDynamicType(), JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, irGetClass.getArgument());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isGetJsCall(IrCall irCall) {
        return Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(irCall.getSymbol().getOwner()), JsClassUsageInReflectionLoweringKt.JS_CLASS_GETTER);
    }
}
