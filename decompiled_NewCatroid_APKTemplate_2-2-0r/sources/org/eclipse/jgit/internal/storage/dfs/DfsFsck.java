package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.eclipse.jgit.errors.CorruptPackIndexException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.fsck.FsckError;
import org.eclipse.jgit.internal.fsck.FsckPackParser;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.submodule.SubmoduleValidator;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GitmoduleEntry;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;

/* JADX INFO: loaded from: classes13.dex */
public class DfsFsck {
    private boolean connectivityOnly;
    private ObjectChecker objChecker = new ObjectChecker();
    private final DfsObjDatabase objdb;
    private final DfsRepository repo;

    public DfsFsck(DfsRepository dfsRepository) {
        this.repo = dfsRepository;
        this.objdb = dfsRepository.getObjectDatabase();
    }

    public FsckError check(ProgressMonitor progressMonitor) throws IOException {
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        FsckError fsckError = new FsckError();
        if (!this.connectivityOnly) {
            this.objChecker.reset();
            checkPacks(progressMonitor, fsckError);
        }
        checkConnectivity(progressMonitor, fsckError);
        return fsckError;
    }

    private void checkPacks(ProgressMonitor progressMonitor, FsckError fsckError) throws IOException {
        Throwable th;
        Throwable th2 = null;
        try {
            DfsReader dfsReaderNewReader = this.objdb.newReader();
            try {
                for (DfsPackFile dfsPackFile : this.objdb.getPacks()) {
                    DfsPackDescription packDescription = dfsPackFile.getPackDescription();
                    if (packDescription.getPackSource() != DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE) {
                        try {
                            ReadableChannel readableChannelOpenFile = this.objdb.openFile(packDescription, PackExt.PACK);
                            try {
                                verifyPack(progressMonitor, fsckError, dfsReaderNewReader, dfsPackFile, readableChannelOpenFile);
                                if (readableChannelOpenFile != null) {
                                    readableChannelOpenFile.close();
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                if (readableChannelOpenFile != null) {
                                    try {
                                        readableChannelOpenFile.close();
                                    } catch (Throwable th4) {
                                        th = th4;
                                        if (th == null) {
                                            th = th;
                                        } else if (th != th) {
                                            try {
                                                try {
                                                    th.addSuppressed(th);
                                                } catch (MissingObjectException e) {
                                                    fsckError.getMissingObjects().add(e.getObjectId());
                                                }
                                            } catch (CorruptPackIndexException e2) {
                                                fsckError.getCorruptIndices().add(new FsckError.CorruptIndex(dfsPackFile.getPackDescription().getFileName(PackExt.INDEX), e2.getErrorType()));
                                            }
                                        }
                                        throw th;
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            th = null;
                        }
                    }
                }
                if (dfsReaderNewReader != null) {
                    dfsReaderNewReader.close();
                }
                checkGitModules(progressMonitor, fsckError);
            } catch (Throwable th6) {
                if (dfsReaderNewReader == null) {
                    throw th6;
                }
                dfsReaderNewReader.close();
                throw th6;
            }
        } catch (Throwable th7) {
            if (0 == 0) {
                throw th7;
            }
            if (null == th7) {
                throw null;
            }
            th2.addSuppressed(th7);
        }
    }

    private void verifyPack(ProgressMonitor progressMonitor, FsckError fsckError, DfsReader dfsReader, DfsPackFile dfsPackFile, ReadableChannel readableChannel) throws IOException, CorruptPackIndexException {
        FsckPackParser fsckPackParser = new FsckPackParser(this.objdb, readableChannel);
        fsckPackParser.setObjectChecker(this.objChecker);
        fsckPackParser.overwriteObjectCount(dfsPackFile.getPackDescription().getObjectCount());
        fsckPackParser.parse(progressMonitor);
        fsckError.getCorruptObjects().addAll(fsckPackParser.getCorruptObjects());
        fsckPackParser.verifyIndex(dfsPackFile.getPackIndex(dfsReader));
    }

    private void checkGitModules(ProgressMonitor progressMonitor, FsckError fsckError) throws IOException {
        progressMonitor.beginTask(JGitText.get().validatingGitModules, this.objChecker.getGitsubmodules().size());
        Iterator<GitmoduleEntry> it = this.objChecker.getGitsubmodules().iterator();
        while (it.hasNext()) {
            AnyObjectId blobId = it.next().getBlobId();
            try {
                SubmoduleValidator.assertValidGitModulesFile(new String(this.objdb.open(blobId, 3).getBytes(), StandardCharsets.UTF_8));
            } catch (SubmoduleValidator.SubmoduleValidationException e) {
                fsckError.getCorruptObjects().add(new FsckError.CorruptObject(blobId.toObjectId(), 3, e.getFsckMessageId()));
            }
            progressMonitor.update(1);
        }
        progressMonitor.endTask();
    }

    private void checkConnectivity(ProgressMonitor progressMonitor, FsckError fsckError) throws IOException {
        progressMonitor.beginTask(JGitText.get().countingObjects, 0);
        Throwable th = null;
        try {
            ObjectWalk objectWalk = new ObjectWalk(this.repo);
            try {
                for (Ref ref : this.repo.getRefDatabase().getRefs()) {
                    ObjectId objectId = ref.getObjectId();
                    if (objectId != null) {
                        try {
                            RevObject any = objectWalk.parseAny(objectId);
                            if (ref.getLeaf().getName().startsWith(Constants.R_HEADS) && any.getType() != 1) {
                                fsckError.getNonCommitHeads().add(ref.getLeaf().getName());
                            }
                            objectWalk.markStart(any);
                        } catch (MissingObjectException e) {
                            fsckError.getMissingObjects().add(e.getObjectId());
                        }
                    }
                }
                try {
                    objectWalk.checkConnectivity();
                } catch (MissingObjectException e2) {
                    fsckError.getMissingObjects().add(e2.getObjectId());
                }
                objectWalk.close();
                progressMonitor.endTask();
            } catch (Throwable th2) {
                objectWalk.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                th.addSuppressed(th3);
            }
            throw null;
        }
    }

    public void setObjectChecker(ObjectChecker objectChecker) {
        this.objChecker = objectChecker;
    }

    public void setConnectivityOnly(boolean z) {
        this.connectivityOnly = z;
    }
}
