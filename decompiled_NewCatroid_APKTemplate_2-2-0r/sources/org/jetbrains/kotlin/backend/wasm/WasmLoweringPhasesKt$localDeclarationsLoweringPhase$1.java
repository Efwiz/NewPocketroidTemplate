package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$localDeclarationsLoweringPhase$1 extends FunctionReference implements Function1<CommonBackendContext, LocalDeclarationsLowering> {
    public static final WasmLoweringPhasesKt$localDeclarationsLoweringPhase$1 INSTANCE = new WasmLoweringPhasesKt$localDeclarationsLoweringPhase$1();

    WasmLoweringPhasesKt$localDeclarationsLoweringPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "localDeclarationsLoweringPhase$lambda$0(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final LocalDeclarationsLowering invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new LocalDeclarationsLowering(p0, null, null, false, false, false, null, 126, null);
    }
}
