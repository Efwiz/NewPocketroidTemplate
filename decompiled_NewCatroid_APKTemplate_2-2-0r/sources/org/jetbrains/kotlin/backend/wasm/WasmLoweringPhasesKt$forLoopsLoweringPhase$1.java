package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.loops.ForLoopsLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$forLoopsLoweringPhase$1 extends FunctionReference implements Function1<CommonBackendContext, ForLoopsLowering> {
    public static final WasmLoweringPhasesKt$forLoopsLoweringPhase$1 INSTANCE = new WasmLoweringPhasesKt$forLoopsLoweringPhase$1();

    WasmLoweringPhasesKt$forLoopsLoweringPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "forLoopsLoweringPhase$lambda$4(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopsLowering;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.jvm.functions.Function1
    public final ForLoopsLowering invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new ForLoopsLowering(p0, null, 2, 0 == true ? 1 : 0);
    }
}
