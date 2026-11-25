package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;
import org.eclipse.jgit.treewalk.TreeWalk;

/* JADX INFO: loaded from: classes14.dex */
public abstract class TreeFilter {
    public static final TreeFilter ALL = new AllFilter(null);
    public static final TreeFilter ANY_DIFF = new AnyDiffFilter(0 == true ? 1 : 0);

    @Override // 
    public abstract TreeFilter clone();

    public abstract boolean include(TreeWalk treeWalk) throws IOException;

    public abstract boolean shouldBeRecursive();

    private static final class AllFilter extends TreeFilter {
        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public TreeFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public boolean include(TreeWalk treeWalk) {
            return true;
        }

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public boolean shouldBeRecursive() {
            return false;
        }

        private AllFilter() {
        }

        /* synthetic */ AllFilter(AllFilter allFilter) {
            this();
        }

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public String toString() {
            return "ALL";
        }
    }

    private static final class AnyDiffFilter extends TreeFilter {
        private static final int baseTree = 0;

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public TreeFilter clone() {
            return this;
        }

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public boolean shouldBeRecursive() {
            return false;
        }

        private AnyDiffFilter() {
        }

        /* synthetic */ AnyDiffFilter(AnyDiffFilter anyDiffFilter) {
            this();
        }

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public boolean include(TreeWalk treeWalk) {
            int treeCount = treeWalk.getTreeCount();
            if (treeCount == 1) {
                return true;
            }
            int rawMode = treeWalk.getRawMode(0);
            for (int i = 1; i < treeCount; i++) {
                if (treeWalk.getRawMode(i) != rawMode || !treeWalk.idEqual(i, 0)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
        public String toString() {
            return "ANY_DIFF";
        }
    }

    public TreeFilter negate() {
        return NotTreeFilter.create(this);
    }

    public int matchFilter(TreeWalk treeWalk) throws IOException {
        return !include(treeWalk) ? 1 : 0;
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
