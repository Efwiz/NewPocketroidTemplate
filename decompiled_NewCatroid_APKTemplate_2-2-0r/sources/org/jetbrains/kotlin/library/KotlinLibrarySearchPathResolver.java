package org.jetbrains.kotlin.library;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.linkage.issues.UserVisibleIrModulesSupport;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.FileKt;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.impl.KotlinLibraryImplKt;
import org.jetbrains.kotlin.util.Logger;
import org.jetbrains.kotlin.util.UtilKt;

/* JADX INFO: compiled from: SearchPathResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\"\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001ZBE\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ&\u0010:\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010;\u001a\u00020\u000b2\u0006\u0010<\u001a\u00020\u000b2\u0006\u0010=\u001a\u00020\u000bH\u0016J\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00100?2\u0006\u0010@\u001a\u00020\u0006H\u0002J\u0012\u0010A\u001a\u0004\u0018\u00010\u00102\u0006\u0010B\u001a\u00020\u0010H\u0002J \u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000?2\u0006\u0010D\u001a\u00020\u00102\b\b\u0002\u0010E\u001a\u00020\u0006H\u0002J\u001e\u0010F\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010G\u001a\u00020\u00102\u0006\u0010H\u001a\u00020\u000bH&J\u001d\u0010I\u001a\u00020\u000b2\u0006\u0010B\u001a\u00028\u00002\u0006\u0010J\u001a\u00020/H\u0016¢\u0006\u0002\u0010KJ\u0016\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00100?2\u0006\u0010M\u001a\u00020\u0006H\u0016J\u0015\u0010N\u001a\u00028\u00002\u0006\u0010M\u001a\u00020\u0006H\u0016¢\u0006\u0002\u0010OJ\u001f\u0010N\u001a\u0004\u0018\u00018\u00002\u0006\u0010J\u001a\u00020P2\u0006\u0010Q\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010RJ\u001d\u0010N\u001a\u00028\u00002\u0006\u0010J\u001a\u00020S2\u0006\u0010Q\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010TJ\u001f\u0010U\u001a\u0004\u0018\u00018\u00002\u0006\u0010J\u001a\u00020/2\u0006\u0010Q\u001a\u00020\u000bH\u0002¢\u0006\u0002\u0010VJ\u0012\u0010W\u001a\u0004\u0018\u00010\u00102\u0006\u0010X\u001a\u00020\u0006H\u0002J\u0018\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00100?*\b\u0012\u0004\u0012\u00020\u00100?H\u0002R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u00058F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0017\u0010\u0015R\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u00108F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0012R\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR!\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0019\u001a\u0004\b\"\u0010#R\u0013\u0010%\u001a\u0004\u0018\u00010\u00108F¢\u0006\u0006\u001a\u0004\b&\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001fR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R!\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00100\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u0019\u001a\u0004\b+\u0010\u0015R>\u0010-\u001a2\u0012\u0004\u0012\u00020/\u0012\u000e\u0012\f00R\b\u0012\u0004\u0012\u00028\u00000\u00000.j\u0018\u0012\u0004\u0012\u00020/\u0012\u000e\u0012\f00R\b\u0012\u0004\u0012\u00028\u00000\u0000`1X\u0082\u0004¢\u0006\u0002\n\u0000R!\u00102\u001a\b\u0012\u0004\u0012\u00020\u00100\u00058VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b4\u0010\u0019\u001a\u0004\b3\u0010\u0015R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0018\u00107\u001a\u00020\u0010*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b8\u00109¨\u0006["}, d2 = {"Lorg/jetbrains/kotlin/library/KotlinLibrarySearchPathResolver;", "L", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/SearchPathResolver;", "repositories", "", "", "directLibs", "distributionKlib", "localKotlinDir", "skipCurrentDir", "", "logger", "Lorg/jetbrains/kotlin/util/Logger;", "(Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;ZLorg/jetbrains/kotlin/util/Logger;)V", "currentDirHead", "Lorg/jetbrains/kotlin/konan/file/File;", "getCurrentDirHead", "()Lorg/jetbrains/kotlin/konan/file/File;", "defaultRoots", "getDefaultRoots", "()Ljava/util/List;", "directLibraries", "getDirectLibraries", "directLibraries$delegate", "Lkotlin/Lazy;", "distHead", "getDistHead", "distPlatformHead", "getDistPlatformHead", "getDistributionKlib", "()Ljava/lang/String;", "files", "", "getFiles", "()Ljava/util/Set;", "files$delegate", "localHead", "getLocalHead", "getLocalKotlinDir", "getLogger", "()Lorg/jetbrains/kotlin/util/Logger;", "repoRoots", "getRepoRoots", "repoRoots$delegate", "resolvedLibraries", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/library/UnresolvedLibrary;", "Lorg/jetbrains/kotlin/library/KotlinLibrarySearchPathResolver$ResolvedLibrary;", "Lkotlin/collections/HashMap;", "searchRoots", "getSearchRoots", "searchRoots$delegate", "getSkipCurrentDir", "()Z", "klib", "getKlib", "(Lorg/jetbrains/kotlin/konan/file/File;)Lorg/jetbrains/kotlin/konan/file/File;", "defaultLinks", "noStdLib", "noDefaultLibs", "noEndorsedLibs", "directLibsSequence", "Lkotlin/sequences/Sequence;", "givenName", "found", "candidate", "getDefaultLibrariesFromDir", "directory", "prefix", "libraryComponentBuilder", "file", "isDefault", "libraryMatch", "unresolved", "(Lorg/jetbrains/kotlin/library/KotlinLibrary;Lorg/jetbrains/kotlin/library/UnresolvedLibrary;)Z", "resolutionSequence", "givenPath", "resolve", "(Ljava/lang/String;)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/LenientUnresolvedLibrary;", "isDefaultLink", "(Lorg/jetbrains/kotlin/library/LenientUnresolvedLibrary;Z)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/RequiredUnresolvedLibrary;", "(Lorg/jetbrains/kotlin/library/RequiredUnresolvedLibrary;Z)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "resolveOrNull", "(Lorg/jetbrains/kotlin/library/UnresolvedLibrary;Z)Lorg/jetbrains/kotlin/library/KotlinLibrary;", "validFileOrNull", "path", "filterOutPre_1_4_libraries", "ResolvedLibrary", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class KotlinLibrarySearchPathResolver<L extends KotlinLibrary> implements SearchPathResolver<L> {

    /* JADX INFO: renamed from: directLibraries$delegate, reason: from kotlin metadata */
    private final Lazy directLibraries;
    private final File distPlatformHead;
    private final String distributionKlib;

    /* JADX INFO: renamed from: files$delegate, reason: from kotlin metadata */
    private final Lazy files;
    private final String localKotlinDir;
    private final Logger logger;

    /* JADX INFO: renamed from: repoRoots$delegate, reason: from kotlin metadata */
    private final Lazy repoRoots;
    private final HashMap<UnresolvedLibrary, KotlinLibrarySearchPathResolver<L>.ResolvedLibrary> resolvedLibraries;

    /* JADX INFO: renamed from: searchRoots$delegate, reason: from kotlin metadata */
    private final Lazy searchRoots;
    private final boolean skipCurrentDir;

    public abstract List<L> libraryComponentBuilder(File file, boolean isDefault);

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public boolean libraryMatch(L candidate, UnresolvedLibrary unresolved) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        return true;
    }

    public KotlinLibrarySearchPathResolver(final List<String> repositories, final List<String> directLibs, String str, String str2, boolean z, Logger logger) {
        Intrinsics.checkNotNullParameter(repositories, "repositories");
        Intrinsics.checkNotNullParameter(directLibs, "directLibs");
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.distributionKlib = str;
        this.localKotlinDir = str2;
        this.skipCurrentDir = z;
        this.logger = logger;
        this.repoRoots = LazyKt.lazy(new Function0<List<? extends File>>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$repoRoots$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends File> invoke() {
                List<String> list = repositories;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new File((String) it.next()));
                }
                return arrayList;
            }
        });
        this.directLibraries = LazyKt.lazy(new Function0<List<? extends L>>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$directLibraries$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<L> invoke() {
                List<String> list = directLibs;
                KotlinLibrarySearchPathResolver<L> kotlinLibrarySearchPathResolver = this;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    File fileFound = kotlinLibrarySearchPathResolver.found(new File((String) it.next()));
                    if (fileFound != null) {
                        arrayList.add(fileFound);
                    }
                }
                KotlinLibrarySearchPathResolver<L> kotlinLibrarySearchPathResolver2 = this;
                ArrayList arrayList2 = new ArrayList();
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    CollectionsKt.addAll(arrayList2, kotlinLibrarySearchPathResolver2.libraryComponentBuilder((File) it2.next(), false));
                }
                return arrayList2;
            }
        });
        this.searchRoots = LazyKt.lazy(new Function0<List<? extends File>>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$searchRoots$2
            final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends File> invoke() {
                return CollectionsKt.filterNotNull(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf(this.this$0.getCurrentDirHead()), (Iterable) this.this$0.getRepoRoots()), (Iterable) CollectionsKt.listOf((Object[]) new File[]{this.this$0.getLocalHead(), this.this$0.getDistHead(), this.this$0.getDistPlatformHead()})));
            }
        });
        this.files = LazyKt.lazy(new Function0<Set<? extends String>>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$files$2
            final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                List<File> searchRoots = this.this$0.getSearchRoots();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = searchRoots.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, ((File) it.next()).getListFilesOrEmpty());
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(((File) it2.next()).getAbsolutePath());
                }
                return CollectionsKt.toSet(arrayList3);
            }
        });
        this.resolvedLibraries = new HashMap<>();
    }

    public final String getDistributionKlib() {
        return this.distributionKlib;
    }

    public final String getLocalKotlinDir() {
        return this.localKotlinDir;
    }

    public final boolean getSkipCurrentDir() {
        return this.skipCurrentDir;
    }

    @Override // org.jetbrains.kotlin.util.WithLogger
    public Logger getLogger() {
        return this.logger;
    }

    public final File getLocalHead() {
        File File;
        String str = this.localKotlinDir;
        if (str == null || (File = FileKt.File(str)) == null) {
            return null;
        }
        return getKlib(File);
    }

    public final File getDistHead() {
        File File;
        String str = this.distributionKlib;
        if (str == null || (File = FileKt.File(str)) == null) {
            return null;
        }
        return File.child(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR);
    }

    public File getDistPlatformHead() {
        return this.distPlatformHead;
    }

    public final File getCurrentDirHead() {
        if (this.skipCurrentDir) {
            return null;
        }
        return File.INSTANCE.getUserDir();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<File> getRepoRoots() {
        return (List) this.repoRoots.getValue();
    }

    private final List<KotlinLibrary> getDirectLibraries() {
        return (List) this.directLibraries.getValue();
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public List<File> getSearchRoots() {
        return (List) this.searchRoots.getValue();
    }

    private final Set<String> getFiles() {
        return (Set) this.files.getValue();
    }

    private static final <L extends KotlinLibrary> boolean found$check(KotlinLibrarySearchPathResolver<L> kotlinLibrarySearchPathResolver, File file) {
        return kotlinLibrarySearchPathResolver.getFiles().contains(file.getAbsolutePath()) || file.getExists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File found(File candidate) {
        File file = new File(UtilKt.removeSuffixIfPresent(candidate.getPath(), KotlinLibraryUtilsKt.KLIB_FILE_EXTENSION_WITH_DOT));
        File file2 = new File(UtilKt.suffixIfNot(candidate.getPath(), KotlinLibraryUtilsKt.KLIB_FILE_EXTENSION_WITH_DOT));
        if (found$check(this, file2)) {
            return file2;
        }
        if (found$check(this, file)) {
            return file;
        }
        return null;
    }

    private final File validFileOrNull(String path) {
        try {
            Path path2 = Paths.get(path, new String[0]);
            Intrinsics.checkNotNullExpressionValue(path2, "get(path)");
            return new File(path2);
        } catch (InvalidPathException unused) {
            return null;
        }
    }

    private final Sequence<File> directLibsSequence(final String givenName) {
        return SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(getDirectLibraries()), new Function1<KotlinLibrary, Boolean>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.directLibsSequence.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinLibrary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(KotlinLibraryKt.getUniqueName(it), givenName));
            }
        }), new Function1<KotlinLibrary, File>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.directLibsSequence.2
            @Override // kotlin.jvm.functions.Function1
            public final File invoke(KotlinLibrary it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getLibraryFile();
            }
        });
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public Sequence<File> resolutionSequence(String givenPath) {
        Sequence<File> sequencePlus;
        Intrinsics.checkNotNullParameter(givenPath, "givenPath");
        final File fileValidFileOrNull = validFileOrNull(givenPath);
        if (fileValidFileOrNull == null) {
            sequencePlus = directLibsSequence(givenPath);
        } else if (fileValidFileOrNull.isAbsolute()) {
            sequencePlus = SequencesKt.sequenceOf(found(fileValidFileOrNull));
        } else {
            sequencePlus = SequencesKt.plus((Sequence) directLibsSequence(givenPath), SequencesKt.map(CollectionsKt.asSequence(getSearchRoots()), new Function1<File, File>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$resolutionSequence$sequence$repoLibs$1
                final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                public final File invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.found(new File(it, fileValidFileOrNull));
                }
            }));
        }
        return SequencesKt.filterNotNull(sequencePlus);
    }

    private final Sequence<File> filterOutPre_1_4_libraries(Sequence<File> sequence) {
        return SequencesKt.filter(sequence, new Function1<File, Boolean>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.filterOutPre_1_4_libraries.1
            final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (KotlinLibraryImplKt.isPre_1_4_Library(it)) {
                    this.this$0.getLogger().warning("Skipping \"" + it + "\" as it is a pre 1.4 library");
                    z = false;
                } else {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    /* JADX INFO: compiled from: SearchPathResolver.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0003R\u0015\u0010\u0002\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/library/KotlinLibrarySearchPathResolver$ResolvedLibrary;", "", "library", "(Lorg/jetbrains/kotlin/library/KotlinLibrarySearchPathResolver;Lorg/jetbrains/kotlin/library/KotlinLibrary;)V", "getLibrary", "()Lorg/jetbrains/kotlin/library/KotlinLibrary;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
    private final class ResolvedLibrary {
        private final L library;

        public ResolvedLibrary(L l) {
            this.library = l;
        }

        public final L getLibrary() {
            return this.library;
        }
    }

    private final L resolveOrNull(final UnresolvedLibrary unresolved, final boolean isDefaultLink) {
        HashMap<UnresolvedLibrary, KotlinLibrarySearchPathResolver<L>.ResolvedLibrary> map = this.resolvedLibraries;
        KotlinLibrarySearchPathResolver<L>.ResolvedLibrary resolvedLibrary = map.get(unresolved);
        if (resolvedLibrary == null) {
            String path = unresolved.getPath();
            try {
                KotlinLibrarySearchPathResolver<L>.ResolvedLibrary resolvedLibrary2 = new ResolvedLibrary((KotlinLibrary) SequencesKt.firstOrNull(SequencesKt.filterNotNull(SequencesKt.map(SequencesKt.flatMap(filterOutPre_1_4_libraries(resolutionSequence(path)), new Function1<File, Sequence<? extends L>>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$resolveOrNull$1$1
                    final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                        this.this$0 = this;
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Sequence<L> invoke(File it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return CollectionsKt.asSequence(this.this$0.libraryComponentBuilder(it, isDefaultLink));
                    }
                }), new Function1<L, L>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver$resolveOrNull$1$2
                    final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                        this.this$0 = this;
                    }

                    /* JADX WARN: Incorrect return type in method signature: (TL;)TL; */
                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinLibrary invoke(KotlinLibrary it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        if (this.this$0.libraryMatch(it, unresolved)) {
                            return it;
                        }
                        return null;
                    }
                }))));
                map.put(unresolved, resolvedLibrary2);
                resolvedLibrary = resolvedLibrary2;
            } catch (Throwable th) {
                getLogger().mo7307error("Failed to resolve Kotlin library: " + path);
                throw th;
            }
        }
        return (L) resolvedLibrary.getLibrary();
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public L resolve(LenientUnresolvedLibrary unresolved, boolean isDefaultLink) {
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        return (L) resolveOrNull(unresolved, isDefaultLink);
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public L resolve(RequiredUnresolvedLibrary unresolved, boolean isDefaultLink) {
        Intrinsics.checkNotNullParameter(unresolved, "unresolved");
        L l = (L) resolveOrNull(unresolved, isDefaultLink);
        if (l != null) {
            return l;
        }
        Logger logger = getLogger();
        StringBuilder sbAppend = new StringBuilder("Could not find \"").append(unresolved.getPath()).append("\" in ");
        List<File> searchRoots = getSearchRoots();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(searchRoots, 10));
        Iterator<T> it = searchRoots.iterator();
        while (it.hasNext()) {
            arrayList.add(((File) it.next()).getAbsolutePath());
        }
        logger.fatal(sbAppend.append(arrayList).toString());
        throw null;
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public L resolve(String givenPath) {
        Intrinsics.checkNotNullParameter(givenPath, "givenPath");
        return (L) resolve(UnresolvedLibraryKt.UnresolvedLibrary(givenPath, null), false);
    }

    private final File getKlib(File file) {
        return new File(file, "klib");
    }

    public final List<File> getDefaultRoots() {
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new File[]{getDistHead(), getDistPlatformHead()});
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListOfNotNull) {
            if (((File) obj).getExists()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    static /* synthetic */ Sequence getDefaultLibrariesFromDir$default(KotlinLibrarySearchPathResolver kotlinLibrarySearchPathResolver, File file, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getDefaultLibrariesFromDir");
        }
        if ((i & 2) != 0) {
            str = UserVisibleIrModulesSupport.KOTLIN_LIBRARY_PREFIX;
        }
        return kotlinLibrarySearchPathResolver.getDefaultLibrariesFromDir(file, str);
    }

    private final Sequence<L> getDefaultLibrariesFromDir(File directory, final String prefix) {
        if (directory.getExists()) {
            return SequencesKt.map(SequencesKt.map(SequencesKt.filterNot(SequencesKt.filterNot(SequencesKt.filter(CollectionsKt.asSequence(directory.getListFiles()), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.getDefaultLibrariesFromDir.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(StringsKt.startsWith$default(it.getName(), prefix, false, 2, (Object) null));
                }
            }), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.getDefaultLibrariesFromDir.2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(StringsKt.startsWith$default((CharSequence) it.getName(), '.', false, 2, (Object) null));
                }
            }), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.getDefaultLibrariesFromDir.3
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(UtilKt.removeSuffixIfPresent(it.getName(), KotlinLibraryUtilsKt.KLIB_FILE_EXTENSION_WITH_DOT), "stdlib"));
                }
            }), new Function1<File, RequiredUnresolvedLibrary>() { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.getDefaultLibrariesFromDir.4
                @Override // kotlin.jvm.functions.Function1
                public final RequiredUnresolvedLibrary invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return UnresolvedLibraryKt.UnresolvedLibrary(it.getAbsolutePath(), null);
                }
            }), new Function1<RequiredUnresolvedLibrary, L>(this) { // from class: org.jetbrains.kotlin.library.KotlinLibrarySearchPathResolver.getDefaultLibrariesFromDir.5
                final /* synthetic */ KotlinLibrarySearchPathResolver<L> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                public final L invoke(RequiredUnresolvedLibrary it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return (L) this.this$0.resolve(it, true);
                }
            });
        }
        return SequencesKt.emptySequence();
    }

    @Override // org.jetbrains.kotlin.library.SearchPathResolver
    public List<L> defaultLinks(boolean noStdLib, boolean noDefaultLibs, boolean noEndorsedLibs) {
        File distPlatformHead;
        File distHead;
        ArrayList arrayList = new ArrayList();
        if (!noStdLib) {
            arrayList.add(resolve(UnresolvedLibraryKt.UnresolvedLibrary("stdlib", null), true));
        }
        if (!noEndorsedLibs && (distHead = getDistHead()) != null) {
            CollectionsKt.addAll(arrayList, getDefaultLibrariesFromDir$default(this, distHead, null, 2, null));
        }
        if (!noDefaultLibs && (distPlatformHead = getDistPlatformHead()) != null) {
            CollectionsKt.addAll(arrayList, getDefaultLibrariesFromDir$default(this, distPlatformHead, null, 2, null));
        }
        return arrayList;
    }
}
