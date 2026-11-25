package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;

/* JADX INFO: compiled from: ModifiersChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fJ6\u0010\u0010\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u0017H\u0002J.\u0010\u0018\u001a\u00020\u00192\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J8\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000b2\b\u0010!\u001a\u0004\u0018\u00010\r2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J*\u0010\"\u001a\u00020\u00192\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00122\b\u0010!\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J&\u0010#\u001a\u00020\u00192\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00122\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J\u0012\u0010$\u001a\u00020\u00192\b\u0010%\u001a\u0004\u0018\u00010\rH\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ModifierCheckerCore;", "", "()V", "MODIFIER_KEYWORD_SET", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "check", "", "listOwner", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkCompatibility", "firstNode", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "secondNode", "owner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "incorrectNodes", "", "checkLanguageLevelSupport", "", "node", "actualTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "checkModifierList", SchemaSymbols.ATTVAL_LIST, "Lorg/jetbrains/kotlin/psi/KtModifierList;", "parentDescriptor", "checkParent", "checkTarget", "isFinalExpectClass", "d", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModifierCheckerCore {
    public static final ModifierCheckerCore INSTANCE = new ModifierCheckerCore();
    private static final TokenSet MODIFIER_KEYWORD_SET;

    /* JADX INFO: compiled from: ModifiersChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
                iArr[Compatibility.COMPATIBLE_FOR_CLASSES_ONLY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Compatibility.INCOMPATIBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[LanguageFeature.State.values().length];
            try {
                iArr2[LanguageFeature.State.ENABLED_WITH_WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[LanguageFeature.State.DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[LanguageFeature.State.ENABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private ModifierCheckerCore() {
    }

    public final void check(KtModifierListOwner listOwner, BindingTrace trace, DeclarationDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(listOwner, "listOwner");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (listOwner instanceof KtDeclarationWithBody) {
            for (KtParameter ktParameter : ((KtDeclarationWithBody) listOwner).getValueParameters()) {
                if (!ktParameter.hasValOrVar()) {
                    Intrinsics.checkNotNull(ktParameter);
                    check(ktParameter, trace, (DeclarationDescriptor) trace.get(BindingContext.VALUE_PARAMETER, ktParameter), languageVersionSettings);
                }
            }
        }
        AnnotationChecker.Companion companion = AnnotationChecker.INSTANCE;
        KtModifierListOwner ktModifierListOwner = listOwner;
        ClassDescriptor classDescriptor = descriptor instanceof ClassDescriptor ? (ClassDescriptor) descriptor : null;
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        List<KotlinTarget> declarationSiteActualTargetList = companion.getDeclarationSiteActualTargetList(ktModifierListOwner, classDescriptor, context);
        KtModifierList modifierList = listOwner.getModifierList();
        if (modifierList == null) {
            return;
        }
        checkModifierList(modifierList, trace, descriptor != null ? descriptor.getContainingModule() : null, declarationSiteActualTargetList, languageVersionSettings);
    }

    static {
        TokenSet tokenSetOrSet = TokenSet.orSet(KtTokens.SOFT_KEYWORDS, TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.FUN_KEYWORD));
        Intrinsics.checkNotNullExpressionValue(tokenSetOrSet, "orSet(...)");
        MODIFIER_KEYWORD_SET = tokenSetOrSet;
    }

    private final void checkModifierList(KtModifierList list, BindingTrace trace, DeclarationDescriptor parentDescriptor, List<? extends KotlinTarget> actualTargets, LanguageVersionSettings languageVersionSettings) {
        if (list.getStub() != 0) {
            return;
        }
        HashSet hashSet = new HashSet();
        ASTNode[] children = list.getNode().getChildren(MODIFIER_KEYWORD_SET);
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        for (ASTNode aSTNode : children) {
            int i = 0;
            for (int length = children.length; i < length; length = length) {
                ASTNode aSTNode2 = children[i];
                if (Intrinsics.areEqual(aSTNode2, aSTNode)) {
                    break;
                }
                Intrinsics.checkNotNull(aSTNode2);
                Intrinsics.checkNotNull(aSTNode);
                PsiElement owner = list.getOwner();
                Intrinsics.checkNotNullExpressionValue(owner, "getOwner(...)");
                checkCompatibility(trace, aSTNode2, aSTNode, owner, hashSet);
                i++;
            }
            if (!hashSet.contains(aSTNode)) {
                Intrinsics.checkNotNull(aSTNode);
                if (!checkTarget(trace, aSTNode, actualTargets)) {
                    hashSet.add(aSTNode);
                } else if (!checkParent(trace, aSTNode, parentDescriptor, languageVersionSettings)) {
                    hashSet.add(aSTNode);
                } else if (!checkLanguageLevelSupport(trace, aSTNode, languageVersionSettings, actualTargets)) {
                    hashSet.add(aSTNode);
                }
            }
        }
    }

    private final void checkCompatibility(BindingTrace trace, ASTNode firstNode, ASTNode secondNode, PsiElement owner, Set<ASTNode> incorrectNodes) {
        IElementType elementType = firstNode.getElementType();
        Intrinsics.checkNotNull(elementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
        KtModifierKeywordToken ktModifierKeywordToken = (KtModifierKeywordToken) elementType;
        IElementType elementType2 = secondNode.getElementType();
        Intrinsics.checkNotNull(elementType2, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
        KtModifierKeywordToken ktModifierKeywordToken2 = (KtModifierKeywordToken) elementType2;
        Compatibility compatibility = ModifierCheckerHelpersKt.compatibility(ktModifierKeywordToken, ktModifierKeywordToken2);
        switch (WhenMappings.$EnumSwitchMapping$0[compatibility.ordinal()]) {
            case 2:
                if (incorrectNodes.add(secondNode)) {
                    trace.report(Errors.REPEATED_MODIFIER.on(secondNode.getPsi(), ktModifierKeywordToken));
                }
                break;
            case 3:
                trace.report(Errors.REDUNDANT_MODIFIER.on(secondNode.getPsi(), ktModifierKeywordToken2, ktModifierKeywordToken));
                break;
            case 4:
                trace.report(Errors.REDUNDANT_MODIFIER.on(firstNode.getPsi(), ktModifierKeywordToken, ktModifierKeywordToken2));
                break;
            case 5:
                trace.report(Errors.DEPRECATED_MODIFIER_PAIR.on(firstNode.getPsi(), ktModifierKeywordToken, ktModifierKeywordToken2));
                trace.report(Errors.DEPRECATED_MODIFIER_PAIR.on(secondNode.getPsi(), ktModifierKeywordToken2, ktModifierKeywordToken));
                break;
            case 6:
            case 7:
                if (compatibility != Compatibility.COMPATIBLE_FOR_CLASSES_ONLY || !(owner instanceof KtClassOrObject)) {
                    if (incorrectNodes.add(firstNode)) {
                        trace.report(Errors.INCOMPATIBLE_MODIFIERS.on(firstNode.getPsi(), ktModifierKeywordToken, ktModifierKeywordToken2));
                    }
                    if (incorrectNodes.add(secondNode)) {
                        trace.report(Errors.INCOMPATIBLE_MODIFIERS.on(secondNode.getPsi(), ktModifierKeywordToken2, ktModifierKeywordToken));
                    }
                }
                break;
        }
    }

    private final boolean checkTarget(BindingTrace trace, ASTNode node, List<? extends KotlinTarget> actualTargets) {
        String description;
        String description2;
        String description3;
        IElementType elementType = node.getElementType();
        Intrinsics.checkNotNull(elementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
        KtModifierKeywordToken ktModifierKeywordToken = (KtModifierKeywordToken) elementType;
        Set<KotlinTarget> setEmptySet = ModifierCheckerHelpersKt.getPossibleTargetMap().get(ktModifierKeywordToken);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        List<? extends KotlinTarget> list = actualTargets;
        boolean z = list instanceof Collection;
        String str = "this";
        if (!z || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (setEmptySet.contains((KotlinTarget) it.next())) {
                    KtModifierKeywordToken ktModifierKeywordToken2 = ModifierCheckerHelpersKt.getDeprecatedModifierMap().get(ktModifierKeywordToken);
                    Set<KotlinTarget> setEmptySet2 = ModifierCheckerHelpersKt.getDeprecatedTargetMap().get(ktModifierKeywordToken);
                    if (setEmptySet2 == null) {
                        setEmptySet2 = SetsKt.emptySet();
                    }
                    Set<KotlinTarget> setEmptySet3 = ModifierCheckerHelpersKt.getRedundantTargetMap().get(ktModifierKeywordToken);
                    if (setEmptySet3 == null) {
                        setEmptySet3 = SetsKt.emptySet();
                    }
                    if (ktModifierKeywordToken2 != null) {
                        trace.report(Errors.DEPRECATED_MODIFIER.on(node.getPsi(), ktModifierKeywordToken, ktModifierKeywordToken2));
                        return true;
                    }
                    if (!z || !list.isEmpty()) {
                        Iterator<T> it2 = list.iterator();
                        while (it2.hasNext()) {
                            if (setEmptySet2.contains((KotlinTarget) it2.next())) {
                                DiagnosticFactory2<PsiElement, KtModifierKeywordToken, String> diagnosticFactory2 = Errors.DEPRECATED_MODIFIER_FOR_TARGET;
                                PsiElement psi = node.getPsi();
                                KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargets);
                                if (kotlinTarget != null && (description = kotlinTarget.getDescription()) != null) {
                                    str = description;
                                }
                                trace.report(diagnosticFactory2.on(psi, ktModifierKeywordToken, str));
                                return true;
                            }
                        }
                    }
                    if (z && list.isEmpty()) {
                        return true;
                    }
                    Iterator<T> it3 = list.iterator();
                    while (it3.hasNext()) {
                        if (setEmptySet3.contains((KotlinTarget) it3.next())) {
                            DiagnosticFactory2<PsiElement, KtModifierKeywordToken, String> diagnosticFactory3 = Errors.REDUNDANT_MODIFIER_FOR_TARGET;
                            PsiElement psi2 = node.getPsi();
                            KotlinTarget kotlinTarget2 = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargets);
                            if (kotlinTarget2 != null && (description2 = kotlinTarget2.getDescription()) != null) {
                                str = description2;
                            }
                            trace.report(diagnosticFactory3.on(psi2, ktModifierKeywordToken, str));
                            return true;
                        }
                    }
                    return true;
                }
            }
        }
        DiagnosticFactory2<PsiElement, KtModifierKeywordToken, String> diagnosticFactory4 = Errors.WRONG_MODIFIER_TARGET;
        PsiElement psi3 = node.getPsi();
        KotlinTarget kotlinTarget3 = (KotlinTarget) CollectionsKt.firstOrNull((List) actualTargets);
        if (kotlinTarget3 != null && (description3 = kotlinTarget3.getDescription()) != null) {
            str = description3;
        }
        trace.report(diagnosticFactory4.on(psi3, ktModifierKeywordToken, str));
        return false;
    }

    private final boolean checkParent(BindingTrace trace, ASTNode node, DeclarationDescriptor parentDescriptor, LanguageVersionSettings languageVersionSettings) {
        List<KotlinTarget> function_list;
        String description;
        String description2;
        IElementType elementType = node.getElementType();
        Intrinsics.checkNotNull(elementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
        KtModifierKeywordToken ktModifierKeywordToken = (KtModifierKeywordToken) elementType;
        if (parentDescriptor instanceof ClassDescriptor) {
            KotlinTarget.Companion companion = KotlinTarget.INSTANCE;
            ClassDescriptor classDescriptor = (ClassDescriptor) parentDescriptor;
            ClassKind kind = classDescriptor.getClassKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            function_list = companion.classActualTargets(kind, classDescriptor.getIsInner(), classDescriptor.getIsCompanionObject(), DescriptorUtils.isLocal(parentDescriptor));
        } else if (parentDescriptor instanceof PropertySetterDescriptor) {
            function_list = KotlinTarget.INSTANCE.getPROPERTY_SETTER_LIST();
        } else if (parentDescriptor instanceof PropertyGetterDescriptor) {
            function_list = KotlinTarget.INSTANCE.getPROPERTY_GETTER_LIST();
        } else {
            function_list = parentDescriptor instanceof FunctionDescriptor ? KotlinTarget.INSTANCE.getFUNCTION_LIST() : KotlinTarget.INSTANCE.getFILE_LIST();
        }
        Set<KotlinTarget> set = ModifierCheckerHelpersKt.getDeprecatedParentTargetMap().get(ktModifierKeywordToken);
        String str = "this scope";
        if (set != null) {
            List<KotlinTarget> list = function_list;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (set.contains((KotlinTarget) it.next())) {
                        DiagnosticFactory2<PsiElement, KtModifierKeywordToken, String> diagnosticFactory2 = Errors.DEPRECATED_MODIFIER_CONTAINING_DECLARATION;
                        PsiElement psi = node.getPsi();
                        KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) function_list);
                        if (kotlinTarget != null && (description2 = kotlinTarget.getDescription()) != null) {
                            str = description2;
                        }
                        trace.report(diagnosticFactory2.on(psi, ktModifierKeywordToken, str));
                        return true;
                    }
                }
            }
        }
        if (Intrinsics.areEqual(ktModifierKeywordToken, KtTokens.PROTECTED_KEYWORD) && isFinalExpectClass(parentDescriptor)) {
            trace.report(Errors.WRONG_MODIFIER_CONTAINING_DECLARATION.on(node.getPsi(), ktModifierKeywordToken, "final expect class"));
        }
        TargetAllowedPredicate targetAllowedPredicate = ModifierCheckerHelpersKt.getPossibleParentTargetPredicateMap().get(ktModifierKeywordToken);
        if (targetAllowedPredicate == null) {
            return true;
        }
        List<KotlinTarget> list2 = function_list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                if (targetAllowedPredicate.isAllowed((KotlinTarget) it2.next(), languageVersionSettings)) {
                    return true;
                }
            }
        }
        DiagnosticFactory2<PsiElement, KtModifierKeywordToken, String> diagnosticFactory3 = Errors.WRONG_MODIFIER_CONTAINING_DECLARATION;
        PsiElement psi2 = node.getPsi();
        KotlinTarget kotlinTarget2 = (KotlinTarget) CollectionsKt.firstOrNull((List) function_list);
        if (kotlinTarget2 != null && (description = kotlinTarget2.getDescription()) != null) {
            str = description;
        }
        trace.report(diagnosticFactory3.on(psi2, ktModifierKeywordToken, str));
        return false;
    }

    private final boolean checkLanguageLevelSupport(BindingTrace trace, ASTNode node, LanguageVersionSettings languageVersionSettings, List<? extends KotlinTarget> actualTargets) {
        IElementType elementType = node.getElementType();
        Intrinsics.checkNotNull(elementType, "null cannot be cast to non-null type org.jetbrains.kotlin.lexer.KtModifierKeywordToken");
        List<LanguageFeature> list = ModifierCheckerHelpersKt.getFeatureDependencies().get((KtModifierKeywordToken) elementType);
        if (list == null) {
            return true;
        }
        for (LanguageFeature languageFeature : list) {
            Set<KotlinTarget> set = ModifierCheckerHelpersKt.getFeatureDependenciesTargets().get(languageFeature);
            if (set == null || !CollectionsKt.intersect(actualTargets, set).isEmpty()) {
                if (languageFeature == LanguageFeature.Coroutines) {
                    PsiElement psi = node.getPsi();
                    Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
                    CoroutineCallCheckerKt.checkCoroutinesFeature(languageVersionSettings, trace, psi);
                } else if (languageFeature == LanguageFeature.InlineClasses && languageVersionSettings.supportsFeature(LanguageFeature.JvmInlineValueClasses)) {
                    trace.report(Errors.INLINE_CLASS_DEPRECATED.on(node.getPsi()));
                } else {
                    LanguageFeature.State featureSupport = languageVersionSettings.getFeatureSupport(languageFeature);
                    if (languageFeature == LanguageFeature.MultiPlatformProjects && featureSupport == LanguageFeature.State.DISABLED) {
                        trace.report(Errors.NOT_A_MULTIPLATFORM_COMPILATION.on(node.getPsi()));
                    } else {
                        Pair<LanguageFeature, LanguageVersionSettings> pair = TuplesKt.to(languageFeature, languageVersionSettings);
                        int i = WhenMappings.$EnumSwitchMapping$1[featureSupport.ordinal()];
                        if (i == 1) {
                            trace.report(Errors.EXPERIMENTAL_FEATURE_WARNING.on(node.getPsi(), pair));
                        } else if (i == 2) {
                            trace.report(Errors.UNSUPPORTED_FEATURE.on(node.getPsi(), pair));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private final boolean isFinalExpectClass(DeclarationDescriptor d) {
        if (d instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) d;
            if (ModalityUtilsKt.isFinalOrEnum(classDescriptor) && classDescriptor.get$isExpect()) {
                return true;
            }
        }
        return false;
    }
}
