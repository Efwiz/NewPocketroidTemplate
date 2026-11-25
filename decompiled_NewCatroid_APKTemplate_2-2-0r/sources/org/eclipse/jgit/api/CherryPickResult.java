package org.eclipse.jgit.api;

import java.util.List;
import java.util.Map;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.revwalk.RevCommit;

/* JADX INFO: loaded from: classes13.dex */
public class CherryPickResult {
    public static final CherryPickResult CONFLICT = new CherryPickResult(CherryPickStatus.CONFLICTING);
    private final List<Ref> cherryPickedRefs;
    private final Map<String, ResolveMerger.MergeFailureReason> failingPaths;
    private final RevCommit newHead;
    private final CherryPickStatus status;

    public enum CherryPickStatus {
        OK { // from class: org.eclipse.jgit.api.CherryPickResult.CherryPickStatus.1
            @Override // java.lang.Enum
            public String toString() {
                return "Ok";
            }
        },
        FAILED { // from class: org.eclipse.jgit.api.CherryPickResult.CherryPickStatus.2
            @Override // java.lang.Enum
            public String toString() {
                return "Failed";
            }
        },
        CONFLICTING { // from class: org.eclipse.jgit.api.CherryPickResult.CherryPickStatus.3
            @Override // java.lang.Enum
            public String toString() {
                return "Conflicting";
            }
        };

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static CherryPickStatus[] valuesCustom() {
            CherryPickStatus[] cherryPickStatusArrValuesCustom = values();
            int length = cherryPickStatusArrValuesCustom.length;
            CherryPickStatus[] cherryPickStatusArr = new CherryPickStatus[length];
            System.arraycopy(cherryPickStatusArrValuesCustom, 0, cherryPickStatusArr, 0, length);
            return cherryPickStatusArr;
        }

        /* synthetic */ CherryPickStatus(CherryPickStatus cherryPickStatus) {
            this();
        }
    }

    public CherryPickResult(RevCommit revCommit, List<Ref> list) {
        this.status = CherryPickStatus.OK;
        this.newHead = revCommit;
        this.cherryPickedRefs = list;
        this.failingPaths = null;
    }

    public CherryPickResult(Map<String, ResolveMerger.MergeFailureReason> map) {
        this.status = CherryPickStatus.FAILED;
        this.newHead = null;
        this.cherryPickedRefs = null;
        this.failingPaths = map;
    }

    private CherryPickResult(CherryPickStatus cherryPickStatus) {
        this.status = cherryPickStatus;
        this.newHead = null;
        this.cherryPickedRefs = null;
        this.failingPaths = null;
    }

    public CherryPickStatus getStatus() {
        return this.status;
    }

    public RevCommit getNewHead() {
        return this.newHead;
    }

    public List<Ref> getCherryPickedRefs() {
        return this.cherryPickedRefs;
    }

    public Map<String, ResolveMerger.MergeFailureReason> getFailingPaths() {
        return this.failingPaths;
    }
}
