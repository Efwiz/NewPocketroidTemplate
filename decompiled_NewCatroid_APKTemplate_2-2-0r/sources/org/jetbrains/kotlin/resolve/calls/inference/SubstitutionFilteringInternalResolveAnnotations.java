package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.types.DelegatedTypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitution;

/* JADX INFO: compiled from: ConstraintSystemImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/SubstitutionFilteringInternalResolveAnnotations;", "Lorg/jetbrains/kotlin/types/DelegatedTypeSubstitution;", SchemaSymbols.ATTVAL_SUBSTITUTION, "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "(Lorg/jetbrains/kotlin/types/TypeSubstitution;)V", "filterAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotations", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SubstitutionFilteringInternalResolveAnnotations extends DelegatedTypeSubstitution {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SubstitutionFilteringInternalResolveAnnotations(TypeSubstitution substitution) {
        super(substitution);
        Intrinsics.checkNotNullParameter(substitution, "substitution");
    }

    @Override // org.jetbrains.kotlin.types.DelegatedTypeSubstitution, org.jetbrains.kotlin.types.TypeSubstitution
    public Annotations filterAnnotations(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        return !AnnotationsForResolveUtilsKt.hasInternalAnnotationForResolve(annotations) ? annotations : new FilteredAnnotations(annotations, new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.SubstitutionFilteringInternalResolveAnnotations.filterAnnotations.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!AnnotationsForResolveUtilsKt.isInternalAnnotationForResolve(it));
            }
        });
    }
}
