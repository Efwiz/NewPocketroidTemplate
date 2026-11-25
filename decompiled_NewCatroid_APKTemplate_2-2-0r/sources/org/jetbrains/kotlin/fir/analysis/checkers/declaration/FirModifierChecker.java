package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.resolve.Compatibility;
import org.jetbrains.kotlin.resolve.ModifierCheckerHelpersKt;
import org.jetbrains.kotlin.resolve.TargetAllowedPredicate;

/* JADX INFO: compiled from: FirModifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016JL\u0010\f\u001a\u00020\u00062\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\tH\u0002J(\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J@\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J@\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\"\u001a\u00020\u00172\b\u0010#\u001a\u0004\u0018\u00010\u0002H\u0002J\u0012\u0010$\u001a\u00020%*\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0002¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirModifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkCompatibilityType", "firstModifier", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier;", "secondModifier", "reportedNodes", "", "owner", "checkModifiers", SchemaSymbols.ATTVAL_LIST, "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList;", "checkParent", "", "modifierSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "modifierToken", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "actualParents", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "parent", "checkTarget", "actualTargets", "isFinalExpectClass", "d", "firstOrThis", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirModifierChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirModifierChecker INSTANCE = new FirModifierChecker();

    /* JADX INFO: compiled from: FirModifierChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Compatibility.values().length];
            try {
                iArr[Compatibility.COMPATIBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Compatibility.REPEATED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Compatibility.REDUNDANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Compatibility.REVERSE_REDUNDANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Compatibility.DEPRECATED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Compatibility.INCOMPATIBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Compatibility.COMPATIBLE_FOR_CLASSES_ONLY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirModifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        FirModifierList modifierList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((declaration instanceof FirFile) || (source = declaration.getSource()) == null || (source.getKind() instanceof KtFakeSourceElementKind) || (modifierList = FirKeywordUtilsKt.getModifierList(source)) == null) {
            return;
        }
        INSTANCE.checkModifiers(modifierList, declaration, context, reporter);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005a  */
    private final void checkModifiers(FirModifierList list, FirDeclaration owner, CheckerContext context, DiagnosticReporter reporter) {
        FirDeclaration firDeclaration;
        List<KotlinTarget> enum_entry_list;
        List<FirModifier<?>> list2;
        FirDeclaration firDeclaration2;
        if (list.getModifiers().isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        List<KotlinTarget> defaultTargets = FirHelpersKt.getActualTargetList(owner).getDefaultTargets();
        Iterator it = CollectionsKt.asReversed(context.getContainingDeclarations()).iterator();
        while (true) {
            if (!it.hasNext()) {
                firDeclaration = null;
                break;
            }
            FirDeclaration firDeclaration3 = (FirDeclaration) it.next();
            if (!(firDeclaration3 instanceof FirDeclaration)) {
                firDeclaration3 = null;
            }
            firDeclaration = firDeclaration3;
            if (firDeclaration != null) {
                FirDeclaration firDeclaration4 = firDeclaration;
                if ((firDeclaration4 instanceof FirPrimaryConstructor) || (firDeclaration4 instanceof FirProperty)) {
                    firDeclaration = null;
                } else {
                    KtSourceElement source = firDeclaration4.getSource();
                    if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
                        firDeclaration = null;
                    }
                }
                if (firDeclaration != null) {
                    break;
                }
            }
        }
        FirDeclaration firDeclaration5 = firDeclaration;
        if (firDeclaration5 instanceof FirAnonymousObject) {
            enum_entry_list = KotlinTarget.INSTANCE.getLOCAL_CLASS_LIST();
        } else if (firDeclaration5 instanceof FirClass) {
            KotlinTarget.Companion companion = KotlinTarget.INSTANCE;
            ClassKind classKind = ((FirClass) firDeclaration5).getClassKind();
            FirMemberDeclaration firMemberDeclaration = firDeclaration5 instanceof FirMemberDeclaration ? (FirMemberDeclaration) firDeclaration5 : null;
            boolean zIsInner = firMemberDeclaration != null ? firMemberDeclaration.getStatus().isInner() : false;
            FirRegularClass firRegularClass = firDeclaration5 instanceof FirRegularClass ? (FirRegularClass) firDeclaration5 : null;
            enum_entry_list = companion.classActualTargets(classKind, zIsInner, firRegularClass != null ? firRegularClass.getStatus().isCompanion() : false, ((FirClassLikeDeclaration) firDeclaration5).getSymbol().getClassId().isLocal());
        } else if (firDeclaration5 instanceof FirPropertyAccessor) {
            enum_entry_list = ((FirPropertyAccessor) firDeclaration5).isSetter() ? KotlinTarget.INSTANCE.getPROPERTY_SETTER_LIST() : KotlinTarget.INSTANCE.getPROPERTY_GETTER_LIST();
        } else if (firDeclaration5 instanceof FirFunction) {
            enum_entry_list = KotlinTarget.INSTANCE.getFUNCTION_LIST();
        } else {
            enum_entry_list = firDeclaration5 instanceof FirEnumEntry ? KotlinTarget.INSTANCE.getENUM_ENTRY_LIST() : KotlinTarget.INSTANCE.getFILE_LIST();
        }
        List<KotlinTarget> list3 = enum_entry_list;
        List<FirModifier<?>> modifiers = list.getModifiers();
        int i = 0;
        for (FirModifier<?> firModifier : modifiers) {
            int i2 = i + 1;
            for (int i3 = 0; i3 < i; i3++) {
                checkCompatibilityType(modifiers.get(i3), firModifier, reporter, hashSet, owner, context);
            }
            if (hashSet.contains(firModifier)) {
                list2 = modifiers;
                firDeclaration2 = firDeclaration5;
            } else {
                KtSourceElement source2 = firModifier.getSource();
                KtModifierKeywordToken token = firModifier.getToken();
                if (checkTarget(source2, token, defaultTargets, firDeclaration5, context, reporter)) {
                    list2 = modifiers;
                    firDeclaration2 = firDeclaration5;
                    if (!checkParent(source2, token, list3, firDeclaration5, context, reporter)) {
                        hashSet.add(firModifier);
                    }
                } else {
                    hashSet.add(firModifier);
                    list2 = modifiers;
                    firDeclaration2 = firDeclaration5;
                }
            }
            modifiers = list2;
            firDeclaration5 = firDeclaration2;
            i = i2;
        }
    }

    private final void checkCompatibilityType(FirModifier<?> firstModifier, FirModifier<?> secondModifier, DiagnosticReporter reporter, Set<FirModifier<?>> reportedNodes, FirDeclaration owner, CheckerContext context) {
        KtModifierKeywordToken token = firstModifier.getToken();
        KtModifierKeywordToken token2 = secondModifier.getToken();
        Compatibility compatibility = ModifierCheckerHelpersKt.compatibility(token, token2);
        switch (WhenMappings.$EnumSwitchMapping$0[compatibility.ordinal()]) {
            case 2:
                if (reportedNodes.add(secondModifier)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, secondModifier.getSource(), FirErrors.INSTANCE.getREPEATED_MODIFIER(), token2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                break;
            case 3:
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, secondModifier.getSource(), FirErrors.INSTANCE.getREDUNDANT_MODIFIER(), token2, token, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                break;
            case 4:
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firstModifier.getSource(), FirErrors.INSTANCE.getREDUNDANT_MODIFIER(), token, token2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                break;
            case 5:
                CheckerContext checkerContext = context;
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firstModifier.getSource(), FirErrors.INSTANCE.getDEPRECATED_MODIFIER_PAIR(), token, token2, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, secondModifier.getSource(), FirErrors.INSTANCE.getDEPRECATED_MODIFIER_PAIR(), token2, token, checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                break;
            case 6:
            case 7:
                if (compatibility != Compatibility.COMPATIBLE_FOR_CLASSES_ONLY || !(owner instanceof FirClass)) {
                    if (reportedNodes.add(firstModifier)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firstModifier.getSource(), FirErrors.INSTANCE.getINCOMPATIBLE_MODIFIERS(), token, token2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    }
                    if (reportedNodes.add(secondModifier)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, secondModifier.getSource(), FirErrors.INSTANCE.getINCOMPATIBLE_MODIFIERS(), token2, token, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    }
                }
                break;
        }
    }

    private static final boolean checkTarget$checkModifier(KtModifierKeywordToken ktModifierKeywordToken, List<? extends KotlinTarget> list, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter, KtSourceElement ktSourceElement, KtDiagnosticFactory2<KtModifierKeywordToken, String> ktDiagnosticFactory2) {
        Map deprecatedTargetMap;
        if (Intrinsics.areEqual(ktDiagnosticFactory2, FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET())) {
            deprecatedTargetMap = ModifierCheckerHelpersKt.getPossibleTargetMap();
        } else {
            deprecatedTargetMap = Intrinsics.areEqual(ktDiagnosticFactory2, FirErrors.INSTANCE.getDEPRECATED_MODIFIER_FOR_TARGET()) ? ModifierCheckerHelpersKt.getDeprecatedTargetMap() : ModifierCheckerHelpersKt.getRedundantTargetMap();
        }
        Set setEmptySet = (Set) deprecatedTargetMap.get(ktModifierKeywordToken);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        if (Intrinsics.areEqual(ktDiagnosticFactory2, FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET())) {
            List<? extends KotlinTarget> list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    if (setEmptySet.contains((KotlinTarget) it.next())) {
                        if (Intrinsics.areEqual(ktModifierKeywordToken, KtTokens.DATA_KEYWORD) && list.contains(KotlinTarget.STANDALONE_OBJECT) && !checkerContext.get$languageVersionSettings().supportsFeature(LanguageFeature.DataObjects)) {
                            break;
                        }
                        return true;
                    }
                }
            }
        } else {
            List<? extends KotlinTarget> list3 = list;
            if ((list3 instanceof Collection) && list3.isEmpty()) {
                return true;
            }
            Iterator<T> it2 = list3.iterator();
            while (it2.hasNext()) {
                if (setEmptySet.contains((KotlinTarget) it2.next())) {
                }
            }
            return true;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, ktSourceElement, ktDiagnosticFactory2, ktModifierKeywordToken, INSTANCE.firstOrThis(list), checkerContext, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        return false;
    }

    private final boolean checkTarget(KtSourceElement modifierSource, KtModifierKeywordToken modifierToken, List<? extends KotlinTarget> actualTargets, FirDeclaration parent, CheckerContext context, DiagnosticReporter reporter) {
        if (!checkTarget$checkModifier(modifierToken, actualTargets, context, reporter, modifierSource, FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET())) {
            return false;
        }
        if ((parent instanceof FirRegularClass) && (Intrinsics.areEqual(modifierToken, KtTokens.EXPECT_KEYWORD) || Intrinsics.areEqual(modifierToken, KtTokens.HEADER_KEYWORD))) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifierSource, FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET(), modifierToken, "nested class", context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return false;
        }
        KtModifierKeywordToken ktModifierKeywordToken = ModifierCheckerHelpersKt.getDeprecatedModifierMap().get(modifierToken);
        if (ktModifierKeywordToken != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifierSource, FirErrors.INSTANCE.getDEPRECATED_MODIFIER(), modifierToken, ktModifierKeywordToken, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            return true;
        }
        if (!checkTarget$checkModifier(modifierToken, actualTargets, context, reporter, modifierSource, FirErrors.INSTANCE.getDEPRECATED_MODIFIER_FOR_TARGET())) {
            return true;
        }
        checkTarget$checkModifier(modifierToken, actualTargets, context, reporter, modifierSource, FirErrors.INSTANCE.getREDUNDANT_MODIFIER_FOR_TARGET());
        return true;
    }

    private final boolean checkParent(KtSourceElement modifierSource, KtModifierKeywordToken modifierToken, List<? extends KotlinTarget> actualParents, FirDeclaration parent, CheckerContext context, DiagnosticReporter reporter) {
        Set<KotlinTarget> set = ModifierCheckerHelpersKt.getDeprecatedParentTargetMap().get(modifierToken);
        if (set != null) {
            List<? extends KotlinTarget> list = actualParents;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (set.contains((KotlinTarget) it.next())) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifierSource, FirErrors.INSTANCE.getDEPRECATED_MODIFIER_CONTAINING_DECLARATION(), modifierToken, firstOrThis(actualParents), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                        return true;
                    }
                }
            }
        }
        if (Intrinsics.areEqual(modifierToken, KtTokens.PROTECTED_KEYWORD) && isFinalExpectClass(parent)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifierSource, FirErrors.INSTANCE.getWRONG_MODIFIER_CONTAINING_DECLARATION(), modifierToken, "final expect class", context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
        TargetAllowedPredicate targetAllowedPredicate = ModifierCheckerHelpersKt.getPossibleParentTargetPredicateMap().get(modifierToken);
        if (targetAllowedPredicate == null) {
            return true;
        }
        List<? extends KotlinTarget> list2 = actualParents;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                if (targetAllowedPredicate.isAllowed((KotlinTarget) it2.next(), FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()))) {
                    return true;
                }
            }
        }
        if (Intrinsics.areEqual(modifierToken, KtTokens.INNER_KEYWORD) && (parent instanceof FirScript)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifierSource, FirErrors.INSTANCE.getINNER_ON_TOP_LEVEL_SCRIPT_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return false;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifierSource, FirErrors.INSTANCE.getWRONG_MODIFIER_CONTAINING_DECLARATION(), modifierToken, firstOrThis(actualParents), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        return false;
    }

    private final String firstOrThis(List<? extends KotlinTarget> list) {
        String description;
        KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) list);
        return (kotlinTarget == null || (description = kotlinTarget.getDescription()) == null) ? "this" : description;
    }

    private final boolean isFinalExpectClass(FirDeclaration d) {
        if (d instanceof FirClass) {
            FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) d;
            Modality modality = firMemberDeclaration.getStatus().getModality();
            if ((modality == null || modality == Modality.FINAL) && firMemberDeclaration.getStatus().isExpect()) {
                return true;
            }
        }
        return false;
    }
}
