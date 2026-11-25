package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/SubstitutionWithCapturedTypeApproximation;", "Lorg/jetbrains/kotlin/types/DelegatedTypeSubstitution;", SchemaSymbols.ATTVAL_SUBSTITUTION, "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "(Lorg/jetbrains/kotlin/types/TypeSubstitution;)V", "approximateCapturedTypes", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubstitutionWithCapturedTypeApproximation extends DelegatedTypeSubstitution {
    @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
    public boolean approximateCapturedTypes() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubstitutionWithCapturedTypeApproximation(TypeSubstitution substitution) {
        super(substitution);
        Intrinsics.checkNotNullParameter(substitution, "substitution");
    }
}
