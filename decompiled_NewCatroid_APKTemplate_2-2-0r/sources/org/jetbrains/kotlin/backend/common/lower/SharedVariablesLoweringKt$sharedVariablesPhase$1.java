package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.BackendContext;

/* JADX INFO: compiled from: SharedVariablesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class SharedVariablesLoweringKt$sharedVariablesPhase$1 extends FunctionReference implements Function1<BackendContext, SharedVariablesLowering> {
    public static final SharedVariablesLoweringKt$sharedVariablesPhase$1 INSTANCE = new SharedVariablesLoweringKt$sharedVariablesPhase$1();

    SharedVariablesLoweringKt$sharedVariablesPhase$1() {
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
        return Reflection.getOrCreateKotlinClass(SharedVariablesLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final SharedVariablesLowering invoke(BackendContext p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new SharedVariablesLowering(p0);
    }
}
