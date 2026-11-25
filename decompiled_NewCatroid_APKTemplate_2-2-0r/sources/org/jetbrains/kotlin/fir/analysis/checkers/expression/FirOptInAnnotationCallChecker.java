package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;

/* JADX INFO: compiled from: FirOptInAnnotationCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\"\u0010\u0011\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInAnnotationCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkOptInArgumentIsMarker", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "checkOptInIsEnabled", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInAnnotationCallChecker extends FirExpressionChecker<FirAnnotationCall> {
    public static final FirOptInAnnotationCallChecker INSTANCE = new FirOptInAnnotationCallChecker();

    private FirOptInAnnotationCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirAnnotationCall expression, CheckerContext context, DiagnosticReporter reporter) {
        ConeClassLikeLookupTag lookupTag;
        FirRegularClassSymbol firRegularClassSymbolExtractClassFromArgument;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirTypeRef annotationTypeRef = expression.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) {
            return;
        }
        ClassId classId = lookupTag.getClassId();
        boolean zAreEqual = Intrinsics.areEqual(classId, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID());
        boolean zAreEqual2 = Intrinsics.areEqual(classId, OptInNames.INSTANCE.getOPT_IN_CLASS_ID());
        boolean zAreEqual3 = Intrinsics.areEqual(classId, OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_CLASS_ID());
        if (zAreEqual || zAreEqual2) {
            checkOptInIsEnabled(expression.getSource(), context, reporter);
            if (zAreEqual2) {
                if (expression.getArgumentList().getArguments().isEmpty()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getOPT_IN_WITHOUT_ARGUMENTS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                FirExpression firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(expression, OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS());
                List<FirRegularClassSymbol> listExtractClassesFromArgument = firExpressionFindArgumentByName != null ? FirAnnotationHelpersKt.extractClassesFromArgument(firExpressionFindArgumentByName, context.getSession()) : null;
                if (listExtractClassesFromArgument == null) {
                    listExtractClassesFromArgument = CollectionsKt.emptyList();
                }
                Iterator<FirRegularClassSymbol> it = listExtractClassesFromArgument.iterator();
                while (it.hasNext()) {
                    checkOptInArgumentIsMarker(it.next(), expression.getSource(), reporter, context);
                }
                return;
            }
            return;
        }
        if (zAreEqual3) {
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
            FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
            if (firClass != null) {
                ClassKind classKind = firClass.getClassKind();
                if (classKind == ClassKind.ENUM_CLASS || classKind == ClassKind.OBJECT || classKind == ClassKind.ANNOTATION_CLASS) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getSUBCLASS_OPT_IN_INAPPLICABLE(), classKind.toString(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
                Modality modality = FirHelpersKt.modality(firClass);
                if (modality == Modality.FINAL || modality == Modality.SEALED) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getSUBCLASS_OPT_IN_INAPPLICABLE(), new StringBuilder().append(modality).append(StringUtil.SPACE).append(classKind).toString(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                } else if (firClass.getStatus().isFun()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getSUBCLASS_OPT_IN_INAPPLICABLE(), "fun interface", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                } else if (firClass.getSymbol().getClassId().isLocal()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getSUBCLASS_OPT_IN_INAPPLICABLE(), "local " + classKind, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
            }
            FirExpression firExpressionFindArgumentByName2 = FirAnnotationUtilsKt.findArgumentByName(expression, OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS());
            if (firExpressionFindArgumentByName2 == null || (firRegularClassSymbolExtractClassFromArgument = FirAnnotationHelpersKt.extractClassFromArgument(firExpressionFindArgumentByName2, context.getSession())) == null) {
                return;
            }
            checkOptInArgumentIsMarker(firRegularClassSymbolExtractClassFromArgument, expression.getSource(), reporter, context);
        }
    }

    private final void checkOptInIsEnabled(KtSourceElement element, CheckerContext context, DiagnosticReporter reporter) {
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession());
        List list = (List) languageVersionSettings.getFlag(AnalysisFlags.getOptIn());
        if (languageVersionSettings.supportsFeature(LanguageFeature.OptInRelease) || list.contains(OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME().asString())) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, element, FirErrors.INSTANCE.getOPT_IN_IS_NOT_ENABLED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final void checkOptInArgumentIsMarker(FirRegularClassSymbol classSymbol, KtSourceElement source, DiagnosticReporter reporter, CheckerContext context) {
        if (FirOptInUsageBaseChecker.loadExperimentalityForMarkerAnnotation$default(FirOptInUsageBaseChecker.INSTANCE, classSymbol, context.getSession(), (String) null, 2, (Object) null) == null) {
            KtDiagnosticFactory1<FqName> opt_in_argument_is_not_marker = FirErrors.INSTANCE.getOPT_IN_ARGUMENT_IS_NOT_MARKER();
            FqName fqNameAsSingleFqName = classSymbol.getClassId().asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, opt_in_argument_is_not_marker, fqNameAsSingleFqName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
