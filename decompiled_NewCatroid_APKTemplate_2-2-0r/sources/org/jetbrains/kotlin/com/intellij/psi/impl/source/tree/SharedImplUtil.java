package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class SharedImplUtil {
    private static final boolean CHECK_FOR_READ_ACTION;
    private static final Logger LOG = Logger.getInstance((Class<?>) SharedImplUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 2:
            case 3:
            case 4:
                objArr[0] = "thisElement";
                break;
            case 5:
            default:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
            case 7:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/SharedImplUtil";
                break;
            case 8:
            case 12:
                objArr[0] = "node";
                break;
            case 9:
            case 13:
                objArr[0] = "elementType";
                break;
            case 14:
                objArr[0] = "visitor";
                break;
            case 15:
                objArr[0] = "root";
                break;
            case 16:
                objArr[0] = "psiElement";
                break;
            case 17:
                objArr[0] = "treeElement";
                break;
            case 18:
                objArr[0] = "newElement";
                break;
        }
        if (i == 6 || i == 7) {
            objArr[1] = "findCharTableByTree";
        } else if (i == 10 || i == 11) {
            objArr[1] = "getChildrenOfType";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/SharedImplUtil";
        }
        switch (i) {
            case 1:
                objArr[2] = "getLastChild";
                break;
            case 2:
                objArr[2] = "getNextSibling";
                break;
            case 3:
                objArr[2] = "getPrevSibling";
                break;
            case 4:
                objArr[2] = "getContainingFile";
                break;
            case 5:
                objArr[2] = "findFileElement";
                break;
            case 6:
            case 7:
            case 10:
            case 11:
                break;
            case 8:
            case 9:
                objArr[2] = "getChildrenOfType";
                break;
            case 12:
            case 13:
                objArr[2] = "countChildrenOfType";
                break;
            case 14:
            case 15:
                objArr[2] = "acceptChildren";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "doReplace";
                break;
            default:
                objArr[2] = "getFirstChild";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        CHECK_FOR_READ_ACTION = DebugUtil.DO_EXPENSIVE_CHECKS || ApplicationManager.getApplication().isInternal();
    }

    private SharedImplUtil() {
    }

    public static PsiElement getParent(ASTNode aSTNode) {
        if (CHECK_FOR_READ_ACTION && (aSTNode instanceof TreeElement)) {
            ((TreeElement) aSTNode).assertReadAccessAllowed();
        }
        return SourceTreeToPsiMap.treeElementToPsi(aSTNode.getTreeParent());
    }

    public static PsiElement getFirstChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        return SourceTreeToPsiMap.treeElementToPsi(aSTNode.getFirstChildNode());
    }

    public static PsiElement getLastChild(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return SourceTreeToPsiMap.treeElementToPsi(aSTNode.getLastChildNode());
    }

    public static PsiElement getNextSibling(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        return SourceTreeToPsiMap.treeElementToPsi(aSTNode.getTreeNext());
    }

    public static PsiElement getPrevSibling(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        return SourceTreeToPsiMap.treeElementToPsi(aSTNode.getTreePrev());
    }

    public static PsiFile getContainingFile(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        FileASTNode fileASTNodeFindFileElement = findFileElement(aSTNode);
        PsiElement psi = fileASTNodeFindFileElement == null ? null : fileASTNodeFindFileElement.getPsi();
        if (psi == null || (psi instanceof PsiFile)) {
            return (PsiFile) psi;
        }
        throw new AssertionError("Invalid PSI " + psi + " of " + psi.getClass() + " for AST " + fileASTNodeFindFileElement + " of " + fileASTNodeFindFileElement.getClass());
    }

    public static boolean isValid(ASTNode aSTNode) {
        LOG.assertTrue(aSTNode instanceof PsiElement);
        PsiFile containingFile = getContainingFile(aSTNode);
        return containingFile != null && containingFile.isValid();
    }

    public static boolean isWritable(ASTNode aSTNode) {
        PsiFile containingFile = getContainingFile(aSTNode);
        return containingFile == null || containingFile.isWritable();
    }

    public static FileASTNode findFileElement(ASTNode aSTNode) {
        ASTNode aSTNode2;
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        ASTNode treeParent = aSTNode.getTreeParent();
        while (true) {
            ASTNode aSTNode3 = treeParent;
            aSTNode2 = aSTNode;
            aSTNode = aSTNode3;
            if (aSTNode == null) {
                break;
            }
            treeParent = aSTNode.getTreeParent();
        }
        if (CHECK_FOR_READ_ACTION && (aSTNode2 instanceof TreeElement)) {
            ((TreeElement) aSTNode2).assertReadAccessAllowed();
        }
        if (aSTNode2 instanceof FileASTNode) {
            return (FileASTNode) aSTNode2;
        }
        return null;
    }

    public static CharTable findCharTableByTree(ASTNode aSTNode) {
        for (ASTNode treeParent = aSTNode; treeParent != null; treeParent = treeParent.getTreeParent()) {
            CharTable charTable = (CharTable) treeParent.getUserData(CharTable.CHAR_TABLE_KEY);
            if (charTable != null) {
                if (charTable == null) {
                    $$$reportNull$$$0(6);
                }
                return charTable;
            }
            if (treeParent instanceof FileASTNode) {
                CharTable charTable2 = ((FileASTNode) treeParent).getCharTable();
                if (charTable2 == null) {
                    $$$reportNull$$$0(7);
                }
                return charTable2;
            }
        }
        throw new AssertionError("CharTable not found in: " + aSTNode);
    }

    public static PsiElement addRange(PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3, ASTNode aSTNode, Boolean bool) throws IncorrectOperationException {
        CheckUtil.checkWritable(psiElement);
        CharTable charTableFindCharTableByTree = findCharTableByTree(SourceTreeToPsiMap.psiElementToTree(psiElement));
        ASTNode treeNext = SourceTreeToPsiMap.psiElementToTree(psiElement3).getTreeNext();
        TreeElement treeElement = null;
        TreeElement treeElement2 = null;
        CompositeElement treeParent = null;
        for (ASTNode aSTNodePsiElementToTree = SourceTreeToPsiMap.psiElementToTree(psiElement2); aSTNodePsiElementToTree != treeNext; aSTNodePsiElementToTree = aSTNodePsiElementToTree.getTreeNext()) {
            TreeElement treeElementCopyElement = ChangeUtil.copyElement((TreeElement) aSTNodePsiElementToTree, charTableFindCharTableByTree);
            if (aSTNodePsiElementToTree == psiElement2.getNode()) {
                treeElement = treeElementCopyElement;
            }
            if (aSTNodePsiElementToTree == psiElement3.getNode()) {
                treeElement2 = treeElementCopyElement;
            }
            if (treeParent == null) {
                treeParent = treeElementCopyElement.getTreeParent();
            } else {
                if (treeElementCopyElement.getElementType() == TokenType.WHITE_SPACE) {
                    CodeEditUtil.setNodeGenerated(treeElementCopyElement, true);
                }
                treeParent.addChild(treeElementCopyElement, null);
            }
        }
        if (treeElement == null) {
            return null;
        }
        TreeElement treeElementAddInternal = ((CompositeElement) SourceTreeToPsiMap.psiElementToTree(psiElement)).addInternal(treeElement, treeElement2, aSTNode, bool);
        TreeElement treeElement3 = treeElementAddInternal;
        while (treeElementAddInternal != null) {
            TreeElement treeElementDecodeInformation = ChangeUtil.decodeInformation(treeElementAddInternal);
            if (treeElementDecodeInformation.getTreePrev() == null) {
                treeElement3 = treeElementDecodeInformation;
            }
            treeElementAddInternal = treeElementDecodeInformation.getTreeNext();
        }
        return SourceTreeToPsiMap.treeElementToPsi(treeElement3);
    }

    public static PsiManager getManagerByTree(ASTNode aSTNode) {
        return aSTNode instanceof FileElement ? aSTNode.getPsi().getManager() : aSTNode.getTreeParent().getPsi().getManager();
    }

    public static ASTNode[] getChildrenOfType(ASTNode aSTNode, IElementType iElementType) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(9);
        }
        int iCountChildrenOfType = countChildrenOfType(aSTNode, iElementType);
        if (iCountChildrenOfType == 0) {
            ASTNode[] aSTNodeArr = ASTNode.EMPTY_ARRAY;
            if (aSTNodeArr == null) {
                $$$reportNull$$$0(10);
            }
            return aSTNodeArr;
        }
        ASTNode[] aSTNodeArr2 = new ASTNode[iCountChildrenOfType];
        int i = 0;
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getElementType() == iElementType) {
                aSTNodeArr2[i] = firstChildNode;
                i++;
            }
        }
        return aSTNodeArr2;
    }

    private static int countChildrenOfType(ASTNode aSTNode, IElementType iElementType) {
        if (aSTNode == null) {
            $$$reportNull$$$0(12);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(13);
        }
        int i = 0;
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode.getElementType() == iElementType) {
                i++;
            }
        }
        return i;
    }

    public static void acceptChildren(PsiElementVisitor psiElementVisitor, ASTNode aSTNode) {
        PsiElement psi;
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(14);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(15);
        }
        for (ASTNode firstChildNode = aSTNode.getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (firstChildNode instanceof PsiElement) {
                psi = (PsiElement) firstChildNode;
            } else {
                psi = firstChildNode.getPsi();
            }
            psi.accept(psiElementVisitor);
        }
    }

    public static PsiElement doReplace(PsiElement psiElement, TreeElement treeElement, PsiElement psiElement2) {
        if (psiElement == null) {
            $$$reportNull$$$0(16);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(18);
        }
        CompositeElement treeParent = treeElement.getTreeParent();
        LOG.assertTrue(treeParent != null);
        CheckUtil.checkWritable(psiElement);
        TreeElement treeElementCopyToElement = ChangeUtil.copyToElement(psiElement2);
        treeParent.replaceChildInternal(treeElement, treeElementCopyToElement);
        return SourceTreeToPsiMap.treeElementToPsi(ChangeUtil.decodeInformation(treeElementCopyToElement));
    }
}
