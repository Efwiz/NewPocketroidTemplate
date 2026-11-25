package org.jetbrains.kotlin.resolve.konan.platform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformConfigurator;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: NativePlatformAnalyzerServices.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/platform/NativePlatformAnalyzerServices;", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "()V", "excludedImports", "", "Lorg/jetbrains/kotlin/name/FqName;", "getExcludedImports", "()Ljava/util/List;", "platformConfigurator", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "getPlatformConfigurator", "()Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "computePlatformSpecificDefaultImports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "result", "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativePlatformAnalyzerServices extends PlatformDependentAnalyzerServices {
    private static final List<FqName> excludedImports;
    public static final NativePlatformAnalyzerServices INSTANCE = new NativePlatformAnalyzerServices();
    private static final PlatformConfigurator platformConfigurator = NativePlatformConfigurator.INSTANCE;

    private NativePlatformAnalyzerServices() {
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public void computePlatformSpecificDefaultImports(StorageManager storageManager, List<ImportPath> result) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(result, "result");
        result.add(ImportPath.INSTANCE.fromString("kotlin.native.*"));
    }

    static {
        List listListOf = CollectionsKt.listOf("identityHashCode");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(new FqName("kotlin.native." + ((String) it.next())));
        }
        excludedImports = arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public PlatformConfigurator getPlatformConfigurator() {
        return platformConfigurator;
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public List<FqName> getExcludedImports() {
        return excludedImports;
    }
}
