package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/InapplicableWrongReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "actualType", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getActualType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getExpectedType", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InapplicableWrongReceiver extends ResolutionDiagnostic {
    private final ConeKotlinType actualType;
    private final ConeKotlinType expectedType;

    /* JADX WARN: Multi-variable type inference failed */
    public InapplicableWrongReceiver() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public /* synthetic */ InapplicableWrongReceiver(ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : coneKotlinType, (i & 2) != 0 ? null : coneKotlinType2);
    }

    public final ConeKotlinType getExpectedType() {
        return this.expectedType;
    }

    public final ConeKotlinType getActualType() {
        return this.actualType;
    }

    public InapplicableWrongReceiver(ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2) {
        super(CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER);
        this.expectedType = coneKotlinType;
        this.actualType = coneKotlinType2;
    }
}
