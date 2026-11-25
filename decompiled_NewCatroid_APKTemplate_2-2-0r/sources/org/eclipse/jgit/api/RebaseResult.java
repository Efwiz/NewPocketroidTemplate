package org.eclipse.jgit.api;

import java.util.List;
import java.util.Map;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.revwalk.RevCommit;

/* JADX INFO: loaded from: classes13.dex */
public class RebaseResult {
    private List<String> conflicts;
    private final RevCommit currentCommit;
    private Map<String, ResolveMerger.MergeFailureReason> failingPaths;
    private final Status status;
    private List<String> uncommittedChanges;
    static final RebaseResult OK_RESULT = new RebaseResult(Status.OK);
    static final RebaseResult ABORTED_RESULT = new RebaseResult(Status.ABORTED);
    static final RebaseResult UP_TO_DATE_RESULT = new RebaseResult(Status.UP_TO_DATE);
    static final RebaseResult FAST_FORWARD_RESULT = new RebaseResult(Status.FAST_FORWARD);
    static final RebaseResult NOTHING_TO_COMMIT_RESULT = new RebaseResult(Status.NOTHING_TO_COMMIT);
    static final RebaseResult INTERACTIVE_PREPARED_RESULT = new RebaseResult(Status.INTERACTIVE_PREPARED);
    static final RebaseResult STASH_APPLY_CONFLICTS_RESULT = new RebaseResult(Status.STASH_APPLY_CONFLICTS);

    public enum Status {
        OK { // from class: org.eclipse.jgit.api.RebaseResult.Status.1
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return true;
            }
        },
        ABORTED { // from class: org.eclipse.jgit.api.RebaseResult.Status.2
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        STOPPED { // from class: org.eclipse.jgit.api.RebaseResult.Status.3
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        EDIT { // from class: org.eclipse.jgit.api.RebaseResult.Status.4
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        FAILED { // from class: org.eclipse.jgit.api.RebaseResult.Status.5
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        UNCOMMITTED_CHANGES { // from class: org.eclipse.jgit.api.RebaseResult.Status.6
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        CONFLICTS { // from class: org.eclipse.jgit.api.RebaseResult.Status.7
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        UP_TO_DATE { // from class: org.eclipse.jgit.api.RebaseResult.Status.8
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return true;
            }
        },
        FAST_FORWARD { // from class: org.eclipse.jgit.api.RebaseResult.Status.9
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return true;
            }
        },
        NOTHING_TO_COMMIT { // from class: org.eclipse.jgit.api.RebaseResult.Status.10
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        INTERACTIVE_PREPARED { // from class: org.eclipse.jgit.api.RebaseResult.Status.11
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return false;
            }
        },
        STASH_APPLY_CONFLICTS { // from class: org.eclipse.jgit.api.RebaseResult.Status.12
            @Override // org.eclipse.jgit.api.RebaseResult.Status
            public boolean isSuccessful() {
                return true;
            }
        };

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Status[] valuesCustom() {
            Status[] statusArrValuesCustom = values();
            int length = statusArrValuesCustom.length;
            Status[] statusArr = new Status[length];
            System.arraycopy(statusArrValuesCustom, 0, statusArr, 0, length);
            return statusArr;
        }

        public abstract boolean isSuccessful();

        /* synthetic */ Status(Status status) {
            this();
        }
    }

    private RebaseResult(Status status) {
        this.status = status;
        this.currentCommit = null;
    }

    private RebaseResult(Status status, RevCommit revCommit) {
        this.status = status;
        this.currentCommit = revCommit;
    }

    static RebaseResult result(Status status, RevCommit revCommit) {
        return new RebaseResult(status, revCommit);
    }

    static RebaseResult failed(Map<String, ResolveMerger.MergeFailureReason> map) {
        RebaseResult rebaseResult = new RebaseResult(Status.FAILED);
        rebaseResult.failingPaths = map;
        return rebaseResult;
    }

    static RebaseResult conflicts(List<String> list) {
        RebaseResult rebaseResult = new RebaseResult(Status.CONFLICTS);
        rebaseResult.conflicts = list;
        return rebaseResult;
    }

    static RebaseResult uncommittedChanges(List<String> list) {
        RebaseResult rebaseResult = new RebaseResult(Status.UNCOMMITTED_CHANGES);
        rebaseResult.uncommittedChanges = list;
        return rebaseResult;
    }

    public Status getStatus() {
        return this.status;
    }

    public RevCommit getCurrentCommit() {
        return this.currentCommit;
    }

    public Map<String, ResolveMerger.MergeFailureReason> getFailingPaths() {
        return this.failingPaths;
    }

    public List<String> getConflicts() {
        return this.conflicts;
    }

    public List<String> getUncommittedChanges() {
        return this.uncommittedChanges;
    }
}
