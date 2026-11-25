package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: PrivateMembersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PrivateMemberBodiesLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "correspondingStatic", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getCorrespondingStatic", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "setCorrespondingStatic", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "correspondingStatic$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrivateMemberBodiesLowering implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(PrivateMemberBodiesLowering.class), "correspondingStatic", "getCorrespondingStatic(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;"))};
    private final JsIrBackendContext context;

    /* JADX INFO: renamed from: correspondingStatic$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate correspondingStatic;

    public PrivateMemberBodiesLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.correspondingStatic = context.getMapping().getPrivateMemberToCorrespondingStatic();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getCorrespondingStatic(IrFunction irFunction) {
        return (IrSimpleFunction) this.correspondingStatic.getValue(irFunction, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        irBody.transform((IrElementTransformer<? super Object>) new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PrivateMemberBodiesLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                IrCall irCallTransformPrivateToStaticCall;
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitCall(expression);
                IrSimpleFunction correspondingStatic = PrivateMemberBodiesLowering.this.getCorrespondingStatic(expression.getSymbol().getOwner());
                if (correspondingStatic != null && (irCallTransformPrivateToStaticCall = transformPrivateToStaticCall(expression, correspondingStatic)) != null) {
                    return irCallTransformPrivateToStaticCall;
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitFunctionReference(final IrFunctionReference expression) {
                IrCallableReference<?> irCallableReferenceTransformPrivateToStaticReference;
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitFunctionReference(expression);
                final IrSimpleFunction correspondingStatic = PrivateMemberBodiesLowering.this.getCorrespondingStatic(expression.getSymbol().getOwner());
                if (correspondingStatic != null && (irCallableReferenceTransformPrivateToStaticReference = transformPrivateToStaticReference(expression, new Function0<IrCallableReference<?>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PrivateMemberBodiesLowering$lower$1$visitFunctionReference$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final IrCallableReference<?> invoke() {
                        return new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), correspondingStatic.getSymbol(), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), expression.getReflectionTarget(), expression.getOrigin());
                    }
                })) != null) {
                    return irCallableReferenceTransformPrivateToStaticReference;
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitPropertyReference(final IrPropertyReference expression) {
                IrSimpleFunction owner;
                IrSimpleFunction owner2;
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitPropertyReference(expression);
                IrSimpleFunctionSymbol getter = expression.getGetter();
                final IrSimpleFunction correspondingStatic = null;
                final IrSimpleFunction correspondingStatic2 = (getter == null || (owner2 = getter.getOwner()) == null) ? null : PrivateMemberBodiesLowering.this.getCorrespondingStatic(owner2);
                IrSimpleFunctionSymbol setter = expression.getSetter();
                if (setter != null && (owner = setter.getOwner()) != null) {
                    correspondingStatic = PrivateMemberBodiesLowering.this.getCorrespondingStatic(owner);
                }
                if (correspondingStatic2 != null || correspondingStatic != null) {
                    return transformPrivateToStaticReference(expression, new Function0<IrCallableReference<?>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.PrivateMemberBodiesLowering$lower$1$visitPropertyReference$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final IrCallableReference<?> invoke() {
                            IrSimpleFunctionSymbol getter2;
                            IrSimpleFunctionSymbol setter2;
                            int startOffset = expression.getStartOffset();
                            int endOffset = expression.getEndOffset();
                            IrType type = expression.getType();
                            IrPropertySymbol symbol = expression.getSymbol();
                            int typeArgumentsCount = expression.getTypeArgumentsCount();
                            IrFieldSymbol field = expression.getField();
                            IrSimpleFunction irSimpleFunction = correspondingStatic2;
                            if (irSimpleFunction == null || (getter2 = irSimpleFunction.getSymbol()) == null) {
                                getter2 = expression.getGetter();
                            }
                            IrSimpleFunctionSymbol irSimpleFunctionSymbol = getter2;
                            IrSimpleFunction irSimpleFunction2 = correspondingStatic;
                            if (irSimpleFunction2 == null || (setter2 = irSimpleFunction2.getSymbol()) == null) {
                                setter2 = expression.getSetter();
                            }
                            return new IrPropertyReferenceImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, field, irSimpleFunctionSymbol, setter2, expression.getOrigin());
                        }
                    });
                }
                return expression;
            }

            private final IrCall transformPrivateToStaticCall(IrCall expression, IrSimpleFunction staticTarget) {
                IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), staticTarget.getSymbol(), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount() + 1, expression.getOrigin(), expression.getSuperQualifierSymbol());
                irCallImpl.setExtensionReceiver(expression.getExtensionReceiver());
                IrExpression dispatchReceiver = expression.getDispatchReceiver();
                if (dispatchReceiver != null) {
                    irCallImpl.putValueArgument(0, dispatchReceiver);
                }
                int valueArgumentsCount = expression.getValueArgumentsCount();
                int i = 0;
                while (i < valueArgumentsCount) {
                    int i2 = i + 1;
                    irCallImpl.putValueArgument(i2, expression.getValueArgument(i));
                    i = i2;
                }
                IrExpressionsKt.copyTypeArgumentsFrom$default(irCallImpl, expression, 0, 2, null);
                return irCallImpl;
            }

            private final IrCallableReference<?> transformPrivateToStaticReference(IrCallableReference<?> expression, Function0<? extends IrCallableReference<?>> builder) {
                IrCallableReference<?> irCallableReferenceInvoke = builder.invoke();
                irCallableReferenceInvoke.setExtensionReceiver(expression.getExtensionReceiver());
                irCallableReferenceInvoke.setDispatchReceiver(expression.getDispatchReceiver());
                int valueArgumentsCount = expression.getValueArgumentsCount();
                for (int i = 0; i < valueArgumentsCount; i++) {
                    irCallableReferenceInvoke.putValueArgument(i, expression.getValueArgument(i));
                }
                IrExpressionsKt.copyTypeArgumentsFrom$default(irCallableReferenceInvoke, expression, 0, 2, null);
                return irCallableReferenceInvoke;
            }
        }, (Object) null);
    }
}
