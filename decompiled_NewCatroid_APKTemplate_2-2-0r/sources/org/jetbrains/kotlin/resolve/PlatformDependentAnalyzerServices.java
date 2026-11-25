package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: PlatformDependentAnalyzerServices.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001 B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0018H&J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fR6\u0010\u0003\u001a*\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\b \u0006*\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00070\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000bR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "", "()V", "defaultImports", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices$DefaultImportsKey;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "defaultLowPriorityImports", "getDefaultLowPriorityImports", "()Ljava/util/List;", "excludedImports", "Lorg/jetbrains/kotlin/name/FqName;", "getExcludedImports", "platformConfigurator", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "getPlatformConfigurator", "()Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "computePlatformSpecificDefaultImports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "result", "", "dependencyOnBuiltIns", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo$DependencyOnBuiltIns;", "getDefaultImports", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "includeLowPriorityImports", "", "DefaultImportsKey", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PlatformDependentAnalyzerServices {
    private final MemoizedFunctionToNotNull<DefaultImportsKey, List<ImportPath>> defaultImports;

    public abstract void computePlatformSpecificDefaultImports(StorageManager storageManager, List<ImportPath> result);

    public abstract PlatformConfigurator getPlatformConfigurator();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PlatformDependentAnalyzerServices.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices$DefaultImportsKey;", "", "includeKotlinComparisons", "", "includeLowPriorityImports", "(ZZ)V", "getIncludeKotlinComparisons", "()Z", "getIncludeLowPriorityImports", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class DefaultImportsKey {
        private final boolean includeKotlinComparisons;
        private final boolean includeLowPriorityImports;

        public static /* synthetic */ DefaultImportsKey copy$default(DefaultImportsKey defaultImportsKey, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                z = defaultImportsKey.includeKotlinComparisons;
            }
            if ((i & 2) != 0) {
                z2 = defaultImportsKey.includeLowPriorityImports;
            }
            return defaultImportsKey.copy(z, z2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getIncludeKotlinComparisons() {
            return this.includeKotlinComparisons;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIncludeLowPriorityImports() {
            return this.includeLowPriorityImports;
        }

        public final DefaultImportsKey copy(boolean includeKotlinComparisons, boolean includeLowPriorityImports) {
            return new DefaultImportsKey(includeKotlinComparisons, includeLowPriorityImports);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DefaultImportsKey)) {
                return false;
            }
            DefaultImportsKey defaultImportsKey = (DefaultImportsKey) other;
            return this.includeKotlinComparisons == defaultImportsKey.includeKotlinComparisons && this.includeLowPriorityImports == defaultImportsKey.includeLowPriorityImports;
        }

        public int hashCode() {
            return (Boolean.hashCode(this.includeKotlinComparisons) * 31) + Boolean.hashCode(this.includeLowPriorityImports);
        }

        public String toString() {
            return "DefaultImportsKey(includeKotlinComparisons=" + this.includeKotlinComparisons + ", includeLowPriorityImports=" + this.includeLowPriorityImports + ')';
        }

        public DefaultImportsKey(boolean z, boolean z2) {
            this.includeKotlinComparisons = z;
            this.includeLowPriorityImports = z2;
        }

        public final boolean getIncludeKotlinComparisons() {
            return this.includeKotlinComparisons;
        }

        public final boolean getIncludeLowPriorityImports() {
            return this.includeLowPriorityImports;
        }
    }

    public PlatformDependentAnalyzerServices() {
        final LockBasedStorageManager lockBasedStorageManager = new LockBasedStorageManager("TargetPlatform");
        MemoizedFunctionToNotNull<DefaultImportsKey, List<ImportPath>> memoizedFunctionToNotNullCreateMemoizedFunction = lockBasedStorageManager.createMemoizedFunction(new Function1<DefaultImportsKey, List<? extends ImportPath>>() { // from class: org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices$defaultImports$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<ImportPath> invoke(PlatformDependentAnalyzerServices.DefaultImportsKey defaultImportsKey) {
                boolean includeKotlinComparisons = defaultImportsKey.getIncludeKotlinComparisons();
                boolean includeLowPriorityImports = defaultImportsKey.getIncludeLowPriorityImports();
                ArrayList arrayList = new ArrayList();
                PlatformDependentAnalyzerServices platformDependentAnalyzerServices = this.this$0;
                LockBasedStorageManager lockBasedStorageManager2 = lockBasedStorageManager;
                Iterator it = CollectionsKt.listOf((Object[]) new String[]{"kotlin.*", "kotlin.annotation.*", "kotlin.collections.*", "kotlin.ranges.*", "kotlin.sequences.*", "kotlin.text.*", "kotlin.io.*"}).iterator();
                while (it.hasNext()) {
                    arrayList.add(ImportPath.INSTANCE.fromString((String) it.next()));
                }
                if (includeKotlinComparisons) {
                    arrayList.add(ImportPath.INSTANCE.fromString("kotlin.comparisons.*"));
                }
                ArrayList arrayList2 = arrayList;
                platformDependentAnalyzerServices.computePlatformSpecificDefaultImports(lockBasedStorageManager2, arrayList2);
                if (includeLowPriorityImports) {
                    arrayList.addAll(platformDependentAnalyzerServices.getDefaultLowPriorityImports());
                }
                return arrayList2;
            }
        });
        Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction, "let(...)");
        this.defaultImports = memoizedFunctionToNotNullCreateMemoizedFunction;
    }

    public List<ImportPath> getDefaultLowPriorityImports() {
        return CollectionsKt.emptyList();
    }

    public final List<ImportPath> getDefaultImports(LanguageVersionSettings languageVersionSettings, boolean includeLowPriorityImports) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        List<ImportPath> listInvoke = this.defaultImports.invoke(new DefaultImportsKey(languageVersionSettings.supportsFeature(LanguageFeature.DefaultImportOfPackageKotlinComparisons), includeLowPriorityImports));
        Intrinsics.checkNotNullExpressionValue(listInvoke, "invoke(...)");
        return listInvoke;
    }

    public List<FqName> getExcludedImports() {
        return CollectionsKt.emptyList();
    }

    public ModuleInfo.DependencyOnBuiltIns dependencyOnBuiltIns() {
        return ModuleInfo.DependencyOnBuiltIns.LAST;
    }
}
