package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: sinceKotlinUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\u0001*\u00020\u0006H\u0002\u001a\u0012\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\u00020\u0006H\u0002¨\u0006\r"}, d2 = {"getSinceKotlinVersionByOverridden", "Lorg/jetbrains/kotlin/resolve/SinceKotlinValue;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "checkSinceKotlinVersionAccessibility", "Lorg/jetbrains/kotlin/resolve/SinceKotlinAccessibility;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getOwnSinceKotlinVersion", "loadWasExperimentalMarkerClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SinceKotlinUtilKt {
    /* JADX WARN: Code duplicated, block: B:7:0x0020  */
    public static final SinceKotlinAccessibility checkSinceKotlinVersionAccessibility(DeclarationDescriptor declarationDescriptor, LanguageVersionSettings languageVersionSettings) {
        SinceKotlinValue ownSinceKotlinVersion;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
            if (callableMemberDescriptor.getKind().isReal()) {
                ownSinceKotlinVersion = getOwnSinceKotlinVersion(declarationDescriptor);
            } else {
                ownSinceKotlinVersion = getSinceKotlinVersionByOverridden(callableMemberDescriptor);
            }
        } else {
            ownSinceKotlinVersion = getOwnSinceKotlinVersion(declarationDescriptor);
        }
        ApiVersion apiVersion = ownSinceKotlinVersion != null ? ownSinceKotlinVersion.getApiVersion() : null;
        if (apiVersion == null || apiVersion.compareTo(languageVersionSettings.getApiVersion()) <= 0) {
            return SinceKotlinAccessibility.Accessible.INSTANCE;
        }
        List<ClassDescriptor> wasExperimentalMarkerClasses = ownSinceKotlinVersion.getWasExperimentalMarkerClasses();
        if (!wasExperimentalMarkerClasses.isEmpty()) {
            return new SinceKotlinAccessibility.NotAccessibleButWasExperimental(apiVersion, wasExperimentalMarkerClasses);
        }
        return new SinceKotlinAccessibility.NotAccessible(apiVersion);
    }

    private static final SinceKotlinValue getSinceKotlinVersionByOverridden(CallableMemberDescriptor callableMemberDescriptor) {
        Set allOverriddenDeclarations = DescriptorUtils.getAllOverriddenDeclarations(callableMemberDescriptor);
        Intrinsics.checkNotNullExpressionValue(allOverriddenDeclarations, "getAllOverriddenDeclarations(...)");
        Set set = allOverriddenDeclarations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator it = set.iterator();
        while (true) {
            Object next = null;
            if (it.hasNext()) {
                CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) it.next();
                Intrinsics.checkNotNull(callableMemberDescriptor2);
                SinceKotlinValue ownSinceKotlinVersion = getOwnSinceKotlinVersion(callableMemberDescriptor2);
                if (ownSinceKotlinVersion == null) {
                    return null;
                }
                arrayList.add(ownSinceKotlinVersion);
            } else {
                Iterator it2 = arrayList.iterator();
                if (it2.hasNext()) {
                    next = it2.next();
                    if (it2.hasNext()) {
                        ApiVersion apiVersion = ((SinceKotlinValue) next).getApiVersion();
                        do {
                            Object next2 = it2.next();
                            ApiVersion apiVersion2 = ((SinceKotlinValue) next2).getApiVersion();
                            if (apiVersion.compareTo(apiVersion2) > 0) {
                                next = next2;
                                apiVersion = apiVersion2;
                            }
                        } while (it2.hasNext());
                    }
                }
                return (SinceKotlinValue) next;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final SinceKotlinValue getOwnSinceKotlinVersion(DeclarationDescriptor declarationDescriptor) {
        ClassDescriptor referencedObject;
        ClassConstructorDescriptor underlyingConstructorDescriptor;
        ClassDescriptor classDescriptor;
        PropertyDescriptor correspondingProperty;
        ClassifierDescriptorWithTypeParameters containingDeclaration;
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        getOwnSinceKotlinVersion$consider(declarationDescriptor, objectRef);
        ConstructorDescriptor constructorDescriptor = declarationDescriptor instanceof ConstructorDescriptor ? (ConstructorDescriptor) declarationDescriptor : null;
        if (constructorDescriptor != null && (containingDeclaration = constructorDescriptor.getContainingDeclaration()) != null) {
            getOwnSinceKotlinVersion$consider(containingDeclaration, objectRef);
        }
        PropertyAccessorDescriptor propertyAccessorDescriptor = declarationDescriptor instanceof PropertyAccessorDescriptor ? (PropertyAccessorDescriptor) declarationDescriptor : null;
        if (propertyAccessorDescriptor != null && (correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty()) != null) {
            getOwnSinceKotlinVersion$consider(correspondingProperty, objectRef);
        }
        TypeAliasDescriptor typeAliasDescriptor = declarationDescriptor instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) declarationDescriptor : null;
        if (typeAliasDescriptor == null) {
            TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = declarationDescriptor instanceof TypeAliasConstructorDescriptor ? (TypeAliasConstructorDescriptor) declarationDescriptor : null;
            typeAliasDescriptor = typeAliasConstructorDescriptor != null ? typeAliasConstructorDescriptor.getTypeAliasDescriptor() : null;
            if (typeAliasDescriptor == null) {
                FakeCallableDescriptorForTypeAliasObject fakeCallableDescriptorForTypeAliasObject = declarationDescriptor instanceof FakeCallableDescriptorForTypeAliasObject ? (FakeCallableDescriptorForTypeAliasObject) declarationDescriptor : null;
                typeAliasDescriptor = fakeCallableDescriptorForTypeAliasObject != null ? fakeCallableDescriptorForTypeAliasObject.getTypeAliasDescriptor() : null;
            }
        }
        if (typeAliasDescriptor != null) {
            getOwnSinceKotlinVersion$consider(typeAliasDescriptor, objectRef);
        }
        if (typeAliasDescriptor != null && (classDescriptor = typeAliasDescriptor.getClassDescriptor()) != null) {
            getOwnSinceKotlinVersion$consider(classDescriptor, objectRef);
        }
        TypeAliasConstructorDescriptor typeAliasConstructorDescriptor2 = declarationDescriptor instanceof TypeAliasConstructorDescriptor ? (TypeAliasConstructorDescriptor) declarationDescriptor : null;
        if (typeAliasConstructorDescriptor2 != null && (underlyingConstructorDescriptor = typeAliasConstructorDescriptor2.getUnderlyingConstructorDescriptor()) != null) {
            getOwnSinceKotlinVersion$consider(underlyingConstructorDescriptor, objectRef);
        }
        FakeCallableDescriptorForTypeAliasObject fakeCallableDescriptorForTypeAliasObject2 = declarationDescriptor instanceof FakeCallableDescriptorForTypeAliasObject ? (FakeCallableDescriptorForTypeAliasObject) declarationDescriptor : null;
        if (fakeCallableDescriptorForTypeAliasObject2 != null && (referencedObject = fakeCallableDescriptorForTypeAliasObject2.getReferencedObject()) != null) {
            getOwnSinceKotlinVersion$consider(referencedObject, objectRef);
        }
        return (SinceKotlinValue) objectRef.element;
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [T, org.jetbrains.kotlin.resolve.SinceKotlinValue] */
    private static final void getOwnSinceKotlinVersion$consider(DeclarationDescriptor declarationDescriptor, Ref.ObjectRef<SinceKotlinValue> objectRef) {
        Map<Name, ConstantValue<?>> allValueArguments;
        Collection<ConstantValue<?>> collectionValues;
        ConstantValue constantValue;
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = declarationDescriptor.getAnnotations().mo6752findAnnotation(AnnotationsKt.getSINCE_KOTLIN_FQ_NAME());
        Object value = (annotationDescriptorMo6752findAnnotation == null || (allValueArguments = annotationDescriptorMo6752findAnnotation.getAllValueArguments()) == null || (collectionValues = allValueArguments.values()) == null || (constantValue = (ConstantValue) CollectionsKt.singleOrNull(collectionValues)) == null) ? null : constantValue.getValue();
        String str = value instanceof String ? (String) value : null;
        ApiVersion apiVersion = str != null ? ApiVersion.INSTANCE.parse(str) : null;
        if (apiVersion != null) {
            if (objectRef.element != null) {
                SinceKotlinValue sinceKotlinValue = objectRef.element;
                Intrinsics.checkNotNull(sinceKotlinValue);
                if (apiVersion.compareTo(sinceKotlinValue.getApiVersion()) <= 0) {
                    return;
                }
            }
            objectRef.element = new SinceKotlinValue(apiVersion, loadWasExperimentalMarkerClasses(declarationDescriptor));
        }
    }

    private static final List<ClassDescriptor> loadWasExperimentalMarkerClasses(DeclarationDescriptor declarationDescriptor) {
        KotlinType argumentType;
        TypeConstructor constructor;
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = declarationDescriptor.getAnnotations().mo6752findAnnotation(OptInNames.INSTANCE.getWAS_EXPERIMENTAL_FQ_NAME());
        if (annotationDescriptorMo6752findAnnotation != null) {
            ConstantValue<?> constantValue = annotationDescriptorMo6752findAnnotation.getAllValueArguments().get(OptInNames.INSTANCE.getWAS_EXPERIMENTAL_ANNOTATION_CLASS());
            if (constantValue instanceof ArrayValue) {
                List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = value.iterator();
                while (it.hasNext()) {
                    ConstantValue constantValue2 = (ConstantValue) it.next();
                    KClassValue kClassValue = constantValue2 instanceof KClassValue ? (KClassValue) constantValue2 : null;
                    ClassifierDescriptor this$0 = (kClassValue == null || (argumentType = kClassValue.getArgumentType(DescriptorUtilsKt.getModule(declarationDescriptor))) == null || (constructor = argumentType.getConstructor()) == null) ? null : constructor.getThis$0();
                    ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                    if (classDescriptor != null) {
                        arrayList.add(classDescriptor);
                    }
                }
                return arrayList;
            }
        }
        return CollectionsKt.emptyList();
    }
}
