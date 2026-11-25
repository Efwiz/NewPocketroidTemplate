package org.eclipse.jgit.merge;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.submodule.SubmoduleConflict;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.LfsFactory;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;

/* JADX INFO: loaded from: classes13.dex */
public class ResolveMerger extends ThreeWayMerger {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy = null;
    private static final Attributes NO_ATTRIBUTES = new Attributes(new Attribute[0]);
    protected static final int T_BASE = 0;
    protected static final int T_FILE = 4;
    protected static final int T_INDEX = 3;
    protected static final int T_OURS = 1;
    protected static final int T_THEIRS = 2;
    protected DirCacheBuilder builder;
    private Map<String, DirCacheCheckout.CheckoutMetadata> checkoutMetadata;
    protected String[] commitNames;
    private ContentMergeStrategy contentStrategy;
    protected DirCache dircache;
    protected boolean enterSubtree;
    protected Map<String, MergeFailureReason> failingPaths;
    protected boolean implicitDirCache;
    protected boolean inCore;
    private int inCoreLimit;
    protected MergeAlgorithm mergeAlgorithm;
    protected Map<String, MergeResult<? extends Sequence>> mergeResults;
    protected List<String> modifiedFiles;
    protected ObjectId resultTree;
    protected Map<String, DirCacheEntry> toBeCheckedOut;
    protected List<String> toBeDeleted;
    protected NameConflictTreeWalk tw;
    protected List<String> unmergedPaths;
    protected WorkingTreeIterator workingTreeIterator;
    protected WorkingTreeOptions workingTreeOptions;

    public enum MergeFailureReason {
        DIRTY_INDEX,
        DIRTY_WORKTREE,
        COULD_NOT_DELETE;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static MergeFailureReason[] valuesCustom() {
            MergeFailureReason[] mergeFailureReasonArrValuesCustom = values();
            int length = mergeFailureReasonArrValuesCustom.length;
            MergeFailureReason[] mergeFailureReasonArr = new MergeFailureReason[length];
            System.arraycopy(mergeFailureReasonArrValuesCustom, 0, mergeFailureReasonArr, 0, length);
            return mergeFailureReasonArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ContentMergeStrategy.valuesCustom().length];
        try {
            iArr2[ContentMergeStrategy.CONFLICT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ContentMergeStrategy.OURS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ContentMergeStrategy.THEIRS.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy = iArr2;
        return iArr2;
    }

    private static MergeAlgorithm getMergeAlgorithm(Config config) {
        return new MergeAlgorithm(DiffAlgorithm.getAlgorithm((DiffAlgorithm.SupportedAlgorithm) config.getEnum("diff", null, ConfigConstants.CONFIG_KEY_ALGORITHM, DiffAlgorithm.SupportedAlgorithm.HISTOGRAM)));
    }

    private static int getInCoreLimit(Config config) {
        return config.getInt("merge", ConfigConstants.CONFIG_KEY_IN_CORE_LIMIT, 10485760);
    }

    private static String[] defaultCommitNames() {
        return new String[]{"BASE", "OURS", "THEIRS"};
    }

    protected ResolveMerger(Repository repository, boolean z) {
        super(repository);
        this.unmergedPaths = new ArrayList();
        this.modifiedFiles = new LinkedList();
        this.toBeCheckedOut = new HashMap();
        this.toBeDeleted = new ArrayList();
        this.mergeResults = new HashMap();
        this.failingPaths = new HashMap();
        this.contentStrategy = ContentMergeStrategy.CONFLICT;
        StoredConfig config = repository.getConfig();
        this.mergeAlgorithm = getMergeAlgorithm(config);
        this.inCoreLimit = getInCoreLimit(config);
        this.commitNames = defaultCommitNames();
        this.inCore = z;
        if (z) {
            this.implicitDirCache = false;
            this.dircache = DirCache.newInCore();
        } else {
            this.implicitDirCache = true;
            this.workingTreeOptions = (WorkingTreeOptions) repository.getConfig().get(WorkingTreeOptions.KEY);
        }
    }

    protected ResolveMerger(Repository repository) {
        this(repository, false);
    }

    protected ResolveMerger(ObjectInserter objectInserter, Config config) {
        super(objectInserter);
        this.unmergedPaths = new ArrayList();
        this.modifiedFiles = new LinkedList();
        this.toBeCheckedOut = new HashMap();
        this.toBeDeleted = new ArrayList();
        this.mergeResults = new HashMap();
        this.failingPaths = new HashMap();
        this.contentStrategy = ContentMergeStrategy.CONFLICT;
        this.mergeAlgorithm = getMergeAlgorithm(config);
        this.commitNames = defaultCommitNames();
        this.inCore = true;
        this.implicitDirCache = false;
        this.dircache = DirCache.newInCore();
    }

    public ContentMergeStrategy getContentMergeStrategy() {
        return this.contentStrategy;
    }

    public void setContentMergeStrategy(ContentMergeStrategy contentMergeStrategy) {
        if (contentMergeStrategy == null) {
            contentMergeStrategy = ContentMergeStrategy.CONFLICT;
        }
        this.contentStrategy = contentMergeStrategy;
    }

    @Override // org.eclipse.jgit.merge.Merger
    protected boolean mergeImpl() throws IOException {
        if (this.implicitDirCache) {
            this.dircache = nonNullRepo().lockDirCache();
        }
        if (!this.inCore) {
            this.checkoutMetadata = new HashMap();
        }
        try {
            return mergeTrees(mergeBase(), this.sourceTrees[0], this.sourceTrees[1], false);
        } finally {
            this.checkoutMetadata = null;
            if (this.implicitDirCache) {
                this.dircache.unlock();
            }
        }
    }

    private void checkout() throws NoWorkTreeException, IOException {
        for (int size = this.toBeDeleted.size() - 1; size >= 0; size--) {
            String str = this.toBeDeleted.get(size);
            File file = new File(nonNullRepo().getWorkTree(), str);
            if (!file.delete() && !file.isDirectory()) {
                this.failingPaths.put(str, MergeFailureReason.COULD_NOT_DELETE);
            }
            this.modifiedFiles.add(str);
        }
        for (Map.Entry<String, DirCacheEntry> entry : this.toBeCheckedOut.entrySet()) {
            DirCacheEntry value = entry.getValue();
            if (value.getFileMode() == FileMode.GITLINK) {
                new File(nonNullRepo().getWorkTree(), entry.getKey()).mkdirs();
            } else {
                DirCacheCheckout.checkoutEntry(this.db, value, this.reader, false, this.checkoutMetadata.get(entry.getKey()));
                this.modifiedFiles.add(entry.getKey());
            }
        }
    }

    protected void cleanUp() throws NoWorkTreeException, IOException {
        if (this.inCore) {
            this.modifiedFiles.clear();
            return;
        }
        DirCache dirCache = nonNullRepo().readDirCache();
        Iterator<String> it = this.modifiedFiles.iterator();
        while (it.hasNext()) {
            String next = it.next();
            DirCacheEntry entry = dirCache.getEntry(next);
            if (entry != null) {
                DirCacheCheckout.checkoutEntry(this.db, entry, this.reader, false, this.checkoutMetadata.get(next));
            }
            it.remove();
        }
    }

    private DirCacheEntry add(byte[] bArr, CanonicalTreeParser canonicalTreeParser, int i, Instant instant, long j) {
        if (canonicalTreeParser == null || canonicalTreeParser.getEntryFileMode().equals(FileMode.TREE)) {
            return null;
        }
        DirCacheEntry dirCacheEntry = new DirCacheEntry(bArr, i);
        dirCacheEntry.setFileMode(canonicalTreeParser.getEntryFileMode());
        dirCacheEntry.setObjectId(canonicalTreeParser.getEntryObjectId());
        dirCacheEntry.setLastModified(instant);
        dirCacheEntry.setLength(j);
        this.builder.add(dirCacheEntry);
        return dirCacheEntry;
    }

    private DirCacheEntry keep(DirCacheEntry dirCacheEntry) {
        DirCacheEntry dirCacheEntry2 = new DirCacheEntry(dirCacheEntry.getRawPath(), dirCacheEntry.getStage());
        dirCacheEntry2.setFileMode(dirCacheEntry.getFileMode());
        dirCacheEntry2.setObjectId(dirCacheEntry.getObjectId());
        dirCacheEntry2.setLastModified(dirCacheEntry.getLastModifiedInstant());
        dirCacheEntry2.setLength(dirCacheEntry.getLength());
        this.builder.add(dirCacheEntry2);
        return dirCacheEntry2;
    }

    protected void addCheckoutMetadata(String str, Attributes attributes) throws IOException {
        if (this.checkoutMetadata != null) {
            this.checkoutMetadata.put(str, new DirCacheCheckout.CheckoutMetadata(EolStreamTypeUtil.detectStreamType(TreeWalk.OperationType.CHECKOUT_OP, this.workingTreeOptions, attributes), this.tw.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
        }
    }

    protected void addToCheckout(String str, DirCacheEntry dirCacheEntry, Attributes attributes) throws IOException {
        this.toBeCheckedOut.put(str, dirCacheEntry);
        addCheckoutMetadata(str, attributes);
    }

    protected void addDeletion(String str, boolean z, Attributes attributes) throws IOException {
        this.toBeDeleted.add(str);
        if (z) {
            addCheckoutMetadata(str, attributes);
        }
    }

    protected boolean processEntry(CanonicalTreeParser canonicalTreeParser, CanonicalTreeParser canonicalTreeParser2, CanonicalTreeParser canonicalTreeParser3, DirCacheBuildIterator dirCacheBuildIterator, WorkingTreeIterator workingTreeIterator, boolean z, Attributes attributes) throws Throwable {
        DirCacheEntry dirCacheEntry;
        MergeResult<RawText> mergeResult;
        MergeResult<RawText> mergeResult2;
        this.enterSubtree = true;
        int rawMode = this.tw.getRawMode(1);
        int rawMode2 = this.tw.getRawMode(2);
        int rawMode3 = this.tw.getRawMode(0);
        boolean z2 = isGitLink(rawMode) || isGitLink(rawMode2) || isGitLink(rawMode3);
        if (rawMode == 0 && rawMode2 == 0 && rawMode3 == 0) {
            return true;
        }
        if (isIndexDirty()) {
            return false;
        }
        if (dirCacheBuildIterator == null || dirCacheBuildIterator.getDirCacheEntry() == null) {
            if (nonTree(rawMode)) {
                dirCacheEntry = new DirCacheEntry(this.tw.getRawPath());
                dirCacheEntry.setObjectId(this.tw.getObjectId(1));
                dirCacheEntry.setFileMode(this.tw.getFileMode(1));
            } else {
                dirCacheEntry = null;
            }
        } else {
            dirCacheEntry = dirCacheBuildIterator.getDirCacheEntry();
        }
        DirCacheEntry dirCacheEntry2 = dirCacheEntry;
        if (nonTree(rawMode) && nonTree(rawMode2) && this.tw.idEqual(1, 2)) {
            if (rawMode == rawMode2) {
                keep(dirCacheEntry2);
                return true;
            }
            int iMergeFileModes = mergeFileModes(rawMode3, rawMode, rawMode2);
            if (iMergeFileModes != FileMode.MISSING.getBits()) {
                if (iMergeFileModes == rawMode) {
                    keep(dirCacheEntry2);
                } else {
                    if (isWorktreeDirty(workingTreeIterator, dirCacheEntry2)) {
                        return false;
                    }
                    addToCheckout(this.tw.getPathString(), add(this.tw.getRawPath(), canonicalTreeParser3, 0, Instant.EPOCH, 0L), attributes);
                }
                return true;
            }
            if (!z) {
                add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                this.unmergedPaths.add(this.tw.getPathString());
                this.mergeResults.put(this.tw.getPathString(), new MergeResult<>(Collections.emptyList()));
            }
            return true;
        }
        if (rawMode3 == rawMode2 && this.tw.idEqual(0, 2)) {
            if (dirCacheEntry2 != null) {
                keep(dirCacheEntry2);
            }
            return true;
        }
        if (rawMode3 == rawMode && this.tw.idEqual(0, 1)) {
            if (isWorktreeDirty(workingTreeIterator, dirCacheEntry2)) {
                return false;
            }
            if (nonTree(rawMode2)) {
                DirCacheEntry dirCacheEntryAdd = add(this.tw.getRawPath(), canonicalTreeParser3, 0, Instant.EPOCH, 0L);
                if (dirCacheEntryAdd != null) {
                    addToCheckout(this.tw.getPathString(), dirCacheEntryAdd, attributes);
                }
                return true;
            }
            if (this.tw.getTreeCount() > 4 && this.tw.getRawMode(4) == 0) {
                return true;
            }
            if (rawMode2 != 0 && rawMode2 == rawMode3) {
                return true;
            }
            addDeletion(this.tw.getPathString(), nonTree(rawMode), attributes);
            return true;
        }
        if (this.tw.isSubtree()) {
            if (nonTree(rawMode) != nonTree(rawMode2)) {
                if (z) {
                    this.enterSubtree = false;
                    return true;
                }
                if (nonTree(rawMode3)) {
                    add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                }
                if (nonTree(rawMode)) {
                    add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                }
                if (nonTree(rawMode2)) {
                    add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                }
                this.unmergedPaths.add(this.tw.getPathString());
                this.enterSubtree = false;
                return true;
            }
            if (!nonTree(rawMode)) {
                return true;
            }
        }
        if (nonTree(rawMode) && nonTree(rawMode2)) {
            boolean zIsWorktreeDirty = isWorktreeDirty(workingTreeIterator, dirCacheEntry2);
            if (!attributes.canBeContentMerged() && zIsWorktreeDirty) {
                return false;
            }
            if (z2 && z) {
                add(this.tw.getRawPath(), canonicalTreeParser2, 0, Instant.EPOCH, 0L);
                return true;
            }
            if (z2) {
                add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                MergeResult<SubmoduleConflict> mergeResultCreateGitLinksMergeResult = createGitLinksMergeResult(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3);
                mergeResultCreateGitLinksMergeResult.setContainsConflicts(true);
                this.mergeResults.put(this.tw.getPathString(), mergeResultCreateGitLinksMergeResult);
                this.unmergedPaths.add(this.tw.getPathString());
                return true;
            }
            if (!attributes.canBeContentMerged()) {
                int i = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy()[getContentMergeStrategy().ordinal()];
                if (i == 2) {
                    keep(dirCacheEntry2);
                    return true;
                }
                if (i == 3) {
                    addToCheckout(this.tw.getPathString(), add(this.tw.getRawPath(), canonicalTreeParser3, 0, Instant.EPOCH, 0L), attributes);
                    return true;
                }
                add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                this.unmergedPaths.add(this.tw.getPathString());
                return true;
            }
            if (zIsWorktreeDirty) {
                return false;
            }
            try {
                mergeResult2 = contentMerge(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3, attributes, getContentMergeStrategy());
            } catch (BinaryBlobException unused) {
                int i2 = $SWITCH_TABLE$org$eclipse$jgit$merge$ContentMergeStrategy()[getContentMergeStrategy().ordinal()];
                if (i2 == 2) {
                    keep(dirCacheEntry2);
                    return true;
                }
                if (i2 == 3) {
                    addToCheckout(this.tw.getPathString(), add(this.tw.getRawPath(), canonicalTreeParser3, 0, Instant.EPOCH, 0L), attributes);
                    return true;
                }
                mergeResult2 = new MergeResult<>(Collections.emptyList());
                mergeResult2.setContainsConflicts(true);
            }
            MergeResult<RawText> mergeResult3 = mergeResult2;
            if (z) {
                mergeResult3.setContainsConflicts(false);
            }
            updateIndex(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3, mergeResult3, attributes);
            String pathString = this.tw.getPathString();
            if (mergeResult3.containsConflicts() && !z) {
                this.unmergedPaths.add(pathString);
            }
            this.modifiedFiles.add(pathString);
            addCheckoutMetadata(pathString, attributes);
        } else if (rawMode != rawMode2 && ((rawMode != 0 && !this.tw.idEqual(0, 1)) || (rawMode2 != 0 && !this.tw.idEqual(0, 2)))) {
            if (z2 && z) {
                add(this.tw.getRawPath(), canonicalTreeParser2, 0, Instant.EPOCH, 0L);
            } else if (z2) {
                add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                MergeResult<SubmoduleConflict> mergeResultCreateGitLinksMergeResult2 = createGitLinksMergeResult(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3);
                mergeResultCreateGitLinksMergeResult2.setContainsConflicts(true);
                this.mergeResults.put(this.tw.getPathString(), mergeResultCreateGitLinksMergeResult2);
                this.unmergedPaths.add(this.tw.getPathString());
            } else {
                try {
                    mergeResult = contentMerge(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3, attributes, ContentMergeStrategy.CONFLICT);
                } catch (BinaryBlobException unused2) {
                    mergeResult = new MergeResult<>(Collections.emptyList());
                    mergeResult.setContainsConflicts(true);
                }
                MergeResult<RawText> mergeResult4 = mergeResult;
                if (z) {
                    mergeResult4.setContainsConflicts(false);
                    updateIndex(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3, mergeResult4, attributes);
                } else {
                    add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                    add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                    DirCacheEntry dirCacheEntryAdd2 = add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                    if (rawMode == 0) {
                        if (isWorktreeDirty(workingTreeIterator, dirCacheEntry2)) {
                            return false;
                        }
                        if (nonTree(rawMode2) && dirCacheEntryAdd2 != null) {
                            addToCheckout(this.tw.getPathString(), dirCacheEntryAdd2, attributes);
                        }
                    }
                    this.unmergedPaths.add(this.tw.getPathString());
                    this.mergeResults.put(this.tw.getPathString(), mergeResult4);
                }
            }
        }
        return true;
    }

    private static MergeResult<SubmoduleConflict> createGitLinksMergeResult(CanonicalTreeParser canonicalTreeParser, CanonicalTreeParser canonicalTreeParser2, CanonicalTreeParser canonicalTreeParser3) {
        SubmoduleConflict[] submoduleConflictArr = new SubmoduleConflict[3];
        submoduleConflictArr[0] = new SubmoduleConflict(canonicalTreeParser == null ? null : canonicalTreeParser.getEntryObjectId());
        submoduleConflictArr[1] = new SubmoduleConflict(canonicalTreeParser2 == null ? null : canonicalTreeParser2.getEntryObjectId());
        submoduleConflictArr[2] = new SubmoduleConflict(canonicalTreeParser3 != null ? canonicalTreeParser3.getEntryObjectId() : null);
        return new MergeResult<>(Arrays.asList(submoduleConflictArr));
    }

    private MergeResult<RawText> contentMerge(CanonicalTreeParser canonicalTreeParser, CanonicalTreeParser canonicalTreeParser2, CanonicalTreeParser canonicalTreeParser3, Attributes attributes, ContentMergeStrategy contentMergeStrategy) throws IOException, BinaryBlobException {
        RawText rawText;
        RawText rawText2;
        RawText rawText3;
        if (canonicalTreeParser == null) {
            rawText = RawText.EMPTY_TEXT;
        } else {
            rawText = getRawText(canonicalTreeParser.getEntryObjectId(), attributes);
        }
        if (canonicalTreeParser2 == null) {
            rawText2 = RawText.EMPTY_TEXT;
        } else {
            rawText2 = getRawText(canonicalTreeParser2.getEntryObjectId(), attributes);
        }
        if (canonicalTreeParser3 == null) {
            rawText3 = RawText.EMPTY_TEXT;
        } else {
            rawText3 = getRawText(canonicalTreeParser3.getEntryObjectId(), attributes);
        }
        this.mergeAlgorithm.setContentMergeStrategy(contentMergeStrategy);
        return this.mergeAlgorithm.merge(RawTextComparator.DEFAULT, rawText, rawText2, rawText3);
    }

    private boolean isIndexDirty() {
        boolean z = false;
        if (this.inCore) {
            return false;
        }
        int rawMode = this.tw.getRawMode(3);
        int rawMode2 = this.tw.getRawMode(1);
        if (nonTree(rawMode) && (rawMode2 != rawMode || !this.tw.idEqual(3, 1))) {
            z = true;
        }
        if (z) {
            this.failingPaths.put(this.tw.getPathString(), MergeFailureReason.DIRTY_INDEX);
        }
        return z;
    }

    private boolean isWorktreeDirty(WorkingTreeIterator workingTreeIterator, DirCacheEntry dirCacheEntry) throws IOException {
        boolean zIsModeDifferent;
        if (workingTreeIterator == null) {
            return false;
        }
        int rawMode = this.tw.getRawMode(4);
        int rawMode2 = this.tw.getRawMode(1);
        if (dirCacheEntry != null) {
            zIsModeDifferent = workingTreeIterator.isModified(dirCacheEntry, true, this.reader);
        } else {
            zIsModeDifferent = workingTreeIterator.isModeDifferent(rawMode2);
            if (!zIsModeDifferent && nonTree(rawMode)) {
                zIsModeDifferent = !this.tw.idEqual(4, 1);
            }
        }
        boolean z = (zIsModeDifferent && rawMode == 16384 && rawMode2 == 0) ? false : zIsModeDifferent;
        if (z) {
            this.failingPaths.put(this.tw.getPathString(), MergeFailureReason.DIRTY_WORKTREE);
        }
        return z;
    }

    private void updateIndex(CanonicalTreeParser canonicalTreeParser, CanonicalTreeParser canonicalTreeParser2, CanonicalTreeParser canonicalTreeParser3, MergeResult<RawText> mergeResult, Attributes attributes) throws Throwable {
        TemporaryBuffer temporaryBuffer = null;
        File fileWriteMergedFile = null;
        try {
            TemporaryBuffer temporaryBufferDoMerge = doMerge(mergeResult);
            try {
                if (!this.inCore) {
                    fileWriteMergedFile = writeMergedFile(temporaryBufferDoMerge, attributes);
                }
                if (mergeResult.containsConflicts()) {
                    add(this.tw.getRawPath(), canonicalTreeParser, 1, Instant.EPOCH, 0L);
                    add(this.tw.getRawPath(), canonicalTreeParser2, 2, Instant.EPOCH, 0L);
                    add(this.tw.getRawPath(), canonicalTreeParser3, 3, Instant.EPOCH, 0L);
                    this.mergeResults.put(this.tw.getPathString(), mergeResult);
                    if (temporaryBufferDoMerge != null) {
                        temporaryBufferDoMerge.destroy();
                        return;
                    }
                    return;
                }
                DirCacheEntry dirCacheEntry = new DirCacheEntry(this.tw.getPathString());
                int iMergeFileModes = mergeFileModes(this.tw.getRawMode(0), this.tw.getRawMode(1), this.tw.getRawMode(2));
                dirCacheEntry.setFileMode(iMergeFileModes == FileMode.MISSING.getBits() ? FileMode.REGULAR_FILE : FileMode.fromBits(iMergeFileModes));
                if (fileWriteMergedFile != null) {
                    dirCacheEntry.setLastModified(nonNullRepo().getFS().lastModifiedInstant(fileWriteMergedFile));
                    dirCacheEntry.setLength((int) fileWriteMergedFile.length());
                }
                dirCacheEntry.setObjectId(insertMergeResult(temporaryBufferDoMerge, attributes));
                this.builder.add(dirCacheEntry);
                if (temporaryBufferDoMerge != null) {
                    temporaryBufferDoMerge.destroy();
                }
            } catch (Throwable th) {
                th = th;
                temporaryBuffer = temporaryBufferDoMerge;
                if (temporaryBuffer != null) {
                    temporaryBuffer.destroy();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private File writeMergedFile(TemporaryBuffer temporaryBuffer, Attributes attributes) throws IOException {
        File workTree = nonNullRepo().getWorkTree();
        FS fs = nonNullRepo().getFS();
        File file = new File(workTree, this.tw.getPathString());
        File parentFile = file.getParentFile();
        if (!fs.exists(parentFile)) {
            parentFile.mkdirs();
        }
        Throwable th = null;
        try {
            OutputStream outputStreamWrapOutputStream = EolStreamTypeUtil.wrapOutputStream(new BufferedOutputStream(new FileOutputStream(file)), EolStreamTypeUtil.detectStreamType(TreeWalk.OperationType.CHECKOUT_OP, this.workingTreeOptions, attributes));
            try {
                temporaryBuffer.writeTo(outputStreamWrapOutputStream, null);
                return file;
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

    private TemporaryBuffer doMerge(MergeResult<RawText> mergeResult) throws IOException {
        TemporaryBuffer.LocalFile localFile = new TemporaryBuffer.LocalFile(this.db != null ? nonNullRepo().getDirectory() : null, this.inCoreLimit);
        try {
            new MergeFormatter().formatMerge(localFile, mergeResult, Arrays.asList(this.commitNames), StandardCharsets.UTF_8);
            localFile.close();
            return localFile;
        } catch (Throwable th) {
            localFile.destroy();
            throw th;
        }
    }

    private ObjectId insertMergeResult(TemporaryBuffer temporaryBuffer, Attributes attributes) throws IOException {
        Throwable th = null;
        try {
            LfsFactory.LfsInputStream lfsInputStreamApplyCleanFilter = LfsFactory.getInstance().applyCleanFilter(getRepository(), temporaryBuffer.openInputStream(), temporaryBuffer.length(), attributes.get("merge"));
            try {
                return getObjectInserter().insert(3, lfsInputStreamApplyCleanFilter.getLength(), lfsInputStreamApplyCleanFilter);
            } finally {
                if (lfsInputStreamApplyCleanFilter != null) {
                    lfsInputStreamApplyCleanFilter.close();
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

    private int mergeFileModes(int i, int i2, int i3) {
        if (i2 == i3) {
            return i2;
        }
        if (i == i2) {
            return i3 == FileMode.MISSING.getBits() ? i2 : i3;
        }
        if (i == i3) {
            return i2 == FileMode.MISSING.getBits() ? i3 : i2;
        }
        return FileMode.MISSING.getBits();
    }

    private RawText getRawText(ObjectId objectId, Attributes attributes) throws IOException, BinaryBlobException {
        if (objectId.equals((AnyObjectId) ObjectId.zeroId())) {
            return new RawText(new byte[0]);
        }
        return RawText.load(LfsFactory.getInstance().applySmudgeFilter(getRepository(), this.reader.open(objectId, 3), attributes.get("merge")), 52428800);
    }

    private static boolean nonTree(int i) {
        return (i == 0 || FileMode.TREE.equals(i)) ? false : true;
    }

    private static boolean isGitLink(int i) {
        return FileMode.GITLINK.equals(i);
    }

    @Override // org.eclipse.jgit.merge.Merger
    public ObjectId getResultTreeId() {
        ObjectId objectId = this.resultTree;
        if (objectId == null) {
            return null;
        }
        return objectId.toObjectId();
    }

    public void setCommitNames(String[] strArr) {
        this.commitNames = strArr;
    }

    public String[] getCommitNames() {
        return this.commitNames;
    }

    public List<String> getUnmergedPaths() {
        return this.unmergedPaths;
    }

    public List<String> getModifiedFiles() {
        return this.modifiedFiles;
    }

    public Map<String, DirCacheEntry> getToBeCheckedOut() {
        return this.toBeCheckedOut;
    }

    public Map<String, MergeResult<? extends Sequence>> getMergeResults() {
        return this.mergeResults;
    }

    public Map<String, MergeFailureReason> getFailingPaths() {
        if (this.failingPaths.isEmpty()) {
            return null;
        }
        return this.failingPaths;
    }

    public boolean failed() {
        return !this.failingPaths.isEmpty();
    }

    public void setDirCache(DirCache dirCache) {
        this.dircache = dirCache;
        this.implicitDirCache = false;
    }

    public void setWorkingTreeIterator(WorkingTreeIterator workingTreeIterator) {
        this.workingTreeIterator = workingTreeIterator;
    }

    protected boolean mergeTrees(AbstractTreeIterator abstractTreeIterator, RevTree revTree, RevTree revTree2, boolean z) throws IOException {
        this.builder = this.dircache.builder();
        DirCacheBuildIterator dirCacheBuildIterator = new DirCacheBuildIterator(this.builder);
        NameConflictTreeWalk nameConflictTreeWalk = new NameConflictTreeWalk(this.db, this.reader);
        this.tw = nameConflictTreeWalk;
        nameConflictTreeWalk.addTree(abstractTreeIterator);
        this.tw.addTree(revTree);
        this.tw.addTree(revTree2);
        int iAddTree = this.tw.addTree(dirCacheBuildIterator);
        WorkingTreeIterator workingTreeIterator = this.workingTreeIterator;
        if (workingTreeIterator != null) {
            this.tw.addTree(workingTreeIterator);
            this.workingTreeIterator.setDirCacheIterator(this.tw, iAddTree);
        } else {
            this.tw.setFilter(TreeFilter.ANY_DIFF);
        }
        if (!mergeTreeWalk(this.tw, z)) {
            return false;
        }
        if (!this.inCore) {
            checkout();
            if (!this.builder.commit()) {
                cleanUp();
                throw new IndexWriteException();
            }
            this.builder = null;
        } else {
            this.builder.finish();
            this.builder = null;
        }
        if (getUnmergedPaths().isEmpty() && !failed()) {
            this.resultTree = this.dircache.writeTree(getObjectInserter());
            return true;
        }
        this.resultTree = null;
        return false;
    }

    protected boolean mergeTreeWalk(TreeWalk treeWalk, boolean z) throws IOException {
        Attributes attributes;
        boolean z2 = this.tw.getTreeCount() > 4;
        boolean z3 = treeWalk.getAttributesNodeProvider() != null;
        while (treeWalk.next()) {
            CanonicalTreeParser canonicalTreeParser = (CanonicalTreeParser) treeWalk.getTree(0, CanonicalTreeParser.class);
            CanonicalTreeParser canonicalTreeParser2 = (CanonicalTreeParser) treeWalk.getTree(1, CanonicalTreeParser.class);
            CanonicalTreeParser canonicalTreeParser3 = (CanonicalTreeParser) treeWalk.getTree(2, CanonicalTreeParser.class);
            DirCacheBuildIterator dirCacheBuildIterator = (DirCacheBuildIterator) treeWalk.getTree(3, DirCacheBuildIterator.class);
            WorkingTreeIterator workingTreeIterator = z2 ? (WorkingTreeIterator) treeWalk.getTree(4, WorkingTreeIterator.class) : null;
            if (z3) {
                attributes = treeWalk.getAttributes();
            } else {
                attributes = NO_ATTRIBUTES;
            }
            if (!processEntry(canonicalTreeParser, canonicalTreeParser2, canonicalTreeParser3, dirCacheBuildIterator, workingTreeIterator, z, attributes)) {
                cleanUp();
                return false;
            }
            if (treeWalk.isSubtree() && this.enterSubtree) {
                treeWalk.enterSubtree();
            }
        }
        return true;
    }
}
