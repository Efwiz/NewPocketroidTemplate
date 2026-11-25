package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

/* JADX INFO: loaded from: classes13.dex */
class PushProcess {
    static final String PROGRESS_OPENING_CONNECTION = JGitText.get().openingConnection;
    private PushConnection connection;
    private final OutputStream out;
    private List<String> pushOptions;
    private final Map<String, RemoteRefUpdate> toPush;
    private final Transport transport;
    private final RevWalk walker;

    PushProcess(Transport transport, Collection<RemoteRefUpdate> collection) throws TransportException {
        this(transport, collection, null);
    }

    PushProcess(Transport transport, Collection<RemoteRefUpdate> collection, OutputStream outputStream) throws TransportException {
        this.walker = new RevWalk(transport.local);
        this.transport = transport;
        this.toPush = new LinkedHashMap();
        this.out = outputStream;
        this.pushOptions = transport.getPushOptions();
        for (RemoteRefUpdate remoteRefUpdate : collection) {
            if (this.toPush.put(remoteRefUpdate.getRemoteName(), remoteRefUpdate) != null) {
                throw new TransportException(MessageFormat.format(JGitText.get().duplicateRemoteRefUpdateIsIllegal, remoteRefUpdate.getRemoteName()));
            }
        }
    }

    PushResult execute(ProgressMonitor progressMonitor) throws NotSupportedException, TransportException {
        try {
            progressMonitor.beginTask(PROGRESS_OPENING_CONNECTION, 0);
            PushResult pushResult = new PushResult();
            this.connection = this.transport.openPush();
            try {
                pushResult.setAdvertisedRefs(this.transport.getURI(), this.connection.getRefsMap());
                pushResult.peerUserAgent = this.connection.getPeerUserAgent();
                pushResult.setRemoteUpdates(this.toPush);
                progressMonitor.endTask();
                Map<String, RemoteRefUpdate> mapPrepareRemoteUpdates = prepareRemoteUpdates();
                if (this.transport.isDryRun()) {
                    modifyUpdatesForDryRun();
                } else if (!mapPrepareRemoteUpdates.isEmpty()) {
                    this.connection.push(progressMonitor, mapPrepareRemoteUpdates, this.out);
                }
                this.connection.close();
                pushResult.addMessages(this.connection.getMessages());
                if (!this.transport.isDryRun()) {
                    updateTrackingRefs();
                }
                Iterator<RemoteRefUpdate> it = this.toPush.values().iterator();
                while (it.hasNext()) {
                    TrackingRefUpdate trackingRefUpdate = it.next().getTrackingRefUpdate();
                    if (trackingRefUpdate != null) {
                        pushResult.add(trackingRefUpdate);
                    }
                }
                this.walker.close();
                return pushResult;
            } catch (Throwable th) {
                this.connection.close();
                pushResult.addMessages(this.connection.getMessages());
                throw th;
            }
        } catch (Throwable th2) {
            this.walker.close();
            throw th2;
        }
    }

    private Map<String, RemoteRefUpdate> prepareRemoteUpdates() throws TransportException {
        boolean z;
        boolean zIsPushAtomic = this.transport.isPushAtomic();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (RemoteRefUpdate remoteRefUpdate : this.toPush.values()) {
            Ref ref = this.connection.getRef(remoteRefUpdate.getRemoteName());
            ObjectId objectId = ref != null ? ref.getObjectId() : null;
            if (objectId == null) {
                objectId = ObjectId.zeroId();
            }
            if (remoteRefUpdate.getNewObjectId().equals((AnyObjectId) objectId)) {
                if (remoteRefUpdate.isDelete()) {
                    remoteRefUpdate.setStatus(RemoteRefUpdate.Status.NON_EXISTING);
                } else {
                    remoteRefUpdate.setStatus(RemoteRefUpdate.Status.UP_TO_DATE);
                }
            } else if (remoteRefUpdate.isExpectingOldObjectId() && !remoteRefUpdate.getExpectedOldObjectId().equals((AnyObjectId) objectId)) {
                remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED);
                if (zIsPushAtomic) {
                    return rejectAll();
                }
            } else {
                if (!remoteRefUpdate.isExpectingOldObjectId()) {
                    remoteRefUpdate.setExpectedOldObjectId(objectId);
                }
                if (objectId.equals((AnyObjectId) ObjectId.zeroId()) || remoteRefUpdate.isDelete()) {
                    remoteRefUpdate.setFastForward(true);
                    linkedHashMap.put(remoteRefUpdate.getRemoteName(), remoteRefUpdate);
                } else {
                    try {
                        RevObject any = this.walker.parseAny(objectId);
                        RevObject any2 = this.walker.parseAny(remoteRefUpdate.getNewObjectId());
                        z = (any instanceof RevCommit) && (any2 instanceof RevCommit) && this.walker.isMergedInto((RevCommit) any, (RevCommit) any2);
                    } catch (MissingObjectException unused) {
                    } catch (Exception e) {
                        throw new TransportException(this.transport.getURI(), MessageFormat.format(JGitText.get().readingObjectsFromLocalRepositoryFailed, e.getMessage()), e);
                    }
                    remoteRefUpdate.setFastForward(z);
                    if (!z && !remoteRefUpdate.isForceUpdate()) {
                        remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_NONFASTFORWARD);
                        if (zIsPushAtomic) {
                            return rejectAll();
                        }
                    } else {
                        linkedHashMap.put(remoteRefUpdate.getRemoteName(), remoteRefUpdate);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    private Map<String, RemoteRefUpdate> rejectAll() {
        for (RemoteRefUpdate remoteRefUpdate : this.toPush.values()) {
            if (remoteRefUpdate.getStatus() == RemoteRefUpdate.Status.NOT_ATTEMPTED) {
                remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
                remoteRefUpdate.setMessage(JGitText.get().transactionAborted);
            }
        }
        return Collections.emptyMap();
    }

    private void modifyUpdatesForDryRun() {
        for (RemoteRefUpdate remoteRefUpdate : this.toPush.values()) {
            if (remoteRefUpdate.getStatus() == RemoteRefUpdate.Status.NOT_ATTEMPTED) {
                remoteRefUpdate.setStatus(RemoteRefUpdate.Status.OK);
            }
        }
    }

    private void updateTrackingRefs() {
        for (RemoteRefUpdate remoteRefUpdate : this.toPush.values()) {
            RemoteRefUpdate.Status status = remoteRefUpdate.getStatus();
            if (remoteRefUpdate.hasTrackingRefUpdate() && (status == RemoteRefUpdate.Status.UP_TO_DATE || status == RemoteRefUpdate.Status.OK)) {
                try {
                    remoteRefUpdate.updateTrackingRef(this.walker);
                } catch (IOException unused) {
                }
            }
        }
    }

    public List<String> getPushOptions() {
        return this.pushOptions;
    }
}
