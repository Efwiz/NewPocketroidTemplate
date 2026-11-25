package org.jetbrains.kotlin.fir.java.enhancement;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.load.java.AnnotationQualifierApplicabilityType;
import org.jetbrains.kotlin.load.java.JavaTypeQualifiersByElementType;
import org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;

/* JADX INFO: compiled from: SignatureEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BA\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\u0016\u00105\u001a\u00020\n*\u00020\u00022\b\u00106\u001a\u0004\u0018\u00010(H\u0016J\f\u00107\u001a\u00020\n*\u00020(H\u0016J\u0014\u00108\u001a\u00020\n*\u00020(2\u0006\u00109\u001a\u00020(H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001dR\u0014\u0010\u000b\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u001dR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u001e\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014*\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*R\u001a\u0010+\u001a\u0004\u0018\u00010(*\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u001a\u0010.\u001a\u0004\u0018\u00010/*\u00020(8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0018\u00102\u001a\u00020\n*\u0002038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u00104¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/EnhancementSignatureParts;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/AbstractSignatureParts;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "annotationTypeQualifierResolver", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirAnnotationTypeQualifierResolver;", "typeContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "isCovariant", "", "forceOnlyHeadTypeConstructor", "containerApplicabilityType", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "containerDefaultTypeQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/java/enhancement/FirAnnotationTypeQualifierResolver;Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;ZZLorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;)V", "getAnnotationTypeQualifierResolver", "()Lorg/jetbrains/kotlin/fir/java/enhancement/FirAnnotationTypeQualifierResolver;", "containerAnnotations", "", "getContainerAnnotations", "()Ljava/lang/Iterable;", "getContainerApplicabilityType", "()Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "getContainerDefaultTypeQualifiers", "()Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "containerIsVarargParameter", "getContainerIsVarargParameter", "()Z", "enableImprovementsInStrictMode", "getEnableImprovementsInStrictMode", "getForceOnlyHeadTypeConstructor", "skipRawTypeArguments", "getSkipRawTypeArguments", "typeSystem", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "annotations", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getAnnotations", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Ljava/lang/Iterable;", "enhancedForWarnings", "getEnhancedForWarnings", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "fqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getFqNameUnsafe", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "isFromJava", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)Z", "forceWarning", "unenhancedType", "isArrayOrPrimitiveArray", "isEqual", "other", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
final class EnhancementSignatureParts extends AbstractSignatureParts<FirAnnotation> {
    private final FirAnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final AnnotationQualifierApplicabilityType containerApplicabilityType;
    private final JavaTypeQualifiersByElementType containerDefaultTypeQualifiers;
    private final boolean forceOnlyHeadTypeConstructor;
    private final boolean isCovariant;
    private final FirSession session;
    private final FirAnnotationContainer typeContainer;

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean forceWarning(FirAnnotation firAnnotation, KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getEnableImprovementsInStrictMode() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public KotlinTypeMarker getEnhancedForWarnings(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getSkipRawTypeArguments() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    /* JADX INFO: renamed from: getAnnotationTypeQualifierResolver, reason: merged with bridge method [inline-methods] */
    public AbstractAnnotationTypeQualifierResolver<FirAnnotation> getAnnotationTypeQualifierResolver2() {
        return this.annotationTypeQualifierResolver;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    /* JADX INFO: renamed from: isCovariant, reason: from getter */
    public boolean getIsCovariant() {
        return this.isCovariant;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getForceOnlyHeadTypeConstructor() {
        return this.forceOnlyHeadTypeConstructor;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public AnnotationQualifierApplicabilityType getContainerApplicabilityType() {
        return this.containerApplicabilityType;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public JavaTypeQualifiersByElementType getContainerDefaultTypeQualifiers() {
        return this.containerDefaultTypeQualifiers;
    }

    public EnhancementSignatureParts(FirSession session, FirAnnotationTypeQualifierResolver annotationTypeQualifierResolver, FirAnnotationContainer firAnnotationContainer, boolean z, boolean z2, AnnotationQualifierApplicabilityType containerApplicabilityType, JavaTypeQualifiersByElementType javaTypeQualifiersByElementType) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkNotNullParameter(containerApplicabilityType, "containerApplicabilityType");
        this.session = session;
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.typeContainer = firAnnotationContainer;
        this.isCovariant = z;
        this.forceOnlyHeadTypeConstructor = z2;
        this.containerApplicabilityType = containerApplicabilityType;
        this.containerDefaultTypeQualifiers = javaTypeQualifiersByElementType;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public Iterable<FirAnnotation> getContainerAnnotations() {
        List<FirAnnotation> listEmptyList;
        FirAnnotationContainer firAnnotationContainer = this.typeContainer;
        if (firAnnotationContainer == null || (listEmptyList = firAnnotationContainer.getAnnotations()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean getContainerIsVarargParameter() {
        FirAnnotationContainer firAnnotationContainer = this.typeContainer;
        return (firAnnotationContainer instanceof FirValueParameter) && ((FirValueParameter) firAnnotationContainer).getIsVararg();
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public TypeSystemContext getTypeSystem() {
        return TypeComponentsKt.getTypeContext(this.session);
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public Iterable<FirAnnotation> getAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return CustomAnnotationTypeAttributeKt.getCustomAnnotations(((ConeKotlinType) kotlinTypeMarker).getAttributes());
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public FqNameUnsafe getFqNameUnsafe(KotlinTypeMarker kotlinTypeMarker) {
        ClassId classId;
        FqName fqNameAsSingleFqName;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        ConeLookupTagBasedType coneLookupTagBasedType = kotlinTypeMarker instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) kotlinTypeMarker : null;
        ConeClassifierLookupTag lookupTag = coneLookupTagBasedType != null ? coneLookupTagBasedType.getLookupTag() : null;
        ConeClassLikeLookupTag coneClassLikeLookupTag = lookupTag instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) lookupTag : null;
        if (coneClassLikeLookupTag == null || (classId = coneClassLikeLookupTag.getClassId()) == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null) {
            return null;
        }
        return fqNameAsSingleFqName.toUnsafe();
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isEqual(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker other) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, TypeComponentsKt.getTypeContext(this.session), kotlinTypeMarker, other, false, 8, null);
    }

    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isArrayOrPrimitiveArray(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return ArrayUtilsKt.isArrayOrPrimitiveArray((ConeKotlinType) kotlinTypeMarker);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts
    public boolean isFromJava(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return ((FirTypeParameter) ((ConeTypeParameterLookupTag) typeParameterMarker).getSymbol().getFir()).getOrigin() instanceof FirDeclarationOrigin.Java;
    }
}
