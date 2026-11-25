package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$localClassExtractionPhase$1 extends FunctionReference implements Function1<BackendContext, LocalClassPopupLowering> {
    public static final WasmLoweringPhasesKt$localClassExtractionPhase$1 INSTANCE = new WasmLoweringPhasesKt$localClassExtractionPhase$1();

    WasmLoweringPhasesKt$localClassExtractionPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "localClassExtractionPhase$lambda$1(Lorg/jetbrains/kotlin/backend/common/BackendContext;)Lorg/jetbrains/kotlin/backend/common/lower/LocalClassPopupLowering;";
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
    public final LocalClassPopupLowering invoke(BackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new LocalClassPopupLowering(p0, null, 2, null);
    }
}
