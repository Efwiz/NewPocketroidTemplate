package org.eclipse.jgit.internal.storage.file;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public class RefDirectory extends RefDatabase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String PACKED_REFS_HEADER = "# pack-refs with:";
    public static final String PACKED_REFS_PEELED = " peeled";
    public static final String SYMREF = "ref: ";
    private final File gitDir;
    final ReentrantLock inProcessPackedRefsLock;
    private final AtomicInteger lastNotifiedModCnt;
    final File logsDir;
    final File logsRefsDir;
    private final AtomicReference<RefList<LooseRef>> looseRefs;
    private final AtomicInteger modCnt;
    final AtomicReference<PackedRefList> packedRefs;
    final File packedRefsFile;
    private final FileRepository parent;
    final File refsDir;
    private List<Integer> retrySleepMs;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) RefDirectory.class);
    private static final String[] additionalRefsNames = {Constants.MERGE_HEAD, Constants.FETCH_HEAD, Constants.ORIG_HEAD, Constants.CHERRY_PICK_HEAD};
    private static final List<Integer> RETRY_SLEEP_MS = Collections.unmodifiableList(Arrays.asList(0, 100, 200, 400, Integer.valueOf(ParserBasicInformation.NUM_RULES), 1600));
    private static final PackedRefList NO_PACKED_REFS = new PackedRefList(RefList.emptyList(), FileSnapshot.MISSING_FILE, ObjectId.zeroId(), null);

    private interface LooseRef extends Ref {
        FileSnapshot getSnapShot();

        LooseRef peel(ObjectIdRef objectIdRef);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private RefList<Ref> upcast(RefList<? extends Ref> refList) {
        return refList;
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public boolean performsAtomicTransactions() {
        return true;
    }

    RefDirectory(FileRepository fileRepository) {
        AtomicReference<RefList<LooseRef>> atomicReference = new AtomicReference<>();
        this.looseRefs = atomicReference;
        AtomicReference<PackedRefList> atomicReference2 = new AtomicReference<>();
        this.packedRefs = atomicReference2;
        this.inProcessPackedRefsLock = new ReentrantLock(true);
        this.modCnt = new AtomicInteger();
        this.lastNotifiedModCnt = new AtomicInteger();
        this.retrySleepMs = RETRY_SLEEP_MS;
        FS fs = fileRepository.getFS();
        this.parent = fileRepository;
        File directory = fileRepository.getDirectory();
        this.gitDir = directory;
        this.refsDir = fs.resolve(directory, Constants.R_REFS);
        this.logsDir = fs.resolve(directory, Constants.LOGS);
        this.logsRefsDir = fs.resolve(directory, "logs/refs/");
        this.packedRefsFile = fs.resolve(directory, Constants.PACKED_REFS);
        atomicReference.set(RefList.emptyList());
        atomicReference2.set(NO_PACKED_REFS);
    }

    Repository getRepository() {
        return this.parent;
    }

    ReflogWriter newLogWriter(boolean z) {
        return new ReflogWriter(this, z);
    }

    public File logFor(String str) {
        if (str.startsWith(Constants.R_REFS)) {
            return new File(this.logsRefsDir, str.substring(Constants.R_REFS.length()));
        }
        return new File(this.logsDir, str);
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public void create() throws IOException {
        FileUtils.mkdir(this.refsDir);
        FileUtils.mkdir(new File(this.refsDir, Constants.R_HEADS.substring(Constants.R_REFS.length())));
        FileUtils.mkdir(new File(this.refsDir, Constants.R_TAGS.substring(Constants.R_REFS.length())));
        newLogWriter(false).create();
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public void close() {
        clearReferences();
    }

    private void clearReferences() {
        this.looseRefs.set(RefList.emptyList());
        this.packedRefs.set(NO_PACKED_REFS);
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public void refresh() {
        super.refresh();
        clearReferences();
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public boolean isNameConflicting(String str) throws IOException {
        int iLastIndexOf = str.lastIndexOf(47);
        while (iLastIndexOf > 0) {
            if (exactRef(str.substring(0, iLastIndexOf)) != null) {
                return true;
            }
            iLastIndexOf = str.lastIndexOf(47, iLastIndexOf - 1);
        }
        return !getRefsByPrefix(String.valueOf(str) + '/').isEmpty();
    }

    @Nullable
    private Ref readAndResolve(String str, RefList<Ref> refList) throws IOException {
        try {
            Ref ref = readRef(str, refList);
            return ref != null ? resolve(ref, 0, null, null, refList) : ref;
        } catch (IOException e) {
            if (str.contains("/") || !(e.getCause() instanceof InvalidObjectIdException)) {
                throw e;
            }
            return null;
        }
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public Ref exactRef(String str) throws IOException {
        try {
            return readAndResolve(str, getPackedRefs());
        } finally {
            fireRefsChanged();
        }
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public Map<String, Ref> exactRef(String... strArr) throws IOException {
        try {
            PackedRefList packedRefs = getPackedRefs();
            HashMap map = new HashMap(strArr.length);
            for (String str : strArr) {
                Ref andResolve = readAndResolve(str, packedRefs);
                if (andResolve != null) {
                    map.put(str, andResolve);
                }
            }
            fireRefsChanged();
            return map;
        } catch (Throwable th) {
            fireRefsChanged();
            throw th;
        }
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    @Nullable
    public Ref firstExactRef(String... strArr) throws IOException {
        try {
            PackedRefList packedRefs = getPackedRefs();
            for (String str : strArr) {
                Ref andResolve = readAndResolve(str, packedRefs);
                if (andResolve != null) {
                    return andResolve;
                }
            }
            return null;
        } finally {
            fireRefsChanged();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.eclipse.jgit.lib.RefDatabase
    public Map<String, Ref> getRefs(String str) throws IOException {
        RefList refListRemove = this.looseRefs.get();
        LooseScanner looseScanner = new LooseScanner(refListRemove);
        looseScanner.scan(str);
        PackedRefList packedRefs = getPackedRefs();
        if (looseScanner.newLoose != null) {
            looseScanner.newLoose.sort();
            RefList refList = looseScanner.newLoose.toRefList();
            if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refListRemove, refList)) {
                this.modCnt.incrementAndGet();
            }
            refListRemove = refList;
        }
        fireRefsChanged();
        RefList.Builder<Ref> builder = looseScanner.symbolic;
        int i = 0;
        while (i < builder.size()) {
            Ref ref = builder.get(i);
            Ref refResolve = resolve(ref, 0, str, refListRemove, packedRefs);
            if (refResolve != null && refResolve.getObjectId() != null) {
                builder.set(i, refResolve);
                i++;
            } else {
                builder.remove(i);
                int iFind = refListRemove.find(ref.getName());
                if (iFind >= 0) {
                    refListRemove = refListRemove.remove(iFind);
                }
            }
        }
        builder.sort();
        return new RefMap(str, packedRefs, upcast(refListRemove), builder.toRefList());
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public List<Ref> getAdditionalRefs() throws IOException {
        LinkedList linkedList = new LinkedList();
        for (String str : additionalRefsNames) {
            Ref refExactRef = exactRef(str);
            if (refExactRef != null) {
                linkedList.add(refExactRef);
            }
        }
        return linkedList;
    }

    private class LooseScanner {
        private int curIdx;
        private final RefList<LooseRef> curLoose;
        RefList.Builder<LooseRef> newLoose;
        final RefList.Builder<Ref> symbolic = new RefList.Builder<>(4);

        LooseScanner(RefList<LooseRef> refList) {
            this.curLoose = refList;
        }

        void scan(String str) {
            if ("".equals(str)) {
                scanOne("HEAD");
                scanTree(Constants.R_REFS, RefDirectory.this.refsDir);
                if (this.newLoose != null || this.curIdx >= this.curLoose.size()) {
                    return;
                }
                this.newLoose = this.curLoose.copy(this.curIdx);
                return;
            }
            if (str.startsWith(Constants.R_REFS) && str.endsWith("/")) {
                this.curIdx = -(this.curLoose.find(str) + 1);
                scanTree(str, new File(RefDirectory.this.refsDir, str.substring(Constants.R_REFS.length())));
                while (this.curIdx < this.curLoose.size() && ((LooseRef) this.curLoose.get(this.curIdx)).getName().startsWith(str)) {
                    if (this.newLoose == null) {
                        this.newLoose = this.curLoose.copy(this.curIdx);
                    }
                    this.curIdx++;
                }
                if (this.newLoose != null) {
                    while (this.curIdx < this.curLoose.size()) {
                        RefList.Builder<LooseRef> builder = this.newLoose;
                        RefList<LooseRef> refList = this.curLoose;
                        int i = this.curIdx;
                        this.curIdx = i + 1;
                        builder.add((LooseRef) refList.get(i));
                    }
                }
            }
        }

        private boolean scanTree(String str, File file) {
            String[] list = file.list(LockFile.FILTER);
            if (list == null) {
                return false;
            }
            if (list.length > 0) {
                for (int i = 0; i < list.length; i++) {
                    if (new File(file, list[i]).isDirectory()) {
                        list[i] = String.valueOf(list[i]) + '/';
                    }
                }
                Arrays.sort(list);
                for (String str2 : list) {
                    if (str2.charAt(str2.length() - 1) == '/') {
                        scanTree(String.valueOf(str) + str2, new File(file, str2));
                    } else {
                        scanOne(String.valueOf(str) + str2);
                    }
                }
            }
            return true;
        }

        private void scanOne(String str) {
            LooseRef looseRef;
            LooseRef looseRefScanRef = null;
            if (this.curIdx >= this.curLoose.size()) {
                looseRef = null;
                break;
            }
            while (true) {
                looseRef = (LooseRef) this.curLoose.get(this.curIdx);
                int iCompareTo = RefComparator.compareTo(looseRef, str);
                if (iCompareTo >= 0) {
                    if (iCompareTo <= 0) {
                        break;
                    } else {
                        break;
                    }
                } else {
                    if (this.newLoose == null) {
                        this.newLoose = this.curLoose.copy(this.curIdx);
                    }
                    int i = this.curIdx + 1;
                    this.curIdx = i;
                    if (i >= this.curLoose.size()) {
                    }
                }
                looseRef = null;
                break;
            }
            try {
                looseRefScanRef = RefDirectory.this.scanRef(looseRef, str);
            } catch (IOException unused) {
            }
            if (looseRefScanRef != null) {
                if (looseRef != looseRefScanRef && this.newLoose == null) {
                    this.newLoose = this.curLoose.copy(this.curIdx);
                }
                RefList.Builder<LooseRef> builder = this.newLoose;
                if (builder != null) {
                    builder.add(looseRefScanRef);
                }
                if (looseRefScanRef.isSymbolic()) {
                    this.symbolic.add(looseRefScanRef);
                }
            } else if (looseRef != null && this.newLoose == null) {
                this.newLoose = this.curLoose.copy(this.curIdx);
            }
            if (looseRef != null) {
                this.curIdx++;
            }
        }
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public Ref peel(Ref ref) throws IOException {
        RefList<LooseRef> refList;
        int iFind;
        Ref leaf = ref.getLeaf();
        if (leaf.isPeeled() || leaf.getObjectId() == null) {
            return ref;
        }
        ObjectIdRef objectIdRefDoPeel = doPeel(leaf);
        if (leaf.getStorage().isLoose() && (iFind = (refList = this.looseRefs.get()).find(leaf.getName())) >= 0 && refList.get(iFind) == leaf) {
            LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList, refList.set(iFind, ((LooseRef) leaf).peel(objectIdRefDoPeel)));
        }
        return recreate(ref, objectIdRefDoPeel);
    }

    private ObjectIdRef doPeel(Ref ref) throws IOException {
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(getRepository());
            try {
                RevObject any = revWalk.parseAny(ref.getObjectId());
                if (any instanceof RevTag) {
                    return new ObjectIdRef.PeeledTag(ref.getStorage(), ref.getName(), ref.getObjectId(), revWalk.peel(any).copy());
                }
                return new ObjectIdRef.PeeledNonTag(ref.getStorage(), ref.getName(), ref.getObjectId());
            } finally {
                revWalk.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    private static Ref recreate(Ref ref, ObjectIdRef objectIdRef) {
        if (!ref.isSymbolic()) {
            return objectIdRef;
        }
        return new SymbolicRef(ref.getName(), recreate(ref.getTarget(), objectIdRef));
    }

    void storedSymbolicRef(RefDirectoryUpdate refDirectoryUpdate, FileSnapshot fileSnapshot, String str) {
        putLooseRef(newSymbolicRef(fileSnapshot, refDirectoryUpdate.getRef().getName(), str));
        fireRefsChanged();
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public RefDirectoryUpdate newUpdate(String str, boolean z) throws IOException {
        PackedRefList packedRefs = getPackedRefs();
        Ref ref = readRef(str, packedRefs);
        if (ref != null) {
            ref = resolve(ref, 0, null, null, packedRefs);
        }
        boolean z2 = false;
        if (ref == null) {
            ref = new ObjectIdRef.Unpeeled(Ref.Storage.NEW, str, null);
        } else if (z && ref.isSymbolic()) {
            z2 = true;
        }
        RefDirectoryUpdate refDirectoryUpdate = new RefDirectoryUpdate(this, ref);
        if (z2) {
            refDirectoryUpdate.setDetachingSymbolicRef();
        }
        return refDirectoryUpdate;
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public RefDirectoryRename newRename(String str, String str2) throws IOException {
        return new RefDirectoryRename(newUpdate(str, false), newUpdate(str2, false));
    }

    @Override // org.eclipse.jgit.lib.RefDatabase
    public PackedBatchRefUpdate newBatchUpdate() {
        return new PackedBatchRefUpdate(this);
    }

    void stored(RefDirectoryUpdate refDirectoryUpdate, FileSnapshot fileSnapshot) {
        putLooseRef(new LooseUnpeeled(fileSnapshot, refDirectoryUpdate.getRef().getLeaf().getName(), refDirectoryUpdate.getNewObjectId().copy()));
    }

    private void putLooseRef(LooseRef looseRef) {
        RefList<LooseRef> refList;
        do {
            refList = this.looseRefs.get();
        } while (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList, refList.put(looseRef)));
        this.modCnt.incrementAndGet();
        fireRefsChanged();
    }

    void delete(RefDirectoryUpdate refDirectoryUpdate) throws IOException {
        RefList<LooseRef> refList;
        int iFind;
        Ref ref = refDirectoryUpdate.getRef();
        if (!refDirectoryUpdate.isDetachingSymbolicRef()) {
            ref = ref.getLeaf();
        }
        String name2 = ref.getName();
        PackedRefList packedRefs = getPackedRefs();
        if (packedRefs.contains(name2)) {
            this.inProcessPackedRefsLock.lock();
            try {
                LockFile lockFileLockPackedRefsOrThrow = lockPackedRefsOrThrow();
                try {
                    PackedRefList packedRefs2 = readPackedRefs();
                    int iFind2 = packedRefs2.find(name2);
                    if (iFind2 >= 0) {
                        commitPackedRefs(lockFileLockPackedRefsOrThrow, packedRefs2.remove(iFind2), packedRefs, true);
                    }
                    lockFileLockPackedRefsOrThrow.unlock();
                    this.inProcessPackedRefsLock.unlock();
                } catch (Throwable th) {
                    lockFileLockPackedRefsOrThrow.unlock();
                    throw th;
                }
            } catch (Throwable th2) {
                this.inProcessPackedRefsLock.unlock();
                throw th2;
            }
        }
        do {
            refList = this.looseRefs.get();
            iFind = refList.find(name2);
            if (iFind < 0) {
                break;
            }
        } while (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList, refList.remove(iFind)));
        int iLevelsIn = levelsIn(name2) - 2;
        delete(logFor(name2), iLevelsIn);
        if (ref.getStorage().isLoose()) {
            refDirectoryUpdate.unlock();
            delete(fileFor(name2), iLevelsIn);
        }
        this.modCnt.incrementAndGet();
        fireRefsChanged();
    }

    public void pack(List<String> list) throws IOException {
        pack(list, Collections.emptyMap());
    }

    PackedRefList pack(Map<String, LockFile> map) throws IOException {
        return pack(map.keySet(), map);
    }

    private PackedRefList pack(Collection<String> collection, Map<String, LockFile> map) throws IOException {
        boolean z;
        RefList<LooseRef> refList;
        int iFind;
        Ref refPeeledPackedRef;
        Iterator<LockFile> it = map.values().iterator();
        while (it.hasNext()) {
            it.next().requireLock();
        }
        LooseRef looseRef = null;
        if (collection.isEmpty()) {
            return null;
        }
        FS fs = this.parent.getFS();
        this.inProcessPackedRefsLock.lock();
        try {
            LockFile lockFileLockPackedRefsOrThrow = lockPackedRefsOrThrow();
            try {
                PackedRefList packedRefs = getPackedRefs();
                RefList<Ref> packedRefs2 = readPackedRefs();
                boolean z2 = false;
                for (String str : collection) {
                    Ref ref = readRef(str, packedRefs2);
                    if (ref != null && !ref.isSymbolic() && (refPeeledPackedRef = peeledPackedRef(ref)) != ref) {
                        int iFind2 = packedRefs2.find(str);
                        if (iFind2 >= 0) {
                            packedRefs2 = packedRefs2.set(iFind2, refPeeledPackedRef);
                        } else {
                            packedRefs2 = packedRefs2.add(iFind2, refPeeledPackedRef);
                        }
                        z2 = true;
                    }
                    looseRef = null;
                }
                if (z2) {
                    packedRefs = commitPackedRefs(lockFileLockPackedRefsOrThrow, packedRefs2, packedRefs, false);
                    for (String str2 : collection) {
                        File fileFileFor = fileFor(str2);
                        if (fs.exists(fileFileFor)) {
                            LockFile lockFile = map.get(str2);
                            if (lockFile == null) {
                                lockFile = new LockFile(fileFileFor);
                                if (lockFile.lock()) {
                                    z = true;
                                } else {
                                    continue;
                                }
                            } else {
                                z = false;
                            }
                            try {
                                LooseRef looseRefScanRef = scanRef(looseRef, str2);
                                if (looseRefScanRef != null && !looseRefScanRef.isSymbolic()) {
                                    Ref ref2 = packedRefs2.get(str2);
                                    ObjectId objectId = looseRefScanRef.getObjectId();
                                    if (objectId != null && objectId.equals((AnyObjectId) ref2.getObjectId())) {
                                        do {
                                            refList = this.looseRefs.get();
                                            iFind = refList.find(str2);
                                            if (iFind < 0) {
                                                break;
                                            }
                                        } while (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList, refList.remove(iFind)));
                                        delete(fileFileFor, levelsIn(str2) - 2, lockFile);
                                    }
                                    if (z) {
                                        lockFile.unlock();
                                    }
                                } else if (z) {
                                    lockFile.unlock();
                                }
                                looseRef = null;
                            } catch (Throwable th) {
                                if (z) {
                                    lockFile.unlock();
                                }
                                throw th;
                            }
                        }
                    }
                }
                lockFileLockPackedRefsOrThrow.unlock();
                this.inProcessPackedRefsLock.unlock();
                return packedRefs;
            } catch (Throwable th2) {
                lockFileLockPackedRefsOrThrow.unlock();
                throw th2;
            }
        } catch (Throwable th3) {
            this.inProcessPackedRefsLock.unlock();
            throw th3;
        }
    }

    @Nullable
    LockFile lockPackedRefs() throws IOException {
        LockFile lockFile = new LockFile(this.packedRefsFile);
        Iterator<Integer> it = getRetrySleepMs().iterator();
        while (it.hasNext()) {
            sleep(it.next().intValue());
            if (lockFile.lock()) {
                return lockFile;
            }
        }
        return null;
    }

    private LockFile lockPackedRefsOrThrow() throws IOException {
        LockFile lockFileLockPackedRefs = lockPackedRefs();
        if (lockFileLockPackedRefs != null) {
            return lockFileLockPackedRefs;
        }
        throw new LockFailedException(this.packedRefsFile);
    }

    private Ref peeledPackedRef(Ref ref) throws IOException {
        if (ref.getStorage().isPacked() && ref.isPeeled()) {
            return ref;
        }
        if (!ref.isPeeled()) {
            ref = peel(ref);
        }
        ObjectId peeledObjectId = ref.getPeeledObjectId();
        if (peeledObjectId != null) {
            return new ObjectIdRef.PeeledTag(Ref.Storage.PACKED, ref.getName(), ref.getObjectId(), peeledObjectId);
        }
        return new ObjectIdRef.PeeledNonTag(Ref.Storage.PACKED, ref.getName(), ref.getObjectId());
    }

    void log(boolean z, RefUpdate refUpdate, String str, boolean z2) throws IOException {
        newLogWriter(z).log(refUpdate, str, z2);
    }

    private Ref resolve(Ref ref, int i, String str, RefList<LooseRef> refList, RefList<Ref> refList2) throws IOException {
        Ref ref2;
        if (!ref.isSymbolic()) {
            return ref;
        }
        Ref target = ref.getTarget();
        if (5 <= i) {
            return null;
        }
        if (refList != null && target.getName().startsWith(str)) {
            int iFind = refList.find(target.getName());
            if (iFind >= 0) {
                ref2 = refList.get(iFind);
            } else {
                int iFind2 = refList2.find(target.getName());
                if (iFind2 < 0) {
                    return ref;
                }
                ref2 = refList2.get(iFind2);
            }
        } else {
            ref2 = readRef(target.getName(), refList2);
            if (ref2 == null) {
                return ref;
            }
        }
        Ref refResolve = resolve(ref2, i + 1, str, refList, refList2);
        if (refResolve == null) {
            return null;
        }
        return new SymbolicRef(ref.getName(), refResolve);
    }

    PackedRefList getPackedRefs() throws IOException {
        boolean z = getRepository().getConfig().getBoolean(ConfigConstants.CONFIG_CORE_SECTION, ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT, true);
        PackedRefList packedRefList = this.packedRefs.get();
        if (z && !packedRefList.snapshot.isModified(this.packedRefsFile)) {
            return packedRefList;
        }
        PackedRefList packedRefs = readPackedRefs();
        if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.packedRefs, packedRefList, packedRefs) && !packedRefList.f650id.equals((AnyObjectId) packedRefs.f650id)) {
            this.modCnt.incrementAndGet();
        }
        return packedRefs;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private PackedRefList readPackedRefs() throws IOException {
        int i = 0;
        while (true) {
            FileSnapshot fileSnapshotSave = FileSnapshot.save(this.packedRefsFile);
            MessageDigest messageDigestNewMessageDigest = Constants.newMessageDigest();
            Throwable th = null;
            Object[] objArr = 0;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DigestInputStream(new FileInputStream(this.packedRefsFile), messageDigestNewMessageDigest), StandardCharsets.UTF_8));
                try {
                    PackedRefList packedRefList = new PackedRefList(parsePackedRefs(bufferedReader), fileSnapshotSave, ObjectId.fromRaw(messageDigestNewMessageDigest.digest()), objArr == true ? 1 : 0);
                    bufferedReader.close();
                    return packedRefList;
                } catch (IOException e) {
                    try {
                        if (FileUtils.isStaleFileHandleInCausalChain(e) && i < 5) {
                            Logger logger = LOG;
                            if (logger.isDebugEnabled()) {
                                logger.debug(MessageFormat.format(JGitText.get().packedRefsHandleIsStale, Integer.valueOf(i)), (Throwable) e);
                            }
                            i++;
                            bufferedReader.close();
                        } else {
                            throw e;
                        }
                    } catch (Throwable th2) {
                        bufferedReader.close();
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                if (0 == 0) {
                    throw th3;
                }
                if (null != th3) {
                    try {
                        th.addSuppressed(th3);
                    } catch (FileNotFoundException e2) {
                        if (this.packedRefsFile.exists()) {
                            throw e2;
                        }
                        return NO_PACKED_REFS;
                    }
                }
                throw null;
            }
        }
    }

    private RefList<Ref> parsePackedRefs(BufferedReader bufferedReader) throws IOException {
        Ref unpeeled;
        RefList.Builder builder = new RefList.Builder();
        Ref ref = null;
        boolean z = false;
        boolean zContains = false;
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                if (line.charAt(0) == '#') {
                    if (line.startsWith(PACKED_REFS_HEADER)) {
                        zContains = line.substring(PACKED_REFS_HEADER.length()).contains(PACKED_REFS_PEELED);
                    }
                } else {
                    if (line.charAt(0) != '^') {
                        int iIndexOf = line.indexOf(32);
                        if (iIndexOf < 0) {
                            throw new IOException(MessageFormat.format(JGitText.get().packedRefsCorruptionDetected, this.packedRefsFile.getAbsolutePath()));
                        }
                        ObjectId objectIdFromString = ObjectId.fromString(line.substring(0, iIndexOf));
                        String strCopy = copy(line, iIndexOf + 1, line.length());
                        if (zContains) {
                            unpeeled = new ObjectIdRef.PeeledNonTag(Ref.Storage.PACKED, strCopy, objectIdFromString);
                        } else {
                            unpeeled = new ObjectIdRef.Unpeeled(Ref.Storage.PACKED, strCopy, objectIdFromString);
                        }
                        if (ref != null && RefComparator.compareTo(ref, unpeeled) > 0) {
                            z = true;
                        }
                        builder.add(unpeeled);
                    } else {
                        if (ref == null) {
                            throw new IOException(JGitText.get().peeledLineBeforeRef);
                        }
                        unpeeled = new ObjectIdRef.PeeledTag(Ref.Storage.PACKED, ref.getName(), ref.getObjectId(), ObjectId.fromString(line.substring(1)));
                        builder.set(builder.size() - 1, unpeeled);
                    }
                    ref = unpeeled;
                }
            } else {
                if (z) {
                    builder.sort();
                }
                return builder.toRefList();
            }
        }
    }

    private static String copy(String str, int i, int i2) {
        return new StringBuilder(i2 - i).append((CharSequence) str, i, i2).toString();
    }

    /* JADX INFO: renamed from: org.eclipse.jgit.internal.storage.file.RefDirectory$1, reason: invalid class name */
    class AnonymousClass1 extends RefWriter {
        private final /* synthetic */ boolean val$changed;
        private final /* synthetic */ LockFile val$lck;
        private final /* synthetic */ PackedRefList val$oldPackedList;
        private final /* synthetic */ RefList val$refs;
        private final /* synthetic */ AtomicReference val$result;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(RefList refList, LockFile lockFile, RefList refList2, PackedRefList packedRefList, boolean z, AtomicReference atomicReference) {
            super((RefList<Ref>) refList);
            this.val$lck = lockFile;
            this.val$refs = refList2;
            this.val$oldPackedList = packedRefList;
            this.val$changed = z;
            this.val$result = atomicReference;
        }

        @Override // org.eclipse.jgit.lib.RefWriter
        protected void writeFile(String str, byte[] bArr) throws IOException {
            this.val$lck.setFSync(true);
            this.val$lck.setNeedSnapshot(true);
            try {
                this.val$lck.write(bArr);
                try {
                    this.val$lck.waitForStatChange();
                    if (!this.val$lck.commit()) {
                        throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToWrite, str));
                    }
                    final PackedRefList packedRefList = new PackedRefList(this.val$refs, this.val$lck.getCommitSnapshot(), ObjectId.fromRaw(Constants.newMessageDigest().digest(bArr)), null);
                    AtomicReference<PackedRefList> atomicReference = RefDirectory.this.packedRefs;
                    final PackedRefList packedRefList2 = this.val$oldPackedList;
                    if (!atomicReference.updateAndGet(new UnaryOperator() { // from class: org.eclipse.jgit.internal.storage.file.RefDirectory$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return RefDirectory.AnonymousClass1.lambda$0(packedRefList2, packedRefList, (RefDirectory.PackedRefList) obj);
                        }
                    }).f650id.equals((AnyObjectId) packedRefList.f650id)) {
                        throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToWrite, str));
                    }
                    if (this.val$changed) {
                        RefDirectory.this.modCnt.incrementAndGet();
                    }
                    this.val$result.set(packedRefList);
                } catch (InterruptedException e) {
                    this.val$lck.unlock();
                    throw new ObjectWritingException(MessageFormat.format(JGitText.get().interruptedWriting, str), e);
                }
            } catch (IOException e2) {
                throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToWrite, str), e2);
            }
        }

        static /* synthetic */ PackedRefList lambda$0(PackedRefList packedRefList, PackedRefList packedRefList2, PackedRefList packedRefList3) {
            return packedRefList3.f650id.equals((AnyObjectId) packedRefList.f650id) ? packedRefList2 : packedRefList3;
        }
    }

    PackedRefList commitPackedRefs(LockFile lockFile, RefList<Ref> refList, PackedRefList packedRefList, boolean z) throws IOException {
        AtomicReference atomicReference = new AtomicReference();
        new AnonymousClass1(refList, lockFile, refList, packedRefList, z, atomicReference).writePackedRefs();
        return (PackedRefList) atomicReference.get();
    }

    private Ref readRef(String str, RefList<Ref> refList) throws IOException {
        RefList<LooseRef> refList2 = this.looseRefs.get();
        int iFind = refList2.find(str);
        if (iFind >= 0) {
            LooseRef looseRef = (LooseRef) refList2.get(iFind);
            LooseRef looseRefScanRef = scanRef(looseRef, str);
            if (looseRefScanRef == null) {
                if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList2, refList2.remove(iFind))) {
                    this.modCnt.incrementAndGet();
                }
                return refList.get(str);
            }
            if (looseRef != looseRefScanRef && LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList2, refList2.set(iFind, looseRefScanRef))) {
                this.modCnt.incrementAndGet();
            }
            return looseRefScanRef;
        }
        LooseRef looseRefScanRef2 = scanRef(null, str);
        if (looseRefScanRef2 == null) {
            return refList.get(str);
        }
        for (String str2 : additionalRefsNames) {
            if (str.equals(str2)) {
                return looseRefScanRef2;
            }
        }
        if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.looseRefs, refList2, refList2.add(iFind, looseRefScanRef2))) {
            this.modCnt.incrementAndGet();
        }
        return looseRefScanRef2;
    }

    LooseRef scanRef(LooseRef looseRef, String str) throws IOException {
        FileSnapshot fileSnapshot;
        File fileFileFor = fileFor(str);
        if (looseRef != null) {
            FileSnapshot snapShot = looseRef.getSnapShot();
            if (!snapShot.isModified(fileFileFor)) {
                return looseRef;
            }
            fileSnapshot = snapShot;
            str = looseRef.getName();
        } else {
            fileSnapshot = null;
        }
        FileSnapshot fileSnapshotSave = FileSnapshot.save(fileFileFor);
        try {
            byte[] some = IO.readSome(fileFileFor, 4096);
            int length = some.length;
            if (length == 0) {
                return null;
            }
            if (isSymRef(some, length)) {
                if (length == 4096) {
                    return null;
                }
                while (length > 0 && Character.isWhitespace(some[length - 1])) {
                    length--;
                }
                if (length < 6) {
                    throw new IOException(MessageFormat.format(JGitText.get().notARef, str, RawParseUtils.decode(some, 0, length)));
                }
                String strDecode = RawParseUtils.decode(some, 5, length);
                if (looseRef != null && looseRef.isSymbolic() && looseRef.getTarget().getName().equals(strDecode)) {
                    fileSnapshot.setClean(fileSnapshotSave);
                    return looseRef;
                }
                return newSymbolicRef(fileSnapshotSave, str, strDecode);
            }
            if (length < 40) {
                return null;
            }
            try {
                ObjectId objectIdFromString = ObjectId.fromString(some, 0);
                if (looseRef != null && !looseRef.isSymbolic() && objectIdFromString.equals((AnyObjectId) looseRef.getTarget().getObjectId())) {
                    fileSnapshot.setClean(fileSnapshotSave);
                    return looseRef;
                }
                return new LooseUnpeeled(fileSnapshotSave, str, objectIdFromString);
            } catch (IllegalArgumentException e) {
                while (length > 0 && Character.isWhitespace(some[length - 1])) {
                    length--;
                }
                throw new IOException(MessageFormat.format(JGitText.get().notARef, str, RawParseUtils.decode(some, 0, length)), e);
            }
        } catch (FileNotFoundException e2) {
            if (fileFileFor.isFile()) {
                throw e2;
            }
            return null;
        }
    }

    private static boolean isSymRef(byte[] bArr, int i) {
        return i >= 6 && bArr[0] == 114 && bArr[1] == 101 && bArr[2] == 102 && bArr[3] == 58 && bArr[4] == 32;
    }

    boolean isInClone() throws IOException {
        return (!hasDanglingHead() || this.packedRefsFile.exists() || hasLooseRef()) ? false : true;
    }

    private boolean hasDanglingHead() throws IOException {
        Ref refExactRef = exactRef("HEAD");
        if (refExactRef == null) {
            return false;
        }
        ObjectId objectId = refExactRef.getObjectId();
        return objectId == null || objectId.equals((AnyObjectId) ObjectId.zeroId());
    }

    private boolean hasLooseRef() throws IOException {
        Throwable th = null;
        try {
            Stream<Path> streamWalk = Files.walk(this.refsDir.toPath(), new FileVisitOption[0]);
            try {
                return streamWalk.anyMatch(new Predicate() { // from class: org.eclipse.jgit.internal.storage.file.RefDirectory$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Files.isRegularFile((Path) obj, new LinkOption[0]);
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
            if (null == th2) {
                throw null;
            }
            th.addSuppressed(th2);
            throw null;
        }
    }

    void fireRefsChanged() {
        int i = this.lastNotifiedModCnt.get();
        int i2 = this.modCnt.get();
        if (i == i2 || !this.lastNotifiedModCnt.compareAndSet(i, i2) || i == 0) {
            return;
        }
        this.parent.fireEvent(new RefsChangedEvent());
    }

    RefDirectoryUpdate newTemporaryUpdate() throws IOException {
        return new RefDirectoryUpdate(this, new ObjectIdRef.Unpeeled(Ref.Storage.NEW, Constants.R_REFS + File.createTempFile("renamed_", "_ref", this.refsDir).getName(), null));
    }

    File fileFor(String str) {
        if (str.startsWith(Constants.R_REFS)) {
            return new File(this.refsDir, str.substring(Constants.R_REFS.length()));
        }
        return new File(this.gitDir, str);
    }

    static int levelsIn(String str) {
        int iIndexOf = str.indexOf(47);
        int i = 0;
        while (iIndexOf >= 0) {
            i++;
            iIndexOf = str.indexOf(47, iIndexOf + 1);
        }
        return i;
    }

    static void delete(File file, int i) throws IOException {
        delete(file, i, null);
    }

    private static void delete(File file, int i, LockFile lockFile) throws IOException {
        if (!file.delete() && file.isFile()) {
            throw new IOException(MessageFormat.format(JGitText.get().fileCannotBeDeleted, file));
        }
        if (lockFile != null) {
            lockFile.unlock();
        }
        File parentFile = file.getParentFile();
        for (int i2 = 0; i2 < i; i2++) {
            try {
                Files.deleteIfExists(parentFile.toPath());
                parentFile = parentFile.getParentFile();
            } catch (DirectoryNotEmptyException unused) {
                return;
            } catch (IOException e) {
                LOG.warn(MessageFormat.format(JGitText.get().unableToRemovePath, parentFile), (Throwable) e);
                return;
            }
        }
    }

    Iterable<Integer> getRetrySleepMs() {
        return this.retrySleepMs;
    }

    void setRetrySleepMs(List<Integer> list) {
        if (list == null || list.isEmpty() || list.get(0).intValue() != 0) {
            throw new IllegalArgumentException();
        }
        this.retrySleepMs = list;
    }

    static void sleep(long j) throws InterruptedIOException {
        if (j <= 0) {
            return;
        }
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            InterruptedIOException interruptedIOException = new InterruptedIOException();
            interruptedIOException.initCause(e);
            throw interruptedIOException;
        }
    }

    static class PackedRefList extends RefList<Ref> {

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        private final ObjectId f650id;
        private final FileSnapshot snapshot;

        /* synthetic */ PackedRefList(RefList refList, FileSnapshot fileSnapshot, ObjectId objectId, PackedRefList packedRefList) {
            this(refList, fileSnapshot, objectId);
        }

        private PackedRefList(RefList<Ref> refList, FileSnapshot fileSnapshot, ObjectId objectId) {
            super(refList);
            this.snapshot = fileSnapshot;
            this.f650id = objectId;
        }
    }

    private static LooseSymbolicRef newSymbolicRef(FileSnapshot fileSnapshot, String str, String str2) {
        return new LooseSymbolicRef(fileSnapshot, str, new ObjectIdRef.Unpeeled(Ref.Storage.NEW, str2, null));
    }

    private static final class LoosePeeledTag extends ObjectIdRef.PeeledTag implements LooseRef {
        private final FileSnapshot snapShot;

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public LooseRef peel(ObjectIdRef objectIdRef) {
            return this;
        }

        LoosePeeledTag(FileSnapshot fileSnapshot, String str, ObjectId objectId, ObjectId objectId2) {
            super(Ref.Storage.LOOSE, str, objectId, objectId2);
            this.snapShot = fileSnapshot;
        }

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public FileSnapshot getSnapShot() {
            return this.snapShot;
        }
    }

    private static final class LooseNonTag extends ObjectIdRef.PeeledNonTag implements LooseRef {
        private final FileSnapshot snapShot;

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public LooseRef peel(ObjectIdRef objectIdRef) {
            return this;
        }

        LooseNonTag(FileSnapshot fileSnapshot, String str, ObjectId objectId) {
            super(Ref.Storage.LOOSE, str, objectId);
            this.snapShot = fileSnapshot;
        }

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public FileSnapshot getSnapShot() {
            return this.snapShot;
        }
    }

    private static final class LooseUnpeeled extends ObjectIdRef.Unpeeled implements LooseRef {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private FileSnapshot snapShot;

        LooseUnpeeled(FileSnapshot fileSnapshot, String str, ObjectId objectId) {
            super(Ref.Storage.LOOSE, str, objectId);
            this.snapShot = fileSnapshot;
        }

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public FileSnapshot getSnapShot() {
            return this.snapShot;
        }

        @Override // org.eclipse.jgit.lib.ObjectIdRef, org.eclipse.jgit.lib.Ref
        public ObjectId getObjectId() {
            return super.getObjectId();
        }

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public LooseRef peel(ObjectIdRef objectIdRef) {
            ObjectId peeledObjectId = objectIdRef.getPeeledObjectId();
            ObjectId objectId = getObjectId();
            if (peeledObjectId != null) {
                return new LoosePeeledTag(this.snapShot, getName(), objectId, peeledObjectId);
            }
            return new LooseNonTag(this.snapShot, getName(), objectId);
        }
    }

    private static final class LooseSymbolicRef extends SymbolicRef implements LooseRef {
        private final FileSnapshot snapShot;

        LooseSymbolicRef(FileSnapshot fileSnapshot, String str, Ref ref) {
            super(str, ref);
            this.snapShot = fileSnapshot;
        }

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public FileSnapshot getSnapShot() {
            return this.snapShot;
        }

        @Override // org.eclipse.jgit.internal.storage.file.RefDirectory.LooseRef
        public LooseRef peel(ObjectIdRef objectIdRef) {
            throw new UnsupportedOperationException();
        }
    }
}
