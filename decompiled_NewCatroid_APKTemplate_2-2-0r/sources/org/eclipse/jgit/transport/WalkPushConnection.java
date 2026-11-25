package org.eclipse.jgit.transport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.Repository;

/* JADX INFO: loaded from: classes13.dex */
class WalkPushConnection extends BaseConnection implements PushConnection {
    final WalkRemoteObjectDatabase dest;
    private final Repository local;
    private Map<String, Ref> newRefs;
    private LinkedHashMap<String, String> packNames;
    private Collection<RemoteRefUpdate> packedRefUpdates;
    private final Transport transport;
    private final URIish uri;

    /* JADX WARN: Multi-variable type inference failed */
    WalkPushConnection(WalkTransport walkTransport, WalkRemoteObjectDatabase walkRemoteObjectDatabase) {
        Transport transport = (Transport) walkTransport;
        this.transport = transport;
        this.local = transport.local;
        this.uri = transport.getURI();
        this.dest = walkRemoteObjectDatabase;
    }

    @Override // org.eclipse.jgit.transport.PushConnection
    public void push(ProgressMonitor progressMonitor, Map<String, RemoteRefUpdate> map) throws Throwable {
        push(progressMonitor, map, null);
    }

    @Override // org.eclipse.jgit.transport.PushConnection
    public void push(ProgressMonitor progressMonitor, Map<String, RemoteRefUpdate> map, OutputStream outputStream) throws Throwable {
        markStartedOperation();
        this.packNames = null;
        this.newRefs = new TreeMap(getRefsMap());
        this.packedRefUpdates = new ArrayList(map.size());
        ArrayList arrayList = new ArrayList();
        for (RemoteRefUpdate remoteRefUpdate : map.values()) {
            String remoteName = remoteRefUpdate.getRemoteName();
            if (!remoteName.startsWith(Constants.R_REFS) || !Repository.isValidRefName(remoteName)) {
                remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
                remoteRefUpdate.setMessage(JGitText.get().funnyRefname);
            } else if (AnyObjectId.isEqual(ObjectId.zeroId(), remoteRefUpdate.getNewObjectId())) {
                deleteCommand(remoteRefUpdate);
            } else {
                arrayList.add(remoteRefUpdate);
            }
        }
        if (!arrayList.isEmpty()) {
            sendpack(arrayList, progressMonitor);
        }
        Iterator<RemoteRefUpdate> it = arrayList.iterator();
        while (it.hasNext()) {
            updateCommand(it.next());
        }
        if (!arrayList.isEmpty() && isNewRepository()) {
            createNewRepository(arrayList);
        }
        RefWriter refWriter = new RefWriter(this.newRefs.values()) { // from class: org.eclipse.jgit.transport.WalkPushConnection.1
            @Override // org.eclipse.jgit.lib.RefWriter
            protected void writeFile(String str, byte[] bArr) throws IOException {
                WalkPushConnection.this.dest.writeFile("../" + str, bArr);
            }
        };
        if (!this.packedRefUpdates.isEmpty()) {
            try {
                refWriter.writePackedRefs();
                Iterator<RemoteRefUpdate> it2 = this.packedRefUpdates.iterator();
                while (it2.hasNext()) {
                    it2.next().setStatus(RemoteRefUpdate.Status.OK);
                }
            } catch (IOException e) {
                for (RemoteRefUpdate remoteRefUpdate2 : this.packedRefUpdates) {
                    remoteRefUpdate2.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
                    remoteRefUpdate2.setMessage(e.getMessage());
                }
                throw new TransportException(this.uri, JGitText.get().failedUpdatingRefs, e);
            }
        }
        try {
            refWriter.writeInfoRefs();
        } catch (IOException e2) {
            throw new TransportException(this.uri, JGitText.get().failedUpdatingRefs, e2);
        }
    }

    @Override // org.eclipse.jgit.transport.BaseConnection, org.eclipse.jgit.transport.Connection, java.lang.AutoCloseable
    public void close() {
        this.dest.close();
    }

    /* JADX WARN: Code duplicated, block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x01a2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:77:0x01a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [org.eclipse.jgit.transport.WalkPushConnection] */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r13v5 */
    private void sendpack(List<RemoteRefUpdate> list, ProgressMonitor progressMonitor) throws Throwable {
        ?? r13;
        Throwable th;
        Throwable th2 = null;
        try {
            try {
                PackWriter packWriter = new PackWriter(this.transport.getPackConfig(), this.local.newObjectReader());
                try {
                    HashSet hashSet = new HashSet();
                    HashSet hashSet2 = new HashSet();
                    Iterator<RemoteRefUpdate> it = list.iterator();
                    while (it.hasNext()) {
                        hashSet.add(it.next().getNewObjectId());
                    }
                    for (Ref ref : getRefs()) {
                        hashSet2.add(ref.getObjectId());
                        if (ref.getPeeledObjectId() != null) {
                            hashSet2.add(ref.getPeeledObjectId());
                        }
                    }
                    packWriter.preparePack(progressMonitor, hashSet, hashSet2);
                    if (packWriter.getObjectCount() != 0) {
                        this.packNames = new LinkedHashMap<>();
                        for (String str : this.dest.getPackNames()) {
                            this.packNames.put(str, str);
                        }
                        PackFile packFile = new PackFile(new File(ConfigConstants.CONFIG_PACK_SECTION), packWriter.computeName(), PackExt.PACK);
                        try {
                            PackFile packFileCreate = packFile.create(PackExt.INDEX);
                            try {
                                if (this.packNames.remove(packFile.getName()) != null) {
                                    this.dest.writeInfoPacks(this.packNames.keySet());
                                    this.dest.deleteFile(packFileCreate.getPath());
                                }
                                String str2 = "Put " + packFile.getName().substring(0, 12);
                                try {
                                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.dest.writeFile(packFile.getPath(), progressMonitor, String.valueOf(str2) + "." + packFile.getPackExt().getExtension()));
                                    try {
                                        packWriter.writePack(progressMonitor, progressMonitor, bufferedOutputStream);
                                        bufferedOutputStream.close();
                                        try {
                                            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(this.dest.writeFile(packFileCreate.getPath(), progressMonitor, String.valueOf(str2) + "." + packFileCreate.getPackExt().getExtension()));
                                            try {
                                                packWriter.writeIndex(bufferedOutputStream2);
                                                bufferedOutputStream2.close();
                                                ArrayList arrayList = new ArrayList();
                                                arrayList.add(packFile.getName());
                                                arrayList.addAll(this.packNames.keySet());
                                                this.dest.writeInfoPacks(arrayList);
                                                packWriter.close();
                                                return;
                                            } catch (Throwable th3) {
                                                bufferedOutputStream2.close();
                                                throw th3;
                                            }
                                        } catch (Throwable th4) {
                                            if (0 == 0) {
                                                throw th4;
                                            }
                                            if (null != th4) {
                                                th2.addSuppressed(th4);
                                            }
                                            throw null;
                                        }
                                    } catch (Throwable th5) {
                                        bufferedOutputStream.close();
                                        throw th5;
                                    }
                                } catch (Throwable th6) {
                                    if (0 == 0) {
                                        throw th6;
                                    }
                                    if (null != th6) {
                                        th2.addSuppressed(th6);
                                    }
                                    throw null;
                                }
                            } catch (Throwable th7) {
                                th = th7;
                            }
                        } catch (Throwable th8) {
                            th = th8;
                        }
                    } else {
                        packWriter.close();
                        return;
                    }
                } catch (Throwable th9) {
                    th = th9;
                }
                packWriter.close();
                throw th;
            } catch (Throwable th10) {
                r13 = list;
                th = th10;
                if (0 != 0) {
                    throw th;
                }
                if (null != th) {
                    try {
                        th2.addSuppressed(th);
                    } catch (IOException e) {
                        safeDelete(r13);
                        safeDelete(null);
                        throw new TransportException(this.uri, JGitText.get().cannotStoreObjects, e);
                    }
                }
                throw null;
            }
        } catch (Throwable th11) {
            th = th11;
            r13 = 0;
            if (0 != 0) {
                throw th;
            }
            if (null != th) {
                th2.addSuppressed(th);
            }
            throw null;
        }
    }

    private void safeDelete(File file) {
        if (file != null) {
            try {
                this.dest.deleteFile(file.getPath());
            } catch (IOException unused) {
            }
        }
    }

    private void deleteCommand(RemoteRefUpdate remoteRefUpdate) {
        Ref refRemove = this.newRefs.remove(remoteRefUpdate.getRemoteName());
        if (refRemove == null) {
            remoteRefUpdate.setStatus(RemoteRefUpdate.Status.OK);
            return;
        }
        if (refRemove.getStorage().isPacked()) {
            this.packedRefUpdates.add(remoteRefUpdate);
        }
        if (refRemove.getStorage().isLoose()) {
            try {
                this.dest.deleteRef(remoteRefUpdate.getRemoteName());
                remoteRefUpdate.setStatus(RemoteRefUpdate.Status.OK);
            } catch (IOException e) {
                remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
                remoteRefUpdate.setMessage(e.getMessage());
            }
        }
        try {
            this.dest.deleteRefLog(remoteRefUpdate.getRemoteName());
        } catch (IOException e2) {
            remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
            remoteRefUpdate.setMessage(e2.getMessage());
        }
    }

    private void updateCommand(RemoteRefUpdate remoteRefUpdate) {
        try {
            this.dest.writeRef(remoteRefUpdate.getRemoteName(), remoteRefUpdate.getNewObjectId());
            this.newRefs.put(remoteRefUpdate.getRemoteName(), new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE, remoteRefUpdate.getRemoteName(), remoteRefUpdate.getNewObjectId()));
            remoteRefUpdate.setStatus(RemoteRefUpdate.Status.OK);
        } catch (IOException e) {
            remoteRefUpdate.setStatus(RemoteRefUpdate.Status.REJECTED_OTHER_REASON);
            remoteRefUpdate.setMessage(e.getMessage());
        }
    }

    private boolean isNewRepository() {
        LinkedHashMap<String, String> linkedHashMap;
        return getRefsMap().isEmpty() && (linkedHashMap = this.packNames) != null && linkedHashMap.isEmpty();
    }

    private void createNewRepository(List<RemoteRefUpdate> list) throws TransportException {
        try {
            this.dest.writeFile("../HEAD", Constants.encode(RefDirectory.SYMREF + pickHEAD(list) + "\n"));
            try {
                this.dest.writeFile("../config", Constants.encode("[core]\n\trepositoryformatversion = 0\n"));
            } catch (IOException e) {
                throw new TransportException(this.uri, JGitText.get().cannotCreateConfig, e);
            }
        } catch (IOException e2) {
            throw new TransportException(this.uri, JGitText.get().cannotCreateHEAD, e2);
        }
    }

    private static String pickHEAD(List<RemoteRefUpdate> list) {
        Iterator<RemoteRefUpdate> it = list.iterator();
        while (it.hasNext()) {
            String remoteName = it.next().getRemoteName();
            if (remoteName.equals("refs/heads/master")) {
                return remoteName;
            }
        }
        Iterator<RemoteRefUpdate> it2 = list.iterator();
        while (it2.hasNext()) {
            String remoteName2 = it2.next().getRemoteName();
            if (remoteName2.startsWith(Constants.R_HEADS)) {
                return remoteName2;
            }
        }
        return list.get(0).getRemoteName();
    }
}
