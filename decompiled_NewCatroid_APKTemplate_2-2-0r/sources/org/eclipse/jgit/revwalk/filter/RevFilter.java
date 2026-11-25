package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public abstract class RevFilter {
    public static final RevFilter ALL = new AllFilter(null);
    public static final RevFilter NONE = new NoneFilter(0 == true ? 1 : 0);
    public static final RevFilter ONLY_MERGES = new OnlyMergesFilter(0 == true ? 1 : 0);
    public static final RevFilter NO_MERGES = new NoMergesFilter(0 == true ? 1 : 0);
    public static final RevFilter MERGE_BASE = new MergeBaseFilter(0 == true ? 1 : 0);

    @Override // 
    public abstract RevFilter clone();

    public abstract boolean include(RevWalk revWalk, RevCommit revCommit) throws IOException, StopWalkException;

    public boolean requiresCommitBody() {
        return true;
    }

    private static final class AllFilter extends RevFilter {
        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public RevFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean include(RevWalk revWalk, RevCommit revCommit) {
            return true;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean requiresCommitBody() {
            return false;
        }

        private AllFilter() {
        }

        /* synthetic */ AllFilter(AllFilter allFilter) {
            this();
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public String toString() {
            return "ALL";
        }
    }

    private static final class NoneFilter extends RevFilter {
        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public RevFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean include(RevWalk revWalk, RevCommit revCommit) {
            return false;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean requiresCommitBody() {
            return false;
        }

        private NoneFilter() {
        }

        /* synthetic */ NoneFilter(NoneFilter noneFilter) {
            this();
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public String toString() {
            return "NONE";
        }
    }

    private static final class OnlyMergesFilter extends RevFilter {
        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public RevFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean requiresCommitBody() {
            return false;
        }

        private OnlyMergesFilter() {
        }

        /* synthetic */ OnlyMergesFilter(OnlyMergesFilter onlyMergesFilter) {
            this();
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean include(RevWalk revWalk, RevCommit revCommit) {
            return revCommit.getParentCount() >= 2;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public String toString() {
            return "ONLY_MERGES";
        }
    }

    private static final class NoMergesFilter extends RevFilter {
        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public RevFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean requiresCommitBody() {
            return false;
        }

        private NoMergesFilter() {
        }

        /* synthetic */ NoMergesFilter(NoMergesFilter noMergesFilter) {
            this();
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean include(RevWalk revWalk, RevCommit revCommit) {
            return revCommit.getParentCount() < 2;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public String toString() {
            return "NO_MERGES";
        }
    }

    private static final class MergeBaseFilter extends RevFilter {
        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public RevFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean requiresCommitBody() {
            return false;
        }

        private MergeBaseFilter() {
        }

        /* synthetic */ MergeBaseFilter(MergeBaseFilter mergeBaseFilter) {
            this();
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public boolean include(RevWalk revWalk, RevCommit revCommit) {
            throw new UnsupportedOperationException(JGitText.get().cannotBeCombined);
        }

        @Override // org.eclipse.jgit.revwalk.filter.RevFilter
        public String toString() {
            return "MERGE_BASE";
        }
    }

    public RevFilter negate() {
        return NotRevFilter.create(this);
    }

    public String toString() {
        String name2 = getClass().getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        if (iLastIndexOf >= 0) {
            name2 = name2.substring(iLastIndexOf + 1);
        }
        return name2.replace('$', '.');
    }
}
