package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: ConstLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ConstTransformer$visitConst$1$5 extends FunctionReference implements Function4<Integer, Integer, IrType, Integer, IrConstImpl<Integer>> {
    ConstTransformer$visitConst$1$5(Object obj) {
        super(4, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "int(IILorg/jetbrains/kotlin/ir/types/IrType;I)Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "int";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(IrConstImpl.Companion.class);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ IrConstImpl<Integer> invoke(Integer num, Integer num2, IrType irType, Integer num3) {
        return invoke(num.intValue(), num2.intValue(), irType, num3.intValue());
    }

    public final IrConstImpl<Integer> invoke(int i, int i2, IrType p2, int i3) {
        Intrinsics.checkNotNullParameter(p2, "p2");
        return ((IrConstImpl.Companion) this.receiver).m7211int(i, i2, p2, i3);
    }
}
