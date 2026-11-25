package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: ES6ConstructorBoxParameterOptimizationLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\f\u0010\u0012\u001a\u00020\u0006*\u00020\u0007H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6ConstructorBoxParameterOptimizationLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "needsOfBoxParameter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getNeedsOfBoxParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Boolean;", "needsOfBoxParameter$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "requiredToHaveBoxParameter", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6ConstructorBoxParameterOptimizationLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property2(new PropertyReference2Impl(Reflection.getOrCreateKotlinClass(ES6ConstructorBoxParameterOptimizationLowering.class), "needsOfBoxParameter", "getNeedsOfBoxParameter(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Boolean;"))};
    private final JsIrBackendContext context;

    /* JADX INFO: renamed from: needsOfBoxParameter$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate needsOfBoxParameter;

    public ES6ConstructorBoxParameterOptimizationLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.needsOfBoxParameter = context.getMapping().getEsClassWhichNeedBoxParameters();
    }

    private final Boolean getNeedsOfBoxParameter(IrClass irClass) {
        return (Boolean) this.needsOfBoxParameter.getValue(irClass, $$delegatedProperties[0]);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0035  */
    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        final boolean z;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.context.getEs6mode()) {
            IrFunction irFunction = container instanceof IrFunction ? (IrFunction) container : null;
            if (irFunction != null) {
                z = ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irFunction) && !requiredToHaveBoxParameter(IrUtilsKt.getParentAsClass(irFunction));
            }
            if (irFunction != null && z && (irBody instanceof IrBlockBody)) {
                List<IrValueParameter> valueParameters = irFunction.getValueParameters();
                ArrayList arrayList = new ArrayList();
                for (Object obj : valueParameters) {
                    if (!ES6AddBoxParameterLoweringKt.isBoxParameter((IrValueParameter) obj)) {
                        arrayList.add(obj);
                    }
                }
                irFunction.setValueParameters(CollectionsKt.compactIfPossible(arrayList));
            }
            IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorBoxParameterOptimizationLowering.lower.2
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitWhen(IrWhen expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    if (z && ES6AddBoxParameterLoweringKt.isBoxParameterDefaultResolution(expression)) {
                        return IrJsUtilsKt.irEmpty(this.context);
                    }
                    return super.visitWhen(expression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitCall(IrCall expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrSimpleFunction owner = expression.getSymbol().getOwner();
                    if (z && (Intrinsics.areEqual(owner.getSymbol(), this.context.getIntrinsics().getJsCreateThisSymbol()) || Intrinsics.areEqual(owner.getSymbol(), this.context.getIntrinsics().getJsCreateExternalThisSymbol()))) {
                        expression.putValueArgument(expression.getValueArgumentsCount() - 1, IrJsUtilsKt.getVoid(this.context));
                        return super.visitCall(expression);
                    }
                    IrSimpleFunction irSimpleFunction = owner;
                    if (ES6ConstructorLoweringKt.isEs6ConstructorReplacement(irSimpleFunction) && (!this.requiredToHaveBoxParameter(IrUtilsKt.getParentAsClass(irSimpleFunction)) || z)) {
                        int valueArgumentsCount = expression.getValueArgumentsCount() - 1;
                        IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getSymbol(), expression.getTypeArgumentsCount(), valueArgumentsCount, expression.getOrigin(), expression.getSuperQualifierSymbol());
                        IrExpressionsKt.copyTypeArgumentsFrom$default(irCallImpl, expression, 0, 2, null);
                        irCallImpl.setDispatchReceiver(expression.getDispatchReceiver());
                        irCallImpl.setExtensionReceiver(expression.getExtensionReceiver());
                        for (int i = 0; i < valueArgumentsCount; i++) {
                            irCallImpl.putValueArgument(i, expression.getValueArgument(i));
                        }
                        return super.visitCall(irCallImpl);
                    }
                    return super.visitCall(expression);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean requiredToHaveBoxParameter(IrClass irClass) {
        return Intrinsics.areEqual((Object) getNeedsOfBoxParameter(irClass), (Object) true);
    }
}
