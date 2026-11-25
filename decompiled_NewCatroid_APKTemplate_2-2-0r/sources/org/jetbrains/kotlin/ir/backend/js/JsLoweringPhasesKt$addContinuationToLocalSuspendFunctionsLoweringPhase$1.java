package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.coroutines.AddContinuationToLocalSuspendFunctionsLowering;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsLoweringPhasesKt$addContinuationToLocalSuspendFunctionsLoweringPhase$1 extends FunctionReference implements Function1<CommonBackendContext, AddContinuationToLocalSuspendFunctionsLowering> {
    public static final JsLoweringPhasesKt$addContinuationToLocalSuspendFunctionsLoweringPhase$1 INSTANCE = new JsLoweringPhasesKt$addContinuationToLocalSuspendFunctionsLoweringPhase$1();

    JsLoweringPhasesKt$addContinuationToLocalSuspendFunctionsLoweringPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(AddContinuationToLocalSuspendFunctionsLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final AddContinuationToLocalSuspendFunctionsLowering invoke(CommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new AddContinuationToLocalSuspendFunctionsLowering(p0);
    }
}
