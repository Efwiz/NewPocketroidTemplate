package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;

/* JADX INFO: compiled from: PolymorphicSignatureLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class PolymorphicSignatureLoweringKt$polymorphicSignaturePhase$1 extends FunctionReference implements Function1<JvmBackendContext, PolymorphicSignatureLowering> {
    public static final PolymorphicSignatureLoweringKt$polymorphicSignaturePhase$1 INSTANCE = new PolymorphicSignatureLoweringKt$polymorphicSignaturePhase$1();

    PolymorphicSignatureLoweringKt$polymorphicSignaturePhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(PolymorphicSignatureLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PolymorphicSignatureLowering invoke(JvmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new PolymorphicSignatureLowering(p0);
    }
}
