package org.jetbrains.kotlin.fir.java;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.java.enhancement.JavaTypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttribute;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRef;
import org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRefBuilder;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaTypesKt;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: JavaTypeConversion.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a$\u0010\u0007\u001a\u00020\b*\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f\u001a9\u0010\r\u001a\u00020\u000e*\u00020\u00042\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u00102\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0002¢\u0006\u0002\u0010\u0015\u001a8\u0010\u0016\u001a\u00020\u0017*\u0004\u0018\u00010\u00182\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aH\u0002\u001a8\u0010\u001c\u001a\u00020\u001d*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0002\u001a&\u0010\u001f\u001a\u00020\u0017*\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0000\u001a@\u0010 \u001a\u00020\u0011*\u0004\u0018\u00010\u00182\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u000b\u001a\u00020\f2\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001aH\u0002\u001a\u0014\u0010#\u001a\u00020$*\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a(\u0010%\u001a\u00020&*\u0004\u0018\u00010\u00182\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0000¨\u0006'"}, d2 = {"argumentsMakeSenseOnlyForMutableContainer", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolveIfJavaType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "mode", "Lorg/jetbrains/kotlin/fir/java/FirJavaTypeConversionMode;", "toConeFlexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "typeArgumentsForUpper", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "(Lorg/jetbrains/kotlin/name/ClassId;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "toConeKotlinType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "additionalAnnotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "toConeKotlinTypeForFlexibleBound", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "lowerBound", "toConeKotlinTypeProbablyFlexible", "toConeTypeProjection", "parameterVariance", "Lorg/jetbrains/kotlin/types/Variance;", "toFirJavaTypeRef", "Lorg/jetbrains/kotlin/fir/types/jvm/FirJavaTypeRef;", "toFirResolvedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeConversionKt {

    /* JADX INFO: compiled from: JavaTypeConversion.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirJavaTypeConversionMode.values().length];
            try {
                iArr[FirJavaTypeConversionMode.ANNOTATION_MEMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private static final ConeFlexibleType toConeFlexibleType(ClassId classId, ConeTypeProjection[] coneTypeProjectionArr, ConeTypeProjection[] coneTypeProjectionArr2, ConeAttributes coneAttributes) {
        ConeClassLikeLookupTagImpl lookupTag = TypeConstructionUtilsKt.toLookupTag(classId);
        return new ConeFlexibleType(TypeConstructionUtilsKt.constructClassType(lookupTag, coneTypeProjectionArr, false, coneAttributes), TypeConstructionUtilsKt.constructClassType(lookupTag, coneTypeProjectionArr2, true, coneAttributes));
    }

    public static /* synthetic */ FirTypeRef resolveIfJavaType$default(FirTypeRef firTypeRef, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, int i, Object obj) {
        if ((i & 4) != 0) {
            firJavaTypeConversionMode = FirJavaTypeConversionMode.DEFAULT;
        }
        return resolveIfJavaType(firTypeRef, firSession, javaTypeParameterStack, firJavaTypeConversionMode);
    }

    public static final FirTypeRef resolveIfJavaType(FirTypeRef firTypeRef, FirSession session, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode mode) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return (!(firTypeRef instanceof FirResolvedTypeRef) && (firTypeRef instanceof FirJavaTypeRef)) ? toFirResolvedTypeRef(((FirJavaTypeRef) firTypeRef).getType(), session, javaTypeParameterStack, mode) : firTypeRef;
    }

    public static /* synthetic */ ConeKotlinType toConeKotlinTypeProbablyFlexible$default(FirTypeRef firTypeRef, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, int i, Object obj) {
        if ((i & 4) != 0) {
            firJavaTypeConversionMode = FirJavaTypeConversionMode.DEFAULT;
        }
        return toConeKotlinTypeProbablyFlexible(firTypeRef, firSession, javaTypeParameterStack, firJavaTypeConversionMode);
    }

    public static final ConeKotlinType toConeKotlinTypeProbablyFlexible(FirTypeRef firTypeRef, FirSession session, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode mode) {
        ConeKotlinType type;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        Intrinsics.checkNotNullParameter(mode, "mode");
        FirTypeRef firTypeRefResolveIfJavaType = resolveIfJavaType(firTypeRef, session, javaTypeParameterStack, mode);
        FirResolvedTypeRef firResolvedTypeRef = firTypeRefResolveIfJavaType instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRefResolveIfJavaType : null;
        return (firResolvedTypeRef == null || (type = firResolvedTypeRef.getType()) == null) ? new ConeErrorType(new ConeSimpleDiagnostic("Type reference in Java not resolved: " + firTypeRef.getClass(), DiagnosticKind.Java), false, null, null, 14, null) : type;
    }

    public static /* synthetic */ FirResolvedTypeRef toFirResolvedTypeRef$default(JavaType javaType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, int i, Object obj) {
        if ((i & 4) != 0) {
            firJavaTypeConversionMode = FirJavaTypeConversionMode.DEFAULT;
        }
        return toFirResolvedTypeRef(javaType, firSession, javaTypeParameterStack, firJavaTypeConversionMode);
    }

    static /* synthetic */ ConeKotlinType toConeKotlinType$default(JavaType javaType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, Collection collection, int i, Object obj) {
        if ((i & 8) != 0) {
            collection = null;
        }
        return toConeKotlinType(javaType, firSession, javaTypeParameterStack, firJavaTypeConversionMode, collection);
    }

    private static final ConeKotlinType toConeKotlinType(JavaType javaType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, Collection<? extends JavaAnnotation> collection) {
        ConeKotlinType type = ConeTypeProjectionKt.getType(toConeTypeProjection(javaType, firSession, javaTypeParameterStack, Variance.INVARIANT, firJavaTypeConversionMode, collection));
        return type == null ? toConeFlexibleType(StandardClassIds.INSTANCE.getAny(), new ConeTypeProjection[0], new ConeTypeProjection[0], ConeAttributes.INSTANCE.getEmpty()) : type;
    }

    static /* synthetic */ ConeTypeProjection toConeTypeProjection$default(JavaType javaType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, Variance variance, FirJavaTypeConversionMode firJavaTypeConversionMode, Collection collection, int i, Object obj) {
        if ((i & 16) != 0) {
            collection = null;
        }
        return toConeTypeProjection(javaType, firSession, javaTypeParameterStack, variance, firJavaTypeConversionMode, collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final ConeTypeProjection toConeTypeProjection(JavaType javaType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, Variance variance, FirJavaTypeConversionMode firJavaTypeConversionMode, Collection<? extends JavaAnnotation> collection) {
        ConeAttributes empty;
        String identifier;
        Name typeName;
        Pair pair;
        ConeFlexibleType coneFlexibleType;
        ConeDefinitelyNotNullType coneDefinitelyNotNullTypeCreate;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (javaType != null && (!javaType.mo7252getAnnotations().isEmpty() || collection != null)) {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            if (!javaType.mo7252getAnnotations().isEmpty()) {
                listCreateListBuilder.addAll(JavaAnnotationsMappingKt.convertAnnotationsToFir(javaType, firSession));
            }
            if (collection != null) {
                listCreateListBuilder.addAll(JavaAnnotationsMappingKt.convertAnnotationsToFir(collection, firSession));
            }
            empty = ConeAttributes.INSTANCE.create(CollectionsKt.listOf(new CustomAnnotationTypeAttribute(CollectionsKt.build(listCreateListBuilder), objArr2 == true ? 1 : 0, 2, objArr == true ? 1 : 0)));
        } else {
            empty = ConeAttributes.INSTANCE.getEmpty();
        }
        ConeAttributes coneAttributes = empty;
        if (javaType instanceof JavaClassifierType) {
            JavaClassifierType javaClassifierType = (JavaClassifierType) javaType;
            ConeLookupTagBasedType coneKotlinTypeForFlexibleBound$default = toConeKotlinTypeForFlexibleBound$default(javaClassifierType, firSession, javaTypeParameterStack, firJavaTypeConversionMode, coneAttributes, null, 16, null);
            if (firJavaTypeConversionMode == FirJavaTypeConversionMode.ANNOTATION_MEMBER) {
                return coneKotlinTypeForFlexibleBound$default;
            }
            ConeLookupTagBasedType coneKotlinTypeForFlexibleBound = toConeKotlinTypeForFlexibleBound(javaClassifierType, firSession, javaTypeParameterStack, firJavaTypeConversionMode, coneAttributes, coneKotlinTypeForFlexibleBound$default);
            ConeLookupTagBasedType coneLookupTagBasedType = (!(coneKotlinTypeForFlexibleBound$default instanceof ConeTypeParameterType) || (coneDefinitelyNotNullTypeCreate = TypeUtilsKt.create(ConeDefinitelyNotNullType.INSTANCE, coneKotlinTypeForFlexibleBound$default, TypeComponentsKt.getTypeContext(firSession), true)) == null) ? coneKotlinTypeForFlexibleBound$default : coneDefinitelyNotNullTypeCreate;
            return javaClassifierType.mo7271isRaw() ? ConeRawType.INSTANCE.create(coneLookupTagBasedType, coneKotlinTypeForFlexibleBound) : new ConeFlexibleType(coneLookupTagBasedType, coneKotlinTypeForFlexibleBound);
        }
        if (javaType instanceof JavaArrayType) {
            JavaType componentType = ((JavaArrayType) javaType).getComponentType();
            if (componentType instanceof JavaPrimitiveType) {
                StandardClassIds standardClassIds = StandardClassIds.INSTANCE;
                PrimitiveType type = ((JavaPrimitiveType) componentType).getType();
                Intrinsics.checkNotNull(type);
                String identifier2 = type.getArrayTypeName().getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier2, "getIdentifier(...)");
                pair = TuplesKt.to(standardClassIds.byName(identifier2), new ConeKotlinType[0]);
            } else {
                pair = TuplesKt.to(StandardClassIds.INSTANCE.getArray(), new ConeKotlinType[]{toConeKotlinType$default(componentType, firSession, javaTypeParameterStack, firJavaTypeConversionMode, null, 8, null)});
            }
            ClassId classId = (ClassId) pair.component1();
            ConeKotlinType[] coneKotlinTypeArr = (ConeKotlinType[]) pair.component2();
            if (firJavaTypeConversionMode == FirJavaTypeConversionMode.ANNOTATION_MEMBER) {
                coneFlexibleType = TypeConstructionUtilsKt.constructClassLikeType(classId, coneKotlinTypeArr, false, coneAttributes);
            } else {
                int length = coneKotlinTypeArr.length;
                ConeKotlinTypeProjectionOut[] coneKotlinTypeProjectionOutArr = new ConeKotlinTypeProjectionOut[length];
                for (int i = 0; i < length; i++) {
                    coneKotlinTypeProjectionOutArr[i] = new ConeKotlinTypeProjectionOut(coneKotlinTypeArr[i]);
                }
                coneFlexibleType = toConeFlexibleType(classId, coneKotlinTypeArr, coneKotlinTypeProjectionOutArr, coneAttributes);
            }
            return coneFlexibleType;
        }
        if (javaType instanceof JavaPrimitiveType) {
            StandardClassIds standardClassIds2 = StandardClassIds.INSTANCE;
            PrimitiveType type2 = ((JavaPrimitiveType) javaType).getType();
            if (type2 == null || (typeName = type2.getTypeName()) == null || (identifier = typeName.getIdentifier()) == null) {
                identifier = "Unit";
            }
            return TypeConstructionUtilsKt.constructClassLikeType(standardClassIds2.byName(identifier), new ConeTypeProjection[0], false, coneAttributes);
        }
        if (!(javaType instanceof JavaWildcardType)) {
            if (javaType == null) {
                return ConeStarProjection.INSTANCE;
            }
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Strange JavaType: " + javaType.getClass(), null);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            exceptionAttachmentBuilder.withEntry(ModuleXmlParser.TYPE, javaType, new Function1<JavaType, String>() { // from class: org.jetbrains.kotlin.fir.java.JavaTypeConversionKt$toConeTypeProjection$1$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(JavaType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.toString();
                }
            });
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
        JavaWildcardType javaWildcardType = (JavaWildcardType) javaType;
        JavaType bound = javaWildcardType.getBound();
        Variance variance2 = javaWildcardType.getIsExtends() ? Variance.OUT_VARIANCE : Variance.IN_VARIANCE;
        if (bound == null || (variance != Variance.INVARIANT && variance != variance2)) {
            return ConeStarProjection.INSTANCE;
        }
        JavaAnnotation javaAnnotationExtractNullabilityAnnotationOnBoundedWildcard = JavaUtilsKt.extractNullabilityAnnotationOnBoundedWildcard(javaWildcardType);
        ConeKotlinType coneKotlinType = toConeKotlinType(bound, firSession, javaTypeParameterStack, firJavaTypeConversionMode, javaAnnotationExtractNullabilityAnnotationOnBoundedWildcard != null ? CollectionsKt.listOf(javaAnnotationExtractNullabilityAnnotationOnBoundedWildcard) : null);
        return javaWildcardType.getIsExtends() ? new ConeKotlinTypeProjectionOut(coneKotlinType) : new ConeKotlinTypeProjectionIn(coneKotlinType);
    }

    static /* synthetic */ ConeLookupTagBasedType toConeKotlinTypeForFlexibleBound$default(JavaClassifierType javaClassifierType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, ConeAttributes coneAttributes, ConeLookupTagBasedType coneLookupTagBasedType, int i, Object obj) {
        if ((i & 16) != 0) {
            coneLookupTagBasedType = null;
        }
        return toConeKotlinTypeForFlexibleBound(javaClassifierType, firSession, javaTypeParameterStack, firJavaTypeConversionMode, coneAttributes, coneLookupTagBasedType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final ConeLookupTagBasedType toConeKotlinTypeForFlexibleBound(JavaClassifierType javaClassifierType, FirSession firSession, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode firJavaTypeConversionMode, ConeAttributes coneAttributes, ConeLookupTagBasedType coneLookupTagBasedType) {
        ClassId classIdMapJavaToKotlin;
        ClassId onlyToMutable;
        Variance variance;
        FirTypeParameterSymbol firTypeParameterSymbol;
        FirTypeParameter firTypeParameter;
        FirRegularClassSymbol firRegularClassSymbol;
        FirRegularClassSymbol firRegularClassSymbol2;
        JavaClassifier classifier = javaClassifierType.getClassifier();
        if (classifier instanceof JavaClass) {
            if (firJavaTypeConversionMode == FirJavaTypeConversionMode.ANNOTATION_MEMBER) {
                JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
                FqName fqName = ((JavaClass) classifier).getFqName();
                Intrinsics.checkNotNull(fqName);
                classIdMapJavaToKotlin = javaToKotlinClassMap.mapJavaToKotlinIncludingClassMapping(fqName);
            } else {
                JavaToKotlinClassMap javaToKotlinClassMap2 = JavaToKotlinClassMap.INSTANCE;
                FqName fqName2 = ((JavaClass) classifier).getFqName();
                Intrinsics.checkNotNull(fqName2);
                classIdMapJavaToKotlin = javaToKotlinClassMap2.mapJavaToKotlin(fqName2);
            }
            if (classIdMapJavaToKotlin == null) {
                classIdMapJavaToKotlin = JavaElementsKt.getClassId((JavaClass) classifier);
                Intrinsics.checkNotNull(classIdMapJavaToKotlin);
            }
            if ((coneLookupTagBasedType == null || argumentsMakeSenseOnlyForMutableContainer(javaClassifierType, classIdMapJavaToKotlin, firSession)) && (onlyToMutable = JavaTypeUtilsKt.readOnlyToMutable(classIdMapJavaToKotlin)) != null) {
                classIdMapJavaToKotlin = onlyToMutable;
            }
            ConeClassLikeLookupTagImpl lookupTag = TypeConstructionUtilsKt.toLookupTag(classIdMapJavaToKotlin);
            ConeStarProjection[] typeArguments = null;
            typeParameterSymbols = null;
            List<FirTypeParameterSymbol> typeParameterSymbols = null;
            typeParameterSymbols = null;
            List<FirTypeParameterSymbol> typeParameterSymbols2 = null;
            if (javaClassifierType.mo7271isRaw()) {
                ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl = (coneLookupTagBasedType != null || firJavaTypeConversionMode == FirJavaTypeConversionMode.TYPE_PARAMETER_BOUND_FIRST_ROUND) ? null : lookupTag;
                if (coneClassLikeLookupTagImpl != null && (firRegularClassSymbol2 = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagImpl, firSession)) != null) {
                    typeParameterSymbols = firRegularClassSymbol2.getTypeParameterSymbols();
                }
                if (WhenMappings.$EnumSwitchMapping$0[firJavaTypeConversionMode.ordinal()] == 1) {
                    int size = ((JavaClass) classifier).mo7258getTypeParameters().size();
                    ConeStarProjection[] coneStarProjectionArr = new ConeStarProjection[size];
                    for (int i = 0; i < size; i++) {
                        coneStarProjectionArr[i] = ConeStarProjection.INSTANCE;
                    }
                    typeArguments = coneStarProjectionArr;
                } else if (typeParameterSymbols == null || (typeArguments = TypeUtilsKt.getProjectionsForRawType(typeParameterSymbols, firSession)) == null) {
                    int size2 = ((JavaClass) classifier).mo7258getTypeParameters().size();
                    ConeStarProjection[] coneStarProjectionArr2 = new ConeStarProjection[size2];
                    for (int i2 = 0; i2 < size2; i2++) {
                        coneStarProjectionArr2[i2] = ConeStarProjection.INSTANCE;
                    }
                    typeArguments = coneStarProjectionArr2;
                }
            } else if (!Intrinsics.areEqual(lookupTag, coneLookupTagBasedType != null ? coneLookupTagBasedType.getLookupTag() : null) && !javaClassifierType.mo7270getTypeArguments().isEmpty()) {
                ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl2 = firJavaTypeConversionMode != FirJavaTypeConversionMode.TYPE_PARAMETER_BOUND_FIRST_ROUND ? lookupTag : null;
                if (coneClassLikeLookupTagImpl2 != null && (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagImpl2, firSession)) != null) {
                    typeParameterSymbols2 = firRegularClassSymbol.getTypeParameterSymbols();
                }
                List<FirTypeParameterSymbol> list = typeParameterSymbols2;
                int size3 = javaClassifierType.mo7270getTypeArguments().size();
                ConeTypeProjection[] coneTypeProjectionArr = new ConeTypeProjection[size3];
                int i3 = 0;
                while (i3 < size3) {
                    FirJavaTypeConversionMode firJavaTypeConversionMode2 = firJavaTypeConversionMode == FirJavaTypeConversionMode.ANNOTATION_MEMBER ? FirJavaTypeConversionMode.DEFAULT : firJavaTypeConversionMode;
                    JavaType javaType = javaClassifierType.mo7270getTypeArguments().get(i3);
                    if (list == null || (firTypeParameterSymbol = (FirTypeParameterSymbol) CollectionsKt.getOrNull(list, i3)) == null || (firTypeParameter = (FirTypeParameter) firTypeParameterSymbol.getFir()) == null || (variance = firTypeParameter.getVariance()) == null) {
                        variance = Variance.INVARIANT;
                    }
                    int i4 = i3;
                    ConeTypeProjection[] coneTypeProjectionArr2 = coneTypeProjectionArr;
                    coneTypeProjectionArr2[i4] = toConeTypeProjection$default(javaType, firSession, javaTypeParameterStack, variance, firJavaTypeConversionMode2, null, 16, null);
                    i3 = i4 + 1;
                    size3 = size3;
                    coneTypeProjectionArr = coneTypeProjectionArr2;
                }
                typeArguments = coneTypeProjectionArr;
            } else if (coneLookupTagBasedType != null) {
                typeArguments = coneLookupTagBasedType.getTypeArguments();
            }
            ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl3 = lookupTag;
            if (typeArguments == null) {
                typeArguments = new ConeTypeProjection[0];
            }
            return TypeConstructionUtilsKt.constructClassType(coneClassLikeLookupTagImpl3, typeArguments, coneLookupTagBasedType != null, coneAttributes);
        }
        if (classifier instanceof JavaTypeParameter) {
            return new ConeTypeParameterTypeImpl(javaTypeParameterStack.get((JavaTypeParameter) classifier).getLookupTag(), coneLookupTagBasedType != null, coneAttributes);
        }
        if (classifier == null) {
            ClassId classId = ClassId.topLevel(new FqName(javaClassifierType.getClassifierQualifiedName()));
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            return TypeConstructionUtilsKt.constructClassLikeType(classId, new ConeTypeProjection[0], coneLookupTagBasedType != null, coneAttributes);
        }
        return new ConeErrorType(new ConeSimpleDiagnostic("Unexpected classifier: " + classifier, DiagnosticKind.Java), false, null, null, 14, null);
    }

    private static final boolean argumentsMakeSenseOnlyForMutableContainer(JavaClassifierType javaClassifierType, ClassId classId, FirSession firSession) {
        ClassId onlyToMutable;
        FirRegularClassSymbol firRegularClassSymbol;
        List<FirTypeParameterSymbol> typeParameterSymbols;
        FirTypeParameterSymbol firTypeParameterSymbol;
        Variance variance;
        return (!JavaToKotlinClassMap.INSTANCE.isReadOnly(classId.asSingleFqName().toUnsafe()) || (onlyToMutable = JavaTypeUtilsKt.readOnlyToMutable(classId)) == null || !JavaTypesKt.isSuperWildcard((JavaType) CollectionsKt.lastOrNull((List) javaClassifierType.mo7270getTypeArguments())) || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(TypeConstructionUtilsKt.toLookupTag(onlyToMutable), firSession)) == null || (typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols()) == null || (firTypeParameterSymbol = (FirTypeParameterSymbol) CollectionsKt.lastOrNull((List) typeParameterSymbols)) == null || (variance = firTypeParameterSymbol.getVariance()) == null || variance == Variance.OUT_VARIANCE) ? false : true;
    }

    public static final FirJavaTypeRef toFirJavaTypeRef(final JavaType javaType, final FirSession session) {
        Intrinsics.checkNotNullParameter(javaType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirJavaTypeRefBuilder firJavaTypeRefBuilder = new FirJavaTypeRefBuilder();
        firJavaTypeRefBuilder.setAnnotationBuilder(new Function0<List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.java.JavaTypeConversionKt$toFirJavaTypeRef$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends FirAnnotation> invoke() {
                return JavaAnnotationsMappingKt.convertAnnotationsToFir(javaType, session);
            }
        });
        firJavaTypeRefBuilder.setType(javaType);
        return firJavaTypeRefBuilder.build();
    }

    public static final FirResolvedTypeRef toFirResolvedTypeRef(JavaType javaType, FirSession session, JavaTypeParameterStack javaTypeParameterStack, FirJavaTypeConversionMode mode) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        Intrinsics.checkNotNullParameter(mode, "mode");
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        ConeSimpleKotlinType coneKotlinType$default = toConeKotlinType$default(javaType, session, javaTypeParameterStack, mode, null, 8, null);
        if (mode == FirJavaTypeConversionMode.SUPERTYPE) {
            coneKotlinType$default = ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType$default);
        }
        firResolvedTypeRefBuilder.setType(coneKotlinType$default);
        CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), CustomAnnotationTypeAttributeKt.getCustomAnnotations(firResolvedTypeRefBuilder.getType().getAttributes()));
        return firResolvedTypeRefBuilder.mo6809build();
    }
}
