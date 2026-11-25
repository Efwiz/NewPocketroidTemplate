package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCSKt;
import org.jetbrains.kotlin.resolve.calls.components.CompletedCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.ErrorCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.components.PartialCallInfo;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableKt;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CompletedCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnosticsKt;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.StubResolvedAtom;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: StubTypesBasedInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B-\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020&2\u0006\u0010'\u001a\u00020*H\u0016J\u0012\u0010+\u001a\u00020&2\n\u0010,\u001a\u0006\u0012\u0002\b\u00030\u0000J\u0010\u0010-\u001a\u00020&2\u0006\u0010'\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u001bH\u0016J\u0012\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u000205H\u0016J\b\u00106\u001a\u000207H\u0016J\u001e\u00108\u001a\u00020&2\u0006\u00109\u001a\u00020:2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00150<H\u0016J\u001a\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000>0<2\u0006\u0010?\u001a\u00020@J\b\u0010A\u001a\u000200H\u0016J\u0010\u0010B\u001a\u0002002\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010E\u001a\u0002002\u0006\u00104\u001a\u000205H\u0016J\u001c\u0010F\u001a\u00020G*\u00020.2\u0006\u0010H\u001a\u00020I2\u0006\u00109\u001a\u00020:H\u0002R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R$\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0014j\b\u0012\u0004\u0012\u00020\u0015`\u0016X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u001b0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\u001d\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e0\u0014j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001e`\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R,\u0010\"\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000\u001aj\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0000`\u001cX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/StubTypesBasedInferenceSession;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "psiCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "commonPartiallyResolvedCalls", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIPartialCallInfo;", "Lkotlin/collections/ArrayList;", "getCommonPartiallyResolvedCalls", "()Ljava/util/ArrayList;", "completedCalls", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "Lkotlin/collections/HashSet;", "errorCallsInfo", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIErrorCallInfo;", "getErrorCallsInfo", "getKotlinConstraintSystemCompleter", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "nestedInferenceSessions", "getNestedInferenceSessions", "()Ljava/util/HashSet;", "addCompletedCallInfo", "", "callInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/CompletedCallInfo;", "addErrorCallInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ErrorCallInfo;", "addNestedInferenceSession", "inferenceSession", "addPartialCallInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/PartialCallInfo;", "callCompleted", "", "resolvedAtom", "computeCompletionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "currentConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "prepareForCompletion", "commonSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "resolvedCallsInfo", "", "resolveCandidates", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionResultCallInfo;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "resolveReceiverIndependently", "shouldCompleteResolvedSubAtomsOf", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "shouldRunCompletion", "asCallResolutionResult", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder$SimpleHolder;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class StubTypesBasedInferenceSession<D extends CallableDescriptor> implements InferenceSession {
    private final KotlinBuiltIns builtIns;
    private final KotlinCallComponents callComponents;
    private final ArrayList<PSIPartialCallInfo> commonPartiallyResolvedCalls;
    private final HashSet<ResolvedAtom> completedCalls;
    private final ArrayList<PSIErrorCallInfo<D>> errorCallsInfo;
    private final KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter;
    private final HashSet<StubTypesBasedInferenceSession<?>> nestedInferenceSessions;
    private final PostponedArgumentsAnalyzer postponedArgumentsAnalyzer;
    private final PSICallResolver psiCallResolver;

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addCompletedCallInfo(CompletedCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public ConstraintSystemCompletionMode computeCompletionMode(ResolutionCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return null;
    }

    public void prepareForCompletion(NewConstraintSystem commonSystem, List<PSIPartialCallInfo> resolvedCallsInfo) {
        Intrinsics.checkNotNullParameter(commonSystem, "commonSystem");
        Intrinsics.checkNotNullParameter(resolvedCallsInfo, "resolvedCallsInfo");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    /* JADX INFO: renamed from: resolveReceiverIndependently */
    public boolean getResolveReceiverIndependently() {
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldCompleteResolvedSubAtomsOf(ResolvedCallAtom resolvedCallAtom) {
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldRunCompletion(ResolutionCandidate candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return false;
    }

    public StubTypesBasedInferenceSession(PSICallResolver psiCallResolver, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, KotlinCallComponents callComponents, KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(psiCallResolver, "psiCallResolver");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.psiCallResolver = psiCallResolver;
        this.postponedArgumentsAnalyzer = postponedArgumentsAnalyzer;
        this.kotlinConstraintSystemCompleter = kotlinConstraintSystemCompleter;
        this.callComponents = callComponents;
        this.builtIns = builtIns;
        this.commonPartiallyResolvedCalls = new ArrayList<>();
        this.errorCallsInfo = new ArrayList<>();
        this.completedCalls = new HashSet<>();
        this.nestedInferenceSessions = new HashSet<>();
    }

    protected final KotlinConstraintSystemCompleter getKotlinConstraintSystemCompleter() {
        return this.kotlinConstraintSystemCompleter;
    }

    protected final KotlinCallComponents getCallComponents() {
        return this.callComponents;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ArrayList<PSIPartialCallInfo> getCommonPartiallyResolvedCalls() {
        return this.commonPartiallyResolvedCalls;
    }

    public final ArrayList<PSIErrorCallInfo<D>> getErrorCallsInfo() {
        return this.errorCallsInfo;
    }

    protected final HashSet<StubTypesBasedInferenceSession<?>> getNestedInferenceSessions() {
        return this.nestedInferenceSessions;
    }

    public final void addNestedInferenceSession(StubTypesBasedInferenceSession<?> inferenceSession) {
        Intrinsics.checkNotNullParameter(inferenceSession, "inferenceSession");
        this.nestedInferenceSessions.add(inferenceSession);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addPartialCallInfo(PartialCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        if (!(callInfo instanceof PSIPartialCallInfo)) {
            throw new AssertionError("Call info for " + callInfo + " should be instance of PSIPartialCallInfo");
        }
        this.commonPartiallyResolvedCalls.add(callInfo);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void addErrorCallInfo(ErrorCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        if (!(callInfo instanceof PSIErrorCallInfo)) {
            throw new AssertionError("Error call info for " + callInfo + " should be instance of PSIErrorCallInfo");
        }
        this.errorCallsInfo.add((PSIErrorCallInfo) callInfo);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public ConstraintStorage currentConstraintSystem() {
        PartialCallResolutionResult callResolutionResult;
        NewConstraintSystem constraintSystem;
        ConstraintSystemBuilder builder;
        ConstraintStorage constraintStorageCurrentStorage;
        PSIPartialCallInfo pSIPartialCallInfo = (PSIPartialCallInfo) CollectionsKt.lastOrNull((List) this.commonPartiallyResolvedCalls);
        return (pSIPartialCallInfo == null || (callResolutionResult = pSIPartialCallInfo.getCallResolutionResult()) == null || (constraintSystem = callResolutionResult.getConstraintSystem()) == null || (builder = constraintSystem.getBuilder()) == null || (constraintStorageCurrentStorage = builder.currentStorage()) == null) ? ConstraintStorage.Empty.INSTANCE : constraintStorageCurrentStorage;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean callCompleted(ResolvedAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        return !this.completedCalls.add(resolvedAtom);
    }

    /* JADX WARN: Code duplicated, block: B:112:0x01e5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:114:0x01c2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x01a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:35:0x00ba A[EDGE_INSN: B:35:0x00ba->B:36:0x00bb BREAK  A[LOOP:1: B:21:0x0084->B:99:?]] */
    /* JADX WARN: Code duplicated, block: B:55:0x013f  */
    /* JADX WARN: Code duplicated, block: B:57:0x017e  */
    /* JADX WARN: Code duplicated, block: B:60:0x01a6  */
    public final List<ResolutionResultCallInfo<D>> resolveCandidates(KotlinResolutionCallbacks resolutionCallbacks) {
        boolean z;
        PSIPartialCallInfo pSIPartialCallInfo;
        List listMutableListOf;
        NewConstraintSystemImpl NewConstraintSystemImpl;
        TypeConstructorMarker key;
        KotlinTypeMarker value;
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        List<PSIPartialCallInfo> list = CollectionsKt.toList(this.commonPartiallyResolvedCalls);
        KotlinDiagnosticsHolder.SimpleHolder simpleHolder = new KotlinDiagnosticsHolder.SimpleHolder();
        int i = 0;
        if (list.size() <= 1) {
            z = false;
            break;
        }
        List<PSIPartialCallInfo> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (PSIPartialCallInfo pSIPartialCallInfo2 : list2) {
            arrayList.add(Boolean.valueOf((pSIPartialCallInfo2.getCallResolutionResult().getConstraintSystem().getErrors().isEmpty() && pSIPartialCallInfo2.getCallResolutionResult().getDiagnostics().isEmpty()) ? false : true));
        }
        ArrayList arrayList2 = arrayList;
        boolean z2 = arrayList2 instanceof Collection;
        if (!z2 || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((Boolean) it.next()).booleanValue()) {
                        if (!z2 || !arrayList2.isEmpty()) {
                            Iterator it2 = arrayList2.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    if (!((Boolean) it2.next()).booleanValue()) {
                                        z = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                z = false;
                break;
            }
        }
        z = false;
        break;
        ArrayList arrayList3 = new ArrayList();
        if (z) {
            List<PSIPartialCallInfo> list3 = list;
            Iterator<T> it3 = list3.iterator();
            while (true) {
                if (it3.hasNext()) {
                    PSIPartialCallInfo pSIPartialCallInfo3 = (PSIPartialCallInfo) it3.next();
                    if (pSIPartialCallInfo3.getCallResolutionResult().getConstraintSystem().getErrors().isEmpty() && pSIPartialCallInfo3.getCallResolutionResult().getDiagnostics().isEmpty()) {
                        Iterator<T> it4 = list3.iterator();
                        do {
                            if (it4.hasNext()) {
                                pSIPartialCallInfo = (PSIPartialCallInfo) it4.next();
                                if (pSIPartialCallInfo.getCallResolutionResult().getConstraintSystem().getErrors().isEmpty()) {
                                }
                                PSIPartialCallInfo[] pSIPartialCallInfoArr = new PSIPartialCallInfo[2];
                                pSIPartialCallInfoArr[i] = pSIPartialCallInfo3;
                                pSIPartialCallInfoArr[1] = pSIPartialCallInfo;
                                for (PSIPartialCallInfo pSIPartialCallInfo4 : CollectionsKt.listOf((Object[]) pSIPartialCallInfoArr)) {
                                    ResolvedAtom[] resolvedAtomArr = new ResolvedAtom[1];
                                    resolvedAtomArr[i] = pSIPartialCallInfo4.getCallResolutionResult();
                                    listMutableListOf = CollectionsKt.mutableListOf(resolvedAtomArr);
                                    NewConstraintSystemImpl = ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(this.callComponents.getConstraintInjector(), this.builtIns, this.callComponents.getKotlinTypeRefiner(), this.callComponents.getLanguageVersionSettings());
                                    NewConstraintSystemImpl.addOtherSystem(pSIPartialCallInfo4.getCallResolutionResult().getConstraintSystem().getBuilder().currentStorage());
                                    if (pSIPartialCallInfo4 == pSIPartialCallInfo) {
                                        for (Map.Entry<TypeConstructorMarker, KotlinTypeMarker> entry : ((ResolutionResultCallInfo) arrayList3.get(i)).getResolutionResult().getConstraintSystem().getBuilder().currentStorage().getFixedTypeVariables().entrySet()) {
                                            key = entry.getKey();
                                            value = entry.getValue();
                                            if (NewConstraintSystemImpl.getNotFixedTypeVariables().containsKey(key)) {
                                                Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeConstructor");
                                                TypeConstructor typeConstructor = (TypeConstructor) key;
                                                NewConstraintSystemImpl.addEqualityConstraint(TypeVariableKt.typeForTypeVariable(typeConstructor), value, SimpleConstraintSystemConstraintPosition.INSTANCE);
                                                listMutableListOf.add(new StubResolvedAtom(typeConstructor));
                                            }
                                        }
                                    }
                                    NewConstraintSystemImpl newConstraintSystemImpl = NewConstraintSystemImpl;
                                    resolveCandidates$runCompletion(this, simpleHolder, resolutionCallbacks, newConstraintSystemImpl, listMutableListOf);
                                    CallResolutionResult callResolutionResultAsCallResolutionResult = asCallResolutionResult(pSIPartialCallInfo4, simpleHolder, newConstraintSystemImpl);
                                    arrayList3.add(new ResolutionResultCallInfo(callResolutionResultAsCallResolutionResult, this.psiCallResolver.convertToOverloadResolutionResults(pSIPartialCallInfo4.getContext(), callResolutionResultAsCallResolutionResult, pSIPartialCallInfo4.getTracingStrategy())));
                                    i = 0;
                                }
                                break;
                            }
                            throw new NoSuchElementException("Collection contains no element matching the predicate.");
                        } while (pSIPartialCallInfo.getCallResolutionResult().getDiagnostics().isEmpty());
                        PSIPartialCallInfo[] pSIPartialCallInfoArr2 = new PSIPartialCallInfo[2];
                        pSIPartialCallInfoArr2[i] = pSIPartialCallInfo3;
                        pSIPartialCallInfoArr2[1] = pSIPartialCallInfo;
                        while (r2.hasNext()) {
                            ResolvedAtom[] resolvedAtomArr2 = new ResolvedAtom[1];
                            resolvedAtomArr2[i] = pSIPartialCallInfo4.getCallResolutionResult();
                            listMutableListOf = CollectionsKt.mutableListOf(resolvedAtomArr2);
                            NewConstraintSystemImpl = ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(this.callComponents.getConstraintInjector(), this.builtIns, this.callComponents.getKotlinTypeRefiner(), this.callComponents.getLanguageVersionSettings());
                            NewConstraintSystemImpl.addOtherSystem(pSIPartialCallInfo4.getCallResolutionResult().getConstraintSystem().getBuilder().currentStorage());
                            if (pSIPartialCallInfo4 == pSIPartialCallInfo) {
                                while (r12.hasNext()) {
                                    key = entry.getKey();
                                    value = entry.getValue();
                                    if (NewConstraintSystemImpl.getNotFixedTypeVariables().containsKey(key)) {
                                        Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeConstructor");
                                        TypeConstructor typeConstructor2 = (TypeConstructor) key;
                                        NewConstraintSystemImpl.addEqualityConstraint(TypeVariableKt.typeForTypeVariable(typeConstructor2), value, SimpleConstraintSystemConstraintPosition.INSTANCE);
                                        listMutableListOf.add(new StubResolvedAtom(typeConstructor2));
                                    }
                                }
                            }
                            NewConstraintSystemImpl newConstraintSystemImpl2 = NewConstraintSystemImpl;
                            resolveCandidates$runCompletion(this, simpleHolder, resolutionCallbacks, newConstraintSystemImpl2, listMutableListOf);
                            CallResolutionResult callResolutionResultAsCallResolutionResult2 = asCallResolutionResult(pSIPartialCallInfo4, simpleHolder, newConstraintSystemImpl2);
                            arrayList3.add(new ResolutionResultCallInfo(callResolutionResultAsCallResolutionResult2, this.psiCallResolver.convertToOverloadResolutionResults(pSIPartialCallInfo4.getContext(), callResolutionResultAsCallResolutionResult2, pSIPartialCallInfo4.getTracingStrategy())));
                            i = 0;
                        }
                        break;
                        break;
                    }
                    i = 0;
                } else {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            }
        } else {
            NewConstraintSystemImpl NewConstraintSystemImpl2 = ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(this.callComponents.getConstraintInjector(), this.builtIns, this.callComponents.getKotlinTypeRefiner(), this.callComponents.getLanguageVersionSettings());
            NewConstraintSystemImpl2.addOtherSystem(currentConstraintSystem());
            NewConstraintSystemImpl newConstraintSystemImpl3 = NewConstraintSystemImpl2;
            prepareForCompletion(newConstraintSystemImpl3, list);
            List<PSIPartialCallInfo> list4 = list;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            Iterator<T> it5 = list4.iterator();
            while (it5.hasNext()) {
                arrayList4.add(((PSIPartialCallInfo) it5.next()).getCallResolutionResult());
            }
            resolveCandidates$runCompletion(this, simpleHolder, resolutionCallbacks, newConstraintSystemImpl3, arrayList4);
            for (PSIPartialCallInfo pSIPartialCallInfo5 : list4) {
                CallResolutionResult callResolutionResultAsCallResolutionResult3 = asCallResolutionResult(pSIPartialCallInfo5, simpleHolder, newConstraintSystemImpl3);
                arrayList3.add(new ResolutionResultCallInfo(callResolutionResultAsCallResolutionResult3, this.psiCallResolver.convertToOverloadResolutionResults(pSIPartialCallInfo5.getContext(), callResolutionResultAsCallResolutionResult3, pSIPartialCallInfo5.getTracingStrategy())));
            }
        }
        ArrayList arrayList5 = arrayList3;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it6 = arrayList5.iterator();
        while (it6.hasNext()) {
            arrayList6.add(((ResolutionResultCallInfo) it6.next()).getResolutionResult());
        }
        ArrayList arrayList7 = arrayList6;
        ArrayList<PSIErrorCallInfo<D>> arrayList8 = this.errorCallsInfo;
        ArrayList<PSIErrorCallInfo> arrayList9 = new ArrayList();
        for (Object obj : arrayList8) {
            if (!arrayList7.contains(((PSIErrorCallInfo) obj).getCallResolutionResult())) {
                arrayList9.add(obj);
            }
        }
        for (PSIErrorCallInfo pSIErrorCallInfo : arrayList9) {
            arrayList3.add(new ResolutionResultCallInfo(pSIErrorCallInfo.getCallResolutionResult(), pSIErrorCallInfo.getResult()));
        }
        return arrayList3;
    }

    private static final <D extends CallableDescriptor> void resolveCandidates$runCompletion(final StubTypesBasedInferenceSession<D> stubTypesBasedInferenceSession, final KotlinDiagnosticsHolder.SimpleHolder simpleHolder, final KotlinResolutionCallbacks kotlinResolutionCallbacks, final NewConstraintSystem newConstraintSystem, List<? extends ResolvedAtom> list) {
        final ConstraintSystemCompletionMode constraintSystemCompletionMode = ConstraintSystemCompletionMode.FULL;
        KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter = ((StubTypesBasedInferenceSession) stubTypesBasedInferenceSession).kotlinConstraintSystemCompleter;
        ConstraintSystemCompletionContext constraintSystemCompletionContextAsConstraintSystemCompleterContext = newConstraintSystem.asConstraintSystemCompleterContext();
        SimpleType unitType = ((StubTypesBasedInferenceSession) stubTypesBasedInferenceSession).builtIns.getUnitType();
        Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
        kotlinConstraintSystemCompleter.runCompletion(constraintSystemCompletionContextAsConstraintSystemCompleterContext, constraintSystemCompletionMode, list, unitType, simpleHolder, new Function1<PostponedResolvedAtom, Unit>(stubTypesBasedInferenceSession) { // from class: org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession$resolveCandidates$runCompletion$1
            final /* synthetic */ StubTypesBasedInferenceSession<D> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = stubTypesBasedInferenceSession;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PostponedResolvedAtom postponedResolvedAtom) {
                invoke2(postponedResolvedAtom);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PostponedResolvedAtom it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ((StubTypesBasedInferenceSession) this.this$0).postponedArgumentsAnalyzer.analyze(newConstraintSystem.asPostponedArgumentsAnalyzerContext(), kotlinResolutionCallbacks, it, constraintSystemCompletionMode, simpleHolder);
            }
        });
    }

    private final CallResolutionResult asCallResolutionResult(PartialCallInfo partialCallInfo, KotlinDiagnosticsHolder.SimpleHolder simpleHolder, NewConstraintSystem newConstraintSystem) {
        return new CompletedCallResolutionResult(partialCallInfo.getCallResolutionResult().getResultCallAtom(), CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) simpleHolder.getDiagnostics(), (Iterable) partialCallInfo.getCallResolutionResult().getDiagnostics()), (Iterable) KotlinCallDiagnosticsKt.asDiagnostics(newConstraintSystem.getErrors())), newConstraintSystem);
    }
}
