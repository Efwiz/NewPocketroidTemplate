package org.jetbrains.kotlin.js.resolve;

import com.reandroid.apkeditor.Options;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformConfigurator;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: JsPlatformAnalyzerServices.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/JsPlatformAnalyzerServices;", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "()V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "excludedImports", "", "Lorg/jetbrains/kotlin/name/FqName;", "getExcludedImports", "()Ljava/util/List;", "platformConfigurator", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "getPlatformConfigurator", "()Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "computePlatformSpecificDefaultImports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "result", "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPlatformAnalyzerServices extends PlatformDependentAnalyzerServices {
    private static final List<FqName> excludedImports;
    public static final JsPlatformAnalyzerServices INSTANCE = new JsPlatformAnalyzerServices();
    private static final PlatformConfigurator platformConfigurator = JsPlatformConfigurator.INSTANCE;

    private JsPlatformAnalyzerServices() {
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public void computePlatformSpecificDefaultImports(StorageManager storageManager, List<ImportPath> result) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(result, "result");
        result.add(ImportPath.INSTANCE.fromString("kotlin.js.*"));
    }

    static {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Promise", "Date", "Console", "Math", "RegExp", "RegExpMatch", "Json", Options.TYPE_JSON});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(new FqName("kotlin.js." + ((String) it.next())));
        }
        excludedImports = arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public PlatformConfigurator getPlatformConfigurator() {
        return platformConfigurator;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return DefaultBuiltIns.INSTANCE.getInstance();
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public List<FqName> getExcludedImports() {
        return excludedImports;
    }
}
