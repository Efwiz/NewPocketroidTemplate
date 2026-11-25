package org.jetbrains.kotlin.js.sourceMap;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;

/* JADX INFO: compiled from: SourceFilePathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\b\u0006\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u001f\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0006J\u0012\u0010\r\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u000e\u0010\u0010\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0004R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceFilePathResolver;", "", "sourceRoots", "", "Ljava/io/File;", ModuleXmlParser.OUTPUT_DIR, "(Ljava/util/List;Ljava/io/File;)V", ResponseCacheMiddleware.CACHE, "", "", "outputDirPathResolver", "Lorg/jetbrains/kotlin/js/sourceMap/RelativePathCalculator;", "", "calculatePathRelativeToOutput", "file", "calculatePathRelativeToSourceRoots", "getPathRelativeToSourceRoots", "Companion", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceFilePathResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<File, String> cache;
    private final RelativePathCalculator outputDirPathResolver;
    private final Set<File> sourceRoots;

    @JvmStatic
    public static final SourceFilePathResolver create(List<String> list, String str, File file) {
        return INSTANCE.create(list, str, file);
    }

    @JvmStatic
    public static final SourceFilePathResolver create(CompilerConfiguration compilerConfiguration) {
        return INSTANCE.create(compilerConfiguration);
    }

    @JvmStatic
    public static final SourceFilePathResolver create(JsConfig jsConfig) {
        return INSTANCE.create(jsConfig);
    }

    public SourceFilePathResolver(List<? extends File> sourceRoots, File file) {
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = sourceRoots.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((File) it.next()).getAbsoluteFile());
        }
        this.sourceRoots = linkedHashSet;
        this.outputDirPathResolver = file != null ? new RelativePathCalculator(file) : null;
        this.cache = new LinkedHashMap();
    }

    public /* synthetic */ SourceFilePathResolver(List list, File file, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? null : file);
    }

    public final String getPathRelativeToSourceRoots(File file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        String str = this.cache.get(file);
        if (str != null) {
            return str;
        }
        String strCalculatePathRelativeToSourceRoots = calculatePathRelativeToSourceRoots(file);
        this.cache.put(file, strCalculatePathRelativeToSourceRoots);
        return strCalculatePathRelativeToSourceRoots;
    }

    private final String calculatePathRelativeToSourceRoots(File file) throws IOException {
        String strCalculatePathRelativeToOutput = calculatePathRelativeToOutput(file);
        if (strCalculatePathRelativeToOutput != null) {
            return strCalculatePathRelativeToOutput;
        }
        ArrayList arrayList = new ArrayList();
        File absoluteFile = file.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
        for (File fileNormalize = FilesKt.normalize(absoluteFile); fileNormalize != null; fileNormalize = fileNormalize.getParentFile()) {
            if (this.sourceRoots.contains(fileNormalize)) {
                if (arrayList.isEmpty()) {
                    break;
                }
                CollectionsKt.reverse(arrayList);
                String separator = File.separator;
                Intrinsics.checkNotNullExpressionValue(separator, "separator");
                return CollectionsKt.joinToString$default(arrayList, separator, null, null, 0, null, null, 62, null);
            }
            String name2 = fileNormalize.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            arrayList.add(name2);
        }
        String name3 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return name3;
    }

    private final String calculatePathRelativeToOutput(File file) {
        RelativePathCalculator relativePathCalculator = this.outputDirPathResolver;
        if (relativePathCalculator != null) {
            return relativePathCalculator.calculateRelativePathTo(file);
        }
        return null;
    }

    /* JADX INFO: compiled from: SourceFilePathResolver.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceFilePathResolver$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/js/sourceMap/SourceFilePathResolver;", "sourceRoots", "", "", "sourceMapPrefix", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final SourceFilePathResolver create(JsConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            CompilerConfiguration configuration = config.getConfiguration();
            Intrinsics.checkNotNullExpressionValue(configuration, "getConfiguration(...)");
            return create(configuration);
        }

        @JvmStatic
        public final SourceFilePathResolver create(CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            Object obj = configuration.get(JSConfigurationKeys.SOURCE_MAP_SOURCE_ROOTS, CollectionsKt.emptyList());
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            Object obj2 = configuration.get(JSConfigurationKeys.SOURCE_MAP_PREFIX, "");
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            return create((List) obj, (String) obj2, (File) configuration.get(JSConfigurationKeys.OUTPUT_DIR));
        }

        @JvmStatic
        public final SourceFilePathResolver create(List<String> sourceRoots, String sourceMapPrefix, File outputDir) {
            Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
            Intrinsics.checkNotNullParameter(sourceMapPrefix, "sourceMapPrefix");
            boolean z = sourceMapPrefix.length() == 0 && sourceRoots.isEmpty();
            List<String> list = sourceRoots;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new File((String) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            if (!z) {
                outputDir = null;
            }
            return new SourceFilePathResolver(arrayList2, outputDir);
        }
    }
}
