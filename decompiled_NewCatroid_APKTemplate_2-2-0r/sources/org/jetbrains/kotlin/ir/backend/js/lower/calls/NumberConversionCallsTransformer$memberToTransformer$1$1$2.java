package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;

/* JADX INFO: compiled from: NumberConversionCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class NumberConversionCallsTransformer$memberToTransformer$1$1$2 extends FunctionReference implements Function1<IrFunctionAccessExpression, IrExpression> {
    NumberConversionCallsTransformer$memberToTransformer$1$1$2(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "useDispatchReceiver(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "useDispatchReceiver";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(NumberConversionCallsTransformer.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final IrExpression invoke(IrFunctionAccessExpression p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((NumberConversionCallsTransformer) this.receiver).useDispatchReceiver(p0);
    }
}
