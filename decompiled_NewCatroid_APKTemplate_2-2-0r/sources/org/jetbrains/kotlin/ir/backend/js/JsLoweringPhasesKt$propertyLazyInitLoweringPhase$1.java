package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.backend.js.lower.PropertyLazyInitLowering;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsLoweringPhasesKt$propertyLazyInitLoweringPhase$1 extends FunctionReference implements Function1<JsCommonBackendContext, PropertyLazyInitLowering> {
    public static final JsLoweringPhasesKt$propertyLazyInitLoweringPhase$1 INSTANCE = new JsLoweringPhasesKt$propertyLazyInitLoweringPhase$1();

    JsLoweringPhasesKt$propertyLazyInitLoweringPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(PropertyLazyInitLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PropertyLazyInitLowering invoke(JsCommonBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new PropertyLazyInitLowering(p0);
    }
}
