package org.jetbrains.kotlin.fir.analysis.checkers.config;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.DeprecationsPerUseSite;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.transformers.ImportUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.PackageResolutionResult;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: FirOptInLanguageVersionSettingsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\bH\u0016J2\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\n2\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00040\bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/config/FirOptInLanguageVersionSettingsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/config/FirLanguageVersionSettingsChecker;", "()V", "check", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "rawReport", "Lkotlin/Function2;", "", "", "checkOptInMarkerArgument", "fqNameAsString", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInLanguageVersionSettingsChecker extends FirLanguageVersionSettingsChecker {
    public static final FirOptInLanguageVersionSettingsChecker INSTANCE = new FirOptInLanguageVersionSettingsChecker();

    private FirOptInLanguageVersionSettingsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.config.FirLanguageVersionSettingsChecker
    public void check(CheckerContext context, Function2<? super Boolean, ? super String, Unit> rawReport) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(rawReport, "rawReport");
        for (String str : (Iterable) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getOptIn())) {
            if (!Intrinsics.areEqual(str, OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME().asString())) {
                INSTANCE.checkOptInMarkerArgument(context, str, rawReport);
            }
        }
    }

    private final void checkOptInMarkerArgument(CheckerContext context, String fqNameAsString, Function2<? super Boolean, ? super String, Unit> rawReport) {
        DeprecationInfo all;
        PackageResolutionResult packageResolutionResultResolveToPackageOrClass = ImportUtilsKt.resolveToPackageOrClass(FirSymbolProviderKt.getSymbolProvider(context.getSession()), new FqName(fqNameAsString));
        PackageResolutionResult.PackageOrClass packageOrClass = packageResolutionResultResolveToPackageOrClass instanceof PackageResolutionResult.PackageOrClass ? (PackageResolutionResult.PackageOrClass) packageResolutionResultResolveToPackageOrClass : null;
        FirClassLikeSymbol<?> classSymbol = packageOrClass != null ? packageOrClass.getClassSymbol() : null;
        if (classSymbol == null) {
            rawReport.invoke(false, "Opt-in requirement marker " + fqNameAsString + " is unresolved. Please make sure it's present in the module dependencies");
            return;
        }
        if (FirAnnotationUtilsKt.getAnnotationByClassId(classSymbol, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), context.getSession()) == null) {
            rawReport.invoke(false, "Class " + fqNameAsString + " is not an opt-in requirement marker");
            return;
        }
        DeprecationsPerUseSite ownDeprecation = classSymbol.getOwnDeprecation(context.getLanguageVersionSettings());
        if (ownDeprecation == null || (all = ownDeprecation.getAll()) == null) {
            return;
        }
        Boolean boolValueOf = Boolean.valueOf(all.getDeprecationLevel() != DeprecationLevelValue.WARNING);
        StringBuilder sbAppend = new StringBuilder("Opt-in requirement marker ").append(fqNameAsString).append(" is deprecated");
        String message = all.getMessage();
        String str = message != null ? ". " + message : null;
        if (str == null) {
            str = "";
        }
        rawReport.invoke(boolValueOf, sbAppend.append(str).toString());
    }
}
