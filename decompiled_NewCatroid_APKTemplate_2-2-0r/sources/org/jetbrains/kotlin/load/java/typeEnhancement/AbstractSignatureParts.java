package org.jetbrains.kotlin.load.java.typeEnhancement;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.AbstractAnnotationTypeQualifierResolver;
import org.jetbrains.kotlin.load.java.AnnotationQualifierApplicabilityType;
import org.jetbrains.kotlin.load.java.JavaDefaultQualifiers;
import org.jetbrains.kotlin.load.java.JavaTypeQualifiersByElementType;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: AbstractSignatureParts.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\n\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0001\\B\u0005¢\u0006\u0002\u0010\u0003J\u001e\u0010;\u001a\u0004\u0018\u00010(2\b\u0010<\u001a\u0004\u0018\u00010(2\b\u0010=\u001a\u0004\u0018\u00010(H\u0002J<\u0010>\u001a\u0012\u0012\u0004\u0012\u00020@\u0012\u0004\u0012\u00020A0?j\u0002`B*\u00020$2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020$0\t2\b\u0010D\u001a\u0004\u0018\u00010E2\b\b\u0002\u0010F\u001a\u00020\u0015J\u0018\u0010G\u001a\u0004\u0018\u00010\u0011*\u00020$2\b\u0010H\u001a\u0004\u0018\u00010\u0011H\u0002J\f\u0010I\u001a\u00020A*\u00020$H\u0002J\f\u0010J\u001a\u00020A*\u00020KH\u0002J9\u0010L\u001a\b\u0012\u0004\u0012\u0002HN0M\"\u0004\b\u0001\u0010N*\u0002HN2\u001a\u0010O\u001a\u0016\u0012\u0004\u0012\u0002HN\u0012\f\u0012\n\u0012\u0004\u0012\u0002HN\u0018\u00010\t0?H\u0002¢\u0006\u0002\u0010PJA\u0010L\u001a\u00020Q\"\u0004\b\u0001\u0010N*\u0002HN2\f\u0010R\u001a\b\u0012\u0004\u0012\u0002HN0S2\u001a\u0010O\u001a\u0016\u0012\u0004\u0012\u0002HN\u0012\f\u0012\n\u0012\u0004\u0012\u0002HN\u0018\u00010\t0?H\u0002¢\u0006\u0002\u0010TJ\u001b\u0010U\u001a\u00020\u0015*\u00028\u00002\b\u0010V\u001a\u0004\u0018\u00010$H&¢\u0006\u0002\u0010WJ\f\u0010X\u001a\u00020\u0015*\u00020$H&J\u0014\u0010Y\u001a\u00020\u0015*\u00020$2\u0006\u0010Z\u001a\u00020$H&J\u0012\u0010[\u001a\b\u0012\u0004\u0012\u00020K0M*\u00020$H\u0002R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017R\u0012\u0010\u001c\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0017R\u0012\u0010\u001d\u001a\u00020\u0015X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0017R\u0012\u0010\u001f\u001a\u00020 X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000\t*\u00020$X¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u001a\u0010'\u001a\u0004\u0018\u00010(*\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+R\u0018\u0010,\u001a\u0004\u0018\u00010$*\u00020$X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u0010/\u001a\u0004\u0018\u000100*\u00020$X¦\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u0016\u00103\u001a\u00020\u0015*\u00020)X¦\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u0018\u00105\u001a\u00020\u0015*\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u001a\u00107\u001a\u0004\u0018\u000108*\u00020$8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b9\u0010:¨\u0006]"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/AbstractSignatureParts;", "TAnnotation", "", "()V", "annotationTypeQualifierResolver", "Lorg/jetbrains/kotlin/load/java/AbstractAnnotationTypeQualifierResolver;", "getAnnotationTypeQualifierResolver", "()Lorg/jetbrains/kotlin/load/java/AbstractAnnotationTypeQualifierResolver;", "containerAnnotations", "", "getContainerAnnotations", "()Ljava/lang/Iterable;", "containerApplicabilityType", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "getContainerApplicabilityType", "()Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "containerDefaultTypeQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "getContainerDefaultTypeQualifiers", "()Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "containerIsVarargParameter", "", "getContainerIsVarargParameter", "()Z", "enableImprovementsInStrictMode", "getEnableImprovementsInStrictMode", "forceOnlyHeadTypeConstructor", "getForceOnlyHeadTypeConstructor", "isCovariant", "skipRawTypeArguments", "getSkipRawTypeArguments", "typeSystem", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "annotations", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getAnnotations", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Ljava/lang/Iterable;", "boundsNullability", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "getBoundsNullability", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "enhancedForWarnings", "getEnhancedForWarnings", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "fqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getFqNameUnsafe", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "isFromJava", "(Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)Z", "isNotNullTypeParameterCompat", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "nullabilityQualifier", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;", "getNullabilityQualifier", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifier;", "mostSpecific", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "computeIndexedQualifiers", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/IndexedJavaTypeQualifiers;", "overrides", "predefined", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementInfo;", "ignoreDeclarationNullabilityAnnotations", "extractAndMergeDefaultQualifiers", "oldQualifiers", "extractQualifiers", "extractQualifiersFromAnnotations", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/AbstractSignatureParts$TypeAndDefaultQualifiers;", "flattenTree", "", ExifInterface.GPS_DIRECTION_TRUE, "children", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "", "result", "", "(Ljava/lang/Object;Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "forceWarning", "unenhancedType", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Z", "isArrayOrPrimitiveArray", "isEqual", "other", "toIndexed", "TypeAndDefaultQualifiers", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractSignatureParts<TAnnotation> {
    public abstract boolean forceWarning(TAnnotation tannotation, KotlinTypeMarker kotlinTypeMarker);

    public abstract AbstractAnnotationTypeQualifierResolver<TAnnotation> getAnnotationTypeQualifierResolver();

    public abstract Iterable<TAnnotation> getAnnotations(KotlinTypeMarker kotlinTypeMarker);

    public abstract Iterable<TAnnotation> getContainerAnnotations();

    public abstract AnnotationQualifierApplicabilityType getContainerApplicabilityType();

    public abstract JavaTypeQualifiersByElementType getContainerDefaultTypeQualifiers();

    public abstract boolean getContainerIsVarargParameter();

    public abstract boolean getEnableImprovementsInStrictMode();

    public abstract KotlinTypeMarker getEnhancedForWarnings(KotlinTypeMarker kotlinTypeMarker);

    public boolean getForceOnlyHeadTypeConstructor() {
        return false;
    }

    public abstract FqNameUnsafe getFqNameUnsafe(KotlinTypeMarker kotlinTypeMarker);

    public abstract boolean getSkipRawTypeArguments();

    public abstract TypeSystemContext getTypeSystem();

    public abstract boolean isArrayOrPrimitiveArray(KotlinTypeMarker kotlinTypeMarker);

    public abstract boolean isCovariant();

    public abstract boolean isEqual(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2);

    public abstract boolean isFromJava(TypeParameterMarker typeParameterMarker);

    public boolean isNotNullTypeParameterCompat(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return false;
    }

    private final NullabilityQualifier getNullabilityQualifier(KotlinTypeMarker kotlinTypeMarker) {
        TypeSystemContext typeSystem = getTypeSystem();
        if (typeSystem.isMarkedNullable(typeSystem.lowerBoundIfFlexible(kotlinTypeMarker))) {
            return NullabilityQualifier.NULLABLE;
        }
        if (typeSystem.isMarkedNullable(typeSystem.upperBoundIfFlexible(kotlinTypeMarker))) {
            return null;
        }
        return NullabilityQualifier.NOT_NULL;
    }

    private final JavaTypeQualifiers extractQualifiers(KotlinTypeMarker kotlinTypeMarker) {
        NullabilityQualifier nullabilityQualifier;
        NullabilityQualifier nullabilityQualifier2 = getNullabilityQualifier(kotlinTypeMarker);
        MutabilityQualifier mutabilityQualifier = null;
        if (nullabilityQualifier2 == null) {
            KotlinTypeMarker enhancedForWarnings = getEnhancedForWarnings(kotlinTypeMarker);
            nullabilityQualifier = enhancedForWarnings != null ? getNullabilityQualifier(enhancedForWarnings) : null;
        } else {
            nullabilityQualifier = nullabilityQualifier2;
        }
        TypeSystemContext typeSystem = getTypeSystem();
        if (JavaToKotlinClassMap.INSTANCE.isReadOnly(getFqNameUnsafe(typeSystem.lowerBoundIfFlexible(kotlinTypeMarker)))) {
            mutabilityQualifier = MutabilityQualifier.READ_ONLY;
        } else if (JavaToKotlinClassMap.INSTANCE.isMutable(getFqNameUnsafe(typeSystem.upperBoundIfFlexible(kotlinTypeMarker)))) {
            mutabilityQualifier = MutabilityQualifier.MUTABLE;
        }
        return new JavaTypeQualifiers(nullabilityQualifier, mutabilityQualifier, getTypeSystem().isDefinitelyNotNullType(kotlinTypeMarker) || isNotNullTypeParameterCompat(kotlinTypeMarker), nullabilityQualifier != nullabilityQualifier2);
    }

    private final JavaTypeQualifiers extractQualifiersFromAnnotations(final TypeAndDefaultQualifiers typeAndDefaultQualifiers) {
        List listEmptyList;
        AnnotationQualifierApplicabilityType containerApplicabilityType;
        NullabilityQualifierWithMigrationStatus nullabilityQualifier;
        NullabilityQualifierWithMigrationStatus boundsNullability;
        KotlinTypeMarker type;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor;
        if (typeAndDefaultQualifiers.getType() == null) {
            TypeSystemContext typeSystem = getTypeSystem();
            TypeParameterMarker typeParameterForArgument = typeAndDefaultQualifiers.getTypeParameterForArgument();
            if ((typeParameterForArgument != null ? typeSystem.getVariance(typeParameterForArgument) : null) == TypeVariance.IN) {
                return JavaTypeQualifiers.INSTANCE.getNONE();
            }
        }
        boolean z = typeAndDefaultQualifiers.getTypeParameterForArgument() == null;
        KotlinTypeMarker type2 = typeAndDefaultQualifiers.getType();
        if (type2 == null || (listEmptyList = getAnnotations(type2)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        TypeSystemContext typeSystem2 = getTypeSystem();
        KotlinTypeMarker type3 = typeAndDefaultQualifiers.getType();
        TypeParameterMarker typeParameterClassifier = (type3 == null || (typeConstructorMarkerTypeConstructor = typeSystem2.typeConstructor(type3)) == null) ? null : typeSystem2.getTypeParameterClassifier(typeConstructorMarkerTypeConstructor);
        boolean z2 = getContainerApplicabilityType() == AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS;
        if (z) {
            if (!z2 && getEnableImprovementsInStrictMode() && (type = typeAndDefaultQualifiers.getType()) != null && isArrayOrPrimitiveArray(type)) {
                Iterable<TAnnotation> containerAnnotations = getContainerAnnotations();
                ArrayList arrayList = new ArrayList();
                for (TAnnotation tannotation : containerAnnotations) {
                    if (!getAnnotationTypeQualifierResolver().isTypeUseAnnotation(tannotation)) {
                        arrayList.add(tannotation);
                    }
                }
                listEmptyList = CollectionsKt.plus((Collection) arrayList, (Iterable) listEmptyList);
            } else {
                listEmptyList = CollectionsKt.plus((Iterable) getContainerAnnotations(), (Iterable) listEmptyList);
            }
        }
        MutabilityQualifier mutabilityQualifierExtractMutability = getAnnotationTypeQualifierResolver().extractMutability(listEmptyList);
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = getAnnotationTypeQualifierResolver().extractNullability((Iterable) listEmptyList, (Function1) new Function1<TAnnotation, Boolean>(this) { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts$extractQualifiersFromAnnotations$annotationsNullability$1
            final /* synthetic */ AbstractSignatureParts<TAnnotation> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TAnnotation extractNullability) {
                Intrinsics.checkNotNullParameter(extractNullability, "$this$extractNullability");
                return Boolean.valueOf(this.this$0.forceWarning(extractNullability, typeAndDefaultQualifiers.getType()));
            }
        });
        if (nullabilityQualifierWithMigrationStatusExtractNullability != null) {
            return new JavaTypeQualifiers(nullabilityQualifierWithMigrationStatusExtractNullability.getQualifier(), mutabilityQualifierExtractMutability, nullabilityQualifierWithMigrationStatusExtractNullability.getQualifier() == NullabilityQualifier.NOT_NULL && typeParameterClassifier != null, nullabilityQualifierWithMigrationStatusExtractNullability.isForWarningOnly());
        }
        if (z || z2) {
            containerApplicabilityType = getContainerApplicabilityType();
        } else {
            containerApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_USE;
        }
        JavaTypeQualifiersByElementType defaultQualifiers = typeAndDefaultQualifiers.getDefaultQualifiers();
        JavaDefaultQualifiers javaDefaultQualifiers = defaultQualifiers != null ? defaultQualifiers.get(containerApplicabilityType) : null;
        NullabilityQualifierWithMigrationStatus boundsNullability2 = typeParameterClassifier != null ? getBoundsNullability(typeParameterClassifier) : null;
        if (boundsNullability2 == null || (nullabilityQualifier = NullabilityQualifierWithMigrationStatus.copy$default(boundsNullability2, NullabilityQualifier.NOT_NULL, false, 2, null)) == null) {
            nullabilityQualifier = javaDefaultQualifiers != null ? javaDefaultQualifiers.getNullabilityQualifier() : null;
        }
        boolean z3 = (boundsNullability2 != null ? boundsNullability2.getQualifier() : null) == NullabilityQualifier.NOT_NULL || !(typeParameterClassifier == null || javaDefaultQualifiers == null || !javaDefaultQualifiers.getDefinitelyNotNull());
        TypeParameterMarker typeParameterForArgument2 = typeAndDefaultQualifiers.getTypeParameterForArgument();
        if (typeParameterForArgument2 == null || (boundsNullability = getBoundsNullability(typeParameterForArgument2)) == null) {
            boundsNullability = null;
        } else if (boundsNullability.getQualifier() == NullabilityQualifier.NULLABLE) {
            boundsNullability = NullabilityQualifierWithMigrationStatus.copy$default(boundsNullability, NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null);
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusMostSpecific = mostSpecific(boundsNullability, nullabilityQualifier);
        return new JavaTypeQualifiers(nullabilityQualifierWithMigrationStatusMostSpecific != null ? nullabilityQualifierWithMigrationStatusMostSpecific.getQualifier() : null, mutabilityQualifierExtractMutability, z3, nullabilityQualifierWithMigrationStatusMostSpecific != null && nullabilityQualifierWithMigrationStatusMostSpecific.isForWarningOnly());
    }

    private final NullabilityQualifierWithMigrationStatus mostSpecific(NullabilityQualifierWithMigrationStatus a, NullabilityQualifierWithMigrationStatus b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.isForWarningOnly() && !b.isForWarningOnly()) {
            return b;
        }
        if (a.isForWarningOnly() || !b.isForWarningOnly()) {
            return (a.getQualifier().compareTo(b.getQualifier()) >= 0 && a.getQualifier().compareTo(b.getQualifier()) > 0) ? a : b;
        }
        return a;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:49:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:55:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:56:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:71:0x00cc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x00c9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:? A[LOOP:3: B:47:0x00b7->B:73:?, LOOP_END, SYNTHETIC] */
    private final NullabilityQualifierWithMigrationStatus getBoundsNullability(TypeParameterMarker typeParameterMarker) {
        ArrayList arrayList;
        List<KotlinTypeMarker> list;
        Iterator<T> it;
        NullabilityQualifier nullabilityQualifier;
        boolean z;
        TypeSystemContext typeSystem = getTypeSystem();
        if (!isFromJava(typeParameterMarker)) {
            return null;
        }
        List<KotlinTypeMarker> upperBounds = typeSystem.getUpperBounds(typeParameterMarker);
        List<KotlinTypeMarker> list2 = upperBounds;
        boolean z2 = list2 instanceof Collection;
        if (!z2 || !list2.isEmpty()) {
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                if (!typeSystem.isError((KotlinTypeMarker) it2.next())) {
                    if (!z2 || !list2.isEmpty()) {
                        Iterator<T> it3 = list2.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                if (getNullabilityQualifier((KotlinTypeMarker) it3.next()) != null) {
                                    arrayList = upperBounds;
                                }
                            }
                            list = arrayList;
                            if (!(list instanceof Collection) && list.isEmpty()) {
                                nullabilityQualifier = NullabilityQualifier.NULLABLE;
                                break;
                            }
                            it = list.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    nullabilityQualifier = NullabilityQualifier.NULLABLE;
                                    break;
                                }
                                if (!typeSystem.isNullableType((KotlinTypeMarker) it.next())) {
                                    nullabilityQualifier = NullabilityQualifier.NOT_NULL;
                                    break;
                                }
                            }
                            if (arrayList != upperBounds) {
                                z = true;
                            } else {
                                z = false;
                            }
                            return new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, z);
                        }
                    }
                    if (!z2 || !list2.isEmpty()) {
                        Iterator<T> it4 = list2.iterator();
                        while (it4.hasNext()) {
                            if (getEnhancedForWarnings((KotlinTypeMarker) it4.next()) != null) {
                                ArrayList arrayList2 = new ArrayList();
                                Iterator<T> it5 = list2.iterator();
                                while (it5.hasNext()) {
                                    KotlinTypeMarker enhancedForWarnings = getEnhancedForWarnings((KotlinTypeMarker) it5.next());
                                    if (enhancedForWarnings != null) {
                                        arrayList2.add(enhancedForWarnings);
                                    }
                                }
                                arrayList = arrayList2;
                                list = arrayList;
                                if (!(list instanceof Collection)) {
                                    it = list.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            nullabilityQualifier = NullabilityQualifier.NULLABLE;
                                            break;
                                        }
                                        if (!typeSystem.isNullableType((KotlinTypeMarker) it.next())) {
                                            nullabilityQualifier = NullabilityQualifier.NOT_NULL;
                                            break;
                                        }
                                    }
                                } else {
                                    it = list.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            nullabilityQualifier = NullabilityQualifier.NULLABLE;
                                            break;
                                        }
                                        if (!typeSystem.isNullableType((KotlinTypeMarker) it.next())) {
                                            nullabilityQualifier = NullabilityQualifier.NOT_NULL;
                                            break;
                                        }
                                    }
                                }
                                if (arrayList != upperBounds) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                return new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, z);
                            }
                        }
                        break;
                    }
                    break;
                }
            }
        }
        return null;
    }

    public static /* synthetic */ Function1 computeIndexedQualifiers$default(AbstractSignatureParts abstractSignatureParts, KotlinTypeMarker kotlinTypeMarker, Iterable iterable, TypeEnhancementInfo typeEnhancementInfo, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: computeIndexedQualifiers");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return abstractSignatureParts.computeIndexedQualifiers(kotlinTypeMarker, iterable, typeEnhancementInfo, z);
    }

    public final Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiers(KotlinTypeMarker kotlinTypeMarker, Iterable<? extends KotlinTypeMarker> overrides, final TypeEnhancementInfo typeEnhancementInfo, boolean z) {
        int size;
        KotlinTypeMarker type;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(overrides, "overrides");
        List<TypeAndDefaultQualifiers> indexed = toIndexed(kotlinTypeMarker);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overrides, 10));
        Iterator<? extends KotlinTypeMarker> it = overrides.iterator();
        while (it.hasNext()) {
            arrayList.add(toIndexed(it.next()));
        }
        ArrayList arrayList2 = arrayList;
        if (getForceOnlyHeadTypeConstructor()) {
            size = 1;
        } else {
            if (isCovariant() && (!(overrides instanceof Collection) || !((Collection) overrides).isEmpty())) {
                Iterator<? extends KotlinTypeMarker> it2 = overrides.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (!isEqual(kotlinTypeMarker, it2.next())) {
                            size = 1;
                        }
                    }
                }
            }
            size = indexed.size();
        }
        final JavaTypeQualifiers[] javaTypeQualifiersArr = new JavaTypeQualifiers[size];
        int i = 0;
        while (i < size) {
            JavaTypeQualifiers javaTypeQualifiersExtractQualifiersFromAnnotations = extractQualifiersFromAnnotations(indexed.get(i));
            ArrayList arrayList3 = new ArrayList();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                TypeAndDefaultQualifiers typeAndDefaultQualifiers = (TypeAndDefaultQualifiers) CollectionsKt.getOrNull((List) it3.next(), i);
                JavaTypeQualifiers javaTypeQualifiersExtractQualifiers = (typeAndDefaultQualifiers == null || (type = typeAndDefaultQualifiers.getType()) == null) ? null : extractQualifiers(type);
                if (javaTypeQualifiersExtractQualifiers != null) {
                    arrayList3.add(javaTypeQualifiersExtractQualifiers);
                }
            }
            javaTypeQualifiersArr[i] = TypeEnhancementUtilsKt.computeQualifiersForOverride(javaTypeQualifiersExtractQualifiersFromAnnotations, arrayList3, i == 0 && isCovariant(), i == 0 && getContainerIsVarargParameter(), z);
            i++;
        }
        return new Function1<Integer, JavaTypeQualifiers>() { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts.computeIndexedQualifiers.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final JavaTypeQualifiers invoke(int i2) {
                Map<Integer, JavaTypeQualifiers> map;
                JavaTypeQualifiers javaTypeQualifiers;
                TypeEnhancementInfo typeEnhancementInfo2 = typeEnhancementInfo;
                if (typeEnhancementInfo2 != null && (map = typeEnhancementInfo2.getMap()) != null && (javaTypeQualifiers = map.get(Integer.valueOf(i2))) != null) {
                    return javaTypeQualifiers;
                }
                JavaTypeQualifiers[] javaTypeQualifiersArr2 = javaTypeQualifiersArr;
                return (i2 < 0 || i2 > ArraysKt.getLastIndex(javaTypeQualifiersArr2)) ? JavaTypeQualifiers.INSTANCE.getNONE() : javaTypeQualifiersArr2[i2];
            }
        };
    }

    private final <T> void flattenTree(T t, List<T> list, Function1<? super T, ? extends Iterable<? extends T>> function1) {
        list.add(t);
        Iterable<? extends T> iterableInvoke = function1.invoke(t);
        if (iterableInvoke != null) {
            Iterator<? extends T> it = iterableInvoke.iterator();
            while (it.hasNext()) {
                flattenTree(it.next(), list, function1);
            }
        }
    }

    private final <T> List<T> flattenTree(T t, Function1<? super T, ? extends Iterable<? extends T>> function1) {
        ArrayList arrayList = new ArrayList(1);
        flattenTree(t, arrayList, function1);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JavaTypeQualifiersByElementType extractAndMergeDefaultQualifiers(KotlinTypeMarker kotlinTypeMarker, JavaTypeQualifiersByElementType javaTypeQualifiersByElementType) {
        return getAnnotationTypeQualifierResolver().extractAndMergeDefaultQualifiers(javaTypeQualifiersByElementType, getAnnotations(kotlinTypeMarker));
    }

    private final List<TypeAndDefaultQualifiers> toIndexed(KotlinTypeMarker kotlinTypeMarker) {
        final TypeSystemContext typeSystem = getTypeSystem();
        return flattenTree(new TypeAndDefaultQualifiers(kotlinTypeMarker, extractAndMergeDefaultQualifiers(kotlinTypeMarker, getContainerDefaultTypeQualifiers()), null), new Function1<TypeAndDefaultQualifiers, Iterable<? extends TypeAndDefaultQualifiers>>(this) { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.AbstractSignatureParts$toIndexed$1$1
            final /* synthetic */ AbstractSignatureParts<TAnnotation> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Iterable<AbstractSignatureParts.TypeAndDefaultQualifiers> invoke(AbstractSignatureParts.TypeAndDefaultQualifiers it) {
                TypeConstructorMarker typeConstructorMarkerTypeConstructor;
                List<TypeParameterMarker> parameters;
                AbstractSignatureParts.TypeAndDefaultQualifiers typeAndDefaultQualifiers;
                KotlinTypeMarker type;
                Intrinsics.checkNotNullParameter(it, "it");
                ArrayList arrayList = null;
                if (this.this$0.getSkipRawTypeArguments() && (type = it.getType()) != null && typeSystem.isRawType(type)) {
                    return null;
                }
                KotlinTypeMarker type2 = it.getType();
                if (type2 != null && (typeConstructorMarkerTypeConstructor = typeSystem.typeConstructor(type2)) != null && (parameters = typeSystem.getParameters(typeConstructorMarkerTypeConstructor)) != null) {
                    List<TypeParameterMarker> list = parameters;
                    List<TypeArgumentMarker> arguments = typeSystem.getArguments(it.getType());
                    TypeSystemContext typeSystemContext = typeSystem;
                    AbstractSignatureParts<TAnnotation> abstractSignatureParts = this.this$0;
                    Iterator<T> it2 = list.iterator();
                    Iterator<T> it3 = arguments.iterator();
                    ArrayList arrayList2 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), CollectionsKt.collectionSizeOrDefault(arguments, 10)));
                    while (it2.hasNext() && it3.hasNext()) {
                        Object next = it2.next();
                        TypeArgumentMarker typeArgumentMarker = (TypeArgumentMarker) it3.next();
                        TypeParameterMarker typeParameterMarker = (TypeParameterMarker) next;
                        if (typeSystemContext.isStarProjection(typeArgumentMarker)) {
                            typeAndDefaultQualifiers = new AbstractSignatureParts.TypeAndDefaultQualifiers(null, it.getDefaultQualifiers(), typeParameterMarker);
                        } else {
                            KotlinTypeMarker type3 = typeSystemContext.getType(typeArgumentMarker);
                            typeAndDefaultQualifiers = new AbstractSignatureParts.TypeAndDefaultQualifiers(type3, abstractSignatureParts.extractAndMergeDefaultQualifiers(type3, it.getDefaultQualifiers()), typeParameterMarker);
                        }
                        arrayList2.add(typeAndDefaultQualifiers);
                    }
                    arrayList = arrayList2;
                }
                return arrayList;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AbstractSignatureParts.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/AbstractSignatureParts$TypeAndDefaultQualifiers;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "defaultQualifiers", "Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "typeParameterForArgument", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)V", "getDefaultQualifiers", "()Lorg/jetbrains/kotlin/load/java/JavaTypeQualifiersByElementType;", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getTypeParameterForArgument", "()Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class TypeAndDefaultQualifiers {
        private final JavaTypeQualifiersByElementType defaultQualifiers;
        private final KotlinTypeMarker type;
        private final TypeParameterMarker typeParameterForArgument;

        public TypeAndDefaultQualifiers(KotlinTypeMarker kotlinTypeMarker, JavaTypeQualifiersByElementType javaTypeQualifiersByElementType, TypeParameterMarker typeParameterMarker) {
            this.type = kotlinTypeMarker;
            this.defaultQualifiers = javaTypeQualifiersByElementType;
            this.typeParameterForArgument = typeParameterMarker;
        }

        public final KotlinTypeMarker getType() {
            return this.type;
        }

        public final JavaTypeQualifiersByElementType getDefaultQualifiers() {
            return this.defaultQualifiers;
        }

        public final TypeParameterMarker getTypeParameterForArgument() {
            return this.typeParameterForArgument;
        }
    }
}
