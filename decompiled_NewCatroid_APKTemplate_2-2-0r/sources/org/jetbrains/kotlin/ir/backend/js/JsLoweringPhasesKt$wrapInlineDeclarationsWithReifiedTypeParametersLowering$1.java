package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.lower.WrapInlineDeclarationsWithReifiedTypeParametersLowering;

/* JADX INFO: compiled from: JsLoweringPhases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsLoweringPhasesKt$wrapInlineDeclarationsWithReifiedTypeParametersLowering$1 extends FunctionReference implements Function1<BackendContext, WrapInlineDeclarationsWithReifiedTypeParametersLowering> {
    public static final JsLoweringPhasesKt$wrapInlineDeclarationsWithReifiedTypeParametersLowering$1 INSTANCE = new JsLoweringPhasesKt$wrapInlineDeclarationsWithReifiedTypeParametersLowering$1();

    JsLoweringPhasesKt$wrapInlineDeclarationsWithReifiedTypeParametersLowering$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/backend/common/BackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(WrapInlineDeclarationsWithReifiedTypeParametersLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final WrapInlineDeclarationsWithReifiedTypeParametersLowering invoke(BackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new WrapInlineDeclarationsWithReifiedTypeParametersLowering(p0);
    }
}
