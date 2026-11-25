package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: BuiltInsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00058@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\u0007"}, d2 = {"builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "getBuiltIns", "(Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;)Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;)Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuiltInsProviderKt {
    public static final KotlinBuiltIns getBuiltIns(ConstraintSystemBuilder constraintSystemBuilder) {
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "<this>");
        TypeSystemInferenceExtensionContext typeSystemContext = ((NewConstraintSystemImpl) constraintSystemBuilder).getTypeSystemContext();
        Intrinsics.checkNotNull(typeSystemContext, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.components.BuiltInsProvider");
        return ((BuiltInsProvider) typeSystemContext).getBuiltIns();
    }

    public static final KotlinBuiltIns getBuiltIns(NewConstraintSystem newConstraintSystem) {
        Intrinsics.checkNotNullParameter(newConstraintSystem, "<this>");
        TypeSystemInferenceExtensionContext typeSystemContext = ((NewConstraintSystemImpl) newConstraintSystem).getTypeSystemContext();
        Intrinsics.checkNotNull(typeSystemContext, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.components.BuiltInsProvider");
        return ((BuiltInsProvider) typeSystemContext).getBuiltIns();
    }
}
