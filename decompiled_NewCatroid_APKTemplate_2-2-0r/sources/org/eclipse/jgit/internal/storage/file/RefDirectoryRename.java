package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.StandardCopyOption;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
class RefDirectoryRename extends RefRename {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) RefDirectoryRename.class);
    private ObjectId objId;
    private final RefDirectory refdb;
    private RefDirectoryUpdate tmp;

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[RefUpdate.Result.valuesCustom().length];
        try {
            iArr2[RefUpdate.Result.FAST_FORWARD.ordinal()] = 6;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[RefUpdate.Result.FORCED.ordinal()] = 5;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[RefUpdate.Result.IO_FAILURE.ordinal()] = 9;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[RefUpdate.Result.LOCK_FAILURE.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[RefUpdate.Result.NEW.ordinal()] = 4;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[RefUpdate.Result.NOT_ATTEMPTED.ordinal()] = 1;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[RefUpdate.Result.NO_CHANGE.ordinal()] = 3;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED.ordinal()] = 7;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED_CURRENT_BRANCH.ordinal()] = 8;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED_MISSING_OBJECT.ordinal()] = 11;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[RefUpdate.Result.REJECTED_OTHER_REASON.ordinal()] = 12;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[RefUpdate.Result.RENAMED.ordinal()] = 10;
        } catch (NoSuchFieldError unused12) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result = iArr2;
        return iArr2;
    }

    RefDirectoryRename(RefDirectoryUpdate refDirectoryUpdate, RefDirectoryUpdate refDirectoryUpdate2) {
        super(refDirectoryUpdate, refDirectoryUpdate2);
        this.refdb = refDirectoryUpdate.getRefDatabase();
    }

    @Override // org.eclipse.jgit.lib.RefRename
    protected RefUpdate.Result doRename() throws IOException {
        if (this.source.getRef().isSymbolic()) {
            return RefUpdate.Result.IO_FAILURE;
        }
        this.objId = this.source.getOldObjectId();
        boolean zNeedToUpdateHEAD = needToUpdateHEAD();
        this.tmp = this.refdb.newTemporaryUpdate();
        Throwable th = null;
        try {
            RevWalk revWalk = new RevWalk(this.refdb.getRepository());
            try {
                this.tmp.setNewObjectId(this.objId);
                this.tmp.setForceUpdate(true);
                this.tmp.disableRefLog();
                int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[this.tmp.update(revWalk).ordinal()];
                if (i != 3 && i != 4 && i != 5) {
                    RefUpdate.Result result = this.tmp.getResult();
                    revWalk.close();
                    try {
                        this.refdb.delete(this.tmp);
                    } catch (IOException unused) {
                        FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                    }
                    return result;
                }
                if (!renameLog(this.source, this.tmp)) {
                    RefUpdate.Result result2 = RefUpdate.Result.IO_FAILURE;
                    revWalk.close();
                    try {
                        this.refdb.delete(this.tmp);
                    } catch (IOException unused2) {
                        FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                    }
                    return result2;
                }
                RefUpdate refUpdateNewUpdate = this.destination;
                if (zNeedToUpdateHEAD) {
                    if (!linkHEAD(this.destination)) {
                        renameLog(this.tmp, this.source);
                        RefUpdate.Result result3 = RefUpdate.Result.LOCK_FAILURE;
                        revWalk.close();
                        try {
                            this.refdb.delete(this.tmp);
                        } catch (IOException unused3) {
                            FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                        }
                        return result3;
                    }
                    refUpdateNewUpdate = this.refdb.newUpdate("HEAD", false);
                    refUpdateNewUpdate.setRefLogIdent(this.destination.getRefLogIdent());
                    refUpdateNewUpdate.setRefLogMessage(this.destination.getRefLogMessage(), false);
                }
                this.source.setExpectedOldObjectId(this.objId);
                this.source.setForceUpdate(true);
                this.source.disableRefLog();
                if (this.source.delete(revWalk) != RefUpdate.Result.FORCED) {
                    renameLog(this.tmp, this.source);
                    if (zNeedToUpdateHEAD) {
                        linkHEAD(this.source);
                    }
                    RefUpdate.Result result4 = this.source.getResult();
                    revWalk.close();
                    try {
                        this.refdb.delete(this.tmp);
                    } catch (IOException unused4) {
                        FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                    }
                    return result4;
                }
                if (!renameLog(this.tmp, this.destination)) {
                    renameLog(this.tmp, this.source);
                    this.source.setExpectedOldObjectId(ObjectId.zeroId());
                    this.source.setNewObjectId(this.objId);
                    this.source.update(revWalk);
                    if (zNeedToUpdateHEAD) {
                        linkHEAD(this.source);
                    }
                    RefUpdate.Result result5 = RefUpdate.Result.IO_FAILURE;
                    revWalk.close();
                    try {
                        this.refdb.delete(this.tmp);
                    } catch (IOException unused5) {
                        FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                    }
                    return result5;
                }
                refUpdateNewUpdate.setExpectedOldObjectId(ObjectId.zeroId());
                refUpdateNewUpdate.setNewObjectId(this.objId);
                if (refUpdateNewUpdate.update(revWalk) == RefUpdate.Result.NEW) {
                    RefUpdate.Result result6 = RefUpdate.Result.RENAMED;
                    revWalk.close();
                    try {
                        this.refdb.delete(this.tmp);
                    } catch (IOException unused6) {
                        FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                    }
                    return result6;
                }
                if (renameLog(this.destination, this.tmp)) {
                    renameLog(this.tmp, this.source);
                }
                this.source.setExpectedOldObjectId(ObjectId.zeroId());
                this.source.setNewObjectId(this.objId);
                this.source.update(revWalk);
                if (zNeedToUpdateHEAD) {
                    linkHEAD(this.source);
                }
                RefUpdate.Result result7 = refUpdateNewUpdate.getResult();
                revWalk.close();
                try {
                    this.refdb.delete(this.tmp);
                } catch (IOException unused7) {
                    FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                }
                return result7;
            } catch (Throwable th2) {
                revWalk.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                try {
                    th.addSuppressed(th3);
                } catch (Throwable th4) {
                    try {
                        this.refdb.delete(this.tmp);
                    } catch (IOException unused8) {
                        FileUtils.delete(this.refdb.fileFor(this.tmp.getName()));
                    }
                    throw th4;
                }
            }
            throw null;
        }
    }

    private boolean renameLog(RefUpdate refUpdate, RefUpdate refUpdate2) {
        File fileLogFor = this.refdb.logFor(refUpdate.getName());
        File fileLogFor2 = this.refdb.logFor(refUpdate2.getName());
        if (!fileLogFor.exists()) {
            return true;
        }
        if (!rename(fileLogFor, fileLogFor2)) {
            return false;
        }
        try {
            RefDirectory.delete(fileLogFor, RefDirectory.levelsIn(refUpdate.getName()) - 2);
            return true;
        } catch (IOException unused) {
            rename(fileLogFor2, fileLogFor);
            return false;
        }
    }

    private static boolean rename(File file, File file2) {
        File parentFile;
        try {
            FileUtils.rename(file, file2, StandardCopyOption.ATOMIC_MOVE);
            return true;
        } catch (AtomicMoveNotSupportedException e) {
            LOG.error(e.getMessage(), (Throwable) e);
            parentFile = file2.getParentFile();
            if ((parentFile.exists() && parentFile.mkdirs()) || parentFile.isDirectory()) {
                try {
                    FileUtils.rename(file, file2, StandardCopyOption.ATOMIC_MOVE);
                    return true;
                } catch (IOException e2) {
                    LOG.error(e2.getMessage(), (Throwable) e2);
                    return false;
                }
            }
        } catch (IOException unused) {
            parentFile = file2.getParentFile();
            return parentFile.exists() ? false : false;
            FileUtils.rename(file, file2, StandardCopyOption.ATOMIC_MOVE);
            return true;
        }
    }

    private boolean linkHEAD(RefUpdate refUpdate) {
        try {
            RefDirectoryUpdate refDirectoryUpdateNewUpdate = this.refdb.newUpdate("HEAD", false);
            refDirectoryUpdateNewUpdate.disableRefLog();
            int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[refDirectoryUpdateNewUpdate.link(refUpdate.getName()).ordinal()];
            return i == 3 || i == 4 || i == 5;
        } catch (IOException unused) {
            return false;
        }
    }
}
