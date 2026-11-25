package org.jetbrains.kotlin.resolve.calls.results;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FlatSignature.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/OverloadabilitySpecificityCallbacks;", "Lorg/jetbrains/kotlin/resolve/calls/results/SpecificityComparisonCallbacks;", "()V", "isNonSubtypeNotLessSpecific", "", "specific", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "general", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverloadabilitySpecificityCallbacks implements SpecificityComparisonCallbacks {
    public static final OverloadabilitySpecificityCallbacks INSTANCE = new OverloadabilitySpecificityCallbacks();

    @Override // org.jetbrains.kotlin.resolve.calls.results.SpecificityComparisonCallbacks
    public boolean isNonSubtypeNotLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
        Intrinsics.checkNotNullParameter(specific, "specific");
        Intrinsics.checkNotNullParameter(general, "general");
        return false;
    }

    private OverloadabilitySpecificityCallbacks() {
    }
}
