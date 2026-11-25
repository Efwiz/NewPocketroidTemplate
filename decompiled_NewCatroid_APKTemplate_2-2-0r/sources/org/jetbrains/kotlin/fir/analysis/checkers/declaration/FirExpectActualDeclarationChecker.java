package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContextKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.ExpectActualAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualAnnotationMatchChecker;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualAnnotationsIncompatibilityType;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirExpectActualDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J*\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0002JX\u0010\u0010\u001a\u00020\u00062\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u00122&\u0010\u0013\u001a\"\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u0015\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u00160\u00142\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J0\u0010\u0018\u001a\u00020\u00062\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u00122\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J0\u0010#\u001a\u00020\u00062\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u00122\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J,\u0010$\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010&\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\rH\u0002J\u001e\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u00162\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0002J\u001c\u0010+\u001a\u00020\u000f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010,\u001a\u00020-H\u0002J\u001a\u0010.\u001a\u00020\u000f*\u0012\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u0015\u0012\u0002\b\u00030\u0014J\u0016\u0010/\u001a\u00020\u000f*\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u0015H\u0002¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirExpectActualDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkActualDeclarationHasExpected", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "checkActual", "", "checkAmbiguousExpects", "actualDeclaration", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "compatibility", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "", NativeSymbol.TYPE_NAME, "checkAnnotationsMatch", "expectSymbol", "actualSymbol", "checkExpectDeclarationHasNoExternalModifier", "checkExpectDeclarationModifiers", "checkExpectPropertyAccessorModifiers", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "checkExpectPropertyAccessorsModifiers", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "checkIfExpectHasDefaultArgumentsAndActualizedWithTypealias", "checkOptInAnnotation", "expectDeclarationSymbol", "containsExpectOrActualModifier", "getMembersWithDefaultValueParametersUnlessAnnotation", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "requireActualModifier", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "allStrongIncompatibilities", "isCompatibleOrWeakCompatible", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpectActualDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirExpectActualDeclarationChecker INSTANCE = new FirExpectActualDeclarationChecker();

    private FirExpectActualDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirMemberDeclaration) {
            if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.MultiPlatformProjects)) {
                FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) declaration;
                if ((firMemberDeclaration.getStatus().isExpect() || firMemberDeclaration.getStatus().isActual()) && containsExpectOrActualModifier(firMemberDeclaration)) {
                    KtDiagnosticReportHelpersKt.reportOn(reporter, declaration.getSource(), FirErrors.INSTANCE.getNOT_A_MULTIPLATFORM_COMPILATION(), context, SourceElementPositioningStrategies.INSTANCE.getEXPECT_ACTUAL_MODIFIER());
                    return;
                }
                return;
            }
            FirMemberDeclaration firMemberDeclaration2 = (FirMemberDeclaration) declaration;
            if (firMemberDeclaration2.getStatus().isExpect()) {
                checkExpectDeclarationModifiers(firMemberDeclaration2, context, reporter);
                checkOptInAnnotation(firMemberDeclaration2, declaration.getSymbol(), context, reporter);
            }
            if (firMemberDeclaration2.getStatus().isActual()) {
                checkActualDeclarationHasExpected$default(this, firMemberDeclaration2, context, reporter, false, 8, null);
            }
        }
    }

    private final boolean containsExpectOrActualModifier(FirMemberDeclaration declaration) {
        FirModifierList modifierList = FirKeywordUtilsKt.getModifierList(declaration.getSource());
        if (modifierList == null) {
            return false;
        }
        KtModifierKeywordToken EXPECT_KEYWORD = KtTokens.EXPECT_KEYWORD;
        Intrinsics.checkNotNullExpressionValue(EXPECT_KEYWORD, "EXPECT_KEYWORD");
        if (!modifierList.contains(EXPECT_KEYWORD)) {
            KtModifierKeywordToken ACTUAL_KEYWORD = KtTokens.ACTUAL_KEYWORD;
            Intrinsics.checkNotNullExpressionValue(ACTUAL_KEYWORD, "ACTUAL_KEYWORD");
            if (!modifierList.contains(ACTUAL_KEYWORD)) {
                return false;
            }
        }
        return true;
    }

    private final void checkExpectDeclarationModifiers(FirMemberDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        checkExpectDeclarationHasNoExternalModifier(declaration, context, reporter);
        if (declaration instanceof FirProperty) {
            checkExpectPropertyAccessorsModifiers((FirProperty) declaration, context, reporter);
        }
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && (declaration instanceof FirFunction) && declaration.getStatus().isTailRec()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getEXPECTED_TAILREC_FUNCTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkExpectPropertyAccessorsModifiers(FirProperty property, CheckerContext context, DiagnosticReporter reporter) {
        Iterator it = CollectionsKt.listOfNotNull((Object[]) new FirPropertyAccessor[]{property.getGetter(), property.getSetter()}).iterator();
        while (it.hasNext()) {
            checkExpectPropertyAccessorModifiers((FirPropertyAccessor) it.next(), context, reporter);
        }
    }

    private static final boolean checkExpectPropertyAccessorModifiers$isDefault(FirPropertyAccessor firPropertyAccessor) {
        KtSourceElement source = firPropertyAccessor.getSource();
        return Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DefaultAccessor.INSTANCE);
    }

    private final void checkExpectPropertyAccessorModifiers(FirPropertyAccessor accessor, CheckerContext context, DiagnosticReporter reporter) {
        if (checkExpectPropertyAccessorModifiers$isDefault(accessor)) {
            return;
        }
        checkExpectDeclarationHasNoExternalModifier(accessor, context, reporter);
    }

    private final void checkExpectDeclarationHasNoExternalModifier(FirMemberDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && declaration.getStatus().isExternal()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getEXPECTED_EXTERNAL_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    static /* synthetic */ void checkActualDeclarationHasExpected$default(FirExpectActualDeclarationChecker firExpectActualDeclarationChecker, FirMemberDeclaration firMemberDeclaration, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        firExpectActualDeclarationChecker.checkActualDeclarationHasExpected(firMemberDeclaration, checkerContext, diagnosticReporter, z);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x0114  */
    /* JADX WARN: Code duplicated, block: B:50:0x0122  */
    private final void checkActualDeclarationHasExpected(FirMemberDeclaration declaration, CheckerContext context, DiagnosticReporter reporter, boolean checkActual) {
        FirBasedSymbol<?> singleExpectForActualOrNull;
        FirBasedSymbol<? extends FirDeclaration> symbol = declaration.getSymbol();
        Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> expectForActual = ExpectActualAttributesKt.getExpectForActual(symbol);
        if (expectForActual == null) {
            return;
        }
        checkAmbiguousExpects(symbol, expectForActual, symbol, context, reporter);
        KtSourceElement source = declaration.getSource();
        if (!declaration.getStatus().isActual()) {
            if (allStrongIncompatibilities(expectForActual)) {
                return;
            }
            if (expectForActual.containsKey(ExpectActualCompatibility.Compatible.INSTANCE)) {
                if (checkActual) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getACTUAL_MISSING(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            }
        }
        ExpectActualCompatibility expectActualCompatibility = (ExpectActualCompatibility) CollectionsKt.singleOrNull(expectForActual.keySet());
        if (expectActualCompatibility instanceof ExpectActualCompatibility.Incompatible.ClassScopes) {
            if (!(symbol instanceof FirRegularClassSymbol) && !(symbol instanceof FirTypeAliasSymbol)) {
                throw new IllegalArgumentException(("Incompatible.ClassScopes is only possible for a class or a typealias: " + declaration).toString());
            }
            List unfulfilled = ((ExpectActualCompatibility.Incompatible.ClassScopes) expectActualCompatibility).getUnfulfilled();
            ArrayList arrayList = new ArrayList();
            for (Object obj : unfulfilled) {
                if (!checkActualDeclarationHasExpected$hasSingleActualSuspect((Pair) obj)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS(), symbol, arrayList2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            }
        } else if (!expectForActual.containsKey(ExpectActualCompatibility.Compatible.INSTANCE)) {
            if (!(declaration instanceof FirFunction)) {
                if (requireActualModifier(declaration.getSymbol(), context.getSession())) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getACTUAL_WITHOUT_EXPECT(), symbol, expectForActual, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                }
            } else {
                Set<ExpectActualCompatibility<FirBasedSymbol<?>>> setKeySet = expectForActual.keySet();
                if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
                    if (requireActualModifier(declaration.getSymbol(), context.getSession())) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getACTUAL_WITHOUT_EXPECT(), symbol, expectForActual, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    }
                } else {
                    Iterator<T> it = setKeySet.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (((ExpectActualCompatibility) it.next()) instanceof ExpectActualCompatibility.Incompatible.ActualFunctionWithDefaultParameters) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            }
                        } else if (requireActualModifier(declaration.getSymbol(), context.getSession())) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getACTUAL_WITHOUT_EXPECT(), symbol, expectForActual, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                        }
                    }
                }
            }
        }
        List<FirBasedSymbol<?>> list = expectForActual.get(ExpectActualCompatibility.Compatible.INSTANCE);
        if (list == null || (singleExpectForActualOrNull = (FirBasedSymbol) CollectionsKt.singleOrNull((List) list)) == null) {
            singleExpectForActualOrNull = ExpectActualAttributesKt.getSingleExpectForActualOrNull(symbol);
        }
        if (singleExpectForActualOrNull != null) {
            checkIfExpectHasDefaultArgumentsAndActualizedWithTypealias(singleExpectForActualOrNull, symbol, context, reporter);
            checkOptInAnnotation(declaration, singleExpectForActualOrNull, context, reporter);
            checkAnnotationsMatch(singleExpectForActualOrNull, symbol, context, reporter);
        }
    }

    private static final boolean checkActualDeclarationHasExpected$hasSingleActualSuspect(Pair<? extends FirBasedSymbol<?>, ? extends Map<ExpectActualCompatibility.Incompatible<FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>> pair) {
        Collection<List<FirBasedSymbol<?>>> collectionValues;
        List list;
        FirBasedSymbol<?> firBasedSymbolComponent1 = pair.component1();
        Map<ExpectActualCompatibility.Incompatible<FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>> mapComponent2 = pair.component2();
        Collection collection = (Collection) CollectionsKt.singleOrNull(mapComponent2.values());
        FirBasedSymbol firBasedSymbol = null;
        FirBasedSymbol firBasedSymbol2 = collection != null ? (FirBasedSymbol) CollectionsKt.singleOrNull(collection) : null;
        if (firBasedSymbol2 != null && !INSTANCE.allStrongIncompatibilities(mapComponent2)) {
            Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> expectForActual = ExpectActualAttributesKt.getExpectForActual(firBasedSymbol2.getFir());
            if (expectForActual != null && (collectionValues = expectForActual.values()) != null && (list = (List) CollectionsKt.singleOrNull(collectionValues)) != null) {
                firBasedSymbol = (FirBasedSymbol) CollectionsKt.singleOrNull(list);
            }
            if (Intrinsics.areEqual(firBasedSymbol, firBasedSymbolComponent1)) {
                return true;
            }
        }
        return false;
    }

    private final void checkAmbiguousExpects(FirBasedSymbol<?> actualDeclaration, Map<ExpectActualCompatibility<FirBasedSymbol<?>>, ? extends List<? extends FirBasedSymbol<?>>> compatibility, FirBasedSymbol<?> symbol, CheckerContext context, DiagnosticReporter reporter) {
        List list = SequencesKt.toList(SequencesKt.sortedWith(SequencesKt.map(SequencesKt.flattenSequenceOfIterable(SequencesKt.map(SequencesKt.filter(MapsKt.asSequence(compatibility), new Function1<Map.Entry<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends List<? extends FirBasedSymbol<?>>>, Boolean>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualDeclarationChecker$checkAmbiguousExpects$filesWithAtLeastWeaklyCompatibleExpects$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Map.Entry<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends List<? extends FirBasedSymbol<?>>> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return Boolean.valueOf(FirExpectActualDeclarationChecker.INSTANCE.isCompatibleOrWeakCompatible(entry.getKey()));
            }
        }), new Function1<Map.Entry<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends List<? extends FirBasedSymbol<?>>>, List<? extends FirBasedSymbol<?>>>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualDeclarationChecker$checkAmbiguousExpects$filesWithAtLeastWeaklyCompatibleExpects$2
            @Override // kotlin.jvm.functions.Function1
            public final List<FirBasedSymbol<?>> invoke(Map.Entry<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends List<? extends FirBasedSymbol<?>>> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return (List) entry.getValue();
            }
        })), new Function1<FirBasedSymbol<?>, FirModuleData>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualDeclarationChecker$checkAmbiguousExpects$filesWithAtLeastWeaklyCompatibleExpects$3
            @Override // kotlin.jvm.functions.Function1
            public final FirModuleData invoke(FirBasedSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getModuleData();
            }
        }), new Comparator() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualDeclarationChecker$checkAmbiguousExpects$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((FirModuleData) t).getName().asString(), ((FirModuleData) t2).getName().asString());
            }
        }));
        if (list.size() > 1) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, actualDeclaration.getSource(), FirErrors.INSTANCE.getAMBIGUOUS_EXPECTS(), symbol, list, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    private final void checkIfExpectHasDefaultArgumentsAndActualizedWithTypealias(FirBasedSymbol<?> expectSymbol, FirBasedSymbol<?> actualSymbol, CheckerContext context, DiagnosticReporter reporter) {
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && (expectSymbol instanceof FirClassSymbol) && (actualSymbol instanceof FirTypeAliasSymbol)) {
            List<FirFunctionSymbol<?>> membersWithDefaultValueParametersUnlessAnnotation = getMembersWithDefaultValueParametersUnlessAnnotation((FirClassSymbol) expectSymbol);
            if (membersWithDefaultValueParametersUnlessAnnotation.isEmpty()) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, actualSymbol.getSource(), FirErrors.INSTANCE.getDEFAULT_ARGUMENTS_IN_EXPECT_WITH_ACTUAL_TYPEALIAS(), expectSymbol, membersWithDefaultValueParametersUnlessAnnotation, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    private final List<FirFunctionSymbol<?>> getMembersWithDefaultValueParametersUnlessAnnotation(FirClassSymbol<?> classSymbol) {
        ArrayList arrayList = new ArrayList();
        getMembersWithDefaultValueParametersUnlessAnnotation$collectFunctions(arrayList, classSymbol);
        return arrayList;
    }

    private static final void getMembersWithDefaultValueParametersUnlessAnnotation$collectFunctions(List<FirFunctionSymbol<?>> list, FirClassSymbol<?> firClassSymbol) {
        if (firClassSymbol.getClassKind() == ClassKind.ANNOTATION_CLASS) {
            return;
        }
        FirContainingNamesAwareScope firContainingNamesAwareScopeDeclaredMemberScope = FirDeclaredMemberScopeProviderKt.declaredMemberScope(firClassSymbol, firClassSymbol.getModuleData().getSession(), (FirResolvePhase) null);
        FirContainingNamesAwareScope firContainingNamesAwareScope = firContainingNamesAwareScopeDeclaredMemberScope;
        for (Object obj : CollectionsKt.plus((Collection) FirContainingNamesAwareScopeKt.collectAllFunctions(firContainingNamesAwareScopeDeclaredMemberScope), (Iterable) FirScopeKt.getDeclaredConstructors(firContainingNamesAwareScope))) {
            List<FirValueParameterSymbol> valueParameterSymbols = ((FirFunctionSymbol) obj).getValueParameterSymbols();
            if (!(valueParameterSymbols instanceof Collection) || !valueParameterSymbols.isEmpty()) {
                Iterator<T> it = valueParameterSymbols.iterator();
                while (it.hasNext()) {
                    if (((FirValueParameterSymbol) it.next()).getHasDefaultValue()) {
                        list.add(obj);
                        break;
                    }
                }
            }
        }
        Set<Name> classifierNames = firContainingNamesAwareScopeDeclaredMemberScope.getClassifierNames();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it2 = classifierNames.iterator();
        while (it2.hasNext()) {
            FirClassifierSymbol<?> singleClassifier = FirScopeKt.getSingleClassifier(firContainingNamesAwareScope, (Name) it2.next());
            FirClassSymbol firClassSymbol2 = singleClassifier instanceof FirClassSymbol ? (FirClassSymbol) singleClassifier : null;
            if (firClassSymbol2 != null) {
                arrayList.add(firClassSymbol2);
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            getMembersWithDefaultValueParametersUnlessAnnotation$collectFunctions(list, (FirClassSymbol) it3.next());
        }
    }

    private final void checkAnnotationsMatch(FirBasedSymbol<?> expectSymbol, FirBasedSymbol<?> actualSymbol, CheckerContext context, DiagnosticReporter reporter) {
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions)) {
            AbstractExpectActualAnnotationMatchChecker.Incompatibility incompatibilityAreAnnotationsCompatible = AbstractExpectActualAnnotationMatchChecker.INSTANCE.areAnnotationsCompatible(expectSymbol, actualSymbol, FirExpectActualMatchingContextKt.getExpectActualMatchingContextFactory(context.getSession()).create(context.getSession(), context.getScopeSession()));
            if (incompatibilityAreAnnotationsCompatible == null) {
                return;
            }
            KtSourceElement source = actualSymbol.getSource();
            KtDiagnosticFactory3<FirBasedSymbol<?>, FirBasedSymbol<?>, ExpectActualAnnotationsIncompatibilityType<FirAnnotation>> actual_annotations_not_match_expect = FirErrors.INSTANCE.getACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT();
            DeclarationSymbolMarker expectSymbol2 = incompatibilityAreAnnotationsCompatible.getExpectSymbol();
            Intrinsics.checkNotNull(expectSymbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.FirBasedSymbol<*>");
            FirBasedSymbol firBasedSymbol = (FirBasedSymbol) expectSymbol2;
            DeclarationSymbolMarker actualSymbol2 = incompatibilityAreAnnotationsCompatible.getActualSymbol();
            Intrinsics.checkNotNull(actualSymbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.FirBasedSymbol<*>");
            KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) source, (KtDiagnosticFactory3<FirBasedSymbol, FirBasedSymbol, Object>) ((KtDiagnosticFactory3<Object, Object, Object>) actual_annotations_not_match_expect), firBasedSymbol, (FirBasedSymbol) actualSymbol2, incompatibilityAreAnnotationsCompatible.getType().mapAnnotationType(new Function1<ExpectActualMatchingContext.AnnotationCallInfo, FirAnnotation>() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirExpectActualDeclarationChecker.checkAnnotationsMatch.1
                @Override // kotlin.jvm.functions.Function1
                public final FirAnnotation invoke(ExpectActualMatchingContext.AnnotationCallInfo it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Object annotationSymbol = it.getAnnotationSymbol();
                    Intrinsics.checkNotNull(annotationSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirAnnotation");
                    return (FirAnnotation) annotationSymbol;
                }
            }), (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
        }
    }

    public final boolean allStrongIncompatibilities(Map<? extends ExpectActualCompatibility<?>, ?> map) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Set<? extends ExpectActualCompatibility<?>> setKeySet = map.keySet();
        if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
            return true;
        }
        Iterator<T> it = setKeySet.iterator();
        while (it.hasNext()) {
            if (!(((ExpectActualCompatibility) it.next()) instanceof ExpectActualCompatibility.Incompatible.StrongIncompatible)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCompatibleOrWeakCompatible(ExpectActualCompatibility<? extends FirBasedSymbol<?>> expectActualCompatibility) {
        return (expectActualCompatibility instanceof ExpectActualCompatibility.Compatible) || (expectActualCompatibility instanceof ExpectActualCompatibility.Incompatible.WeakIncompatible);
    }

    private final boolean requireActualModifier(FirBasedSymbol<?> declaration, FirSession session) {
        return (org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isAnnotationConstructor(declaration, session) || org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.isPrimaryConstructorOfInlineOrValueClass(declaration, session)) ? false : true;
    }

    private final void checkOptInAnnotation(FirMemberDeclaration declaration, FirBasedSymbol<?> expectDeclarationSymbol, CheckerContext context, DiagnosticReporter reporter) {
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && (declaration instanceof FirClass) && ((FirClass) declaration).getClassKind() == ClassKind.ANNOTATION_CLASS && !FirAnnotationUtilsKt.hasAnnotation(expectDeclarationSymbol, StandardClassIds.Annotations.INSTANCE.getOptionalExpectation(), context.getSession()) && FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) declaration, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), context.getSession())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getEXPECT_ACTUAL_OPT_IN_ANNOTATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
