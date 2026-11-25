package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

/* JADX INFO: loaded from: classes13.dex */
public class CheckoutCommand extends GitCommand<Ref> {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result;
    private Set<String> actuallyModifiedPaths;
    private boolean checkoutAllPaths;
    private Stage checkoutStage;
    private boolean createBranch;
    private boolean forceRefUpdate;
    private boolean forced;
    private ProgressMonitor monitor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f633name;
    private boolean orphan;
    private List<String> paths;
    private RevCommit startCommit;
    private String startPoint;
    private CheckoutResult status;
    private CreateBranchCommand.SetupUpstreamMode upstreamMode;

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

    public enum Stage {
        BASE(1),
        OURS(2),
        THEIRS(3);

        private final int number;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Stage[] valuesCustom() {
            Stage[] stageArrValuesCustom = values();
            int length = stageArrValuesCustom.length;
            Stage[] stageArr = new Stage[length];
            System.arraycopy(stageArrValuesCustom, 0, stageArr, 0, length);
            return stageArr;
        }

        Stage(int i) {
            this.number = i;
        }
    }

    protected CheckoutCommand(Repository repository) {
        super(repository);
        this.forceRefUpdate = false;
        this.forced = false;
        this.createBranch = false;
        this.orphan = false;
        this.startPoint = null;
        this.checkoutStage = null;
        this.monitor = NullProgressMonitor.INSTANCE;
        this.paths = new LinkedList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.eclipse.jgit.api.GitCommand, java.util.concurrent.Callable
    public Ref call() throws GitAPIException {
        ObjectId objectIdResolve;
        RefUpdate.Result resultForceUpdate;
        checkCallable();
        try {
            try {
                processOptions();
                Throwable th = null;
                Ref refExactRef = null;
                Object[] objArr = 0;
                if (!this.checkoutAllPaths && this.paths.isEmpty()) {
                    if (this.createBranch) {
                        try {
                            Git git = new Git(this.repo);
                            try {
                                CreateBranchCommand createBranchCommandBranchCreate = git.branchCreate();
                                createBranchCommandBranchCreate.setName(this.f633name);
                                RevCommit revCommit = this.startCommit;
                                if (revCommit != null) {
                                    createBranchCommandBranchCreate.setStartPoint(revCommit);
                                } else {
                                    createBranchCommandBranchCreate.setStartPoint(this.startPoint);
                                }
                                CreateBranchCommand.SetupUpstreamMode setupUpstreamMode = this.upstreamMode;
                                if (setupUpstreamMode != null) {
                                    createBranchCommandBranchCreate.setUpstreamMode(setupUpstreamMode);
                                }
                                createBranchCommandBranchCreate.call();
                                git.close();
                            } catch (Throwable th2) {
                                git.close();
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
                    Ref refExactRef2 = this.repo.exactRef("HEAD");
                    if (refExactRef2 == null) {
                        throw new UnsupportedOperationException(JGitText.get().cannotCheckoutFromUnbornBranch);
                    }
                    String str = "checkout: moving from " + getShortBranchName(refExactRef2);
                    if (!this.orphan) {
                        objectIdResolve = this.repo.resolve(this.f633name);
                        if (objectIdResolve == null) {
                            throw new RefNotFoundException(MessageFormat.format(JGitText.get().refNotResolved, this.f633name));
                        }
                    } else {
                        if (this.startPoint == null && this.startCommit == null) {
                            RefUpdate.Result resultLink = this.repo.updateRef("HEAD").link(getBranchName());
                            if (!EnumSet.of(RefUpdate.Result.NEW, RefUpdate.Result.FORCED).contains(resultLink)) {
                                throw new JGitInternalException(MessageFormat.format(JGitText.get().checkoutUnexpectedResult, resultLink.name()));
                            }
                            this.status = CheckoutResult.NOT_TRIED_RESULT;
                            Ref refExactRef3 = this.repo.exactRef("HEAD");
                            if (this.status == null) {
                                this.status = CheckoutResult.ERROR_RESULT;
                            }
                            return refExactRef3;
                        }
                        objectIdResolve = getStartPointObjectId();
                    }
                    try {
                        RevWalk revWalk = new RevWalk(this.repo);
                        try {
                            ObjectId objectId = refExactRef2.getObjectId();
                            RevCommit commit = objectId == null ? null : revWalk.parseCommit(objectId);
                            RevCommit commit2 = revWalk.parseCommit(objectIdResolve);
                            revWalk.close();
                            RevTree tree = commit == null ? null : commit.getTree();
                            DirCache dirCacheLockDirCache = this.repo.lockDirCache();
                            try {
                                DirCacheCheckout dirCacheCheckout = new DirCacheCheckout(this.repo, tree, dirCacheLockDirCache, commit2.getTree());
                                boolean z = true;
                                dirCacheCheckout.setFailOnConflict(true);
                                dirCacheCheckout.setForce(this.forced);
                                if (this.forced) {
                                    dirCacheCheckout.setFailOnConflict(false);
                                }
                                dirCacheCheckout.setProgressMonitor(this.monitor);
                                try {
                                    dirCacheCheckout.checkout();
                                    dirCacheLockDirCache.unlock();
                                    Ref refFindRef = this.repo.findRef(this.f633name);
                                    if (refFindRef == null || refFindRef.getName().startsWith(Constants.R_HEADS)) {
                                        refExactRef = refFindRef;
                                    }
                                    String strShortenRefName = Repository.shortenRefName(this.f633name);
                                    Repository repository = this.repo;
                                    if (refExactRef != null) {
                                        z = false;
                                    }
                                    RefUpdate refUpdateUpdateRef = repository.updateRef("HEAD", z);
                                    refUpdateUpdateRef.setForceUpdate(this.forceRefUpdate);
                                    refUpdateUpdateRef.setRefLogMessage(String.valueOf(str) + " to " + strShortenRefName, false);
                                    if (refExactRef != null) {
                                        resultForceUpdate = refUpdateUpdateRef.link(refExactRef.getName());
                                    } else if (this.orphan) {
                                        RefUpdate.Result resultLink2 = refUpdateUpdateRef.link(getBranchName());
                                        refExactRef = this.repo.exactRef("HEAD");
                                        resultForceUpdate = resultLink2;
                                    } else {
                                        refUpdateUpdateRef.setNewObjectId(commit2);
                                        resultForceUpdate = refUpdateUpdateRef.forceUpdate();
                                    }
                                    setCallable(false);
                                    int i = $SWITCH_TABLE$org$eclipse$jgit$lib$RefUpdate$Result()[resultForceUpdate.ordinal()];
                                    if (i != 3 && i != 4 && i != 5 && i != 6) {
                                        throw new JGitInternalException(MessageFormat.format(JGitText.get().checkoutUnexpectedResult, resultForceUpdate.name()));
                                    }
                                    if (dirCacheCheckout.getToBeDeleted().isEmpty()) {
                                        this.status = new CheckoutResult(new ArrayList(dirCacheCheckout.getUpdated().keySet()), dirCacheCheckout.getRemoved());
                                    } else {
                                        this.status = new CheckoutResult(CheckoutResult.Status.NONDELETED, dirCacheCheckout.getToBeDeleted(), new ArrayList(dirCacheCheckout.getUpdated().keySet()), dirCacheCheckout.getRemoved());
                                    }
                                    if (this.status == null) {
                                        this.status = CheckoutResult.ERROR_RESULT;
                                    }
                                    return refExactRef;
                                } catch (CheckoutConflictException e) {
                                    this.status = new CheckoutResult(CheckoutResult.Status.CONFLICTS, dirCacheCheckout.getConflicts());
                                    throw new org.eclipse.jgit.api.errors.CheckoutConflictException(dirCacheCheckout.getConflicts(), e);
                                }
                            } catch (Throwable th4) {
                                dirCacheLockDirCache.unlock();
                                throw th4;
                            }
                        } catch (Throwable th5) {
                            revWalk.close();
                            throw th5;
                        }
                    } catch (Throwable th6) {
                        if (0 == 0) {
                            throw th6;
                        }
                        if (null != th6) {
                            (objArr == true ? 1 : 0).addSuppressed(th6);
                        }
                        throw null;
                    }
                }
                checkoutPaths();
                this.status = new CheckoutResult(CheckoutResult.Status.OK, this.paths);
                setCallable(false);
                if (this.status == null) {
                    this.status = CheckoutResult.ERROR_RESULT;
                }
                return null;
            } catch (IOException e2) {
                throw new JGitInternalException(e2.getMessage(), e2);
            }
        } catch (Throwable th7) {
            if (this.status == null) {
                this.status = CheckoutResult.ERROR_RESULT;
            }
            throw th7;
        }
    }

    private String getShortBranchName(Ref ref) {
        if (ref.isSymbolic()) {
            return Repository.shortenRefName(ref.getTarget().getName());
        }
        ObjectId objectId = ref.getObjectId();
        objectId.getClass();
        return objectId.getName();
    }

    public CheckoutCommand setProgressMonitor(ProgressMonitor progressMonitor) {
        if (progressMonitor == null) {
            progressMonitor = NullProgressMonitor.INSTANCE;
        }
        this.monitor = progressMonitor;
        return this;
    }

    public CheckoutCommand addPath(String str) {
        checkCallable();
        this.paths.add(str);
        return this;
    }

    public CheckoutCommand addPaths(List<String> list) {
        checkCallable();
        this.paths.addAll(list);
        return this;
    }

    public CheckoutCommand setAllPaths(boolean z) {
        this.checkoutAllPaths = z;
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0099 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:61:0x009b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:? A[RETURN, SYNTHETIC] */
    protected CheckoutCommand checkoutPaths() throws Throwable {
        Throwable th;
        this.actuallyModifiedPaths = new HashSet();
        DirCache dirCacheLockDirCache = this.repo.lockDirCache();
        try {
            RevWalk revWalk = new RevWalk(this.repo);
            try {
                TreeWalk treeWalk = new TreeWalk(this.repo, revWalk.getObjectReader());
                try {
                    treeWalk.setRecursive(true);
                    if (!this.checkoutAllPaths) {
                        treeWalk.setFilter(PathFilterGroup.createFromStrings(this.paths));
                    }
                    if (isCheckoutIndex()) {
                        checkoutPathsFromIndex(treeWalk, dirCacheLockDirCache);
                    } else {
                        checkoutPathsFromCommit(treeWalk, dirCacheLockDirCache, revWalk.parseCommit(getStartPointObjectId()));
                    }
                    treeWalk.close();
                    revWalk.close();
                    try {
                        dirCacheLockDirCache.unlock();
                        return this;
                    } finally {
                        WorkingTreeModifiedEvent workingTreeModifiedEvent = new WorkingTreeModifiedEvent(this.actuallyModifiedPaths, null);
                        this.actuallyModifiedPaths = null;
                        if (!workingTreeModifiedEvent.isEmpty()) {
                            this.repo.fireEvent(workingTreeModifiedEvent);
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        treeWalk.close();
                        throw th;
                    } catch (Throwable th3) {
                        th = th3;
                        if (th != null) {
                            if (th != th) {
                                try {
                                    th.addSuppressed(th);
                                } catch (Throwable th4) {
                                    th = th4;
                                    if (th != null) {
                                        throw th;
                                    }
                                    if (th != th) {
                                        try {
                                            th.addSuppressed(th);
                                        } catch (Throwable th5) {
                                            try {
                                                dirCacheLockDirCache.unlock();
                                                throw th5;
                                            } finally {
                                                WorkingTreeModifiedEvent workingTreeModifiedEvent2 = new WorkingTreeModifiedEvent(this.actuallyModifiedPaths, null);
                                                this.actuallyModifiedPaths = null;
                                                if (!workingTreeModifiedEvent2.isEmpty()) {
                                                    this.repo.fireEvent(workingTreeModifiedEvent2);
                                                }
                                            }
                                        }
                                    }
                                    throw th;
                                }
                            }
                            th = th;
                        }
                        try {
                            revWalk.close();
                            throw th;
                        } catch (Throwable th6) {
                            th = th6;
                            th = th;
                            if (th != null) {
                                throw th;
                            }
                            if (th != th) {
                                th.addSuppressed(th);
                            }
                            throw th;
                        }
                    }
                }
            } catch (Throwable th7) {
                th = th7;
                th = null;
            }
        } catch (Throwable th8) {
            th = th8;
            th = null;
        }
    }

    private void checkoutPathsFromIndex(TreeWalk treeWalk, DirCache dirCache) throws IOException {
        treeWalk.addTree(new DirCacheIterator(dirCache));
        final ObjectReader objectReader = treeWalk.getObjectReader();
        DirCacheEditor dirCacheEditorEditor = dirCache.editor();
        Object obj = null;
        while (treeWalk.next()) {
            final String pathString = treeWalk.getPathString();
            if (!pathString.equals(obj)) {
                final CoreConfig.EolStreamType eolStreamType = treeWalk.getEolStreamType(TreeWalk.OperationType.CHECKOUT_OP);
                final String filterCommand = treeWalk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
                dirCacheEditorEditor.add(new DirCacheEditor.PathEdit(pathString) { // from class: org.eclipse.jgit.api.CheckoutCommand.1
                    @Override // org.eclipse.jgit.dircache.DirCacheEditor.PathEdit
                    public void apply(DirCacheEntry dirCacheEntry) {
                        int stage = dirCacheEntry.getStage();
                        if (stage > 0) {
                            if (CheckoutCommand.this.checkoutStage != null) {
                                if (stage == CheckoutCommand.this.checkoutStage.number) {
                                    CheckoutCommand.this.checkoutPath(dirCacheEntry, objectReader, new DirCacheCheckout.CheckoutMetadata(eolStreamType, filterCommand));
                                    CheckoutCommand.this.actuallyModifiedPaths.add(pathString);
                                    return;
                                }
                                return;
                            }
                            UnmergedPathException unmergedPathException = new UnmergedPathException(dirCacheEntry);
                            throw new JGitInternalException(unmergedPathException.getMessage(), unmergedPathException);
                        }
                        CheckoutCommand.this.checkoutPath(dirCacheEntry, objectReader, new DirCacheCheckout.CheckoutMetadata(eolStreamType, filterCommand));
                        CheckoutCommand.this.actuallyModifiedPaths.add(pathString);
                    }
                });
                obj = pathString;
            }
        }
        dirCacheEditorEditor.commit();
    }

    private void checkoutPathsFromCommit(TreeWalk treeWalk, DirCache dirCache, RevCommit revCommit) throws IOException {
        treeWalk.addTree(revCommit.getTree());
        final ObjectReader objectReader = treeWalk.getObjectReader();
        DirCacheEditor dirCacheEditorEditor = dirCache.editor();
        while (treeWalk.next()) {
            final ObjectId objectId = treeWalk.getObjectId(0);
            final FileMode fileMode = treeWalk.getFileMode(0);
            final CoreConfig.EolStreamType eolStreamType = treeWalk.getEolStreamType(TreeWalk.OperationType.CHECKOUT_OP);
            final String filterCommand = treeWalk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
            final String pathString = treeWalk.getPathString();
            dirCacheEditorEditor.add(new DirCacheEditor.PathEdit(pathString) { // from class: org.eclipse.jgit.api.CheckoutCommand.2
                @Override // org.eclipse.jgit.dircache.DirCacheEditor.PathEdit
                public void apply(DirCacheEntry dirCacheEntry) {
                    if (dirCacheEntry.getStage() != 0) {
                        dirCacheEntry.setStage(0);
                    }
                    dirCacheEntry.setObjectId(objectId);
                    dirCacheEntry.setFileMode(fileMode);
                    CheckoutCommand.this.checkoutPath(dirCacheEntry, objectReader, new DirCacheCheckout.CheckoutMetadata(eolStreamType, filterCommand));
                    CheckoutCommand.this.actuallyModifiedPaths.add(pathString);
                }
            });
        }
        dirCacheEditorEditor.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkoutPath(DirCacheEntry dirCacheEntry, ObjectReader objectReader, DirCacheCheckout.CheckoutMetadata checkoutMetadata) {
        try {
            DirCacheCheckout.checkoutEntry(this.repo, dirCacheEntry, objectReader, true, checkoutMetadata);
        } catch (IOException e) {
            throw new JGitInternalException(MessageFormat.format(JGitText.get().checkoutConflictWithFile, dirCacheEntry.getPathString()), e);
        }
    }

    private boolean isCheckoutIndex() {
        return this.startCommit == null && this.startPoint == null;
    }

    private ObjectId getStartPointObjectId() throws RefNotFoundException, IOException {
        RevCommit revCommit = this.startCommit;
        if (revCommit != null) {
            return revCommit.getId();
        }
        String str = this.startPoint;
        if (str == null) {
            str = "HEAD";
        }
        ObjectId objectIdResolve = this.repo.resolve(str);
        if (objectIdResolve != null) {
            return objectIdResolve;
        }
        throw new RefNotFoundException(MessageFormat.format(JGitText.get().refNotResolved, str));
    }

    private void processOptions() throws InvalidRefNameException, IOException, RefAlreadyExistsException {
        if (((!this.checkoutAllPaths && this.paths.isEmpty()) || this.orphan) && (this.f633name == null || !Repository.isValidRefName(Constants.R_HEADS + this.f633name))) {
            String str = JGitText.get().branchNameInvalid;
            String str2 = this.f633name;
            if (str2 == null) {
                str2 = "<null>";
            }
            throw new InvalidRefNameException(MessageFormat.format(str, str2));
        }
        if (this.orphan && this.repo.exactRef(getBranchName()) != null) {
            throw new RefAlreadyExistsException(MessageFormat.format(JGitText.get().refAlreadyExists, this.f633name));
        }
    }

    private String getBranchName() {
        if (this.f633name.startsWith(Constants.R_REFS)) {
            return this.f633name;
        }
        return Constants.R_HEADS + this.f633name;
    }

    public CheckoutCommand setName(String str) {
        checkCallable();
        this.f633name = str;
        return this;
    }

    public CheckoutCommand setCreateBranch(boolean z) {
        checkCallable();
        this.createBranch = z;
        return this;
    }

    public CheckoutCommand setOrphan(boolean z) {
        checkCallable();
        this.orphan = z;
        return this;
    }

    @Deprecated
    public CheckoutCommand setForce(boolean z) {
        return setForceRefUpdate(z);
    }

    public CheckoutCommand setForceRefUpdate(boolean z) {
        checkCallable();
        this.forceRefUpdate = z;
        return this;
    }

    public CheckoutCommand setForced(boolean z) {
        checkCallable();
        this.forced = z;
        return this;
    }

    public CheckoutCommand setStartPoint(String str) {
        checkCallable();
        this.startPoint = str;
        this.startCommit = null;
        checkOptions();
        return this;
    }

    public CheckoutCommand setStartPoint(RevCommit revCommit) {
        checkCallable();
        this.startCommit = revCommit;
        this.startPoint = null;
        checkOptions();
        return this;
    }

    public CheckoutCommand setUpstreamMode(CreateBranchCommand.SetupUpstreamMode setupUpstreamMode) {
        checkCallable();
        this.upstreamMode = setupUpstreamMode;
        return this;
    }

    public CheckoutCommand setStage(Stage stage) {
        checkCallable();
        this.checkoutStage = stage;
        checkOptions();
        return this;
    }

    public CheckoutResult getResult() {
        CheckoutResult checkoutResult = this.status;
        return checkoutResult == null ? CheckoutResult.NOT_TRIED_RESULT : checkoutResult;
    }

    private void checkOptions() {
        if (this.checkoutStage != null && !isCheckoutIndex()) {
            throw new IllegalStateException(JGitText.get().cannotCheckoutOursSwitchBranch);
        }
    }
}
