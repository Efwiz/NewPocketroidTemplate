package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FileDeserializationState$symbolDeserializer$2 extends FunctionReference implements Function2<IdSignature, IrSymbol, IrSymbol> {
    FileDeserializationState$symbolDeserializer$2(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "handleExpectActualMapping(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "handleExpectActualMapping";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(KotlinIrLinker.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final IrSymbol invoke(IdSignature p0, IrSymbol p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ((KotlinIrLinker) this.receiver).handleExpectActualMapping(p0, p1);
    }
}
