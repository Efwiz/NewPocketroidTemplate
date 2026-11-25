package org.jetbrains.kotlin.konan.util;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader;

/* JADX INFO: compiled from: DependencyProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u0000 E2\u00020\u0001:\u0002EFB\u008c\u0001\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012U\b\u0002\u0010\f\u001aO\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\rj\u0004\u0018\u0001`\u0015¢\u0006\u0002\u0010\u0016B\u009e\u0001\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0017j\u0002`\u0018\u0012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u001a\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012U\b\u0002\u0010\f\u001aO\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\rj\u0004\u0018\u0001`\u0015¢\u0006\u0002\u0010\u001bBÌ\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0018\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001a0\u001d\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0003\u0012\b\b\u0002\u0010 \u001a\u00020\t\u0012\b\b\u0002\u0010!\u001a\u00020\"\u0012\b\b\u0002\u0010#\u001a\u00020\u0011\u0012U\b\u0002\u0010\f\u001aO\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u0014\u0018\u00010\rj\u0004\u0018\u0001`\u0015\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010$\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010%J\u0018\u0010<\u001a\u00020\u00142\u0006\u0010=\u001a\u00020\u00072\u0006\u0010>\u001a\u00020\u0007H\u0002J\u000e\u0010?\u001a\u00020\u00032\u0006\u0010@\u001a\u00020\u0007J\u0010\u0010A\u001a\u00020\u00032\u0006\u0010=\u001a\u00020\u0007H\u0002J\u0010\u0010B\u001a\u00020\u00032\u0006\u0010C\u001a\u00020\u0007H\u0002J\u0006\u0010D\u001a\u00020\u0014R\u000e\u0010 \u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010&\u001a\u00020\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b'\u0010(R\u000e\u0010$\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010+\u001a\u00020\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b-\u0010*\u001a\u0004\b,\u0010(R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020/X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u00103\u001a\u00020\u00038BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b5\u0010*\u001a\u0004\b4\u0010(R\u001a\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00107\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u00109\"\u0004\b:\u0010;¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyProcessor;", "", "dependenciesRoot", "Ljava/io/File;", "properties", "Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "dependenciesUrl", "", "keepUnstable", "", "archiveType", "Lorg/jetbrains/kotlin/konan/util/ArchiveType;", "customProgressCallback", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "url", "", "currentBytes", "totalBytes", "", "Lorg/jetbrains/kotlin/konan/util/ProgressCallback;", "(Ljava/io/File;Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;Ljava/lang/String;ZLorg/jetbrains/kotlin/konan/util/ArchiveType;Lkotlin/jvm/functions/Function3;)V", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "dependencies", "", "(Ljava/io/File;Ljava/util/Properties;Ljava/util/List;Ljava/lang/String;ZLorg/jetbrains/kotlin/konan/util/ArchiveType;Lkotlin/jvm/functions/Function3;)V", "dependencyToCandidates", "", "Lorg/jetbrains/kotlin/konan/util/DependencySource;", "homeDependencyCache", "airplaneMode", "maxAttempts", "", "attemptIntervalMs", "deleteArchives", "(Ljava/io/File;Ljava/lang/String;Ljava/util/Map;Ljava/io/File;ZIJLkotlin/jvm/functions/Function3;ZZLorg/jetbrains/kotlin/konan/util/ArchiveType;)V", "cacheDirectory", "getCacheDirectory", "()Ljava/io/File;", "cacheDirectory$delegate", "Lkotlin/Lazy;", "dependenciesDirectory", "getDependenciesDirectory", "dependenciesDirectory$delegate", ReplaceExistingProjectDialogFragment.BUNDLE_KEY_DOWNLOADER, "Lorg/jetbrains/kotlin/konan/util/DependencyDownloader;", "extractor", "Lorg/jetbrains/kotlin/konan/util/DependencyExtractor;", "isInfoShown", "lockFile", "getLockFile", "lockFile$delegate", "resolvedDependencies", "showInfo", "getShowInfo", "()Z", "setShowInfo", "(Z)V", "downloadDependency", "dependency", "baseUrl", "resolve", "path", "resolveDependency", "resolveRelative", "relative", "run", "Companion", "DependencyFile", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DependencyProcessor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean airplaneMode;
    private final ArchiveType archiveType;

    /* JADX INFO: renamed from: cacheDirectory$delegate, reason: from kotlin metadata */
    private final Lazy cacheDirectory;
    private final boolean deleteArchives;

    /* JADX INFO: renamed from: dependenciesDirectory$delegate, reason: from kotlin metadata */
    private final Lazy dependenciesDirectory;
    private final String dependenciesUrl;
    private final DependencyDownloader downloader;
    private final DependencyExtractor extractor;
    private boolean isInfoShown;
    private final boolean keepUnstable;

    /* JADX INFO: renamed from: lockFile$delegate, reason: from kotlin metadata */
    private final Lazy lockFile;
    private final Map<String, DependencySource> resolvedDependencies;
    private boolean showInfo;

    public DependencyProcessor(final File dependenciesRoot, String dependenciesUrl, Map<String, ? extends List<? extends DependencySource>> dependencyToCandidates, final File homeDependencyCache, boolean z, int i, long j, Function3<? super String, ? super Long, ? super Long, Unit> function3, boolean z2, boolean z3, ArchiveType archiveType) {
        Intrinsics.checkNotNullParameter(dependenciesRoot, "dependenciesRoot");
        Intrinsics.checkNotNullParameter(dependenciesUrl, "dependenciesUrl");
        Intrinsics.checkNotNullParameter(dependencyToCandidates, "dependencyToCandidates");
        Intrinsics.checkNotNullParameter(homeDependencyCache, "homeDependencyCache");
        Intrinsics.checkNotNullParameter(archiveType, "archiveType");
        this.dependenciesUrl = dependenciesUrl;
        this.airplaneMode = z;
        this.keepUnstable = z2;
        this.deleteArchives = z3;
        this.archiveType = archiveType;
        this.dependenciesDirectory = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.konan.util.DependencyProcessor$dependenciesDirectory$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                File file = dependenciesRoot;
                file.mkdirs();
                return file;
            }
        });
        this.cacheDirectory = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.konan.util.DependencyProcessor$cacheDirectory$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                File file = homeDependencyCache;
                file.mkdirs();
                return file;
            }
        });
        this.lockFile = LazyKt.lazy(new Function0<File>() { // from class: org.jetbrains.kotlin.konan.util.DependencyProcessor$lockFile$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final File invoke() throws IOException {
                File file = new File(this.this$0.getCacheDirectory(), Constants.LOCK_SUFFIX);
                if (!file.exists()) {
                    file.createNewFile();
                }
                return file;
            }
        });
        this.showInfo = true;
        this.downloader = new DependencyDownloader(i, j, function3);
        this.extractor = new DependencyExtractor(archiveType);
        ArrayList arrayList = new ArrayList(dependencyToCandidates.size());
        for (Map.Entry<String, ? extends List<? extends DependencySource>> entry : dependencyToCandidates.entrySet()) {
            String key = entry.getKey();
            List<? extends DependencySource> value = entry.getValue();
            DependencySource dependencySource = (DependencySource) SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence(value), new Function1<DependencySource, DependencySource>() { // from class: org.jetbrains.kotlin.konan.util.DependencyProcessor$resolvedDependencies$1$candidate$1
                @Override // kotlin.jvm.functions.Function1
                public final DependencySource invoke(DependencySource candidate) {
                    Intrinsics.checkNotNullParameter(candidate, "candidate");
                    if (candidate instanceof DependencySource.Local) {
                        if (((DependencySource.Local) candidate).getPath().exists()) {
                            return candidate;
                        }
                    } else {
                        if (Intrinsics.areEqual(candidate, DependencySource.Remote.Public.INSTANCE)) {
                            return candidate;
                        }
                        if (!Intrinsics.areEqual(candidate, DependencySource.Remote.Internal.INSTANCE)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (InternalServer.INSTANCE.isAvailable()) {
                            return candidate;
                        }
                    }
                    return null;
                }
            }));
            if (dependencySource == null) {
                throw new IllegalStateException((key + " is not available; candidates:\n" + CollectionsKt.joinToString$default(value, "\n", null, null, 0, null, null, 62, null)).toString());
            }
            arrayList.add(TuplesKt.to(key, dependencySource));
        }
        this.resolvedDependencies = MapsKt.toMap(arrayList);
    }

    public /* synthetic */ DependencyProcessor(File file, String str, Map map, File file2, boolean z, int i, long j, Function3 function3, boolean z2, boolean z3, ArchiveType archiveType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, str, map, (i2 & 8) != 0 ? DependencyDirectories.INSTANCE.getDependencyCacheDir(file.getAbsolutePath()) : file2, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? 10 : i, (i2 & 64) != 0 ? 3000L : j, (i2 & 128) != 0 ? null : function3, (i2 & 256) != 0 ? true : z2, (i2 & 512) != 0 ? true : z3, (i2 & 1024) != 0 ? ArchiveType.INSTANCE.getSystemDefault() : archiveType);
    }

    private final File getDependenciesDirectory() {
        return (File) this.dependenciesDirectory.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File getCacheDirectory() {
        return (File) this.cacheDirectory.getValue();
    }

    private final File getLockFile() {
        return (File) this.lockFile.getValue();
    }

    public final boolean getShowInfo() {
        return this.showInfo;
    }

    public final void setShowInfo(boolean z) {
        this.showInfo = z;
    }

    public /* synthetic */ DependencyProcessor(File file, KonanPropertiesLoader konanPropertiesLoader, String str, boolean z, ArchiveType archiveType, Function3 function3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, konanPropertiesLoader, (i & 4) != 0 ? DependencyProcessorKt.getDependenciesUrl(konanPropertiesLoader) : str, (i & 8) != 0 ? true : z, (i & 16) != 0 ? ArchiveType.INSTANCE.getSystemDefault() : archiveType, (i & 32) != 0 ? null : function3);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DependencyProcessor(File dependenciesRoot, KonanPropertiesLoader properties, String dependenciesUrl, boolean z, ArchiveType archiveType, Function3<? super String, ? super Long, ? super Long, Unit> function3) {
        this(dependenciesRoot, properties.getProperties(), properties.getDependencies(), dependenciesUrl, z, archiveType, function3);
        Intrinsics.checkNotNullParameter(dependenciesRoot, "dependenciesRoot");
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(dependenciesUrl, "dependenciesUrl");
        Intrinsics.checkNotNullParameter(archiveType, "archiveType");
    }

    public /* synthetic */ DependencyProcessor(File file, Properties properties, List list, String str, boolean z, ArchiveType archiveType, Function3 function3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, properties, list, (i & 8) != 0 ? DependencyProcessorKt.getDependenciesUrl(properties) : str, (i & 16) != 0 ? true : z, (i & 32) != 0 ? ArchiveType.INSTANCE.getSystemDefault() : archiveType, (i & 64) != 0 ? null : function3);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DependencyProcessor(File dependenciesRoot, Properties properties, List<String> dependencies, String dependenciesUrl, boolean z, ArchiveType archiveType, Function3<? super String, ? super Long, ? super Long, Unit> function3) {
        this(dependenciesRoot, dependenciesUrl, DependencyProcessorKt.findCandidates(properties, dependencies), null, DependencyProcessorKt.getAirplaneMode(properties), DependencyProcessorKt.getDownloadingAttempts(properties), DependencyProcessorKt.getDownloadingAttemptIntervalMs(properties), function3, z, false, archiveType, 520, null);
        Intrinsics.checkNotNullParameter(dependenciesRoot, "dependenciesRoot");
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        Intrinsics.checkNotNullParameter(dependenciesUrl, "dependenciesUrl");
        Intrinsics.checkNotNullParameter(archiveType, "archiveType");
    }

    /* JADX INFO: compiled from: DependencyProcessor.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u0005J\u0006\u0010\u0014\u001a\u00020\u0010R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyProcessor$DependencyFile;", "", "directory", "Ljava/io/File;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "(Ljava/io/File;Ljava/lang/String;)V", "dependencies", "", "file", "getFile", "()Ljava/io/File;", ProductAction.ACTION_ADD, "", "dependency", "addAndSave", "", "contains", ProductAction.ACTION_REMOVE, "removeAndSave", "save", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class DependencyFile {
        private final Set<String> dependencies;
        private final File file;

        public DependencyFile(File directory, String fileName) throws IOException {
            Intrinsics.checkNotNullParameter(directory, "directory");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            File file = new File(directory, fileName);
            file.createNewFile();
            this.file = file;
            this.dependencies = CollectionsKt.toMutableSet(FilesKt.readLines$default(file, null, 1, null));
        }

        public final File getFile() {
            return this.file;
        }

        public final boolean contains(String dependency) {
            Intrinsics.checkNotNullParameter(dependency, "dependency");
            return this.dependencies.contains(dependency);
        }

        public final boolean add(String dependency) {
            Intrinsics.checkNotNullParameter(dependency, "dependency");
            return this.dependencies.add(dependency);
        }

        public final boolean remove(String dependency) {
            Intrinsics.checkNotNullParameter(dependency, "dependency");
            return this.dependencies.remove(dependency);
        }

        public final void removeAndSave(String dependency) throws Exception {
            Intrinsics.checkNotNullParameter(dependency, "dependency");
            remove(dependency);
            save();
        }

        public final void addAndSave(String dependency) throws Exception {
            Intrinsics.checkNotNullParameter(dependency, "dependency");
            add(dependency);
            save();
        }

        /* JADX WARN: Code duplicated, block: B:20:0x004a  */
        public final void save() throws Exception {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(this.file), Charsets.UTF_8);
            OutputStreamWriter outputStreamWriter2 = outputStreamWriter;
            boolean z = false;
            try {
                Iterator<T> it = this.dependencies.iterator();
                while (it.hasNext()) {
                    outputStreamWriter.write((String) it.next());
                    outputStreamWriter.write("\n");
                }
                Unit unit = Unit.INSTANCE;
                outputStreamWriter2.close();
            } catch (Exception e) {
                try {
                    try {
                        outputStreamWriter2.close();
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        if (!z) {
                            outputStreamWriter2.close();
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                }
                throw e;
            } catch (Throwable th2) {
                th = th2;
                if (!z) {
                    outputStreamWriter2.close();
                }
                throw th;
            }
        }
    }

    private final void downloadDependency(String dependency, String baseUrl) throws Exception {
        File file = new File(getDependenciesDirectory(), dependency);
        String depName = file.getName();
        String str = depName + '.' + this.archiveType.getFileExtension();
        File fileResolve = FilesKt.resolve(getCacheDirectory(), str);
        URL url = new URL(baseUrl + '/' + str);
        DependencyFile dependencyFile = new DependencyFile(getDependenciesDirectory(), ".extracted");
        Intrinsics.checkNotNullExpressionValue(depName, "depName");
        if (dependencyFile.contains(depName) && file.exists() && file.isDirectory()) {
            String[] list = file.list();
            Intrinsics.checkNotNullExpressionValue(list, "depDir.list()");
            if (!(list.length == 0)) {
                if (this.keepUnstable) {
                    return;
                }
                String[] list2 = file.list();
                Intrinsics.checkNotNullExpressionValue(list2, "depDir.list()");
                if (!ArraysKt.contains(list2, ".unstable")) {
                    return;
                }
                FilesKt.deleteRecursively(file);
                fileResolve.delete();
                dependencyFile.removeAndSave(dependency);
            }
        }
        if (this.showInfo && !this.isInfoShown) {
            System.out.println((Object) "Downloading native dependencies (LLVM, sysroot etc). This is a one-time action performed only on the first run of the compiler.");
            this.isInfoShown = true;
        }
        if (!fileResolve.exists()) {
            if (this.airplaneMode) {
                throw new FileNotFoundException(StringsKt.trimIndent("\n                    Cannot find a dependency locally: " + dependency + ".\n                    Set `airplaneMode = false` in konan.properties to download it.\n                "));
            }
            DependencyDownloader.download$default(this.downloader, url, fileResolve, null, 4, null);
        }
        System.out.println((Object) ("Extracting dependency: " + fileResolve + " into " + getDependenciesDirectory()));
        this.extractor.extract(fileResolve, getDependenciesDirectory());
        if (this.deleteArchives) {
            fileResolve.delete();
        }
        dependencyFile.addAndSave(depName);
    }

    /* JADX INFO: compiled from: DependencyProcessor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/konan/util/DependencyProcessor$Companion;", "", "()V", "isInternalSeverAvailable", "", "()Z", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isInternalSeverAvailable() {
            return InternalServer.INSTANCE.isAvailable();
        }
    }

    private final File resolveDependency(String dependency) {
        DependencySource dependencySource = this.resolvedDependencies.get(dependency);
        if (dependencySource instanceof DependencySource.Local) {
            return ((DependencySource.Local) dependencySource).getPath();
        }
        if (dependencySource instanceof DependencySource.Remote) {
            return new File(getDependenciesDirectory(), dependency);
        }
        if (dependencySource == null) {
            throw new IllegalStateException((dependency + " not declared as dependency").toString());
        }
        throw new NoWhenBranchMatchedException();
    }

    public final File resolve(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return Paths.get(path, new String[0]).isAbsolute() ? new File(path) : resolveRelative(path);
    }

    private final File resolveRelative(String relative) {
        Path path = Paths.get(relative, new String[0]);
        if (path.isAbsolute()) {
            throw new IllegalStateException(("not a relative path: " + relative).toString());
        }
        Intrinsics.checkNotNullExpressionValue(path, "path");
        File fileResolveDependency = resolveDependency(CollectionsKt.first(path).toString());
        if (path.getNameCount() > 1) {
            fileResolveDependency = fileResolveDependency.toPath().resolve(path.subpath(1, path.getNameCount())).toFile();
        }
        Intrinsics.checkNotNullExpressionValue(fileResolveDependency, "resolveDependency(depend…t\n            }\n        }");
        return fileResolveDependency;
    }

    public final void run() {
        String str;
        Pair pair;
        Object lock = System.getProperties().computeIfAbsent("kotlin.native.dependencies.lock", new Function() { // from class: org.jetbrains.kotlin.konan.util.DependencyProcessor$run$lock$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new String("lock");
            }
        });
        Map<String, DependencySource> map = this.resolvedDependencies;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, DependencySource> entry : map.entrySet()) {
            String key = entry.getKey();
            DependencySource value = entry.getValue();
            if (value instanceof DependencySource.Local) {
                pair = null;
            } else {
                if (!(value instanceof DependencySource.Remote)) {
                    throw new NoWhenBranchMatchedException();
                }
                pair = TuplesKt.to(key, value);
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        ArrayList<Pair> arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return;
        }
        Intrinsics.checkNotNullExpressionValue(lock, "lock");
        synchronized (lock) {
            FileLock fileLockLock = new RandomAccessFile(getLockFile(), "rw").getChannel().lock();
            try {
                try {
                    FileLock fileLock = fileLockLock;
                    for (Pair pair2 : arrayList2) {
                        String str2 = (String) pair2.component1();
                        DependencySource.Remote remote = (DependencySource.Remote) pair2.component2();
                        if (Intrinsics.areEqual(remote, DependencySource.Remote.Public.INSTANCE)) {
                            str = this.dependenciesUrl;
                        } else {
                            if (!Intrinsics.areEqual(remote, DependencySource.Remote.Internal.INSTANCE)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            str = InternalServer.url;
                        }
                        downloadDependency(str2, str);
                    }
                    Unit unit = Unit.INSTANCE;
                    if (fileLockLock != null) {
                        fileLockLock.close();
                    }
                    Unit unit2 = Unit.INSTANCE;
                } catch (Throwable th) {
                    if (0 == 0 && fileLockLock != null) {
                        fileLockLock.close();
                    }
                    throw th;
                }
            } catch (Exception e) {
                if (fileLockLock != null) {
                    try {
                        fileLockLock.close();
                    } catch (Exception unused) {
                    }
                }
                throw e;
            }
        }
    }
}
