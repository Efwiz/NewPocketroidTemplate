package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.backend.js.lower.SecondaryConstructorLowering;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsLoweringPhasesKt$secondaryConstructorLoweringPhase$1 extends FunctionReference implements Function1<JsIrBackendContext, SecondaryConstructorLowering> {
    public static final JsLoweringPhasesKt$secondaryConstructorLoweringPhase$1 INSTANCE = new JsLoweringPhasesKt$secondaryConstructorLoweringPhase$1();

    JsLoweringPhasesKt$secondaryConstructorLoweringPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(SecondaryConstructorLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final SecondaryConstructorLowering invoke(JsIrBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new SecondaryConstructorLowering(p0);
    }
}
