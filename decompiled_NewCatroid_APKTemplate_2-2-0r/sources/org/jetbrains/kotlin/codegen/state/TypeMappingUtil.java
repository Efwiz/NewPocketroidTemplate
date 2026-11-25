package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.UtilsKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.ClassicTypeSystemContextKt;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: typeMappingUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a,\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\tH\u0000\u001a\u0014\u0010\u0015\u001a\u00020\t*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u001a\u0014\u0010\u0019\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001bH\u0002\u001a3\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t¢\u0006\u0002\u0010\u001d\u001a\u0012\u0010\u001e\u001a\u00020\t*\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0018\u001a\u001c\u0010\u001e\u001a\u00020\t*\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0007\u001a\u0012\u0010\"\u001a\u00020\t*\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0018\u001a\u0013\u0010#\u001a\u0004\u0018\u00010\t*\u00020$H\u0002¢\u0006\u0002\u0010%\u001a\u0013\u0010#\u001a\u0004\u0018\u00010\t*\u00020&H\u0002¢\u0006\u0002\u0010'\u001a\u001b\u0010#\u001a\u0004\u0018\u00010\t*\u00020\u00182\u0006\u0010(\u001a\u00020\u0016H\u0002¢\u0006\u0002\u0010)\u001a\u001a\u0010*\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010(\u001a\u00020\u0016\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0017\u0010\b\u001a\u00020\t*\u0004\u0018\u00010\n8F¢\u0006\u0006\u001a\u0004\b\b\u0010\u000b\"\u0017\u0010\f\u001a\u00020\t*\u0004\u0018\u00010\u00018F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006+"}, d2 = {"JVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getJVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "JVM_WILDCARD_ANNOTATION_FQ_NAME", "getJVM_WILDCARD_ANNOTATION_FQ_NAME", "METHODS_WITH_DECLARATION_SITE_WILDCARDS", "", "isMethodWithDeclarationSiteWildcards", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Z", "isMethodWithDeclarationSiteWildcardsFqName", "(Lorg/jetbrains/kotlin/name/FqName;)Z", "extractTypeMappingModeFromAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "callableDescriptor", "outerType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isForAnnotationParameter", "mapTypeAliases", "canHaveSubtypesIgnoringNullability", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "kotlinType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "child", "name", "", "callableSuppressWildcardsMode", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;Ljava/lang/Boolean;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;ZZ)Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "isMostPreciseContravariantArgument", ModuleXmlParser.TYPE, "parameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "isMostPreciseCovariantArgument", "suppressWildcardsMode", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Ljava/lang/Boolean;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Ljava/lang/Boolean;", "typeSystem", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;)Ljava/lang/Boolean;", "updateArgumentModeFromAnnotations", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeMappingUtil {
    private static final Set<FqName> METHODS_WITH_DECLARATION_SITE_WILDCARDS = SetsKt.setOf((Object[]) new FqName[]{child(StandardNames.FqNames.mutableCollection, "addAll"), child(StandardNames.FqNames.mutableList, "addAll"), child(StandardNames.FqNames.mutableMap, "putAll")});
    private static final FqName JVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmSuppressWildcards");
    private static final FqName JVM_WILDCARD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmWildcard");

    public static final boolean isMostPreciseContravariantArgument(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return typeSystemCommonBackendContext.isAnyConstructor(typeSystemCommonBackendContext.typeConstructor(type));
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This method is needed for binary compatibility. See KT-56033")
    public static final /* synthetic */ boolean isMostPreciseContravariantArgument(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type, TypeParameterMarker parameter) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return isMostPreciseCovariantArgument(typeSystemCommonBackendContext, type);
    }

    public static final boolean isMostPreciseCovariantArgument(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return !canHaveSubtypesIgnoringNullability(typeSystemCommonBackendContext, type);
    }

    private static final boolean canHaveSubtypesIgnoringNullability(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker kotlinTypeMarker) {
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker);
        if (!typeSystemCommonBackendContext.isClassTypeConstructor(typeConstructorMarkerTypeConstructor) || !typeSystemCommonBackendContext.isFinalClassOrEnumEntryOrAnnotationClassConstructor(typeConstructorMarkerTypeConstructor)) {
            return true;
        }
        int iParametersCount = typeSystemCommonBackendContext.parametersCount(typeConstructorMarkerTypeConstructor);
        for (int i = 0; i < iParametersCount; i++) {
            TypeParameterMarker parameter = typeSystemCommonBackendContext.getParameter(typeConstructorMarkerTypeConstructor, i);
            TypeArgumentMarker argument = typeSystemCommonBackendContext.getArgument(kotlinTypeMarker, i);
            if (typeSystemCommonBackendContext.isStarProjection(argument)) {
                return true;
            }
            Variance varianceConvertVariance = ClassicTypeSystemContextKt.convertVariance(typeSystemCommonBackendContext.getVariance(argument));
            KotlinTypeMarker type = typeSystemCommonBackendContext.getType(argument);
            Variance effectiveVariance = UtilsKt.getEffectiveVariance(ClassicTypeSystemContextKt.convertVariance(typeSystemCommonBackendContext.getVariance(parameter)), varianceConvertVariance);
            if (effectiveVariance == Variance.OUT_VARIANCE && !isMostPreciseCovariantArgument(typeSystemCommonBackendContext, type)) {
                return true;
            }
            if (effectiveVariance == Variance.IN_VARIANCE && !isMostPreciseContravariantArgument(typeSystemCommonBackendContext, type)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isMethodWithDeclarationSiteWildcards(CallableDescriptor callableDescriptor) {
        if (!(callableDescriptor instanceof CallableMemberDescriptor)) {
            return false;
        }
        CallableMemberDescriptor original = ((CallableMemberDescriptor) callableDescriptor).getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return DescriptorUtilsKt.firstOverridden(original, true, new Function1<CallableMemberDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.codegen.state.TypeMappingUtil.isMethodWithDeclarationSiteWildcards.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(TypeMappingUtil.isMethodWithDeclarationSiteWildcardsFqName(DescriptorUtilsKt.fqNameOrNull(DescriptorUtilsKt.getPropertyIfAccessor(it))));
            }
        }) != null;
    }

    public static final boolean isMethodWithDeclarationSiteWildcardsFqName(FqName fqName) {
        return CollectionsKt.contains(METHODS_WITH_DECLARATION_SITE_WILDCARDS, fqName);
    }

    private static final FqName child(FqName fqName, String str) {
        FqName fqNameChild = fqName.child(Name.identifier(str));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    public static final TypeMappingMode updateArgumentModeFromAnnotations(TypeMappingMode typeMappingMode, KotlinTypeMarker type, TypeSystemCommonBackendContext typeSystem) {
        Intrinsics.checkNotNullParameter(typeMappingMode, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Boolean boolSuppressWildcardsMode = suppressWildcardsMode(type, typeSystem);
        if (boolSuppressWildcardsMode != null) {
            return TypeMappingMode.Companion.createWithConstantDeclarationSiteWildcardsMode$default(TypeMappingMode.INSTANCE, boolSuppressWildcardsMode.booleanValue(), typeMappingMode.getIsForAnnotationParameter(), typeMappingMode.getNeedInlineClassWrapping(), typeMappingMode.getMapTypeAliases(), null, 16, null);
        }
        return typeSystem.hasAnnotation(type, JVM_WILDCARD_ANNOTATION_FQ_NAME) ? TypeMappingMode.INSTANCE.createWithConstantDeclarationSiteWildcardsMode(false, typeMappingMode.getIsForAnnotationParameter(), typeMappingMode.getNeedInlineClassWrapping(), typeMappingMode.getMapTypeAliases(), typeMappingMode) : typeMappingMode;
    }

    public static final TypeMappingMode extractTypeMappingModeFromAnnotation(CallableDescriptor callableDescriptor, KotlinType outerType, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(outerType, "outerType");
        return extractTypeMappingModeFromAnnotation(SimpleClassicTypeSystemContext.INSTANCE, callableDescriptor != null ? suppressWildcardsMode(callableDescriptor) : null, outerType, z, z2);
    }

    public static final TypeMappingMode extractTypeMappingModeFromAnnotation(TypeSystemCommonBackendContext typeSystemCommonBackendContext, Boolean bool, KotlinTypeMarker outerType, boolean z, boolean z2) {
        boolean zBooleanValue;
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(outerType, "outerType");
        Boolean boolSuppressWildcardsMode = suppressWildcardsMode(outerType, typeSystemCommonBackendContext);
        if (boolSuppressWildcardsMode != null) {
            zBooleanValue = boolSuppressWildcardsMode.booleanValue();
        } else {
            if (bool == null) {
                return null;
            }
            zBooleanValue = bool.booleanValue();
        }
        return typeSystemCommonBackendContext.argumentsCount(outerType) == 0 ? TypeMappingMode.DEFAULT : TypeMappingMode.Companion.createWithConstantDeclarationSiteWildcardsMode$default(TypeMappingMode.INSTANCE, zBooleanValue, z, !typeSystemCommonBackendContext.isInlineClass(typeSystemCommonBackendContext.typeConstructor(outerType)), z2, null, 16, null);
    }

    private static final Boolean suppressWildcardsMode(DeclarationDescriptor declarationDescriptor) {
        AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) SequencesKt.firstOrNull(SequencesKt.mapNotNull(DescriptorUtilsKt.getParentsWithSelf(declarationDescriptor), new Function1<DeclarationDescriptor, AnnotationDescriptor>() { // from class: org.jetbrains.kotlin.codegen.state.TypeMappingUtil.suppressWildcardsMode.1
            @Override // kotlin.jvm.functions.Function1
            public final AnnotationDescriptor invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getAnnotations().mo6752findAnnotation(TypeMappingUtil.getJVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME());
            }
        }));
        if (annotationDescriptor != null) {
            return suppressWildcardsMode(annotationDescriptor);
        }
        return null;
    }

    private static final Boolean suppressWildcardsMode(KotlinTypeMarker kotlinTypeMarker, TypeSystemCommonBackendContext typeSystemCommonBackendContext) {
        FqName fqName = JVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME;
        if (!typeSystemCommonBackendContext.hasAnnotation(kotlinTypeMarker, fqName)) {
            return null;
        }
        Object annotationFirstArgumentValue = typeSystemCommonBackendContext.getAnnotationFirstArgumentValue(kotlinTypeMarker, fqName);
        Boolean bool = annotationFirstArgumentValue instanceof Boolean ? (Boolean) annotationFirstArgumentValue : null;
        return Boolean.valueOf(bool != null ? bool.booleanValue() : true);
    }

    private static final Boolean suppressWildcardsMode(AnnotationDescriptor annotationDescriptor) {
        ConstantValue constantValue = (ConstantValue) CollectionsKt.firstOrNull(annotationDescriptor.getAllValueArguments().values());
        Object value = constantValue != null ? constantValue.getValue() : null;
        Boolean bool = value instanceof Boolean ? (Boolean) value : null;
        if (bool == null) {
            return true;
        }
        return bool;
    }

    public static final FqName getJVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME() {
        return JVM_SUPPRESS_WILDCARDS_ANNOTATION_FQ_NAME;
    }

    public static final FqName getJVM_WILDCARD_ANNOTATION_FQ_NAME() {
        return JVM_WILDCARD_ANNOTATION_FQ_NAME;
    }
}
