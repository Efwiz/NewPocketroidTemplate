package org.eclipse.jgit.transport;

import com.google.android.gms.stats.CodePackage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.RemoteRepositoryException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.TemporaryBuffer;

/* JADX INFO: loaded from: classes13.dex */
public abstract class BasePackFetchConnection extends BasePackConnection implements FetchConnection {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$PacketLineIn$AckNackResult = null;
    private static final int MAX_HAVES = 256;
    protected static final int MIN_CLIENT_BUFFER = 2952;
    public static final String OPTION_ALLOW_REACHABLE_SHA1_IN_WANT = "allow-reachable-sha1-in-want";
    public static final String OPTION_ALLOW_TIP_SHA1_IN_WANT = "allow-tip-sha1-in-want";
    public static final String OPTION_FILTER = "filter";
    public static final String OPTION_INCLUDE_TAG = "include-tag";
    public static final String OPTION_MULTI_ACK = "multi_ack";
    public static final String OPTION_MULTI_ACK_DETAILED = "multi_ack_detailed";
    public static final String OPTION_NO_DONE = "no-done";
    public static final String OPTION_NO_PROGRESS = "no-progress";
    public static final String OPTION_OFS_DELTA = "ofs-delta";
    public static final String OPTION_SHALLOW = "shallow";
    public static final String OPTION_SIDE_BAND = "side-band";
    public static final String OPTION_SIDE_BAND_64K = "side-band-64k";
    public static final String OPTION_THIN_PACK = "thin-pack";
    final RevFlag ADVERTISED;
    final RevFlag COMMON;
    final RevFlag REACHABLE;
    private final RevFlag STATE;
    private boolean allowOfsDelta;
    private final FilterSpec filterSpec;
    private boolean includeTags;
    private String lockMessage;
    private int maxHaves;
    private GitProtocolConstants.MultiAck multiAck;
    private boolean noDone;
    private boolean noProgress;
    private PackLock packLock;
    private PacketLineOut pckState;
    private RevCommitList<RevCommit> reachableCommits;
    private boolean sideband;
    private TemporaryBuffer.Heap state;
    private boolean thinPack;
    private final RevWalk walk;

    @Override // org.eclipse.jgit.transport.FetchConnection
    public boolean didFetchIncludeTags() {
        return false;
    }

    @Override // org.eclipse.jgit.transport.FetchConnection
    public boolean didFetchTestConnectivity() {
        return false;
    }

    @Override // org.eclipse.jgit.transport.BasePackConnection, org.eclipse.jgit.transport.BaseConnection, org.eclipse.jgit.transport.Connection
    public /* bridge */ /* synthetic */ String getPeerUserAgent() {
        return super.getPeerUserAgent();
    }

    protected void onReceivePack() {
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$PacketLineIn$AckNackResult() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$transport$PacketLineIn$AckNackResult;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[PacketLineIn.AckNackResult.valuesCustom().length];
        try {
            iArr2[PacketLineIn.AckNackResult.ACK.ordinal()] = 2;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[PacketLineIn.AckNackResult.ACK_COMMON.ordinal()] = 4;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[PacketLineIn.AckNackResult.ACK_CONTINUE.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[PacketLineIn.AckNackResult.ACK_READY.ordinal()] = 5;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[PacketLineIn.AckNackResult.NAK.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$transport$PacketLineIn$AckNackResult = iArr2;
        return iArr2;
    }

    public BasePackFetchConnection(PackTransport packTransport) {
        super(packTransport);
        this.multiAck = GitProtocolConstants.MultiAck.OFF;
        if (this.local != null) {
            FetchConfig fetchConfig = getFetchConfig();
            this.allowOfsDelta = fetchConfig.allowOfsDelta;
            this.maxHaves = fetchConfig.maxHaves;
        } else {
            this.allowOfsDelta = true;
            this.maxHaves = Integer.MAX_VALUE;
        }
        this.includeTags = this.transport.getTagOpt() != TagOpt.NO_TAGS;
        this.thinPack = this.transport.isFetchThin();
        this.filterSpec = this.transport.getFilterSpec();
        if (this.local != null) {
            RevWalk revWalk = new RevWalk(this.local);
            this.walk = revWalk;
            revWalk.setRetainBody(false);
            this.reachableCommits = new RevCommitList<>();
            RevFlag revFlagNewFlag = revWalk.newFlag("REACHABLE");
            this.REACHABLE = revFlagNewFlag;
            RevFlag revFlagNewFlag2 = revWalk.newFlag(CodePackage.COMMON);
            this.COMMON = revFlagNewFlag2;
            this.STATE = revWalk.newFlag("STATE");
            RevFlag revFlagNewFlag3 = revWalk.newFlag("ADVERTISED");
            this.ADVERTISED = revFlagNewFlag3;
            revWalk.carry(revFlagNewFlag2);
            revWalk.carry(revFlagNewFlag);
            revWalk.carry(revFlagNewFlag3);
            return;
        }
        this.walk = null;
        this.REACHABLE = null;
        this.COMMON = null;
        this.STATE = null;
        this.ADVERTISED = null;
    }

    static class FetchConfig {
        final boolean allowOfsDelta;
        final int maxHaves;

        FetchConfig(Config config) {
            this.allowOfsDelta = config.getBoolean("repack", "usedeltabaseoffset", true);
            this.maxHaves = config.getInt("fetch", "maxhaves", Integer.MAX_VALUE);
        }

        FetchConfig(boolean z, int i) {
            this.allowOfsDelta = z;
            this.maxHaves = i;
        }
    }

    @Override // org.eclipse.jgit.transport.FetchConnection
    public final void fetch(ProgressMonitor progressMonitor, Collection<Ref> collection, Set<ObjectId> set) throws TransportException {
        fetch(progressMonitor, collection, set, null);
    }

    @Override // org.eclipse.jgit.transport.FetchConnection
    public final void fetch(ProgressMonitor progressMonitor, Collection<Ref> collection, Set<ObjectId> set, OutputStream outputStream) throws TransportException {
        markStartedOperation();
        doFetch(progressMonitor, collection, set, outputStream);
    }

    @Override // org.eclipse.jgit.transport.FetchConnection
    public void setPackLockMessage(String str) {
        this.lockMessage = str;
    }

    @Override // org.eclipse.jgit.transport.FetchConnection
    public Collection<PackLock> getPackLocks() {
        PackLock packLock = this.packLock;
        if (packLock != null) {
            return Collections.singleton(packLock);
        }
        return Collections.emptyList();
    }

    private void clearState() {
        this.walk.dispose();
        this.reachableCommits = null;
        this.state = null;
        this.pckState = null;
    }

    protected void doFetch(ProgressMonitor progressMonitor, Collection<Ref> collection, Set<ObjectId> set, OutputStream outputStream) throws TransportException {
        try {
            this.noProgress = progressMonitor == NullProgressMonitor.INSTANCE;
            markRefsAdvertised();
            markReachable(set, maxTimeWanted(collection));
            if (TransferConfig.ProtocolVersion.V2.equals(getProtocolVersion())) {
                this.state = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
                this.pckState = new PacketLineOut(this.state);
                try {
                    doFetchV2(progressMonitor, collection, outputStream);
                    return;
                } finally {
                    clearState();
                }
            }
            if (this.statelessRPC) {
                this.state = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
                this.pckState = new PacketLineOut(this.state);
            }
            PacketLineOut packetLineOut = this.statelessRPC ? this.pckState : this.pckOut;
            if (sendWants(collection, packetLineOut)) {
                packetLineOut.end();
                this.outNeedsEnd = false;
                negotiate(progressMonitor);
                clearState();
                receivePack(progressMonitor, outputStream);
            }
        } catch (IOException e) {
            e = e;
            close();
            throw new TransportException(e.getMessage(), e);
        } catch (RuntimeException e2) {
            e = e2;
            close();
            throw new TransportException(e.getMessage(), e);
        } catch (CancelledException unused) {
            close();
        }
    }

    private void doFetchV2(ProgressMonitor progressMonitor, Collection<Ref> collection, OutputStream outputStream) throws IOException, CancelledException {
        boolean zSendNextHaveBatch;
        this.sideband = true;
        negotiateBegin();
        this.pckState.writeString("command=fetch");
        String str = UserAgent.get();
        if (str != null && isCapableOf(GitProtocolConstants.OPTION_AGENT)) {
            this.pckState.writeString("agent=" + str);
        }
        HashSet hashSet = new HashSet();
        String capability = getCapability("fetch");
        if (!StringUtils.isEmptyOrNull(capability)) {
            hashSet.addAll(Arrays.asList(capability.split(WalkEncryption.Vals.REGEX_WS)));
        }
        this.pckState.writeDelim();
        Iterator<String> it = getCapabilitiesV2(hashSet).iterator();
        while (it.hasNext()) {
            this.pckState.writeString(it.next());
        }
        if (sendWants(collection, this.pckState)) {
            this.outNeedsEnd = false;
            FetchStateV2 fetchStateV2 = new FetchStateV2(null);
            do {
                this.state.writeTo(this.out, progressMonitor);
                zSendNextHaveBatch = sendNextHaveBatch(fetchStateV2, this.pckOut, progressMonitor);
                if (zSendNextHaveBatch) {
                    break;
                }
            } while (!readAcknowledgments(fetchStateV2, this.pckIn, progressMonitor));
            clearState();
            String string = this.pckIn.readString();
            if (zSendNextHaveBatch && string.startsWith("ERR ")) {
                throw new RemoteRepositoryException(this.uri, string.substring(4));
            }
            if (!GitProtocolConstants.SECTION_PACKFILE.equals(string)) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, GitProtocolConstants.SECTION_PACKFILE, string));
            }
            receivePack(progressMonitor, outputStream);
        }
    }

    private boolean sendNextHaveBatch(FetchStateV2 fetchStateV2, PacketLineOut packetLineOut, ProgressMonitor progressMonitor) throws IOException, CancelledException {
        RevCommit next;
        long j = 0;
        while (j < fetchStateV2.havesToSend && (next = this.walk.next()) != null) {
            packetLineOut.writeString("have " + next.getId().name() + '\n');
            j++;
            if (j % 10 == 0 && progressMonitor.isCancelled()) {
                throw new CancelledException(null);
            }
        }
        fetchStateV2.havesTotal += j;
        if (j == 0 || ((fetchStateV2.hadAcks && fetchStateV2.havesWithoutAck > 256) || fetchStateV2.havesTotal > this.maxHaves)) {
            packetLineOut.writeString("done\n");
            packetLineOut.end();
            return true;
        }
        fetchStateV2.havesWithoutAck += j;
        packetLineOut.end();
        fetchStateV2.incHavesToSend(this.statelessRPC);
        return false;
    }

    private boolean readAcknowledgments(FetchStateV2 fetchStateV2, PacketLineIn packetLineIn, ProgressMonitor progressMonitor) throws IOException, CancelledException {
        String string = packetLineIn.readString();
        if (!GitProtocolConstants.SECTION_ACKNOWLEDGMENTS.equals(string)) {
            throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, GitProtocolConstants.SECTION_ACKNOWLEDGMENTS, string));
        }
        MutableObjectId mutableObjectId = new MutableObjectId();
        String string2 = packetLineIn.readString();
        boolean z = false;
        long j = 0;
        while (!PacketLineIn.isEnd(string2) && !PacketLineIn.isDelimiter(string2)) {
            PacketLineIn.AckNackResult aCKv2 = PacketLineIn.parseACKv2(string2, mutableObjectId);
            if (!z) {
                if (aCKv2 == PacketLineIn.AckNackResult.ACK_COMMON) {
                    markCommon(this.walk.parseAny(mutableObjectId), aCKv2, true);
                    fetchStateV2.havesWithoutAck = 0L;
                    fetchStateV2.hadAcks = true;
                } else if (aCKv2 == PacketLineIn.AckNackResult.ACK_READY) {
                    z = true;
                }
            }
            j++;
            if (j % 10 == 0 && progressMonitor.isCancelled()) {
                throw new CancelledException(null);
            }
            string2 = packetLineIn.readString();
        }
        if (z) {
            if (!PacketLineIn.isDelimiter(string2)) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, "0001", string2));
            }
        } else if (!PacketLineIn.isEnd(string2)) {
            throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, "0000", string2));
        }
        return z;
    }

    @Override // org.eclipse.jgit.transport.BasePackConnection, org.eclipse.jgit.transport.BaseConnection, org.eclipse.jgit.transport.Connection, java.lang.AutoCloseable
    public void close() {
        RevWalk revWalk = this.walk;
        if (revWalk != null) {
            revWalk.close();
        }
        super.close();
    }

    FetchConfig getFetchConfig() {
        return (FetchConfig) this.local.getConfig().get(new Config.SectionParser() { // from class: org.eclipse.jgit.transport.BasePackFetchConnection$$ExternalSyntheticLambda0
            @Override // org.eclipse.jgit.lib.Config.SectionParser
            public final Object parse(Config config) {
                return new BasePackFetchConnection.FetchConfig(config);
            }
        });
    }

    private int maxTimeWanted(Collection<Ref> collection) {
        int commitTime;
        Iterator<Ref> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                RevObject any = this.walk.parseAny(it.next().getObjectId());
                if ((any instanceof RevCommit) && i < (commitTime = ((RevCommit) any).getCommitTime())) {
                    i = commitTime;
                }
            } catch (IOException unused) {
            }
        }
        return i;
    }

    private void markReachable(Set<ObjectId> set, int i) throws IOException {
        for (Ref ref : this.local.getRefDatabase().getRefs()) {
            ObjectId peeledObjectId = ref.getPeeledObjectId();
            if (peeledObjectId == null) {
                peeledObjectId = ref.getObjectId();
            }
            if (peeledObjectId != null) {
                parseReachable(peeledObjectId);
            }
        }
        Iterator<ObjectId> it = this.local.getAdditionalHaves().iterator();
        while (it.hasNext()) {
            parseReachable(it.next());
        }
        Iterator<ObjectId> it2 = set.iterator();
        while (it2.hasNext()) {
            parseReachable(it2.next());
        }
        if (i <= 0) {
            return;
        }
        Date date = new Date(((long) i) * 1000);
        this.walk.sort(RevSort.COMMIT_TIME_DESC);
        this.walk.markStart(this.reachableCommits);
        this.walk.setRevFilter(CommitTimeRevFilter.after(date));
        while (true) {
            RevCommit next = this.walk.next();
            if (next == null) {
                return;
            }
            if (next.has(this.ADVERTISED) && !next.has(this.COMMON)) {
                next.add(this.COMMON);
                next.carry(this.COMMON);
                this.reachableCommits.add(next);
            }
        }
    }

    private void parseReachable(ObjectId objectId) {
        try {
            RevCommit commit = this.walk.parseCommit(objectId);
            if (commit.has(this.REACHABLE)) {
                return;
            }
            commit.add(this.REACHABLE);
            this.reachableCommits.add(commit);
        } catch (IOException unused) {
        }
    }

    private boolean sendWants(Collection<Ref> collection, PacketLineOut packetLineOut) throws IOException {
        Iterator<Ref> it = collection.iterator();
        boolean z = true;
        while (it.hasNext()) {
            ObjectId objectId = it.next().getObjectId();
            if (objectId != null) {
                try {
                    if (!this.walk.parseAny(objectId).has(this.REACHABLE)) {
                        StringBuilder sb = new StringBuilder(46);
                        sb.append("want ");
                        sb.append(objectId.name());
                        if (z && TransferConfig.ProtocolVersion.V0.equals(getProtocolVersion())) {
                            sb.append(enableCapabilities());
                        }
                        sb.append('\n');
                        packetLineOut.writeString(sb.toString());
                        z = false;
                    }
                } catch (IOException unused) {
                }
            }
        }
        if (z) {
            return false;
        }
        if (!this.filterSpec.isNoOp()) {
            packetLineOut.writeString(this.filterSpec.filterLine());
        }
        return true;
    }

    private Set<String> getCapabilitiesV2(Set<String> set) throws TransportException {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (this.noProgress) {
            linkedHashSet.add("no-progress");
        }
        if (this.includeTags) {
            linkedHashSet.add("include-tag");
        }
        if (this.allowOfsDelta) {
            linkedHashSet.add("ofs-delta");
        }
        if (this.thinPack) {
            linkedHashSet.add("thin-pack");
        }
        if (this.filterSpec.isNoOp() || set.contains("filter")) {
            return linkedHashSet;
        }
        throw new PackProtocolException(this.uri, JGitText.get().filterRequiresCapability);
    }

    private String enableCapabilities() throws TransportException {
        StringBuilder sb = new StringBuilder();
        if (this.noProgress) {
            wantCapability(sb, "no-progress");
        }
        if (this.includeTags) {
            this.includeTags = wantCapability(sb, "include-tag");
        }
        if (this.allowOfsDelta) {
            wantCapability(sb, "ofs-delta");
        }
        if (wantCapability(sb, "multi_ack_detailed")) {
            this.multiAck = GitProtocolConstants.MultiAck.DETAILED;
            if (this.statelessRPC) {
                this.noDone = wantCapability(sb, "no-done");
            }
        } else if (wantCapability(sb, "multi_ack")) {
            this.multiAck = GitProtocolConstants.MultiAck.CONTINUE;
        } else {
            this.multiAck = GitProtocolConstants.MultiAck.OFF;
        }
        if (this.thinPack) {
            this.thinPack = wantCapability(sb, "thin-pack");
        }
        if (wantCapability(sb, "side-band-64k") || wantCapability(sb, "side-band")) {
            this.sideband = true;
        }
        if (this.statelessRPC && this.multiAck != GitProtocolConstants.MultiAck.DETAILED) {
            throw new PackProtocolException(this.uri, MessageFormat.format(JGitText.get().statelessRPCRequiresOptionToBeEnabled, "multi_ack_detailed"));
        }
        if (!this.filterSpec.isNoOp() && !wantCapability(sb, "filter")) {
            throw new PackProtocolException(this.uri, JGitText.get().filterRequiresCapability);
        }
        addUserAgentCapability(sb);
        return sb.toString();
    }

    private void negotiate(ProgressMonitor progressMonitor) throws IOException, CancelledException {
        int i;
        MutableObjectId mutableObjectId = new MutableObjectId();
        if (this.statelessRPC) {
            this.state.writeTo(this.out, null);
        }
        negotiateBegin();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        loop0: while (true) {
            RevCommit next = this.walk.next();
            if (next != null) {
                this.pckOut.writeString("have " + next.getId().name() + "\n");
                i2++;
                i3++;
                if ((i2 & 31) == 0) {
                    if (progressMonitor.isCancelled()) {
                        throw new CancelledException(null);
                    }
                    this.pckOut.end();
                    int i5 = i4 + 1;
                    if (i2 != 32 || this.statelessRPC) {
                        while (true) {
                            PacketLineIn.AckNackResult ack = this.pckIn.readACK(mutableObjectId);
                            int i6 = $SWITCH_TABLE$org$eclipse$jgit$transport$PacketLineIn$AckNackResult()[ack.ordinal()];
                            if (i6 != 1) {
                                if (i6 == 2) {
                                    this.multiAck = GitProtocolConstants.MultiAck.OFF;
                                    if (this.statelessRPC) {
                                        this.state.writeTo(this.out, null);
                                    }
                                    z3 = true;
                                    i = 0;
                                    break loop0;
                                }
                                if (i6 == 3 || i6 == 4 || i6 == 5) {
                                    markCommon(this.walk.parseAny(mutableObjectId), ack, this.statelessRPC);
                                    if (ack == PacketLineIn.AckNackResult.ACK_READY) {
                                        z = true;
                                        z2 = true;
                                    } else {
                                        z2 = true;
                                    }
                                    z3 = z2;
                                    i3 = 0;
                                }
                                if (progressMonitor.isCancelled()) {
                                    throw new CancelledException(null);
                                }
                            } else if (!this.noDone || !z) {
                                if (this.statelessRPC) {
                                    this.state.writeTo(this.out, null);
                                }
                                if ((!z2 || i3 <= 256) && i2 < this.maxHaves) {
                                }
                            }
                        }
                    } else {
                        i4 = i5;
                    }
                }
            }
            i = i4;
            break;
        }
        if (progressMonitor.isCancelled()) {
            throw new CancelledException(null);
        }
        if (!z || !this.noDone) {
            this.pckOut.writeString("done\n");
            this.pckOut.flush();
        }
        if (!z3) {
            this.multiAck = GitProtocolConstants.MultiAck.OFF;
            i++;
        }
        do {
            if (i <= 0 && this.multiAck == GitProtocolConstants.MultiAck.OFF) {
                return;
            }
            i--;
            int i7 = $SWITCH_TABLE$org$eclipse$jgit$transport$PacketLineIn$AckNackResult()[this.pckIn.readACK(mutableObjectId).ordinal()];
            if (i7 == 2) {
                return;
            }
            if (i7 == 3 || i7 == 4 || i7 == 5) {
                this.multiAck = GitProtocolConstants.MultiAck.CONTINUE;
            }
        } while (!progressMonitor.isCancelled());
        throw new CancelledException(null);
    }

    private void negotiateBegin() throws IOException {
        this.walk.resetRetain(this.REACHABLE, this.ADVERTISED);
        this.walk.markStart(this.reachableCommits);
        this.walk.sort(RevSort.COMMIT_TIME_DESC);
        this.walk.setRevFilter(new RevFilter() { // from class: org.eclipse.jgit.transport.BasePackFetchConnection.1
            @Override // org.eclipse.jgit.revwalk.filter.RevFilter
            public RevFilter clone() {
                return this;
            }

            @Override // org.eclipse.jgit.revwalk.filter.RevFilter
            public boolean requiresCommitBody() {
                return false;
            }

            @Override // org.eclipse.jgit.revwalk.filter.RevFilter
            public boolean include(RevWalk revWalk, RevCommit revCommit) {
                boolean zHas = revCommit.has(BasePackFetchConnection.this.COMMON);
                if (revCommit.has(BasePackFetchConnection.this.ADVERTISED)) {
                    revCommit.add(BasePackFetchConnection.this.COMMON);
                }
                return !zHas;
            }
        });
    }

    private void markRefsAdvertised() {
        for (Ref ref : getRefs()) {
            markAdvertised(ref.getObjectId());
            if (ref.getPeeledObjectId() != null) {
                markAdvertised(ref.getPeeledObjectId());
            }
        }
    }

    private void markAdvertised(AnyObjectId anyObjectId) {
        try {
            this.walk.parseAny(anyObjectId).add(this.ADVERTISED);
        } catch (IOException unused) {
        }
    }

    private void markCommon(RevObject revObject, PacketLineIn.AckNackResult ackNackResult, boolean z) throws IOException {
        if (z && ackNackResult == PacketLineIn.AckNackResult.ACK_COMMON && !revObject.has(this.STATE)) {
            this.pckState.writeString("have " + revObject.name() + '\n');
            revObject.add(this.STATE);
        }
        revObject.add(this.COMMON);
        if (revObject instanceof RevCommit) {
            ((RevCommit) revObject).carry(this.COMMON);
        }
    }

    private void receivePack(ProgressMonitor progressMonitor, OutputStream outputStream) throws IOException {
        onReceivePack();
        InputStream sideBandInputStream = this.in;
        if (this.sideband) {
            sideBandInputStream = new SideBandInputStream(sideBandInputStream, progressMonitor, getMessageWriter(), outputStream);
        }
        Throwable th = null;
        try {
            ObjectInserter objectInserterNewObjectInserter = this.local.newObjectInserter();
            try {
                PackParser packParserNewPackParser = objectInserterNewObjectInserter.newPackParser(sideBandInputStream);
                packParserNewPackParser.setAllowThin(this.thinPack);
                packParserNewPackParser.setObjectChecker(this.transport.getObjectChecker());
                packParserNewPackParser.setLockMessage(this.lockMessage);
                this.packLock = packParserNewPackParser.parse(progressMonitor);
                objectInserterNewObjectInserter.flush();
            } finally {
                if (objectInserterNewObjectInserter != null) {
                    objectInserterNewObjectInserter.close();
                }
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    private static class CancelledException extends Exception {
        private static final long serialVersionUID = 1;

        private CancelledException() {
        }

        /* synthetic */ CancelledException(CancelledException cancelledException) {
            this();
        }
    }

    private static class FetchStateV2 {
        boolean hadAcks;
        long havesToSend;
        long havesTotal;
        long havesWithoutAck;

        private FetchStateV2() {
            this.havesToSend = 32L;
        }

        /* synthetic */ FetchStateV2(FetchStateV2 fetchStateV2) {
            this();
        }

        void incHavesToSend(boolean z) {
            if (z) {
                long j = this.havesToSend;
                if (j < 16384) {
                    this.havesToSend = j * 2;
                    return;
                } else {
                    this.havesToSend = (j * 11) / 10;
                    return;
                }
            }
            this.havesToSend += 32;
        }
    }
}
