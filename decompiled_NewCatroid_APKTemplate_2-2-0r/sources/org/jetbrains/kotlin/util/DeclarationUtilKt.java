package org.jetbrains.kotlin.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.CfgContainingDeclarationUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: declarationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0006\u001a\u001c\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0007\u001a\u0018\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006\u001a\u001a\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u0001\u001a$\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\r2\u0006\u0010\u0007\u001a\u00020\u000e2\b\b\u0002\u0010\t\u001a\u00020\u0001\u001a\f\u0010\u000f\u001a\u0004\u0018\u00010\u0002*\u00020\u0010\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003¨\u0006\u0011"}, d2 = {"isAnnotated", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "(Lorg/jetbrains/kotlin/psi/KtDeclaration;)Z", "isOrdinaryClass", "findImplementationFromInterface", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptor", "findInterfaceImplementation", "returnImplNotDelegate", "firstSuperMethodFromKotlin", "implementation", "getNonPrivateTraitMembersForDelegation", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "containingNonLocalDeclaration", "Lorg/jetbrains/kotlin/psi/KtElement;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationUtilKt {
    public static final CallableMemberDescriptor findInterfaceImplementation(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return findInterfaceImplementation$default(descriptor, false, 2, null);
    }

    public static final KtDeclaration containingNonLocalDeclaration(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        KtDeclaration containingDeclarationForPseudocode = CfgContainingDeclarationUtilsKt.getContainingDeclarationForPseudocode(ktElement);
        while (containingDeclarationForPseudocode != null && KtPsiUtil.isLocal(containingDeclarationForPseudocode)) {
            containingDeclarationForPseudocode = CfgContainingDeclarationUtilsKt.getContainingDeclarationForPseudocode(containingDeclarationForPseudocode);
        }
        return containingDeclarationForPseudocode;
    }

    public static final boolean isOrdinaryClass(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        if ((ktDeclaration instanceof KtClass) && !ktDeclaration.hasModifier(KtTokens.INLINE_KEYWORD)) {
            KtClass ktClass = (KtClass) ktDeclaration;
            if (!ktClass.isAnnotation() && !ktClass.isInterface()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isAnnotated(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        List<KtAnnotationEntry> annotationEntries = ktDeclaration.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        return !annotationEntries.isEmpty();
    }

    public static final CallableMemberDescriptor findImplementationFromInterface(CallableMemberDescriptor descriptor) {
        Object next;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Set<CallableMemberDescriptor> overriddenDeclarations = OverridingUtil.getOverriddenDeclarations(descriptor);
        Intrinsics.checkNotNullExpressionValue(overriddenDeclarations, "getOverriddenDeclarations(...)");
        Set setFilterOutOverridden = OverridingUtil.filterOutOverridden(overriddenDeclarations);
        Intrinsics.checkNotNullExpressionValue(setFilterOutOverridden, "filterOutOverridden(...)");
        Iterator it = setFilterOutOverridden.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((CallableMemberDescriptor) next).getModality() == Modality.ABSTRACT);
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) next;
        if (callableMemberDescriptor == null || DescriptorUtils.isClassOrEnumClass(callableMemberDescriptor.getContainingModule())) {
            return null;
        }
        return callableMemberDescriptor;
    }

    public static /* synthetic */ CallableMemberDescriptor findInterfaceImplementation$default(CallableMemberDescriptor callableMemberDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return findInterfaceImplementation(callableMemberDescriptor, z);
    }

    public static final CallableMemberDescriptor findInterfaceImplementation(CallableMemberDescriptor descriptor, boolean z) {
        CallableMemberDescriptor callableMemberDescriptorFindImplementationFromInterface;
        CallableMemberDescriptor callableMemberDescriptorFirstSuperMethodFromKotlin;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor.getKind().isReal() || CallResolverUtilKt.isOrOverridesSynthesized(descriptor) || (callableMemberDescriptorFindImplementationFromInterface = findImplementationFromInterface(descriptor)) == null || (callableMemberDescriptorFirstSuperMethodFromKotlin = firstSuperMethodFromKotlin(descriptor, callableMemberDescriptorFindImplementationFromInterface)) == null || !DescriptorUtils.isInterface(callableMemberDescriptorFirstSuperMethodFromKotlin.getContainingModule())) {
            return null;
        }
        return z ? callableMemberDescriptorFindImplementationFromInterface : callableMemberDescriptorFirstSuperMethodFromKotlin;
    }

    public static final CallableMemberDescriptor firstSuperMethodFromKotlin(CallableMemberDescriptor descriptor, CallableMemberDescriptor implementation) {
        Object next;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(implementation, "implementation");
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = descriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Iterator<T> it = overriddenDescriptors.iterator();
        while (it.hasNext()) {
            next = it.next();
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) next;
            if (callableMemberDescriptor.getModality() != Modality.ABSTRACT) {
                if (!Intrinsics.areEqual(callableMemberDescriptor, implementation)) {
                    Intrinsics.checkNotNull(callableMemberDescriptor);
                    if (OverridingUtil.overrides(callableMemberDescriptor, implementation, DescriptorUtilsKt.isTypeRefinementEnabled(DescriptorUtilsKt.getModule(callableMemberDescriptor)), true)) {
                    }
                }
                return (CallableMemberDescriptor) next;
            }
        }
        next = null;
        return (CallableMemberDescriptor) next;
    }

    public static /* synthetic */ Map getNonPrivateTraitMembersForDelegation$default(ClassDescriptor classDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return getNonPrivateTraitMembersForDelegation(classDescriptor, z);
    }

    public static final Map<CallableMemberDescriptor, CallableMemberDescriptor> getNonPrivateTraitMembersForDelegation(ClassDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (DeclarationDescriptor declarationDescriptor : DescriptorUtils.getAllDescriptors(descriptor.getDefaultType().getMemberScope())) {
            if (declarationDescriptor instanceof CallableMemberDescriptor) {
                LinkedHashMap linkedHashMap2 = linkedHashMap;
                CallableMemberDescriptor nonPrivateTraitMembersForDelegation = getNonPrivateTraitMembersForDelegation((CallableMemberDescriptor) declarationDescriptor, z);
                if (nonPrivateTraitMembersForDelegation != null) {
                    linkedHashMap2.put(declarationDescriptor, nonPrivateTraitMembersForDelegation);
                }
            }
        }
        return linkedHashMap;
    }

    public static /* synthetic */ CallableMemberDescriptor getNonPrivateTraitMembersForDelegation$default(CallableMemberDescriptor callableMemberDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return getNonPrivateTraitMembersForDelegation(callableMemberDescriptor, z);
    }

    public static final CallableMemberDescriptor getNonPrivateTraitMembersForDelegation(CallableMemberDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        CallableMemberDescriptor callableMemberDescriptorFindInterfaceImplementation = findInterfaceImplementation(descriptor, z);
        if (callableMemberDescriptorFindInterfaceImplementation == null || DescriptorVisibilities.isPrivate(callableMemberDescriptorFindInterfaceImplementation.getVisibilityImpl()) || Intrinsics.areEqual(callableMemberDescriptorFindInterfaceImplementation.getVisibilityImpl(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            return null;
        }
        return callableMemberDescriptorFindInterfaceImplementation;
    }
}
