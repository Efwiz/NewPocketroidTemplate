package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirUpperBoundViolatedHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aN\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00132\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u001a*\u0010\f\u001a\u00020\r2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u001a4\u0010\f\u001a\u00020\r2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001b\u001a\u00020\u001aH\u0002\u001a*\u0010 \u001a\u00020\u00182\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00132\u0006\u0010!\u001a\u00020\"\u001a\u001c\u0010#\u001a\u0004\u0018\u00010\u001f*\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\"\u001a\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013*\b\u0012\u0004\u0012\u00020&0\u0013\u001a\u0014\u0010'\u001a\u00020\u0016*\u00020\u00162\b\u0010(\u001a\u0004\u0018\u00010)\"!\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\u0006\u001a\u0004\b\t\u0010\n¨\u0006*"}, d2 = {"originalProjection", "Lorg/jetbrains/kotlin/fir/analysis/checkers/OriginalProjectionTypeAttribute;", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getOriginalProjection", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/analysis/checkers/OriginalProjectionTypeAttribute;", "originalProjection$delegate", "Lkotlin/properties/ReadOnlyProperty;", "sourceAttribute", "Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceAttribute;", "getSourceAttribute", "(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceAttribute;", "sourceAttribute$delegate", "checkUpperBoundViolated", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "typeParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "typeArguments", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "isReportExpansionError", "", "isIgnoreTypeParameters", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "notExpandedType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "createSubstitutorForUpperBoundViolationCheck", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "fullyExpandedTypeWithSource", "useSiteSession", "toTypeArgumentsWithSourceInfo", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "withSource", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirTypeRefSource;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirUpperBoundViolatedHelpersKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirUpperBoundViolatedHelpersKt.class, "checkers"), "originalProjection", "getOriginalProjection(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/analysis/checkers/OriginalProjectionTypeAttribute;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirUpperBoundViolatedHelpersKt.class, "checkers"), "sourceAttribute", "getSourceAttribute(Lorg/jetbrains/kotlin/fir/types/ConeAttributes;)Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceAttribute;"))};
    private static final ReadOnlyProperty originalProjection$delegate;
    private static final ReadOnlyProperty sourceAttribute$delegate;

    public static /* synthetic */ void checkUpperBoundViolated$default(FirTypeRef firTypeRef, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        checkUpperBoundViolated(firTypeRef, checkerContext, diagnosticReporter, z);
    }

    private static final void checkUpperBoundViolated(final FirTypeRef firTypeRef, ConeClassLikeType coneClassLikeType, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, boolean z) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedTypeWithSource;
        if (coneClassLikeType.getTypeArguments().length == 0) {
            return;
        }
        if (firTypeRef == null || !((coneClassLikeTypeFullyExpandedTypeWithSource = fullyExpandedTypeWithSource(coneClassLikeType, firTypeRef, checkerContext.getSession())) == null || (coneClassLikeType = (ConeClassLikeType) TypeUtilsKt.withArguments(coneClassLikeTypeFullyExpandedTypeWithSource, new Function1<ConeTypeProjection, ConeTypeProjection>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.FirUpperBoundViolatedHelpersKt$checkUpperBoundViolated$type$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeTypeProjection invoke(ConeTypeProjection it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FirUpperBoundViolatedHelpersKt.withSource(it, new FirTypeRefSource(null, firTypeRef.getSource()));
            }
        })) == null)) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), checkerContext.getSession());
            FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
            if (firRegularClassSymbol == null) {
                return;
            }
            List<FirTypeParameterSymbol> typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols();
            if (typeParameterSymbols.isEmpty()) {
                return;
            }
            checkUpperBoundViolated(checkerContext, diagnosticReporter, typeParameterSymbols, ArraysKt.toList(coneClassLikeType.getTypeArguments()), new FE10LikeConeSubstitutor(MapsKt.toMap(CollectionsKt.zip(typeParameterSymbols, coneClassLikeType.getTypeArguments())), checkerContext.getSession()), true, z);
        }
    }

    static {
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(OriginalProjectionTypeAttribute.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        originalProjection$delegate = readOnlyPropertyGenerateNullableAccessor;
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor2 = ConeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(SourceAttribute.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor2, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
        sourceAttribute$delegate = readOnlyPropertyGenerateNullableAccessor2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OriginalProjectionTypeAttribute getOriginalProjection(ConeAttributes coneAttributes) {
        return (OriginalProjectionTypeAttribute) originalProjection$delegate.getValue(coneAttributes, $$delegatedProperties[0]);
    }

    public static final List<ConeTypeProjection> toTypeArgumentsWithSourceInfo(List<? extends FirTypeProjection> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<? extends FirTypeProjection> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (FirTypeProjection firTypeProjection : list2) {
            ConeTypeProjection coneTypeProjection = FirTypeUtilsKt.toConeTypeProjection(firTypeProjection);
            FirTypeRef typeRef = null;
            FirTypeProjectionWithVariance firTypeProjectionWithVariance = firTypeProjection instanceof FirTypeProjectionWithVariance ? (FirTypeProjectionWithVariance) firTypeProjection : null;
            if (firTypeProjectionWithVariance != null) {
                typeRef = firTypeProjectionWithVariance.getTypeRef();
            }
            arrayList.add(withSource(coneTypeProjection, new FirTypeRefSource(typeRef, firTypeProjection.getSource())));
        }
        return arrayList;
    }

    public static final ConeSubstitutor createSubstitutorForUpperBoundViolationCheck(List<FirTypeParameterSymbol> typeParameters, List<? extends ConeTypeProjection> typeArguments, FirSession session) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(session, "session");
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(typeParameters);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            Object value = indexedValue.getValue();
            ConeTypeProjection coneTypeProjection = typeArguments.get(indexedValue.getIndex());
            Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
            Pair pair = new Pair(value, (ConeKotlinType) coneTypeProjection);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return SubstitutorsKt.substitutorByMap(linkedHashMap, session);
    }

    public static final void checkUpperBoundViolated(CheckerContext context, DiagnosticReporter reporter, List<FirTypeParameterSymbol> typeParameters, List<? extends ConeTypeProjection> typeArguments, ConeSubstitutor substitutor, boolean z, boolean z2) {
        int i;
        ConeInferenceContext coneInferenceContext;
        ConeKotlinType coneKotlinType;
        KtDiagnosticFactory2<ConeKotlinType, ConeKotlinType> upper_bound_violated;
        ConeAttributes attributes;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        int iMin = Math.min(typeParameters.size(), typeArguments.size());
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
        int i2 = 0;
        while (i2 < iMin) {
            ConeKotlinType type = ConeTypeProjectionKt.getType(typeArguments.get(i2));
            SourceAttribute sourceAttribute = (type == null || (attributes = type.getAttributes()) == null) ? null : getSourceAttribute(attributes);
            FirTypeRef typeRef = sourceAttribute != null ? sourceAttribute.getTypeRef() : null;
            KtSourceElement source = sourceAttribute != null ? sourceAttribute.getSource() : null;
            if (type == null || source == null) {
                i = i2;
                coneInferenceContext = typeContext;
            } else {
                if (!z2 || (type.getTypeArguments().length == 0 && !(type instanceof ConeTypeParameterType))) {
                    List<FirResolvedTypeRef> resolvedBounds = typeParameters.get(i2).getResolvedBounds();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
                    Iterator<T> it = resolvedBounds.iterator();
                    while (it.hasNext()) {
                        arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
                    }
                    KotlinTypeMarker kotlinTypeMarkerIntersectTypes = typeContext.intersectTypes((List<? extends KotlinTypeMarker>) arrayList);
                    ConeKotlinType coneKotlinType2 = kotlinTypeMarkerIntersectTypes instanceof ConeKotlinType ? (ConeKotlinType) kotlinTypeMarkerIntersectTypes : null;
                    if (coneKotlinType2 != null) {
                        ConeKotlinType coneKotlinTypeSubstituteOrSelf = substitutor.substituteOrSelf(coneKotlinType2);
                        i = i2;
                        coneInferenceContext = typeContext;
                        if (!AbstractTypeChecker.INSTANCE.isSubtypeOf((TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) type, (KotlinTypeMarker) coneKotlinTypeSubstituteOrSelf, true)) {
                            if (z && typeRef == null) {
                                upper_bound_violated = FirErrors.INSTANCE.getUPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION();
                            } else {
                                upper_bound_violated = FirErrors.INSTANCE.getUPPER_BOUND_VIOLATED();
                            }
                            typeRef = typeRef;
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, upper_bound_violated, coneKotlinTypeSubstituteOrSelf, type.getType(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                            coneKotlinType = type;
                        }
                    } else {
                        i = i2;
                        coneInferenceContext = typeContext;
                    }
                    coneKotlinType = type;
                } else {
                    typeRef = typeRef;
                    coneKotlinType = type;
                    i = i2;
                    coneInferenceContext = typeContext;
                }
                if (coneKotlinType instanceof ConeClassLikeType) {
                    checkUpperBoundViolated(typeRef, (ConeClassLikeType) coneKotlinType, context, reporter, z2);
                }
            }
            i2 = i + 1;
            typeContext = coneInferenceContext;
        }
    }

    public static final ConeClassLikeType fullyExpandedTypeWithSource(ConeClassLikeType coneClassLikeType, FirTypeRef typeRef, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        List<FirTypeRefSource> listExtractArgumentsTypeRefAndSource = FirHelpersKt.extractArgumentsTypeRefAndSource(typeRef);
        if (listExtractArgumentsTypeRefAndSource == null || listExtractArgumentsTypeRefAndSource.size() != coneClassLikeType.getTypeArguments().length) {
            return null;
        }
        ConeTypeProjection[] typeArguments = coneClassLikeType.getTypeArguments();
        int length = typeArguments.length;
        List<FirTypeRefSource> list = listExtractArgumentsTypeRefAndSource;
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), length));
        int i = 0;
        for (Object obj : list) {
            if (i >= length) {
                break;
            }
            arrayList.add(withSource(typeArguments[i], (FirTypeRefSource) obj));
            i++;
        }
        return TypeExpansionUtilsKt.fullyExpandedType$default(ConeTypeUtilsKt.withArguments(coneClassLikeType, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0])), useSiteSession, null, 2, null);
    }

    private static final SourceAttribute getSourceAttribute(ConeAttributes coneAttributes) {
        return (SourceAttribute) sourceAttribute$delegate.getValue(coneAttributes, $$delegatedProperties[1]);
    }

    public static final ConeTypeProjection withSource(ConeTypeProjection coneTypeProjection, FirTypeRefSource firTypeRefSource) {
        Intrinsics.checkNotNullParameter(coneTypeProjection, "<this>");
        if (firTypeRefSource == null || !(coneTypeProjection instanceof ConeKotlinTypeProjection)) {
            return coneTypeProjection;
        }
        ConeKotlinTypeProjection coneKotlinTypeProjection = (ConeKotlinTypeProjection) coneTypeProjection;
        return ConeTypeProjectionKt.replaceType(coneKotlinTypeProjection, TypeUtilsKt.withAttributes(coneKotlinTypeProjection.getType(), ConeAttributes.INSTANCE.create(CollectionsKt.listOf(new SourceAttribute(firTypeRefSource))).add(coneKotlinTypeProjection.getType().getAttributes())));
    }

    public static final void checkUpperBoundViolated(FirTypeRef firTypeRef, CheckerContext context, DiagnosticReporter reporter, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (firTypeRef != null) {
            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
            if (coneClassLikeType == null) {
                return;
            }
            checkUpperBoundViolated(firTypeRef, coneClassLikeType, context, reporter, z);
        }
    }
}
