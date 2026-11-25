package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: ES6PrimaryConstructorOptimizationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6PrimaryConstructorUsageOptimizationLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6PrimaryConstructorUsageOptimizationLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;

    public ES6PrimaryConstructorUsageOptimizationLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6PrimaryConstructorUsageOptimizationLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrSimpleFunction owner = expression.getSymbol().getOwner();
                if (!ES6PrimaryConstructorOptimizationLoweringKt.shouldBeConvertedToPlainConstructor(owner, ES6PrimaryConstructorUsageOptimizationLowering.this.context)) {
                    return super.visitCall(expression);
                }
                if (ES6ConstructorLoweringKt.isSyntheticDelegatingReplacement(expression)) {
                    JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                    IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(IrUtilsKt.getParentAsClass(owner));
                    Intrinsics.checkNotNull(primaryConstructor);
                    IrDelegatingConstructorCall irDelegatingConstructorCallBuildDelegatingConstructorCall$default = JsIrBuilder.buildDelegatingConstructorCall$default(jsIrBuilder, primaryConstructor.getSymbol(), null, 2, null);
                    IrUtilsKt.copyTypeAndValueArgumentsFrom$default(irDelegatingConstructorCallBuildDelegatingConstructorCall$default, expression, false, false, 6, null);
                    return super.visitDelegatingConstructorCall(irDelegatingConstructorCallBuildDelegatingConstructorCall$default);
                }
                JsIrBuilder jsIrBuilder2 = JsIrBuilder.INSTANCE;
                IrConstructor primaryConstructor2 = IrUtilsKt.getPrimaryConstructor(IrUtilsKt.getParentAsClass(owner));
                Intrinsics.checkNotNull(primaryConstructor2);
                IrConstructorCall irConstructorCallBuildConstructorCall$default = JsIrBuilder.buildConstructorCall$default(jsIrBuilder2, primaryConstructor2.getSymbol(), null, null, null, 14, null);
                IrUtilsKt.copyTypeAndValueArgumentsFrom$default(irConstructorCallBuildConstructorCall$default, expression, false, false, 6, null);
                return super.visitConstructorCall(irConstructorCallBuildConstructorCall$default);
            }
        });
    }
}
