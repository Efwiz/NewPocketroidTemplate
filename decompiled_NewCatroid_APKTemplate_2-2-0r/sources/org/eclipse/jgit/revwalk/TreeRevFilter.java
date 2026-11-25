package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

/* JADX INFO: loaded from: classes13.dex */
public class TreeRevFilter extends RevFilter {
    private static final int PARSED = 1;
    private static final int UNINTERESTING = 4;
    private final TreeWalk pathFilter;
    private final int rewriteFlag;

    @Override // org.eclipse.jgit.revwalk.filter.RevFilter
    public boolean requiresCommitBody() {
        return false;
    }

    public TreeRevFilter(RevWalk revWalk, TreeFilter treeFilter) {
        this(revWalk, treeFilter, 0);
    }

    TreeRevFilter(RevWalk revWalk, TreeFilter treeFilter, int i) {
        TreeWalk treeWalk = new TreeWalk(revWalk.reader);
        this.pathFilter = treeWalk;
        treeWalk.setFilter(treeFilter);
        treeWalk.setRecursive(treeFilter.shouldBeRecursive());
        this.rewriteFlag = i;
    }

    @Override // org.eclipse.jgit.revwalk.filter.RevFilter
    public RevFilter clone() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jgit.revwalk.filter.RevFilter
    public boolean include(RevWalk revWalk, RevCommit revCommit) throws IOException, StopWalkException {
        RevCommit[] revCommitArr = revCommit.parents;
        int length = revCommitArr.length;
        TreeWalk treeWalk = this.pathFilter;
        ObjectId[] objectIdArr = new ObjectId[length + 1];
        for (int i = 0; i < length; i++) {
            RevCommit revCommit2 = revCommit.parents[i];
            if ((1 & revCommit2.flags) == 0) {
                revCommit2.parseHeaders(revWalk);
            }
            objectIdArr[i] = revCommit2.getTree();
        }
        objectIdArr[length] = revCommit.getTree();
        treeWalk.reset(objectIdArr);
        if (length == 1) {
            int i2 = 0;
            int i3 = 0;
            while (treeWalk.next()) {
                i2++;
                if (treeWalk.getRawMode(0) != 0 || treeWalk.getRawMode(1) == 0) {
                    break;
                }
                i3++;
            }
            if (i2 == 0) {
                revCommit.flags |= this.rewriteFlag;
                return false;
            }
            if (i3 > 0 && (treeWalk.getFilter() instanceof FollowFilter)) {
                updateFollowFilter(objectIdArr, ((FollowFilter) treeWalk.getFilter()).cfg);
            }
            return true;
        }
        if (length == 0) {
            if (treeWalk.next()) {
                return true;
            }
            revCommit.flags |= this.rewriteFlag;
            return false;
        }
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        while (treeWalk.next()) {
            int rawMode = treeWalk.getRawMode(length);
            for (int i4 = 0; i4 < length; i4++) {
                int rawMode2 = treeWalk.getRawMode(i4);
                if (rawMode != rawMode2 || !treeWalk.idEqual(i4, length)) {
                    iArr[i4] = iArr[i4] + 1;
                    if (rawMode2 == 0 && rawMode != 0) {
                        iArr2[i4] = iArr2[i4] + 1;
                    }
                }
            }
        }
        boolean z = false;
        boolean z2 = false;
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = iArr[i5];
            if (i6 == 0) {
                RevCommit revCommit3 = revCommitArr[i5];
                if ((revCommit3.flags & 4) == 0) {
                    revCommit.flags |= this.rewriteFlag;
                    revCommit.parents = new RevCommit[]{revCommit3};
                    return false;
                }
                z2 = true;
            } else {
                if (i6 == iArr2[i5]) {
                    treeWalk.reset(revCommitArr[i5].getTree());
                    if (!treeWalk.next()) {
                        revCommitArr[i5].parents = RevCommit.NO_PARENTS;
                    }
                }
                z = true;
            }
        }
        if (z && !z2) {
            return true;
        }
        revCommit.flags |= this.rewriteFlag;
        return false;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private void updateFollowFilter(ObjectId[] objectIdArr, DiffConfig diffConfig) throws IOException {
        TreeWalk treeWalk = this.pathFilter;
        FollowFilter followFilter = (FollowFilter) treeWalk.getFilter();
        treeWalk.setFilter(TreeFilter.ANY_DIFF);
        treeWalk.reset(objectIdArr);
        List<DiffEntry> listScan = DiffEntry.scan(treeWalk);
        RenameDetector renameDetector = new RenameDetector(treeWalk.getObjectReader(), diffConfig);
        renameDetector.addAll(listScan);
        for (DiffEntry diffEntry : renameDetector.compute()) {
            if (isRename(diffEntry) && diffEntry.getNewPath().equals(followFilter.getPath())) {
                FollowFilter followFilterCreate = FollowFilter.create(diffEntry.getOldPath(), diffConfig);
                RenameCallback renameCallback = followFilter.getRenameCallback();
                if (renameCallback != null) {
                    renameCallback.renamed(diffEntry);
                    followFilterCreate.setRenameCallback(renameCallback);
                }
                followFilter = followFilterCreate;
                break;
            }
        }
        treeWalk.setFilter(followFilter);
    }

    private static boolean isRename(DiffEntry diffEntry) {
        return diffEntry.getChangeType() == DiffEntry.ChangeType.RENAME || diffEntry.getChangeType() == DiffEntry.ChangeType.COPY;
    }
}
