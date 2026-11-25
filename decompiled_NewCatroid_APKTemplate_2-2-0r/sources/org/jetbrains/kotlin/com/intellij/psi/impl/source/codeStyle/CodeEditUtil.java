package org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle;

import java.util.function.Supplier;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.LanguageTokenSeparatorGenerators;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.NotNullFunction;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CodeEditUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Key<Boolean> GENERATED_FLAG = new Key<>("GENERATED_FLAG");
    private static final Key<Integer> INDENT_INFO = new Key<>("INDENT_INFO");
    private static final Key<Boolean> REFORMAT_BEFORE_KEY = new Key<>("REFORMAT_BEFORE_KEY");
    private static final Key<Boolean> REFORMAT_KEY = new Key<>("REFORMAT_KEY");
    private static final ThreadLocal<Boolean> ALLOW_TO_MARK_NODES_TO_REFORMAT = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return Boolean.TRUE;
        }
    });
    private static final ThreadLocal<Boolean> ALLOW_NODES_REFORMATTING = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return Boolean.TRUE;
        }
    });
    private static final ThreadLocal<NotNullFunction<? super ASTNode, Boolean>> NODE_REFORMAT_STRATEGY = new ThreadLocal<>();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "child";
                break;
            case 2:
            case 4:
            case 8:
            case 11:
            default:
                objArr[0] = "parent";
                break;
            case 5:
            case 9:
                objArr[0] = "first";
                break;
            case 6:
            case 10:
                objArr[0] = "last";
                break;
            case 7:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 12:
                objArr[0] = "oldChild";
                break;
            case 13:
                objArr[0] = "newChild";
                break;
            case 14:
            case 15:
                objArr[0] = "right";
                break;
            case 16:
                objArr[0] = "text";
                break;
            case 17:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 18:
                objArr[0] = "manager";
                break;
            case 19:
            case 20:
                objArr[0] = "node";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/codeStyle/CodeEditUtil";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "removeChild";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "addChildren";
                break;
            case 7:
                objArr[2] = "isComment";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "removeChildren";
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "replaceChild";
                break;
            case 14:
                objArr[2] = "markToReformatBeforeOrInsertWhitespace";
                break;
            case 15:
                objArr[2] = "markToReformatBefore";
                break;
            case 16:
                objArr[2] = "getBlankLines";
                break;
            case 17:
                objArr[2] = "isMarkedToReformatBefore";
                break;
            case 18:
                objArr[2] = "createLineFeed";
                break;
            case 19:
                objArr[2] = "isMarkedToReformat";
                break;
            case 20:
                objArr[2] = "markToReformat";
                break;
            default:
                objArr[2] = "addChild";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private CodeEditUtil() {
    }

    public static void addChild(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(1);
        }
        addChildren(aSTNode, aSTNode2, aSTNode2, aSTNode3);
    }

    public static void removeChild(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(3);
        }
        removeChildren(aSTNode, aSTNode2, aSTNode2);
    }

    public static ASTNode addChildren(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3, ASTNode aSTNode4) {
        ASTNode treePrev;
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(5);
        }
        if (aSTNode3 == null) {
            $$$reportNull$$$0(6);
        }
        ASTNode treeNext = aSTNode3.getTreeNext();
        for (ASTNode treeNext2 = aSTNode2; treeNext2 != treeNext; treeNext2 = treeNext2.getTreeNext()) {
            saveWhitespacesInfo(treeNext2);
        }
        if (aSTNode4 != null && isComment(aSTNode4.getElementType()) && (treePrev = aSTNode4.getTreePrev()) != null && treePrev.getElementType() == TokenType.WHITE_SPACE) {
            aSTNode4 = treePrev;
        }
        aSTNode.addChildren(aSTNode2, treeNext, aSTNode4);
        ASTNode aSTNodeFindFirstLeaf = findFirstLeaf(aSTNode2, aSTNode3);
        ASTNode aSTNodePrevLeaf = TreeUtil.prevLeaf(aSTNode2);
        if (aSTNodeFindFirstLeaf != null) {
            ASTNode aSTNodeMakePlaceHolderBetweenTokens = makePlaceHolderBetweenTokens(aSTNodePrevLeaf, aSTNodeFindFirstLeaf, isFormattingRequired(aSTNodePrevLeaf, aSTNode2), false);
            if ((aSTNodeMakePlaceHolderBetweenTokens == aSTNodePrevLeaf || aSTNode2 != aSTNodeFindFirstLeaf) && aSTNode2.getTreeParent() == aSTNode) {
                aSTNodeMakePlaceHolderBetweenTokens = aSTNode2;
            }
            ASTNode aSTNodeFindLastLeaf = findLastLeaf(aSTNode2, aSTNode3);
            ASTNode aSTNodeMakePlaceHolderBetweenTokens2 = makePlaceHolderBetweenTokens(aSTNodeFindLastLeaf, TreeUtil.nextLeaf(aSTNode3), true, false);
            return (aSTNodeMakePlaceHolderBetweenTokens2 == aSTNodeFindLastLeaf || aSTNodeFindLastLeaf != aSTNode2) ? aSTNodeMakePlaceHolderBetweenTokens : aSTNodeMakePlaceHolderBetweenTokens2;
        }
        makePlaceHolderBetweenTokens(aSTNodePrevLeaf, TreeUtil.nextLeaf(aSTNode3), isFormattingRequired(aSTNodePrevLeaf, aSTNode2), false);
        return aSTNode2;
    }

    private static boolean isComment(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(7);
        }
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(iElementType.getLanguage());
        return parserDefinitionForLanguage != null && parserDefinitionForLanguage.getCommentTokens().contains(iElementType);
    }

    private static boolean isFormattingRequired(ASTNode aSTNode, ASTNode aSTNode2) {
        while (aSTNode2 != null) {
            for (ASTNode treeParent = aSTNode; treeParent != null; treeParent = treeParent.getTreeParent()) {
                if (treeParent.getTreeNext() == aSTNode2) {
                    return true;
                }
            }
            ASTNode treeParent2 = aSTNode2.getTreeParent();
            if (treeParent2 == null || !treeParent2.getTextRange().equals(aSTNode2.getTextRange())) {
                return false;
            }
            aSTNode2 = treeParent2;
        }
        return false;
    }

    public static void saveWhitespacesInfo(ASTNode aSTNode) {
        PsiElement psi;
        if (aSTNode == null || isNodeGenerated(aSTNode) || getOldIndentation(aSTNode) >= 0 || (psi = aSTNode.getPsi()) == null) {
            return;
        }
        setOldIndentation((TreeElement) aSTNode, IndentHelper.getInstance().getIndent(psi.getContainingFile(), aSTNode));
    }

    public static int getOldIndentation(ASTNode aSTNode) {
        Integer num;
        if (aSTNode == null || (num = (Integer) aSTNode.getCopyableUserData(INDENT_INFO)) == null) {
            return -1;
        }
        return num.intValue();
    }

    public static void removeChildren(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(9);
        }
        if (aSTNode3 == null) {
            $$$reportNull$$$0(10);
        }
        boolean z = aSTNode3.getStartOffset() + aSTNode3.getTextLength() == aSTNode.getStartOffset() + aSTNode.getTextLength();
        boolean zNeedToForceReformat = needToForceReformat(aSTNode, aSTNode2, aSTNode3);
        saveWhitespacesInfo(aSTNode2);
        for (TreeElement treeNext = (TreeElement) aSTNode2; treeNext != null && treeNext != aSTNode3; treeNext = treeNext.getTreeNext()) {
        }
        ASTNode aSTNodePrevLeaf = TreeUtil.prevLeaf(aSTNode2);
        ASTNode aSTNodeNextLeaf = TreeUtil.nextLeaf(aSTNode3);
        aSTNode.removeRange(aSTNode2, aSTNode3.getTreeNext());
        if (aSTNodeNextLeaf != null && aSTNodePrevLeaf != null && aSTNodeNextLeaf.getTreeParent() == null) {
            aSTNodeNextLeaf = aSTNodePrevLeaf.getTreeNext();
        }
        makePlaceHolderBetweenTokens(aSTNodePrevLeaf, aSTNodeNextLeaf, zNeedToForceReformat, z);
    }

    private static boolean needToForceReformat(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        return aSTNode == null || aSTNode2.getStartOffset() != aSTNode.getStartOffset() || (aSTNode.getText().trim().length() == getTrimmedTextLength(aSTNode2, aSTNode3) && needToForceReformat(aSTNode.getTreeParent(), aSTNode, aSTNode));
    }

    private static int getTrimmedTextLength(ASTNode aSTNode, ASTNode aSTNode2) {
        StringBuilder sb = new StringBuilder();
        while (aSTNode != aSTNode2.getTreeNext()) {
            sb.append(aSTNode.getText());
            aSTNode = aSTNode.getTreeNext();
        }
        return sb.toString().trim().length();
    }

    public static void replaceChild(ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        ASTNode treeNext;
        if (aSTNode == null) {
            $$$reportNull$$$0(11);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(12);
        }
        if (aSTNode3 == null) {
            $$$reportNull$$$0(13);
        }
        saveWhitespacesInfo(aSTNode2);
        saveWhitespacesInfo(aSTNode3);
        LeafElement leafElementFindFirstLeaf = TreeUtil.findFirstLeaf(aSTNode2);
        aSTNode.replaceChild(aSTNode2, aSTNode3);
        LeafElement leafElementFindFirstLeaf2 = TreeUtil.findFirstLeaf(aSTNode3);
        ASTNode aSTNodePrevLeaf = TreeUtil.prevLeaf(aSTNode3);
        if (leafElementFindFirstLeaf2 != null) {
            ASTNode aSTNodeNextLeaf = TreeUtil.nextLeaf(aSTNode3);
            makePlaceHolderBetweenTokens(aSTNodePrevLeaf, leafElementFindFirstLeaf2, isFormattingRequired(aSTNodePrevLeaf, aSTNode3), false);
            if (aSTNodeNextLeaf == null || CharArrayUtil.containLineBreaks(aSTNodeNextLeaf.getText())) {
                return;
            }
            makePlaceHolderBetweenTokens(TreeUtil.prevLeaf(aSTNodeNextLeaf), aSTNodeNextLeaf, false, false);
            return;
        }
        if (leafElementFindFirstLeaf != null && aSTNodePrevLeaf == null && (treeNext = aSTNode3.getTreeNext()) != null && treeNext.getElementType() == TokenType.WHITE_SPACE) {
            aSTNode.removeChild(treeNext);
        }
        makePlaceHolderBetweenTokens(aSTNodePrevLeaf, TreeUtil.nextLeaf(aSTNode3), isFormattingRequired(aSTNodePrevLeaf, aSTNode3), false);
    }

    private static ASTNode findFirstLeaf(ASTNode aSTNode, ASTNode aSTNode2) {
        do {
            LeafElement leafElementFindFirstLeaf = TreeUtil.findFirstLeaf(aSTNode);
            if (leafElementFindFirstLeaf != null) {
                return leafElementFindFirstLeaf;
            }
            aSTNode = aSTNode.getTreeNext();
            if (aSTNode == null) {
                return null;
            }
        } while (aSTNode != aSTNode2);
        return null;
    }

    private static ASTNode findLastLeaf(ASTNode aSTNode, ASTNode aSTNode2) {
        do {
            ASTNode aSTNodeFindLastLeaf = TreeUtil.findLastLeaf(aSTNode2);
            if (aSTNodeFindLastLeaf != null) {
                return aSTNodeFindLastLeaf;
            }
            aSTNode2 = aSTNode2.getTreePrev();
            if (aSTNode2 == null) {
                return null;
            }
        } while (aSTNode != aSTNode2);
        return null;
    }

    private static ASTNode makePlaceHolderBetweenTokens(ASTNode aSTNode, ASTNode aSTNode2, boolean z, boolean z2) {
        String text;
        if (aSTNode2 == null) {
            return aSTNode;
        }
        markToReformatBefore(aSTNode2, false);
        if (aSTNode == null) {
            markToReformatBefore(aSTNode2, true);
            return aSTNode;
        }
        if (aSTNode.getElementType() == TokenType.WHITE_SPACE && aSTNode.getTreeNext() == null && z2) {
            ASTNode aSTNodePrevLeaf = TreeUtil.prevLeaf(aSTNode);
            aSTNode.getTreeParent().removeChild(aSTNode);
            markToReformatBeforeOrInsertWhitespace(aSTNodePrevLeaf, aSTNode2);
            return aSTNode2;
        }
        if (aSTNode.getElementType() == TokenType.WHITE_SPACE && aSTNode2.getElementType() == TokenType.WHITE_SPACE) {
            int blankLines = getBlankLines(aSTNode.getText());
            int blankLines2 = getBlankLines(aSTNode2.getText());
            boolean z3 = blankLines < blankLines2;
            if (blankLines == 0 && blankLines2 == 0) {
                text = aSTNode.getText() + aSTNode2.getText();
            } else if (z3) {
                text = aSTNode2.getText();
            } else {
                text = aSTNode.getText();
            }
            if (z3 || z) {
                LeafElement leafElementWhitespace = ASTFactory.whitespace(text);
                if (!z3) {
                    aSTNode.getTreeParent().replaceChild(aSTNode, leafElementWhitespace);
                    aSTNode2.getTreeParent().removeChild(aSTNode2);
                } else {
                    aSTNode2.getTreeParent().replaceChild(aSTNode2, leafElementWhitespace);
                    aSTNode.getTreeParent().removeChild(aSTNode);
                }
                return leafElementWhitespace;
            }
            aSTNode2.getTreeParent().removeChild(aSTNode2);
            return aSTNode;
        }
        if (aSTNode.getElementType() == TokenType.WHITE_SPACE && !z) {
            return aSTNode;
        }
        if (aSTNode2.getElementType() == TokenType.WHITE_SPACE) {
            markWhitespaceForReformat(aSTNode2);
            return aSTNode;
        }
        if (aSTNode.getElementType() == TokenType.WHITE_SPACE) {
            markWhitespaceForReformat(aSTNode);
            return aSTNode;
        }
        markToReformatBeforeOrInsertWhitespace(aSTNode, aSTNode2);
        return aSTNode;
    }

    private static void markWhitespaceForReformat(ASTNode aSTNode) {
        aSTNode.getTreeParent().replaceChild(aSTNode, ASTFactory.whitespace(aSTNode.getText()));
    }

    private static void markToReformatBeforeOrInsertWhitespace(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode2 == null) {
            $$$reportNull$$$0(14);
        }
        ASTNode aSTNodeGenerateWhitespaceBetweenTokens = null;
        Language notAnyLanguage = aSTNode != null ? PsiUtilCore.getNotAnyLanguage(aSTNode) : null;
        Language notAnyLanguage2 = PsiUtilCore.getNotAnyLanguage(aSTNode2);
        if (notAnyLanguage != null && notAnyLanguage.isKindOf(notAnyLanguage2)) {
            aSTNodeGenerateWhitespaceBetweenTokens = LanguageTokenSeparatorGenerators.INSTANCE.forLanguage(notAnyLanguage).generateWhitespaceBetweenTokens(aSTNode, aSTNode2);
        } else if (notAnyLanguage2.isKindOf(notAnyLanguage)) {
            aSTNodeGenerateWhitespaceBetweenTokens = LanguageTokenSeparatorGenerators.INSTANCE.forLanguage(notAnyLanguage2).generateWhitespaceBetweenTokens(aSTNode, aSTNode2);
        }
        if (aSTNodeGenerateWhitespaceBetweenTokens != null) {
            TreeUtil.CommonParentState commonParentState = new TreeUtil.CommonParentState();
            TreeUtil.prevLeaf((TreeElement) aSTNode2, commonParentState);
            commonParentState.nextLeafBranchStart.getTreeParent().addChild(aSTNodeGenerateWhitespaceBetweenTokens, commonParentState.nextLeafBranchStart);
            return;
        }
        markToReformatBefore(aSTNode2, true);
    }

    public static void markToReformatBefore(ASTNode aSTNode, boolean z) {
        if (aSTNode == null) {
            $$$reportNull$$$0(15);
        }
        if (ALLOW_TO_MARK_NODES_TO_REFORMAT.get().booleanValue()) {
            aSTNode.putCopyableUserData(REFORMAT_BEFORE_KEY, z ? true : null);
        }
    }

    private static int getBlankLines(String str) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        int i = 0;
        int iIndexOf = -1;
        while (true) {
            iIndexOf = str.indexOf(10, iIndexOf + 1);
            if (iIndexOf < 0) {
                return i;
            }
            i++;
        }
    }

    public static boolean isNodeGenerated(ASTNode aSTNode) {
        return aSTNode == null || aSTNode.getCopyableUserData(GENERATED_FLAG) != null;
    }

    public static void setNodeGenerated(ASTNode aSTNode, boolean z) {
        if (aSTNode == null) {
            return;
        }
        aSTNode.putCopyableUserData(GENERATED_FLAG, z ? true : null);
    }

    public static void setNodeGeneratedRecursively(ASTNode aSTNode, boolean z) {
        if (aSTNode == null) {
            return;
        }
        setNodeGenerated(aSTNode, z);
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            setNodeGeneratedRecursively(firstChildNode, z);
        }
    }

    public static void setOldIndentation(TreeElement treeElement, int i) {
        if (treeElement == null) {
            return;
        }
        treeElement.putCopyableUserData(INDENT_INFO, i >= 0 ? Integer.valueOf(i) : null);
    }

    public static boolean isMarkedToReformatBefore(TreeElement treeElement) {
        if (treeElement == null) {
            $$$reportNull$$$0(17);
        }
        return treeElement.getCopyableUserData(REFORMAT_BEFORE_KEY) != null;
    }

    public static PsiElement createLineFeed(PsiManager psiManager) {
        if (psiManager == null) {
            $$$reportNull$$$0(18);
        }
        return Factory.createSingleLeafElement(TokenType.WHITE_SPACE, "\n", 0, 1, null, psiManager).getPsi();
    }

    public static boolean isMarkedToReformat(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(19);
        }
        if (aSTNode.getCopyableUserData(REFORMAT_KEY) == null || !isSuspendedNodesReformattingAllowed()) {
            return false;
        }
        NotNullFunction<? super ASTNode, Boolean> notNullFunction = NODE_REFORMAT_STRATEGY.get();
        return notNullFunction == null || notNullFunction.fun(aSTNode).booleanValue();
    }

    public static void markToReformat(ASTNode aSTNode, boolean z) {
        if (aSTNode == null) {
            $$$reportNull$$$0(20);
        }
        if (ALLOW_TO_MARK_NODES_TO_REFORMAT.get().booleanValue()) {
            aSTNode.putCopyableUserData(REFORMAT_KEY, z ? true : null);
        }
    }

    public static void allowToMarkNodesForPostponedFormatting(boolean z) {
        ALLOW_TO_MARK_NODES_TO_REFORMAT.set(Boolean.valueOf(z));
    }

    public static boolean isSuspendedNodesReformattingAllowed() {
        return ALLOW_NODES_REFORMATTING.get().booleanValue();
    }

    public static void setAllowSuspendNodesReformatting(boolean z) {
        ALLOW_NODES_REFORMATTING.set(Boolean.valueOf(z));
    }

    public static void setNodeReformatStrategy(NotNullFunction<? super ASTNode, Boolean> notNullFunction) {
        NODE_REFORMAT_STRATEGY.set(notNullFunction);
    }
}
