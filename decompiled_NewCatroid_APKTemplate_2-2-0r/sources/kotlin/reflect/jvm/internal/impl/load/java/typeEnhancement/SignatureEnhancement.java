package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMapper;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.DeprecationCausedByFunctionN;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeEnhancementState;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.ReportLevel;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureBuildingUtilsKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.deprecation.DeprecationKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypeWithEnhancement;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class SignatureEnhancement {
    private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final JavaTypeEnhancementState javaTypeEnhancementState;
    private final JavaTypeEnhancement typeEnhancement;

    public SignatureEnhancement(AnnotationTypeQualifierResolver annotationTypeQualifierResolver, JavaTypeEnhancementState javaTypeEnhancementState, JavaTypeEnhancement typeEnhancement) {
        Intrinsics.checkNotNullParameter(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        Intrinsics.checkNotNullParameter(typeEnhancement, "typeEnhancement");
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.javaTypeEnhancementState = javaTypeEnhancementState;
        this.typeEnhancement = typeEnhancement;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(AnnotationDescriptor annotationDescriptor, boolean z) {
        ConstantValue<?> constantValueFirstArgument = DescriptorUtilsKt.firstArgument(annotationDescriptor);
        EnumValue enumValue = constantValueFirstArgument instanceof EnumValue ? (EnumValue) constantValueFirstArgument : null;
        if (enumValue == null) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        String strAsString = enumValue.getEnumEntryName().asString();
        switch (strAsString.hashCode()) {
            case 73135176:
                if (!strAsString.equals("MAYBE")) {
                    return null;
                }
                break;
            case 74175084:
                if (!strAsString.equals("NEVER")) {
                    return null;
                }
                break;
            case 433141802:
                if (strAsString.equals(GrsBaseInfo.CountryCodeSource.UNKNOWN)) {
                    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, z);
                }
                return null;
            case 1933739535:
                if (strAsString.equals("ALWAYS")) {
                    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
                }
                return null;
            default:
                return null;
        }
        return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
    }

    public final NullabilityQualifierWithMigrationStatus extractNullability(AnnotationDescriptor annotationDescriptor, boolean z, boolean z2) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations2 = extractNullabilityFromKnownAnnotations(annotationDescriptor, z, z2);
        if (nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations2 != null) {
            return nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations2;
        }
        AnnotationDescriptor annotationDescriptorResolveTypeQualifierAnnotation = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor);
        if (annotationDescriptorResolveTypeQualifierAnnotation == null) {
            return null;
        }
        ReportLevel reportLevelResolveJsr305AnnotationState = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptor);
        if (reportLevelResolveJsr305AnnotationState.isIgnore() || (nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations = extractNullabilityFromKnownAnnotations(annotationDescriptorResolveTypeQualifierAnnotation, z, z2)) == null) {
            return null;
        }
        return NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations, null, reportLevelResolveJsr305AnnotationState.isWarning(), 1, null);
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor annotationDescriptor, boolean z, boolean z2) {
        FqName fqName = annotationDescriptor.getFqName();
        if (fqName == null) {
            return null;
        }
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusCommonMigrationStatus = commonMigrationStatus(fqName, annotationDescriptor, (annotationDescriptor instanceof LazyJavaAnnotationDescriptor) && (((LazyJavaAnnotationDescriptor) annotationDescriptor).isFreshlySupportedTypeUseAnnotation() || z2) && !z);
        if (nullabilityQualifierWithMigrationStatusCommonMigrationStatus == null) {
            return null;
        }
        return (!nullabilityQualifierWithMigrationStatusCommonMigrationStatus.isForWarningOnly() && (annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor) && ((PossiblyExternalAnnotationDescriptor) annotationDescriptor).isIdeExternalAnnotation()) ? NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusCommonMigrationStatus, null, true, 1, null) : nullabilityQualifierWithMigrationStatusCommonMigrationStatus;
    }

    private final NullabilityQualifierWithMigrationStatus commonMigrationStatus(FqName fqName, AnnotationDescriptor annotationDescriptor, boolean z) {
        ReportLevel reportLevelInvoke = this.javaTypeEnhancementState.getGetReportLevelForAnnotation().invoke(fqName);
        if (reportLevelInvoke.isIgnore()) {
            return null;
        }
        boolean z2 = reportLevelInvoke.isWarning() || z;
        if (JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(fqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z2);
        }
        if (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(fqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJSPECIFY_NULLABLE())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJSPECIFY_NULLNESS_UNKNOWN())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION())) {
            return extractNullabilityTypeFromArgument(annotationDescriptor, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z2);
        }
        if (Intrinsics.areEqual(fqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z2);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(LazyJavaResolverContext c, Collection<? extends D> platformSignatures) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(platformSignatures, "platformSignatures");
        Collection<? extends D> collection = platformSignatures;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(enhanceSignature((CallableMemberDescriptor) it.next(), c));
        }
        return arrayList;
    }

    private final <D extends CallableMemberDescriptor> Annotations getDefaultAnnotations(D d, LazyJavaResolverContext lazyJavaResolverContext) {
        ClassifierDescriptor topLevelContainingClassifier = DescriptorUtilKt.getTopLevelContainingClassifier(d);
        if (topLevelContainingClassifier == null) {
            return d.getAnnotations();
        }
        LazyJavaClassDescriptor lazyJavaClassDescriptor = topLevelContainingClassifier instanceof LazyJavaClassDescriptor ? (LazyJavaClassDescriptor) topLevelContainingClassifier : null;
        List<JavaAnnotation> moduleAnnotations = lazyJavaClassDescriptor != null ? lazyJavaClassDescriptor.getModuleAnnotations() : null;
        List<JavaAnnotation> list = moduleAnnotations;
        if (list == null || list.isEmpty()) {
            return d.getAnnotations();
        }
        List<JavaAnnotation> list2 = moduleAnnotations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(new LazyJavaAnnotationDescriptor(lazyJavaResolverContext, (JavaAnnotation) it.next(), true));
        }
        return Annotations.Companion.create(CollectionsKt.plus((Iterable) d.getAnnotations(), (Iterable) arrayList));
    }

    /* JADX WARN: Code duplicated, block: B:113:0x01db A[RETURN] */
    private final <D extends CallableMemberDescriptor> D enhanceSignature(D d, LazyJavaResolverContext lazyJavaResolverContext) {
        CallableDescriptor callableDescriptor;
        PartEnhancementResult partEnhancementResultEnhance$default;
        AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
        boolean z;
        JavaPropertyDescriptor javaPropertyDescriptor;
        PropertyGetterDescriptorImpl getter;
        if (!(d instanceof JavaCallableMemberDescriptor)) {
            return d;
        }
        JavaCallableMemberDescriptor javaCallableMemberDescriptor = (JavaCallableMemberDescriptor) d;
        if (javaCallableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && javaCallableMemberDescriptor.getOriginal().getOverriddenDescriptors().size() == 1) {
            return d;
        }
        LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, getDefaultAnnotations(d, lazyJavaResolverContext));
        if (!(d instanceof JavaPropertyDescriptor) || (getter = (javaPropertyDescriptor = (JavaPropertyDescriptor) d).getGetter()) == null || getter.isDefault()) {
            callableDescriptor = d;
        } else {
            PropertyGetterDescriptorImpl getter2 = javaPropertyDescriptor.getGetter();
            Intrinsics.checkNotNull(getter2);
            Intrinsics.checkNotNullExpressionValue(getter2, "getter!!");
            callableDescriptor = getter2;
        }
        int i = 0;
        if (javaCallableMemberDescriptor.getExtensionReceiverParameter() != null) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) (!(callableDescriptor instanceof FunctionDescriptor) ? null : callableDescriptor);
            partEnhancementResultEnhance$default = SignatureParts.enhance$default(partsForValueParameter(d, functionDescriptor == null ? null : (ValueParameterDescriptor) functionDescriptor.getUserData(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER), lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$receiverTypeEnhancement$1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    ReceiverParameterDescriptor extensionReceiverParameter = it.getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    KotlinType type = extensionReceiverParameter.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "it.extensionReceiverParameter!!.type");
                    return type;
                }
            }), null, false, 3, null);
        } else {
            partEnhancementResultEnhance$default = null;
        }
        JavaMethodDescriptor javaMethodDescriptor = d instanceof JavaMethodDescriptor ? (JavaMethodDescriptor) d : null;
        PredefinedFunctionEnhancementInfo predefinedFunctionEnhancementInfo = javaMethodDescriptor == null ? null : PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE().get(MethodSignatureBuildingUtilsKt.signature(SignatureBuildingComponents.INSTANCE, (ClassDescriptor) javaMethodDescriptor.getContainingDeclaration(), MethodSignatureMappingKt.computeJvmDescriptor$default(javaMethodDescriptor, false, false, 3, null)));
        if (predefinedFunctionEnhancementInfo != null) {
            predefinedFunctionEnhancementInfo.getParametersInfo().size();
            javaCallableMemberDescriptor.getValueParameters().size();
        }
        boolean z2 = (UtilsKt.isJspecifyEnabledInStrictMode(this.javaTypeEnhancementState) || lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers.getComponents().getSettings().getIgnoreNullabilityForErasedValueParameters()) && UtilsKt.hasErasedValueParameters(d);
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "annotationOwnerForMember.valueParameters");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (final ValueParameterDescriptor valueParameterDescriptor : list) {
            arrayList.add(partsForValueParameter(d, valueParameterDescriptor, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$valueParameterEnhancements$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    KotlinType type = it.getValueParameters().get(valueParameterDescriptor.getIndex()).getType();
                    Intrinsics.checkNotNullExpressionValue(type, "it.valueParameters[p.index].type");
                    return type;
                }
            }).enhance(predefinedFunctionEnhancementInfo == null ? null : (TypeEnhancementInfo) CollectionsKt.getOrNull(predefinedFunctionEnhancementInfo.getParametersInfo(), valueParameterDescriptor.getIndex()), z2));
        }
        ArrayList arrayList2 = arrayList;
        CallableDescriptor callableDescriptor2 = callableDescriptor;
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) (!(d instanceof PropertyDescriptor) ? null : d);
        if (propertyDescriptor != null && JavaDescriptorUtilKt.isJavaField(propertyDescriptor)) {
            annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.FIELD;
        } else {
            annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE;
        }
        PartEnhancementResult partEnhancementResultEnhance$default2 = SignatureParts.enhance$default(parts(d, callableDescriptor2, true, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, annotationQualifierApplicabilityType, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$returnTypeEnhancement$1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KotlinType returnType = it.getReturnType();
                Intrinsics.checkNotNull(returnType);
                Intrinsics.checkNotNullExpressionValue(returnType, "it.returnType!!");
                return returnType;
            }
        }), predefinedFunctionEnhancementInfo == null ? null : predefinedFunctionEnhancementInfo.getReturnTypeInfo(), false, 2, null);
        if ((partEnhancementResultEnhance$default != null && partEnhancementResultEnhance$default.getContainsFunctionN()) || partEnhancementResultEnhance$default2.getContainsFunctionN()) {
            z = true;
        } else {
            ArrayList arrayList3 = arrayList2;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it = arrayList3.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((PartEnhancementResult) it.next()).getContainsFunctionN()) {
                            z = true;
                        }
                    }
                }
            }
            z = false;
        }
        if ((partEnhancementResultEnhance$default == null || !partEnhancementResultEnhance$default.getWereChanges()) && !partEnhancementResultEnhance$default2.getWereChanges()) {
            ArrayList arrayList4 = arrayList2;
            if ((arrayList4 instanceof Collection) && arrayList4.isEmpty()) {
                if (!z) {
                    break;
                }
                return d;
            }
            Iterator it2 = arrayList4.iterator();
            do {
                if (!it2.hasNext()) {
                    if (!z) {
                        break;
                    }
                    return d;
                }
            } while (!((PartEnhancementResult) it2.next()).getWereChanges());
        }
        Pair<CallableDescriptor.UserDataKey<?>, ?> pair = z ? TuplesKt.to(DeprecationKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionN(d)) : null;
        KotlinType type = partEnhancementResultEnhance$default != null ? partEnhancementResultEnhance$default.getType() : null;
        ArrayList arrayList5 = arrayList2;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        for (Object obj : arrayList5) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList6.add(new ValueParameterData(((PartEnhancementResult) obj).getType(), callableDescriptor.getValueParameters().get(i).declaresDefaultValue()));
            i = i2;
        }
        return javaCallableMemberDescriptor.enhance(type, arrayList6, partEnhancementResultEnhance$default2.getType(), pair);
    }

    public final List<KotlinType> enhanceTypeParameterBounds(TypeParameterDescriptor typeParameterDescriptor, List<? extends KotlinType> bounds, LazyJavaResolverContext context) {
        TypeParameterDescriptor typeParameter = typeParameterDescriptor;
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        Intrinsics.checkNotNullParameter(context, "context");
        List<? extends KotlinType> list = bounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KotlinType type : list) {
            if (!TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceTypeParameterBounds$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof RawType);
                }
            })) {
                type = SignatureParts.enhance$default(new SignatureParts(typeParameter, type, CollectionsKt.emptyList(), false, context, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, true, false, 128, null), null, false, 3, null).getType();
            }
            arrayList.add(type);
            typeParameter = typeParameterDescriptor;
        }
        return arrayList;
    }

    public final KotlinType enhanceSuperType(KotlinType type, LazyJavaResolverContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        return SignatureParts.enhance$default(new SignatureParts(null, type, CollectionsKt.emptyList(), false, context, AnnotationQualifierApplicabilityType.TYPE_USE, false, true, 64, null), null, false, 3, null).getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: signatureEnhancement.kt */
    final class SignatureParts {
        private final AnnotationQualifierApplicabilityType containerApplicabilityType;
        private final LazyJavaResolverContext containerContext;
        private final Collection<KotlinType> fromOverridden;
        private final KotlinType fromOverride;
        private final boolean isCovariant;
        private final boolean isSuperTypesEnhancement;
        private final Annotated typeContainer;
        private final boolean typeParameterBounds;

        /* JADX WARN: Multi-variable type inference failed */
        public SignatureParts(SignatureEnhancement this$0, Annotated annotated, KotlinType fromOverride, Collection<? extends KotlinType> fromOverridden, boolean z, LazyJavaResolverContext containerContext, AnnotationQualifierApplicabilityType containerApplicabilityType, boolean z2, boolean z3) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(fromOverride, "fromOverride");
            Intrinsics.checkNotNullParameter(fromOverridden, "fromOverridden");
            Intrinsics.checkNotNullParameter(containerContext, "containerContext");
            Intrinsics.checkNotNullParameter(containerApplicabilityType, "containerApplicabilityType");
            SignatureEnhancement.this = this$0;
            this.typeContainer = annotated;
            this.fromOverride = fromOverride;
            this.fromOverridden = fromOverridden;
            this.isCovariant = z;
            this.containerContext = containerContext;
            this.containerApplicabilityType = containerApplicabilityType;
            this.typeParameterBounds = z2;
            this.isSuperTypesEnhancement = z3;
        }

        public /* synthetic */ SignatureParts(Annotated annotated, KotlinType kotlinType, Collection collection, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(SignatureEnhancement.this, annotated, kotlinType, collection, z, lazyJavaResolverContext, annotationQualifierApplicabilityType, (i & 64) != 0 ? false : z2, (i & 128) != 0 ? false : z3);
        }

        private final boolean isForVarargParameter() {
            Annotated annotated = this.typeContainer;
            if (!(annotated instanceof ValueParameterDescriptor)) {
                annotated = null;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) annotated;
            return (valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null;
        }

        public static /* synthetic */ PartEnhancementResult enhance$default(SignatureParts signatureParts, TypeEnhancementInfo typeEnhancementInfo, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                typeEnhancementInfo = null;
            }
            if ((i & 2) != 0) {
                z = false;
            }
            return signatureParts.enhance(typeEnhancementInfo, z);
        }

        public final PartEnhancementResult enhance(final TypeEnhancementInfo typeEnhancementInfo, boolean z) {
            boolean zContains;
            final Function1<Integer, JavaTypeQualifiers> function1ComputeIndexedQualifiersForOverride = computeIndexedQualifiersForOverride(z);
            Function1<Integer, JavaTypeQualifiers> function1 = typeEnhancementInfo == null ? null : new Function1<Integer, JavaTypeQualifiers>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$qualifiersWithPredefined$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final JavaTypeQualifiers invoke(int i) {
                    JavaTypeQualifiers javaTypeQualifiers = typeEnhancementInfo.getMap().get(Integer.valueOf(i));
                    return javaTypeQualifiers == null ? function1ComputeIndexedQualifiersForOverride.invoke(Integer.valueOf(i)) : javaTypeQualifiers;
                }
            };
            if (this.isSuperTypesEnhancement) {
                zContains = TypeUtils.containsStoppingAt(this.fromOverride, SignatureEnhancement$SignatureParts$enhance$containsFunctionN$1.INSTANCE, new Function1<KotlinType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$containsFunctionN$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinType kotlinType) {
                        return Boolean.valueOf(kotlinType instanceof RawType);
                    }
                });
            } else {
                zContains = TypeUtils.contains(this.fromOverride, SignatureEnhancement$SignatureParts$enhance$containsFunctionN$3.INSTANCE);
            }
            JavaTypeEnhancement javaTypeEnhancement = SignatureEnhancement.this.typeEnhancement;
            KotlinType kotlinType = this.fromOverride;
            if (function1 != null) {
                function1ComputeIndexedQualifiersForOverride = function1;
            }
            KotlinType kotlinTypeEnhance = javaTypeEnhancement.enhance(kotlinType, function1ComputeIndexedQualifiersForOverride, this.isSuperTypesEnhancement);
            if (kotlinTypeEnhance != null) {
                return new PartEnhancementResult(kotlinTypeEnhance, true, zContains);
            }
            return new PartEnhancementResult(this.fromOverride, false, zContains);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean enhance$containsFunctionN(UnwrappedType unwrappedType) {
            ClassifierDescriptor classifierDescriptorMo4184getDeclarationDescriptor = unwrappedType.getConstructor().mo4184getDeclarationDescriptor();
            return classifierDescriptorMo4184getDeclarationDescriptor != null && Intrinsics.areEqual(classifierDescriptorMo4184getDeclarationDescriptor.getName(), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME().shortName()) && Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classifierDescriptorMo4184getDeclarationDescriptor), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME());
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0048  */
        /* JADX WARN: Code duplicated, block: B:17:0x004b  */
        /* JADX WARN: Code duplicated, block: B:19:0x0051  */
        /* JADX WARN: Code duplicated, block: B:20:0x0054  */
        /* JADX WARN: Code duplicated, block: B:27:0x0068  */
        private final JavaTypeQualifiers extractQualifiers(KotlinType kotlinType) {
            Pair pair;
            NullabilityQualifier nullabilityQualifier;
            NullabilityQualifier nullabilityQualifier2;
            MutabilityQualifier mutabilityQualifier;
            boolean z;
            if (FlexibleTypesKt.isFlexible(kotlinType)) {
                FlexibleType flexibleTypeAsFlexibleType = FlexibleTypesKt.asFlexibleType(kotlinType);
                pair = new Pair(flexibleTypeAsFlexibleType.getLowerBound(), flexibleTypeAsFlexibleType.getUpperBound());
            } else {
                pair = new Pair(kotlinType, kotlinType);
            }
            KotlinType kotlinType2 = (KotlinType) pair.component1();
            KotlinType kotlinType3 = (KotlinType) pair.component2();
            JavaToKotlinClassMapper javaToKotlinClassMapper = JavaToKotlinClassMapper.INSTANCE;
            if (!kotlinType2.isMarkedNullable()) {
                if (kotlinType3.isMarkedNullable()) {
                    nullabilityQualifier = null;
                } else {
                    nullabilityQualifier2 = NullabilityQualifier.NOT_NULL;
                }
                if (javaToKotlinClassMapper.isReadOnly(kotlinType2)) {
                    mutabilityQualifier = MutabilityQualifier.READ_ONLY;
                } else if (javaToKotlinClassMapper.isMutable(kotlinType3)) {
                    mutabilityQualifier = MutabilityQualifier.MUTABLE;
                } else {
                    mutabilityQualifier = null;
                }
                if (!(kotlinType.unwrap() instanceof NotNullTypeParameter) || (kotlinType.unwrap() instanceof DefinitelyNotNullType)) {
                    z = true;
                } else {
                    z = false;
                }
                return new JavaTypeQualifiers(nullabilityQualifier, mutabilityQualifier, z, false, 8, null);
            }
            nullabilityQualifier2 = NullabilityQualifier.NULLABLE;
            nullabilityQualifier = nullabilityQualifier2;
            if (javaToKotlinClassMapper.isReadOnly(kotlinType2)) {
                mutabilityQualifier = MutabilityQualifier.READ_ONLY;
            } else if (javaToKotlinClassMapper.isMutable(kotlinType3)) {
                mutabilityQualifier = MutabilityQualifier.MUTABLE;
            } else {
                mutabilityQualifier = null;
            }
            if (kotlinType.unwrap() instanceof NotNullTypeParameter) {
                z = true;
            } else {
                z = true;
            }
            return new JavaTypeQualifiers(nullabilityQualifier, mutabilityQualifier, z, false, 8, null);
        }

        private final JavaTypeQualifiers extractQualifiersFromAnnotations(KotlinType kotlinType, boolean z, JavaDefaultQualifiers javaDefaultQualifiers, TypeParameterDescriptor typeParameterDescriptor, boolean z2) {
            Annotations annotations;
            Annotated annotated;
            Annotated annotated2;
            if (z2) {
                if ((typeParameterDescriptor == null ? null : typeParameterDescriptor.getVariance()) == Variance.IN_VARIANCE) {
                    return JavaTypeQualifiers.Companion.getNONE();
                }
            }
            boolean typeEnhancementImprovementsInStrictMode = this.containerContext.getComponents().getSettings().getTypeEnhancementImprovementsInStrictMode();
            if (z && (annotated2 = this.typeContainer) != null && !(annotated2 instanceof TypeParameterDescriptor) && typeEnhancementImprovementsInStrictMode) {
                Annotations annotations2 = annotated2.getAnnotations();
                SignatureEnhancement signatureEnhancement = SignatureEnhancement.this;
                ArrayList arrayList = new ArrayList();
                for (AnnotationDescriptor annotationDescriptor : annotations2) {
                    AnnotationTypeQualifierResolver.TypeQualifierWithApplicability typeQualifierWithApplicabilityResolveAnnotation = signatureEnhancement.annotationTypeQualifierResolver.resolveAnnotation(annotationDescriptor);
                    if (typeQualifierWithApplicabilityResolveAnnotation == null || !typeQualifierWithApplicabilityResolveAnnotation.component2().contains(AnnotationQualifierApplicabilityType.TYPE_USE)) {
                        arrayList.add(annotationDescriptor);
                    }
                }
                annotations = AnnotationsKt.composeAnnotations(Annotations.Companion.create(arrayList), kotlinType.getAnnotations());
            } else if (z && (annotated = this.typeContainer) != null) {
                annotations = AnnotationsKt.composeAnnotations(annotated.getAnnotations(), kotlinType.getAnnotations());
            } else {
                annotations = kotlinType.getAnnotations();
            }
            if (z) {
                JavaTypeQualifiersByElementType defaultTypeQualifiers = this.containerContext.getDefaultTypeQualifiers();
                javaDefaultQualifiers = defaultTypeQualifiers == null ? null : defaultTypeQualifiers.get(this.containerApplicabilityType);
            }
            Pair<NullabilityQualifierWithMigrationStatus, Boolean> pairNullabilityInfoBoundsForTypeParameterUsage = nullabilityInfoBoundsForTypeParameterUsage(kotlinType);
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusComponent1 = pairNullabilityInfoBoundsForTypeParameterUsage.component1();
            boolean zBooleanValue = pairNullabilityInfoBoundsForTypeParameterUsage.component2().booleanValue();
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = extractNullability(annotations, typeEnhancementImprovementsInStrictMode, this.typeParameterBounds);
            if (nullabilityQualifierWithMigrationStatusExtractNullability == null || z2) {
                nullabilityQualifierWithMigrationStatusExtractNullability = null;
            }
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusComputeNullabilityInfoInTheAbsenceOfExplicitAnnotation = nullabilityQualifierWithMigrationStatusExtractNullability == null ? computeNullabilityInfoInTheAbsenceOfExplicitAnnotation(nullabilityQualifierWithMigrationStatusComponent1, javaDefaultQualifiers, typeParameterDescriptor) : nullabilityQualifierWithMigrationStatusExtractNullability;
            boolean z3 = false;
            boolean z4 = nullabilityQualifierWithMigrationStatusExtractNullability == null ? zBooleanValue || (javaDefaultQualifiers != null && javaDefaultQualifiers.getDefinitelyNotNull()) : nullabilityQualifierWithMigrationStatusExtractNullability.getQualifier() == NullabilityQualifier.NOT_NULL;
            NullabilityQualifier qualifier = nullabilityQualifierWithMigrationStatusComputeNullabilityInfoInTheAbsenceOfExplicitAnnotation != null ? nullabilityQualifierWithMigrationStatusComputeNullabilityInfoInTheAbsenceOfExplicitAnnotation.getQualifier() : null;
            MutabilityQualifier mutabilityQualifier = (MutabilityQualifier) extractQualifiersFromAnnotations$uniqueNotNull(extractQualifiersFromAnnotations$ifPresent(JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS(), annotations, MutabilityQualifier.READ_ONLY), extractQualifiersFromAnnotations$ifPresent(JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS(), annotations, MutabilityQualifier.MUTABLE));
            boolean z5 = z4 && TypeUtilsKt.isTypeParameter(kotlinType);
            if (nullabilityQualifierWithMigrationStatusComputeNullabilityInfoInTheAbsenceOfExplicitAnnotation != null && nullabilityQualifierWithMigrationStatusComputeNullabilityInfoInTheAbsenceOfExplicitAnnotation.isForWarningOnly()) {
                z3 = true;
            }
            return new JavaTypeQualifiers(qualifier, mutabilityQualifier, z5, z3);
        }

        private static final <T> T extractQualifiersFromAnnotations$ifPresent(List<FqName> list, Annotations annotations, T t) {
            List<FqName> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    if (annotations.mo4178findAnnotation((FqName) it.next()) != null) {
                        return t;
                    }
                }
            }
            return null;
        }

        private static final <T> T extractQualifiersFromAnnotations$uniqueNotNull(T t, T t2) {
            if (t == null || t2 == null || Intrinsics.areEqual(t, t2)) {
                return t == null ? t2 : t;
            }
            return null;
        }

        private final NullabilityQualifierWithMigrationStatus computeNullabilityInfoInTheAbsenceOfExplicitAnnotation(NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, JavaDefaultQualifiers javaDefaultQualifiers, TypeParameterDescriptor typeParameterDescriptor) {
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusBoundsNullability;
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus2 = null;
            if (nullabilityQualifierWithMigrationStatus == null) {
                nullabilityQualifierWithMigrationStatus = javaDefaultQualifiers == null ? null : javaDefaultQualifiers.getNullabilityQualifier();
            }
            if (typeParameterDescriptor != null && (nullabilityQualifierWithMigrationStatusBoundsNullability = boundsNullability(typeParameterDescriptor)) != null) {
                if (nullabilityQualifierWithMigrationStatusBoundsNullability.getQualifier() == NullabilityQualifier.NULLABLE) {
                    nullabilityQualifierWithMigrationStatusBoundsNullability = NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusBoundsNullability, NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null);
                }
                nullabilityQualifierWithMigrationStatus2 = nullabilityQualifierWithMigrationStatusBoundsNullability;
            }
            return mostSpecific(nullabilityQualifierWithMigrationStatus2, nullabilityQualifierWithMigrationStatus);
        }

        private final NullabilityQualifierWithMigrationStatus mostSpecific(NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus2) {
            if (nullabilityQualifierWithMigrationStatus == null) {
                return nullabilityQualifierWithMigrationStatus2;
            }
            if (nullabilityQualifierWithMigrationStatus2 == null) {
                return nullabilityQualifierWithMigrationStatus;
            }
            if (nullabilityQualifierWithMigrationStatus.isForWarningOnly() && !nullabilityQualifierWithMigrationStatus2.isForWarningOnly()) {
                return nullabilityQualifierWithMigrationStatus2;
            }
            if (nullabilityQualifierWithMigrationStatus.isForWarningOnly() || !nullabilityQualifierWithMigrationStatus2.isForWarningOnly()) {
                return (nullabilityQualifierWithMigrationStatus.getQualifier().compareTo(nullabilityQualifierWithMigrationStatus2.getQualifier()) >= 0 && nullabilityQualifierWithMigrationStatus.getQualifier().compareTo(nullabilityQualifierWithMigrationStatus2.getQualifier()) > 0) ? nullabilityQualifierWithMigrationStatus : nullabilityQualifierWithMigrationStatus2;
            }
            return nullabilityQualifierWithMigrationStatus;
        }

        private final Pair<NullabilityQualifierWithMigrationStatus, Boolean> nullabilityInfoBoundsForTypeParameterUsage(KotlinType kotlinType) {
            ClassifierDescriptor classifierDescriptorMo4184getDeclarationDescriptor = kotlinType.getConstructor().mo4184getDeclarationDescriptor();
            TypeParameterDescriptor typeParameterDescriptor = classifierDescriptorMo4184getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo4184getDeclarationDescriptor : null;
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusBoundsNullability = typeParameterDescriptor == null ? null : boundsNullability(typeParameterDescriptor);
            if (nullabilityQualifierWithMigrationStatusBoundsNullability == null) {
                return new Pair<>(null, false);
            }
            return new Pair<>(new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, nullabilityQualifierWithMigrationStatusBoundsNullability.isForWarningOnly()), Boolean.valueOf(nullabilityQualifierWithMigrationStatusBoundsNullability.getQualifier() == NullabilityQualifier.NOT_NULL));
        }

        private final NullabilityQualifierWithMigrationStatus boundsNullability(TypeParameterDescriptor typeParameterDescriptor) {
            NullabilityQualifier nullabilityQualifier;
            if (typeParameterDescriptor instanceof LazyJavaTypeParameterDescriptor) {
                LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptor = (LazyJavaTypeParameterDescriptor) typeParameterDescriptor;
                List<KotlinType> upperBounds = lazyJavaTypeParameterDescriptor.getUpperBounds();
                Intrinsics.checkNotNullExpressionValue(upperBounds, "upperBounds");
                List<KotlinType> list = upperBounds;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        if (!KotlinTypeKt.isError((KotlinType) it.next())) {
                            List<KotlinType> upperBounds2 = lazyJavaTypeParameterDescriptor.getUpperBounds();
                            Intrinsics.checkNotNullExpressionValue(upperBounds2, "upperBounds");
                            List<KotlinType> list2 = upperBounds2;
                            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                                Iterator<T> it2 = list2.iterator();
                                while (it2.hasNext()) {
                                    if (!SignatureEnhancementKt.isNullabilityFlexible((KotlinType) it2.next())) {
                                        List<KotlinType> upperBounds3 = lazyJavaTypeParameterDescriptor.getUpperBounds();
                                        Intrinsics.checkNotNullExpressionValue(upperBounds3, "upperBounds");
                                        List<KotlinType> list3 = upperBounds3;
                                        if ((list3 instanceof Collection) && list3.isEmpty()) {
                                            nullabilityQualifier = NullabilityQualifier.NULLABLE;
                                        } else {
                                            for (KotlinType it3 : list3) {
                                                Intrinsics.checkNotNullExpressionValue(it3, "it");
                                                if (!KotlinTypeKt.isNullable(it3)) {
                                                    nullabilityQualifier = NullabilityQualifier.NOT_NULL;
                                                }
                                            }
                                            nullabilityQualifier = NullabilityQualifier.NULLABLE;
                                        }
                                        return new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, false, 2, null);
                                    }
                                }
                            }
                            List<KotlinType> upperBounds4 = lazyJavaTypeParameterDescriptor.getUpperBounds();
                            Intrinsics.checkNotNullExpressionValue(upperBounds4, "upperBounds");
                            List<KotlinType> list4 = upperBounds4;
                            if (!(list4 instanceof Collection) || !list4.isEmpty()) {
                                for (KotlinType kotlinType : list4) {
                                    if ((kotlinType instanceof FlexibleTypeWithEnhancement) && !KotlinTypeKt.isNullable(((FlexibleTypeWithEnhancement) kotlinType).getEnhancement())) {
                                        return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true);
                                    }
                                }
                            }
                            List<KotlinType> upperBounds5 = lazyJavaTypeParameterDescriptor.getUpperBounds();
                            Intrinsics.checkNotNullExpressionValue(upperBounds5, "upperBounds");
                            List<KotlinType> list5 = upperBounds5;
                            if (!(list5 instanceof Collection) || !list5.isEmpty()) {
                                for (KotlinType kotlinType2 : list5) {
                                    if ((kotlinType2 instanceof FlexibleTypeWithEnhancement) && KotlinTypeKt.isNullable(((FlexibleTypeWithEnhancement) kotlinType2).getEnhancement())) {
                                        return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true);
                                    }
                                }
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            return null;
        }

        private final NullabilityQualifierWithMigrationStatus extractNullability(Annotations annotations, boolean z, boolean z2) {
            SignatureEnhancement signatureEnhancement = SignatureEnhancement.this;
            Iterator<AnnotationDescriptor> it = annotations.iterator();
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = null;
            while (it.hasNext()) {
                NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = signatureEnhancement.extractNullability(it.next(), z, z2);
                if (nullabilityQualifierWithMigrationStatus != null) {
                    if (nullabilityQualifierWithMigrationStatusExtractNullability != null && !Intrinsics.areEqual(nullabilityQualifierWithMigrationStatusExtractNullability, nullabilityQualifierWithMigrationStatus) && (!nullabilityQualifierWithMigrationStatusExtractNullability.isForWarningOnly() || nullabilityQualifierWithMigrationStatus.isForWarningOnly())) {
                        if (nullabilityQualifierWithMigrationStatusExtractNullability.isForWarningOnly() || !nullabilityQualifierWithMigrationStatus.isForWarningOnly()) {
                            return null;
                        }
                    }
                }
                nullabilityQualifierWithMigrationStatus = nullabilityQualifierWithMigrationStatusExtractNullability;
            }
            return nullabilityQualifierWithMigrationStatus;
        }

        /* JADX WARN: Code duplicated, block: B:19:0x0067 A[EDGE_INSN: B:19:0x0067->B:20:0x0068 BREAK  A[LOOP:1: B:14:0x004f->B:44:?]] */
        private final Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiersForOverride(boolean z) {
            boolean z2;
            Collection<KotlinType> collection = this.fromOverridden;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(toIndexed((KotlinType) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            List<TypeAndDefaultQualifiers> indexed = toIndexed(this.fromOverride);
            if (!this.isCovariant) {
                z2 = false;
                break;
            }
            Collection<KotlinType> collection2 = this.fromOverridden;
            if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
                Iterator<T> it2 = collection2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z2 = false;
                        break;
                    }
                    if (!KotlinTypeChecker.DEFAULT.equalTypes((KotlinType) it2.next(), this.fromOverride)) {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = false;
                break;
            }
            int size = z2 ? 1 : indexed.size();
            final JavaTypeQualifiers[] javaTypeQualifiersArr = new JavaTypeQualifiers[size];
            int i = 0;
            while (i < size) {
                boolean z3 = i == 0;
                TypeAndDefaultQualifiers typeAndDefaultQualifiers = indexed.get(i);
                KotlinType kotlinTypeComponent1 = typeAndDefaultQualifiers.component1();
                JavaDefaultQualifiers javaDefaultQualifiersComponent2 = typeAndDefaultQualifiers.component2();
                TypeParameterDescriptor typeParameterDescriptorComponent3 = typeAndDefaultQualifiers.component3();
                boolean zComponent4 = typeAndDefaultQualifiers.component4();
                ArrayList arrayList3 = new ArrayList();
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    TypeAndDefaultQualifiers typeAndDefaultQualifiers2 = (TypeAndDefaultQualifiers) CollectionsKt.getOrNull((List) it3.next(), i);
                    KotlinType type = typeAndDefaultQualifiers2 == null ? null : typeAndDefaultQualifiers2.getType();
                    if (type != null) {
                        arrayList3.add(type);
                    }
                }
                int i2 = i;
                javaTypeQualifiersArr[i2] = computeQualifiersForOverride(kotlinTypeComponent1, arrayList3, javaDefaultQualifiersComponent2, z3, typeParameterDescriptorComponent3, zComponent4, z);
                i = i2 + 1;
            }
            return new Function1<Integer, JavaTypeQualifiers>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                    return invoke(num.intValue());
                }

                public final JavaTypeQualifiers invoke(int i3) {
                    JavaTypeQualifiers[] javaTypeQualifiersArr2 = javaTypeQualifiersArr;
                    return (i3 < 0 || i3 > ArraysKt.getLastIndex(javaTypeQualifiersArr2)) ? JavaTypeQualifiers.Companion.getNONE() : javaTypeQualifiersArr2[i3];
                }
            };
        }

        private final List<TypeAndDefaultQualifiers> toIndexed(KotlinType kotlinType) {
            ArrayList arrayList = new ArrayList(1);
            toIndexed$add(this, arrayList, kotlinType, this.containerContext, null);
            return arrayList;
        }

        private static final void toIndexed$add(SignatureParts signatureParts, ArrayList<TypeAndDefaultQualifiers> arrayList, KotlinType kotlinType, LazyJavaResolverContext lazyJavaResolverContext, TypeParameterDescriptor typeParameterDescriptor) {
            AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
            JavaDefaultQualifiers javaDefaultQualifiers;
            LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, kotlinType.getAnnotations());
            JavaTypeQualifiersByElementType defaultTypeQualifiers = lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
            if (defaultTypeQualifiers == null) {
                javaDefaultQualifiers = null;
            } else {
                if (signatureParts.typeParameterBounds) {
                    annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS;
                } else {
                    annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_USE;
                }
                javaDefaultQualifiers = defaultTypeQualifiers.get(annotationQualifierApplicabilityType);
            }
            arrayList.add(new TypeAndDefaultQualifiers(kotlinType, javaDefaultQualifiers, typeParameterDescriptor, false));
            if (signatureParts.isSuperTypesEnhancement && (kotlinType instanceof RawType)) {
                return;
            }
            List<TypeProjection> arguments = kotlinType.getArguments();
            List<TypeParameterDescriptor> parameters = kotlinType.getConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "type.constructor.parameters");
            for (Pair pair : CollectionsKt.zip(arguments, parameters)) {
                TypeProjection typeProjection = (TypeProjection) pair.component1();
                TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) pair.component2();
                if (typeProjection.isStarProjection()) {
                    KotlinType type = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "arg.type");
                    arrayList.add(new TypeAndDefaultQualifiers(type, javaDefaultQualifiers, typeParameterDescriptor2, true));
                } else {
                    KotlinType type2 = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "arg.type");
                    toIndexed$add(signatureParts, arrayList, type2, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, typeParameterDescriptor2);
                }
            }
        }

        private final JavaTypeQualifiers computeQualifiersForOverride(KotlinType kotlinType, Collection<? extends KotlinType> collection, JavaDefaultQualifiers javaDefaultQualifiers, boolean z, TypeParameterDescriptor typeParameterDescriptor, boolean z2, boolean z3) {
            boolean z4;
            Collection<? extends KotlinType> collection2 = collection;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                arrayList.add(extractQualifiers((KotlinType) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                MutabilityQualifier mutability = ((JavaTypeQualifiers) it2.next()).getMutability();
                if (mutability != null) {
                    arrayList3.add(mutability);
                }
            }
            Set set = CollectionsKt.toSet(arrayList3);
            ArrayList arrayList4 = new ArrayList();
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                NullabilityQualifier nullability = ((JavaTypeQualifiers) it3.next()).getNullability();
                if (nullability != null) {
                    arrayList4.add(nullability);
                }
            }
            Set set2 = CollectionsKt.toSet(arrayList4);
            ArrayList arrayList5 = new ArrayList();
            Iterator<T> it4 = collection2.iterator();
            while (it4.hasNext()) {
                NullabilityQualifier nullability2 = extractQualifiers(TypeWithEnhancementKt.unwrapEnhancement((KotlinType) it4.next())).getNullability();
                if (nullability2 != null) {
                    arrayList5.add(nullability2);
                }
            }
            Set set3 = CollectionsKt.toSet(arrayList5);
            JavaTypeQualifiers javaTypeQualifiersExtractQualifiersFromAnnotations = extractQualifiersFromAnnotations(kotlinType, z, javaDefaultQualifiers, typeParameterDescriptor, z2);
            NullabilityQualifier nullabilityQualifier = null;
            JavaTypeQualifiers javaTypeQualifiers = !javaTypeQualifiersExtractQualifiersFromAnnotations.isNullabilityQualifierForWarning() ? javaTypeQualifiersExtractQualifiersFromAnnotations : null;
            NullabilityQualifier nullability3 = javaTypeQualifiers == null ? null : javaTypeQualifiers.getNullability();
            NullabilityQualifier nullability4 = javaTypeQualifiersExtractQualifiersFromAnnotations.getNullability();
            boolean z5 = this.isCovariant && z;
            NullabilityQualifier nullabilityQualifierSelect = TypeEnhancementUtilsKt.select(set2, nullability3, z5);
            if (nullabilityQualifierSelect != null && !z3 && (!isForVarargParameter() || !z || nullabilityQualifierSelect != NullabilityQualifier.NULLABLE)) {
                nullabilityQualifier = nullabilityQualifierSelect;
            }
            MutabilityQualifier mutabilityQualifier = (MutabilityQualifier) TypeEnhancementUtilsKt.select(set, MutabilityQualifier.MUTABLE, MutabilityQualifier.READ_ONLY, javaTypeQualifiersExtractQualifiersFromAnnotations.getMutability(), z5);
            boolean z6 = (nullability4 == nullability3 && Intrinsics.areEqual(set3, set2)) ? false : true;
            if (javaTypeQualifiersExtractQualifiersFromAnnotations.getDefinitelyNotNull()) {
                z4 = true;
            } else {
                if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                    Iterator it5 = arrayList2.iterator();
                    while (true) {
                        if (it5.hasNext()) {
                            if (((JavaTypeQualifiers) it5.next()).getDefinitelyNotNull()) {
                                z4 = true;
                            }
                        }
                    }
                }
                z4 = false;
            }
            if (nullabilityQualifier == null && z6) {
                return TypeEnhancementUtilsKt.createJavaTypeQualifiers(TypeEnhancementUtilsKt.select(set3, nullability4, z5), mutabilityQualifier, true, z4);
            }
            return TypeEnhancementUtilsKt.createJavaTypeQualifiers(nullabilityQualifier, mutabilityQualifier, nullabilityQualifier == null, z4);
        }
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private static class PartEnhancementResult {
        private final boolean containsFunctionN;
        private final KotlinType type;
        private final boolean wereChanges;

        public PartEnhancementResult(KotlinType type, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
            this.wereChanges = z;
            this.containsFunctionN = z2;
        }

        public final KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final boolean getContainsFunctionN() {
            return this.containsFunctionN;
        }
    }

    private final SignatureParts partsForValueParameter(CallableMemberDescriptor callableMemberDescriptor, ValueParameterDescriptor valueParameterDescriptor, LazyJavaResolverContext lazyJavaResolverContext, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
        if (valueParameterDescriptor != null) {
            lazyJavaResolverContext = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, valueParameterDescriptor.getAnnotations());
        }
        return parts(callableMemberDescriptor, valueParameterDescriptor2, false, lazyJavaResolverContext, AnnotationQualifierApplicabilityType.VALUE_PARAMETER, function1);
    }

    private final SignatureParts parts(CallableMemberDescriptor callableMemberDescriptor, Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        KotlinType kotlinTypeInvoke = function1.invoke(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "this.overriddenDescriptors");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (CallableMemberDescriptor it : collection) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            arrayList.add(function1.invoke(it));
        }
        return new SignatureParts(annotated, kotlinTypeInvoke, arrayList, z, ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, function1.invoke(callableMemberDescriptor).getAnnotations()), annotationQualifierApplicabilityType, false, false, 192, null);
    }
}
