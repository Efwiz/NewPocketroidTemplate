package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateChosenUsingOverloadResolutionByLambdaAnnotation;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleter;
import org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom;
import org.jetbrains.kotlin.fir.resolve.inference.ResolvedLambdaAtom;
import org.jetbrains.kotlin.fir.resolve.inference.ReturnArgumentsAnalysisResult;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: FirOverloadByLambdaReturnTypeResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J9\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012\"\f\b\u0000\u0010\u0014*\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u0002H\u00142\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002¢\u0006\u0002\u0010\u0019JC\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\"\f\b\u0000\u0010\u0014*\u00020\u0016*\u00020\u00152\u0006\u0010\u001b\u001a\u0002H\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\u0002\u0010\u001fJG\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012\"\f\b\u0000\u0010\u0014*\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u0002H\u00142\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\u001dH\u0002¢\u0006\u0002\u0010\"R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirOverloadByLambdaReturnTypeResolver;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;)V", "callCompleter", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "getCallCompleter", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "getComponents", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "inferenceSession", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "getInferenceSession", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "analyzeLambdaAndReduceNumberOfCandidatesRegardingOverloadResolutionByLambdaReturnType", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "call", "candidates", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Ljava/util/Set;)Ljava/util/Set;", "reduceCandidates", "qualifiedAccess", "allCandidates", "", "bestCandidates", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Ljava/util/Collection;Ljava/util/Set;)Ljava/util/Set;", "reduceCandidatesImpl", "reducedCandidates", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Ljava/util/Set;Ljava/util/Collection;)Ljava/util/Set;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOverloadByLambdaReturnTypeResolver {
    private final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components;
    private final FirSession session;

    public FirOverloadByLambdaReturnTypeResolver(FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.components = components;
        this.session = components.getSession();
    }

    public final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents getComponents() {
        return this.components;
    }

    private final FirCallCompleter getCallCompleter() {
        return this.components.getCallCompleter();
    }

    private final FirInferenceSession getInferenceSession() {
        return this.components.getTransformer().getContext().getInferenceSession();
    }

    public final <T extends FirStatement & FirResolvable> Set<Candidate> reduceCandidates(T qualifiedAccess, Collection<Candidate> allCandidates, Set<Candidate> bestCandidates) {
        Set<Candidate> setReduceCandidatesImpl;
        Intrinsics.checkNotNullParameter(qualifiedAccess, "qualifiedAccess");
        Intrinsics.checkNotNullParameter(allCandidates, "allCandidates");
        Intrinsics.checkNotNullParameter(bestCandidates, "bestCandidates");
        boolean z = true;
        if (bestCandidates.size() <= 1) {
            return bestCandidates;
        }
        if (!Intrinsics.areEqual(getInferenceSession(), FirInferenceSession.INSTANCE.getDEFAULT())) {
            T t = qualifiedAccess;
            FirReference calleeReference = t.getCalleeReference();
            boolean z2 = true;
            for (Candidate candidate : bestCandidates) {
                t.replaceCalleeReference(new FirNamedReferenceWithCandidate(null, candidate.getCallInfo().getName(), candidate));
                z2 = z2 && getInferenceSession().shouldRunCompletion(t);
                if (!z2) {
                    break;
                }
            }
            z = z2;
            t.replaceCalleeReference(calleeReference);
        }
        return (z && (setReduceCandidatesImpl = reduceCandidatesImpl(qualifiedAccess, bestCandidates, allCandidates)) != null) ? setReduceCandidatesImpl : bestCandidates;
    }

    private final <T extends FirResolvable & FirStatement> Set<Candidate> reduceCandidatesImpl(T call, Set<Candidate> reducedCandidates, Collection<Candidate> allCandidates) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : allCandidates) {
            FirDeclaration fir = ((Candidate) obj).getSymbol().getFir();
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirAnnotationContainer");
            List<FirAnnotation> annotations = fir.getAnnotations();
            if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(((FirAnnotation) it.next()).getAnnotationTypeRef())), AnnotationsForResolveKt.getOVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION_CLASS_ID())) {
                        arrayList.add(obj);
                        break;
                    }
                }
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return null;
        }
        Set setMinus = SetsKt.minus((Set) reducedCandidates, (Iterable) arrayList2);
        Set<Candidate> setAnalyzeLambdaAndReduceNumberOfCandidatesRegardingOverloadResolutionByLambdaReturnType = analyzeLambdaAndReduceNumberOfCandidatesRegardingOverloadResolutionByLambdaReturnType(call, reducedCandidates);
        if (setAnalyzeLambdaAndReduceNumberOfCandidatesRegardingOverloadResolutionByLambdaReturnType == null) {
            return null;
        }
        Set<Candidate> setChooseMaximallySpecificCandidates$default = ConeCallConflictResolver.chooseMaximallySpecificCandidates$default(this.components.getCallResolver().getConflictResolver(), setAnalyzeLambdaAndReduceNumberOfCandidatesRegardingOverloadResolutionByLambdaReturnType, false, 2, null);
        if (setChooseMaximallySpecificCandidates$default.size() > 1) {
            Set set = setMinus;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator it2 = set.iterator();
                while (it2.hasNext()) {
                    if (setChooseMaximallySpecificCandidates$default.contains((Candidate) it2.next())) {
                        setChooseMaximallySpecificCandidates$default = CollectionsKt.toMutableSet(setChooseMaximallySpecificCandidates$default);
                        setChooseMaximallySpecificCandidates$default.removeAll(arrayList2);
                        Candidate candidate = (Candidate) CollectionsKt.singleOrNull(setChooseMaximallySpecificCandidates$default);
                        if (candidate == null) {
                            break;
                        }
                        candidate.addDiagnostic(CandidateChosenUsingOverloadResolutionByLambdaAnnotation.INSTANCE);
                        break;
                    }
                }
            }
        }
        return setChooseMaximallySpecificCandidates$default;
    }

    private final <T extends FirResolvable & FirStatement> Set<Candidate> analyzeLambdaAndReduceNumberOfCandidatesRegardingOverloadResolutionByLambdaReturnType(T call, Set<Candidate> candidates) {
        Map map;
        Set<Candidate> set = candidates;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                if (!((Candidate) it.next()).isSuccessful()) {
                    return candidates;
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Candidate candidate : set) {
            List<PostponedResolvedAtom> postponedAtoms = candidate.getPostponedAtoms();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : postponedAtoms) {
                PostponedResolvedAtom postponedResolvedAtom = (PostponedResolvedAtom) obj;
                if ((postponedResolvedAtom instanceof ResolvedLambdaAtom) && !postponedResolvedAtom.getAnalyzed()) {
                    arrayList2.add(obj);
                }
            }
            ArrayList<PostponedResolvedAtom> arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            for (PostponedResolvedAtom postponedResolvedAtom2 : arrayList3) {
                Intrinsics.checkNotNull(postponedResolvedAtom2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.inference.ResolvedLambdaAtom");
                arrayList4.add(TuplesKt.to(candidate, (ResolvedLambdaAtom) postponedResolvedAtom2));
            }
            CollectionsKt.addAll(arrayList, arrayList4);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            FirAnonymousFunction reference = ((ResolvedLambdaAtom) ((Pair) obj2).component2()).getReference();
            Object obj3 = linkedHashMap.get(reference);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(reference, obj3);
            }
            ((List) obj3).add(obj2);
        }
        List list = (List) CollectionsKt.singleOrNull(linkedHashMap.values());
        if (list == null || (map = MapsKt.toMap(list)) == null) {
            return null;
        }
        Iterator it2 = map.values().iterator();
        int size = ((ResolvedLambdaAtom) it2.next()).getParameters().size();
        while (it2.hasNext()) {
            if (((ResolvedLambdaAtom) it2.next()).getParameters().size() != size) {
                return null;
            }
        }
        Collection collectionValues = map.values();
        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
            Iterator it3 = collectionValues.iterator();
            while (it3.hasNext()) {
                ConeKotlinType expectedType = ((ResolvedLambdaAtom) it3.next()).getExpectedType();
                if (expectedType == null || !FunctionalTypeUtilsKt.isSomeFunctionType(expectedType, this.session)) {
                    return null;
                }
            }
        }
        FirReference calleeReference = call.getCalleeReference();
        for (Candidate candidate2 : map.keySet()) {
            call.replaceCalleeReference(new FirNamedReferenceWithCandidate(null, candidate2.getCallInfo().getName(), candidate2));
            FirCallCompleter.runCompletionForCall$default(getCallCompleter(), candidate2, ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA, call, ResolveUtilsKt.initialTypeOfCandidate(this.components, candidate2), null, 16, null);
        }
        try {
            Iterator it4 = map.entrySet().iterator();
            Map.Entry entry = (Map.Entry) it4.next();
            Candidate candidate3 = (Candidate) entry.getKey();
            ResolvedLambdaAtom resolvedLambdaAtom = (ResolvedLambdaAtom) entry.getValue();
            TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = candidate3.getSystem().buildCurrentSubstitutor();
            Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
            ConeSubstitutor coneSubstitutor = (ConeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor;
            Collection<ConeKotlinType> inputTypes = resolvedLambdaAtom.getInputTypes();
            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(inputTypes, 10));
            Iterator<T> it5 = inputTypes.iterator();
            while (it5.hasNext()) {
                arrayList5.add(coneSubstitutor.substituteOrSelf((ConeKotlinType) it5.next()));
            }
            ArrayList arrayList6 = arrayList5;
            while (it4.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it4.next();
                Candidate candidate4 = (Candidate) entry2.getKey();
                ResolvedLambdaAtom resolvedLambdaAtom2 = (ResolvedLambdaAtom) entry2.getValue();
                TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor2 = candidate4.getSystem().buildCurrentSubstitutor();
                Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
                ConeSubstitutor coneSubstitutor2 = (ConeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor2;
                Collection<ConeKotlinType> inputTypes2 = resolvedLambdaAtom2.getInputTypes();
                ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(inputTypes2, 10));
                Iterator<T> it6 = inputTypes2.iterator();
                while (it6.hasNext()) {
                    arrayList7.add(coneSubstitutor2.substituteOrSelf((ConeKotlinType) it6.next()));
                }
                if (!Intrinsics.areEqual(arrayList7, arrayList6)) {
                    return null;
                }
            }
            for (Map.Entry entry3 : map.entrySet()) {
                getCallCompleter().prepareLambdaAtomForFactoryPattern((ResolvedLambdaAtom) entry3.getValue(), (Candidate) entry3.getKey());
            }
            Iterator it7 = map.entrySet().iterator();
            Map.Entry entry4 = (Map.Entry) it7.next();
            Candidate candidate5 = (Candidate) entry4.getKey();
            ResolvedLambdaAtom resolvedLambdaAtom3 = (ResolvedLambdaAtom) entry4.getValue();
            PostponedArgumentsAnalyzer postponedArgumentsAnalyzerCreatePostponedArgumentsAnalyzer = getCallCompleter().createPostponedArgumentsAnalyzer(this.components.getTransformer().getResolutionContext());
            call.replaceCalleeReference(new FirNamedReferenceWithCandidate(null, candidate5.getCallInfo().getName(), candidate5));
            ReturnArgumentsAnalysisResult returnArgumentsAnalysisResultAnalyzeLambda = postponedArgumentsAnalyzerCreatePostponedArgumentsAnalyzer.analyzeLambda(candidate5.getSystem(), resolvedLambdaAtom3, candidate5, ConstraintSystemCompletionMode.FULL);
            while (it7.hasNext()) {
                Map.Entry entry5 = (Map.Entry) it7.next();
                Candidate candidate6 = (Candidate) entry5.getKey();
                ResolvedLambdaAtom resolvedLambdaAtom4 = (ResolvedLambdaAtom) entry5.getValue();
                call.replaceCalleeReference(new FirNamedReferenceWithCandidate(null, candidate6.getCallInfo().getName(), candidate6));
                PostponedArgumentsAnalyzer.applyResultsOfAnalyzedLambdaToCandidateSystem$default(postponedArgumentsAnalyzerCreatePostponedArgumentsAnalyzer, candidate6.getSystem(), resolvedLambdaAtom4, candidate6, returnArgumentsAnalysisResultAnalyzeLambda, ConstraintSystemCompletionMode.FULL, null, 32, null);
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            LinkedHashSet linkedHashSet2 = new LinkedHashSet();
            for (Candidate candidate7 : candidates) {
                if (candidate7.isSuccessful()) {
                    linkedHashSet2.add(candidate7);
                } else {
                    linkedHashSet.add(candidate7);
                }
            }
            if (!linkedHashSet2.isEmpty()) {
                linkedHashSet = linkedHashSet2;
            }
            return linkedHashSet;
        } finally {
            call.replaceCalleeReference(calleeReference);
        }
    }
}
