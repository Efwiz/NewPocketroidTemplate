package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import java.util.ArrayList;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocCommentImpl extends LazyParseablePsiElement implements JavaTokenType, Constants, PsiDocComment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiDocCommentImpl.class);
    private static final TokenSet TAG_BIT_SET = TokenSet.create(DOC_TAG);
    private static final ArrayFactory<PsiDocTag> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiDocCommentImpl.lambda$static$0(i);
        }
    };
    private static final Pattern WS_PATTERN = Pattern.compile("\\s*");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 4:
            case 5:
            case 6:
                objArr[0] = "node";
                break;
            case 7:
            case 8:
                objArr[0] = "child";
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocCommentImpl";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getTags";
                break;
            case 2:
                objArr[1] = "findTagsByName";
                break;
            case 3:
                objArr[1] = "getTokenType";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocCommentImpl";
                break;
            default:
                objArr[1] = "getDescriptionElements";
                break;
        }
        switch (i) {
            case 4:
                objArr[2] = "nodeIsNextAfterAsterisks";
                break;
            case 5:
                objArr[2] = "docTagEndsWithLineFeedAndAsterisks";
                break;
            case 6:
                objArr[2] = "nodeOnSameLineWithCommentStartBlock";
                break;
            case 7:
                objArr[2] = "deleteChildInternal";
                break;
            case 8:
                objArr[2] = "getChildRole";
                break;
            case 9:
                objArr[2] = "accept";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    static /* synthetic */ PsiDocTag[] lambda$static$0(int i) {
        return i == 0 ? PsiDocTag.EMPTY_ARRAY : new PsiDocTag[i];
    }

    public PsiDocCommentImpl(CharSequence charSequence) {
        super(JavaDocElementType.DOC_COMMENT, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment, org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase
    public PsiJavaDocumentedElement getOwner() {
        return PsiImplUtil.findDocCommentOwner(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiElement[] getDescriptionElements() {
        ArrayList arrayList = new ArrayList();
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            IElementType elementType = firstChildNode.getElementType();
            if (elementType == DOC_TAG) {
                break;
            }
            if (elementType != DOC_COMMENT_START && elementType != DOC_COMMENT_END && elementType != DOC_COMMENT_LEADING_ASTERISKS) {
                arrayList.add(firstChildNode.getPsi());
            }
        }
        PsiElement[] psiElementArray = PsiUtilCore.toPsiElementArray(arrayList);
        if (psiElementArray == null) {
            $$$reportNull$$$0(0);
        }
        return psiElementArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiDocTag[] getTags() {
        PsiDocTag[] psiDocTagArr = (PsiDocTag[]) getChildrenAsPsiElements(TAG_BIT_SET, ARRAY_FACTORY);
        if (psiDocTagArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiDocTagArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiDocTag findTagByName(String str) {
        if (getFirstChildNode().getElementType() == JavaDocElementType.DOC_COMMENT && !getFirstChildNode().getText().contains(str)) {
            return null;
        }
        for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getElementType() == DOC_TAG) {
                PsiDocTag psiDocTag = (PsiDocTag) SourceTreeToPsiMap.treeToPsiNotNull(firstChildNode);
                CharSequence chars = ((LeafElement) psiDocTag.getNameElement()).getChars();
                if (chars.length() > 0 && chars.charAt(0) == '@' && CharArrayUtil.regionMatches(chars, 1, str)) {
                    return psiDocTag;
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment
    public PsiDocTag[] findTagsByName(String str) {
        ArrayList arrayList = new ArrayList();
        String str2 = PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + str;
        for (PsiDocTag psiDocTag : getTags()) {
            if (psiDocTag.getNameElement().getText().equals(str2)) {
                arrayList.add(psiDocTag);
            }
        }
        PsiDocTag[] psiDocTagArr = (PsiDocTag[]) arrayList.toArray(PsiDocTag.EMPTY_ARRAY);
        if (psiDocTagArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiDocTagArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        IElementType elementType = getElementType();
        if (elementType == null) {
            $$$reportNull$$$0(3);
        }
        return elementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 110) {
            return getFirstChildNode();
        }
        if (i != 111) {
            return null;
        }
        TreeElement lastChildNode = getLastChildNode();
        if (lastChildNode.getElementType() == DOC_COMMENT_END) {
            return lastChildNode;
        }
        return null;
    }

    private static boolean isWhitespaceCommentData(ASTNode aSTNode) {
        return WS_PATTERN.matcher(aSTNode.getText()).matches();
    }

    private static void addNewLineToTag(CompositeElement compositeElement, PsiFile psiFile, PsiManager psiManager) {
        LOG.assertTrue(compositeElement != null && compositeElement.getElementType() == DOC_TAG);
        ASTNode lastChildNode = compositeElement.getLastChildNode();
        while (lastChildNode != null && lastChildNode.getElementType() == DOC_COMMENT_DATA && isWhitespaceCommentData(lastChildNode)) {
            lastChildNode = lastChildNode.getTreePrev();
        }
        if (lastChildNode == null || lastChildNode.getElementType() != DOC_COMMENT_LEADING_ASTERISKS) {
            CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(compositeElement);
            if (JavaFileCodeStyleFacade.forContext(psiFile).isJavaDocLeadingAsterisksEnabled()) {
                compositeElement.addChild(Factory.createSingleLeafElement(TokenType.WHITE_SPACE, "\n ", charTableFindCharTableByTree, psiManager));
                compositeElement.addChild(Factory.createSingleLeafElement(DOC_COMMENT_LEADING_ASTERISKS, "*", charTableFindCharTableByTree, psiManager));
                compositeElement.addChild(Factory.createSingleLeafElement(DOC_COMMENT_DATA, " ", charTableFindCharTableByTree, psiManager));
                return;
            }
            compositeElement.addChild(Factory.createSingleLeafElement(TokenType.WHITE_SPACE, "\n ", charTableFindCharTableByTree, psiManager));
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        boolean z = false;
        if (treeElement == aSTNode && treeElement.getElementType() == DOC_TAG) {
            if (aSTNode2 == null) {
                aSTNode2 = getLastChildNode();
                ASTNode aSTNodeSkipElementsBack = TreeUtil.skipElementsBack(aSTNode2.getTreePrev(), TokenSet.WHITE_SPACE);
                if (aSTNodeSkipElementsBack != null) {
                    bool = Boolean.FALSE;
                    aSTNode2 = aSTNodeSkipElementsBack;
                } else {
                    bool = Boolean.TRUE;
                }
                z = true;
            }
            if (aSTNode2.getElementType() == DOC_TAG) {
                z = true;
            } else if (nodeOnSameLineWithCommentStartBlock(aSTNode2) || !nodeIsNextAfterAsterisks(aSTNode2) || !docTagEndsWithLineFeedAndAsterisks(treeElement)) {
                CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
                PsiManagerEx manager = getManager();
                if (JavaFileCodeStyleFacade.forContext(getContainingFile()).isJavaDocLeadingAsterisksEnabled()) {
                    LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(TokenType.WHITE_SPACE, "\n ", charTableFindCharTableByTree, manager);
                    LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(DOC_COMMENT_LEADING_ASTERISKS, "*", charTableFindCharTableByTree, manager);
                    LeafElement leafElementCreateSingleLeafElement3 = Factory.createSingleLeafElement(DOC_COMMENT_DATA, " ", charTableFindCharTableByTree, manager);
                    leafElementCreateSingleLeafElement.getTreeParent().addChild(leafElementCreateSingleLeafElement2);
                    leafElementCreateSingleLeafElement.getTreeParent().addChild(leafElementCreateSingleLeafElement3);
                    super.addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement3, aSTNode2, Boolean.FALSE);
                    aSTNode2 = leafElementCreateSingleLeafElement3;
                } else {
                    LeafElement leafElementCreateSingleLeafElement4 = Factory.createSingleLeafElement(TokenType.WHITE_SPACE, "\n ", charTableFindCharTableByTree, manager);
                    aSTNode2 = super.addInternal(leafElementCreateSingleLeafElement4, leafElementCreateSingleLeafElement4, aSTNode2, Boolean.FALSE);
                }
                bool = Boolean.FALSE;
            }
        }
        if (bool.booleanValue()) {
            aSTNode2.getTreeParent().addChildren(treeElement, aSTNode.getTreeNext(), aSTNode2);
        } else {
            aSTNode2.getTreeParent().addChildren(treeElement, aSTNode.getTreeNext(), aSTNode2.getTreeNext());
        }
        if (z) {
            if (treeElement.getTreePrev() != null && treeElement.getTreePrev().getElementType() == DOC_TAG) {
                addNewLineToTag((CompositeElement) treeElement.getTreePrev(), getContainingFile(), getManager());
            }
            if (treeElement.getTreeNext() != null && treeElement.getTreeNext().getElementType() == DOC_TAG) {
                addNewLineToTag((CompositeElement) treeElement, getContainingFile(), getManager());
            } else {
                removeEndingAsterisksFromTag((CompositeElement) treeElement);
            }
        }
        return treeElement;
    }

    private static void removeEndingAsterisksFromTag(CompositeElement compositeElement) {
        ASTNode lastChildNode = compositeElement.getLastChildNode();
        while (lastChildNode != null && lastChildNode.getElementType() == DOC_COMMENT_DATA) {
            lastChildNode = lastChildNode.getTreePrev();
        }
        if (lastChildNode == null || lastChildNode.getElementType() != DOC_COMMENT_LEADING_ASTERISKS) {
            return;
        }
        ASTNode treeNext = TreeUtil.skipElementsBack(lastChildNode.getTreePrev(), TokenSet.WHITE_SPACE).getTreeNext();
        while (treeNext != null) {
            ASTNode treeNext2 = treeNext.getTreeNext();
            compositeElement.deleteChildInternal(treeNext);
            treeNext = treeNext2;
        }
    }

    private static boolean nodeIsNextAfterAsterisks(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        ASTNode aSTNodeFindSiblingBackward = TreeUtil.findSiblingBackward(aSTNode, DOC_COMMENT_LEADING_ASTERISKS);
        if (aSTNodeFindSiblingBackward == null || aSTNodeFindSiblingBackward == aSTNode) {
            return false;
        }
        while (aSTNodeFindSiblingBackward.getTreeNext() != aSTNode) {
            aSTNodeFindSiblingBackward = aSTNodeFindSiblingBackward.getTreeNext();
            CharSequence chars = aSTNodeFindSiblingBackward.getChars();
            if (CharArrayUtil.shiftForward(chars, 0, " \t") != chars.length()) {
                return false;
            }
        }
        return true;
    }

    private static boolean docTagEndsWithLineFeedAndAsterisks(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        ASTNode aSTNodeFindChildBackward = TreeUtil.findChildBackward(aSTNode, DOC_COMMENT_LEADING_ASTERISKS);
        if (aSTNodeFindChildBackward == null || !aSTNodeFindChildBackward.getTreePrev().textContains('\n')) {
            return false;
        }
        ASTNode lastChildNode = aSTNode.getLastChildNode();
        while (aSTNodeFindChildBackward != lastChildNode) {
            aSTNodeFindChildBackward = aSTNodeFindChildBackward.getTreeNext();
            CharSequence chars = aSTNodeFindChildBackward.getChars();
            if (CharArrayUtil.shiftForward(chars, 0, " \t") != chars.length()) {
                return false;
            }
        }
        return true;
    }

    private static boolean nodeOnSameLineWithCommentStartBlock(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        ASTNode aSTNodeFindSiblingBackward = TreeUtil.findSiblingBackward(aSTNode, DOC_COMMENT_START);
        if (aSTNodeFindSiblingBackward == null) {
            return false;
        }
        if (aSTNodeFindSiblingBackward == aSTNode) {
            return true;
        }
        while (aSTNodeFindSiblingBackward.getTreeNext() != aSTNode) {
            aSTNodeFindSiblingBackward = aSTNodeFindSiblingBackward.getTreeNext();
            if (aSTNodeFindSiblingBackward.textContains('\n')) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        if (aSTNode.getElementType() == DOC_TAG && (aSTNode.getTreeNext() == null || aSTNode.getTreeNext().getElementType() != DOC_TAG)) {
            ASTNode treePrev = aSTNode.getTreePrev();
            while (treePrev != null && treePrev.getElementType() == DOC_COMMENT_DATA) {
                treePrev = treePrev.getTreePrev();
            }
            ASTNode treeNext = aSTNode.getTreeNext();
            while (treeNext != null && (treeNext.getElementType() == DOC_COMMENT_DATA || treeNext.getElementType() == WHITE_SPACE)) {
                treeNext = treeNext.getTreeNext();
            }
            if (treePrev != null && treePrev.getElementType() == DOC_COMMENT_LEADING_ASTERISKS && !(treeNext instanceof PsiDocTag)) {
                if (treePrev.getTreePrev() != null) {
                    super.deleteChildInternal(treePrev.getTreePrev());
                    super.deleteChildInternal(treePrev);
                }
            } else if (treePrev != null && treePrev.getElementType() == DOC_TAG) {
                CompositeElement compositeElement = (CompositeElement) treePrev;
                ASTNode lastChildNode = compositeElement.getLastChildNode();
                while (lastChildNode != null && lastChildNode.getElementType() == DOC_COMMENT_DATA) {
                    lastChildNode = lastChildNode.getTreePrev();
                }
                if (lastChildNode != null && lastChildNode.getElementType() == DOC_COMMENT_LEADING_ASTERISKS) {
                    while (lastChildNode != null) {
                        ASTNode treeNext2 = lastChildNode.getTreeNext();
                        compositeElement.deleteChildInternal(lastChildNode);
                        lastChildNode = treeNext2;
                    }
                }
            } else {
                ASTNode treeNext3 = aSTNode.getTreeNext();
                if (treeNext3 != null && treeNext3.getElementType() == WHITE_SPACE) {
                    treeNext3.getTreeParent().removeChild(treeNext3);
                }
            }
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == DOC_TAG) {
            return 104;
        }
        if (elementType == JavaDocElementType.DOC_COMMENT || elementType == DOC_INLINE_TAG) {
            return 106;
        }
        if (elementType == DOC_COMMENT_LEADING_ASTERISKS) {
            return 107;
        }
        if (elementType == DOC_COMMENT_START) {
            return 110;
        }
        return elementType == DOC_COMMENT_END ? 111 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocComment(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiDocComment";
    }
}
