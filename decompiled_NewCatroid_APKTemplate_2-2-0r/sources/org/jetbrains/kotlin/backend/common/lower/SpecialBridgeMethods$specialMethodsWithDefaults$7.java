package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;

/* JADX INFO: compiled from: SpecialBridgeMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class SpecialBridgeMethods$specialMethodsWithDefaults$7 extends FunctionReference implements Function1<IrSimpleFunction, IrConstImpl<Integer>> {
    SpecialBridgeMethods$specialMethodsWithDefaults$7(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "constMinusOne(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "constMinusOne";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(SpecialBridgeMethods.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final IrConstImpl<Integer> invoke(IrSimpleFunction p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((SpecialBridgeMethods) this.receiver).constMinusOne(p0);
    }
}
