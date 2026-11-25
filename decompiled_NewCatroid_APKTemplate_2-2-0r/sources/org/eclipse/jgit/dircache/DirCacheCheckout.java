package org.eclipse.jgit.dircache;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public class DirCacheCheckout {
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) DirCacheCheckout.class);
    private static final int MAX_EXCEPTION_TEXT_SIZE = 10240;
    private DirCacheBuilder builder;
    private ArrayList<String> conflicts;
    private DirCache dc;
    private boolean failOnConflict;
    private boolean force;
    private ObjectId headCommitTree;
    private boolean initialCheckout;
    private ArrayList<String> kept;
    private ObjectId mergeCommitTree;
    private ProgressMonitor monitor;
    private boolean performingCheckout;
    private ArrayList<String> removed;
    private Repository repo;
    private ArrayList<String> toBeDeleted;
    private HashMap<String, CheckoutMetadata> updated;
    private NameConflictTreeWalk walk;
    private WorkingTreeIterator workingTree;

    public static class CheckoutMetadata {
        static CheckoutMetadata EMPTY = new CheckoutMetadata(CoreConfig.EolStreamType.DIRECT, null);
        public final CoreConfig.EolStreamType eolStreamType;
        public final String smudgeFilterCommand;

        public CheckoutMetadata(CoreConfig.EolStreamType eolStreamType, String str) {
            this.eolStreamType = eolStreamType;
            this.smudgeFilterCommand = str;
        }
    }

    public Map<String, CheckoutMetadata> getUpdated() {
        return this.updated;
    }

    public List<String> getConflicts() {
        return this.conflicts;
    }

    public List<String> getToBeDeleted() {
        return this.toBeDeleted;
    }

    public List<String> getRemoved() {
        return this.removed;
    }

    public DirCacheCheckout(Repository repository, ObjectId objectId, DirCache dirCache, ObjectId objectId2, WorkingTreeIterator workingTreeIterator) throws IOException {
        this.updated = new HashMap<>();
        this.conflicts = new ArrayList<>();
        this.removed = new ArrayList<>();
        this.kept = new ArrayList<>();
        this.failOnConflict = true;
        this.force = false;
        this.toBeDeleted = new ArrayList<>();
        this.monitor = NullProgressMonitor.INSTANCE;
        this.repo = repository;
        this.dc = dirCache;
        this.headCommitTree = objectId;
        this.mergeCommitTree = objectId2;
        this.workingTree = workingTreeIterator;
        this.initialCheckout = (repository.isBare() || repository.getIndexFile().exists()) ? false : true;
    }

    public DirCacheCheckout(Repository repository, ObjectId objectId, DirCache dirCache, ObjectId objectId2) throws IOException {
        this(repository, objectId, dirCache, objectId2, new FileTreeIterator(repository));
    }

    public DirCacheCheckout(Repository repository, DirCache dirCache, ObjectId objectId, WorkingTreeIterator workingTreeIterator) throws IOException {
        this(repository, null, dirCache, objectId, workingTreeIterator);
    }

    public DirCacheCheckout(Repository repository, DirCache dirCache, ObjectId objectId) throws IOException {
        this(repository, null, dirCache, objectId, new FileTreeIterator(repository));
    }

    public void setProgressMonitor(ProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        this.monitor = progressMonitor;
    }

    public void preScanTwoTrees() throws IOException {
        this.removed.clear();
        this.updated.clear();
        this.conflicts.clear();
        this.walk = new NameConflictTreeWalk(this.repo);
        this.builder = this.dc.builder();
        addTree(this.walk, this.headCommitTree);
        addTree(this.walk, this.mergeCommitTree);
        int iAddTree = this.walk.addTree(new DirCacheBuildIterator(this.builder));
        this.walk.addTree(this.workingTree);
        this.workingTree.setDirCacheIterator(this.walk, iAddTree);
        while (this.walk.next()) {
            processEntry((CanonicalTreeParser) this.walk.getTree(0, CanonicalTreeParser.class), (CanonicalTreeParser) this.walk.getTree(1, CanonicalTreeParser.class), (DirCacheBuildIterator) this.walk.getTree(2, DirCacheBuildIterator.class), (WorkingTreeIterator) this.walk.getTree(3, WorkingTreeIterator.class));
            if (this.walk.isSubtree()) {
                this.walk.enterSubtree();
            }
        }
    }

    private void addTree(TreeWalk treeWalk, ObjectId objectId) throws IOException {
        if (objectId == null) {
            treeWalk.addTree(new EmptyTreeIterator());
        } else {
            treeWalk.addTree(objectId);
        }
    }

    public void prescanOneTree() throws IOException {
        this.removed.clear();
        this.updated.clear();
        this.conflicts.clear();
        this.builder = this.dc.builder();
        NameConflictTreeWalk nameConflictTreeWalk = new NameConflictTreeWalk(this.repo);
        this.walk = nameConflictTreeWalk;
        addTree(nameConflictTreeWalk, this.mergeCommitTree);
        int iAddTree = this.walk.addTree(new DirCacheBuildIterator(this.builder));
        this.walk.addTree(this.workingTree);
        this.workingTree.setDirCacheIterator(this.walk, iAddTree);
        while (this.walk.next()) {
            processEntry((CanonicalTreeParser) this.walk.getTree(0, CanonicalTreeParser.class), (DirCacheBuildIterator) this.walk.getTree(1, DirCacheBuildIterator.class), (WorkingTreeIterator) this.walk.getTree(2, WorkingTreeIterator.class));
            if (this.walk.isSubtree()) {
                this.walk.enterSubtree();
            }
        }
        this.conflicts.removeAll(this.removed);
    }

    void processEntry(CanonicalTreeParser canonicalTreeParser, DirCacheBuildIterator dirCacheBuildIterator, WorkingTreeIterator workingTreeIterator) throws IOException {
        if (canonicalTreeParser == null) {
            if (workingTreeIterator != null) {
                if (this.walk.isDirectoryFileConflict()) {
                    this.conflicts.add(this.walk.getPathString());
                    return;
                } else {
                    if (dirCacheBuildIterator != null) {
                        remove(dirCacheBuildIterator.getEntryPathString());
                        this.conflicts.remove(dirCacheBuildIterator.getEntryPathString());
                        return;
                    }
                    return;
                }
            }
            return;
        }
        checkValidPath(canonicalTreeParser);
        if (dirCacheBuildIterator == null) {
            if (workingTreeIterator != null && !FileMode.TREE.equals(workingTreeIterator.getEntryFileMode()) && !workingTreeIterator.isEntryIgnored()) {
                if (this.failOnConflict) {
                    this.conflicts.add(this.walk.getPathString());
                    return;
                } else {
                    update(canonicalTreeParser.getEntryPathString(), canonicalTreeParser.getEntryObjectId(), canonicalTreeParser.getEntryFileMode());
                    return;
                }
            }
            update(canonicalTreeParser.getEntryPathString(), canonicalTreeParser.getEntryObjectId(), canonicalTreeParser.getEntryFileMode());
            return;
        }
        if (workingTreeIterator == null || !canonicalTreeParser.idEqual(dirCacheBuildIterator)) {
            update(canonicalTreeParser.getEntryPathString(), canonicalTreeParser.getEntryObjectId(), canonicalTreeParser.getEntryFileMode());
            return;
        }
        if (dirCacheBuildIterator.getDirCacheEntry() != null) {
            if (workingTreeIterator.isModified(dirCacheBuildIterator.getDirCacheEntry(), true, this.walk.getObjectReader()) || dirCacheBuildIterator.getDirCacheEntry().getStage() != 0) {
                update(canonicalTreeParser.getEntryPathString(), canonicalTreeParser.getEntryObjectId(), canonicalTreeParser.getEntryFileMode());
                return;
            }
            DirCacheEntry dirCacheEntry = dirCacheBuildIterator.getDirCacheEntry();
            Instant lastModifiedInstant = dirCacheEntry.getLastModifiedInstant();
            if (lastModifiedInstant == null || lastModifiedInstant.equals(Instant.EPOCH)) {
                dirCacheEntry.setLastModified(workingTreeIterator.getEntryLastModifiedInstant());
            }
            keep(dirCacheBuildIterator.getEntryPathString(), dirCacheEntry, workingTreeIterator);
            return;
        }
        keep(dirCacheBuildIterator.getEntryPathString(), dirCacheBuildIterator.getDirCacheEntry(), workingTreeIterator);
    }

    public boolean checkout() throws IOException {
        try {
            try {
                boolean zDoCheckout = doCheckout();
                try {
                    this.dc.unlock();
                    return zDoCheckout;
                } finally {
                    if (this.performingCheckout) {
                        HashSet hashSet = new HashSet(this.conflicts);
                        hashSet.addAll(getUpdated().keySet());
                        hashSet.addAll(this.kept);
                        WorkingTreeModifiedEvent workingTreeModifiedEvent = new WorkingTreeModifiedEvent(hashSet, getRemoved());
                        if (!workingTreeModifiedEvent.isEmpty()) {
                            this.repo.fireEvent(workingTreeModifiedEvent);
                        }
                    }
                }
            } catch (CanceledException e) {
                throw new IOException(e);
            }
        } catch (Throwable th) {
            try {
                this.dc.unlock();
                throw th;
            } finally {
                if (this.performingCheckout) {
                    HashSet hashSet2 = new HashSet(this.conflicts);
                    hashSet2.addAll(getUpdated().keySet());
                    hashSet2.addAll(this.kept);
                    WorkingTreeModifiedEvent workingTreeModifiedEvent2 = new WorkingTreeModifiedEvent(hashSet2, getRemoved());
                    if (!workingTreeModifiedEvent2.isEmpty()) {
                        this.repo.fireEvent(workingTreeModifiedEvent2);
                    }
                }
            }
        }
    }

    private boolean doCheckout() throws IOException, CanceledException {
        Map.Entry<String, CheckoutMetadata> next;
        Exception e;
        this.toBeDeleted.clear();
        Throwable th = null;
        try {
            ObjectReader objectReaderNewReader = this.repo.getObjectDatabase().newReader();
            try {
                if (this.headCommitTree != null) {
                    preScanTwoTrees();
                } else {
                    prescanOneTree();
                }
                if (!this.conflicts.isEmpty()) {
                    if (this.failOnConflict) {
                        throw new CheckoutConflictException((String[]) this.conflicts.toArray(new String[0]));
                    }
                    cleanUpConflicts();
                }
                this.builder.finish();
                this.monitor.beginTask(JGitText.get().checkingOutFiles, this.removed.size() + this.updated.size() + this.conflicts.size());
                this.performingCheckout = true;
                IntList intList = new IntList();
                int size = this.removed.size() - 1;
                File file = null;
                String str = null;
                while (size >= 0) {
                    String str2 = this.removed.get(size);
                    File file2 = new File(this.repo.getWorkTree(), str2);
                    if (!file2.delete() && this.repo.getFS().exists(file2)) {
                        if (!this.repo.getFS().isDirectory(file2)) {
                            intList.add(size);
                            this.toBeDeleted.add(str2);
                        }
                    } else {
                        if (str != null && !isSamePrefix(str2, str)) {
                            removeEmptyParents(new File(this.repo.getWorkTree(), str));
                        }
                        str = str2;
                    }
                    this.monitor.update(1);
                    if (this.monitor.isCancelled()) {
                        throw new CanceledException(MessageFormat.format(JGitText.get().operationCanceled, JGitText.get().checkingOutFiles));
                    }
                    size--;
                    file = file2;
                }
                if (file != null) {
                    removeEmptyParents(file);
                }
                this.removed = filterOut(this.removed, intList);
                Iterator<Map.Entry<String, CheckoutMetadata>> it = this.updated.entrySet().iterator();
                while (it.hasNext()) {
                    try {
                        next = it.next();
                        try {
                            String key = next.getKey();
                            CheckoutMetadata value = next.getValue();
                            DirCacheEntry entry = this.dc.getEntry(key);
                            if (FileMode.GITLINK.equals(entry.getRawMode())) {
                                checkoutGitlink(key, entry);
                            } else {
                                checkoutEntry(this.repo, entry, objectReaderNewReader, false, value);
                            }
                            this.monitor.update(1);
                            if (this.monitor.isCancelled()) {
                                throw new CanceledException(MessageFormat.format(JGitText.get().operationCanceled, JGitText.get().checkingOutFiles));
                            }
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        next = null;
                        e = e3;
                    }
                    if (next != null) {
                        it.remove();
                    }
                    while (it.hasNext()) {
                        it.next();
                        it.remove();
                    }
                    throw e;
                }
                for (String str3 : this.conflicts) {
                    int iFindEntry = this.dc.findEntry(str3);
                    if (iFindEntry >= 0) {
                        while (iFindEntry < this.dc.getEntryCount()) {
                            DirCacheEntry entry2 = this.dc.getEntry(iFindEntry);
                            if (!entry2.getPathString().equals(str3)) {
                                break;
                            }
                            if (entry2.getStage() == 3) {
                                checkoutEntry(this.repo, entry2, objectReaderNewReader, false, null);
                                break;
                            }
                            iFindEntry++;
                        }
                    }
                    this.monitor.update(1);
                    if (this.monitor.isCancelled()) {
                        throw new CanceledException(MessageFormat.format(JGitText.get().operationCanceled, JGitText.get().checkingOutFiles));
                    }
                }
                this.monitor.endTask();
                if (!this.builder.commit()) {
                    throw new IndexWriteException();
                }
                if (objectReaderNewReader != null) {
                    objectReaderNewReader.close();
                }
                return this.toBeDeleted.isEmpty();
            } catch (Throwable th2) {
                if (objectReaderNewReader != null) {
                    objectReaderNewReader.close();
                }
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

    private void checkoutGitlink(String str, DirCacheEntry dirCacheEntry) throws IOException {
        File file = new File(this.repo.getWorkTree(), str);
        FileUtils.mkdirs(file, true);
        dirCacheEntry.setLastModified(this.repo.getFS().lastModifiedInstant(file));
    }

    private static ArrayList<String> filterOut(ArrayList<String> arrayList, IntList intList) {
        int size = intList.size();
        if (size == arrayList.size()) {
            return new ArrayList<>(0);
        }
        if (size != 0) {
            if (size == 1) {
                arrayList.remove(intList.get(0));
            } else {
                int size2 = arrayList.size();
                ArrayList<String> arrayList2 = new ArrayList<>(size2 - size);
                int i = size - 1;
                int i2 = intList.get(i);
                for (int i3 = 0; i3 < size2; i3++) {
                    if (i3 == i2) {
                        i--;
                        i2 = i >= 0 ? intList.get(i) : -1;
                    } else {
                        arrayList2.add(arrayList.get(i3));
                    }
                }
                return arrayList2;
            }
        }
        return arrayList;
    }

    private static boolean isSamePrefix(String str, String str2) {
        return str.substring(0, str.lastIndexOf(47) + 1).equals(str2.substring(0, str2.lastIndexOf(47) + 1));
    }

    private void removeEmptyParents(File file) {
        for (File parentFile = file.getParentFile(); parentFile != null && !parentFile.equals(this.repo.getWorkTree()) && parentFile.delete(); parentFile = parentFile.getParentFile()) {
        }
    }

    private boolean equalIdAndMode(ObjectId objectId, FileMode fileMode, ObjectId objectId2, FileMode fileMode2) {
        if (!fileMode.equals(fileMode2)) {
            return false;
        }
        if (objectId != null) {
            return objectId.equals((AnyObjectId) objectId2);
        }
        return objectId2 == null;
    }

    void processEntry(CanonicalTreeParser canonicalTreeParser, CanonicalTreeParser canonicalTreeParser2, DirCacheBuildIterator dirCacheBuildIterator, WorkingTreeIterator workingTreeIterator) throws IOException {
        int i;
        FileMode fileMode;
        FileMode fileMode2;
        DirCacheEntry dirCacheEntry = dirCacheBuildIterator != null ? dirCacheBuildIterator.getDirCacheEntry() : null;
        String pathString = this.walk.getPathString();
        if (canonicalTreeParser2 != null) {
            checkValidPath(canonicalTreeParser2);
        }
        if (dirCacheBuildIterator == null && canonicalTreeParser2 == null && canonicalTreeParser == null) {
            if (this.walk.isDirectoryFileConflict()) {
                conflict(pathString, null, null, null);
                return;
            }
            return;
        }
        ObjectId entryObjectId = dirCacheBuildIterator == null ? null : dirCacheBuildIterator.getEntryObjectId();
        ObjectId entryObjectId2 = canonicalTreeParser2 == null ? null : canonicalTreeParser2.getEntryObjectId();
        ObjectId entryObjectId3 = canonicalTreeParser == null ? null : canonicalTreeParser.getEntryObjectId();
        FileMode entryFileMode = dirCacheBuildIterator == null ? null : dirCacheBuildIterator.getEntryFileMode();
        FileMode entryFileMode2 = canonicalTreeParser2 == null ? null : canonicalTreeParser2.getEntryFileMode();
        FileMode entryFileMode3 = canonicalTreeParser == null ? null : canonicalTreeParser.getEntryFileMode();
        if (canonicalTreeParser != null) {
            i = FileMode.TREE.equals(entryFileMode3) ? 3328 : 3840;
        } else {
            i = 0;
        }
        if (dirCacheBuildIterator != null) {
            i |= FileMode.TREE.equals(entryFileMode) ? 208 : 240;
        }
        if (canonicalTreeParser2 != null) {
            i |= FileMode.TREE.equals(entryFileMode2) ? 13 : 15;
        }
        int i2 = i & 546;
        if (i2 != 0 && ((i & 15) == 13 || (i & 240) == 208 || (i & 3840) == 3328)) {
            switch (i) {
                case 223:
                    if (!isModifiedSubtree_IndexWorkingtree(pathString)) {
                        update(pathString, entryObjectId2, entryFileMode2);
                    } else {
                        conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    }
                    break;
                case 253:
                case XKeySym.XK_Thai_leksun /* 3568 */:
                    conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    break;
                case 3343:
                    update(pathString, entryObjectId2, entryFileMode2);
                    break;
                case XKeySym.XK_Thai_baht /* 3551 */:
                    if (workingTreeIterator != null && isModifiedSubtree_IndexWorkingtree(pathString)) {
                        conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    } else {
                        update(pathString, entryObjectId2, entryFileMode2);
                    }
                    break;
                case 3581:
                    keep(pathString, dirCacheEntry, workingTreeIterator);
                    break;
                case 3583:
                    if (equalIdAndMode(entryObjectId, entryFileMode, entryObjectId2, entryFileMode2)) {
                        keep(pathString, dirCacheEntry, workingTreeIterator);
                    } else {
                        conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    }
                    break;
                case 3853:
                    remove(pathString);
                    break;
                case 4048:
                    keep(pathString, dirCacheEntry, workingTreeIterator);
                    break;
                case 4061:
                    break;
                case 4063:
                    if (equalIdAndMode(entryObjectId3, entryFileMode3, entryObjectId2, entryFileMode2)) {
                        if (isModifiedSubtree_IndexWorkingtree(pathString)) {
                            conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                        } else {
                            update(pathString, entryObjectId2, entryFileMode2);
                        }
                    } else {
                        conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    }
                    break;
                case 4093:
                    if (equalIdAndMode(entryObjectId3, entryFileMode3, entryObjectId, entryFileMode)) {
                        if (workingTreeIterator != null && workingTreeIterator.isModified(dirCacheEntry, true, this.walk.getObjectReader())) {
                            conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                        } else {
                            remove(pathString);
                        }
                    } else {
                        conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    }
                    break;
                default:
                    keep(pathString, dirCacheEntry, workingTreeIterator);
                    break;
            }
            return;
        }
        if (i2 == 0) {
            if (workingTreeIterator == null || FileMode.TREE.equals(workingTreeIterator.getEntryFileMode()) || idEqual(canonicalTreeParser, canonicalTreeParser2)) {
                return;
            }
            conflict(pathString, null, null, null);
            return;
        }
        if (i == 15 && workingTreeIterator != null && FileMode.TREE.equals(workingTreeIterator.getEntryFileMode())) {
            conflict(pathString, null, canonicalTreeParser, canonicalTreeParser2);
            return;
        }
        if (dirCacheBuildIterator == null) {
            if (workingTreeIterator != null && !workingTreeIterator.isEntryIgnored() && !FileMode.GITLINK.equals(entryFileMode2) && (entryObjectId2 == null || !equalIdAndMode(entryObjectId2, entryFileMode2, workingTreeIterator.getEntryObjectId(), workingTreeIterator.getEntryFileMode()))) {
                conflict(pathString, null, canonicalTreeParser, canonicalTreeParser2);
                return;
            }
            if (canonicalTreeParser == null) {
                update(pathString, entryObjectId2, entryFileMode2);
                return;
            }
            if (canonicalTreeParser2 == null) {
                remove(pathString);
                return;
            }
            if (equalIdAndMode(entryObjectId3, entryFileMode3, entryObjectId2, entryFileMode2)) {
                if (this.initialCheckout || this.force) {
                    update(pathString, entryObjectId2, entryFileMode2);
                    return;
                } else {
                    keep(pathString, dirCacheEntry, workingTreeIterator);
                    return;
                }
            }
            conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
            return;
        }
        if (canonicalTreeParser == null) {
            if (canonicalTreeParser2 != null) {
                if (isModified_IndexTree(pathString, entryObjectId, entryFileMode, entryObjectId2, entryFileMode2, this.mergeCommitTree)) {
                    conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    return;
                }
            }
            if (canonicalTreeParser2 == null && this.walk.isDirectoryFileConflict()) {
                if (dirCacheEntry != null && (workingTreeIterator == null || workingTreeIterator.isModified(dirCacheEntry, true, this.walk.getObjectReader()))) {
                    conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    return;
                } else {
                    remove(pathString);
                    return;
                }
            }
            keep(pathString, dirCacheEntry, workingTreeIterator);
            return;
        }
        if (canonicalTreeParser2 == null) {
            if (entryFileMode == FileMode.GITLINK) {
                remove(pathString);
                return;
            }
            FileMode fileMode3 = entryFileMode3;
            ObjectId objectId = entryObjectId3;
            FileMode fileMode4 = entryFileMode;
            if (!isModified_IndexTree(pathString, entryObjectId, entryFileMode, objectId, fileMode3, this.headCommitTree)) {
                if (workingTreeIterator != null && workingTreeIterator.isModified(dirCacheEntry, true, this.walk.getObjectReader())) {
                    if (FileMode.TREE.equals(workingTreeIterator.getEntryFileMode()) || !FileMode.TREE.equals(fileMode4)) {
                        conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                        return;
                    }
                    return;
                }
                remove(pathString);
                return;
            }
            conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
            return;
        }
        FileMode fileMode5 = entryFileMode;
        FileMode fileMode6 = entryFileMode3;
        if (equalIdAndMode(entryObjectId3, fileMode6, entryObjectId2, entryFileMode2)) {
            fileMode = entryFileMode2;
            fileMode2 = fileMode6;
        } else {
            fileMode = entryFileMode2;
            fileMode2 = fileMode6;
            if (isModified_IndexTree(pathString, entryObjectId, fileMode5, entryObjectId3, fileMode6, this.headCommitTree)) {
                if (isModified_IndexTree(pathString, entryObjectId, fileMode5, entryObjectId2, fileMode, this.mergeCommitTree)) {
                    conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    return;
                }
            }
        }
        if (!isModified_IndexTree(pathString, entryObjectId, fileMode5, entryObjectId3, fileMode2, this.headCommitTree)) {
            if (isModified_IndexTree(pathString, entryObjectId, fileMode5, entryObjectId2, fileMode, this.mergeCommitTree)) {
                if (dirCacheEntry != null && FileMode.GITLINK.equals(dirCacheEntry.getFileMode())) {
                    update(pathString, entryObjectId2, fileMode);
                    return;
                }
                FileMode fileMode7 = fileMode;
                if (dirCacheEntry != null && workingTreeIterator != null && workingTreeIterator.isModified(dirCacheEntry, true, this.walk.getObjectReader())) {
                    conflict(pathString, dirCacheEntry, canonicalTreeParser, canonicalTreeParser2);
                    return;
                } else {
                    update(pathString, entryObjectId2, fileMode7);
                    return;
                }
            }
        }
        keep(pathString, dirCacheEntry, workingTreeIterator);
    }

    private static boolean idEqual(AbstractTreeIterator abstractTreeIterator, AbstractTreeIterator abstractTreeIterator2) {
        if (abstractTreeIterator == abstractTreeIterator2) {
            return true;
        }
        if (abstractTreeIterator == null || abstractTreeIterator2 == null) {
            return false;
        }
        return abstractTreeIterator.getEntryObjectId().equals((AnyObjectId) abstractTreeIterator2.getEntryObjectId());
    }

    private void conflict(String str, DirCacheEntry dirCacheEntry, AbstractTreeIterator abstractTreeIterator, AbstractTreeIterator abstractTreeIterator2) {
        this.conflicts.add(str);
        if (dirCacheEntry != null) {
            DirCacheEntry dirCacheEntry2 = new DirCacheEntry(dirCacheEntry.getPathString(), 1);
            dirCacheEntry2.copyMetaData(dirCacheEntry, true);
            this.builder.add(dirCacheEntry2);
        }
        if (abstractTreeIterator != null && !FileMode.TREE.equals(abstractTreeIterator.getEntryFileMode())) {
            DirCacheEntry dirCacheEntry3 = new DirCacheEntry(abstractTreeIterator.getEntryPathString(), 2);
            dirCacheEntry3.setFileMode(abstractTreeIterator.getEntryFileMode());
            dirCacheEntry3.setObjectId(abstractTreeIterator.getEntryObjectId());
            this.builder.add(dirCacheEntry3);
        }
        if (abstractTreeIterator2 == null || FileMode.TREE.equals(abstractTreeIterator2.getEntryFileMode())) {
            return;
        }
        DirCacheEntry dirCacheEntry4 = new DirCacheEntry(abstractTreeIterator2.getEntryPathString(), 3);
        dirCacheEntry4.setFileMode(abstractTreeIterator2.getEntryFileMode());
        dirCacheEntry4.setObjectId(abstractTreeIterator2.getEntryObjectId());
        this.builder.add(dirCacheEntry4);
    }

    private void keep(String str, DirCacheEntry dirCacheEntry, WorkingTreeIterator workingTreeIterator) throws IOException {
        if (dirCacheEntry == null) {
            return;
        }
        if (!FileMode.TREE.equals(dirCacheEntry.getFileMode())) {
            this.builder.add(dirCacheEntry);
        }
        if (this.force) {
            if (workingTreeIterator == null || workingTreeIterator.isModified(dirCacheEntry, true, this.walk.getObjectReader())) {
                this.kept.add(str);
                checkoutEntry(this.repo, dirCacheEntry, this.walk.getObjectReader(), false, new CheckoutMetadata(this.walk.getEolStreamType(TreeWalk.OperationType.CHECKOUT_OP), this.walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
            }
        }
    }

    private void remove(String str) {
        this.removed.add(str);
    }

    private void update(String str, ObjectId objectId, FileMode fileMode) throws IOException {
        if (FileMode.TREE.equals(fileMode)) {
            return;
        }
        this.updated.put(str, new CheckoutMetadata(this.walk.getEolStreamType(TreeWalk.OperationType.CHECKOUT_OP), this.walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
        DirCacheEntry dirCacheEntry = new DirCacheEntry(str, 0);
        dirCacheEntry.setObjectId(objectId);
        dirCacheEntry.setFileMode(fileMode);
        this.builder.add(dirCacheEntry);
    }

    public void setFailOnConflict(boolean z) {
        this.failOnConflict = z;
    }

    public void setForce(boolean z) {
        this.force = z;
    }

    private void cleanUpConflicts() throws CheckoutConflictException {
        for (String str : this.conflicts) {
            File file = new File(this.repo.getWorkTree(), str);
            if (!file.delete()) {
                throw new CheckoutConflictException(MessageFormat.format(JGitText.get().cannotDeleteFile, str));
            }
            removeEmptyParents(file);
        }
    }

    private boolean isModifiedSubtree_IndexWorkingtree(String str) throws IOException {
        Throwable th = null;
        try {
            TreeWalk nameConflictTreeWalk = new NameConflictTreeWalk(this.repo);
            try {
                int iAddTree = nameConflictTreeWalk.addTree(new DirCacheIterator(this.dc));
                FileTreeIterator fileTreeIterator = new FileTreeIterator(this.repo);
                nameConflictTreeWalk.addTree(fileTreeIterator);
                fileTreeIterator.setDirCacheIterator(nameConflictTreeWalk, iAddTree);
                nameConflictTreeWalk.setRecursive(true);
                nameConflictTreeWalk.setFilter(PathFilter.create(str));
                while (nameConflictTreeWalk.next()) {
                    DirCacheIterator dirCacheIterator = (DirCacheIterator) nameConflictTreeWalk.getTree(0, DirCacheIterator.class);
                    WorkingTreeIterator workingTreeIterator = (WorkingTreeIterator) nameConflictTreeWalk.getTree(1, WorkingTreeIterator.class);
                    if (dirCacheIterator != null && workingTreeIterator != null) {
                        if (workingTreeIterator.isModified(dirCacheIterator.getDirCacheEntry(), true, this.walk.getObjectReader())) {
                            return true;
                        }
                    }
                    return true;
                }
                return false;
            } finally {
                nameConflictTreeWalk.close();
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

    private boolean isModified_IndexTree(String str, ObjectId objectId, FileMode fileMode, ObjectId objectId2, FileMode fileMode2, ObjectId objectId3) throws IOException {
        if (fileMode != fileMode2) {
            return true;
        }
        if (FileMode.TREE.equals(fileMode) && (objectId == null || ObjectId.zeroId().equals((AnyObjectId) objectId))) {
            return isModifiedSubtree_IndexTree(str, objectId3);
        }
        return !equalIdAndMode(objectId, fileMode, objectId2, fileMode2);
    }

    private boolean isModifiedSubtree_IndexTree(String str, ObjectId objectId) throws IOException {
        AbstractTreeIterator tree;
        AbstractTreeIterator tree2;
        Throwable th = null;
        try {
            NameConflictTreeWalk nameConflictTreeWalk = new NameConflictTreeWalk(this.repo);
            try {
                nameConflictTreeWalk.addTree(new DirCacheIterator(this.dc));
                nameConflictTreeWalk.addTree(objectId);
                nameConflictTreeWalk.setRecursive(true);
                nameConflictTreeWalk.setFilter(PathFilter.create(str));
                do {
                    if (!nameConflictTreeWalk.next()) {
                        return false;
                    }
                    tree = nameConflictTreeWalk.getTree(0, DirCacheIterator.class);
                    tree2 = nameConflictTreeWalk.getTree(1, AbstractTreeIterator.class);
                    if (tree != null && tree2 != null) {
                        if (tree.getEntryRawMode() != tree2.getEntryRawMode()) {
                            return true;
                        }
                    }
                    return true;
                } while (tree.getEntryObjectId().equals((AnyObjectId) tree2.getEntryObjectId()));
                return true;
            } finally {
                nameConflictTreeWalk.close();
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

    @Deprecated
    public static void checkoutEntry(Repository repository, DirCacheEntry dirCacheEntry, ObjectReader objectReader) throws IOException {
        checkoutEntry(repository, dirCacheEntry, objectReader, false, null);
    }

    public static void checkoutEntry(Repository repository, DirCacheEntry dirCacheEntry, ObjectReader objectReader, boolean z, CheckoutMetadata checkoutMetadata) throws IOException {
        CheckoutMetadata checkoutMetadata2 = checkoutMetadata == null ? CheckoutMetadata.EMPTY : checkoutMetadata;
        ObjectLoader objectLoaderOpen = objectReader.open(dirCacheEntry.getObjectId());
        File file = new File(repository.getWorkTree(), dirCacheEntry.getPathString());
        File parentFile = file.getParentFile();
        if (parentFile.isFile()) {
            FileUtils.delete(parentFile);
        }
        FileUtils.mkdirs(parentFile, true);
        FS fs = repository.getFS();
        WorkingTreeOptions workingTreeOptions = (WorkingTreeOptions) repository.getConfig().get(WorkingTreeOptions.KEY);
        if (dirCacheEntry.getFileMode() == FileMode.SYMLINK && workingTreeOptions.getSymLinks() == CoreConfig.SymLinks.TRUE) {
            byte[] bytes = objectLoaderOpen.getBytes();
            String strDecode = RawParseUtils.decode(bytes);
            if (z && file.isDirectory()) {
                FileUtils.delete(file, 1);
            }
            fs.createSymLink(file, strDecode);
            dirCacheEntry.setLength(bytes.length);
            dirCacheEntry.setLastModified(fs.lastModifiedInstant(file));
            return;
        }
        String name2 = file.getName();
        if (name2.length() > 200) {
            name2 = name2.substring(0, 200);
        }
        File fileCreateTempFile = File.createTempFile("._" + name2, null, parentFile);
        getContent(repository, dirCacheEntry.getPathString(), checkoutMetadata2, objectLoaderOpen, workingTreeOptions, new FileOutputStream(fileCreateTempFile));
        if (checkoutMetadata2.eolStreamType == CoreConfig.EolStreamType.DIRECT && checkoutMetadata2.smudgeFilterCommand == null) {
            dirCacheEntry.setLength(objectLoaderOpen.getSize());
        } else {
            dirCacheEntry.setLength(fileCreateTempFile.length());
        }
        if (workingTreeOptions.isFileMode() && fs.supportsExecute()) {
            if (FileMode.EXECUTABLE_FILE.equals(dirCacheEntry.getRawMode())) {
                if (!fs.canExecute(fileCreateTempFile)) {
                    fs.setExecute(fileCreateTempFile, true);
                }
            } else if (fs.canExecute(fileCreateTempFile)) {
                fs.setExecute(fileCreateTempFile, false);
            }
        }
        try {
            if (z) {
                try {
                    if (file.isDirectory()) {
                        FileUtils.delete(file, 1);
                    }
                } catch (IOException e) {
                    throw new IOException(MessageFormat.format(JGitText.get().renameFileFailed, fileCreateTempFile.getPath(), file.getPath()), e);
                }
            }
            FileUtils.rename(fileCreateTempFile, file, StandardCopyOption.ATOMIC_MOVE);
            if (fileCreateTempFile.exists()) {
                FileUtils.delete(fileCreateTempFile);
            }
            dirCacheEntry.setLastModified(fs.lastModifiedInstant(file));
        } catch (Throwable th) {
            if (fileCreateTempFile.exists()) {
                FileUtils.delete(fileCreateTempFile);
            }
            throw th;
        }
    }

    public static void getContent(Repository repository, String str, CheckoutMetadata checkoutMetadata, ObjectLoader objectLoader, WorkingTreeOptions workingTreeOptions, OutputStream outputStream) throws IOException {
        CoreConfig.EolStreamType eolStreamType;
        if (checkoutMetadata.eolStreamType != null) {
            eolStreamType = checkoutMetadata.eolStreamType;
        } else if (workingTreeOptions.getAutoCRLF() == CoreConfig.AutoCRLF.TRUE) {
            eolStreamType = CoreConfig.EolStreamType.AUTO_CRLF;
        } else {
            eolStreamType = CoreConfig.EolStreamType.DIRECT;
        }
        Throwable th = null;
        try {
            OutputStream outputStreamWrapOutputStream = EolStreamTypeUtil.wrapOutputStream(outputStream, eolStreamType);
            try {
                if (checkoutMetadata.smudgeFilterCommand != null) {
                    if (FilterCommandRegistry.isRegistered(checkoutMetadata.smudgeFilterCommand)) {
                        runBuiltinFilterCommand(repository, checkoutMetadata, objectLoader, outputStreamWrapOutputStream);
                    } else {
                        runExternalFilterCommand(repository, str, checkoutMetadata, objectLoader, outputStreamWrapOutputStream);
                    }
                } else {
                    objectLoader.copyTo(outputStreamWrapOutputStream);
                }
            } finally {
                if (outputStreamWrapOutputStream != null) {
                    outputStreamWrapOutputStream.close();
                }
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

    private static void runExternalFilterCommand(Repository repository, String str, CheckoutMetadata checkoutMetadata, ObjectLoader objectLoader, OutputStream outputStream) throws IOException {
        FS fs = repository.getFS();
        ProcessBuilder processBuilderRunInShell = fs.runInShell(checkoutMetadata.smudgeFilterCommand, new String[0]);
        processBuilderRunInShell.directory(repository.getWorkTree());
        processBuilderRunInShell.environment().put(Constants.GIT_DIR_KEY, repository.getDirectory().getAbsolutePath());
        try {
            FS.ExecutionResult executionResultExecute = fs.execute(processBuilderRunInShell, objectLoader.openStream());
            int rc = executionResultExecute.getRc();
            if (rc == 0) {
                executionResultExecute.getStdout().writeTo(outputStream, NullProgressMonitor.INSTANCE);
            }
            if (rc != 0) {
                throw new IOException(new FilterFailedException(rc, checkoutMetadata.smudgeFilterCommand, str, executionResultExecute.getStdout().toByteArray(10240), executionResultExecute.getStderr().toString(10240)));
            }
        } catch (IOException | InterruptedException e) {
            throw new IOException(new FilterFailedException(e, checkoutMetadata.smudgeFilterCommand, str));
        }
    }

    private static void runBuiltinFilterCommand(Repository repository, CheckoutMetadata checkoutMetadata, ObjectLoader objectLoader, OutputStream outputStream) throws IOException {
        FilterCommand filterCommandCreateFilterCommand;
        boolean z = repository.getConfig().getBoolean("filter", ConfigConstants.CONFIG_SECTION_LFS, "required", false);
        try {
            filterCommandCreateFilterCommand = FilterCommandRegistry.createFilterCommand(checkoutMetadata.smudgeFilterCommand, repository, objectLoader.openStream(), outputStream);
        } catch (IOException e) {
            LOG.error(JGitText.get().failedToDetermineFilterDefinition, (Throwable) e);
            if (!z) {
                objectLoader.copyTo(outputStream);
                filterCommandCreateFilterCommand = null;
            } else {
                throw e;
            }
        }
        if (filterCommandCreateFilterCommand != null) {
            while (filterCommandCreateFilterCommand.run() != -1) {
            }
        }
    }

    private static void checkValidPath(CanonicalTreeParser canonicalTreeParser) throws InvalidPathException {
        ObjectChecker safeForMacOS = new ObjectChecker().setSafeForWindows(SystemReader.getInstance().isWindows()).setSafeForMacOS(SystemReader.getInstance().isMacOS());
        while (canonicalTreeParser != null) {
            checkValidPathSegment(safeForMacOS, canonicalTreeParser);
            canonicalTreeParser = canonicalTreeParser.getParent();
        }
    }

    private static void checkValidPathSegment(ObjectChecker objectChecker, CanonicalTreeParser canonicalTreeParser) throws InvalidPathException {
        try {
            int nameOffset = canonicalTreeParser.getNameOffset();
            objectChecker.checkPathSegment(canonicalTreeParser.getEntryPathBuffer(), nameOffset, canonicalTreeParser.getNameLength() + nameOffset);
        } catch (CorruptObjectException e) {
            InvalidPathException invalidPathException = new InvalidPathException(canonicalTreeParser.getEntryPathString());
            invalidPathException.initCause(e);
            throw invalidPathException;
        }
    }
}
