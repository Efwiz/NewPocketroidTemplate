package org.jetbrains.kotlin.fir.analysis.checkers.type;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirOptInUsageBaseChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;

/* JADX INFO: compiled from: FirOptInUsageTypeRefChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirOptInUsageTypeRefChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInUsageTypeRefChecker extends FirTypeChecker<FirTypeRef> {
    public static final FirOptInUsageTypeRefChecker INSTANCE = new FirOptInUsageTypeRefChecker();

    private FirOptInUsageTypeRefChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        FirClassLikeSymbol<?> symbol;
        Set<FirOptInUsageBaseChecker.Experimentality> setLoadExperimentalities;
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = typeRef.getSource();
        if ((source != null ? source.getKind() : null) instanceof KtRealSourceElementKind) {
            FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), context.getSession())) == null) {
                return;
            }
            FirClassLikeSymbol<?> firClassLikeSymbol = symbol;
            FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassLikeSymbol, FirResolvePhase.STATUS);
            ClassId classId = symbol.getClassId();
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getCallsOrAssignments());
            FirAnnotation firAnnotation = objLastOrNull instanceof FirAnnotation ? (FirAnnotation) objLastOrNull : null;
            if (firAnnotation == null || firAnnotation.getAnnotationTypeRef() != typeRef) {
                if (Intrinsics.areEqual(classId, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID()) || Intrinsics.areEqual(classId, OptInNames.INSTANCE.getOPT_IN_CLASS_ID())) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getOPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else if ((symbol instanceof FirRegularClassSymbol) && FirAnnotationUtilsKt.getAnnotationByClassId((FirAnnotationContainer) ((FirRegularClassSymbol) symbol).getFir(), OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), context.getSession()) != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getOPT_IN_MARKER_CAN_ONLY_BE_USED_AS_ANNOTATION_OR_ARGUMENT_IN_OPT_IN(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            Object objLastOrNull2 = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
            FirClass firClass = objLastOrNull2 instanceof FirClass ? (FirClass) objLastOrNull2 : null;
            List<FirTypeRef> superTypeRefs = firClass != null ? firClass.getSuperTypeRefs() : null;
            if (superTypeRefs == null) {
                superTypeRefs = CollectionsKt.emptyList();
            }
            boolean zContains = superTypeRefs.contains(typeRef);
            FirOptInUsageBaseChecker firOptInUsageBaseChecker = FirOptInUsageBaseChecker.INSTANCE;
            if (zContains) {
                setLoadExperimentalities = firOptInUsageBaseChecker.loadExperimentalitiesFromSupertype(symbol, context);
            } else {
                setLoadExperimentalities = firOptInUsageBaseChecker.loadExperimentalities(firClassLikeSymbol, context, false, null);
            }
            FirOptInUsageBaseChecker.reportNotAcceptedExperimentalities$default(firOptInUsageBaseChecker, SetsKt.plus((Set) setLoadExperimentalities, (Iterable) firOptInUsageBaseChecker.loadExperimentalitiesFromConeArguments(context, ArraysKt.toList(coneClassLikeType.getTypeArguments()))), typeRef, context, reporter, null, 16, null);
        }
    }
}
