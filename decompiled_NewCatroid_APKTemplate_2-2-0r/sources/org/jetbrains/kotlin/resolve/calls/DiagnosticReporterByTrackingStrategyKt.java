package org.jetbrains.kotlin.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintMismatch;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: DiagnosticReporterByTrackingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0007"}, d2 = {"lowerKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintMismatch;", "getLowerKotlinType", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintMismatch;)Lorg/jetbrains/kotlin/types/KotlinType;", "upperKotlinType", "getUpperKotlinType", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DiagnosticReporterByTrackingStrategyKt {
    public static final KotlinType getUpperKotlinType(NewConstraintMismatch newConstraintMismatch) {
        Intrinsics.checkNotNullParameter(newConstraintMismatch, "<this>");
        KotlinTypeMarker upperType = newConstraintMismatch.getUpperType();
        Intrinsics.checkNotNull(upperType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        return (KotlinType) upperType;
    }

    public static final KotlinType getLowerKotlinType(NewConstraintMismatch newConstraintMismatch) {
        Intrinsics.checkNotNullParameter(newConstraintMismatch, "<this>");
        KotlinTypeMarker lowerType = newConstraintMismatch.getLowerType();
        Intrinsics.checkNotNull(lowerType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
        return (KotlinType) lowerType;
    }
}
