package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;

/* JADX INFO: compiled from: TypeAliasExpansionReportStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0016J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH&J\"\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\u0007H&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H&J\u0018\u0010\u0013\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H&¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "", "boundsViolationInSubstitution", "", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "unsubstitutedArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "argument", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "conflictingProjection", "typeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "substitutedArgument", "recursiveTypeAlias", "repeatedAnnotation", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "wrongNumberOfTypeArguments", "numberOfParameters", "", "DO_NOTHING", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeAliasExpansionReportStrategy {
    void boundsViolationInSubstitution(TypeSubstitutor substitutor, KotlinType unsubstitutedArgument, KotlinType argument, TypeParameterDescriptor typeParameter);

    void conflictingProjection(TypeAliasDescriptor typeAlias, TypeParameterDescriptor typeParameter, KotlinType substitutedArgument);

    void recursiveTypeAlias(TypeAliasDescriptor typeAlias);

    void repeatedAnnotation(AnnotationDescriptor annotation);

    void wrongNumberOfTypeArguments(TypeAliasDescriptor typeAlias, int numberOfParameters);

    /* JADX INFO: compiled from: TypeAliasExpansionReportStrategy.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\"\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000f\u001a\u00020\bH\u0016J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy$DO_NOTHING;", "Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "()V", "boundsViolationInSubstitution", "", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "unsubstitutedArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "argument", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "conflictingProjection", "typeAlias", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "substitutedArgument", "recursiveTypeAlias", "repeatedAnnotation", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "wrongNumberOfTypeArguments", "numberOfParameters", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DO_NOTHING implements TypeAliasExpansionReportStrategy {
        public static final DO_NOTHING INSTANCE = new DO_NOTHING();

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void boundsViolationInSubstitution(TypeSubstitutor substitutor, KotlinType unsubstitutedArgument, KotlinType argument, TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(substitutor, "substitutor");
            Intrinsics.checkNotNullParameter(unsubstitutedArgument, "unsubstitutedArgument");
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void conflictingProjection(TypeAliasDescriptor typeAlias, TypeParameterDescriptor typeParameter, KotlinType substitutedArgument) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(substitutedArgument, "substitutedArgument");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void recursiveTypeAlias(TypeAliasDescriptor typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void repeatedAnnotation(AnnotationDescriptor annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
        }

        @Override // org.jetbrains.kotlin.types.TypeAliasExpansionReportStrategy
        public void wrongNumberOfTypeArguments(TypeAliasDescriptor typeAlias, int numberOfParameters) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        }

        private DO_NOTHING() {
        }
    }
}
