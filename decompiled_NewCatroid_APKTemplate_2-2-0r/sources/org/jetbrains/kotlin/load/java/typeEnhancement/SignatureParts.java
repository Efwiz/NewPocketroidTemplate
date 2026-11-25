package org.jetbrains.kotlin.load.java.typeEnhancement;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.load.java.AnnotationQualifierApplicabilityType;
import org.jetbrains.kotlin.load.java.JavaTypeQualifiersByElementType;
import org.jetbrains.kotlin.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B1\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010\fJ\u0016\u00106\u001a\u00020\u0006*\u00020\u00022\b\u00107\u001a\u0004\u0018\u00010&H\u0016J\f\u00108\u001a\u00020\u0006*\u00020&H\u0016J\u0014\u00109\u001a\u00020\u0006*\u00020&2\u0006\u0010:\u001a\u00020&H\u0016R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001dR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u001dR\u0014\u0010\u000b\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020\u0012*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\u0004\u0018\u00010**\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u001a\u0010-\u001a\u0004\u0018\u00010.*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0018\u00101\u001a\u00020\u0006*\u0002028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00103R\u0018\u00104\u001a\u00020\u0006*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00105¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureParts;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/AbstractSignatureParts;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "typeContainer", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;", "isCovariant", "", "containerContext", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "containerApplicabilityType", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "skipRawTypeArguments", "(Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;ZLorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;Z)V", "annotationTypeQualifierResolver", "Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;", "getAnnotationTypeQualifierResolver", "()Lorg/jetbrains/kotlin/load/java/AnnotationTypeQualifierResolver;", "containerAnnotations", "", "getContainerAnnotations", "()Ljava/lang/Iterable;", "getContainerApplicabilityType", "()Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "containerDefaultTypeQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "getContainerDefaultTypeQualifiers", "()Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "containerIsVarargParameter", "getContainerIsVarargParameter", "()Z", "enableImprovementsInStrictMode", "getEnableImprovementsInStrictMode", "getSkipRawTypeArguments", "typeSystem", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "annotations", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getAnnotations", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Ljava/lang/Iterable;", "enhancedForWarnings", "Lorg/jetbrains/kotlin/types/KotlinType;", "getEnhancedForWarnings", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/types/KotlinType;", "fqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getFqNameUnsafe", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "isFromJava", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)Z", "isNotNullTypeParameterCompat", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "forceWarning", "unenhancedType", "isArrayOrPrimitiveArray", "isEqual", "other", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SignatureParts extends AbstractSignatureParts<AnnotationDescriptor> {
    private final AnnotationQualifierApplicabilityType containerApplicabilityType;
    private final LazyJavaResolverContext containerContext;
    private final boolean isCovariant;
    private final boolean skipRawTypeArguments;
    private final Annotated typeContainer;

    public /* synthetic */ SignatureParts(Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(annotated, z, lazyJavaResolverContext, annotationQualifierApplicabilityType, (i & 16) != 0 ? false : z2);
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    /* JADX INFO: renamed from: isCovariant, reason: from getter */
    public boolean getIsCovariant() {
        return this.isCovariant;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public AnnotationQualifierApplicabilityType getContainerApplicabilityType() {
        return this.containerApplicabilityType;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getSkipRawTypeArguments() {
        return this.skipRawTypeArguments;
    }

    public SignatureParts(Annotated annotated, boolean z, LazyJavaResolverContext containerContext, AnnotationQualifierApplicabilityType containerApplicabilityType, boolean z2) {
        Intrinsics.checkNotNullParameter(containerContext, "containerContext");
        Intrinsics.checkNotNullParameter(containerApplicabilityType, "containerApplicabilityType");
        this.typeContainer = annotated;
        this.isCovariant = z;
        this.containerContext = containerContext;
        this.containerApplicabilityType = containerApplicabilityType;
        this.skipRawTypeArguments = z2;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    /* JADX INFO: renamed from: getAnnotationTypeQualifierResolver, reason: avoid collision after fix types in other method */
    public AbstractAnnotationTypeQualifierResolver<AnnotationDescriptor> getAnnotationTypeQualifierResolver2() {
        return this.containerContext.getComponents().getAnnotationTypeQualifierResolver();
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getEnableImprovementsInStrictMode() {
        return this.containerContext.getComponents().getSettings().get$typeEnhancementImprovementsInStrictMode();
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public Iterable<AnnotationDescriptor> getContainerAnnotations() {
        Iterable<AnnotationDescriptor> iterableEmptyList;
        Annotated annotated = this.typeContainer;
        if (annotated == null || (iterableEmptyList = annotated.getAnnotations()) == null) {
            iterableEmptyList = CollectionsKt.emptyList();
        }
        return iterableEmptyList;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public JavaTypeQualifiersByElementType getContainerDefaultTypeQualifiers() {
        return this.containerContext.getDefaultTypeQualifiers();
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getContainerIsVarargParameter() {
        Annotated annotated = this.typeContainer;
        return (annotated instanceof ValueParameterDescriptor) && ((ValueParameterDescriptor) annotated).getVarargElementType() != null;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public TypeSystemInferenceExtensionContext getTypeSystem() {
        return SimpleClassicTypeSystemContext.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean forceWarning(AnnotationDescriptor annotationDescriptor, KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        return ((annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor) && ((PossiblyExternalAnnotationDescriptor) annotationDescriptor).getIsIdeExternalAnnotation()) || ((annotationDescriptor instanceof LazyJavaAnnotationDescriptor) && !getEnableImprovementsInStrictMode() && (((LazyJavaAnnotationDescriptor) annotationDescriptor).getIsFreshlySupportedTypeUseAnnotation() || getContainerApplicabilityType() == AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS)) || (kotlinTypeMarker != null && KotlinBuiltIns.isPrimitiveArray((KotlinType) kotlinTypeMarker) && getAnnotationTypeQualifierResolver2().isTypeUseAnnotation(annotationDescriptor) && !this.containerContext.getComponents().getSettings().get$enhancePrimitiveArrays());
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public Iterable<AnnotationDescriptor> getAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return ((KotlinType) kotlinTypeMarker).getAnnotations();
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public KotlinType getEnhancedForWarnings(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return TypeWithEnhancementKt.getEnhancement((KotlinType) kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public FqNameUnsafe getFqNameUnsafe(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor((KotlinType) kotlinTypeMarker);
        if (classDescriptor != null) {
            return DescriptorUtils.getFqName(classDescriptor);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isNotNullTypeParameterCompat(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return ((KotlinType) kotlinTypeMarker).unwrap() instanceof NotNullTypeParameterImpl;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isEqual(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker other) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return this.containerContext.getComponents().getKotlinTypeChecker().equalTypes((KotlinType) kotlinTypeMarker, (KotlinType) other);
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isArrayOrPrimitiveArray(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return KotlinBuiltIns.isArrayOrPrimitiveArray((KotlinType) kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isFromJava(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return typeParameterMarker instanceof LazyJavaTypeParameterDescriptor;
    }
}
