package org.jetbrains.kotlin.backend.common.lower.coroutines;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: AddContinuationToFunctionCallsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u0011H\u0002J\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0011H$J\u0014\u0010\u0014\u001a\u00020\u0015*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018²\u0006\f\u0010\u0019\u001a\u0004\u0018\u00010\u0010X\u008a\u0084\u0002²\u0006\n\u0010\u001a\u001a\u00020\u001bX\u008a\u0084\u0002²\u0006\n\u0010\u001c\u001a\u00020\u0017X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/coroutines/AbstractAddContinuationToFunctionCallsLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "()V", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getContinuationParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isContinuationItself", "", "throwLinkageError", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "file", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "ir.backend.common", "continuation", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "plFile"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractAddContinuationToFunctionCallsLowering implements BodyLoweringPass {
    protected abstract CommonBackendContext getContext();

    protected abstract boolean isContinuationItself(IrSimpleFunction irSimpleFunction);

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile, true);
    }

    private static final IrValueParameter lower$lambda$0(Lazy<? extends IrValueParameter> lazy) {
        return lazy.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, final IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        final Lazy lazy = LazyKt.lazy(new Function0<IrValueParameter>() { // from class: org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering$lower$continuation$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrValueParameter invoke() {
                AbstractAddContinuationToFunctionCallsLowering abstractAddContinuationToFunctionCallsLowering = this.this$0;
                IrDeclaration irDeclaration = container;
                Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                return abstractAddContinuationToFunctionCallsLowering.getContinuationParameter((IrSimpleFunction) irDeclaration);
            }
        });
        final Lazy lazy2 = LazyKt.lazy(new Function0<DeclarationIrBuilder>() { // from class: org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering$lower$builder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DeclarationIrBuilder invoke() {
                return LowerUtilsKt.createIrBuilder$default(this.this$0.getContext(), container.getSymbol(), 0, 0, 6, (Object) null);
            }
        });
        final Lazy lazy3 = LazyKt.lazy(new Function0<PartialLinkageUtils.File>() { // from class: org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering$lower$plFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PartialLinkageUtils.File invoke() {
                return PartialLinkageUtils.File.INSTANCE.determineFileFor(container);
            }
        });
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrBody visitBody(IrBody body) {
                Intrinsics.checkNotNullParameter(body, "body");
                return body;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                transformChildrenVoid(expression);
                if (!IrUtilsKt.isSuspend(expression)) {
                    if (Intrinsics.areEqual(expression.getSymbol(), AbstractAddContinuationToFunctionCallsLowering.this.getContext().getIr().getSymbols().getGetContinuation())) {
                        IrGetValue irGetValueLower$getContinuation = AbstractAddContinuationToFunctionCallsLowering.lower$getContinuation(lazy, lazy2);
                        return irGetValueLower$getContinuation != null ? irGetValueLower$getContinuation : AbstractAddContinuationToFunctionCallsLowering.this.throwLinkageError(expression, AbstractAddContinuationToFunctionCallsLowering.lower$lambda$2(lazy3));
                    }
                    return expression;
                }
                IrSimpleFunction orCreateFunctionWithContinuationStub = AddContinuationToFunctionsLoweringKt.getOrCreateFunctionWithContinuationStub(expression.getSymbol().getOwner(), AbstractAddContinuationToFunctionCallsLowering.this.getContext());
                IrCall irCall = IrUtilsKt.irCall(expression, orCreateFunctionWithContinuationStub.getSymbol(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : expression.getSuperQualifierSymbol(), (12 & 32) != 0 ? null : orCreateFunctionWithContinuationStub.getReturnType());
                AbstractAddContinuationToFunctionCallsLowering abstractAddContinuationToFunctionCallsLowering = AbstractAddContinuationToFunctionCallsLowering.this;
                Lazy<IrValueParameter> lazy4 = lazy;
                Lazy<DeclarationIrBuilder> lazy5 = lazy2;
                Lazy<PartialLinkageUtils.File> lazy6 = lazy3;
                int valueArgumentsCount = irCall.getValueArgumentsCount() - 1;
                IrGetValue irGetValueLower$getContinuation2 = AbstractAddContinuationToFunctionCallsLowering.lower$getContinuation(lazy4, lazy5);
                if (irGetValueLower$getContinuation2 == null) {
                    return abstractAddContinuationToFunctionCallsLowering.throwLinkageError(expression, AbstractAddContinuationToFunctionCallsLowering.lower$lambda$2(lazy6));
                }
                irCall.putValueArgument(valueArgumentsCount, irGetValueLower$getContinuation2);
                return irCall;
            }
        });
    }

    private static final DeclarationIrBuilder lower$lambda$1(Lazy<DeclarationIrBuilder> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrGetValue lower$getContinuation(Lazy<? extends IrValueParameter> lazy, Lazy<DeclarationIrBuilder> lazy2) {
        IrGetValueImpl irGetValueImplIrGet;
        IrValueParameter irValueParameterLower$lambda$0 = lower$lambda$0(lazy);
        if (irValueParameterLower$lambda$0 != null) {
            irGetValueImplIrGet = ExpressionHelpersKt.irGet(lower$lambda$1(lazy2), irValueParameterLower$lambda$0);
        } else {
            irGetValueImplIrGet = null;
        }
        return irGetValueImplIrGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PartialLinkageUtils.File lower$lambda$2(Lazy<? extends PartialLinkageUtils.File> lazy) {
        return lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrValueParameter getContinuationParameter(IrSimpleFunction irSimpleFunction) {
        if (isContinuationItself(irSimpleFunction)) {
            IrValueParameter dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            return dispatchReceiverParameter;
        }
        if (!Intrinsics.areEqual(irSimpleFunction.getOrigin(), IrDeclarationOrigin.LOWERED_SUSPEND_FUNCTION.INSTANCE)) {
            if (getContext().getPartialLinkageSupport().isEnabled()) {
                return null;
            }
            throw new IllegalArgumentException("Continuation parameter only exists in lowered suspend functions, but function origin is " + irSimpleFunction.getOrigin());
        }
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irSimpleFunction.getValueParameters());
        if (irValueParameter == null || !Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.CONTINUATION.INSTANCE)) {
            throw new IllegalArgumentException("Continuation parameter is expected to be the last one".toString());
        }
        return irValueParameter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCall throwLinkageError(IrCall irCall, PartialLinkageUtils.File file) {
        return PartialLinkageSupportForLowerings.DefaultImpls.throwLinkageError$default(getContext().getPartialLinkageSupport(), new PartialLinkageCase.SuspendableFunctionCallWithoutCoroutineContext(irCall), irCall, file, false, 8, null);
    }
}
