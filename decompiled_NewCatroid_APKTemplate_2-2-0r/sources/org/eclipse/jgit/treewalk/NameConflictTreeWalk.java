package org.eclipse.jgit.treewalk;

import java.io.IOException;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

/* JADX INFO: loaded from: classes14.dex */
public class NameConflictTreeWalk extends TreeWalk {
    private static final int TREE_MODE = FileMode.TREE.getBits();
    private AbstractTreeIterator dfConflict;
    private boolean fastMinHasMatch;

    public NameConflictTreeWalk(Repository repository) {
        super(repository);
    }

    public NameConflictTreeWalk(@Nullable Repository repository, ObjectReader objectReader) {
        super(repository, objectReader);
    }

    public NameConflictTreeWalk(ObjectReader objectReader) {
        super(objectReader);
    }

    @Override // org.eclipse.jgit.treewalk.TreeWalk
    AbstractTreeIterator min() throws CorruptObjectException {
        while (true) {
            AbstractTreeIterator abstractTreeIteratorFastMin = fastMin();
            if (this.fastMinHasMatch) {
                return abstractTreeIteratorFastMin;
            }
            if (isTree(abstractTreeIteratorFastMin)) {
                if (!skipEntry(abstractTreeIteratorFastMin)) {
                    return abstractTreeIteratorFastMin;
                }
                for (AbstractTreeIterator abstractTreeIterator : this.trees) {
                    if (abstractTreeIterator.matches == abstractTreeIteratorFastMin) {
                        abstractTreeIterator.next(1);
                        abstractTreeIterator.matches = null;
                    }
                }
            } else {
                return combineDF(abstractTreeIteratorFastMin);
            }
        }
    }

    private AbstractTreeIterator fastMin() {
        this.fastMinHasMatch = true;
        AbstractTreeIterator abstractTreeIterator = this.trees[0];
        int i = 0;
        while (abstractTreeIterator.eof() && (i = i + 1) < this.trees.length) {
            abstractTreeIterator = this.trees[i];
        }
        if (abstractTreeIterator.eof()) {
            return abstractTreeIterator;
        }
        abstractTreeIterator.matches = abstractTreeIterator;
        boolean z = false;
        while (true) {
            i++;
            if (i >= this.trees.length) {
                break;
            }
            AbstractTreeIterator abstractTreeIterator2 = this.trees[i];
            if (!abstractTreeIterator2.eof()) {
                int iPathCompare = abstractTreeIterator2.pathCompare(abstractTreeIterator);
                if (iPathCompare < 0) {
                    if (this.fastMinHasMatch && isTree(abstractTreeIterator) && !isTree(abstractTreeIterator2) && nameEqual(abstractTreeIterator, abstractTreeIterator2)) {
                        abstractTreeIterator2.matches = abstractTreeIterator;
                        z = true;
                    } else {
                        this.fastMinHasMatch = false;
                        abstractTreeIterator2.matches = abstractTreeIterator2;
                        abstractTreeIterator = abstractTreeIterator2;
                    }
                } else if (iPathCompare == 0) {
                    abstractTreeIterator2.matches = abstractTreeIterator;
                } else if (this.fastMinHasMatch && isTree(abstractTreeIterator2) && !isTree(abstractTreeIterator) && !isGitlink(abstractTreeIterator) && nameEqual(abstractTreeIterator2, abstractTreeIterator)) {
                    for (int i2 = 0; i2 < i; i2++) {
                        AbstractTreeIterator abstractTreeIterator3 = this.trees[i2];
                        if (abstractTreeIterator3.matches == abstractTreeIterator) {
                            abstractTreeIterator3.matches = abstractTreeIterator2;
                        }
                    }
                    abstractTreeIterator2.matches = abstractTreeIterator2;
                    z = true;
                    abstractTreeIterator = abstractTreeIterator2;
                } else {
                    this.fastMinHasMatch = false;
                }
            }
        }
        if (z && this.fastMinHasMatch && this.dfConflict == null) {
            this.dfConflict = abstractTreeIterator;
        }
        return abstractTreeIterator;
    }

    private static boolean nameEqual(AbstractTreeIterator abstractTreeIterator, AbstractTreeIterator abstractTreeIterator2) {
        return abstractTreeIterator.pathCompare(abstractTreeIterator2, TREE_MODE) == 0;
    }

    private boolean isGitlink(AbstractTreeIterator abstractTreeIterator) {
        return FileMode.GITLINK.equals(abstractTreeIterator.mode);
    }

    private static boolean isTree(AbstractTreeIterator abstractTreeIterator) {
        return FileMode.TREE.equals(abstractTreeIterator.mode);
    }

    private boolean skipEntry(AbstractTreeIterator abstractTreeIterator) throws CorruptObjectException {
        for (AbstractTreeIterator abstractTreeIterator2 : this.trees) {
            if (abstractTreeIterator2.matches != abstractTreeIterator && !abstractTreeIterator2.first()) {
                int i = 0;
                do {
                    i++;
                    abstractTreeIterator2.back(1);
                    int iPathCompare = abstractTreeIterator2.pathCompare(abstractTreeIterator, 0);
                    if (iPathCompare == 0) {
                        abstractTreeIterator2.next(i);
                        return true;
                    }
                    if (iPathCompare < 0) {
                        break;
                    }
                } while (!abstractTreeIterator2.first());
                abstractTreeIterator2.next(i);
            }
        }
        return false;
    }

    private AbstractTreeIterator combineDF(AbstractTreeIterator abstractTreeIterator) throws CorruptObjectException {
        AbstractTreeIterator abstractTreeIterator2 = null;
        for (AbstractTreeIterator abstractTreeIterator3 : this.trees) {
            if (abstractTreeIterator3.matches != abstractTreeIterator && !abstractTreeIterator3.eof()) {
                while (true) {
                    int iPathCompare = abstractTreeIterator3.pathCompare(abstractTreeIterator, TREE_MODE);
                    if (iPathCompare >= 0) {
                        if (iPathCompare == 0) {
                            abstractTreeIterator3.matches = abstractTreeIterator;
                            abstractTreeIterator2 = abstractTreeIterator3;
                            break;
                        }
                        if (abstractTreeIterator3.matchShift == 0) {
                            break;
                        }
                        abstractTreeIterator3.back(abstractTreeIterator3.matchShift);
                        abstractTreeIterator3.matchShift = 0;
                        break;
                    }
                    abstractTreeIterator3.matchShift++;
                    abstractTreeIterator3.next(1);
                    if (abstractTreeIterator3.eof()) {
                        abstractTreeIterator3.back(abstractTreeIterator3.matchShift);
                        abstractTreeIterator3.matchShift = 0;
                        break;
                    }
                }
            }
        }
        if (abstractTreeIterator2 == null) {
            return abstractTreeIterator;
        }
        for (AbstractTreeIterator abstractTreeIterator4 : this.trees) {
            if (abstractTreeIterator4.matches == abstractTreeIterator) {
                abstractTreeIterator4.matches = abstractTreeIterator2;
            }
        }
        if (this.dfConflict == null && !isGitlink(abstractTreeIterator)) {
            this.dfConflict = abstractTreeIterator2;
        }
        return abstractTreeIterator2;
    }

    @Override // org.eclipse.jgit.treewalk.TreeWalk
    void popEntriesEqual() throws CorruptObjectException {
        AbstractTreeIterator abstractTreeIterator = this.currentHead;
        for (AbstractTreeIterator abstractTreeIterator2 : this.trees) {
            if (abstractTreeIterator2.matches == abstractTreeIterator) {
                if (abstractTreeIterator2.matchShift == 0) {
                    abstractTreeIterator2.next(1);
                } else {
                    abstractTreeIterator2.back(abstractTreeIterator2.matchShift);
                    abstractTreeIterator2.matchShift = 0;
                }
                abstractTreeIterator2.matches = null;
            }
        }
        if (abstractTreeIterator == this.dfConflict) {
            this.dfConflict = null;
        }
    }

    @Override // org.eclipse.jgit.treewalk.TreeWalk
    void skipEntriesEqual() throws CorruptObjectException {
        AbstractTreeIterator abstractTreeIterator = this.currentHead;
        for (AbstractTreeIterator abstractTreeIterator2 : this.trees) {
            if (abstractTreeIterator2.matches == abstractTreeIterator) {
                if (abstractTreeIterator2.matchShift == 0) {
                    abstractTreeIterator2.skip();
                } else {
                    abstractTreeIterator2.back(abstractTreeIterator2.matchShift);
                    abstractTreeIterator2.matchShift = 0;
                }
                abstractTreeIterator2.matches = null;
            }
        }
        if (abstractTreeIterator == this.dfConflict) {
            this.dfConflict = null;
        }
    }

    @Override // org.eclipse.jgit.treewalk.TreeWalk
    void stopWalk() throws IOException {
        if (!needsStopWalk()) {
            return;
        }
        while (true) {
            AbstractTreeIterator abstractTreeIteratorMin = min();
            if (abstractTreeIteratorMin.eof()) {
                if (this.depth <= 0) {
                    return;
                }
                exitSubtree();
                popEntriesEqual();
            } else {
                this.currentHead = abstractTreeIteratorMin;
                skipEntriesEqual();
            }
        }
    }

    private boolean needsStopWalk() {
        for (AbstractTreeIterator abstractTreeIterator : this.trees) {
            if (abstractTreeIterator.needsStopWalk()) {
                return true;
            }
        }
        return false;
    }

    public boolean isDirectoryFileConflict() {
        return this.dfConflict != null;
    }
}
