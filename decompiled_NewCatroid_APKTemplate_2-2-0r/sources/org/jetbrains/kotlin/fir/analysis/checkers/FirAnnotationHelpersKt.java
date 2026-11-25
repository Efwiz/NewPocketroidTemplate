package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirRetentionAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirClassReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.references.FirFromMissingDependenciesNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.UseSiteTargetsList;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;

/* JADX INFO: compiled from: FirAnnotationHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000p\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a8\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001a.\u0010\u0003\u001a\u00020\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001a\u0016\u0010\u0013\u001a\u00020\u0014*\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u0019*\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u0012*\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u001d2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u0019*\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0000\u001a\u0016\u0010\u001f\u001a\u00020 *\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u001c\u0010!\u001a\u0004\u0018\u00010\u0006*\u00020\u00102\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\u001a\u0018\u0010\"\u001a\u0004\u0018\u00010 *\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0018\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012*\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f\u001a\u0014\u0010$\u001a\u0004\u0018\u00010\n*\u00020%2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0018\u0010$\u001a\u0004\u0018\u00010\n*\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0012\u0010&\u001a\u00020\u0014*\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"defaultAnnotationTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "checkRepeatedAnnotation", "", "useSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "existingTargetsForAnnotation", "", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "annotations", "", "containsRepeatableAnnotation", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "extractClassFromArgument", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "extractClassesFromArgument", "getAllowedAnnotationTargets", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getAnnotationClassForOptInMarker", "getAnnotationRetention", "Lkotlin/annotation/AnnotationRetention;", "getDefaultUseSiteTarget", "getExplicitAnnotationRetention", "getImplicitUseSiteTargetList", "getTargetAnnotation", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isRepeatable", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAnnotationHelpersKt {
    private static final Set<KotlinTarget> defaultAnnotationTargets = KotlinTarget.INSTANCE.getDEFAULT_TARGET_SET();

    public static final Set<KotlinTarget> getAllowedAnnotationTargets(FirAnnotation firAnnotation, FirSession session) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firAnnotation.getAnnotationTypeRef() instanceof FirErrorTypeRef) {
            return ArraysKt.toSet(KotlinTarget.values());
        }
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        if (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null)) == null || (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) == null || (symbol = LookupTagUtilsKt.toSymbol(lookupTag, session)) == null) {
            return defaultAnnotationTargets;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, FirResolvePhase.BODY_RESOLVE);
        return getAllowedAnnotationTargets(symbol, session);
    }

    public static final FirRegularClassSymbol getAnnotationClassForOptInMarker(FirAnnotation firAnnotation, FirSession session) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) {
            return null;
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(lookupTag, session);
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (firRegularClassSymbol == null || FirAnnotationUtilsKt.getAnnotationByClassId(firRegularClassSymbol, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), session) == null) {
            return null;
        }
        return firRegularClassSymbol;
    }

    public static final Set<KotlinTarget> getAllowedAnnotationTargets(FirRegularClass firRegularClass, FirSession session) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return getAllowedAnnotationTargets(firRegularClass.getSymbol(), session);
    }

    /* JADX WARN: Code duplicated, block: B:40:0x008e A[PHI: r2
      0x008e: PHI (r2v5 java.lang.String) = (r2v3 java.lang.String), (r2v8 java.lang.String) binds: [B:38:0x008a, B:28:0x0071] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:42:0x0099  */
    /* JADX WARN: Code duplicated, block: B:45:0x00a6 A[LOOP:1: B:41:0x0097->B:45:0x00a6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:55:0x008c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:56:0x00a9 A[EDGE_INSN: B:56:0x00a9->B:46:0x00a9 BREAK  A[LOOP:1: B:41:0x0097->B:45:0x00a6], SYNTHETIC] */
    public static final Set<KotlinTarget> getAllowedAnnotationTargets(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession session) {
        String strAsString;
        Name name2;
        KotlinTarget kotlinTarget;
        KotlinTarget[] kotlinTargetArrValues;
        int length;
        int i;
        FirResolvedNamedReference resolved;
        Name name3;
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassLikeSymbol, FirResolvePhase.ANNOTATIONS_ARGUMENTS_MAPPING);
        FirAnnotation targetAnnotation = getTargetAnnotation(firClassLikeSymbol, session);
        if (targetAnnotation == null) {
            return defaultAnnotationTargets;
        }
        FirExpression firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(targetAnnotation, StandardClassIds.Annotations.ParameterNames.INSTANCE.getTargetAllowedTargets());
        List<FirExpression> listUnwrapAndFlattenArgument = firExpressionFindArgumentByName != null ? FirExpressionUtilKt.unwrapAndFlattenArgument(firExpressionFindArgumentByName) : null;
        if (listUnwrapAndFlattenArgument == null) {
            listUnwrapAndFlattenArgument = CollectionsKt.emptyList();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (FirExpression firExpression : listUnwrapAndFlattenArgument) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
            FirReference calleeReference = firQualifiedAccessExpression != null ? firQualifiedAccessExpression.getCalleeReference() : null;
            if (calleeReference == null || (resolved = FirReferenceUtilsKt.getResolved(calleeReference)) == null || (name3 = resolved.getName()) == null || (strAsString = name3.asString()) == null) {
                FirFromMissingDependenciesNamedReference firFromMissingDependenciesNamedReference = calleeReference instanceof FirFromMissingDependenciesNamedReference ? (FirFromMissingDependenciesNamedReference) calleeReference : null;
                strAsString = (firFromMissingDependenciesNamedReference == null || (name2 = firFromMissingDependenciesNamedReference.getName()) == null) ? null : name2.asString();
                if (strAsString == null) {
                    kotlinTarget = null;
                    break;
                }
                Intrinsics.checkNotNull(strAsString);
                kotlinTargetArrValues = KotlinTarget.values();
                length = kotlinTargetArrValues.length;
                i = 0;
                while (true) {
                    if (i < length) {
                        kotlinTarget = null;
                        break;
                    }
                    kotlinTarget = kotlinTargetArrValues[i];
                    if (Intrinsics.areEqual(kotlinTarget.name(), strAsString)) {
                        break;
                    }
                    i++;
                }
            } else {
                Intrinsics.checkNotNull(strAsString);
                kotlinTargetArrValues = KotlinTarget.values();
                length = kotlinTargetArrValues.length;
                i = 0;
                while (true) {
                    if (i < length) {
                        kotlinTarget = null;
                        break;
                    }
                    kotlinTarget = kotlinTargetArrValues[i];
                    if (Intrinsics.areEqual(kotlinTarget.name(), strAsString)) {
                        break;
                        break;
                    }
                    i++;
                }
            }
            if (kotlinTarget != null) {
                linkedHashSet.add(kotlinTarget);
            }
        }
        return linkedHashSet;
    }

    public static final FirAnnotation getTargetAnnotation(FirDeclaration firDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, StandardClassIds.Annotations.INSTANCE.getTarget(), session);
    }

    public static final FirAnnotation getTargetAnnotation(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol, StandardClassIds.Annotations.INSTANCE.getTarget(), session);
    }

    public static final List<FirRegularClassSymbol> extractClassesFromArgument(FirExpression firExpression, FirSession session) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        List<FirExpression> listUnwrapAndFlattenArgument = FirExpressionUtilKt.unwrapAndFlattenArgument(firExpression);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listUnwrapAndFlattenArgument.iterator();
        while (it.hasNext()) {
            FirRegularClassSymbol firRegularClassSymbolExtractClassFromArgument = extractClassFromArgument((FirExpression) it.next(), session);
            if (firRegularClassSymbolExtractClassFromArgument != null) {
                arrayList.add(firRegularClassSymbolExtractClassFromArgument);
            }
        }
        return arrayList;
    }

    public static final FirRegularClassSymbol extractClassFromArgument(FirExpression firExpression, FirSession session) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!(firExpression instanceof FirGetClassCall)) {
            return null;
        }
        FirExpression argument = ((FirGetClassCall) firExpression).getArgument();
        if (argument instanceof FirResolvedQualifier) {
            FirClassLikeSymbol<?> symbol = ((FirResolvedQualifier) argument).getSymbol();
            if (symbol instanceof FirRegularClassSymbol) {
                return (FirRegularClassSymbol) symbol;
            }
            return null;
        }
        if (!(argument instanceof FirClassReferenceExpression)) {
            return null;
        }
        FirTypeRef classTypeRef = ((FirClassReferenceExpression) argument).getClassTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = classTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) classTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null || (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, session, null, 2, null)) == null) {
            return null;
        }
        return TypeUtilsKt.toRegularClassSymbol(coneClassLikeTypeFullyExpandedType$default, session);
    }

    public static final void checkRepeatedAnnotation(AnnotationUseSiteTarget annotationUseSiteTarget, List<AnnotationUseSiteTarget> existingTargetsForAnnotation, FirAnnotation annotation, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(existingTargetsForAnnotation, "existingTargetsForAnnotation");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!existingTargetsForAnnotation.contains(annotationUseSiteTarget)) {
            List<AnnotationUseSiteTarget> list = existingTargetsForAnnotation;
            if ((list instanceof Collection) && list.isEmpty()) {
                return;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                boolean z = true;
                boolean z2 = ((AnnotationUseSiteTarget) it.next()) == null;
                if (annotationUseSiteTarget != null) {
                    z = false;
                }
                if (z2 != z) {
                }
            }
            return;
        }
        if (isRepeatable(annotation, context.getSession())) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotation.getSource(), FirErrors.INSTANCE.getREPEATED_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    public static final boolean isRepeatable(FirAnnotation firAnnotation, FirSession session) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ClassId annotationClassId = FirAnnotationUtilsKt.toAnnotationClassId(firAnnotation, session);
        if (annotationClassId == null || annotationClassId.isLocal() || (classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(annotationClassId)) == null) {
            return false;
        }
        return containsRepeatableAnnotation(classLikeSymbolByClassId, session);
    }

    public static final boolean containsRepeatableAnnotation(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassLikeSymbol<?> firClassLikeSymbol2 = firClassLikeSymbol;
        if (FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol2, StandardClassIds.Annotations.INSTANCE.getRepeatable(), session) != null) {
            return true;
        }
        if (FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol2, StandardClassIds.Annotations.Java.INSTANCE.getRepeatable(), session) == null && FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol2, StandardClassIds.Annotations.INSTANCE.getJvmRepeatable(), session) == null) {
            return false;
        }
        if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.RepeatableAnnotations)) {
            return true;
        }
        return getAnnotationRetention(firClassLikeSymbol, session) == AnnotationRetention.SOURCE && (firClassLikeSymbol.getOrigin() instanceof FirDeclarationOrigin.Java);
    }

    public static final AnnotationRetention getExplicitAnnotationRetention(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firClassLikeSymbol, StandardClassIds.Annotations.INSTANCE.getRetention(), session);
        if (annotationByClassId != null) {
            return FirRetentionAnnotationHelpersKt.getRetention(annotationByClassId);
        }
        return null;
    }

    public static final AnnotationRetention getAnnotationRetention(FirClassLikeSymbol<?> firClassLikeSymbol, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        AnnotationRetention explicitAnnotationRetention = getExplicitAnnotationRetention(firClassLikeSymbol, session);
        return explicitAnnotationRetention == null ? AnnotationRetention.RUNTIME : explicitAnnotationRetention;
    }

    public static final AnnotationUseSiteTarget getDefaultUseSiteTarget(FirAnnotationContainer firAnnotationContainer, FirAnnotation annotation, CheckerContext context) {
        Object next;
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<T> it = getImplicitUseSiteTargetList(firAnnotationContainer, context).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (CollectionsKt.contains(getAllowedAnnotationTargets(annotation, context.getSession()), KotlinTarget.INSTANCE.getUSE_SITE_MAPPING().get((AnnotationUseSiteTarget) next))) {
                return (AnnotationUseSiteTarget) next;
            }
        }
        next = null;
        return (AnnotationUseSiteTarget) next;
    }

    public static final List<AnnotationUseSiteTarget> getImplicitUseSiteTargetList(FirAnnotationContainer firAnnotationContainer, CheckerContext context) {
        FirElement firElement;
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(firAnnotationContainer instanceof FirValueParameter)) {
            if (firAnnotationContainer instanceof FirProperty) {
                return !((FirProperty) firAnnotationContainer).isLocal() ? UseSiteTargetsList.INSTANCE.getT_PROPERTY() : CollectionsKt.emptyList();
            }
            if (firAnnotationContainer instanceof FirPropertyAccessor) {
                return CollectionsKt.listOf(((FirPropertyAccessor) firAnnotationContainer).getIsGetter() ? AnnotationUseSiteTarget.PROPERTY_GETTER : AnnotationUseSiteTarget.PROPERTY_SETTER);
            }
            return CollectionsKt.emptyList();
        }
        Iterator it = CollectionsKt.asReversed(context.getContainingDeclarations()).iterator();
        while (true) {
            firElement = null;
            if (!it.hasNext()) {
                break;
            }
            FirDeclaration firDeclaration = (FirDeclaration) it.next();
            firElement = firDeclaration instanceof FirDeclaration ? firDeclaration : null;
            if (firElement != null && firElement != null) {
                break;
            }
        }
        if (firElement instanceof FirPrimaryConstructor) {
            return UseSiteTargetsList.INSTANCE.getT_CONSTRUCTOR_PARAMETER();
        }
        return CollectionsKt.emptyList();
    }

    public static final void checkRepeatedAnnotation(FirAnnotationContainer firAnnotationContainer, List<? extends FirAnnotation> annotations, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (annotations.size() <= 1) {
            return;
        }
        HashMap map = new HashMap();
        for (FirAnnotation firAnnotation : annotations) {
            AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
            if (useSiteTarget == null) {
                useSiteTarget = firAnnotationContainer != null ? getDefaultUseSiteTarget(firAnnotationContainer, firAnnotation, context) : null;
            }
            ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef()), context.getSession());
            HashMap map2 = map;
            Object obj = map2.get(coneKotlinTypeFullyExpandedType);
            if (obj == null) {
                obj = (List) new ArrayList();
                map2.put(coneKotlinTypeFullyExpandedType, obj);
            }
            List list = (List) obj;
            checkRepeatedAnnotation(useSiteTarget, list, firAnnotation, context, reporter);
            list.add(useSiteTarget);
        }
    }
}
