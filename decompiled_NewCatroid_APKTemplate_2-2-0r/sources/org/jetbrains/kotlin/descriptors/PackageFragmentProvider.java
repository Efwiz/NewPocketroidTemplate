package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PackageFragmentProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\rJ\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H'J*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "", "getPackageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "Empty", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackageFragmentProvider {
    @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
    List<PackageFragmentDescriptor> getPackageFragments(FqName fqName);

    Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter);

    /* JADX INFO: compiled from: PackageFragmentProvider.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0017J*\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider$Empty;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "()V", "getPackageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements PackageFragmentProvider {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
        public /* bridge */ /* synthetic */ Collection getSubPackagesOf(FqName fqName, Function1 function1) {
            return getSubPackagesOf(fqName, (Function1<? super Name, Boolean>) function1);
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
        @Deprecated(message = "for usages use #packageFragments(FqName) at final point, for impl use #collectPackageFragments(FqName, MutableCollection<PackageFragmentDescriptor>)")
        public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
        public Set<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
            return SetsKt.emptySet();
        }
    }
}
