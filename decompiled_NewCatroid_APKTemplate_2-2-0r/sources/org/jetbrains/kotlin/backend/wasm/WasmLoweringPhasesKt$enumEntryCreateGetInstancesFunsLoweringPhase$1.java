package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.EnumEntryCreateGetInstancesFunsLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$enumEntryCreateGetInstancesFunsLoweringPhase$1 extends FunctionReference implements Function1<JsCommonBackendContext, EnumEntryCreateGetInstancesFunsLowering> {
    public static final WasmLoweringPhasesKt$enumEntryCreateGetInstancesFunsLoweringPhase$1 INSTANCE = new WasmLoweringPhasesKt$enumEntryCreateGetInstancesFunsLoweringPhase$1();

    WasmLoweringPhasesKt$enumEntryCreateGetInstancesFunsLoweringPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(EnumEntryCreateGetInstancesFunsLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final EnumEntryCreateGetInstancesFunsLowering invoke(JsCommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new EnumEntryCreateGetInstancesFunsLowering(p0);
    }
}
