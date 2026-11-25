package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirOverrideUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignature;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;

/* JADX INFO: compiled from: ConeOverloadConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J,\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002JD\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J*\u0010\u0016\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u000fH\u0002J>\u0010\u0018\u001a\u00020\u000f2\u0010\u0010\u0019\u001a\f\u0012\u0004\u0012\u00020\u000b0\u001aj\u0002`\u001b2\u0010\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\u000b0\u001aj\u0002`\u001b2\u0006\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u000fH\u0002J$\u0010\u001d\u001a\u00020\u000f2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aH\u0002J*\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u001aj\u0004\u0018\u0001`\u001b*\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u001aj\u0002`\u001b0\u001fH\u0002J\u0014\u0010 \u001a\u00020\u000f*\u00020\u000b2\u0006\u0010!\u001a\u00020\u000bH\u0002¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ConeOverloadConflictResolver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractConeCallConflictResolver;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "inferenceComponents", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "transformerComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "(Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;)V", "chooseCandidatesWithMostSpecificInvokeReceiver", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "candidates", "chooseMaximallySpecificCandidates", "discriminateAbstracts", "", "discriminateGenerics", "discriminateSAMs", "discriminateSuspendConversions", "discriminateByUnwrappedSmartCastOrigin", "filterOverrides", "candidateSet", "findMaximallySpecificCall", "useOriginalSamTypes", "isNotLessSpecificCallWithArgumentMapping", "call1", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateSignature;", "call2", "isOfNotLessSpecificShape", "exactMaxWith", "", "overrides", "other", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeOverloadConflictResolver extends AbstractConeCallConflictResolver {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeOverloadConflictResolver(TypeSpecificityComparator specificityComparator, InferenceComponents inferenceComponents, BodyResolveComponents transformerComponents) {
        super(specificityComparator, inferenceComponents, transformerComponents, false);
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        Intrinsics.checkNotNullParameter(inferenceComponents, "inferenceComponents");
        Intrinsics.checkNotNullParameter(transformerComponents, "transformerComponents");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver
    public Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        return chooseMaximallySpecificCandidates(candidates, discriminateAbstracts, true);
    }

    private final Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts, boolean discriminateGenerics) {
        if (candidates.size() == 1) {
            return candidates;
        }
        if (((Candidate) CollectionsKt.first(candidates)).getCallInfo().getCandidateForCommonInvokeReceiver() != null) {
            candidates = chooseCandidatesWithMostSpecificInvokeReceiver(candidates);
        }
        return chooseMaximallySpecificCandidates(filterOverrides(candidates), discriminateGenerics, discriminateAbstracts, true, true, true);
    }

    private final Set<Candidate> filterOverrides(Set<Candidate> candidateSet) {
        if (candidateSet.size() <= 1) {
            return candidateSet;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Candidate candidate : candidateSet) {
            Iterator it = linkedHashSet.iterator();
            while (true) {
                if (it.hasNext()) {
                    Candidate candidate2 = (Candidate) it.next();
                    if (overrides(candidate, candidate2)) {
                        it.remove();
                    } else if (overrides(candidate2, candidate)) {
                        break;
                    }
                } else {
                    linkedHashSet.add(candidate);
                    break;
                }
            }
        }
        if (linkedHashSet.isEmpty()) {
            throw new IllegalArgumentException(("All candidates filtered out from " + candidateSet).toString());
        }
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean overrides(Candidate candidate, Candidate candidate2) {
        Function3 function3;
        if (!(candidate.getSymbol() instanceof FirCallableSymbol) || !(candidate2.getSymbol() instanceof FirCallableSymbol)) {
            return false;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) ((FirCallableSymbol) candidate2.getSymbol()).getFir();
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            }
            firCallableDeclaration = originalForSubstitutionOverrideAttr;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        if (symbol == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
        }
        FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) ((FirCallableSymbol) candidate.getSymbol()).getFir();
        while (true) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration2) || (firCallableDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration2) : null;
            if (originalForSubstitutionOverrideAttr2 == null) {
                break;
            }
            firCallableDeclaration2 = originalForSubstitutionOverrideAttr2;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration2.getSymbol();
        if (symbol2 != null) {
            if (Intrinsics.areEqual(symbol2, symbol)) {
                return true;
            }
            FirScope originScope = candidate.getOriginScope();
            FirTypeScope firTypeScope = originScope instanceof FirTypeScope ? (FirTypeScope) originScope : null;
            if (firTypeScope == null) {
                return false;
            }
            FirBasedSymbol<?> symbol3 = candidate.getSymbol();
            if (symbol3 instanceof FirNamedFunctionSymbol) {
                ConeOverloadConflictResolver$overrides$overriddenProducer$1 coneOverloadConflictResolver$overrides$overriddenProducer$1 = ConeOverloadConflictResolver$overrides$overriddenProducer$1.INSTANCE;
                Intrinsics.checkNotNull(coneOverloadConflictResolver$overrides$overriddenProducer$1, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.Function3<org.jetbrains.kotlin.fir.scopes.FirTypeScope, org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>, kotlin.Function1<org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>, org.jetbrains.kotlin.fir.scopes.ProcessorAction>, org.jetbrains.kotlin.fir.scopes.ProcessorAction>{ org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.ProcessAllOverridden<org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>> }");
                function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(coneOverloadConflictResolver$overrides$overriddenProducer$1, 3);
            } else {
                if (!(symbol3 instanceof FirPropertySymbol)) {
                    return false;
                }
                ConeOverloadConflictResolver$overrides$overriddenProducer$2 coneOverloadConflictResolver$overrides$overriddenProducer$2 = ConeOverloadConflictResolver$overrides$overriddenProducer$2.INSTANCE;
                Intrinsics.checkNotNull(coneOverloadConflictResolver$overrides$overriddenProducer$2, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.Function3<org.jetbrains.kotlin.fir.scopes.FirTypeScope, org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>, kotlin.Function1<org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>, org.jetbrains.kotlin.fir.scopes.ProcessorAction>, org.jetbrains.kotlin.fir.scopes.ProcessorAction>{ org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.ProcessAllOverridden<org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>> }");
                function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(coneOverloadConflictResolver$overrides$overriddenProducer$2, 3);
            }
            return FirOverrideUtilsKt.overrides(new MemberWithBaseScope((FirCallableSymbol) candidate.getSymbol(), firTypeScope), symbol, function3);
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
    }

    private final Set<Candidate> chooseCandidatesWithMostSpecificInvokeReceiver(Set<Candidate> candidates) {
        Set<Candidate> set = candidates;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Candidate candidate : set) {
            Candidate candidateForCommonInvokeReceiver = candidate.getCallInfo().getCandidateForCommonInvokeReceiver();
            if (candidateForCommonInvokeReceiver == null) {
                throw new IllegalStateException(("If one candidate within a group is property+invoke, other should be the same, but " + candidate + " found").toString());
            }
            linkedHashSet.add(candidateForCommonInvokeReceiver);
        }
        Candidate candidate2 = (Candidate) CollectionsKt.singleOrNull(chooseMaximallySpecificCandidates(linkedHashSet, false, false));
        if (candidate2 == null) {
            return candidates;
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (Object obj : set) {
            if (Intrinsics.areEqual(((Candidate) obj).getCallInfo().getCandidateForCommonInvokeReceiver(), candidate2)) {
                linkedHashSet2.add(obj);
            }
        }
        return linkedHashSet2;
    }

    private final Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateGenerics, boolean discriminateAbstracts, boolean discriminateSAMs, boolean discriminateSuspendConversions, boolean discriminateByUnwrappedSmartCastOrigin) {
        Candidate candidateFindMaximallySpecificCall;
        Candidate candidateFindMaximallySpecificCall$default;
        Candidate candidateFindMaximallySpecificCall$default2 = findMaximallySpecificCall$default(this, candidates, false, false, 4, null);
        if (candidateFindMaximallySpecificCall$default2 != null) {
            return SetsKt.setOf(candidateFindMaximallySpecificCall$default2);
        }
        if (discriminateGenerics && (candidateFindMaximallySpecificCall$default = findMaximallySpecificCall$default(this, candidates, true, false, 4, null)) != null) {
            return SetsKt.setOf(candidateFindMaximallySpecificCall$default);
        }
        if (discriminateSAMs) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (Object obj : candidates) {
                if (!((Candidate) obj).getUsesSAM()) {
                    linkedHashSet.add(obj);
                }
            }
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            int size = linkedHashSet2.size();
            if (size == 1) {
                return linkedHashSet2;
            }
            if (size != 0 && size != candidates.size()) {
                return chooseMaximallySpecificCandidates(linkedHashSet2, discriminateGenerics, discriminateAbstracts, false, discriminateSuspendConversions, discriminateByUnwrappedSmartCastOrigin);
            }
        }
        if (discriminateSuspendConversions) {
            LinkedHashSet linkedHashSet3 = new LinkedHashSet();
            for (Object obj2 : candidates) {
                if (!((Candidate) obj2).getUsesFunctionConversion()) {
                    linkedHashSet3.add(obj2);
                }
            }
            LinkedHashSet linkedHashSet4 = linkedHashSet3;
            int size2 = linkedHashSet4.size();
            if (size2 == 1) {
                return linkedHashSet4;
            }
            if (size2 != 0 && size2 != candidates.size()) {
                return chooseMaximallySpecificCandidates(linkedHashSet4, discriminateGenerics, discriminateAbstracts, discriminateSAMs, false, discriminateByUnwrappedSmartCastOrigin);
            }
        }
        if (discriminateAbstracts) {
            LinkedHashSet linkedHashSet5 = new LinkedHashSet();
            for (Object obj3 : candidates) {
                FirDeclaration fir = ((Candidate) obj3).getSymbol().getFir();
                FirMemberDeclaration firMemberDeclaration = fir instanceof FirMemberDeclaration ? (FirMemberDeclaration) fir : null;
                if ((firMemberDeclaration != null ? firMemberDeclaration.getStatus().getModality() : null) != Modality.ABSTRACT) {
                    linkedHashSet5.add(obj3);
                }
            }
            LinkedHashSet linkedHashSet6 = linkedHashSet5;
            int size3 = linkedHashSet6.size();
            if (size3 == 1) {
                return linkedHashSet6;
            }
            if (size3 != 0 && size3 != candidates.size()) {
                return chooseMaximallySpecificCandidates(linkedHashSet6, discriminateGenerics, false, discriminateSAMs, discriminateSuspendConversions, discriminateByUnwrappedSmartCastOrigin);
            }
        }
        if (discriminateByUnwrappedSmartCastOrigin) {
            LinkedHashSet linkedHashSet7 = new LinkedHashSet();
            for (Object obj4 : candidates) {
                if (!((Candidate) obj4).getIsFromOriginalTypeInPresenceOfSmartCast()) {
                    linkedHashSet7.add(obj4);
                }
            }
            LinkedHashSet linkedHashSet8 = linkedHashSet7;
            int size4 = linkedHashSet8.size();
            if (size4 == 1) {
                return linkedHashSet8;
            }
            if (size4 != 0 && size4 != candidates.size()) {
                return chooseMaximallySpecificCandidates(linkedHashSet8, discriminateGenerics, discriminateAbstracts, discriminateSAMs, discriminateSuspendConversions, false);
            }
        }
        LinkedHashSet linkedHashSet9 = new LinkedHashSet();
        for (Object obj5 : candidates) {
            if (((Candidate) obj5).getUsesSAM()) {
                linkedHashSet9.add(obj5);
            }
        }
        return (linkedHashSet9.isEmpty() || (candidateFindMaximallySpecificCall = findMaximallySpecificCall(candidates, false, true)) == null) ? candidates : SetsKt.setOf(candidateFindMaximallySpecificCall);
    }

    static /* synthetic */ Candidate findMaximallySpecificCall$default(ConeOverloadConflictResolver coneOverloadConflictResolver, Set set, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        return coneOverloadConflictResolver.findMaximallySpecificCall(set, z, z2);
    }

    private final Candidate findMaximallySpecificCall(Set<Candidate> candidates, boolean discriminateGenerics, boolean useOriginalSamTypes) {
        if (candidates.size() <= 1) {
            return (Candidate) CollectionsKt.singleOrNull(candidates);
        }
        Set<Candidate> set = candidates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(createFlatSignature((Candidate) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            FlatSignature<Candidate> flatSignature = (FlatSignature) obj;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        FlatSignature<Candidate> flatSignature2 = (FlatSignature) it2.next();
                        if (flatSignature != flatSignature2 && !isNotLessSpecificCallWithArgumentMapping(flatSignature, flatSignature2, discriminateGenerics, useOriginalSamTypes)) {
                            break;
                        }
                    }
                }
            }
            arrayList3.add(obj);
        }
        FlatSignature<Candidate> flatSignatureExactMaxWith = exactMaxWith(arrayList3);
        if (flatSignatureExactMaxWith != null) {
            return flatSignatureExactMaxWith.getOrigin();
        }
        return null;
    }

    private final boolean isNotLessSpecificCallWithArgumentMapping(FlatSignature<Candidate> call1, FlatSignature<Candidate> call2, boolean discriminateGenerics, boolean useOriginalSamTypes) {
        return compareCallsByUsedArguments(call1, call2, discriminateGenerics, useOriginalSamTypes);
    }

    private final FlatSignature<Candidate> exactMaxWith(List<FlatSignature<Candidate>> list) {
        FlatSignature<Candidate> flatSignature = null;
        for (FlatSignature<Candidate> flatSignature2 : list) {
            if (flatSignature == null || isOfNotLessSpecificShape(flatSignature2, flatSignature)) {
                flatSignature = flatSignature2;
            }
        }
        if (flatSignature == null) {
            return null;
        }
        List<FlatSignature<Candidate>> list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            for (FlatSignature<Candidate> flatSignature3 : list2) {
                if (!Intrinsics.areEqual(flatSignature3, flatSignature) && isOfNotLessSpecificShape(flatSignature3, flatSignature)) {
                    return null;
                }
            }
        }
        return flatSignature;
    }

    private final boolean isOfNotLessSpecificShape(FlatSignature<Candidate> call1, FlatSignature<Candidate> call2) {
        boolean hasVarargs = call1.getHasVarargs();
        boolean hasVarargs2 = call2.getHasVarargs();
        if (!hasVarargs || hasVarargs2) {
            return (!hasVarargs && hasVarargs2) || call1.getNumDefaults() <= call2.getNumDefaults();
        }
        return false;
    }
}
