package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.wasm.lower.JsInteropFunctionsLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$jsInteropFunctionsLowering$1 extends FunctionReference implements Function1<WasmBackendContext, JsInteropFunctionsLowering> {
    public static final WasmLoweringPhasesKt$jsInteropFunctionsLowering$1 INSTANCE = new WasmLoweringPhasesKt$jsInteropFunctionsLowering$1();

    WasmLoweringPhasesKt$jsInteropFunctionsLowering$1() {
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
        return Reflection.getOrCreateKotlinClass(JsInteropFunctionsLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final JsInteropFunctionsLowering invoke(WasmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new JsInteropFunctionsLowering(p0);
    }
}
