package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0013\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/DelegatedTypeSubstitution;", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", SchemaSymbols.ATTVAL_SUBSTITUTION, "(Lorg/jetbrains/kotlin/types/TypeSubstitution;)V", "getSubstitution", "()Lorg/jetbrains/kotlin/types/TypeSubstitution;", "approximateCapturedTypes", "", "approximateContravariantCapturedTypes", "filterAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotations", "get", "Lorg/jetbrains/kotlin/types/TypeProjection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "isEmpty", "prepareTopLevelType", "topLevelType", "position", "Lorg/jetbrains/kotlin/types/Variance;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DelegatedTypeSubstitution extends TypeSubstitution {
    private final TypeSubstitution substitution;

    public DelegatedTypeSubstitution(TypeSubstitution substitution) {
        Intrinsics.checkNotNullParameter(substitution, "substitution");
        this.substitution = substitution;
    }

    public final TypeSubstitution getSubstitution() {
        return this.substitution;
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjection mo7475get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.substitution.mo7475get(key);
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter(position, "position");
        return this.substitution.prepareTopLevelType(topLevelType, position);
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean isEmpty() {
        return this.substitution.isEmpty();
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean approximateCapturedTypes() {
        return this.substitution.approximateCapturedTypes();
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public boolean approximateContravariantCapturedTypes() {
        return this.substitution.approximateContravariantCapturedTypes();
    }

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    public Annotations filterAnnotations(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        return this.substitution.filterAnnotations(annotations);
    }
}
