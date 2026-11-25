package org.jetbrains.kotlin.cli.js.klib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.js.JsPlatforms;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForJSIR.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/TopDownAnalyzerFacadeForJSIR;", "Lorg/jetbrains/kotlin/js/analyze/AbstractTopDownAnalyzerFacadeForWeb;", "()V", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "loadIncrementalCacheMetadata", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "incrementalData", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TopDownAnalyzerFacadeForJSIR extends AbstractTopDownAnalyzerFacadeForWeb {
    public static final TopDownAnalyzerFacadeForJSIR INSTANCE = new TopDownAnalyzerFacadeForJSIR();
    private static final PlatformDependentAnalyzerServices analyzerServices = JsPlatformAnalyzerServices.INSTANCE;
    private static final TargetPlatform platform = JsPlatforms.INSTANCE.getDefaultJsPlatform();

    private TopDownAnalyzerFacadeForJSIR() {
    }

    @Override // org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb
    public PlatformDependentAnalyzerServices getAnalyzerServices() {
        return analyzerServices;
    }

    @Override // org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb
    public TargetPlatform getPlatform() {
        return platform;
    }

    @Override // org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb
    protected PackageFragmentProvider loadIncrementalCacheMetadata(IncrementalDataProvider incrementalData, ModuleContext moduleContext, LookupTracker lookupTracker, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(incrementalData, "incrementalData");
        Intrinsics.checkNotNullParameter(moduleContext, "moduleContext");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        KlibMetadataModuleDescriptorFactory defaultDeserializedDescriptorFactory = KlibKt.getJsFactories().getDefaultDeserializedDescriptorFactory();
        Collection<TranslationResultValue> collectionValues = incrementalData.getCompiledPackageParts().values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((TranslationResultValue) it.next()).getMetadata());
        }
        return defaultDeserializedDescriptorFactory.createCachedPackageFragmentProvider(arrayList, moduleContext.getStorageManager(), moduleContext.getModule(), new CompilerDeserializationConfiguration(languageVersionSettings), lookupTracker);
    }
}
