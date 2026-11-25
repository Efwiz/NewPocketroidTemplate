package org.jetbrains.kotlin.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: AbstractResolverForProject.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B1\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0002J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u000f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00192\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J*\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001b2\u0006\u0010\u0012\u001a\u00020\u000f2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00110\u001dH\u0016J\u0010\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\b\u0010 \u001a\u00020!H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/analyzer/DelegatingPackageFragmentProvider;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProviderOptimized;", "resolverForProject", "Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleContent", "Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "packageOracle", "Lorg/jetbrains/kotlin/analyzer/PackageOracle;", "(Lorg/jetbrains/kotlin/analyzer/AbstractResolverForProject;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/analyzer/ModuleContent;Lorg/jetbrains/kotlin/analyzer/PackageOracle;)V", "syntheticFilePackages", "", "Lorg/jetbrains/kotlin/name/FqName;", "certainlyDoesNotExist", "", "fqName", "collectPackageFragments", "", "packageFragments", "", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "getPackageFragments", "", "getSubPackagesOf", "", "nameFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/Name;", "isEmpty", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DelegatingPackageFragmentProvider<M extends ModuleInfo> implements PackageFragmentProviderOptimized {
    private final ModuleDescriptor module;
    private final PackageOracle packageOracle;
    private final AbstractResolverForProject<M> resolverForProject;
    private final Set<FqName> syntheticFilePackages;

    public DelegatingPackageFragmentProvider(AbstractResolverForProject<M> resolverForProject, ModuleDescriptor module, ModuleContent<? extends M> moduleContent, PackageOracle packageOracle) {
        Intrinsics.checkNotNullParameter(resolverForProject, "resolverForProject");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(moduleContent, "moduleContent");
        Intrinsics.checkNotNullParameter(packageOracle, "packageOracle");
        this.resolverForProject = resolverForProject;
        this.module = module;
        this.packageOracle = packageOracle;
        Collection<KtFile> syntheticFiles = moduleContent.getSyntheticFiles();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(syntheticFiles, 10));
        Iterator<T> it = syntheticFiles.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtFile) it.next()).getPackageFqName());
        }
        this.syntheticFilePackages = CollectionsKt.toSet(arrayList);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return certainlyDoesNotExist(fqName) ? CollectionsKt.emptyList() : this.resolverForProject.resolverForModuleDescriptor(this.module).getPackageFragmentProvider().getPackageFragments(fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> packageFragments) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(packageFragments, "packageFragments");
        if (certainlyDoesNotExist(fqName)) {
            return;
        }
        PackageFragmentProviderKt.collectPackageFragmentsOptimizedIfPossible(this.resolverForProject.resolverForModuleDescriptor(this.module).getPackageFragmentProvider(), fqName, packageFragments);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProviderOptimized
    public boolean isEmpty(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (certainlyDoesNotExist(fqName)) {
            return true;
        }
        return PackageFragmentProviderKt.isEmpty(this.resolverForProject.resolverForModuleDescriptor(this.module).getPackageFragmentProvider(), fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.PackageFragmentProvider
    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return certainlyDoesNotExist(fqName) ? CollectionsKt.emptyList() : this.resolverForProject.resolverForModuleDescriptor(this.module).getPackageFragmentProvider().getSubPackagesOf(fqName, nameFilter);
    }

    private final boolean certainlyDoesNotExist(FqName fqName) {
        return (this.resolverForProject.isResolverForModuleDescriptorComputed$frontend(this.module) || this.packageOracle.packageExists(fqName) || this.syntheticFilePackages.contains(fqName)) ? false : true;
    }

    public String toString() {
        return "DelegatingProvider for " + this.module + " in " + this.resolverForProject.getName();
    }
}
