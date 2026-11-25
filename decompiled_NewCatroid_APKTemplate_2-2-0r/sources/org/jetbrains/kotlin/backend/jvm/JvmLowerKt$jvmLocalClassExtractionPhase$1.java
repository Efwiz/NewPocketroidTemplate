package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.jvm.lower.JvmLocalClassPopupLowering;

/* JADX INFO: compiled from: JvmLower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JvmLowerKt$jvmLocalClassExtractionPhase$1 extends FunctionReference implements Function1<JvmBackendContext, JvmLocalClassPopupLowering> {
    public static final JvmLowerKt$jvmLocalClassExtractionPhase$1 INSTANCE = new JvmLowerKt$jvmLocalClassExtractionPhase$1();

    JvmLowerKt$jvmLocalClassExtractionPhase$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JvmLocalClassPopupLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final JvmLocalClassPopupLowering invoke(JvmBackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new JvmLocalClassPopupLowering(p0);
    }
}
