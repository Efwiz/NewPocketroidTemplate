package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.EnumEntryInstancesBodyLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$enumEntryInstancesBodyLoweringPhase$1 extends FunctionReference implements Function1<JsCommonBackendContext, EnumEntryInstancesBodyLowering> {
    public static final WasmLoweringPhasesKt$enumEntryInstancesBodyLoweringPhase$1 INSTANCE = new WasmLoweringPhasesKt$enumEntryInstancesBodyLoweringPhase$1();

    WasmLoweringPhasesKt$enumEntryInstancesBodyLoweringPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(EnumEntryInstancesBodyLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final EnumEntryInstancesBodyLowering invoke(JsCommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new EnumEntryInstancesBodyLowering(p0);
    }
}
