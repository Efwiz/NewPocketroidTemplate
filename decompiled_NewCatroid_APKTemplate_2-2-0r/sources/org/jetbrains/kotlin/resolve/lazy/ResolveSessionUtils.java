package org.jetbrains.kotlin.resolve.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;

/* JADX INFO: loaded from: classes6.dex */
public class ResolveSessionUtils {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "fqName";
                break;
            case 2:
            default:
                objArr[0] = "module";
                break;
            case 4:
                objArr[0] = "filter";
                break;
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/ResolveSessionUtils";
                break;
            case 7:
                objArr[0] = "packageScope";
                break;
            case 8:
                objArr[0] = "path";
                break;
        }
        if (i == 5 || i == 6) {
            objArr[1] = "getClassOrObjectDescriptorsByFqName";
        } else {
            objArr[1] = "org/jetbrains/kotlin/resolve/lazy/ResolveSessionUtils";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[2] = "getClassOrObjectDescriptorsByFqName";
                break;
            case 5:
            case 6:
                break;
            case 7:
            case 8:
                objArr[2] = "findClassByRelativePath";
                break;
            default:
                objArr[2] = "getClassDescriptorsByFqName";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$getClassDescriptorsByFqName$0(ClassDescriptor classDescriptor) {
        return true;
    }

    private ResolveSessionUtils() {
    }

    public static Collection<ClassDescriptor> getClassDescriptorsByFqName(ModuleDescriptor moduleDescriptor, FqName fqName) {
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (fqName == null) {
            $$$reportNull$$$0(1);
        }
        return getClassOrObjectDescriptorsByFqName(moduleDescriptor, fqName, new Predicate() { // from class: org.jetbrains.kotlin.resolve.lazy.ResolveSessionUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResolveSessionUtils.lambda$getClassDescriptorsByFqName$0((ClassDescriptor) obj);
            }
        });
    }

    private static Collection<ClassDescriptor> getClassOrObjectDescriptorsByFqName(ModuleDescriptor moduleDescriptor, FqName fqName, Predicate<ClassDescriptor> predicate) {
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (fqName == null) {
            $$$reportNull$$$0(3);
        }
        if (predicate == null) {
            $$$reportNull$$$0(4);
        }
        if (fqName.isRoot()) {
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(5);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList(1);
        FqName fqNameParent = fqName.parent();
        while (true) {
            PackageViewDescriptor packageViewDescriptor = moduleDescriptor.getPackage(fqNameParent);
            if (!packageViewDescriptor.isEmpty()) {
                ClassDescriptor classDescriptorFindClassByRelativePath = findClassByRelativePath(packageViewDescriptor.getMemberScope(), FqNamesUtilKt.tail(fqName, fqNameParent));
                if (classDescriptorFindClassByRelativePath != null && predicate.test(classDescriptorFindClassByRelativePath)) {
                    arrayList.add(classDescriptorFindClassByRelativePath);
                }
            }
            if (fqNameParent.isRoot()) {
                return arrayList;
            }
            fqNameParent = fqNameParent.parent();
        }
    }

    public static ClassDescriptor findClassByRelativePath(MemberScope memberScope, FqName fqName) {
        if (memberScope == null) {
            $$$reportNull$$$0(7);
        }
        if (fqName == null) {
            $$$reportNull$$$0(8);
        }
        if (fqName.isRoot()) {
            return null;
        }
        Iterator<Name> it = fqName.pathSegments().iterator();
        ClassifierDescriptor contributedClassifier = null;
        while (it.hasNext()) {
            contributedClassifier = memberScope.mo7465getContributedClassifier(it.next(), NoLookupLocation.WHEN_FIND_BY_FQNAME);
            if (!(contributedClassifier instanceof ClassDescriptor)) {
                return null;
            }
            memberScope = ((ClassDescriptor) contributedClassifier).getUnsubstitutedInnerClassesScope();
        }
        return (ClassDescriptor) contributedClassifier;
    }
}
