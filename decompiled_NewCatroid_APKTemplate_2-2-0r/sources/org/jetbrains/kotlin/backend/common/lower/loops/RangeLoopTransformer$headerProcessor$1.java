package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: ForLoopsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class RangeLoopTransformer$headerProcessor$1 extends FunctionReference implements Function0<IrSymbol> {
    RangeLoopTransformer$headerProcessor$1(Object obj) {
        super(0, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getScopeOwnerSymbol()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getScopeOwnerSymbol";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(RangeLoopTransformer.class);
    }

    @Override // kotlin.jvm.functions.Function0
    public final IrSymbol invoke() {
        return ((RangeLoopTransformer) this.receiver).getScopeOwnerSymbol();
    }
}
