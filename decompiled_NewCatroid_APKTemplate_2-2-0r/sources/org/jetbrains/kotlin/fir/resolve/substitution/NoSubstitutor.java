package org.jetbrains.kotlin.fir.resolve.substitution;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: ConeSubstitutor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/NoSubstitutor;", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "()V", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoSubstitutor implements TypeSubstitutorMarker {
    public static final NoSubstitutor INSTANCE = new NoSubstitutor();

    private NoSubstitutor() {
    }
}
