package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.NullableFieldsForLateinitCreationLowering;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$lateinitNullableFieldsPhase$1 extends FunctionReference implements Function1<CommonBackendContext, NullableFieldsForLateinitCreationLowering> {
    public static final WasmLoweringPhasesKt$lateinitNullableFieldsPhase$1 INSTANCE = new WasmLoweringPhasesKt$lateinitNullableFieldsPhase$1();

    WasmLoweringPhasesKt$lateinitNullableFieldsPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(NullableFieldsForLateinitCreationLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final NullableFieldsForLateinitCreationLowering invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new NullableFieldsForLateinitCreationLowering(p0);
    }
}
