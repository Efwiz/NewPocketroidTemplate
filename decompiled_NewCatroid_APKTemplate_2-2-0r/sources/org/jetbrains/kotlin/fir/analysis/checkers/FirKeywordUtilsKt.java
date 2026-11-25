package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtValVarKeywordOwner;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;

/* JADX INFO: compiled from: FirKeywordUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0017\u0010\b\u001a\u00020\t*\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002\u001a\u0018\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f\u001a\u000e\u0010\u0010\u001a\u0004\u0018\u00010\n*\u0004\u0018\u00010\u0005\u001a\u0012\u0010\u0011\u001a\u00020\t*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f\"\u0013\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u00058@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"MODIFIER_KEYWORD_SET", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "valOrVarKeyword", "Lorg/jetbrains/kotlin/lexer/KtKeywordToken;", "Lorg/jetbrains/kotlin/KtSourceElement;", "getValOrVarKeyword", "(Lorg/jetbrains/kotlin/KtSourceElement;)Lorg/jetbrains/kotlin/lexer/KtKeywordToken;", "contains", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifierList;", "token", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "getModifier", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirModifier;", "Lorg/jetbrains/kotlin/fir/FirElement;", "getModifierList", "hasModifier", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirKeywordUtilsKt {
    private static final TokenSet MODIFIER_KEYWORD_SET;

    static {
        TokenSet tokenSetOrSet = TokenSet.orSet(KtTokens.SOFT_KEYWORDS, TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.FUN_KEYWORD));
        Intrinsics.checkNotNullExpressionValue(tokenSetOrSet, "orSet(...)");
        MODIFIER_KEYWORD_SET = tokenSetOrSet;
    }

    public static final FirModifierList getModifierList(KtSourceElement ktSourceElement) {
        Object next;
        KtModifierList modifierList;
        FirModifierList.FirPsiModifierList firPsiModifierList = null;
        if (ktSourceElement == null) {
            return null;
        }
        if (ktSourceElement instanceof KtPsiSourceElement) {
            PsiElement psi = ((KtPsiSourceElement) ktSourceElement).getPsi();
            KtModifierListOwner ktModifierListOwner = psi instanceof KtModifierListOwner ? (KtModifierListOwner) psi : null;
            if (ktModifierListOwner != null && (modifierList = ktModifierListOwner.getModifierList()) != null) {
                firPsiModifierList = new FirModifierList.FirPsiModifierList(modifierList);
            }
            return firPsiModifierList;
        }
        if (!(ktSourceElement instanceof KtLightSourceElement)) {
            throw new NoWhenBranchMatchedException();
        }
        Iterator<T> it = LightTreeUtilsKt.getChildren(ktSourceElement.getLighterASTNode(), ktSourceElement.getTreeStructure()).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((LighterASTNode) next).getTokenType(), KtNodeTypes.MODIFIER_LIST));
        LighterASTNode lighterASTNode = (LighterASTNode) next;
        if (lighterASTNode == null) {
            return null;
        }
        return new FirModifierList.FirLightModifierList(lighterASTNode, ktSourceElement.getTreeStructure(), ktSourceElement.getStartOffset() - ktSourceElement.getLighterASTNode().getStartOffset());
    }

    public static final boolean contains(FirModifierList firModifierList, KtModifierKeywordToken token) {
        Intrinsics.checkNotNullParameter(token, "token");
        return firModifierList != null && firModifierList.contains(token);
    }

    public static final FirModifier<?> getModifier(FirElement firElement, KtModifierKeywordToken token) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        Intrinsics.checkNotNullParameter(token, "token");
        FirModifierList modifierList = getModifierList(firElement.getSource());
        if (modifierList != null) {
            return modifierList.get(token);
        }
        return null;
    }

    public static final boolean hasModifier(FirElement firElement, KtModifierKeywordToken token) {
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        Intrinsics.checkNotNullParameter(token, "token");
        return contains(getModifierList(firElement.getSource()), token);
    }

    public static final KtKeywordToken getValOrVarKeyword(KtSourceElement ktSourceElement) {
        PsiElement valOrVarKeyword;
        if (ktSourceElement == null) {
            return null;
        }
        if (!(ktSourceElement instanceof KtPsiSourceElement)) {
            if (!(ktSourceElement instanceof KtLightSourceElement)) {
                throw new NoWhenBranchMatchedException();
            }
            LighterASTNode lighterASTNodeValOrVarKeyword = LightTreePositioningStrategiesKt.valOrVarKeyword(ktSourceElement.getTreeStructure(), ktSourceElement.getLighterASTNode());
            IElementType tokenType = lighterASTNodeValOrVarKeyword != null ? lighterASTNodeValOrVarKeyword.getTokenType() : null;
            if (tokenType instanceof KtKeywordToken) {
                return (KtKeywordToken) tokenType;
            }
            return null;
        }
        PsiElement psi = ((KtPsiSourceElement) ktSourceElement).getPsi();
        KtValVarKeywordOwner ktValVarKeywordOwner = psi instanceof KtValVarKeywordOwner ? (KtValVarKeywordOwner) psi : null;
        if (ktValVarKeywordOwner == null || (valOrVarKeyword = ktValVarKeywordOwner.getValOrVarKeyword()) == null) {
            return null;
        }
        ASTNode node = valOrVarKeyword.getNode();
        IElementType elementType = node != null ? node.getElementType() : null;
        if (elementType instanceof KtKeywordToken) {
            return (KtKeywordToken) elementType;
        }
        return null;
    }
}
