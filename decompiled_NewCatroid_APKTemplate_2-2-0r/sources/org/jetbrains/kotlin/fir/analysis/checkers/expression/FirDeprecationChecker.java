package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory4;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FutureApiDeprecationInfo;
import org.jetbrains.kotlin.fir.declarations.RequireKotlinDeprecationInfo;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDeprecationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J(\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u000b\u001a\u00020\fH\u0002J*\u0010\u0015\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0002JC\u0010\u001a\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0000¢\u0006\u0002\b\u001eJ?\u0010\u001f\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0000¢\u0006\u0002\b J>\u0010!\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0002J6\u0010\"\u001a\u00020\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0018\u001a\u00020#2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001a\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010%*\u00020&2\u0006\u0010\u000b\u001a\u00020\fH\u0002J*\u0010'\u001a\u00020\u001d*\u00020\u00102\n\u0010\u001b\u001a\u0006\u0012\u0002\b\u00030\u00142\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirDeprecationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "allowedSourceKinds", "", "Lorg/jetbrains/kotlin/KtSourceElementKind;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getWorstDeprecation", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "reportApiNotAvailable", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "deprecationInfo", "Lorg/jetbrains/kotlin/fir/declarations/FutureApiDeprecationInfo;", "reportApiStatus", "referencedSymbol", "isTypealiasExpansion", "", "reportApiStatus$checkers", "reportApiStatusIfNeeded", "reportApiStatusIfNeeded$checkers", "reportDeprecation", "reportVersionRequirementDeprecation", "Lorg/jetbrains/kotlin/fir/declarations/RequireKotlinDeprecationInfo;", "classSymbolItIsCalledThrough", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "isTypealiasExpansionOf", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeprecationChecker extends FirExpressionChecker<FirStatement> {
    public static final FirDeprecationChecker INSTANCE = new FirDeprecationChecker();
    private static final Set<KtSourceElementKind> allowedSourceKinds = SetsKt.setOf((Object[]) new KtSourceElementKind[]{KtRealSourceElementKind.INSTANCE, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE});

    /* JADX INFO: compiled from: FirDeprecationChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[DeprecationLevelValue.values().length];
            try {
                iArr[DeprecationLevelValue.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeprecationLevelValue.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeprecationLevelValue.HIDDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProtoBuf.VersionRequirement.VersionKind.values().length];
            try {
                iArr2[ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProtoBuf.VersionRequirement.VersionKind.API_VERSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private FirDeprecationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        FirReference calleeReference;
        FirResolvedNamedReference resolved;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Set<KtSourceElementKind> set = allowedSourceKinds;
        KtSourceElement source = expression.getSource();
        if (CollectionsKt.contains(set, source != null ? source.getKind() : null) && !(expression instanceof FirAnnotation)) {
            FirStatement firStatement = expression;
            if (FirHelpersKt.isLhsOfAssignment(firStatement, context) || (calleeReference = FirExpressionUtilKt.getCalleeReference(firStatement)) == null || (resolved = FirReferenceUtilsKt.getResolved(calleeReference)) == null) {
                return;
            }
            FirBasedSymbol<?> resolvedSymbol = resolved.getResolvedSymbol();
            if (expression instanceof FirDelegatedConstructorCall) {
                DeprecationInfo deprecation = DeprecationUtilsKt.getDeprecation(resolvedSymbol, context.getSession(), firStatement);
                if (deprecation == null) {
                    return;
                }
                FirClassLikeDeclaration firClassLikeDeclarationFirClassLike = DeclarationUtilsKt.firClassLike(((FirDelegatedConstructorCall) expression).getConstructedTypeRef(), context.getSession());
                reportApiStatus$checkers(resolved.getSource(), resolvedSymbol, (firClassLikeDeclarationFirClassLike != null ? firClassLikeDeclarationFirClassLike.getSymbol() : null) instanceof FirTypeAliasSymbol, deprecation, reporter, context);
                return;
            }
            reportApiStatusIfNeeded$checkers(resolved.getSource(), resolvedSymbol, context, reporter, firStatement);
        }
    }

    public static /* synthetic */ void reportApiStatusIfNeeded$checkers$default(FirDeprecationChecker firDeprecationChecker, KtSourceElement ktSourceElement, FirBasedSymbol firBasedSymbol, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, FirElement firElement, int i, Object obj) {
        if ((i & 16) != 0) {
            firElement = null;
        }
        firDeprecationChecker.reportApiStatusIfNeeded$checkers(ktSourceElement, firBasedSymbol, checkerContext, diagnosticReporter, firElement);
    }

    public final void reportApiStatusIfNeeded$checkers(KtSourceElement source, FirBasedSymbol<?> referencedSymbol, CheckerContext context, DiagnosticReporter reporter, FirElement callSite) {
        Intrinsics.checkNotNullParameter(referencedSymbol, "referencedSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        DeprecationInfo worstDeprecation = getWorstDeprecation(callSite, referencedSymbol, context);
        if (worstDeprecation == null) {
            return;
        }
        reportApiStatus$checkers(source, referencedSymbol, isTypealiasExpansionOf(worstDeprecation, referencedSymbol, callSite, context), worstDeprecation, reporter, context);
    }

    private final boolean isTypealiasExpansionOf(DeprecationInfo deprecationInfo, FirBasedSymbol<?> firBasedSymbol, FirElement firElement, CheckerContext checkerContext) {
        if (firBasedSymbol instanceof FirConstructorSymbol) {
            FirTypeAliasSymbol typeAliasForConstructor = DeclarationUtilsKt.getTypeAliasForConstructor((FirConstructorSymbol) firBasedSymbol);
            if (typeAliasForConstructor != null) {
                return INSTANCE.isTypealiasExpansionOf(deprecationInfo, typeAliasForConstructor, firElement, checkerContext);
            }
            return false;
        }
        if (!(firBasedSymbol instanceof FirTypeAliasSymbol)) {
            return false;
        }
        DeprecationInfo ownDeprecation = DeprecationUtilsKt.getOwnDeprecation(firBasedSymbol, checkerContext.getSession(), firElement);
        return ownDeprecation == null || ownDeprecation.compareTo(deprecationInfo) < 0;
    }

    public final void reportApiStatus$checkers(KtSourceElement source, FirBasedSymbol<?> referencedSymbol, boolean isTypealiasExpansion, DeprecationInfo deprecationInfo, DiagnosticReporter reporter, CheckerContext context) {
        Intrinsics.checkNotNullParameter(referencedSymbol, "referencedSymbol");
        Intrinsics.checkNotNullParameter(deprecationInfo, "deprecationInfo");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        if (deprecationInfo instanceof FutureApiDeprecationInfo) {
            reportApiNotAvailable(source, (FutureApiDeprecationInfo) deprecationInfo, reporter, context);
        } else if (deprecationInfo instanceof RequireKotlinDeprecationInfo) {
            reportVersionRequirementDeprecation(source, referencedSymbol, (RequireKotlinDeprecationInfo) deprecationInfo, reporter, context);
        } else {
            reportDeprecation(source, referencedSymbol, isTypealiasExpansion, deprecationInfo, reporter, context);
        }
    }

    private final void reportVersionRequirementDeprecation(KtSourceElement source, FirBasedSymbol<?> referencedSymbol, RequireKotlinDeprecationInfo deprecationInfo, DiagnosticReporter reporter, CheckerContext context) {
        KtDiagnosticFactory4<FirBasedSymbol<?>, VersionRequirement.Version, String, String> version_requirement_deprecation_error;
        String versionString;
        if (WhenMappings.$EnumSwitchMapping$0[deprecationInfo.getDeprecationLevel().ordinal()] == 1) {
            version_requirement_deprecation_error = FirErrors.INSTANCE.getVERSION_REQUIREMENT_DEPRECATION();
        } else {
            version_requirement_deprecation_error = FirErrors.INSTANCE.getVERSION_REQUIREMENT_DEPRECATION_ERROR();
        }
        KtDiagnosticFactory4<FirBasedSymbol<?>, VersionRequirement.Version, String, String> ktDiagnosticFactory4 = version_requirement_deprecation_error;
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession());
        int i = WhenMappings.$EnumSwitchMapping$1[deprecationInfo.getVersionRequirement().getKind().ordinal()];
        if (i == 1) {
            versionString = KotlinCompilerVersion.VERSION;
        } else if (i == 2) {
            versionString = languageVersionSettings.getLanguageVersion().getVersionString();
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            versionString = languageVersionSettings.getApiVersion().getVersionString();
        }
        String str = versionString;
        KtSourceElement ktSourceElement = source;
        VersionRequirement.Version version = deprecationInfo.getVersionRequirement().getVersion();
        Intrinsics.checkNotNull(str);
        String message = deprecationInfo.getMessage();
        if (message == null) {
            message = "";
        }
        KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) ktSourceElement, (KtDiagnosticFactory4<FirBasedSymbol<?>, VersionRequirement.Version, String, String>) ((KtDiagnosticFactory4<Object, Object, Object, Object>) ktDiagnosticFactory4), referencedSymbol, version, str, message, (DiagnosticContext) context, (128 & 128) != 0 ? null : null);
    }

    private final void reportDeprecation(KtSourceElement source, FirBasedSymbol<?> referencedSymbol, boolean isTypealiasExpansion, DeprecationInfo deprecationInfo, DiagnosticReporter reporter, CheckerContext context) {
        KtDiagnosticFactory3<FirBasedSymbol<?>, FirBasedSymbol<?>, String> typealias_expansion_deprecation;
        KtDiagnosticFactory2<FirBasedSymbol<?>, String> deprecation;
        if (!isTypealiasExpansion) {
            int i = WhenMappings.$EnumSwitchMapping$0[deprecationInfo.getDeprecationLevel().ordinal()];
            if (i == 1) {
                deprecation = FirErrors.INSTANCE.getDEPRECATION();
            } else if (i == 2 || i == 3) {
                deprecation = FirErrors.INSTANCE.getDEPRECATION_ERROR();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            KtDiagnosticFactory2<FirBasedSymbol<?>, String> ktDiagnosticFactory2 = deprecation;
            KtSourceElement ktSourceElement = source;
            String message = deprecationInfo.getMessage();
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, ktDiagnosticFactory2, referencedSymbol, message == null ? "" : message, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[deprecationInfo.getDeprecationLevel().ordinal()];
        if (i2 == 1) {
            typealias_expansion_deprecation = FirErrors.INSTANCE.getTYPEALIAS_EXPANSION_DEPRECATION();
        } else if (i2 == 2 || i2 == 3) {
            typealias_expansion_deprecation = FirErrors.INSTANCE.getTYPEALIAS_EXPANSION_DEPRECATION_ERROR();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        KtDiagnosticFactory3<FirBasedSymbol<?>, FirBasedSymbol<?>, String> ktDiagnosticFactory3 = typealias_expansion_deprecation;
        KtSourceElement ktSourceElement2 = source;
        String message2 = deprecationInfo.getMessage();
        KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) ktSourceElement2, (KtDiagnosticFactory3<FirBasedSymbol<?>, FirBasedSymbol<?>, String>) ((KtDiagnosticFactory3<Object, Object, Object>) ktDiagnosticFactory3), referencedSymbol, referencedSymbol, message2 == null ? "" : message2, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
    }

    private final void reportApiNotAvailable(KtSourceElement source, FutureApiDeprecationInfo deprecationInfo, DiagnosticReporter reporter, CheckerContext context) {
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getAPI_NOT_AVAILABLE(), deprecationInfo.getSinceVersion(), context.get$languageVersionSettings().getApiVersion(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final DeprecationInfo getWorstDeprecation(FirElement callSite, FirBasedSymbol<?> symbol, CheckerContext context) {
        FirClassLikeSymbol<?> firClassLikeSymbolClassSymbolItIsCalledThrough;
        DeprecationInfo[] deprecationInfoArr = new DeprecationInfo[2];
        DeprecationInfo deprecation = null;
        FirConstructorSymbol firConstructorSymbol = symbol instanceof FirConstructorSymbol ? (FirConstructorSymbol) symbol : null;
        if (firConstructorSymbol != null && (firClassLikeSymbolClassSymbolItIsCalledThrough = classSymbolItIsCalledThrough(firConstructorSymbol, context)) != null) {
            deprecation = DeprecationUtilsKt.getDeprecation(firClassLikeSymbolClassSymbolItIsCalledThrough, context.getSession(), callSite);
        }
        deprecationInfoArr[0] = deprecation;
        deprecationInfoArr[1] = DeprecationUtilsKt.getDeprecation(symbol, context.getSession(), callSite);
        return (DeprecationInfo) CollectionsKt.maxOrNull((Iterable) CollectionsKt.listOfNotNull((Object[]) deprecationInfoArr));
    }

    private final FirClassLikeSymbol<?> classSymbolItIsCalledThrough(FirConstructorSymbol firConstructorSymbol, CheckerContext checkerContext) {
        FirTypeAliasSymbol typeAliasForConstructor = DeclarationUtilsKt.getTypeAliasForConstructor(firConstructorSymbol);
        return typeAliasForConstructor != null ? typeAliasForConstructor : FirHelpersKt.toRegularClassSymbol(firConstructorSymbol.getResolvedReturnTypeRef(), checkerContext.getSession());
    }
}
