package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.lower.EnumClassConstructorBodyTransformer;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$enumClassConstructorBodyLoweringPhase$1 extends FunctionReference implements Function1<JsCommonBackendContext, EnumClassConstructorBodyTransformer> {
    public static final WasmLoweringPhasesKt$enumClassConstructorBodyLoweringPhase$1 INSTANCE = new WasmLoweringPhasesKt$enumClassConstructorBodyLoweringPhase$1();

    WasmLoweringPhasesKt$enumClassConstructorBodyLoweringPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(EnumClassConstructorBodyTransformer.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final EnumClassConstructorBodyTransformer invoke(JsCommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new EnumClassConstructorBodyTransformer(p0);
    }
}
