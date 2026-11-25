package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public class ClassElement extends CompositeElement implements Constants {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ClassElement.class);
    private static final TokenSet MODIFIERS_TO_REMOVE_IN_INTERFACE_BIT_SET = TokenSet.create(PUBLIC_KEYWORD, ABSTRACT_KEYWORD, STATIC_KEYWORD, FINAL_KEYWORD, NATIVE_KEYWORD);
    private static final TokenSet MODIFIERS_TO_REMOVE_IN_INTERFACE_BIT_SET_18_METHOD = TokenSet.create(PUBLIC_KEYWORD, ABSTRACT_KEYWORD, FINAL_KEYWORD, NATIVE_KEYWORD);
    private static final TokenSet MODIFIERS_TO_REMOVE_IN_ENUM_BIT_SET = TokenSet.create(PUBLIC_KEYWORD, FINAL_KEYWORD);
    private static final TokenSet ENUM_CONSTANT_LIST_ELEMENTS_BIT_SET = TokenSet.create(ENUM_CONSTANT, COMMA, SEMICOLON);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "child";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ClassElement";
        if (i != 1) {
            objArr[2] = "deleteChildInternal";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected boolean isVisibilitySupported() {
        return true;
    }

    public ClassElement(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        ASTNode aSTNodeFindChildByRole = findChildByRole(9);
        if (aSTNodeFindChildByRole != null) {
            return aSTNodeFindChildByRole.getStartOffset();
        }
        return super.getTextOffset();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r11v0, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.ClassElement] */
    /* JADX WARN: Type inference failed for: r12v0, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement] */
    /* JADX WARN: Type inference failed for: r12v1, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r12v2, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [org.jetbrains.kotlin.com.intellij.lang.ASTNode] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r4v14 */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        TokenSet tokenSet;
        IElementType elementType;
        PsiClass psiClass = (PsiClass) SourceTreeToPsiMap.treeElementToPsi(this);
        if (aSTNode2 == null && treeElement.getElementType() != JavaDocElementType.DOC_COMMENT) {
            if (bool == null) {
                if (treeElement == aSTNode) {
                    PsiElement psiElementTreeElementToPsi = SourceTreeToPsiMap.treeElementToPsi(treeElement);
                    if (psiElementTreeElementToPsi instanceof PsiEnumConstant) {
                        ASTNode aSTNodeFindEnumConstantListDelimiterPlace = findEnumConstantListDelimiterPlace();
                        bool = Boolean.valueOf(aSTNodeFindEnumConstantListDelimiterPlace != findChildByRole(18));
                        aSTNode2 = aSTNodeFindEnumConstantListDelimiterPlace;
                    } else {
                        PsiElement defaultMemberAnchor = psiElementTreeElementToPsi instanceof PsiMember ? JavaPsiImplementationHelper.getInstance(psiClass.getProject()).getDefaultMemberAnchor(psiClass, (PsiMember) psiElementTreeElementToPsi) : null;
                        ASTNode aSTNodePsiElementToTree = defaultMemberAnchor != null ? SourceTreeToPsiMap.psiElementToTree(defaultMemberAnchor) : null;
                        bool = Boolean.TRUE;
                        aSTNode2 = aSTNodePsiElementToTree;
                    }
                } else {
                    aSTNode2 = findChildByRole(19);
                    bool = Boolean.TRUE;
                }
            } else if (!bool.booleanValue()) {
                aSTNode2 = findChildByRole(18);
            } else {
                aSTNode2 = findChildByRole(19);
            }
        }
        if (isEnum() && !ENUM_CONSTANT_LIST_ELEMENTS_BIT_SET.contains(treeElement.getElementType())) {
            ASTNode aSTNodeFindEnumConstantListDelimiterPlace2 = findEnumConstantListDelimiterPlace();
            for (?? treeNext = treeElement; treeNext != 0; treeNext = treeNext.getTreeNext()) {
                if (!PsiImplUtil.isWhitespaceOrComment(treeNext)) {
                    if (aSTNodeFindEnumConstantListDelimiterPlace2 != null && aSTNodeFindEnumConstantListDelimiterPlace2.getElementType() == SEMICOLON) {
                        break;
                    }
                    LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(SEMICOLON, ";", 0, 1, SharedImplUtil.findCharTableByTree(this), getManager());
                    addInternal(leafElementCreateSingleLeafElement, leafElementCreateSingleLeafElement, aSTNodeFindEnumConstantListDelimiterPlace2, Boolean.FALSE);
                    aSTNodeFindEnumConstantListDelimiterPlace2 = leafElementCreateSingleLeafElement;
                    break;
                }
            }
            for (ASTNode treeNext2 = aSTNode2; treeNext2 != null; treeNext2 = treeNext2.getTreeNext()) {
                if (treeNext2 == aSTNodeFindEnumConstantListDelimiterPlace2) {
                    aSTNode2 = bool.booleanValue() ? aSTNodeFindEnumConstantListDelimiterPlace2.getTreeNext() : aSTNodeFindEnumConstantListDelimiterPlace2;
                    if (aSTNode2 == null || !PsiImplUtil.isWhitespaceOrComment(aSTNode2)) {
                        break;
                        break;
                    }
                    aSTNode2 = PsiTreeUtil.skipWhitespacesAndCommentsForward(aSTNode2.getPsi()).getNode();
                    break;
                }
            }
        }
        ASTNode treeNext3 = aSTNode.getTreeNext();
        ?? r2 = treeElement;
        while (r2 != treeNext3) {
            ASTNode treeNext4 = r2.getTreeNext();
            if (r2.getElementType() == JavaElementType.METHOD && ((PsiMethod) SourceTreeToPsiMap.treeElementToPsi(r2)).isConstructor()) {
                ASTNode aSTNodeFindChildByRole = ((CompositeElement) r2).findChildByRole(9);
                ASTNode aSTNodeCopyElement = findChildByRole(9).copyElement();
                aSTNodeCopyElement.putUserData(CharTable.CHAR_TABLE_KEY, SharedImplUtil.findCharTableByTree(this));
                r2.replaceChild(aSTNodeFindChildByRole, aSTNodeCopyElement);
            }
            r2 = treeNext4;
        }
        if (psiClass.isEnum()) {
            ?? r0 = treeElement;
            while (r0 != treeNext3) {
                ASTNode treeNext5 = r0.getTreeNext();
                if ((r0.getElementType() == JavaElementType.METHOD && ((PsiMethod) SourceTreeToPsiMap.treeElementToPsi(r0)).isConstructor()) || r0.getElementType() == JavaElementType.ENUM_CONSTANT) {
                    CompositeElement compositeElement = (CompositeElement) ((CompositeElement) r0).findChildByRole(8);
                    while (true) {
                        ASTNode aSTNodeFindChildByType = compositeElement.findChildByType(MODIFIERS_TO_REMOVE_IN_ENUM_BIT_SET);
                        if (aSTNodeFindChildByType == null) {
                            break;
                        }
                        compositeElement.deleteChildInternal(aSTNodeFindChildByType);
                    }
                }
                r0 = treeNext5;
            }
        } else if (psiClass.isInterface()) {
            boolean zIsLanguageLevel8OrHigher = PsiUtil.isLanguageLevel8OrHigher(psiClass);
            ?? r3 = treeElement;
            while (r3 != treeNext3) {
                ASTNode treeNext6 = r3.getTreeNext();
                IElementType elementType2 = r3.getElementType();
                if (elementType2 == JavaElementType.METHOD || elementType2 == JavaElementType.FIELD) {
                    CompositeElement compositeElement2 = (CompositeElement) ((CompositeElement) r3).findChildByRole(8);
                    if (zIsLanguageLevel8OrHigher && elementType2 == JavaElementType.METHOD) {
                        tokenSet = MODIFIERS_TO_REMOVE_IN_INTERFACE_BIT_SET_18_METHOD;
                    } else {
                        tokenSet = MODIFIERS_TO_REMOVE_IN_INTERFACE_BIT_SET;
                    }
                    while (true) {
                        ASTNode aSTNodeFindChildByType2 = compositeElement2.findChildByType(tokenSet);
                        if (aSTNodeFindChildByType2 == null) {
                            break;
                        }
                        compositeElement2.deleteChildInternal(aSTNodeFindChildByType2);
                    }
                }
                r3 = treeNext6;
            }
        }
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        if (treeElementAddInternal.getElementType() == ENUM_CONSTANT) {
            CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
            for (ASTNode treeNext7 = treeElement.getTreeNext(); treeNext7 != null; treeNext7 = treeNext7.getTreeNext()) {
                IElementType elementType3 = treeNext7.getElementType();
                if (elementType3 == COMMA || elementType3 == SEMICOLON) {
                    break;
                }
                if (elementType3 == ENUM_CONSTANT) {
                    LeafElement leafElementCreateSingleLeafElement2 = Factory.createSingleLeafElement(COMMA, ",", 0, 1, charTableFindCharTableByTree, getManager());
                    super.addInternal(leafElementCreateSingleLeafElement2, leafElementCreateSingleLeafElement2, treeElement, Boolean.FALSE);
                    break;
                }
            }
            do {
                treeElement = treeElement.getTreePrev();
                if (treeElement == 0 || (elementType = treeElement.getElementType()) == COMMA || elementType == SEMICOLON) {
                }
            } while (elementType != ENUM_CONSTANT);
            LeafElement leafElementCreateSingleLeafElement3 = Factory.createSingleLeafElement(COMMA, ",", 0, 1, charTableFindCharTableByTree, getManager());
            super.addInternal(leafElementCreateSingleLeafElement3, leafElementCreateSingleLeafElement3, treeElement, Boolean.FALSE);
        }
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        ASTNode aSTNodeFindSibling;
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (isEnum() && aSTNode.getElementType() == ENUM_CONSTANT) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        if (aSTNode.getElementType() == FIELD && (aSTNodeFindSibling = TreeUtil.findSibling(aSTNode.getTreeNext(), FIELD)) != null && ((PsiField) aSTNodeFindSibling.getPsi()).getTypeElement().equals(((PsiField) aSTNode.getPsi()).getTypeElement())) {
            CharTable charTableFindCharTableByTree = SharedImplUtil.findCharTableByTree(this);
            ASTNode aSTNodeFindChildByType = aSTNode.findChildByType(MODIFIER_LIST);
            if (aSTNodeFindChildByType != null) {
                LeafElement leafElementCreateSingleLeafElement = Factory.createSingleLeafElement(WHITE_SPACE, " ", 0, 1, charTableFindCharTableByTree, getManager());
                aSTNodeFindSibling.addChild(leafElementCreateSingleLeafElement, aSTNodeFindSibling.getFirstChildNode());
                ASTNode aSTNodeFindChildByType2 = aSTNode.findChildByType(TYPE);
                if (aSTNodeFindChildByType2 == null) {
                    aSTNodeFindSibling.addChild(ChangeUtil.copyElement((TreeElement) aSTNodeFindChildByType, charTableFindCharTableByTree), leafElementCreateSingleLeafElement);
                } else {
                    while (true) {
                        aSTNodeFindSibling.addChild(ChangeUtil.copyElement((TreeElement) aSTNodeFindChildByType, charTableFindCharTableByTree), leafElementCreateSingleLeafElement);
                        if (aSTNodeFindChildByType == aSTNodeFindChildByType2) {
                            break;
                        } else {
                            aSTNodeFindChildByType = aSTNodeFindChildByType.getTreeNext();
                        }
                    }
                }
            }
        }
        super.deleteChildInternal(aSTNode);
    }

    public boolean isEnum() {
        ASTNode aSTNodeFindChildByRole = findChildByRole(11);
        return aSTNodeFindChildByRole != null && aSTNodeFindChildByRole.getElementType() == ENUM_KEYWORD;
    }

    public boolean isAnnotationType() {
        return findChildByRole(247) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        ASTNode treeNext;
        if (i == 7) {
            return PsiImplUtil.findDocComment(this);
        }
        if (i == 8) {
            return findChildByType(MODIFIER_LIST);
        }
        if (i == 9) {
            return findChildByType(IDENTIFIER);
        }
        if (i == 18) {
            return findChildByType(LBRACE);
        }
        if (i == 19) {
            return TreeUtil.findChildBackward(this, RBRACE);
        }
        if (i == 126) {
            if (isEnum()) {
                return findEnumConstantListDelimiter();
            }
            return null;
        }
        if (i == 127) {
            return findChildByType(PERMITS_LIST);
        }
        if (i == 245) {
            return findChildByType(TYPE_PARAMETER_LIST);
        }
        if (i != 247) {
            switch (i) {
                case 11:
                    for (ASTNode firstChildNode = getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
                        if (CLASS_KEYWORD_BIT_SET.contains(firstChildNode.getElementType())) {
                            return firstChildNode;
                        }
                    }
                    String str = "Node not found. Immediate children are:---\n";
                    for (ASTNode firstChildNode2 = getFirstChildNode(); firstChildNode2 != null; firstChildNode2 = firstChildNode2.getTreeNext()) {
                        str = str + firstChildNode2.getClass() + "(" + firstChildNode2.getElementType() + ") " + firstChildNode2 + "\n";
                    }
                    LOG.error(str + "---");
                    return null;
                case 12:
                    return findChildByType(EXTENDS_LIST);
                case 13:
                    return findChildByType(IMPLEMENTS_LIST);
                default:
                    return null;
            }
        }
        ASTNode aSTNodeFindChildByRole = findChildByRole(8);
        if (aSTNodeFindChildByRole != null && (treeNext = aSTNodeFindChildByRole.getTreeNext()) != null) {
            ASTNode aSTNodeSkipWhitespaceAndComments = PsiImplUtil.skipWhitespaceAndComments(treeNext);
            if (aSTNodeSkipWhitespaceAndComments.getElementType() == AT) {
                return aSTNodeSkipWhitespaceAndComments;
            }
        }
        return null;
    }

    private ASTNode findEnumConstantListDelimiter() {
        ASTNode aSTNodeFindEnumConstantListDelimiterPlace = findEnumConstantListDelimiterPlace();
        if (aSTNodeFindEnumConstantListDelimiterPlace == null || aSTNodeFindEnumConstantListDelimiterPlace.getElementType() != SEMICOLON) {
            return null;
        }
        return aSTNodeFindEnumConstantListDelimiterPlace;
    }

    public ASTNode findEnumConstantListDelimiterPlace() {
        ASTNode aSTNodeFindChildByRole = findChildByRole(18);
        if (aSTNodeFindChildByRole == null) {
            return null;
        }
        while (true) {
            aSTNodeFindChildByRole = aSTNodeFindChildByRole.getTreeNext();
            if (aSTNodeFindChildByRole == null) {
                return null;
            }
            IElementType elementType = aSTNodeFindChildByRole.getElementType();
            if (!PsiImplUtil.isWhitespaceOrComment(aSTNodeFindChildByRole) && elementType != ERROR_ELEMENT && elementType != ENUM_CONSTANT && elementType != COMMA) {
                return elementType == SEMICOLON ? aSTNodeFindChildByRole : PsiImplUtil.skipWhitespaceAndCommentsBack(aSTNodeFindChildByRole.getTreePrev());
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == SEMICOLON) {
            return (isEnum() && aSTNode == findEnumConstantListDelimiter()) ? 126 : 0;
        }
        if (elementType == CLASS) {
            return 3;
        }
        if (elementType == FIELD) {
            return 4;
        }
        if (elementType == METHOD || elementType == ANNOTATION_METHOD) {
            return 5;
        }
        if (elementType == CLASS_INITIALIZER) {
            return 6;
        }
        if (elementType == TYPE_PARAMETER_LIST) {
            return 245;
        }
        if (elementType == JavaDocElementType.DOC_COMMENT) {
            return getChildRole(aSTNode, 7);
        }
        if (ElementType.JAVA_PLAIN_COMMENT_BIT_SET.contains(elementType)) {
            return 0;
        }
        if (elementType == MODIFIER_LIST) {
            return 8;
        }
        if (elementType == EXTENDS_LIST) {
            return 12;
        }
        if (elementType == IMPLEMENTS_LIST) {
            return 13;
        }
        if (elementType == PERMITS_LIST) {
            return 127;
        }
        if (ElementType.CLASS_KEYWORD_BIT_SET.contains(elementType)) {
            return getChildRole(aSTNode, 11);
        }
        if (elementType == IDENTIFIER) {
            return getChildRole(aSTNode, 9);
        }
        if (elementType == LBRACE) {
            return getChildRole(aSTNode, 18);
        }
        if (elementType == RBRACE) {
            return getChildRole(aSTNode, 19);
        }
        if (elementType == COMMA) {
            return 23;
        }
        return elementType == AT ? 247 : 0;
    }
}
