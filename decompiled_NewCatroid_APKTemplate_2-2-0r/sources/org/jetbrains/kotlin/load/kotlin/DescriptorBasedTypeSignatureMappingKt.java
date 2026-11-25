package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.SuspendFunctionTypesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.types.ExpandedTypeUtilsKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.FunctionsKt;

/* JADX INFO: compiled from: descriptorBasedTypeSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\b\u0002\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u001a\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001as\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u00052\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u000b\u0018\u00010\u00142 \b\u0002\u0010\u0015\u001a\u001a\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00170\u0016¢\u0006\u0002\u0010\u0018¨\u0006\u0019"}, d2 = {"computeInternalName", "", "klass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "typeMappingConfiguration", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingConfiguration;", "hasVoidReturnType", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "mapType", ExifInterface.GPS_DIRECTION_TRUE, "", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "factory", "Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "descriptorTypeWriter", "Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;", "writeGenericType", "Lkotlin/Function3;", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;Lorg/jetbrains/kotlin/load/kotlin/TypeMappingConfiguration;Lorg/jetbrains/kotlin/load/kotlin/JvmDescriptorTypeWriter;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorBasedTypeSignatureMappingKt {
    public static /* synthetic */ Object mapType$default(KotlinType kotlinType, JvmTypeFactory jvmTypeFactory, TypeMappingMode typeMappingMode, TypeMappingConfiguration typeMappingConfiguration, JvmDescriptorTypeWriter jvmDescriptorTypeWriter, Function3 function3, int i, Object obj) {
        if ((i & 32) != 0) {
            function3 = FunctionsKt.getDO_NOTHING_3();
        }
        return mapType(kotlinType, jvmTypeFactory, typeMappingMode, typeMappingConfiguration, jvmDescriptorTypeWriter, function3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v10, types: [T, java.lang.Object] */
    public static final <T> T mapType(KotlinType kotlinType, JvmTypeFactory<T> factory, TypeMappingMode mode, TypeMappingConfiguration<? extends T> typeMappingConfiguration, JvmDescriptorTypeWriter<T> jvmDescriptorTypeWriter, Function3<? super KotlinType, ? super T, ? super TypeMappingMode, Unit> writeGenericType) {
        T t;
        KotlinType kotlinType2;
        Object objMapType;
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        Intrinsics.checkNotNullParameter(writeGenericType, "writeGenericType");
        KotlinType kotlinTypePreprocessType = typeMappingConfiguration.preprocessType(kotlinType);
        if (kotlinTypePreprocessType != null) {
            return (T) mapType(kotlinTypePreprocessType, factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return (T) mapType(SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(kotlinType), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        KotlinType kotlinType3 = kotlinType;
        Object objMapBuiltInType = TypeSignatureMappingKt.mapBuiltInType(SimpleClassicTypeSystemContext.INSTANCE, kotlinType3, factory, mode);
        if (objMapBuiltInType != null) {
            ?? r9 = (Object) TypeSignatureMappingKt.boxTypeIfNeeded(factory, objMapBuiltInType, mode.getNeedPrimitiveBoxing());
            writeGenericType.invoke(kotlinType, r9, mode);
            return r9;
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor) constructor;
            KotlinType alternative = intersectionTypeConstructor.getAlternative();
            if (alternative == null) {
                alternative = typeMappingConfiguration.commonSupertype(intersectionTypeConstructor.mo7438getSupertypes());
            }
            return (T) mapType(TypeUtilsKt.replaceArgumentsWithStarProjections(alternative), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = constructor.getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null) {
            throw new UnsupportedOperationException("no descriptor for type constructor of " + kotlinType);
        }
        ClassifierDescriptor classifierDescriptor = classifierDescriptorMo7434getDeclarationDescriptor;
        if (ErrorUtils.isError(classifierDescriptor)) {
            T t2 = (T) factory.createObjectType(TypeSignatureMappingKt.NON_EXISTENT_CLASS_NAME);
            typeMappingConfiguration.processErrorType(kotlinType, (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor);
            if (jvmDescriptorTypeWriter != 0) {
                jvmDescriptorTypeWriter.writeClass(t2);
            }
            return t2;
        }
        boolean z = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor;
        if (z && KotlinBuiltIns.isArray(kotlinType)) {
            if (kotlinType.getArguments().size() != 1) {
                throw new UnsupportedOperationException("arrays must have one type argument");
            }
            TypeProjection typeProjection = kotlinType.getArguments().get(0);
            KotlinType type = typeProjection.getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (typeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                objMapType = factory.createObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayType();
                    jvmDescriptorTypeWriter.writeClass(objMapType);
                    jvmDescriptorTypeWriter.writeArrayEnd();
                }
            } else {
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayType();
                }
                Variance projectionKind = typeProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
                objMapType = mapType(type, factory, mode.toGenericArgumentMode(projectionKind, true), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayEnd();
                }
            }
            return (T) factory.createFromString("[" + factory.toString(objMapType));
        }
        if (z) {
            if (InlineClassesUtilsKt.isInlineClass(classifierDescriptor) && !mode.getNeedInlineClassWrapping() && (kotlinType2 = (KotlinType) ExpandedTypeUtilsKt.computeExpandedTypeForInlineClass(SimpleClassicTypeSystemContext.INSTANCE, kotlinType3)) != null) {
                return (T) mapType(kotlinType2, factory, mode.wrapInlineClassesMode(), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
            }
            if (mode.getIsForAnnotationParameter() && KotlinBuiltIns.isKClass((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor)) {
                t = (Object) factory.getJavaLangClassType();
            } else {
                ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
                ClassDescriptor original = classDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                T predefinedTypeForClass = typeMappingConfiguration.getPredefinedTypeForClass(original);
                if (predefinedTypeForClass == null) {
                    if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY) {
                        DeclarationDescriptor moduleDescriptor = classDescriptor.getContainingDeclaration();
                        Intrinsics.checkNotNull(moduleDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        classDescriptor = (ClassDescriptor) moduleDescriptor;
                    }
                    ClassDescriptor original2 = classDescriptor.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original2, "getOriginal(...)");
                    t = (Object) factory.createObjectType(computeInternalName(original2, typeMappingConfiguration));
                } else {
                    t = (Object) predefinedTypeForClass;
                }
            }
            writeGenericType.invoke(kotlinType, t, mode);
            return t;
        }
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            KotlinType representativeUpperBound = TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor);
            if (kotlinType.getIsMarkedNullable()) {
                representativeUpperBound = TypeUtilsKt.makeNullable(representativeUpperBound);
            }
            T t3 = (T) mapType(representativeUpperBound, factory, mode, typeMappingConfiguration, null, FunctionsKt.getDO_NOTHING_3());
            if (jvmDescriptorTypeWriter != 0) {
                Name name2 = classifierDescriptorMo7434getDeclarationDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                jvmDescriptorTypeWriter.writeTypeVariable(name2, t3);
            }
            return t3;
        }
        if ((classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeAliasDescriptor) && mode.getMapTypeAliases()) {
            return (T) mapType(((TypeAliasDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).getExpandedType(), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType);
        }
        throw new UnsupportedOperationException("Unknown type " + kotlinType);
    }

    public static final boolean hasVoidReturnType(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof ConstructorDescriptor) {
            return true;
        }
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        if (KotlinBuiltIns.isUnit(returnType)) {
            KotlinType returnType2 = descriptor.getReturnType();
            Intrinsics.checkNotNull(returnType2);
            if (!TypeUtils.isNullableType(returnType2) && !(descriptor instanceof PropertyGetterDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ String computeInternalName$default(ClassDescriptor classDescriptor, TypeMappingConfiguration typeMappingConfiguration, int i, Object obj) {
        if ((i & 2) != 0) {
            typeMappingConfiguration = TypeMappingConfigurationImpl.INSTANCE;
        }
        return computeInternalName(classDescriptor, typeMappingConfiguration);
    }

    public static final String computeInternalName(ClassDescriptor klass, TypeMappingConfiguration<?> typeMappingConfiguration) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        String predefinedFullInternalNameForClass = typeMappingConfiguration.getPredefinedFullInternalNameForClass(klass);
        if (predefinedFullInternalNameForClass != null) {
            return predefinedFullInternalNameForClass;
        }
        DeclarationDescriptor moduleDescriptor = klass.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "getContainingDeclaration(...)");
        String identifier = SpecialNames.safeIdentifier(klass.getShortName()).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (moduleDescriptor instanceof PackageFragmentDescriptor) {
            FqName fqName = ((PackageFragmentDescriptor) moduleDescriptor).getFqName();
            if (fqName.isRoot()) {
                return identifier;
            }
            StringBuilder sb = new StringBuilder();
            String strAsString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null)).append('/').append(identifier).toString();
        }
        ClassDescriptor classDescriptor = moduleDescriptor instanceof ClassDescriptor ? (ClassDescriptor) moduleDescriptor : null;
        if (classDescriptor == null) {
            throw new IllegalArgumentException("Unexpected container: " + moduleDescriptor + " for " + klass);
        }
        String predefinedInternalNameForClass = typeMappingConfiguration.getPredefinedInternalNameForClass(classDescriptor);
        if (predefinedInternalNameForClass == null) {
            predefinedInternalNameForClass = computeInternalName(classDescriptor, typeMappingConfiguration);
        }
        return predefinedInternalNameForClass + '$' + identifier;
    }
}
