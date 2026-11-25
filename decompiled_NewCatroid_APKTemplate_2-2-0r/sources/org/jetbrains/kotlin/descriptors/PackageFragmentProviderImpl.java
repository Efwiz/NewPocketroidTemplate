package org.jetbrains.kotlin.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PackageFragmentProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\nH\u0016J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f2\u0006\u0010\b\u001a\u00020\tH\u0017J*\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fH\u0016J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderImpl;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderOptimized;", "packageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "(Ljava/util/Collection;)V", "collectPackageFragments", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "", "getPackageFragments", "", "getSubPackagesOf", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "isEmpty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageFragmentProviderImpl implements PackageFragmentProviderOptimized {
    private final Collection<PackageFragmentDescriptor> packageFragments;

    /* JADX WARN: Multi-variable type inference failed */
    public PackageFragmentProviderImpl(Collection<? extends PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        this.packageFragments = packageFragments;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        for (Object obj : this.packageFragments) {
            if (Intrinsics.areEqual(((PackageFragmentDescriptor) obj).getFqName(), fqName)) {
                packageFragments.add(obj);
            }
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public boolean isEmpty(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Collection<PackageFragmentDescriptor> collection = this.packageFragments;
        if ((collection instanceof Collection) && collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((PackageFragmentDescriptor) it.next()).getFqName(), fqName)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Collection<PackageFragmentDescriptor> collection = this.packageFragments;
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (Intrinsics.areEqual(((PackageFragmentDescriptor) obj).getFqName(), fqName)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public Collection<FqName> getSubPackagesOf(final FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return SequencesKt.toList(SequencesKt.filter(SequencesKt.map(CollectionsKt.asSequence(this.packageFragments), new Function1<PackageFragmentDescriptor, FqName>() { // from class: org.jetbrains.kotlin.descriptors.PackageFragmentProviderImpl.getSubPackagesOf.1
            @Override // kotlin.jvm.functions.Function1
            public final FqName invoke(PackageFragmentDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getFqName();
            }
        }), new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.descriptors.PackageFragmentProviderImpl.getSubPackagesOf.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!it.isRoot() && Intrinsics.areEqual(it.parent(), fqName));
            }
        }));
    }
}
