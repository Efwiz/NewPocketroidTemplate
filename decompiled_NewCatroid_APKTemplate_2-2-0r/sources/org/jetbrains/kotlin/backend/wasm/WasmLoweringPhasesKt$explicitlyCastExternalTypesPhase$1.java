package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.wasm.lower.ExplicitlyCastExternalTypesLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$explicitlyCastExternalTypesPhase$1 extends FunctionReference implements Function1<WasmBackendContext, ExplicitlyCastExternalTypesLowering> {
    public static final WasmLoweringPhasesKt$explicitlyCastExternalTypesPhase$1 INSTANCE = new WasmLoweringPhasesKt$explicitlyCastExternalTypesPhase$1();

    WasmLoweringPhasesKt$explicitlyCastExternalTypesPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ExplicitlyCastExternalTypesLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final ExplicitlyCastExternalTypesLowering invoke(WasmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new ExplicitlyCastExternalTypesLowering(p0);
    }
}
