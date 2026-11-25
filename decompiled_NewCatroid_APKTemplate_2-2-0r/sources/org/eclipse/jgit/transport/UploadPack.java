package org.eclipse.jgit.transport;

import com.google.android.gms.stats.CodePackage;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.CachedPackUriProvider;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.internal.transport.parser.FirstWant;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.AsyncRevObjectQueue;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevFlagSet;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackStatistics;
import org.eclipse.jgit.util.RefMap;
import org.eclipse.jgit.util.io.InterruptTimer;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.eclipse.jgit.util.io.TimeoutInputStream;
import org.eclipse.jgit.util.io.TimeoutOutputStream;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes13.dex */
public class UploadPack {
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$GitProtocolConstants$MultiAck;
    private static volatile /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$UploadPack$RequestPolicy;
    private final RevFlag COMMON;
    private final RevFlag PEER_HAS;
    private final RevFlag SATISFIED;
    private final RevFlagSet SAVE;
    private final RevFlag WANT;
    private boolean advertiseRefsHookCalled;
    private Set<ObjectId> advertised;
    private CachedPackUriProvider cachedPackUriProvider;
    private boolean clientRequestedV2;
    private FetchRequest currentRequest;
    private final Repository db;
    private boolean noDone;
    private Boolean okToGiveUp;
    private int oldestTime;
    private PackConfig packConfig;
    private PacketLineIn pckIn;
    private InputStream rawIn;
    private ResponseBufferedOutputStream rawOut;
    private Map<String, Ref> refs;
    private boolean sentReady;
    private PackStatistics statistics;
    private int timeout;
    private InterruptTimer timer;
    private TransferConfig transferConfig;
    String userAgent;
    private final RevWalk walk;
    private boolean biDirectionalPipe = true;
    private OutputStream msgOut = NullOutputStream.INSTANCE;
    private ErrorWriter errOut = new PackProtocolErrorWriter(this, null);
    private ProtocolV2Hook protocolV2Hook = ProtocolV2Hook.DEFAULT;
    private AdvertiseRefsHook advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
    private RefFilter refFilter = RefFilter.DEFAULT;
    private PreUploadHook preUploadHook = PreUploadHook.NULL;
    private PostUploadHook postUploadHook = PostUploadHook.NULL;
    private Set<ObjectId> wantIds = new HashSet();
    private final Set<RevObject> wantAll = new HashSet();
    private final Set<RevObject> commonBase = new HashSet();
    private RequestValidator requestValidator = new AdvertisedRequestValidator();
    private GitProtocolConstants.MultiAck multiAck = GitProtocolConstants.MultiAck.OFF;

    public static final class AnyRequestValidator implements RequestValidator {
        @Override // org.eclipse.jgit.transport.UploadPack.RequestValidator
        public void checkWants(UploadPack uploadPack, List<ObjectId> list) throws IOException {
        }
    }

    private interface ErrorWriter {
        void writeError(String str) throws IOException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @FunctionalInterface
    interface IOConsumer<R> {
        void accept(R r) throws IOException;
    }

    private enum Option {
        WAIT_FOR_DONE,
        NONE;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Option[] valuesCustom() {
            Option[] optionArrValuesCustom = values();
            int length = optionArrValuesCustom.length;
            Option[] optionArr = new Option[length];
            System.arraycopy(optionArrValuesCustom, 0, optionArr, 0, length);
            return optionArr;
        }
    }

    public enum RequestPolicy {
        ADVERTISED,
        REACHABLE_COMMIT,
        TIP,
        REACHABLE_COMMIT_TIP,
        ANY;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static RequestPolicy[] valuesCustom() {
            RequestPolicy[] requestPolicyArrValuesCustom = values();
            int length = requestPolicyArrValuesCustom.length;
            RequestPolicy[] requestPolicyArr = new RequestPolicy[length];
            System.arraycopy(requestPolicyArrValuesCustom, 0, requestPolicyArr, 0, length);
            return requestPolicyArr;
        }
    }

    public interface RequestValidator {
        void checkWants(UploadPack uploadPack, List<ObjectId> list) throws IOException;
    }

    static /* synthetic */ Ref lambda$0(Ref ref, Ref ref2) {
        return ref2;
    }

    static /* synthetic */ Ref lambda$1(Ref ref, Ref ref2) {
        return ref2;
    }

    static /* synthetic */ Ref lambda$4(Ref ref, Ref ref2) {
        return ref2;
    }

    static /* synthetic */ Ref lambda$7(Ref ref, Ref ref2) {
        return ref2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$GitProtocolConstants$MultiAck() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$transport$GitProtocolConstants$MultiAck;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[GitProtocolConstants.MultiAck.valuesCustom().length];
        try {
            iArr2[GitProtocolConstants.MultiAck.CONTINUE.ordinal()] = 2;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[GitProtocolConstants.MultiAck.DETAILED.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[GitProtocolConstants.MultiAck.OFF.ordinal()] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$transport$GitProtocolConstants$MultiAck = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jgit$transport$UploadPack$RequestPolicy() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jgit$transport$UploadPack$RequestPolicy;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[RequestPolicy.valuesCustom().length];
        try {
            iArr2[RequestPolicy.ADVERTISED.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[RequestPolicy.ANY.ordinal()] = 5;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[RequestPolicy.REACHABLE_COMMIT.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[RequestPolicy.REACHABLE_COMMIT_TIP.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[RequestPolicy.TIP.ordinal()] = 3;
        } catch (NoSuchFieldError unused5) {
        }
        $SWITCH_TABLE$org$eclipse$jgit$transport$UploadPack$RequestPolicy = iArr2;
        return iArr2;
    }

    @Deprecated
    public static class FirstLine {
        private final FirstWant firstWant;

        public FirstLine(String str) {
            try {
                this.firstWant = FirstWant.fromLine(str);
            } catch (PackProtocolException e) {
                throw new UncheckedIOException(e);
            }
        }

        public String getLine() {
            return this.firstWant.getLine();
        }

        public Set<String> getOptions() {
            if (this.firstWant.getAgent() != null) {
                HashSet hashSet = new HashSet(this.firstWant.getCapabilities());
                hashSet.add("agent=" + this.firstWant.getAgent());
                return hashSet;
            }
            return this.firstWant.getCapabilities();
        }
    }

    public UploadPack(Repository repository) {
        this.db = repository;
        RevWalk revWalk = new RevWalk(repository);
        this.walk = revWalk;
        revWalk.setRetainBody(false);
        RevFlag revFlagNewFlag = revWalk.newFlag("WANT");
        this.WANT = revFlagNewFlag;
        RevFlag revFlagNewFlag2 = revWalk.newFlag("PEER_HAS");
        this.PEER_HAS = revFlagNewFlag2;
        RevFlag revFlagNewFlag3 = revWalk.newFlag(CodePackage.COMMON);
        this.COMMON = revFlagNewFlag3;
        RevFlag revFlagNewFlag4 = revWalk.newFlag("SATISFIED");
        this.SATISFIED = revFlagNewFlag4;
        revWalk.carry(revFlagNewFlag2);
        RevFlagSet revFlagSet = new RevFlagSet();
        this.SAVE = revFlagSet;
        revFlagSet.add(revFlagNewFlag);
        revFlagSet.add(revFlagNewFlag2);
        revFlagSet.add(revFlagNewFlag3);
        revFlagSet.add(revFlagNewFlag4);
        setTransferConfig(null);
    }

    public final Repository getRepository() {
        return this.db;
    }

    public final RevWalk getRevWalk() {
        return this.walk;
    }

    public final Map<String, Ref> getAdvertisedRefs() {
        return this.refs;
    }

    public void setAdvertisedRefs(@Nullable Map<String, Ref> map) {
        if (map != null) {
            this.refs = map;
        } else {
            this.refs = this.db.getAllRefs();
        }
        if (this.refFilter == RefFilter.DEFAULT) {
            this.refs = this.transferConfig.getRefFilter().filter(this.refs);
        } else {
            this.refs = this.refFilter.filter(this.refs);
        }
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) {
        this.timeout = i;
    }

    public boolean isBiDirectionalPipe() {
        return this.biDirectionalPipe;
    }

    public void setBiDirectionalPipe(boolean z) {
        this.biDirectionalPipe = z;
    }

    public RequestPolicy getRequestPolicy() {
        RequestValidator requestValidator = this.requestValidator;
        if (requestValidator instanceof AdvertisedRequestValidator) {
            return RequestPolicy.ADVERTISED;
        }
        if (requestValidator instanceof ReachableCommitRequestValidator) {
            return RequestPolicy.REACHABLE_COMMIT;
        }
        if (requestValidator instanceof TipRequestValidator) {
            return RequestPolicy.TIP;
        }
        if (requestValidator instanceof ReachableCommitTipRequestValidator) {
            return RequestPolicy.REACHABLE_COMMIT_TIP;
        }
        if (requestValidator instanceof AnyRequestValidator) {
            return RequestPolicy.ANY;
        }
        return null;
    }

    public void setRequestPolicy(RequestPolicy requestPolicy) {
        int i = $SWITCH_TABLE$org$eclipse$jgit$transport$UploadPack$RequestPolicy()[requestPolicy.ordinal()];
        if (i == 2) {
            this.requestValidator = new ReachableCommitRequestValidator();
            return;
        }
        if (i == 3) {
            this.requestValidator = new TipRequestValidator();
            return;
        }
        if (i == 4) {
            this.requestValidator = new ReachableCommitTipRequestValidator();
        } else if (i != 5) {
            this.requestValidator = new AdvertisedRequestValidator();
        } else {
            this.requestValidator = new AnyRequestValidator();
        }
    }

    public void setRequestValidator(@Nullable RequestValidator requestValidator) {
        if (requestValidator == null) {
            requestValidator = new AdvertisedRequestValidator();
        }
        this.requestValidator = requestValidator;
    }

    public AdvertiseRefsHook getAdvertiseRefsHook() {
        return this.advertiseRefsHook;
    }

    public RefFilter getRefFilter() {
        return this.refFilter;
    }

    public void setAdvertiseRefsHook(@Nullable AdvertiseRefsHook advertiseRefsHook) {
        if (advertiseRefsHook == null) {
            advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
        }
        this.advertiseRefsHook = advertiseRefsHook;
    }

    public void setProtocolV2Hook(@Nullable ProtocolV2Hook protocolV2Hook) {
        if (protocolV2Hook == null) {
            protocolV2Hook = ProtocolV2Hook.DEFAULT;
        }
        this.protocolV2Hook = protocolV2Hook;
    }

    public ProtocolV2Hook getProtocolV2Hook() {
        ProtocolV2Hook protocolV2Hook = this.protocolV2Hook;
        return protocolV2Hook != null ? protocolV2Hook : ProtocolV2Hook.DEFAULT;
    }

    public void setRefFilter(@Nullable RefFilter refFilter) {
        if (refFilter == null) {
            refFilter = RefFilter.DEFAULT;
        }
        this.refFilter = refFilter;
    }

    public PreUploadHook getPreUploadHook() {
        return this.preUploadHook;
    }

    public void setPreUploadHook(@Nullable PreUploadHook preUploadHook) {
        if (preUploadHook == null) {
            preUploadHook = PreUploadHook.NULL;
        }
        this.preUploadHook = preUploadHook;
    }

    public PostUploadHook getPostUploadHook() {
        return this.postUploadHook;
    }

    public void setPostUploadHook(@Nullable PostUploadHook postUploadHook) {
        if (postUploadHook == null) {
            postUploadHook = PostUploadHook.NULL;
        }
        this.postUploadHook = postUploadHook;
    }

    public void setPackConfig(@Nullable PackConfig packConfig) {
        this.packConfig = packConfig;
    }

    public void setTransferConfig(@Nullable TransferConfig transferConfig) {
        if (transferConfig == null) {
            transferConfig = new TransferConfig(this.db);
        }
        this.transferConfig = transferConfig;
        if (transferConfig.isAllowTipSha1InWant()) {
            setRequestPolicy(this.transferConfig.isAllowReachableSha1InWant() ? RequestPolicy.REACHABLE_COMMIT_TIP : RequestPolicy.TIP);
        } else {
            setRequestPolicy(this.transferConfig.isAllowReachableSha1InWant() ? RequestPolicy.REACHABLE_COMMIT : RequestPolicy.ADVERTISED);
        }
    }

    public boolean isSideBand() throws RequestNotYetReadException {
        FetchRequest fetchRequest = this.currentRequest;
        if (fetchRequest == null) {
            throw new RequestNotYetReadException();
        }
        Set<String> clientCapabilities = fetchRequest.getClientCapabilities();
        return clientCapabilities.contains("side-band") || clientCapabilities.contains("side-band-64k");
    }

    public void setExtraParameters(Collection<String> collection) {
        this.clientRequestedV2 = collection.contains(GitProtocolConstants.VERSION_2_REQUEST);
    }

    public void setCachedPackUriProvider(@Nullable CachedPackUriProvider cachedPackUriProvider) {
        this.cachedPackUriProvider = cachedPackUriProvider;
    }

    private boolean useProtocolV2() {
        return (this.transferConfig.protocolVersion == null || TransferConfig.ProtocolVersion.V2.equals(this.transferConfig.protocolVersion)) && this.clientRequestedV2;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x000d  */
    /* JADX WARN: Code duplicated, block: B:13:0x0011  */
    /* JADX WARN: Code duplicated, block: B:14:0x0016  */
    /* JADX WARN: Code duplicated, block: B:21:0x002c  */
    public void upload(InputStream inputStream, OutputStream outputStream, @Nullable OutputStream outputStream2) throws IOException {
        String message;
        try {
            uploadWithExceptionPropagation(inputStream, outputStream, outputStream2);
        } catch (IOException e) {
            e = e;
            if (this.rawOut != null) {
                if (e instanceof PackProtocolException) {
                    message = e.getMessage();
                } else {
                    message = JGitText.get().internalServerError;
                }
                try {
                    this.errOut.writeError(message);
                    throw new UploadPackInternalServerErrorException(e);
                } catch (IOException e2) {
                    e.addSuppressed(e2);
                    throw e;
                }
            }
            throw e;
        } catch (Error e3) {
            e = e3;
            if (this.rawOut != null) {
                if (e instanceof PackProtocolException) {
                    message = e.getMessage();
                } else {
                    message = JGitText.get().internalServerError;
                }
                this.errOut.writeError(message);
                throw new UploadPackInternalServerErrorException(e);
            }
            throw e;
        } catch (RuntimeException e4) {
            e = e4;
            if (this.rawOut != null) {
                if (e instanceof PackProtocolException) {
                    message = e.getMessage();
                } else {
                    message = JGitText.get().internalServerError;
                }
                this.errOut.writeError(message);
                throw new UploadPackInternalServerErrorException(e);
            }
            throw e;
        } catch (ServiceMayNotContinueException e5) {
            if (!e5.isOutput() && e5.getMessage() != null) {
                try {
                    this.errOut.writeError(e5.getMessage());
                    e5.setOutput();
                } catch (IOException e6) {
                    e5.addSuppressed(e6);
                    throw e5;
                }
            }
            throw e5;
        }
    }

    public void uploadWithExceptionPropagation(InputStream inputStream, OutputStream outputStream, @Nullable OutputStream outputStream2) throws IOException {
        try {
            this.rawIn = inputStream;
            if (outputStream2 != null) {
                this.msgOut = outputStream2;
            }
            if (this.timeout > 0) {
                this.timer = new InterruptTimer(String.valueOf(Thread.currentThread().getName()) + "-Timer");
                TimeoutInputStream timeoutInputStream = new TimeoutInputStream(this.rawIn, this.timer);
                TimeoutOutputStream timeoutOutputStream = new TimeoutOutputStream(outputStream, this.timer);
                timeoutInputStream.setTimeout(this.timeout * 1000);
                timeoutOutputStream.setTimeout(this.timeout * 1000);
                this.rawIn = timeoutInputStream;
                outputStream = timeoutOutputStream;
            }
            ResponseBufferedOutputStream responseBufferedOutputStream = new ResponseBufferedOutputStream(outputStream);
            this.rawOut = responseBufferedOutputStream;
            if (this.biDirectionalPipe) {
                responseBufferedOutputStream.stopBuffering();
            }
            this.pckIn = new PacketLineIn(this.rawIn);
            PacketLineOut packetLineOut = new PacketLineOut(this.rawOut);
            if (useProtocolV2()) {
                serviceV2(packetLineOut);
            } else {
                service(packetLineOut);
            }
            this.msgOut = NullOutputStream.INSTANCE;
            this.walk.close();
            InterruptTimer interruptTimer = this.timer;
            if (interruptTimer != null) {
                try {
                    interruptTimer.terminate();
                } finally {
                    this.timer = null;
                }
            }
        } catch (Throwable th) {
            this.msgOut = NullOutputStream.INSTANCE;
            this.walk.close();
            InterruptTimer interruptTimer2 = this.timer;
            if (interruptTimer2 != null) {
                try {
                    interruptTimer2.terminate();
                } finally {
                    this.timer = null;
                }
            }
            throw th;
        }
    }

    public PackStatistics getStatistics() {
        return this.statistics;
    }

    private Map<String, Ref> getAdvertisedOrDefaultRefs() throws IOException {
        Map<String, Ref> map = this.refs;
        if (map != null) {
            return map;
        }
        if (!this.advertiseRefsHookCalled) {
            this.advertiseRefsHook.advertiseRefs(this);
            this.advertiseRefsHookCalled = true;
        }
        if (this.refs == null) {
            setAdvertisedRefs((Map) this.db.getRefDatabase().getRefs().stream().collect(RefMap.toRefMap(new BinaryOperator() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda16
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return UploadPack.lambda$0((Ref) obj, (Ref) obj2);
                }
            })));
        }
        return this.refs;
    }

    private Map<String, Ref> getFilteredRefs(final Collection<String> collection) throws IOException {
        if (collection.isEmpty()) {
            return getAdvertisedOrDefaultRefs();
        }
        if (this.refs == null && !this.advertiseRefsHookCalled) {
            this.advertiseRefsHook.advertiseRefs(this);
            this.advertiseRefsHookCalled = true;
        }
        Map<String, Ref> map = this.refs;
        if (map == null) {
            Map<String, Ref> map2 = (Map) this.db.getRefDatabase().getRefsByPrefix((String[]) collection.toArray(new String[0])).stream().collect(RefMap.toRefMap(new BinaryOperator() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return UploadPack.lambda$1((Ref) obj, (Ref) obj2);
                }
            }));
            if (this.refFilter != RefFilter.DEFAULT) {
                return this.refFilter.filter(map2);
            }
            return this.transferConfig.getRefFilter().filter(map2);
        }
        return (Map) map.values().stream().filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UploadPack.lambda$2(collection, (Ref) obj);
            }
        }).collect(RefMap.toRefMap(new BinaryOperator() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda15
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return UploadPack.lambda$4((Ref) obj, (Ref) obj2);
            }
        }));
    }

    static /* synthetic */ boolean lambda$2(Collection collection, Ref ref) {
        Stream stream = collection.stream();
        final String name2 = ref.getName();
        name2.getClass();
        return stream.anyMatch(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return name2.startsWith((String) obj);
            }
        });
    }

    private static Map<String, Ref> mapRefs(final Map<String, Ref> map, List<String> list) {
        Stream<String> stream = list.stream();
        map.getClass();
        return Collections.unmodifiableMap((Map) stream.map(new Function() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Ref) map.get((String) obj);
            }
        }).filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((Ref) obj);
            }
        }).collect(RefMap.toRefMap(new BinaryOperator() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda21
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return UploadPack.lambda$7((Ref) obj, (Ref) obj2);
            }
        })));
    }

    private Map<String, Ref> exactRefs(List<String> list) throws IOException {
        Map<String, Ref> map = this.refs;
        if (map != null) {
            return mapRefs(map, list);
        }
        if (!this.advertiseRefsHookCalled) {
            this.advertiseRefsHook.advertiseRefs(this);
            this.advertiseRefsHookCalled = true;
        }
        if (this.refs == null && this.refFilter == RefFilter.DEFAULT && this.transferConfig.hasDefaultRefFilter()) {
            return Collections.unmodifiableMap(this.db.getRefDatabase().exactRef((String[]) list.toArray(new String[0])));
        }
        return mapRefs(getAdvertisedOrDefaultRefs(), list);
    }

    @Nullable
    private Ref findRef(String str) throws IOException {
        Map<String, Ref> map = this.refs;
        if (map != null) {
            return RefDatabase.findRef(map, str);
        }
        if (!this.advertiseRefsHookCalled) {
            this.advertiseRefsHook.advertiseRefs(this);
            this.advertiseRefsHookCalled = true;
        }
        if (this.refs == null && this.refFilter == RefFilter.DEFAULT && this.transferConfig.hasDefaultRefFilter()) {
            return this.db.getRefDatabase().findRef(str);
        }
        return RefDatabase.findRef(getAdvertisedOrDefaultRefs(), str);
    }

    private void service(final PacketLineOut packetLineOut) throws Throwable {
        int i;
        PackStatistics.Accumulator accumulator = new PackStatistics.Accumulator();
        final ArrayList arrayList = new ArrayList();
        boolean z = false;
        try {
            if (this.biDirectionalPipe) {
                sendAdvertisedRefs(new RefAdvertiser.PacketLineOutRefAdvertiser(packetLineOut));
            } else if (this.requestValidator instanceof AnyRequestValidator) {
                this.advertised = Collections.emptySet();
            } else {
                this.advertised = refIdSet(getAdvertisedOrDefaultRefs().values());
            }
            Instant instantNow = Instant.now();
            accumulator.advertised = this.advertised.size();
            FetchV0Request fetchV0RequestRecvWants = new ProtocolV0Parser(this.transferConfig).recvWants(this.pckIn);
            this.currentRequest = fetchV0RequestRecvWants;
            this.wantIds = fetchV0RequestRecvWants.getWantIds();
            if (fetchV0RequestRecvWants.getWantIds().isEmpty()) {
                this.preUploadHook.onBeginNegotiateRound(this, fetchV0RequestRecvWants.getWantIds(), 0);
                this.preUploadHook.onEndNegotiateRound(this, fetchV0RequestRecvWants.getWantIds(), 0, 0, false);
                if (!this.biDirectionalPipe) {
                    while (true) {
                        if (0 >= this.rawIn.skip(2048L) && this.rawIn.read() < 0) {
                            break;
                        }
                    }
                }
                this.rawOut.stopBuffering();
                return;
            }
            accumulator.wants = fetchV0RequestRecvWants.getWantIds().size();
            if (fetchV0RequestRecvWants.getClientCapabilities().contains("multi_ack_detailed")) {
                this.multiAck = GitProtocolConstants.MultiAck.DETAILED;
                this.noDone = fetchV0RequestRecvWants.getClientCapabilities().contains("no-done");
            } else if (fetchV0RequestRecvWants.getClientCapabilities().contains("multi_ack")) {
                this.multiAck = GitProtocolConstants.MultiAck.CONTINUE;
            } else {
                this.multiAck = GitProtocolConstants.MultiAck.OFF;
            }
            if (!fetchV0RequestRecvWants.getClientShallowCommits().isEmpty()) {
                verifyClientShallow(fetchV0RequestRecvWants.getClientShallowCommits());
            }
            if (fetchV0RequestRecvWants.getDepth() != 0 || fetchV0RequestRecvWants.getDeepenSince() != 0) {
                computeShallowsAndUnshallows(fetchV0RequestRecvWants, new IOConsumer() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda13
                    @Override // org.eclipse.jgit.transport.UploadPack.IOConsumer
                    public final void accept(Object obj) throws IOException {
                        packetLineOut.writeString("shallow " + ((ObjectId) obj).name() + '\n');
                    }
                }, new IOConsumer() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda14
                    @Override // org.eclipse.jgit.transport.UploadPack.IOConsumer
                    public final void accept(Object obj) throws IOException {
                        UploadPack.lambda$9(packetLineOut, arrayList, (ObjectId) obj);
                    }
                }, Collections.emptyList());
                packetLineOut.end();
            }
            if (!fetchV0RequestRecvWants.getClientShallowCommits().isEmpty()) {
                this.walk.assumeShallow(fetchV0RequestRecvWants.getClientShallowCommits());
            }
            boolean zNegotiate = negotiate(fetchV0RequestRecvWants, accumulator, packetLineOut);
            try {
                accumulator.timeNegotiating = Duration.between(instantNow, Instant.now()).toMillis();
                if (zNegotiate && !this.biDirectionalPipe && (i = this.rawIn.read()) >= 0) {
                    throw new CorruptObjectException(MessageFormat.format(JGitText.get().expectedEOFReceived, "\\x" + Integer.toHexString(i)));
                }
                if (!zNegotiate && !this.biDirectionalPipe) {
                    while (true) {
                        if (0 >= this.rawIn.skip(2048L) && this.rawIn.read() < 0) {
                            break;
                        }
                    }
                }
                this.rawOut.stopBuffering();
                if (zNegotiate) {
                    Map<String, Ref> map = this.refs;
                    sendPack(accumulator, fetchV0RequestRecvWants, map == null ? null : map.values(), arrayList, Collections.emptyList(), packetLineOut);
                }
            } catch (Throwable th) {
                th = th;
                z = zNegotiate;
                if (!z && !this.biDirectionalPipe) {
                    while (true) {
                        if (0 >= this.rawIn.skip(2048L) && this.rawIn.read() < 0) {
                            break;
                        }
                    }
                }
                this.rawOut.stopBuffering();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    static /* synthetic */ void lambda$9(PacketLineOut packetLineOut, List list, ObjectId objectId) throws IOException {
        packetLineOut.writeString("unshallow " + objectId.name() + '\n');
        list.add(objectId);
    }

    private void lsRefsV2(PacketLineOut packetLineOut) throws IOException {
        LsRefsV2Request lsRefsRequest = new ProtocolV2Parser(this.transferConfig).parseLsRefsRequest(this.pckIn);
        this.protocolV2Hook.onLsRefs(lsRefsRequest);
        this.rawOut.stopBuffering();
        RefAdvertiser.PacketLineOutRefAdvertiser packetLineOutRefAdvertiser = new RefAdvertiser.PacketLineOutRefAdvertiser(packetLineOut);
        packetLineOutRefAdvertiser.init(this.db);
        packetLineOutRefAdvertiser.setUseProtocolV2(true);
        if (lsRefsRequest.getPeel()) {
            packetLineOutRefAdvertiser.setDerefTags(true);
        }
        Map<String, Ref> filteredRefs = getFilteredRefs(lsRefsRequest.getRefPrefixes());
        if (lsRefsRequest.getSymrefs()) {
            findSymrefs(packetLineOutRefAdvertiser, filteredRefs);
        }
        packetLineOutRefAdvertiser.send(filteredRefs.values());
        packetLineOutRefAdvertiser.end();
    }

    private Map<String, ObjectId> wantedRefs(FetchV2Request fetchV2Request) throws IOException {
        TreeMap treeMap = new TreeMap();
        List<String> wantedRefs = fetchV2Request.getWantedRefs();
        Map<String, Ref> mapExactRefs = exactRefs(wantedRefs);
        for (String str : wantedRefs) {
            Ref ref = mapExactRefs.get(str);
            if (ref == null) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidRefName, str));
            }
            ObjectId objectId = ref.getObjectId();
            if (objectId == null) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidRefName, str));
            }
            treeMap.put(str, objectId);
        }
        return treeMap;
    }

    private void fetchV2(PacketLineOut packetLineOut) throws IOException {
        RequestValidator requestValidator = this.requestValidator;
        if ((requestValidator instanceof TipRequestValidator) || (requestValidator instanceof ReachableCommitTipRequestValidator) || (requestValidator instanceof AnyRequestValidator)) {
            this.advertised = Collections.emptySet();
        } else {
            this.advertised = refIdSet(getAdvertisedOrDefaultRefs().values());
        }
        PackStatistics.Accumulator accumulator = new PackStatistics.Accumulator();
        Instant instantNow = Instant.now();
        FetchV2Request fetchRequest = new ProtocolV2Parser(this.transferConfig).parseFetchRequest(this.pckIn);
        this.currentRequest = fetchRequest;
        this.rawOut.stopBuffering();
        this.protocolV2Hook.onFetch(fetchRequest);
        boolean z = true;
        if (fetchRequest.getSidebandAll()) {
            packetLineOut.setUsingSideband(true);
        }
        ArrayList arrayList = new ArrayList();
        for (String str : fetchRequest.getDeepenNotRefs()) {
            Ref refFindRef = findRef(str);
            if (refFindRef == null) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidRefName, str));
            }
            arrayList.add(refFindRef.getObjectId());
        }
        Map<String, ObjectId> mapWantedRefs = wantedRefs(fetchRequest);
        fetchRequest.getWantIds().addAll(mapWantedRefs.values());
        this.wantIds = fetchRequest.getWantIds();
        boolean z2 = false;
        boolean z3 = (fetchRequest.getDepth() == 0 && fetchRequest.getDeepenSince() == 0 && fetchRequest.getDeepenNotRefs().isEmpty()) ? false : true;
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        if (!fetchRequest.getClientShallowCommits().isEmpty()) {
            verifyClientShallow(fetchRequest.getClientShallowCommits());
        }
        if (z3) {
            computeShallowsAndUnshallows(fetchRequest, new IOConsumer() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda17
                @Override // org.eclipse.jgit.transport.UploadPack.IOConsumer
                public final void accept(Object obj) {
                    arrayList2.add((ObjectId) obj);
                }
            }, new IOConsumer() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda18
                @Override // org.eclipse.jgit.transport.UploadPack.IOConsumer
                public final void accept(Object obj) {
                    arrayList3.add((ObjectId) obj);
                }
            }, arrayList);
        }
        if (!fetchRequest.getClientShallowCommits().isEmpty()) {
            this.walk.assumeShallow(fetchRequest.getClientShallowCommits());
        }
        if (fetchRequest.wasDoneReceived()) {
            processHaveLines(fetchRequest.getPeerHas(), ObjectId.zeroId(), new PacketLineOut(NullOutputStream.INSTANCE, false), accumulator, fetchRequest.wasWaitForDoneReceived() ? Option.WAIT_FOR_DONE : Option.NONE);
        } else {
            packetLineOut.writeString("acknowledgments\n");
            for (ObjectId objectId : fetchRequest.getPeerHas()) {
                if (this.walk.getObjectReader().has(objectId)) {
                    packetLineOut.writeString("ACK " + objectId.getName() + "\n");
                }
            }
            processHaveLines(fetchRequest.getPeerHas(), ObjectId.zeroId(), new PacketLineOut(NullOutputStream.INSTANCE, false), accumulator, Option.NONE);
            if (!fetchRequest.wasWaitForDoneReceived() && okToGiveUp()) {
                packetLineOut.writeString("ready\n");
            } else if (this.commonBase.isEmpty()) {
                packetLineOut.writeString("NAK\n");
            }
            z2 = true;
        }
        if (fetchRequest.wasDoneReceived() || (!fetchRequest.wasWaitForDoneReceived() && okToGiveUp())) {
            if (z3) {
                if (z2) {
                    packetLineOut.writeDelim();
                }
                packetLineOut.writeString("shallow-info\n");
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    packetLineOut.writeString("shallow " + ((ObjectId) it.next()).getName() + '\n');
                }
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    packetLineOut.writeString("unshallow " + ((ObjectId) it2.next()).getName() + '\n');
                }
                z2 = true;
            }
            if (mapWantedRefs.isEmpty()) {
                z = z2;
            } else {
                if (z2) {
                    packetLineOut.writeDelim();
                }
                packetLineOut.writeString("wanted-refs\n");
                for (Map.Entry<String, ObjectId> entry : mapWantedRefs.entrySet()) {
                    packetLineOut.writeString(String.valueOf(entry.getValue().getName()) + StringUtil.SPACE + entry.getKey() + '\n');
                }
            }
            if (z) {
                packetLineOut.writeDelim();
            }
            if (!packetLineOut.isUsingSideband()) {
                packetLineOut.writeString("packfile\n");
            }
            accumulator.timeNegotiating = Duration.between(instantNow, Instant.now()).toMillis();
            sendPack(accumulator, fetchRequest, fetchRequest.getClientCapabilities().contains("include-tag") ? this.db.getRefDatabase().getRefsByPrefix(Constants.R_TAGS) : null, arrayList3, arrayList, packetLineOut);
            return;
        }
        packetLineOut.end();
    }

    private void objectInfo(PacketLineOut packetLineOut) throws IOException {
        ObjectInfoRequest objectInfoRequest = new ProtocolV2Parser(this.transferConfig).parseObjectInfoRequest(this.pckIn);
        this.protocolV2Hook.onObjectInfo(objectInfoRequest);
        ObjectReader objectReaderNewObjectReader = getRepository().newObjectReader();
        packetLineOut.writeString("size");
        for (ObjectId objectId : objectInfoRequest.getObjectIDs()) {
            try {
                packetLineOut.writeString(String.valueOf(objectId.getName()) + " " + objectReaderNewObjectReader.getObjectSize(objectId, -1));
            } catch (MissingObjectException e) {
                throw new PackProtocolException(MessageFormat.format(JGitText.get().missingObject, objectId.name()), e);
            }
        }
        packetLineOut.end();
    }

    private boolean serveOneCommandV2(PacketLineOut packetLineOut) throws IOException {
        try {
            String string = this.pckIn.readString();
            if (PacketLineIn.isEnd(string)) {
                return true;
            }
            if (string.equals("command=ls-refs")) {
                lsRefsV2(packetLineOut);
                return false;
            }
            if (string.equals("command=fetch")) {
                fetchV2(packetLineOut);
                return false;
            }
            if (string.equals("command=object-info")) {
                objectInfo(packetLineOut);
                return false;
            }
            throw new PackProtocolException(MessageFormat.format(JGitText.get().unknownTransportCommand, string));
        } catch (EOFException unused) {
            return true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x002a  */
    private List<String> getV2CapabilityAdvertisement() {
        boolean z;
        ArrayList arrayList = new ArrayList();
        arrayList.add(GitProtocolConstants.VERSION_2);
        arrayList.add(GitProtocolConstants.COMMAND_LS_REFS);
        if (this.transferConfig.isAllowRefInWant()) {
            z = this.db.getConfig().getBoolean("uploadpack", null, "advertiserefinwant", true);
        }
        arrayList.add("fetch=" + (this.transferConfig.isAllowFilter() ? "filter " : "") + (z ? "ref-in-want " : "") + (this.transferConfig.isAdvertiseSidebandAll() ? "sideband-all " : "") + (this.cachedPackUriProvider != null ? "packfile-uris " : "") + (this.transferConfig.isAdvertiseWaitForDone() ? "wait-for-done " : "") + "shallow");
        arrayList.add("server-option");
        return arrayList;
    }

    private void serviceV2(PacketLineOut packetLineOut) throws IOException {
        if (this.biDirectionalPipe) {
            this.protocolV2Hook.onCapabilities(CapabilitiesV2Request.builder().build());
            Iterator<String> it = getV2CapabilityAdvertisement().iterator();
            while (it.hasNext()) {
                packetLineOut.writeString(String.valueOf(it.next()) + "\n");
            }
            packetLineOut.end();
            while (!serveOneCommandV2(packetLineOut)) {
            }
            return;
        }
        try {
            serveOneCommandV2(packetLineOut);
            while (true) {
                if (0 >= this.rawIn.skip(2048L) && this.rawIn.read() < 0) {
                    return;
                }
            }
        } finally {
            while (true) {
                if (0 >= this.rawIn.skip(2048L) && this.rawIn.read() < 0) {
                    this.rawOut.stopBuffering();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Set<ObjectId> refIdSet(Collection<Ref> collection) {
        HashSet hashSet = new HashSet(collection.size());
        for (Ref ref : collection) {
            ObjectId objectId = ref.getObjectId();
            if (objectId != null) {
                hashSet.add(objectId);
            }
            ObjectId peeledObjectId = ref.getPeeledObjectId();
            if (peeledObjectId != null) {
                hashSet.add(peeledObjectId);
            }
        }
        return hashSet;
    }

    private void computeShallowsAndUnshallows(FetchRequest fetchRequest, IOConsumer<ObjectId> iOConsumer, IOConsumer<ObjectId> iOConsumer2, List<ObjectId> list) throws IOException {
        if (fetchRequest.getClientCapabilities().contains(GitProtocolConstants.OPTION_DEEPEN_RELATIVE)) {
            throw new UnsupportedOperationException();
        }
        int depth = fetchRequest.getDepth() == 0 ? Integer.MAX_VALUE : fetchRequest.getDepth() - 1;
        Throwable th = null;
        try {
            DepthWalk.RevWalk revWalk = new DepthWalk.RevWalk(this.walk.getObjectReader(), depth);
            try {
                revWalk.setDeepenSince(fetchRequest.getDeepenSince());
                Iterator<ObjectId> it = fetchRequest.getWantIds().iterator();
                while (it.hasNext()) {
                    try {
                        revWalk.markRoot(revWalk.parseCommit(it.next()));
                    } catch (IncorrectObjectTypeException unused) {
                    }
                }
                revWalk.setDeepenNots(list);
                boolean z = false;
                while (true) {
                    RevCommit next = revWalk.next();
                    if (next == null) {
                        break;
                    }
                    DepthWalk.Commit commit = (DepthWalk.Commit) next;
                    boolean z2 = commit.getDepth() == depth || commit.isBoundary();
                    if (z2 && !fetchRequest.getClientShallowCommits().contains(commit)) {
                        iOConsumer.accept(commit.copy());
                    }
                    if (!z2 && fetchRequest.getClientShallowCommits().remove(commit)) {
                        iOConsumer2.accept(commit.copy());
                    }
                    z = true;
                }
                if (z) {
                } else {
                    throw new PackProtocolException(JGitText.get().noCommitsSelectedForShallow);
                }
            } finally {
                revWalk.close();
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

    private void verifyClientShallow(Set<ObjectId> set) throws IOException {
        AsyncRevObjectQueue any = this.walk.parseAny(set, true);
        while (true) {
            try {
                try {
                    RevObject next = any.next();
                    if (next != null) {
                        if (!(next instanceof RevCommit)) {
                            throw new PackProtocolException(MessageFormat.format(JGitText.get().invalidShallowObject, next.name()));
                        }
                    } else {
                        any.release();
                        return;
                    }
                } catch (MissingObjectException e) {
                    set.remove(e.getObjectId());
                }
            } catch (Throwable th) {
                any.release();
                throw th;
            }
        }
    }

    public void sendAdvertisedRefs(RefAdvertiser refAdvertiser) throws IOException {
        sendAdvertisedRefs(refAdvertiser, null);
    }

    public void sendAdvertisedRefs(RefAdvertiser refAdvertiser, @Nullable String str) throws IOException {
        if (useProtocolV2()) {
            this.protocolV2Hook.onCapabilities(CapabilitiesV2Request.builder().build());
            Iterator<String> it = getV2CapabilityAdvertisement().iterator();
            while (it.hasNext()) {
                refAdvertiser.writeOne(it.next());
            }
            refAdvertiser.end();
            return;
        }
        Map<String, Ref> advertisedOrDefaultRefs = getAdvertisedOrDefaultRefs();
        if (str != null) {
            refAdvertiser.writeOne("# service=" + str + '\n');
            refAdvertiser.end();
        }
        refAdvertiser.init(this.db);
        refAdvertiser.advertiseCapability("include-tag");
        refAdvertiser.advertiseCapability("multi_ack_detailed");
        refAdvertiser.advertiseCapability("multi_ack");
        refAdvertiser.advertiseCapability("ofs-delta");
        refAdvertiser.advertiseCapability("side-band");
        refAdvertiser.advertiseCapability("side-band-64k");
        refAdvertiser.advertiseCapability("thin-pack");
        refAdvertiser.advertiseCapability("no-progress");
        refAdvertiser.advertiseCapability("shallow");
        if (!this.biDirectionalPipe) {
            refAdvertiser.advertiseCapability("no-done");
        }
        RequestPolicy requestPolicy = getRequestPolicy();
        if (requestPolicy == RequestPolicy.TIP || requestPolicy == RequestPolicy.REACHABLE_COMMIT_TIP || requestPolicy == null) {
            refAdvertiser.advertiseCapability("allow-tip-sha1-in-want");
        }
        if (requestPolicy == RequestPolicy.REACHABLE_COMMIT || requestPolicy == RequestPolicy.REACHABLE_COMMIT_TIP || requestPolicy == null) {
            refAdvertiser.advertiseCapability("allow-reachable-sha1-in-want");
        }
        refAdvertiser.advertiseCapability(GitProtocolConstants.OPTION_AGENT, UserAgent.get());
        if (this.transferConfig.isAllowFilter()) {
            refAdvertiser.advertiseCapability("filter");
        }
        refAdvertiser.setDerefTags(true);
        findSymrefs(refAdvertiser, advertisedOrDefaultRefs);
        this.advertised = refAdvertiser.send(advertisedOrDefaultRefs.values());
        if (refAdvertiser.isEmpty()) {
            refAdvertiser.advertiseId(ObjectId.zeroId(), "capabilities^{}");
        }
        refAdvertiser.end();
    }

    public void sendMessage(String str) {
        try {
            this.msgOut.write(Constants.encode(String.valueOf(str) + "\n"));
        } catch (IOException unused) {
        }
    }

    public OutputStream getMessageOutputStream() {
        return this.msgOut;
    }

    public int getDepth() {
        FetchRequest fetchRequest = this.currentRequest;
        if (fetchRequest == null) {
            throw new RequestNotYetReadException();
        }
        return fetchRequest.getDepth();
    }

    @Deprecated
    public final long getFilterBlobLimit() {
        return getFilterSpec().getBlobLimit();
    }

    public final FilterSpec getFilterSpec() {
        FetchRequest fetchRequest = this.currentRequest;
        if (fetchRequest == null) {
            throw new RequestNotYetReadException();
        }
        return fetchRequest.getFilterSpec();
    }

    public String getPeerUserAgent() {
        FetchRequest fetchRequest = this.currentRequest;
        if (fetchRequest != null && fetchRequest.getAgent() != null) {
            return this.currentRequest.getAgent();
        }
        return this.userAgent;
    }

    private boolean negotiate(FetchRequest fetchRequest, PackStatistics.Accumulator accumulator, PacketLineOut packetLineOut) throws IOException {
        this.okToGiveUp = Boolean.FALSE;
        ObjectId objectIdZeroId = ObjectId.zeroId();
        ArrayList arrayList = new ArrayList(64);
        ObjectId objectIdProcessHaveLines = objectIdZeroId;
        while (true) {
            try {
                String string = this.pckIn.readString();
                if (PacketLineIn.isEnd(string)) {
                    objectIdProcessHaveLines = processHaveLines(arrayList, objectIdProcessHaveLines, packetLineOut, accumulator, Option.NONE);
                    if (this.commonBase.isEmpty() || this.multiAck != GitProtocolConstants.MultiAck.OFF) {
                        packetLineOut.writeString("NAK\n");
                    }
                    if (this.noDone && this.sentReady) {
                        packetLineOut.writeString("ACK " + objectIdProcessHaveLines.name() + "\n");
                        return true;
                    }
                    if (!this.biDirectionalPipe) {
                        return false;
                    }
                    packetLineOut.flush();
                } else if (string.startsWith("have ") && string.length() == 45) {
                    arrayList.add(ObjectId.fromString(string.substring(5)));
                    accumulator.haves++;
                } else {
                    if (string.equals(ES6Iterator.DONE_PROPERTY)) {
                        ObjectId objectIdProcessHaveLines2 = processHaveLines(arrayList, objectIdProcessHaveLines, packetLineOut, accumulator, Option.NONE);
                        if (this.commonBase.isEmpty()) {
                            packetLineOut.writeString("NAK\n");
                        } else if (this.multiAck != GitProtocolConstants.MultiAck.OFF) {
                            packetLineOut.writeString("ACK " + objectIdProcessHaveLines2.name() + "\n");
                        }
                        return true;
                    }
                    throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, "have", string));
                }
            } catch (EOFException e) {
                if (this.biDirectionalPipe || fetchRequest.getDepth() <= 0) {
                    throw e;
                }
                return false;
            }
        }
    }

    private ObjectId processHaveLines(List<ObjectId> list, ObjectId objectId, PacketLineOut packetLineOut, PackStatistics.Accumulator accumulator, Option option) throws IOException {
        this.preUploadHook.onBeginNegotiateRound(this, this.wantIds, list.size());
        if (this.wantAll.isEmpty() && !this.wantIds.isEmpty()) {
            parseWants(accumulator);
        }
        if (list.isEmpty()) {
            return objectId;
        }
        this.sentReady = false;
        this.walk.getObjectReader().setAvoidUnreachableObjects(true);
        AsyncRevObjectQueue any = this.walk.parseAny(list, false);
        int i = 0;
        while (true) {
            try {
                try {
                    RevObject next = any.next();
                    if (next == null) {
                        break;
                    }
                    i++;
                    if (next instanceof RevCommit) {
                        RevCommit revCommit = (RevCommit) next;
                        if (this.oldestTime == 0 || revCommit.getCommitTime() < this.oldestTime) {
                            this.oldestTime = revCommit.getCommitTime();
                        }
                    }
                    if (!next.has(this.PEER_HAS)) {
                        next.add(this.PEER_HAS);
                        if (next instanceof RevCommit) {
                            ((RevCommit) next).carry(this.PEER_HAS);
                        }
                        addCommonBase(next);
                        int i2 = $SWITCH_TABLE$org$eclipse$jgit$transport$GitProtocolConstants$MultiAck()[this.multiAck.ordinal()];
                        if (i2 != 1) {
                            if (i2 == 2) {
                                packetLineOut.writeString("ACK " + next.name() + " continue\n");
                            } else if (i2 == 3) {
                                packetLineOut.writeString("ACK " + next.name() + " common\n");
                            }
                        } else if (this.commonBase.size() == 1) {
                            packetLineOut.writeString("ACK " + next.name() + "\n");
                        }
                    }
                    objectId = next;
                } catch (Throwable th) {
                    any.release();
                    this.walk.getObjectReader().setAvoidUnreachableObjects(false);
                    throw th;
                }
            } catch (MissingObjectException unused) {
            }
        }
        any.release();
        this.walk.getObjectReader().setAvoidUnreachableObjects(false);
        int size = list.size() - i;
        if (option != Option.WAIT_FOR_DONE) {
            this.sentReady = shouldGiveUp(list, packetLineOut, size);
        }
        this.preUploadHook.onEndNegotiateRound(this, this.wantAll, i, size, this.sentReady);
        list.clear();
        return objectId;
    }

    private boolean shouldGiveUp(List<ObjectId> list, PacketLineOut packetLineOut, int i) throws IOException {
        boolean z;
        int i2;
        boolean z2 = false;
        if (i <= 0) {
            z = false;
            break;
        }
        int size = list.size() - 1;
        while (true) {
            if (size < 0) {
                z = false;
                break;
            }
            ObjectId objectId = list.get(size);
            if (this.walk.lookupOrNull(objectId) == null) {
                if (okToGiveUp() && (i2 = $SWITCH_TABLE$org$eclipse$jgit$transport$GitProtocolConstants$MultiAck()[this.multiAck.ordinal()]) != 1) {
                    if (i2 == 2) {
                        packetLineOut.writeString("ACK " + objectId.name() + " continue\n");
                    } else if (i2 == 3) {
                        packetLineOut.writeString("ACK " + objectId.name() + " ready\n");
                        z2 = true;
                    }
                }
                z = z2;
                z2 = true;
                break;
            }
            size--;
        }
        if (this.multiAck != GitProtocolConstants.MultiAck.DETAILED || z2 || !okToGiveUp()) {
            return z;
        }
        packetLineOut.writeString("ACK " + list.get(list.size() - 1).name() + " ready\n");
        return true;
    }

    private void parseWants(PackStatistics.Accumulator accumulator) throws IOException {
        ArrayList arrayList = null;
        for (ObjectId objectId : this.wantIds) {
            if (!this.advertised.contains(objectId)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(objectId);
            }
        }
        if (arrayList != null) {
            accumulator.notAdvertisedWants = arrayList.size();
            Instant instantNow = Instant.now();
            this.requestValidator.checkWants(this, arrayList);
            accumulator.reachabilityCheckDuration = Duration.between(instantNow, Instant.now()).toMillis();
        }
        AsyncRevObjectQueue any = this.walk.parseAny(this.wantIds, true);
        while (true) {
            try {
                try {
                    RevObject next = any.next();
                    if (next != null) {
                        want(next);
                        if (!(next instanceof RevCommit)) {
                            next.add(this.SATISFIED);
                        }
                        if (next instanceof RevTag) {
                            RevObject revObjectPeel = this.walk.peel(next);
                            if (revObjectPeel instanceof RevCommit) {
                                want(revObjectPeel);
                            }
                        }
                    } else {
                        this.wantIds.clear();
                        any.release();
                        return;
                    }
                } catch (MissingObjectException e) {
                    throw new WantNotValidException(e.getObjectId(), e);
                }
            } catch (Throwable th) {
                any.release();
                throw th;
            }
        }
    }

    private void want(RevObject revObject) {
        if (revObject.has(this.WANT)) {
            return;
        }
        revObject.add(this.WANT);
        this.wantAll.add(revObject);
    }

    public static final class AdvertisedRequestValidator implements RequestValidator {
        @Override // org.eclipse.jgit.transport.UploadPack.RequestValidator
        public void checkWants(UploadPack uploadPack, List<ObjectId> list) throws IOException {
            if (!uploadPack.isBiDirectionalPipe()) {
                new ReachableCommitRequestValidator().checkWants(uploadPack, list);
            } else if (!list.isEmpty()) {
                throw new WantNotValidException(list.iterator().next());
            }
        }
    }

    public static final class ReachableCommitRequestValidator implements RequestValidator {
        @Override // org.eclipse.jgit.transport.UploadPack.RequestValidator
        public void checkWants(UploadPack uploadPack, List<ObjectId> list) throws IOException {
            UploadPack.checkNotAdvertisedWants(uploadPack, list, uploadPack.getAdvertisedRefs().values());
        }
    }

    public static final class TipRequestValidator implements RequestValidator {
        @Override // org.eclipse.jgit.transport.UploadPack.RequestValidator
        public void checkWants(UploadPack uploadPack, List<ObjectId> list) throws IOException {
            if (!uploadPack.isBiDirectionalPipe()) {
                new ReachableCommitTipRequestValidator().checkWants(uploadPack, list);
                return;
            }
            if (list.isEmpty()) {
                return;
            }
            Set setRefIdSet = UploadPack.refIdSet(uploadPack.getRepository().getRefDatabase().getRefs());
            for (ObjectId objectId : list) {
                if (!setRefIdSet.contains(objectId)) {
                    throw new WantNotValidException(objectId);
                }
            }
        }
    }

    public static final class ReachableCommitTipRequestValidator implements RequestValidator {
        @Override // org.eclipse.jgit.transport.UploadPack.RequestValidator
        public void checkWants(UploadPack uploadPack, List<ObjectId> list) throws IOException {
            UploadPack.checkNotAdvertisedWants(uploadPack, list, uploadPack.getRepository().getRefDatabase().getRefs());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkNotAdvertisedWants(UploadPack uploadPack, List<ObjectId> list, Collection<Ref> collection) throws IOException {
        ObjectReader objectReader = uploadPack.getRevWalk().getObjectReader();
        Throwable th = null;
        try {
            final RevWalk revWalk = new RevWalk(objectReader);
            try {
                revWalk.setRetainBody(false);
                List<RevObject> listObjectIdsToRevObjects = objectIdsToRevObjects(revWalk, list);
                List list2 = (List) listObjectIdsToRevObjects.stream().filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda22
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return UploadPack.lambda$12((RevObject) obj);
                    }
                }).map(new Function() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return UploadPack.lambda$13((RevObject) obj);
                    }
                }).collect(Collectors.toList());
                boolean z = true;
                boolean z2 = listObjectIdsToRevObjects.size() == list2.size();
                if (objectReader.getBitmapIndex() == null) {
                    z = false;
                }
                if (!z2) {
                    if (!z && !uploadPack.transferConfig.isAllowFilter()) {
                        throw new WantNotValidException((RevObject) ((List) listObjectIdsToRevObjects.stream().filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return UploadPack.lambda$14((RevObject) obj);
                            }
                        }).limit(1L).collect(Collectors.toList())).get(0));
                    }
                    try {
                        final ObjectWalk objectWalkWithSameObjects = revWalk.toObjectWalkWithSameObjects();
                        try {
                            Optional<RevObject> optionalAreAllReachable = objectReader.createObjectReachabilityChecker(objectWalkWithSameObjects).areAllReachable(listObjectIdsToRevObjects, importantRefsFirst(collection).map(new Function() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda3
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return UploadPack.refToObjectId((Ref) obj);
                                }
                            }).map(new Function() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda4
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return UploadPack.objectIdToRevObject(objectWalkWithSameObjects, (ObjectId) obj);
                                }
                            }).filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda5
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return Objects.nonNull((RevObject) obj);
                                }
                            }));
                            if (optionalAreAllReachable.isPresent()) {
                                throw new WantNotValidException(optionalAreAllReachable.get());
                            }
                            if (objectWalkWithSameObjects != null) {
                                objectWalkWithSameObjects.close();
                            }
                            revWalk.close();
                            return;
                        } catch (Throwable th2) {
                            if (objectWalkWithSameObjects == null) {
                                throw th2;
                            }
                            objectWalkWithSameObjects.close();
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        if (0 == 0) {
                            throw th3;
                        }
                        if (null != th3) {
                            th.addSuppressed(th3);
                        }
                        throw null;
                    }
                }
                Optional<RevCommit> optionalAreAllReachable2 = objectReader.createReachabilityChecker(revWalk).areAllReachable(list2, importantRefsFirst(collection).map(new Function() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return UploadPack.refToObjectId((Ref) obj);
                    }
                }).map(new Function() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda6
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return UploadPack.objectIdToRevCommit(revWalk, (ObjectId) obj);
                    }
                }).filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Objects.nonNull((RevCommit) obj);
                    }
                }));
                if (optionalAreAllReachable2.isPresent()) {
                    throw new WantNotValidException(optionalAreAllReachable2.get());
                }
                revWalk.close();
            } catch (Throwable th4) {
                revWalk.close();
                throw th4;
            }
        } catch (Throwable th5) {
            if (0 == 0) {
                throw th5;
            }
            if (null != th5) {
                try {
                    th.addSuppressed(th5);
                } catch (MissingObjectException e) {
                    throw new WantNotValidException(e.getObjectId(), e);
                }
            }
            throw null;
        }
    }

    static /* synthetic */ boolean lambda$12(RevObject revObject) {
        return revObject instanceof RevCommit;
    }

    static /* synthetic */ RevCommit lambda$13(RevObject revObject) {
        return (RevCommit) revObject;
    }

    static /* synthetic */ boolean lambda$14(RevObject revObject) {
        return !(revObject instanceof RevCommit);
    }

    static Stream<Ref> importantRefsFirst(Collection<Ref> collection) {
        final Predicate<? super Ref> predicate = new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Ref) obj).getName().startsWith(Constants.R_HEADS);
            }
        };
        final Predicate<? super Ref> predicate2 = new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Ref) obj).getName().startsWith(Constants.R_TAGS);
            }
        };
        return Stream.concat(collection.stream().filter(predicate), Stream.concat(collection.stream().filter(predicate2), collection.stream().filter(new Predicate() { // from class: org.eclipse.jgit.transport.UploadPack$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UploadPack.lambda$23(predicate, predicate2, (Ref) obj);
            }
        })));
    }

    static /* synthetic */ boolean lambda$23(Predicate predicate, Predicate predicate2, Ref ref) {
        return (predicate.test(ref) || predicate2.test(ref)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ObjectId refToObjectId(Ref ref) {
        return ref.getObjectId() != null ? ref.getObjectId() : ref.getPeeledObjectId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static RevCommit objectIdToRevCommit(RevWalk revWalk, ObjectId objectId) {
        if (objectId == null) {
            return null;
        }
        try {
            return revWalk.parseCommit(objectId);
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static RevObject objectIdToRevObject(RevWalk revWalk, ObjectId objectId) {
        if (objectId == null) {
            return null;
        }
        try {
            return revWalk.parseAny(objectId);
        } catch (IOException unused) {
            return null;
        }
    }

    private static List<RevObject> objectIdsToRevObjects(RevWalk revWalk, Iterable<ObjectId> iterable) throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<ObjectId> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(revWalk.parseAny(it.next()));
        }
        return arrayList;
    }

    private void addCommonBase(RevObject revObject) {
        if (revObject.has(this.COMMON)) {
            return;
        }
        revObject.add(this.COMMON);
        this.commonBase.add(revObject);
        this.okToGiveUp = null;
    }

    private boolean okToGiveUp() throws PackProtocolException {
        if (this.okToGiveUp == null) {
            this.okToGiveUp = Boolean.valueOf(okToGiveUpImp());
        }
        return this.okToGiveUp.booleanValue();
    }

    private boolean okToGiveUpImp() throws PackProtocolException {
        if (this.commonBase.isEmpty()) {
            return false;
        }
        try {
            Iterator<RevObject> it = this.wantAll.iterator();
            while (it.hasNext()) {
                if (!wantSatisfied(it.next())) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            throw new PackProtocolException(JGitText.get().internalRevisionError, e);
        }
    }

    private boolean wantSatisfied(RevObject revObject) throws IOException {
        RevCommit next;
        if (revObject.has(this.SATISFIED)) {
            return true;
        }
        RevCommit revCommit = (RevCommit) revObject;
        if (revCommit.getParentCount() == 0) {
            revObject.add(this.SATISFIED);
            return true;
        }
        this.walk.resetRetain(this.SAVE);
        this.walk.markStart(revCommit);
        int i = this.oldestTime;
        if (i != 0) {
            this.walk.setRevFilter(CommitTimeRevFilter.after(((long) i) * 1000));
        }
        do {
            next = this.walk.next();
            if (next == null) {
                return false;
            }
        } while (!next.has(this.PEER_HAS));
        addCommonBase(next);
        revObject.add(this.SATISFIED);
        return true;
    }

    private void sendPack(PackStatistics.Accumulator accumulator, FetchRequest fetchRequest, @Nullable Collection<Ref> collection, List<ObjectId> list, List<ObjectId> list2, PacketLineOut packetLineOut) throws IOException {
        Set<String> clientCapabilities = fetchRequest.getClientCapabilities();
        if (clientCapabilities.contains("side-band") || clientCapabilities.contains("side-band-64k")) {
            this.errOut = new SideBandErrorWriter(this, null);
            int i = fetchRequest.getClientCapabilities().contains("side-band-64k") ? SideBandOutputStream.MAX_BUF : 1000;
            SideBandOutputStream sideBandOutputStream = new SideBandOutputStream(1, i, this.rawOut);
            ProgressMonitor sideBandProgressMonitor = NullProgressMonitor.INSTANCE;
            if (!fetchRequest.getClientCapabilities().contains("no-progress")) {
                this.msgOut = new SideBandOutputStream(2, i, this.rawOut);
                sideBandProgressMonitor = new SideBandProgressMonitor(this.msgOut);
            }
            sendPack(sideBandProgressMonitor, packetLineOut, sideBandOutputStream, fetchRequest, accumulator, collection, list, list2);
            packetLineOut.end();
            return;
        }
        sendPack(NullProgressMonitor.INSTANCE, packetLineOut, this.rawOut, fetchRequest, accumulator, collection, list, list2);
    }

    /* JADX WARN: Code duplicated, block: B:76:0x01bf A[Catch: all -> 0x026d, TryCatch #0 {all -> 0x026d, blocks: (B:10:0x0043, B:12:0x0051, B:14:0x005f, B:16:0x0065, B:18:0x006f, B:22:0x0080, B:24:0x00b2, B:26:0x00b6, B:27:0x00c5, B:29:0x00cb, B:30:0x00cf, B:32:0x00db, B:33:0x00e3, B:36:0x00ea, B:38:0x00f6, B:39:0x00fe, B:41:0x0106, B:43:0x010c, B:50:0x0149, B:52:0x0151, B:54:0x0173, B:57:0x0181, B:58:0x0185, B:61:0x018c, B:64:0x0199, B:66:0x01a1, B:74:0x01b9, B:76:0x01bf, B:77:0x01c9, B:81:0x01d6, B:83:0x01e0, B:85:0x01e6, B:69:0x01aa, B:71:0x01b0, B:86:0x01f1, B:88:0x01f7, B:90:0x01fb, B:92:0x01ff, B:94:0x020c, B:95:0x021e, B:96:0x0223, B:98:0x0233, B:53:0x015d, B:45:0x0112, B:49:0x0122, B:48:0x011c, B:13:0x0055), top: B:110:0x0043 }] */
    private void sendPack(ProgressMonitor progressMonitor, PacketLineOut packetLineOut, OutputStream outputStream, FetchRequest fetchRequest, PackStatistics.Accumulator accumulator, @Nullable Collection<Ref> collection, List<ObjectId> list, List<ObjectId> list2) throws IOException {
        RevWalk revWalk;
        RevWalk revWalk2;
        ObjectId peeledObjectId;
        if (this.wantAll.isEmpty()) {
            this.preUploadHook.onSendPack(this, this.wantIds, this.commonBase);
        } else {
            this.preUploadHook.onSendPack(this, this.wantAll, this.commonBase);
        }
        this.msgOut.flush();
        this.advertised = null;
        this.refs = null;
        PackConfig packConfig = this.packConfig;
        if (packConfig == null) {
            packConfig = new PackConfig(this.db);
        }
        PackWriter packWriter = new PackWriter(packConfig, this.walk.getObjectReader(), accumulator);
        try {
            packWriter.setIndexDisabled(true);
            if (fetchRequest.getFilterSpec().isNoOp()) {
                packWriter.setUseCachedPacks(true);
            } else {
                packWriter.setFilterSpec(fetchRequest.getFilterSpec());
                packWriter.setUseCachedPacks(false);
            }
            packWriter.setUseBitmaps(fetchRequest.getDepth() == 0 && fetchRequest.getClientShallowCommits().isEmpty() && fetchRequest.getFilterSpec().getTreeDepthLimit() == -1);
            packWriter.setClientShallowCommits(fetchRequest.getClientShallowCommits());
            packWriter.setReuseDeltaCommits(true);
            packWriter.setDeltaBaseAsOffset(fetchRequest.getClientCapabilities().contains("ofs-delta"));
            packWriter.setThin(fetchRequest.getClientCapabilities().contains("thin-pack"));
            packWriter.setReuseValidatingObjects(false);
            if (this.commonBase.isEmpty() && this.refs != null) {
                HashSet hashSet = new HashSet();
                for (Ref ref : this.refs.values()) {
                    if (ref.getPeeledObjectId() != null) {
                        hashSet.add(ref.getPeeledObjectId());
                    } else if (ref.getObjectId() != null && ref.getName().startsWith(Constants.R_HEADS)) {
                        hashSet.add(ref.getObjectId());
                    }
                }
                packWriter.setTagTargets(hashSet);
            }
            RevWalk revWalk3 = this.walk;
            if (fetchRequest.getDepth() > 0 || fetchRequest.getDeepenSince() != 0 || !list2.isEmpty()) {
                revWalk = revWalk3;
                int depth = fetchRequest.getDepth() == 0 ? Integer.MAX_VALUE : fetchRequest.getDepth() - 1;
                packWriter.setShallowPack(fetchRequest.getDepth(), list);
                DepthWalk.RevWalk revWalk4 = new DepthWalk.RevWalk(this.walk.getObjectReader(), depth);
                revWalk4.setDeepenSince(fetchRequest.getDeepenSince());
                revWalk4.setDeepenNots(list2);
                revWalk4.assumeShallow(fetchRequest.getClientShallowCommits());
                revWalk = revWalk4;
            }
            revWalk = revWalk3;
            if (this.wantAll.isEmpty()) {
                packWriter.preparePack(progressMonitor, this.wantIds, this.commonBase, fetchRequest.getClientShallowCommits());
                revWalk2 = revWalk;
            } else {
                this.walk.reset();
                ObjectWalk objectWalkWithSameObjects = revWalk.toObjectWalkWithSameObjects();
                packWriter.preparePack(progressMonitor, objectWalkWithSameObjects, this.wantAll, this.commonBase, PackWriter.NONE);
                revWalk2 = objectWalkWithSameObjects;
            }
            if (fetchRequest.getClientCapabilities().contains("include-tag") && collection != null) {
                for (Ref refPeel : collection) {
                    ObjectId objectId = refPeel.getObjectId();
                    if (objectId != null) {
                        if (this.wantAll.isEmpty()) {
                            if (!this.wantIds.contains(objectId)) {
                                if (!refPeel.isPeeled()) {
                                    refPeel = this.db.getRefDatabase().peel(refPeel);
                                }
                                peeledObjectId = refPeel.getPeeledObjectId();
                                ObjectId objectId2 = refPeel.getObjectId();
                                if (peeledObjectId != null && objectId2 != null) {
                                    ObjectId objectId3 = refPeel.getObjectId();
                                    if (!packWriter.willInclude(peeledObjectId) && !packWriter.willInclude(objectId3)) {
                                        RevObject any = revWalk2.parseAny(objectId3);
                                        addTagChain(any, packWriter);
                                        packWriter.addObject(any);
                                    }
                                }
                            }
                        } else {
                            RevObject revObjectLookupOrNull = revWalk2.lookupOrNull(objectId);
                            if (revObjectLookupOrNull == null || !revObjectLookupOrNull.has(this.WANT)) {
                                if (!refPeel.isPeeled()) {
                                    refPeel = this.db.getRefDatabase().peel(refPeel);
                                }
                                peeledObjectId = refPeel.getPeeledObjectId();
                                ObjectId objectId4 = refPeel.getObjectId();
                                if (peeledObjectId != null) {
                                    ObjectId objectId5 = refPeel.getObjectId();
                                    if (!packWriter.willInclude(peeledObjectId)) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (packetLineOut.isUsingSideband()) {
                if ((fetchRequest instanceof FetchV2Request) && this.cachedPackUriProvider != null && !((FetchV2Request) fetchRequest).getPackfileUriProtocols().isEmpty()) {
                    packWriter.setPackfileUriConfig(new PackWriter.PackfileUriConfig(packetLineOut, ((FetchV2Request) fetchRequest).getPackfileUriProtocols(), this.cachedPackUriProvider));
                } else {
                    packetLineOut.writeString("packfile\n");
                }
            }
            packWriter.enableSearchForReuseTimeout();
            packWriter.writePack(progressMonitor, NullProgressMonitor.INSTANCE, outputStream);
            if (this.msgOut != NullOutputStream.INSTANCE) {
                this.msgOut.write(Constants.encode(String.valueOf(packWriter.getStatistics().getMessage()) + '\n'));
                this.msgOut.flush();
            }
        } finally {
            PackStatistics statistics = packWriter.getStatistics();
            this.statistics = statistics;
            if (statistics != null) {
                this.postUploadHook.onPostUpload(statistics);
            }
            packWriter.close();
        }
    }

    private static void findSymrefs(RefAdvertiser refAdvertiser, Map<String, Ref> map) {
        Ref ref = map.get("HEAD");
        if (ref == null || !ref.isSymbolic()) {
            return;
        }
        refAdvertiser.addSymref("HEAD", ref.getLeaf().getName());
    }

    private void addTagChain(RevObject revObject, PackWriter packWriter) throws IOException {
        while (4 == revObject.getType()) {
            revObject = ((RevTag) revObject).getObject();
            if (revObject.getType() == 4 && !packWriter.willInclude(revObject.getId())) {
                this.walk.parseBody(revObject);
                packWriter.addObject(revObject);
            }
        }
    }

    private static class ResponseBufferedOutputStream extends OutputStream {
        private OutputStream out = new ByteArrayOutputStream();
        private final OutputStream rawOut;

        ResponseBufferedOutputStream(OutputStream outputStream) {
            this.rawOut = outputStream;
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.out.write(i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.out.write(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.out.write(bArr, i, i2);
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            this.out.flush();
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.out.close();
        }

        void stopBuffering() throws IOException {
            OutputStream outputStream = this.out;
            OutputStream outputStream2 = this.rawOut;
            if (outputStream != outputStream2) {
                ((ByteArrayOutputStream) outputStream).writeTo(outputStream2);
                this.out = this.rawOut;
            }
        }
    }

    private class SideBandErrorWriter implements ErrorWriter {
        private SideBandErrorWriter() {
        }

        /* synthetic */ SideBandErrorWriter(UploadPack uploadPack, SideBandErrorWriter sideBandErrorWriter) {
            this();
        }

        @Override // org.eclipse.jgit.transport.UploadPack.ErrorWriter
        public void writeError(String str) throws IOException {
            SideBandOutputStream sideBandOutputStream = new SideBandOutputStream(3, 1000, (OutputStream) Objects.requireNonNull(UploadPack.this.rawOut));
            sideBandOutputStream.write(Constants.encode(str));
            sideBandOutputStream.flush();
        }
    }

    private class PackProtocolErrorWriter implements ErrorWriter {
        private PackProtocolErrorWriter() {
        }

        /* synthetic */ PackProtocolErrorWriter(UploadPack uploadPack, PackProtocolErrorWriter packProtocolErrorWriter) {
            this();
        }

        @Override // org.eclipse.jgit.transport.UploadPack.ErrorWriter
        public void writeError(String str) throws IOException {
            new PacketLineOut((OutputStream) Objects.requireNonNull(UploadPack.this.rawOut)).writeString("ERR " + str + '\n');
        }
    }
}
