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
/* synthetic */ class ConstTransformer$visitConst$1$2 extends FunctionReference implements Function4<Integer, Integer, IrType, Short, IrConstImpl<Short>> {
    ConstTransformer$visitConst$1$2(Object obj) {
        super(4, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "short(IILorg/jetbrains/kotlin/ir/types/IrType;S)Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "short";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(IrConstImpl.Companion.class);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ IrConstImpl<Short> invoke(Integer num, Integer num2, IrType irType, Short sh) {
        return invoke(num.intValue(), num2.intValue(), irType, sh.shortValue());
    }

    public final IrConstImpl<Short> invoke(int i, int i2, IrType p2, short s) {
        Intrinsics.checkNotNullParameter(p2, "p2");
        return ((IrConstImpl.Companion) this.receiver).m7213short(i, i2, p2, s);
    }
}
