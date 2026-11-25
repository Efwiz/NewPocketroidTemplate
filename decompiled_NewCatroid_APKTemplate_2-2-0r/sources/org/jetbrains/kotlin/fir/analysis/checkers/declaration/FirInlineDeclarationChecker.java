package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponent;
import org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FirInlineDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001\u001eB\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J6\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0014\u0010\u0016\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\r0\u00180\u00172\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\"\u0010\u001d\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\r0\u00180\u0017*\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkCallableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkCanBeInlined", "", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "checkNothingToInline", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "checkParameters", "overriddenSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "checkParametersInNotInline", "isInlinableDefaultValue", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getOverriddenSymbols", "InlineFunctionBodyContext", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirInlineDeclarationChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirInlineDeclarationChecker INSTANCE = new FirInlineDeclarationChecker();

    private FirInlineDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!declaration.getStatus().isInline()) {
            checkParametersInNotInline(declaration, context, reporter);
            return;
        }
        FirInlineCheckerPlatformSpecificComponent inlineCheckerExtension = FirInlineCheckerPlatformSpecificComponentKt.getInlineCheckerExtension(context.getSession());
        if (inlineCheckerExtension == null || inlineCheckerExtension.isGenerallyOk(declaration, context, reporter)) {
            if ((declaration instanceof FirPropertyAccessor) || (declaration instanceof FirSimpleFunction)) {
                checkCallableDeclaration(declaration, context, reporter);
            }
        }
    }

    /* JADX INFO: compiled from: FirInlineDeclarationChecker.kt */
    @Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001EB+\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ9\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0000¢\u0006\u0002\b J3\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0000¢\u0006\u0002\b%J,\u0010&\u001a\u00020\u00022\n\u0010'\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J3\u0010(\u001a\u00020\u00022\u0006\u0010)\u001a\u00020*2\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0000¢\u0006\u0002\b+J6\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J3\u00101\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0000¢\u0006\u0002\b2J,\u00103\u001a\u00020\u00022\n\u0010$\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J,\u00104\u001a\u00020\u00022\n\u0010'\u001a\u0006\u0012\u0002\b\u0003052\u0006\u00106\u001a\u00020*2\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J6\u00107\u001a\u00020\u00022\u0006\u00108\u001a\u00020*2\f\u0010'\u001a\b\u0012\u0002\b\u0003\u0018\u0001052\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0016\u00109\u001a\u00020\u00112\f\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001aH\u0002J\u0018\u0010:\u001a\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0003H\u0016J\u0018\u0010>\u001a\u00020\u00022\u0006\u0010?\u001a\u00020@2\u0006\u0010=\u001a\u00020\u0003H\u0016J\u0010\u0010A\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030BH\u0002J\u0010\u0010C\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\u001aH\u0002J\u0014\u0010D\u001a\u00020\u0011*\u00020*2\u0006\u0010\u001d\u001a\u00020\u0003H\u0002R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "inlineFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "inlineFunEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "inlinableParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirSession;)V", "getInlineFunction", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "isEffectivelyPrivateApiFunction", "", "prohibitProtectedCallFromInline", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "checkAccessedDeclaration", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext$AccessedDeclarationVisibilityData;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "accessedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "declarationVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "context", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkAccessedDeclaration$checkers", "checkArgumentsOfCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "targetSymbol", "checkArgumentsOfCall$checkers", "checkPrivateClassMemberAccess", "calledDeclaration", "checkQualifiedAccess", "qualifiedAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "checkQualifiedAccess$checkers", "checkReceiver", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "receiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "checkReceiversOfQualifiedAccessExpression", "checkReceiversOfQualifiedAccessExpression$checkers", "checkRecursion", "checkSuperCalls", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "callExpression", "checkVisibilityAndAccess", "accessExpression", "isInvokeOrInlineExtension", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "data", "visitSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "isDefinedInInlineFunction", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "isInsidePrivateClass", "partOfCall", "AccessedDeclarationVisibilityData", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InlineFunctionBodyContext extends FirDefaultVisitor<Unit, CheckerContext> {
        private final List<FirValueParameterSymbol> inlinableParameters;
        private final EffectiveVisibility inlineFunEffectiveVisibility;
        private final FirFunction inlineFunction;
        private final boolean isEffectivelyPrivateApiFunction;
        private final boolean prohibitProtectedCallFromInline;
        private final FirSession session;

        public void visitElement(FirElement element, CheckerContext data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
        }

        public void visitSmartCastExpression(FirSmartCastExpression smartCastExpression, CheckerContext data) {
            Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
            Intrinsics.checkNotNullParameter(data, "data");
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
            visitElement(firElement, (CheckerContext) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
        public /* bridge */ /* synthetic */ Object visitSmartCastExpression(FirSmartCastExpression firSmartCastExpression, Object obj) {
            visitSmartCastExpression(firSmartCastExpression, (CheckerContext) obj);
            return Unit.INSTANCE;
        }

        public final FirFunction getInlineFunction() {
            return this.inlineFunction;
        }

        public final FirSession getSession() {
            return this.session;
        }

        public InlineFunctionBodyContext(FirFunction inlineFunction, EffectiveVisibility inlineFunEffectiveVisibility, List<FirValueParameterSymbol> inlinableParameters, FirSession session) {
            Intrinsics.checkNotNullParameter(inlineFunction, "inlineFunction");
            Intrinsics.checkNotNullParameter(inlineFunEffectiveVisibility, "inlineFunEffectiveVisibility");
            Intrinsics.checkNotNullParameter(inlinableParameters, "inlinableParameters");
            Intrinsics.checkNotNullParameter(session, "session");
            this.inlineFunction = inlineFunction;
            this.inlineFunEffectiveVisibility = inlineFunEffectiveVisibility;
            this.inlinableParameters = inlinableParameters;
            this.session = session;
            this.isEffectivelyPrivateApiFunction = inlineFunEffectiveVisibility.getPrivateApi();
            this.prohibitProtectedCallFromInline = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.ProhibitProtectedCallFromInline);
        }

        public final AccessedDeclarationVisibilityData checkAccessedDeclaration$checkers(KtSourceElement source, FirBasedSymbol<?> accessedSymbol, Visibility declarationVisibility, CheckerContext context, DiagnosticReporter reporter) {
            EffectiveVisibility.Public publishedApiEffectiveVisibility;
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(accessedSymbol, "accessedSymbol");
            Intrinsics.checkNotNullParameter(declarationVisibility, "declarationVisibility");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            if (!(accessedSymbol instanceof FirCallableSymbol)) {
                if (!(accessedSymbol instanceof FirClassLikeSymbol)) {
                    AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                    throw null;
                }
                FirLazyDeclarationResolverKt.lazyResolveToPhase(accessedSymbol, FirResolvePhase.STATUS);
                publishedApiEffectiveVisibility = PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(accessedSymbol.getFir());
                if (publishedApiEffectiveVisibility == null) {
                    publishedApiEffectiveVisibility = ((FirClassLikeSymbol) accessedSymbol).getResolvedStatus().getEffectiveVisibility();
                }
            } else {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(accessedSymbol, FirResolvePhase.STATUS);
                publishedApiEffectiveVisibility = PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(accessedSymbol.getFir());
                if (publishedApiEffectiveVisibility == null) {
                    publishedApiEffectiveVisibility = ((FirCallableSymbol) accessedSymbol).getResolvedStatus().getEffectiveVisibility();
                }
            }
            if (Intrinsics.areEqual(publishedApiEffectiveVisibility, EffectiveVisibility.Local.INSTANCE)) {
                publishedApiEffectiveVisibility = EffectiveVisibility.Public.INSTANCE;
            }
            EffectiveVisibility effectiveVisibility = publishedApiEffectiveVisibility;
            boolean publicApi = effectiveVisibility.getPublicApi();
            boolean publicApi2 = this.inlineFunEffectiveVisibility.getPublicApi();
            if (publicApi2 && !publicApi && declarationVisibility != Visibilities.Local.INSTANCE) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_PUBLIC_CALL_FROM_PUBLIC_INLINE(), this.inlineFunction.getSymbol(), accessedSymbol, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            } else {
                checkPrivateClassMemberAccess(accessedSymbol, source, context, reporter);
            }
            return new AccessedDeclarationVisibilityData(publicApi2, publicApi, effectiveVisibility);
        }

        /* JADX INFO: compiled from: FirInlineDeclarationChecker.kt */
        @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirInlineDeclarationChecker$InlineFunctionBodyContext$AccessedDeclarationVisibilityData;", "", "isInlineFunPublicOrPublishedApi", "", "isCalledFunPublicOrPublishedApi", "calledFunEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "(ZZLorg/jetbrains/kotlin/descriptors/EffectiveVisibility;)V", "getCalledFunEffectiveVisibility", "()Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class AccessedDeclarationVisibilityData {
            private final EffectiveVisibility calledFunEffectiveVisibility;
            private final boolean isCalledFunPublicOrPublishedApi;
            private final boolean isInlineFunPublicOrPublishedApi;

            public static /* synthetic */ AccessedDeclarationVisibilityData copy$default(AccessedDeclarationVisibilityData accessedDeclarationVisibilityData, boolean z, boolean z2, EffectiveVisibility effectiveVisibility, int i, Object obj) {
                if ((i & 1) != 0) {
                    z = accessedDeclarationVisibilityData.isInlineFunPublicOrPublishedApi;
                }
                if ((i & 2) != 0) {
                    z2 = accessedDeclarationVisibilityData.isCalledFunPublicOrPublishedApi;
                }
                if ((i & 4) != 0) {
                    effectiveVisibility = accessedDeclarationVisibilityData.calledFunEffectiveVisibility;
                }
                return accessedDeclarationVisibilityData.copy(z, z2, effectiveVisibility);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final boolean getIsInlineFunPublicOrPublishedApi() {
                return this.isInlineFunPublicOrPublishedApi;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final boolean getIsCalledFunPublicOrPublishedApi() {
                return this.isCalledFunPublicOrPublishedApi;
            }

            /* JADX INFO: renamed from: component3, reason: from getter */
            public final EffectiveVisibility getCalledFunEffectiveVisibility() {
                return this.calledFunEffectiveVisibility;
            }

            public final AccessedDeclarationVisibilityData copy(boolean isInlineFunPublicOrPublishedApi, boolean isCalledFunPublicOrPublishedApi, EffectiveVisibility calledFunEffectiveVisibility) {
                Intrinsics.checkNotNullParameter(calledFunEffectiveVisibility, "calledFunEffectiveVisibility");
                return new AccessedDeclarationVisibilityData(isInlineFunPublicOrPublishedApi, isCalledFunPublicOrPublishedApi, calledFunEffectiveVisibility);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof AccessedDeclarationVisibilityData)) {
                    return false;
                }
                AccessedDeclarationVisibilityData accessedDeclarationVisibilityData = (AccessedDeclarationVisibilityData) other;
                return this.isInlineFunPublicOrPublishedApi == accessedDeclarationVisibilityData.isInlineFunPublicOrPublishedApi && this.isCalledFunPublicOrPublishedApi == accessedDeclarationVisibilityData.isCalledFunPublicOrPublishedApi && Intrinsics.areEqual(this.calledFunEffectiveVisibility, accessedDeclarationVisibilityData.calledFunEffectiveVisibility);
            }

            public int hashCode() {
                return (((Boolean.hashCode(this.isInlineFunPublicOrPublishedApi) * 31) + Boolean.hashCode(this.isCalledFunPublicOrPublishedApi)) * 31) + this.calledFunEffectiveVisibility.hashCode();
            }

            public String toString() {
                return "AccessedDeclarationVisibilityData(isInlineFunPublicOrPublishedApi=" + this.isInlineFunPublicOrPublishedApi + ", isCalledFunPublicOrPublishedApi=" + this.isCalledFunPublicOrPublishedApi + ", calledFunEffectiveVisibility=" + this.calledFunEffectiveVisibility + ')';
            }

            public AccessedDeclarationVisibilityData(boolean z, boolean z2, EffectiveVisibility calledFunEffectiveVisibility) {
                Intrinsics.checkNotNullParameter(calledFunEffectiveVisibility, "calledFunEffectiveVisibility");
                this.isInlineFunPublicOrPublishedApi = z;
                this.isCalledFunPublicOrPublishedApi = z2;
                this.calledFunEffectiveVisibility = calledFunEffectiveVisibility;
            }

            public final boolean isInlineFunPublicOrPublishedApi() {
                return this.isInlineFunPublicOrPublishedApi;
            }

            public final boolean isCalledFunPublicOrPublishedApi() {
                return this.isCalledFunPublicOrPublishedApi;
            }

            public final EffectiveVisibility getCalledFunEffectiveVisibility() {
                return this.calledFunEffectiveVisibility;
            }
        }

        public final void checkReceiversOfQualifiedAccessExpression$checkers(FirQualifiedAccessExpression qualifiedAccessExpression, FirBasedSymbol<?> targetSymbol, CheckerContext context, DiagnosticReporter reporter) {
            Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            checkReceiver(qualifiedAccessExpression, qualifiedAccessExpression.getDispatchReceiver(), targetSymbol, context, reporter);
            checkReceiver(qualifiedAccessExpression, qualifiedAccessExpression.getExtensionReceiver(), targetSymbol, context, reporter);
        }

        public final void checkArgumentsOfCall$checkers(FirFunctionCall functionCall, FirBasedSymbol<?> targetSymbol, CheckerContext context, DiagnosticReporter reporter) {
            Object next;
            KtDiagnosticFactory1<FirBasedSymbol<?>> usage_is_not_inlinable;
            Intrinsics.checkNotNullParameter(functionCall, "functionCall");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            if (context.getIsContractBody()) {
                return;
            }
            FirNamedFunctionSymbol firNamedFunctionSymbol = targetSymbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) targetSymbol : null;
            if (firNamedFunctionSymbol == null) {
                return;
            }
            FirArgumentList argumentList = functionCall.getArgumentList();
            LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
            if (mapping == null) {
                return;
            }
            for (Map.Entry<FirExpression, FirValueParameter> entry : mapping.entrySet()) {
                FirExpression key = entry.getKey();
                FirValueParameter value = entry.getValue();
                FirExpression firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(key);
                FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firExpressionUnwrapArgument);
                FirVariableSymbol firVariableSymbol = resolvedCallableSymbol instanceof FirVariableSymbol ? (FirVariableSymbol) resolvedCallableSymbol : null;
                if (firVariableSymbol != null) {
                    Iterator<T> it = this.inlinableParameters.iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!Intrinsics.areEqual((FirValueParameterSymbol) next, firVariableSymbol));
                    FirValueParameterSymbol firValueParameterSymbol = (FirValueParameterSymbol) next;
                    if (firValueParameterSymbol != null) {
                        if (firNamedFunctionSymbol.getRawStatus().isInline()) {
                            if (value.getIsNoinline()) {
                                usage_is_not_inlinable = FirErrors.INSTANCE.getUSAGE_IS_NOT_INLINABLE();
                            } else if (value.getIsCrossinline() && !firValueParameterSymbol.isCrossinline()) {
                                usage_is_not_inlinable = FirErrors.INSTANCE.getNON_LOCAL_RETURN_NOT_ALLOWED();
                            }
                        } else {
                            usage_is_not_inlinable = FirErrors.INSTANCE.getUSAGE_IS_NOT_INLINABLE();
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpressionUnwrapArgument.getSource(), usage_is_not_inlinable, firValueParameterSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
            }
        }

        private final void checkReceiver(FirQualifiedAccessExpression qualifiedAccessExpression, FirExpression receiverExpression, FirBasedSymbol<?> targetSymbol, CheckerContext context, DiagnosticReporter reporter) {
            FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(receiverExpression);
            if (resolvedCallableSymbol == null || !CollectionsKt.contains(this.inlinableParameters, resolvedCallableSymbol) || isInvokeOrInlineExtension(targetSymbol)) {
                return;
            }
            KtSourceElement source = receiverExpression.getSource();
            if (source == null) {
                source = qualifiedAccessExpression.getSource();
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getUSAGE_IS_NOT_INLINABLE(), resolvedCallableSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }

        private final boolean isInvokeOrInlineExtension(FirBasedSymbol<?> targetSymbol) {
            ConeSimpleKotlinType dispatchReceiverType;
            if (!(targetSymbol instanceof FirNamedFunctionSymbol)) {
                return false;
            }
            FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) targetSymbol;
            return Intrinsics.areEqual(firNamedFunctionSymbol.getName(), OperatorNameConventions.INVOKE) && (dispatchReceiverType = firNamedFunctionSymbol.getDispatchReceiverType()) != null && FunctionalTypeUtilsKt.isSomeFunctionType(dispatchReceiverType, this.session);
        }

        public final void checkQualifiedAccess$checkers(FirStatement qualifiedAccess, FirBasedSymbol<?> targetSymbol, CheckerContext context, DiagnosticReporter reporter) {
            Intrinsics.checkNotNullParameter(qualifiedAccess, "qualifiedAccess");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            KtSourceElement source = qualifiedAccess.getSource();
            if (source != null && (targetSymbol instanceof FirCallableSymbol)) {
                if (CollectionsKt.contains(this.inlinableParameters, targetSymbol)) {
                    if (!partOfCall(qualifiedAccess, context)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getUSAGE_IS_NOT_INLINABLE(), targetSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                    List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
                    if (!(containingDeclarations instanceof Collection) || !containingDeclarations.isEmpty()) {
                        Iterator<T> it = containingDeclarations.iterator();
                        while (it.hasNext()) {
                            if (CollectionsKt.contains(this.inlinableParameters, ((FirDeclaration) it.next()).getSymbol())) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNOT_SUPPORTED_INLINE_PARAMETER_IN_INLINE_PARAMETER_DEFAULT_VALUE(), (FirValueParameterSymbol) targetSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                                break;
                            }
                        }
                    }
                }
                checkVisibilityAndAccess(qualifiedAccess, (FirCallableSymbol) targetSymbol, source, context, reporter);
                checkRecursion(targetSymbol, source, context, reporter);
            }
        }

        private final boolean partOfCall(FirStatement firStatement, CheckerContext checkerContext) {
            FirStatement firStatement2;
            if (!(firStatement instanceof FirExpression) || (firStatement2 = (FirStatement) CollectionsKt.getOrNull(checkerContext.getCallsOrAssignments(), checkerContext.getCallsOrAssignments().size() - 2)) == null) {
                return false;
            }
            FirQualifiedAccessExpression firQualifiedAccessExpression = firStatement2 instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firStatement2 : null;
            if (Intrinsics.areEqual(firStatement, firQualifiedAccessExpression != null ? firQualifiedAccessExpression.getExplicitReceiver() : null)) {
                return true;
            }
            FirCall firCall = firStatement2 instanceof FirCall ? (FirCall) firStatement2 : null;
            if (firCall == null) {
                return false;
            }
            List<FirExpression> arguments = firCall.getArgumentList().getArguments();
            if ((arguments instanceof Collection) && arguments.isEmpty()) {
                return false;
            }
            Iterator<T> it = arguments.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(FirExpressionUtilKt.unwrapArgument((FirExpression) it.next()), firStatement)) {
                    return true;
                }
            }
            return false;
        }

        private final void checkVisibilityAndAccess(FirStatement accessExpression, FirCallableSymbol<?> calledDeclaration, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
            KtDiagnosticFactory2<FirBasedSymbol<?>, FirBasedSymbol<?>> protected_call_from_public_inline_error;
            if (calledDeclaration == null || Intrinsics.areEqual(calledDeclaration.getCallableId().getCallableName(), StandardNames.BACKING_FIELD)) {
                return;
            }
            AccessedDeclarationVisibilityData accessedDeclarationVisibilityDataCheckAccessedDeclaration$checkers = checkAccessedDeclaration$checkers(source, calledDeclaration, calledDeclaration.getResolvedStatus().getVisibility(), context, reporter);
            boolean isInlineFunPublicOrPublishedApi = accessedDeclarationVisibilityDataCheckAccessedDeclaration$checkers.getIsInlineFunPublicOrPublishedApi();
            boolean isCalledFunPublicOrPublishedApi = accessedDeclarationVisibilityDataCheckAccessedDeclaration$checkers.getIsCalledFunPublicOrPublishedApi();
            EffectiveVisibility calledFunEffectiveVisibility = accessedDeclarationVisibilityDataCheckAccessedDeclaration$checkers.getCalledFunEffectiveVisibility();
            if (isInlineFunPublicOrPublishedApi && isCalledFunPublicOrPublishedApi) {
                checkSuperCalls(calledDeclaration, accessExpression, context, reporter);
            }
            boolean z = calledDeclaration instanceof FirConstructorSymbol;
            if (isInlineFunPublicOrPublishedApi && this.inlineFunEffectiveVisibility.toVisibility() != Visibilities.Protected.INSTANCE && calledFunEffectiveVisibility.toVisibility() == Visibilities.Protected.INSTANCE) {
                if (z) {
                    protected_call_from_public_inline_error = FirErrors.INSTANCE.getPROTECTED_CONSTRUCTOR_CALL_FROM_PUBLIC_INLINE();
                } else {
                    protected_call_from_public_inline_error = this.prohibitProtectedCallFromInline ? FirErrors.INSTANCE.getPROTECTED_CALL_FROM_PUBLIC_INLINE_ERROR() : FirErrors.INSTANCE.getPROTECTED_CALL_FROM_PUBLIC_INLINE();
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, protected_call_from_public_inline_error, this.inlineFunction.getSymbol(), calledDeclaration, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        }

        private final void checkPrivateClassMemberAccess(FirBasedSymbol<?> calledDeclaration, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
            if (this.isEffectivelyPrivateApiFunction || !isInsidePrivateClass(calledDeclaration)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getPRIVATE_CLASS_MEMBER_FROM_INLINE(), calledDeclaration, this.inlineFunction.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }

        private final void checkSuperCalls(FirCallableSymbol<?> calledDeclaration, FirStatement callExpression, CheckerContext context, DiagnosticReporter reporter) {
            FirExpression dispatchReceiver;
            FirClassifierSymbol<?> symbol;
            if (callExpression instanceof FirQualifiedAccessExpression) {
                dispatchReceiver = ((FirQualifiedAccessExpression) callExpression).getDispatchReceiver();
            } else {
                dispatchReceiver = callExpression instanceof FirVariableAssignment ? FirExpressionUtilKt.getDispatchReceiver((FirVariableAssignment) callExpression) : null;
            }
            FirQualifiedAccessExpression firQualifiedAccessExpression = dispatchReceiver instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) dispatchReceiver : null;
            if (firQualifiedAccessExpression == null || !(firQualifiedAccessExpression.getCalleeReference() instanceof FirSuperReference) || (symbol = TypeUtilsKt.toSymbol(FirTypeUtilsKt.getConeType(firQualifiedAccessExpression.getDispatchReceiver().getTypeRef()), this.session)) == null || isDefinedInInlineFunction(symbol)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firQualifiedAccessExpression.getSource(), FirErrors.INSTANCE.getSUPER_CALL_FROM_PUBLIC_INLINE(), calledDeclaration, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }

        private final boolean isDefinedInInlineFunction(FirClassifierSymbol<?> firClassifierSymbol) {
            if (firClassifierSymbol instanceof FirAnonymousObjectSymbol) {
                return true;
            }
            if (firClassifierSymbol instanceof FirRegularClassSymbol) {
                return ((FirRegularClassSymbol) firClassifierSymbol).getClassId().isLocal();
            }
            if (firClassifierSymbol instanceof FirTypeAliasSymbol ? true : firClassifierSymbol instanceof FirTypeParameterSymbol) {
                throw new IllegalStateException(("Unexpected classifier declaration type: " + firClassifierSymbol).toString());
            }
            throw new NoWhenBranchMatchedException();
        }

        private final void checkRecursion(FirBasedSymbol<?> targetSymbol, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
            if (Intrinsics.areEqual(targetSymbol, this.inlineFunction.getSymbol())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getRECURSION_IN_INLINE(), targetSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }

        private final boolean isInsidePrivateClass(FirBasedSymbol<?> firBasedSymbol) {
            FirClassLikeSymbol<?> symbol;
            ConeClassLikeLookupTag ownerLookupTag = FirVisibilityCheckerKt.getOwnerLookupTag(firBasedSymbol);
            if (ownerLookupTag == null || (symbol = LookupTagUtilsKt.toSymbol(ownerLookupTag, this.session)) == null || (symbol instanceof FirAnonymousObjectSymbol)) {
                return false;
            }
            boolean z = symbol instanceof FirRegularClassSymbol;
            if (!z && !(symbol instanceof FirTypeAliasSymbol)) {
                throw new NoWhenBranchMatchedException();
            }
            Visibility visibility = symbol.getResolvedStatus().getVisibility();
            if (Intrinsics.areEqual(visibility, Visibilities.Private.INSTANCE) || Intrinsics.areEqual(visibility, Visibilities.PrivateToThis.INSTANCE)) {
                return true;
            }
            if ((firBasedSymbol instanceof FirCallableSymbol) && z && symbol.getRawStatus().isCompanion()) {
                return isInsidePrivateClass(symbol);
            }
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0116  */
    private final void checkParameters(FirSimpleFunction function, List<? extends FirCallableSymbol<? extends FirCallableDeclaration>> overriddenSymbols, CheckerContext context, DiagnosticReporter reporter) {
        FirInlineCheckerPlatformSpecificComponent inlineCheckerExtension;
        for (Iterator<FirValueParameter> it = function.getValueParameters().iterator(); it.hasNext(); it = it) {
            FirValueParameter next = it.next();
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(next.getReturnTypeRef());
            FunctionTypeKind functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(coneType, context.getSession());
            boolean z = functionTypeKind != null;
            boolean z2 = functionTypeKind != null && FunctionTypeKindKt.isSuspendOrKSuspendFunction(functionTypeKind);
            FirExpression defaultValue = next.getDefaultValue();
            if (!z && (next.getIsNoinline() || next.getIsCrossinline())) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, next.getSource(), FirErrors.INSTANCE.getILLEGAL_INLINE_PARAMETER_MODIFIER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            if (!next.getIsNoinline()) {
                FirSimpleFunction firSimpleFunction = function;
                if (firSimpleFunction.getStatus().isSuspend() && defaultValue != null && z2 && (inlineCheckerExtension = FirInlineCheckerPlatformSpecificComponentKt.getInlineCheckerExtension(context.getSession())) != null) {
                    inlineCheckerExtension.checkSuspendFunctionalParameterWithDefaultValue(next, context, reporter);
                }
                if (z2 && !next.getIsCrossinline() && !firSimpleFunction.getStatus().isSuspend()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, next.getSource(), FirErrors.INSTANCE.getINLINE_SUSPEND_FUNCTION_TYPE_UNSUPPORTED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
                if (ConeTypeUtilsKt.isNullable(coneType) && z) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, next.getSource(), FirErrors.INSTANCE.getNULLABLE_INLINE_PARAMETER(), next.getSymbol(), function.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
                if (z && defaultValue != 0) {
                    if (!isInlinableDefaultValue(defaultValue)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, defaultValue.getSource(), FirErrors.INSTANCE.getINVALID_DEFAULT_FUNCTIONAL_PARAMETER_FOR_INLINE(), next.getSymbol(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                }
            }
        }
        if (!overriddenSymbols.isEmpty()) {
            for (FirTypeParameter firTypeParameter : function.getTypeParameters()) {
                if (firTypeParameter.getIsReified()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeParameter.getSource(), FirErrors.INSTANCE.getREIFIED_TYPE_PARAMETER_IN_OVERRIDE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
        FirInlineCheckerPlatformSpecificComponent inlineCheckerExtension2 = FirInlineCheckerPlatformSpecificComponentKt.getInlineCheckerExtension(context.getSession());
        if (inlineCheckerExtension2 != null) {
            inlineCheckerExtension2.checkFunctionalParametersWithInheritedDefaultValues(function, context, reporter, overriddenSymbols);
        }
    }

    private final void checkParametersInNotInline(FirFunction function, CheckerContext context, DiagnosticReporter reporter) {
        for (FirValueParameter firValueParameter : function.getValueParameters()) {
            if (firValueParameter.getIsNoinline() || firValueParameter.getIsCrossinline()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getSource(), FirErrors.INSTANCE.getILLEGAL_INLINE_PARAMETER_MODIFIER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final List<FirCallableSymbol<? extends FirCallableDeclaration>> getOverriddenSymbols(FirCallableDeclaration firCallableDeclaration, CheckerContext checkerContext) {
        if (!firCallableDeclaration.getStatus().isOverride()) {
            return CollectionsKt.emptyList();
        }
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableDeclaration);
        FirClassLikeSymbol<?> symbol = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toSymbol(coneClassLikeLookupTagContainingClassLookupTag, checkerContext.getSession()) : null;
        FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        if (firClassSymbol == null) {
            return CollectionsKt.emptyList();
        }
        FirTypeScope firTypeScopeUnsubstitutedScope = FirHelpersKt.unsubstitutedScope((FirClassSymbol<?>) firClassSymbol, checkerContext);
        firTypeScopeUnsubstitutedScope.processFunctionsByName(firCallableDeclaration.getSymbol().getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirInlineDeclarationChecker.getOverriddenSymbols.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }
        });
        return FirTypeScopeKt.getDirectOverriddenMembers(firTypeScopeUnsubstitutedScope, firCallableDeclaration.getSymbol(), true);
    }

    private final void checkNothingToInline(FirSimpleFunction function, CheckerContext context, DiagnosticReporter reporter) {
        FirSimpleFunction firSimpleFunction = function;
        if (firSimpleFunction.getStatus().isExpect() || firSimpleFunction.getStatus().isSuspend()) {
            return;
        }
        List<FirTypeParameter> typeParameters = function.getTypeParameters();
        if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                if (((FirTypeParameter) it.next()).getSymbol().isReified()) {
                    return;
                }
            }
        }
        FirSession session = context.getSession();
        List<FirValueParameter> valueParameters = function.getValueParameters();
        if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
            for (FirValueParameter firValueParameter : valueParameters) {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
                if (!firValueParameter.getIsNoinline() && !ConeTypeUtilsKt.isNullable(coneType) && (FunctionalTypeUtilsKt.isBasicFunctionType(coneType, session) || FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(coneType, session))) {
                    return;
                }
            }
        }
        if (FirHelpersKt.isInlineOnly(firSimpleFunction, session) || DeclarationUtilsKt.needsMultiFieldValueClassFlattening(function.getReturnTypeRef(), session)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, function.getSource(), FirErrors.INSTANCE.getNOTHING_TO_INLINE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }

    private final boolean checkCanBeInlined(FirCallableDeclaration declaration, EffectiveVisibility effectiveVisibility, CheckerContext context, DiagnosticReporter reporter) {
        if (ClassMembersKt.containingClassLookupTag(declaration) == null || Intrinsics.areEqual(effectiveVisibility, EffectiveVisibility.PrivateInClass.INSTANCE) || DeclarationUtilsKt.isEffectivelyFinal(declaration, context)) {
            return true;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getDECLARATION_CANT_BE_INLINED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        return false;
    }

    private final boolean isInlinableDefaultValue(FirExpression expression) {
        return (expression instanceof FirCallableReferenceAccess) || (expression instanceof FirFunctionCall) || (expression instanceof FirLambdaArgumentExpression) || (expression instanceof FirAnonymousFunctionExpression) || ((expression instanceof FirConstExpression) && ((FirConstExpression) expression).getValue() == null);
    }

    public final void checkCallableDeclaration(FirCallableDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        EffectiveVisibility.Local effectiveVisibility;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirPropertyAccessor) {
            return;
        }
        List<FirCallableSymbol<? extends FirCallableDeclaration>> overriddenSymbols = getOverriddenSymbols(declaration, context);
        if (declaration instanceof FirSimpleFunction) {
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) declaration;
            checkParameters(firSimpleFunction, overriddenSymbols, context, reporter);
            checkNothingToInline(firSimpleFunction, context, reporter);
        }
        FirDeclarationStatus status = declaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
        }
        if (!checkCanBeInlined(declaration, effectiveVisibility, context, reporter) || overriddenSymbols.isEmpty()) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getOVERRIDE_BY_INLINE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
