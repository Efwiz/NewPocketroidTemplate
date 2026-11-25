package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0006\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH¦\u0002J\b\u0010\u000f\u001a\u00020\u0004H\u0016J\u0018\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0006\u0010\u0014\u001a\u00020\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeSubstitution;", "", "()V", "approximateCapturedTypes", "", "approximateContravariantCapturedTypes", "buildSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "filterAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotations", "get", "Lorg/jetbrains/kotlin/types/TypeProjection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "isEmpty", "prepareTopLevelType", "topLevelType", "position", "Lorg/jetbrains/kotlin/types/Variance;", "replaceWithNonApproximating", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeSubstitution {
    public static final TypeSubstitution EMPTY = new TypeSubstitution() { // from class: org.jetbrains.kotlin.types.TypeSubstitution$Companion$EMPTY$1
        public Void get(KotlinType key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return null;
        }

        @Override // org.jetbrains.kotlin.types.TypeSubstitution
        public boolean isEmpty() {
            return true;
        }

        @Override // org.jetbrains.kotlin.types.TypeSubstitution
        /* JADX INFO: renamed from: get, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ TypeProjection mo7475get(KotlinType kotlinType) {
            return (TypeProjection) get(kotlinType);
        }

        public String toString() {
            return "Empty TypeSubstitution";
        }
    };

    public boolean approximateCapturedTypes() {
        return false;
    }

    public boolean approximateContravariantCapturedTypes() {
        return false;
    }

    public Annotations filterAnnotations(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        return annotations;
    }

    /* JADX INFO: renamed from: get */
    public abstract TypeProjection mo7475get(KotlinType key);

    public boolean isEmpty() {
        return false;
    }

    public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        Intrinsics.checkNotNullParameter(position, "position");
        return topLevelType;
    }

    public final TypeSubstitutor buildSubstitutor() {
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(this);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        return typeSubstitutorCreate;
    }

    public final TypeSubstitution replaceWithNonApproximating() {
        return new TypeSubstitution() { // from class: org.jetbrains.kotlin.types.TypeSubstitution.replaceWithNonApproximating.1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public boolean approximateCapturedTypes() {
                return false;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public boolean approximateContravariantCapturedTypes() {
                return false;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return TypeSubstitution.this.mo7475get(key);
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public Annotations filterAnnotations(Annotations annotations) {
                Intrinsics.checkNotNullParameter(annotations, "annotations");
                return TypeSubstitution.this.filterAnnotations(annotations);
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public KotlinType prepareTopLevelType(KotlinType topLevelType, Variance position) {
                Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
                Intrinsics.checkNotNullParameter(position, "position");
                return TypeSubstitution.this.prepareTopLevelType(topLevelType, position);
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public boolean isEmpty() {
                return TypeSubstitution.this.isEmpty();
            }
        };
    }
}
