package org.jetbrains.kotlin.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.load.java.AnnotationQualifierApplicabilityType;
import org.jetbrains.kotlin.load.java.DeprecationCausedByFunctionNInfo;
import org.jetbrains.kotlin.load.java.UtilsKt;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor;
import org.jetbrains.kotlin.load.java.lazy.ContextKt;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureBuildingUtilsKt;
import org.jetbrains.kotlin.load.kotlin.MethodSignatureMappingKt;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponents;
import org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfoKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J,\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\b\b\u0000\u0010\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\nJ*\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u000f\u001a\u00020\nJ\f\u0010\u0015\u001a\u00020\u0016*\u00020\rH\u0002JX\u0010\u0017\u001a\u0004\u0018\u00010\r*\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\b\u0002\u0010 \u001a\u00020\u00162\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\r0\"H\u0002J:\u0010\u0017\u001a\u0004\u0018\u00010\r*\u00020#2\u0006\u0010\u000e\u001a\u00020\r2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\b\u0002\u0010 \u001a\u00020\u0016H\u0002J#\u0010%\u001a\u0002H\u0007\"\b\b\u0000\u0010\u0007*\u00020\b*\u0002H\u00072\u0006\u0010\t\u001a\u00020\nH\u0002¢\u0006\u0002\u0010&JF\u0010'\u001a\u0004\u0018\u00010\r*\u00020\b2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u00162\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\r0\"H\u0002J#\u0010+\u001a\u00020,\"\b\b\u0000\u0010\u0007*\u00020\b*\u0002H\u00072\u0006\u0010\t\u001a\u00020\nH\u0002¢\u0006\u0002\u0010-R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureEnhancement;", "", "typeEnhancement", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement;", "(Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeEnhancement;)V", "enhanceSignatures", "", "D", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "platformSignatures", "enhanceSuperType", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "context", "enhanceTypeParameterBounds", "", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "bounds", "containsFunctionN", "", "enhance", "typeContainer", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotated;", "isCovariant", "containerContext", "containerApplicabilityType", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "predefined", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeEnhancementInfo;", "ignoreDeclarationNullabilityAnnotations", "collector", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/SignatureParts;", "overrides", "enhanceSignature", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;)Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "enhanceValueParameter", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "methodContext", "getDefaultAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;)Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignatureEnhancement {
    private final JavaTypeEnhancement typeEnhancement;

    public SignatureEnhancement(JavaTypeEnhancement typeEnhancement) {
        Intrinsics.checkNotNullParameter(typeEnhancement, "typeEnhancement");
        this.typeEnhancement = typeEnhancement;
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
        return Annotations.INSTANCE.create(CollectionsKt.plus((Iterable) d.getAnnotations(), (Iterable) arrayList));
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:95:0x01f1  */
    private final <D extends CallableMemberDescriptor> D enhanceSignature(D d, LazyJavaResolverContext lazyJavaResolverContext) {
        CallableDescriptor callableDescriptor;
        KotlinType kotlinTypeEnhanceValueParameter;
        PredefinedFunctionEnhancementInfo predefinedFunctionEnhancementInfo;
        AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
        Pair<CallableDescriptor.UserDataKey<?>, ?> pair;
        KotlinType type;
        boolean z;
        KotlinType type2;
        List<TypeEnhancementInfo> parametersInfo;
        JavaPropertyDescriptor javaPropertyDescriptor;
        PropertyGetterDescriptorImpl getter;
        if (!(d instanceof JavaCallableMemberDescriptor)) {
            return d;
        }
        JavaCallableMemberDescriptor javaCallableMemberDescriptor = (JavaCallableMemberDescriptor) d;
        boolean z2 = true;
        if (javaCallableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && javaCallableMemberDescriptor.getOriginal().getOverriddenDescriptors().size() == 1) {
            return d;
        }
        LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, getDefaultAnnotations(d, lazyJavaResolverContext));
        if (!(d instanceof JavaPropertyDescriptor) || (getter = (javaPropertyDescriptor = (JavaPropertyDescriptor) d).getGetter()) == null || getter.isDefault()) {
            callableDescriptor = d;
        } else {
            PropertyGetterDescriptorImpl getter2 = javaPropertyDescriptor.getGetter();
            Intrinsics.checkNotNull(getter2);
            callableDescriptor = getter2;
        }
        TypeEnhancementInfo typeEnhancementInfo = null;
        if (javaCallableMemberDescriptor.getExtensionReceiverParameter() != null) {
            FunctionDescriptor functionDescriptor = callableDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) callableDescriptor : null;
            kotlinTypeEnhanceValueParameter = enhanceValueParameter(d, functionDescriptor != null ? (ValueParameterDescriptor) functionDescriptor.getUserData(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER) : null, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, null, false, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$receiverTypeEnhancement$1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    ReceiverParameterDescriptor extensionReceiverParameter = it.getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    KotlinType type3 = extensionReceiverParameter.getType();
                    Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                    return type3;
                }
            });
        } else {
            kotlinTypeEnhanceValueParameter = null;
        }
        JavaMethodDescriptor javaMethodDescriptor = d instanceof JavaMethodDescriptor ? (JavaMethodDescriptor) d : null;
        if (javaMethodDescriptor != null) {
            SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
            DeclarationDescriptor containingDeclaration = javaMethodDescriptor.getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            String strSignature = MethodSignatureBuildingUtilsKt.signature(signatureBuildingComponents, (ClassDescriptor) containingDeclaration, MethodSignatureMappingKt.computeJvmDescriptor$default(javaMethodDescriptor, false, false, 3, null));
            if (strSignature != null) {
                predefinedFunctionEnhancementInfo = PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE().get(strSignature);
            } else {
                predefinedFunctionEnhancementInfo = null;
            }
        } else {
            predefinedFunctionEnhancementInfo = null;
        }
        if (predefinedFunctionEnhancementInfo != null) {
            predefinedFunctionEnhancementInfo.getParametersInfo().size();
            javaCallableMemberDescriptor.getValueParameters().size();
        }
        boolean z3 = (UtilsKt.isJspecifyEnabledInStrictMode(lazyJavaResolverContext.getComponents().getJavaTypeEnhancementState()) || lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers.getComponents().getSettings().get$ignoreNullabilityForErasedValueParameters()) && UtilsKt.hasErasedValueParameters(d);
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        String str = "getValueParameters(...)";
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        char c = '\n';
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (final ValueParameterDescriptor valueParameterDescriptor : list) {
            ArrayList arrayList2 = arrayList;
            arrayList2.add(enhanceValueParameter(d, valueParameterDescriptor, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, (predefinedFunctionEnhancementInfo == null || (parametersInfo = predefinedFunctionEnhancementInfo.getParametersInfo()) == null) ? typeEnhancementInfo : (TypeEnhancementInfo) CollectionsKt.getOrNull(parametersInfo, valueParameterDescriptor.getIndex()), z3, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$valueParameterEnhancements$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(CallableMemberDescriptor it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    KotlinType type3 = it.getValueParameters().get(valueParameterDescriptor.getIndex()).getType();
                    Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                    return type3;
                }
            }));
            arrayList = arrayList2;
            str = str;
            c = '\n';
            typeEnhancementInfo = null;
        }
        String str2 = str;
        ArrayList arrayList3 = arrayList;
        CallableDescriptor callableDescriptor2 = callableDescriptor;
        PropertyDescriptor propertyDescriptor = d instanceof PropertyDescriptor ? (PropertyDescriptor) d : null;
        if (propertyDescriptor != null && JavaDescriptorUtilKt.isJavaField(propertyDescriptor)) {
            annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.FIELD;
        } else {
            annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE;
        }
        KotlinType kotlinTypeEnhance$default = enhance$default(this, d, callableDescriptor2, true, lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, annotationQualifierApplicabilityType, predefinedFunctionEnhancementInfo != null ? predefinedFunctionEnhancementInfo.getReturnTypeInfo() : null, false, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$returnTypeEnhancement$1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                KotlinType returnType = it.getReturnType();
                Intrinsics.checkNotNull(returnType);
                return returnType;
            }
        }, 32, null);
        KotlinType returnType = javaCallableMemberDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        if (!containsFunctionN(returnType)) {
            ReceiverParameterDescriptor extensionReceiverParameter = javaCallableMemberDescriptor.getExtensionReceiverParameter();
            if (!((extensionReceiverParameter == null || (type2 = extensionReceiverParameter.getType()) == null) ? false : containsFunctionN(type2))) {
                List<ValueParameterDescriptor> valueParameters2 = javaCallableMemberDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters2, str2);
                List<ValueParameterDescriptor> list2 = valueParameters2;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    Iterator<T> it = list2.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        KotlinType type3 = ((ValueParameterDescriptor) it.next()).getType();
                        Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                        if (containsFunctionN(type3)) {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = false;
                    break;
                }
                if (z) {
                    pair = TuplesKt.to(DescriptorBasedDeprecationInfoKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionNInfo(d));
                } else {
                    pair = null;
                }
            } else {
                pair = TuplesKt.to(DescriptorBasedDeprecationInfoKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionNInfo(d));
            }
        } else {
            pair = TuplesKt.to(DescriptorBasedDeprecationInfoKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionNInfo(d));
        }
        if (kotlinTypeEnhanceValueParameter == null && kotlinTypeEnhance$default == null) {
            ArrayList arrayList4 = arrayList3;
            if (!(arrayList4 instanceof Collection) || !arrayList4.isEmpty()) {
                Iterator it2 = arrayList4.iterator();
                do {
                    if (!it2.hasNext()) {
                        z2 = false;
                        break;
                    }
                } while (!(((KotlinType) it2.next()) != null));
            } else {
                z2 = false;
                break;
            }
            if (!z2 && pair == null) {
                return d;
            }
        }
        if (kotlinTypeEnhanceValueParameter == null) {
            ReceiverParameterDescriptor extensionReceiverParameter2 = javaCallableMemberDescriptor.getExtensionReceiverParameter();
            type = extensionReceiverParameter2 != null ? extensionReceiverParameter2.getType() : null;
        } else {
            type = kotlinTypeEnhanceValueParameter;
        }
        ArrayList arrayList5 = arrayList3;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        int i = 0;
        for (Object obj : arrayList5) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType type4 = (KotlinType) obj;
            if (type4 == null) {
                type4 = javaCallableMemberDescriptor.getValueParameters().get(i).getType();
                Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            }
            arrayList6.add(type4);
            i = i2;
        }
        ArrayList arrayList7 = arrayList6;
        if (kotlinTypeEnhance$default == null) {
            kotlinTypeEnhance$default = javaCallableMemberDescriptor.getReturnType();
            Intrinsics.checkNotNull(kotlinTypeEnhance$default);
        }
        JavaCallableMemberDescriptor javaCallableMemberDescriptorEnhance = javaCallableMemberDescriptor.enhance(type, arrayList7, kotlinTypeEnhance$default, pair);
        Intrinsics.checkNotNull(javaCallableMemberDescriptorEnhance, "null cannot be cast to non-null type D of org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement.enhanceSignature");
        return javaCallableMemberDescriptorEnhance;
    }

    public final List<KotlinType> enhanceTypeParameterBounds(TypeParameterDescriptor typeParameter, List<? extends KotlinType> bounds, LazyJavaResolverContext context) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        Intrinsics.checkNotNullParameter(context, "context");
        List<? extends KotlinType> list = bounds;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KotlinType kotlinType : list) {
            if (!TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement$enhanceTypeParameterBounds$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof RawType);
                }
            })) {
                KotlinType kotlinTypeEnhance$default = enhance$default(this, new SignatureParts(typeParameter, false, context, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, false, 16, null), kotlinType, CollectionsKt.emptyList(), null, false, 12, null);
                if (kotlinTypeEnhance$default != null) {
                    kotlinType = kotlinTypeEnhance$default;
                }
            }
            arrayList.add(kotlinType);
        }
        return arrayList;
    }

    public final KotlinType enhanceSuperType(KotlinType type, LazyJavaResolverContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        KotlinType kotlinTypeEnhance$default = enhance$default(this, new SignatureParts(null, false, context, AnnotationQualifierApplicabilityType.TYPE_USE, true), type, CollectionsKt.emptyList(), null, false, 12, null);
        return kotlinTypeEnhance$default == null ? type : kotlinTypeEnhance$default;
    }

    private final boolean containsFunctionN(KotlinType kotlinType) {
        return TypeUtils.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.load.java.typeEnhancement.SignatureEnhancement.containsFunctionN.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                ClassifierDescriptor this$0 = unwrappedType.getConstructor().getThis$0();
                boolean z = false;
                if (this$0 == null) {
                    return false;
                }
                if (Intrinsics.areEqual(this$0.getShortName(), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME().shortName()) && Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(this$0), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME())) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final KotlinType enhanceValueParameter(CallableMemberDescriptor callableMemberDescriptor, ValueParameterDescriptor valueParameterDescriptor, LazyJavaResolverContext lazyJavaResolverContext, TypeEnhancementInfo typeEnhancementInfo, boolean z, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers;
        return enhance(callableMemberDescriptor, valueParameterDescriptor, false, (valueParameterDescriptor == null || (lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, valueParameterDescriptor.getAnnotations())) == null) ? lazyJavaResolverContext : lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, AnnotationQualifierApplicabilityType.VALUE_PARAMETER, typeEnhancementInfo, z, function1);
    }

    static /* synthetic */ KotlinType enhance$default(SignatureEnhancement signatureEnhancement, CallableMemberDescriptor callableMemberDescriptor, Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, TypeEnhancementInfo typeEnhancementInfo, boolean z2, Function1 function1, int i, Object obj) {
        return signatureEnhancement.enhance(callableMemberDescriptor, annotated, z, lazyJavaResolverContext, annotationQualifierApplicabilityType, typeEnhancementInfo, (i & 32) != 0 ? false : z2, function1);
    }

    private final KotlinType enhance(CallableMemberDescriptor callableMemberDescriptor, Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, TypeEnhancementInfo typeEnhancementInfo, boolean z2, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        SignatureParts signatureParts = new SignatureParts(annotated, z, lazyJavaResolverContext, annotationQualifierApplicabilityType, false, 16, null);
        KotlinType kotlinTypeInvoke = function1.invoke(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends CallableMemberDescriptor> collection = overriddenDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (CallableMemberDescriptor callableMemberDescriptor2 : collection) {
            Intrinsics.checkNotNull(callableMemberDescriptor2);
            arrayList.add(function1.invoke(callableMemberDescriptor2));
        }
        return enhance(signatureParts, kotlinTypeInvoke, arrayList, typeEnhancementInfo, z2);
    }

    static /* synthetic */ KotlinType enhance$default(SignatureEnhancement signatureEnhancement, SignatureParts signatureParts, KotlinType kotlinType, List list, TypeEnhancementInfo typeEnhancementInfo, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            typeEnhancementInfo = null;
        }
        TypeEnhancementInfo typeEnhancementInfo2 = typeEnhancementInfo;
        if ((i & 8) != 0) {
            z = false;
        }
        return signatureEnhancement.enhance(signatureParts, kotlinType, list, typeEnhancementInfo2, z);
    }

    private final KotlinType enhance(SignatureParts signatureParts, KotlinType kotlinType, List<? extends KotlinType> list, TypeEnhancementInfo typeEnhancementInfo, boolean z) {
        return this.typeEnhancement.enhance(kotlinType, signatureParts.computeIndexedQualifiers(kotlinType, list, typeEnhancementInfo, z), signatureParts.getSkipRawTypeArguments());
    }
}
