package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.ContentMergeStrategy;
import org.eclipse.jgit.merge.MergeConfig;
import org.eclipse.jgit.merge.MergeMessageFormatter;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.Merger;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.SquashMessageFormatter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevWalkUtils;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.StringUtils;

/* JADX INFO: loaded from: classes13.dex */
public class MergeCommand extends GitCommand<MergeResult> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
    private Boolean commit;
    private List<Ref> commits;
    private ContentMergeStrategy contentStrategy;
    private FastForwardMode fastForwardMode;
    private boolean insertChangeId;
    private MergeStrategy mergeStrategy;
    private String message;
    private ProgressMonitor monitor;
    private Boolean squash;

    public enum ConflictStyle {
        MERGE,
        DIFF3;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static ConflictStyle[] valuesCustom() {
            ConflictStyle[] conflictStyleArrValuesCustom = values();
            int length = conflictStyleArrValuesCustom.length;
            ConflictStyle[] conflictStyleArr = new ConflictStyle[length];
            System.arraycopy(conflictStyleArrValuesCustom, 0, conflictStyleArr, 0, length);
            return conflictStyleArr;
        }
    }

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

    public enum FastForwardMode implements Config.ConfigEnum {
        FF,
        NO_FF,
        FF_ONLY;

        private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode$Merge;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static FastForwardMode[] valuesCustom() {
            FastForwardMode[] fastForwardModeArrValuesCustom = values();
            int length = fastForwardModeArrValuesCustom.length;
            FastForwardMode[] fastForwardModeArr = new FastForwardMode[length];
            System.arraycopy(fastForwardModeArrValuesCustom, 0, fastForwardModeArr, 0, length);
            return fastForwardModeArr;
        }

        static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode$Merge() {
            int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode$Merge;
            if (iArr != null) {
                return iArr;
            }
            int[] iArr2 = new int[Merge.valuesCustom().length];
            try {
                iArr2[Merge.FALSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr2[Merge.ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr2[Merge.TRUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode$Merge = iArr2;
            return iArr2;
        }

        @Override // org.eclipse.jgit.lib.Config.ConfigEnum
        public String toConfigValue() {
            return "--" + name().toLowerCase(Locale.ROOT).replace('_', '-');
        }

        @Override // org.eclipse.jgit.lib.Config.ConfigEnum
        public boolean matchConfigValue(String str) {
            if (!StringUtils.isEmptyOrNull(str) && str.startsWith("--")) {
                return name().equalsIgnoreCase(str.substring(2).replace('-', '_'));
            }
            return false;
        }

        public enum Merge {
            TRUE,
            FALSE,
            ONLY;

            private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode;

            /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
            public static Merge[] valuesCustom() {
                Merge[] mergeArrValuesCustom = values();
                int length = mergeArrValuesCustom.length;
                Merge[] mergeArr = new Merge[length];
                System.arraycopy(mergeArrValuesCustom, 0, mergeArr, 0, length);
                return mergeArr;
            }

            static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode() {
                int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode;
                if (iArr != null) {
                    return iArr;
                }
                int[] iArr2 = new int[FastForwardMode.valuesCustom().length];
                try {
                    iArr2[FastForwardMode.FF.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr2[FastForwardMode.FF_ONLY.ordinal()] = 3;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr2[FastForwardMode.NO_FF.ordinal()] = 2;
                } catch (NoSuchFieldError unused3) {
                }
                $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode = iArr2;
                return iArr2;
            }

            public static Merge valueOf(FastForwardMode fastForwardMode) {
                int i = $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode()[fastForwardMode.ordinal()];
                if (i == 2) {
                    return FALSE;
                }
                if (i == 3) {
                    return ONLY;
                }
                return TRUE;
            }
        }

        public static FastForwardMode valueOf(Merge merge) {
            int i = $SWITCH_TABLE$org$eclipse$jgit$api$MergeCommand$FastForwardMode$Merge()[merge.ordinal()];
            if (i == 2) {
                return NO_FF;
            }
            if (i == 3) {
                return FF_ONLY;
            }
            return FF;
        }
    }

    protected MergeCommand(Repository repository) {
        super(repository);
        this.mergeStrategy = MergeStrategy.RECURSIVE;
        this.commits = new LinkedList();
        this.monitor = NullProgressMonitor.INSTANCE;
    }

    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public MergeResult call() throws Throwable {
        DirCacheCheckout dirCacheCheckout;
        Throwable th;
        boolean zMerge;
        List<String> unmergedPaths;
        Map<String, org.eclipse.jgit.merge.MergeResult<? extends Sequence>> map;
        Map<String, ResolveMerger.MergeFailureReason> map2;
        MergeResult.MergeStatus mergeStatus;
        ObjectId id2;
        ObjectId id3;
        String str;
        MergeResult.MergeStatus mergeStatus2;
        String str2;
        MergeResult.MergeStatus mergeStatus3;
        ObjectId objectId;
        checkCallable();
        fallBackToConfiguration();
        checkParameters();
        Throwable th2 = null;
        try {
            try {
                RevWalk revWalk = new RevWalk(this.repo);
                try {
                    Ref refExactRef = this.repo.exactRef("HEAD");
                    if (refExactRef == null) {
                        throw new NoHeadException(JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
                    }
                    StringBuilder sb = new StringBuilder("merge ");
                    Ref ref = this.commits.get(0);
                    sb.append(ref.getName());
                    Ref refPeel = this.repo.getRefDatabase().peel(ref);
                    ObjectId peeledObjectId = refPeel.getPeeledObjectId();
                    if (peeledObjectId == null) {
                        peeledObjectId = refPeel.getObjectId();
                    }
                    RevCommit revCommitLookupCommit = revWalk.lookupCommit(peeledObjectId);
                    ObjectId objectId2 = refExactRef.getObjectId();
                    if (objectId2 == null) {
                        revWalk.parseHeaders(revCommitLookupCommit);
                        DirCacheCheckout dirCacheCheckout2 = new DirCacheCheckout(this.repo, this.repo.lockDirCache(), revCommitLookupCommit.getTree());
                        try {
                            dirCacheCheckout2.setFailOnConflict(true);
                            dirCacheCheckout2.setProgressMonitor(this.monitor);
                            dirCacheCheckout2.checkout();
                            RefUpdate refUpdateUpdateRef = this.repo.updateRef(refExactRef.getTarget().getName());
                            refUpdateUpdateRef.setNewObjectId(peeledObjectId);
                            refUpdateUpdateRef.setExpectedOldObjectId(null);
                            refUpdateUpdateRef.setRefLogMessage("initial pull", false);
                            if (refUpdateUpdateRef.update() != RefUpdate.Result.NEW) {
                                throw new NoHeadException(JGitText.get().commitOnRepoWithoutHEADCurrentlyNotSupported);
                            }
                            setCallable(false);
                            ObjectId[] objectIdArr = new ObjectId[2];
                            objectIdArr[1] = revCommitLookupCommit;
                            MergeResult mergeResult = new MergeResult(revCommitLookupCommit, revCommitLookupCommit, objectIdArr, MergeResult.MergeStatus.FAST_FORWARD, this.mergeStrategy, null, null);
                            try {
                                revWalk.close();
                                return mergeResult;
                            } catch (Throwable th3) {
                                th = th3;
                                dirCacheCheckout = dirCacheCheckout2;
                                if (0 == 0) {
                                    throw th;
                                }
                                if (null == th) {
                                    throw null;
                                }
                                try {
                                    th2.addSuppressed(th);
                                    throw null;
                                } catch (CheckoutConflictException e) {
                                    throw new org.eclipse.jgit.api.errors.CheckoutConflictException(dirCacheCheckout == null ? Collections.emptyList() : dirCacheCheckout.getConflicts(), e);
                                } catch (IOException e2) {
                                    throw new JGitInternalException(MessageFormat.format(JGitText.get().exceptionCaughtDuringExecutionOfMergeCommand, e2), e2);
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            revWalk.close();
                            throw th;
                        }
                    }
                    RevCommit revCommitLookupCommit2 = revWalk.lookupCommit(objectId2);
                    if (revWalk.isMergedInto(revCommitLookupCommit, revCommitLookupCommit2)) {
                        setCallable(false);
                        MergeResult mergeResult2 = new MergeResult(revCommitLookupCommit2, revCommitLookupCommit, new ObjectId[]{revCommitLookupCommit2, revCommitLookupCommit}, MergeResult.MergeStatus.ALREADY_UP_TO_DATE, this.mergeStrategy, null, null);
                        revWalk.close();
                        return mergeResult2;
                    }
                    try {
                        if (revWalk.isMergedInto(revCommitLookupCommit2, revCommitLookupCommit) && this.fastForwardMode != FastForwardMode.NO_FF) {
                            sb.append(": " + MergeResult.MergeStatus.FAST_FORWARD);
                            DirCacheCheckout dirCacheCheckout3 = new DirCacheCheckout(this.repo, revCommitLookupCommit2.getTree(), this.repo.lockDirCache(), revCommitLookupCommit.getTree());
                            dirCacheCheckout3.setProgressMonitor(this.monitor);
                            dirCacheCheckout3.setFailOnConflict(true);
                            dirCacheCheckout3.checkout();
                            if (this.squash.booleanValue()) {
                                String str3 = JGitText.get().squashCommitNotUpdatingHEAD;
                                MergeResult.MergeStatus mergeStatus4 = MergeResult.MergeStatus.FAST_FORWARD_SQUASHED;
                                this.repo.writeSquashCommitMsg(new SquashMessageFormatter().format(RevWalkUtils.find(revWalk, revCommitLookupCommit, revCommitLookupCommit2), refExactRef));
                                str2 = str3;
                                mergeStatus3 = mergeStatus4;
                                objectId = objectId2;
                            } else {
                                updateHead(sb, revCommitLookupCommit, objectId2);
                                mergeStatus3 = MergeResult.MergeStatus.FAST_FORWARD;
                                str2 = null;
                                objectId = revCommitLookupCommit;
                            }
                            setCallable(false);
                            MergeResult mergeResult3 = new MergeResult(objectId, objectId, new ObjectId[]{revCommitLookupCommit2, revCommitLookupCommit}, mergeStatus3, this.mergeStrategy, null, str2);
                            revWalk.close();
                            return mergeResult3;
                        }
                        if (this.fastForwardMode == FastForwardMode.FF_ONLY) {
                            MergeResult mergeResult4 = new MergeResult(revCommitLookupCommit2, revCommitLookupCommit, new ObjectId[]{revCommitLookupCommit2, revCommitLookupCommit}, MergeResult.MergeStatus.ABORTED, this.mergeStrategy, null, null);
                            revWalk.close();
                            return mergeResult4;
                        }
                        String str4 = "";
                        if (this.squash.booleanValue()) {
                            this.repo.writeSquashCommitMsg(new SquashMessageFormatter().format(RevWalkUtils.find(revWalk, revCommitLookupCommit, revCommitLookupCommit2), refExactRef));
                        } else {
                            str4 = this.message;
                            if (str4 == null) {
                                str4 = new MergeMessageFormatter().format(this.commits, refExactRef);
                            }
                            this.repo.writeMergeCommitMsg(str4);
                            this.repo.writeMergeHeads(Arrays.asList(refPeel.getObjectId()));
                        }
                        Merger mergerNewMerger = this.mergeStrategy.newMerger(this.repo);
                        mergerNewMerger.setProgressMonitor(this.monitor);
                        if (mergerNewMerger instanceof ResolveMerger) {
                            ResolveMerger resolveMerger = (ResolveMerger) mergerNewMerger;
                            resolveMerger.setContentMergeStrategy(this.contentStrategy);
                            resolveMerger.setCommitNames(new String[]{"BASE", "HEAD", refPeel.getName()});
                            resolveMerger.setWorkingTreeIterator(new FileTreeIterator(this.repo));
                            zMerge = mergerNewMerger.merge(revCommitLookupCommit2, revCommitLookupCommit);
                            Map<String, org.eclipse.jgit.merge.MergeResult<? extends Sequence>> mergeResults = resolveMerger.getMergeResults();
                            Map<String, ResolveMerger.MergeFailureReason> failingPaths = resolveMerger.getFailingPaths();
                            unmergedPaths = resolveMerger.getUnmergedPaths();
                            if (!resolveMerger.getModifiedFiles().isEmpty()) {
                                this.repo.fireEvent(new WorkingTreeModifiedEvent(resolveMerger.getModifiedFiles(), null));
                            }
                            map = mergeResults;
                            map2 = failingPaths;
                        } else {
                            zMerge = mergerNewMerger.merge(revCommitLookupCommit2, revCommitLookupCommit);
                            unmergedPaths = null;
                            map = null;
                            map2 = null;
                        }
                        sb.append(": Merge made by ");
                        if (revWalk.isMergedInto(revCommitLookupCommit2, revCommitLookupCommit)) {
                            sb.append("recursive");
                        } else {
                            sb.append(this.mergeStrategy.getName());
                        }
                        sb.append('.');
                        if (!zMerge) {
                            if (map2 == null) {
                                this.repo.writeMergeCommitMsg(new MergeMessageFormatter().formatWithConflicts(str4, unmergedPaths));
                                MergeResult mergeResult5 = new MergeResult(null, mergerNewMerger.getBaseCommitId(), new ObjectId[]{revCommitLookupCommit2.getId(), revCommitLookupCommit.getId()}, MergeResult.MergeStatus.CONFLICTING, this.mergeStrategy, map, null);
                                revWalk.close();
                                return mergeResult5;
                            }
                            this.repo.writeMergeCommitMsg(null);
                            this.repo.writeMergeHeads(null);
                            MergeResult mergeResult6 = new MergeResult(null, mergerNewMerger.getBaseCommitId(), new ObjectId[]{revCommitLookupCommit2.getId(), revCommitLookupCommit.getId()}, MergeResult.MergeStatus.FAILED, this.mergeStrategy, map, map2, null);
                            revWalk.close();
                            return mergeResult6;
                        }
                        DirCacheCheckout dirCacheCheckout4 = new DirCacheCheckout(this.repo, revCommitLookupCommit2.getTree(), this.repo.lockDirCache(), mergerNewMerger.getResultTreeId());
                        dirCacheCheckout4.setFailOnConflict(true);
                        dirCacheCheckout4.setProgressMonitor(this.monitor);
                        dirCacheCheckout4.checkout();
                        MergeResult.MergeStatus mergeStatus5 = (this.commit.booleanValue() || !this.squash.booleanValue()) ? null : MergeResult.MergeStatus.MERGED_SQUASHED_NOT_COMMITTED;
                        if (!this.commit.booleanValue() && !this.squash.booleanValue()) {
                            mergeStatus5 = MergeResult.MergeStatus.MERGED_NOT_COMMITTED;
                        }
                        if (!this.commit.booleanValue() || this.squash.booleanValue()) {
                            mergeStatus = mergeStatus5;
                            id2 = null;
                        } else {
                            try {
                                Git git = new Git(getRepository());
                                try {
                                    id2 = git.commit().setReflogComment(sb.toString()).setInsertChangeId(this.insertChangeId).call().getId();
                                    git.close();
                                    mergeStatus = MergeResult.MergeStatus.MERGED;
                                    getRepository().autoGC(this.monitor);
                                } catch (Throwable th5) {
                                    git.close();
                                    throw th5;
                                }
                            } catch (Throwable th6) {
                                if (0 == 0) {
                                    throw th6;
                                }
                                if (null == th6) {
                                    throw null;
                                }
                                th2.addSuppressed(th6);
                                throw null;
                            }
                        }
                        if (this.commit.booleanValue() && this.squash.booleanValue()) {
                            str = JGitText.get().squashCommitNotUpdatingHEAD;
                            id3 = revCommitLookupCommit2.getId();
                            mergeStatus2 = MergeResult.MergeStatus.MERGED_SQUASHED;
                        } else {
                            id3 = id2;
                            str = null;
                            mergeStatus2 = mergeStatus;
                        }
                        MergeResult mergeResult7 = new MergeResult(id3, null, new ObjectId[]{revCommitLookupCommit2.getId(), revCommitLookupCommit.getId()}, mergeStatus2, this.mergeStrategy, null, str);
                        revWalk.close();
                        return mergeResult7;
                    } catch (Throwable th7) {
                        th = th7;
                        th = th;
                        revWalk.close();
                        throw th;
                    }
                } catch (Throwable th8) {
                    th = th8;
                }
            } catch (Throwable th9) {
                th = th9;
            }
        } catch (Throwable th10) {
            th = th10;
            dirCacheCheckout = null;
        }
    }

    private void checkParameters() throws InvalidMergeHeadsException {
        if (this.squash.booleanValue() && this.fastForwardMode == FastForwardMode.NO_FF) {
            throw new JGitInternalException(JGitText.get().cannotCombineSquashWithNoff);
        }
        if (this.commits.size() != 1) {
            throw new InvalidMergeHeadsException(this.commits.isEmpty() ? JGitText.get().noMergeHeadSpecified : MessageFormat.format(JGitText.get().mergeStrategyDoesNotSupportHeads, this.mergeStrategy.getName(), Integer.valueOf(this.commits.size())));
        }
    }

    private void fallBackToConfiguration() {
        MergeConfig configForCurrentBranch = MergeConfig.getConfigForCurrentBranch(this.repo);
        if (this.squash == null) {
            this.squash = Boolean.valueOf(configForCurrentBranch.isSquash());
        }
        if (this.commit == null) {
            this.commit = Boolean.valueOf(configForCurrentBranch.isCommit());
        }
        if (this.fastForwardMode == null) {
            this.fastForwardMode = configForCurrentBranch.getFastForwardMode();
        }
    }

    private void updateHead(StringBuilder sb, ObjectId objectId, ObjectId objectId2) throws ConcurrentRefUpdateException, IOException {
        RefUpdate refUpdateUpdateRef = this.repo.updateRef("HEAD");
        refUpdateUpdateRef.setNewObjectId(objectId);
        refUpdateUpdateRef.setRefLogMessage(sb.toString(), false);
        refUpdateUpdateRef.setExpectedOldObjectId(objectId2);
        RefUpdate.Result resultUpdate = refUpdateUpdateRef.update();
        int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[resultUpdate.ordinal()];
        if (i != 2) {
            if (i == 4 || i == 6) {
                return;
            }
            if (i != 7) {
                throw new JGitInternalException(MessageFormat.format(JGitText.get().updatingRefFailed, "HEAD", objectId.toString(), resultUpdate));
            }
        }
        throw new ConcurrentRefUpdateException(JGitText.get().couldNotLockHEAD, refUpdateUpdateRef.getRef(), resultUpdate);
    }

    public MergeCommand setStrategy(MergeStrategy mergeStrategy) {
        checkCallable();
        this.mergeStrategy = mergeStrategy;
        return this;
    }

    public MergeCommand setContentMergeStrategy(ContentMergeStrategy contentMergeStrategy) {
        checkCallable();
        this.contentStrategy = contentMergeStrategy;
        return this;
    }

    public MergeCommand include(Ref ref) {
        checkCallable();
        this.commits.add(ref);
        return this;
    }

    public MergeCommand include(AnyObjectId anyObjectId) {
        return include(anyObjectId.getName(), anyObjectId);
    }

    public MergeCommand include(String str, AnyObjectId anyObjectId) {
        return include(new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE, str, anyObjectId.copy()));
    }

    public MergeCommand setSquash(boolean z) {
        checkCallable();
        this.squash = Boolean.valueOf(z);
        return this;
    }

    public MergeCommand setFastForward(@Nullable FastForwardMode fastForwardMode) {
        checkCallable();
        this.fastForwardMode = fastForwardMode;
        return this;
    }

    public MergeCommand setCommit(boolean z) {
        this.commit = Boolean.valueOf(z);
        return this;
    }

    public MergeCommand setMessage(String str) {
        this.message = str;
        return this;
    }

    public MergeCommand setInsertChangeId(boolean z) {
        checkCallable();
        this.insertChangeId = z;
        return this;
    }

    public MergeCommand setProgressMonitor(ProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        this.monitor = progressMonitor;
        return this;
    }
}
