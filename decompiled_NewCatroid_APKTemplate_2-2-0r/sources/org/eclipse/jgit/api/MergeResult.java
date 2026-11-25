package org.eclipse.jgit.api;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.merge.MergeChunk;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;

/* JADX INFO: loaded from: classes13.dex */
public class MergeResult {
    private ObjectId base;
    private List<String> checkoutConflicts;
    private Map<String, int[][]> conflicts;
    private String description;
    private Map<String, ResolveMerger.MergeFailureReason> failingPaths;
    private MergeStatus mergeStatus;
    private MergeStrategy mergeStrategy;
    private ObjectId[] mergedCommits;
    private ObjectId newHead;

    public enum MergeStatus {
        FAST_FORWARD { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.1
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Fast-forward";
            }
        },
        FAST_FORWARD_SQUASHED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.2
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Fast-forward-squashed";
            }
        },
        ALREADY_UP_TO_DATE { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.3
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Already-up-to-date";
            }
        },
        FAILED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.4
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return false;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Failed";
            }
        },
        MERGED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.5
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Merged";
            }
        },
        MERGED_SQUASHED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.6
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Merged-squashed";
            }
        },
        MERGED_SQUASHED_NOT_COMMITTED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.7
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Merged-squashed-not-committed";
            }
        },
        CONFLICTING { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.8
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return false;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Conflicting";
            }
        },
        ABORTED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.9
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return false;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Aborted";
            }
        },
        MERGED_NOT_COMMITTED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.10
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return true;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Merged-not-committed";
            }
        },
        NOT_SUPPORTED { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.11
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return false;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Not-yet-supported";
            }
        },
        CHECKOUT_CONFLICT { // from class: org.eclipse.jgit.api.MergeResult.MergeStatus.12
            @Override // org.eclipse.jgit.api.MergeResult.MergeStatus
            public boolean isSuccessful() {
                return false;
            }

            @Override // java.lang.Enum
            public String toString() {
                return "Checkout Conflict";
            }
        };

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static MergeStatus[] valuesCustom() {
            MergeStatus[] mergeStatusArrValuesCustom = values();
            int length = mergeStatusArrValuesCustom.length;
            MergeStatus[] mergeStatusArr = new MergeStatus[length];
            System.arraycopy(mergeStatusArrValuesCustom, 0, mergeStatusArr, 0, length);
            return mergeStatusArr;
        }

        public abstract boolean isSuccessful();

        /* synthetic */ MergeStatus(MergeStatus mergeStatus) {
            this();
        }
    }

    public MergeResult(ObjectId objectId, ObjectId objectId2, ObjectId[] objectIdArr, MergeStatus mergeStatus, MergeStrategy mergeStrategy, Map<String, org.eclipse.jgit.merge.MergeResult<?>> map) {
        this(objectId, objectId2, objectIdArr, mergeStatus, mergeStrategy, map, null);
    }

    public MergeResult(ObjectId objectId, ObjectId objectId2, ObjectId[] objectIdArr, MergeStatus mergeStatus, MergeStrategy mergeStrategy, Map<String, org.eclipse.jgit.merge.MergeResult<?>> map, String str) {
        this(objectId, objectId2, objectIdArr, mergeStatus, mergeStrategy, map, null, str);
    }

    public MergeResult(ObjectId objectId, ObjectId objectId2, ObjectId[] objectIdArr, MergeStatus mergeStatus, MergeStrategy mergeStrategy, Map<String, org.eclipse.jgit.merge.MergeResult<?>> map, Map<String, ResolveMerger.MergeFailureReason> map2, String str) {
        this.newHead = objectId;
        this.mergedCommits = objectIdArr;
        this.base = objectId2;
        this.mergeStatus = mergeStatus;
        this.mergeStrategy = mergeStrategy;
        this.description = str;
        this.failingPaths = map2;
        if (map != null) {
            for (Map.Entry<String, org.eclipse.jgit.merge.MergeResult<?>> entry : map.entrySet()) {
                addConflict(entry.getKey(), entry.getValue());
            }
        }
    }

    public MergeResult(List<String> list) {
        this.checkoutConflicts = list;
        this.mergeStatus = MergeStatus.CHECKOUT_CONFLICT;
    }

    public ObjectId getNewHead() {
        return this.newHead;
    }

    public MergeStatus getMergeStatus() {
        return this.mergeStatus;
    }

    public ObjectId[] getMergedCommits() {
        return this.mergedCommits;
    }

    public ObjectId getBase() {
        return this.base;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (ObjectId objectId : this.mergedCommits) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(ObjectId.toString(objectId));
        }
        return MessageFormat.format(JGitText.get().mergeUsingStrategyResultedInDescription, sb, ObjectId.toString(this.base), this.mergeStrategy.getName(), this.mergeStatus, this.description == null ? "" : ", " + this.description);
    }

    public void setConflicts(Map<String, int[][]> map) {
        this.conflicts = map;
    }

    public void addConflict(String str, int[][] iArr) {
        if (this.conflicts == null) {
            this.conflicts = new HashMap();
        }
        this.conflicts.put(str, iArr);
    }

    public void addConflict(String str, org.eclipse.jgit.merge.MergeResult<?> mergeResult) {
        int end;
        if (mergeResult.containsConflicts()) {
            if (this.conflicts == null) {
                this.conflicts = new HashMap();
            }
            Iterator<MergeChunk> it = mergeResult.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (it.next().getConflictState().equals(MergeChunk.ConflictState.FIRST_CONFLICTING_RANGE)) {
                    i++;
                }
            }
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i, this.mergedCommits.length + 1);
            int i2 = -1;
            for (MergeChunk mergeChunk : mergeResult) {
                if (mergeChunk.getConflictState().equals(MergeChunk.ConflictState.FIRST_CONFLICTING_RANGE)) {
                    if (i2 > -1) {
                        iArr[i2][this.mergedCommits.length] = 0;
                    }
                    i2++;
                    end = mergeChunk.getEnd();
                    iArr[i2][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
                } else {
                    end = 0;
                }
                if (mergeChunk.getConflictState().equals(MergeChunk.ConflictState.NEXT_CONFLICTING_RANGE)) {
                    if (mergeChunk.getEnd() > end) {
                        mergeChunk.getEnd();
                    }
                    iArr[i2][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
                }
            }
            this.conflicts.put(str, iArr);
        }
    }

    public Map<String, int[][]> getConflicts() {
        return this.conflicts;
    }

    public Map<String, ResolveMerger.MergeFailureReason> getFailingPaths() {
        return this.failingPaths;
    }

    public List<String> getCheckoutConflicts() {
        return this.checkoutConflicts;
    }
}
