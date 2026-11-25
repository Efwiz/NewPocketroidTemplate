package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: IncorrectCapturedApproximationCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class IncorrectCapturedApproximationCallChecker$shouldWarningBeReported$1$1 extends FunctionReference implements Function1<UnwrappedType, Boolean> {
    public static final IncorrectCapturedApproximationCallChecker$shouldWarningBeReported$1$1 INSTANCE = new IncorrectCapturedApproximationCallChecker$shouldWarningBeReported$1$1();

    IncorrectCapturedApproximationCallChecker$shouldWarningBeReported$1$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isCaptured(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isCaptured";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(CapturedTypeConstructorKt.class, "frontend");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(UnwrappedType p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(CapturedTypeConstructorKt.isCaptured(p0));
    }
}
