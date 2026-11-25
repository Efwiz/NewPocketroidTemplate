package org.jetbrains.kotlin.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: PackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010 \n\u0000\u001a \u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\n*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u000b"}, d2 = {"collectPackageFragmentsOptimizedIfPossible", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "packageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "isEmpty", "", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PackageFragmentProviderKt {
    public static final List<PackageFragmentDescriptor> packageFragments(PackageFragmentProvider packageFragmentProvider, FqName fqName) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ArrayList arrayList = new ArrayList();
        collectPackageFragmentsOptimizedIfPossible(packageFragmentProvider, fqName, arrayList);
        return arrayList;
    }

    public static final boolean isEmpty(PackageFragmentProvider packageFragmentProvider, FqName fqName) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return packageFragmentProvider instanceof PackageFragmentProviderOptimized ? ((PackageFragmentProviderOptimized) packageFragmentProvider).isEmpty(fqName) : packageFragments(packageFragmentProvider, fqName).isEmpty();
    }

    public static final void collectPackageFragmentsOptimizedIfPossible(PackageFragmentProvider packageFragmentProvider, FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        if (packageFragmentProvider instanceof PackageFragmentProviderOptimized) {
            ((PackageFragmentProviderOptimized) packageFragmentProvider).collectPackageFragments(fqName, packageFragments);
        } else {
            packageFragments.addAll(packageFragmentProvider.getPackageFragments(fqName));
        }
    }
}
