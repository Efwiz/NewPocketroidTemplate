package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: ES6ConstructorCallLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6ConstructorCallLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "constructorFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructorFactory", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setConstructorFactory", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "constructorFactory$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6ConstructorCallLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(ES6ConstructorCallLowering.class), "constructorFactory", "getConstructorFactory(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};

    /* JADX INFO: renamed from: constructorFactory$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate constructorFactory;
    private final JsIrBackendContext context;

    public ES6ConstructorCallLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.constructorFactory = context.getMapping().getSecondaryConstructorToFactory();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getConstructorFactory(IrConstructor irConstructor) {
        return (IrSimpleFunction) this.constructorFactory.getValue(irConstructor, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (this.context.getEs6mode()) {
            final IrFunction irFunction = container instanceof IrFunction ? (IrFunction) container : null;
            IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6ConstructorCallLowering.lower.1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitConstructorCall(IrConstructorCall expression) {
                    IrValueParameter boxParameter;
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrConstructor owner = expression.getSymbol().getOwner();
                    IrClass parentAsClass = IrUtilsKt.getParentAsClass(owner);
                    ScopeWithIr currentFunction = getCurrentFunction();
                    IrElement irElement = currentFunction != null ? currentFunction.getIrElement() : null;
                    IrFunction irFunction2 = irElement instanceof IrFunction ? (IrFunction) irElement : null;
                    if (irFunction2 == null) {
                        irFunction2 = irFunction;
                    }
                    if (!Intrinsics.areEqual(parentAsClass.getSymbol(), this.getContext().getIrBuiltIns().getAnyClass()) && !IrJsUtilsKt.hasStrictSignature(owner, this.getContext())) {
                        IrSimpleFunction constructorFactory = this.getConstructorFactory(owner);
                        if (constructorFactory == null) {
                            throw new IllegalStateException("Replacement for the constructor is not found".toString());
                        }
                        IrConstructorCall irConstructorCall = expression;
                        if (ES6ConstructorLoweringKt.isInitCall(irConstructorCall)) {
                            ES6ConstructorLoweringKt.isInitFunction(constructorFactory);
                            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, constructorFactory.getSymbol(), null, null, null, null, 30, null);
                            IrUtilsKt.copyValueArgumentsFrom$default(irCallBuildCall$default, expression, constructorFactory, false, false, 12, null);
                            return irCallBuildCall$default;
                        }
                        IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, constructorFactory.getSymbol(), null, null, ES6ConstructorLoweringKt.isSyntheticDelegatingReplacement(irConstructorCall) && irFunction2 != null ? ES6_DELEGATING_CONSTRUCTOR_REPLACEMENT.INSTANCE : JsStatementOrigins.SYNTHESIZED_STATEMENT.INSTANCE, null, 22, null);
                        ES6ConstructorCallLowering eS6ConstructorCallLowering = this;
                        IrUtilsKt.copyValueArgumentsFrom$default(irCallBuildCall$default2, expression, constructorFactory, false, false, 12, null);
                        if (ES6ConstructorLoweringKt.isSyntheticDelegatingReplacement(irConstructorCall)) {
                            if (irFunction2 != null && (boxParameter = ES6AddBoxParameterLoweringKt.getBoxParameter(irFunction2)) != null) {
                                irCallBuildCall$default2.putValueArgument(irCallBuildCall$default2.getValueArgumentsCount() - 1, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, boxParameter.getSymbol(), null, 2, null));
                            }
                            if (irCallBuildCall$default2.getSuperQualifierSymbol() == null) {
                                JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
                                IrValueParameter dispatchReceiverParameter = constructorFactory.getDispatchReceiverParameter();
                                Intrinsics.checkNotNull(dispatchReceiverParameter);
                                irCallBuildCall$default2.setDispatchReceiver(JsIrBuilder.buildGetValue$default(jsIrBuilder, dispatchReceiverParameter.getSymbol(), null, 2, null));
                            }
                        } else {
                            irCallBuildCall$default2.setDispatchReceiver(IrJsUtilsKt.jsConstructorReference(parentAsClass, eS6ConstructorCallLowering.getContext()));
                        }
                        return super.visitCall(irCallBuildCall$default2);
                    }
                    return super.visitConstructorCall(expression);
                }
            });
        }
    }
}
