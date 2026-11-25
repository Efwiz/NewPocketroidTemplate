package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.inline.SyntheticAccessorLowering;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsLoweringPhasesKt$syntheticAccessorLoweringPhase$1 extends FunctionReference implements Function1<CommonBackendContext, SyntheticAccessorLowering> {
    public static final JsLoweringPhasesKt$syntheticAccessorLoweringPhase$1 INSTANCE = new JsLoweringPhasesKt$syntheticAccessorLoweringPhase$1();

    JsLoweringPhasesKt$syntheticAccessorLoweringPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(SyntheticAccessorLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final SyntheticAccessorLowering invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new SyntheticAccessorLowering(p0);
    }
}
