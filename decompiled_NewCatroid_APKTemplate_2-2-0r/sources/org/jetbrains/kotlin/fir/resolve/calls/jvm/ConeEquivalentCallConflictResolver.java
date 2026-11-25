package org.jetbrains.kotlin.fir.resolve.calls.jvm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractConeCallConflictResolver;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.scopes.impl.FirStandardOverrideChecker;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;

/* JADX INFO: compiled from: ConeEquivalentCallConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u000bH\u0002J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00162\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001aH\u0002R\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/jvm/ConeEquivalentCallConflictResolver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractConeCallConflictResolver;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "inferenceComponents", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "transformerComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "(Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;)V", "mappedArgumentsOrderRepresentation", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getMappedArgumentsOrderRepresentation", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)[I", "areEquivalentTopLevelCallables", "", "first", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "firstCandidate", "second", "secondCandidate", "chooseMaximallySpecificCandidates", "", "candidates", "discriminateAbstracts", "filterOutEquivalentCalls", "", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeEquivalentCallConflictResolver extends AbstractConeCallConflictResolver {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeEquivalentCallConflictResolver(TypeSpecificityComparator specificityComparator, InferenceComponents inferenceComponents, BodyResolveComponents transformerComponents) {
        super(specificityComparator, inferenceComponents, transformerComponents, true);
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        Intrinsics.checkNotNullParameter(inferenceComponents, "inferenceComponents");
        Intrinsics.checkNotNullParameter(transformerComponents, "transformerComponents");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver
    public Set<Candidate> chooseMaximallySpecificCandidates(Set<Candidate> candidates, boolean discriminateAbstracts) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        return filterOutEquivalentCalls(candidates);
    }

    private final Set<Candidate> filterOutEquivalentCalls(Collection<Candidate> candidates) {
        List<Candidate> listSortedWith = CollectionsKt.sortedWith(candidates, new Comparator() { // from class: org.jetbrains.kotlin.fir.resolve.calls.jvm.ConeEquivalentCallConflictResolver$filterOutEquivalentCalls$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Boolean.valueOf(((Candidate) t).getSymbol().getFir().getSource() == null), Boolean.valueOf(((Candidate) t2).getSymbol().getFir().getSource() == null));
            }
        });
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Candidate candidate : listSortedWith) {
            FirDeclaration fir = candidate.getSymbol().getFir();
            if (fir instanceof FirCallableDeclaration) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) fir;
                if (ClassMembersKt.containingClassLookupTag(firCallableDeclaration.getSymbol()) == null) {
                    Iterator it = linkedHashSet.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Candidate candidate2 = (Candidate) it.next();
                            FirDeclaration fir2 = candidate2.getSymbol().getFir();
                            if (fir2 instanceof FirCallableDeclaration) {
                                FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) fir2;
                                if (ClassMembersKt.containingClassLookupTag(firCallableDeclaration2.getSymbol()) == null && areEquivalentTopLevelCallables(firCallableDeclaration, candidate, firCallableDeclaration2, candidate2)) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            linkedHashSet.add(candidate);
        }
        return linkedHashSet;
    }

    private final boolean areEquivalentTopLevelCallables(FirCallableDeclaration first, Candidate firstCandidate, FirCallableDeclaration second, Candidate secondCandidate) {
        if (!Intrinsics.areEqual(first.getSymbol().getCallableId(), second.getSymbol().getCallableId())) {
            return false;
        }
        if ((Intrinsics.areEqual(first.getModuleData(), second.getModuleData()) && first.getModuleData().getSession().getKind() == FirSession.Kind.Source) || first.getStatus().isExpect() != second.getStatus().isExpect() || (first instanceof FirVariable) != (second instanceof FirVariable) || !Arrays.equals(getMappedArgumentsOrderRepresentation(firstCandidate), getMappedArgumentsOrderRepresentation(secondCandidate))) {
            return false;
        }
        FirStandardOverrideChecker firStandardOverrideChecker = new FirStandardOverrideChecker(getInferenceComponents().getSession());
        if ((first instanceof FirProperty) && (second instanceof FirProperty)) {
            if (!firStandardOverrideChecker.isOverriddenProperty(first, (FirProperty) second, true) || !firStandardOverrideChecker.isOverriddenProperty(second, (FirProperty) first, true)) {
                return false;
            }
        } else {
            if (!(first instanceof FirSimpleFunction) || !(second instanceof FirSimpleFunction)) {
                return false;
            }
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) first;
            FirSimpleFunction firSimpleFunction2 = (FirSimpleFunction) second;
            if (!firStandardOverrideChecker.isOverriddenFunction(firSimpleFunction, firSimpleFunction2, true) || !firStandardOverrideChecker.isOverriddenFunction(firSimpleFunction2, firSimpleFunction, true)) {
                return false;
            }
        }
        return true;
    }

    private final int[] getMappedArgumentsOrderRepresentation(Candidate candidate) {
        FirDeclaration fir = candidate.getSymbol().getFir();
        FirFunction firFunction = fir instanceof FirFunction ? (FirFunction) fir : null;
        if (firFunction == null) {
            return null;
        }
        List<FirValueParameter> valueParameters = firFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        int i = 0;
        int i2 = 0;
        for (Object obj : valueParameters) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(TuplesKt.to((FirValueParameter) obj, Integer.valueOf(i2)));
            i2 = i3;
        }
        Map map = MapsKt.toMap(arrayList);
        LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
        if (argumentMapping == null) {
            return null;
        }
        int size = argumentMapping.size() + 1;
        int[] iArr = new int[size];
        for (int i4 = 0; i4 < size; i4++) {
            iArr[i4] = firFunction.getValueParameters().size();
        }
        Iterator<FirValueParameter> it = argumentMapping.values().iterator();
        while (it.hasNext()) {
            i++;
            Integer num = (Integer) map.get(it.next());
            if (num == null) {
                throw new IllegalStateException("Unmapped argument in arguments mapping".toString());
            }
            iArr[i] = num.intValue();
        }
        return iArr;
    }
}
