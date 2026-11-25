package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B9\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/InferredEmptyIntersectionDiagnostic;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "incompatibleTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "causingTypes", "typeVariable", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "isError", "", "(Ljava/util/Collection;Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;Z)V", "getCausingTypes", "()Ljava/util/Collection;", "getIncompatibleTypes", "()Z", "getKind", "()Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "getTypeVariable", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InferredEmptyIntersectionDiagnostic extends ResolutionDiagnostic {
    private final Collection<ConeKotlinType> causingTypes;
    private final Collection<ConeKotlinType> incompatibleTypes;
    private final boolean isError;
    private final EmptyIntersectionTypeKind kind;
    private final ConeTypeVariable typeVariable;

    public final Collection<ConeKotlinType> getIncompatibleTypes() {
        return this.incompatibleTypes;
    }

    public final Collection<ConeKotlinType> getCausingTypes() {
        return this.causingTypes;
    }

    public final ConeTypeVariable getTypeVariable() {
        return this.typeVariable;
    }

    public final EmptyIntersectionTypeKind getKind() {
        return this.kind;
    }

    /* JADX INFO: renamed from: isError, reason: from getter */
    public final boolean getIsError() {
        return this.isError;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public InferredEmptyIntersectionDiagnostic(Collection<? extends ConeKotlinType> incompatibleTypes, Collection<? extends ConeKotlinType> causingTypes, ConeTypeVariable typeVariable, EmptyIntersectionTypeKind kind, boolean z) {
        super(CandidateApplicability.INAPPLICABLE);
        Intrinsics.checkNotNullParameter(incompatibleTypes, "incompatibleTypes");
        Intrinsics.checkNotNullParameter(causingTypes, "causingTypes");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.incompatibleTypes = incompatibleTypes;
        this.causingTypes = causingTypes;
        this.typeVariable = typeVariable;
        this.kind = kind;
        this.isError = z;
    }
}
