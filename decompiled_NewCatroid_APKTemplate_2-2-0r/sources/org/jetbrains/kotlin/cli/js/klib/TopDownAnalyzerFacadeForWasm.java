package org.jetbrains.kotlin.cli.js.klib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.context.ModuleContext;
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.js.IncrementalDataProvider;
import org.jetbrains.kotlin.incremental.js.TranslationResultValue;
import org.jetbrains.kotlin.ir.backend.js.KlibKt;
import org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb;
import org.jetbrains.kotlin.js.config.WasmTarget;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataModuleDescriptorFactory;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.wasm.WasmPlatforms;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForWasm.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/TopDownAnalyzerFacadeForWasm;", "Lorg/jetbrains/kotlin/js/analyze/AbstractTopDownAnalyzerFacadeForWeb;", "()V", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "loadIncrementalCacheMetadata", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentProvider;", "incrementalData", "Lorg/jetbrains/kotlin/incremental/js/IncrementalDataProvider;", "moduleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Companion", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TopDownAnalyzerFacadeForWasm extends AbstractTopDownAnalyzerFacadeForWeb {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final TargetPlatform platform = WasmPlatforms.Default.INSTANCE;

    @Override // org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb
    public TargetPlatform getPlatform() {
        return this.platform;
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

    /* JADX INFO: compiled from: TopDownAnalyzerFacadeForWasm.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/TopDownAnalyzerFacadeForWasm$Companion;", "", "()V", "facadeFor", "Lorg/jetbrains/kotlin/cli/js/klib/TopDownAnalyzerFacadeForWasm;", "target", "Lorg/jetbrains/kotlin/js/config/WasmTarget;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: TopDownAnalyzerFacadeForWasm.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[WasmTarget.values().length];
                try {
                    iArr[WasmTarget.WASI.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TopDownAnalyzerFacadeForWasm facadeFor(WasmTarget target) {
            if ((target == null ? -1 : WhenMappings.$EnumSwitchMapping$0[target.ordinal()]) == 1) {
                return TopDownAnalyzerFacadeForWasmWasi.INSTANCE;
            }
            return TopDownAnalyzerFacadeForWasmJs.INSTANCE;
        }
    }
}
