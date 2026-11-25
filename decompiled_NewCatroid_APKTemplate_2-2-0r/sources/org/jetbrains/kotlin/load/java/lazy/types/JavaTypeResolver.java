package org.jetbrains.kotlin.load.java.lazy.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.CompositeAnnotations;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.UtilsKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaAnnotations;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.TypeParameterResolver;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaTypesKt;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.LazyWrappedType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeParameterUpperBoundEraser;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JavaTypeResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J&\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J4\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u0002J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\"\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J \u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010$\u001a\u00020%J\u0018\u0010&\u001a\u00020!2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0018\u0010'\u001a\u00020!2\b\u0010\u000e\u001a\u0004\u0018\u00010(2\u0006\u0010\u0010\u001a\u00020\u0011J\"\u0010)\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010(2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010*\u001a\u00020\u0016H\u0002J\u0014\u0010+\u001a\u00020%*\u00020\u000f2\u0006\u0010,\u001a\u00020\u001dH\u0002J\u0014\u0010-\u001a\u00020%*\u00020.2\u0006\u0010*\u001a\u00020\u0016H\u0002J\f\u0010/\u001a\u00020%*\u00020\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeResolver;", "", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "typeParameterResolver", "Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/lazy/TypeParameterResolver;)V", "projectionComputer", "Lorg/jetbrains/kotlin/load/java/lazy/types/RawProjectionComputer;", "typeParameterUpperBoundEraser", "Lorg/jetbrains/kotlin/types/TypeParameterUpperBoundEraser;", "computeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "javaType", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", ASTExpr.DEFAULT_ATTRIBUTE_NAME_DEPRECATED, "Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeAttributes;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "computeRawTypeArguments", "typeParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "computeSimpleJavaClassifierType", "Lorg/jetbrains/kotlin/types/SimpleType;", "lowerResult", "computeTypeConstructor", "createNotFoundClass", "mapKotlinClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "transformArrayType", "Lorg/jetbrains/kotlin/types/KotlinType;", "arrayType", "Lorg/jetbrains/kotlin/load/java/structure/JavaArrayType;", "isVararg", "", "transformJavaClassifierType", "transformJavaType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "transformToTypeProjection", "typeParameter", "argumentsMakeSenseOnlyForMutableContainer", "readOnlyContainer", "isConflictingArgumentFor", "Lorg/jetbrains/kotlin/types/Variance;", "isNullable", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeResolver {
    private final LazyJavaResolverContext c;
    private final RawProjectionComputer projectionComputer;
    private final TypeParameterResolver typeParameterResolver;
    private final TypeParameterUpperBoundEraser typeParameterUpperBoundEraser;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaTypeResolver(LazyJavaResolverContext c, TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        this.c = c;
        this.typeParameterResolver = typeParameterResolver;
        RawProjectionComputer rawProjectionComputer = new RawProjectionComputer();
        this.projectionComputer = rawProjectionComputer;
        this.typeParameterUpperBoundEraser = new TypeParameterUpperBoundEraser(rawProjectionComputer, null, 2, 0 == true ? 1 : 0);
    }

    public final KotlinType transformJavaType(JavaType javaType, JavaTypeAttributes attr) {
        KotlinType kotlinTypeTransformJavaType;
        SimpleType unitType;
        Intrinsics.checkNotNullParameter(attr, "attr");
        if (javaType instanceof JavaPrimitiveType) {
            PrimitiveType type = ((JavaPrimitiveType) javaType).getType();
            if (type != null) {
                unitType = this.c.getModule().getBuiltIns().getPrimitiveKotlinType(type);
            } else {
                unitType = this.c.getModule().getBuiltIns().getUnitType();
            }
            Intrinsics.checkNotNull(unitType);
            return unitType;
        }
        if (javaType instanceof JavaClassifierType) {
            return transformJavaClassifierType((JavaClassifierType) javaType, attr);
        }
        if (javaType instanceof JavaArrayType) {
            return transformArrayType$default(this, (JavaArrayType) javaType, attr, false, 4, null);
        }
        if (!(javaType instanceof JavaWildcardType)) {
            if (javaType == null) {
                SimpleType defaultBound = this.c.getModule().getBuiltIns().getDefaultBound();
                Intrinsics.checkNotNullExpressionValue(defaultBound, "getDefaultBound(...)");
                return defaultBound;
            }
            throw new UnsupportedOperationException("Unsupported type: " + javaType);
        }
        JavaType bound = ((JavaWildcardType) javaType).getBound();
        if (bound != null && (kotlinTypeTransformJavaType = transformJavaType(bound, attr)) != null) {
            return kotlinTypeTransformJavaType;
        }
        SimpleType defaultBound2 = this.c.getModule().getBuiltIns().getDefaultBound();
        Intrinsics.checkNotNullExpressionValue(defaultBound2, "getDefaultBound(...)");
        return defaultBound2;
    }

    public static /* synthetic */ KotlinType transformArrayType$default(JavaTypeResolver javaTypeResolver, JavaArrayType javaArrayType, JavaTypeAttributes javaTypeAttributes, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return javaTypeResolver.transformArrayType(javaArrayType, javaTypeAttributes, z);
    }

    public final KotlinType transformArrayType(JavaArrayType arrayType, JavaTypeAttributes attr, boolean isVararg) {
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        Intrinsics.checkNotNullParameter(attr, "attr");
        JavaType componentType = arrayType.getComponentType();
        JavaPrimitiveType javaPrimitiveType = componentType instanceof JavaPrimitiveType ? (JavaPrimitiveType) componentType : null;
        PrimitiveType type = javaPrimitiveType != null ? javaPrimitiveType.getType() : null;
        LazyJavaAnnotations lazyJavaAnnotations = new LazyJavaAnnotations(this.c, arrayType, true);
        if (type != null) {
            SimpleType primitiveArrayKotlinType = this.c.getModule().getBuiltIns().getPrimitiveArrayKotlinType(type);
            Intrinsics.checkNotNull(primitiveArrayKotlinType);
            KotlinType kotlinTypeReplaceAnnotations = TypeUtilsKt.replaceAnnotations(primitiveArrayKotlinType, new CompositeAnnotations(primitiveArrayKotlinType.getAnnotations(), lazyJavaAnnotations));
            Intrinsics.checkNotNull(kotlinTypeReplaceAnnotations, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
            SimpleType simpleType = (SimpleType) kotlinTypeReplaceAnnotations;
            if (attr.isForAnnotationParameter()) {
                return simpleType;
            }
            return KotlinTypeFactory.flexibleType(simpleType, simpleType.makeNullableAsSpecified(true));
        }
        KotlinType kotlinTypeTransformJavaType = transformJavaType(componentType, JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, attr.isForAnnotationParameter(), false, null, 6, null));
        if (attr.isForAnnotationParameter()) {
            SimpleType arrayType2 = this.c.getModule().getBuiltIns().getArrayType(isVararg ? Variance.OUT_VARIANCE : Variance.INVARIANT, kotlinTypeTransformJavaType, lazyJavaAnnotations);
            Intrinsics.checkNotNullExpressionValue(arrayType2, "getArrayType(...)");
            return arrayType2;
        }
        LazyJavaAnnotations lazyJavaAnnotations2 = lazyJavaAnnotations;
        SimpleType arrayType3 = this.c.getModule().getBuiltIns().getArrayType(Variance.INVARIANT, kotlinTypeTransformJavaType, lazyJavaAnnotations2);
        Intrinsics.checkNotNullExpressionValue(arrayType3, "getArrayType(...)");
        return KotlinTypeFactory.flexibleType(arrayType3, this.c.getModule().getBuiltIns().getArrayType(Variance.OUT_VARIANCE, kotlinTypeTransformJavaType, lazyJavaAnnotations2).makeNullableAsSpecified(true));
    }

    private static final ErrorType transformJavaClassifierType$errorType(JavaClassifierType javaClassifierType) {
        return ErrorUtils.createErrorType(ErrorTypeKind.UNRESOLVED_JAVA_CLASS, javaClassifierType.getPresentableText());
    }

    private final KotlinType transformJavaClassifierType(JavaClassifierType javaType, JavaTypeAttributes attr) {
        boolean z = (attr.isForAnnotationParameter() || attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) ? false : true;
        boolean zMo7271isRaw = javaType.mo7271isRaw();
        if (!zMo7271isRaw && !z) {
            SimpleType simpleTypeComputeSimpleJavaClassifierType = computeSimpleJavaClassifierType(javaType, attr, null);
            return simpleTypeComputeSimpleJavaClassifierType != null ? simpleTypeComputeSimpleJavaClassifierType : transformJavaClassifierType$errorType(javaType);
        }
        SimpleType simpleTypeComputeSimpleJavaClassifierType2 = computeSimpleJavaClassifierType(javaType, attr.withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND), null);
        if (simpleTypeComputeSimpleJavaClassifierType2 == null) {
            return transformJavaClassifierType$errorType(javaType);
        }
        SimpleType simpleTypeComputeSimpleJavaClassifierType3 = computeSimpleJavaClassifierType(javaType, attr.withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND), simpleTypeComputeSimpleJavaClassifierType2);
        if (simpleTypeComputeSimpleJavaClassifierType3 == null) {
            return transformJavaClassifierType$errorType(javaType);
        }
        if (zMo7271isRaw) {
            return new RawTypeImpl(simpleTypeComputeSimpleJavaClassifierType2, simpleTypeComputeSimpleJavaClassifierType3);
        }
        return KotlinTypeFactory.flexibleType(simpleTypeComputeSimpleJavaClassifierType2, simpleTypeComputeSimpleJavaClassifierType3);
    }

    private final SimpleType computeSimpleJavaClassifierType(JavaClassifierType javaType, JavaTypeAttributes attr, SimpleType lowerResult) {
        TypeAttributes defaultAttributes;
        if (lowerResult == null || (defaultAttributes = lowerResult.getAttributes()) == null) {
            defaultAttributes = TypeAttributesKt.toDefaultAttributes(new LazyJavaAnnotations(this.c, javaType, false, 4, null));
        }
        TypeAttributes typeAttributes = defaultAttributes;
        TypeConstructor typeConstructorComputeTypeConstructor = computeTypeConstructor(javaType, attr);
        if (typeConstructorComputeTypeConstructor == null) {
            return null;
        }
        boolean zIsNullable = isNullable(attr);
        if (Intrinsics.areEqual(lowerResult != null ? lowerResult.getConstructor() : null, typeConstructorComputeTypeConstructor) && !javaType.mo7271isRaw() && zIsNullable) {
            return lowerResult.makeNullableAsSpecified(true);
        }
        return KotlinTypeFactory.simpleType$default(typeAttributes, typeConstructorComputeTypeConstructor, computeArguments(javaType, attr, typeConstructorComputeTypeConstructor), zIsNullable, (KotlinTypeRefiner) null, 16, (Object) null);
    }

    private final TypeConstructor computeTypeConstructor(JavaClassifierType javaType, JavaTypeAttributes attr) {
        TypeConstructor typeConstructor;
        JavaClassifier classifier = javaType.getClassifier();
        if (classifier == null) {
            return createNotFoundClass(javaType);
        }
        if (classifier instanceof JavaClass) {
            JavaClass javaClass = (JavaClass) classifier;
            FqName fqName = javaClass.getFqName();
            if (fqName != null) {
                ClassDescriptor classDescriptorMapKotlinClass = mapKotlinClass(javaType, attr, fqName);
                if (classDescriptorMapKotlinClass == null) {
                    classDescriptorMapKotlinClass = this.c.getComponents().getModuleClassResolver().resolveClass(javaClass);
                }
                return (classDescriptorMapKotlinClass == null || (typeConstructor = classDescriptorMapKotlinClass.getTypeConstructor()) == null) ? createNotFoundClass(javaType) : typeConstructor;
            }
            throw new AssertionError("Class type should have a FQ name: " + classifier);
        }
        if (classifier instanceof JavaTypeParameter) {
            TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = this.typeParameterResolver.resolveTypeParameter((JavaTypeParameter) classifier);
            if (typeParameterDescriptorResolveTypeParameter != null) {
                return typeParameterDescriptorResolveTypeParameter.getTypeConstructor();
            }
            return null;
        }
        throw new IllegalStateException("Unknown classifier kind: " + classifier);
    }

    private final TypeConstructor createNotFoundClass(JavaClassifierType javaType) {
        ClassId classId = ClassId.topLevel(new FqName(javaType.getClassifierQualifiedName()));
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        TypeConstructor typeConstructor = this.c.getComponents().getDeserializedDescriptorResolver().getComponents().getNotFoundClasses().getClass(classId, CollectionsKt.listOf(0)).getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        return typeConstructor;
    }

    private final ClassDescriptor mapKotlinClass(JavaClassifierType javaType, JavaTypeAttributes attr, FqName fqName) {
        if (attr.isForAnnotationParameter() && Intrinsics.areEqual(fqName, JavaTypeResolverKt.JAVA_LANG_CLASS_FQ_NAME)) {
            return this.c.getComponents().getReflectionTypes().getKClass();
        }
        JavaToKotlinClassMapper javaToKotlinClassMapper = JavaToKotlinClassMapper.INSTANCE;
        ClassDescriptor classDescriptorMapJavaToKotlin$default = JavaToKotlinClassMapper.mapJavaToKotlin$default(javaToKotlinClassMapper, fqName, this.c.getModule().getBuiltIns(), null, 4, null);
        if (classDescriptorMapJavaToKotlin$default == null) {
            return null;
        }
        return (javaToKotlinClassMapper.isReadOnly(classDescriptorMapJavaToKotlin$default) && (attr.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE || argumentsMakeSenseOnlyForMutableContainer(javaType, classDescriptorMapJavaToKotlin$default))) ? javaToKotlinClassMapper.convertReadOnlyToMutable(classDescriptorMapJavaToKotlin$default) : classDescriptorMapJavaToKotlin$default;
    }

    private final boolean argumentsMakeSenseOnlyForMutableContainer(JavaClassifierType javaClassifierType, ClassDescriptor classDescriptor) {
        Variance variance;
        if (!JavaTypesKt.isSuperWildcard((JavaType) CollectionsKt.lastOrNull((List) javaClassifierType.mo7270getTypeArguments()))) {
            return false;
        }
        List<TypeParameterDescriptor> parameters = JavaToKotlinClassMapper.INSTANCE.convertReadOnlyToMutable(classDescriptor).getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.lastOrNull((List) parameters);
        return (typeParameterDescriptor == null || (variance = typeParameterDescriptor.getVariance()) == null || variance == Variance.OUT_VARIANCE) ? false : true;
    }

    private final List<TypeProjection> computeRawTypeArguments(final JavaClassifierType javaType, List<? extends TypeParameterDescriptor> typeParameters, final TypeConstructor constructor, final JavaTypeAttributes attr) {
        TypeProjection typeProjectionComputeProjection;
        List<? extends TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (final TypeParameterDescriptor typeParameterDescriptor : list) {
            if (TypeUtilsKt.hasTypeParameterRecursiveBounds(typeParameterDescriptor, null, attr.getVisitedTypeParameters())) {
                typeProjectionComputeProjection = TypeUtils.makeStarProjection(typeParameterDescriptor, attr);
            } else {
                typeProjectionComputeProjection = this.projectionComputer.computeProjection(typeParameterDescriptor, attr.markIsRaw(javaType.mo7271isRaw()), this.typeParameterUpperBoundEraser, new LazyWrappedType(this.c.getStorageManager(), new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.load.java.lazy.types.JavaTypeResolver$computeRawTypeArguments$1$erasedUpperBound$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final KotlinType invoke() {
                        TypeParameterUpperBoundEraser typeParameterUpperBoundEraser = this.this$0.typeParameterUpperBoundEraser;
                        TypeParameterDescriptor typeParameterDescriptor2 = typeParameterDescriptor;
                        JavaTypeAttributes javaTypeAttributes = attr;
                        ClassifierDescriptor this$0 = constructor.getThis$0();
                        return typeParameterUpperBoundEraser.getErasedUpperBound(typeParameterDescriptor2, javaTypeAttributes.withDefaultType(this$0 != null ? this$0.getDefaultType() : null).markIsRaw(javaType.mo7271isRaw()));
                    }
                }));
            }
            arrayList.add(typeProjectionComputeProjection);
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0026  */
    private final List<TypeProjection> computeArguments(JavaClassifierType javaType, JavaTypeAttributes attr, TypeConstructor constructor) {
        boolean z;
        if (!javaType.mo7271isRaw()) {
            if (javaType.mo7270getTypeArguments().isEmpty()) {
                List<TypeParameterDescriptor> parameters = constructor.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                z = parameters.isEmpty() ? false : true;
            }
        }
        List<TypeParameterDescriptor> parameters2 = constructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
        if (z) {
            return computeRawTypeArguments(javaType, parameters2, constructor, attr);
        }
        if (parameters2.size() != javaType.mo7270getTypeArguments().size()) {
            List<TypeParameterDescriptor> list = parameters2;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (TypeParameterDescriptor typeParameterDescriptor : list) {
                ErrorTypeKind errorTypeKind = ErrorTypeKind.MISSED_TYPE_ARGUMENT_FOR_TYPE_PARAMETER;
                String strAsString = typeParameterDescriptor.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                arrayList.add(new TypeProjectionImpl(ErrorUtils.createErrorType(errorTypeKind, strAsString)));
            }
            return CollectionsKt.toList(arrayList);
        }
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(javaType.mo7270getTypeArguments());
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
        for (IndexedValue indexedValue : iterableWithIndex) {
            int index = indexedValue.getIndex();
            JavaType javaType2 = (JavaType) indexedValue.component2();
            parameters2.size();
            TypeParameterDescriptor typeParameterDescriptor2 = parameters2.get(index);
            JavaTypeAttributes attributes$default = JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, null, 7, null);
            Intrinsics.checkNotNull(typeParameterDescriptor2);
            arrayList2.add(transformToTypeProjection(javaType2, attributes$default, typeParameterDescriptor2));
        }
        return CollectionsKt.toList(arrayList2);
    }

    private final TypeProjection transformToTypeProjection(JavaType javaType, JavaTypeAttributes attr, TypeParameterDescriptor typeParameter) {
        TypeProjection typeProjectionMakeStarProjection;
        if (javaType instanceof JavaWildcardType) {
            JavaWildcardType javaWildcardType = (JavaWildcardType) javaType;
            JavaType bound = javaWildcardType.getBound();
            Variance variance = javaWildcardType.getIsExtends() ? Variance.OUT_VARIANCE : Variance.IN_VARIANCE;
            if (bound == null || isConflictingArgumentFor(variance, typeParameter)) {
                typeProjectionMakeStarProjection = TypeUtils.makeStarProjection(typeParameter, attr);
            } else {
                AnnotationDescriptor annotationDescriptorExtractNullabilityAnnotationOnBoundedWildcard = UtilsKt.extractNullabilityAnnotationOnBoundedWildcard(this.c, javaWildcardType);
                KotlinType kotlinTypeTransformJavaType = transformJavaType(bound, JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, null, 7, null));
                if (annotationDescriptorExtractNullabilityAnnotationOnBoundedWildcard != null) {
                    kotlinTypeTransformJavaType = TypeUtilsKt.replaceAnnotations(kotlinTypeTransformJavaType, Annotations.INSTANCE.create(CollectionsKt.plus(kotlinTypeTransformJavaType.getAnnotations(), annotationDescriptorExtractNullabilityAnnotationOnBoundedWildcard)));
                }
                typeProjectionMakeStarProjection = TypeUtilsKt.createProjection(kotlinTypeTransformJavaType, variance, typeParameter);
            }
            Intrinsics.checkNotNull(typeProjectionMakeStarProjection);
            return typeProjectionMakeStarProjection;
        }
        return new TypeProjectionImpl(Variance.INVARIANT, transformJavaType(javaType, attr));
    }

    private final boolean isConflictingArgumentFor(Variance variance, TypeParameterDescriptor typeParameterDescriptor) {
        return (typeParameterDescriptor.getVariance() == Variance.INVARIANT || variance == typeParameterDescriptor.getVariance()) ? false : true;
    }

    private final boolean isNullable(JavaTypeAttributes javaTypeAttributes) {
        return (javaTypeAttributes.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || javaTypeAttributes.isForAnnotationParameter() || javaTypeAttributes.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) ? false : true;
    }
}
