package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;

/* JADX INFO: compiled from: SealedClassInheritorsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CliSealedClassInheritorsProvider;", "Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "()V", "computeSealedSubclasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "sealedClass", "allowSealedInheritorsInDifferentFilesOfSamePackage", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliSealedClassInheritorsProvider extends SealedClassInheritorsProvider {
    public static final CliSealedClassInheritorsProvider INSTANCE = new CliSealedClassInheritorsProvider();

    private CliSealedClassInheritorsProvider() {
    }

    @Override // org.jetbrains.kotlin.resolve.SealedClassInheritorsProvider
    public Collection<ClassDescriptor> computeSealedSubclasses(ClassDescriptor sealedClass, boolean allowSealedInheritorsInDifferentFilesOfSamePackage) {
        DeclarationDescriptor next;
        DeclarationDescriptor containingDeclaration;
        Intrinsics.checkNotNullParameter(sealedClass, "sealedClass");
        if (sealedClass.getModality() != Modality.SEALED) {
            return CollectionsKt.emptyList();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (!allowSealedInheritorsInDifferentFilesOfSamePackage) {
            containingDeclaration = sealedClass.getContainingDeclaration();
        } else {
            Iterator<DeclarationDescriptor> it = DescriptorUtilsKt.getParents(sealedClass).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!(next instanceof PackageFragmentDescriptor));
            containingDeclaration = next;
        }
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            computeSealedSubclasses$collectSubclasses(sealedClass, linkedHashSet, ((PackageFragmentDescriptor) containingDeclaration).getScope(), allowSealedInheritorsInDifferentFilesOfSamePackage);
        }
        MemberScope unsubstitutedInnerClassesScope = sealedClass.getUnsubstitutedInnerClassesScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
        computeSealedSubclasses$collectSubclasses(sealedClass, linkedHashSet, unsubstitutedInnerClassesScope, true);
        return CollectionsKt.sortedWith(linkedHashSet, new Comparator() { // from class: org.jetbrains.kotlin.resolve.CliSealedClassInheritorsProvider$computeSealedSubclasses$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) t).asString(), DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) t2).asString());
            }
        });
    }

    private static final void computeSealedSubclasses$collectSubclasses(ClassDescriptor classDescriptor, LinkedHashSet<ClassDescriptor> linkedHashSet, MemberScope memberScope, boolean z) {
        for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(memberScope, DescriptorKindFilter.CLASSIFIERS, null, 2, null)) {
            if (declarationDescriptor instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor2 = (ClassDescriptor) declarationDescriptor;
                if (classDescriptor2.get$isExpect()) {
                    Name name2 = classDescriptor2.getShortName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name2, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
                    if (contributedClassifier instanceof ClassDescriptor) {
                        classDescriptor2 = (ClassDescriptor) contributedClassifier;
                    } else {
                        classDescriptor2 = contributedClassifier instanceof TypeAliasDescriptor ? ((TypeAliasDescriptor) contributedClassifier).getClassDescriptor() : null;
                    }
                }
                if (classDescriptor2 != null) {
                    if (DescriptorUtils.isDirectSubclass(classDescriptor2, classDescriptor)) {
                        linkedHashSet.add(classDescriptor2);
                    }
                    if (z) {
                        MemberScope unsubstitutedInnerClassesScope = classDescriptor2.getUnsubstitutedInnerClassesScope();
                        Intrinsics.checkNotNullExpressionValue(unsubstitutedInnerClassesScope, "getUnsubstitutedInnerClassesScope(...)");
                        computeSealedSubclasses$collectSubclasses(classDescriptor, linkedHashSet, unsubstitutedInnerClassesScope, z);
                    }
                }
            }
        }
    }
}
