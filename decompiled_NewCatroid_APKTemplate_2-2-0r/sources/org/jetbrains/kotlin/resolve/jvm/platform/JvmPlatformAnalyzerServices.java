package org.jetbrains.kotlin.resolve.jvm.platform;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JvmBuiltIns;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformConfigurator;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: JvmPlatformAnalyzerServices.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/platform/JvmPlatformAnalyzerServices;", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "()V", "defaultLowPriorityImports", "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "getDefaultLowPriorityImports", "()Ljava/util/List;", "platformConfigurator", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "getPlatformConfigurator", "()Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "computePlatformSpecificDefaultImports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "result", "", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPlatformAnalyzerServices extends PlatformDependentAnalyzerServices {
    public static final JvmPlatformAnalyzerServices INSTANCE = new JvmPlatformAnalyzerServices();
    private static final List<ImportPath> defaultLowPriorityImports = CollectionsKt.listOf(ImportPath.INSTANCE.fromString("java.lang.*"));
    private static final PlatformConfigurator platformConfigurator = JvmPlatformConfigurator.INSTANCE;

    private JvmPlatformAnalyzerServices() {
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public void computePlatformSpecificDefaultImports(StorageManager storageManager, List<ImportPath> result) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(result, "result");
        result.add(ImportPath.INSTANCE.fromString("kotlin.jvm.*"));
        Iterator<PackageViewDescriptor> it = new JvmBuiltIns(storageManager, JvmBuiltIns.Kind.FALLBACK).getBuiltInPackagesImportedByDefault().iterator();
        while (it.hasNext()) {
            computePlatformSpecificDefaultImports$addAllClassifiersFromScope(result, it.next().getMemberScope());
        }
    }

    private static final void computePlatformSpecificDefaultImports$addAllClassifiersFromScope(List<ImportPath> list, MemberScope memberScope) {
        Iterator<DeclarationDescriptor> it = memberScope.getContributedDescriptors(DescriptorKindFilter.CLASSIFIERS, MemberScope.INSTANCE.getALL_NAME_FILTER()).iterator();
        while (it.hasNext()) {
            FqName fqNameSafe = DescriptorUtils.getFqNameSafe(it.next());
            Intrinsics.checkNotNullExpressionValue(fqNameSafe, "getFqNameSafe(...)");
            list.add(new ImportPath(fqNameSafe, false, null, 4, null));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public List<ImportPath> getDefaultLowPriorityImports() {
        return defaultLowPriorityImports;
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public PlatformConfigurator getPlatformConfigurator() {
        return platformConfigurator;
    }
}
