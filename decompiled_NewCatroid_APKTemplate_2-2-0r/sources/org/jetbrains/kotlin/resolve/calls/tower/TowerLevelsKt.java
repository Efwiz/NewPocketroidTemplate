package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScopesKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00022\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002\u001a\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002\u001a\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\t\u001a\u001e\u0010\u000f\u001a\u0004\u0018\u00010\t*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u001a>\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\u001a>\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\u001a\"\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0016*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u001a(\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\"0\u0016*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u001a>\u0010#\u001a\b\u0012\u0004\u0012\u00020 0\u0016*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\u001a$\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016*\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006%"}, d2 = {"canHaveCallableConstructors", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getCanHaveCallableConstructors", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Z", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;)Z", "getClassWithConstructors", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getConstructorsOfClassifier", "", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "getFakeDescriptorForObject", "Lorg/jetbrains/kotlin/resolve/calls/util/FakeCallableDescriptorForObject;", "getClassifierFromMeAndSuperclasses", "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedFunctionsAndConstructors", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "extensionReceiver", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "getContributedFunctionsAndIntercept", "getContributedObjectVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getContributedObjectVariablesIncludeDeprecated", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "getContributedVariablesAndIntercept", "getInnerConstructors", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TowerLevelsKt {
    private static final ClassifierDescriptor getClassifierFromMeAndSuperclasses(KotlinType kotlinType, Name name2, LookupLocation lookupLocation) {
        while (kotlinType != null) {
            ClassifierDescriptor contributedClassifier = kotlinType.getMemberScope().mo7465getContributedClassifier(name2, lookupLocation);
            if (contributedClassifier != null) {
                return contributedClassifier;
            }
            kotlinType = TypeUtilsKt.getImmediateSuperclassNotAny(kotlinType);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<FunctionDescriptor> getInnerConstructors(KotlinType kotlinType, Name name2, LookupLocation lookupLocation) {
        Collection<ClassConstructorDescriptor> constructors;
        ClassDescriptor classWithConstructors = getClassWithConstructors(kotlinType != null ? getClassifierFromMeAndSuperclasses(kotlinType, name2, lookupLocation) : null);
        if (classWithConstructors == null || (constructors = classWithConstructors.getConstructors()) == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : constructors) {
            if (((ClassConstructorDescriptor) obj).mo6753getDispatchReceiverParameter() != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<FunctionDescriptor> getContributedFunctionsAndConstructors(ResolutionScope resolutionScope, Name name2, LookupLocation lookupLocation, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo2, ImplicitScopeTower implicitScopeTower) {
        Collection<? extends FunctionDescriptor> contributedFunctions = resolutionScope.getContributedFunctions(name2, lookupLocation);
        ArrayList arrayList = new ArrayList(contributedFunctions);
        ClassifierDescriptor classifierDescriptorMo7465getContributedClassifier = resolutionScope.mo7465getContributedClassifier(name2, lookupLocation);
        if (classifierDescriptorMo7465getContributedClassifier != null) {
            arrayList.addAll(getConstructorsOfClassifier(classifierDescriptorMo7465getContributedClassifier));
            arrayList.addAll(SyntheticScopesKt.collectSyntheticConstructors(implicitScopeTower.getSyntheticScopes(), classifierDescriptorMo7465getContributedClassifier, lookupLocation));
        }
        if (!contributedFunctions.isEmpty()) {
            arrayList.addAll(SyntheticScopesKt.collectSyntheticStaticFunctions(implicitScopeTower.getSyntheticScopes(), contributedFunctions, lookupLocation));
        }
        return implicitScopeTower.interceptFunctionCandidates(resolutionScope, name2, arrayList, lookupLocation, receiverValueWithSmartCastInfo, receiverValueWithSmartCastInfo2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<VariableDescriptor> getContributedVariablesAndIntercept(ResolutionScope resolutionScope, Name name2, LookupLocation lookupLocation, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo2, ImplicitScopeTower implicitScopeTower) {
        return implicitScopeTower.interceptVariableCandidates(resolutionScope, name2, resolutionScope.getContributedVariables(name2, lookupLocation), lookupLocation, receiverValueWithSmartCastInfo, receiverValueWithSmartCastInfo2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<FunctionDescriptor> getContributedFunctionsAndIntercept(ResolutionScope resolutionScope, Name name2, LookupLocation lookupLocation, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo, ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo2, ImplicitScopeTower implicitScopeTower) {
        return implicitScopeTower.interceptFunctionCandidates(resolutionScope, name2, resolutionScope.getContributedFunctions(name2, lookupLocation), lookupLocation, receiverValueWithSmartCastInfo, receiverValueWithSmartCastInfo2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<ConstructorDescriptor> getConstructorsOfClassifier(ClassifierDescriptor classifierDescriptor) {
        List listEmptyList;
        if (classifierDescriptor instanceof TypeAliasDescriptor) {
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) classifierDescriptor;
            listEmptyList = getCanHaveCallableConstructors(typeAliasDescriptor) ? typeAliasDescriptor.getConstructors() : CollectionsKt.emptyList();
        } else if (classifierDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptor;
            if (getCanHaveCallableConstructors(classDescriptor)) {
                listEmptyList = classDescriptor.getConstructors();
                Intrinsics.checkNotNullExpressionValue(listEmptyList, "getConstructors(...)");
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : listEmptyList) {
            if (((ConstructorDescriptor) obj).mo6753getDispatchReceiverParameter() == null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<VariableDescriptor> getContributedObjectVariables(ResolutionScope resolutionScope, Name name2, LookupLocation lookupLocation) {
        return CollectionsKt.listOfNotNull(getFakeDescriptorForObject(resolutionScope.mo7465getContributedClassifier(name2, lookupLocation)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<DescriptorWithDeprecation<VariableDescriptor>> getContributedObjectVariablesIncludeDeprecated(ResolutionScope resolutionScope, Name name2, LookupLocation lookupLocation) {
        DescriptorWithDeprecation<ClassifierDescriptor> contributedClassifierIncludeDeprecated = resolutionScope.getContributedClassifierIncludeDeprecated(name2, lookupLocation);
        if (contributedClassifierIncludeDeprecated == null) {
            return CollectionsKt.emptyList();
        }
        ClassifierDescriptor classifierDescriptor = (ClassifierDescriptor) contributedClassifierIncludeDeprecated.component1();
        boolean isDeprecated = contributedClassifierIncludeDeprecated.getIsDeprecated();
        FakeCallableDescriptorForObject fakeDescriptorForObject = getFakeDescriptorForObject(classifierDescriptor);
        return fakeDescriptorForObject == null ? CollectionsKt.emptyList() : CollectionsKt.listOf(new DescriptorWithDeprecation(fakeDescriptorForObject, isDeprecated));
    }

    public static final FakeCallableDescriptorForObject getFakeDescriptorForObject(ClassifierDescriptor classifierDescriptor) {
        FakeCallableDescriptorForTypeAliasObject fakeCallableDescriptorForTypeAliasObject = null;
        if (classifierDescriptor instanceof TypeAliasDescriptor) {
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) classifierDescriptor;
            ClassDescriptor classDescriptor = typeAliasDescriptor.getClassDescriptor();
            if (classDescriptor != null && DescriptorUtilsKt.getHasClassValueDescriptor(classDescriptor)) {
                fakeCallableDescriptorForTypeAliasObject = new FakeCallableDescriptorForTypeAliasObject(typeAliasDescriptor);
            }
            return fakeCallableDescriptorForTypeAliasObject;
        }
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            return null;
        }
        ClassDescriptor classDescriptor2 = (ClassDescriptor) classifierDescriptor;
        if (DescriptorUtilsKt.getHasClassValueDescriptor(classDescriptor2)) {
            return new FakeCallableDescriptorForObject(classDescriptor2);
        }
        return null;
    }

    private static final ClassDescriptor getClassWithConstructors(ClassifierDescriptor classifierDescriptor) {
        if (classifierDescriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptor;
            if (getCanHaveCallableConstructors(classDescriptor)) {
                return classDescriptor;
            }
        }
        return null;
    }

    private static final boolean getCanHaveCallableConstructors(ClassDescriptor classDescriptor) {
        return (ErrorUtils.isError(classDescriptor) || classDescriptor.getKind().isSingleton()) ? false : true;
    }

    private static final boolean getCanHaveCallableConstructors(TypeAliasDescriptor typeAliasDescriptor) {
        if (typeAliasDescriptor.getClassDescriptor() != null && !ErrorUtils.isError(typeAliasDescriptor.getClassDescriptor())) {
            ClassDescriptor classDescriptor = typeAliasDescriptor.getClassDescriptor();
            Intrinsics.checkNotNull(classDescriptor);
            if (getCanHaveCallableConstructors(classDescriptor)) {
                return true;
            }
        }
        return false;
    }
}
