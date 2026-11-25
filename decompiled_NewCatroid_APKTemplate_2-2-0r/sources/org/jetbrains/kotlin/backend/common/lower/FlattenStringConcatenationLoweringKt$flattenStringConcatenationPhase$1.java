package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;

/* JADX INFO: compiled from: FlattenStringConcatenationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FlattenStringConcatenationLoweringKt$flattenStringConcatenationPhase$1 extends FunctionReference implements Function1<CommonBackendContext, FlattenStringConcatenationLowering> {
    public static final FlattenStringConcatenationLoweringKt$flattenStringConcatenationPhase$1 INSTANCE = new FlattenStringConcatenationLoweringKt$flattenStringConcatenationPhase$1();

    FlattenStringConcatenationLoweringKt$flattenStringConcatenationPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FlattenStringConcatenationLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final FlattenStringConcatenationLowering invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new FlattenStringConcatenationLowering(p0);
    }
}
