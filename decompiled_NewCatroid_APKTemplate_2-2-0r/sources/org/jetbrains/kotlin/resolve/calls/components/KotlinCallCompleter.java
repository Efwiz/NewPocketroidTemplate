package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.SimpleResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.inference.components.TrivialConstraintTypeInferenceOracle;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExpectedTypeConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.model.AllCandidatesResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.CandidateWithDiagnostics;
import org.jetbrains.kotlin.resolve.calls.model.CompletedCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.ErrorCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ManyCandidatesCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.NoneCandidatesCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.PartialCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedToSamWithVarargDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.TransformableToWarning;
import org.jetbrains.kotlin.resolve.calls.model.TypeCheckerHasRanIntoRecursion;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateFactory;
import org.jetbrains.kotlin.resolve.calls.tower.TowerUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: KotlinCallCompleter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000f\u001a\u00020\u0010J&\u0010\u0011\u001a\u00020\u00122\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u000f\u001a\u00020\u0010J,\u0010\u0017\u001a\u00020\u00142\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u00192\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\rH\u0002J:\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010(\u001a\u00020)H\u0002J4\u0010\u001f\u001a\u00020\u00122\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u00192\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u000f\u001a\u00020\u0010J \u0010*\u001a\u00020\u001b*\u00020\u00142\b\u0010+\u001a\u0004\u0018\u00010\u00162\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u001e\u0010,\u001a\u00020\u001b*\u00020\u00142\b\u0010+\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J$\u0010-\u001a\u00020\u0012*\u00020\u00142\u0006\u0010.\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001d2\b\b\u0002\u0010/\u001a\u00020)J\u0014\u00100\u001a\u00020\u001b*\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u001a\u00101\u001a\b\u0012\u0004\u0012\u00020\u001602*\u00020\r2\u0006\u00103\u001a\u000204H\u0002J$\u0010\u001f\u001a\u00020\u001b*\u00020\u00142\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020%2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\f\u00105\u001a\u00020\u0016*\u000206H\u0002J\u000e\u00107\u001a\u0004\u0018\u00010\u0016*\u00020\u0014H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/KotlinCallCompleter;", "", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "trivialConstraintTypeInferenceOracle", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "chooseCandidateRegardingOverloadResolutionByLambdaReturnType", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "candidates", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "createAllCandidatesResult", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "prepareCandidateForCompletion", "factory", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "propagateLambdaAnalysisDiagnostics", "", "diagnosticHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder$SimpleHolder;", "candidate", "runCompletion", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "collectAllCandidatesMode", "", "addExpectedTypeConstraint", "returnType", "addExpectedTypeFromCastConstraint", "asCallResolutionResult", ModuleXmlParser.TYPE, "forwardToInferenceSession", "checkSamWithVararg", "getInputTypesOfLambdaAtom", "", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "substitutedReflectionType", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "substitutedReturnType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallCompleter {
    private final KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter;
    private final LanguageVersionSettings languageVersionSettings;
    private final PostponedArgumentsAnalyzer postponedArgumentsAnalyzer;
    private final TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle;

    /* JADX INFO: compiled from: KotlinCallCompleter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintSystemCompletionMode.values().length];
            try {
                iArr[ConstraintSystemCompletionMode.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintSystemCompletionMode.PARTIAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KotlinCallCompleter(PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(trivialConstraintTypeInferenceOracle, "trivialConstraintTypeInferenceOracle");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.postponedArgumentsAnalyzer = postponedArgumentsAnalyzer;
        this.kotlinConstraintSystemCompleter = kotlinConstraintSystemCompleter;
        this.trivialConstraintTypeInferenceOracle = trivialConstraintTypeInferenceOracle;
        this.languageVersionSettings = languageVersionSettings;
    }

    public final CallResolutionResult runCompletion(CandidateFactory<? extends ResolutionCandidate> factory, Collection<? extends ResolutionCandidate> candidates, UnwrappedType expectedType, KotlinResolutionCallbacks resolutionCallbacks) {
        UnwrappedType unwrappedTypeSubstitutedReflectionType;
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        KotlinDiagnosticsHolder.SimpleHolder simpleHolder = new KotlinDiagnosticsHolder.SimpleHolder();
        if (candidates.isEmpty()) {
            simpleHolder.addDiagnostic(new NoneCandidatesCallDiagnostic());
        } else if (candidates.size() > 1) {
            simpleHolder.addDiagnostic(new ManyCandidatesCallDiagnostic(candidates));
        }
        ResolutionCandidate resolutionCandidatePrepareCandidateForCompletion = prepareCandidateForCompletion(factory, candidates, resolutionCallbacks);
        if (resolutionCandidatePrepareCandidateForCompletion instanceof SimpleResolutionCandidate) {
            checkSamWithVararg(resolutionCandidatePrepareCandidateForCompletion, simpleHolder);
            unwrappedTypeSubstitutedReflectionType = substitutedReturnType(resolutionCandidatePrepareCandidateForCompletion);
            addExpectedTypeConstraint(resolutionCandidatePrepareCandidateForCompletion, unwrappedTypeSubstitutedReflectionType, expectedType);
            addExpectedTypeFromCastConstraint(resolutionCandidatePrepareCandidateForCompletion, unwrappedTypeSubstitutedReflectionType, resolutionCallbacks);
        } else {
            if (!(resolutionCandidatePrepareCandidateForCompletion instanceof CallableReferenceResolutionCandidate)) {
                throw new NoWhenBranchMatchedException();
            }
            unwrappedTypeSubstitutedReflectionType = substitutedReflectionType((CallableReferenceResolutionCandidate) resolutionCandidatePrepareCandidateForCompletion);
        }
        ConstraintSystemCompletionMode constraintSystemCompletionModeComputeCompletionMode = CompletionModeCalculator.INSTANCE.computeCompletionMode(resolutionCandidatePrepareCandidateForCompletion, expectedType, unwrappedTypeSubstitutedReflectionType, this.trivialConstraintTypeInferenceOracle, resolutionCallbacks.getInferenceSession());
        int i = WhenMappings.$EnumSwitchMapping$0[constraintSystemCompletionModeComputeCompletionMode.ordinal()];
        if (i == 1) {
            if (resolutionCallbacks.getInferenceSession().shouldRunCompletion(resolutionCandidatePrepareCandidateForCompletion)) {
                runCompletion(resolutionCandidatePrepareCandidateForCompletion, constraintSystemCompletionModeComputeCompletionMode, simpleHolder, resolutionCallbacks);
                return asCallResolutionResult$default(this, resolutionCandidatePrepareCandidateForCompletion, constraintSystemCompletionModeComputeCompletionMode, simpleHolder, false, 4, null);
            }
            return asCallResolutionResult(resolutionCandidatePrepareCandidateForCompletion, ConstraintSystemCompletionMode.PARTIAL, simpleHolder, true);
        }
        if (i == 2) {
            runCompletion(resolutionCandidatePrepareCandidateForCompletion, constraintSystemCompletionModeComputeCompletionMode, simpleHolder, resolutionCallbacks);
            return asCallResolutionResult$default(this, resolutionCandidatePrepareCandidateForCompletion, constraintSystemCompletionModeComputeCompletionMode, simpleHolder, false, 4, null);
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalStateException("Should not be here");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Set<SimpleResolutionCandidate> chooseCandidateRegardingOverloadResolutionByLambdaReturnType(Set<? extends SimpleResolutionCandidate> candidates, KotlinResolutionCallbacks resolutionCallbacks) {
        Map map;
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        ArrayList arrayList = new ArrayList();
        for (SimpleResolutionCandidate simpleResolutionCandidate : candidates) {
            List<ResolvedAtom> subResolvedAtoms = simpleResolutionCandidate.getSubResolvedAtoms();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : subResolvedAtoms) {
                ResolvedAtom resolvedAtom = (ResolvedAtom) obj;
                if ((resolvedAtom instanceof ResolvedLambdaAtom) && !resolvedAtom.getAnalyzed()) {
                    arrayList2.add(obj);
                }
            }
            ArrayList<ResolvedAtom> arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            for (ResolvedAtom resolvedAtom2 : arrayList3) {
                Intrinsics.checkNotNull(resolvedAtom2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom");
                arrayList4.add(TuplesKt.to(simpleResolutionCandidate, (ResolvedLambdaAtom) resolvedAtom2));
            }
            CollectionsKt.addAll(arrayList, arrayList4);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            LambdaKotlinCallArgument atom = ((ResolvedLambdaAtom) ((Pair) obj2).component2()).getAtom();
            Object obj3 = linkedHashMap.get(atom);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(atom, obj3);
            }
            ((List) obj3).add(obj2);
        }
        List list = (List) CollectionsKt.singleOrNull(linkedHashMap.values());
        if (list == null || (map = MapsKt.toMap(list)) == null) {
            return candidates;
        }
        Iterator it = map.values().iterator();
        int size = ((ResolvedLambdaAtom) it.next()).getParameters().size();
        while (it.hasNext()) {
            if (((ResolvedLambdaAtom) it.next()).getParameters().size() != size) {
                return candidates;
            }
        }
        Collection collectionValues = map.values();
        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
            Iterator it2 = collectionValues.iterator();
            while (it2.hasNext()) {
                UnwrappedType expectedType = ((ResolvedLambdaAtom) it2.next()).getExpectedType();
                if (expectedType == null || !FunctionTypesKt.isFunctionTypeOrSubtype(expectedType)) {
                    return candidates;
                }
            }
        }
        for (SimpleResolutionCandidate simpleResolutionCandidate2 : map.keySet()) {
            runCompletion(simpleResolutionCandidate2, ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA, simpleResolutionCandidate2, resolutionCallbacks);
        }
        Iterator it3 = map.entrySet().iterator();
        Map.Entry entry = (Map.Entry) it3.next();
        List<UnwrappedType> inputTypesOfLambdaAtom = getInputTypesOfLambdaAtom((SimpleResolutionCandidate) entry.getKey(), (ResolvedLambdaAtom) entry.getValue());
        while (it3.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it3.next();
            if (!Intrinsics.areEqual(getInputTypesOfLambdaAtom((SimpleResolutionCandidate) entry2.getKey(), (ResolvedLambdaAtom) entry2.getValue()), inputTypesOfLambdaAtom)) {
                return candidates;
            }
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        for (Map.Entry entry3 : map.entrySet()) {
            Object key = entry3.getKey();
            SimpleResolutionCandidate simpleResolutionCandidate3 = (SimpleResolutionCandidate) entry3.getKey();
            linkedHashMap2.put(key, this.kotlinConstraintSystemCompleter.prepareLambdaAtomForFactoryPattern((ResolvedLambdaAtom) entry3.getValue(), simpleResolutionCandidate3, simpleResolutionCandidate3));
        }
        KotlinDiagnosticsHolder.SimpleHolder simpleHolder = new KotlinDiagnosticsHolder.SimpleHolder();
        Iterator it4 = linkedHashMap2.entrySet().iterator();
        Map.Entry entry4 = (Map.Entry) it4.next();
        SimpleResolutionCandidate simpleResolutionCandidate4 = (SimpleResolutionCandidate) entry4.getKey();
        ResolvedLambdaAtom resolvedLambdaAtom = (ResolvedLambdaAtom) entry4.getValue();
        resolutionCallbacks.recordInlinabilityOfLambda(map.entrySet());
        KotlinDiagnosticsHolder.SimpleHolder simpleHolder2 = simpleHolder;
        ReturnArgumentsAnalysisResult returnArgumentsAnalysisResultAnalyzeLambda = this.postponedArgumentsAnalyzer.analyzeLambda(simpleResolutionCandidate4.getSystem().asPostponedArgumentsAnalyzerContext(), resolutionCallbacks, resolvedLambdaAtom, ConstraintSystemCompletionMode.FULL, simpleHolder2);
        propagateLambdaAnalysisDiagnostics(simpleHolder, simpleResolutionCandidate4);
        while (it4.hasNext()) {
            Map.Entry entry5 = (Map.Entry) it4.next();
            SimpleResolutionCandidate simpleResolutionCandidate5 = (SimpleResolutionCandidate) entry5.getKey();
            PostponedArgumentsAnalyzer.applyResultsOfAnalyzedLambdaToCandidateSystem$default(this.postponedArgumentsAnalyzer, simpleResolutionCandidate5.getSystem().asPostponedArgumentsAnalyzerContext(), (ResolvedLambdaAtom) entry5.getValue(), returnArgumentsAnalysisResultAnalyzeLambda, ConstraintSystemCompletionMode.FULL, simpleHolder2, null, 32, null);
            propagateLambdaAnalysisDiagnostics(simpleHolder, simpleResolutionCandidate5);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (SimpleResolutionCandidate simpleResolutionCandidate6 : candidates) {
            if (simpleResolutionCandidate6.getIsSuccessful()) {
                linkedHashSet2.add(simpleResolutionCandidate6);
            } else {
                linkedHashSet.add(simpleResolutionCandidate6);
            }
        }
        return !linkedHashSet2.isEmpty() ? linkedHashSet2 : linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void propagateLambdaAnalysisDiagnostics(KotlinDiagnosticsHolder.SimpleHolder diagnosticHolder, SimpleResolutionCandidate candidate) {
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.DontLoseDiagnosticsDuringOverloadResolutionByReturnType);
        for (KotlinCallDiagnostic kotlinCallDiagnosticTransformToWarning : diagnosticHolder.getDiagnostics()) {
            if (kotlinCallDiagnosticTransformToWarning instanceof TransformableToWarning) {
                if (!zSupportsFeature) {
                    kotlinCallDiagnosticTransformToWarning = ((TransformableToWarning) kotlinCallDiagnosticTransformToWarning).transformToWarning();
                }
                if (kotlinCallDiagnosticTransformToWarning != 0) {
                    candidate.addDiagnostic(kotlinCallDiagnosticTransformToWarning);
                }
            }
        }
    }

    private final List<UnwrappedType> getInputTypesOfLambdaAtom(SimpleResolutionCandidate simpleResolutionCandidate, ResolvedLambdaAtom resolvedLambdaAtom) {
        ArrayList arrayList = new ArrayList();
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = simpleResolutionCandidate.getSystem().getBuilder().buildCurrentSubstitutor();
        ConstraintSystemCompletionContext constraintSystemCompletionContextAsConstraintSystemCompleterContext = simpleResolutionCandidate.getSystem().asConstraintSystemCompleterContext();
        Iterator<UnwrappedType> it = resolvedLambdaAtom.getInputTypes().iterator();
        while (it.hasNext()) {
            KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, constraintSystemCompletionContextAsConstraintSystemCompleterContext, it.next());
            Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
            arrayList.add((UnwrappedType) kotlinTypeMarkerSafeSubstitute);
        }
        return arrayList;
    }

    private final void checkSamWithVararg(ResolutionCandidate resolutionCandidate, KotlinDiagnosticsHolder.SimpleHolder simpleHolder) {
        KotlinCallArgument kotlinCallArgument;
        boolean z = resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.SamConversionPerArgument) && !resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitVarargAsArrayAfterSamArgument);
        CallableDescriptor callableDescriptor = resolutionCandidate.getResolvedCall().get_candidateDescriptor();
        if (z && (callableDescriptor instanceof SyntheticMemberDescriptor)) {
            DeclarationDescriptor samInterface = ((SyntheticMemberDescriptor) callableDescriptor).getSamInterface();
            FunctionDescriptor functionDescriptor = samInterface instanceof FunctionDescriptor ? (FunctionDescriptor) samInterface : null;
            if (functionDescriptor == null) {
                return;
            }
            List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.lastOrNull((List) valueParameters);
            if (valueParameterDescriptor == null || !ArgumentsUtilsKt.isVararg(valueParameterDescriptor) || (kotlinCallArgument = (KotlinCallArgument) CollectionsKt.lastOrNull((List) resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis())) == null) {
                return;
            }
            simpleHolder.addDiagnostic(new ResolvedToSamWithVarargDiagnostic(kotlinCallArgument));
        }
    }

    public final CallResolutionResult createAllCandidatesResult(Collection<? extends ResolutionCandidate> candidates, UnwrappedType expectedType, KotlinResolutionCallbacks resolutionCallbacks) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Collection<? extends ResolutionCandidate> collection = candidates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (ResolutionCandidate resolutionCandidate : collection) {
            KotlinDiagnosticsHolder.SimpleHolder simpleHolder = new KotlinDiagnosticsHolder.SimpleHolder();
            addExpectedTypeConstraint(resolutionCandidate, substitutedReturnType(resolutionCandidate), expectedType);
            runCompletion(resolutionCandidate.getResolvedCall(), ConstraintSystemCompletionMode.FULL, simpleHolder, resolutionCandidate.getSystem(), resolutionCallbacks, true);
            arrayList.add(new CandidateWithDiagnostics(resolutionCandidate, CollectionsKt.plus((Collection) simpleHolder.getDiagnostics(), (Iterable) resolutionCandidate.getDiagnostics())));
        }
        return new AllCandidatesResolutionResult(arrayList, resolutionCallbacks.createEmptyConstraintSystem());
    }

    private final void runCompletion(ResolutionCandidate resolutionCandidate, ConstraintSystemCompletionMode constraintSystemCompletionMode, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, KotlinResolutionCallbacks kotlinResolutionCallbacks) {
        runCompletion$default(this, resolutionCandidate.getResolvedCall(), constraintSystemCompletionMode, kotlinDiagnosticsHolder, resolutionCandidate.getSystem(), kotlinResolutionCallbacks, false, 32, null);
    }

    static /* synthetic */ void runCompletion$default(KotlinCallCompleter kotlinCallCompleter, ResolvedCallAtom resolvedCallAtom, ConstraintSystemCompletionMode constraintSystemCompletionMode, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, NewConstraintSystem newConstraintSystem, KotlinResolutionCallbacks kotlinResolutionCallbacks, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        kotlinCallCompleter.runCompletion(resolvedCallAtom, constraintSystemCompletionMode, kotlinDiagnosticsHolder, newConstraintSystem, kotlinResolutionCallbacks, z);
    }

    private final void runCompletion(ResolvedCallAtom resolvedCallAtom, final ConstraintSystemCompletionMode completionMode, final KotlinDiagnosticsHolder diagnosticsHolder, final NewConstraintSystem constraintSystem, final KotlinResolutionCallbacks resolutionCallbacks, final boolean collectAllCandidatesMode) {
        SimpleType freshReturnType = ResolutionAtomsKt.getFreshReturnType(resolvedCallAtom);
        if (freshReturnType == null) {
            SimpleType unitType = BuiltInsProviderKt.getBuiltIns(constraintSystem).getUnitType();
            Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
            freshReturnType = unitType;
        }
        UnwrappedType unwrappedType = freshReturnType;
        this.kotlinConstraintSystemCompleter.runCompletion(constraintSystem.asConstraintSystemCompleterContext(), completionMode, CollectionsKt.listOf(resolvedCallAtom), unwrappedType, diagnosticsHolder, new Function1<PostponedResolvedAtom, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.components.KotlinCallCompleter.runCompletion.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PostponedResolvedAtom postponedResolvedAtom) {
                invoke2(postponedResolvedAtom);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PostponedResolvedAtom it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (collectAllCandidatesMode) {
                    it.setEmptyAnalyzedResults();
                } else {
                    this.postponedArgumentsAnalyzer.analyze(constraintSystem.asPostponedArgumentsAnalyzerContext(), resolutionCallbacks, it, completionMode, diagnosticsHolder);
                }
            }
        });
        Iterator<T> it = constraintSystem.getErrors().iterator();
        while (it.hasNext()) {
            ResolvedCallAtomsKt.addError(diagnosticsHolder, (ConstraintSystemError) it.next());
        }
        if ((unwrappedType instanceof ErrorType) && ((ErrorType) unwrappedType).getKind() == ErrorTypeKind.RECURSIVE_TYPE) {
            diagnosticsHolder.addDiagnostic(TypeCheckerHasRanIntoRecursion.INSTANCE);
        }
    }

    private final ResolutionCandidate prepareCandidateForCompletion(CandidateFactory<? extends ResolutionCandidate> factory, Collection<? extends ResolutionCandidate> candidates, KotlinResolutionCallbacks resolutionCallbacks) {
        MutableResolvedCallAtom resolvedCall;
        ResolutionCandidate resolutionCandidate = (ResolutionCandidate) CollectionsKt.singleOrNull(candidates);
        if (resolutionCandidate != null && (resolvedCall = resolutionCandidate.getResolvedCall()) != null) {
            Set<KotlinCallArgument> setKeySet = resolvedCall.getArgumentToCandidateParameter().keySet();
            if (!(setKeySet instanceof Collection) || !setKeySet.isEmpty()) {
                Iterator<T> it = setKeySet.iterator();
                while (it.hasNext()) {
                    if (((KotlinCallArgument) it.next()) instanceof LambdaKotlinCallArgument) {
                        resolutionCallbacks.bindStubResolvedCallForCandidate(resolvedCall);
                        break;
                    }
                }
            }
            resolutionCallbacks.disableContractsIfNecessary(resolvedCall);
        }
        return resolutionCandidate == null ? (ResolutionCandidate) TowerUtilsKt.forceResolution(factory.createErrorCandidate()) : resolutionCandidate;
    }

    private final UnwrappedType substitutedReflectionType(CallableReferenceResolutionCandidate callableReferenceResolutionCandidate) {
        return callableReferenceResolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor().safeSubstitute(callableReferenceResolutionCandidate.getReflectionCandidateType());
    }

    private final UnwrappedType substitutedReturnType(ResolutionCandidate resolutionCandidate) {
        UnwrappedType unwrappedTypeUnwrap;
        UnwrappedType unwrappedTypeSafeSubstitute;
        KotlinType returnType = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getReturnType();
        if (returnType == null || (unwrappedTypeUnwrap = returnType.unwrap()) == null) {
            return null;
        }
        UnwrappedType unwrappedTypeSafeSubstitute2 = resolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor().safeSubstitute(unwrappedTypeUnwrap);
        TypeSubstitutorMarker resultingSubstitutor = resolutionCandidate.getResultingSubstitutor();
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = resultingSubstitutor instanceof NewTypeSubstitutorByConstructorMap ? (NewTypeSubstitutorByConstructorMap) resultingSubstitutor : null;
        return (newTypeSubstitutorByConstructorMap == null || (unwrappedTypeSafeSubstitute = newTypeSubstitutorByConstructorMap.safeSubstitute(unwrappedTypeSafeSubstitute2)) == null) ? unwrappedTypeSafeSubstitute2 : unwrappedTypeSafeSubstitute;
    }

    private final void addExpectedTypeConstraint(ResolutionCandidate resolutionCandidate, UnwrappedType unwrappedType, UnwrappedType unwrappedType2) {
        if (unwrappedType == null || unwrappedType2 == null) {
            return;
        }
        if (!TypeUtils.noExpectedType(unwrappedType2) || unwrappedType2 == TypeUtils.UNIT_EXPECTED_TYPE) {
            ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
            if (builder.currentStorage().getNotFixedTypeVariables().isEmpty()) {
                return;
            }
            if (unwrappedType2 == TypeUtils.UNIT_EXPECTED_TYPE) {
                SimpleType unitType = BuiltInsProviderKt.getBuiltIns(builder).getUnitType();
                Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
                ConstraintSystemBuilderKt.addEqualityConstraintIfCompatible(builder, unwrappedType, unitType, new ExpectedTypeConstraintPositionImpl(resolutionCandidate.getResolvedCall().getAtom()));
                return;
            }
            builder.addSubtypeConstraint(unwrappedType, unwrappedType2, new ExpectedTypeConstraintPositionImpl(resolutionCandidate.getResolvedCall().getAtom()));
        }
    }

    private final void addExpectedTypeFromCastConstraint(ResolutionCandidate resolutionCandidate, UnwrappedType unwrappedType, KotlinResolutionCallbacks kotlinResolutionCallbacks) {
        UnwrappedType expectedTypeFromAsExpressionAndRecordItInTrace;
        if (!resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.ExpectedTypeFromCast) || unwrappedType == null || (expectedTypeFromAsExpressionAndRecordItInTrace = kotlinResolutionCallbacks.getExpectedTypeFromAsExpressionAndRecordItInTrace(resolutionCandidate.getResolvedCall())) == null) {
            return;
        }
        resolutionCandidate.getSystem().getBuilder().addSubtypeConstraint(unwrappedType, expectedTypeFromAsExpressionAndRecordItInTrace, new ExpectedTypeConstraintPositionImpl(resolutionCandidate.getResolvedCall().getAtom()));
    }

    public static /* synthetic */ CallResolutionResult asCallResolutionResult$default(KotlinCallCompleter kotlinCallCompleter, ResolutionCandidate resolutionCandidate, ConstraintSystemCompletionMode constraintSystemCompletionMode, KotlinDiagnosticsHolder.SimpleHolder simpleHolder, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return kotlinCallCompleter.asCallResolutionResult(resolutionCandidate, constraintSystemCompletionMode, simpleHolder, z);
    }

    public final CallResolutionResult asCallResolutionResult(ResolutionCandidate resolutionCandidate, ConstraintSystemCompletionMode type, KotlinDiagnosticsHolder.SimpleHolder diagnosticsHolder, boolean z) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        NewConstraintSystem system = resolutionCandidate.getSystem();
        List listPlus = CollectionsKt.plus((Collection) diagnosticsHolder.getDiagnostics(), (Iterable) resolutionCandidate.getDiagnostics());
        if (KotlinCallCompleterKt.isErrorCandidate(resolutionCandidate)) {
            return new ErrorCallResolutionResult(resolutionCandidate.getResolvedCall(), listPlus, system);
        }
        if (type == ConstraintSystemCompletionMode.FULL) {
            return new CompletedCallResolutionResult(resolutionCandidate.getResolvedCall(), listPlus, system);
        }
        return new PartialCallResolutionResult(resolutionCandidate.getResolvedCall(), listPlus, system, z);
    }
}
