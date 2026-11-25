package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: descriptorUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\u0012\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0014\u0010\b\u001a\u0004\u0018\u00010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f\u001a\f\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0002\u001a\u001b\u0010\u000f\u001a\u00020\u0010*\u00020\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a\n\u0010\u0011\u001a\u00020\u0010*\u00020\u0012\u001a\n\u0010\u0013\u001a\u00020\u0010*\u00020\u0002\u001a\u001a\u0010\u0013\u001a\u00020\u0010*\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015\u001a\n\u0010\u0017\u001a\u00020\u0010*\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u0010*\u00020\u0018\u001a\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0004*\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007ò\u0001\u0004\n\u00020\u0004¨\u0006\u001c"}, d2 = {"containingPackage", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "findContinuationClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "lookupLocation", "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "findContinuationClassDescriptorOrNull", "getContinuationOfTypeOrAny", "Lorg/jetbrains/kotlin/types/SimpleType;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTopLevelContainingClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "isSealed", "", "isSupportedForCallableReference", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isTopLevelInPackage", "name", "", "packageName", "isTypedEqualsInValueClass", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "overridesEqualsFromAny", "resolveClassByFqName", "fqName", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorUtilKt {
    public static final ClassDescriptor resolveClassByFqName(ModuleDescriptor moduleDescriptor, FqName fqName, LookupLocation lookupLocation) {
        ClassifierDescriptor contributedClassifier;
        MemberScope unsubstitutedInnerClassesScope;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        if (fqName.isRoot()) {
            return null;
        }
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = moduleDescriptor.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        ClassifierDescriptor contributedClassifier2 = memberScope.mo7465getContributedClassifier(nameShortName, lookupLocation);
        ClassDescriptor classDescriptor = contributedClassifier2 instanceof ClassDescriptor ? (ClassDescriptor) contributedClassifier2 : null;
        if (classDescriptor != null) {
            return classDescriptor;
        }
        FqName fqNameParent2 = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
        ClassDescriptor classDescriptorResolveClassByFqName = resolveClassByFqName(moduleDescriptor, fqNameParent2, lookupLocation);
        if (classDescriptorResolveClassByFqName == null || (unsubstitutedInnerClassesScope = classDescriptorResolveClassByFqName.getUnsubstitutedInnerClassesScope()) == null) {
            contributedClassifier = null;
        } else {
            Name nameShortName2 = fqName.shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName2, "shortName(...)");
            contributedClassifier = unsubstitutedInnerClassesScope.mo7465getContributedClassifier(nameShortName2, lookupLocation);
        }
        if (contributedClassifier instanceof ClassDescriptor) {
            return (ClassDescriptor) contributedClassifier;
        }
        return null;
    }

    public static final ClassDescriptor findContinuationClassDescriptorOrNull(ModuleDescriptor moduleDescriptor, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        return resolveClassByFqName(moduleDescriptor, StandardNames.CONTINUATION_INTERFACE_FQ_NAME, lookupLocation);
    }

    public static final ClassDescriptor findContinuationClassDescriptor(ModuleDescriptor moduleDescriptor, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        ClassDescriptor classDescriptorFindContinuationClassDescriptorOrNull = findContinuationClassDescriptorOrNull(moduleDescriptor, lookupLocation);
        if (classDescriptorFindContinuationClassDescriptorOrNull != null) {
            return classDescriptorFindContinuationClassDescriptorOrNull;
        }
        throw new AssertionError("Continuation interface is not found");
    }

    public static final SimpleType getContinuationOfTypeOrAny(ModuleDescriptor moduleDescriptor, KotlinType kotlinType) {
        SimpleType defaultType;
        SimpleType simpleTypeSimpleType$default;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        ModuleDescriptor moduleDescriptor2 = moduleDescriptor;
        ClassDescriptor classDescriptorFindContinuationClassDescriptorOrNull = findContinuationClassDescriptorOrNull(DescriptorUtilsKt.getModule(moduleDescriptor2), NoLookupLocation.FROM_DESERIALIZATION);
        if (classDescriptorFindContinuationClassDescriptorOrNull != null && (defaultType = classDescriptorFindContinuationClassDescriptorOrNull.getDefaultType()) != null && (simpleTypeSimpleType$default = KotlinTypeFactory.simpleType$default(defaultType, (TypeAttributes) null, (TypeConstructor) null, CollectionsKt.listOf(TypeUtilsKt.asTypeProjection(kotlinType)), false, 22, (Object) null)) != null) {
            return simpleTypeSimpleType$default;
        }
        SimpleType nullableAnyType = DescriptorUtilsKt.getModule(moduleDescriptor2).getBuiltIns().getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
        return nullableAnyType;
    }

    public static final boolean isTopLevelInPackage(DeclarationDescriptor declarationDescriptor, String name2, String packageName) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (!Intrinsics.areEqual(name2, declarationDescriptor.getShortName().asString())) {
            return false;
        }
        DeclarationDescriptor moduleDescriptor = declarationDescriptor.getContainingModule();
        PackageFragmentDescriptor packageFragmentDescriptor = moduleDescriptor instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) moduleDescriptor : null;
        if (packageFragmentDescriptor == null) {
            return false;
        }
        String strAsString = packageFragmentDescriptor.getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return Intrinsics.areEqual(packageName, strAsString);
    }

    public static final boolean isTopLevelInPackage(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return declarationDescriptor.getContainingModule() instanceof PackageFragmentDescriptor;
    }

    public static final ClassifierDescriptor getTopLevelContainingClassifier(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor moduleDescriptor = declarationDescriptor.getContainingModule();
        if (moduleDescriptor == null || (declarationDescriptor instanceof PackageFragmentDescriptor)) {
            return null;
        }
        if (!isTopLevelInPackage(moduleDescriptor)) {
            return getTopLevelContainingClassifier(moduleDescriptor);
        }
        if (moduleDescriptor instanceof ClassifierDescriptor) {
            return (ClassifierDescriptor) moduleDescriptor;
        }
        return null;
    }

    public static final boolean isSupportedForCallableReference(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return (callableDescriptor instanceof PropertyDescriptor) || (callableDescriptor instanceof FunctionDescriptor);
    }

    public static final boolean isSealed(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return DescriptorUtils.isSealedClass(declarationDescriptor);
    }

    public static final FqName containingPackage(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        DeclarationDescriptor moduleDescriptor = declarationDescriptor.getContainingModule();
        while (moduleDescriptor != null && !(moduleDescriptor instanceof PackageFragmentDescriptor)) {
            moduleDescriptor = moduleDescriptor.getContainingModule();
        }
        if (!(moduleDescriptor == null ? true : moduleDescriptor instanceof PackageFragmentDescriptor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) moduleDescriptor;
        if (packageFragmentDescriptor != null) {
            return packageFragmentDescriptor.getFqName();
        }
        return null;
    }

    public static final boolean isTypedEqualsInValueClass(FunctionDescriptor functionDescriptor) {
        SimpleType defaultType;
        KotlinType kotlinTypeReplaceArgumentsWithStarProjections;
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        DeclarationDescriptor moduleDescriptor = functionDescriptor.getContainingModule();
        ClassDescriptor classDescriptor = moduleDescriptor instanceof ClassDescriptor ? (ClassDescriptor) moduleDescriptor : null;
        if (classDescriptor == null) {
            return false;
        }
        ClassDescriptor classDescriptor2 = InlineClassesUtilsKt.isValueClass(classDescriptor) ? classDescriptor : null;
        if (classDescriptor2 == null || (defaultType = classDescriptor2.getDefaultType()) == null || (kotlinTypeReplaceArgumentsWithStarProjections = TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType)) == null || (returnType = functionDescriptor.getReturnType()) == null || !Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.EQUALS)) {
            return false;
        }
        if ((!TypeUtilsKt.isBoolean(returnType) && !TypeUtilsKt.isNothing(returnType)) || functionDescriptor.getValueParameters().size() != 1) {
            return false;
        }
        KotlinType type = functionDescriptor.getValueParameters().get(0).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return Intrinsics.areEqual(TypeUtilsKt.replaceArgumentsWithStarProjections(type), kotlinTypeReplaceArgumentsWithStarProjections) && functionDescriptor.getContextReceiverParameters().isEmpty() && functionDescriptor.getExtensionReceiverParameter() == null;
    }

    public static final boolean overridesEqualsFromAny(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        if (!Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.EQUALS) || functionDescriptor.getValueParameters().size() != 1) {
            return false;
        }
        KotlinType type = functionDescriptor.getValueParameters().get(0).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return TypeUtilsKt.isNullableAny(type) && functionDescriptor.getContextReceiverParameters().isEmpty() && functionDescriptor.getExtensionReceiverParameter() == null;
    }
}
