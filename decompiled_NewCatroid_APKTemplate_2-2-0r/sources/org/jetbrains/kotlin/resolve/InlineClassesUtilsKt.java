package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;

/* JADX INFO: compiled from: inlineClassesUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0003\u001a\n\u0010\b\u001a\u00020\t*\u00020\n\u001a\n\u0010\u000b\u001a\u00020\t*\u00020\n\u001a\n\u0010\f\u001a\u00020\t*\u00020\n\u001a\n\u0010\r\u001a\u00020\t*\u00020\u000e\u001a\n\u0010\u000f\u001a\u00020\t*\u00020\u0010\u001a\n\u0010\u0011\u001a\u00020\t*\u00020\u000e\u001a\n\u0010\u0012\u001a\u00020\t*\u00020\u0010\u001a\n\u0010\u0013\u001a\u00020\t*\u00020\u0010\u001a\n\u0010\u0014\u001a\u00020\t*\u00020\u0010\u001a$\u0010\u0015\u001a\u00020\t*\u00020\u00102\u0016\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019H\u0002\u001a\n\u0010\u001a\u001a\u00020\t*\u00020\u001b\u001a\n\u0010\u001c\u001a\u00020\t*\u00020\u001b\u001a\n\u0010\u001d\u001a\u00020\t*\u00020\u001b\u001a\n\u0010\u001e\u001a\u00020\t*\u00020\u000e\u001a\n\u0010\u001f\u001a\u00020\t*\u00020\u0010\u001a\n\u0010 \u001a\u00020\t*\u00020\u0010\u001a\f\u0010!\u001a\u0004\u0018\u00010\u0010*\u00020\u0010\u001a\u0012\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100#*\u00020\u0010\u001a\f\u0010$\u001a\u0004\u0018\u00010\u0010*\u00020\u0010\u001a\u0010\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100#*\u00020\u0010\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006&"}, d2 = {"JVM_INLINE_ANNOTATION_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getJVM_INLINE_ANNOTATION_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", "JVM_INLINE_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getJVM_INLINE_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "isGetterOfUnderlyingPropertyOfInlineClass", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isGetterOfUnderlyingPropertyOfMultiFieldValueClass", "isGetterOfUnderlyingPropertyOfValueClass", "isInlineClass", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isInlineClassType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isMultiFieldValueClass", "isMultiFieldValueClassType", "isNullableUnderlyingType", "isRecursiveInlineOrValueClassType", "isRecursiveInlineOrValueClassTypeInner", "visited", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "Lkotlin/collections/HashSet;", "isUnderlyingPropertyOfInlineClass", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "isUnderlyingPropertyOfMultiFieldValueClass", "isUnderlyingPropertyOfValueClass", "isValueClass", "isValueClassType", "needsMfvcFlattening", "substitutedUnderlyingType", "substitutedUnderlyingTypes", "", "unsubstitutedUnderlyingType", "unsubstitutedUnderlyingTypes", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassesUtilsKt {
    private static final ClassId JVM_INLINE_ANNOTATION_CLASS_ID;
    private static final FqName JVM_INLINE_ANNOTATION_FQ_NAME;

    static {
        FqName fqName = new FqName("kotlin.jvm.JvmInline");
        JVM_INLINE_ANNOTATION_FQ_NAME = fqName;
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        JVM_INLINE_ANNOTATION_CLASS_ID = classId;
    }

    public static final FqName getJVM_INLINE_ANNOTATION_FQ_NAME() {
        return JVM_INLINE_ANNOTATION_FQ_NAME;
    }

    public static final ClassId getJVM_INLINE_ANNOTATION_CLASS_ID() {
        return JVM_INLINE_ANNOTATION_CLASS_ID;
    }

    public static final boolean isInlineClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof ClassDescriptor) && (((ClassDescriptor) declarationDescriptor).getValueClassRepresentation() instanceof InlineClassRepresentation);
    }

    public static final boolean isMultiFieldValueClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof ClassDescriptor) && (((ClassDescriptor) declarationDescriptor).getValueClassRepresentation() instanceof MultiFieldValueClassRepresentation);
    }

    public static final boolean isValueClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return isInlineClass(declarationDescriptor) || isMultiFieldValueClass(declarationDescriptor);
    }

    public static final KotlinType unsubstitutedUnderlyingType(KotlinType kotlinType) {
        InlineClassRepresentation<SimpleType> inlineClassRepresentation;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        SimpleType simpleType = null;
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor != null && (inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(classDescriptor)) != null) {
            simpleType = (SimpleType) inlineClassRepresentation.getUnderlyingType();
        }
        return simpleType;
    }

    public static final List<KotlinType> unsubstitutedUnderlyingTypes(KotlinType kotlinType) {
        List<ValueParameterDescriptor> valueParameters;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (isInlineClass(classDescriptor2)) {
            return CollectionsKt.listOfNotNull(unsubstitutedUnderlyingType(kotlinType));
        }
        if (isMultiFieldValueClass(classDescriptor2)) {
            ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
            if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null || (valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters()) == null) {
                return CollectionsKt.emptyList();
            }
            List<ValueParameterDescriptor> list = valueParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                arrayList.add(type);
            }
            return arrayList;
        }
        return CollectionsKt.emptyList();
    }

    public static final boolean isInlineClassType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null) {
            return isInlineClass(this$0);
        }
        return false;
    }

    public static final boolean isMultiFieldValueClassType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null) {
            return isMultiFieldValueClass(this$0);
        }
        return false;
    }

    public static final boolean isValueClassType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null) {
            return isValueClass(this$0);
        }
        return false;
    }

    public static final boolean needsMfvcFlattening(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        return (this$0 == null || !isMultiFieldValueClass(this$0) || SimpleClassicTypeSystemContext.INSTANCE.isNullableType(kotlinType)) ? false : true;
    }

    public static final KotlinType substitutedUnderlyingType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinType kotlinTypeUnsubstitutedUnderlyingType = unsubstitutedUnderlyingType(kotlinType);
        if (kotlinTypeUnsubstitutedUnderlyingType != null) {
            return TypeSubstitutor.create(kotlinType).substitute(kotlinTypeUnsubstitutedUnderlyingType, Variance.INVARIANT);
        }
        return null;
    }

    public static final List<KotlinType> substitutedUnderlyingTypes(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        List<KotlinType> listUnsubstitutedUnderlyingTypes = unsubstitutedUnderlyingTypes(kotlinType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listUnsubstitutedUnderlyingTypes, 10));
        Iterator<T> it = listUnsubstitutedUnderlyingTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(TypeSubstitutor.create(kotlinType).substitute((KotlinType) it.next(), Variance.INVARIANT));
        }
        return arrayList;
    }

    public static final boolean isRecursiveInlineOrValueClassType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isRecursiveInlineOrValueClassTypeInner(kotlinType, new HashSet());
    }

    private static final boolean isRecursiveInlineOrValueClassTypeInner(KotlinType kotlinType, HashSet<ClassifierDescriptor> hashSet) {
        List<KotlinType> listEmptyList;
        ClassifierDescriptor original;
        ClassifierDescriptor original2;
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        ClassifierDescriptor classifierDescriptor = null;
        if (this$0 != null && (original2 = this$0.getOriginal()) != null && isValueClass(original2)) {
            classifierDescriptor = original2;
        }
        if (classifierDescriptor instanceof ClassDescriptor) {
            listEmptyList = isValueClass(classifierDescriptor) ? unsubstitutedUnderlyingTypes(kotlinType) : CollectionsKt.emptyList();
        } else if (classifierDescriptor instanceof TypeParameterDescriptor) {
            listEmptyList = ((TypeParameterDescriptor) classifierDescriptor).getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "getUpperBounds(...)");
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List<KotlinType> list = listEmptyList;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (KotlinType kotlinType2 : list) {
            ClassifierDescriptor this$1 = kotlinType2.getConstructor().getThis$0();
            if (this$1 != null && (original = this$1.getOriginal()) != null) {
                Intrinsics.checkNotNull(original);
                if (hashSet.add(original)) {
                    boolean zIsRecursiveInlineOrValueClassTypeInner = isRecursiveInlineOrValueClassTypeInner(kotlinType2, hashSet);
                    hashSet.remove(original);
                    if (zIsRecursiveInlineOrValueClassTypeInner) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static final boolean isNullableUnderlyingType(KotlinType kotlinType) {
        KotlinType kotlinTypeUnsubstitutedUnderlyingType;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (isInlineClassType(kotlinType) && (kotlinTypeUnsubstitutedUnderlyingType = unsubstitutedUnderlyingType(kotlinType)) != null) {
            return TypeUtils.isNullableType(kotlinTypeUnsubstitutedUnderlyingType);
        }
        return false;
    }

    public static final boolean isGetterOfUnderlyingPropertyOfInlineClass(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (callableDescriptor instanceof PropertyGetterDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) callableDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            if (isUnderlyingPropertyOfInlineClass(correspondingProperty)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isGetterOfUnderlyingPropertyOfMultiFieldValueClass(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (callableDescriptor instanceof PropertyGetterDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) callableDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            if (isUnderlyingPropertyOfMultiFieldValueClass(correspondingProperty)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isGetterOfUnderlyingPropertyOfValueClass(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        if (callableDescriptor instanceof PropertyGetterDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyGetterDescriptor) callableDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            if (isUnderlyingPropertyOfValueClass(correspondingProperty)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isUnderlyingPropertyOfInlineClass(VariableDescriptor variableDescriptor) {
        InlineClassRepresentation<SimpleType> inlineClassRepresentation;
        Intrinsics.checkNotNullParameter(variableDescriptor, "<this>");
        if (variableDescriptor.getExtensionReceiverParameter() == null) {
            DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
            Name underlyingPropertyName = null;
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor != null && (inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(classDescriptor)) != null) {
                underlyingPropertyName = inlineClassRepresentation.getUnderlyingPropertyName();
            }
            if (Intrinsics.areEqual(underlyingPropertyName, variableDescriptor.getShortName())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isUnderlyingPropertyOfMultiFieldValueClass(VariableDescriptor variableDescriptor) {
        MultiFieldValueClassRepresentation<SimpleType> multiFieldValueClassRepresentation;
        Intrinsics.checkNotNullParameter(variableDescriptor, "<this>");
        if (variableDescriptor.getExtensionReceiverParameter() == null) {
            DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor != null && (multiFieldValueClassRepresentation = DescriptorUtilsKt.getMultiFieldValueClassRepresentation(classDescriptor)) != null) {
                Name name2 = variableDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (multiFieldValueClassRepresentation.containsPropertyWithName(name2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isUnderlyingPropertyOfValueClass(VariableDescriptor variableDescriptor) {
        ValueClassRepresentation<SimpleType> valueClassRepresentation;
        Intrinsics.checkNotNullParameter(variableDescriptor, "<this>");
        if (variableDescriptor.getExtensionReceiverParameter() == null) {
            DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingDeclaration();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor != null && (valueClassRepresentation = classDescriptor.getValueClassRepresentation()) != null) {
                Name name2 = variableDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (valueClassRepresentation.containsPropertyWithName(name2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
