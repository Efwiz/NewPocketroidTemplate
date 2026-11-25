package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.incremental.storage.BasicMapsOwner;
import org.jetbrains.kotlin.incremental.storage.FileToIdMap;
import org.jetbrains.kotlin.incremental.storage.IdToFileMap;
import org.jetbrains.kotlin.incremental.storage.LookupMap;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LookupStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 92\u00020\u0001:\u00019B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010%\u001a\u00020&2\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020*0(2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020*0\rJ\u0010\u0010,\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020\u0003H\u0002J\b\u0010.\u001a\u00020&H\u0016J\u0014\u0010/\u001a\u00020*2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020)0\rJ\u0010\u00100\u001a\u00020&2\u0006\u00101\u001a\u00020\"H\u0016J\u0006\u00102\u001a\u00020&J\u0014\u00103\u001a\b\u0012\u0004\u0012\u00020*0\u00192\u0006\u00104\u001a\u00020)J\b\u00105\u001a\u00020&H\u0002J\u0014\u00106\u001a\u00020&2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u000308R\u0016\u0010\u0007\u001a\u00070\b¢\u0006\u0002\b\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0010R\u000e\u0010 \u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\"8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/incremental/LookupStorage;", "Lorg/jetbrains/kotlin/incremental/storage/BasicMapsOwner;", "targetDataDir", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "getLOG", "()Lcom/intellij/openapi/diagnostic/Logger;", "addedLookupSymbols", "", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "getAddedLookupSymbols", "()Ljava/util/Set;", "countersFile", "fileToId", "Lorg/jetbrains/kotlin/incremental/storage/FileToIdMap;", "idToFile", "Lorg/jetbrains/kotlin/incremental/storage/IdToFileMap;", "lookupMap", "Lorg/jetbrains/kotlin/incremental/TrackedLookupMap;", "lookupSymbols", "", "getLookupSymbols", "()Ljava/util/Collection;", "oldSize", "", "removedLookupSymbols", "getRemovedLookupSymbols", "size", "trackChanges", "", "getTrackChanges", "()Z", "addAll", "", "lookups", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "", "allPaths", "addFileIfNeeded", "file", Constants.ATTR_FILTER_TYPE_CLEAN, ArchiveStreamFactory.DUMP, "flush", "memoryCachesOnly", "forceGC", "get", "lookupSymbol", "removeGarbageForTests", "removeLookupsFrom", "files", "Lkotlin/sequences/Sequence;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LookupStorage extends BasicMapsOwner {
    private final Logger LOG;
    private final File countersFile;
    private final FileToIdMap fileToId;
    private final IncrementalCompilationContext icContext;
    private final IdToFileMap idToFile;
    private final TrackedLookupMap lookupMap;
    private int oldSize;
    private volatile int size;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LookupStorage(File targetDataDir, IncrementalCompilationContext icContext) throws IOException {
        Integer intOrNull;
        super(targetDataDir);
        Intrinsics.checkNotNullParameter(targetDataDir, "targetDataDir");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        this.icContext = icContext;
        Logger logger = Logger.getInstance("#org.jetbrains.kotlin.jps.build.KotlinBuilder");
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        this.LOG = logger;
        File storageFile = getStorageFile("counters");
        this.countersFile = storageFile;
        this.idToFile = (IdToFileMap) registerMap(new IdToFileMap(getStorageFile("id-to-file"), icContext));
        this.fileToId = (FileToIdMap) registerMap(new FileToIdMap(getStorageFile("file-to-id"), icContext));
        this.lookupMap = new TrackedLookupMap((LookupMap) registerMap(new LookupMap(getStorageFile("lookups"), icContext)), getTrackChanges());
        try {
            if (storageFile.exists()) {
                String str = (String) CollectionsKt.firstOrNull(FilesKt.readLines$default(storageFile, null, 1, null));
                if (str == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) {
                    throw new IOException(storageFile + " exists, but it is empty. Counters file is corrupted");
                }
                this.size = intOrNull.intValue();
                this.oldSize = this.size;
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e2) {
            throw new IOException("Could not read " + this.countersFile, e2);
        }
    }

    public final Logger getLOG() {
        return this.LOG;
    }

    private final boolean getTrackChanges() {
        return this.icContext.getTrackChangesInLookupCache();
    }

    public final Set<LookupSymbolKey> getAddedLookupSymbols() {
        if (!getTrackChanges()) {
            throw new IllegalStateException("trackChanges is not enabled".toString());
        }
        Set<LookupSymbolKey> addedKeys = this.lookupMap.getAddedKeys();
        Intrinsics.checkNotNull(addedKeys);
        return addedKeys;
    }

    public final Set<LookupSymbolKey> getRemovedLookupSymbols() {
        if (!getTrackChanges()) {
            throw new IllegalStateException("trackChanges is not enabled".toString());
        }
        Set<LookupSymbolKey> removedKeys = this.lookupMap.getRemovedKeys();
        Intrinsics.checkNotNull(removedKeys);
        return removedKeys;
    }

    public final Collection<LookupSymbolKey> getLookupSymbols() {
        return this.lookupMap.getKeys();
    }

    public final synchronized Collection<String> get(LookupSymbol lookupSymbol) {
        Intrinsics.checkNotNullParameter(lookupSymbol, "lookupSymbol");
        LookupSymbolKey lookupSymbolKey = new LookupSymbolKey(lookupSymbol.getName(), lookupSymbol.getScope());
        Collection<Integer> collection = this.lookupMap.get(lookupSymbolKey);
        if (collection == null) {
            return SetsKt.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        Iterator<Integer> it = collection.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            File file = this.idToFile.get(iIntValue);
            String path = file != null ? file.getPath() : null;
            if (path != null) {
                linkedHashSet.add(path);
                linkedHashSet2.add(Integer.valueOf(iIntValue));
            }
        }
        if (this.size > 10000 && ((double) linkedHashSet2.size()) / ((double) collection.size()) < 0.5d) {
            this.lookupMap.set(lookupSymbolKey, linkedHashSet2);
        }
        return linkedHashSet;
    }

    public final synchronized void addAll(MultiMap<LookupSymbol, String> lookups, Set<String> allPaths) {
        Intrinsics.checkNotNullParameter(lookups, "lookups");
        Intrinsics.checkNotNullParameter(allPaths, "allPaths");
        Map mapKeysToMap = org.jetbrains.kotlin.utils.CollectionsKt.keysToMap(CollectionsKt.sorted(allPaths), new Function1<String, Integer>() { // from class: org.jetbrains.kotlin.incremental.LookupStorage$addAll$pathToId$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(this.this$0.addFileIfNeeded(new File(it)));
            }
        });
        Set<LookupSymbol> setKeySet = lookups.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "keySet(...)");
        for (LookupSymbol lookupSymbol : CollectionsKt.sorted(setKeySet)) {
            LookupSymbolKey lookupSymbolKey = new LookupSymbolKey(lookupSymbol.getName(), lookupSymbol.getScope());
            Collection<String> collection = lookups.get(lookupSymbol);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            TreeSet treeSet = new TreeSet();
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                Object obj = mapKeysToMap.get((String) it.next());
                Intrinsics.checkNotNull(obj);
                treeSet.add(Integer.valueOf(((Number) obj).intValue()));
            }
            this.lookupMap.append(lookupSymbolKey, treeSet);
        }
    }

    public final synchronized void removeLookupsFrom(Sequence<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        for (File file : files) {
            Integer num = this.fileToId.get(file);
            if (num != null) {
                this.idToFile.remove(num.intValue());
                this.fileToId.remove(file);
            }
        }
    }

    @Override // org.jetbrains.kotlin.incremental.storage.BasicMapsOwner
    public synchronized void clean() {
        CompilationTransaction transaction = this.icContext.getTransaction();
        Path path = this.countersFile.toPath();
        Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
        transaction.deleteFile(path);
        this.size = 0;
        super.clean();
    }

    @Override // org.jetbrains.kotlin.incremental.storage.BasicMapsOwner
    public synchronized void flush(boolean memoryCachesOnly) {
        try {
            if (this.size != this.oldSize && this.size > 0) {
                CompilationTransaction transaction = this.icContext.getTransaction();
                Path path = this.countersFile.toPath();
                Intrinsics.checkNotNullExpressionValue(path, "toPath(...)");
                CompilationTransactionKt.writeText(transaction, path, this.size + "\n0");
            }
            super.flush(memoryCachesOnly);
        } catch (Throwable th) {
            super.flush(memoryCachesOnly);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int addFileIfNeeded(File file) {
        Integer num = this.fileToId.get(file);
        if (num != null) {
            return num.intValue();
        }
        int i = this.size;
        this.size = i + 1;
        this.fileToId.set(file, i);
        this.idToFile.set(i, file);
        return i;
    }

    private final void removeGarbageForTests() {
        for (LookupSymbolKey lookupSymbolKey : this.lookupMap.getKeys()) {
            TrackedLookupMap trackedLookupMap = this.lookupMap;
            Collection<Integer> collection = trackedLookupMap.get(lookupSymbolKey);
            Intrinsics.checkNotNull(collection);
            ArrayList arrayList = new ArrayList();
            for (Object obj : collection) {
                if (this.idToFile.contains(((Number) obj).intValue())) {
                    arrayList.add(obj);
                }
            }
            trackedLookupMap.set(lookupSymbolKey, CollectionsKt.toSet(arrayList));
        }
        Map<File, Integer> map = this.fileToId.toMap();
        HashMap map2 = new HashMap(map.size());
        this.idToFile.clean();
        this.fileToId.clean();
        this.size = 0;
        for (Map.Entry entry : CollectionsKt.sortedWith(map.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.incremental.LookupStorage$removeGarbageForTests$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((File) ((Map.Entry) t).getKey()).getPath(), ((File) ((Map.Entry) t2).getKey()).getPath());
            }
        })) {
            File file = (File) entry.getKey();
            HashMap map3 = map2;
            map3.put(Integer.valueOf(((Number) entry.getValue()).intValue()), Integer.valueOf(addFileIfNeeded(file)));
        }
        for (LookupSymbolKey lookupSymbolKey2 : this.lookupMap.getKeys()) {
            Collection<Integer> collection2 = this.lookupMap.get(lookupSymbolKey2);
            Intrinsics.checkNotNull(collection2);
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) map2.get(Integer.valueOf(((Number) it.next()).intValue()));
                if (num != null) {
                    arrayList2.add(num);
                }
            }
            Set<Integer> set = CollectionsKt.toSet(arrayList2);
            if (set.isEmpty()) {
                this.lookupMap.remove(lookupSymbolKey2);
            } else {
                this.lookupMap.set(lookupSymbolKey2, set);
            }
        }
    }

    public final void forceGC() {
        removeGarbageForTests();
        flush(false);
    }

    public final String dump(Set<LookupSymbol> lookupSymbols) {
        String string;
        Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
        flush(false);
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(sb);
        printer.println("====== File to id map");
        printer.println(this.fileToId.dump());
        printer.println("====== Id to file map");
        printer.println(this.idToFile.dump());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : lookupSymbols) {
            LookupSymbol lookupSymbol = (LookupSymbol) obj;
            LookupSymbolKey lookupSymbolKey = new LookupSymbolKey(lookupSymbol.getName(), lookupSymbol.getScope());
            Object obj2 = linkedHashMap.get(lookupSymbolKey);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(lookupSymbolKey, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (LookupSymbolKey lookupSymbolKey2 : CollectionsKt.sorted(this.lookupMap.getKeys())) {
            Collection<Integer> collection = this.lookupMap.get(lookupSymbolKey2);
            Intrinsics.checkNotNull(collection);
            if (linkedHashMap.containsKey(lookupSymbolKey2)) {
                Object obj3 = linkedHashMap.get(lookupSymbolKey2);
                Intrinsics.checkNotNull(obj3);
                Iterable<LookupSymbol> iterable = (Iterable) obj3;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                for (LookupSymbol lookupSymbol2 : iterable) {
                    arrayList.add(lookupSymbol2.getScope() + '#' + lookupSymbol2.getName());
                }
                string = CollectionsKt.joinToString$default(CollectionsKt.sorted(arrayList), ", ", null, null, 0, null, null, 62, null);
            } else {
                string = lookupSymbolKey2.toString();
            }
            Collection<Integer> collection2 = collection;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
            Iterator<T> it = collection2.iterator();
            while (it.hasNext()) {
                arrayList2.add(String.valueOf(((Number) it.next()).intValue()));
            }
            printer.println(string + " -> " + CollectionsKt.joinToString$default(CollectionsKt.sorted(arrayList2), ", ", null, null, 0, null, null, 62, null));
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return string2;
    }
}
