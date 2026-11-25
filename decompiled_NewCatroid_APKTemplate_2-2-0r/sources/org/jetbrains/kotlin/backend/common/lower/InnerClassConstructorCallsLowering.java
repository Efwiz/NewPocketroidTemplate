package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: InnerClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/InnerClassConstructorCallsLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "getInnerClassesSupport", "()Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InnerClassConstructorCallsLowering implements BodyLoweringPass {
    private final BackendContext context;
    private final InnerClassesSupport innerClassesSupport;

    public InnerClassConstructorCallsLowering(BackendContext context, InnerClassesSupport innerClassesSupport) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(innerClassesSupport, "innerClassesSupport");
        this.context = context;
        this.innerClassesSupport = innerClassesSupport;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    public final InnerClassesSupport getInnerClassesSupport() {
        return this.innerClassesSupport;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.InnerClassConstructorCallsLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitConstructorCall(IrConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrExpression dispatchReceiver = expression.getDispatchReceiver();
                if (dispatchReceiver == null) {
                    return expression;
                }
                IrConstructorSymbol symbol = expression.getSymbol();
                if (!IrUtilsKt.getParentAsClass(symbol.getOwner()).getIsInner()) {
                    return expression;
                }
                IrConstructor innerClassConstructorWithOuterThisParameter = InnerClassConstructorCallsLowering.this.getInnerClassesSupport().getInnerClassConstructorWithOuterThisParameter(symbol.getOwner());
                IrConstructorCallImpl irConstructorCallImplFromSymbolOwner = IrConstructorCallImpl.INSTANCE.fromSymbolOwner(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), innerClassConstructorWithOuterThisParameter.getSymbol(), expression.getTypeArgumentsCount() - expression.getConstructorTypeArgumentsCount(), expression.getOrigin());
                IrExpressionsKt.copyTypeArgumentsFrom$default(irConstructorCallImplFromSymbolOwner, expression, 0, 2, null);
                irConstructorCallImplFromSymbolOwner.putValueArgument(0, dispatchReceiver);
                int lastIndex = CollectionsKt.getLastIndex(innerClassConstructorWithOuterThisParameter.getValueParameters());
                int i = 1;
                if (1 <= lastIndex) {
                    while (true) {
                        irConstructorCallImplFromSymbolOwner.putValueArgument(i, expression.getValueArgument(i - 1));
                        if (i == lastIndex) {
                            break;
                        }
                        i++;
                    }
                }
                return irConstructorCallImplFromSymbolOwner;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrExpression dispatchReceiver = expression.getDispatchReceiver();
                if (dispatchReceiver == null) {
                    return expression;
                }
                IrConstructor owner = expression.getSymbol().getOwner();
                if (!IrUtilsKt.getParentAsClass(owner).getIsInner()) {
                    return expression;
                }
                IrConstructor innerClassConstructorWithOuterThisParameter = InnerClassConstructorCallsLowering.this.getInnerClassesSupport().getInnerClassConstructorWithOuterThisParameter(owner);
                IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = new IrDelegatingConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), InnerClassConstructorCallsLowering.this.getContext().getIrBuiltIns().getUnitType(), innerClassConstructorWithOuterThisParameter.getSymbol(), expression.getTypeArgumentsCount(), innerClassConstructorWithOuterThisParameter.getValueParameters().size());
                IrExpressionsKt.copyTypeArgumentsFrom$default(irDelegatingConstructorCallImpl, expression, 0, 2, null);
                irDelegatingConstructorCallImpl.putValueArgument(0, dispatchReceiver);
                int lastIndex = CollectionsKt.getLastIndex(innerClassConstructorWithOuterThisParameter.getValueParameters());
                int i = 1;
                if (1 <= lastIndex) {
                    while (true) {
                        irDelegatingConstructorCallImpl.putValueArgument(i, expression.getValueArgument(i - 1));
                        if (i == lastIndex) {
                            break;
                        }
                        i++;
                    }
                }
                return irDelegatingConstructorCallImpl;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitFunctionReference(IrFunctionReference expression) {
                IrConstructor innerClassConstructorWithOuterThisParameter;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrFunctionSymbol symbol = expression.getSymbol();
                IrConstructorSymbol irConstructorSymbol = symbol instanceof IrConstructorSymbol ? (IrConstructorSymbol) symbol : null;
                if (irConstructorSymbol == null) {
                    return expression;
                }
                IrDeclarationParent parent = irConstructorSymbol.getOwner().getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass != null && irClass.getIsInner()) {
                    IrConstructor innerClassConstructorWithOuterThisParameter2 = InnerClassConstructorCallsLowering.this.getInnerClassesSupport().getInnerClassConstructorWithOuterThisParameter(irConstructorSymbol.getOwner());
                    IrFunctionSymbol reflectionTarget = expression.getReflectionTarget();
                    if (reflectionTarget != null) {
                        InnerClassConstructorCallsLowering innerClassConstructorCallsLowering = InnerClassConstructorCallsLowering.this;
                        if (reflectionTarget instanceof IrConstructorSymbol) {
                            innerClassConstructorWithOuterThisParameter = innerClassConstructorCallsLowering.getInnerClassesSupport().getInnerClassConstructorWithOuterThisParameter(((IrConstructorSymbol) reflectionTarget).getOwner());
                        } else {
                            if (!(reflectionTarget instanceof IrSimpleFunctionSymbol)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            innerClassConstructorWithOuterThisParameter = null;
                        }
                    } else {
                        innerClassConstructorWithOuterThisParameter = null;
                    }
                    IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), innerClassConstructorWithOuterThisParameter2.getSymbol(), expression.getTypeArgumentsCount(), innerClassConstructorWithOuterThisParameter2.getValueParameters().size(), innerClassConstructorWithOuterThisParameter != null ? innerClassConstructorWithOuterThisParameter.getSymbol() : null, expression.getOrigin());
                    IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionReferenceImpl, expression, 0, 2, null);
                    irFunctionReferenceImpl.setDispatchReceiver(expression.getDispatchReceiver());
                    irFunctionReferenceImpl.setExtensionReceiver(expression.getExtensionReceiver());
                    int valueArgumentsCount = expression.getValueArgumentsCount();
                    for (int i = 0; i < valueArgumentsCount; i++) {
                        irFunctionReferenceImpl.putValueArgument(i, expression.getValueArgument(i));
                    }
                    return irFunctionReferenceImpl;
                }
                return expression;
            }
        });
    }
}
