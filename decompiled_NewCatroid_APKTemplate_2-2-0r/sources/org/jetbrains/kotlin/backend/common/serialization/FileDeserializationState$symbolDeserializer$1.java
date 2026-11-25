package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FileDeserializationState$symbolDeserializer$1 extends FunctionReference implements Function1<IdSignature, Unit> {
    FileDeserializationState$symbolDeserializer$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "addIdSignature(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "addIdSignature";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FileDeserializationState.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IdSignature idSignature) {
        invoke2(idSignature);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IdSignature p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((FileDeserializationState) this.receiver).addIdSignature(p0);
    }
}
