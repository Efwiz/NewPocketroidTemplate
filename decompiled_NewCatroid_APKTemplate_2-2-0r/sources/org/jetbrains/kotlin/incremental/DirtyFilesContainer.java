package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DirtyFilesContainer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B'\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u001e\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\bJ\u0014\u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015J\u0014\u0010\u0017\u001a\u00020\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0015J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\f0\u001dj\b\u0012\u0004\u0012\u00020\f`\u001eR\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/DirtyFilesContainer;", "", "caches", "Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;", "reporter", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "sourceFilesExtensions", "", "", "(Lorg/jetbrains/kotlin/incremental/IncrementalCachesManager;Lorg/jetbrains/kotlin/build/report/ICReporter;Ljava/util/List;)V", "myDirtyFiles", "Ljava/util/HashSet;", "Ljava/io/File;", "Lkotlin/collections/HashSet;", ProductAction.ACTION_ADD, "", "files", "", "reason", "addByDirtyClasses", "dirtyClassesFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "addByDirtySymbols", "lookupSymbols", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "isEmpty", "", "toMutableLinkedSet", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DirtyFilesContainer {
    private final IncrementalCachesManager<?> caches;
    private final HashSet<File> myDirtyFiles;
    private final ICReporter reporter;
    private final List<String> sourceFilesExtensions;

    public DirtyFilesContainer(IncrementalCachesManager<?> caches, ICReporter reporter, List<String> sourceFilesExtensions) {
        Intrinsics.checkNotNullParameter(caches, "caches");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(sourceFilesExtensions, "sourceFilesExtensions");
        this.caches = caches;
        this.reporter = reporter;
        this.sourceFilesExtensions = sourceFilesExtensions;
        this.myDirtyFiles = new HashSet<>();
    }

    public final boolean isEmpty() {
        return this.myDirtyFiles.isEmpty();
    }

    public final LinkedHashSet<File> toMutableLinkedSet() {
        return new LinkedHashSet<>(this.myDirtyFiles);
    }

    public final void addByDirtySymbols(Collection<LookupSymbol> lookupSymbols) {
        Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
        if (lookupSymbols.isEmpty()) {
            return;
        }
        add(BuildUtilKt.mapLookupSymbolsToFiles$default(this.caches.getLookupCache(), lookupSymbols, this.reporter, null, 8, null), null);
    }

    public final void addByDirtyClasses(Collection<FqName> dirtyClassesFqNames) {
        Intrinsics.checkNotNullParameter(dirtyClassesFqNames, "dirtyClassesFqNames");
        if (dirtyClassesFqNames.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = dirtyClassesFqNames.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, BuildUtilKt.withSubtypes((FqName) it.next(), CollectionsKt.listOf(this.caches.getPlatformCache())));
        }
        add(BuildUtilKt.mapClassesFqNamesToFiles$default(CollectionsKt.listOf(this.caches.getPlatformCache()), arrayList, this.reporter, null, 8, null), null);
    }

    public final void add(Iterable<? extends File> files, String reason) {
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList();
        for (File file : files) {
            if (FileUtilsKt.isKotlinFile(file, this.sourceFilesExtensions)) {
                arrayList.add(file);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList2;
        if (arrayList3.isEmpty()) {
            return;
        }
        this.myDirtyFiles.addAll(arrayList3);
        if (reason != null) {
            this.reporter.reportMarkDirty(arrayList2, reason);
        }
    }
}
