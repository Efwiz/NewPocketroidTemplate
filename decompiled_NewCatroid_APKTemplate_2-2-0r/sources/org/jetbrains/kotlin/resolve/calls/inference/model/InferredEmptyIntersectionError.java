package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B1\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersectionError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InferredEmptyIntersection;", "incompatibleTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "causingTypes", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;)V", "getCausingTypes", "()Ljava/util/List;", "getIncompatibleTypes", "getKind", "()Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "getTypeVariable", "()Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InferredEmptyIntersectionError extends ConstraintSystemError implements InferredEmptyIntersection {
    private final List<KotlinTypeMarker> causingTypes;
    private final List<KotlinTypeMarker> incompatibleTypes;
    private final EmptyIntersectionTypeKind kind;
    private final TypeVariableMarker typeVariable;

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection
    public List<KotlinTypeMarker> getIncompatibleTypes() {
        return this.incompatibleTypes;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection
    public List<KotlinTypeMarker> getCausingTypes() {
        return this.causingTypes;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection
    public TypeVariableMarker getTypeVariable() {
        return this.typeVariable;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection
    public EmptyIntersectionTypeKind getKind() {
        return this.kind;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public InferredEmptyIntersectionError(List<? extends KotlinTypeMarker> incompatibleTypes, List<? extends KotlinTypeMarker> causingTypes, TypeVariableMarker typeVariable, EmptyIntersectionTypeKind kind) {
        super(CandidateApplicability.INAPPLICABLE, null);
        Intrinsics.checkNotNullParameter(incompatibleTypes, "incompatibleTypes");
        Intrinsics.checkNotNullParameter(causingTypes, "causingTypes");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.incompatibleTypes = incompatibleTypes;
        this.causingTypes = causingTypes;
        this.typeVariable = typeVariable;
        this.kind = kind;
    }
}
