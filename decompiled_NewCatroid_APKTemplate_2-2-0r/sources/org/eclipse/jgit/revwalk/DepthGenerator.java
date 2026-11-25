package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Iterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
class DepthGenerator extends Generator {
    private final RevFlag DEEPEN_NOT;
    private final RevFlag REINTERESTING;
    private final RevFlag UNSHALLOW;
    private final int deepenSince;
    private final int depth;
    private final FIFORevQueue pending;
    private final RevWalk walk;

    /* JADX WARN: Multi-variable type inference failed */
    DepthGenerator(DepthWalk depthWalk, Generator generator) throws IOException {
        super(generator.firstParent);
        FIFORevQueue fIFORevQueue = new FIFORevQueue(this.firstParent);
        this.pending = fIFORevQueue;
        this.walk = (RevWalk) depthWalk;
        this.depth = depthWalk.getDepth();
        this.deepenSince = depthWalk.getDeepenSince();
        this.UNSHALLOW = depthWalk.getUnshallowFlag();
        this.REINTERESTING = depthWalk.getReinterestingFlag();
        this.DEEPEN_NOT = depthWalk.getDeepenNotFlag();
        generator.shareFreeList(fIFORevQueue);
        FIFORevQueue fIFORevQueue2 = new FIFORevQueue();
        while (true) {
            RevCommit next = generator.next();
            if (next == null) {
                break;
            }
            if (next.has(this.UNSHALLOW)) {
                fIFORevQueue2.add(next);
            } else if (((DepthWalk.Commit) next).getDepth() == 0) {
                this.pending.add(next);
            }
        }
        while (true) {
            RevCommit next2 = fIFORevQueue2.next();
            if (next2 == null) {
                break;
            } else {
                this.pending.unpop(next2);
            }
        }
        Iterator<ObjectId> it = depthWalk.getDeepenNots().iterator();
        while (it.hasNext()) {
            try {
                RevCommit commit = this.walk.parseCommit(it.next());
                FIFORevQueue fIFORevQueue3 = new FIFORevQueue();
                fIFORevQueue3.add(commit);
                while (true) {
                    RevCommit next3 = fIFORevQueue3.next();
                    if (next3 == null) {
                        break;
                    }
                    if (!next3.has(this.DEEPEN_NOT)) {
                        this.walk.parseHeaders(next3);
                        next3.add(this.DEEPEN_NOT);
                        for (RevCommit revCommit : next3.getParents()) {
                            fIFORevQueue3.add(revCommit);
                        }
                    }
                }
            } catch (IncorrectObjectTypeException unused) {
            }
        }
    }

    @Override // org.eclipse.jgit.revwalk.Generator
    int outputType() {
        return this.pending.outputType() | 16;
    }

    @Override // org.eclipse.jgit.revwalk.Generator
    void shareFreeList(BlockRevQueue blockRevQueue) {
        this.pending.shareFreeList(blockRevQueue);
    }

    /* JADX WARN: Code duplicated, block: B:43:0x007a  */
    @Override // org.eclipse.jgit.revwalk.Generator
    RevCommit next() throws IOException {
        boolean z;
        while (true) {
            DepthWalk.Commit commit = (DepthWalk.Commit) this.pending.next();
            if (commit == null) {
                return null;
            }
            boolean z2 = true;
            if ((commit.flags & 1) == 0) {
                commit.parseHeaders(this.walk);
            }
            if (commit.getCommitTime() >= this.deepenSince && !commit.has(this.DEEPEN_NOT)) {
                int i = commit.depth + 1;
                for (int i2 = 0; i2 < commit.parents.length && (!this.firstParent || i2 <= 0); i2++) {
                    RevCommit revCommit = commit.parents[i2];
                    DepthWalk.Commit commit2 = (DepthWalk.Commit) revCommit;
                    if (commit2.depth == -1) {
                        if (this.deepenSince == 0) {
                            z = false;
                        } else {
                            if ((revCommit.flags & 1) == 0) {
                                revCommit.parseHeaders(this.walk);
                            }
                            if (revCommit.getCommitTime() < this.deepenSince) {
                                z = true;
                            } else {
                                z = false;
                            }
                        }
                        commit2.depth = i;
                        if (i <= this.depth && !z && !revCommit.has(this.DEEPEN_NOT)) {
                            this.pending.add(revCommit);
                        } else {
                            commit2.makesChildBoundary = true;
                        }
                    }
                    if (commit2.makesChildBoundary) {
                        commit.isBoundary = true;
                    }
                    if (commit.has(this.UNSHALLOW) || commit.has(this.REINTERESTING)) {
                        revCommit.add(this.REINTERESTING);
                        revCommit.flags &= -5;
                    }
                }
                if ((commit.flags & 4) != 0 && !commit.has(this.UNSHALLOW)) {
                    z2 = false;
                }
                if (commit.getCommitTime() >= this.deepenSince ? z2 : false) {
                    return commit;
                }
            }
        }
    }
}
