package org.jetbrains.kotlin.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CompositePackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0016J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0017J*\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\u0017\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/CompositePackageFragmentProvider;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderOptimized;", "providers", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "debugName", "", "(Ljava/util/List;Ljava/lang/String;)V", "collectPackageFragments", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "packageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getPackageFragments", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "isEmpty", "toString", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositePackageFragmentProvider implements PackageFragmentProviderOptimized {
    private final String debugName;
    private final List<PackageFragmentProvider> providers;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositePackageFragmentProvider(List<? extends PackageFragmentProvider> providers, String debugName) {
        Intrinsics.checkNotNullParameter(providers, "providers");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        this.providers = providers;
        this.debugName = debugName;
        providers.size();
        CollectionsKt.toSet(providers).size();
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ArrayList arrayList = new ArrayList();
        Iterator<PackageFragmentProvider> it = this.providers.iterator();
        while (it.hasNext()) {
            PackageFragmentProviderKt.collectPackageFragmentsOptimizedIfPossible(it.next(), fqName, arrayList);
        }
        return CollectionsKt.toList(arrayList);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        Iterator<PackageFragmentProvider> it = this.providers.iterator();
        while (it.hasNext()) {
            PackageFragmentProviderKt.collectPackageFragmentsOptimizedIfPossible(it.next(), fqName, packageFragments);
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public boolean isEmpty(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<PackageFragmentProvider> list = this.providers;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!PackageFragmentProviderKt.isEmpty((PackageFragmentProvider) it.next(), fqName)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        HashSet hashSet = new HashSet();
        Iterator<PackageFragmentProvider> it = this.providers.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getSubPackagesOf(fqName, nameFilter));
        }
        return hashSet;
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getDebugName() {
        return this.debugName;
    }
}
