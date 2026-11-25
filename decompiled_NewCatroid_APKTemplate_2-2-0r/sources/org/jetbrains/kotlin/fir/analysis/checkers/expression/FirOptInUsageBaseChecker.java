package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirSinceKotlinAccessibility;
import org.jetbrains.kotlin.fir.analysis.checkers.FirSinceKotlinHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.SymbolInternals;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.utils.CollectionsKt;
import org.jetbrains.kotlin.utils.SmartSet;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirOptInUsageBaseChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001BB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0002J\"\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\"\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011J8\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001c2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ2\u0010\u001f\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00182\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J4\u0010\"\u001a\u00020\u0016*\u0004\u0018\u00010#2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0%2\u000e\b\u0002\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0002J\u001c\u0010)\u001a\u00020\u0007*\u00020*2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010+\u001a\u00020,H\u0002J\u001c\u0010-\u001a\u00020\u0007*\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\b\u001a\u00020\tH\u0002J$\u0010.\u001a\u00020\u0007*\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0007H\u0002JN\u0010/\u001a\u00020\u0016*\u0002002\n\u0010 \u001a\u0006\u0012\u0002\b\u0003012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u00102\u001a\u00020\u00072\b\u00103\u001a\u0004\u0018\u00010#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0%H\u0002J<\u00104\u001a\u00020\u0016*\u0002052\n\u0010 \u001a\u0006\u0012\u0002\b\u0003012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0%H\u0002J.\u00106\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e*\u0006\u0012\u0002\b\u0003012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00102\u001a\u00020\u00072\b\u00103\u001a\u0004\u0018\u00010#JV\u00106\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e*\u0006\u0012\u0002\b\u0003012\u0006\u0010\n\u001a\u00020\u000b2\u000e\u00107\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u00102\u001a\u00020\u00072\b\u00103\u001a\u0004\u0018\u00010#2\u0006\u0010\f\u001a\u00020\u0007H\u0002J*\u00108\u001a\u00020\u0016*\u00020(2\u0006\u0010+\u001a\u00020,2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f092\u0006\u0010\f\u001a\u00020\u0007H\u0002J$\u00108\u001a\u00020\u0016*\u0006\u0012\u0002\b\u0003012\u0006\u0010+\u001a\u00020,2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f09J\u001c\u0010:\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e*\u0006\u0012\u0002\b\u00030;2\u0006\u0010\n\u001a\u00020\u000bJ\"\u0010<\u001a\u0004\u0018\u00010\u000f*\u00020=2\u0006\u0010+\u001a\u00020,2\n\b\u0002\u0010>\u001a\u0004\u0018\u00010?H\u0002J \u0010<\u001a\u0004\u0018\u00010\u000f*\u00020@2\u0006\u0010+\u001a\u00020,2\n\b\u0002\u0010>\u001a\u0004\u0018\u00010?J\u001c\u0010A\u001a\u00020\u0007*\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010\b\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker;", "", "()V", "LEVEL", "Lorg/jetbrains/kotlin/name/Name;", "MESSAGE", "isExperimentalityAcceptableInContext", "", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "fromSupertype", "loadExperimentalitiesFromConeArguments", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality;", "typeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "loadExperimentalitiesFromTypeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "reportNotAcceptedExperimentalities", "", "experimentalities", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "reportNotAcceptedOverrideExperimentalities", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "addExperimentalities", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "result", "Lorg/jetbrains/kotlin/utils/SmartSet;", "visited", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isAnnotatedWithOptIn", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isAnnotatedWithSubclassOptInRequired", "isExperimentalityAcceptable", "loadCallableSpecificExperimentalities", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "fromSetter", "dispatchReceiverType", "loadClassLikeSpecificExperimentalities", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "loadExperimentalities", "knownExperimentalities", "loadExperimentalitiesFromAnnotationTo", "", "loadExperimentalitiesFromSupertype", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "loadExperimentalityForMarkerAnnotation", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "annotatedOwnerClassName", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "primaryConstructorParameterIsExperimentalityAcceptable", "Experimentality", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInUsageBaseChecker {
    public static final FirOptInUsageBaseChecker INSTANCE = new FirOptInUsageBaseChecker();
    private static final Name LEVEL;
    private static final Name MESSAGE;

    /* JADX INFO: compiled from: FirOptInUsageBaseChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Experimentality.Severity.values().length];
            try {
                iArr[Experimentality.Severity.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Experimentality.Severity.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: compiled from: FirOptInUsageBaseChecker.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u0000  2\u00020\u0001:\u0002 !B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\nHÆ\u0003J?\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001b\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\t\u0010\u001f\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality;", "", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "severity", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Severity;", CrashHianalyticsData.MESSAGE, "", "supertypeName", "fromSupertype", "", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Severity;Ljava/lang/String;Ljava/lang/String;Z)V", "getAnnotationClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getFromSupertype", "()Z", "getMessage", "()Ljava/lang/String;", "getSeverity", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Severity;", "getSupertypeName", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "Companion", "Severity", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Experimentality {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final Severity DEFAULT_SEVERITY = Severity.ERROR;
        private final ClassId annotationClassId;
        private final boolean fromSupertype;
        private final String message;
        private final Severity severity;
        private final String supertypeName;

        /* JADX INFO: compiled from: FirOptInUsageBaseChecker.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Severity;", "", "(Ljava/lang/String;I)V", "WARNING", MediaError.ERROR_TYPE_ERROR, "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum Severity {
            WARNING,
            ERROR;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<Severity> getEntries() {
                return $ENTRIES;
            }
        }

        public static /* synthetic */ Experimentality copy$default(Experimentality experimentality, ClassId classId, Severity severity, String str, String str2, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                classId = experimentality.annotationClassId;
            }
            if ((i & 2) != 0) {
                severity = experimentality.severity;
            }
            Severity severity2 = severity;
            if ((i & 4) != 0) {
                str = experimentality.message;
            }
            String str3 = str;
            if ((i & 8) != 0) {
                str2 = experimentality.supertypeName;
            }
            String str4 = str2;
            if ((i & 16) != 0) {
                z = experimentality.fromSupertype;
            }
            return experimentality.copy(classId, severity2, str3, str4, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassId getAnnotationClassId() {
            return this.annotationClassId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Severity getSeverity() {
            return this.severity;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final String getSupertypeName() {
            return this.supertypeName;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getFromSupertype() {
            return this.fromSupertype;
        }

        public final Experimentality copy(ClassId annotationClassId, Severity severity, String message, String supertypeName, boolean fromSupertype) {
            Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
            Intrinsics.checkNotNullParameter(severity, "severity");
            return new Experimentality(annotationClassId, severity, message, supertypeName, fromSupertype);
        }

        public String toString() {
            return "Experimentality(annotationClassId=" + this.annotationClassId + ", severity=" + this.severity + ", message=" + this.message + ", supertypeName=" + this.supertypeName + ", fromSupertype=" + this.fromSupertype + ')';
        }

        public Experimentality(ClassId annotationClassId, Severity severity, String str, String str2, boolean z) {
            Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
            Intrinsics.checkNotNullParameter(severity, "severity");
            this.annotationClassId = annotationClassId;
            this.severity = severity;
            this.message = str;
            this.supertypeName = str2;
            this.fromSupertype = z;
        }

        public /* synthetic */ Experimentality(ClassId classId, Severity severity, String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(classId, severity, str, (i & 8) != 0 ? null : str2, (i & 16) != 0 ? false : z);
        }

        public final ClassId getAnnotationClassId() {
            return this.annotationClassId;
        }

        public final Severity getSeverity() {
            return this.severity;
        }

        public final String getMessage() {
            return this.message;
        }

        public final String getSupertypeName() {
            return this.supertypeName;
        }

        public final boolean getFromSupertype() {
            return this.fromSupertype;
        }

        /* JADX INFO: compiled from: FirOptInUsageBaseChecker.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Companion;", "", "()V", "DEFAULT_SEVERITY", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Severity;", "getDEFAULT_SEVERITY", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageBaseChecker$Experimentality$Severity;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Severity getDEFAULT_SEVERITY() {
                return Experimentality.DEFAULT_SEVERITY;
            }
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Experimentality)) {
                return false;
            }
            Experimentality experimentality = (Experimentality) other;
            return Intrinsics.areEqual(this.annotationClassId, experimentality.annotationClassId) && this.severity == experimentality.severity && Intrinsics.areEqual(this.message, experimentality.message);
        }

        public int hashCode() {
            int iHashCode = ((this.annotationClassId.hashCode() * 31) + this.severity.hashCode()) * 31;
            String str = this.message;
            return iHashCode + (str != null ? str.hashCode() : 0);
        }
    }

    private FirOptInUsageBaseChecker() {
    }

    public static /* synthetic */ Experimentality loadExperimentalityForMarkerAnnotation$default(FirOptInUsageBaseChecker firOptInUsageBaseChecker, FirRegularClassSymbol firRegularClassSymbol, FirSession firSession, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        return firOptInUsageBaseChecker.loadExperimentalityForMarkerAnnotation(firRegularClassSymbol, firSession, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Experimentality loadExperimentalityForMarkerAnnotation(FirRegularClassSymbol firRegularClassSymbol, FirSession session, String str) {
        Intrinsics.checkNotNullParameter(firRegularClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firRegularClassSymbol, FirResolvePhase.BODY_RESOLVE);
        return loadExperimentalityForMarkerAnnotation((FirRegularClass) firRegularClassSymbol.getFir(), session, str);
    }

    public final void loadExperimentalitiesFromAnnotationTo(FirBasedSymbol<?> firBasedSymbol, FirSession session, Collection<Experimentality> result) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(result, "result");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firBasedSymbol, FirResolvePhase.STATUS);
        loadExperimentalitiesFromAnnotationTo(firBasedSymbol.getFir(), session, result, false);
    }

    private final void loadExperimentalitiesFromAnnotationTo(FirDeclaration firDeclaration, FirSession firSession, Collection<Experimentality> collection, boolean z) {
        String strAsString;
        FqName className;
        Name nameShortName;
        FirExpression firExpressionFindArgumentByName;
        Experimentality experimentalityLoadExperimentalityForMarkerAnnotation$default;
        for (FirAnnotation firAnnotation : firDeclaration.getAnnotations()) {
            FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
            Experimentality experimentalityCopy$default = null;
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null) {
                if (firDeclaration instanceof FirRegularClass) {
                    strAsString = ((FirRegularClass) firDeclaration).getName().asString();
                } else {
                    strAsString = (!(firDeclaration instanceof FirCallableDeclaration) || (className = ((FirCallableDeclaration) firDeclaration).getSymbol().getCallableId().getClassName()) == null || (nameShortName = className.shortName()) == null) ? null : nameShortName.asString();
                }
                FirRegularClassSymbol firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeType.getLookupTag(), firSession);
                CollectionsKt.addIfNotNull(collection, firRegularClassSymbol != null ? loadExperimentalityForMarkerAnnotation(firRegularClassSymbol, firSession, strAsString) : null);
                if (z && Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_CLASS_ID()) && (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation, OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS())) != null) {
                    FirRegularClassSymbol firRegularClassSymbolExtractClassFromArgument = FirAnnotationHelpersKt.extractClassFromArgument(firExpressionFindArgumentByName, firSession);
                    if (firRegularClassSymbolExtractClassFromArgument != null && (experimentalityLoadExperimentalityForMarkerAnnotation$default = loadExperimentalityForMarkerAnnotation$default(this, firRegularClassSymbolExtractClassFromArgument, firSession, (String) null, 2, (Object) null)) != null) {
                        experimentalityCopy$default = Experimentality.copy$default(experimentalityLoadExperimentalityForMarkerAnnotation$default, null, null, null, null, true, 15, null);
                    }
                    CollectionsKt.addIfNotNull(collection, experimentalityCopy$default);
                }
            }
        }
    }

    public final Set<Experimentality> loadExperimentalitiesFromTypeArguments(CheckerContext context, List<? extends FirTypeProjection> typeArguments) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return SetsKt.emptySet();
        }
        List<? extends FirTypeProjection> list = typeArguments;
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.toConeTypeProjection((FirTypeProjection) it.next()));
        }
        return loadExperimentalitiesFromConeArguments(context, arrayList);
    }

    public final Set<Experimentality> loadExperimentalitiesFromConeArguments(CheckerContext context, List<? extends ConeTypeProjection> typeArguments) {
        ConeKotlinType type;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return SetsKt.emptySet();
        }
        SmartSet smartSetCreate = SmartSet.INSTANCE.create();
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            if (!ConeTypeProjectionKt.isStarProjection(coneTypeProjection) && (type = ConeTypeProjectionKt.getType(coneTypeProjection)) != null) {
                addExperimentalities$default(INSTANCE, type, context, smartSetCreate, null, 4, null);
            }
        }
        return smartSetCreate;
    }

    public final Set<Experimentality> loadExperimentalities(FirBasedSymbol<?> firBasedSymbol, CheckerContext context, boolean z, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return loadExperimentalities(firBasedSymbol, context, null, new LinkedHashSet(), z, coneKotlinType, false);
    }

    public final Set<Experimentality> loadExperimentalitiesFromSupertype(FirClassLikeSymbol<?> firClassLikeSymbol, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return loadExperimentalities(firClassLikeSymbol, context, null, new LinkedHashSet(), false, null, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Set<Experimentality> loadExperimentalities(FirBasedSymbol<?> firBasedSymbol, CheckerContext checkerContext, SmartSet<Experimentality> smartSet, Set<FirDeclaration> set, boolean z, ConeKotlinType coneKotlinType, boolean z2) {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firBasedSymbol, FirResolvePhase.STATUS);
        FirDeclaration fir = firBasedSymbol.getFir();
        if (!set.add(fir)) {
            return SetsKt.emptySet();
        }
        SmartSet<Experimentality> smartSetCreate = smartSet == null ? SmartSet.INSTANCE.create() : smartSet;
        FirSession session = checkerContext.getSession();
        if (fir instanceof FirCallableDeclaration) {
            loadCallableSpecificExperimentalities((FirCallableDeclaration) fir, firBasedSymbol, checkerContext, set, z, coneKotlinType, smartSetCreate);
        } else if (fir instanceof FirClassLikeDeclaration) {
            loadClassLikeSpecificExperimentalities((FirClassLikeDeclaration) fir, firBasedSymbol, checkerContext, set, smartSetCreate);
        } else if (!(fir instanceof FirAnonymousInitializer ? true : fir instanceof FirDanglingModifierList ? true : fir instanceof FirFile ? true : fir instanceof FirTypeParameter ? true : fir instanceof FirScript)) {
            boolean z3 = fir instanceof FirCodeFragment;
        }
        SmartSet<Experimentality> smartSet2 = smartSetCreate;
        loadExperimentalitiesFromAnnotationTo(fir, session, smartSet2, z2);
        if (FirAnnotationUtilsKt.getAnnotationByClassId(fir, OptInNames.INSTANCE.getWAS_EXPERIMENTAL_CLASS_ID(), session) != null) {
            FirSinceKotlinAccessibility firSinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility = FirSinceKotlinHelpersKt.checkSinceKotlinVersionAccessibility(fir, checkerContext);
            if (firSinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility instanceof FirSinceKotlinAccessibility.NotAccessibleButWasExperimental) {
                for (FirRegularClassSymbol firRegularClassSymbol : ((FirSinceKotlinAccessibility.NotAccessibleButWasExperimental) firSinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility).getMarkerClasses()) {
                    FirLazyDeclarationResolverKt.lazyResolveToPhase(firRegularClassSymbol, FirResolvePhase.STATUS);
                    CollectionsKt.addIfNotNull(smartSet2, loadExperimentalityForMarkerAnnotation$default(INSTANCE, (FirRegularClass) firRegularClassSymbol.getFir(), session, (String) null, 2, (Object) null));
                }
            }
        }
        return smartSetCreate;
    }

    private final void loadCallableSpecificExperimentalities(FirCallableDeclaration firCallableDeclaration, FirBasedSymbol<?> firBasedSymbol, CheckerContext checkerContext, Set<FirDeclaration> set, boolean z, ConeKotlinType coneKotlinType, SmartSet<Experimentality> smartSet) {
        FirTypeRef typeRef;
        FirPropertyAccessorSymbol setterSymbol;
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableDeclaration);
        ConeKotlinType coneType = null;
        FirClassLikeSymbol<?> symbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, checkerContext.getSession()) : null;
        FirRegularClassSymbol firRegularClassSymbol = symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
        if (!(firCallableDeclaration instanceof FirConstructor)) {
            FirTypeRef returnTypeRef = firCallableDeclaration.getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            addExperimentalities(type, checkerContext, smartSet, set);
            FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
            if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null) {
                coneType = FirTypeUtilsKt.getConeType(typeRef);
            }
            addExperimentalities(coneType, checkerContext, smartSet, set);
        } else if (firRegularClassSymbol != null) {
            loadExperimentalities(firRegularClassSymbol, checkerContext, smartSet, set, false, null, false);
        }
        if (coneKotlinType != null) {
            addExperimentalities(coneKotlinType, checkerContext, smartSet, set);
        }
        if (firCallableDeclaration instanceof FirFunction) {
            Iterator<T> it = ((FirFunction) firCallableDeclaration).getValueParameters().iterator();
            while (it.hasNext()) {
                INSTANCE.addExperimentalities(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef()), checkerContext, smartSet, set);
            }
        }
        if (z && (firBasedSymbol instanceof FirPropertySymbol) && (setterSymbol = ((FirPropertySymbol) firBasedSymbol).getSetterSymbol()) != null) {
            loadExperimentalities(setterSymbol, checkerContext, smartSet, set, false, coneKotlinType, false);
        }
    }

    private final void loadClassLikeSpecificExperimentalities(FirClassLikeDeclaration firClassLikeDeclaration, FirBasedSymbol<?> firBasedSymbol, CheckerContext checkerContext, Set<FirDeclaration> set, SmartSet<Experimentality> smartSet) {
        FirClassLikeSymbol<?> firClassLikeSymbolOuterClassSymbol;
        if (firClassLikeDeclaration instanceof FirRegularClass) {
            if (!(firBasedSymbol instanceof FirRegularClassSymbol) || (firClassLikeSymbolOuterClassSymbol = FirHelpersKt.outerClassSymbol((FirClassLikeSymbol) firBasedSymbol, checkerContext)) == null) {
                return;
            }
            loadExperimentalities(firClassLikeSymbolOuterClassSymbol, checkerContext, smartSet, set, false, null, false);
            return;
        }
        if (firClassLikeDeclaration instanceof FirTypeAlias) {
            addExperimentalities(FirTypeUtilsKt.getConeType(((FirTypeAlias) firClassLikeDeclaration).getExpandedTypeRef()), checkerContext, smartSet, set);
        } else {
            boolean z = firClassLikeDeclaration instanceof FirAnonymousObject;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void addExperimentalities$default(FirOptInUsageBaseChecker firOptInUsageBaseChecker, ConeKotlinType coneKotlinType, CheckerContext checkerContext, SmartSet smartSet, Set set, int i, Object obj) {
        if ((i & 4) != 0) {
            set = new LinkedHashSet();
        }
        firOptInUsageBaseChecker.addExperimentalities(coneKotlinType, checkerContext, smartSet, set);
    }

    private final void addExperimentalities(ConeKotlinType coneKotlinType, CheckerContext checkerContext, SmartSet<Experimentality> smartSet, Set<FirDeclaration> set) {
        ConeKotlinType type;
        if (coneKotlinType instanceof ConeClassLikeType) {
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneKotlinType;
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), checkerContext.getSession());
            if (symbol != null) {
                loadExperimentalities(symbol, checkerContext, smartSet, set, false, null, false);
            }
            for (ConeTypeProjection coneTypeProjection : TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, checkerContext.getSession(), null, 2, null).getTypeArguments()) {
                if (!ConeTypeProjectionKt.isStarProjection(coneTypeProjection) && (type = ConeTypeProjectionKt.getType(coneTypeProjection)) != null) {
                    INSTANCE.addExperimentalities(type, checkerContext, smartSet, set);
                }
            }
        }
    }

    static /* synthetic */ Experimentality loadExperimentalityForMarkerAnnotation$default(FirOptInUsageBaseChecker firOptInUsageBaseChecker, FirRegularClass firRegularClass, FirSession firSession, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        return firOptInUsageBaseChecker.loadExperimentalityForMarkerAnnotation(firRegularClass, firSession, str);
    }

    private final Experimentality loadExperimentalityForMarkerAnnotation(FirRegularClass firRegularClass, FirSession firSession, String str) {
        Experimentality.Severity default_severity;
        FirReference calleeReference;
        FirResolvedNamedReference resolved;
        Name name2;
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firRegularClass, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), firSession);
        if (annotationByClassId == null) {
            return null;
        }
        FirExpression firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(annotationByClassId, LEVEL);
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpressionFindArgumentByName instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpressionFindArgumentByName : null;
        String strAsString = (firQualifiedAccessExpression == null || (calleeReference = firQualifiedAccessExpression.getCalleeReference()) == null || (resolved = FirReferenceUtilsKt.getResolved(calleeReference)) == null || (name2 = resolved.getName()) == null) ? null : name2.asString();
        Experimentality.Severity[] severityArrValues = Experimentality.Severity.values();
        int length = severityArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                default_severity = null;
                break;
            }
            default_severity = severityArrValues[i];
            if (Intrinsics.areEqual(default_severity.name(), strAsString)) {
                break;
            }
            i++;
        }
        if (default_severity == null) {
            default_severity = Experimentality.INSTANCE.getDEFAULT_SEVERITY();
        }
        Experimentality.Severity severity = default_severity;
        FirExpression firExpressionFindArgumentByName2 = FirAnnotationUtilsKt.findArgumentByName(annotationByClassId, MESSAGE);
        FirConstExpression firConstExpression = firExpressionFindArgumentByName2 instanceof FirConstExpression ? (FirConstExpression) firExpressionFindArgumentByName2 : null;
        Object value = firConstExpression != null ? firConstExpression.getValue() : null;
        return new Experimentality(firRegularClass.getSymbol().getClassId(), severity, value instanceof String ? (String) value : null, str, false, 16, null);
    }

    public static /* synthetic */ void reportNotAcceptedExperimentalities$default(FirOptInUsageBaseChecker firOptInUsageBaseChecker, Collection collection, FirElement firElement, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 16) != 0) {
            ktSourceElement = firElement.getSource();
        }
        firOptInUsageBaseChecker.reportNotAcceptedExperimentalities(collection, firElement, checkerContext, diagnosticReporter, ktSourceElement);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0096  */
    public final void reportNotAcceptedExperimentalities(Collection<Experimentality> experimentalities, FirElement element, CheckerContext context, DiagnosticReporter reporter, KtSourceElement source) {
        Pair pair;
        String strBuildDefaultDiagnosticMessage;
        Intrinsics.checkNotNullParameter(experimentalities, "experimentalities");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (Experimentality experimentality : experimentalities) {
            ClassId annotationClassId = experimentality.getAnnotationClassId();
            Experimentality.Severity severity = experimentality.getSeverity();
            String message = experimentality.getMessage();
            if (!isExperimentalityAcceptableInContext(annotationClassId, context, experimentality.getFromSupertype())) {
                int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
                if (i == 1) {
                    pair = TuplesKt.to(FirErrors.INSTANCE.getOPT_IN_USAGE(), "should");
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    pair = TuplesKt.to(FirErrors.INSTANCE.getOPT_IN_USAGE_ERROR(), "must");
                }
                KtDiagnosticFactory2 ktDiagnosticFactory2 = (KtDiagnosticFactory2) pair.component1();
                String str = (String) pair.component2();
                FqName fqNameAsSingleFqName = annotationClassId.asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                if (message != null) {
                    if (StringsKt.isBlank(message)) {
                        message = null;
                    }
                    if (message == null) {
                        OptInNames optInNames = OptInNames.INSTANCE;
                        String strBuildMessagePrefix = OptInNames.INSTANCE.buildMessagePrefix(str);
                        String strAsString = fqNameAsSingleFqName.asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        strBuildDefaultDiagnosticMessage = optInNames.buildDefaultDiagnosticMessage(strBuildMessagePrefix, strAsString);
                    } else {
                        strBuildDefaultDiagnosticMessage = message;
                    }
                } else {
                    OptInNames optInNames2 = OptInNames.INSTANCE;
                    String strBuildMessagePrefix2 = OptInNames.INSTANCE.buildMessagePrefix(str);
                    String strAsString2 = fqNameAsSingleFqName.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    strBuildDefaultDiagnosticMessage = optInNames2.buildDefaultDiagnosticMessage(strBuildMessagePrefix2, strAsString2);
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, ktDiagnosticFactory2, fqNameAsSingleFqName, strBuildDefaultDiagnosticMessage, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }
    }

    @SymbolInternals
    public final void reportNotAcceptedOverrideExperimentalities(Collection<Experimentality> experimentalities, FirCallableSymbol<?> symbol, CheckerContext context, DiagnosticReporter reporter) {
        Pair pair;
        Intrinsics.checkNotNullParameter(experimentalities, "experimentalities");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (Experimentality experimentality : experimentalities) {
            ClassId annotationClassId = experimentality.getAnnotationClassId();
            Experimentality.Severity severity = experimentality.getSeverity();
            String message = experimentality.getMessage();
            String supertypeName = experimentality.getSupertypeName();
            if (!isExperimentalityAcceptable(symbol.getFir(), context.getSession(), annotationClassId, false) && !isExperimentalityAcceptableInContext(annotationClassId, context, false)) {
                int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
                if (i == 1) {
                    pair = TuplesKt.to(FirErrors.INSTANCE.getOPT_IN_OVERRIDE(), "should");
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    pair = TuplesKt.to(FirErrors.INSTANCE.getOPT_IN_OVERRIDE_ERROR(), "must");
                }
                KtDiagnosticFactory2 ktDiagnosticFactory2 = (KtDiagnosticFactory2) pair.component1();
                String str = (String) pair.component2();
                OptInNames optInNames = OptInNames.INSTANCE;
                if (supertypeName == null) {
                    supertypeName = "???";
                }
                String strAsFqNameString = annotationClassId.asFqNameString();
                Intrinsics.checkNotNullExpressionValue(strAsFqNameString, "asFqNameString(...)");
                String strBuildOverrideMessage = optInNames.buildOverrideMessage(supertypeName, message, str, strAsFqNameString);
                KtSourceElement source = symbol.getSource();
                FqName fqNameAsSingleFqName = annotationClassId.asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, ktDiagnosticFactory2, fqNameAsSingleFqName, strBuildOverrideMessage, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }
    }

    private final boolean isExperimentalityAcceptableInContext(ClassId annotationClassId, CheckerContext context, boolean fromSupertype) {
        LanguageVersionSettings languageVersionSettings = FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession());
        String strAsFqNameString = annotationClassId.asFqNameString();
        Intrinsics.checkNotNullExpressionValue(strAsFqNameString, "asFqNameString(...)");
        if (((List) languageVersionSettings.getFlag(AnalysisFlags.getOptIn())).contains(strAsFqNameString)) {
            return true;
        }
        Iterator<FirAnnotationContainer> it = context.getAnnotationContainers().iterator();
        while (it.hasNext()) {
            if (isExperimentalityAcceptable(it.next(), context.getSession(), annotationClassId, fromSupertype)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isExperimentalityAcceptable(FirAnnotationContainer firAnnotationContainer, FirSession firSession, ClassId classId, boolean z) {
        return FirAnnotationUtilsKt.getAnnotationByClassId(firAnnotationContainer, classId, firSession) != null || isAnnotatedWithOptIn(firAnnotationContainer, classId, firSession) || (z && isAnnotatedWithSubclassOptInRequired(firAnnotationContainer, firSession, classId)) || primaryConstructorParameterIsExperimentalityAcceptable(firAnnotationContainer, firSession, classId);
    }

    private final boolean primaryConstructorParameterIsExperimentalityAcceptable(FirAnnotationContainer firAnnotationContainer, FirSession firSession, ClassId classId) {
        FirValueParameterSymbol correspondingValueParameterFromPrimaryConstructor;
        if ((firAnnotationContainer instanceof FirProperty) && (correspondingValueParameterFromPrimaryConstructor = DeclarationAttributesKt.getCorrespondingValueParameterFromPrimaryConstructor((FirProperty) firAnnotationContainer)) != null) {
            return isExperimentalityAcceptable(correspondingValueParameterFromPrimaryConstructor.getFir(), firSession, classId, false);
        }
        return false;
    }

    private final boolean isAnnotatedWithOptIn(FirAnnotationContainer firAnnotationContainer, ClassId classId, FirSession firSession) {
        FirExpression firExpressionFindArgumentByName;
        ConeClassLikeLookupTag lookupTag;
        for (FirAnnotation firAnnotation : firAnnotationContainer.getAnnotations()) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
            ClassId classId2 = null;
            ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                classId2 = lookupTag.getClassId();
            }
            if (Intrinsics.areEqual(classId2, OptInNames.INSTANCE.getOPT_IN_CLASS_ID()) && (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation, OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS())) != null) {
                List<FirRegularClassSymbol> listExtractClassesFromArgument = FirAnnotationHelpersKt.extractClassesFromArgument(firExpressionFindArgumentByName, firSession);
                if (!(listExtractClassesFromArgument instanceof Collection) || !listExtractClassesFromArgument.isEmpty()) {
                    Iterator<T> it = listExtractClassesFromArgument.iterator();
                    while (it.hasNext()) {
                        if (Intrinsics.areEqual(((FirRegularClassSymbol) it.next()).getClassId(), classId)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean isAnnotatedWithSubclassOptInRequired(FirAnnotationContainer firAnnotationContainer, FirSession firSession, ClassId classId) {
        FirExpression firExpressionFindArgumentByName;
        ConeClassLikeLookupTag lookupTag;
        for (FirAnnotation firAnnotation : firAnnotationContainer.getAnnotations()) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
            ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
            if (Intrinsics.areEqual((coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : lookupTag.getClassId(), OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_CLASS_ID()) && (firExpressionFindArgumentByName = FirAnnotationUtilsKt.findArgumentByName(firAnnotation, OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS())) != null) {
                FirRegularClassSymbol firRegularClassSymbolExtractClassFromArgument = FirAnnotationHelpersKt.extractClassFromArgument(firExpressionFindArgumentByName, firSession);
                if (Intrinsics.areEqual(firRegularClassSymbolExtractClassFromArgument != null ? firRegularClassSymbolExtractClassFromArgument.getClassId() : null, classId)) {
                    return true;
                }
            }
        }
        return false;
    }

    static {
        Name nameIdentifier = Name.identifier(FirebaseAnalytics.Param.LEVEL);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        LEVEL = nameIdentifier;
        Name nameIdentifier2 = Name.identifier(CrashHianalyticsData.MESSAGE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        MESSAGE = nameIdentifier2;
    }
}
