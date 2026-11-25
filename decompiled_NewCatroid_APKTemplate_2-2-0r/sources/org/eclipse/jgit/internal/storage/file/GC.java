package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CancelledException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.internal.WorkQueue;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.GitDateParser;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public class GC {
    private static final int DEFAULT_AUTOLIMIT = 6700;
    private static final int DEFAULT_AUTOPACKLIMIT = 50;
    private static final String PRUNE_EXPIRE_DEFAULT = "2.weeks.ago";
    private static final String PRUNE_PACK_EXPIRE_DEFAULT = "1.hour.ago";
    private static volatile ExecutorService executor;
    private boolean automatic;
    private boolean background;
    private Date expire;
    private Collection<Ref> lastPackedRefs;
    private long lastRepackTime;
    private Date packExpire;
    private PackConfig pconfig;
    private final FileRepository repo;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) GC.class);
    private static final Pattern PATTERN_LOOSE_OBJECT = Pattern.compile("[0-9a-fA-F]{38}");
    private static final String PACK_EXT = "." + PackExt.PACK.getExtension();
    private static final String BITMAP_EXT = "." + PackExt.BITMAP_INDEX.getExtension();
    private static final String INDEX_EXT = "." + PackExt.INDEX.getExtension();
    private static final String KEEP_EXT = "." + PackExt.KEEP.getExtension();
    private long expireAgeMillis = -1;
    private long packExpireAgeMillis = -1;
    private ProgressMonitor pm = NullProgressMonitor.INSTANCE;

    private void addRepackAllOption() {
    }

    public static void setExecutor(ExecutorService executorService) {
        executor = executorService;
    }

    public GC(FileRepository fileRepository) {
        this.repo = fileRepository;
        this.pconfig = new PackConfig(fileRepository);
    }

    public Collection<Pack> gc() throws IOException, ParseException {
        if (!this.background) {
            return doGc();
        }
        final GcLog gcLog = new GcLog(this.repo);
        if (!gcLog.lock()) {
            return Collections.emptyList();
        }
        executor().submit(new Callable() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda5
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m6036lambda$0$orgeclipsejgitinternalstoragefileGC(gcLog);
            }
        });
        return Collections.emptyList();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX INFO: renamed from: lambda$0$org-eclipse-jgit-internal-storage-file-GC, reason: not valid java name */
    /* synthetic */ Collection m6036lambda$0$orgeclipsejgitinternalstoragefileGC(GcLog gcLog) throws Exception {
        try {
            try {
                Collection<Pack> collectionDoGc = doGc();
                if (this.automatic && tooManyLooseObjects()) {
                    gcLog.write(JGitText.get().gcTooManyUnpruned);
                    gcLog.commit();
                }
                return collectionDoGc;
            } catch (IOException e) {
                e.addSuppressed(e);
                LOG.error(e.getMessage(), (Throwable) e);
            } finally {
                gcLog.unlock();
            }
        } catch (IOException | ParseException e2) {
            gcLog.write(e2.getMessage());
            StringWriter stringWriter = new StringWriter();
            e2.printStackTrace(new PrintWriter(stringWriter));
            gcLog.write(stringWriter.toString());
            gcLog.commit();
            return Collections.emptyList();
        }
    }

    private ExecutorService executor() {
        return executor != null ? executor : WorkQueue.getExecutor();
    }

    private Collection<Pack> doGc() throws Throwable {
        if (this.automatic && !needGc()) {
            return Collections.emptyList();
        }
        this.pm.start(6);
        packRefs();
        Collection<Pack> collectionRepack = repack();
        prune(Collections.emptySet());
        return collectionRepack;
    }

    private void loosen(ObjectDirectoryInserter objectDirectoryInserter, ObjectReader objectReader, Pack pack, HashSet<ObjectId> hashSet) throws IOException {
        Iterator<PackIndex.MutableEntry> it = pack.iterator();
        while (it.hasNext()) {
            ObjectId objectId = it.next().toObjectId();
            if (!hashSet.contains(objectId)) {
                hashSet.add(objectId);
                ObjectLoader objectLoaderOpen = objectReader.open(objectId);
                objectDirectoryInserter.insert(objectLoaderOpen.getType(), objectLoaderOpen.getSize(), (InputStream) objectLoaderOpen.openStream(), true);
            }
        }
    }

    private void deleteOldPacks(Collection<Pack> collection, Collection<Pack> collection2) throws IOException, ParseException {
        HashSet<ObjectId> hashSet = new HashSet<>();
        Iterator<Pack> it = collection2.iterator();
        while (it.hasNext()) {
            Iterator<PackIndex.MutableEntry> it2 = it.next().iterator();
            while (it2.hasNext()) {
                hashSet.add(it2.next().toObjectId());
            }
        }
        ObjectReader objectReaderNewObjectReader = this.repo.newObjectReader();
        ObjectDirectoryInserter objectDirectoryInserterNewInserter = this.repo.getObjectDatabase().newInserter();
        boolean z = !"now".equals(getPruneExpireStr()) && getExpireDate() < Long.MAX_VALUE;
        prunePreserved();
        long packExpireDate = getPackExpireDate();
        for (Pack pack : collection) {
            checkCancelled();
            String packName = pack.getPackName();
            Iterator<Pack> it3 = collection2.iterator();
            do {
                if (!it3.hasNext()) {
                    if (!pack.shouldBeKept() && this.repo.getFS().lastModifiedInstant(pack.getPackFile()).toEpochMilli() < packExpireDate) {
                        if (z) {
                            loosen(objectDirectoryInserterNewInserter, objectReaderNewObjectReader, pack, hashSet);
                        }
                        pack.close();
                        prunePack(pack.getPackFile());
                        break;
                    }
                    break;
                }
            } while (!packName.equals(it3.next().getPackName()));
        }
        this.repo.getObjectDatabase().close();
    }

    private void removeOldPack(PackFile packFile, int i) throws IOException {
        if (this.pconfig.isPreserveOldPacks()) {
            File preservedDirectory = this.repo.getObjectDatabase().getPreservedDirectory();
            FileUtils.mkdir(preservedDirectory, true);
            FileUtils.rename(packFile, packFile.createPreservedForDirectory(preservedDirectory));
            return;
        }
        FileUtils.delete(packFile, i);
    }

    private void prunePreserved() {
        if (this.pconfig.isPrunePreserved()) {
            try {
                FileUtils.delete(this.repo.getObjectDatabase().getPreservedDirectory(), 7);
            } catch (IOException unused) {
            }
        }
    }

    private void prunePack(PackFile packFile) {
        try {
            removeOldPack(packFile.create(PackExt.PACK), 6);
            for (PackExt packExt : PackExt.valuesCustom()) {
                if (!PackExt.PACK.equals(packExt)) {
                    removeOldPack(packFile.create(packExt), 14);
                }
            }
        } catch (IOException unused) {
        }
    }

    public void prunePacked() throws IOException {
        String[] list;
        ObjectDirectory objectDatabase = this.repo.getObjectDatabase();
        Collection<Pack> packs = objectDatabase.getPacks();
        File objectsDirectory = this.repo.getObjectsDirectory();
        String[] list2 = objectsDirectory.list();
        if (list2 == null || list2.length <= 0) {
            return;
        }
        this.pm.beginTask(JGitText.get().pruneLoosePackedObjects, list2.length);
        try {
            for (String str : list2) {
                checkCancelled();
                this.pm.update(1);
                if (str.length() == 2 && (list = new File(objectsDirectory, str).list()) != null) {
                    for (String str2 : list) {
                        checkCancelled();
                        if (str2.length() == 38) {
                            try {
                                ObjectId objectIdFromString = ObjectId.fromString(String.valueOf(str) + str2);
                                for (Pack pack : packs) {
                                    checkCancelled();
                                    if (pack.hasObject(objectIdFromString)) {
                                        FileUtils.delete(objectDatabase.fileFor(objectIdFromString), 14);
                                        break;
                                    }
                                }
                            } catch (IllegalArgumentException unused) {
                            }
                        }
                    }
                }
            }
        } finally {
            this.pm.endTask();
        }
    }

    public void prune(Set<ObjectId> set) throws IOException, ParseException {
        Collection<Ref> allRefs;
        File file;
        long expireDate = getExpireDate();
        HashMap map = new HashMap();
        File objectsDirectory = this.repo.getObjectsDirectory();
        String[] list = objectsDirectory.list();
        if (list == null || list.length == 0) {
            return;
        }
        this.pm.beginTask(JGitText.get().pruneLooseUnreferencedObjects, list.length);
        try {
            int length = list.length;
            Set<ObjectId> setListNonHEADIndexObjects = null;
            int i = 0;
            while (i < length) {
                String str = list[i];
                checkCancelled();
                this.pm.update(1);
                if (str.length() == 2) {
                    File file2 = new File(objectsDirectory, str);
                    File[] fileArrListFiles = file2.listFiles();
                    if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                        file = objectsDirectory;
                        FileUtils.delete(file2, 8);
                    } else {
                        int length2 = fileArrListFiles.length;
                        int i2 = 0;
                        while (i2 < length2) {
                            File file3 = fileArrListFiles[i2];
                            checkCancelled();
                            String name2 = file3.getName();
                            File file4 = objectsDirectory;
                            if (name2.length() == 38 && this.repo.getFS().lastModifiedInstant(file3).toEpochMilli() < expireDate) {
                                try {
                                    ObjectId objectIdFromString = ObjectId.fromString(String.valueOf(str) + name2);
                                    try {
                                        if (!set.contains(objectIdFromString)) {
                                            if (setListNonHEADIndexObjects == null) {
                                                setListNonHEADIndexObjects = listNonHEADIndexObjects();
                                            }
                                            if (!setListNonHEADIndexObjects.contains(objectIdFromString)) {
                                                map.put(objectIdFromString, file3);
                                            }
                                        }
                                    } catch (IllegalArgumentException unused) {
                                    }
                                } catch (IllegalArgumentException unused2) {
                                }
                            }
                            i2++;
                            objectsDirectory = file4;
                        }
                    }
                    i++;
                    objectsDirectory = file;
                }
                file = objectsDirectory;
                i++;
                objectsDirectory = file;
            }
            this.pm.endTask();
            if (map.isEmpty()) {
                return;
            }
            checkCancelled();
            Collection<Ref> collection = this.lastPackedRefs;
            if (collection == null || collection.isEmpty()) {
                allRefs = getAllRefs();
            } else {
                HashMap map2 = new HashMap();
                for (Ref ref : this.lastPackedRefs) {
                    map2.put(ref.getName(), ref);
                }
                allRefs = new ArrayList<>();
                for (Ref ref2 : getAllRefs()) {
                    if (!equals(ref2, (Ref) map2.get(ref2.getName()))) {
                        allRefs.add(ref2);
                    }
                }
            }
            if (!allRefs.isEmpty()) {
                ObjectWalk objectWalk = new ObjectWalk(this.repo);
                try {
                    for (Ref ref3 : allRefs) {
                        checkCancelled();
                        objectWalk.markStart(objectWalk.parseAny(ref3.getObjectId()));
                    }
                    Collection<Ref> collection2 = this.lastPackedRefs;
                    if (collection2 != null) {
                        Iterator<Ref> it = collection2.iterator();
                        while (it.hasNext()) {
                            objectWalk.markUninteresting(objectWalk.parseAny(it.next().getObjectId()));
                        }
                    }
                    removeReferenced(map, objectWalk);
                    objectWalk.dispose();
                } catch (Throwable th) {
                    objectWalk.dispose();
                    throw th;
                }
            }
            if (map.isEmpty()) {
                return;
            }
            ObjectWalk objectWalk2 = new ObjectWalk(this.repo);
            try {
                Iterator<Ref> it2 = getAllRefs().iterator();
                while (it2.hasNext()) {
                    for (ObjectId objectId : listRefLogObjects(it2.next(), this.lastRepackTime)) {
                        checkCancelled();
                        objectWalk2.markStart(objectWalk2.parseAny(objectId));
                    }
                }
                Collection<Ref> collection3 = this.lastPackedRefs;
                if (collection3 != null) {
                    for (Ref ref4 : collection3) {
                        checkCancelled();
                        objectWalk2.markUninteresting(objectWalk2.parseAny(ref4.getObjectId()));
                    }
                }
                removeReferenced(map, objectWalk2);
                objectWalk2.dispose();
                if (map.isEmpty()) {
                    return;
                }
                checkCancelled();
                HashSet hashSet = new HashSet();
                for (File file5 : map.values()) {
                    if (file5.lastModified() < expireDate) {
                        file5.delete();
                        hashSet.add(file5.getParentFile());
                    }
                }
                Iterator it3 = hashSet.iterator();
                while (it3.hasNext()) {
                    FileUtils.delete((File) it3.next(), 24);
                }
                this.repo.getObjectDatabase().close();
            } catch (Throwable th2) {
                objectWalk2.dispose();
                throw th2;
            }
        } catch (Throwable th3) {
            this.pm.endTask();
            throw th3;
        }
    }

    private long getExpireDate() throws ParseException {
        if (this.expire == null && this.expireAgeMillis == -1) {
            String pruneExpireStr = getPruneExpireStr();
            if (pruneExpireStr == null) {
                pruneExpireStr = PRUNE_EXPIRE_DEFAULT;
            }
            this.expire = GitDateParser.parse(pruneExpireStr, null, SystemReader.getInstance().getLocale());
            this.expireAgeMillis = -1L;
        }
        Date date = this.expire;
        return this.expireAgeMillis != -1 ? System.currentTimeMillis() - this.expireAgeMillis : date != null ? date.getTime() : Long.MAX_VALUE;
    }

    private String getPruneExpireStr() {
        return this.repo.getConfig().getString(ConfigConstants.CONFIG_GC_SECTION, null, ConfigConstants.CONFIG_KEY_PRUNEEXPIRE);
    }

    private long getPackExpireDate() throws ParseException {
        if (this.packExpire == null && this.packExpireAgeMillis == -1) {
            String string = this.repo.getConfig().getString(ConfigConstants.CONFIG_GC_SECTION, null, ConfigConstants.CONFIG_KEY_PRUNEPACKEXPIRE);
            if (string == null) {
                string = PRUNE_PACK_EXPIRE_DEFAULT;
            }
            this.packExpire = GitDateParser.parse(string, null, SystemReader.getInstance().getLocale());
            this.packExpireAgeMillis = -1L;
        }
        Date date = this.packExpire;
        return this.packExpireAgeMillis != -1 ? System.currentTimeMillis() - this.packExpireAgeMillis : date != null ? date.getTime() : Long.MAX_VALUE;
    }

    private void removeReferenced(Map<ObjectId, File> map, ObjectWalk objectWalk) throws IOException {
        RevCommit next = objectWalk.next();
        while (next != null) {
            checkCancelled();
            if (map.remove(next.getId()) != null && map.isEmpty()) {
                return;
            } else {
                next = objectWalk.next();
            }
        }
        RevObject revObjectNextObject = objectWalk.nextObject();
        while (revObjectNextObject != null) {
            checkCancelled();
            if (map.remove(revObjectNextObject.getId()) != null && map.isEmpty()) {
                return;
            } else {
                revObjectNextObject = objectWalk.nextObject();
            }
        }
    }

    private static boolean equals(Ref ref, Ref ref2) {
        if (ref != null && ref2 != null) {
            if (ref.isSymbolic()) {
                return ref2.isSymbolic() && ref.getTarget().getName().equals(ref2.getTarget().getName());
            }
            if (!ref2.isSymbolic() && Objects.equals(ref.getObjectId(), ref2.getObjectId())) {
                return true;
            }
        }
        return false;
    }

    public void packRefs() throws IOException {
        RefDatabase refDatabase = this.repo.getRefDatabase();
        if (refDatabase instanceof FileReftableDatabase) {
            this.pm.beginTask(JGitText.get().packRefs, 1);
            try {
                ((FileReftableDatabase) refDatabase).compactFully();
                return;
            } finally {
                this.pm.endTask();
            }
        }
        List<Ref> refsByPrefix = refDatabase.getRefsByPrefix(Constants.R_REFS);
        ArrayList arrayList = new ArrayList(refsByPrefix.size());
        this.pm.beginTask(JGitText.get().packRefs, refsByPrefix.size());
        try {
            for (Ref ref : refsByPrefix) {
                checkCancelled();
                if (!ref.isSymbolic() && ref.getStorage().isLoose()) {
                    arrayList.add(ref.getName());
                }
                this.pm.update(1);
            }
            ((RefDirectory) this.repo.getRefDatabase()).pack(arrayList);
        } finally {
            this.pm.endTask();
        }
    }

    public Collection<Pack> repack() throws Throwable {
        long j;
        ArrayList arrayList;
        Pack packWritePack;
        Pack packWritePack2;
        Collection<Pack> packs = this.repo.getObjectDatabase().getPacks();
        long jCurrentTimeMillis = System.currentTimeMillis();
        Collection<Ref> allRefs = getAllRefs();
        Set<? extends ObjectId> hashSet = new HashSet<>();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        HashSet hashSet4 = new HashSet();
        Set<? extends ObjectId> hashSet5 = new HashSet<>();
        Set<ObjectId> hashSet6 = new HashSet<>();
        Set<ObjectId> setListNonHEADIndexObjects = listNonHEADIndexObjects();
        Iterator<Ref> it = allRefs.iterator();
        while (it.hasNext()) {
            Ref next = it.next();
            checkCancelled();
            Set<ObjectId> set = setListNonHEADIndexObjects;
            Iterator<Ref> it2 = it;
            hashSet4.addAll(listRefLogObjects(next, 0L));
            if (!next.isSymbolic() && next.getObjectId() != null) {
                if (isHead(next)) {
                    hashSet2.add(next.getObjectId());
                } else if (isTag(next)) {
                    hashSet3.add(next.getObjectId());
                } else {
                    hashSet4.add(next.getObjectId());
                }
                if (next.getPeeledObjectId() != null) {
                    hashSet6.add(next.getPeeledObjectId());
                }
            }
            setListNonHEADIndexObjects = set;
            it = it2;
        }
        List<ObjectIdSet> linkedList = new LinkedList<>();
        for (Pack pack : this.repo.getObjectDatabase().getPacks()) {
            checkCancelled();
            if (pack.shouldBeKept()) {
                linkedList.add(pack.getIndex());
            }
        }
        hashSet3.removeAll(hashSet2);
        hashSet.addAll(hashSet2);
        hashSet.addAll(hashSet3);
        hashSet6.addAll(hashSet);
        hashSet4.addAll(setListNonHEADIndexObjects);
        if (this.pconfig.getSinglePack()) {
            hashSet.addAll(hashSet4);
            hashSet4.clear();
        }
        ArrayList arrayList2 = new ArrayList(2);
        if (hashSet.isEmpty()) {
            j = jCurrentTimeMillis;
            arrayList = arrayList2;
        } else {
            j = jCurrentTimeMillis;
            arrayList = arrayList2;
            Pack packWritePack3 = writePack(hashSet, PackWriter.NONE, hashSet3, hashSet6, linkedList);
            if (packWritePack3 != null) {
                arrayList.add(packWritePack3);
                linkedList.add(0, packWritePack3.getIndex());
            }
        }
        if (!hashSet4.isEmpty() && (packWritePack2 = writePack(hashSet4, hashSet, PackWriter.NONE, hashSet6, linkedList)) != null) {
            arrayList.add(packWritePack2);
        }
        if (!hashSet5.isEmpty() && (packWritePack = writePack(hashSet5, PackWriter.NONE, PackWriter.NONE, null, linkedList)) != null) {
            arrayList.add(packWritePack);
        }
        try {
            deleteOldPacks(packs, arrayList);
            prunePacked();
            if (this.repo.getRefDatabase() instanceof RefDirectory) {
                deleteEmptyRefsFolders();
            }
            deleteOrphans();
            deleteTempPacksIdx();
            this.lastPackedRefs = allRefs;
            this.lastRepackTime = j;
            return arrayList;
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }

    private static boolean isHead(Ref ref) {
        return ref.getName().startsWith(Constants.R_HEADS);
    }

    private static boolean isTag(Ref ref) {
        return ref.getName().startsWith(Constants.R_TAGS);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0071 A[Catch: all -> 0x0075, TRY_ENTER, TryCatch #1 {all -> 0x0075, blocks: (B:3:0x001d, B:8:0x0036, B:28:0x0071, B:29:0x0074, B:4:0x002a, B:5:0x002e, B:22:0x0068, B:24:0x006c), top: B:38:0x001d, inners: #3 }] */
    /* JADX WARN: Code duplicated, block: B:51:? A[Catch: all -> 0x0075, SYNTHETIC, TRY_LEAVE, TryCatch #1 {all -> 0x0075, blocks: (B:3:0x001d, B:8:0x0036, B:28:0x0071, B:29:0x0074, B:4:0x002a, B:5:0x002e, B:22:0x0068, B:24:0x006c), top: B:38:0x001d, inners: #3 }] */
    private void deleteEmptyRefsFolders() throws Throwable {
        Path pathResolve = this.repo.getDirectory().toPath().resolve(Constants.R_REFS);
        final Instant instantMinus = Instant.now().minus(30L, (TemporalUnit) ChronoUnit.SECONDS);
        Throwable th = null;
        try {
            Stream<Path> streamFilter = Files.list(pathResolve).filter(new Predicate() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Files.isDirectory((Path) obj, new LinkOption[0]);
                }
            });
            try {
                Iterator<Path> it = streamFilter.iterator();
                while (it.hasNext()) {
                    try {
                        Stream<Path> list = Files.list(it.next());
                        try {
                            list.filter(new Predicate() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda10
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return this.f$0.m6037lambda$2$orgeclipsejgitinternalstoragefileGC(instantMinus, (Path) obj);
                                }
                            }).forEach(new Consumer() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.deleteDir((Path) obj);
                                }
                            });
                            if (list != null) {
                                list.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (list == null) {
                                throw th;
                            }
                            list.close();
                            throw th;
                            if (streamFilter != null) {
                                throw th;
                            }
                            streamFilter.close();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        if (th == null) {
                            throw th3;
                        }
                        if (th != th3) {
                            th.addSuppressed(th3);
                        }
                        throw th;
                    }
                }
                if (streamFilter != null) {
                    streamFilter.close();
                }
            } catch (Throwable th4) {
                if (streamFilter != null) {
                    throw th4;
                }
                streamFilter.close();
                throw th4;
            }
        } catch (Throwable th5) {
            if (th == null) {
                throw th5;
            }
            if (th != th5) {
                th.addSuppressed(th5);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: canBeSafelyDeleted, reason: merged with bridge method [inline-methods] */
    public boolean m6037lambda$2$orgeclipsejgitinternalstoragefileGC(Path path, Instant instant) {
        try {
            return Files.getLastModifiedTime(path, new LinkOption[0]).toInstant().isBefore(instant);
        } catch (IOException e) {
            LOG.warn(MessageFormat.format(JGitText.get().cannotAccessLastModifiedForSafeDeletion, path), (Throwable) e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDir(Path path) {
        Throwable th = null;
        try {
            Stream<Path> streamWalk = Files.walk(path, new FileVisitOption[0]);
            try {
                streamWalk.filter(new Predicate() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.isDirectory((Path) obj);
                    }
                }).sorted(Comparator.reverseOrder()).forEach(new Consumer() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.delete((Path) obj);
                    }
                });
            } finally {
                if (streamWalk != null) {
                    streamWalk.close();
                }
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                try {
                    th.addSuppressed(th2);
                } catch (IOException e) {
                    LOG.error(e.getMessage(), (Throwable) e);
                    return;
                }
            }
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDirectory(Path path) {
        return path.toFile().isDirectory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delete(Path path) {
        try {
            Files.delete(path);
        } catch (DirectoryNotEmptyException unused) {
        } catch (IOException e) {
            LOG.error(MessageFormat.format(JGitText.get().cannotDeleteFile, path), (Throwable) e);
        }
    }

    private void deleteOrphans() {
        Path path = this.repo.getObjectDatabase().getPackDirectory().toPath();
        Throwable th = null;
        String id2 = null;
        try {
            Stream<Path> list = Files.list(path);
            try {
                List list2 = (List) list.map(new Function() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda7
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((Path) obj).getFileName().toString();
                    }
                }).filter(new Predicate() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda8
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return GC.lambda$7((String) obj);
                    }
                }).sorted(Collections.reverseOrder()).collect(Collectors.toList());
                if (list != null) {
                    list.close();
                }
                if (list2 == null) {
                    return;
                }
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    PackFile packFile = new PackFile(path.toFile(), (String) it.next());
                    PackExt packExt = packFile.getPackExt();
                    if (packExt.equals(PackExt.PACK) || packExt.equals(PackExt.KEEP)) {
                        id2 = packFile.getId();
                    }
                    if (id2 == null || !packFile.getId().equals(id2)) {
                        try {
                            FileUtils.delete(packFile, 6);
                            LOG.warn(JGitText.get().deletedOrphanInPackDir, packFile);
                        } catch (IOException e) {
                            LOG.error(e.getMessage(), (Throwable) e);
                        }
                    }
                }
            } catch (Throwable th2) {
                if (list == null) {
                    throw th2;
                }
                list.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                try {
                    th.addSuppressed(th3);
                } catch (IOException e2) {
                    LOG.error(e2.getMessage(), (Throwable) e2);
                    return;
                }
            }
            throw null;
        }
    }

    static /* synthetic */ boolean lambda$7(String str) {
        return str.endsWith(PACK_EXT) || str.endsWith(BITMAP_EXT) || str.endsWith(INDEX_EXT) || str.endsWith(KEEP_EXT);
    }

    private void deleteTempPacksIdx() {
        Path path = this.repo.getObjectDatabase().getPackDirectory().toPath();
        final Instant instantMinus = Instant.now().minus(1L, (TemporalUnit) ChronoUnit.DAYS);
        if (Files.exists(path, new LinkOption[0])) {
            Throwable th = null;
            try {
                DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(path, "gc_*_tmp");
                try {
                    directoryStreamNewDirectoryStream.forEach(new Consumer() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            GC.lambda$8(instantMinus, (Path) obj);
                        }
                    });
                } finally {
                    if (directoryStreamNewDirectoryStream != null) {
                        directoryStreamNewDirectoryStream.close();
                    }
                }
            } catch (Throwable th2) {
                if (0 == 0) {
                    throw th2;
                }
                if (null != th2) {
                    try {
                        th.addSuppressed(th2);
                    } catch (IOException e) {
                        LOG.error(e.getMessage(), (Throwable) e);
                        return;
                    }
                }
                throw null;
            }
        }
    }

    static /* synthetic */ void lambda$8(Instant instant, Path path) {
        try {
            if (Files.getLastModifiedTime(path, new LinkOption[0]).toInstant().isBefore(instant)) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), (Throwable) e);
        }
    }

    private Set<ObjectId> listRefLogObjects(Ref ref, long j) throws IOException {
        ReflogReader reflogReader = this.repo.getReflogReader(ref.getName());
        if (reflogReader == null) {
            return Collections.emptySet();
        }
        List<ReflogEntry> reverseEntries = reflogReader.getReverseEntries();
        if (reverseEntries == null || reverseEntries.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        for (ReflogEntry reflogEntry : reverseEntries) {
            if (reflogEntry.getWho().getWhen().getTime() < j) {
                break;
            }
            ObjectId newId = reflogEntry.getNewId();
            if (newId != null && !ObjectId.zeroId().equals((AnyObjectId) newId)) {
                hashSet.add(newId);
            }
            ObjectId oldId = reflogEntry.getOldId();
            if (oldId != null && !ObjectId.zeroId().equals((AnyObjectId) oldId)) {
                hashSet.add(oldId);
            }
        }
        return hashSet;
    }

    private Collection<Ref> getAllRefs() throws IOException {
        RefDatabase refDatabase = this.repo.getRefDatabase();
        List<Ref> refs = refDatabase.getRefs();
        List<Ref> additionalRefs = refDatabase.getAdditionalRefs();
        if (additionalRefs.isEmpty()) {
            return refs;
        }
        ArrayList arrayList = new ArrayList(refs.size() + additionalRefs.size());
        arrayList.addAll(refs);
        for (Ref ref : additionalRefs) {
            checkCancelled();
            if (ref.getName().startsWith(Constants.R_REFS)) {
                arrayList.add(ref);
            }
        }
        return arrayList;
    }

    private Set<ObjectId> listNonHEADIndexObjects() throws IOException {
        if (this.repo.isBare()) {
            return Collections.emptySet();
        }
        Throwable th = null;
        try {
            TreeWalk treeWalk = new TreeWalk(this.repo);
            try {
                treeWalk.addTree(new DirCacheIterator(this.repo.readDirCache()));
                ObjectId objectIdResolve = this.repo.resolve("HEAD");
                if (objectIdResolve != null) {
                    try {
                        RevWalk revWalk = new RevWalk(this.repo);
                        try {
                            treeWalk.addTree(revWalk.parseTree(objectIdResolve));
                            revWalk.close();
                        } catch (Throwable th2) {
                            revWalk.close();
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        if (0 == 0) {
                            throw th3;
                        }
                        if (null == th3) {
                            throw null;
                        }
                        th.addSuppressed(th3);
                        throw null;
                    }
                }
                treeWalk.setFilter(TreeFilter.ANY_DIFF);
                treeWalk.setRecursive(true);
                HashSet hashSet = new HashSet();
                while (treeWalk.next()) {
                    checkCancelled();
                    ObjectId objectId = treeWalk.getObjectId(0);
                    int rawMode = treeWalk.getRawMode(0) & 61440;
                    if (rawMode != 0) {
                        if (rawMode == 16384 || rawMode == 32768 || rawMode == 40960) {
                            hashSet.add(objectId);
                        } else if (rawMode != 57344) {
                            throw new IOException(MessageFormat.format(JGitText.get().corruptObjectInvalidMode3, String.format("%o", Integer.valueOf(treeWalk.getRawMode(0))), objectId == null ? "null" : objectId.name(), treeWalk.getPathString(), this.repo.getIndexFile()));
                        }
                    }
                }
                treeWalk.close();
                return hashSet;
            } catch (Throwable th4) {
                treeWalk.close();
                throw th4;
            }
        } catch (Throwable th5) {
            if (0 == 0) {
                throw th5;
            }
            if (null == th5) {
                throw null;
            }
            th.addSuppressed(th5);
            throw null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:202:0x0348  */
    /* JADX WARN: Code duplicated, block: B:250:0x0354 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:252:0x0342 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v3, types: [org.eclipse.jgit.lib.ObjectReader] */
    private Pack writePack(Set<? extends ObjectId> set, Set<? extends ObjectId> set2, Set<ObjectId> set3, Set<ObjectId> set4, List<ObjectIdSet> list) throws IOException {
        ?? NewObjectReader;
        checkCancelled();
        TreeMap treeMap = new TreeMap(new Comparator() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return GC.lambda$9((PackExt) obj, (PackExt) obj2);
            }
        });
        Throwable th = null;
        try {
            PackConfig packConfig = this.pconfig;
            NewObjectReader = this.repo.newObjectReader();
            PackWriter packWriter = new PackWriter(packConfig, (ObjectReader) NewObjectReader);
            boolean z = true;
            try {
                try {
                    packWriter.setDeltaBaseAsOffset(true);
                    packWriter.setReuseDeltaCommits(false);
                    if (set4 != null) {
                        packWriter.setTagTargets(set4);
                    }
                    if (list != null) {
                        Iterator<ObjectIdSet> it = list.iterator();
                        while (it.hasNext()) {
                            packWriter.excludeObjects(it.next());
                        }
                    }
                    packWriter.preparePack(this.pm, set, set2, PackWriter.NONE, set3);
                    if (packWriter.getObjectCount() != 0) {
                        checkCancelled();
                        ObjectId objectIdComputeName = packWriter.computeName();
                        File packDirectory = this.repo.getObjectDatabase().getPackDirectory();
                        packDirectory.mkdirs();
                        File fileCreateTempFile = File.createTempFile("gc_", ".pack_tmp", packDirectory);
                        try {
                            String strSubstring = fileCreateTempFile.getName().substring(0, fileCreateTempFile.getName().lastIndexOf(46));
                            File file = new File(packDirectory, String.valueOf(strSubstring) + ".idx_tmp");
                            treeMap.put(PackExt.INDEX, file);
                            if (!file.createNewFile()) {
                                throw new IOException(MessageFormat.format(JGitText.get().cannotCreateIndexfile, file.getPath()));
                            }
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
                                try {
                                    FileChannel channel = fileOutputStream.getChannel();
                                    try {
                                        OutputStream outputStreamNewOutputStream = Channels.newOutputStream(channel);
                                        try {
                                            ProgressMonitor progressMonitor = this.pm;
                                            packWriter.writePack(progressMonitor, progressMonitor, outputStreamNewOutputStream);
                                            channel.force(true);
                                            if (outputStreamNewOutputStream != null) {
                                                outputStreamNewOutputStream.close();
                                            }
                                            if (channel != null) {
                                                channel.close();
                                            }
                                            fileOutputStream.close();
                                            try {
                                                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                                                try {
                                                    FileChannel channel2 = fileOutputStream2.getChannel();
                                                    try {
                                                        OutputStream outputStreamNewOutputStream2 = Channels.newOutputStream(channel2);
                                                        try {
                                                            packWriter.writeIndex(outputStreamNewOutputStream2);
                                                            channel2.force(true);
                                                            if (outputStreamNewOutputStream2 != null) {
                                                                outputStreamNewOutputStream2.close();
                                                            }
                                                            if (channel2 != null) {
                                                                channel2.close();
                                                            }
                                                            fileOutputStream2.close();
                                                            if (packWriter.prepareBitmapIndex(this.pm)) {
                                                                File file2 = new File(packDirectory, String.valueOf(strSubstring) + ".bitmap_tmp");
                                                                treeMap.put(PackExt.BITMAP_INDEX, file2);
                                                                if (!file2.createNewFile()) {
                                                                    throw new IOException(MessageFormat.format(JGitText.get().cannotCreateIndexfile, file2.getPath()));
                                                                }
                                                                try {
                                                                    FileOutputStream fileOutputStream3 = new FileOutputStream(file2);
                                                                    try {
                                                                        FileChannel channel3 = fileOutputStream3.getChannel();
                                                                        try {
                                                                            OutputStream outputStreamNewOutputStream3 = Channels.newOutputStream(channel3);
                                                                            try {
                                                                                packWriter.writeBitmapIndex(outputStreamNewOutputStream3);
                                                                                channel3.force(true);
                                                                                if (outputStreamNewOutputStream3 != null) {
                                                                                    outputStreamNewOutputStream3.close();
                                                                                }
                                                                                if (channel3 != null) {
                                                                                    channel3.close();
                                                                                }
                                                                                fileOutputStream3.close();
                                                                            } catch (Throwable th2) {
                                                                                if (outputStreamNewOutputStream3 == null) {
                                                                                    throw th2;
                                                                                }
                                                                                outputStreamNewOutputStream3.close();
                                                                                throw th2;
                                                                            }
                                                                        } catch (Throwable th3) {
                                                                            if (0 == 0) {
                                                                                th = th3;
                                                                            } else if (null != th3) {
                                                                                th.addSuppressed(th3);
                                                                            }
                                                                            if (channel3 == null) {
                                                                                throw th;
                                                                            }
                                                                            channel3.close();
                                                                            throw th;
                                                                        }
                                                                    } catch (Throwable th4) {
                                                                        if (0 == 0) {
                                                                            th = th4;
                                                                        } else if (null != th4) {
                                                                            th.addSuppressed(th4);
                                                                        }
                                                                        fileOutputStream3.close();
                                                                        throw th;
                                                                    }
                                                                } catch (Throwable th5) {
                                                                    if (0 == 0) {
                                                                        throw th5;
                                                                    }
                                                                    if (null == th5) {
                                                                        throw null;
                                                                    }
                                                                    th.addSuppressed(th5);
                                                                    throw null;
                                                                }
                                                            }
                                                            File packDirectory2 = this.repo.getObjectDatabase().getPackDirectory();
                                                            PackFile packFile = new PackFile(packDirectory2, objectIdComputeName, PackExt.PACK);
                                                            this.repo.getObjectDatabase().closeAllPackHandles(packFile);
                                                            fileCreateTempFile.setReadOnly();
                                                            FileUtils.rename(fileCreateTempFile, packFile, StandardCopyOption.ATOMIC_MOVE);
                                                            for (Map.Entry entry : treeMap.entrySet()) {
                                                                File file3 = (File) entry.getValue();
                                                                file3.setReadOnly();
                                                                PackFile packFile2 = new PackFile(packDirectory2, objectIdComputeName, (PackExt) entry.getKey());
                                                                try {
                                                                    FileUtils.rename(file3, packFile2, StandardCopyOption.ATOMIC_MOVE);
                                                                } catch (IOException e) {
                                                                    e = e;
                                                                    File file4 = new File(packFile2.getParentFile(), String.valueOf(packFile2.getName()) + ".new");
                                                                    try {
                                                                        FileUtils.rename(file3, file4, StandardCopyOption.ATOMIC_MOVE);
                                                                        file3 = file4;
                                                                    } catch (IOException e2) {
                                                                        e = e2;
                                                                    }
                                                                    throw new IOException(MessageFormat.format(JGitText.get().panicCantRenameIndexFile, file3, packFile2), e);
                                                                }
                                                            }
                                                            try {
                                                                FileSnapshot fileSnapshotSave = FileSnapshot.save(packFile);
                                                                if (this.pconfig.doWaitPreventRacyPack(fileSnapshotSave.size())) {
                                                                    fileSnapshotSave.waitUntilNotRacy();
                                                                }
                                                                z = false;
                                                            } catch (InterruptedException unused) {
                                                            }
                                                            try {
                                                                Pack packOpenPack = this.repo.getObjectDatabase().openPack(packFile);
                                                                if (z) {
                                                                    Thread.currentThread().interrupt();
                                                                }
                                                                packWriter.close();
                                                                if (fileCreateTempFile != null && fileCreateTempFile.exists()) {
                                                                    fileCreateTempFile.delete();
                                                                }
                                                                for (File file5 : treeMap.values()) {
                                                                    if (file5.exists()) {
                                                                        file5.delete();
                                                                    }
                                                                }
                                                                return packOpenPack;
                                                            } catch (Throwable th6) {
                                                                if (z) {
                                                                    Thread.currentThread().interrupt();
                                                                }
                                                                throw th6;
                                                            }
                                                        } catch (Throwable th7) {
                                                            if (outputStreamNewOutputStream2 == null) {
                                                                throw th7;
                                                            }
                                                            outputStreamNewOutputStream2.close();
                                                            throw th7;
                                                        }
                                                    } catch (Throwable th8) {
                                                        if (0 == 0) {
                                                            th = th8;
                                                        } else if (null != th8) {
                                                            th.addSuppressed(th8);
                                                        }
                                                        if (channel2 == null) {
                                                            throw th;
                                                        }
                                                        channel2.close();
                                                        throw th;
                                                    }
                                                } catch (Throwable th9) {
                                                    if (0 == 0) {
                                                        th = th9;
                                                    } else if (null != th9) {
                                                        th.addSuppressed(th9);
                                                    }
                                                    fileOutputStream2.close();
                                                    throw th;
                                                }
                                            } catch (Throwable th10) {
                                                if (0 == 0) {
                                                    throw th10;
                                                }
                                                if (null == th10) {
                                                    throw null;
                                                }
                                                th.addSuppressed(th10);
                                                throw null;
                                            }
                                        } catch (Throwable th11) {
                                            if (outputStreamNewOutputStream == null) {
                                                throw th11;
                                            }
                                            outputStreamNewOutputStream.close();
                                            throw th11;
                                        }
                                    } catch (Throwable th12) {
                                        if (0 == 0) {
                                            th = th12;
                                        } else if (null != th12) {
                                            th.addSuppressed(th12);
                                        }
                                        if (channel == null) {
                                            throw th;
                                        }
                                        channel.close();
                                        throw th;
                                    }
                                } catch (Throwable th13) {
                                    if (0 == 0) {
                                        th = th13;
                                    } else if (null != th13) {
                                        th.addSuppressed(th13);
                                    }
                                    fileOutputStream.close();
                                    throw th;
                                }
                            } catch (Throwable th14) {
                                if (0 == 0) {
                                    throw th14;
                                }
                                if (null == th14) {
                                    throw null;
                                }
                                th.addSuppressed(th14);
                                throw null;
                            }
                        } catch (Throwable th15) {
                            th = th15;
                            Throwable th16 = th;
                            packWriter.close();
                            throw th16;
                        }
                    }
                    packWriter.close();
                    for (File file6 : treeMap.values()) {
                        if (file6.exists()) {
                            file6.delete();
                        }
                    }
                    return null;
                } catch (Throwable th17) {
                    th = th17;
                    if (0 == 0) {
                        throw th;
                    }
                    if (null == th) {
                        throw null;
                    }
                    try {
                        th.addSuppressed(th);
                        throw null;
                    } catch (Throwable th18) {
                        if (NewObjectReader != 0) {
                            NewObjectReader.delete();
                        }
                        for (File file7 : treeMap.values()) {
                            if (file7.exists()) {
                                file7.delete();
                            }
                        }
                        throw th18;
                    }
                    if (NewObjectReader != 0 && NewObjectReader.exists()) {
                        NewObjectReader.delete();
                    }
                    while (r2.hasNext()) {
                        if (file7.exists()) {
                            file7.delete();
                        }
                    }
                    throw th18;
                }
            } catch (Throwable th19) {
                th = th19;
            }
        } catch (Throwable th20) {
            th = th20;
            NewObjectReader = 0;
        }
    }

    static /* synthetic */ int lambda$9(PackExt packExt, PackExt packExt2) {
        if (packExt == packExt2) {
            return 0;
        }
        if (packExt == PackExt.INDEX) {
            return 1;
        }
        if (packExt2 == PackExt.INDEX) {
            return -1;
        }
        return Integer.signum(packExt.hashCode() - packExt2.hashCode());
    }

    private void checkCancelled() throws CancelledException {
        if (this.pm.isCancelled() || Thread.currentThread().isInterrupted()) {
            throw new CancelledException(JGitText.get().operationCanceled);
        }
    }

    public static class RepoStatistics {
        public long numberOfBitmaps;
        public long numberOfLooseObjects;
        public long numberOfLooseRefs;
        public long numberOfPackFiles;
        public long numberOfPackedObjects;
        public long numberOfPackedRefs;
        public long sizeOfLooseObjects;
        public long sizeOfPackedObjects;

        public String toString() {
            StringBuilder sb = new StringBuilder("numberOfPackedObjects=");
            sb.append(this.numberOfPackedObjects);
            sb.append(", numberOfPackFiles=").append(this.numberOfPackFiles);
            sb.append(", numberOfLooseObjects=").append(this.numberOfLooseObjects);
            sb.append(", numberOfLooseRefs=").append(this.numberOfLooseRefs);
            sb.append(", numberOfPackedRefs=").append(this.numberOfPackedRefs);
            sb.append(", sizeOfLooseObjects=").append(this.sizeOfLooseObjects);
            sb.append(", sizeOfPackedObjects=").append(this.sizeOfPackedObjects);
            sb.append(", numberOfBitmaps=").append(this.numberOfBitmaps);
            return sb.toString();
        }
    }

    public RepoStatistics getStatistics() throws IOException {
        File[] fileArrListFiles;
        RepoStatistics repoStatistics = new RepoStatistics();
        for (Pack pack : this.repo.getObjectDatabase().getPacks()) {
            repoStatistics.numberOfPackedObjects += pack.getIndex().getObjectCount();
            repoStatistics.numberOfPackFiles++;
            repoStatistics.sizeOfPackedObjects += pack.getPackFile().length();
            if (pack.getBitmapIndex() != null) {
                repoStatistics.numberOfBitmaps += (long) pack.getBitmapIndex().getBitmapCount();
            }
        }
        File objectsDirectory = this.repo.getObjectsDirectory();
        String[] list = objectsDirectory.list();
        if (list != null && list.length > 0) {
            for (String str : list) {
                if (str.length() == 2 && (fileArrListFiles = new File(objectsDirectory, str).listFiles()) != null) {
                    for (File file : fileArrListFiles) {
                        if (file.getName().length() == 38) {
                            repoStatistics.numberOfLooseObjects++;
                            repoStatistics.sizeOfLooseObjects += file.length();
                        }
                    }
                }
            }
        }
        Iterator<Ref> it = this.repo.getRefDatabase().getRefs().iterator();
        while (it.hasNext()) {
            Ref.Storage storage = it.next().getStorage();
            if (storage == Ref.Storage.LOOSE || storage == Ref.Storage.LOOSE_PACKED) {
                repoStatistics.numberOfLooseRefs++;
            }
            if (storage == Ref.Storage.PACKED || storage == Ref.Storage.LOOSE_PACKED) {
                repoStatistics.numberOfPackedRefs++;
            }
        }
        return repoStatistics;
    }

    public GC setProgressMonitor(ProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        this.pm = progressMonitor;
        return this;
    }

    public void setExpireAgeMillis(long j) {
        this.expireAgeMillis = j;
        this.expire = null;
    }

    public void setPackExpireAgeMillis(long j) {
        this.packExpireAgeMillis = j;
        this.expire = null;
    }

    public void setPackConfig(PackConfig packConfig) {
        this.pconfig = packConfig;
    }

    public void setExpire(Date date) {
        this.expire = date;
        this.expireAgeMillis = -1L;
    }

    public void setPackExpire(Date date) {
        this.packExpire = date;
        this.packExpireAgeMillis = -1L;
    }

    public void setAuto(boolean z) {
        this.automatic = z;
    }

    void setBackground(boolean z) {
        this.background = z;
    }

    private boolean needGc() {
        if (tooManyPacks()) {
            addRepackAllOption();
            return true;
        }
        return tooManyLooseObjects();
    }

    boolean tooManyPacks() {
        int i = this.repo.getConfig().getInt(ConfigConstants.CONFIG_GC_SECTION, ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT, 50);
        return i > 0 && this.repo.getObjectDatabase().getPacks().size() > i + 1;
    }

    boolean tooManyLooseObjects() {
        int looseObjectLimit = getLooseObjectLimit();
        if (looseObjectLimit <= 0) {
            return false;
        }
        int i = (looseObjectLimit + 255) / 256;
        Path pathResolve = this.repo.getObjectsDirectory().toPath().resolve("17");
        if (!pathResolve.toFile().exists()) {
            return false;
        }
        Throwable th = null;
        try {
            DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(pathResolve, (DirectoryStream.Filter<? super Path>) new DirectoryStream.Filter() { // from class: org.eclipse.jgit.internal.storage.file.GC$$ExternalSyntheticLambda6
                @Override // java.nio.file.DirectoryStream.Filter
                public final boolean accept(Object obj) {
                    return GC.lambda$10((Path) obj);
                }
            });
            try {
                Iterator<Path> it = directoryStreamNewDirectoryStream.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    i2++;
                    if (i2 > i) {
                        if (directoryStreamNewDirectoryStream != null) {
                            directoryStreamNewDirectoryStream.close();
                        }
                        return true;
                    }
                    it.next();
                }
                if (directoryStreamNewDirectoryStream != null) {
                    directoryStreamNewDirectoryStream.close();
                }
                return false;
            } catch (Throwable th2) {
                if (directoryStreamNewDirectoryStream == null) {
                    throw th2;
                }
                directoryStreamNewDirectoryStream.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                try {
                    th.addSuppressed(th3);
                } catch (IOException e) {
                    LOG.error(e.getMessage(), (Throwable) e);
                }
            }
            throw null;
        }
    }

    static /* synthetic */ boolean lambda$10(Path path) throws IOException {
        Path fileName = path.getFileName();
        return path.toFile().isFile() && fileName != null && PATTERN_LOOSE_OBJECT.matcher(fileName.toString()).matches();
    }

    private int getLooseObjectLimit() {
        return this.repo.getConfig().getInt(ConfigConstants.CONFIG_GC_SECTION, "auto", DEFAULT_AUTOLIMIT);
    }
}
