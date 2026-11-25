package org.jetbrains.kotlin.js.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.js.analyzer.JsAnalysisResult;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.resolve.JsPlatformAnalyzerServices;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.js.JsPlatforms;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.resolve.TargetEnvironment;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptSerializationUtil;
import org.jetbrains.kotlin.serialization.js.PackagesWithHeaderMetadata;
import org.jetbrains.kotlin.utils.JsMetadataVersion;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForJS.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J(\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/analyze/TopDownAnalyzerFacadeForJS;", "Lorg/jetbrains/kotlin/js/analyze/AbstractTopDownAnalyzerFacadeForWeb;", "()V", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "analyzeFiles", "Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "loadIncrementalCacheMetadata", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "incrementalData", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TopDownAnalyzerFacadeForJS extends AbstractTopDownAnalyzerFacadeForWeb {
    public static final TopDownAnalyzerFacadeForJS INSTANCE = new TopDownAnalyzerFacadeForJS();
    private static final PlatformDependentAnalyzerServices analyzerServices = JsPlatformAnalyzerServices.INSTANCE;
    private static final TargetPlatform platform = JsPlatforms.INSTANCE.getDefaultJsPlatform();

    private TopDownAnalyzerFacadeForJS() {
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
        byte[] headerMetadata = incrementalData.getHeaderMetadata();
        Collection<TranslationResultValue> collectionValues = incrementalData.getCompiledPackageParts().values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((TranslationResultValue) it.next()).getMetadata());
        }
        int[] metadataVersion = incrementalData.getMetadataVersion();
        return KotlinJavascriptSerializationUtil.INSTANCE.readDescriptors(new PackagesWithHeaderMetadata(headerMetadata, arrayList, new JsMetadataVersion(Arrays.copyOf(metadataVersion, metadataVersion.length))), moduleContext.getStorageManager(), moduleContext.getModule(), new CompilerDeserializationConfiguration(languageVersionSettings), lookupTracker);
    }

    @JvmStatic
    public static final JsAnalysisResult analyzeFiles(Collection<? extends KtFile> files, JsConfig config) {
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(config, "config");
        config.init();
        TopDownAnalyzerFacadeForJS topDownAnalyzerFacadeForJS = INSTANCE;
        Project project = config.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        CompilerConfiguration configuration = config.getConfiguration();
        Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
        List<ModuleDescriptorImpl> moduleDescriptors = config.getModuleDescriptors();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptors, "getModuleDescriptors(...)");
        List<ModuleDescriptorImpl> friendModuleDescriptors = config.getFriendModuleDescriptors();
        Intrinsics.checkNotNullExpressionValue(friendModuleDescriptors, "getFriendModuleDescriptors(...)");
        TargetEnvironment targetEnvironment = config.targetEnvironment;
        Intrinsics.checkNotNullExpressionValue(targetEnvironment, "targetEnvironment");
        return AbstractTopDownAnalyzerFacadeForWeb.analyzeFiles$default(topDownAnalyzerFacadeForJS, files, project, configuration, moduleDescriptors, friendModuleDescriptors, targetEnvironment, false, null, 192, null);
    }
}
