package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/PartialCallResolutionResult;", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "resultCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "forwardToInferenceSession", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;Z)V", "getForwardToInferenceSession", "()Z", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialCallResolutionResult extends SingleCallResolutionResult {
    private final boolean forwardToInferenceSession;

    public /* synthetic */ PartialCallResolutionResult(ResolvedCallAtom resolvedCallAtom, List list, NewConstraintSystem newConstraintSystem, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolvedCallAtom, list, newConstraintSystem, (i & 8) != 0 ? false : z);
    }

    public final boolean getForwardToInferenceSession() {
        return this.forwardToInferenceSession;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PartialCallResolutionResult(ResolvedCallAtom resultCallAtom, List<? extends KotlinCallDiagnostic> diagnostics, NewConstraintSystem constraintSystem, boolean z) {
        super(resultCallAtom, diagnostics, constraintSystem);
        Intrinsics.checkNotNullParameter(resultCallAtom, "resultCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        this.forwardToInferenceSession = z;
    }
}
