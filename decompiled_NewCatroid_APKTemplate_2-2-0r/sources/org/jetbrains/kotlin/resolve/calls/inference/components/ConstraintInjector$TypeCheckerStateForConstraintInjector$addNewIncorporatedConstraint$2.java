package org.jetbrains.kotlin.resolve.calls.inference.components;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstraintInjector.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ConstraintInjector$TypeCheckerStateForConstraintInjector$addNewIncorporatedConstraint$2 extends FunctionReference implements Function1<KotlinTypeMarker, Boolean> {
    ConstraintInjector$TypeCheckerStateForConstraintInjector$addNewIncorporatedConstraint$2(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isCapturedTypeFromSubtyping(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isCapturedTypeFromSubtyping";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ConstraintInjector.TypeCheckerStateForConstraintInjector.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(KotlinTypeMarker p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(((ConstraintInjector.TypeCheckerStateForConstraintInjector) this.receiver).isCapturedTypeFromSubtyping(p0));
    }
}
