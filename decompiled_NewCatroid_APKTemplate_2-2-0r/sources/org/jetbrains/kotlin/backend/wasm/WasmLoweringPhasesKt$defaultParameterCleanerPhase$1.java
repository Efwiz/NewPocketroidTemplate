package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DefaultParameterCleaner;

/* JADX INFO: compiled from: WasmLoweringPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class WasmLoweringPhasesKt$defaultParameterCleanerPhase$1 extends FunctionReference implements Function1<CommonBackendContext, DefaultParameterCleaner> {
    public static final WasmLoweringPhasesKt$defaultParameterCleanerPhase$1 INSTANCE = new WasmLoweringPhasesKt$defaultParameterCleanerPhase$1();

    WasmLoweringPhasesKt$defaultParameterCleanerPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "defaultParameterCleanerPhase$lambda$2(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)Lorg/jetbrains/kotlin/backend/common/lower/DefaultParameterCleaner;";
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
    public final DefaultParameterCleaner invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new DefaultParameterCleaner(p0, false, 2, null);
    }
}
