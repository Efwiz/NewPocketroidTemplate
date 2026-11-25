package org.eclipse.jgit.api;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

/* JADX INFO: loaded from: classes13.dex */
public class AddCommand extends GitCommand<DirCache> {
    private Collection<String> filepatterns;
    private boolean update;
    private WorkingTreeIterator workingTreeIterator;

    public AddCommand(Repository repository) {
        super(repository);
        this.update = false;
        this.filepatterns = new LinkedList();
    }

    public AddCommand addFilepattern(String str) {
        checkCallable();
        this.filepatterns.add(str);
        return this;
    }

    public AddCommand setWorkingTreeIterator(WorkingTreeIterator workingTreeIterator) {
        this.workingTreeIterator = workingTreeIterator;
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:110:0x018e  */
    /* JADX WARN: Code duplicated, block: B:111:0x0190 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:112:0x0192 A[Catch: all -> 0x0196, TRY_ENTER, TryCatch #2 {all -> 0x0196, blocks: (B:20:0x0070, B:116:0x019a, B:117:0x019d, B:112:0x0192), top: B:145:0x0014 }] */
    /* JADX WARN: Code duplicated, block: B:116:0x019a A[Catch: all -> 0x0196, TryCatch #2 {all -> 0x0196, blocks: (B:20:0x0070, B:116:0x019a, B:117:0x019d, B:112:0x0192), top: B:145:0x0014 }] */
    /* JADX WARN: Code duplicated, block: B:121:0x01a2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:151:0x01a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:197:? A[Catch: all -> 0x0196, SYNTHETIC, TRY_LEAVE, TryCatch #2 {all -> 0x0196, blocks: (B:20:0x0070, B:116:0x019a, B:117:0x019d, B:112:0x0192), top: B:145:0x0014 }] */
    /* JADX WARN: Code duplicated, block: B:198:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Throwable, org.eclipse.jgit.dircache.DirCache] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v8 */
    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public DirCache call() throws Throwable {
        if (this.filepatterns.isEmpty()) {
            throw new NoFilepatternException(JGitText.get().atLeastOnePatternIsRequired);
        }
        checkCallable();
        boolean zContains = this.filepatterns.contains(".");
        ?? r1 = 0;
        th = null;
        th = null;
        Throwable th = null;
        r1 = 0;
        try {
            try {
                ObjectInserter objectInserterNewObjectInserter = this.repo.newObjectInserter();
                try {
                    try {
                        NameConflictTreeWalk nameConflictTreeWalk = new NameConflictTreeWalk(this.repo);
                        try {
                            nameConflictTreeWalk.setOperationType(TreeWalk.OperationType.CHECKIN_OP);
                            DirCache dirCacheLockDirCache = this.repo.lockDirCache();
                            try {
                                DirCacheBuilder dirCacheBuilderBuilder = dirCacheLockDirCache.builder();
                                nameConflictTreeWalk.addTree(new DirCacheBuildIterator(dirCacheBuilderBuilder));
                                if (this.workingTreeIterator == null) {
                                    this.workingTreeIterator = new FileTreeIterator(this.repo);
                                }
                                this.workingTreeIterator.setDirCacheIterator(nameConflictTreeWalk, 0);
                                nameConflictTreeWalk.addTree(this.workingTreeIterator);
                                if (!zContains) {
                                    nameConflictTreeWalk.setFilter(PathFilterGroup.createFromStrings(this.filepatterns));
                                }
                                byte[] rawPath = null;
                                while (nameConflictTreeWalk.next()) {
                                    DirCacheIterator dirCacheIterator = (DirCacheIterator) nameConflictTreeWalk.getTree(0, DirCacheIterator.class);
                                    WorkingTreeIterator workingTreeIterator = (WorkingTreeIterator) nameConflictTreeWalk.getTree(1, WorkingTreeIterator.class);
                                    if (dirCacheIterator != null || workingTreeIterator == null || !workingTreeIterator.isEntryIgnored()) {
                                        if (dirCacheIterator != null || !this.update) {
                                            DirCacheEntry dirCacheEntry = dirCacheIterator != null ? dirCacheIterator.getDirCacheEntry() : null;
                                            if (dirCacheEntry == null || dirCacheEntry.getStage() <= 0 || rawPath == null || rawPath.length != nameConflictTreeWalk.getPathLength() || nameConflictTreeWalk.isPathPrefix(rawPath, rawPath.length) != 0) {
                                                if (nameConflictTreeWalk.isSubtree() && !nameConflictTreeWalk.isDirectoryFileConflict()) {
                                                    nameConflictTreeWalk.enterSubtree();
                                                } else if (workingTreeIterator == null) {
                                                    if (dirCacheEntry != null && (!this.update || FileMode.GITLINK == dirCacheEntry.getFileMode())) {
                                                        dirCacheBuilderBuilder.add(dirCacheEntry);
                                                    }
                                                } else if (dirCacheEntry != null && dirCacheEntry.isAssumeValid()) {
                                                    dirCacheBuilderBuilder.add(dirCacheEntry);
                                                } else if ((workingTreeIterator.getEntryRawMode() == 16384 && workingTreeIterator.getIndexFileMode(dirCacheIterator) != FileMode.GITLINK) || (workingTreeIterator.getEntryRawMode() == 57344 && workingTreeIterator.getIndexFileMode(dirCacheIterator) == FileMode.TREE)) {
                                                    nameConflictTreeWalk.enterSubtree();
                                                } else {
                                                    rawPath = nameConflictTreeWalk.getRawPath();
                                                    if (dirCacheEntry == null || dirCacheEntry.getStage() > 0) {
                                                        dirCacheEntry = new DirCacheEntry(rawPath);
                                                    }
                                                    FileMode indexFileMode = workingTreeIterator.getIndexFileMode(dirCacheIterator);
                                                    dirCacheEntry.setFileMode(indexFileMode);
                                                    if (FileMode.GITLINK != indexFileMode) {
                                                        dirCacheEntry.setLength(workingTreeIterator.getEntryLength());
                                                        dirCacheEntry.setLastModified(workingTreeIterator.getEntryLastModifiedInstant());
                                                        long entryContentLength = workingTreeIterator.getEntryContentLength();
                                                        try {
                                                            InputStream inputStreamOpenEntryStream = workingTreeIterator.openEntryStream();
                                                            try {
                                                                dirCacheEntry.setObjectId(objectInserterNewObjectInserter.insert(3, entryContentLength, inputStreamOpenEntryStream));
                                                                if (inputStreamOpenEntryStream != null) {
                                                                    inputStreamOpenEntryStream.close();
                                                                }
                                                            } catch (Throwable th2) {
                                                                th = th2;
                                                                if (inputStreamOpenEntryStream == null) {
                                                                    throw th;
                                                                }
                                                                inputStreamOpenEntryStream.close();
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
                                                    } else {
                                                        dirCacheEntry.setLength(0);
                                                        dirCacheEntry.setLastModified(Instant.ofEpochSecond(0L));
                                                        dirCacheEntry.setObjectId(workingTreeIterator.getEntryObjectId());
                                                    }
                                                    dirCacheBuilderBuilder.add(dirCacheEntry);
                                                }
                                            }
                                        }
                                    }
                                }
                                objectInserterNewObjectInserter.flush();
                                dirCacheBuilderBuilder.commit();
                                setCallable(false);
                                nameConflictTreeWalk.close();
                                if (objectInserterNewObjectInserter != null) {
                                    objectInserterNewObjectInserter.close();
                                }
                                if (dirCacheLockDirCache != null) {
                                    dirCacheLockDirCache.unlock();
                                }
                                return dirCacheLockDirCache;
                            } catch (Throwable th4) {
                                th = th4;
                                Throwable th5 = th;
                                nameConflictTreeWalk.close();
                                throw th5;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        if (0 == 0) {
                            r1 = th;
                        } else if (null != th) {
                            r1.addSuppressed(th);
                        }
                        if (objectInserterNewObjectInserter != null) {
                            throw r1;
                        }
                        objectInserterNewObjectInserter.close();
                        throw r1;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    if (0 == 0) {
                        r1 = th;
                    } else if (null != th) {
                        r1.addSuppressed(th);
                    }
                    if (objectInserterNewObjectInserter != null) {
                        throw r1;
                    }
                    objectInserterNewObjectInserter.close();
                    throw r1;
                }
            } catch (Throwable th9) {
                th = th9;
                try {
                    if (0 != 0) {
                        throw th;
                    }
                    if (null != th) {
                        try {
                            r1.addSuppressed(th);
                        } catch (IOException e) {
                            Throwable cause = e.getCause();
                            if (cause != null && (cause instanceof FilterFailedException)) {
                                throw ((FilterFailedException) cause);
                            }
                            throw new JGitInternalException(JGitText.get().exceptionCaughtDuringExecutionOfAddCommand, e);
                        }
                    }
                    throw null;
                } catch (Throwable th10) {
                    if (0 != 0) {
                        r1.unlock();
                    }
                    throw th10;
                }
            }
        } catch (Throwable th11) {
            th = th11;
            if (0 != 0) {
                throw th;
            }
            if (null != th) {
                r1.addSuppressed(th);
            }
            throw null;
        }
    }

    public AddCommand setUpdate(boolean z) {
        this.update = z;
        return this;
    }

    public boolean isUpdate() {
        return this.update;
    }
}
