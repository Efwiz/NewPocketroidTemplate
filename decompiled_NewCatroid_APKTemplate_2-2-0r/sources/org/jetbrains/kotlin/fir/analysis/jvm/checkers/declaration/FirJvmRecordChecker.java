package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirJvmRecordChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmRecordChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmRecordChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirJvmRecordChecker INSTANCE = new FirJvmRecordChecker();

    private FirJvmRecordChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        List<FirValueParameterSymbol> valueParameterSymbols;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirTypeRef firTypeRef = (FirTypeRef) CollectionsKt.firstOrNull((List) declaration.getSuperTypeRefs());
        if (firTypeRef != null && firTypeRef.getSource() != null) {
            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (Intrinsics.areEqual(coneClassLikeType != null ? ConeTypeUtilsKt.getClassId(coneClassLikeType) : null, StandardClassIds.Java.INSTANCE.getRecord())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRef.getSource(), FirJvmErrors.INSTANCE.getILLEGAL_JAVA_LANG_RECORD_SUPERTYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
        }
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, JvmNames.JVM_RECORD_ANNOTATION_CLASS_ID, context.getSession());
        if (annotationByClassId == null || (source = annotationByClassId.getSource()) == null) {
            return;
        }
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession());
        if (!languageVersionSettings.supportsFeature(LanguageFeature.JvmRecordSupport)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.JvmRecordSupport, languageVersionSettings), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if (declaration.getSymbol().getClassId().isLocal()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJvmErrors.INSTANCE.getLOCAL_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        FirRegularClass firRegularClass = declaration;
        if (firRegularClass.getStatus().isInner()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getINNER_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        Modality modality = firRegularClass.getStatus().getModality();
        if (modality != null && modality != Modality.FINAL) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getNON_FINAL_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        FirRegularClass firRegularClass2 = declaration;
        if (firRegularClass2.getClassKind() == ClassKind.ENUM_CLASS) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getENUM_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (!declaration.getStatus().isData()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJvmErrors.INSTANCE.getNON_DATA_CLASS_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny(firRegularClass2, context.getSession());
        if (firConstructorSymbolPrimaryConstructorIfAny != null && (valueParameterSymbols = firConstructorSymbolPrimaryConstructorIfAny.getValueParameterSymbols()) != null) {
            if (valueParameterSymbols.isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJvmErrors.INSTANCE.getJVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            for (FirValueParameterSymbol firValueParameterSymbol : CollectionsKt.dropLast(valueParameterSymbols, 1)) {
                if (firValueParameterSymbol.isVararg()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameterSymbol.getSource(), FirJvmErrors.INSTANCE.getJVM_RECORD_NOT_LAST_VARARG_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
        for (FirDeclaration firDeclaration : declaration.getDeclarations()) {
            if (firDeclaration instanceof FirProperty) {
                KtSourceElement source2 = firDeclaration.getSource();
                boolean zAreEqual = Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.PropertyFromParameter.INSTANCE);
                FirProperty firProperty = (FirProperty) firDeclaration;
                if (firProperty.getIsVar() && zAreEqual) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firDeclaration.getSource(), FirJvmErrors.INSTANCE.getJVM_RECORD_NOT_VAL_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                } else if (!zAreEqual && (DeclarationAttributesKt.getHasBackingField(firProperty) || firProperty.getDelegateFieldSymbol() != null)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firDeclaration.getSource(), FirJvmErrors.INSTANCE.getFIELD_IN_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            } else if ((firDeclaration instanceof FirField) && (firDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firDeclaration.getSource(), FirJvmErrors.INSTANCE.getDELEGATION_BY_IN_JVM_RECORD(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
        FirTypeRef firTypeRef2 = (FirTypeRef) CollectionsKt.firstOrNull((List) declaration.getSuperTypeRefs());
        if (firTypeRef2 != null) {
            KtSourceElement source3 = firTypeRef2.getSource();
            if (Intrinsics.areEqual(source3 != null ? source3.getKind() : null, KtRealSourceElementKind.INSTANCE)) {
                FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(firTypeRef2, context.getSession());
                if ((regularClassSymbol != null ? regularClassSymbol.getClassKind() : null) == ClassKind.CLASS) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getJVM_RECORD_EXTENDS_CLASS(), FirTypeUtilsKt.getConeType(firTypeRef2), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
