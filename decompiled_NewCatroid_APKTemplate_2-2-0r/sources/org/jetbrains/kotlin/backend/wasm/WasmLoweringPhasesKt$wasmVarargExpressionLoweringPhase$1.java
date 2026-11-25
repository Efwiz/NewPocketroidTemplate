package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.wasm.lower.WasmVarargExpressionLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$wasmVarargExpressionLoweringPhase$1 extends FunctionReference implements Function1<WasmBackendContext, WasmVarargExpressionLowering> {
    public static final WasmLoweringPhasesKt$wasmVarargExpressionLoweringPhase$1 INSTANCE = new WasmLoweringPhasesKt$wasmVarargExpressionLoweringPhase$1();

    WasmLoweringPhasesKt$wasmVarargExpressionLoweringPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(WasmVarargExpressionLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final WasmVarargExpressionLowering invoke(WasmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new WasmVarargExpressionLowering(p0);
    }
}
