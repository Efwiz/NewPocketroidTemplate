package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.RefList;

/* JADX INFO: loaded from: classes13.dex */
class PackedBatchRefUpdate extends BatchRefUpdate {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$ReceiveCommand$Type;
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private RefDirectory refdb;

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$ReceiveCommand$Type() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$transport$ReceiveCommand$Type;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ReceiveCommand.Type.valuesCustom().length];
        try {
            iArr2[ReceiveCommand.Type.CREATE.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ReceiveCommand.Type.DELETE.ordinal()] = 4;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ReceiveCommand.Type.UPDATE.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[ReceiveCommand.Type.UPDATE_NONFASTFORWARD.ordinal()] = 3;
        } catch (NoSuchFieldError unused4) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$transport$ReceiveCommand$Type = iArr2;
        return iArr2;
    }

    PackedBatchRefUpdate(RefDirectory refDirectory) {
        super(refDirectory);
        this.refdb = refDirectory;
    }

    @Override // org.eclipse.jgit.lib.BatchRefUpdate
    public void execute(RevWalk revWalk, ProgressMonitor progressMonitor, List<String> list) throws IOException {
        RefDirectory.PackedRefList packedRefs;
        if (!isAtomic()) {
            super.execute(revWalk, progressMonitor, list);
            return;
        }
        List<ReceiveCommand> listFilter = ReceiveCommand.filter(getCommands(), ReceiveCommand.Result.NOT_ATTEMPTED);
        if (listFilter.isEmpty()) {
            return;
        }
        if (listFilter.size() == 1) {
            super.execute(revWalk, progressMonitor, list);
            return;
        }
        if (containsSymrefs(listFilter)) {
            reject(listFilter.get(0), ReceiveCommand.Result.REJECTED_OTHER_REASON, JGitText.get().atomicSymRefNotSupported, listFilter);
            return;
        }
        if (blockUntilTimestamps(MAX_WAIT)) {
            if (list != null) {
                setPushOptions(list);
            }
            if (checkConflictingNames(listFilter) && checkObjectExistence(revWalk, listFilter) && checkNonFastForwards(revWalk, listFilter)) {
                try {
                    this.refdb.pack((List<String>) listFilter.stream().map(new PackedBatchRefUpdate$$ExternalSyntheticLambda0()).collect(Collectors.toList()));
                    this.refdb.inProcessPackedRefsLock.lock();
                    Map<String, LockFile> mapLockLooseRefs = null;
                    try {
                        if (this.refdb.isInClone()) {
                            packedRefs = this.refdb.getPackedRefs();
                        } else {
                            mapLockLooseRefs = lockLooseRefs(listFilter);
                            if (mapLockLooseRefs == null) {
                                try {
                                    unlockAll(mapLockLooseRefs);
                                    return;
                                } finally {
                                    this.refdb.inProcessPackedRefsLock.unlock();
                                }
                            }
                            packedRefs = this.refdb.pack(mapLockLooseRefs);
                        }
                        RefList<Ref> refListApplyUpdates = applyUpdates(revWalk, packedRefs, listFilter);
                        if (refListApplyUpdates == null) {
                            try {
                                unlockAll(mapLockLooseRefs);
                                return;
                            } finally {
                                this.refdb.inProcessPackedRefsLock.unlock();
                            }
                        }
                        LockFile lockFileLockPackedRefs = this.refdb.lockPackedRefs();
                        if (lockFileLockPackedRefs == null) {
                            lockFailure(listFilter.get(0), listFilter);
                            try {
                                unlockAll(mapLockLooseRefs);
                                return;
                            } finally {
                                this.refdb.inProcessPackedRefsLock.unlock();
                            }
                        }
                        this.refdb.commitPackedRefs(lockFileLockPackedRefs, refListApplyUpdates, packedRefs, true);
                        try {
                            unlockAll(mapLockLooseRefs);
                            this.refdb.inProcessPackedRefsLock.unlock();
                            this.refdb.fireRefsChanged();
                            listFilter.forEach(new Consumer() { // from class: org.eclipse.jgit.internal.storage.file.PackedBatchRefUpdate$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    ((ReceiveCommand) obj).setResult(ReceiveCommand.Result.OK);
                                }
                            });
                            writeReflog(listFilter);
                        } catch (Throwable th) {
                            this.refdb.inProcessPackedRefsLock.unlock();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        try {
                            unlockAll(null);
                            throw th2;
                        } finally {
                            this.refdb.inProcessPackedRefsLock.unlock();
                        }
                    }
                } catch (LockFailedException unused) {
                    lockFailure(listFilter.get(0), listFilter);
                }
            }
        }
    }

    private static boolean containsSymrefs(List<ReceiveCommand> list) {
        for (ReceiveCommand receiveCommand : list) {
            if (receiveCommand.getOldSymref() != null || receiveCommand.getNewSymref() != null) {
                return true;
            }
        }
        return false;
    }

    private boolean checkConflictingNames(List<ReceiveCommand> list) throws IOException {
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        for (ReceiveCommand receiveCommand : list) {
            if (receiveCommand.getType() != ReceiveCommand.Type.DELETE) {
                hashSet.add(receiveCommand.getRefName());
                addPrefixesTo(receiveCommand.getRefName(), hashSet2);
            } else {
                hashSet3.add(receiveCommand.getRefName());
            }
        }
        for (String str : this.refdb.getRefs("").keySet()) {
            if (!hashSet3.contains(str)) {
                hashSet.add(str);
                addPrefixesTo(str, hashSet2);
            }
        }
        for (ReceiveCommand receiveCommand2 : list) {
            if (receiveCommand2.getType() != ReceiveCommand.Type.DELETE && hashSet2.contains(receiveCommand2.getRefName())) {
                lockFailure(receiveCommand2, list);
                return false;
            }
            Iterator<String> it = getPrefixes(receiveCommand2.getRefName()).iterator();
            while (it.hasNext()) {
                if (hashSet.contains(it.next())) {
                    lockFailure(receiveCommand2, list);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkObjectExistence(RevWalk revWalk, List<ReceiveCommand> list) throws IOException {
        for (ReceiveCommand receiveCommand : list) {
            try {
                if (!receiveCommand.getNewId().equals((AnyObjectId) ObjectId.zeroId())) {
                    revWalk.parseAny(receiveCommand.getNewId());
                }
            } catch (MissingObjectException unused) {
                reject(receiveCommand, ReceiveCommand.Result.REJECTED_MISSING_OBJECT, list);
                return false;
            }
        }
        return true;
    }

    private boolean checkNonFastForwards(RevWalk revWalk, List<ReceiveCommand> list) throws IOException {
        if (isAllowNonFastForwards()) {
            return true;
        }
        for (ReceiveCommand receiveCommand : list) {
            receiveCommand.updateType(revWalk);
            if (receiveCommand.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD) {
                reject(receiveCommand, ReceiveCommand.Result.REJECTED_NONFASTFORWARD, list);
                return false;
            }
        }
        return true;
    }

    @Nullable
    private Map<String, LockFile> lockLooseRefs(List<ReceiveCommand> list) throws IOException {
        ReceiveCommand next;
        LockFile lockFile;
        HashMap map = new HashMap();
        try {
            Iterator<Integer> it = this.refdb.getRetrySleepMs().iterator();
            ReceiveCommand receiveCommand = null;
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                unlockAll(map);
                map.clear();
                RefDirectory.sleep(iIntValue);
                Iterator<ReceiveCommand> it2 = list.iterator();
                do {
                    if (it2.hasNext()) {
                        next = it2.next();
                        String refName = next.getRefName();
                        lockFile = new LockFile(this.refdb.fileFor(refName));
                        if (map.put(refName, lockFile) != null) {
                            throw new IOException(MessageFormat.format(JGitText.get().duplicateRef, refName));
                        }
                    } else {
                        unlockAll(null);
                        return map;
                    }
                } while (lockFile.lock());
                receiveCommand = next;
            }
            unlockAll(map);
            if (receiveCommand == null) {
                receiveCommand = list.get(0);
            }
            lockFailure(receiveCommand, list);
            return null;
        } catch (Throwable th) {
            unlockAll(map);
            throw th;
        }
    }

    private static RefList<Ref> applyUpdates(RevWalk revWalk, RefList<Ref> refList, List<ReceiveCommand> list) throws IOException {
        int iCompareTo;
        Collections.sort(list, Comparator.comparing(new PackedBatchRefUpdate$$ExternalSyntheticLambda0()));
        Iterator<ReceiveCommand> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = $SWITCH_TABLE$org$eclipse$jgit$transport$ReceiveCommand$Type()[it.next().getType().ordinal()];
            if (i2 == 1) {
                i++;
            } else if (i2 == 4) {
                i--;
            }
        }
        RefList.Builder builder = new RefList.Builder(refList.size() + i);
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 < refList.size() || i4 < list.size()) {
                Ref ref = i3 < refList.size() ? refList.get(i3) : null;
                ReceiveCommand receiveCommand = i4 < list.size() ? list.get(i4) : null;
                if (ref != null && receiveCommand != null) {
                    iCompareTo = ref.getName().compareTo(receiveCommand.getRefName());
                } else if (ref == null) {
                    iCompareTo = 1;
                } else {
                    iCompareTo = receiveCommand == null ? -1 : 0;
                }
                if (iCompareTo < 0) {
                    builder.add(ref);
                } else if (iCompareTo > 0) {
                    if (receiveCommand.getType() != ReceiveCommand.Type.CREATE) {
                        lockFailure(receiveCommand, list);
                        return null;
                    }
                    builder.add(peeledRef(revWalk, receiveCommand));
                    i4++;
                } else {
                    if (!receiveCommand.getOldId().equals((AnyObjectId) ref.getObjectId())) {
                        lockFailure(receiveCommand, list);
                        return null;
                    }
                    if (receiveCommand.getType() != ReceiveCommand.Type.DELETE) {
                        builder.add(peeledRef(revWalk, receiveCommand));
                    }
                    i4++;
                }
                i3++;
            } else {
                return builder.toRefList();
            }
        }
    }

    private void writeReflog(List<ReceiveCommand> list) {
        String str;
        String resultString;
        PersonIdent refLogIdent = getRefLogIdent();
        if (refLogIdent == null) {
            refLogIdent = new PersonIdent(this.refdb.getRepository());
        }
        for (ReceiveCommand receiveCommand : list) {
            if (receiveCommand.getResult() == ReceiveCommand.Result.OK) {
                String refName = receiveCommand.getRefName();
                if (receiveCommand.getType() == ReceiveCommand.Type.DELETE) {
                    try {
                        RefDirectory.delete(this.refdb.logFor(refName), RefDirectory.levelsIn(refName));
                    } catch (IOException unused) {
                    }
                } else if (!isRefLogDisabled(receiveCommand)) {
                    String refLogMessage = getRefLogMessage(receiveCommand);
                    if (!isRefLogIncludingResult(receiveCommand) || (resultString = toResultString(receiveCommand)) == null) {
                        str = refLogMessage;
                    } else {
                        if (!refLogMessage.isEmpty()) {
                            resultString = String.valueOf(refLogMessage) + ": " + resultString;
                        }
                        str = resultString;
                    }
                    new ReflogWriter(this.refdb, isForceRefLog(receiveCommand)).log(refName, receiveCommand.getOldId(), receiveCommand.getNewId(), refLogIdent, str);
                }
            }
        }
    }

    private String toResultString(ReceiveCommand receiveCommand) {
        int i = $SWITCH_TABLE$org$eclipse$jgit$transport$ReceiveCommand$Type()[receiveCommand.getType().ordinal()];
        if (i == 1) {
            return ReflogEntry.PREFIX_CREATED;
        }
        if (i == 2) {
            return isAllowNonFastForwards() ? ReflogEntry.PREFIX_FORCED_UPDATE : ReflogEntry.PREFIX_FAST_FORWARD;
        }
        if (i != 3) {
            return null;
        }
        return ReflogEntry.PREFIX_FORCED_UPDATE;
    }

    private static Ref peeledRef(RevWalk revWalk, ReceiveCommand receiveCommand) throws IOException {
        ObjectId objectIdCopy = receiveCommand.getNewId().copy();
        RevObject any = revWalk.parseAny(objectIdCopy);
        if (any instanceof RevTag) {
            return new ObjectIdRef.PeeledTag(Ref.Storage.PACKED, receiveCommand.getRefName(), objectIdCopy, revWalk.peel(any).copy());
        }
        return new ObjectIdRef.PeeledNonTag(Ref.Storage.PACKED, receiveCommand.getRefName(), objectIdCopy);
    }

    private static void unlockAll(@Nullable Map<?, LockFile> map) {
        if (map != null) {
            map.values().forEach(new Consumer() { // from class: org.eclipse.jgit.internal.storage.file.PackedBatchRefUpdate$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((LockFile) obj).unlock();
                }
            });
        }
    }

    private static void lockFailure(ReceiveCommand receiveCommand, List<ReceiveCommand> list) {
        reject(receiveCommand, ReceiveCommand.Result.LOCK_FAILURE, list);
    }

    private static void reject(ReceiveCommand receiveCommand, ReceiveCommand.Result result, List<ReceiveCommand> list) {
        reject(receiveCommand, result, null, list);
    }

    private static void reject(ReceiveCommand receiveCommand, ReceiveCommand.Result result, String str, List<ReceiveCommand> list) {
        receiveCommand.setResult(result, str);
        for (ReceiveCommand receiveCommand2 : list) {
            if (receiveCommand2.getResult() == ReceiveCommand.Result.OK) {
                receiveCommand2.setResult(ReceiveCommand.Result.NOT_ATTEMPTED);
            }
        }
        ReceiveCommand.abort(list);
    }
}
