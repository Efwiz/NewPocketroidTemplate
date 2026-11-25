package org.eclipse.jgit.transport;

import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
public class RemoteRefUpdate {
    private ObjectId expectedOldObjectId;
    private boolean fastForward;
    private final boolean forceUpdate;
    private final Repository localDb;
    private RefUpdate localUpdate;
    private String message;
    private final ObjectId newObjectId;
    private final String remoteName;
    private final String srcRef;
    private Status status;
    private final TrackingRefUpdate trackingRefUpdate;

    public enum Status {
        NOT_ATTEMPTED,
        UP_TO_DATE,
        REJECTED_NONFASTFORWARD,
        REJECTED_NODELETE,
        REJECTED_REMOTE_CHANGED,
        REJECTED_OTHER_REASON,
        NON_EXISTING,
        AWAITING_REPORT,
        OK;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Status[] valuesCustom() {
            Status[] statusArrValuesCustom = values();
            int length = statusArrValuesCustom.length;
            Status[] statusArr = new Status[length];
            System.arraycopy(statusArrValuesCustom, 0, statusArr, 0, length);
            return statusArr;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public RemoteRefUpdate(Repository repository, String str, String str2, boolean z, String str3, ObjectId objectId) throws IOException {
        ObjectId objectIdZeroId;
        if (str != null) {
            objectIdZeroId = repository.resolve(str);
        } else {
            objectIdZeroId = ObjectId.zeroId();
        }
        this(repository, str, objectIdZeroId, str2, z, str3, objectId);
    }

    public RemoteRefUpdate(Repository repository, Ref ref, String str, boolean z, String str2, ObjectId objectId) throws IOException {
        this(repository, ref != null ? ref.getName() : null, ref != null ? ref.getObjectId() : null, str, z, str2, objectId);
    }

    public RemoteRefUpdate(Repository repository, String str, ObjectId objectId, String str2, boolean z, String str3, ObjectId objectId2) throws IOException {
        ObjectId objectIdZeroId;
        if (str2 == null) {
            throw new IllegalArgumentException(JGitText.get().remoteNameCannotBeNull);
        }
        if (objectId == null && str != null) {
            throw new IOException(MessageFormat.format(JGitText.get().sourceRefDoesntResolveToAnyObject, str));
        }
        if (str != null) {
            this.srcRef = str;
        } else if (objectId != null && !objectId.equals((AnyObjectId) ObjectId.zeroId())) {
            this.srcRef = objectId.name();
        } else {
            this.srcRef = null;
        }
        if (objectId != null) {
            this.newObjectId = objectId;
        } else {
            this.newObjectId = ObjectId.zeroId();
        }
        this.remoteName = str2;
        this.forceUpdate = z;
        if (str3 != null && repository != null) {
            RefUpdate refUpdateUpdateRef = repository.updateRef(str3);
            this.localUpdate = refUpdateUpdateRef;
            refUpdateUpdateRef.setForceUpdate(true);
            this.localUpdate.setRefLogMessage("push", true);
            this.localUpdate.setNewObjectId(this.newObjectId);
            if (this.localUpdate.getOldObjectId() != null) {
                objectIdZeroId = this.localUpdate.getOldObjectId();
            } else {
                objectIdZeroId = ObjectId.zeroId();
            }
            this.trackingRefUpdate = new TrackingRefUpdate(true, str2, str3, objectIdZeroId, this.newObjectId);
        } else {
            this.trackingRefUpdate = null;
        }
        this.localDb = repository;
        this.expectedOldObjectId = objectId2;
        this.status = Status.NOT_ATTEMPTED;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public RemoteRefUpdate(RemoteRefUpdate remoteRefUpdate, ObjectId objectId) throws IOException {
        Repository repository = remoteRefUpdate.localDb;
        String str = remoteRefUpdate.srcRef;
        String str2 = remoteRefUpdate.remoteName;
        boolean z = remoteRefUpdate.forceUpdate;
        TrackingRefUpdate trackingRefUpdate = remoteRefUpdate.trackingRefUpdate;
        this(repository, str, str2, z, trackingRefUpdate == null ? null : trackingRefUpdate.getLocalName(), objectId);
    }

    public ObjectId getExpectedOldObjectId() {
        return this.expectedOldObjectId;
    }

    public boolean isExpectingOldObjectId() {
        return this.expectedOldObjectId != null;
    }

    public ObjectId getNewObjectId() {
        return this.newObjectId;
    }

    public boolean isDelete() {
        return ObjectId.zeroId().equals((AnyObjectId) this.newObjectId);
    }

    public String getRemoteName() {
        return this.remoteName;
    }

    public TrackingRefUpdate getTrackingRefUpdate() {
        return this.trackingRefUpdate;
    }

    public String getSrcRef() {
        return this.srcRef;
    }

    public boolean hasTrackingRefUpdate() {
        return this.trackingRefUpdate != null;
    }

    public boolean isForceUpdate() {
        return this.forceUpdate;
    }

    public Status getStatus() {
        return this.status;
    }

    public boolean isFastForward() {
        return this.fastForward;
    }

    public String getMessage() {
        return this.message;
    }

    void setExpectedOldObjectId(ObjectId objectId) {
        this.expectedOldObjectId = objectId;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    void setFastForward(boolean z) {
        this.fastForward = z;
    }

    void setMessage(String str) {
        this.message = str;
    }

    protected void updateTrackingRef(RevWalk revWalk) throws IOException {
        if (isDelete()) {
            this.trackingRefUpdate.setResult(this.localUpdate.delete(revWalk));
        } else {
            this.trackingRefUpdate.setResult(this.localUpdate.update(revWalk));
        }
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("RemoteRefUpdate[remoteName=").append(this.remoteName).append(", ").append(this.status).append(", ");
        ObjectId objectId = this.expectedOldObjectId;
        StringBuilder sbAppend2 = sbAppend.append(objectId != null ? objectId.name() : "(null)").append("...");
        ObjectId objectId2 = this.newObjectId;
        return sbAppend2.append(objectId2 != null ? objectId2.name() : "(null)").append(this.fastForward ? ", fastForward" : "").append(", srcRef=").append(this.srcRef).append(this.forceUpdate ? ", forceUpdate" : "").append(", message=").append(this.message != null ? "\"" + this.message + "\"" : "null").append("]").toString();
    }
}
